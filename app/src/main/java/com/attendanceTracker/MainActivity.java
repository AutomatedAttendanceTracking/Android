package com.attendanceTracker;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.attendanceTracker.R;

import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.helloworld_menu:
                Toast.makeText(getApplicationContext(), "Hello World", Toast.LENGTH_LONG).show();
                return true;

            case R.id.helloase_menu:
                Toast.makeText(getApplicationContext(), "Hello Ase", Toast.LENGTH_LONG).show();
                return true;

            default: return false;
        }
    }


    public class NetWorkRequestTask extends AsyncTask<Void, Void, Boolean> {

        private final String url;
        private String response;

        public NetWorkRequestTask(String url) {
            this.url = url;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                //get rawAnswer from server
                String rawAnswer = new ClientResource(url).get().getText();
                String content = "";


                //Create a DocumentBuilder
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                //Create a Document from a file or stream
                StringBuilder stringBuilder = new StringBuilder();
                InputStream is = new ByteArrayInputStream(rawAnswer.getBytes("UTF-8"));
                Document doc = builder.parse(is);

                //Build XPath
                XPath xpath = XPathFactory.newInstance().newXPath();

                //Prepare Path expression and evaluate it
                String expression = "*/greeting";
                NodeList nodeList = (NodeList) xpath.compile(expression).evaluate(doc, XPathConstants.NODESET);

                //Iterate over NodeList
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    Element eElement = (Element) node;

                    stringBuilder.append("E-Mail: " + (String) eElement.getElementsByTagName("email").item(0).getTextContent() + "\r\n");
                    stringBuilder.append("Content: " + (String) eElement.getElementsByTagName("content").item(0).getTextContent() + "\n\n");

                    //content =+ "E-Mail: \n" + (String) eElement.getElementsByTagName("email").item(0).getTextContent()+"\n";
                    //content =+ "Content: \n" + (String) eElement.getElementsByTagName("content").item(0).getTextContent()+"\n";
                    //content += "Text: "+nodeList.item(i).getNodeValue()+"\n";
                }
                content = stringBuilder.toString();
                this.response = content;
                Log.d("success", this.response);
                return true;
            } catch (Exception exc) {
                Log.e("error", exc.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Failed to get server response!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
