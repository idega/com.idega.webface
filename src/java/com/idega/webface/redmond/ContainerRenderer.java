/*
 * Created on 3.8.2004
 *  
 */
package com.idega.webface.redmond;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.idega.faces.renderkit.BaseRenderer;
import com.idega.util.RenderUtils;
import com.idega.webface.WFContainer;

/**
 * @author tryggvil
 */
public class ContainerRenderer extends BaseRenderer {

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		return true;
	}
	
	protected static String HTML_TABLE_TAG="table";
	protected static String HTML_DIV_TAG = "div";

	public void encodeBegin(FacesContext ctx, UIComponent comp) throws IOException {
		WFContainer container = (WFContainer) comp;
		if (!container.isRendered()) {
			return;
		}
		ResponseWriter out = ctx.getResponseWriter();
		//			out.write("<link type=\"text/css\" href=\"style/webfacestyle.css\"
		// rel=\"stylesheet\">");
		//RenderUtils.ensureAllTagsFinished();
		
		//out.startElement(getMarkupElementType(), comp);
		out.startElement(getMarkupElementType(), null);
		
		
		if (getStyleClass(container) != null) {
			out.writeAttribute("class", getStyleClass(container), null);
		}
		if (getStyleAttributes(container) != null) {
			out.writeAttribute("style", getStyleAttributes(container), null);
		}
		//out.endElement(getMarkupElementType());
	}

	protected String getStyleClass(WFContainer container){
		if (container.getStyleClass() != null) {
			return container.getStyleClass();
		}
		return null;
	}
	
	protected String getStyleAttributes(WFContainer container){
		if (container.getStyleAttribute() != null) {
			return container.getStyleAttribute();
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
	public void encodeChildren(FacesContext ctx, UIComponent comp) throws IOException {
		if (!comp.isRendered()) {
			return;
		}
		// TODO Auto-generated method stub
		super.encodeChildren(ctx, comp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
	public void encodeEnd(FacesContext ctx, UIComponent comp) throws IOException {
		WFContainer container = (WFContainer) comp;
		if (!container.isRendered()) {
			return;
		}
		ResponseWriter out = ctx.getResponseWriter();
		out.endElement(getMarkupElementType());
	}

	protected String getMarkupElementType() {
		return HTML_DIV_TAG;
	}
	
	
	/**
	 * Render the specified facet.
	 */
	protected void renderFacet(FacesContext context,UIComponent parent, String facetName) throws IOException {
		RenderUtils.renderFacet(context,parent,facetName);
	}
}