package com.example.sunilkuntal.myalbum;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;
import com.example.sunilkuntal.myalbum.ui.feature.home.AlbumScreen;
import com.example.sunilkuntal.myalbum.ui.feature.home.AlbumViewModel;
import com.example.sunilkuntal.myalbum.ui.feature.home.ListItemClickable;
import com.example.sunilkuntal.myalbum.ui.feature.home.MyAlbumAdapter;

import java.util.List;

public class AlbumGridActivity extends AppCompatActivity implements AlbumScreen, ListItemClickable {
    private static final String TAG = AlbumGridActivity.class.getSimpleName();
    private AlbumViewModel mViewModel;
    private ProgressBar mProgress;
    private RecyclerView listViewAlbum;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyAlbumAdapter mAdapter;

    private final Observer<List<AlbumEntity>> dataObserver = new Observer<List<AlbumEntity>>() {
        @Override
        public void onChanged(@Nullable List<AlbumEntity> albums) {
            if (mProgress != null)
                mProgress.setVisibility(View.GONE);
            updateData(albums);
        }
    };


    private final Observer<String> errorObserver = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String error) {
            setError(error);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_grid);
        bindViews();
        mViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        mViewModel.getAlbumData().observe(this, dataObserver);
        mViewModel.getErrorUpdates().observe(this, errorObserver);
        if (savedInstanceState == null) {
            mProgress.setVisibility(View.VISIBLE);
            mViewModel.fetchData();
        }

    }


    private void bindViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        listViewAlbum = findViewById(R.id.recView);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        mProgress = findViewById(R.id.progress);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "\tNot fetching from network because interval didn't reach");
                mSwipeRefreshLayout.setRefreshing(true);
                mViewModel.fetchData();
            }
        });
        mAdapter = new MyAlbumAdapter(this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        listViewAlbum.setLayoutManager(lm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listViewAlbum.getContext(),
                lm.getOrientation());
        listViewAlbum.addItemDecoration(dividerItemDecoration);
        listViewAlbum.setAdapter(mAdapter);
        setSupportActionBar(toolbar);
    }

    private void callDetailScreen(int id) {
        Intent intent = new Intent(AlbumGridActivity.this, AlbumDetailsActivity.class);
        intent.putExtra(AlbumDetailsActivity.KEY_SELECTED_ID, id);
        startActivity(intent);
    }

    @Override
    public void updateData(List<AlbumEntity> data) {
        mAdapter.setItems(data);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setError(String msg) {
        Toast.makeText(this, "Error:" + msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleListItemClick(int selectedId) {
        callDetailScreen(selectedId);
    }
}
