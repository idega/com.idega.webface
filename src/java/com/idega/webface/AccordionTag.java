package com.idega.webface;

import org.jdom.Document;
import javax.faces.component.UIComponent; 
import javax.faces.webapp.UIComponentTag; 

//public class AccordionTag extends IWTreeTag{
//	public class IWTreeTag{
public class AccordionTag extends UIComponentTag{
	
	 public void release() { 
		  // the super class method should be called  
		  super.release(); 
		 } 
		  
		 protected void setProperties(UIComponent component) { 
		  // the super class method should be called  
		  super.setProperties(component); 
		   
		 } 
		 public String getComponentType() { 
		  return "accordion"; 
		 } 
		 
		 public String getRendererType() { 
		  // null means the component renders itself 
			 return null;
//			 return "com.idega.webface.Accordion";			 
		  }	
	
	
//	public String getComponentType() { 
//		return "accordion"; 
//	} 
//
//    public String getRendererType() {
//        return "com.idega.webface.Accordion";
//    }
}
