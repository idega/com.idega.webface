package com.idega.webface.htmlarea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import com.idega.idegaweb.IWBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.Page;

public class HTMLAreaRenderer extends Renderer {
	
	private final static String IW_BUNDLE_IDENTIFIER = "com.idega.webface";
	private String rootFolder = null;
	
	private Renderer textareaRenderer = null;
	private HashMap pluginLocation = new HashMap();
	
	public HTMLAreaRenderer() {
		IWContext iwc = IWContext.getInstance();
		init(iwc);
	}
	
	private void init(IWContext iwc) {
		if (iwc != null) {
			IWBundle iwb = iwc.getIWMainApplication().getBundle(IW_BUNDLE_IDENTIFIER);
			rootFolder = iwb.getResourcesVirtualPath() + "/htmlarea/";
		}
	}
	
	private Renderer getTextareaRenderer(FacesContext context) {
		if (textareaRenderer == null) {
			RenderKitFactory factory = (RenderKitFactory)FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
			RenderKit kit = factory.getRenderKit(context, RenderKitFactory.HTML_BASIC_RENDER_KIT);
			textareaRenderer = kit.getRenderer("javax.faces.Input", "javax.faces.Textarea");
		} 
		return textareaRenderer;
	}
	
	public void decode(FacesContext context, UIComponent component) {
		// Rendering useing default HtmlInputTextarea renderer
		getTextareaRenderer(context).decode(context, component);
	}
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		HtmlInputTextarea textarea = (HtmlInputTextarea) component;
		// Setting default values
		String areaStyle = textarea.getStyle();
		if (areaStyle == null) {
			String styleString = "";
			//if (textarea.getCols() <= 0) {
			//	styleString += "width:100%;";
			//}
			if (textarea.getRows() <= 0) {
				styleString += " heigth:400px";
			}
			if (!styleString.equals("")) {
				textarea.setStyle(styleString);
			}
		}
		
		// Rendering useing default HtmlInputTextarea renderer
		getTextareaRenderer(context).encodeBegin(context, component);
	}
	
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		// Rendering useing default HtmlInputTextarea renderer
		getTextareaRenderer(context).encodeChildren(context, component);
	}
	
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		// Rendering useing default HtmlInputTextarea renderer
		getTextareaRenderer(context).encodeEnd(context, component);
				

		
		// Checking if component has a parent Page
		boolean pageParent = false;
		UIComponent parent = null;
		
		//Disabled parentPage detection
		/*
		parent = component.getParent();
		if (parent instanceof Page) {
			pageParent = true;
		}
		while (parent != null && !pageParent) {
			parent = parent.getParent();
			if (parent instanceof Page) {
				pageParent = true;
			}
		}
		*/
		
		StringBuffer variables = getVariablesScript(); // Initializing variables 
		StringBuffer initEditorScript = getInitEditorScript(context, component); // Initializing editor starts
		StringBuffer loadPlugins = getRegisterPluginsScript(component, initEditorScript); // Registering plugins
		finalizeInitEditorScript(initEditorScript); //Finishing initialize editor script, all plugins must be registered at this point
		
		
		if (pageParent) {
			// This must be added in this order
			((Page) parent).addJavaScriptBeforeJavaScriptURLs("htmlAreaInitialVariables", variables.toString());
			((Page) parent).addJavascriptURL(rootFolder + "htmlarea.js");
			((Page) parent).addJavaScriptAfterJavaScriptURLs("htmlAreaLoadPlugins", loadPlugins.toString());
			((Page) parent).addJavaScriptAfterJavaScriptURLs("htmlAreainitEditorMethod", initEditorScript.toString());
			((Page) parent).addStyleSheetURL(rootFolder+"htmlarea.css");
			((Page) parent).setOnLoad("HTMLArea.init()");
		} else {
			// Adding necessary scripts to html file (note: currently added to <body> should be moved to <head>)
			// This must be added in this order
			ResponseWriter writer = context.getResponseWriter();
			addJavascript(writer, variables.toString());
			addJavascriptUrl(writer, "htmlarea.js");
			addJavascript(writer, loadPlugins.toString());
			addJavascript(writer, initEditorScript.toString());
			addStyleSheet(writer, "htmlarea.css");
		
			// Adding HTMLArea.init() to html file (note: currently added to <body> should be moved to <body onload>)
			writer.write("\n");
			writer.startElement("SCRIPT", null);
			writer.writeAttribute("type", "text/javascript", null);
			writer.writeText("HTMLArea.init();", "value");
			writer.endElement("SCRIPT");
		}
	}
	
	private void finalizeInitEditorScript(StringBuffer initEditorScript) {
		initEditorScript.append("\t\teditor.generate();\n").append("\t}\n");
		initEditorScript.append("\n\tHTMLArea.onload = initEditor;");
		initEditorScript.append("\n\tfunction insertHTML() {\n");
		initEditorScript.append("\t\tvar html = prompt(\"Enter some HTML code here\");\n");
		initEditorScript.append("\t\tif (html) {\n");
		initEditorScript.append("\t\t\teditor.insertHTML(html);\n");
		initEditorScript.append("\t\t}\n");
		initEditorScript.append("\t}\n");
		
		initEditorScript.append("\n\tfunction highlight() {\n");
		initEditorScript.append("\t\teditor.surroundHTML('<span style=\"background-color: yellow\">', '</span>');\n");
		initEditorScript.append("\t}\n");
	}

	private StringBuffer getRegisterPluginsScript(UIComponent component, StringBuffer initEditorScript) {
		StringBuffer loadPlugins = new StringBuffer("\n");
		String[] plugins = getPlugins(component);
		String location;
		for (int i = 0; i < plugins.length; i++) {
			location = (String) this.pluginLocation.get(plugins[i]);
			if (location == null) {
				location = "2";
			}
			if ("CSS".equals(plugins[i])) {
				addPlugin("CSS", getCSSPluginString().toString(), loadPlugins, initEditorScript, location);
				// stylesheet used by CSS plugin (and perhaps also DynamicCSS)
				initEditorScript.append("\teditor.config.pageStyle = \"@import url("+rootFolder+"examples/custom.css);\";\n");
			} else {
				addPlugin(plugins[i], plugins[i], loadPlugins, initEditorScript, location);
			}
		}
		return loadPlugins;
	}

	private StringBuffer getInitEditorScript(FacesContext context, UIComponent component) {
		HTMLArea htmlArea = null;
		try {
			htmlArea = (HTMLArea) component;
		} catch (ClassCastException e) {
			System.out.println("[HTMLAreaRenderer] component not instance of HTMLArea");
		}

		String inputName = component.getClientId(context);
		
		StringBuffer initEditorScript = new StringBuffer("\n");
		initEditorScript.append("\tvar editor = null;\n").append("\tfunction initEditor() {\n").append(
				"\t\t// create an editor for the \"" + inputName + "\" textbox\n").append(
						"\t\teditor = new HTMLArea('" + inputName + "');\n");
		
		boolean allowFontSelection = (htmlArea != null && htmlArea.getAllowFontSelection());
		String space = "\"space\"";
		String separator = "\"separator\"";
		String justify = "\"justifyleft\",\"justifycenter\",\"justifyright\",\"justifyfull\"";
		String lists = "\"lefttoright\",\"righttoleft\"," +
			separator+",\"orderedlist\",\"unorderedlist\"";
		

		// This must happen before the plugins are loaded, otherwize this overrides the plugins
		initEditorScript.append("\t\teditor.config.toolbar = [ " +
				"[");
					if (allowFontSelection) { 
						initEditorScript.append("\"fontname\","+space+",\"fontsize\","+space+",\"formatblock\","+space+"," +
						"\"bold\",\"italic\",\"underline\",\"strikethrough\","+
						separator+",\"subscript\",\"superscript\","+separator+",");
					} else {
						initEditorScript.append(lists+",");
					}
					initEditorScript.append("\"copy\",\"cut\",\"paste\","+
					space+",\"undo\",\"redo\","+
					space+",\"removeformat\",\"killword\"" +
				"], " +
				
				"[");
					if (allowFontSelection) { 
						initEditorScript.append(justify+","+separator+","+lists+",");
					} else {
						initEditorScript.append(justify+",");
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
		return initEditorScript;
	}

	private StringBuffer getCSSPluginString() {
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
	}

	private StringBuffer getVariablesScript() {
		StringBuffer variables = new StringBuffer();
		variables.append("\n\t_editor_url = \"").append(rootFolder).append("\"").append(";\n").append(
		"\t_editor_lang = \"en\";");
		return variables;
	}

	private String[] getPlugins(UIComponent component) {
		try {
			String plugins = ((HTMLArea) component).getPlugins();
			if (plugins != null) {
				ArrayList list = new ArrayList();
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
						System.out.print(", ");
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
				return (String[]) list.toArray(new String[]{});
			}
		} catch (ClassCastException c) {
			c.printStackTrace();
		}
		
		// Adding these as default if nothing else if found
		//return new String[]{"TableOperations", "ContextMenu", "ListType", "CharacterMap", "DynamicCSS", "CSS"};

		return new String[]{"TableOperations", "ContextMenu"};
		
	}
	
	private void addPlugin(String pluginName, String registerString, StringBuffer loadPlugins, StringBuffer initEditorScript, String toolbarNumber) {
		loadPlugins.append("\tHTMLArea.loadPlugin(\""+pluginName+"\");\n");
		initEditorScript.append("\t\teditor.registerPlugin("+registerString+", "+toolbarNumber+");\n");
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
		writer.writeAttribute("src", rootFolder+url, null);
		writer.writeText("", "value");
		writer.endElement("script");
	}
	
	private void addStyleSheet(ResponseWriter writer, String stylesheetName) throws IOException {
		writer.write("\n");
		writer.startElement("link", null);
		writer.writeAttribute("REL", "StyleSheet", null);
		writer.writeAttribute("HREF", rootFolder+stylesheetName, null);
		writer.writeAttribute("TYPE", "text/css", null);
	}
	
}
