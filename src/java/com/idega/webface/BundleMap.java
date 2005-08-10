/*
 * $Id: BundleMap.java,v 1.2 2005/08/10 18:36:26 tryggvil Exp $
 * Created in 2001 by Tryggvi Larusson
 * 
 * Copyright (C) 2001-2004 Idega hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import com.idega.idegaweb.BundleLocalizationMap;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;

/**
 * <p>
 * Map of Bundles for localization. This class is only used inside the ArticleBlock module.
 * </p>
 * Last modified: $Date: 2005/08/10 18:36:26 $ by $Author: tryggvil $<br/>
 * 
 * @deprecated This class is replaced by com.idega.idegaweb.BundleLocalizationMap
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.2 $
 */
class BundleMap extends BundleLocalizationMap {

	private ResourceBundle _bundle;
	private List _values;

	public BundleMap(ResourceBundle bundle) {
		super(((IWResourceBundle)bundle).getIWBundleParent());
		setResourceBundle(bundle);
	}

	/*
	public Object get(Object key) {
		try{
			return getResourceBundle().getObject(key.toString());
		}
		catch(MissingResourceException msre){
			return handleKeyNotFound((String)key);
		}
	}
	protected String handleKeyNotFound(String key){
		IWResourceBundle iwrb  = getIWResourceBundle();
		//Set the default application locale to be English
		Locale defaultLocale = Locale.ENGLISH;
		if( !iwrb.getLocale().equals(defaultLocale)){
			//this block is not gone into of this resourcebundle is the default (english) bundle
			iwrb = iwrb.getIWBundleParent().getResourceBundle(defaultLocale);
		}
		//set the default value as the key and auto create it for the english resourcebundle:
		return iwrb.getLocalizedString(key,key);
	}
	*/
	

	public boolean isEmpty() {
		return !getResourceBundle().getKeys().hasMoreElements();
	}

	public boolean containsKey(Object key) {
		return getResourceBundle().getObject(key.toString()) != null;
	}


	//Unoptimized methods

	public Collection values() {
		if (_values == null) {
			_values = new ArrayList();
			for (Enumeration enumer = getResourceBundle().getKeys(); enumer.hasMoreElements();) {
				String v = getResourceBundle().getString((String)enumer.nextElement());
				_values.add(v);
			}
		}
		return _values;
	}

	public int size() {
		return values().size();
	}

	public boolean containsValue(Object value) {
		return values().contains(value);
	}

	public Set entrySet() {
		Set set = new HashSet();
		for (Enumeration enumer = getResourceBundle().getKeys(); enumer.hasMoreElements();) {
			final String k = (String) enumer.nextElement();
			set.add(new Map.Entry() {
				public Object getKey() {
					return k;
				}

				public Object getValue() {
					return getResourceBundle().getObject(k);
				}

				public Object setValue(Object value) {
					throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
				}
			});
		}
		return set;
	}

	public Set keySet() {
		Set set = new HashSet();
		for (Enumeration enumer = getResourceBundle().getKeys(); enumer.hasMoreElements();) {
			set.add(enumer.nextElement());
		}
		return set;
	}


	//Unsupported methods

	public Object remove(Object key) {
		throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
	}

	public void putAll(Map t) {
		throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
	}

	public Object put(Object key, Object value) {
		throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
	}

	public void clear() {
		throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
	}

	
	/**
	 * @return Returns the _bundle.
	 */
	public ResourceBundle getResourceBundle() {
		return _bundle;
	}
	
	/**
	 * @param _bundle The _bundle to set.
	 */
	public void setResourceBundle(ResourceBundle _bundle) {
		this._bundle = _bundle;
	}
	
	/**
	 * @return Returns the _bundle.
	 */
	public IWResourceBundle getIWResourceBundle() {
		return (IWResourceBundle)getResourceBundle();
	}
	
	protected IWBundle getBundle(){
		return getIWResourceBundle().getIWBundleParent();
	}
	
}