/*
 * Created on 5.9.2003 by  tryggvil in project com.project
 */
package com.idega.webface;


/**
 * IWApplicationToolbar //TODO: tryggvil Describe class
 * Copyright (C) idega software 2003
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFToolbar extends WFContainer
{
	
	public WFToolbar(){
		addDefaultButtons();
		setStyleClass("wf_toolbar");
	}

	/**
	 * 
	 */
	private void addDefaultButtons() {
		// TODO Auto-generated method stub
		
	}
	
	public void addButton(WFToolbarButton button){
		this.getChildren().add(button);
	}
	
	/*public void encodeChildren(FacesContext context) throws IOException {
		Iterator children = this.getChildren().iterator();
		ResponseWriter out = context.getResponseWriter();
		out.startElement("tr",this);
		while (children.hasNext()) {
			WFToolbarButton element = (WFToolbarButton) children.next();
			
			//renderChild(context,element);
		}
		out.endElement("tr");
	}*/
	
}
