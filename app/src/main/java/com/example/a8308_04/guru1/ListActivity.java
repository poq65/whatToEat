package com.example.a8308_04.guru1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 8308-04 on 2017-07-31.
 */
public class ListActivity extends Activity{

    private static final String[] LIST_MENU ={"용우동 02-000-00000", "김가네 02-000-0000", "밥푸리 02-000-0000"} ;
    Button button1;
    ImageView picture;
    ArrayList<String> orderList=new ArrayList<String>();


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);

        // Spinner

        final Spinner monthSpinner = (Spinner)findViewById(R.id.menu);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this, R.array.date_month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (parent.getSelectedItem().toString()){
                    case "용우동":
                    {
                        picture=(ImageView)findViewById(R.id.picture);
                        Bitmap bm1= BitmapFactory.decodeResource(getResources(), R.drawable.shopinfo_default);
                        picture.setImageBitmap(bm1);
                        String[] arrProv = new String[] {"밥 요리", "국수 요리", "철판 요리"};

                        final String[][] arrCity = new String[][]{
                                {"순두부찌개", "비빔밥", "된장찌개"},
                                {"잔치국수", "비빔국수", "칼국수", "냉면"            },
                                {"철판함박스테이크", "철판데리야끼치킨", "철판불닭", "철판매콤떡갈비"}
                        };

                        //ExpandableeiListApater
                        //ArrayList -> Lise -> Map(HashMap)
                        //익스펜더블 리스트 어뎁터의 형식은 ArrayList의 부모-List로 받아야 한다 특이하게도
                        //List이 부모는 맵(Hashmap)
                        //데이터는 배열로 넣으면 자동으로 읽게끔 되있음

                        //포문을 이용해 데이터를 집어넣는다,

                        List<Map<String, String>> provData =new ArrayList<>();
                        final List<List<Map<String, String>>> cityData =new ArrayList<>();

                        for(int i=0; i<arrProv.length; i++) {
                            Map<String, String> prov =  new HashMap<>();
                            prov.put("prov",arrProv[i]);
                            provData.add(prov);

                            List<Map<String,String>> cityes
                                    =new ArrayList<>();
                            for (int j=0; j<arrCity[i].length; j++){
                                Map<String, String> city
                                        =new HashMap<>();
                                city.put("city", arrCity[i][j]);
                                cityes.add(city);
                            }//데이터
                            cityData.add(cityes);
                        }
                        ExpandableListAdapter adapter1 =new SimpleExpandableListAdapter(parent.getContext(), provData, android.R.layout.simple_expandable_list_item_1, new String[]{"prov"},
                                new int[] {android.R.id.text1}, cityData, android.R.layout.simple_expandable_list_item_1,
                                new String[] {"city"},
                                new int[] {android.R.id.text1} );

                        ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.list);
                        expandableListView.setAdapter(adapter1);
                        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                                if(expandableListView.isSelected()==false)
                                    orderList.add(arrCity[i][i1]);

                                Toast.makeText(getApplicationContext(),arrCity[i][i1]+"",Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        });

                    }
                    break;
                    case "김가네":
                    {
                        picture=(ImageView)findViewById(R.id.picture);
                        Bitmap bm2= BitmapFactory.decodeResource(getResources(), R.drawable.kim);
                        picture.setImageBitmap(bm2);
                        String[] arrProv = new String[] {"김밥류", "식사류", "분식류","면류"};
                        // 충청이 나오면 첫줄이 나오도록 묶어야 함
                        final String[][] arrCity = new String[][]{
                                {"꼬마김밥", "참치김밥", "못난이김밥"},
                                {"소고기덮밥", "돌솥비빔밥", "김치찌개", "육개장"            },
                                {"라볶이", "김치수제비", "물만두", "치즈쌀떡볶이"},
                                {"떡라면","만두라면","해물우동"}
                        };

                        //ExpandableeiListApater
                        //ArrayList -> Lise -> Map(HashMap)
                        //익스펜더블 리스트 어뎁터의 형식은 ArrayList의 부모-List로 받아야 한다 특이하게도
                        //List이 부모는 맵(Hashmap)
                        //데이터는 배열로 넣으면 자동으로 읽게끔 되있음

                        //포문을 이용해 데이터를 집어넣는다,

                        List<Map<String, String>> provData =new ArrayList<>();
                        List<List<Map<String, String>>> cityData =new ArrayList<>();

                        for(int i=0; i<arrProv.length; i++) {
                            Map<String, String> prov =  new HashMap<>();
                            prov.put("prov",arrProv[i]);
                            provData.add(prov);

                            List<Map<String,String>> cityes
                                    =new ArrayList<>();
                            for (int j=0; j<arrCity[i].length; j++){
                                Map<String, String> city
                                        =new HashMap<>();
                                city.put("city", arrCity[i][j]);
                                cityes.add(city);
                            }//데이터
                            cityData.add(cityes);
                        }
                        ExpandableListAdapter adapter1 =new SimpleExpandableListAdapter(parent.getContext(), provData, android.R.layout.simple_expandable_list_item_1, new String[]{"prov"},
                                new int[] {android.R.id.text1}, cityData, android.R.layout.simple_expandable_list_item_1,
                                new String[] {"city"},
                                new int[] {android.R.id.text1} );

                        ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.list);
                        expandableListView.setAdapter(adapter1);
                        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                                if(expandableListView.isSelected()==false)
                                    orderList.add(arrCity[i][i1]);

                                Toast.makeText(getApplicationContext(),arrCity[i][i1]+"",Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        });
                    }
                    break;
                    case "밥푸리":
                    {
                        picture=(ImageView)findViewById(R.id.picture);
                        Bitmap bm3= BitmapFactory.decodeResource(getResources(), R.drawable.bi_new);
                        picture.setImageBitmap(bm3);
                        String[] arrProv = new String[] {"김밥", "덮밥", "스낵"};
                        // 충청이 나오면 첫줄이 나오도록 묶어야 함
                        final String[][] arrCity = new String[][]{
                                {"밥푸리숯불김밥", "더블치즈김밥", "돈까스김밥"},
                                {"데리숯불제육덮밥", "오므라이스"},
                                {"치즈라면", "클래식컵라면", "국물떡볶이", "치즈라볶이"}
                        };

                        //ExpandableeiListApater
                        //ArrayList -> Lise -> Map(HashMap)
                        //익스펜더블 리스트 어뎁터의 형식은 ArrayList의 부모-List로 받아야 한다 특이하게도
                        //List이 부모는 맵(Hashmap)
                        //데이터는 배열로 넣으면 자동으로 읽게끔 되있음

                        //포문을 이용해 데이터를 집어넣는다,

                        List<Map<String, String>> provData =new ArrayList<>();
                        List<List<Map<String, String>>> cityData =new ArrayList<>();

                        for(int i=0; i<arrProv.length; i++) {
                            Map<String, String> prov =  new HashMap<>();
                            prov.put("prov",arrProv[i]);
                            provData.add(prov);

                            List<Map<String,String>> cityes
                                    =new ArrayList<>();
                            for (int j=0; j<arrCity[i].length; j++){
                                Map<String, String> city
                                        =new HashMap<>();
                                city.put("city", arrCity[i][j]);
                                cityes.add(city);
                            }//데이터
                            cityData.add(cityes);
                        }
                        ExpandableListAdapter adapter1 =new SimpleExpandableListAdapter(parent.getContext(), provData, android.R.layout.simple_expandable_list_item_1, new String[]{"prov"},
                                new int[] {android.R.id.text1}, cityData, android.R.layout.simple_expandable_list_item_1,
                                new String[] {"city"},
                                new int[] {android.R.id.text1} );

                        ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.list);
                        expandableListView.setAdapter(adapter1);
                        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                                if(expandableListView.isSelected()==false)
                                    orderList.add(arrCity[i][i1]);

                                Toast.makeText(getApplicationContext(),arrCity[i][i1]+"",Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        });
                    }
                    break;
                    case "봉구스":
                    {
                        picture=(ImageView)findViewById(R.id.picture);
                        Bitmap bm4= BitmapFactory.decodeResource(getResources(), R.drawable.bong);
                        picture.setImageBitmap(bm4);
                        String[] arrProv = new String[] {"밥버거"};
                        // 충청이 나오면 첫줄이 나오도록 묶어야 함
                        final String[][] arrCity = new String[][]{
                                {"봉구스밥버거", "돈까스밥버거", "치즈밥버거","돈까스마요밥버거","햄치즈밥버거","멸치마요밥버거"}

                        };

                        //ExpandableeiListApater
                        //ArrayList -> Lise -> Map(HashMap)
                        //익스펜더블 리스트 어뎁터의 형식은 ArrayList의 부모-List로 받아야 한다 특이하게도
                        //List이 부모는 맵(Hashmap)
                        //데이터는 배열로 넣으면 자동으로 읽게끔 되있음

                        //포문을 이용해 데이터를 집어넣는다,

                        List<Map<String, String>> provData =new ArrayList<>();
                        List<List<Map<String, String>>> cityData =new ArrayList<>();

                        for(int i=0; i<arrProv.length; i++) {
                            Map<String, String> prov =  new HashMap<>();
                            prov.put("prov",arrProv[i]);
                            provData.add(prov);

                            List<Map<String,String>> cityes
                                    =new ArrayList<>();
                            for (int j=0; j<arrCity[i].length; j++){
                                Map<String, String> city
                                        =new HashMap<>();
                                city.put("city", arrCity[i][j]);
                                cityes.add(city);
                            }//데이터
                            cityData.add(cityes);
                        }
                        ExpandableListAdapter adapter1 =new SimpleExpandableListAdapter(parent.getContext(), provData, android.R.layout.simple_expandable_list_item_1, new String[]{"prov"},
                                new int[] {android.R.id.text1}, cityData, android.R.layout.simple_expandable_list_item_1,
                                new String[] {"city"},
                                new int[] {android.R.id.text1} );

                        ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.list);
                        expandableListView.setAdapter(adapter1);
                        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                                if(expandableListView.isSelected()==false)
                                    orderList.add(arrCity[i][i1]);

                                Toast.makeText(getApplicationContext(),arrCity[i][i1]+"",Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        });

                    }

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //ListView listView = (ListView) findViewById(R.id.list);
        //listView.setAdapter(adapter);



        button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ListActivity.this,BasketActivity.class);
                intent.putStringArrayListExtra("order :", orderList);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),orderList.toString(),Toast.LENGTH_SHORT).show();
            }
        });


    }




}
