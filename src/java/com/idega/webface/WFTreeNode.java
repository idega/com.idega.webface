/*
 * $Id: WFTreeNode.java,v 1.12 2008/02/26 14:06:39 valdas Exp $
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
import java.util.Locale;

import org.apache.myfaces.custom.tree2.TreeNode;

import com.idega.core.builder.business.BuilderService;
import com.idega.core.builder.business.BuilderServiceFactory;
import com.idega.core.builder.data.ICPage;
import com.idega.core.data.ICTreeNode;
import com.idega.presentation.IWContext;
import com.idega.util.CoreUtil;


/**
 * 
 * Wrapper object for com.idega.core.data.ICTreeNode
 * 
 *  Last modified: $Date: 2008/02/26 14:06:39 $ by $Author: valdas $
 * 
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.12 $
 */
public class WFTreeNode implements TreeNode {
	
	private static final long serialVersionUID = 2723941084348902419L;
	
	private ICTreeNode<?> icNode;
	private List <WFTreeNode> children = null;
    private String type = null;
    private String iconURI = null;
    private String pageType = null;
    private ICTreeNode<?> parent = null;
    private String templateURI = null;
    
    private Locale currentLocale = null;

	public WFTreeNode() {
		super();
	}

	
	/**
	 * 
	 */
	public WFTreeNode(ICTreeNode<?> node) {
		this.icNode = node;
		String className = node.getClass().getName();
		if(className.indexOf('.')>-1){
			setType(className.substring(className.lastIndexOf('.')+1));
		} else {
			setType(className);
		}
	}

	public WFTreeNode(ICTreeNode<?> node, String iconURI, String pageType) {
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
	public List <WFTreeNode> getChildren() {
		addChildren();
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
		if (currentLocale == null) {
			currentLocale = CoreUtil.getIWContext().getCurrentLocale();
		}

		return this.icNode.getNodeName(currentLocale);
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
	}
	
	public void addChild(ICTreeNode<?> node, String iconUrl, String pageType){
		addChildren();
		this.children.add(new WFTreeNode(node, iconUrl, pageType));		
	}

	public void addChild(ICTreeNode<?> node) {
		addChildren();
		WFTreeNode newChild = new WFTreeNode(node);
		
		IWContext iwc = CoreUtil.getIWContext();
		BuilderService bservice = null;
		try {
			bservice = BuilderServiceFactory.getBuilderService(iwc);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		String startPageId = node.getId();
		ICPage page = null;
		try {
			page = bservice.getICPage(startPageId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if (page != null) {
			setPageType(page.getSubType());
			newChild = settingSubTypes(node, newChild);
			newChild.setParent(this.icNode);
			this.children.add(newChild);
		}
	}

	public WFTreeNode settingSubTypes(ICTreeNode<?> icnode, WFTreeNode wfnode){		
		IWContext iwc = CoreUtil.getIWContext();
		BuilderService bservice = null;
		try {
			bservice = BuilderServiceFactory.getBuilderService(iwc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(wfnode.getPageType() == null)
		wfnode.setPageType(bservice.getICPage(icnode.getId()).getSubType());
		if(icnode.getChildCount() == 0)
			return wfnode;		
		Iterator<?> it = icnode.getChildrenIterator();
		if (it != null) {
			int i = 0;
			while (it.hasNext()) {
				Object o = it.next();
				if (o instanceof ICTreeNode) {
					wfnode.getChildren().set(i, settingSubTypes((ICTreeNode<?>) o, wfnode.getChildren().get(i)));
					i++;
				}
			}
		}
		
		List <WFTreeNode> children = new ArrayList<WFTreeNode>();
		for(int j = 0; j < wfnode.getChildCount(); j++){
			children.add(null);
		}
		for (Iterator<?> iter = wfnode.getChildren().iterator(); iter.hasNext();) {
			WFTreeNode element = (WFTreeNode)iter.next();
			int order = bservice.getTreeOrder(Integer.valueOf(element.getIdentifier()).intValue());
			try {
				children.set(order - 1, element);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return wfnode;
			}
		}
		
		for (Iterator<?> iter = children.iterator(); iter.hasNext();) {
			if (iter.next() == null){
				return wfnode;
			}				
		}
		
		wfnode.children = children;
		
		return wfnode;
	}
	
	public void addChild(WFTreeNode wfnode){		
		addChildren();
		wfnode.setParent(this.icNode);
		this.children.add(wfnode);
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

	public ICTreeNode<?> getParent() {
		return parent;
	}

	public void setParent(ICTreeNode<?> parent) {
		this.parent = parent;
	}


	public String getTemplateURI() {
		return templateURI;
	}


	public void setTemplateURI(String templateURI) {
		this.templateURI = templateURI;
	}
	
	private void addChildren() {
		if(this.children == null) {
			this.children = new ArrayList<WFTreeNode>();
			if(icNode != null) {
				for (Iterator<?> iter = this.icNode.getChildrenIterator(); iter.hasNext();) {
					Object o = iter.next();
					if (o instanceof ICTreeNode) {
						this.children.add(new WFTreeNode((ICTreeNode<?>) o));
					}
				}
			}
		}
	}
}
