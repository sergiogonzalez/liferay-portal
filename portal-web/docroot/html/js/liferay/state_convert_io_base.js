AUI.add(
	'liferay-state-convert-io-base',
	function(A) {
		var Lang = A.Lang;

		var DEFAULT_IO_CONFIG = {
			autoLoad: false,
			dataType: 'json'
		};

		var STR_SUCCESS = 'success';

		var StateConvertIOBase = function(){};

		StateConvertIOBase.ATTRS = {
			ioConfig: {
				validator: Lang.isObject,
				value: {}
			}
		};

		StateConvertIOBase.prototype = {
			requestResource: function(uri, node) {
				var instance = this;

				uri = instance.convertState(uri);

				var formConfig = instance._getFormConfig(node);

				var ioConfig = A.merge(
					StateConvertIOBase.DEFAULT_IO_CONFIG,
					formConfig,
					instance.get('ioConfig')
				);

				if (!A.Object.isEmpty(formConfig)) {
					ioConfig['method'] = 'POST';
				}

				var ioRequest = A.io.request(uri, ioConfig);

				var sendIOResponse = A.bind(instance._sendIOResponse, instance, ioRequest);

				ioRequest.after(['failure', STR_SUCCESS], sendIOResponse);

				ioRequest.start();
			},

			_getFormConfig: function(node) {
				var instance = this;

				var config = {};

				if (node) {
					var tagName = node.attr('tagName');

					if (tagName && tagName.toLowerCase() === 'form') {
						var formConfig = {};

						config['form'] = formConfig;

						var formId = node.generateID();

						formConfig['id'] = formId;

						var fileNode = node.one('input[type="file"]');

						if (fileNode) {
							formConfig['upload'] = true;
						}
					}
				}

				return config;
			},

			_sendIOResponse: function(ioRequest, event) {
				var instance = this;

				var data = ioRequest.get('data');
				var reponseData = ioRequest.get('responseData');

				var eventType = 'dataRetrieveSuccess';

				if (event.type.indexOf(STR_SUCCESS) == -1) {
					eventType = 'dataRetrieveFailure';
				}

				instance.fire(
					eventType,
					{
						data: data,
						responseData: reponseData
					}
				);
			}
		};

		A.mix(StateConvertIOBase, Liferay.StateConvertBase, true, null, 2, true);

		StateConvertIOBase.DEFAULT_IO_CONFIG = DEFAULT_IO_CONFIG;

		Liferay.StateConvertIOBase = StateConvertIOBase;
	},
	'',
	{
		requires: ['io-form', 'io-upload-iframe', 'liferay-state-convert-base', 'aui-io-request']
	}
);