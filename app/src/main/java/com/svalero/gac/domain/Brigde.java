package com.svalero.gac.domain;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "brigdes")
public class Brigde {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long brigde_id;

    @ColumnInfo
    private String name;
    @ColumnInfo
    private String country;
    @ColumnInfo
    private String city;
    @ColumnInfo
    private String yearBuild;
    @ColumnInfo
    private double latitude; //para poder ubicar en el mapa
    @ColumnInfo
    private double longitude; //para poder ubicar en el mapa
    @ColumnInfo
    @NonNull
    private int numberVain;
    @ColumnInfo
    private int numberStapes;
    @ColumnInfo
    private String platform;

    public Brigde() {

    }

    public Brigde(String name, String country, String city, String yearBuild, double latitude, double longitude, int numberVain, int numberStapes, String platform) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.yearBuild = yearBuild;
        this.latitude = latitude;
        this.longitude = longitude;
        this.numberVain = numberVain;
        this.numberStapes = numberStapes;
        this.platform = platform;
    }

    public long getBrigde_id() {
        return brigde_id;
    }

    public void setBrigde_id(long brigde_id) {
        this.brigde_id = brigde_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYearBuild() {
        return yearBuild;
    }

    public void setYearBuild(String yearBuild) {
        this.yearBuild = yearBuild;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getNumberVain() {
        return numberVain;
    }

    public void setNumberVain(int numberVain) {
        this.numberVain = numberVain;
    }

    public int getNumberStapes() {
        return numberStapes;
    }

    public void setNumberStapes(int numberStapes) {
        this.numberStapes = numberStapes;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
