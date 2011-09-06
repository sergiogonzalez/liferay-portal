/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.engines.mediawiki;

import java.util.List;

import org.jamwiki.SearchEngine;
import org.jamwiki.model.SearchResultEntry;
import org.jamwiki.model.Topic;

/**
 * @author Jonathan Potter
 */
public class LiferaySearchEngine implements SearchEngine {

	public void addToIndex(Topic arg0) {
	}

	public void addToIndex(Topic topic, List<String> links) {
	}

	public void commit(String arg0) {
	}

	public void deleteFromIndex(Topic topic) {
	}

	public List<SearchResultEntry> findLinkedTo(
		String virtualWiki, String topicName) {

		return null;
	}

	public List<SearchResultEntry> findResults(
		String virtualWiki, String text) {

		return null;
	}

	public void refreshIndex() {
	}

	public void setAutoCommit(boolean autoCommit) {
	}

	public void shutdown() {
	}

	public void updateInIndex(Topic topic) {
	}

	public void updateInIndex(Topic topic, List<String> links) {
	}

}