/*
 * Created on 4.3.2004 by  tryggvil in project com.project
 */
package com.idega.webface;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionListener;

import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;

/**
 * WFUtil //TODO: tryggvil Describe class
 * Copyright (C) idega software 2004
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @author Anders Lindman
 * @version 1.0
 */
public class WFUtil {
	
	private static String BUNDLE_IDENTIFIER="com.idega.webface";
	
	public static IWBundle getBundle(){
		return getBundle(FacesContext.getCurrentInstance());
	}

	public static IWBundle getBundle(FacesContext context){
		return IWContext.getIWContext(context).getIWMainApplication().getBundle(BUNDLE_IDENTIFIER);
	}
	
	public static IWResourceBundle getResourceBundle(FacesContext context){
		return getBundle(context).getResourceBundle(context.getExternalContext().getRequestLocale());
	}
	
	public static IWResourceBundle getResourceBundle(){
		return getResourceBundle(FacesContext.getCurrentInstance());
	}
	
	/**
	 * Returns an html text component.
	 */
	public static HtmlOutputText getText(String s) {
		HtmlOutputText t = new HtmlOutputText();
		t.setValue(s);
		return t;
	}
	
	/**
	 * Returns an html text component with value binding.
	 */
	public static HtmlOutputText getTextVB(String ref) {
		HtmlOutputText t = new HtmlOutputText();
		t.setValueBinding("value", createValueBinding("#{" + ref + "}"));
		return t;
	}
	
	/**
	 * Returns an html text component with the specified id.
	 */
	public static HtmlOutputText getText(String id, String s) {
		HtmlOutputText t = getText(s);
		t.setId(id);
		return t;
	}
	
	/**
	 * Returns an html header text component.
	 */
	public static HtmlOutputText getHeaderText(String s) {
		HtmlOutputText t = new HtmlOutputText();
		t.setValue(s);
		t.setStyleClass("wf_headertext");
		return t;
	}
	
	/**
	 * Returns an html header text component with value binding.
	 */
	public static HtmlOutputText getHeaderTextVB(String ref) {
		HtmlOutputText t = new HtmlOutputText();
		t.setValueBinding("value", createValueBinding("#{" + ref + "}"));
		t.setStyleClass("wf_headertext");
		return t;
	}
	
	/**
	 * Returns an html header text component with the specified id.
	 */
	public static HtmlOutputText getHeaderText(String id, String s) {
		HtmlOutputText t = getHeaderText(s);
		t.setId(id);
		return t;
	}
	
	/**
	 * Returns an html text component with value binding to the localized key.
	 */
	public static HtmlOutputText getLocalizedText(String key) {
		HtmlOutputText t = new HtmlOutputText();
		t.setValueBinding("value", createValueBinding("#{" + WFPage.WF_BUNDLE + "." + key + "}"));
		return t;
	}

	/**
	 * Returns an html break tag.
	 */
	public static WFPlainTextOutput getBreak() {
		return new WFPlainTextOutput("<br>");
	}

	/**
	 * Returns html break tags.
	 */
	public static WFPlainTextOutput getBreak(int nrOfBreaks) {
		String s = "";
		for (int i = 0; i < nrOfBreaks; i++) {
			s += "<br>";
		}
		return new WFPlainTextOutput(s);
	}
	
	/**
	 * Returns an html command link.
	 */
	public static HtmlCommandLink getLink(String id, String value) {
		HtmlCommandLink l = new HtmlCommandLink();
		l.setId(id);
		l.setValue(value);
		return l;
	}
	
	/**
	 * Returns an html command list link.
	 */
	public static HtmlCommandLink getListLink(String id, String value) {
		HtmlCommandLink l = getLink(id, value);
		l.setStyleClass("wf_listlink");
		return l;
	}
	
	/**
	 * Returns an html command list link with value binding.
	 */
	public static HtmlCommandLink getListLinkVB(String ref) {
		HtmlCommandLink l = new HtmlCommandLink();
		l.setStyleClass("wf_listlink");
		l.setValueBinding("value", createValueBinding("#{" + ref + "}"));
		return l;
	}
	
	/**
	 * Returns an html list text with value binding.
	 */
	public static HtmlOutputText getListTextVB(String ref) {
		HtmlOutputText t = new HtmlOutputText();
		t.setStyleClass("wf_listtext");
		t.setValueBinding("value", createValueBinding("#{" + ref + "}"));
		return t;
	}
	
	/**
	 * Returns an html input text with value binding.
	 */
	public static HtmlInputText getInputText(String id, String ref) {
		HtmlInputText t = new HtmlInputText();
		t.setId(id);
		t.setValueBinding("value", createValueBinding("#{" + ref + "}"));
		setInputStyle(t);
		return t;
	}
	
	/**
	 * Returns an html text area component with value binding. 
	 */
	public static HtmlInputTextarea getTextArea(String id, String ref, String width, String height) {
		HtmlInputTextarea a = new HtmlInputTextarea();
		a.setId(id);
		a.setValueBinding("value", createValueBinding("#{" + ref + "}"));
		a.setStyle("width:" + width + ";height:" + height + ";");
		setInputStyle(a);
		return a;
	}
	
	/**
	 * Returns an html select one menu with value binding.
	 */
	public static HtmlSelectOneMenu getSelectOneMenu(String id, String itemsRef, String selectedItemRef) {
		HtmlSelectOneMenu m = new HtmlSelectOneMenu();
		m.setId(id);
		m.setValueBinding("value", createValueBinding("#{" + selectedItemRef + "}"));
		setInputStyle(m);
		UISelectItems items = new UISelectItems();
		items.setValueBinding("value", createValueBinding("#{" + itemsRef + "}"));
		m.getChildren().add(items);
		return m;
	}
	
	/**
	 * Returns an html command button.
	 */
	public static HtmlCommandButton getButton(String id, String value) {
		HtmlCommandButton b = new HtmlCommandButton();
		b.setId(id);
		b.setValue(value);
		setInputStyle(b);
		return b;
	}
	
	/**
	 * Returns an html command button.
	 */
	public static HtmlCommandButton getButton(String id, String value, ActionListener actionListener) {
		HtmlCommandButton b = getButton(id, value);
		b.addActionListener(actionListener);
		return b;
	}
	
	/**
	 * Returns the idega page header banner. 
	 */
	public static UIComponent getBannerBox() {
		WFContainer box = new WFContainer();
		box.setStyleClass("wf_bannerbox");
		
		HtmlGraphicImage logo = new HtmlGraphicImage();
		logo.setStyleClass("wf_bannerimg");
		logo.setUrl("icons/idegalogo_20px.gif  ");
		box.add(logo);
		
		WFContainer c = new WFContainer();
		c.setStyleClass("wf_bannertext");
		WFPlainTextOutput t = new WFPlainTextOutput();
		t.setValue(" idega<i>Web</i> Content   ");
		c.add(t);
		box.add(c);
		t = new WFPlainTextOutput();
		t.setValue(" "); // Microsoft Internet Explorer 5.2 for Mac fix
		box.add(t);
		
		return box;
	}

	/**
	 * Sets the default css class for the specified input component. 
	 */
	public static UIComponent setInputStyle(UIComponent component) {
		if (component instanceof HtmlInputText) {
			((HtmlInputText) component).setStyleClass("wf_inputcomponent");
		} else if (component instanceof HtmlInputTextarea) {
			((HtmlInputTextarea) component).setStyleClass("wf_inputcomponent");			
		} else if (component instanceof HtmlSelectOneMenu) {
			((HtmlSelectOneMenu) component).setStyleClass("wf_selectonemenu");			
		} else if (component instanceof HtmlCommandButton) {
			((HtmlCommandButton) component).setStyleClass("wf_button");			
		}
		return component;
	}
	
	/**
	 * Sets the default css class for the specified block for the specified child component. 
	 */
	public static WFBlock setBlockStyle(WFBlock block, UIComponent child) {
		if (child instanceof WFViewMenu) {
			block.setMainAreaStyleClass("wf_viewmenublockarea");
			block.setWidth("100%");
		}
		return block;
	}
	
	/**
	 * Adds a UIParameter to the specified component. 
	 */
	public static void addParameter(UIComponent component, String name, String value) {
		UIParameter p = new UIParameter();
		p.setName(name);
		p.setValue(value);			
		component.getChildren().add(p);		
	}
	
	/**
	 * Adds a UIParameter with value binding to the specified component. 
	 */
	public static void addParameterVB(UIComponent component, String name, String ref) {
		UIParameter p = new UIParameter();
		p.setName(name);
		p.setValueBinding("value", createValueBinding("#{" + ref + "}"));
		component.getChildren().add(p);		
	}
	
	/**
	 * Returns the value for the parameter with the specified name. 
	 */
	public static String getParameter(UIComponent component, String name) {
		List parameters = component.getChildren();
		for (int i = 0; i < parameters.size(); i++) {
			UIComponent child = (UIComponent) parameters.get(i);
			if (child instanceof UIParameter) {
				UIParameter p = (UIParameter) child;
				if (((String) p.getName()).equals(name)) {
					return (String) p.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * Render the specified facet.
	 */
	public static void renderFacet(FacesContext context, UIComponent component, String facetName) throws IOException {
		UIComponent facet = (UIComponent) (component.getFacets().get(facetName));
		if (facet != null) {
			facet.encodeBegin(context);
			facet.encodeChildren(context);
			facet.encodeEnd(context);
		}
	}
	
	/**
	 * Convenience method to retrieve the JSF application.
	 */
	public static Application getApplication() {
		ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
		Application app = appFactory.getApplication();
		return app;
	}
	
	/**
	 * Returns a value binding instance.
	 * @param ref Value binding expression 
	 */
	public static ValueBinding createValueBinding(String ref) {
		return getApplication().createValueBinding(ref);
	}
		
	/**
	 * Sets a value binding for the specified component.
	 */
	public static void setValueBinding(UIComponent component, String attributeName, String attribute) {
		component.setValueBinding(attributeName, createValueBinding("#{" + attribute + "}"));
	}
	
	/**
	 * Adds a message for the specified component. 
	 */
	public static void addMessage(UIComponent component, String message) {
		FacesMessage m = new FacesMessage(message);
		FacesContext ctx = FacesContext.getCurrentInstance();
		ctx.addMessage(component.getClientId(ctx), m);
	}
	
	/**
	 * Adds a localized message for the specified component. 
	 */
	public static void addLocalizedMessage(UIComponent component, String localizationKey) {
		addMessage(component, localize(localizationKey));
	}
	
	/**
	 * Returns the localized text for the specified key. 
	 */
	public static String localize(String localizationKey) {
		String text = localizationKey;
		FacesContext ctx = FacesContext.getCurrentInstance();
		Locale locale = ctx.getViewRoot().getLocale();
		locale = new Locale("sv", "SE"); //test		
		ResourceBundle bundle = null; // TODO: replace with IWResourceBundle
		String bundleName = "com.idega.webface.test.TestBundle";
		try {
			bundle = ResourceBundle.getBundle(bundleName, locale);
		} catch (MissingResourceException e) {
			System.out.println("Resource bundle '" + bundleName + "' could not be found.");
			return text;
		}
		String s = bundle.getString(localizationKey);
		if (s != null) {
			text = s;
		}
		return text;
	}
}
