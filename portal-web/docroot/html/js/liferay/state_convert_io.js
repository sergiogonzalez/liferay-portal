AUI.add(
	'liferay-state-convert-io',
	function(A) {
		var Lang = A.Lang;

		var StateConvertIO = A.Component.create(
			{
				AUGMENTS: [Liferay.StateConvertIOBase],

				EXTENDS: A.Base,

				NAME: 'StateConvertIO'
			}
		);

		Liferay.StateConvertIO = StateConvertIO;
	},
	'',
	{
		requires: ['liferay-state-convert-io-base']
	}
);