package com.example.pspamigosatr.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "call",
        foreignKeys = @ForeignKey(
                entity = Friend.class,
                parentColumns = "id",
                childColumns = "idfriend",
                onDelete = ForeignKey.CASCADE))
public class Call {

    @PrimaryKey(autoGenerate = true)
    private long id;


    @ColumnInfo(name = "idfriend")
    private long idfriend;


    @ColumnInfo(name = "datecall")
    private long datecall;


    public Call(long id, long idfriend, long datecall) {
        this.id = id;
        this.idfriend = idfriend;
        this.datecall = datecall;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdfriend() {
        return idfriend;
    }

    public void setIdfriend(long idfriend) {
        this.idfriend = idfriend;
    }

    public long getDatecall() {
        return datecall;
    }

    public void setDatecall(long datecall) {
        this.datecall = datecall;
    }


    @Override
    public String toString() {
        return "Call{" +
                "id=" + id +
                ", idfriend=" + idfriend +
                ", datecall=" + datecall +
                '}';
    }
}
