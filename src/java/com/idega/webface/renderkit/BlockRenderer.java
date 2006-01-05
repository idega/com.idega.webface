/*
 * $Id: BlockRenderer.java,v 1.6 2006/01/05 15:56:21 laddi Exp $
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
import com.idega.webface.WFBlock;
import com.idega.webface.WFBlockTabbed;


/**
 * <p>
 * This is the default Renderer for the WFBlock component.
 * </p>
 * 
 *  Last modified: $Date: 2006/01/05 15:56:21 $ by $Author: laddi $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.6 $
 */
public class BlockRenderer extends ContainerRenderer{
	
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context,UIComponent component) throws IOException {
		super.encodeBegin(context,component);
		WFBlock block = (WFBlock)component;
		if (!component.isRendered()) {
			return;
		}
		
		String mainAreaStyleClass = block.getMainAreaStyleClass();

		renderFacet(context, component,WFBlock.FACET_TITLEBAR);
		if (!block.isToolbarEmbeddedInTitlebar()) {
			renderFacet(context, component,WFBlock.FACET_TOOLBAR);
		}

		ResponseWriter out = context.getResponseWriter();
		
		UIComponent header = component.getFacet(WFBlock.FACET_HEADER);
		if(header!=null){
			String headerStyleClass = WFBlock.FACET_HEADER;
			out.startElement("div",component);
			out.writeAttribute("class",headerStyleClass,null);
			renderChild(context,header);
			out.endElement("div");
		}
		
		renderContainerStart(out,mainAreaStyleClass);
		String mainAreaStyle = block.getMainAreaStyleAttributes();
		if(mainAreaStyle!=null){
			out.writeAttribute("style",mainAreaStyle, null);
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeChildren(FacesContext context, UIComponent comp) throws IOException {
		if(comp instanceof WFBlockTabbed){
			WFBlockTabbed block = (WFBlockTabbed)comp;
			UIComponent tabView = block.getTabbedPane().getSelectedTabView();
			renderChild(context,tabView);
		}
		super.encodeChildren(context, comp);
	}
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeEnd(FacesContext context, UIComponent comp) throws IOException {
		ResponseWriter out = context.getResponseWriter();

		renderContainerEnd(out);
		
		UIComponent footer = comp.getFacet(WFBlock.FACET_FOOTER);
		if(footer!=null){
			String footerStyleClass = WFBlock.FACET_FOOTER;
			out.startElement("div",comp);
			out.writeAttribute("class",footerStyleClass,null);
			renderChild(context,footer);
			out.endElement("div");
		}
		
		
		super.encodeEnd(context, comp);

	}

}
