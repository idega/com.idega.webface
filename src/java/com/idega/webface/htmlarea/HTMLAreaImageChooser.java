/*
 * $Id: HTMLAreaImageChooser.java,v 1.1 2005/03/09 09:45:43 gimmi Exp $
 * Created on 8.3.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.htmlarea;

import java.util.Iterator;
import java.util.Vector;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import com.idega.presentation.IWContext;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.IFrame;
import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;
import com.idega.webface.WFTabBar;
import com.idega.webface.WFTitlebar;
import com.idega.webface.WFUtil;



public class HTMLAreaImageChooser extends HTMLAreaLinkCreator {

	HTMLAreaImageType currentImageType;
	
	private final static String PARAMETER_LINK_TYPE = "f_ct";
	private final static String PARAMETER_CHOOSER = "pch";
	
	private static final String PARAMETER_URL = "f_url";
	private static final String PARAMETER_ALT = "f_alt";
	private static final String PARAMETER_ALIGNMENT = "f_align";
	private static final String PARAMETER_BORDER = "f_border";
	private static final String PARAMETER_HORIZONTAL_SPACING = "f_horiz";
	private static final String PARAMETER_VERTICAL_SPACING = "f_vert";
	private static final String PARAMETER_PREVIEW = "ipreview";
	
	protected void init(IWContext iwc) {
		bundle = iwc.getIWMainApplication().getBundle(IW_BUNDLE_IDENTIFIER);
		if (tabs == null) {
			tabs = new Vector();
		}
		tabs.add(new HTMLAreaExternalImageType());
		
		String selectedType = iwc.getParameter(PARAMETER_LINK_TYPE);
		String pc = iwc.getParameter(PARAMETER_CHOOSER);
		if (pc == null) {
			if (selectedType != null) {
				Iterator iter = getLinkTypes().iterator();
				while (iter.hasNext() && currentImageType == null) {
					HTMLAreaImageType t = (HTMLAreaImageType) iter.next();
					if (selectedType.equals(t.getLinkType())) {
						currentImageType = t;
					}
				}
			} else {
				currentImageType = new HTMLAreaExternalImageType();
			}
		} else {
			try {
				currentImageType = (HTMLAreaImageType) Class.forName(pc).newInstance();
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	protected WFTabBar getLinkTabBar() {
		WFTabBar bar = new WFTabBar();
		int col = 1;
		Iterator iter = getLinkTypes().iterator();
		while (iter.hasNext()) {
			HTMLAreaImageType t = (HTMLAreaImageType) iter.next();
			HtmlOutputText text = new HtmlOutputText();
			text.setValueBinding("value", t.getLinkTypeName(bundle));
			HtmlOutputLink link = bar.addLink(text, null, "HTMLAIC_"+col++, currentImageType.getLinkType().equals(t.getLinkType()));
			WFUtil.addParameter(link, PARAMETER_CHOOSER, t.getClass().getName());
		}
		return bar;
	}

	protected UIComponent getCreationComponent() {
		return currentImageType.getCreationComponent();
	}
	
	protected WFBlock getSubmitTable() {
		WFBlock block = new WFBlock();
		WFTitlebar header = new WFTitlebar();
		header.addTitleText(bundle.getLocalizedText("image_chooser"));
		block.setTitlebar(header);

		HtmlInputText url = new HtmlInputText();
		url.setId(PARAMETER_URL);
		
		HtmlInputText alt = new HtmlInputText();
		alt.setId(PARAMETER_ALT);
		
		DropdownMenu alignment = new DropdownMenu(PARAMETER_ALIGNMENT);
		alignment.setId(PARAMETER_ALIGNMENT);
		alignment.setID(PARAMETER_ALIGNMENT);
		alignment.addMenuElement("", "Not set");
		alignment.addMenuElement("left", "Left");
		alignment.addMenuElement("right", "Right");
		alignment.addMenuElement("texttop", "Texttop");
		alignment.addMenuElement("absmiddle", "Absmiddle");
		alignment.addMenuElement("baseline", "Baseline");
		alignment.addMenuElement("absbottom", "Absbottom");
		alignment.addMenuElement("bottom", "Bottom");
		alignment.addMenuElement("middle", "Middle");
		alignment.addMenuElement("top", "Top");
		alignment.setSelectedElement("baseline");
				
		HtmlInputText border = new HtmlInputText();
		border.setId(PARAMETER_BORDER);
		
		HtmlInputText horiz = new HtmlInputText();
		horiz.setId(PARAMETER_HORIZONTAL_SPACING);
		
		HtmlInputText vert = new HtmlInputText();
		vert.setId(PARAMETER_VERTICAL_SPACING);
		
		IFrame iframe = new IFrame();
		iframe.setId(PARAMETER_PREVIEW);
		iframe.setName(PARAMETER_PREVIEW);
		iframe.setSrc("");
		
		HtmlCommandButton saveButton = new HtmlCommandButton();
		bundle.getLocalizedUIComponent("save", saveButton);
		saveButton.setType("button");
		saveButton.setOnclick("onOK()");
		saveButton.setId("HTMLAIC_SB");
		
		WFContainer urlLine = new WFContainer();
		urlLine.setStyleClass("wf_imagechooser_line");
		urlLine.getChildren().add(bundle.getLocalizedText("url"));
		urlLine.getChildren().add(url);
		
		WFContainer altLine = new WFContainer();
		altLine.setStyleClass("wf_imagechooser_line");
		altLine.getChildren().add(bundle.getLocalizedText("alt"));
		altLine.getChildren().add(alt);
		
		WFContainer alignmentLine = new WFContainer();
		alignmentLine.setStyleClass("wf_imagechooser_line");
		alignmentLine.getChildren().add(bundle.getLocalizedText("alignment"));
		alignmentLine.getChildren().add(alignment);
		
		WFContainer borderLine = new WFContainer();
		borderLine.setStyleClass("wf_imagechooser_line");
		borderLine.getChildren().add(bundle.getLocalizedText("border"));
		borderLine.getChildren().add(border);
		
		WFContainer horizLine = new WFContainer();
		horizLine.setStyleClass("wf_imagechooser_line");
		horizLine.getChildren().add(bundle.getLocalizedText("horizontal"));
		horizLine.getChildren().add(horiz);
		
		WFContainer vertiLine = new WFContainer();
		vertiLine.setStyleClass("wf_imagechooser_line");
		vertiLine.getChildren().add(bundle.getLocalizedText("vertical"));
		vertiLine.getChildren().add(vert);
		
		WFContainer previewLine = new WFContainer();
		previewLine.setStyleClass("wf_imagechooser_preview");
		previewLine.getChildren().add(bundle.getLocalizedText("image_preview"));
//		previewLine.getChildren().add(iframe);

		WFContainer saveLine = new WFContainer();
		saveLine.setStyleClass("wf_imagechooser_line");
		saveLine.getChildren().add(saveButton);
		
		block.add(urlLine);
		block.add(altLine);
		block.add(alignmentLine);
		block.add(borderLine);
		block.add(horizLine);
		block.add(vertiLine);
		block.add(previewLine);
		block.add(saveLine);
		return block;
	}

	public void setExternalTabClass(String tab) {
		if (tab != null) {
			Vector v = new Vector();
			int index = tab.indexOf(",");
			try {
				while (index > -1) {
					String tmp = tab.substring(0, index);
					v.add((HTMLAreaImageType)Class.forName(tmp).newInstance());
					tab = tab.substring(index+1);
					index = tab.indexOf(",");
				}
				v.add((HTMLAreaImageType)Class.forName(tab).newInstance());
				if (tabs == null) {
					tabs = v;
				} else {
					tabs.addAll(v);
				}
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
