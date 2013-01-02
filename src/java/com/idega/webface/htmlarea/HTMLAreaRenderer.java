package com.idega.webface.htmlarea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import com.idega.core.file.util.MimeTypeUtil;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWMainApplicationSettings;
import com.idega.idegaweb.UnavailableIWContext;
import com.idega.presentation.IWContext;
import com.idega.util.CoreConstants;
import com.idega.webface.business.WebfaceConstants;

public class HTMLAreaRenderer extends Renderer {
	
	private final static String IW_BUNDLE_IDENTIFIER = "com.idega.webface";
	private String rootFolder = null;
	
	private static final String XINHA_FOLDER = "/xinha/";
	
	private String htmlareaFolder = XINHA_FOLDER;
	
	private Renderer textareaRenderer = null;
	private Map<String, String> pluginLocation = new HashMap<String, String>();
	
	private boolean addExtraStyle = false;
	
	public HTMLAreaRenderer() {
		try {
			IWContext iwc = IWContext.getInstance();
			init(iwc);
		} catch (UnavailableIWContext e) {
			//Annoying output
			//e.printStackTrace();
			init(null);
		}
	}
	
	public HTMLAreaRenderer(boolean addExtraStyle) {
		this();
		this.addExtraStyle = addExtraStyle;
	}
	
	private void init(IWContext iwc) {
		if (iwc != null) {
			IWBundle iwb = iwc.getIWMainApplication().getBundle(IW_BUNDLE_IDENTIFIER);
			this.rootFolder = iwb.getResourcesVirtualPath() + this.htmlareaFolder;
		}
	}
	
	private Renderer getTextareaRenderer(FacesContext context) {
		if (this.textareaRenderer == null) {
			RenderKitFactory factory = (RenderKitFactory)FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
			RenderKit kit = factory.getRenderKit(context, RenderKitFactory.HTML_BASIC_RENDER_KIT);
			this.textareaRenderer = kit.getRenderer("javax.faces.Input", "javax.faces.Textarea");
		} 
		return this.textareaRenderer;
	}
	
	@Override
	public void decode(FacesContext context, UIComponent component) {
		// Rendering useing default HtmlInputTextarea renderer
		getTextareaRenderer(context).decode(context, component);
	}
	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		HtmlInputTextarea textarea = (HtmlInputTextarea) component;
		// Setting default values
		String areaStyle = textarea.getStyle();
		if (areaStyle == null) {
			String styleString = "";
			if (textarea.getCols() <= 0) {
				//styleString += "width:100%;";
				styleString += "height:500px;";
			}
			if (textarea.getRows() <= 0) {
				styleString += "height:400px;";
			}
			if (!styleString.equals("")) {
				textarea.setStyle(styleString);
			}
		}
		
		// Rendering useing default HtmlInputTextarea renderer
		getTextareaRenderer(context).encodeBegin(context, component);
	}
	
	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		// Rendering useing default HtmlInputTextarea renderer
		getTextareaRenderer(context).encodeChildren(context, component);
	}
	
	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		// Rendering useing default HtmlInputTextarea renderer
		getTextareaRenderer(context).encodeEnd(context, component);
				
		StringBuffer variables = getVariablesScript(); // Initializing variables 
		StringBuffer initEditorScript = getInitEditorScript(context, component); // Initializing editor starts
		
		IWContext iwc = IWContext.getIWContext(context);
		IWBundle webfaceBundle = iwc.getIWMainApplication().getBundle(IW_BUNDLE_IDENTIFIER);
		IWBundle workspaceBundle = iwc.getIWMainApplication().getBundle(CoreConstants.WORKSPACE_BUNDLE_IDENTIFIER);
		String workspacecss = workspaceBundle.getVirtualPathWithFileNameString("style/workspace.css");
		String webfacecss = webfaceBundle.getVirtualPathWithFileNameString("style/webfacestyle.css");

		// Adding necessary scripts to html file (note: currently added to <body> should be moved to <head>)
		// This must be added in this order
		ResponseWriter writer = context.getResponseWriter();
		addJavascript(writer, variables.toString());
		addJavascriptUrl(writer, WebfaceConstants.XINHA_CORE);
		addJavascript(writer, initEditorScript.toString());
		if (addExtraStyle) {
			addStyleSheet(writer, workspacecss);
			addStyleSheet(writer, webfacecss);
		}
	}
	
	private StringBuffer getInitEditorScript(FacesContext context, UIComponent component) {
		HTMLArea htmlArea = null;
		try {
			htmlArea = (HTMLArea) component;
		} catch (ClassCastException e) {
			Logger.getLogger(getClass().getName()).warning("[HTMLAreaRenderer] component not instance of HTMLArea");
		}

		String inputName = component.getClientId(context);
		
		StringBuffer initEditorScript = new StringBuffer("\n");
		initEditorScript.append("\tvar xinha_editors = xinha_editors ? xinha_editors : [ ];\n");
		initEditorScript.append("\txinha_editors.push('" + inputName + "');\n\n");
		initEditorScript.append("\tvar xinha_init = xinha_init ? xinha_init : function() {\n");
	
		initEditorScript.append("\t\tvar xinha_plugins = [ ");
		boolean containsStylist = false;
		String[] plugins = getPlugins(component);
		for (int i = 0; i < plugins.length; i++) {
			String plugin = plugins[i];
			if (i != 0) {
				initEditorScript.append(", ");
			}
			initEditorScript.append("'" + plugin + "'");
			if (plugin.equals("Stylist")) {
				containsStylist = true;
			}
		}
		initEditorScript.append(" ];\n");
		initEditorScript.append("\t\tif (!Xinha.loadPlugins(xinha_plugins, xinha_init)) return;\n\n");
		
		initEditorScript.append("\t\txinha_config = new Xinha.Config();\n");

		boolean allowFontSelection = (htmlArea != null && htmlArea.getAllowFontSelection());
		String space = "\"space\"";
		String separator = "\"separator\"";
		String justify = "\"justifyleft\",\"justifycenter\",\"justifyright\",\"justifyfull\"";
		String lists = "\"lefttoright\",\"righttoleft\"," +	separator+",\"orderedlist\",\"unorderedlist\"";

		// This must happen before the plugins are loaded, otherwize this overrides the plugins
		initEditorScript.append("\t\txinha_config.toolbar = [ " +
				"[");
					if (allowFontSelection) { 
						initEditorScript.append("\"fontname\","+space+",\"fontsize\","+space+",\"formatblock\","+space+"," +
						"\"bold\",\"italic\",\"underline\",\"strikethrough\","+
						separator+",\"subscript\",\"superscript\","+separator+",");
					} else {
						initEditorScript.append("\"bold\",\"italic\",\"underline\",\"strikethrough\","+
								separator+",\"subscript\",\"superscript\","+separator+",");
					}
					initEditorScript.append("\"copy\",\"cut\",\"paste\","+
					space+",\"undo\",\"redo\","+
					space+",\"removeformat\",\"killword\"" +
				"], " +
				
				"[");
					if (allowFontSelection) { 
						initEditorScript.append(justify+","+separator+","+lists+",");
					} else {
						initEditorScript.append(justify+","+lists+",");
					}
					initEditorScript.append("\"outdent\",\"indent\",");
					if (allowFontSelection) { 
						initEditorScript.append(separator+",\"forecolor\", \"hilitecolor\", ");
					}
					initEditorScript.append(separator+",\"inserthorizontalrule\", \"createlink\", \"insertimage\", \"inserttable\", \"htmlmode\", "+
					separator+",\"popupeditor\"," +
					separator+", \"showhelp\", \"about\"" +
				"]" +
			"];\n");
					
		if (containsStylist) {
			IWMainApplicationSettings settings = IWContext.getIWContext(context).getApplicationSettings();
			String cssPath = settings.getProperty("SITE_EDITOR_STYLESHEET_URI", "/content/files/public/style/editorstyles.css");
			if (settings.getBoolean("add_article_editor_css", Boolean.FALSE))
				initEditorScript.append("\t\txinha_config.stylistLoadStylesheet('"+cssPath+"');\n");
		}
		
		initEditorScript.append("\n");
		initEditorScript.append("\t\txinha_editors = Xinha.makeEditors(xinha_editors, xinha_config, xinha_plugins);\n");
		initEditorScript.append("\t\tXinha.startEditors(xinha_editors);\n");

		initEditorScript.append("\t}\n");
		initEditorScript.append("\n\twindow.onload = xinha_init;\n");

		return initEditorScript;
	}

	/*private StringBuffer getCSSPluginString() {
		// Creatioing the cssPluginString, used to define what "Syntax" is available
		StringBuffer cssPluginString = new StringBuffer();
		cssPluginString.append("CSS, ")
		.append("\n\t\t{ combos : [\n\t\t\t{ label: \"Syntax:\",\n\t\t\t\t// menu text       // CSS class\n")
		.append("\t\t\t\toptions: {\t\"None\" : \"\",\n")
		.append("\t\t\t\t\t\t\"Code\" : \"code\",\n")
		.append("\t\t\t\t\t\t\"String\" : \"string\",\n")
		.append("\t\t\t\t\t\t\"Variable name\" : \"variable-name\",\n")
		.append("\t\t\t\t\t\t\"Type\" : \"type\",\n")
		.append("\t\t\t\t\t\t\"Reference\" : \"reference\",\n")
		.append("\t\t\t\t\t\t\"Preprocessor\" : \"preprocessor\",\n")
		.append("\t\t\t\t\t\t\"Keyword\" : \"keyword\",\n")
		.append("\t\t\t\t\t\t\"Function name\" : \"function-name\",\n")
		.append("\t\t\t\t\t\t\"Html tag\" : \"html-tag\",\n")
		.append("\t\t\t\t\t\t\"Html italic\" : \"html-helper-italic\",\n")
		.append("\t\t\t\t\t\t\"Warning\" : \"warning\",\n")
		.append("\t\t\t\t\t\t\"Html bold\" : \"html-helper-bold\"\n")
		.append("\t\t\t\t},")
		// adding to the cssPluginString, used to define what "Info" is available
		.append(" context: \"pre\" },\n")
		.append("\t\t\t{ label: \"Info:\",\n")
		.append("\t\t\t\toptions: {\t\"None\"           : \"\",\n")
		.append("\t\t\t\t\t\t\"Quote\"          : \"quote\",\n")
		.append("\t\t\t\t\t\t\"Highlight\"      : \"highlight\",\n")
		.append("\t\t\t\t\t\t\"Deprecated\"     : \"deprecated\"\n")
		.append("\t\t\t\t}\n\t\t\t}\n\t\t]}");
		return cssPluginString;
	}*/

	private StringBuffer getVariablesScript() {
		StringBuffer variables = new StringBuffer();
		variables.append("\n\t_editor_url = \"").append(this.rootFolder).append("\"").append(";\n").append(
		"\t_editor_lang = \"en\";");
		return variables;
	}

	private String[] getPlugins(UIComponent component) {
		try {
			String plugins = ((HTMLArea) component).getPlugins();
			if (plugins != null) {
				List<String> list = new ArrayList<String>();
				String plugin;
				int index = plugins.indexOf(",");
				while (index >= 0) {
					plugin = plugins.substring(0, index).trim();
					int locationIndex = plugin.indexOf("(");
					if (locationIndex >= 0) {
						String location = plugin.substring(locationIndex+1, plugin.indexOf(")"));
						location = Integer.toString(Integer.parseInt(location.trim())-1);
						plugin = plugin.substring(0, locationIndex).trim();
						this.pluginLocation.put(plugin, location);
					}
					
					list.add(plugin);
					plugins = plugins.substring(index+1);
					index = plugins.indexOf(",");
					if (index > -1) {
						Logger.getLogger(getClass().getName()).info(", ");
					}
				}
				
				plugin = plugins.trim();
				int locationIndex = plugin.indexOf("(");
				if (locationIndex >= 0) {
					String location = plugin.substring(locationIndex+1, plugin.indexOf(")"));
					location = Integer.toString(Integer.parseInt(location.trim())-1);
					plugin = plugin.substring(0, locationIndex).trim();
					this.pluginLocation.put(plugin, location);
				}
				
				list.add(plugin.trim());
				return list.toArray(new String[list.size()]);
			}
		} catch (ClassCastException c) {
			c.printStackTrace();
		}
		
		// Adding these as default if nothing else if found
		//return new String[]{"TableOperations", "ContextMenu", "ListType", "CharacterMap", "DynamicCSS", "CSS"};

		//Returning no plugins by default:
		return new String[0];
		
	}
	
	private void addJavascript(ResponseWriter writer, String script) throws IOException {
		writer.write("\n");
		writer.startElement("script", null);
		writer.writeAttribute("type", "text/javascript", null);
		writer.writeText(script, "value");
		writer.endElement("script");
	}
	
	private void addJavascriptUrl(ResponseWriter writer, String url) throws IOException {
		writer.write("\n");
		writer.startElement("script", null);
		writer.writeAttribute("type", "text/javascript", null);
		writer.writeAttribute("src", this.rootFolder+url, null);
		writer.writeText("", "value");
		writer.endElement("script");
	}
	
	private void addStyleSheet(ResponseWriter writer, String stylesheetName) throws IOException {
		writer.write("\n");
		writer.startElement("link", null);
		writer.writeAttribute("rel", "stylesheet", null);
		writer.writeAttribute("href", stylesheetName, null);
		writer.writeAttribute("media", "screen", null);
		writer.writeAttribute("type", MimeTypeUtil.MIME_TYPE_CSS, null);
	}
	
}
