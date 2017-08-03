package com.example.a8308_04.guru1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 8308-04 on 2017-07-31.
 */
public class BasketActivity extends Activity {
    Button dutchPay;
    ListView listView;
    ArrayList<String> orderList=new ArrayList<String>();
    private ArrayAdapter<String> adapter=null;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket_list);

        //String message;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                orderList= null;
            } else {
                orderList=extras.getStringArrayList("order :");
            }
        } else {
            orderList.add((String) savedInstanceState.getSerializable("order :"));
        }

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        if(orderList!=null) {
            for (int i = 0; i < orderList.size(); i++) {
                adapter.add(orderList.get(i));
                //Toast.makeText(getApplicationContext(),orderList.toString(),Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"장바구니가 비었습니다♥",Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(getApplicationContext(),orderList.toString(),Toast.LENGTH_SHORT).show();



        dutchPay=(Button)findViewById(R.id.dutchPay);
        dutchPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(BasketActivity.this,OrderListActivity.class);
//                startActivity(intent);
//                finish();

                try {
                    JSONObject jo = new JSONObject();
                    JSONArray ja = new JSONArray();

//                    orderList
                    for(int i=0; i<orderList.size();i++) {
                        ja.put(i, orderList.get(i).toString());
                    }

                    jo.put("Order", ja);
                    Communication communication=new Communication();
                    communication.execute(jo);
                }

                catch(Exception e) {

                }
                Toast.makeText(getApplicationContext(),"주문완료! 맛있게드세요~",Toast.LENGTH_SHORT).show();

            }
        });

    }

    public class Communication extends AsyncTask<JSONObject, Integer, Long> {
        HttpURLConnection myConnection;
        Activity mainActivity;
        JSONObject resultJson; //여기에 sendHttpData 에서 처리후 서버에서 받아온 데이터 저장됨

        public String sendHTTPData(String urlpath, JSONObject json) {
            HttpURLConnection connection = null;
            try {
                URL url=new URL(urlpath);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                OutputStreamWriter streamWriter =
                        new OutputStreamWriter(connection.getOutputStream());
                streamWriter.write(json.toString());
                streamWriter.flush();
                StringBuilder stringBuilder = new StringBuilder();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(streamReader);
                    String response = null;
                    while ((response = bufferedReader.readLine()) != null) {
                        stringBuilder.append(response + "\n");
                    }
                    bufferedReader.close();

                    Log.d("test", stringBuilder.toString());
                    resultJson = new JSONObject(stringBuilder.toString());
                    return stringBuilder.toString();
                } else {
                    Log.e("test", connection.getResponseMessage());
                    return null;
                }
            } catch (Exception exception){
                Log.e("test", exception.toString());
                return null;
            } finally {
                if (connection != null){
                    connection.disconnect();
                }
            }
        }

        protected void onPreExecute() {
        }

        protected Long doInBackground(JSONObject ...obj) { //...는 여러개 라는 뜻
//            for (String str : strs) {
//                Log.d("", str);
//            }


            try {
                // Create URL
                sendHTTPData("http://117.17.93.58:3000/addOrderList", obj[0]); //본인의 서버 아이피, array형태로 한개씩=>이게 보내는 데이터

            }
            catch(Exception e) {
                Log.d("", e.toString());
            }
            finally {
            }
            return 100L;

        }

        protected void onProgressUpdate(Integer... values) {

        }

        protected void onPostExecute(Long result) {  //여기서 하고싶은거 하는거. ListAdpater.add(   ); 화면을 그리는거는 여기서 해야함!!!!!!!!!!!!

//            if(orderList!=null) {
//                for (int i = 0; i < orderList.size(); i++) {
//                    adapter.add(orderList.get(i));
//                    //Toast.makeText(getApplicationContext(),orderList.toString(),Toast.LENGTH_SHORT).show();
//                }
//            }
//            else
//            {
//                Toast.makeText(getApplicationContext(),"장바구니를 입력해주세요♥",Toast.LENGTH_SHORT).show();
//            }
//            haha

            try {

                String rslt;
                Log.d("=========", "--------");
                Log.d("=== This is result: ", rslt = resultJson.get("status").toString());
                rslt += "/";


                JSONArray arr2 = (JSONArray)resultJson.get("data");
                for(int i=0;i<arr2.length();i++){
                    JSONObject tempObj=arr2.getJSONObject(i);
                    tempObj.getString("Order");
//                    String tempStr=tempObj.getString("Name");
//                    String tempPwd=tempObj.getString("PhoneNumber");
                }
                //JSONObject obj2 = arr2.getJSONObject(0);
                //Log.d("", rslt += obj2.getString("one3"));


                Log.d("=========", "--------");


            }
            catch(Exception e) {

            }

//            tvResult.setText();
        }

        protected void onCancelled() {

            throw new RuntimeException("Stub!");

        }

    }

}
