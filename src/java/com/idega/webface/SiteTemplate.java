package com.idega.webface;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.apache.myfaces.custom.tree2.TreeNode;
import com.idega.core.data.ICTreeNode;
import com.idega.core.data.IWTreeNode;
import com.idega.core.data.ICTreeNodeAddable;
import com.idega.presentation.PageTag;
import com.idega.webface.WFTreeNode;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class SiteTemplate {
// TO DO change IWTreeNode to PageTreeNode
	
	private static final String SITE_LINK = "http://localhost:8080" + "/idegaweb/bundles/com.idega.content.bundle/resources/templates/site-templates.xml";
	private static final String PAGE_LINK = "http://localhost:8080" + "/idegaweb/bundles/com.idega.content.bundle/resources/templates/page-templates.xml";
	
	TreeNode siteTree = null;
	TreeNode pageTree = null;
	String path = null;
	Document siteDocument = null;
//	ICTreeNodeAddable iwnode = null;
	
	public TreeNode getSiteTree(){
//	public IWTreeNode getSiteTree(){
//		TreeNode result = getSiteStructure();
		System.out.println("SITE_LINK = "+"http://localhost:8080");
		return getSiteStructure();
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
	
	public TreeNode getSiteStructure(){
//	public IWTreeNode getSiteStructure(){
		
//		ICTreeNodeAddable rootNode = null;
//		IWTreeNode rootNode = null;
		
		Document siteDocument = getXMLDocument(SITE_LINK);
		Element root = siteDocument.getRootElement();
		Element siteRoot = (Element)root.getChild("site");
		
		Element currentElement = (Element)siteRoot.getChildren().get(0);
		
//		rootNode = (ICTreeNodeAddable)(new IWTreeNode(currentElement.getAttributeValue("name")));
		IWTreeNode rootNode = new IWTreeNode(currentElement.getAttributeValue("name"));
		
//		rootNode = new IWTreeNode(currentElement.getAttributeValue("name"));
//		
//		currentElement = (Element)currentElement.getChildren().get(0);
		
		rootNode = getPage(currentElement, rootNode);
//		ICTreeNode icnode = rootNode.getParentNode();
		ICTreeNode icnode = rootNode;

		
//		IWTreeNode node = rootNode;

		return new WFTreeNode(icnode);
//		return rootNode;

//		return rootNode;	
	}	
	
//	public ICTreeNodeAddable getPage(Element currElement, ICTreeNodeAddable currNode){
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
	
	public TreeNode getPageTree(){
		Document siteDocument = getXMLDocument(SITE_LINK);
		Element root = siteDocument.getRootElement();
//		Element siteRoot = (Element)root.getChild("site");
//		root = (Element)root.getChild("site"); 
		
//		Element currentElement = (Element)siteRoot.getChildren().get(0);
		Element currentElement = (Element)root;
		IWTreeNode rootNode = new IWTreeNode("");
		
		rootNode = getPage(currentElement, rootNode);
		ICTreeNode icnode = rootNode;
		return new WFTreeNode(icnode);
		}

//	public String getPath() {
//		return ThemesHelper.getInstance().getWebRootWithoutContent() + "/idegaweb/bundles/com.idega.content.bundle/resources/templates/site-templates.xml";
//	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setSiteDocument(Document siteDocument) {
		this.siteDocument = siteDocument;
	}
}
