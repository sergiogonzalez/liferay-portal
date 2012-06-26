AUI.add(
	'liferay-passwordexpiringsoon',
	function(A) {
		var PasswordExpiringSoon = A.Component.create(
			{
				EXTENDS: A.Base,
				NAME: 'passwordexpiringsoon',
				prototype: {
					initializer: function(config) {
						var instance = this;

						var banner = instance._banner;

						instance._expiringSoonText = Liferay.Language.get('warning-your-password-will-expire-soon');
						instance._closeText = Liferay.Language.get('close');
						banner = new Liferay.Notice(
							{
								closeText: instance._closeText,
								content: instance._expiringSoonText,
								noticeClass: 'aui-helper-hidden',
								toggleText: false
							}
						);

						instance._banner = banner;

						banner.show();
					}
				}
			}
		);
		Liferay.PasswordExpiringSoon = PasswordExpiringSoon;
	},
	'',
	{
		requires: ['liferay-notice']
	}
);