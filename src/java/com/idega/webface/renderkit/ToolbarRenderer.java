/*
 * $Id: ToolbarRenderer.java,v 1.1 2004/12/28 13:55:13 eiki Exp $
 * Created on 25.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.renderkit;

import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.idega.webface.WFToolbarButton;


/**
 * 
 *  Last modified: $Date: 2004/12/28 13:55:13 $ by $Author: eiki $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public class ToolbarRenderer extends ContainerRenderer {
	
	
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context,UIComponent component) throws IOException {
		Iterator children = component.getChildren().iterator();
		ResponseWriter out = context.getResponseWriter();
		out.startElement("tr",component);
		while (children.hasNext()) {
			WFToolbarButton element = (WFToolbarButton) children.next();
			out.startElement("td", component);
			renderChild(context, element);
			out.endElement("td");
		}
		out.endElement("tr");
	}
	

	protected String getMarkupElementType(){
		return HTML_TABLE_TAG;
	}	
	
}
