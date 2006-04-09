/*
 * $Id: ListRenderer.java,v 1.3 2006/04/09 11:59:21 laddi Exp $
 * Created on 21.3.2006 in project com.idega.webface
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.renderkit;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import org.apache.myfaces.renderkit.html.HTML;
import org.apache.myfaces.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer;

import com.idega.webface.WFList;


/**
 * <p>
 * Default renderer for the WFList table component
 * </p>
 *  Last modified: $Date: 2006/04/09 11:59:21 $ by $Author: laddi $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.3 $
 */
public class ListRenderer extends HtmlTableRenderer{

	/**
	 * 
	 */
	public ListRenderer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.idega.webface.renderkit.BaseRenderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		super.encodeChildren(context, component);
	}


	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer#determineChildColSpan(javax.faces.component.UIComponent)
	 */
	protected int determineChildColSpan(UIComponent uiComponent) {
		// TODO Auto-generated method stub
		return super.determineChildColSpan(uiComponent);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer#encodeColumnChild(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIData, javax.faces.component.UIComponent, java.util.Iterator)
	 */
	protected void encodeColumnChild(FacesContext facesContext, ResponseWriter writer, UIData uiData, UIComponent component, Iterator columnStyleIterator) throws IOException {
		// TODO Auto-generated method stub
		super.encodeColumnChild(facesContext, writer, uiData, component, columnStyleIterator);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer#hasFacet(boolean, javax.faces.component.UIComponent)
	 */
	protected boolean hasFacet(boolean header, UIComponent uiComponent) {
		// TODO Auto-generated method stub
		return super.hasFacet(header, uiComponent);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer#renderColumnBody(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIData, javax.faces.component.UIComponent, java.util.Iterator)
	 */
	protected void renderColumnBody(FacesContext facesContext, ResponseWriter writer, UIData uiData, UIComponent component, Iterator columnStyleIterator) throws IOException {
		// TODO Auto-generated method stub
		super.renderColumnBody(facesContext, writer, uiData, component, columnStyleIterator);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer#renderColumnChildHeaderOrFooterRow(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIComponent, java.lang.String, boolean)
	 */
	protected void renderColumnChildHeaderOrFooterRow(FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent, String styleClass, boolean header) throws IOException {
		// TODO Auto-generated method stub
		super.renderColumnChildHeaderOrFooterRow(facesContext, writer, uiComponent, styleClass, header);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer#renderColumnFooterCell(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIComponent, javax.faces.component.UIComponent, java.lang.String, int)
	 */
	protected void renderColumnFooterCell(FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent, UIComponent facet, String footerStyleClass, int colspan) throws IOException {
		// TODO Auto-generated method stub
		super.renderColumnFooterCell(facesContext, writer, uiComponent, facet, footerStyleClass, colspan);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer#renderColumnHeaderCell(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIComponent, javax.faces.component.UIComponent, java.lang.String, int)
	 */
	protected void renderColumnHeaderCell(FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent, UIComponent facet, String headerStyleClass, int colspan) throws IOException {
		// TODO Auto-generated method stub
		super.renderColumnHeaderCell(facesContext, writer, uiComponent, facet, headerStyleClass, colspan);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer#renderHtmlColumnAttributes(javax.faces.context.ResponseWriter, javax.faces.component.UIComponent, java.lang.String)
	 */
	protected void renderHtmlColumnAttributes(ResponseWriter writer, UIComponent uiComponent, String prefix) throws IOException {
		// TODO Auto-generated method stub
		super.renderHtmlColumnAttributes(writer, uiComponent, prefix);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer#renderRowAttribute(javax.faces.context.ResponseWriter, java.lang.String, java.lang.Object)
	 */
	protected void renderRowAttribute(ResponseWriter writer, String htmlAttribute, Object value) throws IOException {
		// TODO Auto-generated method stub
		super.renderRowAttribute(writer, htmlAttribute, value);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer#renderRowStart(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIData, java.util.Iterator)
	 */
	protected void renderRowStart(FacesContext facesContext, ResponseWriter writer, UIData uiData, Iterator rowStyleClassIterator) throws IOException {
		// TODO Auto-generated method stub
		super.renderRowStart(facesContext, writer, uiData, rowStyleClassIterator);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer#renderRowStyle(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIData, java.util.Iterator)
	 */
	protected void renderRowStyle(FacesContext facesContext, ResponseWriter writer, UIData uiData, Iterator rowStyleIterator) throws IOException {
		// TODO Auto-generated method stub
		super.renderRowStyle(facesContext, writer, uiData, rowStyleIterator);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.HtmlTableRendererBase#encodeInnerHtml(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeInnerHtml(FacesContext facesContext, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		super.encodeInnerHtml(facesContext, component);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.HtmlTableRendererBase#renderRowEnd(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIData)
	 */
	protected void renderRowEnd(FacesContext facesContext, ResponseWriter writer, UIData uiData) throws IOException {
		// TODO Auto-generated method stub
		super.renderRowEnd(facesContext, writer, uiData);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.HtmlTableRendererBase#beforeTable(javax.faces.context.FacesContext, javax.faces.component.UIData)
	 */
	protected void beforeTable(FacesContext facesContext, UIData uiData) throws IOException {
		// TODO Auto-generated method stub
		super.beforeTable(facesContext, uiData);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.HtmlTableRendererBase#beforeRow(javax.faces.context.FacesContext, javax.faces.component.UIData)
	 */
	protected void beforeRow(FacesContext facesContext, UIData uiData) throws IOException {
		// TODO Auto-generated method stub
		super.beforeRow(facesContext, uiData);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.HtmlTableRendererBase#afterRow(javax.faces.context.FacesContext, javax.faces.component.UIData)
	 */
	protected void afterRow(FacesContext facesContext, UIData uiData) throws IOException {
		// TODO Auto-generated method stub
		super.afterRow(facesContext, uiData);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.HtmlTableRendererBase#afterTable(javax.faces.context.FacesContext, javax.faces.component.UIData)
	 */
	protected void afterTable(FacesContext facesContext, UIData uiData) throws IOException {
		// TODO Auto-generated method stub
		super.afterTable(facesContext, uiData);
	}

	/**
	 * <p>
	 *  This method is borrowed from the Myfaces implementation to allow thead to be settable with style class attribute
	 * </p>
	 * @see org.apache.myfaces.renderkit.html.HtmlTableRendererBase#renderFacet(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIComponent, boolean)
	 */
	protected void renderFacet(FacesContext facesContext, ResponseWriter writer, UIComponent component, boolean header) throws IOException {
		// TODO Auto-generated method stub
		//super.renderFacet(facesContext, writer, component, header);
        int colspan = 0;
        boolean hasColumnFacet = false;
        for (Iterator it = component.getChildren().iterator(); it.hasNext();)
        {
            UIComponent uiComponent = (UIComponent) it.next();
            if(uiComponent.isRendered())
            {
                  colspan += determineChildColSpan(uiComponent);
                  if (!hasColumnFacet)
                  {
                        hasColumnFacet = hasFacet(header, uiComponent);
              }
            }
        }

        UIComponent facet = header ? (UIComponent) component.getFacets().get(HEADER_FACET_NAME)
                : (UIComponent) component.getFacets().get(FOOTER_FACET_NAME);
        if (facet != null || hasColumnFacet)
        {
            // Header or Footer present
            String elemName = header ? HTML.THEAD_ELEM : HTML.TFOOT_ELEM;

            HtmlRendererUtils.writePrettyLineSeparator(facesContext);
            writer.startElement(elemName, component);
            if(component instanceof WFList){
            	WFList list = (WFList)component;
            	String headClass = list.getTableHeadStyleClass();
            	if(headClass!=null){
            		writer.writeAttribute("class",headClass,null);
            	}
            }
            if (header)
            {
                String headerStyleClass = getHeaderClass(component);
                if (facet != null) {
									renderTableHeaderRow(facesContext, writer, component, facet, headerStyleClass, colspan);
								}
                if (hasColumnFacet) {
									renderColumnHeaderRow(facesContext, writer, component, headerStyleClass);
								}
            }
            else
            {
                String footerStyleClass = getFooterClass(component);
                if (hasColumnFacet) {
									renderColumnFooterRow(facesContext, writer, component, footerStyleClass);
								}
                if (facet != null) {
									renderTableFooterRow(facesContext, writer, component, facet, footerStyleClass, colspan);
								}
            }
            writer.endElement(elemName);
        }
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.HtmlTableRendererBase#renderTableHeaderRow(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIComponent, javax.faces.component.UIComponent, java.lang.String, int)
	 */
	protected void renderTableHeaderRow(FacesContext facesContext, ResponseWriter writer, UIComponent component, UIComponent headerFacet, String headerStyleClass, int colspan) throws IOException {
		// TODO Auto-generated method stub
		super.renderTableHeaderRow(facesContext, writer, component, headerFacet, headerStyleClass, colspan);
        //renderTableHeaderOrFooterRow(facesContext, writer, component, headerFacet, headerStyleClass, HTML.TH_ELEM,colspan);
	}

	/*
    private void renderTableHeaderOrFooterRow(FacesContext facesContext, ResponseWriter writer, UIComponent component,
            UIComponent facet, String styleClass, String colElementName, int colspan) throws IOException
    {
        HtmlRendererUtils.writePrettyLineSeparator(facesContext);
        writer.startElement(HTML.TR_ELEM, component);
        writer.startElement(colElementName, component);
        if (colElementName.equals(HTML.TH_ELEM))
        {
            writer.writeAttribute(HTML.SCOPE_ATTR, HTML.SCOPE_COLGROUP_VALUE, null);
        }
        writer.writeAttribute(HTML.COLSPAN_ATTR, new Integer(colspan), null);
        if (styleClass != null)
        {
            writer.writeAttribute(HTML.CLASS_ATTR, styleClass, null);
        }
        if (facet != null)
        {
            RendererUtils.renderChild(facesContext, facet);
        }
        writer.endElement(colElementName);
        writer.endElement(HTML.TR_ELEM);
    }
    */
	
	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.HtmlTableRendererBase#renderTableFooterRow(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIComponent, javax.faces.component.UIComponent, java.lang.String, int)
	 */
	protected void renderTableFooterRow(FacesContext facesContext, ResponseWriter writer, UIComponent component, UIComponent footerFacet, String footerStyleClass, int colspan) throws IOException {
		// TODO Auto-generated method stub
		super.renderTableFooterRow(facesContext, writer, component, footerFacet, footerStyleClass, colspan);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.HtmlTableRendererBase#renderColumnHeaderRow(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIComponent, java.lang.String)
	 */
	protected void renderColumnHeaderRow(FacesContext facesContext, ResponseWriter writer, UIComponent component, String headerStyleClass) throws IOException {
		// TODO Auto-generated method stub
		super.renderColumnHeaderRow(facesContext, writer, component, headerStyleClass);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.HtmlTableRendererBase#renderColumnFooterRow(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIComponent, java.lang.String)
	 */
	protected void renderColumnFooterRow(FacesContext facesContext, ResponseWriter writer, UIComponent component, String footerStyleClass) throws IOException {
		// TODO Auto-generated method stub
		super.renderColumnFooterRow(facesContext, writer, component, footerStyleClass);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.HtmlTableRendererBase#renderColumnHeaderCell(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIColumn, java.lang.String, int)
	 */
	protected void renderColumnHeaderCell(FacesContext facesContext, ResponseWriter writer, UIColumn uiColumn, String headerStyleClass, int colspan) throws IOException {
		// TODO Auto-generated method stub
		super.renderColumnHeaderCell(facesContext, writer, uiColumn, headerStyleClass, colspan);
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.renderkit.html.HtmlTableRendererBase#renderColumnFooterCell(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIColumn, java.lang.String, int)
	 */
	protected void renderColumnFooterCell(FacesContext facesContext, ResponseWriter writer, UIColumn uiColumn, String footerStyleClass, int colspan) throws IOException {
		// TODO Auto-generated method stub
		super.renderColumnFooterCell(facesContext, writer, uiColumn, footerStyleClass, colspan);
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#convertClientId(javax.faces.context.FacesContext, java.lang.String)
	 */
	public String convertClientId(FacesContext context, String clientId) {
		// TODO Auto-generated method stub
		return super.convertClientId(context, clientId);
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#decode(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void decode(FacesContext context, UIComponent component) {
		// TODO Auto-generated method stub
		super.decode(context, component);
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		if(component instanceof WFList){
			WFList list = (WFList)component;
			if(list.isBodyScrollable()){
				ResponseWriter writer = context.getResponseWriter();
				writer.startElement("script",component);
				writer.writeAttribute("type","text/javascript",null);
				writer.write("window.onload = function() { addIEonScroll(); }");
				writer.endElement("script");
			}
		}
		super.encodeBegin(context, component);
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		super.encodeEnd(context, component);
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#getConvertedValue(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
		// TODO Auto-generated method stub
		return super.getConvertedValue(context, component, submittedValue);
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		// TODO Auto-generated method stub
		return super.getRendersChildren();
	}
}
