/*
 * Created on 5.9.2003 by  tryggvil in project com.project
 */
package com.idega.webface;
import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
/**
 * IWApplicationToolButton //TODO: tryggvil Describe class Copyright (C) idega
 * software 2003
 * 
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson </a>
 * @version 1.0
 */
public class WFToolbarButton extends UICommand {
	private String defaultImageURI;
	private String toolTip;
	private String hoverImageURI;
	private String inactiveImageURI;
	private String pressedImageURI;
	public WFToolbarButton(String defaultImageURI) {
		this.setDefaultImageURI(defaultImageURI);
		UIGraphic defaultImage = new UIGraphic();
		defaultImage.setUrl(defaultImageURI);
		this.getChildren().add(defaultImage);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		out.startElement("a", this);
		out.writeAttribute("href", "?rugl", null);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		out.endElement("a");
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		Iterator children = this.getChildren().iterator();
		while (children.hasNext()) {
			UIComponent element = (UIComponent) children.next();
			renderChild(context, element);
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
}
