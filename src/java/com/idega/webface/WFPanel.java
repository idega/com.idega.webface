/*
 * $Id: WFPanel.java,v 1.1 2004/05/27 12:38:12 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * Container that layouts its child components in rows and columns.
 * <p>
 * Last modified: $Date: 2004/05/27 12:38:12 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public class WFPanel extends WFContainer {

	private int _rows = 0;
	private int _columns = 0;
	private Map _sizes = null;
	private Map _styleClasses = null;
	
	/**
	 * Default contructor.
	 */
	public WFPanel() {
		_sizes = new HashMap();
		_styleClasses = new HashMap();
		setStyleClass("wf_panel");
	}

	/**
	 * Returns the number of rows in this panel.
	 */
	public int getRows() {
		return _rows;
	}

	/**
	 * Returns the number of columns in this panel.
	 */
	public int getColumns() {
		return _columns;
	}

	/**
	 * Sets the number of rows in this panel. 
	 */
	public void setRows(int rows) {
		_rows = rows;
	}

	/**
	 * Sets the number of columns in this panel. 
	 */
	public void setColumns(int columns) {
		_columns = columns;
	}

	/**
	 * Sets the specified component at a certain column and row.
	 */
	public void set(UIComponent component, int column, int row) {
		set(component, column, row, null);
	}

	/**
	 * Sets the specified component and column width at a certain column and row.
	 */
	public void set(UIComponent component, int column, int row, String columnWidth) {
		set(component, column, row, columnWidth, null);
	}

	/**
	 * Sets the specified component, column width and row height at a certain column and row.
	 */
	public void set(UIComponent component, int column, int row, String columnWidth, String rowHeight) {
		getFacets().put("component" + column + "_" + row, component);
		setSize(columnWidth, rowHeight, column, row);
		if (column > _columns) {
			_columns = column;
		}
		if (row > _rows) {
			_rows = row;
		}
	}
	
	/**
	 * Sets the width and height for the specified column and row.
	 * The parameters columnWidth and rowHeight can be null and are then ignored. 
	 */
	public void setSize(String columnWidth, String rowHeight, int column, int row) {
		if (columnWidth != null) {
			_sizes.put("width" + column + "_" + row, columnWidth);
		}
		if (rowHeight != null) {
			_sizes.put("height" + column + "_" + row, rowHeight);
		}		
	}
	
	/**
	 * Sets the style class for the specified column and row.
	 */
	public void setStyleClass(String styleClass, int column, int row) {
		_styleClasses.put("class" + column + "_" + row, styleClass);
	}
	
	/**
	 * Sets the default input header style class for the specified column and row.
	 */
	public void setInputHeaderStyle(int column, int row) {
		setStyleClass("wf_inputheadercell", column, row);
	}
	
	/**
	 * Sets the default input component style class for the specified column and row.
	 */
	public void setInputStyle(int column, int row) {
		setStyleClass("wf_inputcomponentcell", column, row);
	}
	
	public String getInputStyle() {
		return null;
	}
	
	public String getInputHeaderStyle() {
		return null;
	}
	
	/**
	 * Sets a text at the specified column and row.
	 */
	public void setText(String text, int column, int row) {
		HtmlOutputText t = new HtmlOutputText();
		t.setValue(text);
		set(t, column, row);
	}
	
	/**
	 * Sets an input input header at the specified column and row.
	 */
	public void setInputHeader(String text, int column, int row) {
		setText(text, column, row);
		setInputHeaderStyle(column, row);
	}
	
	/**
	 * Sets an input header at the specified column and row.
	 */
	public void setInputHeader(String text, int column, int row, String width) {
		setText(text, column, row);
		setSize(width, null, column, row);
		setInputHeaderStyle(column, row);
	}
	
	/**
	 * Sets an input component at the specified column and row.
	 */
	public void setInput(UIComponent input, int column, int row) {
		set(input, column, row);
		setInputStyle(column, row);
	}
	
	/**
	 * Sets an input component at the specified column and row.
	 */
	public void setInput(UIComponent input, int column, int row, String width) {
		set(input, column, row);
		setSize(width, null, column, row);
		setInputStyle(column, row);
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		
		out.startElement("table", null);
		out.writeAttribute("class", "wf_paneltable", null);

		for (int row = 1; row <= _rows; row++) {	
			out.startElement("tr", null);
			int maxColumn = 0;
			for (int column = 1; column <= _columns; column++) {
				if (getFacet("component" + column + "_" + row) != null) {
					maxColumn = column;
				}
			}
			for (int column = 1; column <= maxColumn; column++) {
				out.startElement("td", null);
				String styleClass = (String) _styleClasses.get("class" + column + "_" + row);
				if (styleClass != null) {
					out.writeAttribute("class", styleClass, null);
				}
				String style = null;
				String width = (String) _sizes.get("width" + column + "_" + row);
				String height = (String) _sizes.get("height" + column + "_" + row);
				if (width != null) {
					style = "width:" + width + ";";
				}
				if (height != null) {
					if (style == null) {
						style = "";
					}
					style += "height:" + height + ";";
				}
				if (style != null) {
					out.writeAttribute("style", style, null);
				}
				renderFacet(context, "component" + column + "_" + row);
				out.endElement("td");
			}
			out.endElement("tr");			
		}
		out.endElement("table");
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		super.encodeEnd(context);
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[5];
		values[0] = super.saveState(ctx);
		values[1] = new Integer(_rows);
		values[2] = new Integer(_columns);
		values[3] = _sizes;
		values[4] = _styleClasses;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		_rows = ((Integer) values[1]).intValue();
		_columns = ((Integer) values[2]).intValue();
		_sizes = (Map) values[3];
		_styleClasses = (Map) values[4];
	}
}
