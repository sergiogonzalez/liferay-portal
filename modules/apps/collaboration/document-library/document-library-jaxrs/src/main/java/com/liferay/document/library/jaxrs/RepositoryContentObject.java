package com.liferay.document.library.jaxrs;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sergiogonzalez on 26/07/16.
 */
@XmlRootElement
public class RepositoryContentObject {

	public RepositoryContentObject() {
	}

	public RepositoryContentObject(
		long id, String title, String url,
		RepositoryContentType type) {
		_id = id;
		_title = title;
		_url = url;
		_type = type;
	}

	private long _id;
	private String _title;
	private String _url;
	private RepositoryContentType _type;

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public RepositoryContentType getType() {
		return _type;
	}

	public void setType(
		RepositoryContentType type) {
		_type = type;
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public enum RepositoryContentType {
		FILE, FOLDER, SHORTCUT
	}

	public static RepositoryContentObject createContentObject(Object object) {
		if (object instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry)object;

			return new RepositoryContentObject(
				fileEntry.getFileEntryId(), fileEntry.getTitle(), "",
				RepositoryContentType.FILE);
		}
		else if (object instanceof Folder) {
			Folder folder = (Folder)object;

			return new RepositoryContentObject(
				folder.getFolderId(), folder.getName(), "",
				RepositoryContentType.FOLDER);
		}
		else if (object instanceof FileShortcut) {
			FileShortcut fileShortcut = (FileShortcut)object;

			return new RepositoryContentObject(
				fileShortcut.getFileShortcutId(), fileShortcut.getToTitle(), "",
				RepositoryContentType.SHORTCUT);
		}
		else {
			throw new IllegalArgumentException(
				"Object must be an instance of FileEntry, Folder of FileShortcut");
		}
	}
}
