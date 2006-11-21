/*
 * $Id: WFTreeNode.java,v 1.6 2006/11/21 11:58:05 justinas Exp $
 * Created on 2.5.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.myfaces.custom.tree2.TreeNode;
import com.idega.core.data.ICTreeNode;
import com.idega.presentation.IWContext;


/**
 * 
 * Wrapper object for com.idega.core.data.ICTreeNode
 * 
 *  Last modified: $Date: 2006/11/21 11:58:05 $ by $Author: justinas $
 * 
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.6 $
 */
public class WFTreeNode implements TreeNode {
	
	private ICTreeNode icNode;
	private List children = null;
    private String type = null;
    private String iconURI = null;
    private String pageType = null;
    private ICTreeNode parent = null;

	public WFTreeNode() {
		super();
//		children.
	}

	
	/**
	 * 
	 */
	
	public WFTreeNode(ICTreeNode node) {
		this.icNode = node;
		String className = node.getClass().getName();
		if(className.indexOf('.')>-1){
			setType(className.substring(className.lastIndexOf('.')+1));
		} else {
			setType(className);
		}
	}

	public WFTreeNode(ICTreeNode node, String iconURI, String pageType) {
		this.icNode = node;
		this.iconURI = iconURI;
		this.pageType = pageType;
		String className = node.getClass().getName();
		if(className.indexOf('.')>-1){
			setType(className.substring(className.lastIndexOf('.')+1));
		} else {
			setType(className);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.apache.myfaces.custom.tree2.TreeNode#isLeaf()
	 */
	public boolean isLeaf() {		
		if(this.icNode != null){
			if(((this.children == null) || (this.children.isEmpty())) && this.icNode.isLeaf()){
				return true;
			}
			else return false;
		}
		else{
			if((this.children == null) || (this.children.isEmpty())){
				return true;
			}
			else 
				return false;			
		}
			
//		if(this.icNode != null)
//			if(this.icNode.isLeaf()){
//				return true;}
//			else return false;
//		if(((this.children == null) || (this.children.isEmpty())) ){
//			return true;
//		}
//		else 
//			return false;
//		return this.icNode.isLeaf();
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.custom.tree2.TreeNode#setLeaf(boolean)
	 */
	public void setLeaf(boolean leaf) {
		//Do nothing
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.custom.tree2.TreeNode#getChildren()
	 */
	public List getChildren() {
		if(this.children == null){
			this.children = new ArrayList();
			for (Iterator iter = this.icNode.getChildrenIterator(); iter.hasNext();) {
				this.children.add(new WFTreeNode((ICTreeNode) iter.next()));
			}
		}

//		System.out.println("[getChildren]:"+this.children.size());
		return this.children;
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.custom.tree2.TreeNode#getType()
	 */
	public String getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.custom.tree2.TreeNode#setType(java.lang.String)
	 */
	public void setType(String type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.custom.tree2.TreeNode#getDescription()
	 */
	public String getDescription() {
		IWContext iwc = IWContext.getInstance();
		return this.icNode.getNodeName(iwc.getCurrentLocale(),iwc);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.custom.tree2.TreeNode#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		//DoNothing
	}
 
	
	/* (non-Javadoc)
	 * @see org.apache.myfaces.custom.tree2.TreeNode#setIdentifier(java.lang.String)
	 */
	public void setIdentifier(String identifier) {
		//doNothing
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.custom.tree2.TreeNode#getIdentifier()
	 */
	public String getIdentifier() {
//		System.out.println("[getIdentifier]:"+this.icNode.getId());
//		return this.icNode.getId();
		if(this.icNode.getId() == null)
			return "0";
		else return this.icNode.getId();
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.custom.tree2.TreeNode#getChildCount()
	 */
	public int getChildCount() {
		if(this.icNode != null)
			if(this.icNode.getChildCount() != 0)
				return this.icNode.getChildCount();
		if((this.children == null) || (this.children.isEmpty())){
			return 0;
		}
		else return this.children.size();
//		System.out.println("[getChildCount]:"+this.icNode.getChildCount());
//		return this.icNode.getChildCount();
	}
//	public void addChild(ICTreeNode node, String iconUrl, String pageType){		
//		if(this.children == null){
//			this.children = new ArrayList();
//			if(icNode != null)
//				for (Iterator iter = this.icNode.getChildrenIterator(); iter.hasNext();) {
//					this.children.add(new WFTreeNode((ICTreeNode) iter.next()));
//				}
//		}		
////		this.children.add(node);
//		this.children.add(new WFTreeNode(node, iconUrl, pageType));
//		
//	}

	
	public void addChild(ICTreeNode node, String iconUrl, String pageType){		
		if(this.children == null){
			this.children = new ArrayList();
			if(icNode != null)
				for (Iterator iter = this.icNode.getChildrenIterator(); iter.hasNext();) {
					this.children.add(new WFTreeNode((ICTreeNode) iter.next()));
				}
		}		
//		this.children.add(node);
		this.children.add(new WFTreeNode(node, iconUrl, pageType));		
	}

	public void addChild(ICTreeNode node){		
		if(this.children == null){
			this.children = new ArrayList();
			if(icNode != null)
				for (Iterator iter = this.icNode.getChildrenIterator(); iter.hasNext();) {
					this.children.add(new WFTreeNode((ICTreeNode) iter.next()));
				}
		}		
//		this.children.add(node);
		WFTreeNode newChild = new WFTreeNode(node);
		newChild.setParent(this.icNode);
		this.children.add(newChild);
//		this.children.add(new WFTreeNode(node));
//		node.setParent(this.icNode);
		
//		node.setParent(this);
	}

	public void addChild(WFTreeNode wfnode){		
		if(this.children == null){
			this.children = new ArrayList();
			if(icNode != null)
				for (Iterator iter = this.icNode.getChildrenIterator(); iter.hasNext();) {
					this.children.add(new WFTreeNode((ICTreeNode) iter.next()));
				}
		}		
//		this.children.add(node);
//		child.setParent(this);
		wfnode.setParent(this.icNode);
//		wfnode.setParent(this);
		this.children.add(wfnode);
//		wfnode.setParent(this);
	}

	
	public String getIconURI() {
		return iconURI;
	}

	public void setIconURI(String iconURI) {
		this.iconURI = iconURI;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public ICTreeNode getParent() {
		return parent;
	}

	public void setParent(ICTreeNode parent) {
		this.parent = parent;
	}
}
