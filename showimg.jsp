<%@ page import="java.io.File,
                 java.io.InputStream,
                 java.io.FileInputStream,
                 java.io.OutputStream,
                 java.util.List,
                 com.idega.webface.test.*"
%>
<%@ page session="false"%>
<%
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
	response.setHeader("Expires", "01 Apr 1995 01:10:10 GMT");
	
    ArticleItemBean bean = (ArticleItemBean) request.getSession().getAttribute("articleItemBean");
    String imageNumberParameter = request.getParameter("image_number");
    int imageNumber = -1;
    if (imageNumberParameter != null) {
    	imageNumber = Integer.parseInt(imageNumberParameter);
    }
    byte[] bytes = null;
    String contentType = null;
    if (bean != null) {
    	List images = bean.getImages();
    	if (images != null && imageNumber >= 0) {
    		ContentItemFieldBean field = (ContentItemFieldBean) images.get(imageNumber);
    		bytes = field.getBinaryValue();
    		contentType = field.getFieldType();    		
    	}
    }
    if (bytes != null) {
        //String contentType = (String)application.getAttribute("fileupload_type");
        response.setContentType(contentType);
        response.getOutputStream().write(bytes);
    }
%>