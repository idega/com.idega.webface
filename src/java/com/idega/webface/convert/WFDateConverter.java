/*
 * $Id: WFDateConverter.java,v 1.1 2004/06/11 13:43:57 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.convert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * Converter for java.util.Date values.   
 * <p>
 * Last modified: $Date: 2004/06/11 13:43:57 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 * @see javax.faces.convert.Converter
 */
public class WFDateConverter implements Converter {
	
	private boolean _allowEmptyValues = true;
	
	/**
	 * @see javax.faces.convert.Converter#getAsObject()
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Date date = null;
		if (value == null) {
			return date;
		}
		if (value.trim().length() == 0) {
			if (_allowEmptyValues) {
				return date;
			} else {
				throw new ConverterException(new FacesMessage("Date input empty."));				
			}
		}
		Locale locale = context.getViewRoot().getLocale();
		if (locale.getCountry().equals("SE")) {
			date = getDate_SE(value);
			if (date == null) {
				throw new ConverterException(new FacesMessage("Date format error: " + value));
			}
		} else {
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
			try {
				date = df.parse(value);
			} catch (ParseException e) {
				throw new ConverterException(new FacesMessage("Date format error: " + value));
			}
		}
		return date;
	}
	
	/**
	 * @see javax.faces.convert.Converter#getAsString()
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		if (!(value instanceof Date)) {
			return value.toString();
		}
		Locale locale = context.getViewRoot().getLocale();
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		return df.format(value);
	}
	
	/*
	 * Converts string to date for locale SE. 
	 */
	private Date getDate_SE(String value) {
		SimpleDateFormat formatter = null;
		ParsePosition pos = null;
		java.util.Date d = null;
		String s = value.trim();
		Date date = null;
		
		if ((d == null) && (s.length() == 4)) {
			s = "20" + s + "01";
		}
		if ((d == null) && (s.length() == 6)) {
			pos = new ParsePosition(0);
			formatter = new SimpleDateFormat ("yyMMdd");
			d = formatter.parse(s, pos);
		}
		if ((d == null) && (s.length() == 8) && (s.indexOf('-') == -1)) {
			pos = new ParsePosition(0);
			formatter = new SimpleDateFormat ("yyyyMMdd");
			d = formatter.parse(s, pos);
		}
		if ((d == null) && (s.length() == 8)) {
			pos = new ParsePosition(0);
			formatter = new SimpleDateFormat ("yy-MM-dd");
			d = formatter.parse(s, pos);
		}
		if ((d == null) && (s.length() == 10)) {
			pos = new ParsePosition(0);
			formatter = new SimpleDateFormat ("yyyy-MM-dd");
			d = formatter.parse(s, pos);
		}

		if (d != null) {
			date = validateDate(d, s);		 	
		}
		
		return date;		
	}
	
	/*
	 * Returns a Date object if s has valid date format.
	 */
	private Date validateDate(java.util.Date d, String s) {
		Date date = null;
		if (s.length() == 4) {
			s = "20" + s + "01";
		}
		if (d != null) {
			date = new Date(d.getTime());
			String validate = null;
			if ((s.length() == 8) && (s.indexOf('-') != -1)) {
				SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd");
				validate = formatter.format(d);		
			} else {
				validate = formatDate_SE(date, s.length());
			}
			if (!validate.equals(s)) {
				date = null;
			}
		}
		return date;
	}
	
	/*
	 * Formats the specified date into SE standard format of the specified length. 
	 */
	private String formatDate_SE(Date date, int length) {
		if (date == null) {
			return "";
		}
		
		SimpleDateFormat formatter = null;
		
		if (length == 4) {
			formatter = new SimpleDateFormat ("yyMM"); 
		} else if (length == 6) {
			formatter = new SimpleDateFormat ("yyMMdd"); 
		} else if (length == 8) {
			formatter = new SimpleDateFormat ("yyyyMMdd"); 
		} else if (length == 10) {
			formatter = new SimpleDateFormat ("yyyy-MM-dd"); 
		}

		String dateString = "";
		
		if (formatter != null) {
			dateString = formatter.format(date);		
		}
		
		return dateString;
	}
	
	/**
	 * Returns allow empty values. 
	 */
	public boolean getAllowEmptyValues() {
		return _allowEmptyValues;
	}
	
	/**
	 * Sets allow empty values. 
	 */
	public void setAllowEmptyValues(boolean allowEmptyValues) {
		_allowEmptyValues = allowEmptyValues;
	}
}

