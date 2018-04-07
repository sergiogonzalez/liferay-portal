if (!CKEDITOR.plugins.get('videoembed')) {
	var REGEX_HTTP = /^https?/;

	CKEDITOR.DEFAULT_LFR_VIDEO_EMBED_WIDGET_TPL = '<div data-embed-video-url="{url}">{videoContent}<div class="embed-video-help-message">{helpMessageIcon}<span> {helpMessage}</span></div></div><br>';

	/**
	 * CKEditor plugin which adds the infrastructure to embed video urls as media objects
	 *
	 * This plugin adds an `embedVideoUrl` command that can be used to easily embed a URL and transform it
	 * to an embedded content.
	 *
	 * @class CKEDITOR.plugins.videoembed
	 */

	CKEDITOR.plugins.add(
		'videoembed',
		{
			requires: 'widget',
			init: function(editor) {
				var LFR_VIDEO_EMBED_WIDGET_TPL = new CKEDITOR.template(editor.config.embedWidgetTpl || CKEDITOR.DEFAULT_LFR_VIDEO_EMBED_WIDGET_TPL);

				var providers = editor.config.embedVideoProviders || [];

                providers = providers.map(
                    provider => {
                        return {
                            id: provider.id,
                            schemas: provider.schemas.map(schema => new RegExp(schema)),
                            tpl: new CKEDITOR.template(provider.tpl)
                        }
                    }
				);

				var generateEmbedContent = function(url, videoContent) {
					return LFR_VIDEO_EMBED_WIDGET_TPL.output(
						{
							helpMessage: Liferay.Language.get('video-playback-is-disabled-during-edition-mode'),
							helpMessageIcon: Liferay.Util.getLexiconIconTpl('info-circle'),
							url: url,
							videoContent: videoContent
						}
					);
				};

				var defaultEmbedWidgetUpcastFn = function(element, data) {
					var upcastWidget = false;

					if (element.name === 'div' && element.attributes['data-embed-video-url']) {
						data.url = element.attributes['data-embed-video-url'];

						upcastWidget = true;
					}
					else if (element.name === 'div' && element.attributes['data-embed-id']) {
						var iframe = element.children[0];

						data.url = iframe.attributes.src;

						var embedContent = generateEmbedContent(data.url, element.getOuterHtml());

						var widgetFragment = new CKEDITOR.htmlParser.fragment.fromHtml(embedContent);

						upcastWidget = widgetFragment.children[0];

						element.replaceWith(upcastWidget);
					}

					return upcastWidget;
				};

				var showError = function(errorMsg) {
					editor.fire('error', errorMsg);

					setTimeout(
						function() {
							editor.getSelection().removeAllRanges();
							editor.focus();
						},
						0
					);
				};

				editor.addCommand(
					'embedVideoUrl',
					{
						exec: function(editor, data) {
							var url = data.url;
							var videoContent;

							if (REGEX_HTTP.test(url)) {
								var validProvider = providers.some(
									function(provider) {
										var schema = provider.schemas.find(
											function(schema) {
												return schema.test(url);
											}
										);

										if (schema) {
											var embedId = schema.exec(url)[1];

											videoContent = provider.tpl.output(
												{
													embedId: embedId
												}
											);
										}

										return schema;
									}
								);

								if (validProvider) {
									editor._selectEmbedVideoWidget = url;

									var embedContent = generateEmbedContent(url, videoContent);

									editor.insertHtml(embedContent);
								}
								else {
									showError(Liferay.Language.get('sorry,-this-platform-is-not-supported'));
								}
							}
							else {
								showError(Liferay.Language.get('please-enter-a-valid-url'));
							}
						}
					}
				);

				editor.widgets.add(
					'videoembed',
					{
						draggable: false,
						mask: true,
						requiredContent: 'div[data-embed-video-url]',

						data: function(event) {
							var instance = this;

							if (editor._selectEmbedVideoWidget === event.data.url) {
								setTimeout(
									function() {
										editor.getSelection().selectElement(instance.wrapper);

										editor._selectEmbedVideoWidget = null;
									},
									0
								);
							}
						},

						downcast: function(widget) {
							return widget.children[0];
						},

						upcast: function(element, data) {
							var embedWidgetUpcastFn = editor.config.embedWidgetUpcastFn || defaultEmbedWidgetUpcastFn;

							return embedWidgetUpcastFn(element, data);
						}
					}
				);

				editor.on(
					'selectionChange',
					function(event) {
						var selection = editor.getSelection();

						if (selection) {
							var element = selection.getSelectedElement();

							if (element) {
								var widgetElement = element.findOne('[data-widget="videoembed"]');

								if (widgetElement) {
									var scrollPosition = new CKEDITOR.dom.window(window).getScrollPosition();

									var region = element.getClientRect();

									region.direction = CKEDITOR.SELECTION_BOTTOM_TO_TOP;
									region.left -= scrollPosition.x;
									region.top += scrollPosition.y;

									editor.fire(
										'editorInteraction',
										{
											nativeEvent: {},
											selectionData: {
												element: widgetElement,
												region: region
											}
										}
									);
								}
							}
						}
					}
				);

				editor.filter.addElementCallback(
					function(element) {
						if ('data-embed-video-url' in element.attributes) {
							return CKEDITOR.FILTER_SKIP_TREE;
						}
					}
				);
			}
		}
	);
}

export default CKEDITOR.plugins.get('videoembed');