/*
 * $Id: BlockRenderer.java,v 1.1 2004/10/19 11:09:29 tryggvil Exp $
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
import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;


/**
 * 
 *  Last modified: $Date: 2004/10/19 11:09:29 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public class BlockRenderer extends ContainerRenderer{
	
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context,UIComponent component) throws IOException {
		WFBlock block = (WFBlock)component;
		if (!component.isRendered()) {
			return;
		}
		super.encodeBegin(context,component);
		String mainAreaStyleClass = block.getMainAreaStyleClass();
		if (mainAreaStyleClass != null) {
			if(component.getChildren().size()>0){
				WFContainer mainArea = (WFContainer) component.getChildren().get(0);
				mainArea.setStyleClass(mainAreaStyleClass);
			}
		}
		if (!block.isToolbarEmbeddedInTitlebar()) {
			renderFacet(context, component,"toolbar");
		}
		renderFacet(context, component,"titlebar");
	}
	
}