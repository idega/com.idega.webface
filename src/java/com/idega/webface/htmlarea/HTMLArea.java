/*
 * Created on 22.10.2004
 */
package com.idega.webface.htmlarea;

import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;


/**
 * @author gimmi
 */
public class HTMLArea extends HtmlInputTextarea {

	public static String RENDERER_TYPE="wf_htmlarea";
	
	private String plugins;
	
	public HTMLArea() {
		super();
	}
	public void setPlugins(String plugins) {
		this.plugins = plugins;
	}
	
	public String getPlugins() {
    if (plugins != null) return plugins;
    ValueBinding vb = getValueBinding("plugins");
    String v = vb != null ? (String)vb.getValue(getFacesContext()) : null;
    return v != null ? v : null;
	}

	public void restoreState(FacesContext context, Object state) {
    Object values[] = (Object[])state;
    super.restoreState(context, values[0]);
    plugins = (String)values[1];
	}

  public Object saveState(FacesContext context)  {
	  Object values[] = new Object[2];
	  values[0] = super.saveState(context);
	  values[1] = plugins;
	  
	  return (Object) values;
	}
	
	protected Renderer getRenderer(FacesContext context) {
		return new HTMLAreaRenderer();
	}
	
	public String getRendererType() {
		return RENDERER_TYPE;
	}
}
