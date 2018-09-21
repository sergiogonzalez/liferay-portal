/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.source.formatter.parser;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.JavaImportsFormatter;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaClassParser {

	public static List<JavaClass> parseAnonymousClasses(String content)
		throws IOException, ParseException {

		List<JavaClass> anonymousClasses = new ArrayList<>();

		Matcher matcher = _anonymousClassPattern.matcher(content);

		while (matcher.find()) {
			String s = content.substring(matcher.start(2), matcher.end());

			if (JavaSourceUtil.getLevel(s) != 0) {
				continue;
			}

			int x = matcher.start() + 1;
			int y = matcher.end();

			while (true) {
				String classContent = content.substring(x, y);

				if (JavaSourceUtil.getLevel(classContent, "{", "}") != 0) {
					y++;

					continue;
				}

				int lineNumber = SourceUtil.getLineNumber(
					content, matcher.start(2));

				anonymousClasses.add(
					_parseJavaClass(
						StringPool.BLANK, classContent, lineNumber,
						JavaTerm.ACCESS_MODIFIER_PRIVATE, false, false, false,
						false, true));

				break;
			}
		}

		return anonymousClasses;
	}

	public static JavaClass parseJavaClass(String fileName, String content)
		throws IOException, ParseException {

		String className = JavaSourceUtil.getClassName(fileName);

		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"\n(public\\s+)?(abstract\\s+)?(final\\s+)?@?",
				"(class|enum|interface)\\s+", className,
				"([<|\\s][^\\{]*)\\{"));

		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			throw new ParseException("Parsing error");
		}

		int x = content.lastIndexOf("\n\n", matcher.start() + 1);

		int lineNumber = SourceUtil.getLineNumber(content, x + 2);

		String classContent = content.substring(x + 2);

		boolean isAbstract = false;

		if (matcher.group(2) != null) {
			isAbstract = true;
		}

		boolean isEnum = false;
		boolean isInterface = false;

		if (matcher.group(4) != null) {
			String token = matcher.group(4);

			if (token.equals("enum")) {
				isEnum = true;
			}
			else if (token.equals("interface")) {
				isInterface = true;
			}
		}

		JavaClass javaClass = _parseJavaClass(
			className, classContent, lineNumber,
			JavaTerm.ACCESS_MODIFIER_PUBLIC, isAbstract, false, isEnum,
			isInterface, false);

		javaClass.setPackageName(JavaSourceUtil.getPackageName(content));

		String[] importLines = StringUtil.splitLines(
			JavaImportsFormatter.getImports(content));

		for (String importLine : importLines) {
			if (Validator.isNotNull(importLine)) {
				javaClass.addImport(
					importLine.substring(7, importLine.length() - 1));
			}
		}

		return _parseExtendsImplements(
			javaClass, StringUtil.trim(matcher.group(5)));
	}

	private static String _getClassName(String line) {
		int pos = line.indexOf(" extends ");

		if (pos == -1) {
			pos = line.indexOf(" implements ");
		}

		if (pos == -1) {
			pos = line.indexOf(CharPool.OPEN_CURLY_BRACE);
		}

		if (pos != -1) {
			line = line.substring(0, pos);
		}

		pos = line.indexOf(CharPool.LESS_THAN);

		if (pos != -1) {
			line = line.substring(0, pos);
		}

		line = line.trim();

		pos = line.lastIndexOf(CharPool.SPACE);

		return line.substring(pos + 1);
	}

	private static int _getCommentEndLineNumber(
		String classContent, int lineNumber) {

		while (true) {
			String line = SourceUtil.getLine(classContent, lineNumber);

			if (line.endsWith("*/")) {
				return lineNumber;
			}

			lineNumber++;
		}
	}

	private static String _getConstructorOrMethodName(String line, int pos) {
		line = line.substring(0, pos);

		int x = line.lastIndexOf(CharPool.SPACE);

		return line.substring(x + 1);
	}

	private static JavaTerm _getJavaTerm(
			String metadata, String javaTermContent, int lineNumber)
		throws IOException, ParseException {

		Matcher matcher = _javaTermStartLinePattern.matcher(javaTermContent);

		if (!matcher.find()) {
			return null;
		}

		String startLine = StringUtil.trim(matcher.group());

		startLine = StringUtil.replace(
			startLine, new String[] {"\t", "(\n", "\n", " synchronized "},
			new String[] {"", "(", " ", " "});

		javaTermContent = metadata + javaTermContent;

		if (startLine.startsWith("static {")) {
			return new JavaStaticBlock(javaTermContent, lineNumber);
		}

		String accessModifier = JavaTerm.ACCESS_MODIFIER_DEFAULT;

		for (String curAccessModifier : JavaTerm.ACCESS_MODIFIERS) {
			if (startLine.startsWith(curAccessModifier)) {
				accessModifier = curAccessModifier;

				break;
			}
		}

		boolean isAbstract = startLine.contains(" abstract ");
		boolean isEnum = startLine.contains(" enum ");
		boolean isInterface = startLine.contains(" interface ");
		boolean isStatic = startLine.contains(" static ");

		int x = startLine.indexOf(CharPool.EQUAL);
		int y = startLine.indexOf(CharPool.OPEN_PARENTHESIS);

		if (startLine.contains(" @interface ") ||
			startLine.contains(" class ") || startLine.contains(" enum ") ||
			startLine.contains(" interface ")) {

			return _parseJavaClass(
				_getClassName(startLine), javaTermContent, lineNumber,
				accessModifier, isAbstract, isStatic, isEnum, isInterface,
				false);
		}

		if (((x > 0) && ((y == -1) || (y > x))) ||
			(startLine.endsWith(StringPool.SEMICOLON) && (y == -1))) {

			return new JavaVariable(
				_getVariableName(startLine), javaTermContent, accessModifier,
				lineNumber, isAbstract, isStatic);
		}

		if (y == -1) {
			return null;
		}

		int spaceCount = StringUtil.count(
			startLine.substring(0, y), CharPool.SPACE);

		if (isStatic || (spaceCount > 1) ||
			(accessModifier.equals(JavaTerm.ACCESS_MODIFIER_DEFAULT) &&
			 (spaceCount > 0))) {

			return new JavaMethod(
				_getConstructorOrMethodName(startLine, y), javaTermContent,
				accessModifier, lineNumber, isAbstract, isStatic);
		}

		if ((spaceCount == 1) ||
			(accessModifier.equals(JavaTerm.ACCESS_MODIFIER_DEFAULT) &&
			 (spaceCount == 0))) {

			return new JavaConstructor(
				_getConstructorOrMethodName(startLine, y), javaTermContent,
				accessModifier, lineNumber, isAbstract, isStatic);
		}

		return null;
	}

	private static int _getJavaTermEndLineNumber(
		String classContent, int lineNumber) {

		int x = SourceUtil.getLineStartPos(classContent, lineNumber);

		String s = classContent.substring(x);

		Matcher matcher = _javaTermEndPattern.matcher(s);

		while (matcher.find()) {
			String javaTermContent = s.substring(0, matcher.end());

			if (SourceUtil.getLevel(javaTermContent, "{", "}") == 0) {
				return lineNumber + StringUtil.count(javaTermContent, "\n") - 1;
			}
		}

		return -1;
	}

	private static int _getMatchingEndLineNumber(
		String classContent, int lineNumber, String increaseLevelString,
		String decreaseLevelString) {

		int level = 0;

		while (true) {
			String line = SourceUtil.getLine(classContent, lineNumber);

			level += SourceUtil.getLevel(
				line, increaseLevelString, decreaseLevelString);

			if (level == 0) {
				return lineNumber;
			}

			lineNumber++;
		}
	}

	private static String _getVariableName(String line) {
		int x = line.indexOf(CharPool.EQUAL);
		int y = line.lastIndexOf(CharPool.SPACE);

		if (x != -1) {
			line = line.substring(0, x);
			line = StringUtil.trim(line);

			y = line.lastIndexOf(CharPool.SPACE);

			return line.substring(y + 1);
		}

		if (line.endsWith(StringPool.SEMICOLON)) {
			return line.substring(y + 1, line.length() - 1);
		}

		return StringPool.BLANK;
	}

	private static JavaClass _parseExtendsImplements(
			JavaClass javaClass, String s)
		throws ParseException {

		if (SourceUtil.getLevel(s, "<", ">") != 0) {
			throw new ParseException("Parsing error around class declaration");
		}

		outerLoop:
		while (true) {
			int x = s.indexOf("<");

			if (x == -1) {
				break;
			}

			int y = x;

			while (true) {
				y = s.indexOf(">", y + 1);

				if (SourceUtil.getLevel(s.substring(x, y + 1), "<", ">") == 0) {
					s = StringUtil.trim(s.substring(0, x) + s.substring(y + 1));

					continue outerLoop;
				}
			}
		}

		Matcher matcher = _implementsPattern.matcher(s);

		if (matcher.find()) {
			javaClass.addImplementedClassNames(
				StringUtil.split(s.substring(matcher.end())));

			s = StringUtil.trim(s.substring(0, matcher.start()));
		}

		if (s.startsWith("extends")) {
			javaClass.addExtendedClassNames(StringUtil.split(s.substring(7)));
		}

		return javaClass;
	}

	private static JavaClass _parseJavaClass(
			String className, String classContent, int classLineNumber,
			String accessModifier, boolean isAbstract, boolean isStatic,
			boolean isEnum, boolean isInterface, boolean anonymous)
		throws IOException, ParseException {

		JavaClass javaClass = new JavaClass(
			className, classContent, accessModifier, classLineNumber,
			isAbstract, isStatic, isInterface, anonymous);

		int lineNumber = 0;

		int annotationLevel = 0;
		int level = 0;

		while (true) {
			String line = SourceUtil.getLine(classContent, ++lineNumber);

			annotationLevel += SourceUtil.getLevel(line);
			level += SourceUtil.getLevel(line, "{", "}");

			if ((annotationLevel == 0) && (level != 0)) {
				break;
			}
		}

		if (isEnum) {
			while (true) {
				String line = StringUtil.trim(
					SourceUtil.getLine(classContent, ++lineNumber));

				level += SourceUtil.getLevel(line, "{", "}");

				if (level == 0) {
					return javaClass;
				}

				if (line.endsWith(";") && (level == 1)) {
					break;
				}
			}
		}

		int javaTermLineNumber = -1;

		while (true) {
			String line = StringUtil.trim(
				SourceUtil.getLine(classContent, ++lineNumber));

			if ((line == null) || line.equals("}")) {
				return javaClass;
			}

			if (line.equals(StringPool.BLANK) || line.startsWith("//")) {
				continue;
			}

			if (line.equals("/*") || line.matches("/\\*[^*].*")) {
				lineNumber = _getCommentEndLineNumber(classContent, lineNumber);

				continue;
			}

			if (line.equals("{")) {
				lineNumber = _getMatchingEndLineNumber(
					classContent, lineNumber, "{", "}");

				continue;
			}

			if (javaTermLineNumber == -1) {
				javaTermLineNumber = lineNumber;
			}

			if (line.startsWith("@")) {
				lineNumber = _getMatchingEndLineNumber(
					classContent, lineNumber, "(", ")");
			}
			else if (line.startsWith("/**")) {
				lineNumber = _getCommentEndLineNumber(classContent, lineNumber);
			}
			else {
				int x = SourceUtil.getLineStartPos(
					classContent, javaTermLineNumber);
				int y = SourceUtil.getLineStartPos(classContent, lineNumber);

				String metadata = classContent.substring(x, y);

				int javaTermEndLineNumber = _getJavaTermEndLineNumber(
					classContent, lineNumber);

				if (javaTermEndLineNumber == -1) {
					throw new ParseException(
						"Parsing error at line '" + StringUtil.trim(line) +
							"'");
				}

				int z = SourceUtil.getLineStartPos(
					classContent, javaTermEndLineNumber + 1);

				String javaTermContent = classContent.substring(y, z);

				JavaTerm javaTerm = _getJavaTerm(
					metadata, javaTermContent,
					classLineNumber + javaTermLineNumber - 1);

				if (javaTerm == null) {
					throw new ParseException(
						"Parsing error at line '" + StringUtil.trim(line) +
							"'");
				}

				javaClass.addChildJavaTerm(javaTerm);

				javaTermLineNumber = -1;
				lineNumber = javaTermEndLineNumber;
			}
		}
	}

	private static final Pattern _anonymousClassPattern = Pattern.compile(
		"\n\t+(\\S.* )?new ((.|\\(\n|\\w\n)*\\)) \\{\n\n");
	private static final Pattern _implementsPattern = Pattern.compile(
		"(\\A|\\s)implements\\s");
	private static final Pattern _javaTermEndPattern = Pattern.compile(
		"[;}]\\s*?\n");
	private static final Pattern _javaTermStartLinePattern = Pattern.compile(
		".*?[{;]\\s*?\n", Pattern.DOTALL);

}