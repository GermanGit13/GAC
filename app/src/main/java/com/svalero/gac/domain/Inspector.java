package com.svalero.gac.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "inspectors") //Es una Entidad de la BBDD
public class Inspector {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int inspector_id;

    @ColumnInfo
    private String name;
    @ColumnInfo
    private String surname;
    @ColumnInfo
    @NonNull
    private int numberLicense;
    @ColumnInfo
    private String dni;
    @ColumnInfo
    private String company;

    public Inspector(){

    }

    public Inspector(int inspector_id, String name, String surname, int numberLicense, String dni, String company) {
        this.inspector_id = inspector_id;
        this.name = name;
        this.surname = surname;
        this.numberLicense = numberLicense;
        this.dni = dni;
        this.company = company;
    }

    public int getId() {
        return inspector_id;
    }

    public void setId(int id) {
        this.inspector_id = id;
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

    public int getNumberLicense() {
        return numberLicense;
    }

    public void setNumberLicense(int numberLicense) {
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
