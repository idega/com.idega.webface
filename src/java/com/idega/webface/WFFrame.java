/*
 * $Id: WFFrame.java,v 1.9 2005/06/02 18:08:07 tryggvil Exp $
 * Created on 20.10.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import javax.faces.context.FacesContext;
import com.idega.presentation.ui.IFrame;


/**
 * 
 *  Last modified: $Date: 2005/06/02 18:08:07 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.9 $
 */
public class WFFrame extends WFBlock {

	public static final String DEFAULT_STYLE_CLASS = "wf_frame";
	private String frameURL;
	
	/**
	 * 
	 */
	public WFFrame() {
		super();
		setStyleClass(DEFAULT_STYLE_CLASS);
		this.setNoMarginsOnMainArea();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param titleBarText
	 */
	public WFFrame(String titleBarText) {
		super(titleBarText);
		setStyleClass(DEFAULT_STYLE_CLASS);
		this.setNoMarginsOnMainArea();
		// TODO Auto-generated constructor stub
	}
	
	public WFFrame(String titleBarText,String frameURL){
		this(titleBarText);
		this.setFrameURL(frameURL);
		setStyleClass(DEFAULT_STYLE_CLASS);
	}
	
	
	/* (non-Javadoc)
	 * @see com.idega.webface.WFBlock#initializeContent()
	 */
	protected void initializeContent() {
		IFrame frame = new IFrame(this.getId()+"subframe",this.getFrameURL());
		
		if(getFrameMarginTop()!=null && getFrameMarginBottom()!=null){
			frame.setFrameHeight(getFrameMarginTop().intValue(),getFrameMarginBottom().intValue());
		}
		add(frame);
	}
	/**
	 * @return Returns the frameURL.
	 */
	public String getFrameURL() {
		return frameURL;
	}
	/**
	 * @param frameURL The frameURL to set.
	 */
	public void setFrameURL(String frameURL) {
		this.frameURL = frameURL;
	}
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = this.frameURL;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		frameURL = ((String) values[1]);
	}
	
	/**
	 * <p>
	 * This method is for creating an iframe with 'floating' height, i.e. that the frame with take 
	 * the height of the window minus the top and bottom margins specified in this function. This is
	 * implemented by an added javascript call.
	 * </p>
	 * @param marginTop space for the margin from the top in pixels;
	 * @param marginBottom space for the margin from the bottom in pixels
	 */
	public void setFrameHeight(int marginTop,int marginBottom){
		setFrameMarginTop(new Integer(marginTop));
		setFrameMarginBottom(new Integer(marginBottom));
	}
	
	/**
	 * @return Returns the frameMarginBottom.
	 */
	protected Integer getFrameMarginBottom() {
		return (Integer)getAttributes().get("iframeMarginBottom");
	}

	
	/**
	 * @param frameMarginBottom The frameMarginBottom to set.
	 */
	protected void setFrameMarginBottom(Integer frameMarginBottom) {
		getAttributes().put("iframeMarginBottom",frameMarginBottom);
	}

	
	/**
	 * @return Returns the frameMarginTop.
	 */
	protected Integer getFrameMarginTop() {
		return (Integer)getAttributes().get("iframeMarginTop");
	}

	
	/**
	 * @param frameMarginTop The frameMarginTop to set.
	 */
	protected void setFrameMarginTop(Integer frameMarginTop) {
		getAttributes().put("iframeMarginTop",frameMarginTop);
	}
	
}
