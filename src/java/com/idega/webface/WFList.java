/*
 * $Id: WFList.java,v 1.1 2004/05/13 13:56:10 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.webface.event.WFListNavigationEvent;
import com.idega.webface.event.WFListNavigationListener;

/**
 * ...  
 * <p>
 * Last modified: $Date: 2004/05/13 13:56:10 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public class WFList extends WFContainer implements ActionListener, NamingContainer {
	
	private int _columns = 0;
	private int _rows = 0;
	private int _row = 0;
	private int _maxDisplayRows = 0;
	private boolean _showListNavigation = true;
	private String _listStyleClass = null;
	private String _columnHeadingStyleClass = null;
	private String _evenRowStyleClass = null;
	private String _oddRowStyleClass = null;

	private final static String ACTION_PREVIOUS = "previous";
	private final static String ACTION_NEXT = "next";
	private final static String ACTION_GOTO = "goto";
	
	private final static int MAX_NAVIGATION_LINKS = 10;
	
	/**
	 * Default contructor.
	 */
	public WFList() {
		setListStyleClass("wf_list");
		setColumnHeadingStyleClass("wf_listheading");
		setEvenRowStyleClass("wf_listevenrow");
		setOddRowStyleClass("wf_listoddrow");
		setRow(1);
	}
	
	/**
	 * Constructs a WFList with the specified number of columns.
	 */
	public WFList(int columns) {
		this();
		setColumns(columns);
	}
		
	/**
	 * Returns the number of columns for this list.
	 */
	public int getColumns() {
		return _columns;
	}
		
	/**
	 * Returns the number of rows for this list.
	 */
	public int getRows() {
		return _rows;
	}
		
	/**
	 * Returns the current start display row for this list.
	 */
	public int getRow() {
		return _row;
	}
		
	/**
	 * Returns the maximum number of rows to display for this list.
	 * If this value is less than 1 then all rows in the list will be displayed.
	 */
	public int getMaxDisplayRows() {
		return _maxDisplayRows;
	}
		
	/**
	 * Returns true if the list navigation is shown.
	 */
	public boolean getShowListNavigation() {
		return _showListNavigation;
	}

	/**
	 * Returns the css class for this list.
	 */
	public String getListStyleClass() {
		return _listStyleClass;
	}

	/**
	 * Returns the css class for column headings.
	 */
	public String getColumnHeadingStyleClass() {
		return _columnHeadingStyleClass;
	}

	/**
	 * Returns the css class for even rows.
	 */
	public String getEvenRowStyleClass() {
		return _evenRowStyleClass;
	}

	/**
	 * Returns the css class for odd rows.
	 */
	public String getOddRowStyleClass() {
		return _oddRowStyleClass;
	}

	/**
	 * Sets the number of columns for this list. 
	 */
	public void setColumns(int columns) {
		_columns = columns;
	}

	/**
	 * Sets the number of rows for this list. 
	 */
	public void setRows(int rows) {
		_rows = rows;
	}

	/**
	 * Sets the current start display row for this list.
	 */
	public void setRow(int row) {
		_row = row;
		if (_maxDisplayRows > 0) {
			setListNavigationLinks();			
		}
	}

	/**
	 * Sets the maximum number of rows to display for this list.
	 * If this value is set to less than 1 then all rows in the list will be displayed.
	 */
	public void setMaxDisplayRows(int maxDisplayRows) {
		_maxDisplayRows = maxDisplayRows;
		setListNavigationLinks();
	}

	/**
	 * Sets if the list navigation shall be shown. 
	 */
	public void setShowListNavigation(boolean showListNavigation) {
		_showListNavigation = showListNavigation;
	}

	/**
	 * Sets the css class for this list. 
	 */
	public void setListStyleClass(String listStyleClass) {
		_listStyleClass = listStyleClass;
	}

	/**
	 * Sets the css class for column headings. 
	 */
	public void setColumnHeadingStyleClass(String columnHeadingStyleClass) {
		_columnHeadingStyleClass = columnHeadingStyleClass;
	}

	/**
	 * Sets the css class for even rows. 
	 */
	public void setEvenRowStyleClass(String evenRowStyleClass) {
		_evenRowStyleClass = evenRowStyleClass;
	}

	/**
	 * Sets the css class for odd rows. 
	 */
	public void setOddRowStyleClass(String oddRowStyleClass) {
		_oddRowStyleClass = oddRowStyleClass;
	}

	/**
	 * Sets a column heading component.
	 * @param column the index of the column
	 * @param component the column heading component to set  
	 */
	public void setColumnHeading(int column, UIComponent component) {
		getFacets().put("column_heading" + column, component);
		if (column > _columns) {
			_columns = column;
		}
	}
	
	/**
	 * Returns the heading component at the specified column index.
	 */
	public UIComponent getColumnHeading(int column) {
		return (UIComponent) getFacets().get("column_heading" + column); 
	}
	
	// MyFaces throws an exception if this method is missing 
	public UIComponent getColumnHeading() {
		return null; 
	}

	/**
	 * Sets a cell component in this list.
	 * @param column the index of the column
	 * @param row the index of the row
	 * @param component the cell component to set  
	 */
	public void setCell(int column, int row, UIComponent component) {
		getFacets().put("cell" + column + "_" + row, component);
		if (column > _columns) {
			_columns = column;
		}
		if (row > _rows) {
			_rows = row;
		}
	}
	
	/**
	 * Returns the cell component at the specified position.
	 */
	public UIComponent getCell(int column, int row) {
		return (UIComponent) getFacets().get("cell" + column + "_" + row); 
	}

	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
		ResponseWriter out = context.getResponseWriter();
		// Main table
		out.startElement("table", null);
		if (getListStyleClass() != null) {
			out.writeAttribute("class", getListStyleClass(), null);
		}
		if (_showListNavigation) {
			renderListNavigation(context);
		}
		// Column headings
		out.startElement("tr", null);
		for (int i = 1; i <= _columns; i++) {
			out.startElement("th", null);
			if (getColumnHeadingStyleClass() != null) {
				out.writeAttribute("class", getColumnHeadingStyleClass(), null);
			}
			// Render column heading component
			renderFacet(context, "column_heading" + i);
			out.endElement("th");
		}
		out.endElement("tr");
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		// Render list rows
		int rowCount = 0;
		for (int row = _row; row <= _rows; row++) {
			if (_maxDisplayRows > 0 && ++rowCount > _maxDisplayRows) {
				break;
			}
			out.startElement("tr", null);
			if (row % 2 == 0) {
				if (getEvenRowStyleClass() != null) {
					out.writeAttribute("class", getEvenRowStyleClass(), null);
				}
			} else {
				if (getOddRowStyleClass() != null) {
					out.writeAttribute("class", getOddRowStyleClass(), null);
				}				
			}
			for (int column = 1; column <= _columns; column++) {
				out.startElement("td", null);
				// Render cell component				
				renderFacet(context, "cell" + column + "_" + row);
				out.endElement("td");
			}
			out.endElement("tr");
		}
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		out.endElement("table");
		super.encodeEnd(context);
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[10];
		values[0] = super.saveState(ctx);
		values[1] = new Integer(_columns);
		values[2] = new Integer(_rows);
		values[3] = new Integer(_row);
		values[4] = new Integer(_maxDisplayRows);
		values[5] = new Boolean(_showListNavigation);
		values[6] = _listStyleClass;
		values[7] = _columnHeadingStyleClass;
		values[8] = _evenRowStyleClass;
		values[9] = _oddRowStyleClass;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		_columns = ((Integer) values[1]).intValue();
		_rows = ((Integer) values[2]).intValue();
		_row = ((Integer) values[3]).intValue();
		_maxDisplayRows = ((Integer) values[4]).intValue();
		_showListNavigation = ((Boolean) values[5]).booleanValue();
		_listStyleClass = (String) values[6];
		_columnHeadingStyleClass = (String) values[7];
		_evenRowStyleClass = (String) values[8];
		_oddRowStyleClass = (String) values[9];
	}

	/**
	 * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
	 */
	public void processAction(ActionEvent event) throws AbortProcessingException {
		
		boolean sendNavigationEvent = false;
		
		UIComponent source = event.getComponent();
		WFList parent = (WFList) source.getParent();
		String id = source.getId();
		if (id.equals(ACTION_PREVIOUS)) {
			if (parent.getMaxDisplayRows() > 0) {
				parent.setRow(parent.getRow() - parent.getMaxDisplayRows());
				sendNavigationEvent = true;
			}
		}
		if (id.equals(ACTION_NEXT)) {
			if (parent.getMaxDisplayRows() > 0) {
				parent.setRow(parent.getRow() + parent.getMaxDisplayRows());
				sendNavigationEvent = true;
			}
		}
		if (id.length() > 4 && id.substring(0, 4).equals(ACTION_GOTO)) {
			int pageNumber = Integer.parseInt((String) ((HtmlCommandLink) source).getValue());
			if (parent.getMaxDisplayRows() > 0) {
				parent.setRow((pageNumber - 1) * parent.getMaxDisplayRows() + 1);
				sendNavigationEvent = true;
			}
		}
		
		if (sendNavigationEvent) {
			WFListNavigationEvent e = new WFListNavigationEvent(parent);
			parent.queueEvent(e);
		}
	}
	
	/**
	 * Register the specified listener for list navigation events.
	 */
	public void addListNavigationListener(WFListNavigationListener listener) {
		addFacesListener(listener);
	}

	/**
	 * Remove the specified listener for list navigation events.
	 */
	public void removeListNavigationListener(WFListNavigationListener listener) {
		removeFacesListener(listener);
	}
	
	/*
	 * Renders the list navigation
	 */
	private void renderListNavigation(FacesContext context) throws IOException {
		if (_maxDisplayRows <= 0 || _rows <= _maxDisplayRows) {
			return;
		}
		
		ResponseWriter out = context.getResponseWriter();

		out.startElement("tr", null);
		out.startElement("td", null);
		out.writeAttribute("colspan", String.valueOf(_columns), null);
		
		out.startElement("table", null);
		out.writeAttribute("style", "width:100%; border-spacing:0; padding:0;", null);
		out.startElement("tr", null);
		out.startElement("td", null);
		out.writeAttribute("align", "left", null);
		out.writeAttribute("valign", "middle", null);
		out.writeAttribute("class", "wf_smalltext", null);
		
		int nrOfPages = nrOfPages = _rows / _maxDisplayRows;
		if (_rows > nrOfPages * _maxDisplayRows) {
			nrOfPages++;
		}
		int currentPage = _row / _maxDisplayRows + 1;
		int pageOffset = 0;
		if (currentPage > MAX_NAVIGATION_LINKS) {
			pageOffset = currentPage - MAX_NAVIGATION_LINKS;
		}
		
		if (currentPage > 1) {
			renderFacet(context, "navigationlink_previous");
			out.write(" ");
		}
		for (int i = 1; i <= nrOfPages && i <= MAX_NAVIGATION_LINKS; i++) {
			if ((i + pageOffset) == currentPage) {
				out.write(String.valueOf(i + pageOffset));
			} else {
				renderFacet(context, "navigationlink_goto" + i);
			}
			out.write("&nbsp;");
		}
		out.endElement("td");
		out.startElement("td", null);
		out.writeAttribute("align", "right", null);
		out.writeAttribute("valign", "middle", null);
		out.writeAttribute("class", "wf_smalltext", null);
		if (currentPage < nrOfPages) {
			renderFacet(context, "navigationlink_next");
		}
		out.endElement("td");
		out.endElement("tr");
		out.endElement("table");
		
		out.endElement("td");
		out.endElement("tr");
		
	}
	
	/*
	 * Sets the list navigation links.
	 */
	private void setListNavigationLinks() {
		HtmlCommandLink previous = new HtmlCommandLink();
		previous.setValue("<< previous");
		previous.setId(ACTION_PREVIOUS);
		previous.addActionListener(this);
		getFacets().put("navigationlink_previous", previous);

		HtmlCommandLink next = new HtmlCommandLink();
		next.setValue("next >>");
		next.setId(ACTION_NEXT);
		next.addActionListener(this);
		getFacets().put("navigationlink_next", next);

		int pageOffset = 0;
		int currentPage = _row / _maxDisplayRows + 1;
		if (currentPage > MAX_NAVIGATION_LINKS) {
			pageOffset = currentPage - MAX_NAVIGATION_LINKS;
		}
		for (int i = 1; i <= MAX_NAVIGATION_LINKS; i++) {
			HtmlCommandLink gotoLink = new HtmlCommandLink();
			gotoLink.setValue(String.valueOf(i + pageOffset));
			gotoLink.setId(ACTION_GOTO + i);
			gotoLink.addActionListener(this);
			getFacets().put("navigationlink_goto" + i, gotoLink);			
		}
	}
}
