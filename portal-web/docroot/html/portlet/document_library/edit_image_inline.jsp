<%--
/**
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
--%>

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%@ page import="com.liferay.portlet.documentlibrary.util.ImgEditorUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.action.EditImageInlineAction.ImgFile" %>
<%@ page import="org.w3c.dom.Element" %>
<%@ page import="org.w3c.dom.NodeList" %>

<%
String strutsAction = ParamUtil.getString(request, "struts_action");

String cmd = ParamUtil.getString(request, Constants.CMD, Constants.EDIT);

String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

String referringPortletResourceRootPortletId = PortletConstants.getRootPortletId(referringPortletResource);

String uploadProgressId = "dlFileEntryUploadProgress";

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

long fileEntryId = BeanParamUtil.getLong(fileEntry, request, "fileEntryId");

long repositoryId = BeanParamUtil.getLong(fileEntry, request, "repositoryId");

if (repositoryId <= 0) {

	// add_asset.jspf only passes in groupId

	repositoryId = BeanParamUtil.getLong(fileEntry, request, "groupId");
}

long folderId = BeanParamUtil.getLong(fileEntry, request, "folderId");
String extension = BeanParamUtil.getString(fileEntry, request, "extension");

Folder folder = null;

if (fileEntry != null) {
	folder = fileEntry.getFolder();
}

if ((folder == null) && (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = DLAppServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

FileVersion fileVersion = null;

long fileVersionId = 0;

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

if (fileEntry != null) {
	fileVersion = fileEntry.getLatestFileVersion();

	fileVersionId = fileVersion.getFileVersionId();

	if ((fileEntryTypeId == -1) && (fileVersion.getModel() instanceof DLFileVersion)) {
		DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

		fileEntryTypeId = dlFileVersion.getFileEntryTypeId();
	}
}

DLFileEntryType dlFileEntryType = null;

if (fileEntryTypeId > 0) {
	dlFileEntryType = DLFileEntryTypeLocalServiceUtil.getFileEntryType(fileEntryTypeId);
}

long assetClassPK = 0;

if ((fileVersion != null) && !fileVersion.isApproved() && Validator.isNotNull(fileVersion.getVersion()) && !fileVersion.getVersion().equals(DLFileEntryConstants.VERSION_DEFAULT)) {
	assetClassPK = fileVersion.getFileVersionId();
}
else if (fileEntry != null) {
	assetClassPK = fileEntry.getFileEntryId();
}

boolean approved = false;
boolean checkedOut = false;
boolean draft = false;
boolean hasLock = false;
boolean pending = false;

Lock lock = null;

if (fileEntry != null) {
	approved = fileVersion.isApproved();
	checkedOut = fileEntry.isCheckedOut();
	draft = fileVersion.isDraft();
	hasLock = fileEntry.hasLock();
	lock = fileEntry.getLock();
	pending = fileVersion.isPending();
}

boolean saveAsDraft = false;

if ((checkedOut || pending) && !PropsValues.DL_FILE_ENTRY_DRAFTS_ENABLED) {
	saveAsDraft = true;
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", strutsAction);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("fileEntryId", String.valueOf(fileEntryId));

//----------------------------------------------------
ImgFile imgfile = (ImgFile) request.getAttribute("imgfile");

String imgWidth;
String imgHeight;

if (imgfile != null) {
	imgWidth = ParamUtil.getString(request, "img-width", imgfile.getWidth());
	imgHeight = ParamUtil.getString(request, "img-height", imgfile.getHeight());
} else {
	imgWidth = ParamUtil.getString(request, "img-width");
	imgHeight = ParamUtil.getString(request, "img-height");
}

String idmenu = ParamUtil.getString(request, "idmenu");
int tmbMaxWidth = GetterUtil.get(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH), 40);
int tmbMaxHeight = GetterUtil.get(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT), 40);
String mimetype = ImgEditorUtil.getFileMimeType(fileEntry, imgfile);
String blockList = "";
String fileDescr = fileEntry.getTitle() + ImgEditorUtil.getFileExtension(fileEntry, imgfile) + "  width=" + imgWidth + "  height=" + imgHeight;
String imgpath = themeDisplay.getPathThemeImages() + "/document_library/editor/";

%>

<style>
.dnav_clip_rect {
  position:relative;
  border:dashed black 1px;
  z-index:999;
  display:none;
}
.dnav_corner {
	position:relative;
	z-index:9999;
	background-color:black;
	display:none;
}
.dnav_corner:hover {
	background-color:gray;
}

.toggle_btn {
  height:20px;
  width:95%;
  background-color:lightgray;
  font-size:14px;
  border:solid gray 1px;
  padding-left:5px;
  margin-left:2px;
  cursor:pointer;
}
.toggle_btn:hover {
  font-weight:bold;
}	

.toggle_block {
  width:95%;
  display:none;
  margin-left:2px;
  max-height: 300px;
  overflow-y:auto;
  overflow-x:hidden;  
  border:solid lightgray 1px;
}
.toggle_block p {
	margin-top:5px;
	margin-bottom:2px;
	clear:both;
}	

.img_editor_cmd {
  width:100%;
  font-weight:bold;
  cursor:pointer;
}

.img_editor_cmd:hover {
  background-color:lightgray;
}

.img_cmd_icon {
	float:left;
	margin-left:5px;
	margin-right:5px;
	vertical-align:middle;
}

.edit_cmd_dlg {
  border:solid gray 1px;
  width: 100%;
  display: none;
  background-color: white;
}
.edit_dlg_cab {
  height:20px;
  width:100%;
  background-color:lightgray;
  font-size:14px;
}
.edit_dlg_cab span {
  font-weight:bold;
  margin-left:5px;
}
.tmb_img {
  margin: 5px;
}

.panel_sup {
  height:20px;
  background-color:lightgray;
  font-weight:bold;
  font-size:12px;
  float: left;
}
.panel_sup span {
  margin-left:5px;
  margin-top:2px;
}

.img_button_enabled {
	cursor:pointer;
	width:30px;
	height:30px;
    border:outset gray 3px;
    margin-top:5px;
}
.img_button_disabled {
	width:30px;
	height:30px;
    border:inset gray 3px;
    margin-top:5px;
}
</style>

<c:if test="<%= Validator.isNull(referringPortletResource) %>">
	<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />
</c:if>

<c:if test="<%= checkedOut %>">
	<c:choose>
		<c:when test="<%= hasLock %>">
			<div class="portlet-msg-success">
				<c:choose>
					<c:when test="<%= lock.isNeverExpires() %>">
						<liferay-ui:message key="you-now-have-an-indefinite-lock-on-this-document" />
					</c:when>
					<c:otherwise>

						<%
						String lockExpirationTime = LanguageUtil.getTimeDescription(pageContext, DLFileEntryConstants.LOCK_EXPIRATION_TIME).toLowerCase();
						%>

						<%= LanguageUtil.format(pageContext, "you-now-have-a-lock-on-this-document", lockExpirationTime, false) %>
					</c:otherwise>
				</c:choose>
			</div>
		</c:when>
		<c:otherwise>
			<div class="portlet-msg-error">
				<%= LanguageUtil.format(pageContext, "you-cannot-modify-this-document-because-it-was-checked-out-by-x-on-x", new Object[] {HtmlUtil.escape(PortalUtil.getUserName(lock.getUserId(), String.valueOf(lock.getUserId()))), dateFormatDateTime.format(lock.getCreateDate())}, false) %>
			</div>
		</c:otherwise>
	</c:choose>
</c:if>

<%
boolean localizeTitle = true;
String headerTitle = LanguageUtil.get(pageContext, "new-document");

if (fileVersion != null) {
	headerTitle = fileVersion.getTitle();
	localizeTitle= false;
}
else if (dlFileEntryType != null) {
	headerTitle = LanguageUtil.format(pageContext, "new-x", new Object[] {dlFileEntryType.getName()});
}
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= localizeTitle %>"
	title="<%= headerTitle %>"
/>

<portlet:actionURL var="editFileEntryURL">
	<portlet:param name="struts_action" value="/document_library/edit_image_inline" />
	<portlet:param name="uploader" value="classic" />
</portlet:actionURL>
<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="servResourseURL">
	<portlet:param name="struts_action" value="/document_library/edit_image_inline" />
</liferay-portlet:resourceURL>

<aui:form action="<%= editFileEntryURL %>" cssClass="lfr-dynamic-form" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFileEntry(" + saveAsDraft + ");" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="uploadProgressId" type="hidden" value="<%= uploadProgressId %>" />
	<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
	<aui:input name="fileEntryId" type="hidden" value="<%= fileEntryId %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

	<input name="idmenu" type="hidden"/>
	<input name="par1" type="hidden"/>
	<input name="par2" type="hidden"/>
	<input name="par3" type="hidden"/>
	<input name="par4" type="hidden"/>

	<liferay-ui:error exception="<%= DuplicateFileException.class %>" message="please-enter-a-unique-document-name" />

	<%
	long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

	if (fileMaxSize == 0) {
		fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
	}

	fileMaxSize /= 1024;
	%>

	<liferay-ui:error exception="<%= FileSizeException.class %>">
		<liferay-ui:message arguments="<%= fileMaxSize %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" />
	</liferay-ui:error>

	<liferay-ui:asset-categories-error />

	<liferay-ui:asset-tags-error />

	<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />

	<c:if test="<%= fileVersion != null %>">
		<aui:workflow-status model="<%= DLFileEntry.class %>" status="<%= fileVersion.getStatus() %>" version="<%= fileVersion.getVersion() %>" />
	</c:if>

		<c:if test="<%= approved %>">
			<div class="portlet-msg-info">
				<liferay-ui:message key="a-new-version-will-be-created-automatically-if-this-content-is-modified" />
			</div>
		</c:if>

		<c:if test="<%= pending %>">
			<div class="portlet-msg-info">
				<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
			</div>
		</c:if>
		
<%-- Start editor window --%>
<div class="ventana_marco" style="width:100%;">

<div class="panel_sup" style="width:70%">
<span><%=fileDescr %></span><br/>
</div>
<div class="panel_sup" style="width:29%">
<span id="<portlet:namespace/>stat1"></span><br/>
</div>

<br clear="all">
<br/>

<div style="width:70%; height:500px; float:left; overflow:auto; border:1px solid black;">

<div id="<portlet:namespace/>marco" style="width:<%=imgWidth%>px; height:<%=imgHeight%>px;">
<div id="corner0" class="dnav_corner"></div>
<div id="corner1" class="dnav_corner"></div>
<div id="corner2" class="dnav_corner"></div>
<div id="corner3" class="dnav_corner"></div>
<div id="clip-rect" class="dnav_clip_rect"></div>
</div>
</div>

<div style="width:28%; height:500px; float:left; margin-left:1%">

<div id="<portlet:namespace/>area" class="img_button_enabled" onclick="<portlet:namespace/>area();">
<img src="<%=imgpath%>area.gif" title='<liferay-ui:message key="area"/>'/>
</div>
<br/>

<div id="<portlet:namespace/>commands" style="width:100%;">
<%
NodeList blocks = ImgEditorUtil.getMenuBlocks();

for (int idxBlk = 0; idxBlk < blocks.getLength(); idxBlk++) {
	Element block = (Element) blocks.item(idxBlk);
	String blockName = block.getAttribute("name");
	if (blockList.length() > 0) blockList += ",";
	blockList += "'" + blockName + "'";
%>

<div id="<portlet:namespace/><%=blockName %>" class="toggle_btn" onclick="<portlet:namespace/>showToggle('<%= blockName%>')">
  <liferay-ui:message key="<%=blockName %>"/>
</div>

<div id="<portlet:namespace/>block_<%=blockName %>" class="toggle_block">

<%
	NodeList commands = ImgEditorUtil.getBlockCommands(blockName);
	for (int cmdId = 0; cmdId < commands.getLength(); cmdId++) {
		Element cmdElm = (Element) commands.item(cmdId);
		String cmdName = cmdElm.getAttribute("name"); 
		int numArgs = GetterUtil.get(cmdElm.getAttribute("nargs"), 0);
%>
<c:choose>
	<c:when test="<%= ImgEditorUtil.isCommandImlemented(cmdElm) %>">
		<c:choose>
			<c:when test="<%= ImgEditorUtil.hasStandardProcess(cmdElm) %>">
				<c:choose>
					<c:when test="<%= numArgs == 0 %>">
  <p class="img_editor_cmd" onclick="<portlet:namespace/>accept('<%=cmdName%>');">
					</c:when>
					<c:otherwise>
  <p class="img_editor_cmd" onclick="<portlet:namespace/>cmdStd(<%=ImgEditorUtil.getJSArguments(cmdElm, locale)%>);">
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
  <p class="img_editor_cmd" onclick="<portlet:namespace/><%=cmdName%>();">
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
  <p class="img_editor_cmd" onclick="<portlet:namespace/>cmdNotImpl('<%=cmdName%>');">
	</c:otherwise>
</c:choose>

  <img class="img_cmd_icon" src="<%=imgpath%><%=ImgEditorUtil.getIcon(cmdElm) %>" title='<liferay-ui:message key="<%=cmdName%>"/>' />
  <liferay-ui:message key="<%=cmdName%>"/>
  </p>

<%
	}
%>

</div>

<%
}
%>
<br/>

</div>

<div id="<portlet:namespace/>parmdlg" class="edit_cmd_dlg">
<div class="edit_dlg_cab">
<span id="<portlet:namespace/>tit"></span>
</div>
<input name="parm_mdt" type="hidden" value=""/>
<div style="text-align:center;">
<img id="<portlet:namespace/>tmb" class="tmb_img"/>
</div>
<br/>
<br/>
<div style="margin:5px;">
<div id="<portlet:namespace/>mime">
<liferay-ui:message key="type" /> : 
<select name="<portlet:namespace />new_mime" size="1">
<%for (String mime : PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES)) {%>
<option value="<%=mime%>" <%if (mime.equalsIgnoreCase(mimetype)) {%> selected <%}%>><%=mime %></option>
<%}%>
</select>
<br/>
<br/>
</div>
<span id="<portlet:namespace/>lab_1"></span>
<input id="<portlet:namespace/>inp_1" name="value_1" size="5" type="text" style="text-align:center; margin-left:20px;"/>
<br/>
<span id="<portlet:namespace/>lab_2"></span>
<input id="<portlet:namespace/>inp_2" name="value_2" size="5" type="text" style="text-align:center; margin-left:20px;"/>
<br/>
<span id="<portlet:namespace/>lab_3"></span>
<input id="<portlet:namespace/>inp_3" name="value_3" size="5" type="text" style="text-align:center; margin-left:20px;"/>
<br/>
</div>
<br clear="all"/>
<br/>
<div style="text-align:left; margin:2px; width:100%;">
<input type="button" id="<portlet:namespace/>test" value="<liferay-ui:message key="test" />" onClick="javascript:<portlet:namespace/>testAdjust();" />
<br clear="all"/>
<input type="button" value="<liferay-ui:message key="ok" />" onClick="javascript:<portlet:namespace/>acceptEditDlg();" />
<input type="button" value="<liferay-ui:message key="cancel" />" onClick="javascript:<portlet:namespace/>cancelEditDlg();" />
</div>

</div>

</div>

<br clear="all"/>
<br/>

</div>
<%-- Finish editor window --%>

		<aui:button-row>

			<%
			String saveButtonLabel = "save";

			if ((fileVersion == null) || draft || approved) {
				saveButtonLabel = "save-as-draft";
			}
			%>

			<c:if test="<%= PropsValues.DL_FILE_ENTRY_DRAFTS_ENABLED %>">
				<aui:button disabled="<%= checkedOut && !hasLock %>" name="saveButton" onClick='<%= renderResponse.getNamespace() + "saveFileEntry(true);" %>' value="<%= saveButtonLabel %>" />
			</c:if>

			<%
			String publishButtonLabel = "publish";

			if (DLUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, folderId, fileEntryTypeId)) {
				publishButtonLabel = "submit-for-publication";
			}

			if (saveAsDraft) {
				publishButtonLabel = "save";
			}
			%>

			<aui:button disabled="<%= checkedOut && !hasLock || (pending && PropsValues.DL_FILE_ENTRY_DRAFTS_ENABLED) %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />
			<aui:button onClick='<%= renderResponse.getNamespace() + "cancelAndExit();" %>' value="cancel" />
		</aui:button-row>
</aui:form>


<script type="text/javascript">

var <portlet:namespace/>bloks = new Array(<%=blockList%>);

<c:if test="<%= Validator.isNotNull(idmenu) %>">
<portlet:namespace/>showToggle('<%=idmenu%>');
</c:if>

<portlet:namespace/>repaintImage("repaint");

function <portlet:namespace/>repaintImage(mdt, parm) {
 var xxx = "<%=servResourseURL%>&<%= Constants.CMD %>=repaint";
 if (parm) xxx += parm;
 var marco = document.getElementById('<portlet:namespace/>marco');
 marco.style.backgroundRepeat="no-repeat";
 marco.style.backgroundImage="url(" + xxx + ")";
}

function <portlet:namespace />accept(command) {
	document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = command;
	submitForm(document.<portlet:namespace />fm);
}

function <portlet:namespace/>cmdStd(command, title, nargs, arg1, def1, arg2, def2, arg3, def3) {
	document.<portlet:namespace/>fm.parm_mdt.value=command;
    <portlet:namespace/>startEditDlg(
    		title, false, true,
    		arg1, def1, arg2, def2, arg3, def3
			);
}

function <portlet:namespace/>cmdNotImpl(comm) {
	alert(comm + ': ' + '<liferay-ui:message key="command-not-implement"/>')
}

function <portlet:namespace/>transform() {
	document.<portlet:namespace/>fm.parm_mdt.value="transform";
    <portlet:namespace/>startEditDlg(
    		'<liferay-ui:message key="transform"/>',
    		true, false,
			'<liferay-ui:message key="width"/> (px) :',
			'<%=imgWidth%>',
			'<liferay-ui:message key="height"/> (px) :',
			'<%=imgHeight%>',
			'<liferay-ui:message key="quality"/> (%) :',
			'100'
			);
}


function <portlet:namespace/>startEditDlg(title, change_mime, enable_test, label1, def1, label2, def2, label3, def3) {
	var cmds = document.getElementById('<portlet:namespace/>commands');
	var dlg = document.getElementById('<portlet:namespace/>parmdlg');
	var tit = document.getElementById('<portlet:namespace/>tit');
	tit.innerHTML=title;
	
	if (enable_test) {
		var tmb = document.getElementById('<portlet:namespace/>tmb');
		var xxx = "<%=servResourseURL%>&<%= Constants.CMD %>=tmbinit";
	    tmb.src=xxx;
	}
	
	<portlet:namespace/>enableElement('mime', change_mime);
	<portlet:namespace/>enableElement('test', enable_test);
	<portlet:namespace/>enableElement('tmb', enable_test);

	<portlet:namespace/>setDlgParam("1", label1, def1);
	<portlet:namespace/>setDlgParam("2", label2, def2);
	<portlet:namespace/>setDlgParam("3", label3, def3);
	
	cmds.style.display = 'none';
	dlg.style.display = 'block';
}

function <portlet:namespace/>enableElement(name, enable) {
	var elm = document.getElementById('<portlet:namespace/>' + name);
	if (elm) {
		if (enable) {
			elm.style.display='block';
		} else {
			elm.style.display='none';
		}
	}
}

function <portlet:namespace/>setDlgParam(idx, label, defv) {
	var lab1 = document.getElementById('<portlet:namespace/>lab_' + idx);
	var inp1 = document.getElementById('<portlet:namespace/>inp_' + idx);
	if (label) {
		lab1.style.display='block'; 
		lab1.innerHTML=label;
		inp1.style.display='block';
		inp1.value=defv;
	} else {
		lab1.style.display='none'; 
		inp1.style.display='none'; 
	}
}

function <portlet:namespace/>acceptEditDlg() {
	document.<portlet:namespace/>fm.par1.value = document.<portlet:namespace/>fm.value_1.value;
	document.<portlet:namespace/>fm.par2.value = document.<portlet:namespace/>fm.value_2.value;
	document.<portlet:namespace/>fm.par3.value = document.<portlet:namespace/>fm.value_3.value;
	<portlet:namespace/>accept(document.<portlet:namespace/>fm.parm_mdt.value);
}

function <portlet:namespace/>cancelEditDlg() {
	<portlet:namespace/>accept("cancel-mdt");
}

function <portlet:namespace/>testAdjust() {
	var mdt = document.<portlet:namespace/>fm.parm_mdt.value;
	var tmb = document.getElementById('<portlet:namespace/>tmb');
	var xxx = "<%=servResourseURL%>&<%= Constants.CMD %>=" + mdt;
	xxx += '&par1=' + document.<portlet:namespace/>fm.value_1.value;
	xxx += '&par2=' + document.<portlet:namespace/>fm.value_2.value;
	xxx += '&par3=' + document.<portlet:namespace/>fm.value_3.value;
    tmb.src=xxx;
}

function <portlet:namespace/>showToggle(blkname) {
	for (var i=0; i<<portlet:namespace/>bloks.length; i++) {
		var block = document.getElementById('<portlet:namespace/>block_' + <portlet:namespace/>bloks[i]);
		if (block) {
			if (<portlet:namespace/>bloks[i]==blkname) {
				block.style.display = 'block';
				document.<portlet:namespace/>fm.idmenu.value = blkname;
			} else {
				block.style.display = 'none';
			}
		}
	}
}

function <portlet:namespace/>crop() {
  if (clipRect) {
	  var pos = getClipRectData();
	  document.<portlet:namespace/>fm.par1.value = pos.x;
	  document.<portlet:namespace/>fm.par2.value = pos.y;
	  document.<portlet:namespace/>fm.par3.value = pos.w;
	  document.<portlet:namespace/>fm.par4.value = pos.h;
	  <portlet:namespace/>accept('crop');
  }	
}

function <portlet:namespace/>area() {
 sttBar = document.getElementById('<portlet:namespace/>stat1');
 var btn1 = document.getElementById('<portlet:namespace/>area');
 btn1.className='img_button_disabled'; 
 var image1 = document.getElementById('<portlet:namespace/>marco');
 if (image1) {
 	image1.onmousedown = function(ev){
 		 	mouseOffset = getMouseOffset(this);
 		 	docScroll = getDocScroll(this);
 		 	maxDim = {x:<%=imgWidth%>, y:<%=imgHeight%>};
			var mousePos = getMousePosition(ev);
			<portlet:namespace/>imgClicked(this, mousePos);
			return false;
	};
 }
}

function <portlet:namespace/>imgClicked(img1, mousePos) {
 img1.onmousedown=null;
 var btn1 = document.getElementById('<portlet:namespace/>area');
 btn1.className='img_button_enabled'; 

 var div = document.getElementById('clip-rect');
 div.style.display = 'block';
 div.style.left = mousePos.x.toString() + 'px';
 div.style.top = (mousePos.y - cr_dim * 4).toString() + 'px';
 div.style.width = cr_dim.toString() + 'px';
 div.style.height = cr_dim.toString() + 'px';
 corners = new Array(4);
 for (var i=0; i<4; i++) {
	 <portlet:namespace/>paintCorner(mousePos, i);
 }
 clipRect = div;
 idCorner = 3;
 dispStt2();
 
}

function <portlet:namespace/>paintCorner(mousePos, idx) {
 var corner = document.getElementById('corner' + idx);
 corner.style.width = cr_dim.toString() + 'px';
 corner.style.height = cr_dim.toString() + 'px';
 corner.style.display = 'block';
 corner.style.left = mousePos.x.toString() + 'px';
 corner.style.top = (mousePos.y - cr_dim * idx).toString() + 'px';
 corner.idx = idx;
 makeDraggable(corner);
 corners[idx] = corner;
}
</script>

<script type="text/javascript">

document.onmousemove = mouseMove;
document.onmouseup   = mouseUp;

var cr_dim = 6;
var mouseOffset = null;
var docScroll = null;
var clipRect = null;
var corners = null;
var idCorner = -1;
var sttBar = null;
var maxDim = null;

function mouseCoords(ev) { 
	 if(ev.pageX || ev.pageY) {
	  return {x:ev.pageX, y:ev.pageY}; 
	 }
	 return { 
	  x:ev.clientX + document.body.scrollLeft - document.body.clientLeft, 
	  y:ev.clientY + document.body.scrollTop - document.body.clientTop 
	 }; 
} 

function getMouseOffset(target) {
	var docPos = getPosition(target);
	return docPos;
}
function getDocScroll(target) {
	return {x:target.parentNode.scrollLeft, y:target.parentNode.scrollTop};
}

function getPosition(e){
	var left = 0;
	var top  = 0;

	while (e.offsetParent){
		left += e.offsetLeft;
		top  += e.offsetTop;
		e     = e.offsetParent;
	}
	left += e.offsetLeft;
	top  += e.offsetTop;
	return {x:left, y:top};
}

function mouseMove(ev){
	if(idCorner>=0){
	  pos = getMousePosition(ev);
	  if (pos.x < 0) pos.x = 0;
	  if (pos.y < 0) pos.y = 0;
	  if (pos.x > maxDim.x) pos.x = maxDim.x;
	  if (pos.y > maxDim.y) pos.y = maxDim.y;
	  if (idCorner>=0 && idCorner<4) {
		  paintClipRect(pos);
	  }
	  return false;
	}
}

function getMousePosition(ev){
	 ev = ev || window.event;
	 var mousePos = mouseCoords(ev);
	 return {x:mousePos.x - mouseOffset.x + docScroll.x, y:mousePos.y - mouseOffset.y + docScroll.y};
}


function paintClipRect(cornPos) {
	 cornPos.y = cornPos.y - cr_dim * idCorner;
	 if (idCorner > 1) cornPos.y = cornPos.y - cr_dim;
	 if (idCorner == 1 || idCorner == 3) cornPos.x = cornPos.x - cr_dim;
	 
	 switch (idCorner) {
		case 0:
			if (cornPos.x > parseInt(corners[1].style.left)) return;
			if (cornPos.y > parseInt(corners[2].style.top) + 2 * cr_dim) return;
			corners[1].style.top = (cornPos.y - cr_dim).toString() + 'px';
			corners[2].style.left = cornPos.x.toString() + 'px';
			break;
		case 1:
			if (cornPos.x < parseInt(corners[0].style.left)) return;
			if (cornPos.y > parseInt(corners[3].style.top) + cr_dim) return;
			corners[0].style.top = (cornPos.y + cr_dim).toString() + 'px';
			corners[3].style.left = cornPos.x.toString() + 'px';
			break;
		case 2:
			if (cornPos.x > parseInt(corners[3].style.left)) return;
			if (cornPos.y < parseInt(corners[0].style.top) - 2 * cr_dim) return;
			corners[3].style.top = (cornPos.y - cr_dim).toString() + 'px';
			corners[0].style.left = cornPos.x.toString() + 'px';
			break;
		case 3:
			if (cornPos.x < parseInt(corners[2].style.left)) return;
			if (cornPos.y < parseInt(corners[1].style.top) - cr_dim) return;
			corners[2].style.top = (cornPos.y + cr_dim).toString() + 'px';
			corners[1].style.left = cornPos.x.toString() + 'px';
			break;
	 }

	 corners[idCorner].style.left = cornPos.x.toString() + 'px';
	 corners[idCorner].style.top  = cornPos.y.toString() + 'px';
	 
	 clipRect.style.top = (parseInt(corners[0].style.top) - cr_dim * 4).toString() + 'px';
	 clipRect.style.left = corners[0].style.left;
	 var wdt = parseInt(corners[3].style.left) + cr_dim - 2 - parseInt(corners[0].style.left);
	 var hth = parseInt(corners[3].style.top) + cr_dim * 4 - 2 - parseInt(corners[0].style.top);
	 clipRect.style.width = wdt + 'px';
	 clipRect.style.height = hth + 'px';
	 var pos = getClipRectData();
	 sttBar.innerHTML="x=" + pos.x.toString() + 
	                  " y=" + pos.y.toString() + 
	                  " width=" + pos.w.toString() + 
	                  " height=" + pos.h.toString();
}
function getClipRectData() {
	return {
			x: parseInt(clipRect.style.left),
			y: parseInt(clipRect.style.top) + 4 * cr_dim,
			w: parseInt(clipRect.style.width) + 2,
			h: parseInt(clipRect.style.height) + 2
		}
}

function mouseUp(){
	idCorner = -1;
}

function makeDraggable(item){
	if(!item) return;
	item.onmousedown = function(ev){
		if(idCorner>=0) return false;
		docScroll = getDocScroll(this.parentNode);
		idCorner = this.idx;
		return false;
	};
}
</script>


<aui:script>
   function <portlet:namespace />cancelAndExit() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.CANCEL %>"
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />cancelCheckOut() {
		submitForm(document.hrefFm, "<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_image_inline" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CANCEL_CHECKOUT %>" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntryId) %>" /></portlet:actionURL>");
	}

	function <portlet:namespace />checkIn() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.UPDATE_AND_CHECKIN %>"
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />checkOut() {
		submitForm(document.hrefFm, "<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_image_inline" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CHECKOUT %>" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntryId) %>" /></portlet:actionURL>");
	}

	function <portlet:namespace />saveFileEntry(draft) {
		if (draft) {
			document.<portlet:namespace />fm.<portlet:namespace />workflowAction.value = "<%= WorkflowConstants.ACTION_SAVE_DRAFT %>";
		}
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

</aui:script>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.document_library.edit_image_online_jsp");
%>
