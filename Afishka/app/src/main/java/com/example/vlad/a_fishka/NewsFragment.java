package com.example.vlad.a_fishka;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Vlad on 02.04.2018.
 */

public class NewsFragment extends Fragment {

    // то в чем будем хранить данные пока не передадим адаптеру
    public ArrayList<String> titleList = new ArrayList<String>();
    public ArrayList<String> imgList = new ArrayList<String>();
    public ArrayList<String> contentList = new ArrayList<String>();
    public ArrayList<String> dateList = new ArrayList<String>();
    RecyclerView recyclerView;
    ProgressBar loader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list,container,false);
        recyclerView =(RecyclerView) view.findViewById(R.id.listrecycler);

        // textView =view.findViewById(R.id.txtname);

        loader = view.findViewById(R.id.loader);
//        RVAdapter listAdapter = new RVAdapter();
//        recyclerView.setAdapter(listAdapter);
        new NewThread().execute();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    public class NewThread extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg) {


            Document doc;
            try {

                doc = Jsoup.connect("https://www.film.ru/news").get();
                Elements text = doc.select(".news_title");
                Elements imgTags = doc.select(".item");
                titleList.clear();
                contentList.clear();
                imgList.clear();
                dateList.clear();

                Elements elements = doc.getElementsByAttributeValue("class", "pr cb");
                for (Element e: elements) {
                    imgList.add(e.select("img").attr("abs:src"));
                }
                for(Element title : text){
                    titleList.add(title.text());
                }
                for(Element content:elements){
                    contentList.add(content.text());
                }

                Elements date = doc.getElementsByAttributeValue("class","date mt30");
                for(Element d : date){
                    dateList.add(d.text());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            // ничего не возвращаем потому что я так захотел)
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            loader.setVisibility(ProgressBar.INVISIBLE);
            NewsListAdapter adapter = new NewsListAdapter(titleList,imgList,contentList,dateList);
            recyclerView.setAdapter(adapter);
        }
    }


}
