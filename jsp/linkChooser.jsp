<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="1.2" 
	xmlns:f="http://java.sun.com/jsf/core" 	
	xmlns:h="http://java.sun.com/jsf/html" 	
	xmlns:jsp="http://java.sun.com/JSP/Page" 	
	xmlns:c="http://xmlns.idega.com/com.idega.core"
	xmlns:wf="http://xmlns.idega.com/com.idega.webface"
	xmlns:ws="http://xmlns.idega.com/com.idega.workspace"
	xmlns:co="http://xmlns.idega.com/com.idega.content">
	<jsp:directive.page contentType="text/html"/>
	<f:view>
		<ws:page layout="ws_layout_compact" id="linkChooserp1" onload="Init()"
			 javascripturls="/idegaweb/bundles/com.idega.webface.bundle/resources/xinha/popups/popup.js,
			 				/idegaweb/bundles/com.idega.webface.bundle/resources/xinha/plugins/IWSelectdocument/popups/IWSelectDocument.js">
			<h:form id="uploadForm" enctype="multipart/form-data">
				<wf:htmlarealinkcreator externalTabClass="com.idega.content.presentation.HTMLAreaDocumentLinkCreator"/>
			</h:form>
		</ws:page>
	</f:view>
</jsp:root>