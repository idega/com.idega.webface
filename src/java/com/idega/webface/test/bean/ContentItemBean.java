/*
 * $Id: ContentItemBean.java,v 1.5 2004/12/09 13:59:22 joakim Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test.bean;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.RemoveException;
import com.idega.core.data.ICTreeNode;
import com.idega.core.file.data.ICFile;
import com.idega.core.localisation.data.ICLocale;
import com.idega.data.IDOEntityDefinition;
import com.idega.data.IDOStoreException;
import com.idega.data.TreeableEntity;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.webface.WFPage;
import com.idega.webface.WFUtil;


/**
 * Bean for idegaWeb content items.   
 * <p>
 * Last modified: $Date: 2004/12/09 13:59:22 $ by $Author: joakim $
 *
 * @author Anders Lindman
 * @version $Revision: 1.5 $
 */

public class ContentItemBean implements Serializable, ContentItem, ICFile {
	
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
	public String getLocaleIdAsString() { return _locale == null ? "" : _locale.getLanguage(); }
	public String getName() { return _name; }
	public String getDescription() { return _description; }
	public String getItemType() { return _itemType; }
//	public Date getCreatedTimestamp() { return _createdTimestamp; }
	public int getCreatedByUserId() { return _createdByUserId; }

	public void setContentItemId(int id) { _contentItemId = id; } 
	public void setLocaleId(String localeId) { setLocale(new Locale(localeId)); }
	public void setName(String s) { _name = s; }
	public void setDescription(String s) { _description = s; }
	public void setItemType(String s) { _itemType = s; }
//	public void setCreatedTimestamp(Date d) { _createdTimestamp = d; }
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
			versionId = ((Integer) _versionIds.get(getLocaleIdAsString())).intValue();
		} catch (Exception e) {}
		return versionId; 
	}
	
	public int getMainCategoryId() {
		int mainCategoryId = -1;
		try {
			mainCategoryId = ((Integer) _mainCategoryIds.get(getLocaleIdAsString())).intValue();
		} catch (Exception e) {}
		return mainCategoryId; 
	}

	public void setVersionId(int id) {
		if (_versionIds == null) {
			_versionIds = new HashMap();
		}
		_versionIds.put(getLocaleIdAsString(), new Integer(id));
	}
	
	public void setMainCategoryId(int id) {
		setMainCategoryId(new Integer(id));
	}

	public void setMainCategoryId(Integer id) {
		if (_mainCategoryIds == null) {
			_mainCategoryIds = new HashMap();
		}
		_mainCategoryIds.put(getLocaleIdAsString(), id);
	}

	/**
	 * Returns the item field with the specified key. 
	 */
	public ContentItemField getItemField(String key) {
		if (_itemFields == null) {
			_itemFields = new HashMap();
		}
		ContentItemField field = (ContentItemField) _itemFields.get(key + getLocaleIdAsString());
		if (field == null) {
			field = new ContentItemFieldBean(-1, -1, key, "", 0, ContentItemField.FIELD_TYPE_STRING);
			setItemField(key + getLocaleIdAsString(), field);
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
		_itemFields.put(key + getLocaleIdAsString(), field);
	}
	
	/**
	 * Returns the list of item fields with the specified key. 
	 */
	public List getItemFields(String key) {
		if (_itemFields == null) {
			return null;
		}
		return (List) _itemFields.get(key + getLocaleIdAsString());
	}
	
	/**
	 *Sets the list of item fields with the specified key. 
	 */
	public void setItemFields(String key, List fields) {
		if (_itemFields == null) {
			_itemFields = new HashMap();
		}
		_itemFields.put(key + getLocaleIdAsString(), fields);
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
		Map categoriesByLocale = (Map) _categories.get(getLocaleIdAsString());
		if (categoriesByLocale == null) {
			categoriesByLocale = new LinkedHashMap();
			categoriesByLocale.put(WFUtil.getValue(bref + "category_public_news"),  new Integer(1));
			categoriesByLocale.put(WFUtil.getValue(bref + "category_business_news"),  new Integer(2));
			categoriesByLocale.put(WFUtil.getValue(bref + "category_company_info"), new Integer(3));
			_categories.put(getLocaleIdAsString(), categoriesByLocale);
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

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#getCreationDate()
	 */
	public Timestamp getCreationDate() {
		// TODO Auto-generated method stub
		return new Timestamp(System.currentTimeMillis());
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#getDeleted()
	 */
	public boolean getDeleted() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#getDeletedByUserId()
	 */
	public int getDeletedByUserId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#getDeletedWhen()
	 */
	public Timestamp getDeletedWhen() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#getFileSize()
	 */
	public Integer getFileSize() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#getFileValue()
	 */
	public InputStream getFileValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#getFileValueForWrite()
	 */
	public OutputStream getFileValueForWrite() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#getICLocale()
	 */
	public ICLocale getICLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#getLanguage()
	 */
	public int getLanguage() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#getMimeType()
	 */
	public String getMimeType() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#getModificationDate()
	 */
	public Timestamp getModificationDate() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#initializeAttributes()
	 */
	public void initializeAttributes() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#isLeaf()
	 */
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#setCreationDate(java.sql.Timestamp)
	 */
	public void setCreationDate(Timestamp p0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#setDeleted(boolean)
	 */
	public void setDeleted(boolean p0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#setFileSize(java.lang.Integer)
	 */
	public void setFileSize(Integer p0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#setFileSize(int)
	 */
	public void setFileSize(int p0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#setFileValue(java.io.InputStream)
	 */
	public void setFileValue(InputStream p0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#setLanguage(int)
	 */
	public void setLanguage(int p0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#setLocale()
	 */
	public void setLocale() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#setMimeType(java.lang.String)
	 */
	public void setMimeType(String p0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#setModificationDate(java.sql.Timestamp)
	 */
	public void setModificationDate(Timestamp p0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#superDelete()
	 */
	public void superDelete() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#unDelete(boolean)
	 */
	public void unDelete(boolean p0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#delete()
	 */
	public void delete() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#isFolder()
	 */
	public boolean isFolder() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#isEmpty()
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#getLocalizationKey()
	 */
	public String getLocalizationKey() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#setLocalizationKey(java.lang.String)
	 */
	public void setLocalizationKey(String key) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.data.IDOEntity#store()
	 */
	public void store() throws IDOStoreException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.data.IDOEntity#getEntityDefinition()
	 */
	public IDOEntityDefinition getEntityDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.IDOEntity#decode(java.lang.String)
	 */
	public Object decode(String pkString) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.IDOEntity#decode(java.lang.String[])
	 */
	public Collection decode(String[] pkString) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.ejb.EJBLocalObject#getEJBLocalHome()
	 */
	public EJBLocalHome getEJBLocalHome() throws EJBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.ejb.EJBLocalObject#getPrimaryKey()
	 */
	public Object getPrimaryKey() throws EJBException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.ejb.EJBLocalObject#remove()
	 */
	public void remove() throws RemoveException, EJBException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.ejb.EJBLocalObject#isIdentical(javax.ejb.EJBLocalObject)
	 */
	public boolean isIdentical(EJBLocalObject arg0) throws EJBException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.TreeableEntity#addChild(com.idega.data.TreeableEntity)
	 */
	public void addChild(TreeableEntity p0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.data.TreeableEntity#getChildrenIterator(java.lang.String)
	 */
	public Iterator getChildrenIterator(String p0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.TreeableEntity#getIndex(com.idega.core.data.ICTreeNode)
	 */
	public int getIndex(ICTreeNode p0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.TreeableEntity#getParentEntity()
	 */
	public TreeableEntity getParentEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.TreeableEntity#getTreeRelationshipChildColumnName(com.idega.data.TreeableEntity)
	 */
	public String getTreeRelationshipChildColumnName(TreeableEntity p0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.TreeableEntity#getTreeRelationshipTableName(com.idega.data.TreeableEntity)
	 */
	public String getTreeRelationshipTableName(TreeableEntity p0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.TreeableEntity#moveChildrenFrom(com.idega.data.TreeableEntity)
	 */
	public void moveChildrenFrom(TreeableEntity p0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.data.TreeableEntity#removeChild(com.idega.data.TreeableEntity)
	 */
	public void removeChild(TreeableEntity p0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.data.TreeableEntity#leafsFirst()
	 */
	public boolean leafsFirst() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.TreeableEntity#sortLeafs()
	 */
	public boolean sortLeafs() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.TreeableEntity#setLeafsFirst(boolean)
	 */
	public void setLeafsFirst(boolean b) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.data.TreeableEntity#setToSortLeafs(boolean)
	 */
	public void setToSortLeafs(boolean b) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.data.ICTreeNode#getChildren()
	 */
	public Collection getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.data.ICTreeNode#getChildrenIterator()
	 */
	public Iterator getChildrenIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.data.ICTreeNode#getAllowsChildren()
	 */
	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.data.ICTreeNode#getChildAtIndex(int)
	 */
	public ICTreeNode getChildAtIndex(int childIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.data.ICTreeNode#getChildCount()
	 */
	public int getChildCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.data.ICTreeNode#getParentNode()
	 */
	public ICTreeNode getParentNode() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.data.ICTreeNode#getNodeName()
	 */
	public String getNodeName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.data.ICTreeNode#getNodeName(java.util.Locale)
	 */
	public String getNodeName(Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.data.ICTreeNode#getNodeName(java.util.Locale, com.idega.idegaweb.IWApplicationContext)
	 */
	public String getNodeName(Locale locale, IWApplicationContext iwac) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.data.ICTreeNode#getNodeID()
	 */
	public int getNodeID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.data.ICTreeNode#getSiblingCount()
	 */
	public int getSiblingCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.MetaDataCapable#setMetaDataAttributes(java.util.Map)
	 */
	public void setMetaDataAttributes(Map map) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.data.MetaDataCapable#getMetaDataAttributes()
	 */
	public Map getMetaDataAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.MetaDataCapable#getMetaDataTypes()
	 */
	public Map getMetaDataTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.MetaDataCapable#setMetaData(java.lang.String, java.lang.String)
	 */
	public void setMetaData(String metaDataKey, String value) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.data.MetaDataCapable#setMetaData(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void setMetaData(String metaDataKey, String value, String type) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.data.MetaDataCapable#getMetaData(java.lang.String)
	 */
	public String getMetaData(String metaDataKey) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.MetaDataCapable#renameMetaData(java.lang.String, java.lang.String)
	 */
	public void renameMetaData(String oldKeyName, String newKeyName) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.data.MetaDataCapable#renameMetaData(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void renameMetaData(String oldKeyName, String newKeyName, String value) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.data.MetaDataCapable#removeMetaData(java.lang.String)
	 */
	public boolean removeMetaData(String metaDataKey) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.idega.data.MetaDataCapable#updateMetaData()
	 */
	public void updateMetaData() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.idega.core.file.data.ICFile#getLocaleId()
	 */
	public int getLocaleId() {
		// TODO Auto-generated method stub
		return 0;
	}
}