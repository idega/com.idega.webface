/*
 * $Id: ArticleBlock.java,v 1.1 2004/06/28 09:32:10 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test.component;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectManyListbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.convert.IntegerConverter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.webface.WFBlock;
import com.idega.webface.WFComponentSelector;
import com.idega.webface.WFContainer;
import com.idega.webface.WFDateInput;
import com.idega.webface.WFErrorMessages;
import com.idega.webface.WFList;
import com.idega.webface.WFPanelUtil;
import com.idega.webface.WFPlainOutputText;
import com.idega.webface.WFTaskbar;
import com.idega.webface.WFUtil;
import com.idega.webface.convert.WFCommaSeparatedListConverter;
import com.idega.webface.event.WFTaskbarListener;
import com.idega.webface.test.bean.*;

/**
 * Block for editing an article.   
 * <p>
 * Last modified: $Date: 2004/06/28 09:32:10 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public class ArticleBlock extends WFBlock implements ActionListener, ManagedContentBeans {

	public final static String ARTICLE_BLOCK_ID = "article_block";
	
	private final static String P = "article_block_"; // Id prefix
	
	public final static String TASK_ID_EDIT = P + "t_edit";
	public final static String TASK_ID_PREVIEW = P + "t_preview";
	public final static String TASK_ID_MESSAGES = P + "t_messages";
	
	private final static String HEADLINE_ID = P + "headline";
	private final static String LOCALE_ID = P + "locale";
	private final static String TEASER_ID = P + "teaser";
	public final static String BODY_ID = P + "body";
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
	private final static String ADD_ATTACHMENT_ID = P + "add_attachment";
	private final static String REMOVE_ATTACHMENT_ID = P + "remove_attachment";
	private final static String FILE_UPLOAD_FORM_ID = P + "file_upload_form";
	private final static String FILE_UPLOAD_ID = P + "file_upload";
	private final static String FILE_UPLOAD_CANCEL_ID = P + "file_upload_cancel";
	private final static String ADD_RELATED_CONTENT_ITEM_ID = P + "add_related_item";
	private final static String REMOVE_RELATED_CONTENT_ITEM_ID = P + "remove_related_item";
	private final static String RELATED_CONTENT_ITEMS_CANCEL_ID = P + "related_items_cancel";
	
	private final static String TASKBAR_ID = P + "taskbar";

	private final static String BUTTON_SELECTOR_ID = P + "button_selector";
	private final static String EDITOR_SELECTOR_ID = P + "editor_selector";
	private final static String ARTICLE_EDITOR_ID = P + "article_editor";
	private final static String CATEGORY_EDITOR_ID = P + "category_editor";
	private final static String RELATED_CONTENT_ITEMS_EDITOR_ID = P + "related_items_editor";

	private final static String RELATED_CONTENT_ITEMS_LIST_ID = P + "related_items_list";

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
		
		String ref = ARTICLE_ITEM_BEAN_ID + ".";

		WFContainer mainContainer = new WFContainer();
		mainContainer.setId(ARTICLE_EDITOR_ID);

		WFErrorMessages em = new WFErrorMessages();
		em.addErrorMessage(HEADLINE_ID);
		em.addErrorMessage(TEASER_ID);
		em.addErrorMessage(PUBLISHED_FROM_DATE_ID);
		em.addErrorMessage(PUBLISHED_TO_DATE_ID);
		em.addErrorMessage(SAVE_ID);
		
		mainContainer.add(em);

		HtmlPanelGrid p = WFPanelUtil.getFormPanel(2);		
		p.getChildren().add(WFUtil.getText("Headline:"));		
		p.getChildren().add(WFUtil.getText("Language:"));
		HtmlInputText headlineInput = WFUtil.getInputText(HEADLINE_ID, ref + "headline");		
		headlineInput.setSize(40);
		p.getChildren().add(headlineInput);		
		HtmlSelectOneMenu localeMenu = WFUtil.getSelectOneMenu(LOCALE_ID, ref + "allLocales", ref + "pendingLocaleId");
		localeMenu.setOnchange("document.forms[0].submit();");
		p.getChildren().add(localeMenu);		
		p.getChildren().add(WFUtil.getText("Teaser:"));		
		p.getChildren().add(WFUtil.getText("Author:"));		
		HtmlInputTextarea teaserArea = WFUtil.getTextArea(TEASER_ID, ref + "teaser", "440px", "30px");
		p.getChildren().add(teaserArea);		
		HtmlInputText authorInput = WFUtil.getInputText(AUTHOR_ID, ref + "author");
		authorInput.setSize(22);
		p.getChildren().add(authorInput);		
		p.getChildren().add(WFUtil.getText("Body:"));		
		p.getChildren().add(WFUtil.getText("Images:"));		
		HtmlInputTextarea bodyArea = WFUtil.getTextArea(BODY_ID, ref + "body", "460px", "400px");
		HtmlCommandButton editButton = WFUtil.getButton("test", "Edit");
		editButton.setOnclick("wurl='htmlarea/webface/htmledit.jsp?" + PREVIEW_ARTICLE_ITEM_ID + 
					"='+this.tabindex;window.open(wurl,'Edit','height=450,width=600,resizable=yes,status=no,toolbar=no,menubar=no,location=no,scrollbars=no');return false;");
		p.getChildren().add(WFUtil.group(WFUtil.group(bodyArea, WFUtil.getBreak()), editButton));
		WFContainer imageContainer = new WFContainer();		
		imageContainer.add(WFUtil.getButton(ADD_IMAGE_ID, "Add image", this));
		imageContainer.add(WFUtil.getBreak());
		imageContainer.add(getImageList());
		p.getChildren().add(imageContainer);
		p.getChildren().add(WFUtil.getText("Source:"));		
		p.getChildren().add(WFUtil.getText("Main category:"));		
		HtmlInputTextarea sourceArea = WFUtil.getTextArea(SOURCE_ID, ref + "source", "440px", "30px");
		p.getChildren().add(sourceArea);		
		HtmlSelectOneMenu mainCategoryMenu = WFUtil.getSelectOneMenu(MAIN_CATEGORY_ID, ref + "categories", ref + "mainCategoryId");
		p.getChildren().add(mainCategoryMenu);		

		mainContainer.add(p);
		mainContainer.add(WFUtil.getBreak());
		
		p = WFPanelUtil.getPlainFormPanel(1);
		p.getChildren().add(WFUtil.group(WFUtil.getHeaderText("Created:"), WFUtil.getText(" 4/20/04 3:04 PM")));
		p.getChildren().add(WFUtil.getText(" "));
		p.getChildren().add(WFUtil.group(WFUtil.getHeaderText("Status:"), WFUtil.getTextVB(ref + "status")));
		p.getChildren().add(WFUtil.getText(" "));
		p.getChildren().add(WFUtil.group(WFUtil.getHeaderText("Current version:"), WFUtil.getText(" 1.5")));
		
		mainContainer.add(p);
		mainContainer.add(WFUtil.getBreak());
		
		p = WFPanelUtil.getFormPanel(2);		
		p.getChildren().add(WFUtil.getText("Comment:"));		
		p.getChildren().add(WFUtil.getText("Attachments:"));		
		HtmlInputTextarea commentArea = WFUtil.getTextArea(COMMENT_ID, ref + "comment", "400px", "60px");		
		p.getChildren().add(commentArea);
		WFContainer attachmentContainer = new WFContainer();		
		attachmentContainer.add(WFUtil.getButton(ADD_ATTACHMENT_ID, "Add attachment", this));
		attachmentContainer.add(WFUtil.getBreak());
		attachmentContainer.add(getAttachmentList());
		p.getChildren().add(attachmentContainer);
		
		mainContainer.add(p);
		
		p = WFPanelUtil.getFormPanel(1);
		p.getChildren().add(WFUtil.getText("Related content items:"));
		WFContainer contentItemContainer = new WFContainer();		
		contentItemContainer.add(WFUtil.getButton(ADD_RELATED_CONTENT_ITEM_ID, "Add content item", this));
		contentItemContainer.add(WFUtil.getBreak());
		contentItemContainer.add(getRelatedContentItemsList());
		p.getChildren().add(contentItemContainer);
		p.getChildren().add(WFUtil.getText("Publishing date:"));
		WFDateInput publishedFromInput = WFUtil.getDateInput(PUBLISHED_FROM_DATE_ID, ref + "case.publishedFromDate");
		publishedFromInput.setShowTime(true);
		p.getChildren().add(publishedFromInput);
		p.getChildren().add(WFUtil.getText("Expiration date:"));
		WFDateInput publishedToInput = WFUtil.getDateInput(PUBLISHED_TO_DATE_ID, ref + "case.publishedToDate");
		publishedToInput.setShowTime(true);
		p.getChildren().add(publishedToInput);
		
		mainContainer.add(p);
		mainContainer.add(WFUtil.getBreak());

		p = WFPanelUtil.getPlainFormPanel(1);
		HtmlCommandButton editCategoriesButton = WFUtil.getButton(EDIT_CATEGORIES_ID, "Edit categories", this);
		p.getChildren().add(editCategoriesButton);
		p.getChildren().add(WFUtil.getBreak());
		WFComponentSelector cs = new WFComponentSelector();
		cs.setId(BUTTON_SELECTOR_ID);
		cs.setDividerText(" ");
		HtmlCommandButton saveButton = WFUtil.getButton(SAVE_ID, "Save", this);
		cs.add(saveButton);
		cs.add(WFUtil.getButton(FOR_REVIEW_ID, "For review", this));
		cs.add(WFUtil.getButton(PUBLISH_ID, "Publish", this));
		cs.add(WFUtil.getButton(REWRITE_ID, "Rewrite", this));
		cs.add(WFUtil.getButton(REJECT_ID, "Reject", this));
		cs.add(WFUtil.getButton(DELETE_ID, "Delete", this));
		cs.add(WFUtil.getButton(CANCEL_ID, "Cancel", this));
		cs.setSelectedId(CANCEL_ID, true);
		p.getChildren().add(cs);
		
		mainContainer.add(p);
		
		WFComponentSelector editorSelector = new WFComponentSelector();
		editorSelector.setId(EDITOR_SELECTOR_ID);
		editorSelector.add(mainContainer);
		editorSelector.add(getCategoryEditContainer());
//		FileUploadForm f = new FileUploadForm(this, FILE_UPLOAD_ID, FILE_UPLOAD_CANCEL_ID);
		FileUploadForm f = new FileUploadForm();
		f.setId(FILE_UPLOAD_FORM_ID);
		editorSelector.add(f);
		editorSelector.add(getRelatedContentItemsContainer());
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
	 * Returns a list with attachment links for the article.
	 */
	private UIComponent getAttachmentList() {
		String var = "article_attachment";
		HtmlDataTable t = new HtmlDataTable();
		t.setVar(var);
		WFUtil.setValueBinding(t, "value", ARTICLE_ITEM_BEAN_ID + ".attachments");
		t.setColumnClasses("wf_valign_middle");
		
		UIColumn col = new UIColumn();
		HtmlCommandLink link = new HtmlCommandLink();
		WFUtil.setValueBinding(link, "value", var + ".imageURI");
		col.getChildren().add(link);
		t.getChildren().add(col);
		
		col = new UIColumn();
		HtmlCommandButton removeButton = WFUtil.getButton(REMOVE_ATTACHMENT_ID, "Remove", this);
		removeButton.setOnclick("return confirm('Are you sure you want to remove the attachment?');return false;");
		WFUtil.addParameterVB(removeButton, "attachment_no", var + ".orderNoString");
		col.getChildren().add(removeButton);
		t.getChildren().add(col);
		
		return t;
	}

	/*
	 * Returns a list with realted content item links for the article.
	 */
	private UIComponent getRelatedContentItemsList() {
		String var = "article_related_items";
		HtmlDataTable t = new HtmlDataTable();
		t.setVar(var);
		WFUtil.setValueBinding(t, "value", ARTICLE_ITEM_BEAN_ID + ".relatedContentItems");
		t.setColumnClasses("wf_valign_middle");
		
		UIColumn col = new UIColumn();
		HtmlOutputLink link = new HtmlOutputLink();
		WFUtil.setValueBinding(link, "tabindex", var + ".value");
		link.setOnclick("wurl='previewarticle.jsf?" + PREVIEW_ARTICLE_ITEM_ID + 
					"='+this.tabindex;window.open(wurl,'Preview','height=300,width=500,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes  ');return false;");
		HtmlOutputText txt = new HtmlOutputText();
		WFUtil.setValueBinding(txt, "value", var + ".name");
		link.getChildren().add(txt);
		col.getChildren().add(link);
		t.getChildren().add(col);
		
		col = new UIColumn();
		HtmlCommandButton removeButton = WFUtil.getButton(REMOVE_RELATED_CONTENT_ITEM_ID, "Remove", this);
		WFUtil.addParameterVB(removeButton, "item_id", var + ".value");
		removeButton.setOnclick("return confirm('Are you sure you want to remove the reference to the content item?');return false;");
		WFUtil.addParameterVB(removeButton, "related_item_no", var + ".orderNoString");
		col.getChildren().add(removeButton);
		t.getChildren().add(col);
		
		return t;
	}
	
	/*
	 * Returns container with form for editing categories.
	 */
	private UIComponent getCategoryEditContainer() {
		String ref = ARTICLE_ITEM_BEAN_ID + ".";

		HtmlPanelGrid p = WFPanelUtil.getFormPanel(3);
		p.setId(CATEGORY_EDITOR_ID);
		p.getChildren().add(WFUtil.getText("Available categories:"));		
		p.getChildren().add(WFUtil.getText(" "));		
		p.getChildren().add(WFUtil.getText("Categories for this article:"));
		WFContainer c = new WFContainer();
		HtmlSelectManyListbox availableCategories = WFUtil.getSelectManyListbox(AVAILABLE_CATEGORIES_ID,
				ref + "availableCategories", ref + "selectedAvailableCategories");
		availableCategories.setStyle("width:200px;height:160px;");
		availableCategories.setConverter(new IntegerConverter());
		c.add(availableCategories);
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getButton(CATEGORY_BACK_ID, "Back", this));
		p.getChildren().add(c);		
		c = new WFContainer();
		c.add(WFUtil.getBreak());
		c.add(WFUtil.getButton(ADD_CATEGORIES_ID, ">", this));
		c.add(WFUtil.getBreak(2));
		c.add(WFUtil.getButton(SUB_CATEGORIES_ID, "<", this));
		p.getChildren().add(c);		
		HtmlSelectManyListbox articleCategories = WFUtil.getSelectManyListbox(ARTICLE_CATEGORIES_ID, 
				ref + "categories", ref + "selectedCategories");
		articleCategories.setStyle("width:200px;height:160px;");
		articleCategories.setConverter(new IntegerConverter());
		p.getChildren().add(articleCategories);
				
		return p;
	}
	
	/*
	 * Returns container with form for selecting related content items.
	 */
	private UIComponent getRelatedContentItemsContainer() {
		WFContainer c = new WFContainer();
		c.setId(RELATED_CONTENT_ITEMS_EDITOR_ID);
		WFUtil.invoke(RELATED_ITEMS_LIST_BEAN_ID, "setCaseLinkListener", this, ActionListener.class);
		WFList l = new WFList(RELATED_ITEMS_LIST_BEAN_ID, 0, 10);
		l.setId(RELATED_CONTENT_ITEMS_LIST_ID);
		c.add(l);
		c.add(WFUtil.getBreak());
		c.add(new WFPlainOutputText("&nbsp;&nbsp;&nbsp;"));
		c.add(WFUtil.getButton(RELATED_CONTENT_ITEMS_CANCEL_ID, "Cancel", this));
		return c;
	}
	
	/**
	 * Updates the buttons in edit mode depending on the status of the current article.
	 */
	public void updateEditButtons() {
		WFComponentSelector cs = (WFComponentSelector) findComponent(BUTTON_SELECTOR_ID);
		String s = WFUtil.getStringValue(ARTICLE_ITEM_BEAN_ID, "status");

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
		String ref = ARTICLE_ITEM_BEAN_ID + ".";

		HtmlPanelGrid p = WFPanelUtil.getPlainFormPanel(1);
		p.getChildren().add(WFUtil.getHeaderTextVB(ref + "headline"));
		p.getChildren().add(WFUtil.getText(" "));
		p.getChildren().add(WFUtil.getTextVB(ref + "teaser"));
		p.getChildren().add(WFUtil.getText(" "));
		WFPlainOutputText bodyText = new WFPlainOutputText();
		WFUtil.setValueBinding(bodyText, "value", ref + "body");
		p.getChildren().add(bodyText);
		p.getChildren().add(WFUtil.getBreak());		
		p.getChildren().add(new WFPlainOutputText("<hr/>"));		
		p.getChildren().add(WFUtil.group(WFUtil.getHeaderText("Author: "), WFUtil.getTextVB(ref + "author")));
		p.getChildren().add(WFUtil.getText(" "));
		p.getChildren().add(WFUtil.group(WFUtil.getHeaderText("Created: "), WFUtil.getText("4/20/04 3:04 PM")));
		p.getChildren().add(WFUtil.getText(" "));
		p.getChildren().add(WFUtil.group(WFUtil.getHeaderText("Status: "), WFUtil.getTextVB(ref + "status")));
		p.getChildren().add(WFUtil.getText(" "));
		HtmlOutputText t = WFUtil.getTextVB(ref + "categoryNames");
		t.setConverter(new WFCommaSeparatedListConverter());		
		p.getChildren().add(WFUtil.group(WFUtil.getHeaderText("Categories: "), t));
		p.getChildren().add(WFUtil.getText(" "));
		p.getChildren().add(WFUtil.group(WFUtil.getHeaderText("Current version: "), WFUtil.getText("1.5")));
		p.getChildren().add(WFUtil.getText(" "));
		p.getChildren().add(WFUtil.group(WFUtil.getHeaderText("Comment: "), WFUtil.getTextVB(ref + "comment")));
		p.getChildren().add(WFUtil.getText(" "));
		p.getChildren().add(WFUtil.group(WFUtil.getHeaderText("Source: "), WFUtil.getTextVB(ref + "source")));
		p.getChildren().add(WFUtil.getText(" "));
				
		return p;
	}
	
	/*
	 * Creates a message container for the article.
	 */
	private UIComponent getMessageContainer() {
		WFContainer c = new WFContainer();
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
		ArticleBlock ab = (ArticleBlock) event.getComponent().getParent().getParent().getParent().findComponent(ARTICLE_BLOCK_ID);
		if (id.equals(SAVE_ID)) {
			ab.storeArticle();
		} else if (id.equals(FOR_REVIEW_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setRequestedStatus", ContentItemCaseBean.STATUS_READY_FOR_REVIEW);
			ab.storeArticle();
		} else if (id.equals(PUBLISH_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setRequestedStatus", ContentItemCaseBean.STATUS_PUBLISHED);
			ab.storeArticle();
		} else if (id.equals(REWRITE_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setRequestedStatus", ContentItemCaseBean.STATUS_REWRITE);
			ab.storeArticle();
		} else if (id.equals(REJECT_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setRequestedStatus", ContentItemCaseBean.STATUS_DELETED);
			ab.storeArticle();
		} else if (id.equals(DELETE_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "setRequestedStatus", ContentItemCaseBean.STATUS_DELETED);
			ab.storeArticle();
		} else if (id.equals(EDIT_CATEGORIES_ID)) {
			ab.setEditView(CATEGORY_EDITOR_ID);
		} else if (id.equals(ADD_CATEGORIES_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "addSelectedCategories");
		} else if (id.equals(SUB_CATEGORIES_ID)) {
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "removeSelectedCategories");
		} else if (id.equals(CATEGORY_BACK_ID)) {
			ab.setEditView(ARTICLE_EDITOR_ID);
		} else if (id.equals(ADD_RELATED_CONTENT_ITEM_ID)) {
			ab.setEditView(RELATED_CONTENT_ITEMS_EDITOR_ID);
		} else if (id.equals(ADD_IMAGE_ID)) {
			ab.setEditView(FILE_UPLOAD_FORM_ID);
		} else if (id.equals(FILE_UPLOAD_CANCEL_ID)) {
			ab.setEditView(ARTICLE_EDITOR_ID);
		} else if (id.equals(FILE_UPLOAD_ID)) {
			ab.setEditView(ARTICLE_EDITOR_ID);
		} else if (id.equals(REMOVE_IMAGE_ID)) {
			int imageNo = WFUtil.getIntParameter(event.getComponent(), "image_no");
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "removeImage", new Integer(imageNo));
		} else if (id.equals(CaseListBean.CASE_ID)){
			String itemId = WFUtil.getParameter(event.getComponent(), "id");
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "addRelatedContentItem", new Integer(itemId));
			ab.setEditView(ARTICLE_EDITOR_ID);
		} else if (id.equals(RELATED_CONTENT_ITEMS_CANCEL_ID)) {
			ab.setEditView(ARTICLE_EDITOR_ID);
		} else if (id.equals(REMOVE_RELATED_CONTENT_ITEM_ID)) {
			int itemId = WFUtil.getIntParameter(event.getComponent(), "item_id");
			WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "removeRelatedContentItem", new Integer(itemId));
		}
	}

	/**
	 * Sets the editor view for this article block.
	 *
	 */
	public void setEditView(String s) {
		WFComponentSelector cs = (WFComponentSelector) findComponent(EDITOR_SELECTOR_ID);
		cs.setSelectedId(ARTICLE_EDITOR_ID, s.equals(ARTICLE_EDITOR_ID));
		cs.setSelectedId(CATEGORY_EDITOR_ID, s.equals(CATEGORY_EDITOR_ID));
		cs.setSelectedId(FILE_UPLOAD_FORM_ID, s.equals(FILE_UPLOAD_FORM_ID));
		cs.setSelectedId(RELATED_CONTENT_ITEMS_EDITOR_ID, s.equals(RELATED_CONTENT_ITEMS_EDITOR_ID));
	}
	
	/**
	 * Stores the current article. 
	 */
	public void storeArticle() {
		boolean storeOk = ((Boolean) WFUtil.invoke(ARTICLE_ITEM_BEAN_ID, "store")).booleanValue();
		if (!storeOk) {
			List errorKeys = (List) WFUtil.getValue(ARTICLE_ITEM_BEAN_ID, "errorKeys");
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
