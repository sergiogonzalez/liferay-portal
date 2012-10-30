package com.liferay.portlet.documentlibrary.util;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Future;

import javax.portlet.PortletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.liferay.portlet.documentlibrary.action.EditImageInlineAction.ImgFile;
import com.liferay.portlet.documentlibrary.service.impl.ImageMagickImpl2;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.repository.model.FileEntry;

public class ImgEditorUtil {
	public static void init() {
		if (cmdDocument == null) initcmdDocument();
	}
	
	public static NodeList getMenuBlocks() {
		ImgEditorUtil.init();
		return cmdDocument.getDocumentElement().getElementsByTagName("block");
	}
	
	public static NodeList getBlockCommands(String blockName) {
		NodeList blocks = ImgEditorUtil.getMenuBlocks();
		for (int i = 0; i < blocks.getLength(); i++) {
			Node node = blocks.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) continue;
			Element el = (Element) node;
			if (blockName.equalsIgnoreCase(el.getAttribute("name")))
				return el.getElementsByTagName("command");
		}
		return null;
	}
	
	public static Element getCommand(String cmdName) {
		NodeList blocks = ImgEditorUtil.getMenuBlocks();
		for (int i = 0; i < blocks.getLength(); i++) {
			Node node = blocks.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) continue;
			Element el = (Element) node;
			Element cmd = ImgEditorUtil.getCommand(el.getAttribute("name"), cmdName);
			if (cmd != null) return cmd;
		}
		return null;
	}

	public static Element getCommand(String blockName, String cmdName) {
		NodeList commands = ImgEditorUtil.getBlockCommands(blockName); 
		for (int i = 0; i < commands.getLength(); i++) {
			Node node = commands.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) continue;
			Element el = (Element) node;
			if (cmdName.equalsIgnoreCase(el.getAttribute("name"))) return el;
		}
		return null;
	}

	public static String getCommandDescr(Element el, Locale locale) {
		if (el != null)
			return LanguageUtil.get(locale, el.getAttribute("name"));
		return "";
	}
	
	public static boolean hasStandardProcess(Element cmd) {
		return "standard".equalsIgnoreCase(cmd.getAttribute("process"));
	}
	public static boolean isCommandImlemented(Element cmd) {
		return !"not_implement".equalsIgnoreCase(cmd.getAttribute("process"));
	}
	public static boolean needResize(Element cmd) {
		if (cmd.hasAttribute("resize")) {
			return GetterUtil.getBoolean(cmd.getAttribute("resize"), false);
		}
		return false;
	}
	
	public static String getIcon(Element cmd) {
    	NodeList icons = cmd.getElementsByTagName("icon");
    	String iName = "";
    	if (icons.getLength() > 0) iName = icons.item(0).getTextContent();
    	if (Validator.isNull(iName) || iName.length() == 0) iName = "default.gif";
		return iName;
	}
	
	public static String getArgDescr(Element argument, Locale locale) {
		StringBuilder sb = new StringBuilder();
		sb.append(LanguageUtil.get(locale, argument.getAttribute("name")));
		sb.append(" ");
		NodeList opts = argument.getElementsByTagName("descr");
		if (opts.getLength() > 0) {
			sb.append(opts.item(0).getTextContent());
		}
		return sb.toString();
	}
	
	public static String getArgDefault(Element argument) {
		return argument.getAttribute("default");
	}
	
	public static String getJSArguments(Element el, Locale locale) {
		StringBuilder sb = new StringBuilder();
		int numArgs = GetterUtil.get(el.getAttribute("nargs"), 0);
		sb.append("'");
		sb.append(el.getAttribute("name"));
		sb.append("','");
		sb.append(ImgEditorUtil.getCommandDescr(el, locale));
		sb.append("',");
		sb.append(Integer.toString(numArgs));
		
		int argCount = 0;
        
		if (numArgs > 0) {
        	NodeList args = el.getElementsByTagName("argument"); 
        	for (int n = 0; n < args.getLength(); n++) {
    			Node node = args.item(n);
    			if (node.getNodeType() != Node.ELEMENT_NODE) continue;
    			Element arg = (Element) node;
        		sb.append(",'");
        		sb.append(ImgEditorUtil.getArgDescr(arg, locale));
        		sb.append("','");
        		sb.append(ImgEditorUtil.getArgDefault(arg));
        		sb.append("'");
        		argCount++;
        	}
        }
		
		for (int i = argCount; i < ImgEditorUtil.MAX_NUM_ARGS; i++) {
			sb.append(",null,null");
		}		
		return sb.toString();
	}
	
	public static String createCommand(PortletRequest request, Element command) {
		String option = "";
		NodeList opts = command.getElementsByTagName("option");
		if (opts.getLength() > 0) {
			option = opts.item(0).getTextContent();
			int nargs = GetterUtil.get(command.getAttribute("nargs"), 0);
			for (int i = 0; i < nargs; i++) {
				String s = Integer.toString(i + 1);
				String param = ParamUtil.getString(request, "par" + s);
				if (Validator.isNull(param)) param = "0";
				int n = option.indexOf("#" + s);
				if (n >= 0) option = option.substring(0, n) + param + option.substring(n + 2); 
			}
		}
		return option;
	}
	
	public static String getFileExtension(FileEntry fileEntry, ImgFile imgfile) throws Exception {
		if (imgfile != null && Validator.isNotNull(imgfile.getFilename())) {
			return imgfile.getExtension();
		} else {
			return "." + fileEntry.getExtension();
		}
	}
	public static String getFileMimeType(FileEntry fileEntry, ImgFile imgfile) throws Exception {
		if (imgfile != null && Validator.isNotNull(imgfile.getFilename())) {
			return MimeTypesUtil.getContentType(imgfile.getFilename());
		} else {
			return fileEntry.getMimeType();
		}
	}
	
	//// 6.1.x without ImageMagicUtil 
	public static String[] identify(List<String> arguments) throws Exception {
		return getImageMagick().identify(arguments);
	}

	public static void reset() {
		getImageMagick().reset();
	}

	public static Future<?> convert(List<String> arguments) throws Exception {
		return getImageMagick().convert(arguments);
	}

	private static ImageMagickImpl2 getImageMagick() {
		if (imageMagicImpl == null) imageMagicImpl = ImageMagickImpl2.getInstance();
		return imageMagicImpl;
	}
	//// 6.1.x without ImageMagicUtil 
	
	private static void initcmdDocument() {
		URL url = ImgEditorUtil.class.getResource("imageEditorCmd.xml");
		try {
			read(url.openStream());
		}
		catch (Exception e) {
			//_log.error("Unable to populate extensions map", e);
		}
	}
	private static void read(InputStream stream) throws Exception {
		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		cmdDocument = documentBuilder.parse(new InputSource(stream));
	}	
//////////////////////
	

public static final int MAX_NUM_ARGS = 3;
private static Document cmdDocument = null;
private static ImageMagickImpl2 imageMagicImpl = null;

}
