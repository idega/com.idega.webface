package com.idega.webface;

import javax.faces.component.UIComponent; 
import javax.faces.webapp.UIComponentTag; 
 
 
public class SiteTemplatesViewerTag extends UIComponentTag{ 
 
	 public void release() { 
	  // the super class method should be called  
		 super.release();
	 } 
  
 protected void setProperties(UIComponent component) { 
  // the super class method should be called
	 super.setProperties(component);
 } 
 
 public String getComponentType() { 
  return "siteTemplatesViewer"; 
 } 
 
 public String getRendererType() { 	
  // null means the component renders itself 
  return null; 
 } 
 
} 
