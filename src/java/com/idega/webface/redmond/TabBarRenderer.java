/*
 * Created on 3.8.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.idega.webface.redmond;

import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.idega.webface.WFConstants;
import com.idega.webface.WFContainer;
import com.idega.webface.WFTab;
import com.idega.webface.WFTabBar;

/**
 * @author tryggvil
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TabBarRenderer extends ContainerRenderer {

	//setTaskbarStyleClass("wf_taskbar");
	//setMainAreaStyleClass("wf_taskbarmainarea");
	//setButtonSelectedStyleClass("wf_taskbarbuttonselected");
	//setButtonDeselectedStyleClass("wf_taskbarbuttondeselected");
	
	
	
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeBegin(FacesContext context, UIComponent comp)
			throws IOException {

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
	public void encodeEnd(FacesContext context, UIComponent comp)
			throws IOException {
		WFTabBar bar = (WFTabBar)comp;
		
		//super.encodeBegin(context,comp);
		
		ResponseWriter out = context.getResponseWriter();
		// Taskbar buttons
		out.startElement("ul", null);
		if (bar.getTaskbarStyleClass() != null) {
			out.writeAttribute("class", bar.getTaskbarStyleClass(), null);
		}
		out.writeAttribute("id", "" + comp.getId(), null);
		//out.startElement("tr", null);
		Iterator iter = bar.getButtonIds().iterator();
		while (iter.hasNext()) {
			String buttonId = (String) iter.next();
			String buttonStyleClass = bar.getButtonDeselectedStyleClass();
			WFTab button = (WFTab) comp.getFacet("button_" + buttonId);
			if (buttonId.equals(bar.getSelectedButtonId())) {
				button.setSelected(true);
				buttonStyleClass = bar.getButtonSelectedStyleClass();
			} else {
				button.setSelected(false);
			}
			out.startElement("li", null);
			if (buttonStyleClass != null) {
				out.writeAttribute("class", buttonStyleClass, null);
			}
			renderFacet(context,bar, "button_" + buttonId);
			out.endElement("li");
		}
		//out.endElement("tr");
		out.endElement("ul");
	}
	
	protected String getStyleClass(WFContainer container){
		//Overrided from superclass:
		return WFConstants.STYLE_CLASS_BOX;
	}
	
	/* (non-Javadoc)
	 * @see com.idega.webface.redmond.ContainerRenderer#getStyleAttributes(com.idega.webface.WFContainer)
	 */
	protected String getStyleAttributes(WFContainer container) {
		// TODO Auto-generated method stub
		WFTabBar bar = (WFTabBar)container;
		String attr = super.getStyleAttributes(container);
		return attr+this.getCssHelper().getBackgroundColorAttribute(bar.getBackgroundColor());
	}
}
