package com.idega.webface;
import java.io.IOException;
import javax.faces.context.FacesContext;
import com.idega.presentation.IWBaseComponent;
import com.idega.util.RenderUtils;
/**
 * Title:        idegaclasses
 * Description:  Container holding child components.  
 * Copyright:    Copyright (c) 2001
 * Company:      idega
 * @author <a href="tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFContainer extends IWBaseComponent
{
//	private static boolean imagesSet = false;
	
	public static String RENDERER_TYPE="wf_container";
	
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

	
	public WFContainer()
	{
		//setBackgroundColor(IWConstants.DEFAULT_LIGHT_INTERFACE_COLOR);

		//setAllMargins(0);
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getRendererType()
	 */
	public String getRendererType() {
		return RENDERER_TYPE;
	}

	public String getFamily() {
		return WFConstants.FAMILY_WEBFACE;
	}
	
	public boolean getRendersChildren() {
		return true;
		//return super.getRendersChildren();
	}
	
	public void setLightShadowColor(String color)
	{
		_lightColor = color;
	}
	
	public String getLightShadowColor()
	{
		return _lightColor;
	}
	
	public void setDarkShadowColor(String color)
	{
		_darkColor = color;
	}

	public String getDarkShadowColor()
	{
		return _darkColor;
	}
	
	/**
	 * @return
	 * 
	 * @uml.property name="backgroundColor"
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param string
	 * 
	 * @uml.property name="backgroundColor"
	 */
	public void setBackgroundColor(String string) {
		backgroundColor = string;
	}

	/**
	 * @return Returns the height.
	 * 
	 * @uml.property name="height"
	 */
	public String getHeight() {
		return height;
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
		return styleClass;
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
		return width;
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
		Object values[] = new Object[4];
		values[0] = super.saveState(ctx);
		values[1] = getStyleAttribute();
		values[2] = getStyleClass();
		values[3] = title;
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
		title=(String)values[3];
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
		return title;
	}

}
