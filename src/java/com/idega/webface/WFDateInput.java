/*
 * $Id: WFDateInput.java,v 1.1 2004/06/23 13:22:06 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.component.UIInput;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * Input component for date/time using dropdown menus for selection.
 * <p>
 * Last modified: $Date: 2004/06/23 13:22:06 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public class WFDateInput extends UIInput {

	private boolean _showTime = false;
	
	/**
	 * Default contructor.
	 */
	public WFDateInput() {
		super();
	}
	
	/**
	 * Returns true if the input shows hour and minute dropdown menus.
	 */
	public boolean getShowTime() {
		return _showTime;
	}
	
	/**
	 * Sets the input to show hour and minute dropdown menus.
	 */
	public void setShowTime(boolean showTime) {
		_showTime = showTime;
	}
	
	/*
	 * Returns year item map.
	 */
	private Map getYears() {
		Map m = new LinkedHashMap();
		m.put("Year", "");
		m.put("1999", "1999");
		m.put("2000", "2000");
		m.put("2001", "2001");
		m.put("2002", "2002");
		m.put("2003", "2003");
		m.put("2004", "2004");
		m.put("2005", "2005");
		return m;
	}
	
	/*
	 * Returns month item map.
	 */
	private Map getMonths() {
		Map m = new LinkedHashMap();
		m.put("Month", "");
		m.put("January", "01"); // TODO: localize it
		m.put("February", "02");
		m.put("March", "03");
		m.put("April", "04");
		m.put("May", "05");
		m.put("June", "06");
		m.put("July", "07");
		m.put("August", "08");
		m.put("September", "09");
		m.put("October", "10");
		m.put("November", "11");
		m.put("December", "12");
		return m;
	}
	
	/*
	 * Returns day item map.
	 */
	private Map getDays() {
		Map m = new LinkedHashMap();
		m.put("Day", "");
		for (int i = 1; i <= 31; i++) {
			m.put(String.valueOf(i), (i < 10 ? "0" : "") + i);
		}
		return m;
	}
	
	/*
	 * Returns hour item map.
	 */
	private Map getHours() {
		Map m = new LinkedHashMap();
		m.put("h", "");
		for (int i = 0; i <= 23; i++) {
			String s = (i < 10 ? "0" : "") + i;
			m.put(s, s);
		}
		return m;
	}
	
	/*
	 * Returns minute item map.
	 */
	private Map getMinutes() {
		Map m = new LinkedHashMap();
		m.put("m", "");
		for (int i = 0; i <= 59; i++) {
			String s = (i < 10 ? "0" : "") + i;
			m.put(s, s);
		}
		return m;
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		if (getFacet("year_input") == null) {
			HtmlSelectOneMenu yearInput = new HtmlSelectOneMenu();
			yearInput = (HtmlSelectOneMenu) WFUtil.setInputStyle(yearInput);
			UISelectItems items = new UISelectItems();
			items.setValue(getYears());
			yearInput.getChildren().add(items);
			getFacets().put("year_input", yearInput);
		
			HtmlSelectOneMenu monthInput = new HtmlSelectOneMenu();
			monthInput = (HtmlSelectOneMenu) WFUtil.setInputStyle(monthInput);
			items = new UISelectItems();
			items.setValue(getMonths());
			monthInput.getChildren().add(items);
			getFacets().put("month_input", monthInput);
		
			HtmlSelectOneMenu dayInput = new HtmlSelectOneMenu();
			dayInput = (HtmlSelectOneMenu) WFUtil.setInputStyle(dayInput);
			items = new UISelectItems();
			items.setValue(getDays());
			dayInput.getChildren().add(items);
			getFacets().put("day_input", dayInput);			
		
			HtmlSelectOneMenu hourInput = new HtmlSelectOneMenu();
			hourInput = (HtmlSelectOneMenu) WFUtil.setInputStyle(hourInput);
			items = new UISelectItems();
			items.setValue(getHours());
			hourInput.getChildren().add(items);
			getFacets().put("hour_input", hourInput);			
		
			HtmlSelectOneMenu minuteInput = new HtmlSelectOneMenu();
			minuteInput = (HtmlSelectOneMenu) WFUtil.setInputStyle(minuteInput);
			items = new UISelectItems();
			items.setValue(getMinutes());
			minuteInput.getChildren().add(items);
			getFacets().put("minute_input", minuteInput);			
		}
		ResponseWriter out = context.getResponseWriter();
		Date date = (Date) getValue();
		String year = null;
		String month = null;
		String day = null;
		if (date != null) {
			java.sql.Date d = new java.sql.Date(date.getTime());
			String s = d.toString();
			year = s.substring(0, 4);
			month = s.substring(5, 7);
			day = s.substring(8, 10);
		}		
		HtmlSelectOneMenu yearInput = (HtmlSelectOneMenu) getFacet("year_input");
		yearInput.setId(getId() + "_year");
		if (year != null) {
			yearInput.setValue(year);
		}
		WFUtil.renderFacet(context, this, "year_input");
		out.write(" ");		
		HtmlSelectOneMenu monthInput = (HtmlSelectOneMenu) getFacet("month_input");
		monthInput.setId(getId() + "_month");
		if (month != null) {
			monthInput.setValue(month);
		}
		WFUtil.renderFacet(context, this, "month_input");
		out.write(" ");		
		HtmlSelectOneMenu dayInput = (HtmlSelectOneMenu) getFacet("day_input");
		dayInput.setId(getId() + "_day");
		if (day != null) {
			dayInput.setValue(day);
		}
		WFUtil.renderFacet(context, this, "day_input");
		
		if (_showTime) {
			out.write(" at "); // Localize this
			
			String hour = null;
			String minute = null;
			if (date != null) {
				java.sql.Timestamp t = new java.sql.Timestamp(date.getTime());
				String s = t.toString();
				hour = s.substring(11, 13);
				minute = s.substring(14, 16);
			}

			HtmlSelectOneMenu hourInput = (HtmlSelectOneMenu) getFacet("hour_input");
			hourInput.setId(getId() + "_hour");
			if (hour != null) {
				hourInput.setValue(hour);
			}
			WFUtil.renderFacet(context, this, "hour_input");			
			HtmlSelectOneMenu minuteInput = (HtmlSelectOneMenu) getFacet("minute_input");
			minuteInput.setId(getId() + "_minute");
			if (minute != null) {
				minuteInput.setValue(minute);
			}
			WFUtil.renderFacet(context, this, "minute_input");
		}
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = new Boolean(_showTime);
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		_showTime = ((Boolean) values[1]).booleanValue();
	}
	
	/**
	 * @see javax.faces.component.UIComponent#getRenderType()
	 */
	public String getRenderType() {
		return null;
	}
	
	/**
	 * @see javax.faces.component.UIComponent#decode(javax.faces.context.FacesContext)
	 */
	public void decode(FacesContext context) {
		String year = (String) context.getExternalContext().getRequestParameterMap().get(getClientId(context) + "_year");
		String month = (String) context.getExternalContext().getRequestParameterMap().get(getClientId(context) + "_month");
		String day = (String) context.getExternalContext().getRequestParameterMap().get(getClientId(context) + "_day");
		String hour = (String) context.getExternalContext().getRequestParameterMap().get(getClientId(context) + "_hour");
		hour = hour == null ? "00" : hour;
		String minute = (String) context.getExternalContext().getRequestParameterMap().get(getClientId(context) + "_minute");
		minute = minute == null ? "00" : minute;
		java.sql.Timestamp t = null;
		try {
			t = java.sql.Timestamp.valueOf(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00.000000000");
		} catch (Exception e) {}
		this.setValue(t);
	}
}
