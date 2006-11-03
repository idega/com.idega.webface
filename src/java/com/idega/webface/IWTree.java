package com.idega.webface;

import org.apache.myfaces.custom.tree2.HtmlTree;
import org.apache.myfaces.custom.tree2.TreeModel;
import org.apache.myfaces.custom.tree2.TreeModelBase;
import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeState;

import com.idega.core.data.IWTreeNode;

public class IWTree extends HtmlTree{
	public static final String COMPONENT_TYPE = "com.idega.content.webface.IWTree";
	private static final String DEFAULT_RENDERER_TYPE = "com.idega.webface.IWTreeRenderer";

	public TreeModel _model;
	public TreeState _restoredState = null;
    
    public TreeModel getDataModel() {
        if (_model != null) {
            return _model;
        }
        Object value = getValue();
        System.out.println("Checking node type");
        if (value != null) {
            if (value instanceof TreeModel) {
            	System.out.println("TreeModel");
                _model = (TreeModel) value;
            }
            else if (value instanceof IWTreeNode) {
            	System.out.println("before IWTreeNode");
                _model = new TreeModelBase((TreeNode) value);
                System.out.println("after IWTreeNode");
            }
            else if (value instanceof IWTree) {
            	System.out.println("IWTree");
                _model = new TreeModelBase((TreeNode) value);
            }
//            else if (value instanceof PageTreeNode) {
//            	System.out.println("PageTreeNode");
//                _model = new TreeModelBase((TreeNode) value);
//            }
            else if (value instanceof TreeNode) {
            	System.out.println("TreeNode");
                _model = new TreeModelBase((TreeNode) value);
            }            
            else {
            	System.out.println("Neither");
//            	System.out.println("value is "+ value.getClass());
                throw new IllegalArgumentException("Value must be a TreeModel or TreeNode");
            }
        }
        else System.out.println("value = null");

        if (_restoredState != null){
        	System.out.println("Setting tree state");
//        	System.out.println(-restoredState.toString());
            _model.setTreeState(_restoredState); // set the restored state (if there is one) on the model
        }
        System.out.println("have value");
        return _model;
    }    
}
