/*
 * $Id: SearchArticleBlock.java,v 1.2 2004/06/18 14:11:02 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;
import com.idega.webface.WFErrorMessages;
import com.idega.webface.WFList;
import com.idega.webface.WFPanel;
import com.idega.webface.WFUtil;
import com.idega.webface.convert.WFDateConverter;

/**
 * Block for searching articles.   
 * <p>
 * Last modified: $Date: 2004/06/18 14:11:02 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */
public class SearchArticleBlock extends WFBlock implements ActionListener, Serializable {

	public final static String SEARCH_ARTICLE_BLOCK_ID = "search_article_block";

	public final static String SEARCH_ARTICLE_BEAN_ID = "searchArticleBean";
	
	private final static String P = "search_article_block_"; // Id prefix
	
	private final static String SEARCH_TEXT_ID = P + "search_text";
	private final static String SEARCH_AUTHOR_ID = P + "search_author";
	private final static String SEARCH_CATEGORY_ID = P + "search_category";
	private final static String SEARCH_PUBLISHED_FROM_ID = P + "search_published_from";
	private final static String SEARCH_PUBLISHED_TO_ID = P + "search_published_to";

	private final static String SEARCH_BUTTON_ID = P + "search_button";

	private final static String RESULT_LIST_ID = P + "result_list";
	
	/**
	 * Default contructor.
	 */
	public SearchArticleBlock() {
	}
	
	/**
	 * Constructs an ArticleBlock with the specified title key. 
	 */
	public SearchArticleBlock(String titleKey) {
		super(titleKey);
		setId(SEARCH_ARTICLE_BLOCK_ID);
		
		WFUtil.invoke(SEARCH_ARTICLE_BEAN_ID, "setArticleLinkListener", this, ActionListener.class);

		add(getSearchPanel());
		add(WFUtil.getBreak());
		add(getSearchResultList());
		add(WFUtil.getBreak());
	}
	
	/*
	 * Creates a search form panel.
	 */
	private UIComponent getSearchPanel() {

		String ref = SEARCH_ARTICLE_BEAN_ID + ".";
		
		WFPanel p = new WFPanel();		
		p.setInputHeader("Article text:", 1, 1);		
		p.setInputHeader("Author:", 2, 1);		
		p.setInputHeader("Category:", 3, 1);		
		HtmlInputText searchTextInput = WFUtil.getInputText(SEARCH_TEXT_ID, ref + "searchText");		
		searchTextInput.setSize(40);
		p.setInput(searchTextInput, 1, 2);		
		HtmlInputText searchAuthorInput = WFUtil.getInputText(SEARCH_AUTHOR_ID, ref + "searchAuthor");		
		searchAuthorInput.setSize(30);
		p.setInput(searchAuthorInput, 2, 2);		
		HtmlSelectOneMenu searchCategoryMenu = WFUtil.getSelectOneMenu(SEARCH_CATEGORY_ID, ref + "categories", ref + "searchCategoryId");
		p.setInput(searchCategoryMenu, 3, 2);		
		p.setInputHeader("Published from:", 1, 3);		
		p.setInputHeader("Published to:", 2, 3);		
		p.setInputHeader(" ", 3, 3);		
		HtmlInputText searchPublishedFromInput = WFUtil.getInputText(SEARCH_PUBLISHED_FROM_ID, ref + "searchPublishedFrom");		
		searchPublishedFromInput.setSize(20);
		searchPublishedFromInput.setConverter(new WFDateConverter());
		p.setInput(searchPublishedFromInput, 1, 4);		
		HtmlInputText searchPublishedToInput = WFUtil.getInputText(SEARCH_PUBLISHED_TO_ID, ref + "searchPublishedTo");		
		searchPublishedToInput.setSize(20);
		searchPublishedToInput.setConverter(new WFDateConverter());
		p.setInput(searchPublishedToInput, 2, 4);		
		p.setInput(WFUtil.getText(" "), 3, 4);
		p.set(WFUtil.getText(" "), 1, 5);
		p.set(WFUtil.getButton(SEARCH_BUTTON_ID, "Search", this), 1, 6);

		WFErrorMessages em = new WFErrorMessages();
		em.addErrorMessage(SEARCH_PUBLISHED_FROM_ID);
		em.addErrorMessage(SEARCH_PUBLISHED_TO_ID);

		WFContainer c = new WFContainer();
		c.add(em);
		c.add(p);
		
		return c;
	}
	
	/**
	 * Returns the article list.
	 */
	protected UIComponent getSearchResultList() {
		WFList l = new WFList(SEARCH_ARTICLE_BEAN_ID, 0, 0);
		l.setId(RESULT_LIST_ID);
		return l;
	}
	
	/**
	 * javax.faces.event.ActionListener#processAction()
	 */
	public void processAction(ActionEvent event) {
		String date = "no date";
		date = "" + WFUtil.getObjectValue(SEARCH_ARTICLE_BEAN_ID, "searchPublishedFrom");
		
		if (event.getComponent().getId().equals(SEARCH_BUTTON_ID)) {
			WFUtil.invoke(SEARCH_ARTICLE_BEAN_ID, "search");
			return;
		}
		
		UIComponent link = event.getComponent();
		String id = WFUtil.getParameter(link, "id");
		WFUtil.invoke(ArticleBlock.ARTICLE_ITEM_BEAN_ID, "clear");
		WFUtil.invoke(ArticleBlock.ARTICLE_ITEM_BEAN_ID, "setLocaleId", "sv");
		WFUtil.invoke(ArticleBlock.ARTICLE_ITEM_BEAN_ID, "setHeadline", "search result, date =" + date);
		WFUtil.invoke(ArticleBlock.ARTICLE_ITEM_BEAN_ID, "setTeaser", "Teaser");
		WFUtil.invoke(ArticleBlock.ARTICLE_ITEM_BEAN_ID, "setBody", "Article " + id);
		WFUtil.invoke(ArticleBlock.ARTICLE_ITEM_BEAN_ID, "setAuthor", "author");
		WFUtil.invoke(ArticleBlock.ARTICLE_ITEM_BEAN_ID, "setComment", "comment");
		WFUtil.invoke(ArticleBlock.ARTICLE_ITEM_BEAN_ID, "setDescription", "description");
		WFUtil.invoke(ArticleBlock.ARTICLE_ITEM_BEAN_ID, "setStatus", ContentItemCaseBean.STATUS_PUBLISHED);
		WFUtil.invoke(ArticleBlock.ARTICLE_ITEM_BEAN_ID, "setMainCategoryId", new Integer(3));
		WFUtil.invoke(ArticleBlock.ARTICLE_ITEM_BEAN_ID, "setUpdated", new Boolean(true));
				
		WFUtil.setViewRoot("/cmspage.jsf");
	}
}
