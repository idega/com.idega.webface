/*
 * $Id: ContentItemBean.java,v 1.1 2004/06/07 07:51:19 anders Exp $
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Bean for idegaWeb content items.   
 * <p>
 * Last modified: $Date: 2004/06/07 07:51:19 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */

public class ContentItemBean implements Serializable {
	
	private int _contentItemId = 0;
	private int _versionId = 0;
	private Locale _locale = null;
	private String _name = null;
	private String _description = null;
	private String _itemType = null;
	private Timestamp _createdTimestamp = null;
	private int _createdByUserId = 0;
	private int _mainCategoryId = 0;
	
	private ContentItemCaseBean _caseBean = null;
	
	private Map _itemFields = null;
	private Map _categories = null;

	/**
	 * Default constructor.
	 */
	public ContentItemBean() {}
	
	/**
	 * Constructs a new content item bean with the specified parameters. 
	 */
	public ContentItemBean(
			int contentItemId,
			int versionId,
			Locale locale,
			String name,
			String description,
			String itemType,
			Timestamp createdTimestamp,
			int createdByUserId) {
		_contentItemId = contentItemId;
		_versionId = versionId;
		_locale = locale;
		_name = name;
		_description = description;
		_itemType = itemType;
		_createdTimestamp = createdTimestamp;
		_createdByUserId = createdByUserId;
	}
		
	public int getContentItemId() { return _contentItemId; }
	public int getVersionId() { return _versionId; }
	public Locale getLocale() { return _locale; }
	public String getName() { return _name; }
	public String getDescription() { return _description; }
	public String getItemType() { return _itemType; }
	public Timestamp getCreatedTimestamp() { return _createdTimestamp; }
	public int getCreatedByUserId() { return _createdByUserId; }
	public int getMainCategoryId() { return _mainCategoryId; }

	public void setContentItemId(int id) { _contentItemId = id; } 
	public void setVersionId(int id) { _versionId = id; }
	public void setLocale(Locale l) { _locale = l; }
	public void setName(String s) { _name = s; }
	public void setDescription(String s) { _description = s; }
	public void setItemType(String s) { _itemType = s; }
	public void setCreatedTimestamp(Timestamp t) { _createdTimestamp = t; }
	public void setCreatedByUserId(int id) { _createdByUserId = id; }
	public void setMainCategoryId(int id) { _mainCategoryId = id; }
	
	/**
	 * Returns the item field with the specified key. 
	 */
	public ContentItemFieldBean getItemField(String key) {
		if (_itemFields == null) {
			return null;
		}
		return (ContentItemFieldBean) _itemFields.get(key);
	}
	
	/**
	 *Sets the item field with the specified key. 
	 */
	public void setItemField(String key, ContentItemFieldBean field) {
		if (_itemFields == null) {
			_itemFields = new HashMap();
		}
		_itemFields.put(key, field);
	}
	
	/**
	 * Returns the list of item fields with the specified key. 
	 */
	public List getItemFields(String key) {
		if (_itemFields == null) {
			return null;
		}
		return (List) _itemFields.get(key);
	}
	
	/**
	 *Sets the list of item fields with the specified key. 
	 */
	public void setItemFields(String key, List fields) {
		if (_itemFields == null) {
			_itemFields = new HashMap();
		}
		_itemFields.put(key, fields);
	}
	
	/**
	 *Sets the item field value with the specified key. 
	 */
	public void setItemField(String key, String value) {
		if (_itemFields == null) {
			_itemFields = new HashMap();
		}
		ContentItemFieldBean field = getItemField(key);
		if (field == null) {
			field = new ContentItemFieldBean(-1, -1, key, value, 0, ContentItemFieldBean.FIELD_TYPE_STRING);
		} else {
			field.setValue(value);
		}
		setItemField(key, field);
	}
	
	/**
	 * Returns the case for this content item.
	 */
	public ContentItemCaseBean getCase() {
		return _caseBean;
	}
	
	/**
	 * Sets the case for this content item. 
	 */
	public void setCase(ContentItemCaseBean caseBean) {
		_caseBean = caseBean;
	}
	
	/**
	 * Returns the case status for this content item. 
	 */
	public String getStatus() {
		if (_caseBean == null) {
			return null;
		}
		return _caseBean.getCaseStatus();
	}
	
	/**
	 * Sets the case status for this content item. 
	 */
	public void setStatus(String status) {
		if (_caseBean == null) {
			_caseBean = new ContentItemCaseBean(-1, status, null, null, null, _versionId);
		} else {
			_caseBean.setCaseStatus(status);
		}
	}
	
	/**
	 * Returns the categoies associated with this content item.
	 */
	public Map getCategories() {
		if (_categories == null) {
			_categories = new LinkedHashMap();
			_categories.put("Public news", new Integer(1));
			_categories.put("Business news", new Integer(2));
			_categories.put("Company info", new Integer(3));
		}
		return _categories;
	}
}
