/*
 * $Id: BlockRenderer.java,v 1.4 2004/11/14 23:38:39 tryggvil Exp $
 * Created on 25.8.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.redmond;

import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;


/**
 * 
 *  Last modified: $Date: 2004/11/14 23:38:39 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.4 $
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
		if (mainAreaStyleClass != null) {
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
		}
		if (!block.isToolbarEmbeddedInTitlebar()) {
			renderFacet(context, component,"toolbar");
		}
		renderFacet(context, component,"titlebar");
	}
	
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeChildren(FacesContext ctx, UIComponent comp) throws IOException {
		// TODO Auto-generated method stub
		super.encodeChildren(ctx, comp);
	}
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeEnd(FacesContext ctx, UIComponent comp) throws IOException {
		// TODO Auto-generated method stub
		super.encodeEnd(ctx, comp);
	}
}
