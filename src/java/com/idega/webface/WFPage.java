/*
 * $Id: WFPage.java,v 1.3 2004/06/30 13:35:21 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;
import java.io.IOException;
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

import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.idega.faces.IWBaseComponent;

/**
 * ...
 * <p>
 * Last modified: $Date: 2004/06/30 13:35:21 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.3 $
 */
public class WFPage extends IWBaseComponent {

	public final static String WF_BUNDLE = "wf_bundle"; // test, TODO: use IWResourceBundle
	public final static String CONTENT_BUNDLE = "content_bundle";
	
	/**
	 * Default contructor. 
	 */
	public WFPage() {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		loadResourceBundles(context);
		super.encodeBegin(context);
	}

	/*
	 * Test load bundles. 
	 */
	private void loadResourceBundles(FacesContext context) {
		if (context.getExternalContext().getSessionMap().get(CONTENT_BUNDLE) != null) {
			return;
		}

		UIViewRoot viewRoot = context.getViewRoot();
		if (viewRoot == null) {
			throw new FacesException("No view root. A WFPage object must be a part of the component tree.");
		}

		Locale locale = viewRoot.getLocale();
		if (locale == null) {
			locale = context.getApplication().getDefaultLocale();
		}
		
		locale = new Locale("sv", "SE"); //test
		ResourceBundle bundle = null;
//		String bundleName = "com.idega.webface.test.TestBundle";
		String bundleName = "com.idega.webface.test.Content";
		try {
			bundle = ResourceBundle.getBundle(bundleName, locale);
		} catch (MissingResourceException e) {
			System.out.println("Resource bundle '" + bundleName + "' could not be found.");
			return;
		}
		context.getExternalContext().getSessionMap().put(CONTENT_BUNDLE, new BundleMap(bundle));
		
		bundleName = "com.idega.webface.test.Webface";
		try {
			bundle = ResourceBundle.getBundle(bundleName, locale);
		} catch (MissingResourceException e) {
			System.out.println("Resource bundle '" + bundleName + "' could not be found.");
			return;
		}
		context.getExternalContext().getSessionMap().put(WF_BUNDLE, new BundleMap(bundle));
	}

	private static class BundleMap implements Map {

		private ResourceBundle _bundle;
		private List _values;

		public BundleMap(ResourceBundle bundle) {
			_bundle = bundle;
		}

		//Optimized methods

		public Object get(Object key) {
			return _bundle.getObject(key.toString());
		}

		public boolean isEmpty() {
			return !_bundle.getKeys().hasMoreElements();
		}

		public boolean containsKey(Object key) {
			return _bundle.getObject(key.toString()) != null;
		}


		//Unoptimized methods

		public Collection values() {
			if (_values == null) {
				_values = new ArrayList();
				for (Enumeration enum = _bundle.getKeys(); enum.hasMoreElements();) {
					String v = _bundle.getString((String)enum.nextElement());
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
			for (Enumeration enum = _bundle.getKeys(); enum.hasMoreElements();) {
				final String k = (String) enum.nextElement();
				set.add(new Map.Entry() {
					public Object getKey() {
						return k;
					}

					public Object getValue() {
						return _bundle.getObject(k);
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
			for (Enumeration enum = _bundle.getKeys(); enum.hasMoreElements();) {
				set.add(enum.nextElement());
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
	}
}
