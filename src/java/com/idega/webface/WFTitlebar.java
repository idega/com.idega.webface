/*
 * Created on 5.9.2003 by  tryggvil in project com.project
 */
package com.idega.webface;

import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.html.HtmlOutputText;

/**
 * WFTitlebar //TODO: tryggvil Describe class
 * Copyright (C) idega software 2003
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFTitlebar extends WFContainer
{
	
	private boolean viewWithTitleBar=true;

	/**
	 * 
	 * @uml.property name="defaultToolbar"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private WFToolbar defaultToolbar;

	/**
	 * 
	 * @uml.property name="embeddedToolbar"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private WFToolbar embeddedToolbar;

	private String titleText="Untitled";
	private String titlebarColor;
	private String titleTextColor;
	private String iconImageURI;
	
	public WFTitlebar(){
		setStyleClass("wf_titlebar");
		setIconImageURI("icons/view_default.gif");
		setDefaultToolbar();
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
	 * 
	 * @uml.property name="defaultToolbar"
	 */
	public WFToolbar getDefaultToolbar() {
		return defaultToolbar;
	}


	/**
	 * @return
	 */
	public boolean isViewWithTitleBar()
	{
		return viewWithTitleBar;
	}

	/**
	 * @param toolbar
	 * 
	 * @uml.property name="defaultToolbar"
	 */
	public void setDefaultToolbar(WFToolbar toolbar) {
		set(4, toolbar);
		this.defaultToolbar = toolbar;
	}

	/**
	 * @param b
	 * 
	 * @uml.property name="viewWithTitleBar"
	 */
	public void setViewWithTitleBar(boolean b) {
		viewWithTitleBar = b;
	}

	/**
	 * @return
	 * 
	 * @uml.property name="titleText"
	 */
	public String getTitleText() {
		return titleText;
	}

	/**
	 * @param string
	 * 
	 * @uml.property name="titleText"
	 */
	public void setTitleText(String string) {
		HtmlOutputText hText = new HtmlOutputText();
		hText.setValue(string);
		hText.setStyleClass("wf_titlebartext");
		set(1, hText);
		titleText = string;
	}

	/**
	 * @return
	 * 
	 * @uml.property name="titlebarColor"
	 */
	public String getTitlebarColor() {
		return titlebarColor;
	}

	/**
	 * @return
	 * 
	 * @uml.property name="titleTextColor"
	 */
	public String getTitleTextColor() {
		return titleTextColor;
	}

	/**
	 * @param string
	 * 
	 * @uml.property name="titlebarColor"
	 */
	public void setTitlebarColor(String string) {
		titlebarColor = string;
	}

	/**
	 * @param string
	 * 
	 * @uml.property name="titleTextColor"
	 */
	public void setTitleTextColor(String string) {
		titleTextColor = string;
	}

	/**
	 * @return Returns the embeddedToolbar.
	 * 
	 * @uml.property name="embeddedToolbar"
	 */
	public WFToolbar getEmbeddedToolbar() {
		return embeddedToolbar;
	}

	/**
	 * @param embeddedToolbar The embeddedToolbar to set.
	 * 
	 * @uml.property name="embeddedToolbar"
	 */
	public void setEmbeddedToolbar(WFToolbar embeddedToolbar) {
		set(3, embeddedToolbar);
		this.embeddedToolbar = embeddedToolbar;
	}

	/**
	 * @return Returns the iconImageURI.
	 * 
	 * @uml.property name="iconImageURI"
	 */
	public String getIconImageURI() {
		return iconImageURI;
	}

	/**
	 * @param iconImageURI The iconImageURI to set.
	 * 
	 * @uml.property name="iconImageURI"
	 */
	public void setIconImageURI(String iconImageURI) {
		UIGraphic graphic = new UIGraphic();
		graphic.setUrl(iconImageURI);
		set(1, graphic);
		this.iconImageURI = iconImageURI;
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
