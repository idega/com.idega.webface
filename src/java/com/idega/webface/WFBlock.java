/*
 * Created on 5.9.2003 by  tryggvil in project com.project
 */
package com.idega.webface;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * WFBlock //TODO: tryggvil Describe class
 * Copyright (C) idega software 2003
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFBlock extends WFContainer implements ActionListener
{
	private final static String ACTION_BACK = "back"; // temp test
	
	private WFTitlebar titlebar;
	private WFToolbar toolbar;
	private WFContainer mainArea;
	private boolean toolbarEmbeddedInTitlebar=true;
	
	public WFBlock(){
		setWidth("300px");
		setHeight("300px");
		this.setStyleClass("wf_simplebox");
	}
	
	public WFBlock(String titleBarText){
		this();
		WFTitlebar titlebar = new WFTitlebar(titleBarText);
		setTitlebar(titlebar);
		setDefaultToolbar();
	}
	
	/**
	 * 
	 */
	private void setDefaultToolbar() {
		WFToolbar toolbar = new WFToolbar();
		this.setToolbar(toolbar);
		
		WFBackButton backButton = new WFBackButton();
		backButton.setId(ACTION_BACK);
		backButton.setValue("back button");
		backButton.addActionListener(this);
		toolbar.addButton(backButton);
		toolbar.addButton(new WFForwardButton());
		toolbar.addButton(new WFStopButton());
	}

	/**
	 * @return
	 */
	public WFTitlebar getTitlebar() {
		return titlebar;
	}

	/**
	 * @return
	 */
	public WFToolbar getToolbar() {
		return toolbar;
	}

	/**
	 * @param titlebar
	 */
	public void setTitlebar(WFTitlebar titlebar) {
		this.titlebar = titlebar;
		getChildren().add(titlebar);
	}

	/**
	 * @param toolbar
	 */
	public void setToolbar(WFToolbar toolbar) {
		this.toolbar = toolbar;
		titlebar.setEmbeddedToolbar(toolbar);
	}

	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		out.write("<link type=\"text/css\" href=\"style/webfacestyle.css\" rel=\"stylesheet\">");
		super.encodeBegin(context);
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
		this.toolbarEmbeddedInTitlebar = toolbarEmbeddedInTitlebar;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[3];
		values[0] = super.saveState(ctx);
		values[1] = titlebar;
		values[2] = toolbar;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		titlebar = (WFTitlebar) values[1];
		toolbar = (WFToolbar) values[2];
	}

	/**
	 * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
	 */
	public void processAction(ActionEvent event) throws AbortProcessingException {
		UIComponent source = event.getComponent();
		if (source.getId().equals(ACTION_BACK)) {
			System.out.println("EVENT: back button clicked");
			HtmlOutputText text = new HtmlOutputText();
			text.setValue("back ");
			source.getParent().getParent().getParent().getChildren().add(text);
		}
	}
}
