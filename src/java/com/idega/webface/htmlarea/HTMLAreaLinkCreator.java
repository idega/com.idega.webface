/*
 * $Id: HTMLAreaLinkCreator.java,v 1.1 2005/03/08 10:39:39 gimmi Exp $
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
import java.util.Map;
import java.util.Vector;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import com.idega.idegaweb.IWBundle;
import com.idega.presentation.IWBaseComponent;
import com.idega.presentation.IWContext;
import com.idega.presentation.text.Link;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;
import com.idega.webface.WFTabBar;
import com.idega.webface.WFTitlebar;
import com.idega.webface.WFUtil;


public class HTMLAreaLinkCreator extends IWBaseComponent{
	
	public final static String IW_BUNDLE_IDENTIFIER = "com.idega.webface";
	
	private Collection tabs = null;
	
	private final String PARAMETER_CREATOR = "pc";
	private final static String PARAMETER_HREF = "f_href";
	private final static String PARAMETER_TARGET = "f_target";
	private final static String PARAMETER_TOOLTIP = "f_title";
	private final static String PARAMETER_LINK_TYPE = "f_lt";
	
	private IWBundle bundle;
	private String selectedType;
	HTMLAreaLinkType currentLinkType = null;
	
	public void decode(FacesContext context) {
		Map parameters = context.getExternalContext().getRequestParameterMap();
		selectedType = (String) parameters.get(PARAMETER_LINK_TYPE);
		System.out.println(selectedType);
		super.decode(context);
	}
	
	public void initializeContent() {
		IWContext iwc = IWContext.getInstance();
		init(iwc);
		
		WFContainer con = new WFContainer();
		UIComponent creation = currentLinkType.getLinkCreation();
		con.add(getLinkTabBar());
		if (creation != null) {
			con.add(creation);
		}
		con.add(getSubmitTable(currentLinkType));

		add(con);
		
	}
	
	private void init(IWContext iwc) {
		bundle = iwc.getIWMainApplication().getBundle(IW_BUNDLE_IDENTIFIER);
		if (tabs == null) {
			tabs = new Vector();
		}
		tabs.add(new HTMLAreaExternalLinkType());
		tabs.add(new HTMLAreaEmailLinkType());
		
		selectedType = iwc.getParameter(PARAMETER_LINK_TYPE);
		String pc = iwc.getParameter(PARAMETER_CREATOR);
		if (pc == null) {
			if (selectedType != null) {
				Iterator iter = getLinkTypes().iterator();
				while (iter.hasNext() && currentLinkType == null) {
					HTMLAreaLinkType t = (HTMLAreaLinkType) iter.next();
					if (selectedType.equals(t.getLinkType())) {
						currentLinkType = t;
					}
				}
			} else {
				currentLinkType = new HTMLAreaExternalLinkType();
			}
		} else {
			try {
				currentLinkType = (HTMLAreaLinkType) Class.forName(pc).newInstance();
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

	private ValueBinding localize(String key) {
		return bundle.getValueBinding(key);
	}
	
	private HtmlOutputText getLocalizedText(String key) {
		return bundle.getLocalizedText(key);
	}
	
	private WFTabBar getLinkTabBar() {
		WFTabBar bar = new WFTabBar();
		int col = 1;
		Iterator iter = getLinkTypes().iterator();
		while (iter.hasNext()) {
			HTMLAreaLinkType t = (HTMLAreaLinkType) iter.next();
			HtmlOutputText text = new HtmlOutputText();
			text.setValueBinding("value", t.getLinkTypeName(bundle));
			HtmlOutputLink link = bar.addLink(text, null, "HTMLALCL_"+col++, currentLinkType.getLinkType().equals(t.getLinkType()));
			WFUtil.addParameter(link, PARAMETER_CREATOR, t.getClass().getName());
		}
		return bar;
	}
	
	private WFBlock getSubmitTable(HTMLAreaLinkType currentLinkType) {
		HtmlInputText url = new HtmlInputText();
		url.setId(PARAMETER_HREF);
		url.setSize(40);
		if (currentLinkType.getStartingURL() != null) {
			url.setValue(currentLinkType.getStartingURL());
		}
		HtmlInputText title = new HtmlInputText();
		title.setId(PARAMETER_TOOLTIP);
		if (currentLinkType.getStartingTitle() != null) {
			title.setValue(currentLinkType.getStartingTitle());
		}
				
		DropdownMenu menu = new DropdownMenu(PARAMETER_TARGET);
		menu.setID(PARAMETER_TARGET);
		menu.addMenuElement(Link.TARGET_TOP_WINDOW);
		menu.addMenuElement(Link.TARGET_SELF_WINDOW);
		menu.addMenuElement(Link.TARGET_PARENT_WINDOW);
		menu.addMenuElement(Link.TARGET_NEW_WINDOW);
		menu.addMenuElement(Link.TARGET_BLANK_WINDOW);
		if (currentLinkType.getStartingURL() != null) {
			menu.setSelectedElement(currentLinkType.getStartingTarget());
		}

		HtmlCommandButton saveButton = new HtmlCommandButton();
		bundle.getLocalizedUIComponent("save", saveButton);
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

		WFContainer line2 = new WFContainer();
		line2.setStyleClass("wf_htmlare_linkcreator_line");
		line2.setId("wf_lc_tooltip");
		line2.getChildren().add(txtTitle);
		line2.getChildren().add(title);
		
		WFContainer line3 = new WFContainer();
		line3.setStyleClass("wf_htmlare_linkcreator_line");
		line3.setId("wf_lc_target");
		line3.getChildren().add(txtTarget);
		line3.getChildren().add(menu);

		WFContainer line4 = new WFContainer();
		line4.setStyleClass("wf_htmlare_linkcreator_line");
		line4.setId("wf_lc_save");
		line4.getChildren().add(saveButton);

				
		WFBlock block = new WFBlock();
		WFTitlebar header = new WFTitlebar();
		header.addTitleText(bundle.getLocalizedText("link_creator"));
		block.setTitlebar(header);
		block.add(line1);
		block.add(line2);
		block.add(line3);
		block.add(line4);
		return block;
	}
	

	public String getBundleIdentifier() {
		return this.IW_BUNDLE_IDENTIFIER;
	}

	private Collection getLinkTypes() {
		return tabs;
	}
	
	public void setExternalTabClass(String tab) {
		if (tab != null) {
			Vector v = new Vector();
			int index = tab.indexOf(",");
			try {
				while (index > -1) {
					String tmp = tab.substring(0, index);
					v.add((HTMLAreaLinkType)Class.forName(tmp).newInstance());
					tab = tab.substring(index+1);
					index = tab.indexOf(",");
				}
				v.add((HTMLAreaLinkType)Class.forName(tab).newInstance());
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
