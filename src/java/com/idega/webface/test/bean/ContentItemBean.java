/*
 * $Id: ContentItemBean.java,v 1.4 2004/10/19 11:09:29 tryggvil Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.idega.webface.WFPage;
import com.idega.webface.WFUtil;


/**
 * Bean for idegaWeb content items.   
 * <p>
 * Last modified: $Date: 2004/10/19 11:09:29 $ by $Author: tryggvil $
 *
 * @author Anders Lindman
 * @version $Revision: 1.4 $
 */

public class ContentItemBean implements Serializable, ContentItem {
	
	private int _contentItemId = 0;
	private Locale _locale = null;
	private String _name = null;
	private String _description = null;
	private String _itemType = null;
	private Date _createdTimestamp = null;
	private int _createdByUserId = 0;

	private String _pendingLocaleId = null;
	private String _requestedStatus = null;
	
	private ContentItemCase _caseBean = null;
	
	private Map _itemFields = null;
	private Map _categories = null;
	private Map _mainCategoryIds = null;
	private Map _versionIds = null;
	private Map _locales = null;

	private Map _allCategories = null;
	private Map _allLocales = null;
	
	private Object[] _selectedAvailableCategories = null;
	private Object[] _selectedCategories = null;

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
		_locale = locale;
		_name = name;
		_description = description;
		_itemType = itemType;
		_createdTimestamp = createdTimestamp;
		_createdByUserId = createdByUserId;
		
		if (_locale == null) {
			setLocale(new Locale("sv"));
		}
		
		setVersionId(versionId);
	}
		
	public int getContentItemId() { return _contentItemId; }
	public Locale getLocale() { return _locale; }
	public String getLocaleId() { return _locale == null ? "" : _locale.getLanguage(); }
	public String getName() { return _name; }
	public String getDescription() { return _description; }
	public String getItemType() { return _itemType; }
	public Date getCreatedTimestamp() { return _createdTimestamp; }
	public int getCreatedByUserId() { return _createdByUserId; }

	public void setContentItemId(int id) { _contentItemId = id; } 
	public void setLocaleId(String localeId) { setLocale(new Locale(localeId)); }
	public void setName(String s) { _name = s; }
	public void setDescription(String s) { _description = s; }
	public void setItemType(String s) { _itemType = s; }
	public void setCreatedTimestamp(Date d) { _createdTimestamp = d; }
	public void setCreatedByUserId(int id) { _createdByUserId = id; }
	
	public void setLocale(Locale locale) {
		_locale = locale;
		if (_locales == null) {
			_locales = new HashMap();
		}
		_locales.put(locale.getLanguage(), locale);
	}
	
	public Map getLocales() { return _locales; }
	
	public String getPendingLocaleId() { return _pendingLocaleId != null ? _pendingLocaleId : _locale.getLanguage(); }
	public void setPendingLocaleId(String localeId) { _pendingLocaleId = localeId; }

	public String getRequestedStatus() { return _requestedStatus; }
	public void setRequestedStatus(String requestedStatus) { _requestedStatus = requestedStatus; }

	/**
	 * Clears all attributes for this bean.
	 */
	public void clear() {
		_contentItemId = 0;
		setLocale(Locale.getDefault());
		_name = null;
		_description = null;
		_itemType = null;
		_createdTimestamp = null;
		_createdByUserId = 0;

		_pendingLocaleId = null;
	
		_caseBean = null;
	
		_itemFields = null;
		_categories = null;
		_mainCategoryIds = null;
		_versionIds = null;

		_allCategories = null;
		_allLocales = null;
	
		_selectedAvailableCategories = null;
		_selectedCategories = null;
		
		setStatus(ContentItemCase.STATUS_NEW);
	}

	// Locale dependent attributes
	
	public int getVersionId() {
		int versionId = -1;
		try {
			versionId = ((Integer) _versionIds.get(getLocaleId())).intValue();
		} catch (Exception e) {}
		return versionId; 
	}
	
	public int getMainCategoryId() {
		int mainCategoryId = -1;
		try {
			mainCategoryId = ((Integer) _mainCategoryIds.get(getLocaleId())).intValue();
		} catch (Exception e) {}
		return mainCategoryId; 
	}

	public void setVersionId(int id) {
		if (_versionIds == null) {
			_versionIds = new HashMap();
		}
		_versionIds.put(getLocaleId(), new Integer(id));
	}
	
	public void setMainCategoryId(int id) {
		setMainCategoryId(new Integer(id));
	}

	public void setMainCategoryId(Integer id) {
		if (_mainCategoryIds == null) {
			_mainCategoryIds = new HashMap();
		}
		_mainCategoryIds.put(getLocaleId(), id);
	}

	/**
	 * Returns the item field with the specified key. 
	 */
	public ContentItemField getItemField(String key) {
		if (_itemFields == null) {
			_itemFields = new HashMap();
		}
		ContentItemField field = (ContentItemField) _itemFields.get(key + getLocaleId());
		if (field == null) {
			field = new ContentItemFieldBean(-1, -1, key, "", 0, ContentItemField.FIELD_TYPE_STRING);
			setItemField(key + getLocaleId(), field);
		}
		return field;
	}
	
	/**
	 *Sets the item field with the specified key. 
	 */
	public void setItemField(String key, ContentItemField field) {
		if (_itemFields == null) {
			_itemFields = new HashMap();
		}
		_itemFields.put(key + getLocaleId(), field);
	}
	
	/**
	 * Returns the list of item fields with the specified key. 
	 */
	public List getItemFields(String key) {
		if (_itemFields == null) {
			return null;
		}
		return (List) _itemFields.get(key + getLocaleId());
	}
	
	/**
	 *Sets the list of item fields with the specified key. 
	 */
	public void setItemFields(String key, List fields) {
		if (_itemFields == null) {
			_itemFields = new HashMap();
		}
		_itemFields.put(key + getLocaleId(), fields);
	}
	
	/**
	 *Sets the item field value with the specified key. 
	 */
	public void setItemField(String key, String value) {
		if (_itemFields == null) {
			_itemFields = new HashMap();
		}
		ContentItemField field = getItemField(key);
		if (field == null) {
			field = new ContentItemFieldBean(-1, -1, key, value, 0, ContentItemField.FIELD_TYPE_STRING);
		} else {
			field.setValue(value);
		}
		setItemField(key, field);
	}
	
	/**
	 * Returns the case for this content item.
	 */
	public ContentItemCase getCase() {
		return _caseBean;
	}
	
	/**
	 * Sets the case for this content item. 
	 */
	public void setCase(ContentItemCase caseBean) {
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
			_caseBean = new ContentItemCaseBean(-1, status, null, null, null, getVersionId());
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
	 * Returns the current selected categories. 
	 */
	public Object[] getSelectedCategories() {
		return _selectedCategories;
	}
	
	/**
	 * Sets the current selected categories. 
	 */
	public void setSelectedCategories(Object[] selectedCategories) {
		_selectedCategories = selectedCategories;
	}
	
	/**
	 * Returns the categories associated with this content item.
	 */
	public Map getCategories() {
		String bref = WFPage.CONTENT_BUNDLE + ".";
		if (_categories == null) {
			_categories = new HashMap();
		}
		Map categoriesByLocale = (Map) _categories.get(getLocaleId());
		if (categoriesByLocale == null) {
			categoriesByLocale = new LinkedHashMap();
			categoriesByLocale.put(WFUtil.getValue(bref + "category_public_news"),  new Integer(1));
			categoriesByLocale.put(WFUtil.getValue(bref + "category_business_news"),  new Integer(2));
			categoriesByLocale.put(WFUtil.getValue(bref + "category_company_info"), new Integer(3));
			_categories.put(getLocaleId(), categoriesByLocale);
		}
		return categoriesByLocale;
	}
	
	/**
	 * Returns the category names for this content item.
	 */
	public Collection getCategoryNames() {
		return getCategories().keySet();
	}
	
	/**
	 * Returns all categories available for content items.
	 */
	public Map getAllCategories() {
		String bref = WFPage.CONTENT_BUNDLE + ".";
		if (_allCategories == null) {
			_allCategories = new LinkedHashMap();

			_allCategories.put(WFUtil.getValue(bref + "category_public_news"), new Integer(1));
			_allCategories.put(WFUtil.getValue(bref + "category_business_news"), new Integer(2));
			_allCategories.put(WFUtil.getValue(bref + "category_company_info"), new Integer(3));
			_allCategories.put(WFUtil.getValue(bref + "category_general_info"), new Integer(4));
			_allCategories.put(WFUtil.getValue(bref + "category_it_stuff"), new Integer(5));
			_allCategories.put(WFUtil.getValue(bref + "category_press_releases"), new Integer(6));
			_allCategories.put(WFUtil.getValue(bref + "category_internal_info"), new Integer(7));
		}
		return _allCategories;
	}
	
	/**
	 * Returns all locales available for content items.
	 */
	public Map getAllLocales() {
		if (_allLocales == null) {
			String bref = WFPage.CONTENT_BUNDLE + ".";
			_allLocales = new LinkedHashMap();
			Locale sv = new Locale("sv");
			Locale en = new Locale("en");
			Locale is = new Locale("is");
			String displayLang = is.getDisplayLanguage();
			String lang = is.getLanguage();
			_allLocales.put(WFUtil.getValue(bref + sv.getLanguage()), sv.getDisplayLanguage());
			_allLocales.put(WFUtil.getValue(bref + en.getLanguage()), en.getDisplayLanguage());
			//_allLocales.put(WFUtil.getValue(bref + is.getDisplayLanguage()), is.getLanguage());
		}
		return _allLocales;
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
			Object id = categoryIds[i];
			for (Iterator iter = getAllCategories().keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				if (getAllCategories().get(key).equals(id)) {
					getCategories().put(key, id);
					break;
				}
			}
		}
	}
	
	/**
	 * Removes the selected categories from this content item.
	 */
	public void removeSelectedCategories() {
		Object[] categoryIds = getSelectedCategories();
		for (int i = 0; i < categoryIds.length; i++) {
			Object id = categoryIds[i];
			for (Iterator iter = getAllCategories().keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				if (getAllCategories().get(key).equals(id)) {
					getCategories().remove(key);
					break;
				}
			}
		}
	}
	
	/**
	 * Update pending locale change.
	 */
	public void updateLocale() {
		if (_pendingLocaleId != null) {
			setLocale(new Locale(_pendingLocaleId));
			_pendingLocaleId = null;
		}
	}
}