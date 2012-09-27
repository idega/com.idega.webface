/*
 * Created on 4.3.2004 by  tryggvil in project com.project
 */
package com.idega.webface;

import java.util.List;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
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
import javax.faces.context.FacesContextFactory;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.myfaces.el.unified.resolver.FacesCompositeELResolver;
import org.apache.myfaces.el.unified.resolver.FacesCompositeELResolver.Scope;

import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWMainApplication;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.webface.htmlarea.HTMLArea;

/**
 * <p>
 * This is a class with various utility methods when working with JSF.
 * </p>
 * Last modified: $Date: 2009/01/30 07:35:06 $ by $Author: valdas $
 *
 * @author Anders Lindman,<a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version $Revision: 1.41 $
 */
public class WFUtil {

	public static String BUNDLE_IDENTIFIER="com.idega.webface";

	public static final String EXPRESSION_BEGIN="#{";
	public static final String EXPRESSION_END="}";
	public static final String VALUE_STRING="value";

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
	 * <p>
	 * Returns an html text component. The String s can be either a full valuebinding expression
	 * {myBeanId.myProperty} or a regular text String set as the value.
	 * </p>
	 */
	public static HtmlOutputText getText(String s) {
		HtmlOutputText t = new HtmlOutputText();
		if(isValueBinding(s)){
			t.setValueBinding(VALUE_STRING, createValueBinding(s));
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
		t.setValueExpression(VALUE_STRING, createValueExpression(FacesContext.getCurrentInstance().getELContext(), getExpression(ref), String.class));
		return t;
	}

	/**
	 * Returns an html text component with value binding.
	 */
	public static HtmlOutputText getTextVB(String bundleIdentifier,String localizationKey) {
		HtmlOutputText t = new HtmlOutputText();
		setLocalizedValue(t, bundleIdentifier, localizationKey);
		return t;
	}

	/**
	 * Returns an html header text component.
	 */
	public static HtmlOutputText getHeaderText(String s) {
		HtmlOutputText t = getText(s);
		t.setStyleClass("wf_headertext");
		return t;
	}

	/**
	 * Returns an html header text component with value binding.
	 */
	public static HtmlOutputText getHeaderTextVB(String ref) {
		HtmlOutputText t = getTextVB(ref);
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
		if (isValueBinding(value)) {
			l.setValueBinding(VALUE_STRING, createValueBinding(value));
		}
		else {
			l.setValue(value);
		}
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
		return getHtmlAreaTextArea(id, ref, width, height, false);
	}

	/**
	 * Returns an html text area component with value binding reference, and height and width
	 */
	public static HTMLArea getHtmlAreaTextArea(String id, String ref, String width, String height, boolean addWebfaceCss) {
		HTMLArea a = new HTMLArea(addWebfaceCss);
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

	public static ActionListener getActionListener(ELContext elContext, String expression) {
		return getMethodExpressionForActionListener(elContext, expression);
	}

	public static MethodExpressionActionListener getMethodExpressionForActionListener(ELContext elContext, String expression) {
		return new MethodExpressionActionListener(getMethodExpression(elContext, expression, null, new Class[] {ActionEvent.class}));
	}

	public static MethodExpression getMethodExpression(ELContext elContext, String expression, Class<?> resultType, Class<?>[] parameters) {
		return getApplication().getExpressionFactory().createMethodExpression(elContext, getExpression(expression), resultType, parameters);
	}

	/**
	 * Returns an html command button.
	 */
	public static HtmlCommandButton getButton(String id, String value) {
		HtmlCommandButton b = (HtmlCommandButton) FacesContext.getCurrentInstance().getApplication().createComponent(HtmlCommandButton.COMPONENT_TYPE);

		if (id != null) {
			b.setId(id);
		}
		if (value != null) {
			b.setValue(value);
		}
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
		HtmlCommandButton b = getButton(id, null);

		b.setValueBinding(VALUE_STRING, createValueBinding(getExpression(ref)));

		return b;
	}

	/**
	 * Returns an html command button with value binding text label.
	 */
	public static HtmlCommandButton getButtonVB(String id, String bundleIdentifier, String localizationKey) {
		HtmlCommandButton b = getButton(id, null);
		setLocalizedValue(b, bundleIdentifier, localizationKey);
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
		p.setValueExpression(VALUE_STRING, createValueExpression(FacesContext.getCurrentInstance().getELContext(), getExpression(ref), String.class));
		component.getChildren().add(p);
	}

	/**
	 * Adds a UIParameter with value binding to the specified component.
	 */
	public static void addParameterVB(UIComponent component, String name, String bundleIdentifier, String localizationKey) {
		UIParameter p = new UIParameter();
		p.setName(name);
		setLocalizedValue(component, bundleIdentifier, localizationKey);
		component.getChildren().add(p);
	}

	/**
	 * Returns the value for the parameter with the specified name.
	 */
	public static String getParameter(UIComponent component, String name) {
		List<UIComponent> parameters = component.getChildren();
		for (int i = 0; i < parameters.size(); i++) {
			UIComponent child = parameters.get(i);
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

	public static ValueExpression createValueExpression(ELContext elContext, String ref, Class<?> expectedReturnType) {
		return IWMainApplication.getDefaultIWMainApplication().createValueExpression(elContext, ref, expectedReturnType);
	}

	/**
	 * Creates value binding expression for given key
	 * @param bundleIdentifier
	 * @param localizationKey
	 * @return a String #{localizedStrings['bundle']['key']}
	 */
	public static String getLocalizedStringExpr(String bundleIdentifier, String localizationKey) {
		return "#{localizedStrings['"+bundleIdentifier+"']['"+localizationKey+"']}";
	}

	/**
	 * Creates value binding for localized string, and sets it on component's "value"
	 */
	public static void setLocalizedValue(UIComponent comp, String bundleIdentifier, String localizationKey) {
		String expr = getLocalizedStringExpr(bundleIdentifier, localizationKey);
		comp.setValueBinding(VALUE_STRING, createValueBinding(expr));
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
		String idFor = component.getClientId(ctx);
		if (idFor.endsWith(WFMessages.MESSAGE_COMPONENT_ID_ENDING)) {
			idFor = idFor.substring(0, idFor.lastIndexOf(WFMessages.MESSAGE_COMPONENT_ID_ENDING));
		}
		ctx.addMessage(idFor, m);
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
		String valueBinding = getLocalizedStringExpr(bundleIdentifier, localizationKey);
		ValueBinding vb = WFUtil.createValueBinding(valueBinding);
		addMessage(component, (String) vb.getValue(FacesContext.getCurrentInstance()));
	}

	/**
	 * Adds a message with value binding for the specified component.
	 */
	public static void addErrorMessageVB(UIComponent component, String bundleIdentifier,String localizationKey) {
		String valueBinding = getLocalizedStringExpr(bundleIdentifier, localizationKey);
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
		ValueBinding vb = WFUtil.createValueBinding(getExpression(ref));
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

	@SuppressWarnings("unchecked")
	public static <T>T invoke(FacesContext fc, String beanId, String completeMethodName, Object value, Class<T> resultType) {
		MethodExpression me = WFUtil.getMethodExpression(fc.getELContext(), "#{"+beanId+"."+completeMethodName+"}", resultType,
				new Class[] {value.getClass()});
		Object o = me.invoke(fc.getELContext(), new Object[] {value});
		if (o != null && o.getClass().getName().equals(resultType.getName()))
			return (T) o;

		return null;
	}

	public static void invoke(FacesContext fc, String beanId, String completeMethodName, Object value) {
		invoke(fc, beanId, completeMethodName, value, Void.class);
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
    public static boolean isValueBinding(String value) {
        if (value == null)
        	return false;

        int start = value.indexOf(EXPRESSION_BEGIN);
        if (start < 0)
        	return false;

        int end = value.lastIndexOf('}');
        return (end >=0 && start < end);
    }

    /**
     * Creates the expression syntax, i.e. wraps the beanReference String around with #{ and },
     * if it does not have them already
	 *
     * @param beanReference
     * @return
     */
    public static String getExpression(String beanReference){
    	String exp = beanReference;
    	if (!isValueBinding(beanReference)) {
    		exp = new StringBuilder(EXPRESSION_BEGIN).append(beanReference).append(EXPRESSION_END).toString();
    	}
    	return exp;
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
    public static <T>T getBeanInstance(FacesContext context, String beanId) {
    	doEnsureScopeIsSet(context);

		String expr = getExpression(beanId);
		ValueExpression ve = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), expr, Object.class);
		Object o = ve.getValue(context.getELContext());
		@SuppressWarnings("unchecked")
		T bean = (T) o;
    	return bean;
    }

    public static <T> T getBeanInstance(String beanId) {
   		return getBeanInstance(FacesContext.getCurrentInstance(), beanId);
    }

    private static final void doEnsureScopeIsSet(FacesContext context) {
    	if (context.getExternalContext().getRequestMap().get(FacesCompositeELResolver.SCOPE) == null)
    		context.getExternalContext().getRequestMap().put(FacesCompositeELResolver.SCOPE, Scope.Faces);
    }

    public static <T> T getBeanInstance(String beanId, Class<T> beanType) {
    	FacesContext context = FacesContext.getCurrentInstance();
    	doEnsureScopeIsSet(context);

	    String expr = getExpression(beanId);
	    ELContext elContext = context.getELContext();
	    ValueExpression ve = createValueExpression(elContext, expr, beanType);
	    @SuppressWarnings("unchecked")
		T bean = (T) ve.getValue(elContext);
	    return bean;
    }

    /**
     * creates faces context. might be used in the filter for example.
     * reference used: http://www.thoughtsabout.net/blog/archives/000033.html
     * @param request
     * @param response
     * @return FacesContext without viewroot. Doesn't place faces context to current thread.
     */
    public static FacesContext createFacesContext(ServletContext servletContext, ServletRequest request, ServletResponse response) {
    	FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext == null) {
			FacesContextFactory contextFactory = (FacesContextFactory)FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
			LifecycleFactory lifecycleFactory = (LifecycleFactory)FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
			Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

			  // Either set a private member servletContext = filterConfig.getServletContext();
			  // in you filter init() method or set it here like this:
			  // ServletContext servletContext = ((HttpServletRequest)request).getSession().getServletContext();
			  // Note that the above line would fail if you are using any other protocol than http

			  // Doesn't set this instance as the current instance of FacesContext.getCurrentInstance
			  facesContext = contextFactory.getFacesContext(servletContext, request, response, lifecycle);

			  // Set using our inner class
			  //InnerFacesContext.setFacesContextAsCurrentInstance(facesContext);

			  // set a new viewRoot, otherwise context.getViewRoot returns null
			  //UIViewRoot view = facesContext.getApplication().getViewHandler().createView(facesContext, "yourOwnID");
			  //facesContext.setViewRoot(view);
		}

		return facesContext;
    }
}