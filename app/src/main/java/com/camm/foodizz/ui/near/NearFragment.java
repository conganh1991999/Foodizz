package com.camm.foodizz.ui.near;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.camm.foodizz.R;

public class NearFragment extends Fragment {

    private NearViewModel nearViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        nearViewModel =
                ViewModelProviders.of(this).get(NearViewModel.class);
        View root = inflater.inflate(R.layout.fragment_near, container, false);
        final TextView textView = root.findViewById(R.id.text_near);
        nearViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}