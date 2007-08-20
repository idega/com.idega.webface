/*
 * $Id: HTMLAreaLinkCreator.java,v 1.14 2007/08/20 14:43:02 valdas Exp $
 * Created on 1.3.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.htmlarea;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.stylesheet.Stylesheet;

import com.idega.idegaweb.IWBundle;
import com.idega.presentation.IWBaseComponent;
import com.idega.presentation.IWContext;
import com.idega.presentation.text.Link;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.repository.data.RefactorClassRegistry;
import com.idega.util.StringHandler;
import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;
import com.idega.webface.WFTabBar;
import com.idega.webface.WFTitlebar;
import com.idega.webface.WFUtil;


public class HTMLAreaLinkCreator extends IWBaseComponent{
	
	public final static String IW_BUNDLE_IDENTIFIER = "com.idega.webface";
	
	//	This parameter is referenced as an id in IWSelectDocument.js:
	final static String PARAMETER_HREF = "f_href";
	final static String PARAMETER_TARGET = "f_target";
	final static String PARAMETER_TOOLTIP = "f_title";
	private final static String PARAMETER_LINK_TYPE = "f_lt";
	public final static String PARAMETER_CREATOR = "pc";
	
	protected Collection tabs = null;
	protected IWBundle bundle;
	private String selectedType;
	private HTMLAreaLinkType currentLinkType = null;
	
	public void initializeComponent(FacesContext context) {
		IWContext iwc = IWContext.getIWContext(context);
		this.bundle = iwc.getIWMainApplication().getBundle(IW_BUNDLE_IDENTIFIER);
		init(iwc);
		
		Stylesheet sheet = new Stylesheet();
		sheet.setPath(bundle.getResourcesVirtualPath()+"/style/webfacewindow.css");
		add(sheet);
		
		WFContainer con = new WFContainer();
		UIComponent creation = getCreationComponent();
		con.add(getLinkTabBar());
		if (creation != null) {
			con.add(creation);
		}
		con.add(getSubmitTable(iwc));

		add(con);
		
	}
	
	/**
	 * 
	 * <p>
	 * TODO gimmi describe method getCreation
	 * </p>
	 * @return
	 */
	protected UIComponent getCreationComponent() {
		
		return this.currentLinkType.getLinkCreation();
	}
	
	protected void init(IWContext iwc) {
		if (this.tabs == null) {
			this.tabs = new Vector();
		}
		this.tabs.add(new HTMLAreaExternalLinkType());
		this.tabs.add(new HTMLAreaEmailLinkType());
		this.tabs.add(new HTMLAreaPageLinkType());
		
		
		this.selectedType = iwc.getParameter(PARAMETER_LINK_TYPE);
		String pc = iwc.getParameter(PARAMETER_CREATOR);
		if (pc == null) {
			if (this.selectedType != null) {
				Iterator iter = getLinkTypes().iterator();
				while (iter.hasNext() && this.currentLinkType == null) {
					HTMLAreaLinkType t = (HTMLAreaLinkType) iter.next();
					if (this.selectedType.equals(t.getLinkType())) {
						this.currentLinkType = t;
					}
				}
			} else {
				this.currentLinkType = new HTMLAreaExternalLinkType();
			}
		} else {
			try {
				this.currentLinkType = (HTMLAreaLinkType) RefactorClassRegistry.forName(pc).newInstance();
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

	private HtmlOutputText getLocalizedText(String key) {
		return this.bundle.getLocalizedText(key);
	}
	
	protected WFTabBar getLinkTabBar() {
		WFTabBar bar = new WFTabBar();
		bar.setStyleClass("ws_mainnavigation");
		int col = 1;
		Iterator iter = getLinkTypes().iterator();
		while (iter.hasNext()) {
			HTMLAreaLinkType t = (HTMLAreaLinkType) iter.next();
			HtmlOutputText text = new HtmlOutputText();
			text.setValueBinding("value", t.getLinkTypeName(this.bundle));
			HtmlOutputLink link = bar.addLink(text, null, "HTMLALCL_"+col++, this.currentLinkType.getLinkType().equals(t.getLinkType()));
			WFUtil.addParameter(link, PARAMETER_CREATOR, t.getClass().getName());
		}
		return bar;
	}
	
	protected WFBlock getSubmitTable(IWContext iwc) {
		WFBlock block = new WFBlock();
		WFTitlebar header = new WFTitlebar();
		header.addTitleText(this.bundle.getLocalizedText("link_creator"));
		block.setTitlebar(header);

		String surl = IWContext.getInstance().getParameter(PARAMETER_HREF);
		String star = IWContext.getInstance().getParameter(PARAMETER_TARGET);
		String stoo = IWContext.getInstance().getParameter(PARAMETER_TOOLTIP);
		
		HtmlInputText url = new HtmlInputText();
		url.setId(PARAMETER_HREF);
		url.setSize(40);
		if (surl != null) {
			url.setValue(StringHandler.removeAbsoluteReference(iwc.getServerName(), surl));
		} else if (this.currentLinkType.getStartingURL() != null) {
			url.setValue(this.currentLinkType.getStartingURL());
		} 
		HtmlInputText title = new HtmlInputText();
		title.setId(PARAMETER_TOOLTIP);
		if (stoo != null) {
			title.setValue(stoo);
		} else if (this.currentLinkType.getStartingTitle() != null) {
			title.setValue(this.currentLinkType.getStartingTitle());
		}
				
		DropdownMenu menu = new DropdownMenu(PARAMETER_TARGET);
		menu.setID(PARAMETER_TARGET);
		menu.addMenuElement(Link.TARGET_TOP_WINDOW);
		menu.addMenuElement(Link.TARGET_SELF_WINDOW);
		menu.addMenuElement(Link.TARGET_PARENT_WINDOW);
		menu.addMenuElement(Link.TARGET_NEW_WINDOW);
		menu.addMenuElement(Link.TARGET_BLANK_WINDOW);
		if (star != null) {
			menu.setSelectedElement(star);
		} else if (this.currentLinkType.getStartingTarget() != null) {
			menu.setSelectedElement(this.currentLinkType.getStartingTarget());
		}

		HtmlCommandButton saveButton = new HtmlCommandButton();
		this.bundle.getLocalizedUIComponent("save", saveButton);
		saveButton.setType("button");
		saveButton.setOnclick("onOK()");
		saveButton.setId("HTMLALC_SB");
		
		HtmlOutputText txtUrl = getLocalizedText("URL");
		HtmlOutputText txtTitle = getLocalizedText("title_tooltip");
		HtmlOutputText txtTarget = getLocalizedText("target");

		txtUrl.setStyleClass("wf_htmlare_linkcreator");
		txtTitle.setStyleClass("wf_htmlare_linkcreator");
		txtTarget.setStyleClass("wf_htmlare_linkcreator");
		url.setStyleClass("wf_inputtext");
		title.setStyleClass("wf_inputtext");
		menu.setStyleClass("wf_inputtext");

		WFContainer line1 = new WFContainer();
		line1.setStyleClass("wf_htmlare_linkcreator_line");
		line1.setId("wf_lc_href");
		line1.getChildren().add(txtUrl);
		line1.getChildren().add(url);
		block.add(line1);

		WFContainer line2 = new WFContainer();
		line2.setStyleClass("wf_htmlare_linkcreator_line");
		line2.setId("wf_lc_tooltip");
		line2.getChildren().add(txtTitle);
		line2.getChildren().add(title);
		block.add(line2);
		
		WFContainer line3 = new WFContainer();
		line3.setStyleClass("wf_htmlare_linkcreator_line");
		line3.setId("wf_lc_target");
		line3.getChildren().add(txtTarget);
		line3.getChildren().add(menu);
		block.add(line3);

		WFContainer line4 = new WFContainer();
		line4.setStyleClass("wf_htmlare_linkcreator_line");
		line4.setId("wf_lc_save");
		line4.getChildren().add(saveButton);
		block.add(line4);

				
		return block;
	}
	

	public String getBundleIdentifier() {
		return HTMLAreaLinkCreator.IW_BUNDLE_IDENTIFIER;
	}

	protected Collection getLinkTypes() {
		return this.tabs;
	}
	
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
