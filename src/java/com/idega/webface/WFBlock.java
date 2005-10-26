/*
 * Created on 5.9.2003 by tryggvil in project com.project
 */
package com.idega.webface;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Component with title bar and container area. Copyright (C) idega software
 * 2003
 * 
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson </a>
 * @author Anders Lindman
 * @version 1.0
 */
public class WFBlock extends WFContainer {

	//static constants
	public static String RENDERER_TYPE = "wf_block";
	public static final String FACET_TOOLBAR="wf_block_toolbar";
	public static final String FACET_TITLEBAR="wf_block_titlebar";
	
	private boolean toolbarEmbeddedInTitlebar = false;
	private String mainAreaStyleClass = WFConstants.STYLE_CLASS_MAINAREA;

	
	public WFBlock() {
		this("untitled");
	}

	public WFBlock(String titleBarText) {
		this(titleBarText, false);
	}
	
	public WFBlock(String titleBarText, boolean titleIsVB) {
		setStyleClass(RENDERER_TYPE);
		setMainAreaStyleClass(WFConstants.STYLE_CLASS_MAINAREA);
		WFTitlebar titlebar = new WFTitlebar(titleBarText, titleIsVB);
		setTitlebar(titlebar);
		//setDefaultToolbar();
		//WFContainer mainArea = new WFContainer();
		//super.add(mainArea);
	}

	/**
	 *  
	 */
	private void setDefaultToolbar() {
		/*if (getToolbar() == null) {
			WFToolbar toolbar = new WFToolbar();
			this.setToolbar(toolbar);
			
			WFBackButton back = new WFBackButton();
			back.setId(this.getId()+"_back");
			WFForwardButton forward = new WFForwardButton();
			forward.setId(this.getId()+"_forward");
			toolbar.addButton(back);
			toolbar.addButton(forward);
			WFHelpButton help = new WFHelpButton();
			help.setId(this.getId()+"_help");
			WFCloseButton close = new WFCloseButton();
			close.setId(this.getId()+"_close");
	
			toolbar.addButton(help);
			toolbar.addButton(close);
		}*/
	}

	protected void initializeDefault() {
		String title = (String) getAttributes().get("title");
		if (title != null) {
			setTitlebar(new WFTitlebar(title));
		}
		setDefaultToolbar();
	}
	
	/**
	 * @return
	 */
	public WFTitlebar getTitlebar() {
		return (WFTitlebar) getFacets().get(FACET_TITLEBAR);
	}

	/**
	 * @return
	 */
	public WFToolbar getToolbar() {
		WFToolbar toolbar = null;
		if (isToolbarEmbeddedInTitlebar()) {
			if (getTitlebar() != null) {
				toolbar = getTitlebar().getEmbeddedToolbar();
			}
		}
		else {
			toolbar = (WFToolbar) getFacets().get(FACET_TOOLBAR);
		}
		return toolbar;
	}

	/**
	 * @param titlebar
	 */
	public void setTitlebar(WFTitlebar titlebar) {
		getFacets().put(FACET_TITLEBAR, titlebar);
		String titleBarId = titlebar.getId();
		if(titleBarId==null){
			String thisId = this.getId();
			if(thisId!=null){
				titleBarId=thisId+"_titlebar";
				titlebar.setId(titleBarId);
			}
		}
	}

	/**
	 * @param toolbar
	 */
	public void setToolbar(WFToolbar toolbar) {
		if (isToolbarEmbeddedInTitlebar()) {
			if (getTitlebar() != null) {
				getFacets().remove(FACET_TOOLBAR);
				getTitlebar().setEmbeddedToolbar(toolbar);
			}
		}
		else {
			if (getTitlebar() != null) {
				getTitlebar().removeEmbeddedToolbar();
			}
			getFacets().put(FACET_TOOLBAR, toolbar);
		}
		String toolbarId = toolbar.getId();
		if(toolbarId==null){
			String thisId = this.getId();
			if(thisId!=null){
				toolbarId=thisId+"_toolbar";
				toolbar.setId(toolbarId);
			}
		}
	}

	/**
	 * Sets the css style class for the main area in this block.
	 */
	public void setMainAreaStyleClass(String mainAreaStyleClass) {
		this.mainAreaStyleClass = mainAreaStyleClass;
	}

	/**
	 * Returns the css style class for the main area in this block.
	 */
	public String getMainAreaStyleClass() {
		return mainAreaStyleClass;
	}

	/**
	 * Adds a child component to this block.
	 */
	public void add(UIComponent child) {
		/*WFContainer mainArea = null;
		if(getChildren().size()==0) {
			mainArea = new WFContainer();
			getChildren().add(mainArea);
		}
		else {
			mainArea = (WFContainer) getChildren().get(0);
		}
		mainArea.add(child);
		*/
		super.add(child);
	}

	public String getRendererType() {
		return RENDERER_TYPE;
	}

	public void encodeBegin(FacesContext context) throws IOException {
		if (!isInitialized()) {
			initializeDefault();
			this.initializeComponent(null);
			this.setInitialized();
		}
		super.encodeBegin(context);
	}

	/*
	 * public void encodeBegin(FacesContext context) throws IOException { if
	 * (!isRendered()) { return; } super.encodeBegin(context); if
	 * (mainAreaStyleClass != null) { WFContainer mainArea = (WFContainer)
	 * getChildren().get(0); mainArea.setStyleClass(mainAreaStyleClass); } if
	 * (!isToolbarEmbeddedInTitlebar()) { renderFacet(context, FACET_TOOLBAR); }
	 * renderFacet(context, FACET_TITLEBAR); }
	 */
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		if (!isRendered()) {
			return;
		}
		
		
		super.encodeChildren(context);
		//for (Iterator iter = getChildren().iterator(); iter.hasNext();) {
		//	UIComponent child = (UIComponent) iter.next();
		//	RenderUtils.renderChild(context,child);
		//}
	}

	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		if (!isRendered()) {
			return;
		}
		super.encodeEnd(context);
	}

	/**
	 * @return Returns the toolbarEmbeddedInTitlebar.
	 */
	public boolean isToolbarEmbeddedInTitlebar() {
		return toolbarEmbeddedInTitlebar;
	}

	/**
	 * @param toolbarEmbeddedInTitlebar
	 *            the toolbarEmbeddedInTitlebar to set
	 */
	public void setToolbarEmbeddedInTitlebar(boolean toolbarEmbeddedInTitlebar) {
		WFToolbar toolbar = getToolbar();
		this.toolbarEmbeddedInTitlebar = toolbarEmbeddedInTitlebar;
		if (toolbar != null) {
			setToolbar(toolbar);
		}
	}

	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[3];
		values[0] = super.saveState(ctx);
		values[1] = new Boolean(toolbarEmbeddedInTitlebar);
		values[2] = mainAreaStyleClass;
		return values;
	}

	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(ctx, values[0]);
		toolbarEmbeddedInTitlebar = ((Boolean) values[1]).booleanValue();
		mainAreaStyleClass = (String) values[2];
		//super.restoreState(ctx,state);
	}

	public void processRestoreState(FacesContext context, Object state) {
		super.processRestoreState(context,state);
		/*
		if (context == null)
			throw new NullPointerException("context");
		Object myState = ((Object[]) state)[0];
		Map facetMap = (Map) ((Object[]) state)[1];
		List childrenList = (List) ((Object[]) state)[2];
		for (Iterator it = getFacets().entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			Object facetState = facetMap.get(entry.getKey());
			if (facetState != null) {
				((UIComponent) entry.getValue()).processRestoreState(context, facetState);
			}
			else {
				context.getExternalContext().log("No state found to restore facet " + entry.getKey());
			}
		}
		int childCount = getChildCount();
		if (childCount > 0) {
			int idx = 0;
			for (Iterator it = getChildren().iterator(); it.hasNext();) {
				UIComponent child = (UIComponent) it.next();
				Object childState = childrenList.get(idx++);
				if (childState != null) {
					child.processRestoreState(context, childState);
				}
				else {
					context.getExternalContext().log("No state found to restore child of component " + getId());
				}
			}
		}
		restoreState(context, myState);
		*/
	}
	
	
    public Object processSaveState(FacesContext context)
    {
    	return super.processSaveState(context);
    	/*
        if (context == null) throw new NullPointerException("context");
        if (isTransient()) return null;
        Map facetMap = null;
        for (Iterator it = getFacets().entrySet().iterator(); it.hasNext(); )
        {
            Map.Entry entry = (Map.Entry)it.next();
            if (facetMap == null) facetMap = new HashMap();
            UIComponent component = (UIComponent)entry.getValue();
            if (!component.isTransient())
            {
                facetMap.put(entry.getKey(), component.processSaveState(context));
            }
        }
        List childrenList = null;
        if (getChildCount() > 0)
        {
            for (Iterator it = getChildren().iterator(); it.hasNext(); )
            {
                UIComponent child = (UIComponent)it.next();
                if (!child.isTransient())
                {
                    if (childrenList == null) childrenList = new ArrayList(getChildCount());
                    childrenList.add(child.processSaveState(context));
                }
            }
        }
        return new Object[] {saveState(context),
                             facetMap,
                             childrenList};
        */
    }

    /**
     * Sets so that the "mainArea" or area below the title and toolbars doesn't include
     *  any margins between the borders and the content of this block.
     */
    public void setNoMarginsOnMainArea(){
    		setMainAreaStyleAttributes("margin:0px;padding:0px;");
    }

	/**
	 * Sets all style attributes for the mainArea container
	 * @param string
	 */
	public void setMainAreaStyleAttributes(String styleString) {
		getAttributes().put("mainAreaStyle",styleString);
	}
    
	/**
	 * Gets all set style attributes for the mainArea container
	 * @param string
	 */
	public String getMainAreaStyleAttributes() {
		return (String)getAttributes().get("mainAreaStyle");
	}
    
}