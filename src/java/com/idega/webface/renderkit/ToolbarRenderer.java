/*
 * $Id: ToolbarRenderer.java,v 1.2 2005/02/02 03:01:09 tryggvil Exp $
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
import com.idega.webface.WFContainer;


/**
 * 
 *  Last modified: $Date: 2005/02/02 03:01:09 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.2 $
 */
public class ToolbarRenderer extends ContainerRenderer {
	
	
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context,UIComponent component) throws IOException {
		Iterator children = component.getChildren().iterator();
		ResponseWriter out = context.getResponseWriter();
		
		
		//out.startElement("tr",component);
		while (children.hasNext()) {
			UIComponent child = (UIComponent) children.next();
			//WFToolbarButton child = (WFToolbarButton) children.next();
			//out.startElement("td", component);
			renderChild(context, child);
			//out.endElement("td");
		}
		//out.endElement("tr");
	}
	
	protected String getStyleClass(WFContainer container){
		/*if (container.getStyleClass() != null) {
			return container.getStyleClass();
		}
		return "wf_toolbar";*/
		
		return super.getStyleClass(container);
	}

	protected String getMarkupElementType(){
		//return HTML_TABLE_TAG;
		return HTML_DIV_TAG;
	}	
	
}
