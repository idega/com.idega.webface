/*
 * Created on 5.9.2003 by  tryggvil in project com.project
 */
package com.idega.webface;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * A menu component with toolbar buttons. 
 * Copyright (C) idega software 2003
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFToolbar extends WFContainer implements Serializable
{
	
	public WFToolbar(){
		setStyleClass("wf_toolbar");
	}

	
	public void addButton(WFToolbarButton button){
		this.getChildren().add(button);
	}

	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		Iterator children = this.getChildren().iterator();
		ResponseWriter out = context.getResponseWriter();
		out.startElement("tr",this);
		while (children.hasNext()) {
			WFToolbarButton element = (WFToolbarButton) children.next();
			out.startElement("td", null);
			renderChild(context, element);
			out.endElement("td");
		}
		out.endElement("tr");
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		super.encodeEnd(context);
	}

	protected String getMarkupElementType(){
		return "table";
	}
}
