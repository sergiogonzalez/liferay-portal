AUI.add(
	'liferay-app-view-dd-navigation',
	function(A) {
		var AObject = A.Object;
		var History = Liferay.HistoryManager;
		var Lang = A.Lang;
		var UA = A.UA;

		var CSS_ACTIVE_AREA = 'active-area';

		var CSS_ACTIVE_AREA_PROXY = 'active-area-proxy';

		var CSS_SELECTED = 'selected';

		var DATA_FOLDER_ID = 'data-folder-id';

		var SELECTOR_DRAGGABLE_NODES = '[data-draggable]';

		var STR_ACTIONS = 'actions';

		var STR_BLANK = '';

		var STR_DATA = 'data';

		var STR_DISPLAY_STYLE = 'displayStyleCSSClass';

		var STR_DRAG_NODE = 'dragNode';

		var STR_FORM = 'form';

		var STR_PORTLET_GROUP = 'portletGroup';

		var TOUCH = UA.touch;

		var AppViewDDNavigation = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				ATTRS: {
					actions: {
						validator: Lang.isObject
					},
					allRowIds: {
						validator: Lang.isString
					},
					processEntryIds: {
						validator: Lang.isObject
					},
					displayStyleCSSClass: {
						validator: Lang.isString
					},
					draggableCSSClass: {
						validator: Lang.isString
					},
					editEntryUrl: {
						validator: Lang.isString
					},
					folderIdHashRegEx:{
						setter: function(value) {
							if (Lang.isString(value)) {
								value = new RegExp(value);
							}

							return value;
						},
						validator: function(value) {
							return (value instanceof RegExp || Lang.isString(value));
						}
					},
					form: {
						validator: Lang.isObject
					},
					moveConstant: { // No one is setting this param, where does it come from?
						validator: Lang.isNumber
					},
					moveEntryRenderUrl: {
						validator: Lang.isString
					},
					namespace:  {
						validator: Lang.isString
					},
					portletContainerId:  {
						validator: Lang.isString
					},
					portletGroup:  {
						validator: Lang.isString
					},
					selectNavigation: {
						validator: Lang.isObject
					},
					updateable: {
						validator: Lang.isBoolean
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferayappviewddnavigation',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._portletContainer = instance.byId(instance.get('portletContainerId'));

						instance._entriesContainer = instance.byId('entriesContainer');

						instance._eventEditEntry = instance.ns('editEntry');

						var eventHandles = [
							Liferay.on(instance._eventEditEntry, instance._editEntry, instance)
						];

						instance._eventHandles = eventHandles;

						if (themeDisplay.isSignedIn() && this.get('updateable')) {
							instance._initDragDrop();
						}
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						instance._ddHandler.destroy();
					},

					_editEntry: function(event) {
						var instance = this;

						var action = event.action;

						var url = instance.get('editEntryUrl');

						var actions = instance.get(STR_ACTIONS);

						if (action === actions.MOVE) {
							url = instance.get('moveEntryRenderUrl');
						}

						instance._processEntryAction(action, url);
					},

					_getMoveText: function(selectedItemsCount, targetAvailable) {
						var moveText = STR_BLANK;

						if (targetAvailable) {
							moveText = Liferay.Language.get('x-item-is-ready-to-be-moved-to-x');

							if (selectedItemsCount > 1) {
								moveText = Liferay.Language.get('x-items-are-ready-to-be-moved-to-x');
							}
						}
						else {
							moveText = Liferay.Language.get('x-item-is-ready-to-be-moved');

							if (selectedItemsCount > 1) {
								moveText = Liferay.Language.get('x-items-are-ready-to-be-moved');
							}
						}

						return moveText;
					},

					_initDragDrop: function() {
						var instance = this;

						var ddHandler = new A.DD.Delegate(
							{
								container: instance._portletContainer,
								nodes: SELECTOR_DRAGGABLE_NODES,
								on: {
									'drag:drophit': A.bind(instance._onDragDropHit, instance),
									'drag:enter': A.bind(instance._onDragEnter, instance),
									'drag:exit': A.bind(instance._onDragExit, instance),
									'drag:start': A.bind(instance._onDragStart, instance)
								}
							}
						);

						var dd = ddHandler.dd;

						dd.set('offsetNode', false);

						dd.removeInvalid('a');

						dd.set('groups', [instance.get(STR_PORTLET_GROUP)]);

						dd.plug(
							[
								{
									cfg: {
										moveOnEnd: false
									},
									fn: A.Plugin.DDProxy
								},
								{
									cfg: {
										constrain2node: instance._portletContainer
									},
									fn: A.Plugin.DDConstrained
								}
							]
						);

						if (TOUCH) {
							instance._dragTask = A.debounce(
								function(entryLink) {
									if (entryLink) {
										entryLink.simulate('click');
									}
								},
								A.DD.DDM.get('clickTimeThresh')
							);

							dd.after(
								'afterMouseDown',
								function(event) {
									instance._dragTask(event.target.get('node').one(instance.get('draggableCSSClass')));
								},
								instance
							);
						}

						instance._initDropTargets();

						instance._ddHandler = ddHandler;
					},

					_initDropTargets: function() {
						var instance = this;

						if (themeDisplay.isSignedIn()) {
							var items = instance._portletContainer.all('[data-folder="true"]');

							items.each(
								function(item, index, collection) {
									item.plug(
										A.Plugin.Drop,
										{
											groups: [instance.get(STR_PORTLET_GROUP)],
											padding: '-1px'
										}
									);
								}
							);
						}
					},

					_moveEntries: function(folderId) {
						var instance = this;

						var form = instance.get(STR_FORM).node;

						form.get(instance.ns('newFolderId')).val(folderId);

						var config = instance._config;

						instance._processEntryAction(this.get('moveConstant'), this.get('moveEntryRenderUrl'));
					},

					_onDragDropHit: function(event) {
						var instance = this;

						var proxyNode = event.target.get(STR_DRAG_NODE);

						proxyNode.removeClass(CSS_ACTIVE_AREA_PROXY);

						proxyNode.empty();

						var dropTarget = event.drop.get('node');

						var folderId = dropTarget.attr(DATA_FOLDER_ID);

						var folderContainer = dropTarget.ancestor('.' + instance.get(STR_DISPLAY_STYLE));

						var selectedItems = instance._ddHandler.dd.get(STR_DATA).selectedItems;

						if (selectedItems.indexOf(folderContainer) == -1) {
							instance._moveEntries(folderId);
						}
					},

					_onDragEnter: function(event) {
						var instance = this;

						var dragNode = event.drag.get('node');
						var dropTarget = event.drop.get('node');

						dropTarget = dropTarget.ancestor('.' + instance.get(STR_DISPLAY_STYLE)) || dropTarget;

						if (!dragNode.compareTo(dropTarget)) {
							dropTarget.addClass(CSS_ACTIVE_AREA);

							var proxyNode = event.target.get(STR_DRAG_NODE);

							var dd = instance._ddHandler.dd;

							var selectedItemsCount = dd.get(STR_DATA).selectedItemsCount;

							var moveText = instance._getMoveText(selectedItemsCount, true);

							var itemTitle = Lang.trim(dropTarget.attr('data-title'));

							proxyNode.html(Lang.sub(moveText, [selectedItemsCount, itemTitle]));
						}
					},

					_onDragExit: function(event) {
						var instance = this;

						var dropTarget = event.drop.get('node');

						dropTarget = dropTarget.ancestor('.' + instance.get(STR_DISPLAY_STYLE)) || dropTarget;

						dropTarget.removeClass(CSS_ACTIVE_AREA);

						var proxyNode = event.target.get(STR_DRAG_NODE);

						var selectedItemsCount = instance._ddHandler.dd.get(STR_DATA).selectedItemsCount;

						var moveText = instance._getMoveText(selectedItemsCount);

						proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));
					},

					_onDragStart: function(event) {
						var instance = this;

						if (instance._dragTask) {
							instance._dragTask.cancel();
						}

						var target = event.target;

						var node = target.get('node');

						var selectNavigation = instance.get('selectNavigation');

						if (!node.hasClass(CSS_SELECTED)) {
							selectNavigation._unselectAllEntries();

							selectNavigation._toggleSelected(node);
						}

						var proxyNode = target.get(STR_DRAG_NODE);

						proxyNode.setStyles(
							{
								height: STR_BLANK,
								width: STR_BLANK
							}
						);

						var selectedItems = instance._entriesContainer.all('.' + instance.get(STR_DISPLAY_STYLE) + '.selected');

						var selectedItemsCount = selectedItems.size();

						var moveText = instance._getMoveText(selectedItemsCount);

						proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));

						proxyNode.addClass(CSS_ACTIVE_AREA_PROXY);

						var dd = instance._ddHandler.dd;

						dd.set(
							STR_DATA,
							{
								selectedItemsCount: selectedItemsCount,
								selectedItems: selectedItems
							}
						);
					},

					_processEntryAction: function(action, url) {
						var instance = this;

						var form = instance.get(STR_FORM).node;

						var redirectUrl = location.href;

						var actions = instance.get(STR_ACTIONS);

						if (action === actions.DELETE && !History.HTML5 && location.hash) {
							redirectUrl = instance._updateFolderIdRedirectUrl(redirectUrl);
						}

						form.attr('method', instance.get(STR_FORM).method);

						form.get(instance.ns('cmd')).val(action);
						form.get(instance.ns('redirect')).val(redirectUrl);

						var allRowIds = instance.get('allRowIds');

						var allRowsIdCheckbox = instance.ns(allRowIds + 'Checkbox');

						var processEntryIds = instance.get('processEntryIds');

						var entryIds = processEntryIds.entryIds;

						var checkBoxesIds = processEntryIds.checkBoxesIds;

						for (var i = 0, checkBoxesIdsLength = checkBoxesIds.length; i < checkBoxesIdsLength; i++) {
							var listEntryIds = Liferay.Util.listCheckedExcept(form, allRowsIdCheckbox, checkBoxesIds[i]);

							form.get(entryIds[i]).val(listEntryIds);
						}

						submitForm(form, url);
					},

					_updateFolderIdRedirectUrl: function(redirectUrl) {
						var instance = this;

						var currentFolderMatch = instance.get('folderIdHashRegEx').exec(redirectUrl);

						if (currentFolderMatch) {
							var currentFolderId = currentFolderMatch[1];

							redirectUrl = redirectUrl.replace(
								this.get('folderIdRegEx'),
								function(match, folderId) {
									return match.replace(folderId, currentFolderId);
								}
							);
						}

						return redirectUrl;
					}
				}
			}
		);

		Liferay.AppViewDDNavigation = AppViewDDNavigation;
	},
	'',
	{
		requires: ['aui-base', 'dd-constrain', 'dd-delegate', 'dd-drag', 'dd-drop', 'dd-proxy', 'liferay-history-manager', 'liferay-portlet-base', 'liferay-util-list-fields']
	}
);