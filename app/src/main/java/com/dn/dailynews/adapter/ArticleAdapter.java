package com.dn.dailynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dn.dailynews.DetailActivity;
import com.dn.dailynews.R;
import com.dn.dailynews.model.Article;
import com.dn.dailynews.utils.ISO8601DateParser;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<Article> articles = new ArrayList<>();
    private Context context;

    public ArticleAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Article> list, boolean isRefresh) {
        if(isRefresh) {
            articles.clear();
        }
        articles.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder viewHolder, int i) {

        viewHolder.title.setText(articles.get(i).getTitle());

        Date date = null;
        try {
            date = ISO8601DateParser.parse(articles.get(i).getPublishedAt());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM • HH:mm");
        viewHolder.publishedAt.setText(simpleDateFormat.format(date.getTime()));

        Picasso.get()
                .load(articles.get(i).getUrlToImage())
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.urlToImage);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, description, url;
        private ImageView urlToImage;
        private TextView publishedAt;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.all_news_title);
            description = (TextView) itemView.findViewById(R.id.detail_description);
            url = (TextView) itemView.findViewById(R.id.url_button);
            publishedAt = (TextView) itemView.findViewById(R.id.all_news_time);
            urlToImage = (ImageView) itemView.findViewById(R.id.all_news_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("title", articles.get(pos).getTitle());
                        intent.putExtra("description", articles.get(pos).getDescription());
                        intent.putExtra("url", articles.get(pos).getUrl());
                        intent.putExtra("publishedAt", articles.get(pos).getPublishedAt());
                        intent.putExtra("urlToImage", articles.get(pos).getUrlToImage());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

}


