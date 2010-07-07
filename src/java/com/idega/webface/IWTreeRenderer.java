package com.idega.webface;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.custom.tree2.HtmlTreeRenderer;
import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeState;
import org.apache.myfaces.custom.tree2.TreeWalker;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

public class IWTreeRenderer extends HtmlTreeRenderer {
	
	private int node = 0;
	private boolean sourceTree = false;

	public String getNode() {
		node++;
		return "node" + node;
	}

	public IWTreeRenderer() {
	}

	protected void beforeNodeEncode(FacesContext context, ResponseWriter out, IWTree tree) throws IOException {
		out.startElement(HTML.LI_ELEM, tree);
		out.writeAttribute(HTML.ID_ATTR, tree.getDataModel().getNodeById(tree.getNodeId()).getIdentifier(), null);
		out.writeAttribute(HTML.CLASS_ATTR, "idegaTreeListElementContainerStyleClass", null);
		
		if (sourceTree) {
			out.writeAttribute("sourceTree", "true", null);
			out.writeAttribute("noChildren", "true", null);
		}
		
		WFTreeNode treeNode = (WFTreeNode) tree.getNode();
		if (treeNode.getPageType() != null) {
			out.writeAttribute("pageType", treeNode.getPageType(), null);
		}
		if (treeNode.getIconURI() != null) {
			out.writeAttribute("iconfile", treeNode.getIconURI(), null);
		}

		if (treeNode.getTemplateURI() != null) {
			out.writeAttribute("templateFile", treeNode.getTemplateURI(), null);
		}
	}

	@Override
	protected void afterNodeEncode(FacesContext context, ResponseWriter out) throws IOException {
		out.endElement(HTML.LI_ELEM);
	}

	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		IWTree tree = (IWTree) component;
		sourceTree = false;

		Object sourceTreeAtt = tree.getAttributes().get("sourceTree");
		if (sourceTreeAtt != null && sourceTreeAtt.equals("true")) {
			sourceTree = true;
		}
		tree.setShowLines(false);

		if (!component.isRendered()) {
			return;
		}
		if (tree.getValue() == null) {
			return;
		}
		
		ResponseWriter out = context.getResponseWriter();
		String clientId = null;

		if (component.getId() != null && !component.getId().startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
			clientId = component.getClientId(context);
		}
		boolean isOuterSpanUsed = false;

		if (clientId != null) {
			isOuterSpanUsed = true;
			out.startElement("span", component);
			out.writeAttribute("id", component.getId(), "id");

			out.startElement("div", component);
			out.writeAttribute("id", "div_id_" + component.getId(), "id");
		}

		boolean clientSideToggle = tree.isClientSideToggle();
		boolean showRootNode = tree.isShowRootNode();
		TreeState state = tree.getDataModel().getTreeState();
		TreeWalker walker = tree.getDataModel().getTreeWalker();

		walker.reset();
		walker.setTree(tree);

		walker.setCheckState(!clientSideToggle); // walk all nodes in client mode

		if (showRootNode) {
			if (walker.next()) {
				if (!tree.getNode().isLeaf()) {
					out.startElement(HTML.UL_ELEM, tree);
					out.writeAttribute(HTML.ID_ATTR, "page_tree_div", null);
					out.writeAttribute(HTML.CLASS_ATTR, "tree_drag_drop", null);
				}

				encodeRoot(context, out, tree, walker);
			}
		}
		else {
			if (walker.next()) {
				if (!tree.getNode().isLeaf()) {
					out.startElement(HTML.UL_ELEM, tree);
					out.writeAttribute(HTML.ID_ATTR, "page_tree_div", null);
					out.writeAttribute(HTML.CLASS_ATTR, "tree_drag_drop", null);
				}
				TreeNode rootNode = tree.getNode();
				String rootNodeId = tree.getNodeId();
				if (!state.isNodeExpanded(rootNodeId)) {
					state.toggleExpanded(rootNodeId);
				}
				for (int i = 0; i < rootNode.getChildCount(); i++) {
					if (walker.next()) {
						encodeRoot(context, out, tree, walker);
					}
				}

			}
		}
		if (!tree.getNode().isLeaf()) {
			out.endElement(HTML.UL_ELEM);
		}
		tree.setNodeId(null);

		if (isOuterSpanUsed) {
			out.endElement("div");
			out.endElement("span");
		}
	}

	protected void encodeTree(FacesContext context, ResponseWriter out, IWTree tree, TreeWalker walker, TreeNode node) throws IOException {
		boolean clientSideToggle = tree.isClientSideToggle();

		HtmlRendererUtils.writePrettyLineSeparator(context);
		beforeNodeEncode(context, out, tree);
		encodeCurrentNode(context, out, tree, node);

		if (clientSideToggle) {
			out.startElement(HTML.SPAN_ELEM, tree);
			out.writeAttribute(HTML.ID_ATTR, tree.getDataModel().getNodeById(tree.getNodeId()).getIdentifier(), null);
			if (!tree.getDataModel().getNodeById(tree.getNodeId()).isLeaf()) {
				out.writeAttribute(HTML.STYLE_ATTR, "display:block", null);
			}
			else {
				out.writeAttribute(HTML.STYLE_ATTR, "display:none", null);
			}
		}

		if (tree.getDataModel().getNodeById(tree.getNodeId()).getChildCount() > 0) {
			out.startElement(HTML.UL_ELEM, tree);
		}

		if (tree.getDataModel().getNodeById(tree.getNodeId()).isLeaf()) {
			if (clientSideToggle) {
				out.endElement(HTML.SPAN_ELEM);
			}
			out.endElement(HTML.LI_ELEM);
			return;
		}
		else {
			int child_count = tree.getDataModel().getNodeById(tree.getNodeId()).getChildCount();
			List<TreeNode> children = null;
			if (child_count > 0) {
				children = tree.getDataModel().getNodeById(tree.getNodeId()).getChildren();
				for (int i = 0; i < child_count; i++) {
					node = children.get(i);
					walker.next();
					encodeTree(context, out, tree, walker, node);
				}
			}
			out.endElement(HTML.UL_ELEM);
			if (clientSideToggle) {
				out.endElement(HTML.SPAN_ELEM);
			}
			out.endElement(HTML.LI_ELEM);
		}
	}

	protected void encodeRoot(FacesContext context, ResponseWriter out, IWTree tree, TreeWalker walker) throws IOException {
		boolean clientSideToggle = tree.isClientSideToggle();
		boolean expanded = false;
		HtmlRendererUtils.writePrettyLineSeparator(context);

		out.startElement(HTML.LI_ELEM, tree);
		out.writeAttribute(HTML.ID_ATTR, tree.getDataModel().getNodeById(tree.getNodeId()).getIdentifier(), null);

		if (sourceTree) {
			out.writeAttribute("sourceTree", "true", null);
			out.writeAttribute("noChildren", "true", null);
		}
		
		WFTreeNode treeNode = (WFTreeNode) tree.getNode();
		if (treeNode.getIconURI() != null) {
			out.writeAttribute("iconfile", treeNode.getIconURI(), null);
		}
		if (treeNode.getPageType() != null) {
			out.writeAttribute("pageType", treeNode.getPageType(), null);
		}
		if (treeNode.getTemplateURI() != null) {
			out.writeAttribute("templateFile", treeNode.getTemplateURI(), null);
		}
		encodeRootNode(context, out, tree);

		if (clientSideToggle) {
			out.startElement(HTML.SPAN_ELEM, tree);
			out.writeAttribute(HTML.ID_ATTR, tree.getDataModel().getNodeById(tree.getNodeId()).getIdentifier(), null);
			if (!tree.getDataModel().getNodeById(tree.getNodeId()).isLeaf()) {
				out.writeAttribute(HTML.STYLE_ATTR, "display:block", null);
			}
			else {
				out.writeAttribute(HTML.STYLE_ATTR, "display:none", null);
			}
		}

		TreeNode node = tree.getNode();

		if (!node.isLeaf()) {
			out.startElement(HTML.UL_ELEM, tree);
			expanded = true;
		}

		int child_count = node.getChildCount();
		for (int i = 0; i < child_count; i++) {
			if (walker.next()) {
				encodeTree(context, out, tree, walker, node);
			}
		}

		if (expanded) {
			out.endElement(HTML.UL_ELEM);
		}

		if (clientSideToggle) {
			out.endElement(HTML.SPAN_ELEM);
		}
		out.endElement(HTML.LI_ELEM);
	}

	/**
	 * Handles the encoding related to the navigation functionality.
	 * 
	 * @param context
	 *          FacesContext
	 * @param out
	 *          ResponseWriter
	 * @param tree
	 *          HtmlTree
	 * @return The additional navigation image to display inside the node (if
	 *         any). Only used with client-side toggle.
	 * @throws IOException
	 */

	private String getImageSrc(FacesContext context, UIComponent component, String imageName, boolean withContextPath) {
		String imageLocation = ((IWTree) component).getImageLocation();
		AddResource addResource = AddResourceFactory.getInstance(context);

		if (imageLocation == null) {
			return addResource.getResourceUri(context, HtmlTreeRenderer.class, "images2/" + imageName, withContextPath);
		}
		else {
			return addResource.getResourceUri(context, imageLocation + "/" + imageName, withContextPath);
		}
	}

	protected void encodeCurrentNode(FacesContext context, ResponseWriter out, IWTree tree, TreeNode node) throws IOException {
		String nodeType = node.getType();
		UIComponent nodeTypeFacet = tree.getFacet(nodeType);

		if (nodeTypeFacet == null) {
			throw new IllegalArgumentException("(not root)Unable to locate facet with the name: " + node.getType());
		}

		RendererUtils.renderChild(context, nodeTypeFacet);
	}

	protected void encodeRootNode(FacesContext context, ResponseWriter out, IWTree tree) throws IOException {
		TreeNode node = tree.getNode();
		boolean showRootNode = tree.isShowRootNode();
		boolean showLines = tree.isShowLines();

		String nodeType = node.getType();

		UIComponent nodeTypeFacet = tree.getFacet(nodeType);

		if (nodeTypeFacet == null) {
			System.out.println("Unable to locate facet with the name: " + nodeType);
			throw new IllegalArgumentException("Unable to locate facet with the name: " + node.getType());
		}

		String[] pathInfo = tree.getPathInformation(tree.getNodeId());
		int paddingLevel = pathInfo.length - 1;

		for (int i = (showRootNode ? 0 : 1); i < paddingLevel; i++) {
			boolean lastChild = tree.isLastChild(pathInfo[i]);
			String lineSrc = (!lastChild && showLines) ? getImageSrc(context, tree, "line-trunk.gif", true) : getImageSrc(context, tree, "spacer.gif", true);

			out.startElement(HTML.IMG_ELEM, tree);
			out.writeURIAttribute(HTML.SRC_ATTR, lineSrc, null);

			out.endElement(HTML.IMG_ELEM);
		}

		RendererUtils.renderChild(context, nodeTypeFacet);
	}
}