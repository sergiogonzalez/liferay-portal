AUI.add(
	'liferay-image-selector',
	function(A) {
		var Lang = A.Lang;

		var ImageSelector = A.Component.create(
			{
				ATTRS: {
					documentSelectorURL: {
						validator: Lang.isString
					},

					paramName: {
						validator: Lang.isString
					},

					uploadURL: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'imageselector',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._updateFileEntryDataFn = A.bind(instance._updateFileEntryData, instance);

						instance.rootNode = instance.one('#taglibImageSelector');

						instance._renderUploader();
						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance.rootNode.delegate('click', instance._onBrowseClick, '.browse-image', instance),
							instance.one('#removeImage').on('click', instance._updateFileEntryData, instance)
						];
					},

					_onBrowseClick: function(event) {
						var instance = this;

						Liferay.Util.selectEntity(
							{
								dialog: {
									constrain: true,
									destroyOnHide: true,
									modal: true
								},
								eventName: instance.ns('selectImage'),
								id: instance.ns('selectImage'),
								title: Liferay.Language.get('select-image'),
								uri: instance.get('documentSelectorURL')
							},
							instance._updateFileEntryDataFn
						);
					},

					_renderUploader: function() {
						var instance = this;

						var uploader = new A.Uploader(
							{
								boundingBox: instance.rootNode,
								dragAndDropArea: instance.rootNode,
								fileFieldName: 'file',
								on: {
									render: function(event) {
										//debugger;
									}
								},
								simLimit: 2,
								uploadURL: instance.get('uploadURL')
							}
						).render();

						var getLogFn = function(logMsg) {
							var logFn = function(event) {
								console.log(logMsg);
								console.log(event);

								if (event.type === 'uploader:fileselect') {
									uploader.uploadAll();
								}
								else if (event.type === 'file:uploadcomplete') {
									debugger;
								}
							};

							return logFn;
						}

						uploader.after('fileselect', getLogFn('fileselect'), instance);

						uploader.on('alluploadscomplete', getLogFn('alluploadscomplete'), instance);
						uploader.on('fileuploadstart', getLogFn('fileuploadstart'), instance);
						uploader.on('uploadcomplete', getLogFn('uploadcomplete'), instance);
						uploader.on('uploadprogress', getLogFn('uploadprogress'), instance);

					},

					_updateFileEntryData: function(event) {
						var instance = this;

						var fileEntryIdNode = instance.rootNode.one('#' + instance.get('paramName'));

						var fileEntryImage = instance.one('#image');

						var browseImageControls = instance.one('.browse-image-controls');

						var changeImageControls = instance.one('.change-image-controls');

						var fileEntryId = event.fileentryid || 0;

						var fileEntryUrl = event.url || '';

						fileEntryIdNode.val(fileEntryId);

						fileEntryImage.setAttribute('src', fileEntryUrl);

						var showImageControls = (fileEntryId !== 0 && fileEntryUrl !== '');

						fileEntryImage.toggle(showImageControls);

						changeImageControls.toggle(showImageControls);

						browseImageControls.toggle(!showImageControls);

						instance.rootNode.toggleClass('drop-enabled', !showImageControls);
					}
				}
			}
		);

		Liferay.ImageSelector = ImageSelector;
	},
	'',
	{
		requires: ['aui-base', 'liferay-portlet-base', 'uploader']
	}
);