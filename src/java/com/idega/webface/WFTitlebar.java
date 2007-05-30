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
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 3978146556140729395L;
	private static String RENDERER_TYPE="wf_titlebar";
	public static String DEFAULT_STYLE_CLASS=RENDERER_TYPE;
	public static String DEFAULT_ICON_STYLE_CLASS="wf_titlebar_icon";
	public static String DEFAULT_TEXT_STYLE_CLASS="wf_titlebar_text";
	
	public static String getWFRendererType() {
		return RENDERER_TYPE;
	}
	
	private boolean viewWithTitleBar=true;

	//private WFMenu defaultToolbar;

//	private String titleText="Untitled";
	private String titlebarColor;
	private String titleTextColor;
	private String iconImageURI;
//	private boolean valueRefTitle = false;
//	private String toolTip;
	private String iconStyleClass = DEFAULT_ICON_STYLE_CLASS;
	
	public final static String FACET_DEFAULTTOOLBAR="wf_defaulttoolbar";
	public final static String FACET_EMBEDDEDTOOLBAR="wf_embeddedtoolbar";
	public final static String FACET_TEXT="wf_titlebar_text";
	
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
	}
	
	public WFTitlebar(HtmlOutputText text) {
		this();
		addTitleText(text);
	}
	
	protected void initializeDefaultToolbar(){
		WFMenu toolbar = new WFMenu();
//		toolbar.addButton(new WFHelpButton());
//		toolbar.addButton(new WFCloseButton());
		setDefaultToolbar(toolbar);
	}

	
	public void initializeComponent(FacesContext context){
		initializeDefaultToolbar();
	}
	
	/**
	 * @return
	 */
	public WFMenu getDefaultToolbar() {
		//return defaultToolbar;
		return (WFMenu)getFacets().get(FACET_DEFAULTTOOLBAR);
	}
	
	/**
	 * @return
	 */
	public boolean isViewWithTitleBar() {
		return this.viewWithTitleBar;
	}
	
	/**
	 * @param toolbar
	 */
	public void setDefaultToolbar(WFMenu toolbar) {
		//this.defaultToolbar = toolbar;
		//getChildren().add(toolbar);
		getFacets().put(FACET_DEFAULTTOOLBAR,toolbar);
	}

	/**
	 * @param b
	 */
	public void setViewWithTitleBar(boolean b) {
		this.viewWithTitleBar = b;
	}

//	/**
//	 * @return
//	 */
//	public String getTitleText() {
//		return titleText;
//	}

	public void addTitleText(String string) {
		HtmlOutputText title = WFUtil.getText(string);
		addTitleText(title);
	}
	
	public void addTitleText(String string, boolean valueRefTitle) {
		HtmlOutputText title = null;
		if (valueRefTitle) {
			title = WFUtil.getTextVB(string);
		} else {
			title = WFUtil.getText(string);
		}
		addTitleText(title);
	}
	
	public void addTitleText(HtmlOutputText text) {
		/*if (text.getStyleClass() == null) {
			text.setStyleClass(DEFAULT_TEXT_STYLE_CLASS);
		}*/
		WFContainer list = getTitleTextContainer();
		list.add(text);
//		getChildren().add(text);
//		getFacets().put("title", text);
	}
	
	public WFContainer getTitleTextContainer(){
		WFContainer list = (WFContainer) getFacets().get(FACET_TEXT);
		if (list == null) {
			list = new WFContainer();
			list.setStyleClass(DEFAULT_TEXT_STYLE_CLASS);
			getFacets().put(FACET_TEXT, list);
		}
		return list;
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
		return this.titlebarColor;
	}

	/**
	 * @return
	 */
	public String getTitleTextColor() {
		return this.titleTextColor;
	}

	/**
	 * @param string
	 */
	public void setTitlebarColor(String string) {
		this.titlebarColor = string;
	}

	/**
	 * @param string
	 */
	public void setTitleTextColor(String string) {
		this.titleTextColor = string;
	}

	/**
	 * @return Returns the embeddedToolbar.
	 */
	public WFMenu getEmbeddedToolbar() {
		return (WFMenu) getFacets().get(FACET_EMBEDDEDTOOLBAR);
	}

	/**
	 * @param embeddedToolbar The embeddedToolbar to set.
	 */
	public void setEmbeddedToolbar(WFMenu embeddedToolbar) {
		getFacets().put(FACET_EMBEDDEDTOOLBAR, embeddedToolbar);
	}

	/**
	 * @param embeddedToolbar The embeddedToolbar to set.
	 */
	public void removeEmbeddedToolbar() {
		getFacets().remove(FACET_EMBEDDEDTOOLBAR);
	}

	/**
	 * @return Returns the iconImageURI.
	 */
	public String getIconImageURI() {
		return this.iconImageURI;
	}

	public void setToolTip(String toolTip) {
		//this.toolTip = toolTip;
		getTitleTextContainer().setTitle(toolTip);
	}
	
	public String getToolTip() {
		//return toolTip;
		return getTitleTextContainer().getTitle();
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
		return this.iconStyleClass;
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
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
//		values[1] = toolTip;
		values[1] = this.iconStyleClass;
//		values[2] = new Boolean(valueRefTitle);
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
//		toolTip = (String) values[1];
		this.iconStyleClass = (String) values[1];
//		valueRefTitle = ((Boolean) values[2]).booleanValue();
	}

	//protected String getMarkupElementType(){
	//	return "table";
	//}
}
