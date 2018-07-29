package com.example.muledattatraya.moneytap.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.muledattatraya.moneytap.R;
import com.example.muledattatraya.moneytap.pojo.SearchData;
import com.example.muledattatraya.moneytap.utils.Globals;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private Handler mHandler;
    private EditText mEditTxtSearch;
    private TableLayout mTableLayout;
    private ArrayList<SearchData.Query> mQueryArrayList;
    private ImageView mImgageVwBack, mImgageVwClear;
    private Button mBtnNoDataFound;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        clickListener();
    }

    // Below method is used to initializations views
    public void init() {
        mEditTxtSearch = (EditText) findViewById(R.id.edt_search);
        mEditTxtSearch.addTextChangedListener(textWatcher);
        mTableLayout = (TableLayout) findViewById(R.id.tl_details);
        mQueryArrayList = new ArrayList<>();
        mImgageVwBack = (ImageView) findViewById(R.id.img_back);
        mImgageVwClear = findViewById(R.id.img_clear);
        mBtnNoDataFound = (Button) findViewById(R.id.btn_no_data_found);
        mProgressBar = (ProgressBar)findViewById(R.id.search_loading_progress);
    }

    // Below method is used for click listener on views
    public void clickListener() {
        mImgageVwBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                builder.setTitle(getResources().getString(R.string.str_dialog_title));
                builder.setMessage(getResources().getString(R.string.str_dialog_message));
                builder.setCancelable(false);
                builder.setPositiveButton(getResources().getString(R.string.str_dialog_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.str_dialog_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        mImgageVwClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTxtSearch.getText().toString().length() != 0) {
                    mEditTxtSearch.setText(null);
                }
            }
        });
    }

    // Below method is used to fetch data using volley
    private void fetchData(String baseUrl) {
        StringRequest request = new StringRequest(Request.Method.GET, baseUrl, onPostsLoaded, onPostsError);
        AppController.getInstance().addToRequestQueue(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            if (response != null) {
                Gson gson = new Gson();
                SearchData searchData = gson.fromJson(response, SearchData.class);
                if (searchData.getQuery() != null) {
                    setDataToView(searchData);
                } else {
                    mTableLayout.removeAllViews();
                    mProgressBar.setVisibility(View.GONE);
                    mBtnNoDataFound.setVisibility(View.VISIBLE);
                    mBtnNoDataFound.setEnabled(false);
                    mBtnNoDataFound.setClickable(false);
                }
            } else {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            mProgressBar.setVisibility(View.GONE);
        }
    };


    // Below code is textwatcher of edit text search field
    private final TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // cancel task if any before
            AppController.getInstance().cancelPendingRequests();
            if (s.toString().trim().length() == 0) {
                if (mQueryArrayList != null) {
                    mQueryArrayList.clear();
                    mTableLayout.removeAllViews();
                    mProgressBar.setVisibility(View.GONE);
                }
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
                getDataFromService();
            }
        }
        public void afterTextChanged(Editable s) {
            if (s.toString().length() == 0) {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    };

    public void getDataFromService() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mEditTxtSearch.getText().toString().trim().length() > 0) {
                    String url = getResources().getString(R.string.str_url_to_get_records) + mEditTxtSearch.getText().toString().trim() + getResources().getString(R.string.str_gpslimit);
                    mTableLayout.removeAllViews();
                    checkInternet(url);
                } else if (mEditTxtSearch.getText().toString().trim().length() == 0) {
                    mTableLayout.removeAllViews();
                    String url = getResources().getString(R.string.str_url_to_get_records) + mEditTxtSearch.getText().toString().trim() + getResources().getString(R.string.str_gpslimit);
                    checkInternet(url);
                }
            }
        }, 10);
    }

    // Below method to setdata from service on views in layout

    public void setDataToView(SearchData searchData) {
        mBtnNoDataFound.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        if (mQueryArrayList.size() != 0) {
            mQueryArrayList.clear();
            mQueryArrayList.add(searchData.getQuery());
        } else {
            mQueryArrayList.add(searchData.getQuery());
        }
        for (int i = 0; i < mQueryArrayList.size(); i++) {
            for (int j = 0; j < mQueryArrayList.get(i).getPages().size(); j++) {
                LayoutInflater inflater = SearchActivity.this.getLayoutInflater();
                View childView = inflater.inflate(R.layout.cell_layout, null);
                TextView txt_description = (TextView) childView.findViewById(R.id.txt_description);
                final TextView txt_Title = (TextView) childView.findViewById(R.id.txt_Title);
                final ImageView imageProfile = (ImageView) childView.findViewById(R.id.imageProfile);
                if (mQueryArrayList.get(i).getPages().get(j).getTerms() != null) {
                    txt_description.setText(mQueryArrayList.get(i).getPages().get(j).getTerms().getDescription().get(0));
                } else {
                    txt_description.setText("");
                }
                txt_Title.setText(mQueryArrayList.get(i).getPages().get(j).getTitle());
                if (mQueryArrayList.get(i).getPages().get(j).getThumbnail() != null) {
                    Picasso.with(SearchActivity.this)
                            .load(mQueryArrayList.get(i).getPages().get(j).getThumbnail().getSource())
                            .into(imageProfile);

                }
                TableRow row2 = new TableRow(SearchActivity.this);
                mTableLayout.addView(row2);
                row2.addView(childView);
                row2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                        intent.putExtra(Globals.KEY_INTENT_TITLE, txt_Title.getText().toString());
                        startActivity(intent);
                    }
                });
            }
        }
    }

    // Below method to check internet connection before hitting the service
    public void checkInternet(String url) {
        if (Globals.isConnectedToInternet(SearchActivity.this)) {
            fetchData(url);
        } else {
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(SearchActivity.this, R.string.str_internet_connection_check, Toast.LENGTH_SHORT).show();
        }
    }
}
