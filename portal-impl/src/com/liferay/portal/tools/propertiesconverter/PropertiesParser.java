
package com.liferay.portal.tools.propertiesconverter;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ArgumentsUtil;
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

		// Create a data model for Freemarker

		Map root = new HashMap();
		
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);
		
		String pageTitle = arguments.get("properties.title");

		root.put("pageTitle", pageTitle);

		boolean toc = Boolean.parseBoolean(arguments.get("properties.toc"));

		root.put("toc", toc);

		String propertiesFilePath = arguments.get("properties.file.path");

		int pos = propertiesFilePath.lastIndexOf(StringPool.SLASH);
		
		String propertiesFileName = StringPool.BLANK;

		if (pos != -1) {
			propertiesFileName = propertiesFilePath.substring(pos + 1);
		}
		else {
			propertiesFileName = propertiesFilePath;
		}

		root.put("propertiesFileName", propertiesFileName);

		// Parse properties file and create sections and properties for the data
		// model

		StringBundler sb = new StringBundler(3);

		sb.append(System.getProperty("user.dir"));
		sb.append(StringPool.SLASH);
		sb.append(propertiesFilePath);

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

						int eqlPos = line.indexOf(StringPool.EQUAL);

						String propertyKey = line.substring(0, eqlPos);
						String propertyValue = line.substring(eqlPos);

						if (!properties.containsKey(propertyKey)) {
							properties.put(propertyKey, propertyValue);
						}
					}
				}

				Property property = new Property(properties, paragraphs[i]);

				section.addProperty(property);
			}
		}

		// Populate the properties of each section

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
		
		// Get the Freemarker template and merge it with the data model

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