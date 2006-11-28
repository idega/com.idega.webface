package com.idega.webface;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.custom.tree2.TreeTag;


public class IWTreeTag extends TreeTag{
	String sourceTree = null;
//public class IWTreeTag{
    public String getRendererType()
    {
        return "com.idega.webface.IWTree";
    }
    public String getComponentType()
    {
    	return "iwtree";
    }
    
    public void setsourceTree(String sourceTree) {
    	this.sourceTree = sourceTree;
    }
    
    public String getsourceTree(){
    	return sourceTree;
    }
    
    public void release(){
    	super.release();
    	sourceTree = null;
    }
    
    protected void setProperties(UIComponent component){
    	super.setProperties(component);
        if (sourceTree != null) { 
        	setString(component, "sourceTree", sourceTree);
        }
    } 
    public void setString(UIComponent component, 
    	String attributeName, String attributeValue) { 
    	if (attributeValue == null)
    		return; 
    	if (isValueReference(attributeValue)) 
    		setValueBinding(component, attributeName, attributeValue); 
    		else 
    			component.getAttributes().put(attributeName, attributeValue); 
    } 
    public void setValueBinding(UIComponent component, String attributeName, String attributeValue) { 
    	FacesContext context = FacesContext.getCurrentInstance(); 
    	Application app = context.getApplication(); 
    	ValueBinding vb = app.createValueBinding(attributeValue);
    	component.setValueBinding(attributeName, vb);      
    }
}