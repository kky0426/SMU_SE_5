package com.example.campingapp.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.campingapp.reservation.CampingEntity;
import com.example.campingapp.R;
import com.example.campingapp.interfaces.Callback;
import com.example.campingapp.reservation.ManageReservationActivity;
import com.example.campingapp.upload.UploadItemView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SearchSystem extends AndroidViewModel {

    private DatabaseReference mDatabase;
    private StorageReference storage;
    SearchItemAdapter searchAdapter;
    UploadItemAdapter uploadItemAdapter;
    private FirebaseAuth auth;
    private FirebaseUser user;
    Context context;
    public SearchSystem(@NonNull Application application) {
        super(application);
        this.context=application;
        init(new Callback() {
            @Override
            public void onSuccess() {
                uploadItemAdapter.notifyDataSetChanged();
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {

            }
        });

    }


    private void init(Callback callback){
        searchAdapter = new SearchItemAdapter();
        uploadItemAdapter = new UploadItemAdapter();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mDatabase=FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();
        storage= FirebaseStorage.getInstance().getReferenceFromUrl("gs://smu-se5-camping.appspot.com");
        mDatabase.child("camp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dSnap : dataSnapshot.getChildren()){
                    CampingEntity camp = dSnap.getValue(CampingEntity.class);
                    camp.setId(dSnap.getKey());


                    storage.child("campImage").child(camp.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            camp.setPhotoUri(uri.toString());
                            searchAdapter.addItem(camp);
                            if(camp.getOwner().equals(user.getUid())){
                                uploadItemAdapter.addItem(camp);
                            }
                            //uploadItemAdapter.addItem(camp);
                            callback.onSuccess();
                        }
                    });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void filtering(boolean bbq,boolean parking,boolean pickup,boolean water,boolean wifi){
        ArrayList<CampingEntity> itemList = searchAdapter.items;
        ArrayList<CampingEntity> filterdList= new ArrayList<CampingEntity>();

        for(CampingEntity item : itemList){
            if ( (!bbq || bbq==item.isDetailBbq()) && (!parking || parking==item.isDetailParking())
            && (!pickup || pickup == item.isDetailPickup()) && (!water || item.isDetailWater() ==water)
            && (!wifi || item.isDetailWifi()==wifi)){
                filterdList.add(item);
            }

        }
        searchAdapter.items = filterdList;
        searchAdapter.notifyDataSetChanged();
    }

    public void printSearchItem(ListView view, String search) {
        view.setAdapter(this.searchAdapter);
        searchAdapter.getFilter().filter(search);
    }

    public void printUploadedCamp(ListView view,Activity activity){
        uploadItemAdapter.activity = activity;
        view.setAdapter(uploadItemAdapter);
    }

    public void deleteCamp(CampingEntity camp,Callback callback){
        mDatabase.child("camp").child(camp.getId()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess();
            }
        });
    }



    public void sortByReview(){
        ArrayList<CampingEntity> itemList = searchAdapter.items;
        Comparator<CampingEntity> sort_review = new Comparator<CampingEntity>() {
            @Override
            public int compare(CampingEntity o1, CampingEntity o2) {
                return (o2.getReview() - o1.getReview());
            }
        };
        Collections.sort(itemList, sort_review);
        searchAdapter.items = itemList;
        searchAdapter.notifyDataSetChanged();
    }

    public void sortByRating(){
        ArrayList<CampingEntity> itemList = searchAdapter.items;
        Comparator<CampingEntity> sort_rating = new Comparator<CampingEntity>() {
            @Override
            public int compare(CampingEntity o1, CampingEntity o2) {
                if (o1.getRating() > o2.getRating())
                    return -1;
                else if (o1.getRating() == o2.getRating())
                    return 0;
                else return 1;
            }
        };
        Collections.sort(itemList, sort_rating);
        searchAdapter.items = itemList;
        searchAdapter.notifyDataSetChanged();
    }

    public void sortByPrice(){
        ArrayList<CampingEntity> itemList = searchAdapter.items;
        Comparator<CampingEntity> cond = new Comparator<CampingEntity>() {
            @Override
            public int compare(CampingEntity o1, CampingEntity o2) {
                if (o1.getPrice() > o2.getPrice())
                    return 1;
                else if (o1.getPrice() == o2.getPrice())
                    return 0;
                else return -1;
            }
        };
        Collections.sort(itemList, cond);
        searchAdapter.items = itemList;
        searchAdapter.notifyDataSetChanged();
    }


    class  SearchItemAdapter extends BaseAdapter implements Filterable {
        ArrayList<CampingEntity> items = new ArrayList<CampingEntity>();
        private ArrayList<CampingEntity> oriItems = items;
        Filter listFilter;

        public void addItem(CampingEntity item){
            items.add(item);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public CampingEntity getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SearchItemView view = null;
            if(convertView == null){
                view = new SearchItemView(context);
            }else{
                view = (SearchItemView)convertView;
            }
            CampingEntity item = items.get(position);
            view.setTextName(item.getCampName());
            view.setTextReview(item.getReview());
            view.setTextRating(Math.round(item.getRating()*100)/100.0);
            view.setTextPrice(item.getPrice());
            if (item.getPhotoUri()!=null){
                view.setCampImage(context,item.getPhotoUri());
            }
            //view.setCampImage(context,item.getPhotoUri());

            return view;
        }


        @Override
        public Filter getFilter() {
            if (listFilter == null){
                listFilter = new ListFilter();
            }
            return listFilter;
        }


        private class ListFilter extends Filter{
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if(constraint == null || constraint.length() ==0){
                    results.values = oriItems;
                    results.count = oriItems.size();
                }else{
                    ArrayList<CampingEntity> itemList = new ArrayList<CampingEntity>();
                    for(CampingEntity item :oriItems){
                        if(item.getCampName().toUpperCase().contains(constraint.toString().toUpperCase())){
                            itemList.add(item);
                        }
                    }
                    results.values = itemList;
                    results.count = itemList.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (ArrayList<CampingEntity>)results.values;

                if (results.count >0){
                    notifyDataSetChanged();
                }else {
                    notifyDataSetInvalidated();
                }
            }
        }

    }

    class UploadItemAdapter extends BaseAdapter {
        ArrayList<CampingEntity> uploadItems = new ArrayList<CampingEntity>();
        Activity activity;

        public void addItem(CampingEntity camp) {
            uploadItems.add(camp);
        }

        @Override
        public int getCount() {
            return uploadItems.size();
        }

        @Override
        public CampingEntity getItem(int position) {
            return uploadItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            UploadItemView view = null;
            if (convertView == null) {
                view = new UploadItemView(context);
            } else {
                view = (UploadItemView) convertView;
            }
            CampingEntity item = uploadItems.get(position);
            view.setTextName(item.getCampName());
            Button delete = (Button) view.findViewById(R.id.camp_delete);
            Button manage = (Button) view.findViewById(R.id.reservation_manage);

            manage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ManageReservationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("CampingEntity", item);
                    context.startActivity(intent);
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                    dialog.setMessage("삭제하시겠습니까?");
                    dialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteCamp(item, new Callback() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(context,"삭제되었습니다.",Toast.LENGTH_SHORT).show();
                                    uploadItems.remove(position);
                                    uploadItemAdapter.uploadItems = uploadItems;
                                    uploadItemAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                        }
                    });
                    dialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
            });


            return view;
        }
    }



}

