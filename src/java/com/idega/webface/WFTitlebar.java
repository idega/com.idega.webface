/*
 * Created on 5.9.2003 by  tryggvil in project com.project
 */
package com.idega.webface;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * WFTitlebar //TODO: tryggvil Describe class
 * Copyright (C) idega software 2003
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFTitlebar extends WFContainer implements Serializable 
{
	private boolean viewWithTitleBar=true;

	private WFToolbar defaultToolbar;

	private String titleText="Untitled";
	private String titlebarColor;
	private String titleTextColor;
	private String iconImageURI;
	private boolean localizedTitle = false;
	
	public WFTitlebar(){
		setStyleClass("wf_titlebar");
		setIconImageURI("icons/view_default.gif");
	}
	
	public WFTitlebar(String text){
		this();
		setTitleText(text);
	}
	
	protected void setDefaultToolbar(){
		WFToolbar toolbar = new WFToolbar();
		toolbar.addButton(new WFHelpButton());
		toolbar.addButton(new WFCloseButton());
		setDefaultToolbar(toolbar);
	}

	/**
	 * @return
	 */
	public WFToolbar getDefaultToolbar() {
		return defaultToolbar;
	}
	
	/**
	 * @return
	 */
	public boolean isViewWithTitleBar() {
		return viewWithTitleBar;
	}
	
	/**
	 * @param toolbar
	 */
	public void setDefaultToolbar(WFToolbar toolbar) {
		this.defaultToolbar = toolbar;
		getChildren().add(toolbar);
	}

	/**
	 * @param b
	 */
	public void setViewWithTitleBar(boolean b) {
		viewWithTitleBar = b;
	}

	/**
	 * @return
	 */
	public String getTitleText() {
		return titleText;
	}

	/**
	 * @param string
	 */
	public void setTitleText(String string) {
		HtmlOutputText title = null;
		if (localizedTitle) {
			title = WFUtil.getLocalizedText(string);
		} else {
			title = WFUtil.getText(string);
		}
		title.setStyleClass("wf_titlebartext");
		getFacets().put("title", title);
		titleText = string;
	}
	
	/**
	 * @return
	 */
	public boolean isLocalizedTitle() {
		return localizedTitle;
	}

	/**
	 * 
	 */
	public void setLocalizedTitle(boolean b) {
		localizedTitle = b;
		setTitleText(getTitleText());
	}
	
	/**
	 * @return
	 */
	public String getTitlebarColor() {
		return titlebarColor;
	}

	/**
	 * @return
	 */
	public String getTitleTextColor() {
		return titleTextColor;
	}

	/**
	 * @param string
	 */
	public void setTitlebarColor(String string) {
		titlebarColor = string;
	}

	/**
	 * @param string
	 */
	public void setTitleTextColor(String string) {
		titleTextColor = string;
	}

	/**
	 * @return Returns the embeddedToolbar.
	 */
	public WFToolbar getEmbeddedToolbar() {
		return (WFToolbar) getFacets().get("toolbar");
	}

	/**
	 * @param embeddedToolbar The embeddedToolbar to set.
	 */
	public void setEmbeddedToolbar(WFToolbar embeddedToolbar) {
		getFacets().put("toolbar", embeddedToolbar);
	}

	/**
	 * @param embeddedToolbar The embeddedToolbar to set.
	 */
	public void removeEmbeddedToolbar() {
		getFacets().remove("toolbar");
	}

	/**
	 * @return Returns the iconImageURI.
	 */
	public String getIconImageURI() {
		return iconImageURI;
	}

	/**
	 * @param iconImageURI The iconImageURI to set.
	 */
	public void setIconImageURI(String iconImageURI) {
		this.iconImageURI = iconImageURI;
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		super.encodeBegin(context);
		out.startElement("tr", null);
		out.startElement("td", null);
		out.writeAttribute("width", "20", null); // TODO: fix css style
		out.startElement("img", null);
		out.writeAttribute("src", getIconImageURI(), null);
		out.endElement("td");
		out.startElement("td", null);
		out.writeAttribute("width", "100%", null);
		WFUtil.renderFacet(context, this, "title");
//		out.startElement("font", null);
//		out.writeAttribute("class", "wf_titlebartext", null);
//		out.write(getTitleText());
//		out.endElement("font");
		out.endElement("td");
		out.startElement("td", null);
		out.writeAttribute("nowrap", "true", null);
		out.write("");
		renderFacet(context, "toolbar");
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
		ResponseWriter out = context.getResponseWriter();
		out.endElement("td");
		out.endElement("tr");
		super.encodeEnd(context);
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[3];
		values[0] = super.saveState(ctx);
		values[1] = titleText;
		values[2] = new Boolean(localizedTitle);
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		titleText = (String) values[1];
		localizedTitle = ((Boolean) values[2]).booleanValue();
	}

	protected String getMarkupElementType(){
		return "table";
	}
}
