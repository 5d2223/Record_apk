package com.example.deliveryinformationrecord;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.deliveryinformationrecord.view.Fragment_set;
import com.example.deliveryinformationrecord.view.Fragment_show;


//fragment的宿主活动

public class MainActivity extends AppCompatActivity{

    public Fragment_show show ;
    public Fragment_set set ;
    public Button bianji,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }
    public void initView(){

        bianji=findViewById(R.id.bianji);

        bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set=new Fragment_set();
                getFragmentManager().beginTransaction().replace(R.id.frameLayout,set).commit();
            }
        });

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show = new Fragment_show();
                getFragmentManager().beginTransaction().replace(R.id.frameLayout,show).commit();
            }
        });

        show = new Fragment_show();
        getFragmentManager().beginTransaction().replace(R.id.frameLayout,show).commit();

    }
}