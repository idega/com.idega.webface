/*
 * Created on 5.9.2003 by  tryggvil in project com.project
 */
package com.idega.webface;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * WFBlock //TODO: tryggvil Describe class
 * Copyright (C) idega software 2003
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFBlock extends WFContainer
{
	private WFContainer mainArea;
	private boolean toolbarEmbeddedInTitlebar=true;
	private String mainAreaStyleClass = null;
	
	public WFBlock(){
		setWidth("300px");
//		setHeight("300px");
		setStyleClass("wf_simplebox");
		setMainAreaStyleClass("wf_blockmainarea");
	}
	
	public WFBlock(String titleBarText){
		this();
		WFTitlebar titlebar = new WFTitlebar(titleBarText);
		setTitlebar(titlebar);
		setDefaultToolbar();
		WFContainer mainArea = new WFContainer();
		super.add(mainArea);
	}
	
	/**
	 * 
	 */
	private void setDefaultToolbar() {
		WFToolbar toolbar = new WFToolbar();
		this.setToolbar(toolbar);
		
		toolbar.addButton(new WFBackButton());
		toolbar.addButton(new WFForwardButton());
//		toolbar.addButton(new WFStopButton());
		toolbar.addButton(new WFHelpButton());
		toolbar.addButton(new WFCloseButton());
	}

	/**
	 * @return
	 */
	public WFTitlebar getTitlebar() {
		return (WFTitlebar) getFacets().get("titlebar");
	}

	/**
	 * @return
	 */
	public WFToolbar getToolbar() {
		WFToolbar toolbar = null;
		if (isToolbarEmbeddedInTitlebar()) {
			if (getTitlebar() != null) {
				toolbar = getTitlebar().getEmbeddedToolbar();
			}
		} else {
			toolbar = (WFToolbar) getFacets().get("toolbar");
		}
		return toolbar;
	}

	/**
	 * @param titlebar
	 */
	public void setTitlebar(WFTitlebar titlebar) {
		getFacets().put("titlebar", titlebar);
	}

	/**
	 * @param toolbar
	 */
	public void setToolbar(WFToolbar toolbar) {
		if (isToolbarEmbeddedInTitlebar()) {
			if (getTitlebar() != null) {
				getFacets().remove("toolbar");
				getTitlebar().setEmbeddedToolbar(toolbar);
			}
		} else {
			if (getTitlebar() != null) {
				getTitlebar().removeEmbeddedToolbar();
			}			
			getFacets().put("toolbar", toolbar);
		}
	}

	/**
	 * Sets the css style class for the main area in this block. 
	 */
	public void setMainAreaStyleClass(String mainAreaStyleClass) {
		this.mainAreaStyleClass = mainAreaStyleClass;
	}
	
	/**
	 * Returns the css style class  for the main area in this block. 
	 */
	public String getMainAreaStyleClass() {
		return mainAreaStyleClass;
	}
	
	/**
	 * Adds a child component to this block.
	 */
	public void add(UIComponent child) {
		WFContainer mainArea = (WFContainer) getChildren().get(0);
		if (mainArea != null) {
			mainArea.add(child);
		}
	}

	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
		if (mainAreaStyleClass != null) {
			WFContainer mainArea = (WFContainer) getChildren().get(0);
			mainArea.setStyleClass(mainAreaStyleClass);
		}
		if (!isToolbarEmbeddedInTitlebar()) {
			renderFacet(context, "toolbar");
		}
		renderFacet(context, "titlebar");
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {		
		super.encodeChildren(context);
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		super.encodeEnd(context);
	}
	
	/**
	 * @return Returns the toolbarEmbeddedInTitlebar.
	 */
	public boolean isToolbarEmbeddedInTitlebar() {
		return toolbarEmbeddedInTitlebar;
	}

	/**
	 * @param toolbarEmbeddedInTitlebar the toolbarEmbeddedInTitlebar to set
	 */
	public void setToolbarEmbeddedInTitlebar(boolean toolbarEmbeddedInTitlebar) {
		WFToolbar toolbar = getToolbar();
		this.toolbarEmbeddedInTitlebar = toolbarEmbeddedInTitlebar;
		if (toolbar != null) {
			setToolbar(toolbar);
		}
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[3];
		values[0] = super.saveState(ctx);
		values[1] = new Boolean(toolbarEmbeddedInTitlebar);
		values[2] = mainAreaStyleClass;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		toolbarEmbeddedInTitlebar = ((Boolean) values[1]).booleanValue();
		mainAreaStyleClass = (String) values[2];
	}
}
