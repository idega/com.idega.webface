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
		_plugins = null ; 
		_accesskey = null;
		_cols = -1;
		_dir = null;
		_disabled = false;
		_lang = null;
		_readonly = false;
		_rows = -1;
		_style = null;
		_styleClass = null;
		_tabindex = null;
		_title = null;
	}
	
	protected void setProperties(UIComponent component) {      
		super.setProperties(component);
		if (component != null) {
			component.getAttributes().put("plugins", _plugins);     
			component.getAttributes().put("accesskey", _accesskey);     
			component.getAttributes().put("cols", new Integer(_cols));     
			component.getAttributes().put("dir", _dir);     
			component.getAttributes().put("disabled",new Boolean(_disabled));     
			component.getAttributes().put("lang", _lang);     
			component.getAttributes().put("readonly", new Boolean(_readonly));     
			component.getAttributes().put("rows", new Integer(_rows));     
			component.getAttributes().put("style", _style);     
			component.getAttributes().put("styleClass", _styleClass);     
			component.getAttributes().put("tabindex", _tabindex);     
			component.getAttributes().put("title", _title);
		}
	}
	
	public String getPlugins() {
		return _plugins;
	}
	
	public void setPlugins(String plugins) {
		this._plugins = plugins;
	}
	
	public String getAccesskey() {
		return _accesskey;
	}
	
	public void setAccesskey(String accesskey) {
		this._accesskey = accesskey;
	}
	
	public int getCols() {
		return _cols;
	}
	
	public void setCols(int cols) {
		this._cols = cols;
	}
	
	public String getDir() {
		return _dir;
	}
	
	public void setDir(String dir) {
		this._dir = dir;
	}
	
	public boolean getDisabled() {
		return _disabled;
	}
	
	public void setDisabled(boolean disabled) {
		this._disabled = disabled;
	}
	
	public String getLang() {
		return _lang;
	}
	
	public void setlang(String lang) {
		this._lang = lang;
	}
	
	public boolean getReadonly() {
		return _readonly;
	}
	
	public void setReadonly(boolean readonly) {
		this._readonly = readonly;
	}
	
	public int getRows() {
		return _rows;
	}
	
	public void setRows(int rows) {
		this._rows = rows;
	}
	
	public String getStyle() {
		return _style;
	}
	
	public void setStyle(String style) {
		this._style = style;
	}
	
	public String getStyleClass() {
		return _styleClass;
	}
	
	public void setStyleClass(String styleClass) {
		_styleClass = styleClass;
	}
	
	public String getTabindex() {
		return _tabindex;
	}
	
	public void setTabindex(String tabindex) {
		this._tabindex = tabindex;
	}
	
	public String getTitle() {
		return _title;
	}
	
	public void setTitle(String title) {
		this._title = title;
	}
}
