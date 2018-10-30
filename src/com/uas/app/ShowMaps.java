package com.uas.app;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
 
public class ShowMaps extends Activity {
    /** Called when the activity is first created. */
    private WebView webView;
    int id;
    DBHelper dbhelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_maps);
        //Mengeset webView sebagai layout utama
        webView = (WebView) findViewById(R.id.web_view);
        //memanggil method openBrowser #youdontsay
        openBrowser();
        Intent i_get = getIntent();
		id = i_get.getIntExtra("id_for_detail", 0);
		
        dbhelper = new DBHelper(this);
    }
 
    //karena ada method static yang diakses oleh method non-static (openBrowser)
    @SuppressWarnings("static-access")
    private void openBrowser()
    {
		ArrayList<Object> row = dbhelper.getDetail(id);
		String Kabupaten = row.get(5).toString();
        //memanggil URL, /// berguna untuk menandakan bahwa file yang diakses
        //sedangkan android_asset merefers ke folder assets yang ada di
        //proyek androidmu
		Log.e("cek", Kabupaten);
        String url = "file:///android_asset/maps.html";
 
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
}