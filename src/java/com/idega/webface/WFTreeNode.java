/*
 * $Id: WFTreeNode.java,v 1.4 2006/05/29 18:23:47 tryggvil Exp $
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
 *  Last modified: $Date: 2006/05/29 18:23:47 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.4 $
 */
public class WFTreeNode implements TreeNode {
	
	private ICTreeNode icNode;
	private List children = null;
    private String type = null;
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

	/* (non-Javadoc)
	 * @see org.apache.myfaces.custom.tree2.TreeNode#isLeaf()
	 */
	public boolean isLeaf() {
		return this.icNode.isLeaf();
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

		System.out.println("[getChildren]:"+this.children.size());
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
		System.out.println("[getIdentifier]:"+this.icNode.getId());
		return this.icNode.getId();
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.custom.tree2.TreeNode#getChildCount()
	 */
	public int getChildCount() {
		System.out.println("[getChildCount]:"+this.icNode.getChildCount());
		return this.icNode.getChildCount();
	}
}
