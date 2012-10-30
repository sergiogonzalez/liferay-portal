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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.DuplicateLockException;
import com.liferay.portal.NoSuchRepositoryEntryException;
//import com.liferay.portal.kernel.image.ImageMagickUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileMimeTypeException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.ImgEditorUtil;
import com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException;

import de.schlichtherle.io.FileInputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.im4java.core.IMOperation;
import org.w3c.dom.Element;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Sergio González
 * @author Manuel de la Peña
 * @author Levente Hudák
 * @author Vladimir Kochubey
 */

public class EditImageInlineAction extends PortletAction {
	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);
		String idmenu = ParamUtil.getString(actionRequest, "idmenu");

		try {
			if (Validator.isNull(cmd)) {
				cmd = "";
			}
			if (cmd.equals(Constants.UPDATE) ||
				cmd.equals(Constants.UPDATE_AND_CHECKIN)) {

				updateFileEntry(portletConfig, actionRequest, actionResponse, cmd);
				
			} else if (cmd.equalsIgnoreCase("crop")) {
				cropImage(actionRequest, actionResponse);
				return;
			} else if (cmd.equalsIgnoreCase("transform")) {
				transformImage(actionRequest, actionResponse);
				return;
			} else if (cmd.equalsIgnoreCase("cancel-mdt")) {
				PortletSession session = actionRequest.getPortletSession(); 
				cleanThumbnail(session);
				ImgFile imgfile = (ImgFile) session.getAttribute("imgfile");
				processMetadata(actionRequest, actionResponse, imgfile, false);
				return;
			} else if (cmd.equalsIgnoreCase(Constants.CANCEL)) {
				cleanEditData(actionRequest.getPortletSession());
			} else if (Validator.isNotNull(cmd) && !cmd.equals("")) {
				Element cmdElm = null;
				if (Validator.isNotNull(idmenu)) {
				  cmdElm = ImgEditorUtil.getCommand(idmenu, cmd);
				} else {
				  cmdElm = ImgEditorUtil.getCommand(cmd);
				} 
				if (cmdElm != null) {
					simpleImageConvert(actionRequest, actionResponse,
							ImgEditorUtil.createCommand(actionRequest, cmdElm), ImgEditorUtil.needResize(cmdElm));
				}
				return;
			}

			String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception e) {
			_log.error(e);

			cleanEditData(actionRequest.getPortletSession());

			if (e instanceof DuplicateLockException ||
				e instanceof InvalidFileVersionException ||
				e instanceof NoSuchFileEntryException ||
				e instanceof PrincipalException) {

				if (e instanceof DuplicateLockException) {
					DuplicateLockException dle = (DuplicateLockException)e;

					SessionErrors.add(
						actionRequest, dle.getClass(), dle.getLock());
				}
				else {
					SessionErrors.add(actionRequest, e.getClass());
				}

				setForward(actionRequest, "portlet.document_library.error");
			}
			else if (e instanceof DuplicateFileException ||
					 e instanceof DuplicateFolderNameException ||
					 e instanceof FileExtensionException ||
					 e instanceof FileMimeTypeException ||
					 e instanceof FileNameException ||
					 e instanceof FileSizeException ||
					 e instanceof NoSuchFolderException ||
					 e instanceof SourceFileNameException ||
					 e instanceof StorageFieldRequiredException) {

				if (!cmd.equals(Constants.ADD_MULTIPLE) &&
					!cmd.equals(Constants.ADD_TEMP)) {

					SessionErrors.add(actionRequest, e.getClass());

					return;
				}

				if (e instanceof DuplicateFileException) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(actionResponse);

					response.setStatus(
						ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION);
				}
				else if (e instanceof FileExtensionException) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(actionResponse);

					response.setStatus(
						ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION);
				}
				else if (e instanceof FileNameException) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(actionResponse);

					response.setStatus(
						ServletResponseConstants.SC_FILE_NAME_EXCEPTION);
				}
				else if (e instanceof FileSizeException) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(actionResponse);

					response.setStatus(
						ServletResponseConstants.SC_FILE_SIZE_EXCEPTION);
				}

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getFileEntry(renderRequest);
			FileEntry fileEntry = (FileEntry) renderRequest.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);
			PortletSession session = renderRequest.getPortletSession();
			ImgFile imgfile = (ImgFile) session.getAttribute("imgfile");
			if (imgfile == null) {
				imgfile = startEditor(renderRequest, fileEntry);
			}
			renderRequest.setAttribute("imgfile", imgfile);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileEntryException ||
				e instanceof NoSuchFileVersionException ||
				e instanceof NoSuchRepositoryEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.document_library.error");
			}
			else {
				throw e;
			}
		}

		String forward = "portlet.document_library.edit_image_inline";

		return mapping.findForward(getForward(renderRequest, forward));
	}

	@Override
	public void serveResource (
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest request, ResourceResponse response)
		throws Exception {

		String cmd = ParamUtil.getString(request, Constants.CMD);
	    if (Validator.isNull(cmd)) cmd = "";
		PortletSession session = request.getPortletSession();
		
		cmd = cmd.trim();
	    
	    if (cmd.equalsIgnoreCase("repaint")) {
	    	serveImageData(session, response);
	    } else if (cmd.equalsIgnoreCase("tmbinit")) {
			createThumbnail(session, response);
	    } else if (!"".equals(cmd)) {
			Element cmdElm = ImgEditorUtil.getCommand(cmd);
			if (cmdElm != null) {
		    	processThumbnail(session, request, response, cmdElm);
			}
	    }
	}
	
	private void cleanEditData(PortletSession session) {
		ImgFile imgFile = (ImgFile) session.getAttribute("imgfile");
		session.removeAttribute("imgfile");
		session.removeAttribute("editEntry");
		if (imgFile != null && Validator.isNotNull(imgFile.getFilename())) {
			File f = new File(imgFile.getFilename());
			if (f.exists()) f.delete();
		}
		cleanThumbnail(session);
	}
	
	private void cleanThumbnail(PortletSession session) {
		ImgFile tmbFile = (ImgFile) session.getAttribute("tmbfile");
		session.removeAttribute("tmbfile");
		if (tmbFile != null && Validator.isNotNull(tmbFile.getFilename())) {
			File f = new File(tmbFile.getFilename());
			if (f.exists()) f.delete();
		}
	}

	protected void updateFileEntry(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse, String cmd) throws Exception {

		PortletSession session = actionRequest.getPortletSession();

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");
		DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(fileEntryId);
		ImgFile imgfile = (ImgFile) session.getAttribute("imgfile");

		long folderId = ParamUtil.getLong(actionRequest, "folderId");
		ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), actionRequest);
		String changeLog = ParamUtil.getString(actionRequest, "changeLog");
		
		if (fileEntry != null && imgfile != null && Validator.isNotNull(imgfile.getFilename())) {
			File file = new File(imgfile.getFilename());
			if (file.exists() && file.length() > 0) {
				InputStream inputStream = new FileInputStream(file);
				String contentType = MimeTypesUtil.getContentType(file);
				FileEntry entry;
				try {
					if (cmd.equals(Constants.UPDATE_AND_CHECKIN)) {
						entry = DLAppServiceUtil.updateFileEntryAndCheckIn(
								fileEntryId, 
								fileEntry.getName(),
								contentType,
								fileEntry.getTitle(),
								fileEntry.getDescription(),
								changeLog,
								true,
								inputStream,
								file.length(),
								serviceContext);
					}
					else {
						entry = DLAppServiceUtil.updateFileEntry(
								fileEntryId, 
								fileEntry.getName(),
								contentType,
								fileEntry.getTitle(),
								fileEntry.getDescription(),
								changeLog,
								true,
								inputStream,
								file.length(),
								serviceContext);
					}
					AssetPublisherUtil.addRecentFolderId(
							actionRequest, DLFileEntry.class.getName(), folderId);
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}
			}
		}
		cleanEditData(session);
	}

	private ImgFile startEditor(PortletRequest request, FileEntry fileEntry) throws Exception {
		ImgEditorUtil.reset();  //ImageMagickUtil.reset();
		PortletSession session = request.getPortletSession();
		ImgFile imgfile = new ImgFile(null);
		File f = DLFileEntryLocalServiceUtil.getFile(fileEntry.getUserId(), fileEntry.getFileEntryId(), fileEntry.getVersion(), false);
		extractImgMetadata(imgfile, ejecutarImIdentify(f.getAbsolutePath()));
    	session.setAttribute("editEntry", fileEntry);
    	session.setAttribute("imgfile", imgfile);
    	return imgfile;
	}

	private String[] ejecutarImIdentify(String filename) {
		ArrayList<String> args = new ArrayList<String>();
		args.add(filename);
		String[] res = null;
        try {
    		String[] res2 = ImgEditorUtil.identify(args); //ImageMagickUtil.identify(args);
    		if (res2.length > 0) {
    			res = res2[0].split(" ");
    		}
        } catch (Exception ex1) {
           _log.error(ex1);
        }
        return res;
	}
	
	protected void processConvert(ImgFile imgfile, String inputFileName, IMOperation cmd, File dest) throws Exception {
		processConvert(imgfile, inputFileName, cmd, dest, true);
	}
	protected void processConvert(ImgFile imgfile, String inputFileName, IMOperation cmd, File dest,
			                      boolean removeOrigin) throws Exception {

		Future<?> future = ImgEditorUtil.convert(cmd.getCmdArgs()); // ImageMagickUtil.convert(cmd.getCmdArgs());
		future.get(10, TimeUnit.SECONDS);
		if (dest.length() > 0) {
			imgfile.setFilename(dest.getAbsolutePath());
			if (removeOrigin) {
				File f = new File(inputFileName);
				if (f.exists()) f.delete();
			}
		} else {
			if (dest.exists()) dest.delete();
			_log.error("IM error procesing : " + cmd.toString());
		}
	}
	
	protected void processMetadata(ActionRequest request, ActionResponse response, 
			                       ImgFile imgfile, boolean extractMetadata) {
		if (extractMetadata) {
			extractImgMetadata(imgfile, ejecutarImIdentify(imgfile.getFilename()));
		}
		sendImgMetadata(request, response, imgfile);
	}

	protected void cropImage(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		PortletSession session = actionRequest.getPortletSession();
		FileEntry fileEntry = (FileEntry) session.getAttribute("editEntry");
		int pos_x = ParamUtil.getInteger(actionRequest, "par1", -1);
		int pos_y = ParamUtil.getInteger(actionRequest, "par2", -1);
		int width = ParamUtil.getInteger(actionRequest, "par3", -1);
		int height = ParamUtil.getInteger(actionRequest, "par4", -1);
		if (pos_x < 0) return;

		ImgFile imgfile = (ImgFile) session.getAttribute("imgfile");
		String inputFileName = getInputFileName(fileEntry, imgfile);
		
		File dest = File.createTempFile("dimg", ImgEditorUtil.getFileExtension(fileEntry, imgfile));
		String outputFileName = dest.getAbsolutePath();

		//convert rose: -crop 40x30+10+10  +repage  repage.gif
		IMOperation imOperation = new IMOperation();
		imOperation.addImage(inputFileName);
		imOperation.addRawArgs("-crop", width + "x" + height + "+" + pos_x + "+" + pos_y, "+repage");
		imOperation.addImage(outputFileName);

		processConvert(imgfile, inputFileName, imOperation, dest);
		processMetadata(actionRequest, actionResponse, imgfile, true);
	}
	
	protected void simpleImageConvert(ActionRequest actionRequest, ActionResponse actionResponse, 
			                          String option, boolean needExtractMetadata) throws Exception {
		PortletSession session = actionRequest.getPortletSession();
		FileEntry fileEntry = (FileEntry) session.getAttribute("editEntry");

		ImgFile imgfile = (ImgFile) session.getAttribute("imgfile");
		String inputFileName = getInputFileName(fileEntry, imgfile);
		
		File dest = File.createTempFile("dimg", ImgEditorUtil.getFileExtension(fileEntry, imgfile));
		String outputFileName = dest.getAbsolutePath();

		IMOperation imOperation = new IMOperation();
		imOperation.addImage(inputFileName);
		String[] vars = option.split(" ");
		for (String s: vars) imOperation.addRawArgs(s);
		imOperation.addImage(outputFileName);
		
		processConvert(imgfile, inputFileName, imOperation, dest);		
		processMetadata(actionRequest, actionResponse, imgfile, needExtractMetadata);
	}
	
	protected void transformImage(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		PortletSession session = actionRequest.getPortletSession();
		FileEntry fileEntry = (FileEntry) session.getAttribute("editEntry");

		ImgFile imgfile = (ImgFile) session.getAttribute("imgfile");
		String inputFileName = getInputFileName(fileEntry, imgfile);
		
		String newMimetype = ParamUtil.getString(actionRequest, "new_mime");
		String newWidth = ParamUtil.getString(actionRequest, "par1");
		String newHeight = ParamUtil.getString(actionRequest, "par2");
		int quality = GetterUtil.get(ParamUtil.getString(actionRequest, "par3"), 100);
		if (quality > 100) quality = 100;
		String extension = ImgEditorUtil.getFileExtension(fileEntry, imgfile);
		if (Validator.isNotNull(newMimetype) && !"".equals(newMimetype)) {
			Set<String> extensions = MimeTypesUtil.getExtensions(newMimetype);
			if (extensions.size() > 0) {
				extension = (String) extensions.toArray()[0];
			}	
		}
		
		File dest = File.createTempFile("dimg", extension);
		String outputFileName = dest.getAbsolutePath();

		IMOperation imOperation = new IMOperation();
		imOperation.addImage(inputFileName);
		imOperation.addRawArgs("-resize", newWidth + "x" + newHeight);
		if (quality >= 0 && quality < 100) {
			imOperation.addRawArgs("-quality",quality + "%");
		}
		imOperation.addImage(outputFileName);
		
		processConvert(imgfile, inputFileName, imOperation, dest);		
		processMetadata(actionRequest, actionResponse, imgfile, true);
	}
	
	protected String getInputFileName(FileEntry fileEntry, ImgFile imgfile) throws Exception {
		if (imgfile != null && Validator.isNotNull(imgfile.getFilename())) {
			return imgfile.getFilename();
		} else {
			File f = DLFileEntryLocalServiceUtil.getFile(fileEntry.getUserId(), fileEntry.getFileEntryId(), fileEntry.getVersion(), false);
			File origin = File.createTempFile("oimg", "." + fileEntry.getExtension());
			FileUtil.copyFile(f, origin);
			return origin.getAbsolutePath();
		}
	}

	protected void extractImgMetadata(ImgFile imgfile, String[] info) {
		if (info != null && info.length > 2 && info[2] != null) {
			String[] dim = info[2].split("x");
			if (dim.length > 1) {
				imgfile.setWidth(dim[0]);
				imgfile.setHeight(dim[1]);
			}
		}	
	}
	protected void sendImgMetadata(ActionRequest actionRequest, ActionResponse actionResponse, ImgFile imgfile) {
		if (imgfile != null) {
			actionResponse.setRenderParameter("img-width", imgfile.getWidth());
			actionResponse.setRenderParameter("img-height", imgfile.getHeight());
		}
		String idmenu = ParamUtil.getString(actionRequest, "idmenu");
		if (Validator.isNotNull(idmenu)) actionResponse.setRenderParameter("idmenu", idmenu);
	}

	protected void processThumbnail(PortletSession session, 
									ResourceRequest request,
									ResourceResponse response,
									Element cmd) {
		ImgFile tmbfile = (ImgFile) session.getAttribute("tmbfile");
		try {
			String inputFileName = tmbfile.getFilename();
			File dest = File.createTempFile("dimg", ".gif");
			String outputFileName = dest.getAbsolutePath();

			IMOperation imOperation = new IMOperation();
			imOperation.addImage(inputFileName);
			String option = ImgEditorUtil.createCommand(request, cmd);
			String[] vars = option.split(" ");
			for (String s: vars) imOperation.addRawArgs(s);
			imOperation.addImage(outputFileName);

			Future<?> future = ImgEditorUtil.convert(imOperation.getCmdArgs()); // ImageMagickUtil.convert(imOperation.getCmdArgs());
			future.get(10, TimeUnit.SECONDS);

			if (dest.length() > 0) {
				serveThumbnailData(response, outputFileName);
				dest.delete();
				return;
			} else {
				if (dest.exists()) dest.delete();
				_log.error(" *** ERROR creando Thumbnail " + imOperation.toString());
			}
		} catch (Exception e) {
			_log.error(" *** ERROR creando Thumbnail: " + e.getMessage());
		}
	}

	
	protected void createThumbnail(PortletSession session, ResourceResponse response) {
		ImgFile imgfile = (ImgFile) session.getAttribute("imgfile");
		FileEntry entry = (FileEntry) session.getAttribute("editEntry");
		
		int maxWidth = GetterUtil.get(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH), 40);
		int maxHeight = GetterUtil.get(PropsUtil.get(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT), 40);
		int width = GetterUtil.get(imgfile.getWidth(), 0);
		int height = GetterUtil.get(imgfile.getHeight(), 0);
		if (width > maxWidth) {
			double koeff = width / maxWidth;
			width = maxWidth;
			height = (int) (height * koeff);
			if (height == 0) height = 1;
		}
		if (height > maxHeight) {
			double koeff = height / maxHeight;
			height = maxHeight;
			width = (int) (width * koeff);
			if (width == 0) width = 1;
		}

		if (width > 0 && height > 0) {
			try {
				String inputFileName = getInputFileName(entry, imgfile);
				File dest = File.createTempFile("dimg", ".gif");
				String outputFileName = dest.getAbsolutePath();
				
				IMOperation imOperation = new IMOperation();
				imOperation.addImage(inputFileName);
				imOperation.addRawArgs("-resize", width + "x" + height);
				imOperation.addImage(outputFileName);
				
				ImgFile tmbfile = new ImgFile(outputFileName);
				
				processConvert(tmbfile, inputFileName, imOperation, dest, false);
				tmbfile.setWidth(Integer.toString(width));
				tmbfile.setHeight(Integer.toString(height));
				session.setAttribute("tmbfile", tmbfile);
				serveThumbnailData(response, outputFileName);
				
			} catch (Exception e) {
				_log.error(e);
			}
		}
	}
	
	protected void serveThumbnailData(ResourceResponse response, String filename) {
		BufferedInputStream buf = null;
		OutputStream out = null;
		try {
			File file = new File(filename);
			String mimetype = MimeTypesUtil.getContentType(file);
			buf = new BufferedInputStream(new FileInputStream(file));
			int length = (int) file.length();
			out = response.getPortletOutputStream();
			response.setContentType(mimetype);
			response.setContentLength(length);
			int readBytes = 0;
			while ( (readBytes = buf.read()) != -1) out.write(readBytes);
		} catch (Exception e) {
			_log.error(e);
		} finally {
			try {
				if (out != null) out.close();
				if (buf != null) buf.close();
			} catch (Exception e2) {
			}
		}
	}
	
	protected void serveImageData(PortletSession session, ResourceResponse response) {
		ImgFile imgfile = (ImgFile) session.getAttribute("imgfile");
		FileEntry entry = (FileEntry) session.getAttribute("editEntry");
		serveImageData(response, entry, imgfile);
	}
	
	protected void serveImageData(ResourceResponse response, FileEntry fileEntry, ImgFile imgfile) {

		if (fileEntry != null) {
			BufferedInputStream buf = null;
			OutputStream out = null;
			int length = 0;
			try {
				String mimetype;
				if (imgfile != null && Validator.isNotNull(imgfile.getFilename())) {
					File file = new File(imgfile.getFilename());
					mimetype = MimeTypesUtil.getContentType(file);
					buf = new BufferedInputStream(new FileInputStream(file));
					length = (int) file.length();
				} else {
					buf = new BufferedInputStream(
							  DLFileEntryLocalServiceUtil.getFileAsStream(fileEntry.getUserId(),
									  fileEntry.getFileEntryId(),
									  fileEntry.getVersion(),
									  true));
					mimetype = fileEntry.getMimeType();
					length = (int) fileEntry.getSize();
				}
				out = response.getPortletOutputStream();
				response.setContentType(mimetype);
				response.setContentLength(length);
				int readBytes = 0;
				while ( (readBytes = buf.read()) != -1) out.write(readBytes);
			} catch (Exception e) {
				_log.error(e);
			} finally {
				try {
					if (out != null) out.close();
					if (buf != null) buf.close();
				} catch (Exception e2) {
				}
			}
		}
	}

	
	public class ImgFile {
		public ImgFile(String fname) {
			filename = fname;
		}
		public String getFilename() {
			return filename;
		}
		public void setFilename(String fname) {
			filename = fname;
			if (Validator.isNotNull(filename)) {
				int n = filename.lastIndexOf('.');
				if (n > 0) {
					extension = filename.substring(n);
				}
			}
		}
		public String getExtension() {
			return extension;
		}
		public String getWidth() {
			return width;
		}
		public void setWidth(String width) {
			this.width = width;
		}
		public String getHeight() {
			return height;
		}
		public void setHeight(String height) {
			this.height = height;
		}
		String filename;
		String extension;
		String width;
		String height;
	}
	
//	private static final String _TEMP_FOLDER_NAME =	EditImageInlineAction.class.getName();
	private static Log _log = LogFactoryUtil.getLog(EditImageInlineAction.class);

}
