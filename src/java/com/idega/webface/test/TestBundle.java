package com.idega.webface.test;

import java.util.ListResourceBundle;

/**
 * @author al
 */
public class TestBundle extends ListResourceBundle {

	static final Object[][] contents = {
		{"name", "Name"},
		{"author", "Phone"},
		{"company", "Company"}
	};

	public Object[][] getContents() {
	  return contents;
	}
}
