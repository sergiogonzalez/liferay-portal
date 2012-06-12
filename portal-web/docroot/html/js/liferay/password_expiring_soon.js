/*
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-password-expiring-soon',
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