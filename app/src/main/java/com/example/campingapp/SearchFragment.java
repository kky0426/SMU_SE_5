package com.example.campingapp;

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

import com.example.campingapp.databinding.FragmentSearchBinding;

import java.io.Serializable;


public class SearchFragment extends Fragment {
    String[] sortCondition_ = {"정렬","리뷰 많은 순", "평점 높은 순", "가격 낮은 순"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_search,container,false);
        FragmentSearchBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        //View view = binding.getRoot();

        SearchSystem viewModel = ViewModelProviders.of(this).get(SearchSystem.class);
        //검색 버튼

        //ListView searchView = (ListView)view.findViewById(R.id.listview_camp);
        ListView searchView = (ListView)binding.listviewCamp;
        //Button searchButton = (Button) view.findViewById(R.id.button_search);
        Button searchButton = (Button) binding.buttonSearch;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText searchText = (EditText)view.findViewById(R.id.text_search);
                EditText searchText = (EditText)binding.textSearch;
                String search = searchText.getText().toString();
                viewModel.printSearchItem(searchView,search);

                searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CampingEntity camp= (CampingEntity) parent.getItemAtPosition(position);
                        Intent intent = new Intent(getActivity(),CampInformationActivity.class);
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






        return binding.getRoot();
    }



}