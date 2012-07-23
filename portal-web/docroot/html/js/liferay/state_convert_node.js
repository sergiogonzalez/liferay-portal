AUI.add(
	'liferay-state-convert-node',
	function(A) {
		var Lang = A.Lang,
			AObject = A.Object;

		var EVENT_RESOURCE_REQUEST = 'resourceRequest';

		var PREFIX_JAVASCRIPT = 'javascript:';

		var STR_HOST = 'host';

		var StateConvertNode = A.Component.create(
			{
				AUGMENTS: [Liferay.StateConvertIOBase],

				ATTRS: {
					nodeSelector: {
						validator: Lang.isObject,
						value: {
							link: {
								attribute: 'href',
								event: 'click',
								filters: {
									prefixJS: function(value) {
										return (value && value.indexOf(PREFIX_JAVASCRIPT) !== 0);
									}
								},
								selector: 'a[href*="p_p_state="]'
							},
							form: {
								attribute: 'action',
								event: 'submit',
								selector: 'form[action*="p_p_state="]'
							}
						}
					}
				},

				EXTENDS: A.Plugin.Base,

				NAME: 'StateConvertNode',

				NS: 'stateconvert',

				prototype: {
					initializer: function() {
						var instance = this;

						StateConvertNode.superclass.initializer.apply(this, arguments);

						instance._eventHandles = [];

						instance.publish(
							EVENT_RESOURCE_REQUEST,
							{
								defaultFn: instance._defaultResourceRequest
							}
						);

						instance._bindListeners();

						instance._bindSelectors();
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');
					},

					_afterDataRetrieveFailure: function(event) {
						var instance = this;

						instance.get(STR_HOST).setContent(Liferay.Language.get('your-request-failed-to-complete'));
					},
					
					_afterDataRetrieveSuccess: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var responseData = event.responseData;

						Liferay.Portlet.loadPortletFiles(responseData, A.bind(host.setContent, host));
					},

					_bindListeners: function() {
						var instance = this;

						Liferay.on(
							'menu:click',
							function(event) {
								return !instance._handleEvent(event.item, instance.get('nodeSelector.link'));
							},
							instance
						);

						Liferay.on(
							'submitForm',
							function(event) {
								var form = event.form;

								var host = instance.get('host');

								if (host.contains(form)) {
									event.preventDefault();
								}
							},
							instance
						);

						instance.after('dataRetrieveSuccess', instance._afterDataRetrieveSuccess, instance);
						instance.after('dataRetrieveFailure', instance._afterDataRetrieveFailure, instance);
					},

					_bindSelectors: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						var nodeSelector = instance.get('nodeSelector');

						AObject.each(
							nodeSelector,
							function(item, index, collection) {
								var event = item.event;
									
								var selector = item.selector;

								instance._eventHandles.push(
									host.delegate(event, instance._onEvent, selector, instance, item)
								);
							}
						);
					},

					_defaultResourceRequest: function(event) {
						var instance = this;

						var node = event.node;
						
						var attributeValue = event.attribute;

						instance.requestResource(attributeValue, node);
					},

					_handleEvent: function(node, params) {
						var instance = this;

						var attributeValue = node.attr(params.attribute);

						if (attributeValue && instance._filterValue(node, attributeValue, params)) {
							return instance.fire(
								EVENT_RESOURCE_REQUEST,
								{
									node: node,
									attribute: attributeValue
								}
							);
						}

						return false;
					},

					_filterValue: function(node, value, params) {
						var instance = this;

						var result = true;

						var filters = params.filters;

						if (filters) {
							result = AObject.some(
								filters,
								function(item, index, collection) {
									if (Lang.isFunction(item)){
										return item(value);
									}
								}
							);
						}

						return result;
					},

					_onEvent: function(event, params) {
						var instance = this;

						event.preventDefault();

						var node = event.currentTarget;

						instance._handleEvent(node, params);
					}
				}
			}
		);

		Liferay.namespace('Plugin').StateConvertNode = StateConvertNode;
	},
	'',
	{
		requires: ['liferay-state-convert-io-base', 'aui-node', 'plugin']
	}
);