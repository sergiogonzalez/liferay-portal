def uninstallApp(appName):
	app = ''
	appList = AdminApp.list()

	if len(appList) > 0:
		for item in appList.split('\n'):
			item = item.rstrip()

			if item.find(appName) >= 0:
				app = item

				break

	if app != '':
		print AdminApp.uninstall(appName)

		print AdminConfig.save()

uninstallApp('${plugin.servlet.context.name}')

print AdminApp.install('${auto.deploy.dest.dir}/${plugin.servlet.context.name}.war', '[-appname ${plugin.servlet.context.name} -contextroot /${plugin.servlet.context.name} -usedefaultbindings]')

print AdminConfig.save()

appManager = AdminControl.queryNames('${auto.deploy.websphere.wsadmin.app.manager.query}')

print AdminControl.invoke(appManager, 'startApplication', '${plugin.servlet.context.name}')