package com.idega.webface;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.idega.presentation.IWBaseComponent;

public class WFDivision extends IWBaseComponent {
	
	public static final String COMPONENT_TYPE = "Division";
	
	public String id;
	public String styleClass;
	public String onclick;
	public String style;
	
	public boolean getRendersChildren() {
		return true;
	}
	
	public Object saveState(FacesContext context) {
		Object values[] = new Object[5];
		values[0] = super.saveState(context);
		values[1] = id;
		values[2] = styleClass;
		values[3] = onclick;
		values[4] = style;
		return values;
	}
	
	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		id = (String) values[1];
		styleClass = (String) values[2];
		onclick = (String) values[3];
		style = (String) values[4];
	}
	
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("DIV", this);
		if(id != null && !id.equals("")) {
			writer.writeAttribute("id", id, null);
		}
		if(styleClass != null && !styleClass.equals("")) {
			writer.writeAttribute("class", styleClass, null);
		}
		if(onclick != null && !onclick.equals("")) {
			writer.writeAttribute("onClick", onclick, null);
		}
		if(style != null && !style.equals("")) {
			writer.writeAttribute("style", style, null);
		}
	}
	
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("DIV");
	}
	
	public void encodeChildren(FacesContext context) throws IOException {
		Iterator children = getChildren().iterator();
		while(children.hasNext()) {
			UIComponent component = (UIComponent) children.next();
			if(component != null) {
				component.encodeBegin(context);
				component.encodeChildren(context);
				component.encodeEnd(context);
			}
		}
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
	public void add(UIComponent component) {
		try	{
			super.getChildren().add(component);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}