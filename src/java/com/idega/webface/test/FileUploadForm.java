/*
 * $Id: FileUploadForm.java,v 1.4 2004/06/23 13:23:43 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import java.io.Serializable;

//import javax.faces.component.UIComponent;
//import javax.faces.component.html.HtmlCommandButton;
//import javax.faces.component.html.HtmlPanelGroup;
//import javax.faces.event.ActionListener;

//import net.sourceforge.myfaces.custom.fileupload.HtmlInputFileUpload;

import com.idega.webface.WFContainer;
//import com.idega.webface.WFPanel;
//import com.idega.webface.WFUtil;

/**
 * Form for uploading files.   
 * <p>
 * Last modified: $Date: 2004/06/23 13:23:43 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.4 $
 */
public class FileUploadForm extends WFContainer implements ManagedContentBeans, Serializable {


//	private final static String P = "file_upload_block_"; // Id prefix

//	private final static String IMAGE_NAME_ID = P + "image_name";
	
//	private String _uploadButtonId = null;
//	private String _cancelButtonId = null;
//	private ActionListener _parentListener = null;
	
	/**
	 * Default contructor.
	 */
	public FileUploadForm() {
	}
	
	/**
	 * Constructs a file upload form with the specified parameters. 
	 */
//	public FileUploadForm(ActionListener parentListener, String uploadButtonId, String cancelButtonId) {
//		_parentListener = parentListener;
//		_uploadButtonId = uploadButtonId;
//		_cancelButtonId = cancelButtonId;

//		add(getFileUploadPanel());
//	}
	
	/*
	 * Creates a file upload panel.
	 */
//	private UIComponent getFileUploadPanel() {
		/*
		String ref = FILE_UPLOAD_BEAN_ID + ".";
		
		WFPanel p = new WFPanel();
		HtmlPanelGroup g = new HtmlPanelGroup();
		g.getChildren().add(WFUtil.getText("Image to upload: "));
		HtmlInputFileUpload f = new HtmlInputFileUpload();
		f.setValueBinding("value", WFUtil.createValueBinding("#{" + ref + "upFile}"));
		g.getChildren().add(f);
		p.set(g, 1, 1);
		p.setInputHeader("Image name:", 1, 2);
		p.setInput(WFUtil.getInputText(IMAGE_NAME_ID, ref + "name"), 1, 3);
		g = new HtmlPanelGroup();		
		HtmlCommandButton b = WFUtil.getButton(_uploadButtonId, "Upload", _parentListener);
		b.setAction(WFUtil.createMethodBinding("#{" + ref + "upload}", null));		
		g.getChildren().add(b);
		g.getChildren().add(WFUtil.getText(" "));
		b = WFUtil.getButton(_cancelButtonId, "Cancel", _parentListener);
		g.getChildren().add(b);
		p.set(g, 1, 4);

		return p;
		*/
//		return null;
//	}
	
}
