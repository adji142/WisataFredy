package com.uas.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Wisata2 extends Activity{
	String Kab = "Air";
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wisata2);
		Button Alam=(Button)findViewById(R.id.button1);
		Button Now=(Button)findViewById(R.id.button2);
		Button Air=(Button)findViewById(R.id.button3);
		Button Sejarah=(Button)findViewById(R.id.button4);
		
		Alam.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Wisata2.this,Alam.class);
				
				startActivity(intent);
				
			}
		});
		
		Now.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Wisata2.this,Now.class);
				
				startActivity(intent);
				
			}
		});
		
		Air.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Wisata2.this,Air.class);
				
				startActivity(intent);
				
			}
		});
		
		Sejarah.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Wisata2.this,Sejarah.class);
				
				startActivity(intent);
				
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_wisata2, menu);
		return true;
	}
}
