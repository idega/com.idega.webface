/*
 * $Id: TitlebarRenderer.java,v 1.5 2005/03/10 22:24:06 tryggvil Exp $
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
 *  Last modified: $Date: 2005/03/10 22:24:06 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.5 $
 */
public class TitlebarRenderer extends ContainerRenderer {
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context,UIComponent component) throws IOException {
		WFTitlebar titlebar = (WFTitlebar)component;
		ResponseWriter out = context.getResponseWriter();
		super.encodeBegin(context,component);
		///out.startElement("tr", null);
		
		//out.startElement("td", null);
		//out.writeAttribute("width", "20", null); // TODO: fix css style
		
		UIComponent embeddedToolbar = titlebar.getEmbeddedToolbar();
		if(embeddedToolbar!=null){
			//renderContainerStart(out,"wf_titlebar_toolbar");
			renderChild(context,embeddedToolbar);
			//renderContainerEnd(out);
		}
		
		UIComponent defaultToolbar = titlebar.getDefaultToolbar();
		if(defaultToolbar!=null){
			//renderContainerStart(out,"wf_titlebar_toolbar");
			renderChild(context,defaultToolbar);
			//renderContainerEnd(out);
		}
		
		//renderContainerStart(out,"wf_titlebar_icon");
		
		String iconStyle = titlebar.getIconStyleClass();
		if (iconStyle == null) {
			iconStyle="wf_titlebar_icon";
		}

		out.startElement("div", null);
		out.writeAttribute("class", iconStyle, null);
		
		out.startElement("img", null);
		out.writeAttribute("src", titlebar.getIconImageURI(), null);
		out.endElement("img");
		//if (iconStyle != null) {
		//	out.endElement("div");
		//}		
		
		out.endElement("div");
		
		//renderContainerEnd(out);
		
		//out.endElement("td");
		//out.startElement("td", null);
		//out.writeAttribute("width", "100%", null);
		//out.startElement("div", null);
		
		/*renderContainerStart(out,"wf_titlebar_text");
		
		String toolTip = titlebar.getToolTip();
		if (toolTip != null && !toolTip.equals("")) {
			out.writeAttribute("title", toolTip, null);
			out.writeAttribute("alt", toolTip, null);
		}*/

		RenderUtils.renderFacet(context, component, WFTitlebar.FACET_TEXT);
		
		//renderContainerEnd(out);
		
		//out.endElement("div");
//		out.startElement("font", null);
//		out.writeAttribute("class", "wf_titlebartext", null);
//		out.write(getTitleText());
//		out.endElement("font");
		//out.endElement("td");
		//out.startElement("td", null);
		//out.writeAttribute("nowrap", "true", null);
		//out.write("");
		

		
	}	
	
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context,UIComponent component) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		//out.endElement("td");
		//out.endElement("tr");
		super.encodeEnd(context,component);
	}
	
	
	protected String getMarkupElementType(){
		//TODO: Change to DIV
		//return HTML_TABLE_TAG;
		return HTML_DIV_TAG;
	}
	
}
