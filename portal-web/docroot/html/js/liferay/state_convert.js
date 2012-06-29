AUI.add(
	'liferay-state-convert',
	function(A) {
		var Lang = A.Lang;

		var StateConvert = A.Component.create(
			{
				AUGMENTS: [Liferay.StateConvertBase],

				EXTENDS: A.Base,

				NAME: 'StateConvert'
			}
		);

		Liferay.StateConvert = StateConvert;
	},
	'',
	{
		requires: ['liferay-state-convert-base']
	}
);