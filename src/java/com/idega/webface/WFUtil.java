/*
 * Created on 4.3.2004 by  tryggvil in project com.project
 */
package com.idega.webface;

import java.util.List;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.UISelectItems;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlSelectManyListbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionListener;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.webface.htmlarea.HTMLArea;

/**
 * <p>
 * This is a class with various utility methods when working with JSF.
 * </p>
 * Last modified: $Date: 2006/01/04 14:43:11 $ by $Author: tryggvil $
 *
 * @author Anders Lindman,<a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version $Revision: 1.30 $
 */
public class WFUtil {
	
	private static String bundle_identifier="com.idega.webface";

	public static final String EXPRESSION_BEGIN="#{";
	public static final String EXPRESSION_END="}";
	public static final String VALUE_STRING="value";
	
	public static IWBundle getBundle(){
		return getBundle(FacesContext.getCurrentInstance());
	}
	
	/**
	 * This is only temporary. Will be moved to content bundle
	 * @return
	 */
	public static IWBundle getContentBundle(){
		//TODO: Move this to com.idega.content
		FacesContext context = FacesContext.getCurrentInstance();
		return IWContext.getIWContext(context).getIWMainApplication().getBundle("com.idega.content");
	}

	public static IWBundle getBundle(FacesContext context){
		return IWContext.getIWContext(context).getIWMainApplication().getBundle(bundle_identifier);
	}
	
	public static IWResourceBundle getResourceBundle(FacesContext context){
		return getBundle(context).getResourceBundle(context.getExternalContext().getRequestLocale());
	}
	
	public static IWResourceBundle getResourceBundle(){
		return getResourceBundle(FacesContext.getCurrentInstance());
	}
	
	/**
	 * <p>
	 * Returns an html text component. The String s can be either a full valuebinding expression 
	 * {myBeanId.myProperty} or a regular text String set as the value. 
	 * </p>
	 */
	public static HtmlOutputText getText(String s) {
		HtmlOutputText t = new HtmlOutputText();
		if(isValueBinding(s)){
			t.setValueBinding(VALUE_STRING, createValueBinding( s ));
		}
		else{
			t.setValue(s);
		}
		return t;
	}
	
	/**
	 * <p>
	 * Returns an html text component with a style class. The String s can be either a full valuebinding expression 
	 * {myBeanId.myProperty} or a regular text String set as the value. 
	 * </p>
	 */
	public static HtmlOutputText getText(String s, String styleClass) {
		HtmlOutputText t = new HtmlOutputText();
		t.setValue(s);
		t.setStyleClass(styleClass);
		return t;
	}
	
	/**
	 * Returns an html text component with value binding.
	 */
	public static HtmlOutputText getTextVB(String ref) {
		HtmlOutputText t = new HtmlOutputText();
		t.setValueBinding(VALUE_STRING, createValueBinding(getExpression(ref)));
		return t;
	}
	
	/**
	 * Returns an html text component with value binding.
	 */
	public static HtmlOutputText getTextVB(String bundleIdentifier,String localizationKey) {
		HtmlOutputText t = new HtmlOutputText();
		IWContext iwContext = IWContext.getInstance();
		if(iwContext!=null){
			IWBundle bundle = iwContext.getIWMainApplication().getBundle(bundleIdentifier);
			if(bundle!=null){
				t.setValueBinding(VALUE_STRING,bundle.getValueBinding(localizationKey));
			}
		}
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
		t.setValueBinding(VALUE_STRING, createValueBinding(getExpression(ref)));
		t.setStyleClass("wf_headertext");
		return t;
	}
	
	public static HtmlOutputText getHeaderTextVB(String bundleIdentifier,String localizationKey) {
		HtmlOutputText t = getTextVB(bundleIdentifier, localizationKey);
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
	 * Returns an html break tag.
	 */
	public static WFPlainOutputText getBreak() {
		return new WFPlainOutputText("<br/>");
	}

	/**
	 * Returns html break tags.
	 */
	public static WFPlainOutputText getBreak(int nrOfBreaks) {
		String s = "";
		for (int i = 0; i < nrOfBreaks; i++) {
			s += "<br/>";
		}
		return new WFPlainOutputText(s);
	}
	
	/**
	 * Returns an html command link.
	 */
	public static HtmlCommandLink getLink(String id, String value) {
		HtmlCommandLink l = new HtmlCommandLink();
		l.setId(id);
		l.getChildren().add(getText(value));
		return l;
	}
	
	/**
	 * Returns an html command link with the specified action listener added.
	 */
	public static HtmlCommandLink getLink(String id, String value, ActionListener actionListener) {
		HtmlCommandLink l = getLink(id, value);
		l.addActionListener(actionListener);
		return l;
	}
	
	/**
	 * Returns an html command link with value binding.
	 */
	public static HtmlCommandLink getLinkVB(String id, String ref, ActionListener actionListener) {
		HtmlCommandLink l = new HtmlCommandLink();
		if(id!=null && !"".equals(id)){
			l.setId(id);
		}
		l.getChildren().add(getTextVB(ref));
		if (actionListener != null) {
			l.addActionListener(actionListener);
		}
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
		l.getChildren().add(getTextVB(ref));
		return l;
	}
	
	/**
	 * Returns an html list text with value binding.
	 */
	public static HtmlOutputText getListTextVB(String ref) {
		HtmlOutputText t = new HtmlOutputText();
		t.setStyleClass("wf_listtext");
		t.setValueBinding(VALUE_STRING, createValueBinding(getExpression(ref)));
		return t;
	}
	
	/**
	 * Returns an html list text with value binding.
	 */
	public static HtmlOutputText getListTextVB(String bundleIdentifier,String localizationKey) {
		HtmlOutputText t = getTextVB(bundleIdentifier, localizationKey);
		t.setStyleClass("wf_listtext");
		return t;
	}
	
	/**
	 * Returns an html input text with value binding.
	 */
	public static HtmlInputText getInputText(String id, String ref) {
		HtmlInputText t = new HtmlInputText();
		t.setId(id);
		t.setValueBinding(VALUE_STRING, createValueBinding(getExpression(ref)));
		setInputStyle(t);
		return t;
	}
	
	/**
	 * Returns an html date input with value binding.
	 */
	public static WFDateInput getDateInput(String id, String ref) {
		WFDateInput di = new WFDateInput();
		di.setId(id);
		di.setValueBinding(VALUE_STRING, createValueBinding(getExpression(ref)));
		setInputStyle(di);
		return di;
	}
	
	/**
	 * Returns an html text area component with value binding. 
	 */
	public static HtmlInputTextarea getTextArea(String id, String ref, String width, String height) {
		HtmlInputTextarea a = new HtmlInputTextarea();
		a.setId(id);
		a.setValueBinding(VALUE_STRING, createValueBinding(getExpression(ref)));
		a.setStyle("width:" + width + ";height:" + height + ";");
		setInputStyle(a);
		return a;
	}
	
	/**
	 * Returns an html text area component with value binding reference. 
	 */
	public static HTMLArea getHtmlAreaTextArea(String id, String ref){
		return getHtmlAreaTextArea(id,ref,null,null);
	}
	
	/**
	 * Returns an html text area component with value binding reference, and height and width
	 */
	public static HTMLArea getHtmlAreaTextArea(String id, String ref, String width, String height) {
		HTMLArea a = new HTMLArea();
		a.setId(id);
		a.setValueBinding(VALUE_STRING, createValueBinding(getExpression(ref)));
		String styleString = null;
		if(width!=null){
			if(styleString==null){
				styleString="";
			}
			styleString = "width:" + width+";";
		}
		if(height!=null){
			if(styleString==null){
				styleString="";
			}
			styleString += "height:" + height + ";";
		}
		if(styleString!=null){
			a.setStyle(styleString);
		}
		//a.setStyle("width:" + width + ";height:" + height + ";");
		setInputStyle(a);
		return a;
	}
	
	/**
	 * Returns an html select one menu with value binding.
	 */
	public static HtmlSelectOneMenu getSelectOneMenu(String id, String itemsRef, String selectedItemRef) {
		HtmlSelectOneMenu m = new HtmlSelectOneMenu();
		m.setId(id);
		m.setValueBinding(VALUE_STRING, createValueBinding(getExpression(selectedItemRef)));
		setInputStyle(m);
		UISelectItems items = new UISelectItems();
		items.setValueBinding(VALUE_STRING, createValueBinding(getExpression(itemsRef)));
		m.getChildren().add(items);
		return m;
	}
	
	/**
	 * Returns an html select many listbox with value binding.
	 */
	public static HtmlSelectManyListbox getSelectManyListbox(String id, String itemsRef, String selectedItemsRef) {
		HtmlSelectManyListbox m = new HtmlSelectManyListbox();
		m.setId(id);
		m.setValueBinding(VALUE_STRING, createValueBinding(getExpression(selectedItemsRef)));
		setInputStyle(m);
		UISelectItems items = new UISelectItems();
		items.setValueBinding(VALUE_STRING, createValueBinding(getExpression(itemsRef)));
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
	 * Returns an html command button with the specified action listener added.
	 */
	public static HtmlCommandButton getButton(String id, String value, ActionListener actionListener) {
		HtmlCommandButton b = getButton(id, value);
		b.addActionListener(actionListener);
		return b;
	}
	
	/**
	 * Returns an html command button with value binding text label.
	 */
	public static HtmlCommandButton getButtonVB(String id, String ref) {
		HtmlCommandButton b = new HtmlCommandButton();
		b.setId(id);
		b.setValueBinding(VALUE_STRING, createValueBinding(getExpression(ref)));
		setInputStyle(b);
		return b;
	}
	
	/**
	 * Returns an html command button with value binding text label.
	 */
	public static HtmlCommandButton getButtonVB(String id, String bundleIdentifier, String localizationKey) {
		HtmlCommandButton b = new HtmlCommandButton();
		b.setId(id);
		String valueBinding = "#{localizedStrings['"+bundleIdentifier+"']['"+localizationKey+"']}";
		b.setValueBinding(VALUE_STRING,createValueBinding(valueBinding));
		setInputStyle(b);
		return b;
	}
	
	/**
	 * Returns an html command button with value binding text label and with the specified action listener added.
	 */
	public static HtmlCommandButton getButtonVB(String id, String key, ActionListener actionListener) {
		HtmlCommandButton b = getButtonVB(id, key);
		b.addActionListener(actionListener);
		return b;
	}
	
	/**
	 * Returns an html command button with value binding text label and with the specified action listener added.
	 */
	public static HtmlCommandButton getButtonVB(String id, String bundleIdentifier, String localizationKey, ActionListener actionListener) {
		HtmlCommandButton b = getButtonVB(id, bundleIdentifier, localizationKey);
		b.addActionListener(actionListener);
		return b;
	}
	
	/**
	 * Returns the two specified components as one component group.
	 */
	public static HtmlPanelGroup group(UIComponent c1, UIComponent c2) {
		HtmlPanelGroup g = new HtmlPanelGroup();
		g.getChildren().add(c1);
		g.getChildren().add(c2);
		return g;
	}

	/**
	 * Returns the idega page header banner. 
	 */
	public static UIComponent getBannerBox() {
		WFContainer box = new WFContainer();
		box.setStyleClass("wf_bannerbox");
		
		//HtmlGraphicImage logo = new HtmlGraphicImage();
		//logo.setStyleClass("wf_bannerimg");
		//logo.setUrl("icons/idegalogo_20px.gif");
		//box.add(logo);
		
		WFContainer c = new WFContainer();
		c.setStyleClass("wf_bannertext");
		WFPlainOutputText t = new WFPlainOutputText();
		t.setValue("idega<i>Web</i> Content");
		c.add(t);
		box.add(c);
		t = new WFPlainOutputText();
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
		p.setValueBinding(VALUE_STRING, createValueBinding(getExpression(ref)));
		component.getChildren().add(p);		
	}
	
	/**
	 * Adds a UIParameter with value binding to the specified component. 
	 */
	public static void addParameterVB(UIComponent component, String name, String bundleIdentifier, String localizationKey) {
		UIParameter p = new UIParameter();
		p.setName(name);
		String valueBinding = "#{localizedStrings['"+bundleIdentifier+"']['"+localizationKey+"']}";
		p.setValueBinding(VALUE_STRING,createValueBinding(valueBinding));
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
				if (p.getName().equals(name)) {
					return p.getValue().toString();
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns the integer value for the parameter with the specified name. 
	 */
	public static int getIntParameter(UIComponent component, String name) {
		int value = -1;
		try {
			value = Integer.parseInt(getParameter(component, name));
		} catch (Exception e) {}
		return value;
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
	 * Returns a method binding instance.
	 * @param ref Method binding expression
	 * @param params parameter classes 
	 */
	public static MethodBinding createMethodBinding(String ref, Class[] params) {
		return getApplication().createMethodBinding(ref, params);
	}
		
	/**
	 * Sets a value binding for the specified component.
	 */
	public static void setValueBinding(UIComponent component, String attributeName, String attribute) {
		component.setValueBinding(attributeName, createValueBinding(getExpression(attribute)));
	}
	
	/**
	 * Sets a value binding for the specified component.
	 */
	public static void setValueBindingToArray(UIComponent component, String attributeName, String attribute, int index) {
		component.setValueBinding(attributeName, createValueBinding("#{"+attribute+"["+index+"]}"));
	}
	
	/**
	 * Adds a message for the specified component. 
	 */
	public static void addMessage(UIComponent component, String message) {
		addMessage(component,message,null,null);
	}

	/**
	 * Adds a message for the specified component. 
	 */
	public static void addErrorMessage(UIComponent component, String message) {
		addErrorMessage(component,message,null,null);
	}
	
	/**
	 * Adds a message for the specified component. 
	 */
	public static void addErrorMessage(UIComponent component, String message,String detail,String summary) {
		addMessage(component,message,detail,FacesMessage.SEVERITY_ERROR);
	}

	
	/**
	 * Adds a message for the specified component. 
	 */
	public static void addMessage(UIComponent component, String summary,String detail,FacesMessage.Severity severity) {
		FacesMessage m = new FacesMessage();
		if(detail!=null){
			m.setDetail(detail);
		}
		if(summary!=null){
			m.setSummary(summary);
		}
		if(severity!=null){
			m.setSeverity(severity);
		}
		FacesContext ctx = FacesContext.getCurrentInstance();
		ctx.addMessage(component.getClientId(ctx), m);
	}
	
	/**
	 * Adds a message with value binding for the specified component. 
	 */
	public static void addMessageVB(UIComponent component, String ref) {
		ValueBinding vb = WFUtil.createValueBinding(getExpression(ref));
		addMessage(component, (String) vb.getValue(FacesContext.getCurrentInstance()));
	}
	
	/**
	 * Adds a message with value binding for the specified component. 
	 */
	public static void addMessageVB(UIComponent component, String bundleIdentifier,String localizationKey) {
		String valueBinding = "#{localizedStrings['"+bundleIdentifier+"']['"+localizationKey+"']}";
		ValueBinding vb = WFUtil.createValueBinding(valueBinding);
		addMessage(component, (String) vb.getValue(FacesContext.getCurrentInstance()));
	}
	
	/**
	 * Adds a message with value binding for the specified component. 
	 */
	public static void addErrorMessageVB(UIComponent component, String bundleIdentifier,String localizationKey) {
		String valueBinding = "#{localizedStrings['"+bundleIdentifier+"']['"+localizationKey+"']}";
		ValueBinding vb = WFUtil.createValueBinding(valueBinding);
		addErrorMessage(component, (String) vb.getValue(FacesContext.getCurrentInstance()));
	}
	
	/**
	 * Sets the current view root.
	 */
	public static void setViewRoot(String viewId) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = WFUtil.getApplication().getViewHandler().createView(context, viewId);
		if (root != null) {
			context.setViewRoot(root);	
		}
	}
	
	/**
	 * Returns the value from the specified value binding reference. 
	 */
	public static Object getValue(String ref) {
		ValueBinding vb = WFUtil.createValueBinding("#{" + ref + "}");
		return vb.getValue(FacesContext.getCurrentInstance());		
	}
	
	/**
	 * Returns a value by calling a method in a managed bean. 
	 */
	public static Object getValue(String beanId, String methodName) {
		return getValue(beanId + "." + methodName);		
	}
	
	/**
	 * Returns a string value by calling a method in a managed bean. 
	 */
	public static String getStringValue(String beanId, String methodName) {
		return (String) getValue(beanId, methodName);		
	}
	
	/**
	 * Returns a boolean value by calling a method in a managed bean. 
	 */
	public static boolean getBooleanValue(String beanId, String methodName) {
		return ((Boolean) getValue(beanId, methodName)).booleanValue();		
	}
	
	/**
	 * Invokes a method in a managed bean. 
	 */
	public static Object invoke(String beanId, String methodName, Object value, Class parameterClass) {
		MethodBinding mb = WFUtil.createMethodBinding("#{" + beanId + "." + methodName + "}", 
				new Class[] { parameterClass });
		return mb.invoke(FacesContext.getCurrentInstance(), new Object[] { value });
	}
	
	/**
	 * Invokes a method in a managed bean. 
	 */
	public static Object invoke(String beanId, String methodName, Object value) {
		return invoke(beanId, methodName, value, value.getClass());
	}
	
	/**
	 * Invokes a method in a managed bean. 
	 */
	public static Object invoke(String completeBinding) {
		MethodBinding mb = WFUtil.createMethodBinding(completeBinding, null); 
		return mb.invoke(FacesContext.getCurrentInstance(), null);
	}
	/**
	 * Invokes a method in a managed bean. 
	 */
	public static Object invoke(String beanId, String methodName) {
		String reference = beanId + "." + methodName;
		String expression = getExpression(reference);
		MethodBinding mb = WFUtil.createMethodBinding(expression, null); 
		return mb.invoke(FacesContext.getCurrentInstance(), null);
	}
	
	/**
	 * Invokes a method in a managed bean. 
	 */
	public static Object invoke(String beanId, String methodName, Object value1, Object value2) {
		String reference = beanId + "." + methodName;
		String expression = getExpression(reference);
		MethodBinding mb = WFUtil.createMethodBinding(expression, 
				new Class[] { value1.getClass(), value2.getClass() });
		return mb.invoke(FacesContext.getCurrentInstance(), new Object[] { value1, value2 });
	}
	
	/**
	 * Invokes a method in a managed bean. 
	 */
	public static Object invoke(String beanId, String methodName, Object[] values, Class[] params) {
		String reference = beanId + "." + methodName;
		String expression = getExpression(reference);
		MethodBinding mb = WFUtil.createMethodBinding(expression, params);
		return mb.invoke(FacesContext.getCurrentInstance(), values);
	}
	
	/**
	 * <p>
	 * A method to check if the passed String is a valuebinding expression,
	 * i.e. a string in the format '#{MyBeanId.myProperty}'
	 * </p>
	 * @param any String
	 * @return
	 */
    public static boolean isValueBinding(String value)
    {
        if (value == null) return false;
        
        int start = value.indexOf(EXPRESSION_BEGIN);
        if (start < 0) return false;
        
        int end = value.lastIndexOf('}');
        return (end >=0 && start < end);
    }

    /**
     * <p>
     * Creates the expression syntax, i.e. wraps the beanReference String around with #{ and }
     * </p>
     * @param beanReference
     * @return
     */
    public static String getExpression(String beanReference){
    		return EXPRESSION_BEGIN+beanReference+EXPRESSION_END;
    }
    
    /**
     * <p>
     * This method finds a bean instance from a given beanId.<br/>
     * Goes first to request scope, then to session scope and finally to application scope
     * and returns the first found.
     * </p>
     * @param beanId
     * @return
     */
    public static Object getBeanInstance(String beanId) {
	    	FacesContext context = FacesContext.getCurrentInstance();
	    	Object bean = context.getExternalContext().getRequestMap().get(beanId);
		String expr= getExpression(beanId);
		ValueBinding vb = context.getApplication().createValueBinding(expr);
		bean = vb.getValue(context);
	    	return bean;
    }
    
    
}
