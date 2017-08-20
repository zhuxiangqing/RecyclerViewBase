package com.zhuxiangqing.recyclerviewbase;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhuxiangqing.recyclerbaselibrary.RecyclerBaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PagersAdapter pagersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pagers);
        pagersAdapter = new PagersAdapter();
        pagersAdapter.setmItemClickListener(new RecyclerBaseAdapter.OnItemClickListener<RecyclerBaseAdapter.RecyclerBaseViewHolder<String>>() {
            @Override
            public void onItemClick(RecyclerBaseAdapter.RecyclerBaseViewHolder<String> holder) {
                Toast.makeText(MainActivity.this, pagersAdapter.getItemData(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(pagersAdapter);
        //这下面的两行代码是为了实现类似ViewPager的效果
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        refresh();
    }

    private void refresh() {
        List<String> imageList = new ArrayList<>();
        imageList.add("https://ws1.sinaimg.cn/large/610dc034ly1fil82i7zsmj20u011hwja.jpg");
        imageList.add("https://ws1.sinaimg.cn/large/610dc034ly1fik2q1k3noj20u00u07wh.jpg");
        imageList.add("https://ws1.sinaimg.cn/large/610dc034ly1fiiiyfcjdoj20u00u0ju0.jpg");
        pagersAdapter.refresh(imageList);
    }


    static class PagersAdapter extends RecyclerBaseAdapter<String> {



        @Override
        public RecyclerBaseViewHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PagerViewHolder(parent, mItemClickListener);
        }

        static class PagerViewHolder extends RecyclerBaseViewHolder<String> {

            private ImageView mImage;

            public PagerViewHolder(ViewGroup parent, OnItemClickListener<RecyclerBaseViewHolder<String>> listener) {
                super(parent, R.layout.item_pager, listener);
                mImage = itemView.findViewById(R.id.image);
            }

            @Override
            protected void onItemDataBind(String itemData) {
                Glide.with(mImage.getContext()).load(itemData).into(mImage);
            }
        }

    }

}
