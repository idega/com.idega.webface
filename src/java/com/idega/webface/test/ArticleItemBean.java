/*
 * $Id: ArticleItemBean.java,v 1.2 2004/06/08 16:14:47 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

/**
 * Bean for idegaWeb article content items.   
 * <p>
 * Last modified: $Date: 2004/06/08 16:14:47 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */

public class ArticleItemBean extends ContentItemBean implements Serializable {
	
	public final static String KP = "article_"; // Key prefix
	
	public final static String KEY_ERROR_HEADLINE_EMPTY = KP + "headline_empty";
	public final static String KEY_ERROR_BODY_EMPTY = KP + "body_empty";
	
	/**
	 * Default constructor.
	 */
	public ArticleItemBean() {}
	
	/**
	 * Constructs a new article content item bean with the specified parameters. 
	 */
	public ArticleItemBean(
			int contentItemId,
			int versionId,
			Locale locale,
			String name,
			String description,
			Timestamp createdTimestamp,
			int createdByUserId) {
		super(
			contentItemId,
			versionId,
			locale,
			name,
			description,
			"ARTICLE_ITEM",
			createdTimestamp,
			createdByUserId);
	}
		
	public String getHeadline() { return getItemField("headline").getValue(); }
	public String getTeaser() { return getItemField("teaser").getValue(); }
	public String getBody() { return getItemField("body").getValue(); }
	public String getAuthor() { return getItemField("author").getValue(); }
	public String getSource() { return getItemField("source").getValue(); }
	public String getComment() { return getItemField("comment").getValue(); }
	public List getImages() { return getItemFields("image"); }
	public List getAttachments() { return getItemFields("attachment"); }
	public List getRelatedContentItems() { return getItemFields("related_items"); }

	public void setHeadline(String s) { setItemField("headline", s); } 
	public void setHeadline(Object o) { setItemField("headline", o.toString()); } 
	public void setTeaser(String s) { setItemField("teaser", s); } 
	public void setBody(String s) { setItemField("body", s); } 
	public void setAuthor(String s) { setItemField("author", s); } 
	public void setSource(String s) { setItemField("source", s); }
	public void setComment(String s) { setItemField("comment", s); }
	public void setImages(List l) { setItemFields("image", l); }
	public void setAttachment(List l) { setItemFields("attachment", l); }
	public void setRelatedContentItems(List l) { setItemFields("related_items", l); }
	
	/**
	 * Stores this article item to the database. 
	 */
	public void store() throws ContentItemException {
		if (getHeadline().trim().equals("")) {
			throw new ContentItemException(KEY_ERROR_HEADLINE_EMPTY);
		}
		if (getBody().trim().equals("")) {
			throw new ContentItemException(KEY_ERROR_BODY_EMPTY);
		}
		// Store article item
	}
}
