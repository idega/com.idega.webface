package com.idega.webface.test;

import java.util.ListResourceBundle;

/**
 * @author al
 */
public class TestBundle_sv_SE extends ListResourceBundle {

	static final Object[][] contents = {
		{"name", "Namn"},
		{"author", "Författare"},
		{"company", "Företag"},
		{"edit_article", "Redigera artikel"},
		{"article_headline_empty", "Rubrik måste anges."},
		{"article_body_empty", "Artikeltext måste anges."}
	};

	public Object[][] getContents() {
	  return contents;
	}
}
