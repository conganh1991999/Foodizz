package com.camm.foodizz.ui.home_menu.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.camm.foodizz.R;
import com.camm.foodizz.models.adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private View view;

    private CircleImageView myProfileImage;
    private TextView myProfileName;
    //private TextView myLocation, txtNumOfFriends, txtExperiences;

    private TabLayout profileTabLayout;
    private ViewPager profileFragmentPager;

    private DatabaseReference userRef;
    private ValueEventListener userListener;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        mapping();

        getCurrentUserInfo();

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FavouriteFragment());
        fragmentList.add(new SettingFragment());
        List<String> titleList = new ArrayList<>();
        titleList.add("Loves");
        titleList.add("Yours");

        if(getActivity() != null){
            PagerAdapter profilePagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), 1);
            profilePagerAdapter.addFragment(fragmentList, titleList);
            profileFragmentPager.setAdapter(profilePagerAdapter);
            profileTabLayout.setupWithViewPager(profileFragmentPager);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(userRef != null && userListener != null)
            userRef.removeEventListener(userListener);
    }

    private void mapping(){
        myProfileImage = view.findViewById(R.id.myProfileImage);
        myProfileName = view.findViewById(R.id.myProfileName);
        profileTabLayout = view.findViewById(R.id.profileTabLayout);
        profileFragmentPager = view.findViewById(R.id.profileFragmentPager);
    }

    private void getCurrentUserInfo(){
        FirebaseUser myInfo = FirebaseAuth.getInstance().getCurrentUser();
        if(myInfo != null){
            String myId = myInfo.getUid();
            userRef = FirebaseDatabase.getInstance().getReference("users").child(myId);
            userListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String myImage = dataSnapshot.child("userImage").getValue(String.class);
                    String myName = dataSnapshot.child("userName").getValue(String.class);
                    Picasso.get().load(myImage).into(myProfileImage);
                    myProfileName.setText(myName);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            userRef.addListenerForSingleValueEvent(userListener);
        }
    }

}
