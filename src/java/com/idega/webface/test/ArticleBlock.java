/*
 * $Id: ArticleBlock.java,v 1.4 2004/06/18 14:11:02 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectManyListbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.convert.IntegerConverter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.webface.WFBlock;
import com.idega.webface.WFComponentSelector;
import com.idega.webface.WFContainer;
import com.idega.webface.WFErrorMessages;
import com.idega.webface.WFPanel;
import com.idega.webface.WFPlainOutputText;
import com.idega.webface.WFTaskbar;
import com.idega.webface.WFUtil;
import com.idega.webface.convert.WFCommaSeparatedListConverter;
import com.idega.webface.convert.WFDateConverter;
import com.idega.webface.event.WFTaskbarListener;

/**
 * Block for editing an article.   
 * <p>
 * Last modified: $Date: 2004/06/18 14:11:02 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.4 $
 */
public class ArticleBlock extends WFBlock implements ActionListener {

	public final static String ARTICLE_BLOCK_ID = "article_block";

	public final static String ARTICLE_ITEM_BEAN_ID = "articleItemBean";
	
	private final static String P = "article_block_"; // Id prefix
	
	public final static String TASK_ID_EDIT = P + "t_edit";
	public final static String TASK_ID_PREVIEW = P + "t_preview";
	public final static String TASK_ID_MESSAGES = P + "t_messages";
	
	private final static String HEADLINE_ID = P + "headline";
	private final static String LOCALE_ID = P + "locale";
	private final static String TEASER_ID = P + "teaser";
	private final static String BODY_ID = P + "body";
	private final static String MAIN_CATEGORY_ID = P + "main_category";
	private final static String AUTHOR_ID = P + "author";
	private final static String SOURCE_ID = P + "source";
	private final static String COMMENT_ID = P + "comment";
	private final static String PUBLISHED_FROM_DATE_ID = P + "published_from_date";
	private final static String PUBLISHED_TO_DATE_ID = P + "published_to_date";
	
	private final static String USER_MESSAGE_ID = P + "user_message";
	
	private final static String SAVE_ID = P + "save";
	private final static String FOR_REVIEW_ID = P + "for_review";
	private final static String PUBLISH_ID = P + "publish";
	private final static String REWRITE_ID = P + "rewrite";
	private final static String REJECT_ID = P + "reject";
	private final static String DELETE_ID = P + "delete";
	private final static String CANCEL_ID = P + "cancel";
	private final static String EDIT_CATEGORIES_ID = P + "edit_categories";
	private final static String ADD_IMAGE_ID = P + "add_image";
	private final static String REMOVE_IMAGE_ID = P + "remove_image";
	private final static String FILE_UPLOAD_FORM_ID = P + "file_upload_form";
	private final static String FILE_UPLOAD_ID = P + "file_upload";
	private final static String FILE_UPLOAD_CANCEL_ID = P + "file_upload_cancel";
	
	private final static String TASKBAR_ID = P + "taskbar";

	private final static String BUTTON_SELECTOR_ID = P + "button_selector";
	private final static String EDITOR_SELECTOR_ID = P + "editor_selector";
	private final static String ARTICLE_EDITOR_ID = P + "article_editor";
	private final static String CATEGORY_EDITOR_ID = P + "category_editor";

	private final static String AVAILABLE_CATEGORIES_ID = P + "avaliable_categories";
	private final static String ARTICLE_CATEGORIES_ID = P + "article_categories";
	private final static String ADD_CATEGORIES_ID = P + "add_categories";
	private final static String SUB_CATEGORIES_ID = P + "sub_categories";
	private final static String CATEGORY_BACK_ID = P + "category_back";

	/**
	 * Default contructor.
	 */
	public ArticleBlock() {
	}
	
	/**
	 * Constructs an ArticleBlock with the specified title key. 
	 */
	public ArticleBlock(String titleKey, WFTaskbarListener taskbarListener) {
		super(titleKey);
		setId(ARTICLE_BLOCK_ID);
		getTitlebar().setLocalizedTitle(true);
		setMainAreaStyleClass(null);
		
		WFTaskbar tb = new WFTaskbar();
		tb.setId(TASKBAR_ID);
		add(tb);
		tb.addButton(TASK_ID_EDIT, "Edit", getEditContainer());
		tb.addButton(TASK_ID_PREVIEW, "Preview", getPreviewContainer());
		tb.addButton(TASK_ID_MESSAGES, "Messages", getMessageContainer());
		tb.setSelectedButtonId(TASK_ID_EDIT);
		if (taskbarListener != null) {
			tb.addTaskbarListener(taskbarListener);
		}
	}
	
	/**
	 * Constructs an ArticleBlock with the specified title key and taskbar listener. 
	 */
	public ArticleBlock(String titleKey) {
		this(titleKey, null);
	}
	
	/*
	 * Creates an edit container for the article.
	 */
	private UIComponent getEditContainer() {
		
		WFContainer c = new WFContainer();
		c.setId(ARTICLE_EDITOR_ID);

		String ref = ARTICLE_ITEM_BEAN_ID + ".";
		
		WFPanel p = new WFPanel();		
		p.setInputHeader("Headline:", 1, 1, "400px");		
		p.setInputHeader("Language:", 2, 1);		
		HtmlInputText headlineInput = WFUtil.getInputText(HEADLINE_ID, ref + "headline");		
		headlineInput.setSize(40);
		p.setInput(headlineInput, 1, 2);		
		HtmlSelectOneMenu localeMenu = WFUtil.getSelectOneMenu(LOCALE_ID, ref + "allLocales", ref + "pendingLocaleId");
		localeMenu.setOnchange("document.forms[0].submit();");
		p.setInput(localeMenu, 2, 2);		
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
		WFContainer imageContainer = new WFContainer();		
		imageContainer.add(WFUtil.getLink(ADD_IMAGE_ID, "Add image", this));
		imageContainer.add(WFUtil.getBreak());
		imageContainer.add(getImageList());
		p.setInput(imageContainer, 2, 6);
		p.setInputHeader("Source:", 1, 7);		
		p.setInputHeader("main category:", 2, 7);		
		HtmlInputTextarea sourceArea = WFUtil.getTextArea(SOURCE_ID, ref + "source", "440px", "30px");
		p.setInput(sourceArea, 1, 8);		
		HtmlSelectOneMenu mainCategoryMenu = WFUtil.getSelectOneMenu(MAIN_CATEGORY_ID, ref + "categories", ref + "mainCategoryId");
		p.setInput(mainCategoryMenu, 2, 8);		

		WFErrorMessages em = new WFErrorMessages();
		em.addErrorMessage(headlineInput.getId());
		em.addErrorMessage(teaserArea.getId());
		em.addErrorMessage(PUBLISHED_FROM_DATE_ID);
		em.addErrorMessage(PUBLISHED_TO_DATE_ID);
		
		c.add(em);		
		c.add(p);
		
		WFContainer c2 = new WFContainer();
		c2.setStyleAttribute("padding", "6px");		
		c2.add(WFUtil.getBreak());
		c2.add(WFUtil.getHeaderText("Created:"));
		c2.add(WFUtil.getText(" 4/20/04 3:04 PM"));
		c2.add(WFUtil.getBreak(2));
		c2.add(WFUtil.getHeaderText("Status:"));
		c2.add(WFUtil.getTextVB(ref + "status"));
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
		HtmlInputText publishedFromInput = WFUtil.getInputText(PUBLISHED_FROM_DATE_ID, ref + "case.publishedFromDate");		
		publishedFromInput.setSize(20);
		publishedFromInput.setConverter(new WFDateConverter());
		p.setInput(publishedFromInput, 1, 4);		
		p.setInputHeader("Expiration date:", 1, 5);
		HtmlInputText publishedToInput = WFUtil.getInputText(PUBLISHED_TO_DATE_ID, ref + "case.publishedToDate");		
		publishedToInput.setSize(20);
		publishedToInput.setConverter(new WFDateConverter());
		p.setInput(publishedToInput, 1, 6);		
		c.add(p);

		c.add(WFUtil.getBreak());

		WFContainer bc = new WFContainer();
		bc.setStyleAttribute("padding", "4px");
		HtmlCommandButton editCategoriesButton = WFUtil.getButton(EDIT_CATEGORIES_ID, "Edit categories", this);
		bc.add(editCategoriesButton);
		c.add(bc);

		c.add(WFUtil.getBreak());
		
		WFComponentSelector cs = new WFComponentSelector();
		cs.setId(BUTTON_SELECTOR_ID);
		cs.setStyleAttribute("padding", "4px");
		cs.setDividerText(" ");
		HtmlCommandButton saveButton = WFUtil.getButton(SAVE_ID, "Save", this);
		em.addErrorMessage(saveButton.getId());
		cs.add(saveButton);
		cs.add(WFUtil.getButton(FOR_REVIEW_ID, "For review", this));
		cs.add(WFUtil.getButton(PUBLISH_ID, "Publish", this));
		cs.add(WFUtil.getButton(REWRITE_ID, "Rewrite", this));
		cs.add(WFUtil.getButton(REJECT_ID, "Reject", this));
		cs.add(WFUtil.getButton(DELETE_ID, "Delete", this));
		cs.add(WFUtil.getButton(CANCEL_ID, "Cancel", this));
		cs.setSelectedId(CANCEL_ID, true);
		
		c.add(cs);
		
		WFComponentSelector editorSelector = new WFComponentSelector();
		editorSelector.setId(EDITOR_SELECTOR_ID);
		editorSelector.setStyleAttribute("padding", "8px");
		editorSelector.add(c);
		editorSelector.add(getCategoryEditContainer());
		FileUploadForm f = new FileUploadForm(this, FILE_UPLOAD_ID, FILE_UPLOAD_CANCEL_ID);
		f.setId(FILE_UPLOAD_FORM_ID);
		editorSelector.add(f);
		editorSelector.setSelectedId(ARTICLE_EDITOR_ID, true);
		
		return editorSelector;
	}

	/*
	 * Returns a list with images for the article.
	 */
	private UIComponent getImageList() {
		String var = "article_images";
		HtmlDataTable t = new HtmlDataTable();
		t.setVar(var);
		WFUtil.setValueBinding(t, "value", ARTICLE_ITEM_BEAN_ID + ".images");
		t.setColumnClasses("wf_valign_middle");
		
		UIColumn col = new UIColumn();
		HtmlGraphicImage image = new HtmlGraphicImage();
		image.setStyle("width:40px;height:40px;");
		WFUtil.setValueBinding(image, "url", var + ".imageURI");
		col.getChildren().add(image);
		t.getChildren().add(col);
		
		col = new UIColumn();
		HtmlCommandButton removeButton = WFUtil.getButton(REMOVE_IMAGE_ID, "Remove", this);
		removeButton.setOnclick("return confirm('Are you sure you want to remove the image?');return false;");
		WFUtil.addParameterVB(removeButton, "image_no", var + ".orderNoString");
		col.getChildren().add(removeButton);
		t.getChildren().add(col);
		
		return t;
	}
	
	/*
	 * Returns container with form for editing categories.
	 */
	private UIComponent getCategoryEditContainer() {
		WFPanel p = new WFPanel();

		String ref = ARTICLE_ITEM_BEAN_ID + ".";

		p.setId(CATEGORY_EDITOR_ID);
		p.setInputHeader("Available categories:", 1, 1, "20%");		
		p.setInputHeader(" ", 2, 1);		
		p.setInputHeader("Categories for this article:", 3, 1, "75%");

		WFContainer c = new WFContainer();
		HtmlSelectManyListbox availableCategories = WFUtil.getSelectManyListbox(AVAILABLE_CATEGORIES_ID,
				ref + "availableCategories", ref + "selectedAvailableCategories");
		availableCategories.setStyle("width:200px;height:160px;");
		availableCategories.setConverter(new IntegerConverter());
		c.add(availableCategories);
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getButton(CATEGORY_BACK_ID, "Back", this));
		p.setInput(c, 1, 2);
		
		c = new WFContainer();
		c.add(WFUtil.getBreak());
		c.add(WFUtil.getButton(ADD_CATEGORIES_ID, ">", this));
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getButton(SUB_CATEGORIES_ID, "<", this));
		p.setInput(c, 2, 2);
		
		HtmlSelectManyListbox articleCategories = WFUtil.getSelectManyListbox(ARTICLE_CATEGORIES_ID, 
				ref + "categories", ref + "selectedCategories");
		articleCategories.setStyle("width:200px;height:160px;");
		articleCategories.setConverter(new IntegerConverter());
		p.setInput(articleCategories, 3, 2);
				
		return p;
	}
	
	/**
	 * Updates the buttons in edit mode depending on the status of the current article.
	 */
	public void updateEditButtons() {
		WFComponentSelector cs = (WFComponentSelector) findComponent(BUTTON_SELECTOR_ID);
		String s = WFUtil.getValue(ARTICLE_ITEM_BEAN_ID, "status");

		if (s.equals(ContentItemCaseBean.STATUS_NEW)) {
			cs.setSelectedId(SAVE_ID, false);
			cs.setSelectedId(FOR_REVIEW_ID, true);
			cs.setSelectedId(PUBLISH_ID, true);
			cs.setSelectedId(REWRITE_ID, false);
			cs.setSelectedId(REJECT_ID, false);
			cs.setSelectedId(DELETE_ID, false);
		} else if (s.equals(ContentItemCaseBean.STATUS_READY_FOR_REVIEW)) {
			cs.setSelectedId(SAVE_ID, false);
			cs.setSelectedId(FOR_REVIEW_ID, false);
			cs.setSelectedId(PUBLISH_ID, true);
			cs.setSelectedId(REWRITE_ID, true);
			cs.setSelectedId(REJECT_ID, true);
			cs.setSelectedId(DELETE_ID, false);
		} else if (s.equals(ContentItemCaseBean.STATUS_UNDER_REVIEW)) {
			cs.setSelectedId(SAVE_ID, false);
			cs.setSelectedId(FOR_REVIEW_ID, false);
			cs.setSelectedId(PUBLISH_ID, true);
			cs.setSelectedId(REWRITE_ID, true);
			cs.setSelectedId(REJECT_ID, true);
			cs.setSelectedId(DELETE_ID, false);
		} else if (s.equals(ContentItemCaseBean.STATUS_REWRITE)) {
			cs.setSelectedId(SAVE_ID, false);
			cs.setSelectedId(FOR_REVIEW_ID, true);
			cs.setSelectedId(PUBLISH_ID, true);
			cs.setSelectedId(REWRITE_ID, false);
			cs.setSelectedId(REJECT_ID, false);
			cs.setSelectedId(DELETE_ID, false);
		} else if (s.equals(ContentItemCaseBean.STATUS_PENDING_PUBLISHING)) {
			cs.setSelectedId(SAVE_ID, true);
			cs.setSelectedId(FOR_REVIEW_ID, false);
			cs.setSelectedId(PUBLISH_ID, false);
			cs.setSelectedId(REWRITE_ID, false);
			cs.setSelectedId(REJECT_ID, false);
			cs.setSelectedId(DELETE_ID, true);
		} else if (s.equals(ContentItemCaseBean.STATUS_PUBLISHED)) {
			cs.setSelectedId(SAVE_ID, true);
			cs.setSelectedId(FOR_REVIEW_ID, false);
			cs.setSelectedId(PUBLISH_ID, false);
			cs.setSelectedId(REWRITE_ID, false);
			cs.setSelectedId(REJECT_ID, false);
			cs.setSelectedId(DELETE_ID, true);
		} else if (s.equals(ContentItemCaseBean.STATUS_EXPIRED)) {
			cs.setSelectedId(SAVE_ID, true);
			cs.setSelectedId(FOR_REVIEW_ID, false);
			cs.setSelectedId(PUBLISH_ID, false);
			cs.setSelectedId(REWRITE_ID, false);
			cs.setSelectedId(REJECT_ID, false);
			cs.setSelectedId(DELETE_ID, true);
		} else if (s.equals(ContentItemCaseBean.STATUS_DELETED)) {
			cs.setSelectedId(SAVE_ID, true);
			cs.setSelectedId(FOR_REVIEW_ID, true);
			cs.setSelectedId(PUBLISH_ID, true);
			cs.setSelectedId(REWRITE_ID, true);
			cs.setSelectedId(REJECT_ID, true);
			cs.setSelectedId(DELETE_ID, true);
		}
	}
	
	/*
	 * Creates a preview container for the article.
	 */
	private UIComponent getPreviewContainer() {
		WFContainer c = new WFContainer();
		c.setStyleAttribute("padding", "14px");

		String ref = ARTICLE_ITEM_BEAN_ID + ".";

		c.add(WFUtil.getHeaderTextVB(ref + "headline"));
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getTextVB(ref + "teaser"));
		c.add(WFUtil.getBreak(2));
		WFPlainOutputText bodyText = new WFPlainOutputText();
		WFUtil.setValueBinding(bodyText, "value", ref + "body");
		c.add(bodyText);
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
		HtmlOutputText t = WFUtil.getTextVB(ref + "categoryNames");
		t.setConverter(new WFCommaSeparatedListConverter());		
		c.add(t);
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
	 * javax.faces.event.ActionListener#processAction()
	 */
	public void processAction(ActionEvent event) {
		String id = event.getComponent().getId();
		ArticleBlock ab = (ArticleBlock) event.getComponent().findComponent(ARTICLE_BLOCK_ID);
		if (id.equals(SAVE_ID)) {
			ab.storeArticle();
		} else if (id.equals(FOR_REVIEW_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setStatus", ContentItemCaseBean.STATUS_READY_FOR_REVIEW);
			ab.storeArticle();
		} else if (id.equals(PUBLISH_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setStatus", ContentItemCaseBean.STATUS_PUBLISHED);
			ab.storeArticle();
		} else if (id.equals(REWRITE_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setStatus", ContentItemCaseBean.STATUS_REWRITE);
			ab.storeArticle();
		} else if (id.equals(REJECT_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setStatus", ContentItemCaseBean.STATUS_DELETED);
			ab.storeArticle();
		} else if (id.equals(DELETE_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setStatus", ContentItemCaseBean.STATUS_DELETED);
			ab.storeArticle();
		} else if (id.equals(EDIT_CATEGORIES_ID)) {
			WFComponentSelector cs = (WFComponentSelector) event.getComponent().findComponent(EDITOR_SELECTOR_ID);
			cs.setSelectedId(ARTICLE_EDITOR_ID, false);
			cs.setSelectedId(CATEGORY_EDITOR_ID, true);
			cs.setSelectedId(FILE_UPLOAD_FORM_ID, false);
		} else if (id.equals(ADD_CATEGORIES_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "addSelectedCategories");
		} else if (id.equals(SUB_CATEGORIES_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "removeSelectedCategories");
		} else if (id.equals(CATEGORY_BACK_ID)) {
			WFComponentSelector cs = (WFComponentSelector) event.getComponent().findComponent(EDITOR_SELECTOR_ID);
			cs.setSelectedId(ARTICLE_EDITOR_ID, true);
			cs.setSelectedId(CATEGORY_EDITOR_ID, false);
			cs.setSelectedId(FILE_UPLOAD_FORM_ID, false);
		} else if (id.equals(ADD_IMAGE_ID)) {
			WFComponentSelector cs = (WFComponentSelector) event.getComponent().findComponent(EDITOR_SELECTOR_ID);
			cs.setSelectedId(ARTICLE_EDITOR_ID, false);
			cs.setSelectedId(CATEGORY_EDITOR_ID, false);
			cs.setSelectedId(FILE_UPLOAD_FORM_ID, true);
		} else if (id.equals(FILE_UPLOAD_CANCEL_ID)) {
			WFComponentSelector cs = (WFComponentSelector) event.getComponent().findComponent(EDITOR_SELECTOR_ID);
			cs.setSelectedId(ARTICLE_EDITOR_ID, true);
			cs.setSelectedId(CATEGORY_EDITOR_ID, false);
			cs.setSelectedId(FILE_UPLOAD_FORM_ID, false);
		} else if (id.equals(FILE_UPLOAD_ID)) {
			WFComponentSelector cs = (WFComponentSelector) event.getComponent().findComponent(EDITOR_SELECTOR_ID);
			cs.setSelectedId(ARTICLE_EDITOR_ID, true);
			cs.setSelectedId(CATEGORY_EDITOR_ID, false);
			cs.setSelectedId(FILE_UPLOAD_FORM_ID, false);
		} else if (id.equals(REMOVE_IMAGE_ID)) {
			int imageNo = WFUtil.getIntParameter(event.getComponent(), "image_no");
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "removeImage", new Integer(imageNo));
		}
	}
	
	/**
	 * Stores the current article. 
	 */
	public void storeArticle() {
		boolean storeOk = ((Boolean) WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "store")).booleanValue();
		if (!storeOk) {
			List errorKeys = (List) WFUtil.getObjectValue(ARTICLE_ITEM_BEAN_ID, "errorKeys");
			if (errorKeys != null) {
				for (Iterator iter = errorKeys.iterator(); iter.hasNext();) {
					String errorKey = (String) iter.next();
					WFUtil.addLocalizedMessage(findComponent(SAVE_ID), errorKey);
				}
			}
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
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "updateLocale");
		updateEditButtons();
		super.encodeBegin(context);
	}
}
