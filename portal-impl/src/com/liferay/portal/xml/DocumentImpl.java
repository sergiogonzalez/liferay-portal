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

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentType;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Visitor;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentImpl extends BranchImpl implements Document {

	public DocumentImpl(org.dom4j.Document document) {
		super(document);

		_document = document;
	}

	@Override
	public <T, V extends Visitor<T>> T accept(V visitor) {
		return visitor.visitDocument(this);
	}

	public Document addComment(String comment) {
		_document.addComment(comment);

		return this;
	}

	public Document addDocumentType(
		String name, String publicId, String systemId) {

		_document.addDocType(name, publicId, systemId);

		return this;
	}

	@Override
	public boolean equals(Object obj) {
		org.dom4j.Document document = ((DocumentImpl)obj).getWrappedDocument();

		return _document.equals(document);
	}

	@Override
	public String formattedString() throws IOException {
		return XMLFormatter.toString(_document);
	}

	@Override
	public String formattedString(String indent) throws IOException {
		return XMLFormatter.toString(_document, indent);
	}

	@Override
	public String formattedString(String indent, boolean expandEmptyElements)
		throws IOException {

		return XMLFormatter.toString(_document, indent, expandEmptyElements);
	}

	public String formattedString(
			String indent, boolean expandEmptyElements, boolean trimText)
		throws IOException {

		return XMLFormatter.toString(
			_document, indent, expandEmptyElements, trimText);
	}

	public DocumentType getDocumentType() {
		return new DocumentTypeImpl(_document.getDocType());
	}

	public Element getRootElement() {
		return new ElementImpl(_document.getRootElement());
	}

	public org.dom4j.Document getWrappedDocument() {
		return _document;
	}

	public String getXMLEncoding() {
		return _document.getXMLEncoding();
	}

	@Override
	public int hashCode() {
		return _document.hashCode();
	}

	public void setRootElement(Element rootElement) {
		ElementImpl rootElementImpl = (ElementImpl)rootElement;

		_document.setRootElement(rootElementImpl.getWrappedElement());
	}

	public void setXMLEncoding(String encoding) {
		_document.setXMLEncoding(encoding);
	}

	@Override
	public String toString() {
		return _document.toString();
	}

	private org.dom4j.Document _document;

}