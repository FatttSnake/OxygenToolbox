package com.fatapp.oxygentoolbox.ui.about.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatapp.oxygentoolbox.R;
import com.fatapp.oxygentoolbox.util.ResourceUtil;
import com.fatapp.oxygentoolbox.util.json.DependenciesJson;
import com.fatapp.oxygentoolbox.util.json.LicenseJson;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class LibrariesAdapter extends RecyclerView.Adapter<LibrariesAdapter.ViewHolder> implements Filterable {
    private final Context context;
    private final DependenciesJson dependencies;
    private List<DependenciesJson.LibrariesDTO> librariesFiltered;

    public LibrariesAdapter(Context context, DependenciesJson dependencies) {
        this.context = context;
        this.dependencies = dependencies;
        this.librariesFiltered = dependencies.getLibraries();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libraries, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DependenciesJson.LibrariesDTO library = librariesFiltered.get(position);
        holder.getLibraryName().setText(library.getName());
        holder.getLibraryCreator().setText(library.getDevelopersStr());
        holder.getLibraryVersion().setText(library.getArtifactVersion());
        holder.getLibraryDescription().setText(library.getDescription());
        holder.getLibraryLicense().setText(getLicensesNameStr(library.getLicenses()));

        String url = library.getWebsite() != null && !library.getWebsite().isEmpty() ? library.getWebsite() : library.getScm() != null && library.getScm().getUrl() != null && !library.getScm().getUrl().isEmpty() ? library.getScm().getUrl() : null;
        if (url != null) {
            holder.cardView.setOnClickListener(view -> context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));
        }

        holder.getLibraryLicense().setOnClickListener(view -> {
            for (String license : library.getLicenses()) {
                try {
                    String licenseStr = new JSONObject(dependencies.getLicenses()).getString(license);
                    LicenseJson licenseObject = new Gson().fromJson(licenseStr, new TypeToken<LicenseJson>() {
                    }.getType());
                    new MaterialAlertDialogBuilder(context).setMessage(licenseObject.getContent()).show()
                            .getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                } catch (JSONException e) {
                    new MaterialAlertDialogBuilder(context).setMessage(String.format("Could not load license \"%s\"", license)).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return librariesFiltered.size();
    }

    private String getLicensesNameStr(List<String> licenses) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (String license : licenses) {
            try {
                String licenseStr = new JSONObject(dependencies.getLicenses()).getString(license);
                LicenseJson licenseObject = new Gson().fromJson(licenseStr, new TypeToken<LicenseJson>() {
                }.getType());
                stringJoiner.add(licenseObject.getName());
            } catch (JSONException e) {
                stringJoiner.add(license);
            }
        }
        return stringJoiner.toString();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<DependenciesJson.LibrariesDTO> libraries = new ArrayList<>();
                if (charSequence.toString().isEmpty()) {
                    libraries = dependencies.getLibraries();
                } else {
                    for (DependenciesJson.LibrariesDTO library : dependencies.getLibraries()) {
                        if (library.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            libraries.add(library);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.count = libraries.size();
                results.values = libraries;
                return results;
            }

            @SuppressWarnings("unchecked")
            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                librariesFiltered = (ArrayList<DependenciesJson.LibrariesDTO>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView cardView;
        private final TextView libraryName;
        private final TextView libraryCreator;
        private final TextView libraryVersion;
        private final TextView libraryDescription;
        private final TextView libraryLicense;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            libraryName = itemView.findViewById(R.id.text_view_library_name);
            libraryCreator = itemView.findViewById(R.id.text_view_library_creator);
            libraryVersion = itemView.findViewById(R.id.text_view_library_version);
            libraryDescription = itemView.findViewById(R.id.text_view_library_description);
            libraryLicense = itemView.findViewById(R.id.text_view_library_license);
        }

        public TextView getLibraryName() {
            return libraryName;
        }

        public TextView getLibraryCreator() {
            return libraryCreator;
        }

        public TextView getLibraryVersion() {
            return libraryVersion;
        }

        public TextView getLibraryDescription() {
            return libraryDescription;
        }

        public TextView getLibraryLicense() {
            return libraryLicense;
        }
    }

    public static class LibrariesItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            if (parent.getChildLayoutPosition(view) != 0) {
                outRect.top = -ResourceUtil.dpToPx(10f);
            }
        }
    }
}
