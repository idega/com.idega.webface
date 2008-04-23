package com.idega.webface;

import org.apache.myfaces.custom.tree2.HtmlTree;
import org.apache.myfaces.custom.tree2.TreeModel;
import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeState;

public class IWTree extends HtmlTree {
	public static final String COMPONENT_TYPE = "com.idega.content.webface.IWTree";

	public TreeModel _model;
	public TreeState _restoredState = null;
	
    public IWTree(TreeNode value, boolean showRootNode) {
		this();
		setValue(value);
		setShowRootNode(showRootNode);
	}
 
	public IWTree() {
		super();
	} 
}
