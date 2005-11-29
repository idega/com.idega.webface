/*
 * Created on 22.10.2004
 */
package com.idega.webface.htmlarea;

import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;

import com.idega.webface.htmlarea.HTMLAreaRenderer;


/**
 * @author gimmi
 */
public class HTMLArea extends HtmlInputTextarea {
	
	public static String RENDERER_TYPE="wf_htmlarea";
	
	public static String PLUGIN_TABLE_OPERATIONS = "TableOperations";
	public static String PLUGIN_CONTEXT_MENU = "ContextMenu";
	public static String PLUGIN_LIST_TYPE = "ListType";
	public static String PLUGIN_CHARACTER_MAP = "CharacterMap";
	public static String PLUGIN_DYNAMIC_CSS = "DynamicCSS";
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
		
		if (plugins == null) {
			plugins = plugin;
		} else {
			plugins += ","+plugin;
		}
	}
	
	public void addPlugin(String plugin) {
		addPlugin(plugin, null);
	}
	
	public String getPlugins() {
		if (plugins != null) return plugins;
		ValueBinding vb = getValueBinding("plugins");
		String v = vb != null ? (String)vb.getValue(getFacesContext()) : null;
		return v != null ? v : null;
	}
	
	public void setAllowFontSelection(boolean allowFontSelection) {
		this.allowFontSelection = new Boolean(allowFontSelection);
	}
	
	public boolean getAllowFontSelection() {
		if (allowFontSelection != null) return allowFontSelection.booleanValue();
		ValueBinding vb = getValueBinding("allowFontSelection");
		Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
		return v != null ? v.booleanValue() : DEFAULT_ALLOW_FONT_SELECTION;
	}
	
	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(context, values[0]);
		plugins = (String)values[1];
		allowFontSelection = (Boolean) values[2];
	}
	
	public Object saveState(FacesContext context)  {
		Object values[] = new Object[3];
		values[0] = super.saveState(context);
		values[1] = plugins;
		values[2] = allowFontSelection;
		
		return values;
	}
	
	protected Renderer getRenderer(FacesContext context) {
		return new HTMLAreaRenderer();
	}
	
	public String getRendererType() {
		return RENDERER_TYPE;
	}
}
