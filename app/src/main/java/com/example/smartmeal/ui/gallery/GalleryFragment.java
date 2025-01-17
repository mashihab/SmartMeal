package com.example.smartmeal.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartmeal.R;
import com.example.smartmeal.sharedpreference.MySharedPreferences;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final EditText uniqueToken = root.findViewById(R.id.et_unique_token);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getContext());

              String uniquetoken =  sharedPreferences.getUniqueId();

              uniqueToken.setText(uniquetoken);
            }
        });
        return root;
    }
}