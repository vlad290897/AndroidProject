package com.example.vlad.a_fishka;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Vlad on 02.04.2018.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {


    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;

        private ImageView mTitleImage;
        private Context mContext;
        private TextView mContentText;
        private TextView mDateText;


        NewsViewHolder(View itemView) {
            super(itemView);
            mTitleImage =(ImageView)itemView.findViewById(R.id.imageNews);

            mTitle = (TextView)itemView.findViewById(R.id.txtname);
            mContentText = itemView.findViewById(R.id.contentText);
            mDateText = itemView.findViewById(R.id.dateNews);
            mContext = itemView.getContext();
        }
    }

    private ArrayList<String> titleList;
    private ArrayList<String> imgList;
    private ArrayList<String> contentList;
    private ArrayList<String> dateList;


    NewsListAdapter( ArrayList<String> titleList, ArrayList<String> imgList,ArrayList<String> contentList,ArrayList<String> dateList){
        this.titleList = titleList;
        this.imgList = imgList;
        this.contentList = contentList;
        this.dateList = dateList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_list_item, viewGroup, false);
        NewsViewHolder pvh = new NewsViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder newsViewHolder, int i) {
        Picasso.with(newsViewHolder.mContext)
                .load(imgList.get(i))
                .into(newsViewHolder.mTitleImage);

        newsViewHolder.mTitle.setText(titleList.get(i));
        newsViewHolder.mContentText.setText(contentList.get(i));
        newsViewHolder.mDateText.setText(dateList.get(i));

        if (i == titleList.size() - 1) {

            Toast toast = Toast.makeText(newsViewHolder.mContext,
                    "Конец списка", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }
}
