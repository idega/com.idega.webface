<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="1.2" 
	xmlns:f="http://java.sun.com/jsf/core" 	
	xmlns:h="http://java.sun.com/jsf/html" 	
	xmlns:jsp="http://java.sun.com/JSP/Page" 	
	xmlns:c="http://xmlns.idega.com/com.idega.core"
	xmlns:wf="http://xmlns.idega.com/com.idega.webface"
	xmlns:ws="http://xmlns.idega.com/com.idega.workspace"
	xmlns:co="http://xmlns.idega.com/com.idega.content">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
    	<ws:page layout="ws_layout_compact" id="p1" onload="Init()" 
    			javascripturls="/idegaweb/bundles/com.idega.webface.bundle/resources/xinha/popups/popup.js,
    							/idegaweb/bundles/com.idega.webface.bundle/resources/xinha/plugins/IWSelectdocument/popups/IWSelectDocumentRedir.js">
               <h:form id="uploadForm" enctype="multipart/form-data">
               <wf:htmlarealinkcreator id="lc1" externalTabClass="com.idega.content.presentation.HTMLAreaDocumentLinkCreator"/>

               <!--
    		<ws:page id="listdocuments1" javascripturls="../idegaweb/bundles/com.idega.webface.bundle/resources/htmlarea/popups/popup.js">
               <h:form id="uploadForm" enctype="multipart/form-data">
				<co:ContentViewer showPermissionTab="false" onFileClickEvent="__dlg_close(NAME)" showUploadComponent="false" rootPath="/files" startPath="/files" id="gt"/>
			</h:form>
		</ws:page>
				-->

			</h:form>
		</ws:page>
    </f:view>
</jsp:root>