/*
 * Created on 5.9.2003 by  tryggvil in project com.project
 */
package com.idega.webface;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * WFBlock //TODO: tryggvil Describe class
 * Copyright (C) idega software 2003
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFBlock extends WFContainer
{

	/**
	 * 
	 * @uml.property name="titlebar"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private WFTitlebar titlebar;

	/**
	 * 
	 * @uml.property name="toolbar"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private WFToolbar toolbar;

	/**
	 * 
	 * @uml.property name="mainArea"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private WFContainer mainArea;

	private boolean toolbarEmbeddedInTitlebar=true;
	
	public WFBlock(){
		setDefaultTitlebar();
		setWidth("200px");
		setHeight("300px");
		this.setStyleClass("wf_simplebox");
	}
	
	public WFBlock(String titleBarText){
		this();
		this.getTitlebar().setTitleText(titleBarText);
	}
	
	/**
	 * 
	 */
	private void setDefaultTitlebar() {
		
		//WFToolbar toolbar = new WFToolbar();
		//this.setToolbar(toolbar);
		
		
		WFToolbar toolbar = new WFToolbar();
		this.setToolbar(toolbar);
		this.getTitlebar().getDefaultToolbar().setWidth("60px");
		
		toolbar.addButton(new WFBackButton());
		toolbar.addButton(new WFForwardButton());
		toolbar.addButton(new WFStopButton());
		toolbar.setWidth("100px");
	}

	/**
	 * @return
	 * 
	 * @uml.property name="titlebar"
	 */
	public WFTitlebar getTitlebar() {
		if (titlebar == null) {
			setTitlebar(new WFTitlebar());
		}
		return titlebar;
	}

	/**
	 * @return
	 * 
	 * @uml.property name="toolbar"
	 */
	public WFToolbar getToolbar() {
		if (toolbar == null) {
			setToolbar(new WFToolbar());
		}
		return toolbar;
	}

	/**
	 * @param titlebar
	 * 
	 * @uml.property name="titlebar"
	 */
	public void setTitlebar(WFTitlebar titlebar) {
		this.titlebar = titlebar;
		this.set(0, titlebar);
	}

	/**
	 * @param toolbar
	 * 
	 * @uml.property name="toolbar"
	 */
	public void setToolbar(WFToolbar toolbar) {
		this.toolbar = toolbar;
		if (this.isToolbarEmbeddedInTitlebar()) {
			getTitlebar().setEmbeddedToolbar(toolbar);
		} else {
			this.set(1, toolbar);
		}
	}

	
	

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext ctx) throws IOException {
		ResponseWriter out = ctx.getResponseWriter();
		out.write("<link type=\"text/css\" href=\"/style/webfacestyle.css\" rel=\"stylesheet\">");
		// TODO Auto-generated method stub
		super.encodeBegin(ctx);
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		// TODO Auto-generated method stub
		super.encodeChildren(context);
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext arg0) throws IOException {
		// TODO Auto-generated method stub
		super.encodeEnd(arg0);
	}
	/**
	 * @return Returns the toolbarEmbeddedInTitlebar.
	 */
	public boolean isToolbarEmbeddedInTitlebar() {
		return toolbarEmbeddedInTitlebar;
	}

	/**
	 * @param toolbarEmbeddedInTitlebar The toolbarEmbeddedInTitlebar to set.
	 * 
	 * @uml.property name="toolbarEmbeddedInTitlebar"
	 */
	public void setToolbarEmbeddedInTitlebar(boolean toolbarEmbeddedInTitlebar) {
		this.toolbarEmbeddedInTitlebar = toolbarEmbeddedInTitlebar;
	}

	
	
	protected void set(int index,UIComponent comp){
		//if(getChildren().size()<index){
				getChildren().add(comp);
				//getChildren().add(index,comp);
		/*}
		else{
			if(getChildren().get(index)==null){
				getChildren().add(index,comp);
			}
			else{
				getChildren().set(index,comp);
			}
		}*/
	}
}
