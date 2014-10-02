package ganzi.dev.parsingtest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;




//import org.apache.commons.logging.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.util.*;

public class MainActivityView extends ActionBarActivity {
	String xml;
	TextView txtResult;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		setContentView(R.layout.activity_main_view);
		txtResult = (TextView)findViewById(R.id.txtResult);
		
		StringBuffer sBuffer = new StringBuffer();
		try{
			String urlAddr = "http://192.168.1.172:8099/Main.xml";
			URL url = new URL(urlAddr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			if(conn !=null){
				conn.setConnectTimeout(20000);				
				conn.setUseCaches(false);
				
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					InputStreamReader isr = new InputStreamReader(conn.getInputStream());
					BufferedReader br = new BufferedReader(isr);

					while (true) {
						String line = br.readLine();
						if (line == null) {
							break;
						}
						sBuffer.append(line);
					}
					br.close();
				}
					conn.disconnect();
			
		}
		xml = sBuffer.toString();
	} catch(Exception e){
		Log.e("다운로드 중 에러 발생", e.getMessage());
		android.util.Log.e("다운로드 중 에러 발생", e.getMessage());
		Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
	}
		parse();
}
	

	private void parse() {
		// TODO Auto-generated method stub
		try {
			   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			   DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			   InputStream is = new ByteArrayInputStream(xml.getBytes());
			   Document doc = documentBuilder.parse(is);
			   doc.getDocumentElement().normalize();
			   
			   NodeList headline_node_list = doc.getElementsByTagName("menu");
			   String name = "", best = "";
			   String text = "";
			   
			   for(int i = 0;i<headline_node_list.getLength(); i++) {
			    Node headline_node = headline_node_list.item(i);
			    if(headline_node.getNodeType() == Node.ELEMENT_NODE) {
			     Element element = (Element) headline_node;
			     NodeList node_list = element.getElementsByTagName("name");
			     Node node = node_list.item(0);
			     name = node.getTextContent();
			    }
			    if(headline_node.getNodeType() == Node.ELEMENT_NODE) {
			     Element element = (Element) headline_node;
			     NodeList node_list = element.getElementsByTagName("best");
			     Node node = node_list.item(0);
			     best = node.getTextContent();
			    }
			    text += (name + "    " + best +"\n");
			   }
			   
			   txtResult.setText(text.toString());

			   } catch(Exception e) {
			    Log.e("파싱 중 에러 발생", e.getMessage());
			   }
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
