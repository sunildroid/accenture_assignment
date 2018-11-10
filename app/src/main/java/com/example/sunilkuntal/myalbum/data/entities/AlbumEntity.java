package com.example.sunilkuntal.myalbum.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "albums",
        indices = {@Index("id")})

public class AlbumEntity implements Comparable<AlbumEntity>
{
    @SerializedName("userId")
    @Expose
    @ColumnInfo(name="userId")
    private Integer userId;

    @SerializedName("id")
    @Expose
    @ColumnInfo(name="id")
    @PrimaryKey
    private Integer id;

    @SerializedName("title")
    @Expose
    @ColumnInfo(name="title")
    private String title;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id='" + id + '\'' +
                ", userid='" + userId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!AlbumEntity.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final AlbumEntity other = (AlbumEntity) obj;
        if ((this.id == null) ? (other.id != null) : !this.title.equals(other.title)) {
            return false;
        }

        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 53 * hash + this.id+this.userId;
        return hash;
    }

    @Override
    public int compareTo(AlbumEntity o) {
        return this.title.compareTo(o.getTitle());
    }
}
