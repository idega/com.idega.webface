/*
 * Created on 20.2.2004 by  tryggvil in project smile
 */
package net.sourceforge.smile.examples.cbp;

import javax.faces.component.UIComponent;

import net.sourceforge.smile.component.Tree;
import net.sourceforge.smile.component.TreeNode;
import net.sourceforge.smile.component.TreeSelectionChangedEvent;
import net.sourceforge.smile.component.TreeSelectionChangedListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.idega.faces.TestComponent1;

/**
 * IWHello //TODO: tryggvil Describe class
 * Copyright (C) idega software 2004
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class IWHello extends AbstractDemo implements TreeSelectionChangedListener{
	private static Log log = LogFactory.getLog(IWHello.class);
	
	public UIComponent createContent() {
		TestComponent1 comp = new TestComponent1();
		comp.addToTable(getTree());
		return comp;
	}
	
	public Tree getTree(){
		Tree tree = new Tree();
		tree.setId("tree");
		TreeNode nodeUSA = new TreeNode();
		nodeUSA.setLabel("USA");
		TreeNode nodeIndia = new TreeNode();
		nodeIndia.setLabel("India");
		TreeNode nodeMumbai = new TreeNode();
		nodeMumbai.setLabel("Mumbai");
		TreeNode nodeDelhi = new TreeNode();
		nodeDelhi.setLabel("Delhi");
		TreeNode nodeMadras = new TreeNode();
		nodeMadras.setLabel("Madras");
		TreeNode nodeEurope = new TreeNode();
		nodeEurope.setLabel("Europe");
		TreeNode nodeFrance = new TreeNode();
		nodeFrance.setLabel("France");
		nodeFrance.setExpanded(false);
		TreeNode nodeParis = new TreeNode();
		nodeParis.setLabel("Paris");
		TreeNode nodeLyon = new TreeNode();
		nodeLyon.setLabel("Lyon");
		TreeNode nodeBelgium = new TreeNode();
		nodeBelgium.setLabel("Belgium");
		nodeBelgium.setExpandOnClient(false);	// Serverside expand.
		nodeBelgium.setExpanded(false);
		
		TreeNode node;
		char c='a';
		for(int i=0; i<26; i++) {
			node = new TreeNode();
			node.setLabel(new String(new char[] {c}));
			c++;
			 
			nodeBelgium.getSubNodes().add(node);
		}
		

		nodeFrance.getSubNodes().add(nodeParis);
		nodeFrance.getSubNodes().add(nodeLyon);
		
		nodeIndia.getSubNodes().add(nodeMumbai);
		nodeIndia.getSubNodes().add(nodeDelhi);
		nodeIndia.getSubNodes().add(nodeMadras);
		
		nodeEurope.getSubNodes().add(nodeFrance);
		nodeEurope.getSubNodes().add(nodeBelgium);

		tree.getRootNodes().add(nodeUSA);
		tree.getRootNodes().add(nodeIndia);
		tree.getRootNodes().add(nodeEurope);
		
		tree.addTreeSelectionChangedListener(this);

		return tree;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.smile.component.TreeSelectionChangedListener#selectionChanged(net.sourceforge.smile.component.TreeSelectionChangedEvent)
	 */
	public void selectionChanged(TreeSelectionChangedEvent e) {
		Tree tree = (Tree) e.getSource();
		log.info("tree changed selection to <" + tree.getSelected().getLabel() + ">");
	}
	
}
