<%--
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
--%>

<%@ include file="/html/portlet/css_init.jsp" %>

.lfr-image-gallery-actions {
	font-size: 11px;
	text-align: right;
}

.portlet-image-gallery-display {
	.lfr-asset-attributes {
		clear: both;
	}

	.folder-search {
		float: right;
		margin: 0 0 0.5em 0.5em;
	}

	.image-score {
		display: block;
		margin: 0 0 5px 35px;
		padding-top: 3px;
	}

	.image-thumbnail {
		border: 2px solid #eee;
		float: left;
		margin: 20px 4px 0;
		padding: 5px 5px 0;
		text-align: center;
		text-decoration: none;

		&:hover {
			border-color: #ccc;
		}
	}

	.taglib-webdav {
		margin-top: 3em;
	}

	.image-title {
		display: block;
	}
	
	.document-display-style {
		a {
			color: #333;
		}

		.document-link {
			display: block;
			text-align: center;
			text-decoration: none;
		}

		&.descriptive {
			display: block;
			height: 140px;
			margin: 5px;
			padding-bottom: 5px;
			padding-top: 5px;
			position: relative;
			text-align: left;

			.document-title {
				display: block;
				font-size: 1.15em;
				font-weight: bold;
				padding: 1.4em 0 0;
			}

			.document-description {
				display: block;
				height: 100px;
				overflow: hidden;
			}

			.document-action {
				height: 20px;
				overflow: hidden;
				position: absolute;
				right: 6px;
				top: 10px;
			}

			.document-selector {
				left: 5px;
				position: absolute;
				top: 10px;
			}

			.document-thumbnail {
				float: left;
				margin: 5px 10px;
				position: relative;
				text-align: center;
			}

			.document-link {
				display: block;
				text-align: left;
				text-decoration: none;
			}

			img.locked-icon {
				bottom: 10px;
				right: 0;
				position: absolute;
			}

			&:hover .document-selector, &.selected .document-selector {
				clip: auto;
				position: absolute;
			}

			&.selected, .selected:hover {
				background-color: #00A2EA;
			}
		}

		&.icon {
			display: inline-block;
			margin: 5px;
			padding: 10px 0;
			position: relative;
			vertical-align: top;
			width: 200px;

			.document-action {
				overflow: hidden;
				position: absolute;
				right: 10px;
			}

			.document-selector {
				left: 10px;
				position: absolute;
				top: 10px;
			}

			.document-thumbnail {
				text-align: center;
				position: relative;
			}

			img.locked-icon {
				bottom: 0;
				position: absolute;
				right: 0;
			}

			.document-title {
				clear: both;
				display: block;
				padding: 0 10px;
				word-wrap: break-word;
			}

			&.selected, &.selected:hover {
				background-color: #00A2EA;
			}

			.document-selector, &:hover .document-selector, &.selected .document-selector {
				position: absolute;
			}
		}

		.document-action .direction-down {
			height: 20px;
		}

		&.selected a {
			color: #FFF;
		}

		.overlay.document-action a {
			display: block;
			float: right;
			min-height: 15px;
		}

		&:hover .overlay, &.hover .overlay, &.selected .document-selector {
			clip: auto;
		}
	}
}