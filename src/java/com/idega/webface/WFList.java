/*
 * $Id: WFList.java,v 1.2 2004/05/27 12:40:46 anders Exp $
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
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.webface.bean.WFListBean;
import com.idega.webface.event.WFListNavigationEvent;
import com.idega.webface.event.WFListNavigationListener;

/**
 * Renders child components in a list. Supports automatic list navigation and 
 * fires events for optional listeners to dynamically update list values.   
 * <p>
 * Last modified: $Date: 2004/05/27 12:40:46 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */
public class WFList extends HtmlDataTable implements ActionListener, NamingContainer {
	
	private boolean _showListNavigation = true;
	private String _listStyleClass = null;
	private WFListBean _listBean = null;

	private final static String ACTION_PREVIOUS = "previous";
	private final static String ACTION_NEXT = "next";
	private final static String ACTION_GOTO = "goto";
	
	private final static int MAX_NAVIGATION_LINKS = 10;
	
	/**
	 * Default contructor.
	 */
	public WFList() {
		super();
		setVar("var");
		setListStyleClass("wf_list");
		this.setStyleClass("wf_listtable");
		this.setHeaderClass("wf_listheading");
		this.setRowClasses("wf_listoddrow,wf_listevenrow");
	}
	
	/**
	 * Constructs a new WFList component with the specified list bean as data source.
	 */
	public WFList(WFListBean listBean, String var, int first, int rows) {
		this();
		setVar(var);
		setListBean(listBean);
		UIColumn[] columns = listBean.createColumns(var);
		for (int i = 0; i < columns.length; i++) {
			addColumn(columns[i]);
		}
		setRows(rows);
		setFirst(first);
	}
		
	/**
	 * @see javax.faces.component.UIComponent#getRenderType()
	 */
	public String getRenderType() {
		return null;
	}
		
	/**
	 * Returns true if the list navigation is shown.
	 */
	public boolean getShowListNavigation() {
		return _showListNavigation;
	}
		
	/**
	 * Returns the list bean for this list.
	 */
	public WFListBean getListBean() {
		return _listBean;
	}

	/**
	 * Returns the css class for this list.
	 */
	public String getListStyleClass() {
		return _listStyleClass;
	}

	/**
	 * Sets the current start display row for this list.
	 */
	public void setFirst(int first) {
		super.setFirst(first);
		if (getRows() > 0) {
			setListNavigationLinks();			
		}
		if (_listBean != null) {
			this.setValue(_listBean.updateDataModel(getFirst(), getRows())); 
		}
	}

	/**
	 * Sets the maximum number of rows to display for this list.
	 * If this value is set to 0 then all rows from first row in the list will be displayed.
	 */
	public void setRows(int rows) {
		super.setRows(rows);
		setListNavigationLinks();
	}

	/**
	 * Sets if the list navigation shall be shown. 
	 */
	public void setShowListNavigation(boolean showListNavigation) {
		_showListNavigation = showListNavigation;
	}

	/**
	 * Sets the list bean for this list. 
	 */
	public void setListBean(WFListBean listBean) {
		_listBean = listBean;
	}

	/**
	 * Sets the css class for this list. 
	 */
	public void setListStyleClass(String listStyleClass) {
		_listStyleClass = listStyleClass;
	}

	/**
	 * Adds a column to this list.
	 */
	public void addColumn(UIColumn column) {
		getChildren().add(column);
	}

	/**
	 * Adds a text column to this list with the specified parameters.
	 * @param header the column header text
	 * @param propertyName the name of the bean property for the row text 
	 */
	public void addTextColumn(String header, String propertyName) {
		UIColumn c = new UIColumn();
		c.setHeader(WFUtil.getText(header));
		HtmlOutputText t = new HtmlOutputText();
		t.setValueBinding("value", WFUtil.createValueBinding("#{" + getVar() + "." + propertyName + "}"));
		c.getChildren().add(t);
		getChildren().add(c);
	}

	/**
	 * Adds a link column to this list with the specified parameters.
	 * @param header the column header text
	 * @param propertyName the name of the bean property for the row link text 
	 */
	public void addLinkColumn(String header, String propertyName) {
		UIColumn c = new UIColumn();
		c.setHeader(WFUtil.getText(header));
		HtmlCommandLink l = new HtmlCommandLink();
		l.setStyleClass("wf_listlink");
		l.setValueBinding("value", WFUtil.createValueBinding("#{" + getVar() + "." + propertyName + "}"));
		c.getChildren().add(l);
		getChildren().add(c);
	}

	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		// Main container
		out.startElement("div", null);
		if (getListStyleClass() != null) {
			out.writeAttribute("class", getListStyleClass(), null);
		}
		if (_showListNavigation) {
			renderListNavigation(context);
		}
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		// Render table
		super.encodeBegin(context);
		super.encodeChildren(context);
		super.encodeEnd(context);
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		out.endElement("div");
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[4];
		values[0] = super.saveState(ctx);
		values[1] = new Boolean(_showListNavigation);
		values[2] = _listStyleClass;
		values[3] = _listBean;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		_showListNavigation = ((Boolean) values[1]).booleanValue();
		_listStyleClass = (String) values[2];
		_listBean = (WFListBean) values[3];
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
			if (parent.getRows() > 0) {
				parent.setFirst(parent.getFirst() - parent.getRows());
				sendNavigationEvent = true;
			}
		} else if (id.equals(ACTION_NEXT)) {
			if (parent.getRows() > 0) {
				parent.setFirst(parent.getFirst() + parent.getRows());
				sendNavigationEvent = true;
			}
		} else {
			int pageNumber = Integer.parseInt(WFUtil.getParameter(source, ACTION_GOTO));
			if (parent.getRows() > 0) {
				parent.setFirst((pageNumber - 1) * parent.getRows());
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
		int maxDisplayRows = getRows();
		int rows = getRowCount();
		int row = getFirst();
		if (maxDisplayRows <= 0 || rows <= maxDisplayRows) {
			return;
		}
		
		ResponseWriter out = context.getResponseWriter();

		out.startElement("table", null);
		out.writeAttribute("class", "wf_listnavigation", null);
		out.startElement("tr", null);
		out.startElement("td", null);
		out.writeAttribute("align", "left", null);
		out.writeAttribute("valign", "middle", null);
		out.writeAttribute("class", "wf_smalltext", null);
		
		int nrOfPages = rows / maxDisplayRows;
		if (rows > nrOfPages * maxDisplayRows) {
			nrOfPages++;
		}
		int currentPage = row / maxDisplayRows + 1;
		int pageOffset = 0;
		if (currentPage > MAX_NAVIGATION_LINKS) {
			pageOffset = currentPage - MAX_NAVIGATION_LINKS;
		}
		
		if (currentPage > 1) {
			WFUtil.renderFacet(context, this, "navigationlink_previous");
			out.write(" ");
		}
		for (int i = 1; i <= nrOfPages && i <= MAX_NAVIGATION_LINKS; i++) {
			if ((i + pageOffset) == currentPage) {
				out.write(String.valueOf(i + pageOffset));
			} else {
				WFUtil.renderFacet(context, this, "navigationlink_goto" + i);
			}
			out.write("&nbsp;");
		}
		out.endElement("td");
		out.startElement("td", null);
		out.writeAttribute("align", "right", null);
		out.writeAttribute("valign", "middle", null);
		out.writeAttribute("class", "wf_smalltext", null);
		if (currentPage < nrOfPages) {
			WFUtil.renderFacet(context, this, "navigationlink_next");
		}
		out.endElement("td");
		out.endElement("tr");
		out.endElement("table");
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
		int currentPage = getFirst() / getRows() + 1;
		if (currentPage > MAX_NAVIGATION_LINKS) {
			pageOffset = currentPage - MAX_NAVIGATION_LINKS;
		}
		for (int i = 1; i <= MAX_NAVIGATION_LINKS; i++) {
			HtmlCommandLink gotoLink = new HtmlCommandLink();
			gotoLink.setValue(String.valueOf(i + pageOffset));
			gotoLink.setId(ACTION_GOTO + i);
			WFUtil.addParameter(gotoLink, ACTION_GOTO, String.valueOf(i));			
			gotoLink.addActionListener(this);
			getFacets().put("navigationlink_goto" + i, gotoLink);			
		}
	}
}
