/*
 * $Id: WFList.java,v 1.11 2006/04/09 11:59:21 laddi Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandLink;
import org.apache.myfaces.component.html.ext.HtmlDataTable;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.util.RenderUtils;
import com.idega.webface.event.WFListNavigationEvent;
import com.idega.webface.event.WFListNavigationListener;

/**
 * Renders child components in a list. Supports automatic list navigation and 
 * fires events for optional listeners to dynamically update list values.   
 * <p>
 * Last modified: $Date: 2006/04/09 11:59:21 $ by $Author: laddi $
 *
 * @author Anders Lindman
 * @version $Revision: 1.11 $
 */
public class WFList extends HtmlDataTable implements ActionListener {
	
	private boolean _showListNavigation = true;
	private boolean _navigationBelowList = false;
	private String _listStyleClass = null;
	private String _listBeanSessionId = null;

	private final static String ACTION_PREVIOUS = "previous";
	private final static String ACTION_NEXT = "next";
	private final static String ACTION_GOTO = "goto";
	public final static String DEFAULT_RENDER_TYPE="wf_list";
	
	private final static int MAX_NAVIGATION_LINKS = 10;
	
	private String tableHeadStyleClass;
	private String tableBodyStyleClass;
	boolean isBodyScrollable;
	private boolean bodyScrollable=false;
	
	/**
	 * Default contructor.
	 */
	public WFList() {
		super();
		setListStyleClass("wf_list");
		//this.setStyleClass("wf_listtable");
		//this.setHeaderClass("wf_listheading");
		this.setRowClasses("oddrow,evenrow");
	}
	
	/**
	 * Constructs a new WFList component with the specified list session bean as data source.
	 */
	public WFList(String listBeanSessionId, int first, int rows) {
		this();
		this._listBeanSessionId = listBeanSessionId;
		String var = listBeanSessionId + "_var";
		setVar(var);
		setValueBinding("value", WFUtil.createValueBinding("#{" + this._listBeanSessionId + ".dataModel}"));
		UIColumn[] columns = (UIColumn[]) WFUtil.invoke(this._listBeanSessionId, "createColumns", var);
		for (int i = 0; i < columns.length; i++) {
			addColumn(columns[i]);
		}
		setRows(rows);
		setFirst(first);
		
	}
	
	/**
	 * Constructs a new WFList component with the specified list session bean as data source
	 * with first row set to the beginning of the list and number of rows unlimited.
	 */
	public WFList(String listBeanSessionId) {
		this(listBeanSessionId, 0, 0);
	}
		

		
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getRendererType()
	 */
	public String getRendererType() {
		//return null;
		//return super.getRendererType();
		return DEFAULT_RENDER_TYPE;
	}
	/**
	 * Returns true if the list navigation is shown.
	 */
	public boolean getShowListNavigation() {
		return this._showListNavigation;
	}
		
	/**
	 * Returns true if the list navigation shall be placed below the list.
	 */
	public boolean getNavigationBelowList() {
		return this._navigationBelowList;
	}

	/**
	 * Returns the css class for this list.
	 */
	public String getListStyleClass() {
		return this._listStyleClass;
	}

	/**
	 * Sets the current start display row for this list.
	 */
	public void setFirst(int first) {
		super.setFirst(first);
		if (getRows() > 0) {
			setListNavigationLinks();			
		}
		if (this._listBeanSessionId != null) {
			WFUtil.invoke(this._listBeanSessionId, "updateDataModel", new Integer(getFirst()), new Integer(getRows()));
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
		this._showListNavigation = showListNavigation;
	}
		
	/**
	 * Sets if if the list navigation shall be placed below the list.
	 */
	public void setNavigationBelowList(boolean navigationBelowList) {
		this._navigationBelowList = navigationBelowList;
	}

	/**
	 * Sets the css class for this list. 
	 */
	public void setListStyleClass(String listStyleClass) {
		this._listStyleClass = listStyleClass;
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
		HtmlOutputText t = new HtmlOutputText();
		t.setValueBinding("value", WFUtil.createValueBinding("#{" + getVar() + "." + propertyName + "}"));
		l.getChildren().add(t);
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
		out.writeAttribute("id",getId(),null);
		if (getListStyleClass() != null) {
			out.writeAttribute("class", getListStyleClass(), null);
		}
		if (this._showListNavigation && !this._navigationBelowList) {
			renderListNavigation(context);
		}
		super.encodeBegin(context);
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		// Render table
		//super.encodeBegin(context);
		super.encodeChildren(context);
		//super.encodeEnd(context);
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		
		super.encodeEnd(context);
		
		if (this._showListNavigation && this._navigationBelowList) {
			renderListNavigation(context);
		}
		ResponseWriter out = context.getResponseWriter();
		out.endElement("div");
		
		
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[6];
		values[0] = super.saveState(ctx);
		values[1] = new Boolean(this._showListNavigation);
		values[2] = new Boolean(this._navigationBelowList);
		values[3] = this._listStyleClass;
		values[4] = this._listBeanSessionId;
		values[5] = new Boolean(this.bodyScrollable);
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		this._showListNavigation = ((Boolean) values[1]).booleanValue();
		this._navigationBelowList = ((Boolean) values[2]).booleanValue();
		this._listStyleClass = (String) values[3];
		this._listBeanSessionId = (String) values[4];
		this.bodyScrollable=((Boolean)values[5]).booleanValue();
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
			WFListNavigationListener[] listeners = (WFListNavigationListener[]) this.getFacesListeners(WFListNavigationListener.class);
//			parent.queueEvent(e);
//	          UIComponent src = e.getComponent();
	          try
	          {
	              parent.broadcast(event);
	              for (int i = 0; i<listeners.length; i++) {
	              	listeners[i].updateList(e);
	              }
	          }
	          catch (AbortProcessingException evt)
	          {
	          }
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
			RenderUtils.renderFacet(context, this, "navigationlink_previous");
			out.write(" ");
		}
		for (int i = 1; i <= nrOfPages && i <= MAX_NAVIGATION_LINKS; i++) {
			if ((i + pageOffset) == currentPage) {
				out.write(String.valueOf(i + pageOffset));
			} else {
				RenderUtils.renderFacet(context, this, "navigationlink_goto" + i);
			}
			out.write("&nbsp;");
		}
		out.endElement("td");
		out.startElement("td", null);
		out.writeAttribute("align", "right", null);
		out.writeAttribute("valign", "middle", null);
		out.writeAttribute("class", "wf_smalltext", null);
		if (currentPage < nrOfPages) {
			RenderUtils.renderFacet(context, this, "navigationlink_next");
		}
		out.endElement("td");
		out.endElement("tr");
		out.endElement("table");
	}
	
	/*
	 * Sets the list navigation links.
	 */
	private void setListNavigationLinks() {
		String bref = WFPage.WF_BUNDLE + ".";
		HtmlCommandLink previous = new HtmlCommandLink();
		HtmlOutputText t = new HtmlOutputText();
		t.setValueBinding("value", WFUtil.createValueBinding("#{" + bref + "navigation_previous}"));
		previous.getChildren().add(t);
		previous.setId(ACTION_PREVIOUS);
		previous.addActionListener(this);
		getFacets().put("navigationlink_previous", previous);

		HtmlCommandLink next = new HtmlCommandLink();
		t = new HtmlOutputText();
		t.setValueBinding("value", WFUtil.createValueBinding("#{" + bref + "navigation_next}"));
		next.getChildren().add(t);
		next.setId(ACTION_NEXT);
		next.addActionListener(this);
		getFacets().put("navigationlink_next", next);

		int pageOffset = 0;
		int currentPage = 1;
		if (getRows() > 0) {
			currentPage = getFirst() / getRows() + 1;
		}
		if (currentPage > MAX_NAVIGATION_LINKS) {
			pageOffset = currentPage - MAX_NAVIGATION_LINKS;
		}
		for (int i = 1; i <= MAX_NAVIGATION_LINKS; i++) {
			HtmlCommandLink gotoLink = new HtmlCommandLink();
			t = new HtmlOutputText();
			t.setValue(String.valueOf(i + pageOffset));
			gotoLink.getChildren().add(t);
			gotoLink.setId(ACTION_GOTO + i);
			WFUtil.addParameter(gotoLink, ACTION_GOTO, String.valueOf(i));			
			gotoLink.addActionListener(this);
			getFacets().put("navigationlink_goto" + i, gotoLink);			
		}
	}
	
	public boolean getRendersChildren(){
		//this component renders its children
		return true;
	}

	
	/**
	 * @return Returns the tableBodyStyleClass.
	 */
	public String getTableBodyStyleClass() {
		return this.tableBodyStyleClass;
	}

	
	/**
	 * @param tableBodyStyleClass The tableBodyStyleClass to set.
	 */
	public void setTableBodyStyleClass(String tableBodyStyleClass) {
		this.tableBodyStyleClass = tableBodyStyleClass;
	}

	
	/**
	 * @return Returns the tableHeadStyleClass.
	 */
	public String getTableHeadStyleClass() {
		return this.tableHeadStyleClass;
	}

	
	/**
	 * @param tableHeadStyleClass The tableHeadStyleClass to set.
	 */
	public void setTableHeadStyleClass(String tableHeadStyleClass) {
		this.tableHeadStyleClass = tableHeadStyleClass;
	}
	
	/**
	 * Enables scrolling to be done inside the tbody element.<br/>
	 * Special solution to handle IE is also applied.
	 */
	public void setBodyScrollable(boolean scroll){
		this.bodyScrollable=scroll;
		if(scroll){
			setListStyleClass(getListStyleClass()+" scrollContainer");
			//setListStyleClass("scrollContainer");
			//setId("scrollContainer");
			setStyleClass("scrollTable");
			setTableHeadStyleClass("fixedHeader");
		}
		else{

			//TODO: Handle disabling
		}
	}
	
	public boolean isBodyScrollable(){
		return this.bodyScrollable;
	}
}
