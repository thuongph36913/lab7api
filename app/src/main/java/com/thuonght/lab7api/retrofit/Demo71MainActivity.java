package com.thuonght.lab7api.retrofit;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.thuonght.lab7api.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Demo71MainActivity extends AppCompatActivity {
    EditText txtName,txtPrice,txtDes;
    Button btnI,btnU,btnD,btnS;
    TextView tvKQ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo71_main);
        txtName=findViewById(R.id.demo71TxtName);
        txtPrice=findViewById(R.id.demo71TxtPrice);
        txtDes=findViewById(R.id.demo71TxtDes);
        btnI=findViewById(R.id.demo71BtnInsert);
        btnU=findViewById(R.id.demo71BtnUpdate);
        btnD=findViewById(R.id.demo71BtnDelete);
        btnS=findViewById(R.id.demo71BtnSelect);
        tvKQ=findViewById(R.id.demo71TvKQ);
        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRetrofit();
            }
        });
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRetrofit();
            }
        });
    }
    String strKQ="";
    List<Prod> ls;
    private void selectRetrofit() {
        //b1. tao doi tuong Retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.0.105/aflutter/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterSelectProd interSelectProd
                =retrofit.create(InterSelectProd.class);
        Call<SvrResponseProd> call=interSelectProd.getProd();
        call.enqueue(new Callback<SvrResponseProd>() {
            @Override
            public void onResponse(Call<SvrResponseProd> call, Response<SvrResponseProd> response) {
                SvrResponseProd svrResponseProd=response.body();
                ls=new ArrayList<>(Arrays.asList(svrResponseProd.getProducts()));
                for(Prod p: ls )
                {
                    strKQ += "Id: "+p.getPid()+"; Name: "+p.getName()+"; Price: "+p.getPrice()+"\n";
                }
                tvKQ.setText(strKQ);
            }
            @Override
            public void onFailure(Call<SvrResponseProd> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });

    }

    private void insertRetrofit() {
        Prd prd=new Prd();
        prd.setName(txtName.getText().toString());
        prd.setPrice(txtPrice.getText().toString());
        prd.setDesciption(txtDes.getText().toString());
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.0.105/aflutter/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        InterInserPrd insertPrdObj=retrofit.create(InterInserPrd.class);
        Call<SvrResponsePrd> call=insertPrdObj.insertPrd(prd.getName(),prd.getPrice(),prd.getDesciption());
        call.enqueue(new Callback<SvrResponsePrd>() {
            @Override
            public void onResponse(Call<SvrResponsePrd> call, Response<SvrResponsePrd> response) {
                SvrResponsePrd svrResponsePrd=response.body();//lay ket qua ma server tra ve
                tvKQ.setText(svrResponsePrd.getMessage());
            }
            @Override
            public void onFailure(Call<SvrResponsePrd> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });
    }
}