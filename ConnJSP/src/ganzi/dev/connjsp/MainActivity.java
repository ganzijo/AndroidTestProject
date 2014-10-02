package ganzi.dev.connjsp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends ActionBarActivity {
	 WebView wv;
	 private final Handler handler = new Handler();
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        loadWeb("http://192.168.1.172:8099/androidtest.jsp");
	      } //본인의 PC아이피와 포트번호를 적으시고 jsp파일 적으시면 됩니다.
	      
	      
	      public class AndroidHandler {
	    	  @JavascriptInterface
	          public void setMessage(final String argv) {
	              handler.post(new Runnable() {
	              public void run() {
	                  @SuppressWarnings("unused")
					String msg = argv;
	                   if(argv.equals("exit")) {
	                   Append();       
	                  } 
	              }
	          });
	              
	      }
	  }
	      
	      
	      public void Append() { 
	       AlertDialog.Builder builder = new AlertDialog.Builder(this);      
	    builder.setMessage("종료하시겠습니까?")      
	    .setCancelable(false)      
	    .setPositiveButton("종료", new DialogInterface.OnClickListener() {      
	        public void onClick(DialogInterface dialog, int id) {      
	            
	               finish();
	        }      
	    })      
	    
	        
	    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {      
	        public void onClick(DialogInterface dialog, int id) {      
	              
	                dialog.cancel();      
	        }      
	    })      
	    .show();      
	      }
	      public void onConfigurationChanged(Configuration newConfig) {
	       super.onConfigurationChanged(newConfig);
	       
	       if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
	       {}
	        if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_NO)
	        {}
	      }

	      @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
		private void loadWeb(String url) {
	       final Context myApp = this;
	       wv = (WebView)findViewById(R.id.webview);
	       wv.clearCache(true);
	       wv.getSettings().setJavaScriptEnabled(true);
	          wv.addJavascriptInterface(new AndroidHandler(), "hybrid");
	          wv.getSettings().setDomStorageEnabled(true);
	       wv.setWebChromeClient(new WebChromeClient() {
	        public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result)
	        {
	         new AlertDialog.Builder(myApp)
	         .setIcon(R.drawable.ic_launcher)
	         .setTitle("확인!")
	         .setMessage(message)
	         .setPositiveButton(android.R.string.ok, 
	           new AlertDialog.OnClickListener()
	         {
	          public void onClick(DialogInterface dialog, int which)
	          {
	           result.confirm();
	          }
	         })
	         .setCancelable(false)
	         .create()
	         .show();
	         
	         return true;
	        };
	        
	       });
	       
	       wv.loadUrl(url);
	       wv.setWebViewClient(new HelloWebViewClient());
	       wv.setHorizontalScrollBarEnabled(false);
	       wv.setVerticalScrollBarEnabled(false);
	      }
	     

	      
	      @Override
	      public boolean onKeyDown(int keyCode, KeyEvent event) {
	       if((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
	        wv.goBack();
	     
	        return true;
	        
	       }
	       return super.onKeyDown(keyCode, event);
	       
	       
	      }
	      
	      public class HelloWebViewClient extends WebViewClient {
	       public boolean shouldOverrideUrlLoding(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	       }
	      }
	  }