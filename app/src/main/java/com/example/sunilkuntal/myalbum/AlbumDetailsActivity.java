package com.example.sunilkuntal.myalbum;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;
import com.example.sunilkuntal.myalbum.ui.feature.details.AlbumDetailScreen;
import com.example.sunilkuntal.myalbum.ui.feature.details.AlbumDetailViewModel;
import com.example.sunilkuntal.myalbum.ui.feature.details.AlbumDetailViewModelFactory;

public class AlbumDetailsActivity extends AppCompatActivity implements AlbumDetailScreen {
    private static final String TAG = AlbumDetailsActivity.class.getSimpleName();
    private AlbumDetailViewModel mViewModel;
    private ProgressBar mProgress;

    public static final String KEY_SELECTED_ID = "SelectedAlbum";

    TextView tvID;
    TextView tvUserId;
    TextView tvTitle;
    int selectedAlbumID;

    private final Observer<AlbumEntity> dataObserver = new Observer<AlbumEntity>() {
        @Override
        public void onChanged(@Nullable AlbumEntity album) {
            if (mProgress != null)
                mProgress.setVisibility(View.GONE);
            updateData(album);
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
        selectedAlbumID = getIntent().getIntExtra(KEY_SELECTED_ID, -1);
        setContentView(R.layout.activity_album_detail);
        bindViews();
       // mViewModel = ViewModelProviders.of(this,new MyselectedAlbumID).get(AlbumDetailViewModel.class);
        mViewModel = ViewModelProviders.of(this, new AlbumDetailViewModelFactory(this.getApplication(), selectedAlbumID)).get(AlbumDetailViewModel.class);
        mViewModel.getAlbumData().observe(this, dataObserver);
        mViewModel.getErrorUpdates().observe(this, errorObserver);
        if (selectedAlbumID >= 0 && savedInstanceState == null) {//Considering id is positive integer
            mProgress.setVisibility(View.VISIBLE);
            mViewModel.fetchData(selectedAlbumID);
        }
    }


    private void bindViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        tvID = findViewById(R.id.tvID);
        tvTitle = findViewById(R.id.tvTitle);
        tvUserId = findViewById(R.id.tvUserId);
        mProgress = findViewById(R.id.progress);
        setSupportActionBar(toolbar);
    }

    @Override
    public void updateData(AlbumEntity data) {
        if (data != null) {
            tvUserId.setText(getResources().getString(R.string.userID, data.getUserId()));
            tvTitle.setText(getResources().getString(R.string.title, data.getTitle()));
            tvID.setText(getResources().getString(R.string.id, data.getId()));
        }
    }

    @Override
    public void setError(String msg) {
        Toast.makeText(this, "Error:" + msg, Toast.LENGTH_LONG).show();
    }
}
