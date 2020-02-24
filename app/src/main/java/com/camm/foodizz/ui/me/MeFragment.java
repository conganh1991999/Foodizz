package com.camm.foodizz.ui.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.camm.foodizz.R;
import com.nex3z.notificationbadge.NotificationBadge;

public class MeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        NotificationBadge mBadge = view.findViewById(R.id.mBadge);
        mBadge.setNumber(1);
        return view;
    }
}
