package com.saltside;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.saltside.Utils.CardAdapter;
import com.saltside.Utils.ObjectHandler;
import com.saltside.model.SaltSideModel;
import com.saltside.model.SaltSideModelData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ResponseErrorDialog.ResponseErrorDialogSelectedListener {

    public static final String TAG = "MainActivity";
    Button mButton;
    String baseUrl;
    ProgressDialog progressDialog;
    SaltSideModelData saltSideModelData;
    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseUrl = "https://gist.githubusercontent.com/maclir/f715d78b49c3b4b3b77f/raw/8854ab2fe4cbe2a5919cea97d71b714ae5a4838d/items.json";
        setupUI();
        setUpToolbar();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setupUI() {
        mButton = (Button)findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJSONDataAPI(MainActivity.this, TAG, baseUrl);
            }
        });
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Salt Side List");
    }
    private void getJSONDataAPI(final Context context, final String TAG, String sBaseURL) {
        final Activity activity = (Activity) context;
        if (progressDialog != null && progressDialog.isShowing()) {
            if (!isFinishing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setMessage("Please wait!");
        progressDialog.setCanceledOnTouchOutside(false);
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                sBaseURL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("VMA", "Response " + response.toString());
                        ObjectHandler objectHandler = new ObjectHandler();
                        saltSideModelData = objectHandler.parseSaltSideModelDataJson(response.toString());

                        final List<SaltSideModel> saltSideModelList = saltSideModelData.getSaltSideModelList();

                        adapter = new CardAdapter(MainActivity.this,saltSideModelList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        mButton.setVisibility(View.GONE);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            if (!isFinishing()) {
                                progressDialog.dismiss();
                                progressDialog = null;
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        if (!isFinishing()) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                    }
                    ResponseErrorDialog responseErrorDialog = new ResponseErrorDialog(context, "Unable to retrieve data. Try again ");
                    responseErrorDialog.setCanceledOnTouchOutside(false);
                    responseErrorDialog.show();
                } else {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        if (!isFinishing()) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                    }
                    ResponseErrorDialog responseErrorDialog = new ResponseErrorDialog(context, "Network Failure");
                    responseErrorDialog.setCanceledOnTouchOutside(false);
                    responseErrorDialog.show();
                }
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq, TAG);
    }

    @Override
    public void responseErrorDialogClickOk() {

    }
}
