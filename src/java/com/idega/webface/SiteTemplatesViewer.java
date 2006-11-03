package com.idega.webface;

import java.io.IOException; 
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import javax.faces.component.UIOutput; 
import javax.faces.context.FacesContext; 
import javax.faces.context.ResponseWriter; 

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.idega.block.web2.presentation.Accordion;
import com.idega.core.data.IWTreeNode;
import com.idega.presentation.IWBaseComponent;
import com.idega.presentation.IWContext;
import com.idega.presentation.text.Text;

//import com.idega.content.themes.helpers.ThemesHelper;

public class SiteTemplatesViewer extends IWBaseComponent {
//	private static final String SITE_LINK = ThemesHelper.getInstance().getWebRootWithoutContent()+"/idegaweb/bundles/com.idega.content.bundle/resources/templates/site-templates.xml";
	private static final String SITE_LINK = "http://localhost:8080"+"/idegaweb/bundles/com.idega.content.bundle/resources/templates/site-templates.xml";

	protected void initializeComponent(FacesContext context) {
	  
		Document siteDocument = getXMLDocument(SITE_LINK);		
		Element root = siteDocument.getRootElement();		
		Collection siteRoot = root.getChildren();			
		Iterator itr = siteRoot.iterator();
		
		while(itr.hasNext()){
			Element currentSite = (Element)itr.next();
			Element structure = (Element)currentSite.getChildren().get(0);
			//IWTreeNode fake = new IWTreeNode(structure.getAttributeValue("name"));
			IWTreeNode rootNode = new IWTreeNode(structure.getAttributeValue("name"));
			rootNode.addChild(rootNode);
			rootNode = getPage(structure, rootNode);
			
		}		
		Accordion acc = new Accordion("site_templates");
		acc.setHeight("100");
		acc.addPanel("panel1", new Text("gimmi1"), new Text("gimmi 1 texxt"));
		acc.addPanel("panel2", new Text("gimmi2"), new Text("gimmi 2 texxt"));
		acc.addPanel("panel3", new Text("gimmi3"), new Text("gimmi 3 texxt"));
		acc.addPanel("panel4", new Text("Svana"), new Text("Svana 3 texxt"));
		acc.addPanel("panel5", new Text("Svana1"), new Text("Svana 4 texxt"));
		getChildren().add(acc);
		System.out.println("ACCORDION_DONE");
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
//	
//	 public void encodeEnd(FacesContext context) throws IOException { 
//	  ResponseWriter writer = context.getResponseWriter(); 
////	     writer.endElement("div"); 
//	 } 
}
