package com.example.campingapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SearchSystem extends AndroidViewModel {
    //AppDatabase db;
    private DatabaseReference mDatabase;
    SearchItemAdapter searchAdapter;
    Context context;
    public SearchSystem(@NonNull Application application) {
        super(application);
        this.context=application;
        //db = AppDatabase.getInstance(application);
        searchAdapter = new SearchItemAdapter();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                //List<CampingEntity> campList = db.campingDao().getAll();
                mDatabase=FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();
                mDatabase.child("camp").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //List<CampingEntity> campList;
                        for(DataSnapshot dSnap : dataSnapshot.getChildren()){
                            CampingEntity camp = dSnap.getValue(CampingEntity.class);
                            camp.setId(dSnap.getKey());
                            searchAdapter.addItem(camp);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
/*
                for (int i = 0; i < campList.size(); i++) {
                    CampingEntity camp = campList.get(i);
                    //SearchItem item = new SearchItem(camp.getCampName(), camp.getReview(), camp.getRating(), camp.getPrice());
                    searchAdapter.addItem(camp);
                }*/
            }
        });
        th.start();

    }

    public void printSearchItem(ListView view, String search) {
        view.setAdapter(this.searchAdapter);
        searchAdapter.getFilter().filter(search);
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
    /*
    public void SortSearchItem(String condition){
        ArrayList<SearchItem> itemList = searchAdapter.items;
        switch (condition) {
            case "리뷰 많은 순":
                Comparator<SearchItem> sort_review = new Comparator<SearchItem>() {
                    @Override
                    public int compare(SearchItem o1, SearchItem o2) {
                        return (o2.getReview() - o1.getReview());
                    }
                };
                Collections.sort(itemList, sort_review);
                searchAdapter.items = itemList;
                searchAdapter.notifyDataSetChanged();
                break;
            case "평점 높은 순":
                Comparator<SearchItem> sort_rating = new Comparator<SearchItem>() {
                    @Override
                    public int compare(SearchItem o1, SearchItem o2) {
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
                break;
            case "가격 낮은 순":
                Comparator<SearchItem> cond = new Comparator<SearchItem>() {
                    @Override
                    public int compare(SearchItem o1, SearchItem o2) {
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
                break;
            default:
                searchAdapter.items = itemList;
                searchAdapter.notifyDataSetChanged();
                break;

        }
    }
    */
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
        public Object getItem(int position) {
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
            view.setTextRating(item.getRating());
            view.setTextPrice(item.getPrice());

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



}

