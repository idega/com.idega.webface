/*
 * $Id: BlockRenderer.java,v 1.3 2005/02/04 11:13:55 tryggvil Exp $
 * Created on 25.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.renderkit;

import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.idega.webface.WFBlock;


/**
 * <p>
 * This is the default Renderer for the WFBlock component.
 * </p>
 * 
 *  Last modified: $Date: 2005/02/04 11:13:55 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.3 $
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
		
		List children = component.getChildren();
		String mainAreaStyleClass = block.getMainAreaStyleClass();
		/*if (mainAreaStyleClass != null) {
			if(component.getChildren().size()>0){
				try{
					WFContainer mainArea = (WFContainer) children.get(0);
					if(mainArea!=null){
						mainArea.setStyleClass(mainAreaStyleClass);
					}
				}
				catch(ClassCastException cce){
					
				}
				
			}
		}*/

		renderFacet(context, component,WFBlock.FACET_TITLEBAR);
		if (!block.isToolbarEmbeddedInTitlebar()) {
			renderFacet(context, component,WFBlock.FACET_TOOLBAR);
		}
		ResponseWriter out = context.getResponseWriter();
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
		super.encodeChildren(context, comp);
	}
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeEnd(FacesContext context, UIComponent comp) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		renderContainerEnd(out);
		
		super.encodeEnd(context, comp);

	}
}
