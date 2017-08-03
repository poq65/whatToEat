package com.example.a8308_04.guru1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by 8308-04 on 2017-08-01.
 */
public class OrderListActivity extends AppCompatActivity {

    private ListView m_ListView;
    List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> m_Adapter=null;
    TextView tvDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderlist);
        try {
            JSONObject jo = new JSONObject();

//            jo.put("Order", m_ListView.toString());

            Communication communication=new Communication();
            communication.execute(jo);
        }
        catch(Exception e) {

        }
        m_Adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listview2);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);

        // ListView 아이템 터치 시 이벤트 추가
        m_ListView.setOnItemClickListener(onClickListItem);


        //이부분을 서버에서 받아와야해요 postexcuete 부분에 이거 구현
//        m_Adapter.add("용우동");
//        m_Adapter.add("김가네");
//        m_Adapter.add("바푸리");
    }

    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
            Toast.makeText(getApplicationContext(), m_Adapter.getItem(arg2), Toast.LENGTH_SHORT).show();
        }
    };
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
                sendHTTPData("http://117.17.93.58:3000/getOrderList", obj[0]); //본인의 서버 아이피, array형태로 한개씩=>이게 보내는 데이터

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

//            listview.add(item);
//            for(arra){
//                listAdapter.add(sssssss);
//
//            }
//            haha


            try {
                tvDate=(TextView)findViewById(R.id.date);
                JSONObject orderObject = (JSONObject)resultJson.get("data");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
//                sdf.format(orderObject.get("createdAt"));


                  String strDate = (orderObject.get("createdAt").toString());
                  tvDate.setText(strDate);
                Log.d("111", strDate);
//                Date tmpDate = new Date(strDate);
//                Log.d("222", tmpDate.toString());
//                String tmpStrDate = sdf.format(tmpDate);
//                Log.d("333", tmpStrDate);

                JSONArray orderList = (JSONArray)orderObject.get("orderList");
                for(int i=0;i<orderList.length();i++){
                    String tempStr = orderList.getString(i);

                    m_Adapter.add(tempStr);

                }
//                String rslt;
//                Log.d("=========", "--------");
//                Log.d("=== This is result: ", rslt = resultJson.get("status").toString());
//
//                if (resultJson.get("status").toString().equals("success")) {
//                    //어뎁터.add
//                    Log.d("=========", "어뎁터추가");
//                    for(int i=0;i<resultJson.length();i++){
//                        m_Adapter.add(list.get(i).toString());
//                    }
//
//                    Log.d(" ", rslt = resultJson.get("status").toString());
//
//
//                } else {
//                    if (resultJson.get("status").toString().equals("Not success")) {
//                        Log.d(" ", rslt = resultJson.get("status").toString());
//                        Toast.makeText(getApplicationContext(), "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Log.d(" ", rslt = resultJson.get("status").toString());
//                        Toast.makeText(getApplicationContext(), "주문내역이 없습니다.", Toast.LENGTH_SHORT).show();
//                    }
//                }



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
