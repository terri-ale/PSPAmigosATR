package com.example.pspamigosatr.model.entity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "friend")
public class Friend {

    @PrimaryKey
    private long id;


    @NonNull
    @ColumnInfo(name = "name")
    private String name;


    @NonNull
    @ColumnInfo(name = "phone")
    private String phone;



    @Nullable
    @ColumnInfo(name = "birthdate")
    private long birthdate;


    public Friend(long id, String name, String phone, long birthdate) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.phone = phone;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
