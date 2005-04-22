/*
 * Created on 5.9.2003 by  tryggvil in project com.project
 */
package com.idega.webface;
import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;

import com.idega.idegaweb.IWBundle;
import com.idega.webface.event.WFToolbarButtonPressedListener;

/**
 * Button component used in WFToolbar. 
 * software 2003
 * 
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson </a>
 * @version 1.0
 */
public class WFToolbarButton extends UICommand {
	private String defaultImageURI;
	private String toolTip;
	private String displayText;
	private String hoverImageURI;
	private String inactiveImageURI;
	private String pressedImageURI;
	private String styleClass;
	
	public WFToolbarButton() {
		// Default contstructor, for JSF
	}

	public WFToolbarButton(String defaultImageURI, IWBundle bundle) {
		String uri = bundle.getResourcesVirtualPath()+"/"+defaultImageURI;
		this.setDefaultImageURI(uri);
	}
	
	public WFToolbarButton(String defaultImageURI) {
		String uriWithBundle = WFUtil.getBundle().getResourcesVirtualPath()+"/"+defaultImageURI;
		this.setDefaultImageURI(uriWithBundle);
	}
	
	public void setListener(WFToolbarButtonPressedListener listener) {
		this.addFacesListener(listener);
	}
	
	/**
	 * @return Returns the defaultImageURI.
	 * 
	 * @uml.property name="defaultImageURI"
	 */
	public String getDefaultImageURI() {
		return defaultImageURI;
	}

	/**
	 * @param defaultImageURI
	 *            The defaultImageURI to set.
	 * 
	 * @uml.property name="defaultImageURI"
	 */
	public void setDefaultImageURI(String defaultImageURI) {
		this.defaultImageURI = defaultImageURI;
	}

	/**
	 * @return Returns the hoverImageURI.
	 * 
	 * @uml.property name="hoverImageURI"
	 */
	public String getHoverImageURI() {
		return hoverImageURI;
	}

	/**
	 * @param hoverImageURI
	 *            The hoverImageURI to set.
	 * 
	 * @uml.property name="hoverImageURI"
	 */
	public void setHoverImageURI(String hoverImageURI) {
		this.hoverImageURI = hoverImageURI;
	}

	/**
	 * @return Returns the inactiveImageURI.
	 * 
	 * @uml.property name="inactiveImageURI"
	 */
	public String getInactiveImageURI() {
		return inactiveImageURI;
	}

	/**
	 * @param inactiveImageURI
	 *            The inactiveImageURI to set.
	 * 
	 * @uml.property name="inactiveImageURI"
	 */
	public void setInactiveImageURI(String inactiveImageURI) {
		this.inactiveImageURI = inactiveImageURI;
	}

	/**
	 * @return Returns the pressedImageURI.
	 * 
	 * @uml.property name="pressedImageURI"
	 */
	public String getPressedImageURI() {
		return pressedImageURI;
	}

	/**
	 * @param pressedImageURI
	 *            The pressedImageURI to set.
	 * 
	 * @uml.property name="pressedImageURI"
	 */
	public void setPressedImageURI(String pressedImageURI) {
		this.pressedImageURI = pressedImageURI;
	}

	/**
	 * @return Returns the toolTip.
	 * 
	 * @uml.property name="toolTip"
	 */
	public String getToolTip() {
		return toolTip;
	}

	/**
	 * @param toolTip
	 *            The toolTip to set.
	 * 
	 * @uml.property name="toolTip"
	 */
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	
	public String getDisplayText() {
		if(displayText != null) return displayText;
		ValueBinding binding = getValueBinding("displayText");
		return (binding!=null)?(String)binding.getValue(getFacesContext()):null;
	}

	public void setDisplayText(String text) {
		this.displayText = text;
	}
	
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
	public String getStyleClass() {
		return styleClass;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter out = context.getResponseWriter();

		String buttonId = getClientId(context);
		String imageId = buttonId + "_img";
		
		out.startElement("input", null);
		out.writeAttribute("id", buttonId, null);
		out.writeAttribute("type", "hidden", null);
		out.writeAttribute("name", buttonId, null);
		out.writeAttribute("value", "false", null);
		out.writeAttribute("style", "display:none;", null);
		out.endElement("input");

		String formName = determineFormName(this);
		if (getDefaultImageURI() != null) {
			out.startElement("img", null);
			out.writeAttribute("src", getDefaultImageURI(), null);
			out.writeAttribute("id", imageId, null);
			if (formName == null) {
				throw new IOException("Toolbars should be nested in a UIForm !");
			}
			if (toolTip != null) {
				out.writeAttribute("alt", toolTip, null);
				out.writeAttribute("title", toolTip, null);
			}
			if (getPressedImageURI() != null) {
				String onmousedown = "this.src='" + getPressedImageURI() +"'";
				out.writeAttribute("onmousedown", onmousedown, null);
			}
			String onmouseup = "document.forms['" + formName + "'].elements['" + buttonId + 
			"'].value='true';document.forms['" + formName + "'].submit();";
			out.writeAttribute("onmouseup", onmouseup, null);
			String onmouseout = "document.forms['" + formName + "'].elements['" + buttonId + "'].value='';this.src='" + 
					getDefaultImageURI() + "'";
			out.writeAttribute("onmouseout", onmouseout, null);
			out.endElement("img");
		} else {
			out.startElement("a", null);
			String onmouseup = "document.forms['" + formName + "'].elements['" + buttonId + 
			"'].value='true';document.forms['" + formName + "'].submit();";
			out.writeAttribute("onmouseup", onmouseup, null);
			
			if (styleClass != null) {
				out.writeAttribute("class", styleClass, null);
			}
			if (toolTip != null) {
				out.writeAttribute("alt", toolTip, null);
				out.writeAttribute("title", toolTip, null);
			}
			
			out.writeAttribute("href","#",null);
			
			String text = getDisplayText();
			if(text != null){
				out.write(text);
			}
			
			out.endElement("a");
		}
	}
		
	/**
	 * Renders a child component for the current component. This operation is
	 * handy when implementing renderes that perform child rendering themselves
	 * (eg. a layout renderer/grid renderer/ etc..). Passes on any IOExceptions
	 * thrown by the child/child renderer.
	 * 
	 * @param context
	 *            the current FacesContext
	 * @param child
	 *            which child to render
	 */
	public void renderChild(FacesContext context, UIComponent child) throws IOException {
		child.encodeBegin(context);
		child.encodeChildren(context);
		child.encodeEnd(context);
	}
	
	/**
	 * @see javax.faces.component.UIComponent#decode(javax.faces.context.FacesContext)
	 */
	public void decode(FacesContext context) {
		String buttonId = getClientId(context);
		String inputValue =	(String) context.getExternalContext().getRequestParameterMap().get(buttonId);
		if (inputValue != null && inputValue.equals("true")) {
			ActionEvent event = new ActionEvent(this);
			queueEvent(event);
		}
	}
	
	/**
	 * Determines the client id of the form in which a component is enclosed.
	 * Useful for generating submitForm('xyz') javascripts...
	 * 
	 * @return
	 */
	public static String determineFormName(UIComponent component) {
		String ret = null;
		UIComponent current = component.getParent();
		UIComponent form = null;
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		while(current != null) {
			if(current instanceof UIForm) {
				form = current;
				break;
			}
			current = current.getParent();
		}
		if(form==null){
			UIComponent root = ctx.getViewRoot();
			form = findFormDown(root);
		}
		
		if(form != null) {
			ret = form.getClientId(ctx);
		}
		
		return ret;
	}
	
	
	public static UIForm findFormDown(UIComponent component){
		for (Iterator iterator = component.getFacetsAndChildren(); iterator.hasNext();) {
			UIComponent child = (UIComponent) iterator.next();
			if(child instanceof UIForm){
				return (UIForm)child;
			}
			else{
				return findFormDown(child);
			}
		}
		return null;
	}
	

	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[7];
		values[0] = super.saveState(ctx);
		values[1] = defaultImageURI;
		values[2] = hoverImageURI;
		values[3] = inactiveImageURI;
		values[4] = pressedImageURI;
		values[5] = toolTip;
		values[6] = displayText;
		return values;
	}

	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(ctx, values[0]);
		defaultImageURI = (String) values[1];
		hoverImageURI = (String)  values[2];
		inactiveImageURI = (String) values[3];
		pressedImageURI = (String) values[4];
		toolTip = (String) values[5];
		displayText = (String) values[6];
	}
}
