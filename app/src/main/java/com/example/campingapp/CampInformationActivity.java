package com.example.campingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.campingapp.databinding.ActivityCampInformationBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CampInformationActivity extends AppCompatActivity {

    ActivityCampInformationBinding binding;
    CampingEntity camp;
    private DatabaseReference mDatabase2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camp_information);
        Dialog dialog = new Dialog(CampInformationActivity.this);
        mDatabase2=FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();
        Intent intent = getIntent();
        ReviewAdapter adapter10 = new ReviewAdapter();
        camp = (CampingEntity) intent.getSerializableExtra("CampingEntity");

        if (camp == null) {


        }else {
            binding.setCamp(camp);
            System.out.println(camp.getPhotoUri());

            Uri uri = Uri.parse(camp.getPhotoUri());
            Glide.with(this)
                    .load(uri)
                    .into(binding.campInfoImage);


        }

        ListView listView1 = findViewById(R.id.listview_review);
        String campid=camp.getId();
        DatabaseReference review1= mDatabase2.child("review").child(campid);
        review1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap2:snapshot.getChildren()){
                    WritingReviewEntity review=snap2.getValue(WritingReviewEntity.class);

                    adapter10.additem(review);

                }

                listView1.setAdapter(adapter10);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











        Button reserve_btn = (Button) binding.reserveButton;
        reserve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReservationActivity.class);
                intent.putExtra("CampingEntity", camp);
                startActivity(intent);
            }
        });

        Button detail_btn = (Button) binding.campDetailButton;
        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CampInformationActivity.this);
                dialog.setTitle(camp.getCampName());

                dialog.setMessage("가격 : "+camp.getPrice()+"\n"+
                                "인원 : "+camp.getMinPeople()+"~"+camp.getMaxPeople()+"\n"+
                                "추가 비용 : "+camp.getAddPrice()+"\n"+
                                "바베큐 : "+boolToString(camp.isDetailBbq())+"\n"+
                                "온수 : "+boolToString(camp.isDetailWater())+"\n"+
                                "Wifi : "+boolToString(camp.isDetailWifi())+"\n"+
                                "주차장 : "+boolToString(camp.isDetailParking())+"\n"+
                                "픽업 서비스 : "+boolToString(camp.isDetailPickup())+"\n"
                );
                dialog.show();
            }
        });


    }
    class ReviewAdapter extends BaseAdapter {
        ArrayList<WritingReviewEntity> items = new ArrayList<WritingReviewEntity>();

        @Override
        public int getCount()
        {
            return items.size();
        }
        public void additem(WritingReviewEntity review){
            items.add(review);
        }
        @Override
        public Object getItem(int position){
            return items.get(position);
        }
        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Review_sing rev_sing=null;
            if(convertView==null)
            {
                rev_sing=new Review_sing(getApplicationContext());
            }
            else
            {
                rev_sing=(Review_sing)convertView;
            }
            WritingReviewEntity item = items.get(position);
            rev_sing.setUsname(item.getUserName());
            rev_sing.setRecont(item.getCont());
            return rev_sing;
        }

    }

    private String boolToString(boolean flag){
        String str =new String();
        if (flag){
             str = "O";
        }else{
            str = "X";
        }
        return str;
    }

}