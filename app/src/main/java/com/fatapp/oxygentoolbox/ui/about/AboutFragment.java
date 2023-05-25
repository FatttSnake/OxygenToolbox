package com.fatapp.oxygentoolbox.ui.about;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.util.ResourceUtil;

public class AboutFragment extends Fragment {
    private TextView textViewAppVersion;
    private TextView textViewOpenSource;

    private void initView(View root) {
        textViewAppVersion = root.findViewById(R.id.text_view_app_version);
        textViewOpenSource = root.findViewById(R.id.text_view_open_source);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);

        initView(root);
        initAppVersion();
        initOpenSource();

        return root;
    }

    private void initOpenSource() {
        textViewOpenSource.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), LibrariesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    private void initAppVersion() {
        textViewAppVersion.setText(String.format(ResourceUtil.getAppLocale(), "%s(%d)", ResourceUtil.getAppVersionName(), ResourceUtil.getAppVersionCode()));
    }
}