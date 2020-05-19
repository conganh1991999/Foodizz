package com.camm.foodizz.ui.food_detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.camm.foodizz.data.listener.ListReviewListener;
import com.camm.foodizz.models.adapter.FoodReviewAdapter;
import com.camm.foodizz.models.Review;
import com.camm.foodizz.models.decorator.ReviewListDivider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodReviewFragment extends Fragment {

    private static final String TAG = "FoodReviewFragment";

    private View view;

    private FoodDetailViewModel foodModel;

    private String foodId;

    private CircleImageView myImageOnFoodDetail;
    private TextView myNameOnFoodDetail;
    private RatingBar myRbOnFoodDetail;
    private EditText edtReviewFoodDetail;
    private Button btnPostReviewFoodDetail;

    private RecyclerView recyclerReview;
    private LinearLayoutManager reviewManager;

    private FoodReviewAdapter adapter;

    private ArrayList<Review> listReview;

    private Query reviewRef;
    private ChildEventListener reviewListener;

    public static String nextReviewItemKey = null;
    public static boolean isScrollingReview = false;

    public static boolean isReviewInserted = false;

    private double currentScore;
    private int currentNumOfRate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food_reviews, container, false);

        if(getActivity() != null) {
            foodModel = new ViewModelProvider(getActivity()).get(FoodDetailViewModel.class);
            foodId = foodModel.getFoodId();
        }

        mapping();

        manageRecyclerViews();

        getCurrentUserInfo();

        listReview = new ArrayList<>();
        adapter = new FoodReviewAdapter(listReview, getContext());
        recyclerReview.addItemDecoration(new ReviewListDivider(getContext(), LinearLayoutManager.VERTICAL));
        recyclerReview.setAdapter(adapter);

        btnPostReviewFoodDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = edtReviewFoodDetail.getText().toString();
                if (review.isEmpty()){
                    Toast.makeText(getContext(), "Type something", Toast.LENGTH_SHORT).show();
                }
                else{
                    edtReviewFoodDetail.setText("");
                    isReviewInserted = true;
                    saveUserReview(review);
                }
            }
        });

        preLoadReviewData();
        // TODO: bug in initScrollListener
        // initScrollReviewListener();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            foodModel.getTotalScore().observe(getViewLifecycleOwner(), new Observer<Double>() {
                @Override
                public void onChanged(Double aDouble) {
                    currentScore = aDouble;
                }
            });
            foodModel.getNumOfRate().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    currentNumOfRate = integer;
                }
            });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        reviewRef.removeEventListener(reviewListener);
    }

    private void mapping(){
        recyclerReview = view.findViewById(R.id.recyclerReviewFoodDetail);
        myImageOnFoodDetail = view.findViewById(R.id.myImageOnFoodDetail);
        myNameOnFoodDetail = view.findViewById(R.id.myNameOnFoodDetail);
        myRbOnFoodDetail = view.findViewById(R.id.myRbOnFoodDetail);
        edtReviewFoodDetail = view.findViewById(R.id.edtReviewFoodDetail);
        btnPostReviewFoodDetail = view.findViewById(R.id.btnPostReviewFoodDetail);
    }

    private void manageRecyclerViews(){
        recyclerReview.setHasFixedSize(true);
        reviewManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerReview.setLayoutManager(reviewManager);
    }

    private void getCurrentUserInfo(){
        FirebaseUser myInfo = FirebaseAuth.getInstance().getCurrentUser();
        if(myInfo != null){
            String myId = myInfo.getUid();
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("users");
            mRef.child(myId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String myImage = dataSnapshot.child("userImage").getValue(String.class);
                    String myName = dataSnapshot.child("userName").getValue(String.class);
                    Picasso.get().load(myImage).into(myImageOnFoodDetail);
                    myNameOnFoodDetail.setText(myName);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    private void preLoadReviewData(){
        if(foodId != null){
            reviewRef = FirebaseDatabase.getInstance().getReference("foods")
                    .child(foodId).child("reviews")
                    .orderByKey()
                    .limitToFirst(5);
            reviewListener = new ListReviewListener(listReview, adapter);
            reviewRef.addChildEventListener(reviewListener);
        }
        else
            Log.d(TAG, "FoodReviewFragment: food id is null");
    }

    private void initScrollReviewListener() {
        recyclerReview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScrollingReview) {
                    if (reviewManager != null &&
                            reviewManager.findLastCompletelyVisibleItemPosition() == listReview.size() - 1) {
                        loadMoreReview();
                        isScrollingReview = false;
                    }
                }
            }
        });
    }

    private void loadMoreReview() {

        reviewRef.removeEventListener(reviewListener);
        reviewRef = FirebaseDatabase.getInstance().getReference("foods")
                .child(foodId).child("reviews")
                .orderByKey()
                .startAt(nextReviewItemKey)
                .limitToFirst(5);

        reviewListener = new ListReviewListener(listReview, adapter);

        reviewRef.addChildEventListener(reviewListener);

    }

    private void saveUserReview(String review){
        FirebaseUser myInfo = FirebaseAuth.getInstance().getCurrentUser();
        if(myInfo != null) {
            String myId = myInfo.getUid();
            long time = System.currentTimeMillis()/1000;
            double rating = myRbOnFoodDetail.getRating();
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("foods")
                    .child(foodId).child("reviews").push();

            Review userReview = new Review(myId, review, rating, time);
            mRef.setValue(userReview);

            updateTotalFoodScore(rating);
        }
    }

    private void updateTotalFoodScore(double rating){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("foods").child(foodId);
        if(currentScore == -1 || currentNumOfRate == -1){
            Log.d(TAG, "FoodReviewFragment: foodLiveData.getValue() == null");
        }
        else{
            currentScore = currentScore*currentNumOfRate + rating;
            currentNumOfRate += 1;
            mRef.child("numOfRate").setValue(currentNumOfRate);
            mRef.child("totalScore").setValue(currentScore/currentNumOfRate);
        }
    }

}
