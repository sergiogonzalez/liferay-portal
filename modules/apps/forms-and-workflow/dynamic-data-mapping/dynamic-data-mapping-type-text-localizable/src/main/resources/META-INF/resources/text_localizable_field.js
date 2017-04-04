AUI.add(
	'liferay-ddm-form-field-text-localizable',
	function(A) {
		var Renderer = Liferay.DDM.Renderer;

		new A.TooltipDelegate(
			{
				position: 'left',
				trigger: '.liferay-ddm-form-field-text-localizable .help-icon',
				triggerHideEvent: ['blur', 'mouseleave'],
				triggerShowEvent: ['focus', 'mouseover'],
				visible: false
			}
		);

		var TextLocalizableField = A.Component.create(
			{
				ATTRS: {
					displayStyle: {
						state: true,
						value: 'singleline'
					},

					placeholder: {
						state: true,
						value: ''
					},

					type: {
						value: 'text_localizable'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-text-localizable',

				prototype: {
					initializer: function() {
						var instance = this;

						Liferay.InputLocalized.unregister(instance.getQualifiedName());

						instance._eventHandlers.push(
							instance.after('valueChange', instance._onTextFieldValueChange),
							instance.bindContainerEvent('blur', instance._hideLocalizedPanel, '.liferay-ddm-form-field-text-localizable'),
							instance.bindContainerEvent('focus', instance._showLocalizedPanel, '.liferay-ddm-form-field-text-localizable'),
							instance.bindContainerEvent('mouseenter', instance._showLocalizedPanel, '.liferay-ddm-form-field-text-localizable'),
							instance.bindContainerEvent('mouseleave', instance._hideLocalizedPanel, '.liferay-ddm-form-field-text-localizable')
						);
					},

					getChangeEventName: function() {
						return 'input';
					},

					getTextHeight: function() {
						var instance = this;

						var text = instance.getValue();

						return text.split('\n').length;
					},

					render: function() {
						var instance = this;

						TextLocalizableField.superclass.render.apply(instance, arguments);

						if (instance.get('displayStyle') === 'multiline') {
							instance.syncInputHeight();
						}

						return instance;
					},

					showErrorMessage: function() {
						var instance = this;

						TextLocalizableField.superclass.showErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var inputGroup = container.one('.input-group-container');

						inputGroup.insert(container.one('.help-block'), 'after');
					},

					syncInputHeight: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						var height = instance.getTextHeight();

						if (height < 2) {
							inputNode.set('rows', 2);
						}
						else {
							inputNode.set('rows', height);
						}
					},

					_createInputLocalized: function() {
						var instance = this;

						var inputParentNode = instance.getInputNode().get('parentNode');

						Liferay.InputLocalized.register(
							instance.getQualifiedName(),
							{
								boundingBox: inputParentNode,
								columns: 20,
								contentBox: inputParentNode.one('.input-localized-content'),
								defaultLanguageId: instance.get('locale'),
								fieldPrefix: "",
								fieldPrefixSeparator: "",
								id: instance.get('fieldName'),
								inputPlaceholder: instance.getInputSelector(),
								instanceId: instance.getQualifiedName(),
								items: ["en_US", "zh_CN", "es_ES", "ja_JP", "nl_NL", "hu_HU", "pt_BR", "de_DE", "iw_IL", "fi_FI", "ca_ES", "fr_FR"],
								itemsError: [],
								lazy: true,
								name: instance.get('fieldName'),
								namespace: instance.get('portletNamespace'),
								toggleSelection: false,
								translatedLanguages: instance.get('locale')
							}
						);
					},

					_getInputLocalizedInstance: function() {
						var instance = this;

						return Liferay.InputLocalized._instances[instance.getQualifiedName()];
					},

					_hideLocalizedPanel: function() {
						var instance = this;

						if (!instance.get('container').one('.input-localized').hasClass('input-localized-focused')) {
							instance.get('container').one('.input-localized-content').addClass('hidden');
						}
					},

					_onTextFieldValueChange: function() {
						var instance = this;

						if (instance.get('displayStyle') === 'multiline') {
							instance.syncInputHeight();
						}
					},

					_showLocalizedPanel: function() {
						var instance = this;

						if (!instance._getInputLocalizedInstance()) {
							instance._createInputLocalized();
						}

						instance.get('container').one('.input-localized-content').removeClass('hidden');
					}

				}
			}
		);

		Liferay.namespace('DDM.Field').TextLocalizable = TextLocalizableField;
	},
	'',
	{
		requires: ['aui-autosize-deprecated', 'aui-tooltip', 'liferay-ddm-form-renderer-field', 'liferay-input-localized']
	}
);