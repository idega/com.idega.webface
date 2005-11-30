/*
 * $Id: TitlebarRenderer.java,v 1.6 2005/11/30 09:35:43 laddi Exp $
 * Created on 25.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.renderkit;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.idega.util.RenderUtils;
import com.idega.webface.WFTitlebar;


/**
 * 
 *  Last modified: $Date: 2005/11/30 09:35:43 $ by $Author: laddi $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.6 $
 */
public class TitlebarRenderer extends ContainerRenderer {
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context,UIComponent component) throws IOException {
		WFTitlebar titlebar = (WFTitlebar)component;
		ResponseWriter out = context.getResponseWriter();
		super.encodeBegin(context,component);
		
		UIComponent embeddedToolbar = titlebar.getEmbeddedToolbar();
		if(embeddedToolbar!=null){
			renderChild(context,embeddedToolbar);
		}
		
		UIComponent defaultToolbar = titlebar.getDefaultToolbar();
		if(defaultToolbar!=null){
			renderChild(context,defaultToolbar);
		}
		
		String iconStyle = titlebar.getIconStyleClass();
		if (iconStyle == null) {
			iconStyle="wf_titlebar_icon";
		}

		out.startElement("div", null);
		out.writeAttribute("class", iconStyle, null);
		
		out.startElement("img", null);
		out.writeAttribute("src", titlebar.getIconImageURI(), null);
		out.endElement("img");
		
		out.endElement("div");

		RenderUtils.renderFacet(context, component, WFTitlebar.FACET_TEXT);
	}	
	
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context,UIComponent component) throws IOException {
		super.encodeEnd(context,component);
	}
	
	
	protected String getMarkupElementType(){
		return HTML_DIV_TAG;
	}
	
}
