package com.zhuxiangqing.recyclerbaselibrary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhu on 2017/8/20.
 */

/**
 * @param <T> 数据类型
 */
public abstract class RecyclerBaseAdapter<T> extends RecyclerView.Adapter<RecyclerBaseAdapter.RecyclerBaseViewHolder<T>> {
    //数据容器
    private List<T> mDataList;

    //item点击事件监听器
    protected OnItemClickListener<RecyclerBaseAdapter.RecyclerBaseViewHolder<T>> mItemClickListener;


    //构造函数
    public RecyclerBaseAdapter() {
    }

    public RecyclerBaseAdapter(List<T> mDataList) {
        this.mDataList = mDataList;
    }

    public RecyclerBaseAdapter(List<T> mDataList, OnItemClickListener<RecyclerBaseViewHolder<T>> mItemClickListener) {
        this.mDataList = mDataList;
        this.mItemClickListener = mItemClickListener;
    }


    public T getItemData(int position) {
        if (null == mDataList||position >= mDataList.size()) {
            return null;
        }
        return mDataList.get(position);
    }

    //用于刷新数据
    public void refresh(List<T> dataList) {
        if (null == mDataList) {
            mDataList = dataList;
            if (null == mDataList) {
                return;
            }
            notifyDataSetChanged();
            return;
        }
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    //用于添加单个数据
    public void addData(T data) {
        if (null == data) {
            return;
        }
        if (null == mDataList) {
            mDataList = new ArrayList<>();
        }
        mDataList.add(data);
        notifyDataSetChanged();
    }

    //用于添加多条数据
    public void addAll(List<T> dataList) {
        if (null == dataList) {
            return;
        }
        if (null == mDataList) {
            mDataList = new ArrayList<>();
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    //用于获取Adapter中的数据
    public List<T> getmDataList() {
        return mDataList;
    }

    //设置点击事件
    public void setmItemClickListener(OnItemClickListener<RecyclerBaseViewHolder<T>> mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    @Override
    public void onBindViewHolder(RecyclerBaseViewHolder<T> holder, int position) {
        holder.onItemDataBind(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        //mDataList可能存在为空的情况 比如使用无参构造方法 而未调用refresh和add方法
        return null == mDataList ? 0 : mDataList.size();
    }

    /**
     * ViewHolder 基类
     *
     * @param <T> 数据类型
     */
    public abstract static class RecyclerBaseViewHolder<T> extends RecyclerView.ViewHolder {

        public RecyclerBaseViewHolder(ViewGroup parent, int layoutResId, final OnItemClickListener<RecyclerBaseViewHolder<T>> listener) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != listener) {
                        listener.onItemClick(RecyclerBaseViewHolder.this);
                    }
                }
            });
        }

        protected abstract void onItemDataBind(T itemData);
    }

    /**
     * 点击事件
     *
     * @param <T> holder
     */
    public interface OnItemClickListener<T extends RecyclerBaseAdapter.RecyclerBaseViewHolder> {
        void onItemClick(T holder);
    }
}
