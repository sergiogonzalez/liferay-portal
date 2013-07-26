/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.samplesqlbuilder;

import com.liferay.portal.dao.db.MySQLDB;
import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.io.CharPipe;
import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncTeeWriter;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.InitUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.nio.channels.FileChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SampleSQLBuilder {

	public static void main(String[] args) {
		List<String> extraConfigLocations = new ArrayList<String>();

		extraConfigLocations.add("META-INF/portlet-container-spring.xml");

		InitUtil.initWithSpring(false, extraConfigLocations);

		Reader reader = null;

		try {
			Properties properties = new SortedProperties();

			reader = new FileReader(args[0]);

			properties.load(reader);

			new SampleSQLBuilder(properties);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}

	public SampleSQLBuilder(Properties properties) throws Exception {
		_dbType = properties.getProperty("sample.sql.db.type");

		_maxAssetCategoryCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.asset.category.count"));
		_maxAssetEntryToAssetCategoryCount = GetterUtil.getInteger(
			properties.getProperty(
				"sample.sql.max.asset.entry.to.asset.category.count"));
		_maxAssetEntryToAssetTagCount = GetterUtil.getInteger(
			properties.getProperty(
				"sample.sql.max.asset.entry.to.asset.tag.count"));
		_maxAssetPublisherFilterRuleCount = GetterUtil.getInteger(
			properties.getProperty(
				"sample.sql.max.asset.publisher.filter.rule.count"));
		_maxAssetPublisherPageCount = GetterUtil.getInteger(
			properties.getProperty(
				"sample.sql.max.asset.publisher.page.count"));
		_maxAssetTagCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.asset.tag.count"));
		_maxAssetVocabularyCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.asset.vocabulary.count"));
		_maxBlogsEntryCommentCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.blogs.entry.comment.count"));
		_maxBlogsEntryCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.blogs.entry.count"));
		_maxDDLCustomFieldCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.ddl.custom.field.count"));
		_maxDDLRecordCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.ddl.record.count"));
		_maxDDLRecordSetCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.ddl.record.set.count"));
		_maxDLFileEntryCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.dl.file.entry.count"));
		_maxDLFileEntrySize = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.dl.file.entry.size"));
		_maxDLFolderCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.dl.folder.count"));
		_maxDLFolderDepth = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.dl.folder.depth"));
		_maxGroupCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.group.count"));
		_maxJournalArticleCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.journal.article.count"));
		_maxJournalArticlePageCount = GetterUtil.getInteger(
			properties.getProperty(
				"sample.sql.max.journal.article.page.count"));
		_maxJournalArticleSize = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.journal.article.size"));
		_maxJournalArticleVersionCount = GetterUtil.getInteger(
			properties.getProperty(
				"sample.sql.max.journal.article.version.count"));
		_maxMBCategoryCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.mb.category.count"));
		_maxMBMessageCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.mb.message.count"));
		_maxMBThreadCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.mb.thread.count"));
		_maxUserCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.user.count"));
		_maxUserToGroupCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.user.to.group.count"));
		_maxWikiNodeCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.wiki.node.count"));
		_maxWikiPageCommentCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.wiki.page.comment.count"));
		_maxWikiPageCount = GetterUtil.getInteger(
			properties.getProperty("sample.sql.max.wiki.page.count"));
		_optimizeBufferSize = GetterUtil.getInteger(
			properties.getProperty("sample.sql.optimize.buffer.size"));
		_outputDir = properties.getProperty("sample.sql.output.dir");
		_outputMerge = GetterUtil.getBoolean(
			properties.getProperty("sample.sql.output.merge"));

		_dataFactory = new DataFactory(
			_maxAssetCategoryCount, _maxAssetEntryToAssetCategoryCount,
			_maxAssetEntryToAssetTagCount, _maxAssetPublisherFilterRuleCount,
			_maxAssetPublisherPageCount, _maxAssetTagCount,
			_maxAssetVocabularyCount, _maxBlogsEntryCount,
			_maxDDLCustomFieldCount, _maxGroupCount, _maxJournalArticleCount,
			_maxJournalArticleSize, _maxMBCategoryCount, _maxMBThreadCount,
			_maxMBMessageCount, _maxUserCount, _maxUserToGroupCount,
			_maxWikiNodeCount, _maxWikiPageCount);

		_db = DBFactoryUtil.getDB(_dbType);

		if (_db instanceof MySQLDB) {
			_db = new SampleMySQLDB();
		}

		// Clean up previous output

		FileUtil.delete(_outputDir + "/sample-" + _dbType + ".sql");
		FileUtil.deltree(_outputDir + "/output");

		// Generic

		_tempDir = new File(_outputDir, "temp");

		_tempDir.mkdirs();

		final CharPipe charPipe = new CharPipe(_PIPE_BUFFER_SIZE);

		generateSQL(charPipe);

		try {

			// Specific

			compressSQL(charPipe.getReader());

			// Merge

			mergeSQL();
		}
		finally {
			FileUtil.deltree(_tempDir);
		}

		StringBundler sb = new StringBundler();

		for (String key : properties.stringPropertyNames()) {
			if (!key.startsWith("sample.sql")) {
				continue;
			}

			String value = properties.getProperty(key);

			sb.append(key);
			sb.append(StringPool.EQUAL);
			sb.append(value);
			sb.append(StringPool.NEW_LINE);
		}

		FileUtil.write(
			new File(_outputDir, "benchmarks-actual.properties"),
			sb.toString());
	}

	protected void compressInsertSQL(String insertSQL) throws IOException {
		String tableName = insertSQL.substring(0, insertSQL.indexOf(' '));

		int pos = insertSQL.indexOf(" values ") + 8;

		String values = insertSQL.substring(pos, insertSQL.length() - 1);

		StringBundler sb = _insertSQLs.get(tableName);

		if ((sb == null) || (sb.index() == 0)) {
			sb = new StringBundler();

			_insertSQLs.put(tableName, sb);

			sb.append("insert into ");
			sb.append(insertSQL.substring(0, pos));
			sb.append("\n");
		}
		else {
			sb.append(",\n");
		}

		sb.append(values);

		if (sb.index() >= _optimizeBufferSize) {
			sb.append(";\n");

			String sql = _db.buildSQL(sb.toString());

			sb.setIndex(0);

			writeToInsertSQLFile(tableName, sql);
		}
	}

	protected void compressSQL(Reader reader) throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			reader);

		String s = null;

		while ((s = unsyncBufferedReader.readLine()) != null) {
			s = s.trim();

			if (s.length() > 0) {
				if (s.startsWith("insert into ")) {
					compressInsertSQL(s.substring(12));
				}
				else if (s.length() > 0) {
					_otherSQLs.add(s);
				}
			}
		}

		unsyncBufferedReader.close();
	}

	protected Writer createFileWriter(File file) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);

		Writer writer = new OutputStreamWriter(fileOutputStream);

		return createUnsyncBufferedWriter(writer);
	}

	protected Writer createFileWriter(String fileName) throws IOException {
		File file = new File(fileName);

		return createFileWriter(file);
	}

	protected Writer createUnsyncBufferedWriter(Writer writer) {
		return new UnsyncBufferedWriter(writer, _WRITER_BUFFER_SIZE) {

			@Override
			public void flush() {

				// Disable FreeMarker from flushing

			}

		};
	}

	protected void generateSQL(final CharPipe charPipe) {
		final Writer writer = createUnsyncBufferedWriter(charPipe.getWriter());

		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					_writerSampleSQL = new UnsyncTeeWriter(
						writer, createFileWriter(_outputDir + "/sample.sql"));

					Map<String, Object> context = getContext();

					processTemplate(_tplSample, context);

					for (String fileName : _CSV_FILE_NAMES) {
						Writer writer = (Writer)context.get(
							fileName + "CSVWriter");

						writer.close();
					}

					_writerSampleSQL.close();

					charPipe.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

		};

		thread.start();
	}

	protected Map<String, Object> getContext() throws Exception {
		Map<String, Object> context = new HashMap<String, Object>();

		put(context, "counter", _dataFactory.getCounter());
		put(context, "dataFactory", _dataFactory);
		put(context, "maxAssetPublisherPageCount", _maxAssetPublisherPageCount);
		put(context, "maxDLFileEntrySize", _maxDLFileEntrySize);
		put(context, "maxBlogsEntryCommentCount", _maxBlogsEntryCommentCount);
		put(context, "maxBlogsEntryCount", _maxBlogsEntryCount);
		put(context, "maxDDLRecordCount", _maxDDLRecordCount);
		put(context, "maxDDLRecordSetCount", _maxDDLRecordSetCount);
		put(context, "maxDLFileEntryCount", _maxDLFileEntryCount);
		put(context, "maxDLFolderCount", _maxDLFolderCount);
		put(context, "maxDLFolderDepth", _maxDLFolderDepth);
		put(context, "maxGroupCount", _maxGroupCount);
		put(context, "maxJournalArticleCount", _maxJournalArticleCount);
		put(context, "maxJournalArticlePageCount", _maxJournalArticlePageCount);
		put(
			context, "maxJournalArticleVersionCount",
			_maxJournalArticleVersionCount);
		put(context, "maxMBCategoryCount", _maxMBCategoryCount);
		put(context, "maxMBMessageCount", _maxMBMessageCount);
		put(context, "maxMBThreadCount", _maxMBThreadCount);
		put(context, "maxUserCount", _maxUserCount);
		put(context, "maxUserToGroupCount", _maxUserToGroupCount);
		put(context, "maxWikiNodeCount", _maxWikiNodeCount);
		put(context, "maxWikiPageCommentCount", _maxWikiPageCommentCount);
		put(context, "maxWikiPageCount", _maxWikiPageCount);

		for (String fileName : _CSV_FILE_NAMES) {
			Writer writer = createFileWriter(
				new File(_outputDir, fileName + ".csv"));

			put(context, fileName + "CSVWriter", writer);
		}

		return context;
	}

	protected File getInsertSQLFile(String tableName) {
		return new File(_tempDir, tableName + ".sql");
	}

	protected void mergeSQL() throws IOException {
		File outputFile = new File(_outputDir + "/sample-" + _dbType + ".sql");

		FileOutputStream fileOutputStream = null;
		FileChannel fileChannel = null;

		if (_outputMerge) {
			fileOutputStream = new FileOutputStream(outputFile);
			fileChannel = fileOutputStream.getChannel();
		}

		Set<Map.Entry<String, StringBundler>> insertSQLs =
			_insertSQLs.entrySet();

		for (Map.Entry<String, StringBundler> entry : insertSQLs) {
			String tableName = entry.getKey();

			String sql = _db.buildSQL(entry.getValue().toString());

			writeToInsertSQLFile(tableName, sql);

			Writer insertSQLWriter = _insertSQLWriters.remove(tableName);

			insertSQLWriter.write(";\n");

			insertSQLWriter.close();

			if (_outputMerge) {
				File insertSQLFile = getInsertSQLFile(tableName);

				FileInputStream insertSQLFileInputStream = new FileInputStream(
					insertSQLFile);

				FileChannel insertSQLFileChannel =
					insertSQLFileInputStream.getChannel();

				insertSQLFileChannel.transferTo(
					0, insertSQLFileChannel.size(), fileChannel);

				insertSQLFileChannel.close();

				insertSQLFile.delete();
			}
		}

		Writer writer = null;

		if (_outputMerge) {
			writer = new OutputStreamWriter(fileOutputStream);
		}
		else {
			writer = new FileWriter(getInsertSQLFile("others"));
		}

		for (String sql : _otherSQLs) {
			sql = _db.buildSQL(sql);

			writer.write(sql);
			writer.write(StringPool.NEW_LINE);
		}

		writer.close();

		File outputFolder = new File(_outputDir, "output");

		if (!_outputMerge && !_tempDir.renameTo(outputFolder)) {

			// This will only happen when temp and output folders are on
			// different file systems

			FileUtil.copyDirectory(_tempDir, outputFolder);
		}
	}

	protected void processTemplate(String name, Map<String, Object> context)
		throws Exception {

		FreeMarkerUtil.process(name, context, _writerSampleSQL);
	}

	protected void put(Map<String, Object> context, String key, Object value) {
		context.put(key, value);
	}

	protected void writeToInsertSQLFile(String tableName, String sql)
		throws IOException {

		Writer writer = _insertSQLWriters.get(tableName);

		if (writer == null) {
			File file = getInsertSQLFile(tableName);

			writer = createFileWriter(file);

			_insertSQLWriters.put(tableName, writer);
		}

		writer.write(sql);
	}

	private static final String[] _CSV_FILE_NAMES = {
		"assetPublisher", "blog", "company", "documentLibrary",
		"dynamicDataList", "layout", "messageBoard", "repository", "wiki"
	};

	private static final int _PIPE_BUFFER_SIZE = 16 * 1024 * 1024;

	private static final String _TPL_ROOT =
		"com/liferay/portal/tools/samplesqlbuilder/dependencies/";

	private static final int _WRITER_BUFFER_SIZE = 16 * 1024;

	private DataFactory _dataFactory;
	private DB _db;
	private String _dbType;
	private Map<String, StringBundler> _insertSQLs =
		new ConcurrentHashMap<String, StringBundler>();
	private Map<String, Writer> _insertSQLWriters =
		new ConcurrentHashMap<String, Writer>();
	private int _maxAssetCategoryCount;
	private int _maxAssetEntryToAssetCategoryCount;
	private int _maxAssetEntryToAssetTagCount;
	private int _maxAssetPublisherFilterRuleCount;
	private int _maxAssetPublisherPageCount;
	private int _maxAssetTagCount;
	private int _maxAssetVocabularyCount;
	private int _maxBlogsEntryCommentCount;
	private int _maxBlogsEntryCount;
	private int _maxDDLCustomFieldCount;
	private int _maxDDLRecordCount;
	private int _maxDDLRecordSetCount;
	private int _maxDLFileEntryCount;
	private int _maxDLFileEntrySize;
	private int _maxDLFolderCount;
	private int _maxDLFolderDepth;
	private int _maxGroupCount;
	private int _maxJournalArticleCount;
	private int _maxJournalArticlePageCount;
	private int _maxJournalArticleSize;
	private int _maxJournalArticleVersionCount;
	private int _maxMBCategoryCount;
	private int _maxMBMessageCount;
	private int _maxMBThreadCount;
	private int _maxUserCount;
	private int _maxUserToGroupCount;
	private int _maxWikiNodeCount;
	private int _maxWikiPageCommentCount;
	private int _maxWikiPageCount;
	private int _optimizeBufferSize;
	private List<String> _otherSQLs = new ArrayList<String>();
	private String _outputDir;
	private boolean _outputMerge;
	private File _tempDir;
	private String _tplSample = _TPL_ROOT + "sample.ftl";
	private Writer _writerSampleSQL;

}