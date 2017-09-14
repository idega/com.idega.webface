/*
 * $Id: HTMLAreaImageChooser.java,v 1.10 2007/08/20 14:43:02 valdas Exp $
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
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;

import com.idega.presentation.IWContext;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.FieldSet;
import com.idega.presentation.ui.IFrame;
import com.idega.repository.data.RefactorClassRegistry;
import com.idega.util.CoreConstants;
import com.idega.util.StringHandler;
import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;
import com.idega.webface.WFTabBar;
import com.idega.webface.WFTitlebar;
import com.idega.webface.WFUtil;



public class HTMLAreaImageChooser extends HTMLAreaLinkCreator {

	HTMLAreaImageType currentImageType;
	
	private final static String PARAMETER_LINK_TYPE = "f_ct";
	private final static String PARAMETER_CHOOSER = "pch";
	
	//This parameter is referenced as an id in HTMLAreaImageChooser.js:
	private static final String PARAMETER_URL = "f_url";//HTMLAreaLinkCreator.PARAMETER_HREF;
	private static final String PARAMETER_ALT = "f_alt";
	private static final String PARAMETER_ALIGNMENT = "f_align";
	private static final String PARAMETER_BORDER = "f_border";
	private static final String PARAMETER_HORIZONTAL_SPACING = "f_horiz";
	private static final String PARAMETER_VERTICAL_SPACING = "f_vert";
	private static final String PARAMETER_PREVIEW = "ipreview";
	private static final String LINK_TYPE_DOCUMENT = "document";
	
	@Override
	protected void init(IWContext iwc) {
		this.bundle = iwc.getIWMainApplication().getBundle(IW_BUNDLE_IDENTIFIER);
		if (this.tabs == null) {
			this.tabs = new Vector();
		}
		this.tabs.add(new HTMLAreaExternalImageType());

		String selectedType = iwc.getParameter(PARAMETER_LINK_TYPE);
		String pc = iwc.getParameter(PARAMETER_CHOOSER);
		if (pc == null) {
			if (selectedType != null) {
				Iterator iter = getLinkTypes().iterator();
				while (iter.hasNext() && this.currentImageType == null) {
					HTMLAreaImageType t = (HTMLAreaImageType) iter.next();
					if (selectedType.equals(t.getLinkType())) {
						this.currentImageType = t;
					}
				}
			} else {
				
				
				//currentImageType = new HTMLAreaExternalImageType();
				try {
					pc="com.idega.content.presentation.HTMLAreaDocumentImageChooser";
					this.currentImageType = (HTMLAreaImageType) RefactorClassRegistry.forName(pc).newInstance();
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
		} else {
			try {
				this.currentImageType = (HTMLAreaImageType) RefactorClassRegistry.forName(pc).newInstance();
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

	@Override
	protected WFTabBar getLinkTabBar() {
		WFTabBar bar = new WFTabBar();
		//This should reference WorkspaceBar.MAIN_NAVIGATION_STYLE_CLASS, but this is not typesafe (dependency on workspace is missing)
		bar.setStyleClass("ws_mainnavigation");
		int col = 1;
		Iterator iter = getLinkTypes().iterator();
		while (iter.hasNext()) {
			HTMLAreaImageType t = (HTMLAreaImageType) iter.next();
			HtmlOutputText text = new HtmlOutputText();
			text.setValueExpression("value", t.getLinkTypeName(this.bundle));
			HtmlOutputLink link = bar.addLink(text, null, "HTMLAIC_"+col++, this.currentImageType.getLinkType().equals(t.getLinkType()));
			WFUtil.addParameter(link, PARAMETER_CHOOSER, t.getClass().getName());
		}
		return bar;
	}

	@Override
	protected UIComponent getCreationComponent() {
		String surl = IWContext.getInstance().getParameter(PARAMETER_URL);
		if (surl != null && surl.lastIndexOf("/") > -1) {
			surl = surl.substring(0, surl.lastIndexOf("/"));
		}
		if (surl != null && surl.indexOf("//") > -1) {
			surl = surl.substring(surl.indexOf("//")+2);
			surl = surl.substring(surl.indexOf("/"));
		}
		return this.currentImageType.getCreationComponent(surl);
	}
	
	@Override
	protected WFBlock getSubmitTable(IWContext iwc) {
		WFBlock mainblock = new WFBlock();
		WFTitlebar header = new WFTitlebar();
		header.addTitleText(this.bundle.getLocalizedText("image_chooser"));
		mainblock.setTitlebar(header);

		WFContainer block = new WFContainer();
		block.setStyleClass("wf_imagechooser");
		mainblock.add(block);
		
		String surl = IWContext.getInstance().getParameter(PARAMETER_URL);
		String salt = IWContext.getInstance().getParameter(PARAMETER_ALT);
		String sali = IWContext.getInstance().getParameter(PARAMETER_ALIGNMENT);
		String sbor = IWContext.getInstance().getParameter(PARAMETER_BORDER);
		String shor = IWContext.getInstance().getParameter(PARAMETER_HORIZONTAL_SPACING);
		String sver = IWContext.getInstance().getParameter(PARAMETER_VERTICAL_SPACING);
		
		
		HtmlInputText url = new HtmlInputText();
		url.setId(PARAMETER_URL);
		if (surl != null) {
			url.setValue(StringHandler.removeAbsoluteReference(iwc.getServerName(), surl));
		}

		HtmlInputText alt = new HtmlInputText();
		alt.setId(PARAMETER_ALT);
		if (salt != null) {
			alt.setValue(salt);
		}
		
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
		if (sali != null) {
			alignment.setSelectedElement(sali);
		} else {
			alignment.setSelectedElement("baseline");
		}
				
		HtmlInputText border = new HtmlInputText();
		border.setId(PARAMETER_BORDER);
		if (sbor != null) {
			border.setValue(sbor);
		}
		
		HtmlInputText horiz = new HtmlInputText();
		horiz.setId(PARAMETER_HORIZONTAL_SPACING);
		if (shor != null && !"-1".equals(shor)) {
			horiz.setValue(shor);
		}
		
		HtmlInputText vert = new HtmlInputText();
		vert.setId(PARAMETER_VERTICAL_SPACING);
		if (sver != null && !"-1".equals(sver)) {
			vert.setValue(sver);
		}
		
		IFrame iframe = new IFrame();
		iframe.setId(PARAMETER_PREVIEW);
		iframe.setName(PARAMETER_PREVIEW);
		if (surl == null) {
			iframe.setSrc("");
		} else {
			iframe.setSrc(surl);
		}
		iframe.addLanguageParameter(false);
		
		HtmlCommandButton saveButton = new HtmlCommandButton();
		this.bundle.getLocalizedUIComponent("save", saveButton);
		saveButton.setType("button");
		saveButton.setOnclick("onOK()");
		saveButton.setId("HTMLAIC_SB");

		String linkType = CoreConstants.EMPTY;
		if (currentImageType != null) {
			linkType = currentImageType.getLinkType();
		}
		
		HtmlCommandButton previewButton = new HtmlCommandButton();
		if (!linkType.equals(LINK_TYPE_DOCUMENT)) {
			this.bundle.getLocalizedUIComponent("preview", previewButton);
			previewButton.setType("button");
			previewButton.setOnclick("onPreview()");
			previewButton.setId("HTMLAIC_PB");
			previewButton.setStyleClass("wf_imagechooser_preview_button");
		}
		
		WFContainer urlLine = new WFContainer();
		urlLine.setStyleClass("wf_imagechooser_line_long");
		urlLine.getChildren().add(getLabel("url", PARAMETER_URL));
		urlLine.getChildren().add(url);
		
		if (!linkType.equals(LINK_TYPE_DOCUMENT)) {
			urlLine.getChildren().add(previewButton);
		}
		
		WFContainer altLine = new WFContainer();
		altLine.setStyleClass("wf_imagechooser_line_long");
		altLine.getChildren().add(getLabel("alt", PARAMETER_ALT));
		altLine.getChildren().add(alt);
		
		WFContainer alignmentLine = new WFContainer();
		alignmentLine.setStyleClass("wf_imagechooser_line_short");
		alignmentLine.getChildren().add(getLabel("alignment", PARAMETER_ALIGNMENT));
		alignmentLine.getChildren().add(alignment);
		
		WFContainer borderLine = new WFContainer();
		borderLine.setStyleClass("wf_imagechooser_line_short");
		borderLine.getChildren().add(getLabel("border", PARAMETER_BORDER));
		borderLine.getChildren().add(border);

		FieldSet layoutBox = new FieldSet(this.bundle.getLocalizedString("layout"));
		layoutBox.setStyleClass("wf_imagechooser_left_box");
//		layoutBox.add(alignmentLine);
//		layoutBox.add(borderLine);
		
		WFContainer horizLine = new WFContainer();
		horizLine.setStyleClass("wf_imagechooser_line_short");
		horizLine.getChildren().add(getLabel("horizontal", PARAMETER_HORIZONTAL_SPACING));
		horizLine.getChildren().add(horiz);
		
		WFContainer vertiLine = new WFContainer();
		vertiLine.setStyleClass("wf_imagechooser_line_short");
		vertiLine.getChildren().add(getLabel("vertical", PARAMETER_VERTICAL_SPACING));
		vertiLine.getChildren().add(vert);
		
		FieldSet spacingBox = new FieldSet(this.bundle.getLocalizedString("spacing"));
		spacingBox.setStyleClass("wf_imagechooser_right_box");
//		spacingBox.add(horizLine);
//		spacingBox.add(vertiLine);

		WFContainer previewLine = new WFContainer();
		previewLine.setStyleClass("wf_imagechooser_preview");
		previewLine.getChildren().add(getLabel("image_preview", PARAMETER_PREVIEW));
		previewLine.getChildren().add(iframe);

		WFContainer saveLine = new WFContainer();
		saveLine.setStyleClass("wf_imagechooser_line");
		saveLine.getChildren().add(saveButton);
		
		FieldSet mainFieldSet = new FieldSet(this.bundle.getLocalizedString("image_chooser"));
		mainFieldSet.setStyleClass("wf_imagechooser_fieldset");
		mainFieldSet.getChildren().add(urlLine);
		mainFieldSet.getChildren().add(altLine);
//		mainFieldSet.getChildren().add(alignmentLine);
//		mainFieldSet.getChildren().add(borderLine);
//		mainFieldSet.getChildren().add(horizLine);
//		mainFieldSet.getChildren().add(vertiLine);
		mainFieldSet.getChildren().add(layoutBox);
		mainFieldSet.getChildren().add(spacingBox);
		mainFieldSet.getChildren().add(previewLine);
		mainFieldSet.getChildren().add(saveLine);
		
//		block.add(mainFieldSet);
		
//		block.add(urlLine);
//		block.add(altLine);
		layoutBox.add(alignmentLine);
		layoutBox.add(borderLine);
		spacingBox.add(horizLine);
		spacingBox.add(vertiLine);

		
		block.add(urlLine);
		block.add(altLine);
		block.add(layoutBox);
		block.add(spacingBox);
		block.add(previewLine);
		block.add(saveLine);
		return mainblock;
	}
	
	public HtmlOutputLabel getLabel(String localizeKey, String forName) {
		HtmlOutputLabel label = new HtmlOutputLabel();
		this.bundle.getLocalizedUIComponent(localizeKey, label);
		label.setFor(forName);
		return label;
	}

	@Override
	public void setExternalTabClass(String tab) {
		if (tab != null) {
			Vector v = new Vector();
			int index = tab.indexOf(",");
			try {
				while (index > -1) {
					String tmp = tab.substring(0, index);
					v.add(RefactorClassRegistry.forName(tmp).newInstance());
					tab = tab.substring(index+1);
					index = tab.indexOf(",");
				}
				v.add(RefactorClassRegistry.forName(tab).newInstance());
				if (this.tabs == null) {
					this.tabs = v;
				} else {
					this.tabs.addAll(v);
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
