/*
 * $Id: ArticleItemBean.java,v 1.3 2004/10/19 11:09:29 tryggvil Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Bean for idegaWeb article content items.   
 * <p>
 * Last modified: $Date: 2004/10/19 11:09:29 $ by $Author: tryggvil $
 *
 * @author Anders Lindman
 * @version $Revision: 1.3 $
 */

public class ArticleItemBean extends ContentItemBean implements Serializable {
	
	public final static String KP = "error_"; // Key prefix
	
	public final static String KEY_ERROR_HEADLINE_EMPTY = KP + "headline_empty";
	public final static String KEY_ERROR_BODY_EMPTY = KP + "body_empty";
	public final static String KEY_ERROR_PUBLISHED_FROM_DATE_EMPTY = KP + "published_from_date_empty";	
	
	private boolean _isUpdated = false;
	private List _errorKeys = null;
	
	/**
	 * Default constructor.
	 */
	public ArticleItemBean() {
		clear();
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

	public boolean isUpdated() { return _isUpdated; }
	public void setUpdated(boolean b) { _isUpdated = b; }
	public void setUpdated(Boolean b) { _isUpdated = b.booleanValue(); }
	
	/**
	 * Clears all all attributes for this bean. 
	 */
	public void clear() {
		super.clear();
		_isUpdated = false;
	}
	
	/**
	 * Adds an image to this article item.
	 */
	public void addImage(byte[] imageData, String contentType) {
		List l = getImages();
		if (l == null) {
			l = new ArrayList();
		}
		ContentItemField field = new ContentItemFieldBean();
		field.setBinaryValue(imageData);
		field.setFieldType(contentType);
		field.setOrderNo(l.size());
		l.add(field);
		setImages(l);
	}
	
	/**
	 * Removes the image with the specified image number from this article item.
	 */
	public void removeImage(Integer imageNumber) {
		int imageNo = imageNumber.intValue();
		try {
			List l = getImages();
			l.remove(imageNo);
			for (int i = 0; i < l.size(); i++) {
				ContentItemField field = (ContentItemField) l.get(i);
				field.setOrderNo(i);
			}
		} catch (Exception e) {}
	}
	
	/**
	 * Adds a related content item to this article item.
	 */
	public void addRelatedContentItem(Integer contentItemId) {
		List l = getRelatedContentItems();
		if (l == null) {
			l = new ArrayList();
		}
		ContentItemField field = new ContentItemFieldBean();
		field.setValue(contentItemId.toString());
//		field.setFieldType(contentType);
		field.setName("Content item..." + contentItemId);
		field.setOrderNo(l.size());
		l.add(field);
		setRelatedContentItems(l);
	}
	
	/**
	 * Removes the related content item with the specified item id from this article item.
	 */
	public void removeRelatedContentItem(Integer contentItemId) {
		String itemId = contentItemId.toString();
		try {
			List l = getRelatedContentItems();
			for (Iterator iter = l.iterator(); iter.hasNext();) {
				ContentItemField field = (ContentItemField) iter.next();
				if (field.getValue().equals(itemId)) {
					l.remove(field);
					break;
				}
			}
			for (int i = 0; i < l.size(); i++) {
				ContentItemField field = (ContentItemField) l.get(i);
				field.setOrderNo(i);
			}
		} catch (Exception e) {}
	}
	
	/**
	 * Returns localization keys for error messages.  
	 */
	public List getErrorKeys() {
		return _errorKeys;
	}
	
	/**
	 * Adds an error message localization key.  
	 */
	public void addErrorKey(String key) {
		if (_errorKeys == null) {
			_errorKeys = new ArrayList();
		}
		_errorKeys.add(key);
	}
	
	/**
	 * Clears all error message localization keys.  
	 */
	public void clearErrorKeys() {
		_errorKeys = new ArrayList();
	}
	
	/**
	 * Stores this article item to the database. 
	 */
	public Boolean store() {
		boolean storeOk = true;
		clearErrorKeys();
		
		if (getHeadline().trim().equals("")) {
			addErrorKey(KEY_ERROR_HEADLINE_EMPTY);
			storeOk = false;
		}
		if (getBody().trim().equals("")) {
			addErrorKey(KEY_ERROR_BODY_EMPTY);
			storeOk = false;
		}
		if (getRequestedStatus() != null && getRequestedStatus().equals(ContentItemCase.STATUS_PUBLISHED)) {
			if (getCase().getPublishedFromDate() == null) {
				addErrorKey(KEY_ERROR_PUBLISHED_FROM_DATE_EMPTY);
				storeOk = false;
			}
		}
		
		if (storeOk) {
			if (getRequestedStatus() != null) {
				setStatus(getRequestedStatus());
				setRequestedStatus(null);
			}
		}
		
		return new Boolean(storeOk);
	}
}
