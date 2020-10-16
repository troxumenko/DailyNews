package com.dn.dailynews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.dn.dailynews.views.AllNewsView;
import com.dn.dailynews.views.BusinessNewsView;
import com.dn.dailynews.views.EntertainmentNewsView;
import com.dn.dailynews.views.HealthNewsView;
import com.dn.dailynews.views.SportNewsView;
import com.dn.dailynews.views.TechnologyNewsView;
import com.dn.dailynews.views.WorldNewsView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WorldNewsView worldNewsView;
    private AllNewsView allNewsView;
    private TechnologyNewsView technologyNewsView;
    private SportNewsView sportNewsView;
    private HealthNewsView healthNewsView;
    private EntertainmentNewsView entertainmentNewsView;
    private BusinessNewsView businessNewsView;

    @BindView(R.id.content_layout)
    FrameLayout mContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_all_news);

        mContainer.removeAllViews();
        allNewsView = allNewsView != null ? allNewsView : new AllNewsView(this);
        mContainer.addView(allNewsView);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_world_news) {
            mContainer.removeAllViews();
            worldNewsView = worldNewsView != null ? worldNewsView : new WorldNewsView(this);
            mContainer.addView(worldNewsView);

        } else if (id == R.id.nav_all_news) {
            mContainer.removeAllViews();
            allNewsView = allNewsView != null ? allNewsView : new AllNewsView(this);
            mContainer.addView(allNewsView);

        } else if (id == R.id.nav_technology) {
            mContainer.removeAllViews();
            technologyNewsView = technologyNewsView != null ? technologyNewsView : new TechnologyNewsView(this);
            mContainer.addView(technologyNewsView);

        } else if (id == R.id.nav_sports) {
            mContainer.removeAllViews();
            sportNewsView = sportNewsView != null ? sportNewsView : new SportNewsView(this);
            mContainer.addView(sportNewsView);

        } else if (id == R.id.nav_entertainment) {
            mContainer.removeAllViews();
            entertainmentNewsView = entertainmentNewsView != null ? entertainmentNewsView : new EntertainmentNewsView(this);
            mContainer.addView(entertainmentNewsView);

        } else if (id == R.id.nav_business) {
            mContainer.removeAllViews();
            businessNewsView = businessNewsView != null ? businessNewsView : new BusinessNewsView(this);
            mContainer.addView(businessNewsView);

        } else if (id == R.id.nav_health) {
            mContainer.removeAllViews();
            healthNewsView = healthNewsView != null ? healthNewsView : new HealthNewsView(this);
            mContainer.addView(healthNewsView);

        } else if (id == R.id.nav_settings) {
            SettingsActivity.startActivity(this);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.activity_main_menu_settings) {
            SettingsActivity.startActivity(this);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
