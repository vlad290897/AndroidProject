package com.example.vlad.a_fishka.Fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.vlad.a_fishka.Adapters.NewsListAdapter;
import com.example.vlad.a_fishka.Model.News;
import com.example.vlad.a_fishka.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {


    public List<News> newsList = new ArrayList<>();

    RecyclerView recyclerView;
    ProgressBar loader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list, container, false);
        recyclerView = view.findViewById(R.id.listrecycler);


        loader = view.findViewById(R.id.loader);

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


                Elements elements = doc.getElementsByAttributeValue("class", "pr cb");
                Elements titles = doc.select(".news_title");
                Elements dates = doc.getElementsByAttributeValue("class", "date mt30");


                for (int i = 0; i < elements.size(); i++) {
                    Element e = elements.get(i);
                    String url = e.select("img").attr("abs:src");
                    String title = titles.get(i).text();
                    String content = e.select("div[class=pr cb]").text();
                    String date = dates.get(i).text();
                    newsList.add(new News(title, content, url, date));
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            // ничего не возвращаем потому что я так захотел)
            return null;
        }

        public void parse() {
            Document doc = (Document) Jsoup.connect("https://www.film.ru/news");
            Elements els = doc.getElementsByAttributeValue("class", "news_title");
            for (Element myEl : els) {
                String str = myEl.text();
                Log.d("myDev", str);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            loader.setVisibility(ProgressBar.INVISIBLE);
            NewsListAdapter adapter = new NewsListAdapter(newsList);
            recyclerView.setAdapter(adapter);
        }
    }


}
