<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="1.2" 
	xmlns:f="http://java.sun.com/jsf/core" 	
	xmlns:h="http://java.sun.com/jsf/html" 	
	xmlns:jsp="http://java.sun.com/JSP/Page" 	
	xmlns:c="http://xmlns.idega.com/com.idega.core"
	xmlns:wf="http://xmlns.idega.com/com.idega.webface"
	xmlns:ws="http://xmlns.idega.com/com.idega.workspace"
	xmlns:cmf="http://myfaces.sourceforge.net/tld/myfaces_ext_0_9.tld"
	xmlns:co="http://xmlns.idega.com/com.idega.content">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
    		<c:page id="p1" onload="Init()" javascripturls="../idegaweb/bundles/com.idega.webface.bundle/resources/htmlarea/popups/popup.js,../idegaweb/bundles/com.idega.webface.bundle/resources/htmlarea/plugins/Selectdocument/popups/IWSelectDocumentRedir.js">
               <h:form id="uploadForm" name="uploadForm" enctype="multipart/form-data">
               <wf:htmlareaimagechooser id="lc1" externalTabClass="com.idega.content.presentation.HTMLAreaDocumentImageChooser"/>

               <!--
               <wf:htmlarealinkcreator id="lc1" externalTabClass="com.idega.content.presentation.HTMLAreaDocumentLinkCreator"/>
    		<ws:page id="listdocuments1" javascripturls="../idegaweb/bundles/com.idega.webface.bundle/resources/htmlarea/popups/popup.js">
               <h:form id="uploadForm" name="uploadForm" enctype="multipart/form-data">
				<co:ContentViewer showPermissionTab="false" onFileClickEvent="__dlg_close(NAME)" showUploadComponent="false" rootPath="/files" startPath="/files" id="gt"/>
			</h:form>
		</ws:page>
				-->

			</h:form>
		</c:page>
    </f:view>
</jsp:root>