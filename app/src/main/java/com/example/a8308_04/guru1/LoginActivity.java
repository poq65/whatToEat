package com.example.a8308_04.guru1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 8308-04 on 2017-08-01.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText Name, Phone;
    private EditText etName;
    private EditText etPassword;
    private Button btnRegist, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Name = (EditText) findViewById(R.id.Name);
        Phone = (EditText) findViewById(R.id.etPhone);
        btnRegist = (Button) findViewById(R.id.btnRegist);
        btnLogin=(Button)findViewById(R.id.btnLogin);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RigisterActivity.class);

                // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // 동시에 사용 가능
                // intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)을 준다.
                startActivityForResult(intent, 1000);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //서버데이터와 입력된 데이터를 비교해서 일치하면 로그인 성공하였습니다 메세지

                try {
                    JSONObject jo = new JSONObject();
                    jo.put("Name", Name.getText());
                    jo.put("PhoneNumber", Phone.getText());

                    Communication communication=new Communication();
                    communication.execute(jo);
                }
                catch(Exception e) {

                }
                //서버데이터

//                if( Name.getText() == ) {
//                    Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
//                }
//
//                else {
//                    Toast.makeText(LoginActivity.this, "존재하지 않습니다.", Toast.LENGTH_SHORT).show();
//                }
//
//
//                if(Name==communication.resultJson.getJSONArray("Tom") && Phone==서버 비밀번호){
//                      JSONOBject object = new jsonobec();
//                         object.put '아이디입니다.', Name.getText()
                //                         object.put '패스워드입니다..', Name.getText()
//                    Toast.makeText(getApplicationContext(),"로그인되었습니다",Toast.LENGTH_SHORT).show();
//                }
//                else{
//
//                    Toast.makeText(getApplicationContext(),"존재하지 않습니다",Toast.LENGTH_SHORT).show();
//
//                }



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
                sendHTTPData("http://117.17.93.58:3000/userLogin", obj[0]); //본인의 서버 아이피, array형태로 한개씩=>이게 보내는 데이터

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

                String rslt;
                Log.d("=========", "--------");
                Log.d("=== This is result: ", rslt = resultJson.get("status").toString());

                if (resultJson.get("status").toString().equals("success")) {
                    //로그인 성공 후 액션
                    Log.d(" ", rslt = resultJson.get("status").toString());
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    if (resultJson.get("status").toString().equals("Not success")) {
                        Log.d(" ", rslt = resultJson.get("status").toString());
                        Toast.makeText(getApplicationContext(), "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(" ", rslt = resultJson.get("status").toString());
                        Toast.makeText(getApplicationContext(), "틀린 비밀번호입니다.", Toast.LENGTH_SHORT).show();
                    }
                }


                JSONArray arr2 = (JSONArray)resultJson.get("data");
                for(int i=0;i<arr2.length();i++){
                    JSONObject tempObj=arr2.getJSONObject(i);
                    tempObj.getString("Name");
                    String tempStr=tempObj.getString("Name");
                    String tempPwd=tempObj.getString("PhoneNumber");
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
