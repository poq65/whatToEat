package com.example.a8308_04.guru1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
public class RigisterActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private EditText etPhone;
    private Button btnDone;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        // 비밀번호 일치 검사
        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();

                if( password.equals(confirm) ) {
                    etPassword.setBackgroundColor(Color.GREEN);
                    etPasswordConfirm.setBackgroundColor(Color.GREEN);
                } else {
                    etPassword.setBackgroundColor(Color.RED);
                    etPasswordConfirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 이름 입력 확인
                if( etName.getText().toString().length() == 0 ) {
                    Toast.makeText(RigisterActivity.this, "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etName.requestFocus();
                    return;
                }

                // 이름 입력 확인
                if( etPhone.getText().toString().length() == 0 ) {
                    Toast.makeText(RigisterActivity.this, "번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    etPhone.requestFocus();
                    return;
                }


                // 비밀번호 입력 확인
                if( etPassword.getText().toString().length() == 0 ) {
                    Toast.makeText(RigisterActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                // 비밀번호 확인 입력 확인
                if( etPasswordConfirm.getText().toString().length() == 0 ) {
                    Toast.makeText(RigisterActivity.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etPasswordConfirm.requestFocus();
                    return;
                }

                // 비밀번호 일치 확인
                if( !etPassword.getText().toString().equals(etPasswordConfirm.getText().toString()) ) {
                    Toast.makeText(RigisterActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etPasswordConfirm.setText("");
                    etPassword.requestFocus();
                    return;
                }

                try {
                    JSONObject jo = new JSONObject();
                    jo.put("Name", etName.getText());
                    jo.put("PhoneNumber", etPassword.getText());

                    Communication communication=new Communication();
                    communication.execute(jo);
                }
                catch(Exception e) {

                }

                Intent result = new Intent();
                result.putExtra("Name", etName.getText().toString());

                // 자신을 호출한 Activity로 데이터를 보낸다.
                setResult(RESULT_OK, result);
                Toast.makeText(RigisterActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                //서버에 보내야해

                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                sendHTTPData("http://117.17.93.58:3000/userAdd", obj[0]); //본인의 서버 아이피, array형태로 한개씩=>이게 보내는 데이터

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
                rslt += "/";


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
