package com.idega.webface.htmlarea;

import java.io.IOException;
import java.util.ArrayList;

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

public class HTMLAreaRenderer extends Renderer {
	
	private final static String IW_BUNDLE_IDENTIFIER = "com.idega.webface";
	private IWBundle iwb;
	
	boolean fullHTMLPageSupport = false;
	private String inputName = null;
	private String rootFolder = null;
	
	private Renderer textareaRenderer = null;
	
	public HTMLAreaRenderer() {
		IWContext iwc = IWContext.getInstance();
		init(iwc);
	}
	
	private void init(IWContext iwc) {
		if (iwc != null) {
			iwb = iwc.getIWMainApplication().getBundle(IW_BUNDLE_IDENTIFIER);
			rootFolder = iwb.getResourcesVirtualPath() + "/htmlarea/";
		}
	}
	
	public Renderer getTextareaRenderer(FacesContext context) {
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
		if (textarea.getStyle() == null) {
			String styleString = "";
			if (textarea.getCols() <= 0) {
				styleString += "width:100%;";
			}
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

		inputName = component.getClientId(context);
		
		// Initializing variables
		StringBuffer variables = new StringBuffer();
		variables.append("\n\t_editor_url = \"").append(rootFolder).append("\"").append(";\n").append(
		"\t_editor_lang = \"en\";");
		
		
		// Initializing editor starts
		StringBuffer initEditorScript = new StringBuffer("\n");
		initEditorScript.append("\tvar editor = null;\n").append("\tfunction initEditor() {\n").append(
				"\t\t// create an editor for the \"" + inputName + "\" textbox\n").append(
						"\t\teditor = new HTMLArea('" + inputName + "');\n");
		
		// Creatioing the cssPluginString, used to define what "Syntax" is available
		StringBuffer cssPluginString = new StringBuffer();
		cssPluginString.append("CSS, {\n	    combos : [\n	      { label: \"Syntax:\",\n	                   // menu text       // CSS class\n")
		.append("			options: {     \"None\" : \"\",\n")
		.append("	                   \"Code\" : \"code\",\n")
		.append("	                   \"String\" : \"string\",\n")
		.append("	                   \"Variable name\" : \"variable-name\",\n")
		.append("	                   \"Type\" : \"type\",\n")
		.append("	                   \"Reference\" : \"reference\",\n")
		.append("	                   \"Preprocessor\" : \"preprocessor\",\n")
		.append("	                   \"Keyword\" : \"keyword\",\n")
		.append("	                   \"Function name\" : \"function-name\",\n")
		.append("	                   \"Html tag\" : \"html-tag\",\n")
		.append("	                   \"Html italic\" : \"html-helper-italic\",\n")
		.append("	                   \"Warning\" : \"warning\",\n")
		.append("	                   \"Html bold\" : \"html-helper-bold\"\n")
		.append("	                 },\n")
		// adding to the cssPluginString, used to define what "Info" is available
		.append("      context: \"pre\"\n	      },\n	      { label: \"Info:\",\n")
		.append("      options: {    \"None\"           : \"\",\n")
		.append("	                   \"Quote\"          : \"quote\",\n")
		.append("	                   \"Highlight\"          : \"highlight\",\n")
		.append("	                   \"Deprecated\"          : \"deprecated\"\n")
		.append("	                 }\n	      }\n	    ]\n	  }");
		
		
		ResponseWriter writer = context.getResponseWriter();
		
		// Registering plugins
		StringBuffer loadPlugins = new StringBuffer("\n");
		String[] plugins = getPlugins(component);
		for (int i = 0; i < plugins.length; i++) {
			if ("CSS".equals(plugins[i])) {
				addPlugin("CSS", cssPluginString.toString(), loadPlugins, initEditorScript);
				// stylesheet used by CSS plugin (and perhaps also DynamicCSS)
				initEditorScript.append("\teditor.config.pageStyle = \"@import url("+rootFolder+"examples/custom.css);\";\n");
			} else {
				addPlugin(plugins[i], plugins[i], loadPlugins, initEditorScript);
			}
		}
		
		//Finishing initialize editor script, all plugins must be registered at this point
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
		
		// Adding necessary scripts to html file (note: currently added to <body> should be moved to <head>)
		// This must be added in this order
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
	
	private String[] getPlugins(UIComponent component) {
		
		try {
			String plugins = ((HTMLArea) component).getPlugins();
			if (plugins != null) {
				ArrayList list = new ArrayList();
				
				int index = plugins.indexOf(",");
				while (index >= 0) {
					list.add(plugins.substring(0, index).trim());
					plugins = plugins.substring(index+1);
					index = plugins.indexOf(",");
				}
				
				list.add(plugins.trim());
				return (String[]) list.toArray(new String[]{});
			}
		} catch (ClassCastException c) {
			c.printStackTrace();
		}
		
		// Adding these as default if nothing else if found
		return new String[]{"TableOperations", "ContextMenu", "ListType", "CharacterMap", "DynamicCSS", "CSS"};
	}
	
	private void addPlugin(String pluginName, String registerString, StringBuffer loadPlugins, StringBuffer initEditorScript) {
		loadPlugins.append("\tHTMLArea.loadPlugin(\""+pluginName+"\");\n");
		initEditorScript.append("\t\teditor.registerPlugin("+registerString+");\n");
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
