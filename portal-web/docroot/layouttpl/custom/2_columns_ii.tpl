<div class="columns-2" id="main-content" role="main">
	#if ($browserSniffer.isIe($request) && $browserSniffer.getMajorVersion($request) < 8)
		<table class="portlet-layout">
		<tr>
			<td class="aui-span4 portlet-column portlet-column-first" id="column-1">
				$processor.processColumn("column-1", "portlet-column-content portlet-column-content-first")
			</td>
			<td class="aui-span8 portlet-column portlet-column-last" id="column-2">
				$processor.processColumn("column-2", "portlet-column-content portlet-column-content-last")
			</td>
		</tr>
		</table>
	#else
		<div class="portlet-layout aui-row-fluid">
			<div class="aui-span4 portlet-column portlet-column-first" id="column-1">
				$processor.processColumn("column-1", "portlet-column-content portlet-column-content-first")
			</div>

			<div class="aui-span8 portlet-column portlet-column-last" id="column-2">
				$processor.processColumn("column-2", "portlet-column-content portlet-column-content-last")
			</div>
		</div>
	#end
</div>