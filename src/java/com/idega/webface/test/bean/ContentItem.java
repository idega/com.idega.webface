/*
 * Created on 9.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.test.bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version 1.0
 */
public interface ContentItem {

	public abstract int getContentItemId();

	public abstract Locale getLocale();

	public abstract String getLocaleId();

	public abstract String getName();

	public abstract String getDescription();

	public abstract String getItemType();

	public abstract Date getCreatedTimestamp();

	public abstract int getCreatedByUserId();

	public abstract void setContentItemId(int id);

	public abstract void setLocaleId(String localeId);

	public abstract void setName(String s);

	public abstract void setDescription(String s);

	public abstract void setItemType(String s);

	public abstract void setCreatedTimestamp(Date d);

	public abstract void setCreatedByUserId(int id);

	public abstract void setLocale(Locale locale);

	public abstract Map getLocales();

	public abstract String getPendingLocaleId();

	public abstract void setPendingLocaleId(String localeId);

	public abstract String getRequestedStatus();

	public abstract void setRequestedStatus(String requestedStatus);

	/**
	 * Clears all attributes for this bean.
	 */
	public abstract void clear();

	// Locale dependent attributes
	public abstract int getVersionId();

	public abstract int getMainCategoryId();

	public abstract void setVersionId(int id);

	public abstract void setMainCategoryId(int id);

	public abstract void setMainCategoryId(Integer id);

	/**
	 * Returns the item field with the specified key. 
	 */
	public abstract ContentItemField getItemField(String key);

	/**
	 *Sets the item field with the specified key. 
	 */
	public abstract void setItemField(String key, ContentItemField field);

	/**
	 * Returns the list of item fields with the specified key. 
	 */
	public abstract List getItemFields(String key);

	/**
	 *Sets the list of item fields with the specified key. 
	 */
	public abstract void setItemFields(String key, List fields);

	/**
	 *Sets the item field value with the specified key. 
	 */
	public abstract void setItemField(String key, String value);

	/**
	 * Returns the case for this content item.
	 */
	public abstract ContentItemCase getCase();

	/**
	 * Sets the case for this content item. 
	 */
	public abstract void setCase(ContentItemCase caseBean);

	/**
	 * Returns the case status for this content item. 
	 */
	public abstract String getStatus();

	/**
	 * Sets the case status for this content item. 
	 */
	public abstract void setStatus(String status);

	/**
	 * Returns the current selected available categories. 
	 */
	public abstract Object[] getSelectedAvailableCategories();

	/**
	 * Sets the current selected available categories. 
	 */
	public abstract void setSelectedAvailableCategories(Object[] selectedAvailableCategories);

	/**
	 * Returns the current selected categories. 
	 */
	public abstract Object[] getSelectedCategories();

	/**
	 * Sets the current selected categories. 
	 */
	public abstract void setSelectedCategories(Object[] selectedCategories);

	/**
	 * Returns the categories associated with this content item.
	 */
	public abstract Map getCategories();

	/**
	 * Returns the category names for this content item.
	 */
	public abstract Collection getCategoryNames();

	/**
	 * Returns all categories available for content items.
	 */
	public abstract Map getAllCategories();

	/**
	 * Returns all locales available for content items.
	 */
	public abstract Map getAllLocales();

	/**
	 * Returns categories not connected to this content item.
	 */
	public abstract Map getAvailableCategories();

	/**
	 * Adds the selected available categories to this content item.
	 */
	public abstract void addSelectedCategories();

	/**
	 * Removes the selected categories from this content item.
	 */
	public abstract void removeSelectedCategories();

	/**
	 * Update pending locale change.
	 */
	public abstract void updateLocale();
}