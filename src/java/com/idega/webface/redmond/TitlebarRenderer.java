/*
 * $Id: TitlebarRenderer.java,v 1.2 2004/11/03 18:44:14 joakim Exp $
 * Created on 25.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.redmond;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.idega.util.RenderUtils;
import com.idega.webface.WFTitlebar;


/**
 * 
 *  Last modified: $Date: 2004/11/03 18:44:14 $ by $Author: joakim $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.2 $
 */
public class TitlebarRenderer extends ContainerRenderer {
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context,UIComponent component) throws IOException {
		WFTitlebar titlebar = (WFTitlebar)component;
		ResponseWriter out = context.getResponseWriter();
		super.encodeBegin(context,component);
		out.startElement("tr", null);
		
		out.startElement("td", null);
		out.writeAttribute("width", "20", null); // TODO: fix css style
		out.startElement("img", null);
		out.writeAttribute("src", titlebar.getIconImageURI(), null);
		out.endElement("img");
		out.endElement("td");
		out.startElement("td", null);
		out.writeAttribute("width", "100%", null);
		RenderUtils.renderFacet(context, component, "title");
//		out.startElement("font", null);
//		out.writeAttribute("class", "wf_titlebartext", null);
//		out.write(getTitleText());
//		out.endElement("font");
		out.endElement("td");
		out.startElement("td", null);
		out.writeAttribute("nowrap", "true", null);
		out.write("");
		renderFacet(context, component, "toolbar");
	}	
	
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context,UIComponent component) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		out.endElement("td");
		out.endElement("tr");
		super.encodeEnd(context,component);
	}
	
	
	protected String getMarkupElementType(){
		//TODO: Change to DIV
		return HTML_TABLE_TAG;
	}
	
}
