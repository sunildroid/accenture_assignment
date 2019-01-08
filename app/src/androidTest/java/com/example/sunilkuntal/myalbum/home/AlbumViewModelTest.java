package com.example.sunilkuntal.myalbum.home;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.sunilkuntal.myalbum.R;
import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;
import com.example.sunilkuntal.myalbum.data.repository.AlbumRepositoryImpl;
import com.example.sunilkuntal.myalbum.ui.feature.home.AlbumViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.sunilkuntal.myalbum.home.RecyclerViewItemCountAssertion.withItemCount;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class AlbumViewModelTest  {

    private AlbumRepositoryImpl repo;
    private AlbumViewModel mViewModel;
    private static final int NUM_OF_ALBUMS=5;
    @Mock
    private Observer< List<AlbumEntity>> observer;
    private List<AlbumEntity> randomAlbums;


    @Before
    public void init() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        Context context = InstrumentationRegistry.getTargetContext();
        repo = AlbumRepositoryImpl.createImpl(context);
        mViewModel = new AlbumViewModel((Application) context.getApplicationContext(), repo);
        randomAlbums=createRandomAlbums();

    }


    @Test
    public void testRandomAlbumsWithObserver() throws InterruptedException {
        CountDownLatch latch=new CountDownLatch(1);
        mViewModel.getAlbumData().observeForever(observer);
        repo.deleteAllAlbums();
        repo.insertAllAlbums(randomAlbums);
        latch.await(5000, TimeUnit.MILLISECONDS);
        verify(observer,atLeastOnce()).onChanged(randomAlbums);
    }



    @Test
    public void testItemCount(){
        onView(withId(R.id.recView)).check(withItemCount(NUM_OF_ALBUMS));
    }


    private List<AlbumEntity> createRandomAlbums() {
        List<AlbumEntity> albums = new ArrayList<>();
        AlbumEntity entity;
        for (int i = 0; i < NUM_OF_ALBUMS; i++) {
            AlbumEntity albumEntity =new AlbumEntity();
            albumEntity.setId(i);
            albumEntity.setUserId(i+NUM_OF_ALBUMS);
            albumEntity.setTitle("Title"+i);
            albums.add(albumEntity);
        }
        return albums;
    }



}
