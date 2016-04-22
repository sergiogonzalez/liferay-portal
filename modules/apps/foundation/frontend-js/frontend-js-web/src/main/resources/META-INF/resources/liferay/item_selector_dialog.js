AUI.add(
	'liferay-item-selector-dialog',
	function(A) {
		var Lang = A.Lang;

		var Util = Liferay.Util;

		var STR_EVENT_NAME = 'eventName';

		var STR_SELECTED_ITEM = 'selectedItem';

		var LiferayItemSelectorDialog = A.Component.create(
			{
				ATTRS: {
					editUrl: {
						validator: Lang.isString
					},

					eventName: {
						validator: Lang.isString
					},

					selectedItem: {
					},

					strings: {
						value: {
							add: Liferay.Language.get('add'),
							cancel: Liferay.Language.get('cancel'),
							edit: Liferay.Language.get('edit'),
							save: Liferay.Language.get('save')
						}
					},

					title: {
						validator: Lang.isString,
						value: Liferay.Language.get('select-file')
					},

					url: {
						validator: Lang.isString
					},

					zIndex: {
						validator: Lang.isNumber
					}
				},

				NAME: 'item-selector-dialog',

				NS: 'item-selector-dialog',

				prototype: {
					close: function() {
						var instance = this;

						Util.getWindow(instance.get(STR_EVENT_NAME)).hide();
					},

					open: function() {
						var instance = this;

						var strings = instance.get('strings');

						var eventName = instance.get(STR_EVENT_NAME);

						var zIndex = instance.get('zIndex');

						instance._currentItem = null;
						instance._selectedItem = null;

						var toolbarFooter = [
							{
								cssClass: 'btn-lg btn-primary',
								disabled: true,
								id: 'addButton',
								label: strings.add,
								on: {
									click: function() {
										instance._selectedItem = instance._currentItem;
										instance.close();
									}
								}
							},
							{
								cssClass: 'btn-lg btn-link close-modal',
								id: 'cancelButton',
								label: strings.cancel,
								on: {
									click: function() {
										instance.close();
									}
								}
							}
						];

						if (instance.get('editUrl')) {
							toolbarFooter.push(
								{
									cssClass: 'btn-lg btn-default pull-right',
									disabled: true,
									id: 'editButton',
									label: strings.edit,
									on: {
										click: function() {
											var portletURL = new Liferay.PortletURL.createURL(instance.get('editUrl'));

											portletURL.setParameter('image_editor_url', instance._currentItem.value);

											Liferay.Util.openWindow(
												{
													dialog: {
														zIndex: ++zIndex,
														'toolbars.footer': [
															{
																cssClass: 'btn-lg btn-primary',
																id: 'saveButton',
																label: strings.save
															},
															{
																cssClass: 'btn-lg btn-link close-modal',
																id: 'cancelButton',
																label: strings.cancel
															}
														]
													},
													uri: portletURL.toString(),
													stack: !zIndex,
													title: Liferay.Language.get('Edit Image')
												}
											);
										}
									}
								}
							);
						}

						Util.selectEntity(
							{
								dialog: {
									constrain: true,
									destroyOnHide: true,
									modal: true,
									on: {
										'visibleChange': function(event) {
											if (!event.newVal) {
												instance.set(STR_SELECTED_ITEM, instance._selectedItem);
											}
										}
									},
									'toolbars.footer': toolbarFooter,
									zIndex: ++zIndex
								},
								eventName: eventName,
								id: eventName,
								stack: !zIndex,
								title: instance.get('title'),
								uri: instance.get('url')
							},
							A.bind(instance._onItemSelected, instance)
						);
					},

					_onItemSelected: function(event) {
						var instance = this;

						var currentItem = event.data;

						var dialog = Util.getWindow(instance.get(STR_EVENT_NAME));

						var footerNode = dialog.getToolbar('footer').get('boundingBox');

						var addButton = footerNode.one('#addButton');
						var editButton = footerNode.one('#editButton');

						Util.toggleDisabled(addButton, !currentItem);
						Util.toggleDisabled(editButton, !currentItem);

						instance._currentItem = currentItem;
					}
				}
			}
		);

		A.LiferayItemSelectorDialog = LiferayItemSelectorDialog;
	},
	'',
	{
		requires: ['aui-component', 'liferay-portlet-url']
	}
);