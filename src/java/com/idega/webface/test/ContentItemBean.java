/*
 * $Id: ContentItemBean.java,v 1.2 2004/06/08 16:14:47 anders Exp $
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Bean for idegaWeb content items.   
 * <p>
 * Last modified: $Date: 2004/06/08 16:14:47 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
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

	private Map _allCategories = null;
	
	private Object[] _selectedAvailableCategories = null;
	private Object[] _selectedArticleCategories = null;

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
	 * Returns the current selected available categories. 
	 */
	public Object[] getSelectedAvailableCategories() {
		return _selectedAvailableCategories;
	}
	
	/**
	 * Sets the current selected available categories. 
	 */
	public void setSelectedAvailableCategories(Object[] selectedAvailableCategories) {
		_selectedAvailableCategories = selectedAvailableCategories;
	}
	
	/**
	 * Returns the current selected article categories. 
	 */
	public Object[] getSelectedArticleCategories() {
		return _selectedArticleCategories;
	}
	
	/**
	 * Sets the current selected article categories. 
	 */
	public void setSelectedArticleCategories(Object[] selectedArticleCategories) {
		_selectedArticleCategories = selectedArticleCategories;
	}
	
	/**
	 * Returns the categories associated with this content item.
	 */
	public Map getCategories() {
		if (_categories == null) {
			_categories = new LinkedHashMap();
			_categories.put("Public news", "" + new Integer(1));
			_categories.put("Business news", "" + new Integer(2));
			_categories.put("Company info", "" + new Integer(3));
		}
		return _categories;
	}
	
	/**
	 * Returns all categories available for content items.
	 */
	public Map getAllCategories() {
		if (_allCategories == null) {
			_allCategories = new LinkedHashMap();
			/*
			_allCategories.put("Public news", new Integer(1));
			_allCategories.put("Business news", new Integer(2));
			_allCategories.put("Company info", new Integer(3));
			_allCategories.put("General info", new Integer(4));
			_allCategories.put("IT stuff", new Integer(5));
			_allCategories.put("Press releases", new Integer(6));
			_allCategories.put("Internal info", new Integer(7));
			 */
			_allCategories.put("Public news", "" + new Integer(1));
			_allCategories.put("Business news", "" + new Integer(2));
			_allCategories.put("Company info", "" + new Integer(3));
			_allCategories.put("General info", "" + new Integer(4));
			_allCategories.put("IT stuff", "" + new Integer(5));
			_allCategories.put("Press releases", "" + new Integer(6));
			_allCategories.put("Internal info", "" + new Integer(7));
		}
		return _allCategories;
	}
	
	/**
	 * Returns categories not connected to this content item.
	 */
	public Map getAvailableCategories() {
		Map c = new LinkedHashMap();
		for (Iterator iter = getAllCategories().keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (getCategories().get(key) == null) {
				c.put(key, getAllCategories().get(key));
			}
		}
		return c;
	}
	
	/**
	 * Adds the selected available categories to this content item.
	 */
	public void addSelectedCategories() {
		Object[] categoryIds = getSelectedAvailableCategories();
		for (int i = 0; i < categoryIds.length; i++) {
			String id = categoryIds[i].toString();
			for (Iterator iter = getAllCategories().keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				if (getAllCategories().get(key).equals(id)) {
					getCategories().put(key, id);
					break;
				}
			}
		}
	}
}
