/*
 * $Id: ArticleBlock.java,v 1.1 2004/06/07 07:50:29 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;
import com.idega.webface.WFErrorMessages;
import com.idega.webface.WFPanel;
import com.idega.webface.WFTaskbar;
import com.idega.webface.WFUtil;
import com.idega.webface.event.WFTaskbarEvent;
import com.idega.webface.event.WFTaskbarListener;

/**
 * Block for editing an article.   
 * <p>
 * Last modified: $Date: 2004/06/07 07:50:29 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public class ArticleBlock extends WFBlock implements ActionListener, WFTaskbarListener {

	public final static String ARTICLE_BLOCK_ID = "article_block";

	public final static String PARAMETER_ARTICLE_BEAN = "article_bean";
	
	private final static String TASK_ID_EDIT = "t_edit";
	private final static String TASK_ID_PREVIEW = "t_preview";
	private final static String TASK_ID_MESSAGES = "t_messages";
	
	private final static String P = "article_block_"; // Id prefix
	
	private final static String HEADLINE_ID = P + "headline";
	private final static String TEASER_ID = P + "teaser";
	private final static String BODY_ID = P + "body";
	private final static String MAIN_CATEGORY_ID = P + "main_category";
	private final static String AUTHOR_ID = P + "author";
	private final static String SOURCE_ID = P + "source";
	private final static String COMMENT_ID = P + "comment";
	
	private final static String USER_MESSAGE_ID = P + "user_message";
	
	private final static String SAVE_ID = P + "save";
	private final static String CANCEL_ID = P + "cancel";
	private final static String EDIT_CATEGORIES_ID = P + "edit_categories";
	
	private final static String TASKBAR_ID = P + "taskbar";
	
	private ArticleItemBean _articleItem = null;
	
	/**
	 * Default contructor.
	 */
	public ArticleBlock() {
	}
	
	/**
	 * Constructs an ArticleBlock with the specified title key. 
	 */
	public ArticleBlock(String titleKey) {
		super(titleKey);
		setId(ARTICLE_BLOCK_ID);
		ArticleItemBean bean = new ArticleItemBean();
		bean.setHeadline("");
		bean.setBody("");
		bean.setTeaser("");
		bean.setAuthor("");
		bean.setComment("");
		bean.setSource("");
		setArticleItem(bean);
		setWidth("700px");
		getTitlebar().setLocalizedTitle(true);
		setMainAreaStyleClass(null);
		
		WFTaskbar tb = new WFTaskbar();
		tb.setId(TASKBAR_ID);
		add(tb);
		tb.addButton(TASK_ID_EDIT, "Edit", getEditContainer());
		tb.addButton(TASK_ID_PREVIEW, "Preview", getPreviewContainer());
		tb.addButton(TASK_ID_MESSAGES, "Messages", getMessageContainer());
		tb.setSelectedButtonId(TASK_ID_EDIT);
		tb.addTaskbarListener(this);
	}
	
	/*
	 * Creates an edit container for the article.
	 */
	private UIComponent getEditContainer() {
		
		WFContainer c = new WFContainer();
		c.setStyleAttribute("padding", "8px");

		String ref = PARAMETER_ARTICLE_BEAN + ".";
		
		WFPanel p = new WFPanel();		
		p.setInputHeader("Headline:", 1, 1, "400px");		
		p.setInputHeader("Main Category:", 2, 1);		
		HtmlInputText headlineInput = WFUtil.getInputText(HEADLINE_ID, ref + "headline");
		
		headlineInput.setSize(40);
		p.setInput(headlineInput, 1, 2);		
		HtmlSelectOneMenu mainCategoryMenu = WFUtil.getSelectOneMenu(MAIN_CATEGORY_ID, ref + "categories", ref + "mainCategoryId");
		p.setInput(mainCategoryMenu, 2, 2);		
		p.setInputHeader("Teaser:", 1, 3);		
		p.setInputHeader("Author:", 2, 3);		
		HtmlInputTextarea teaserArea = WFUtil.getTextArea(TEASER_ID, ref + "teaser", "440px", "30px");
		p.setInput(teaserArea, 1, 4);		
		HtmlInputText authorInput = WFUtil.getInputText(AUTHOR_ID, ref + "author");
		authorInput.setSize(22);
		p.setInput(authorInput, 2, 4);		
		p.setInputHeader("Body:", 1, 5);		
		p.setInputHeader("Images:", 2, 5);		
		HtmlInputTextarea bodyArea = WFUtil.getTextArea(BODY_ID, ref + "body", "400px", "300px");
		p.setInput(bodyArea, 1, 6);		
		p.setInput(WFUtil.getText(" "), 2, 6);
		p.setInputHeader("Source:", 1, 7);		
		p.setInputHeader("", 2, 7);		
		HtmlInputTextarea sourceArea = WFUtil.getTextArea(SOURCE_ID, ref + "source", "440px", "30px");
		p.setInput(sourceArea, 1, 8);		
		p.setInput(WFUtil.getText(" "), 2, 8);		

		WFErrorMessages em = new WFErrorMessages();
		em.addErrorMessage(headlineInput.getId());
		em.addErrorMessage(teaserArea.getId());
		
		c.add(em);		
		c.add(p);
		
		WFContainer c2 = new WFContainer();
		c2.setStyleAttribute("padding", "6px");		
		c2.add(WFUtil.getBreak());
		c2.add(WFUtil.getHeaderText("Created:"));
		c2.add(WFUtil.getText(" 4/20/04 3:04 PM"));
		c2.add(WFUtil.getBreak(2));
		c2.add(WFUtil.getHeaderText("Status:"));
		c2.add(WFUtil.getText(" Published"));
		c2.add(WFUtil.getBreak(2));
		c2.add(WFUtil.getHeaderText("Current version:"));
		c2.add(WFUtil.getText(" 1.5"));
		c.add(c2);

		c.add(WFUtil.getBreak());
		
		p = new WFPanel();		
		p.setInputHeader("Comment:", 1, 1);		
		HtmlInputTextarea commentArea = WFUtil.getTextArea(COMMENT_ID, ref + "comment", "400px", "60px");
		p.setInput(commentArea, 1, 2);
		p.setInputHeader("Publishing date:", 1, 3);
		p.setInput(WFUtil.getText(" "), 1, 4);
		p.setInputHeader("Expiration date:", 1, 5);
		p.setInput(WFUtil.getText(" "), 1, 6);		
		c.add(p);

		c.add(WFUtil.getBreak());

		WFContainer bc = new WFContainer();
		bc.setStyleAttribute("padding", "4px");
		HtmlCommandButton saveButton = WFUtil.getButton(SAVE_ID, "Save", this);
		em.addErrorMessage(saveButton.getId()); //temp
		bc.add(saveButton);
		bc.add(WFUtil.getText(" "));
		HtmlCommandButton editCategoriesButton = WFUtil.getButton(EDIT_CATEGORIES_ID, "Edit categories", this);
		bc.add(editCategoriesButton);
		bc.add(WFUtil.getText(" "));
		HtmlCommandButton cancelButton = WFUtil.getButton(CANCEL_ID, "Cancel", this);
		bc.add(cancelButton);
		c.add(bc);

		return c;
	}
	
	/*
	 * Creates a preview container for the article.
	 */
	private UIComponent getPreviewContainer() {
		WFContainer c = new WFContainer();
		c.setStyleAttribute("padding", "14px");

		String ref = PARAMETER_ARTICLE_BEAN + ".";

		c.add(WFUtil.getHeaderTextVB(ref + "headline"));
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getTextVB(ref + "teaser"));
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getTextVB(ref + "body"));
		c.add(WFUtil.getBreak(4));
		
		c.add(WFUtil.getHeaderText("Author: "));
		c.add(WFUtil.getTextVB(ref + "author"));
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getHeaderText("Created: "));
		c.add(WFUtil.getText("4/20/04 3:04 PM"));
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getHeaderText("Status: "));
		c.add(WFUtil.getTextVB(ref + "status"));
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getHeaderText("Categories: "));
		c.add(WFUtil.getText("Public news, Business info"));
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getHeaderText("Current version: "));
		c.add(WFUtil.getText("1.5"));
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getHeaderText("Comment: "));
		c.add(WFUtil.getTextVB(ref + "comment"));
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getHeaderText("Source: "));
		c.add(WFUtil.getTextVB(ref + "source"));
		
		c.add(WFUtil.getBreak(2));
				
		return c;
	}
	
	/*
	 * Creates a message container for the article.
	 */
	private UIComponent getMessageContainer() {
		
		WFContainer c = new WFContainer();
		c.setStyleAttribute("padding", "12px");
		
		c.add(WFUtil.getText(USER_MESSAGE_ID, "No messages."));
		
		return c;
	}

	/**
	 * Returns the article item bean for this block. 
	 */
	public ArticleItemBean getArticleItem() {
		return _articleItem;
	}

	/**
	 * Sets the article item bean for this block. 
	 */
	public void setArticleItem(ArticleItemBean articleItem) {
		_articleItem = articleItem;
		if (_articleItem != null) {
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(PARAMETER_ARTICLE_BEAN, _articleItem);
		}
	}

	/**
	 * Sets this block to edit mode. 
	 */
	public void setEditMode() {
		WFTaskbar tb = (WFTaskbar) findComponent(TASKBAR_ID);
		tb.setSelectedButtonId(TASK_ID_EDIT);
	}

	/**
	 * Sets this block to preview mode. 
	 */
	public void setPreviewMode() {
		WFTaskbar tb = (WFTaskbar) findComponent(TASKBAR_ID);
		tb.setSelectedButtonId(TASK_ID_PREVIEW);
	}

	/**
	 * Sets this block to message mode. 
	 */
	public void setMessageMode() {
		WFTaskbar tb = (WFTaskbar) findComponent(TASKBAR_ID);
		tb.setSelectedButtonId(TASK_ID_MESSAGES);
	}

	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = _articleItem;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		_articleItem = (ArticleItemBean) values[1];
	}
	
	/**
	 * @see javax.faces.component.UIComponent#decode(javax.faces.context.FacesContext)
	 */
	public void decode(FacesContext context) {
		super.decode(context);
		setArticleItem(getArticleItem()); // Updates request map
	}
	
	/**
	 * javax.faces.event.ActionListener#processAction()
	 */
	public void processAction(ActionEvent event) {
		String id = event.getComponent().getId();
		ArticleBlock ab = (ArticleBlock) event.getComponent().findComponent(ARTICLE_BLOCK_ID);
		if (id.equals(SAVE_ID)) {
			ab.storeArticle();
		} else if (id.equals(CANCEL_ID)) {
			// ...
		}
	}

	/**
	 * Taskbar button pressed.  
	 */
	public void taskbarButtonPressed(WFTaskbarEvent e) {
		WFTaskbar t = e.getTaskbar();
		if (t.getSelectedButtonId().equals(TASK_ID_PREVIEW)) {
			ArticleBlock ab = (ArticleBlock) t.getParent().getParent();
		}
	}
	
	/**
	 * Stores the current article. 
	 */
	public void storeArticle() {
		try {
			_articleItem.store();
		} catch (ContentItemException e) {
			WFUtil.addLocalizedMessage(findComponent(SAVE_ID), e.getLocalizationKey());
			return;
		}
		setUserMessage("Article saved.");
	}
	
	/*
	 * Sets the text in the message task container. 
	 */
	private void setUserMessage(String message) {
		HtmlOutputText t = (HtmlOutputText) findComponent(USER_MESSAGE_ID);
		t.setValue(message);
		setMessageMode();
	}
}
