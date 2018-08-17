import Soy from 'metal-soy';
import { Config } from 'metal-state';

import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

import templates from './Sharing.soy';

class Sharing extends PortletBase {
	constructor(config, ...args) {
		super(config, ...args);

		this.classNameId = config.classNameId;
		this.classPK = config.classPK;
	}

	_getEmailAdress(userEmailAddress = '') {
		return userEmailAddress.split(/,\s*/);
	}

	/**
	 * Save the share permisions
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleSubmit(event) {
		event.preventDefault();

		this.fetch(
			this.shareActionURL,
			{
				classNameId: this.classNameId,
				classPK: this.classPK,
				shareEnabled: this.shareEnabled,
				sharePermissionKey: this.sharePermissionKey,
				userEmailAddress: this.userEmailAddress
			}
		);
	}

	_handleInputChange(event) {
		const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this[name] = value;
  }
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
Sharing.STATE = {
	shareActionURL: Config.string().required(),
	refererPortletName: Config.string().required(),
	shareEnabled: Config.bool().required(),
	sharePermissionKey: Config.string().required()
};

Soy.register(Sharing, templates);

export default Sharing;