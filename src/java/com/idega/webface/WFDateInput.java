/*
 * $Id: WFDateInput.java,v 1.7 2008/04/24 23:55:29 laddi Exp $
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
import javax.faces.el.ValueBinding;
import com.idega.util.RenderUtils;

/**
 * Input component for date/time using dropdown menus for selection.
 * <p>
 * Last modified: $Date: 2008/04/24 23:55:29 $ by $Author: laddi $
 *
 * @author Anders Lindman
 * @version $Revision: 1.7 $
 */
public class WFDateInput extends UIInput {

	private boolean _showTime = false;
	private boolean _showYear = false;
	private boolean _showDay = false;
	private boolean _displayDayLast = false;
	private int _fromYear = 0;
	private int _toYear = 0;
	
	/**
	 * Default contructor.
	 */
	public WFDateInput() {
		super();
		java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
		String year = now.toString().substring(0, 4);
		this._fromYear = Integer.parseInt(year) - 1;
		this._toYear = Integer.parseInt(year) + 2;
		this._showYear = true;
		this._showDay = true;
	}
	
	/**
	 * Returns true if the input shows hour and minute dropdown menus.
	 */
	public boolean getShowTime() {
		return this._showTime;
	}
	
	/**
	 * Returns true if the year dropdown menu shall be displayed.
	 */
	public boolean getShowYear() {
		return this._showYear;
	}
	
	/**
	 * Returns true if the day dropdown menu shall be displayed.
	 */
	public boolean getShowDay() {
		return this._showDay;
	}
	
	/**
	 * Returns true if the dropdown menus shall be displayed in the order: year, month, day.
	 */
	public boolean getDisplayDayLast() {
		return this._displayDayLast;
	}
	
	/**
	 * Returns the first year for the year dropdown menu.
	 */
	public int getFromYear() {
		return this._fromYear;
	}
	
	/**
	 * Returns the last year for the year dropdown menu.
	 */
	public int getToYear() {
		return this._toYear;
	}
	
	/**
	 * Sets the input to show hour and minute dropdown menus.
	 */
	public void setShowTime(boolean showTime) {
		this._showTime = showTime;
	}
	
	/**
	 * Sets if the year dropdown menu shall be displayed.
	 */
	public void setShowYear(boolean showYear) {
		this._showYear = showYear;
	}
	
	/**
	 * Sets if the day dropdown menu shall be displayed.
	 */
	public void setShowDay(boolean showDay) {
		this._showDay = showDay;
	}
	
	/**
	 * If set to true the dropdown menus will be displayed in the order: year, month, day.
	 * If set to false the order will be: day, month, year.
	 */
	public void setDisplayDayLast(boolean displayDayLast) {
		this._displayDayLast = displayDayLast;
	}
	
	/**
	 * Sets if the day dropdown menu shall be displayed.
	 */
	public void setDayYear(boolean showDay) {
		this._showDay = showDay;
	}
	
	/**
	 * Sets the first year for the year dropdown menu.
	 */
	public void setFromYear(int fromYear) {
		this._fromYear = fromYear;
	}
	
	/**
	 * Sets the last year for the year dropdown menu.
	 */
	public void setToYear(int toYear) {
		this._toYear = toYear;
	}
	
	/*
	 * Returns year item map.
	 */
	private Map getYears() {
		Map m = new LinkedHashMap();
		m.put(translate("year"), "");
		for (int i = this._fromYear; i <= this._toYear; i++) {
			String year = String.valueOf(i);		
			m.put(year, year);
		}
		return m;
	}
	
	/*
	 * Returns month item map.
	 */
	private Map getMonths() {
		Map m = new LinkedHashMap();
		m.put(translate("month"), "");
		m.put(translate("january"), "01");
		m.put(translate("february"), "02");
		m.put(translate("march"), "03");
		m.put(translate("april"), "04");
		m.put(translate("may"), "05");
		m.put(translate("june"), "06");
		m.put(translate("july"), "07");
		m.put(translate("august"), "08");
		m.put(translate("september"), "09");
		m.put(translate("october"), "10");
		m.put(translate("november"), "11");
		m.put(translate("december"), "12");
		return m;
	}
	
	/*
	 * Returns day item map.
	 */
	private Map getDays() {
		Map m = new LinkedHashMap();
		m.put(translate("day"), "");
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
		m.put(translate("hours_short"), "");
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
		m.put(translate("minutes_short"), "");
		for (int i = 0; i <= 59; i++) {
			String s = (i < 10 ? "0" : "") + i;
			m.put(s, s);
		}
		return m;
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeBegin(FacesContext context) {
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
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeChildren(FacesContext context) {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		//String bid = "com.idega.webface";
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
		HtmlSelectOneMenu monthInput = (HtmlSelectOneMenu) getFacet("month_input");
		monthInput.setId(getId() + "_month");
		if (month != null) {
			monthInput.setValue(month);
		}
		HtmlSelectOneMenu dayInput = (HtmlSelectOneMenu) getFacet("day_input");
		dayInput.setId(getId() + "_day");
		if (day != null) {
			dayInput.setValue(day);
		}
		if (this._showYear && this._displayDayLast) {
			RenderUtils.renderFacet(context, this, "year_input");
			out.write(" ");
		}
		if (this._showDay && !this._displayDayLast) {
			RenderUtils.renderFacet(context, this, "day_input");
			out.write(" ");
		}
		RenderUtils.renderFacet(context, this, "month_input");
		out.write(" ");		
		if (this._showYear && !this._displayDayLast) {
			RenderUtils.renderFacet(context, this, "year_input");
		}
		if (this._showDay && this._displayDayLast) {
			RenderUtils.renderFacet(context, this, "day_input");
		}
		
		if (this._showTime) {
			out.write(" " + translate("at_time") + " ");
			
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
			RenderUtils.renderFacet(context, this, "hour_input");			
			HtmlSelectOneMenu minuteInput = (HtmlSelectOneMenu) getFacet("minute_input");
			minuteInput.setId(getId() + "_minute");
			if (minute != null) {
				minuteInput.setValue(minute);
			}
			RenderUtils.renderFacet(context, this, "minute_input");
		}
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	@Override
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[7];
		values[0] = super.saveState(ctx);
		values[1] = new Boolean(this._showTime);
		values[2] = new Boolean(this._showYear);
		values[3] = new Boolean(this._showDay);
		values[4] = new Boolean(this._displayDayLast);
		values[5] = new Integer(this._fromYear);
		values[6] = new Integer(this._toYear);
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	@Override
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		this._showTime = ((Boolean) values[1]).booleanValue();
		this._showYear = ((Boolean) values[2]).booleanValue();
		this._showDay = ((Boolean) values[3]).booleanValue();
		this._displayDayLast = ((Boolean) values[4]).booleanValue();
		this._fromYear = ((Integer) values[5]).intValue();
		this._toYear = ((Integer) values[6]).intValue();
	}
	
	/**
	 * @see javax.faces.component.UIComponent#getRenderType()
	 */
	public String getRenderType() {
		return null;
	}
	
	@Override
	public String getRendererType(){
		return null;
	}
	
	/**
	 * @see javax.faces.component.UIComponent#decode(javax.faces.context.FacesContext)
	 */
	@Override
	public void decode(FacesContext context) {
		String year = (String) context.getExternalContext().getRequestParameterMap().get(getClientId(context) + "_year");
		year = year == null ? "" + this._fromYear : year;
		String month = (String) context.getExternalContext().getRequestParameterMap().get(getClientId(context) + "_month");
		String day = (String) context.getExternalContext().getRequestParameterMap().get(getClientId(context) + "_day");
		day = day == null ? "01" : day;
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
	
	private static String translate(String localizationKey) {
		String expr = WFUtil.getLocalizedStringExpr(IWBundleStarter.BUNDLE_IDENTIFIER, localizationKey);
		ValueBinding vb = WFUtil.createValueBinding(expr);
		return (String) vb.getValue(FacesContext.getCurrentInstance()); 
	}

}
