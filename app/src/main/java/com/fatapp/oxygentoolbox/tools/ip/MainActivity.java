package com.fatapp.oxygentoolbox.tools.ip;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fatapp.oxygentoolbox.BuildConfig;
import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.util.ResourceUtil;
import com.fatapp.oxygentoolbox.util.http.HttpHelper;
import com.fatapp.oxygentoolbox.util.http.ResponseListener;
import com.fatapp.oxygentoolbox.util.tool.BaseActivityNormal;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivityNormal {
    final String URL_SELF = "https://www.mxnzp.com/api/ip/self?app_id=%s&app_secret=%s";
    final String URL_QUERY = "https://www.mxnzp.com/api/ip/aim_ip?ip=%s&app_id=%s&app_secret=%s";

    private TextView textViewCurrentIP;
    private EditText editTextIpAddress;
    private ImageView imageViewQuery;
    private TextView textViewResultIP;
    private TextView textViewResultProvince;
    private TextView textViewResultCity;
    private TextView textViewResultIsp;

    private void initView() {
        textViewCurrentIP = findViewById(R.id.text_view_current_ip);
        editTextIpAddress = findViewById(R.id.edit_text_ipaddress);
        imageViewQuery = findViewById(R.id.image_view_query);
        textViewResultIP = findViewById(R.id.text_view_result_ip);
        textViewResultProvince = findViewById(R.id.text_view_result_province);
        textViewResultCity = findViewById(R.id.text_view_result_city);
        textViewResultIsp = findViewById(R.id.text_view_result_isp);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.loadView(this, R.layout.activity_tool_ip);

        initView();
        getCurrentIP();
        initQuery();
    }

    private void getCurrentIP() {
        final HttpHelper httpHelper = new HttpHelper(this, URL_SELF, new ResponseListener() {
            @Override
            public void onResponse(int code, String responseBody) {
                if (code == 200) {
                    try {
                        if (new JSONObject(responseBody).getInt("code") == 1) {
                            final JSONObject data = new JSONObject(responseBody).getJSONObject("data");
                            String ip = data.getString("ip");
                            String desc = data.getString("desc");
                            textViewCurrentIP.setText(String.format("%s %s", ip, desc));
                        } else {
                            onFailed();
                        }
                    } catch (JSONException e) {
                        onFailed();
                    }
                } else {
                    onFailed();
                }
            }

            @Override
            public void onFailed() {
                textViewCurrentIP.setText(ResourceUtil.getString(R.string.tool_ip_Unknown));
            }
        });

        httpHelper.request(BuildConfig.ROLL_APP_ID, BuildConfig.ROLL_APP_SECRET);
    }

    private void initQuery() {
        editTextIpAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                imageViewQuery.setEnabled(charSequence.length() != 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextIpAddress.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                query();
                return true;
            }
            return false;
        });

        imageViewQuery.setEnabled(false);
        imageViewQuery.setOnClickListener(view -> query());
    }

    private void query() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editTextIpAddress.getWindowToken(), 0);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.tool_ip_querying);
        progressDialog.show();
        final HttpHelper httpHelper = new HttpHelper(this, URL_QUERY, new ResponseListener() {
            @Override
            public void onResponse(int code, String responseBody) {
                progressDialog.cancel();
                if (code == 200) {
                    try {
                        if (new JSONObject(responseBody).getInt("code") == 1) {
                            final JSONObject data = new JSONObject(responseBody).getJSONObject("data");
                            String ip = data.getString("ip");
                            String province = data.getString("province");
                            String city = data.getString("city");
                            String isp = data.getString("isp");
                            textViewResultIP.setText(ip.isEmpty() ? ResourceUtil.getString(R.string.tool_ip_Unknown) : ip);
                            textViewResultProvince.setText(province.isEmpty() ? ResourceUtil.getString(R.string.tool_ip_Unknown) : province);
                            textViewResultCity.setText(city.isEmpty() ? ResourceUtil.getString(R.string.tool_ip_Unknown) : city);
                            textViewResultIsp.setText(isp.isEmpty() ? ResourceUtil.getString(R.string.tool_ip_Unknown) : isp);
                        } else {
                            onFailed();
                            textViewResultIP.setText(new JSONObject(responseBody).getString("msg"));
                        }
                    } catch (JSONException e) {
                        onFailed();
                    }
                } else {
                    onFailed();
                }
            }

            @Override
            public void onFailed() {
                textViewResultIP.setText(ResourceUtil.getString(R.string.tool_ip_Unknown));
                textViewResultProvince.setText(ResourceUtil.getString(R.string.tool_ip_Unknown));
                textViewResultCity.setText(ResourceUtil.getString(R.string.tool_ip_Unknown));
                textViewResultIsp.setText(ResourceUtil.getString(R.string.tool_ip_Unknown));
            }
        });
        httpHelper.request(editTextIpAddress.getText().toString(), BuildConfig.ROLL_APP_ID, BuildConfig.ROLL_APP_SECRET);
    }
}
