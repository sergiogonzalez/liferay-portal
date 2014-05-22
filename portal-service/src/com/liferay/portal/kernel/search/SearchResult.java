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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portlet.messageboards.model.MBMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class SearchResult {

	public SearchResult(SearchResultKey key) {
		_key = key;
	}

	public void addFileEntry(FileEntry fileEntry, Summary summary) {
		Tuple tuple = new Tuple(fileEntry, summary);

		_fileEntryTuples.add(tuple);
	}

	public void addMBMessage(MBMessage mbMessage) {
		_mbMessages.add(mbMessage);
	}

	public void addVersion(String version) {
		_versions.add(version);
	}

	public String getClassName() {
		return _key.getClassName();
	}

	public long getClassPK() {
		return _key.getClassPK();
	}

	public List<Tuple> getFileEntryTuples() {
		return _fileEntryTuples;
	}

	public List<MBMessage> getMBMessages() {
		return _mbMessages;
	}

	public Summary getSummary() {
		return _summary;
	}

	public List<String> getVersions() {
		return _versions;
	}

	public void setMessages(List<MBMessage> mbMessages) {
		_mbMessages = mbMessages;
	}

	public void setSummary(Summary summary) {
		_summary = summary;
	}

	private List<Tuple> _fileEntryTuples = new ArrayList<Tuple>();
	private final SearchResultKey _key;
	private List<MBMessage> _mbMessages = new ArrayList<MBMessage>();
	private Summary _summary;
	private List<String> _versions = new ArrayList<String>();

}