/*
 * $Id: BundleMap.java,v 1.1 2004/12/16 17:07:58 joakim Exp $
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
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Map of Bundles for localization
 * 
 *  Last modified: $Date: 2004/12/16 17:07:58 $ by $Author: joakim $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.1 $
 */
class BundleMap implements Map {

	private ResourceBundle _bundle;
	private List _values;

	public BundleMap(ResourceBundle bundle) {
		_bundle = bundle;
	}

	//Optimized methods

	public Object get(Object key) {
		try{
			return _bundle.getObject(key.toString());
		}
		catch(MissingResourceException msre){
			msre.printStackTrace();
			return "";
		}
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
			for (Enumeration enumer = _bundle.getKeys(); enumer.hasMoreElements();) {
				String v = _bundle.getString((String)enumer.nextElement());
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
		for (Enumeration enumer = _bundle.getKeys(); enumer.hasMoreElements();) {
			final String k = (String) enumer.nextElement();
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
		for (Enumeration enumer = _bundle.getKeys(); enumer.hasMoreElements();) {
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
}