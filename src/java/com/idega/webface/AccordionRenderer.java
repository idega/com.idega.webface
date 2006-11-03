package com.idega.webface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.apache.myfaces.custom.tree2.TreeNode;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

import com.idega.core.data.ICTreeNode;
import com.idega.core.data.IWTreeNode;

//public class AccordionRenderer extends IWTreeRenderer{
public class AccordionRenderer extends Renderer {
//	TreeNode siteTree = null;
	private static final String SITE_LINK = "http://localhost:8080"+"/idegaweb/bundles/com.idega.content.bundle/resources/templates/site-templates.xml";

	 public void encodeBegin(FacesContext context, UIComponent accordion) throws IOException { 
		  ResponseWriter writer = context.getResponseWriter(); 
		     writer.startElement("div", accordion); 
		     writer.writeAttribute("id", "accordionComponentId", "id");
		     System.out.println("encodeBegin");		     
		 } 
		 
		 public void encodeEnd(FacesContext context, UIComponent accordion) throws IOException { 
		  ResponseWriter writer = context.getResponseWriter(); 
		     writer.endElement("div"); 
		     System.out.println("encodeEnd");
		     super.encodeEnd(context, accordion);
		 } 	
		 
		 @Override
		public boolean getRendersChildren() {
			// TODO Auto-generated method stub
			return false;
		}
//	public TreeNode getSiteTree(){
//		return getSiteStructure(FacesContext.getCurrentInstance().getResponseWriter());
//	}
//
//	public TreeNode getSiteStructure(ResponseWriter out){




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
		    
		    ICTreeNode icnode = rootNode;
		    
		    IWTree tree = new IWTree();

//			rootNode = getPage(structure, rootNode);
			tree.setValue((TreeNode)(new WFTreeNode(icnode)));
		    tree.setShowRootNode(false);		    
		    
		    UIComponent uic = tree;
		    
		    tree.getFacets().put("PageTreeNode", uic);
		    
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
		    out.endElement(HTML.DIV_ELEM);		    
		}
		out.endElement(HTML.DIV_ELEM);		    
	} 
	
	
	 
//	public void encodeEnd(FacesContext context) throws IOException { 
//		ResponseWriter writer = context.getResponseWriter(); 
//	    writer.endElement("div"); 
//	}
	
//    public void encodeChildren(FacesContext context, UIComponent component) throws IOException{
//    	
//    	ResponseWriter out = context.getResponseWriter();
//    	
//		Document siteDocument = getXMLDocument(SITE_LINK);
//		Element root = siteDocument.getRootElement();
//		Collection siteRoot = root.getChildren();			
//		Iterator itr = siteRoot.iterator();
//		int i = 0;
////	    out.startElement(HTML.DIV_ELEM, tree);
//		out.startElement(HTML.DIV_ELEM, null);
//	    out.writeAttribute("id", "accordionDiv", "id"); 		
//		while(itr.hasNext()){
//			out.startElement(HTML.DIV_ELEM, null);
//			out.writeAttribute("id", "overviewPanel"+i, "id"); 
//				
//			Element currentSite = (Element)itr.next();
//				
//			out.startElement(HTML.DIV_ELEM, null);
//		    out.writeAttribute("id", "overviewHeader"+i, "id");
//		    out.append(currentSite.getAttributeValue("name"));
//			out.endElement(HTML.DIV_ELEM);
//
//			out.startElement(HTML.DIV_ELEM, null);
//		    out.writeAttribute("id", "overviewContent"+i, "id");
//		        
//		    Element structure = (Element)currentSite.getChildren().get(0);
//		    Iterator siteItr = (structure.getChildren()).iterator();
//		      
//		    IWTreeNode rootNode = new IWTreeNode("root");
////		    rootNode = getPage(currentElement, rootNode);
//		    out.endElement(HTML.DIV_ELEM);				
//		}
//	}
	
	public Document getXMLDocument(String link) {
		URL url = null;
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
//			log.error(e);
			return null;
		}
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = builder.build(url);
		} catch (JDOMException e) {
//			log.error(e);
			return null;
		} catch (IOException e) {
//			log.error(e);
			return null;
		}
		return document;
	}	
}	
