package com.example.a8308_04.guru1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listview;
    ListAdapter adapter;  //통신끝나면 나중에 쓰려고

    protected void onCreate(Bundle savedInstanceState) {

//        listview=(ListView)findViewById(R.id.listview2);
//        adapter=listview.setAdapter(listeAdapter);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //데이터베이스에서 주문내역 가져오기
                Intent intent=new Intent(MainActivity.this,OrderListActivity.class);
                startActivity(intent);

            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }



    public void onButton1Clicked(View v){
        Intent intent=new Intent(MainActivity.this,ListActivity.class);
        startActivity(intent);

    }
    @Override
    public void onBackPressed() {
//        AlertDialog.Builder d = new AlertDialog.Builder(this);
//        d.setMessage("정말 종료하시겠습니까?");
//        d.setPositiveButton("예", new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialog, int which) {
//                // process전체 종료
//                finish();
//            }
//        });
//        d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        d.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            //오늘뭐먹지
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.search.naver.com/search.naver?sm=mtb_sug.top&where=m&query=%EB%84%A4%EC%9D%B4%EB%B2%84+%EB%A3%B0%EB%A0%9B&oquery=%EB%A3%B0%EB%A0%9B&tqi=ThshiwpySAsssbsT9sKssssssNl-452015&tab=&qdt=0&acq=%EB%A3%B0%EB%A0%9B&acr=3"));
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {
            //더치페이
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://search.naver.com/search.naver?where=nexearch&sm=top_sug.pre&fbm=1&acr=1&acq=%EC%82%AC%EB%8B%A4%EB%A6%AC%ED%83%80&qdt=0&ie=utf8&query=%EC%82%AC%EB%8B%A4%EB%A6%AC%ED%83%80%EA%B8%B0+%EA%B2%8C%EC%9E%84"));
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            //장바구니
            Intent intent=new Intent(MainActivity.this,BasketActivity.class);
            startActivity(intent);
           }
        else if (id == R.id.nav_orderList) {
            //주문내역
            Intent intent=new Intent(MainActivity.this,OrderListActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }








}
