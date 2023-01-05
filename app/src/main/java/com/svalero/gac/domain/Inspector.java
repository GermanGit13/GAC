package com.svalero.gac.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inspectors") //Es una Entidad de la BBDD
public class Inspector {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long inspector_id;

    @ColumnInfo
    private String name;
    @ColumnInfo
    private String surname;
    @ColumnInfo
    @NonNull
    private String numberLicense;
    @ColumnInfo
    private String dni;
    @ColumnInfo
    private String company;

    public Inspector() {

    }

    public Inspector(long inspector_id, String name, String surname, String numberLicense, String dni, String company) {
        this.inspector_id = inspector_id;
        this.name = name;
        this.surname = surname;
        this.numberLicense = numberLicense;
        this.dni = dni;
        this.company = company;
    }

    public Inspector(String name, String surname, String  numberLicense, String dni, String company) {
        this.name = name;
        this.surname = surname;
        this.numberLicense = numberLicense;
        this.dni = dni;
        this.company = company;
    }

    public long getInspector_id() {
        return inspector_id;
    }

    public void setInspector_id(long inspector_id) {
        this.inspector_id = inspector_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String  getNumberLicense() {
        return numberLicense;
    }

    public void setNumberLicense(String  numberLicense) {
        this.numberLicense = numberLicense;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
