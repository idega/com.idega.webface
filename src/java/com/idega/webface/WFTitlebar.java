/*
 * Created on 5.9.2003 by  tryggvil in project com.project
 */
package com.idega.webface;

import java.io.Serializable;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

/**
 * WFTitlebar //TODO: tryggvil Describe class
 * Copyright (C) idega software 2003
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFTitlebar extends WFContainer implements Serializable 
{
	public static String RENDERER_TYPE="wf_titlebar";
	public static String DEFAULT_STYLE_CLASS=RENDERER_TYPE;
	public static String DEFAULT_ICON_STYLE_CLASS="wf_titlebar_icon";
	
	private boolean viewWithTitleBar=true;

	private WFToolbar defaultToolbar;

//	private String titleText="Untitled";
	private String titlebarColor;
	private String titleTextColor;
	private String iconImageURI;
//	private boolean valueRefTitle = false;
	private String toolTip;
	private String iconStyleClass = DEFAULT_ICON_STYLE_CLASS;
	
	public WFTitlebar(){
		setStyleClass(DEFAULT_STYLE_CLASS);
		setIconImageURI("icons/view_default.gif");
	}
	
	public WFTitlebar(String text){
		this(text, false);
	}

	public WFTitlebar(String text, boolean isVB){
		this();
		addTitleText(text, isVB);
		getFacets().put("titlebar", this);
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

//	/**
//	 * @return
//	 */
//	public String getTitleText() {
//		return titleText;
//	}

	public void addTitleText(String string) {
		addTitleText(string, false);
	}
	/**
	 * @param string
	 */
	public void addTitleText(String string, boolean valueRefTitle) {
		HtmlOutputText title = null;
		if (valueRefTitle) {
			title = WFUtil.getTextVB(string);
		} else {
			title = WFUtil.getText(string);
		}
		addTitleText(title);
//		titleText = string;
	}
	
	public void addTitleText(HtmlOutputText text) {
		if (text.getStyleClass() == null) {
			text.setStyleClass("wf_titlebartext");
		}
		WFContainer list = (WFContainer) getFacets().get("title");
		if (list == null) {
			list = new WFContainer();
			getFacets().put("title", list);
		}
		list.add(text);
//		getChildren().add(text);
//		getFacets().put("title", text);
	}
	
//	/**
//	 * @return
//	 */
//	public boolean isValueRefTitle() {
//		return valueRefTitle;
//	}


//	public void setValueRefTitle(boolean b) {
//		valueRefTitle = b;
//		setTitleText(getTitleText());
//	}
	
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

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	
	public String getToolTip() {
		return toolTip;
	}
	
	/**
	 * @param iconImageURI The iconImageURI to set.
	 */
	public void setIconImageURI(String iconImageURI) {
		String uriWithBundle = WFUtil.getBundle().getResourcesVirtualPath()+"/"+iconImageURI;
		this.iconImageURI = uriWithBundle;
	}
	
	public String getRendererType(){
		return RENDERER_TYPE;
	}
	
	public void setIconStyleClass(String iconStyleClass) {
		this.iconStyleClass = iconStyleClass;
	}
	
	public String getIconStyleClass() {
		return iconStyleClass;
	}
	
	/*
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
		RenderUtils.renderFacet(context, this, "title");
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

	public void encodeChildren(FacesContext context) throws IOException {		
		super.encodeChildren(context);
	}
	

	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		out.endElement("td");
		out.endElement("tr");
		super.encodeEnd(context);
	}*/
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[3];
		values[0] = super.saveState(ctx);
		values[1] = toolTip;
		values[2] = iconStyleClass;
//		values[2] = new Boolean(valueRefTitle);
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		toolTip = (String) values[1];
		iconStyleClass = (String) values[2];
//		valueRefTitle = ((Boolean) values[2]).booleanValue();
	}

	//protected String getMarkupElementType(){
	//	return "table";
	//}
}
