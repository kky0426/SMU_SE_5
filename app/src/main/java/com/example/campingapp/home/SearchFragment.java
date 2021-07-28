package com.example.campingapp.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.campingapp.R;
import com.example.campingapp.databinding.FragmentSearchBinding;
import com.example.campingapp.home.SearchSystem;
import com.example.campingapp.reservation.CampInformationActivity;
import com.example.campingapp.reservation.CampingEntity;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    String[] sortCondition_ = {"정렬","리뷰 많은 순", "평점 높은 순", "가격 낮은 순"};
    boolean bbq;
    boolean wifi;
    boolean parking;
    boolean water;
    boolean pickup;
    final ArrayList<String> list = new ArrayList<String>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentSearchBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        SearchSystem viewModel = ViewModelProviders.of(this).get(SearchSystem.class);

        //검색 버튼
        ListView searchView = (ListView)binding.listviewCamp;
        Button searchButton = (Button) binding.buttonSearch;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchText = (EditText)binding.textSearch;
                String search = searchText.getText().toString();
                viewModel.printSearchItem(searchView,search);

                searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CampingEntity camp= (CampingEntity) parent.getItemAtPosition(position);
                        Intent intent = new Intent(getActivity(), CampInformationActivity.class);
                        intent.putExtra("CampingEntity", camp);
                        startActivity(intent);
                    }
                });


            }
        });

        //정렬 스피너
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item,sortCondition_
        );

        //Spinner spinner = (Spinner) view.findViewById(R.id.spinner_sort);
        Spinner spinner = (Spinner)binding.spinnerSort;
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (sortCondition_[position]){
                    case "리뷰 많은 순":
                        viewModel.sortByReview();
                        break;
                    case "평점 높은 순":
                        viewModel.sortByRating();
                        break;
                    case "가격 낮은 순":
                        viewModel.sortByPrice();
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        Button filter = (Button)binding.filterButton;
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"셔틀 or 픽업 서비스",
                        "바베큐", "온수", "주차장", "Wifi"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("세부정보 선택")
                        .setMultiChoiceItems(items,
                                new boolean[]{false, false, false, false, false},
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) {
                                            Toast.makeText(getActivity(),
                                                    items[which], Toast.LENGTH_SHORT).show();
                                            list.add(items[which]);
                                        } else {
                                            list.remove(items[which]);
                                        }
                                    }
                                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                for (String item : list) {
                                    switch (item){
                                        case "셔틀 or 픽업 서비스":
                                            pickup=true;
                                            break;
                                        case "바베큐":
                                            bbq=true;
                                            break;
                                        case "온수":
                                            water=true;
                                            break;
                                        case "주차장":
                                            parking=true;
                                            break;
                                        case "Wifi":
                                            wifi=true;
                                            break;
                                    }
                                }
                                viewModel.filtering(bbq,parking,pickup,water,wifi);

                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bbq=false;
                                pickup=false;
                                parking=false;
                                wifi=false;
                                water=false;
                            }
                        });
                dialog.create();
                dialog.show();
            }
        });





        return binding.getRoot();
    }



}