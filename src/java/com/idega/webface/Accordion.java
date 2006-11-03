package com.idega.webface;

import java.io.IOException; 
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;


import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput; 
import javax.faces.context.FacesContext; 
import javax.faces.context.ResponseWriter; 

import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;

import com.idega.core.data.ICTreeNode;
import com.idega.core.data.IWTreeNode;
import com.idega.presentation.IWContext;
 
 
//public class Accordion { 
//	public static final String COMPONENT_TYPE = "com.idega.content.webface.Accordion";
//	private static final String DEFAULT_RENDERER_TYPE = "com.idega.webface.AccordionRenderer";
//} 

//public class Accordion extends UIOutput {
public class Accordion extends IWTree {
	// TO DO SITE_LINK should not be hardcoded

//public class Accordion extends IWTreeRenderer {
	  
	private static final String SITE_LINK = "http://localhost:8080"+"/idegaweb/bundles/com.idega.content.bundle/resources/templates/site-templates.xml";
	private String webRoot;
	private static final String EMPTY = "";

	public static final String COMPONENT_TYPE = "com.idega.webface.Accordion";
//	private static final String DEFAULT_RENDERER_TYPE = "com.idega.webface.Accordion";

//		 public void encodeBegin(FacesContext context) throws IOException { 
//		  ResponseWriter writer = context.getResponseWriter(); 
//		     writer.startElement("div", this); 
//		     writer.writeAttribute("id", "accordionComponentId", "id");
//		 } 
	public void encodeBegin(FacesContext context) throws IOException { 
//		ResponseWriter writer = context.getResponseWriter(); 
//		writer.startElement("div", this);
//	    writer.writeAttribute("id", "ComponentAccordion", "id"); 	
   	ResponseWriter out = context.getResponseWriter();
    	
		Document siteDocument = getXMLDocument(SITE_LINK);
		
		Element root = siteDocument.getRootElement();		
//		Element root = siteDocument.getRootElement();
		Collection siteRoot = root.getChildren();			
		Iterator itr = siteRoot.iterator();
		
		int i = 0;
		
		out.startElement(HTML.DIV_ELEM, null);
	    out.writeAttribute("id", "accordionDiv", "id"); 		
		while(itr.hasNext()){
			out.startElement(HTML.DIV_ELEM, null);
			out.writeAttribute("id", "overviewPanel"+i, "id"); 
				
			Element currentSite = (Element)itr.next();
				
			out.startElement(HTML.DIV_ELEM, null);
		    out.writeAttribute("id", "overviewHeader"+i, "id");
		    out.append(currentSite.getAttributeValue("name"));
			out.endElement(HTML.DIV_ELEM);

			out.startElement(HTML.DIV_ELEM, null);
		    out.writeAttribute("id", "overviewContent"+i, "id");
		        
		    Element structure = (Element)currentSite.getChildren().get(0);
//		    Iterator siteItr = (structure.getChildren()).iterator();
		      
		    IWTreeNode rootNode = new IWTreeNode(structure.getAttributeValue("name"));
		     
		    rootNode = getPage(structure, rootNode);		    
		    
//		    ICTreeNode icnode = rootNode;
		    
		    IWTree tree = new IWTree();

//			tree.setValue((TreeNode)(new WFTreeNode(icnode)));
		    tree.setValue(getTree(rootNode));
		    tree.setShowRootNode(false);		    
		    
		    UIComponent uic = tree;
		    
		    tree.getFacets().put("IWTree", this);
		    System.out.println("before encode rootNode acc");
		    IWTreeRenderer iwtr = new IWTreeRenderer();
		    SiteTemplate stemp = new SiteTemplate();
		    this.setValue(stemp.getSiteTree());
//		    iwtr.encodeChildren(context, this);
		    System.out.println("after encode rootNode acc");
//		    IWTreeRenderer iwtr = new IWTreeRenderer();
//		    iwtr.encodeChildren(context, uic);
		    
//		    if(tree.getFacet("TreeNode") != null)
//		    	System.out.println("facet right");
//		    else
//		    	System.out.println("problems with facet TreeNode");
//		    
//		    if(tree.getFacet("IWTreeNode") != null)
//		    	System.out.println("facet right");
//		    else
//		    	System.out.println("problems with facet IWTreeNode");		    
//
//		    if(tree.getFacet("PageTreeNode") != null)
//		    	System.out.println("facet right");
//		    else
//		    	System.out.println("problems with facet PageTreeNode");		    
//		    tree = (IWTreeNode)rootNode;
//		    

			
//			ICTreeNode icnode = rootNode.getParentNode();
//			ICTreeNode icnode = rootNode;		    
		    
//		    rootNode = getPage(currentElement, rootNode);
		    out.endElement(HTML.DIV_ELEM);
		    System.out.println("complete");
		    out.endElement(HTML.DIV_ELEM);		    
		}
		out.endElement(HTML.DIV_ELEM);		    
	} 		 
	public TreeNode getTree2(IWTreeNode rootNode){
		ICTreeNode icnode = rootNode;
		return new WFTreeNode(icnode);		
	}
	public TreeNode getTree(IWTreeNode rootNode){
		return getTree2(rootNode);
	}
		 public void encodeEnd(FacesContext context) throws IOException { 
		  ResponseWriter writer = context.getResponseWriter(); 
		     writer.endElement("div"); 
//		     super.encodeEnd(context);
		 } 

	public Accordion() {
		super();
		setRendererType("com.idega.webface.Accordion");
	}
	public Document getXMLDocument(String link) {
		URL url = null;
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			return null;
		}
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = builder.build(url);
		} catch (JDOMException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return document;
	}	
	public void encodeTree(){
		
	}
	
	public IWTreeNode getPage(Element currElement, IWTreeNode currNode){
		Iterator itr = (currElement.getChildren()).iterator();
		while(itr.hasNext()){
			Element current = (Element)itr.next();
			IWTreeNode newNode = new IWTreeNode(current.getAttributeValue("name"));
			newNode.setParent(currNode);
			currNode.addChild(newNode);
			if(!current.getChildren().isEmpty()){
				newNode = getPage(current, newNode);
			}
		}
		return currNode;
	}
	 public String getRendererType() { 
		  // null means the component renders itself 
			 return null;
//			 return "com.idega.webface.Accordion";			 
		  }
//    public boolean rendersChildren() { 
//    	return true; 
//    }

} 
