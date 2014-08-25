AUI.add(
	'liferay-blogs-cover-uploader',
	function(A) {
		var Lang = A.Lang;

		var STR_EMPTY = '';

		var STR_FILE_ENTRY = 'fileEntry';

		var STR_CLICK = 'click';

		var STR_MOUSEOUT = 'mouseout';

		var STR_MOUSEOVER = 'mouseover';

		var CoverUploader = A.Component.create(
			{
				ATTRS: {
					fileEntry: {
						validator: Lang.isObject,
						value: {
							id: 0,
							url: STR_EMPTY
						}
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'blogscoveruploader',

				NS: 'blogscoveruploader',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._originalFileEntryId = instance.get(STR_FILE_ENTRY).id;
						instance._originalFileEntryUrl = instance.get(STR_FILE_ENTRY).url;

						instance._cancelImageButton = instance.one('#cancelImage');
						instance._coverImageInput = instance.one('#coverImageFile');
						instance._coverImageInputWrapper = instance.one('.cover-image-input-wrapper');
						instance._coverImagePreview = instance.one('#coverImagePreview');
						instance._coverImagePreviewWrapper = instance.one('.cover-image-preview-wrapper');
						instance._coverImageUploadWrapper = instance.one('#imageUploadWrapper');
						instance._deleteImageButton = instance.one('#deleteImage');

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						var eventHandles = [
							instance._coverImageInput.on([STR_MOUSEOUT, STR_MOUSEOVER], instance._onInputMouseInteraction, instance),
							instance._coverImageInput.on('change', instance._uploadCoverImage, instance),
							instance._coverImagePreview.on('load', instance._notifyImageUpdated, instance),
							instance._cancelImageButton.on(STR_CLICK, instance._cancelImage, instance),
							instance._deleteImageButton.on(STR_CLICK, instance._deleteImage, instance),
							instance.on('fileEntryChange', instance._onFileEntryChange, instance)
						];
					},

					_cancelImage: function(event) {
						var instance = this;

						instance._updateImage(instance._originalFileEntryId, instance._originalFileEntryUrl);
					},

					_deleteImage: function(event) {
						var instance = this;

						instance._originalFileEntryId = 0;
						instance._originalFileEntryUrl = STR_EMPTY;

						instance._updateImage(0, STR_EMPTY);
					},

					_getCropRegion: function() {
						var instance = this;

						var imagePreview = instance._coverImagePreview;

						var imagePreviewWrapper = instance._coverImagePreviewWrapper;

						var naturalSize = instance._getImgNaturalSize(imagePreview);

						var scaleY = naturalSize.height / imagePreview.height();

						var cropRegion = {
							height: Math.ceil(imagePreviewWrapper.height() * scaleY),
							width: naturalSize.width,
							x: 0,
							y: Math.ceil((imagePreviewWrapper.getY() - imagePreview.getY()) * scaleY)
						};

						return cropRegion;
					},

					_getImgNaturalSize: function(img) {
						var imageHeight = img.get('naturalHeight');
						var imageWidth = img.get('naturalWidth');

						if (Lang.isUndefined(imageHeight) || Lang.isUndefined(imageWidth)) {
							var tmp = new Image();

							tmp.src = img.attr('src');

							imageHeight = tmp.height;
							imageWidth = tmp.width;
						}

						return {
							height: imageHeight,
							width: imageWidth
						};
					},

					_initDD: function() {
						var instance = this;

						var dd = instance._dd;

						if (!dd) {
							var previewWrapper = instance._coverImagePreviewWrapper;

							dd = new A.DD.Drag(
								{
									node: instance._coverImagePreview,
									on: {
										'drag:drag': function(event) {
											var pageY = event.pageY;

											var previewWrapper = instance._coverImagePreviewWrapper;

											var top = previewWrapper.getY();

											var bottom = top + previewWrapper.height() - instance._coverImagePreview.height();

											if (pageY >= top || pageY <= bottom) {
												event.preventDefault();
											}
										},
										'drag:end': A.bind(instance._notifyImageUpdated, instance)
									}
								}
							).plug(
								A.Plugin.DDConstrained,
								{
									constrain: {
										left: previewWrapper.getX(),
										right: previewWrapper.getX() + previewWrapper.width()
									}
								}
							);

							instance._dd = dd;
						}
					},

					_notifyImageUpdated: function(event) {
						var instance = this;

						var cropRegion = {};

						var fileEntry = event.newVal || instance.get(STR_FILE_ENTRY);

						var fileEntryId = fileEntry.id;

						if (fileEntryId && fileEntryId !== instance._originalFileEntryId) {
							cropRegion = instance._getCropRegion();
						}

						Liferay.fire(
							'coverImageUpdated',
							{
								cropRegion: cropRegion,
								fileEntryId: fileEntryId,
								fileEntryUrl: fileEntry.url
							}
						);
					},

					_onFileEntryChange: function(event) {
						var instance = this;

						instance.one('#fileEntryId').val(event.newVal.id);

						var newUrl = event.newVal.url;

						var showUploadWrapper = !newUrl;

						instance._coverImagePreview.attr('src', newUrl);
						instance._coverImagePreview.setStyle('left', 0);
						instance._coverImagePreview.setStyle('top', 0);

						instance._coverImagePreviewWrapper.toggle(!showUploadWrapper);
						instance._coverImageUploadWrapper.toggle(showUploadWrapper);

						var coverImageFileWrapper = showUploadWrapper ? instance.one('.cover-image-upload-wrapper') : instance.one('.cover-image-change-wrapper');

						coverImageFileWrapper.append(instance._coverImageInputWrapper);

						if (!showUploadWrapper) {
							instance._initDD();

							var showOriginal = newUrl === instance._originalFileEntryUrl;

							instance._cancelImageButton.toggle(!showOriginal);
							instance._deleteImageButton.toggle(showOriginal);
							instance.one('.cover-image-preview-mask').toggle(showOriginal);
						}

						instance._notifyImageUpdated(event);
					},

					_onInputMouseInteraction: function(event) {
						var instance = this;

						instance.all('.btn-primary').toggleClass('active', event.type === STR_MOUSEOVER);
					},

					_updateImage: function(fileEntryId, fileEntryUrl, fileEntryCropRegion) {
						var instance = this;

						instance.set(
							STR_FILE_ENTRY,
							{
								id: fileEntryId,
								url: fileEntryUrl
							}
						);
					},

					_uploadCoverImage: function() {
						var instance = this;

						var form = instance.one('#coverImageSelectorForm');

						A.io.request(
							form.get('action'),
							{
								dataType: 'JSON',
								form: {
									id: form,
									upload: true
								},
								on: {
									complete: function(event, id, obj) {
										var responseData = A.JSON.parse(obj.responseText);

										if (responseData.success) {
											instance._updateImage(responseData.fileEntryId, responseData.fileEntryURL);
										}
									}
								}
							}
						);
					}
				}
			}
		);

		Liferay.BlogsCoverUploader = CoverUploader;
	},
	'',
	{
		requires: ['aui-io-request', 'dd-drag', 'liferay-portlet-base']
	}
);