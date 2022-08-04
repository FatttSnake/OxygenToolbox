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

    private AboutViewModel aboutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);

        TextView appVersion = root.findViewById(R.id.app_version);
        appVersion.setText(String.format(ResourceUtil.getAppLocale(), "%s(%d)", ResourceUtil.getAppVersionName(), ResourceUtil.getAppVersionCode()));

        TextView openSource = root.findViewById(R.id.open_source);
        openSource.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), LibrariesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        return root;
    }
}