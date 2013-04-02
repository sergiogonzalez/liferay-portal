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

package com.liferay.portal.service;

import com.liferay.portal.asset.LayoutRevisionAssetRendererFactory;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.User;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.blogs.asset.BlogsEntryAssetRendererFactory;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.trash.BlogsEntryTrashHandler;
import com.liferay.portlet.blogs.util.BlogsIndexer;
import com.liferay.portlet.blogs.workflow.BlogsEntryWorkflowHandler;
import com.liferay.portlet.bookmarks.asset.BookmarksEntryAssetRendererFactory;
import com.liferay.portlet.bookmarks.asset.BookmarksFolderAssetRendererFactory;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.util.BookmarksEntryIndexer;
import com.liferay.portlet.bookmarks.util.BookmarksFolderIndexer;
import com.liferay.portlet.calendar.asset.CalEventAssetRendererFactory;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.directory.asset.UserAssetRendererFactory;
import com.liferay.portlet.directory.workflow.UserWorkflowHandler;
import com.liferay.portlet.documentlibrary.asset.DLFileEntryAssetRendererFactory;
import com.liferay.portlet.documentlibrary.asset.DLFolderAssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.trash.DLFileEntryTrashHandler;
import com.liferay.portlet.documentlibrary.trash.DLFileShortcutTrashHandler;
import com.liferay.portlet.documentlibrary.trash.DLFolderTrashHandler;
import com.liferay.portlet.documentlibrary.util.DLFileEntryIndexer;
import com.liferay.portlet.documentlibrary.util.DLFolderIndexer;
import com.liferay.portlet.documentlibrary.workflow.DLFileEntryWorkflowHandler;
import com.liferay.portlet.dynamicdatalists.asset.DDLRecordAssetRendererFactory;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.journal.asset.JournalArticleAssetRendererFactory;
import com.liferay.portlet.journal.asset.JournalFolderAssetRendererFactory;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.trash.JournalArticleTrashHandler;
import com.liferay.portlet.journal.util.JournalArticleIndexer;
import com.liferay.portlet.journal.util.JournalFolderIndexer;
import com.liferay.portlet.journal.workflow.JournalArticleWorkflowHandler;
import com.liferay.portlet.messageboards.asset.MBCategoryAssetRendererFactory;
import com.liferay.portlet.messageboards.asset.MBDiscussionAssetRendererFactory;
import com.liferay.portlet.messageboards.asset.MBMessageAssetRendererFactory;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.trash.MBCategoryTrashHandler;
import com.liferay.portlet.messageboards.trash.MBThreadTrashHandler;
import com.liferay.portlet.messageboards.util.MBMessageIndexer;
import com.liferay.portlet.messageboards.workflow.MBDiscussionWorkflowHandler;
import com.liferay.portlet.messageboards.workflow.MBMessageWorkflowHandler;
import com.liferay.portlet.trash.util.TrashIndexer;
import com.liferay.portlet.usersadmin.util.ContactIndexer;
import com.liferay.portlet.usersadmin.util.OrganizationIndexer;
import com.liferay.portlet.usersadmin.util.UserIndexer;
import com.liferay.portlet.wiki.asset.WikiPageAssetRendererFactory;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.trash.WikiNodeTrashHandler;
import com.liferay.portlet.wiki.trash.WikiPageTrashHandler;
import com.liferay.portlet.wiki.util.WikiNodeIndexer;
import com.liferay.portlet.wiki.util.WikiPageIndexer;
import com.liferay.portlet.wiki.workflow.WikiPageWorkflowHandler;

/**
 * @author Roberto Díaz
 */
public class PortalRegisterTestUtil {

	protected static void registerAssetRendererFactories() {
		for (Object[] array : _ASSET_RENDERER_FACTORY_CLASSES) {
			try {
				Class<?> factoryClass = (Class<?>)array[0];

				AssetRendererFactory assetRendererFactory =
					(AssetRendererFactory)factoryClass.newInstance();

				Class<?> entityClass = (Class<?>)array[1];

				assetRendererFactory.setClassName(entityClass.getName());

				AssetRendererFactoryRegistryUtil.register(assetRendererFactory);
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	protected static void registerIndexers() {
		IndexerRegistryUtil.register(new BlogsIndexer());
		IndexerRegistryUtil.register(new ContactIndexer());
		IndexerRegistryUtil.register(new BookmarksEntryIndexer());
		IndexerRegistryUtil.register(new BookmarksFolderIndexer());
		IndexerRegistryUtil.register(new DLFileEntryIndexer());
		IndexerRegistryUtil.register(new DLFolderIndexer());
		IndexerRegistryUtil.register(new JournalArticleIndexer());
		IndexerRegistryUtil.register(new JournalFolderIndexer());
		IndexerRegistryUtil.register(new MBMessageIndexer());
		IndexerRegistryUtil.register(new OrganizationIndexer());
		IndexerRegistryUtil.register(new TrashIndexer());
		IndexerRegistryUtil.register(new UserIndexer());
		IndexerRegistryUtil.register(new WikiNodeIndexer());
		IndexerRegistryUtil.register(new WikiPageIndexer());
	}

	protected static void registerTrashHandlers() {
		TrashHandlerRegistryUtil.register(new BlogsEntryTrashHandler());
		TrashHandlerRegistryUtil.register(new DLFileEntryTrashHandler());
		TrashHandlerRegistryUtil.register(new DLFileShortcutTrashHandler());
		TrashHandlerRegistryUtil.register(new DLFolderTrashHandler());
		TrashHandlerRegistryUtil.register(new JournalArticleTrashHandler());
		TrashHandlerRegistryUtil.register(new MBCategoryTrashHandler());
		TrashHandlerRegistryUtil.register(new MBThreadTrashHandler());
		TrashHandlerRegistryUtil.register(new WikiNodeTrashHandler());
		TrashHandlerRegistryUtil.register(new WikiPageTrashHandler());
	}

	protected static void registerWorkflowHandlers() {
		WorkflowHandlerRegistryUtil.register(new BlogsEntryWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new DLFileEntryWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(
			new JournalArticleWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new MBDiscussionWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new MBMessageWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new UserWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new WikiPageWorkflowHandler());
	}

	private static final Class[][] _ASSET_RENDERER_FACTORY_CLASSES = {
		{BlogsEntryAssetRendererFactory.class, BlogsEntry.class},
		{BookmarksEntryAssetRendererFactory.class, BookmarksEntry.class},
		{BookmarksFolderAssetRendererFactory.class, BookmarksFolder.class},
		{CalEventAssetRendererFactory.class, CalEvent.class},
		{DDLRecordAssetRendererFactory.class, DDLRecord.class},
		{DLFileEntryAssetRendererFactory.class, DLFileEntry.class},
		{DLFolderAssetRendererFactory.class, DLFolder.class},
		{JournalArticleAssetRendererFactory.class, JournalArticle.class},
		{JournalFolderAssetRendererFactory.class, JournalFolder.class},
		{LayoutRevisionAssetRendererFactory.class, LayoutRevision.class},
		{MBCategoryAssetRendererFactory.class, MBCategory.class},
		{MBDiscussionAssetRendererFactory.class, MBDiscussion.class},
		{MBMessageAssetRendererFactory.class, MBMessage.class},
		{UserAssetRendererFactory.class, User.class},
		{WikiPageAssetRendererFactory.class, WikiPage.class}
	};

}