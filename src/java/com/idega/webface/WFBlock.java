/*
 * Created on 5.9.2003 by  tryggvil in project com.project
 */
package com.idega.webface;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import com.idega.util.RenderUtils;

/**
 * Component with title bar and container area.
 * Copyright (C) idega software 2003
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @author Anders Lindman
 * @version 1.0
 */
public class WFBlock extends WFContainer
{
	
	public static String RENDERER_TYPE="wf_block";
	private boolean isInitialized=false;
	
	private boolean toolbarEmbeddedInTitlebar=true;
	private String mainAreaStyleClass = WFConstants.STYLE_CLASS_MAINAREA;
	public WFBlock(){
		this("untitled");
	}
	
	public WFBlock(String titleBarText){

		setStyleClass(WFConstants.STYLE_CLASS_BOX);
		setMainAreaStyleClass(WFConstants.STYLE_CLASS_MAINAREA);
		
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
		toolbar.addButton(new WFHelpButton());
		toolbar.addButton(new WFCloseButton());
	}

	
	/**
	 * This method is intended to be implemented in subclasses to add components to mainArea
	 */
	protected void initializeContent(){
		//does nothing by default
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

	public String getRendererType(){
		return RENDERER_TYPE;
	}
	
	public void encodeBegin(FacesContext context) throws IOException {
		if(!isInitialized){
			this.initializeContent();
			this.isInitialized=true;
		}
	}
	
	/*
	public void encodeBegin(FacesContext context) throws IOException {
		if (!isRendered()) {
			return;
		}
		super.encodeBegin(context);
		if (mainAreaStyleClass != null) {
			WFContainer mainArea = (WFContainer) getChildren().get(0);
			mainArea.setStyleClass(mainAreaStyleClass);
		}
		if (!isToolbarEmbeddedInTitlebar()) {
			renderFacet(context, "toolbar");
		}
		renderFacet(context, "titlebar");
	}*/
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		if (!isRendered()) {
			return;
		}
		super.encodeChildren(context);
		//for (Iterator iter = getChildren().iterator(); iter.hasNext();) {
		//	UIComponent child = (UIComponent) iter.next();
		//	RenderUtils.renderChild(context,child);
		//}
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		if (!isRendered()) {
			return;
		}
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
		Object values[] = new Object[4];
		values[0] = super.saveState(ctx);
		values[1] = new Boolean(toolbarEmbeddedInTitlebar);
		values[2] = mainAreaStyleClass;
		values[3] = new Boolean(isInitialized);
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
		isInitialized = ((Boolean) values[3]).booleanValue();
	}
}
