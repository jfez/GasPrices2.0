package com.example.jorge.gasprices;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = "province_id"),

        foreignKeys = @ForeignKey(entity = Province.class,
                parentColumns = "id",
                childColumns = "province_id"),
        tableName = "town"
)

public class Town implements Parcelable {

    @PrimaryKey
    public int id;

    public String name;

    protected Town(Parcel in) {
        id = in.readInt();
        name = in.readString();
        provinceID = in.readInt();
    }

    public static final Creator<Town> CREATOR = new Creator<Town>() {
        @Override
        public Town createFromParcel(Parcel in) {
            return new Town(in);
        }

        @Override
        public Town[] newArray(int size) {
            return new Town[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(provinceID);
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @ColumnInfo(name = "province_id")
    int provinceID;

    public Town(){

    }

    public Town(int id, String name, int provinceID) {
        this.id = id;
        this.name = name;
        this.provinceID = provinceID;
    }


}
