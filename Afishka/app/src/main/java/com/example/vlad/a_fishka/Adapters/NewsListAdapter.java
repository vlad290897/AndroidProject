package com.example.vlad.a_fishka.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vlad.a_fishka.Model.News;
import com.example.vlad.a_fishka.R;
import com.squareup.picasso.Picasso;

import java.util.List;

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
            mTitleImage =itemView.findViewById(R.id.imageNews);

            mTitle = itemView.findViewById(R.id.txtname);
            mContentText = itemView.findViewById(R.id.contentText);
            mDateText = itemView.findViewById(R.id.dateNews);
            mContext = itemView.getContext();
        }
    }

    private List<News> newsList;



    public NewsListAdapter( List<News> newsList){
        this.newsList = newsList;
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
        News news = newsList.get(i);
        Picasso.with(newsViewHolder.mContext)
                .load(news.getImageUrl())
                .into(newsViewHolder.mTitleImage);

        newsViewHolder.mTitle.setText(news.getTitle());
        newsViewHolder.mContentText.setText(news.getContent());
        newsViewHolder.mDateText.setText(news.getDate());

//        if (i == titleList.size() - 1) {
//            Toast toast = Toast.makeText(newsViewHolder.mContext,
//                    "Конец списка(Тут будет догрузка статей)", Toast.LENGTH_LONG);
//            toast.show();
//        }

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
