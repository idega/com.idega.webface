/*
 * $Id: WFPage.java,v 1.12 2005/09/08 23:04:40 tryggvil Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import com.idega.presentation.IWBaseComponent;
import com.idega.presentation.IWContext;

/**
 * ...
 * <p>
 * Last modified: $Date: 2005/09/08 23:04:40 $ by $Author: tryggvil $
 *
 * @author Anders Lindman
 * @version $Revision: 1.12 $
 */
public class WFPage extends IWBaseComponent {

	public final static String WF_BUNDLE = "wf_bundle"; // test, TODO: use IWResourceBundle
	public final static String CONTENT_BUNDLE = "content_bundle";
	
	/**
	 * Default contructor. 
	 */
	public WFPage() {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		loadResourceBundles(context);
		super.encodeBegin(context);
	}

	/*
	 * Test load bundles. 
	 */
	public static void loadResourceBundles(FacesContext context) {
		if (context.getExternalContext().getSessionMap().get(CONTENT_BUNDLE) != null) {
			return;
		}

		UIViewRoot viewRoot = context.getViewRoot();
		if (viewRoot == null) {
			throw new FacesException("No view root. A WFPage object must be a part of the component tree.");
		}

		Locale locale = viewRoot.getLocale();
		if (locale == null) {
			locale = context.getApplication().getDefaultLocale();
		}
		
//		locale = new Locale("sv", "SE"); //test
		locale = IWContext.getIWContext(context).getCurrentLocale();
//		TODO: Move this to com.idega.content
		ResourceBundle bundle = null;
//		String bundleName = "com.idega.webface.test.TestBundle";
		//String bundleName = "com.idega.webface.test.Content";
		try {
			//bundle = ResourceBundle.getBundle(bundleName, locale);
			bundle = WFUtil.getContentBundle().getResourceBundle(locale);
		} catch (MissingResourceException e) {
			//System.out.println("Resource bundle '" + bundleName + "' could not be found.");
			e.printStackTrace();
			return;
		}
		context.getExternalContext().getSessionMap().put(CONTENT_BUNDLE, new BundleMap(bundle));
		
		//bundleName = "com.idega.webface.test.Webface";
		try {
			//bundle = ResourceBundle.getBundle(bundleName, locale);
			bundle = WFUtil.getBundle().getResourceBundle(locale);
		} catch (MissingResourceException e) {
			//System.out.println("Resource bundle '" + bundleName + "' could not be found.");
			e.printStackTrace();
			return;
		}
		context.getExternalContext().getSessionMap().put(WF_BUNDLE, new BundleMap(bundle));
	}
	
	
	 
		public void encodeChildren(FacesContext context) throws IOException {
			if(getRendersChildren()){
				Iterator children = this.getChildren().iterator();
				while (children.hasNext()) {
					UIComponent element = (UIComponent) children.next();
					renderChild(context,element);
				}
			}
			/*Iterator children = this.getChildren().iterator();
				while (children.hasNext()) {
					UIComponent element = (UIComponent) children.next();
					renderChild(context,element);
				}*/
			//super.encodeChildren(context);
		}
	
		/* (non-Javadoc)
		 * @see javax.faces.component.UIComponent#getRendersChildren()
		 */
		public boolean getRendersChildren() {
			return true;
			//return super.getRendersChildren();
		}	

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily() {
		// TODO Auto-generated method stub
		return "idegaweb";
	}
}
