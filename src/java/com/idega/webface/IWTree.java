package com.idega.webface;

import org.apache.myfaces.custom.tree2.HtmlTree;
import org.apache.myfaces.custom.tree2.TreeModel;
import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeState;

public class IWTree extends HtmlTree{
	public static final String COMPONENT_TYPE = "com.idega.content.webface.IWTree";
	//private static final String DEFAULT_RENDERER_TYPE = "com.idega.webface.IWTreeRenderer";

	public TreeModel _model;
	public TreeState _restoredState = null;
//	public String noChildren = null;
    public IWTree(TreeNode value, boolean showRootNode) {
		super();
		// TODO Auto-generated constructor stub
		setValue(value);
		setShowRootNode(showRootNode);
	}
 
	public IWTree() {
		super();
		// TODO Auto-generated constructor stub
	}

//	public TreeModel getDataModel() {
//        if (_model != null) {
//            return _model;
//        }
//        Object value = getValue();
//        if (value != null) {
//            if (value instanceof TreeModel) {
//                _model = (TreeModel) value;
//            }
////            else if (value instanceof IWTreeNode) {
////                _model = new TreeModelBase((TreeNode) value);
////            }
////            else if (value instanceof IWTree) {
////                _model = new TreeModelBase((TreeNode) value);
////            }
//            else if (value instanceof TreeNode) {
//                _model = new TreeModelBase((TreeNode) value);
//            }            
//            else {
//                throw new IllegalArgumentException("Value must be a TreeModel or TreeNode");
//            }
//        }
//
//        if (_restoredState != null){
//            _model.setTreeState(_restoredState); // set the restored state (if there is one) on the model
//        }
//        return _model;
//    }    
}
