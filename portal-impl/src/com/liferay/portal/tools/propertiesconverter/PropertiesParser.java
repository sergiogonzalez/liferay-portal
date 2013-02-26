
package com.liferay.portal.tools.propertiesconverter;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FileImpl;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertiesParser {

	public static void main(String[] args) {

		// Create a Data Model for Freemarker

		Map root = new HashMap();

		String pageTitle = "Portal Properties";

		if (!args[0].isEmpty()) {
			pageTitle = args[0];
		}

		root.put("pageTitle", pageTitle);

		boolean toc = true;

		if (!args[1].isEmpty()) {
			toc = Boolean.parseBoolean(args[1]);
		}

		root.put("toc", toc);

		String propertiesFileName = "portal.properties";

		if (!args[2].isEmpty()) {
			int pos = args[2].lastIndexOf(StringPool.FORWARD_SLASH);

			if (pos != -1) {
				propertiesFileName = args[2].substring(pos + 1);
			}
		}

		root.put("propertiesFileName", propertiesFileName);

		// Parse properties file and create sections and properties for the data
		// model

		StringBundler sb = new StringBundler(3);

		sb.append(System.getProperty("user.dir"));
		sb.append(StringPool.SLASH);
		sb.append(args[2]);

		System.out.println("Converting " + sb.toString() + " to HTML");

		File propertiesFile = new File(sb.toString());

		String propertiesString = StringPool.BLANK;

		try {
			FileUtil fileUtil = new FileUtil();

			fileUtil.setFile(new FileImpl());

			propertiesString = FileUtil.read(propertiesFile);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		String[] paragraphs = propertiesString.split("\n\n");

		List<Section> sections = new ArrayList<Section>();

		Section section = null;

		for (int i = 0; i < paragraphs.length; i++) {
			if (paragraphs[i].startsWith("##")) {
				String title = paragraphs[i].replace(
					StringPool.POUND, StringPool.BLANK);

				section = new Section(title.trim());

				sections.add(section);
			}
			else {
				String[] lines = paragraphs[i].split(StringPool.NEW_LINE);

				Map<String, String> properties = new HashMap<String, String>();

				for (String line : lines) {
					if (line.trim().contains(StringPool.EQUAL) &&
						!line.trim().startsWith("# ")) {

						int pos = line.indexOf(StringPool.EQUAL);

						String propertyKey = line.substring(0, pos);
						String propertyValue = line.substring(pos);

						if (!properties.containsKey(propertyKey)) {
							properties.put(propertyKey, propertyValue);
						}
					}
				}

				Property property = new Property(properties, paragraphs[i]);

				section.addProperty(property);
			}
		}

		// Populate the properties of every section

		for (Section curSection : sections) {
			for (Property property : curSection.getProperties()) {
				List<String> description = new ArrayList<String>();

				String descriptionLine = StringPool.BLANK;

				String content = property.getContent();

				String[] lines = content.trim().split(StringPool.NEW_LINE, 0);

				for (String line : lines) {
					line = line.trim();

					if (line.matches("#[\\s]+[^\\s].*")) {
						descriptionLine += line.substring(1);
					}
					else {
						if (Validator.isNotNull(descriptionLine)) {
							description.add(descriptionLine);

							descriptionLine = StringPool.BLANK;
						}
					}
				}

				property.setDescription(description);

				if (property.getProperties().isEmpty()) {
					continue;
				}

				String propertiesParagraph = content.substring(
					content.lastIndexOf("#\n") + 1);

				property.setPropertiesParagraph(propertiesParagraph);
			}
		}

		root.put("sections", sections);

		System.out.println("Writing " + sb.toString() + ".html");

		try {
			Configuration configuration = new Configuration();

			File file = new File(
				System.getProperty("user.dir") +
					"/src/com/liferay/portal/tools/propertiesconverter/" +
						"dependencies");

			try {
				configuration.setDirectoryForTemplateLoading(file);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}

			configuration.setObjectWrapper(new DefaultObjectWrapper());

			Template template = configuration.getTemplate("properties.ftl");

			File propertiesHTMLFile = new File(sb.toString() + ".html");

			Writer writer = new FileWriter(propertiesHTMLFile);

			try {
				template.process(root, writer);
			}
			catch (TemplateException te) {
				te.printStackTrace();
			}

			writer.flush();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}