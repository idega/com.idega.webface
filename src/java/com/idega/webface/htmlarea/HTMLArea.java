/*
 * Created on 22.10.2004
 */
package com.idega.webface.htmlarea;

import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;


/**
 * @author gimmi
 */
public class HTMLArea extends HtmlInputTextarea {
	
	private static String RENDERER_TYPE="wf_htmlarea";
	
	public static String getWFRendererType() {
		return RENDERER_TYPE;
	}
	
	public static String PLUGIN_TABLE_OPERATIONS = "TableOperations";
	public static String PLUGIN_CONTEXT_MENU = "ContextMenu";
	public static String PLUGIN_FULL_SCREEN = "FullScreen";
	public static String PLUGIN_LIST_TYPE = "ListType";
	public static String PLUGIN_CHARACTER_MAP = "CharacterMap";
	public static String PLUGIN_STYLIST = "Stylist";
	public static String PLUGIN_CSS = "CSS";
	
	private static final boolean DEFAULT_ALLOW_FONT_SELECTION = true;
	
	private String plugins;
	private Boolean allowFontSelection = null;
	
	public HTMLArea() {
		super();
	}
	
	/**
	 * Specify which plugins to use. If you wish to control the toolbarLevel add ([level]) behind then plugin name.
	 * <code>CSS(2)</code>
	 * @param plugins
	 */
	public void setPlugins(String plugins) {
		this.plugins = plugins;
	}
	
	public void addPlugin(String plugin, String toolbarLevel) {
		if (toolbarLevel != null && !toolbarLevel.trim().equals("")) {
			plugin += "("+toolbarLevel+")";
		}
		
		if (this.plugins == null) {
			this.plugins = plugin;
		} else {
			this.plugins += ","+plugin;
		}
	}
	
	public void addPlugin(String plugin) {
		addPlugin(plugin, null);
	}
	
	public String getPlugins() {
		if (this.plugins != null) {
			return this.plugins;
		}
		ValueBinding vb = getValueBinding("plugins");
		String v = vb != null ? (String)vb.getValue(getFacesContext()) : null;
		return v != null ? v : null;
	}
	
	public void setAllowFontSelection(boolean allowFontSelection) {
		this.allowFontSelection = new Boolean(allowFontSelection);
	}
	
	public boolean getAllowFontSelection() {
		if (this.allowFontSelection != null) {
			return this.allowFontSelection.booleanValue();
		}
		ValueBinding vb = getValueBinding("allowFontSelection");
		Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
		return v != null ? v.booleanValue() : DEFAULT_ALLOW_FONT_SELECTION;
	}
	
	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(context, values[0]);
		this.plugins = (String)values[1];
		this.allowFontSelection = (Boolean) values[2];
	}
	
	public Object saveState(FacesContext context)  {
		Object values[] = new Object[3];
		values[0] = super.saveState(context);
		values[1] = this.plugins;
		values[2] = this.allowFontSelection;
		
		return values;
	}
	
	protected Renderer getRenderer(FacesContext context) {
		return new HTMLAreaRenderer();
	}
	
	public String getRendererType() {
		return RENDERER_TYPE;
	}
}
