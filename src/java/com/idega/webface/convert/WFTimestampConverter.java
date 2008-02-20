/*
 * $Id: WFTimestampConverter.java,v 1.2 2008/02/20 14:10:10 laddi Exp $
 * Created on 17.1.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.convert;

import java.util.Date;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.idega.util.IWTimestamp;


/**
 * 
 *  Last modified: $Date: 2008/02/20 14:10:10 $ by $Author: laddi $
 * 
 * @author <a href="mailto:gimmi@idega.com">gimmi</a>
 * @version $Revision: 1.2 $
 */
public class WFTimestampConverter implements Converter {

	private String datePattern;

	public WFTimestampConverter() {
		this(null);
	}

	public WFTimestampConverter(String datePattern) {
		this.datePattern = datePattern;
	}

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		try {
			IWTimestamp stamp = new IWTimestamp(value);
			Locale locale = context.getViewRoot().getLocale();
			if (datePattern != null) {
				return stamp.getDateString(datePattern, locale);
			}
			return stamp.getLocaleDateAndTime(locale,IWTimestamp.SHORT, IWTimestamp.SHORT);
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value == null) {
			return null;
		}
		if (value instanceof Long){
			if ( ((Long) value).longValue() == 0 ) {
				return null;
			}
			String valueStr = value.toString();
			value = new Date(Long.parseLong(valueStr.toString()));
		} else if (!(value instanceof Date)) {
			return value.toString();
		}
		IWTimestamp stamp = new IWTimestamp((Date)value);
		Locale locale = context.getViewRoot().getLocale();
		return stamp.getLocaleDateAndTime(locale,IWTimestamp.MEDIUM, IWTimestamp.MEDIUM);
	}
}
