/*
 * Created on 3.8.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.idega.webface.redmond;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import com.idega.webface.WFBezel;
import com.idega.webface.WFContainer;

/**
 * @author tryggvil
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BezelRenderer extends ContainerRenderer {

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeBegin(FacesContext ctx, UIComponent comp)
			throws IOException {
		WFBezel bezel = (WFBezel)comp;
		super.encodeBegin(ctx,comp);
	}
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeChildren(FacesContext ctx, UIComponent comp)
			throws IOException {
		// TODO Auto-generated method stub
		super.encodeChildren(ctx, comp);
	}
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeEnd(FacesContext ctx, UIComponent comp)
			throws IOException {
		// TODO Auto-generated method stub
		super.encodeEnd(ctx, comp);
	}
	
	protected String getStyleClass(WFContainer container){
		//Overrided from superclass:
		return super.getStyleClass(container);
	}
	
	/* (non-Javadoc)
	 * @see com.idega.webface.redmond.ContainerRenderer#getStyleAttributes(com.idega.webface.WFContainer)
	 */
	protected String getStyleAttributes(WFContainer container) {
		// TODO Auto-generated method stub
		WFBezel bezel = (WFBezel)container;
		String attr = super.getStyleAttributes(container);
		return attr+this.getCssHelper().getBackgroundColorAttribute(bezel.getColor());
	}
}
