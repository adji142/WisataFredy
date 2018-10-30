package com.uas.app;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class AboutApp extends Activity{
	
	TextView txtAbout;
	
	/**
	 * the value of this attribute is about application description. 
	 * you can change it with your own. it also accept html tag.
	 */
	String aboutContent = "Template App is an application under Android platform " +
			"which can be used to create application about food recipes. <br />" +
			"it's designed and developed by : Name1 NIM, Name2 NIM, Name3 NIM .<br /><br />" +
			"&copy; 2016 Template App. All rights reserved.";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_app);
		
		txtAbout = (TextView) findViewById(R.id.txtAbout);
		txtAbout.setText(Html.fromHtml(aboutContent));
	}
}
