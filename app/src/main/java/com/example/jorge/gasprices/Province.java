package com.example.jorge.gasprices;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity (indices = @Index(value = "community_id"),

        foreignKeys = @ForeignKey(entity = Community.class,
                                    parentColumns = "id",
                                    childColumns = "community_id"),
            tableName = "province"
)


public class Province {

    @PrimaryKey
    public int id;

    public String name;

    @ColumnInfo (name = "community_id")
    int communityID;

    public Province(){

    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
