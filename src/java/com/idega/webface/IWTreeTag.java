package com.idega.webface;

import org.apache.myfaces.custom.tree2.TreeTag;

public class IWTreeTag extends TreeTag{
//public class IWTreeTag{
    public String getRendererType()
    {
        return "com.idega.webface.IWTree";
    }
}
