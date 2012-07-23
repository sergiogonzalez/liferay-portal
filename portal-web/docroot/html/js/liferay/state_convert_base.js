AUI.add(
	'liferay-state-convert-base',
	function(A) {
		var Lang = A.Lang;

		var StateConvertBase = function() {};

		StateConvertBase.ATTRS = {
			stateConverter: {
				validator: '_validateStateConverter',
				value: '$1exclusive_resourceful$2'
			},

			stateSelector: {
				setter: '_setStateSelector',
				validator: '_validateStateSelector',
				value: /(p_p_state=)[^&]+(&?)/
			}
		};

		StateConvertBase.prototype = {
			convertState: function(resourceState) {
				var instance = this;

				var stateConverter = instance.get('stateConverter');
				var stateSelector = instance.get('stateSelector');

				var result = resourceState.replace(stateSelector, stateConverter);

				return result;
			},

			_setStateSelector: function(value) {
				if (Lang.isString(value)) {
					value = new RegExp(value);
				}

				return value;
			},

			_validateStateConverter: function(value) {
				return Lang.isString(value) || Lang.isFunction(value);
			},

			_validateStateSelector: function(value) {
				return Lang.isString(value) || A.instanceOf(value, RegExp);
			}
		};

		Liferay.StateConvertBase = StateConvertBase;
	},
	'',
	{
		requires: ['aui-base']
	}
);