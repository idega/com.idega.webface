/*
 * $Id: WFContainer.java,v 1.15 2006/11/10 00:18:56 gimmi Exp $
 *
 * Copyright (C) 2004-2005 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 */
package com.idega.webface;
import java.io.IOException;
import javax.faces.context.FacesContext;
import com.idega.presentation.IWBaseComponent;
import com.idega.util.RenderUtils;

/**
 *  <p>
 *  This is a component that renders our child components 
 *  with a "div" tag surrounded in HTML.
 *  </p>
 * 
 *  Last modified: $Date: 2006/11/10 00:18:56 $ by $Author: gimmi $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.15 $
 */
public class WFContainer extends IWBaseComponent
{
//	private static boolean imagesSet = false;
	
	private static String RENDERER_TYPE="wf_container";
	private static String DEFAULT_STYLE_CLASS=RENDERER_TYPE;
	
	public static String getWFRendererType() {
		return RENDERER_TYPE;
	}
	
	/*private static Image topleft;
	private static Image topright;
	private static Image bottomleft;
	private static Image bottomright;
	private static Image top;
	private static Image bottom;
	private static Image left;
	private static Image right;*/
	
	private String _lightColor = "#FFFFFF";
	private String _darkColor = "#999999";
	private String backgroundColor;
	private String width;
	private String height;
	private String styleClass=RENDERER_TYPE;
	private String title;
	private boolean isSpan = false;

	
	public WFContainer()
	{
		//setBackgroundColor(IWConstants.DEFAULT_LIGHT_INTERFACE_COLOR);

		//setAllMargins(0);
		setRendererType(RENDERER_TYPE);
		setStyleClass(DEFAULT_STYLE_CLASS);
	}

//	/* (non-Javadoc)
//	 * @see javax.faces.component.UIComponent#getRendererType()
//	 */
//	public String getRendererType() {
//		return RENDERER_TYPE;
//	}

	public String getFamily() {
		return WFConstants.FAMILY_WEBFACE;
	}
	
	public boolean getRendersChildren() {
		return true;
		//return super.getRendersChildren();
	}
	
	public void setLightShadowColor(String color)
	{
		this._lightColor = color;
	}
	
	public String getLightShadowColor()
	{
		return this._lightColor;
	}
	
	public void setDarkShadowColor(String color)
	{
		this._darkColor = color;
	}

	public String getDarkShadowColor()
	{
		return this._darkColor;
	}
	
	/**
	 * @return
	 * 
	 * @uml.property name="backgroundColor"
	 */
	public String getBackgroundColor() {
		return this.backgroundColor;
	}

	/**
	 * @param string
	 * 
	 * @uml.property name="backgroundColor"
	 */
	public void setBackgroundColor(String string) {
		this.backgroundColor = string;
	}

	/**
	 * @return Returns the height.
	 * 
	 * @uml.property name="height"
	 */
	public String getHeight() {
		return this.height;
	}

	/**
	 * @param height The height to set.
	 * 
	 * @uml.property name="height"
	 */
	public void setHeight(String height) {
		this.setStyleAttribute("height", height);
		this.height = height;
	}

	/**
	 * @return Returns the styleClass.
	 * 
	 * @uml.property name="styleClass"
	 */
	public String getStyleClass() {
		return this.styleClass;
	}

	/**
	 * @param styleClass The styleClass to set.
	 * 
	 * @uml.property name="styleClass"
	 */
	public void setStyleClass(String styleClass) {
		if(styleClass!=null){
			this.styleClass = styleClass;
		}
	}

	/**
	 * @return Returns the width.
	 * 
	 * @uml.property name="width"
	 */
	public String getWidth() {
		return this.width;
	}

	/**
	 * @param width The width to set.
	 * 
	 * @uml.property name="width"
	 */
	public void setWidth(String width) {
		this.setStyleAttribute("width", width);
		this.width = width;
	}


	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[5];
		values[0] = super.saveState(ctx);
		values[1] = getStyleAttribute();
		values[2] = getStyleClass();
		values[3] = this.title;
		values[4] = new Boolean(isSpan);
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		setStyleAttribute((String) values[1]);
		setStyleClass((String) values[2]);
		this.title=(String)values[3];
		this.isSpan= ((Boolean) values[4]).booleanValue();
	}
	
	/**
	 * Render the specified facet.
	 */
	protected void renderFacet(FacesContext context, String facetName) throws IOException {
		/*UIComponent facet = (UIComponent) (getFacets().get(facetName));
		if (facet != null) {
			facet.encodeBegin(context);
			facet.encodeChildren(context);
			facet.encodeEnd(context);
		}*/
		RenderUtils.renderFacet(context,this,facetName);
	}
	
	
	/**
	 * Sets a "title" on this container, this is usually rendered like a tooltip
	 * @param title
	 */
	public void setTitle(String title){
		this.title=title;
	}
	/**
	 * Gets the set "title" if any is set for this container, this is usually rendered like a tooltip
	 * @param title
	 */
	public String getTitle(){
		return this.title;
	}

	public boolean isSpan() {
		return isSpan;
	}

	public void setSpan(boolean isSpan) {
		this.isSpan = isSpan;
	}

}
