/*
 * $Id: ListArticlesBlock.java,v 1.3 2004/06/28 09:09:50 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import java.io.Serializable;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.webface.WFBlock;
import com.idega.webface.WFComponentSelector;
import com.idega.webface.WFContainer;
import com.idega.webface.WFErrorMessages;
import com.idega.webface.WFList;
import com.idega.webface.WFPanelUtil;
import com.idega.webface.WFPlainOutputText;
import com.idega.webface.WFUtil;
import com.idega.webface.convert.WFDateConverter;
import com.idega.webface.test.bean.*;

/**
 * Block for listing articles.   
 * <p>
 * Last modified: $Date: 2004/06/28 09:09:50 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.3 $
 */
public class ListArticlesBlock extends WFBlock implements ManagedContentBeans, ActionListener, Serializable {

	public final static String LIST_ARTICLES_BLOCK_ID = "list_articles_block";

	public final static String LIST_ARTICLES_BEAN_ID = "listArticlesBean";
	
	private final static String P = "list_articles_block_"; // Id prefix
	
	private final static String SEARCH_PUBLISHED_FROM_ID = P + "search_published_from";
	private final static String SEARCH_PUBLISHED_TO_ID = P + "search_published_to";
	private final static String SEARCH_CATEGORY_ID = P + "search_category";

	private final static String RESULT_LIST_ID = P + "result_list";
	private final static String LOCALE_ID = P + "locale_id";
	private final static String LIST_BUTTON_ID = P + "list_button";
	private final static String VIEW_ARTICLE_BACK_BUTTON_ID = P + "view_back_button";

	private final static String LIST_PANEL_ID = P + "list_panel";
	private final static String VIEW_ARTICLE_PANEL_ID = P + "view_article_panel";
	private final static String DISPLAY_SELECTOR_ID = P + "selector";
	
	/**
	 * Default contructor.
	 */
	public ListArticlesBlock() {
	}
	
	/**
	 * Constructs a ListArticlesBlock with the specified title key. 
	 */
	public ListArticlesBlock(String titleKey) {
		super(titleKey);
		setId(LIST_ARTICLES_BLOCK_ID);
		
		WFUtil.invoke(LIST_ARTICLES_BEAN_ID, "setArticleLinkListener", this, ActionListener.class);

		WFComponentSelector cs = new WFComponentSelector();
		cs.setId(DISPLAY_SELECTOR_ID);
		cs.add(getListPanel());
		cs.add(getViewArticlePanel());
		cs.setSelectedId(LIST_PANEL_ID, true);
		cs.setSelectedId(VIEW_ARTICLE_PANEL_ID, false);		
		add(cs);
	}
	
	/*
	 * Creates a search form panel.
	 */
	private UIComponent getListPanel() {
		WFContainer listPanel = new WFContainer();
		listPanel.setId(LIST_PANEL_ID);
		listPanel.add(getSearchPanel());
		listPanel.add(WFUtil.getBreak());
		listPanel.add(getSearchResultList());
		return listPanel;
	}
	
	/*
	 * Creates a search form panel.
	 */
	private UIComponent getSearchPanel() {

		String ref = LIST_ARTICLES_BEAN_ID + ".";

		WFContainer mainContainer = new WFContainer();
		
		WFErrorMessages em = new WFErrorMessages();
		em.addErrorMessage(SEARCH_PUBLISHED_FROM_ID);
		em.addErrorMessage(SEARCH_PUBLISHED_TO_ID);
		
		mainContainer.add(em);
		
		HtmlPanelGrid p = WFPanelUtil.getFormPanel(3);		
		p.getChildren().add(WFUtil.getText("Published from:"));		
		p.getChildren().add(WFUtil.getText("Published to:"));		
		p.getChildren().add(WFUtil.getText("Category:"));		
		HtmlInputText searchPublishedFromInput = WFUtil.getInputText(SEARCH_PUBLISHED_FROM_ID, ref + "searchPublishedFrom");		
		searchPublishedFromInput.setSize(20);
		searchPublishedFromInput.setConverter(new WFDateConverter());
		p.getChildren().add(searchPublishedFromInput);		
		HtmlInputText searchPublishedToInput = WFUtil.getInputText(SEARCH_PUBLISHED_TO_ID, ref + "searchPublishedTo");		
		searchPublishedToInput.setSize(20);
		searchPublishedToInput.setConverter(new WFDateConverter());
		p.getChildren().add(searchPublishedToInput);		
		HtmlSelectOneMenu searchCategoryMenu = WFUtil.getSelectOneMenu(SEARCH_CATEGORY_ID, ref + "categories", ref + "searchCategoryId");
		p.getChildren().add(searchCategoryMenu);
		
		mainContainer.add(p);
		mainContainer.add(WFUtil.getText(" "));

		p = WFPanelUtil.getPlainFormPanel(1);
		p.getChildren().add(WFUtil.getButton(LIST_BUTTON_ID, "List", this));
		
		mainContainer.add(p);
		
		return mainContainer;
	}
	
	/*
	 * Returns the article list.
	 */
	private UIComponent getSearchResultList() {
		WFList l = new WFList(LIST_ARTICLES_BEAN_ID, 0, 5);
		l.setRowClasses(null);
		l.setId(RESULT_LIST_ID);
		l.setNavigationBelowList(true);
		return l;
	}
	
	/*
	 * Returns the view article panel.
	 */
	private UIComponent getViewArticlePanel() {
		String ref = ARTICLE_ITEM_BEAN_ID + ".";
		WFContainer c = new WFContainer();
		c.setStyleAttribute("padding", "10px");
		c.setId(VIEW_ARTICLE_PANEL_ID);
		c.add(WFUtil.getTextVB(ref + "case.publishedFromDate"));
		c.add(new WFPlainOutputText("&nbsp;&nbsp;"));
		HtmlSelectOneMenu localeMenu = WFUtil.getSelectOneMenu(LOCALE_ID, ref + "allLocales", ref + "localeId");
		localeMenu.setOnchange("document.forms[0].submit();");
		c.add(localeMenu);		
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getLinkVB("", ref + "headline", null));
		c.add(WFUtil.getBreak(2));
		WFPlainOutputText t = new WFPlainOutputText();
		WFUtil.setValueBinding(t, "value", ref + "body");
		c.add(t);
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getButton(VIEW_ARTICLE_BACK_BUTTON_ID, "Back", this));
		return c;
	}
	
	/**
	 * javax.faces.event.ActionListener#processAction()
	 */
	public void processAction(ActionEvent event) {
		if (event.getComponent().getId().equals(LIST_BUTTON_ID)) {
			WFUtil.invoke(LIST_ARTICLES_BEAN_ID, "list");
			return;
		} else if (event.getComponent().getId().equals(VIEW_ARTICLE_BACK_BUTTON_ID)) {
			WFComponentSelector cs = (WFComponentSelector) event.getComponent().findComponent(DISPLAY_SELECTOR_ID);
			cs.setSelectedId(LIST_PANEL_ID, true);
			cs.setSelectedId(VIEW_ARTICLE_PANEL_ID, false);
			return;
		}
		
		UIComponent link = event.getComponent();
		String id = WFUtil.getParameter(link, "id");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "clear");
		ContentItemCaseBean caze = new ContentItemCaseBean();
		caze.setPublishedFromDate(new Date());
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setCase", caze);		
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setLocaleId", "en");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setHeadline", "Electronic Reykjavik Up-And-Running (id = " + id + ")");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setTeaser", "Teaser");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setBody", "The first 24/7 service offered by Reykjavik Municipality as part of the Electronic Reykjavik " +
				"concept, was launched on May 25th., as planned. Now all applications and administration " +
				"processes regarding Music school applications and internal student registration are " +
				"implemented in the IdegaWeb eGOV solution.<br/><br/>" +
				"All applications are submitted through the Citizen Account to the Music schools. "+
				"The schools use the system to process the applications. "+
				"In the Citizen Account users can see the status of cases and applications, " + 
				"view messages and communicate with the Music school administrators.<br/><br/>" +
				"For more information, you can access the website of Reykjavik <a href=\"#\">here</a>. " +
				"Click on the icon for Electronic Reykjavik (Rafræn Reykjavik) or go directly to the portal " +
				"<a href=\"#\">here</a>.");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setAuthor", "Author");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setComment", "Comment");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setDescription", "Description");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setStatus", ContentItemCaseBean.STATUS_PUBLISHED);
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setMainCategoryId", new Integer(3));
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setLocaleId", "sv");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setHeadline", "Electronic Rykjavik klar");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setBody", "Den första 24-timmarsmyndigheten för Reykjaviks kommun igång.");
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setLocaleId", "en");

		WFComponentSelector cs = (WFComponentSelector) event.getComponent().getParent().getParent().getParent().findComponent(DISPLAY_SELECTOR_ID);
		cs.setSelectedId(LIST_PANEL_ID, false);
		cs.setSelectedId(VIEW_ARTICLE_PANEL_ID, true);
	}
}
