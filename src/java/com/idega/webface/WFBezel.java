/*
 * Created on 3.8.2004
 * 
 */
package com.idega.webface;

/**
 * Class to be a (grouping) box around other components.
 * @author tryggvil
 *
 */
public class WFBezel extends WFContainer {

	//String color="#DDDDDD";
	String color;
	public static String STYLE_CLASS="wf_bezel";
	public static String RENDERER_TYPE="wf_bezel";
	
	
	public WFBezel(){
		super.setStyleClass(STYLE_CLASS);
	}
	
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getRendererType()
	 */
	public String getRendererType() {
		return RENDERER_TYPE;
	}
	/**
	 * @return Returns the color.
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color The color to set.
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
