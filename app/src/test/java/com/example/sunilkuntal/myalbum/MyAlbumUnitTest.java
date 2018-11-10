package com.example.sunilkuntal.myalbum;

import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MyAlbumUnitTest {

    List<AlbumEntity> albums;

    @Before
    public void setup() {
        albums = new ArrayList<>();
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setTitle("BBB");
        AlbumEntity albumEntity2 = new AlbumEntity();
        albumEntity2.setTitle("AAA");
        albums.add(0, albumEntity);
        albums.add(1, albumEntity2);
        Collections.sort(albums);
    }

    @Test
    public void sorting_isSortedByTitle() {
        assertTrue(albums.get(0).getTitle().compareTo(albums.get(1).getTitle())<0);
    }
}