/*
 * Created on 5.9.2003 by  tryggvil in project com.project
 */
package com.idega.webface;

import javax.faces.component.UIComponent;


/**
 * A menu component with toolbar buttons. 
 * Copyright (C) idega software 2003
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFToolbar extends WFContainer //implements Serializable
{
	
	public static String RENDERER_TYPE="wf_toolbar";
	public static String DEFAULT_STYLE_CLASS=RENDERER_TYPE;
	
	public WFToolbar(){
		setStyleClass(DEFAULT_STYLE_CLASS);
	}

	
	public void addButton(WFToolbarButton button){
		this.getChildren().add(button);
	}
	
	public void addButton(UIComponent button){
		this.getChildren().add(button);
	}
	
	/*
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
	}
	
	/**
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
	
	public void encodeEnd(FacesContext context) throws IOException {
		super.encodeEnd(context);
	}

	protected String getMarkupElementType(){
		return "table";
	}*/
	
	public String getRendererType(){
		return RENDERER_TYPE;
	}
}
