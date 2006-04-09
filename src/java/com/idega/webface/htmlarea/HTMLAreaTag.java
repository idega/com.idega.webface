package com.idega.webface.htmlarea;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

/**
 * @author gimmi
 */
public class HTMLAreaTag extends UIComponentTag {
	
	private String _plugins;
	
	// Gotten from HtmlInputTextarea.class
	private String _accesskey = null;
	private int _cols = -1;
	private String _dir = null;
	private boolean _disabled = false;
	private String _lang = null;
	private boolean _readonly = false;
	private int _rows = -1;
	private String _style = null;
	private String _styleClass = null;
	private String _tabindex = null;
	private String _title = null;
	
	public String getComponentType() {
		return "HTMLArea";
	}
	
	public String getRendererType() {
		return null;
	}
	
	public void release() {      
		super.release();      
		this._plugins = null ; 
		this._accesskey = null;
		this._cols = -1;
		this._dir = null;
		this._disabled = false;
		this._lang = null;
		this._readonly = false;
		this._rows = -1;
		this._style = null;
		this._styleClass = null;
		this._tabindex = null;
		this._title = null;
	}
	
	protected void setProperties(UIComponent component) {      
		super.setProperties(component);
		if (component != null) {
			component.getAttributes().put("plugins", this._plugins);     
			component.getAttributes().put("accesskey", this._accesskey);     
			component.getAttributes().put("cols", new Integer(this._cols));     
			component.getAttributes().put("dir", this._dir);     
			component.getAttributes().put("disabled",new Boolean(this._disabled));     
			component.getAttributes().put("lang", this._lang);     
			component.getAttributes().put("readonly", new Boolean(this._readonly));     
			component.getAttributes().put("rows", new Integer(this._rows));     
			component.getAttributes().put("style", this._style);     
			component.getAttributes().put("styleClass", this._styleClass);     
			component.getAttributes().put("tabindex", this._tabindex);     
			component.getAttributes().put("title", this._title);
		}
	}
	
	public String getPlugins() {
		return this._plugins;
	}
	
	public void setPlugins(String plugins) {
		this._plugins = plugins;
	}
	
	public String getAccesskey() {
		return this._accesskey;
	}
	
	public void setAccesskey(String accesskey) {
		this._accesskey = accesskey;
	}
	
	public int getCols() {
		return this._cols;
	}
	
	public void setCols(int cols) {
		this._cols = cols;
	}
	
	public String getDir() {
		return this._dir;
	}
	
	public void setDir(String dir) {
		this._dir = dir;
	}
	
	public boolean getDisabled() {
		return this._disabled;
	}
	
	public void setDisabled(boolean disabled) {
		this._disabled = disabled;
	}
	
	public String getLang() {
		return this._lang;
	}
	
	public void setlang(String lang) {
		this._lang = lang;
	}
	
	public boolean getReadonly() {
		return this._readonly;
	}
	
	public void setReadonly(boolean readonly) {
		this._readonly = readonly;
	}
	
	public int getRows() {
		return this._rows;
	}
	
	public void setRows(int rows) {
		this._rows = rows;
	}
	
	public String getStyle() {
		return this._style;
	}
	
	public void setStyle(String style) {
		this._style = style;
	}
	
	public String getStyleClass() {
		return this._styleClass;
	}
	
	public void setStyleClass(String styleClass) {
		this._styleClass = styleClass;
	}
	
	public String getTabindex() {
		return this._tabindex;
	}
	
	public void setTabindex(String tabindex) {
		this._tabindex = tabindex;
	}
	
	public String getTitle() {
		return this._title;
	}
	
	public void setTitle(String title) {
		this._title = title;
	}
}
