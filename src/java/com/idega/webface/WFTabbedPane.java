/*
 * $Id: WFTabbedPane.java,v 1.8 2007/05/30 15:09:18 gediminas Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.webface.event.WFTabEvent;
import com.idega.webface.event.WFTabListener;

/**
 * This is a class for a "Tab bar" which is event controlled and manages its Tabs and
 * each tabs "perspective".
 * A perspective can be any component that is rendered when
 * its tab bar button is pressed.   
 * <p>
 * Last modified: $Date: 2007/05/30 15:09:18 $ by $Author: gediminas $
 *
 * @author Anders Lindman,Tryggvi Larusson
 * @version $Revision: 1.8 $
 */
public class WFTabbedPane extends WFMenu implements ActionListener {
	
	//public static String RENDERER_TYPE="wf_tabbar";
	/*
	private String _taskbarStyleClass = "wf_tabbar";
	private String _buttonSelectedStyleClass = "wf_tabbarbuttonselected";
	private String _buttonDeselectedStyleClass = "wf_tabbarbuttondeselected";
	*/
	private String selectedTabViewStyleClass = "tabview";
	private boolean renderSelectedViewAsChild=true;
	
	/**
	 * Default contructor.
	 */
	public WFTabbedPane() {
		setTabStyle();
	}

	/**
	 * Returns the css class around the selected tab view if it is rendered out.
	 */
	public String getSelectedTabViewStyleClass() {
		return this.selectedTabViewStyleClass;
	}



	/**
	 * Sets the css class for the perspecitive main area container. 
	 */
	public void setSelectedTabViewStyleClass(String mainAreaStyleClass) {
		this.selectedTabViewStyleClass = mainAreaStyleClass;
	}

	/**
	 * Adds a tab button with its corresponding tabview component.
	 */
	public WFTab addTab(String menuItemId, String buttonLabel, UIComponent tabview, boolean isValueRef) {
		WFTab tab = new WFTab(buttonLabel, isValueRef);
		return setupTab(tab, menuItemId, tabview);
	}

	/**
	 * Adds a tab button with its corresponding tabview component.
	 * @param menuItemId
	 * @param buttonLabel simple string, or #{localizedString['...']}
	 * @param tabview
	 */
	public WFTab addTab(String menuItemId, String buttonLabel, UIComponent tabview) {
		WFTab tab = new WFTab(buttonLabel);
		return setupTab(tab, menuItemId, tabview);
	}
	
	private WFTab setupTab(WFTab tab, String menuItemId, UIComponent tabview) {
		tab.setId(menuItemId);
		tab.addActionListener(this);
		tab.setImmediate(true);
		
		tab.setStyleClass(menuItemId);
		//getFacets().put("button_" + menuItemId, button);
		setMenuItem(menuItemId,tab);
		//getFacets().put("perspective_" + menuItemId, c);
		setTabView(menuItemId,tabview);
		return tab;
	}
	
	public WFTab getTab(String menuItemId){
		return (WFTab)getMenuItem(menuItemId);
	}
	
	/**
	 * Sets the tabview component for the given menuItemId.<br>
	 * This tabview component is rendered when the button with id menuItemId is pressed.
	 * @param menuItemId
	 * @param perspective
	 */
	public void setTabView(String menuItemId,UIComponent viewComponent){
		//WFContainer container = new WFContainer();
		//container.setStyleClass(getMainAreaStyleClass());
		//container.add(viewComponent);
		
		getFacets().put("tabview_" + menuItemId, viewComponent);
	}
	
	
	public UIComponent getTabView(String menuItemId){
		return (UIComponent) getFacets().get("tabview_" + menuItemId);
	}
	
	public UIComponent getSelectedTabView(){
		return getTabView(getSelectedMenuItemId());
	}
	
	/*
	 *
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
		ResponseWriter out = context.getResponseWriter();
		// Taskbar buttons
		out.startElement("table", null);
		if (getTaskbarStyleClass() != null) {
			out.writeAttribute("class", getTaskbarStyleClass(), null);
		}
		out.writeAttribute("id", "" + getId(), null);
		out.startElement("tr", null);
		Iterator iter = _buttonIds.iterator();
		while (iter.hasNext()) {
			String buttonId = (String) iter.next();
			String buttonStyleClass = getButtonDeselectedStyleClass();
			WFTab button = (WFTab) getFacet("button_" + buttonId);
			if (buttonId.equals(_selectedButtonId)) {
				button.setSelected(true);
				buttonStyleClass = getButtonSelectedStyleClass();
			} else {
				button.setSelected(false);
			}
			out.startElement("td", null);
			if (buttonStyleClass != null) {
				out.writeAttribute("class", buttonStyleClass, null);
			}
			renderFacet(context, "button_" + buttonId);
			out.endElement("td");
		}
		out.endElement("tr");
		out.endElement("table");
	}
	*/
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		super.encodeChildren(context);
	}
	
	/**
	 * <p>
	 * Gets if to render the tabview component by default as a child.
	 * </p>
	 * @return
	 */
	public boolean getRenderSelectedTabViewAsChild() {
		return this.renderSelectedViewAsChild;
	}

	/**
	 * <p>
	 * TODO tryggvil describe method getRenderSelectedView
	 * </p>
	 * @return
	 */
	public void setRenderSelectedTabViewAsChild(boolean doRender) {
		this.renderSelectedViewAsChild=doRender;
	}

	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		super.encodeEnd(context);
	}
	
	public String getRendererType(){
		return getWFRendererType();
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[3];
		values[0] = super.saveState(ctx);
		values[1] = this.selectedTabViewStyleClass;
		values[2] = Boolean.valueOf(this.renderSelectedViewAsChild);
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		this.selectedTabViewStyleClass = (String) values[1];
		this.renderSelectedViewAsChild = ((Boolean)values[2]).booleanValue();
	}
	
	/**
	 * Register the specified listener for taskbar events.
	 */
	public void addTabListener(WFTabListener listener) {
		addFacesListener(listener);
	}

	/**
	 * Remove the specified j�listener for taskbar events.
	 */
	public void removeTabListener(WFTabListener listener) {
		removeFacesListener(listener);
	}

	/**
	 * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
	 */
	public void processAction(ActionEvent event) throws AbortProcessingException {
		WFTab button = (WFTab) event.getComponent();
		WFTabbedPane taskbar = (WFTabbedPane) button.getParent();
		taskbar.setSelectedMenuItemId(button.getId());
		WFTabEvent e = new WFTabEvent(taskbar);
		//(JJ) Cant do this since it will create a concurrent modification exception
		//taskbar.queueEvent(e);
        try
        {
        	taskbar.broadcast(e);
        }
        catch (AbortProcessingException e1)
        {
        	e1.printStackTrace();
        }
	}
	
}
