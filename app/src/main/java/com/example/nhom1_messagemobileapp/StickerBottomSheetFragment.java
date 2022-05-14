package com.example.nhom1_messagemobileapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.nhom1_messagemobileapp.adapter.StickerAdapter;
import com.example.nhom1_messagemobileapp.entity.StickerPackage;
import com.example.nhom1_messagemobileapp.utils.FlaticonAPI;
import com.example.nhom1_messagemobileapp.utils.GridSpacingItemDecoration;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StickerBottomSheetFragment extends BottomSheetDialogFragment {
    private ViewPager mPager;
    private View view;

    private Context context;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private StickerAdapter recyclerAdapter;
    private List<StickerPackage> packages;
    private LinearLayout viewSearching;
    private LinearLayout viewContent;

    public StickerBottomSheetFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerAdapter = new StickerAdapter(getContext());

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 20, true));

        viewSearching = view.findViewById(R.id.view_searching);
        viewContent = view.findViewById(R.id.view_content);

        ShowPackagesTask getPackagesTask = new ShowPackagesTask();
        getPackagesTask.execute();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showSticker(packages.get(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        return view;
    }

    public void viewLoading(){
//        viewError.setVisibility(View.INVISIBLE);
        viewContent.setVisibility(View.INVISIBLE);
        viewSearching.setVisibility(View.VISIBLE);
    }

    public void viewContent(){
//        viewError.setVisibility(View.INVISIBLE);
        viewContent.setVisibility(View.VISIBLE);
        viewSearching.setVisibility(View.INVISIBLE);
    }

    private void showSticker(StickerPackage stickerPackage){

        ShowStickersTask showStickersTask = new ShowStickersTask(stickerPackage.getUrl());
        showStickersTask.execute();
    }

    public class ShowPackagesTask extends AsyncTask<String, String, List<StickerPackage>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            viewLoading();
        }

        @Override
        protected List<StickerPackage> doInBackground (String...params){
            FlaticonAPI flaticonApi = new FlaticonAPI();
            return flaticonApi.getPackages();
        }

        @Override
        protected void onPostExecute (List<StickerPackage> packages){
            StickerBottomSheetFragment.this.packages = packages;
            Log.e("Sticker packages", packages.toString());
            for(int i=0; i<packages.size(); i++){
                tabLayout.addTab(tabLayout.newTab());
                View _view = getLayoutInflater().inflate(R.layout.tab_custom,null);
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                ImageView img = _view.findViewById(R.id.icon);
                Picasso.get().load(packages.get(i).getSprite()).into(img);
                if(tab!=null) tab.setCustomView(_view);
            }
            viewContent();
            showSticker(packages.get(0));
        }
    }

    public class ShowStickersTask extends AsyncTask<String, String, List<String>> {

        private String url;

        public ShowStickersTask (String url){
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            viewLoading();
        }

        @Override
        protected List<String> doInBackground (String...params){
            FlaticonAPI flaticonApi = new FlaticonAPI();
            return flaticonApi.getStickers(url);
        }

        @Override
        protected void onPostExecute (List<String> stickers){
            Log.e("Stickers", stickers.toString());
            recyclerAdapter.setStickerList(stickers);
            viewContent();
        }
    }
}