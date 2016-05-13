function ${namespace}editWithImageEditor(
	editItemURL, uploadItemURL, dialogTitle, fileEntrySrc) {

	Liferay.Util.editEntity(
		{
			dialog: {
				destroyOnHide: true,
				zIndex: 100000
			},
			id: 'dlImageEditor',
			stack: false,
			title: dialogTitle,
			uri: editItemURL,
			urlParams: {
				entityURL: fileEntrySrc,
				saveParamName: 'imageEditorFileName',
				saveURL: uploadItemURL
			}
		},
		AUI().bind('${namespace}_onSaveEditSuccess', this)
	);
}

function ${namespace}_onSaveEditSuccess() {
	Liferay.Portlet.refresh('#p_p_id${namespace}');
}