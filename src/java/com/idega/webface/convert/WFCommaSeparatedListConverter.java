/*
 * $Id: WFCommaSeparatedListConverter.java,v 1.1 2004/06/11 13:43:57 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.convert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * Converter for generating comma separared lists.   
 * <p>
 * Last modified: $Date: 2004/06/11 13:43:57 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 * @see javax.faces.convert.Converter
 */
public class WFCommaSeparatedListConverter implements Converter {
	
	/**
	 * @see javax.faces.convert.Converter#getAsObject()
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		List l = new ArrayList();
		StringTokenizer st = new StringTokenizer(value, ",");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			l.add(token.trim());
		}
		return null;
	}
	
	/**
	 * @see javax.faces.convert.Converter#getAsString()
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String s = "";
		if (value == null) {
			return s;
		}
		if (value instanceof Collection) {
			Collection c = (Collection) value;
			for (Iterator iter = c.iterator(); iter.hasNext();) {
				s += iter.next().toString();
				if (iter.hasNext()) {
					s += ", ";
				}
			}
		} else if (value instanceof Iterator) {
			for (Iterator iter = (Iterator) value; iter.hasNext();) {
				s += iter.next().toString();
				if (iter.hasNext()) {
					s += ", ";
				}
			}			
		} else if (value instanceof Object[]) {
			Object[] a = (Object[]) value; 
			for (int i = 0; i < a.length; i++) {
				s += a[i].toString();
				if (i < a.length - 1) {
					s += ", ";
				}
			}
		} else {
			throw new ConverterException("WFCommaSeparatedListConverter: value must be of type Collection, Iterator or array.");
		}
		return s;
	}
}

