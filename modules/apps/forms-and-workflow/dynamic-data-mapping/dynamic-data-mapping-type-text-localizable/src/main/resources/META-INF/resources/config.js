;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-text-localizable': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-text-localizable': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'text_localizable_field.js',
							requires: [
								'aui-autosize-deprecated',
								'aui-tooltip',
								'liferay-ddm-form-renderer-field',
								'liferay-input-localized'
							]
						},
						'liferay-ddm-form-field-text-localizable-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'text_localizable.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();