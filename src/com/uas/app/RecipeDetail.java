package com.uas.app;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class RecipeDetail extends Activity {
	
	TextView txtRecipeName, txtPrepTime, txtCookTime, txtServes, txtSummary;
	ImageView imgPreviewDetail;
	ProgressBar prgLoading;
	ScrollView sclDetail;
	private WebView webView;
	DBHelper dbhelper;
	ArrayList<Object> data;
	int id;
	String Nama_wisata,Image,Lokasi,Informasi,Deskripsi,Kabupaten;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recipe_detail);
		
//		Button BTN1=(Button)findViewById(R.id.loc);
//			BTN1.setOnClickListener(new View.OnClickListener() {
//			
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent intent=new Intent(RecipeDetail.this,ShowMaps.class);
//				startActivity(intent);
//				
//			}
//		});
		Intent i_get = getIntent();
		id = i_get.getIntExtra("id_for_detail", 0);
		
		
		dbhelper = new DBHelper(this);
		
		txtRecipeName = (TextView) findViewById(R.id.txtRecipeName);
		txtPrepTime = (TextView) findViewById(R.id.txtPrepTime);
		txtCookTime = (TextView) findViewById(R.id.txtCookTime);
		txtServes = (TextView) findViewById(R.id.txtServes);
		txtSummary = (TextView) findViewById(R.id.txtSummary);
		imgPreviewDetail = (ImageView) findViewById(R.id.imgPreviewDetail);
		prgLoading = (ProgressBar) findViewById(R.id.prgLoading);
		sclDetail = (ScrollView) findViewById(R.id.sclDetail);
		
		try{
			dbhelper.openDataBase();
		}catch(SQLException sqle){
			throw sqle;
		}
		webView = (WebView) findViewById(R.id.web_view);
		new getDetailTask().execute();
		
	}
	
	/** this class is used to handle thread */
	public class getDetailTask extends AsyncTask<Void, Void, Void>{
    	
    	
    	@Override
		 protected void onPreExecute() {
		  // TODO Auto-generated method stub
    		
    	}
    	
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			getDetailFromDatabase();
			 openBrowser();
			return null;
		}
    	
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			prgLoading.setVisibility(8);
			sclDetail.setVisibility(0);
			showDetail();
			dbhelper.close();
		}
    }
	
	/**
     * this code is used to get data from database and store them
     * to attributes
     */
	public void getDetailFromDatabase(){
    		ArrayList<Object> row = dbhelper.getDetail(id);
    		
    		Nama_wisata = row.get(0).toString();
    		Image = row.get(1).toString();
    		Lokasi = row.get(2).toString();
    		Informasi = row.get(3).toString();
    		Deskripsi = row.get(4).toString();
    		Kabupaten = row.get(5).toString();
//    		Ingredients = row.get(6).toString();
//    		Directions = row.get(7).toString();
    	}
	
	/**
	 * then set those values of attributes to the views
	 */
	public void showDetail(){
		txtRecipeName.setText(Nama_wisata);
		int imagePreview = getResources().getIdentifier(Image, "drawable", getPackageName());
		imgPreviewDetail.setImageResource(imagePreview);
		txtPrepTime.setText("Lokasi : "+Lokasi);
		txtCookTime.setText("HTM : "+Informasi);
		txtServes.setText("Jam Buka : "+Kabupaten);
		txtSummary.setText("Deskripsi :"+Deskripsi);
//		txtIngredients.setText(Html.fromHtml(Ingredients));
//		txtDirections.setText(Html.fromHtml(Directions));
	}
    private void openBrowser()
    {
		ArrayList<Object> row = dbhelper.getDetail(id);
		String Nama_wisata = row.get(0).toString();
        //memanggil URL, /// berguna untuk menandakan bahwa file yang diakses
        //sedangkan android_asset merefers ke folder assets yang ada di
        //proyek androidmu
		Log.e("cek", Kabupaten);
        String url = "file:///android_asset/"+Nama_wisata+".html";
 
        //Menginstantiasi webchrome client baru, buat gaya-gayaan aja B)
        WebChromeClient wcc = new WebChromeClient();
        webView.setWebChromeClient(wcc);
        //mengaktifkan javascript, kalo nggak aktif... TRY IT!
        webView.getSettings().setJavaScriptEnabled(true);
        //mengaktifkan built in zoom controls
        webView.getSettings().setBuiltInZoomControls(true);
        //bagian ini akan dijelaskan
//        webView.enablePlatformNotifications();
        //meload URL
        webView.loadUrl(url);
    }
	@Override
	public void onConfigurationChanged(final Configuration newConfig)
	{
	    // Ignore orientation change to keep activity from restarting
	    super.onConfigurationChanged(newConfig);
	}
}
