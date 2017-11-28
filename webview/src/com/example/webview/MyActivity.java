package com.example.webview;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MyActivity extends Activity  {
    WebView webview;
    EditText searchEditText;
    ImageButton searchIButton;

    /**
     * Called when the activity is first created.
     */
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        webview=(WebView)findViewById(R.id.webView);
        WebSettings ws=webview.getSettings();
        ws.setJavaScriptEnabled(true);
        webview.setWebViewClient(new MyBrowser());
        webview.loadUrl("https://www.google.com");

        searchEditText = (EditText) findViewById(R.id.edit_text_search);
        searchIButton = (ImageButton) findViewById(R.id.button_search);

        searchIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=searchEditText.getText().toString();
                if (!url.contains("https://"))url+="https://";
                    webview.loadUrl(searchEditText.getText() + "");
            }
        });
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(true);


    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
           // super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }
    }

    @Override

    public boolean onKeyDown(int keycode,KeyEvent event){
           if(keycode==(KeyEvent.KEYCODE_BACK)&&this.webview.canGoBack())this.webview.goBack();
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_forward:
                if(webview.canGoForward())webview.goForward();
                break;
            case R.id.menu_back:
                if(webview.canGoBack())webview.goBack();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
