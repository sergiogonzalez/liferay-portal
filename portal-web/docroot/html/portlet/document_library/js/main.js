AUI.add(
	'liferay-document-library',
	function(A) {
		var AObject = A.Object;
		var Lang = A.Lang;
		var History = Liferay.HistoryManager;

		var DISPLAY_STYLE_TOOLBAR = 'displayStyleToolbar';

		var MESSAGE_TYPE_ERROR = 'error';

		var SEARCH_TYPE = 'searchType';

		var SEARCH_TYPE_SINGLE = 1;

		var STR_ROW_IDS_FILE_SHORTCUT_CHECKBOX = 'rowIdsDLFileShortcut';

		var STR_ROW_IDS_FOLDER_CHECKBOX = 'rowIdsFolder';

		var STR_ROW_IDS_FILE_ENTRY_CHECKBOX = 'rowIdsFileEntry';

		var STR_SELECTED_FOLDER = 'selectedFolder';

		var STR_SHOW_REPOSITORY_TABS = 'showRepositoryTabs';

		var STR_SHOW_SEARCH_INFO = 'showSearchInfo';

		var SRC_HISTORY = 2;

		var SRC_SEARCH = 3;

		var WIN = A.config.win;

		var HTML5_UPLOAD = (WIN && WIN.File && WIN.FormData && WIN.XMLHttpRequest);

		var DocumentLibrary = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'documentlibrary',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var documentLibraryContainer = instance.byId('documentLibraryContainer');

						instance._documentLibraryContainer = documentLibraryContainer;

						instance._eventDataProcessed = instance.ns('dataProcessed');
						instance._eventDataRequest = instance.ns('dataRequest');
						instance._eventOpenDocument = instance.ns('openDocument');
						instance._entriesContainer = instance.byId('entriesContainer');

						if (!config.syncMessageDisabled) {
							instance._syncMessage = new Liferay.Message(
								{
									boundingBox: instance.byId('syncNotification'),
									contentBox: instance.byId('syncNotificationContent'),
									id: instance.NS + 'show-sync-message',
									trigger: instance.one('#showSyncMessageIconContainer'),
									visible: true
								}
							).render();
						}

						var checkBoxesId = [
							instance.ns(STR_ROW_IDS_FILE_SHORTCUT_CHECKBOX),
							instance.ns(STR_ROW_IDS_FOLDER_CHECKBOX),
							instance.ns(STR_ROW_IDS_FILE_ENTRY_CHECKBOX)
						];

						var displayStyle = config.displayStyle;

						var displayStyleCSSClass = 'entry-display-style';

						var displayStyleToolbar = instance.byId(DISPLAY_STYLE_TOOLBAR);

						var namespace = instance.NS;

						var portletContainerId = instance.ns('documentLibraryContainer');

						var selectConfig = config.select;

						selectConfig.checkBoxesId = checkBoxesId;
						selectConfig.displayStyle = displayStyle;
						selectConfig.displayStyleCSSClass = displayStyleCSSClass;
						selectConfig.displayStyleToolbar = displayStyleToolbar;
						selectConfig.folderContainer = instance.byId('folderContainer');
						selectConfig.namespace = namespace;
						selectConfig.portletContainerId = portletContainerId;
						selectConfig.repositories = config.repositories;
						selectConfig.selector = 'entry-selector';

						instance._appViewSelect = new Liferay.AppViewSelect(selectConfig);

						var moveConfig = config.move;

						moveConfig.processEntryIds = {
							checkBoxesIds: checkBoxesId,
							entryIds: [
								instance.ns('fileShortcutIds'),
								instance.ns('folderIds'),
								instance.ns('fileEntryIds')
							]
						};

						moveConfig.displayStyleCSSClass = displayStyleCSSClass;
						moveConfig.draggableCSSClass = '.entry-link';
						moveConfig.namespace = namespace;
						moveConfig.portletContainerId = portletContainerId;
						moveConfig.portletGroup = 'document-library';

						instance._appViewMove = new Liferay.AppViewMove(moveConfig);

						var foldersConfig = config.folders;

						foldersConfig.displayStyle = displayStyle;
						foldersConfig.displayStyleCSSClass = displayStyleCSSClass;
						foldersConfig.displayStyleToolbar = displayStyleToolbar;
						foldersConfig.namespace = namespace;
						foldersConfig.portletContainerId = portletContainerId;

						instance._appViewFolders = new Liferay.AppViewFolders(foldersConfig);

						instance._folderId = foldersConfig.defaultParentFolderId;

						var eventHandles = [
							Liferay.on(instance._eventOpenDocument, instance._openDocument, instance),
							History.after('stateChange', instance._afterStateChange, instance),
						];

						instance._config = config;

						instance._eventHandles = eventHandles;

						eventHandles.push(
							Liferay.on(
								config.portletId + ':portletRefreshed',
								A.bind('destructor', instance)
							)
						);

						instance._toggleTrashAction();

						var hasPermission = (themeDisplay.isSignedIn() && instance.one('#addButtonContainer'));

						if (HTML5_UPLOAD && hasPermission && instance._entriesContainer.inDoc()) {
							config.appViewEntryTemplates = instance.byId('appViewEntryTemplates');

							A.getDoc().once('dragenter', instance._plugUpload, instance, config);
						}
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						instance._appViewFolders.destroy();
						instance._appViewMove.destroy();
						instance._appViewSelect.destroy();

						instance._documentLibraryContainer.purge(true);
					},

					_afterStateChange: function(event) {
						var instance = this;

						var namespace = instance.NS;

						var requestParams = {};

						var state = History.get();

						AObject.each(
							state,
							function(item, index) {
								if (index.indexOf(namespace) === 0) {
									requestParams[index] = item;
								}
							}
						);

						instance._tuneStateChangeParams(requestParams);

						if (AObject.isEmpty(requestParams)) {
							requestParams = instance._getDefaultHistoryState();
						}

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams,
								src: SRC_HISTORY
							}
						);
					},

					_getDefaultHistoryState: function() {
						var instance = this;

						var initialState = History.get();

						return initialState;
					},

					_openDocument: function(event) {
						var instance = this;

						Liferay.Util.openDocument(
							event.webDavUrl,
							null,
							function(exception) {
								var errorMessage = Lang.sub(
									Liferay.Language.get('cannot-open-the-requested-document-due-to-the-following-reason'),
									[exception.message]
								);

								instance._appViewFolders.displayMessage(MESSAGE_TYPE_ERROR, errorMessage);
							}
						);
					},

					_plugUpload: function(event, config) {
						var instance = this;

						instance.plug(
							Liferay.DocumentLibraryUpload,
							{
								appViewEntryTemplates: config.appViewEntryTemplates,
								appViewMove: instance._appViewMove,
								columnNames: config.columnNames,
								dimensions: config.folders.dimensions,
								displayStyle: config.displayStyle,
								entriesContainer: instance._entriesContainer,
								folderId: instance._folderId,
								listViewContainer: instance.byId('listViewContainer'),
								maxFileSize: config.maxFileSize,
								redirect: config.redirect,
								uploadURL: config.uploadURL,
								viewFileEntryURL: config.viewFileEntryURL
							}
						);
					},

					_toggleTrashAction: function() {
						var instance = this;

						var trashEnabled = instance._config.trashEnabled;

						if (trashEnabled) {
							var repositoryId = instance._appViewSelect.get(STR_SELECTED_FOLDER).repositoryId;

							var scopeGroupId = instance._config.scopeGroupId;

							trashEnabled = (scopeGroupId === repositoryId);
						}

						instance.one('#deleteAction').toggle(!trashEnabled);

						instance.one('#moveToTrashAction').toggle(trashEnabled);
					},

					_tuneStateChangeParams: function(requestParams) {
						var instance = this;

						var entriesContainer = instance._entriesContainer;

						var namespacedShowRepositoryTabs = instance.ns(STR_SHOW_REPOSITORY_TABS);

						if (AObject.owns(requestParams, namespacedShowRepositoryTabs) &&
							!requestParams[namespacedShowRepositoryTabs] &&
							!entriesContainer.one('ul.nav-tabs')) {

							requestParams[namespacedShowRepositoryTabs] = true;

							requestParams[instance.ns(SEARCH_TYPE)] = SEARCH_TYPE_SINGLE;
						}

						var namespacedShowSearchInfo = instance.ns(STR_SHOW_SEARCH_INFO);

						if (AObject.owns(requestParams, namespacedShowSearchInfo) &&
							!requestParams[namespacedShowSearchInfo] &&
							!entriesContainer.one('.search-info')) {

							requestParams[namespacedShowSearchInfo] = true;

							requestParams[instance.ns(SEARCH_TYPE)] = SEARCH_TYPE_SINGLE;
						}
					}
				}
			}
		);

		Liferay.DL_SEARCH = SRC_SEARCH;

		Liferay.Portlet.DocumentLibrary = DocumentLibrary;
	},
	'',
	{
		requires: ['aui-loading-mask-deprecated', 'document-library-upload', 'event-simulate', 'liferay-app-view-folders', 'liferay-app-view-move', 'liferay-app-view-select', 'liferay-history-manager', 'liferay-message', 'liferay-portlet-base']
	}
);