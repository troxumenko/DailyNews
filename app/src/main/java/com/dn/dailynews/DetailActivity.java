package com.dn.dailynews;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dn.dailynews.utils.ISO8601DateParser;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_title)
    TextView mTitle;
    @BindView(R.id.detail_time)
    TextView time;
    @BindView(R.id.detail_description)
    TextView mDescription;
    @BindView(R.id.detail_image)
    ImageView imageView;
    @BindView(R.id.url_button)
    Button mUrl;
    @BindView(R.id.detail_toolbar)
    Toolbar detailToolbar;
    @BindView(R.id.detail_fab)
    FloatingActionButton floatingActionButton;

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, DetailActivity.class)/*, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle()*/);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(detailToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String title = getIntent().getExtras().getString("title");
        String publishedAt = getIntent().getExtras().getString("publishedAt");
        String url = getIntent().getExtras().getString("url");
        String urlToImage = getIntent().getExtras().getString("urlToImage");
        String description = getIntent().getExtras().getString("description");

        mUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        mTitle.setText(title);
        mDescription.setText(description);

        Date date = null;
        try {
            date = ISO8601DateParser.parse(publishedAt);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM • HH:mm");
        time.setText(simpleDateFormat.format(date.getTime()));

        Picasso.get()
                .load(urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(imageView);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createShare();
                startActivity(Intent.createChooser(createShare(), "Виберіть отримувача..."));
            }
        });
    }

    private Intent createShare() {
        String title = getIntent().getExtras().getString("title");
        String url = getIntent().getExtras().getString("url");
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText("Заголовок: " + title + ", джерело: " + url)
                .getIntent();
        return shareIntent;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
