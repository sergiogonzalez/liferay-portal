
package com.liferay.portal.tools.propertiesconverter;

import java.util.ArrayList;
import java.util.List;

public class Section {

	public Section(String title) {
		_title = title;
	}
	
	public void addProperty(Property property) {
		_properties.add(property);
	}

	public String getTitle() {
		return _title;
	}
	
	public List<Property> getProperties() {
		return _properties;
	}

	public void setTitle(String title) {
		_title = title;
	}
	
	private String _title;

	private List<Property> _properties = new ArrayList<Property>();

}