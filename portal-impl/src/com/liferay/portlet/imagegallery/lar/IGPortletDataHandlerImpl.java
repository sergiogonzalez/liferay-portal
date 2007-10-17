/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.imagegallery.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.xstream.XStream;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="IGPortletDataHandlerImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Bruno Farache
 */
public class IGPortletDataHandlerImpl implements PortletDataHandler {

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {_enableExport};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException{

		return new PortletDataHandlerControl[] {_enableImport};
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean exportData = MapUtil.getBoolean(
			parameterMap, _EXPORT_IG_DATA,
			_enableExport.getDefaultState());

		if (_log.isDebugEnabled()) {
			if (exportData) {
				_log.debug("Exporting data is enabled");
			}
			else {
				_log.debug("Exporting data is disabled");
			}
		}

		if (!exportData) {
			return null;
		}

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("image-gallery");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			// Folders

			List folders = IGFolderUtil.findByGroupId(context.getGroupId());

			List igImages = new ArrayList();

			Iterator itr = folders.iterator();

			while (itr.hasNext()) {
				IGFolder folder = (IGFolder)itr.next();

				if (context.addPrimaryKey(
						IGFolder.class,
						new Long(folder.getFolderId()))) {

					itr.remove();
				}
				else {
					List folderIGImages = IGImageUtil.findByFolderId(
						folder.getFolderId());

					igImages.addAll(folderIGImages);
				}
			}

			String xml = xStream.toXML(folders);

			Element el = root.addElement("ig-folders");

			Document tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			// IGImages

			List images = new ArrayList();

			itr = igImages.iterator();

			while (itr.hasNext()) {
				IGImage igImage = (IGImage)itr.next();

				if (context.addPrimaryKey(
						IGImage.class, new Long(igImage.getImageId()))) {

					itr.remove();
				}
				else {
					Image smallImage =
						ImageUtil.fetchByPrimaryKey(igImage.getSmallImageId());

					images.add(smallImage);

					Image largeImage =
						ImageUtil.fetchByPrimaryKey(igImage.getLargeImageId());

					images.add(largeImage);
				}
			}

			xml = xStream.toXML(igImages);

			el = root.addElement("ig-images");

			tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			// Images

			itr = images.iterator();

			while (itr.hasNext()) {
				Image image = (Image)itr.next();

				if (context.addPrimaryKey(
						Image.class, new Long(image.getImageId()))) {

					itr.remove();
				}
			}

			xml = xStream.toXML(images);

			el = root.addElement("images");

			tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean importData = MapUtil.getBoolean(
			parameterMap, _IMPORT_IG_DATA,
			_enableImport.getDefaultState());

		if (_log.isDebugEnabled()) {
			if (importData) {
				_log.debug("Importing data is enabled");
			}
			else {
				_log.debug("Importing data is disabled");
			}
		}

		if (!importData) {
			return null;
		}

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = reader.read(new StringReader(data));

			Element root = doc.getRootElement();

			// Folders

			Element el = root.element("ig-folders").element("list");

			Document tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List folders = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			Iterator itr = folders.iterator();

			while (itr.hasNext()) {
				IGFolder folder = (IGFolder)itr.next();

				folder.setGroupId(context.getGroupId());

				IGFolderUtil.update(folder, true);
			}

			// IGImages

			el = root.element("ig-images").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List igImages = (List) xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			itr = igImages.iterator();

			while (itr.hasNext()) {
				IGImage igImage = (IGImage)itr.next();

				IGImageUtil.update(igImage, true);
			}

			// Images

			el = root.element("images").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List images = (List) xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			itr = images.iterator();

			while (itr.hasNext()) {
				Image image = (Image)itr.next();

				ImageUtil.update(image, true);
			}

			// No special modification to the incoming portlet preferences
			// needed

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected String getPrimaryKey(long groupId, String key) {
		StringMaker sm = new StringMaker();

		sm.append(groupId);
		sm.append(StringPool.POUND);
		sm.append(key);

		return sm.toString();
	}

	private static final String _EXPORT_IG_DATA =
		"export-" + PortletKeys.IMAGE_GALLERY + "-data";

	private static final String _IMPORT_IG_DATA =
		"import-" + PortletKeys.IMAGE_GALLERY + "-data";

	private static final PortletDataHandlerBoolean _enableExport =
		new PortletDataHandlerBoolean(_EXPORT_IG_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableImport =
		new PortletDataHandlerBoolean(_IMPORT_IG_DATA, true, null);

	private static Log _log =
		LogFactory.getLog(IGPortletDataHandlerImpl.class);

}