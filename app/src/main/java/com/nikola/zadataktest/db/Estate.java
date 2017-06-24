package com.nikola.zadataktest.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by nikola on 6/24/17.
 */

@DatabaseTable(tableName = Estate.TABLE_NAME_ESTATE)
public class Estate {


    public static final String TABLE_NAME_ESTATE = "estates";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_NAME = "name";
    public static final String FIELD_NAME_DESCRIPTION = "description";
    public static final String FIELD_NAME_ADDRESS = "address";
    public static final String FIELD_NAME_PHONE_NUMBER = "phone_number";
    public static final String FIELD_NAME_QUADRATURE = "quadrature";
    public static final String FIELD_NAME_ROOMS_NUMBER = "rooms_number";
    public static final String FIELD_NAME_PRICE = "price";

//    public static final String FIELD_NAME_IMAGES = "images";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int id;
    @DatabaseField(columnName = FIELD_NAME_NAME)
    private String name;
    @DatabaseField(columnName = FIELD_NAME_DESCRIPTION)
    private String description;
    @DatabaseField(columnName = FIELD_NAME_ADDRESS)
    private String address;
    @DatabaseField(columnName = FIELD_NAME_PHONE_NUMBER)
    private int phoneNumber;
    @DatabaseField(columnName = FIELD_NAME_QUADRATURE)
    private double quadrature;
    @DatabaseField(columnName = FIELD_NAME_ROOMS_NUMBER)
    private int roomsNumber;
    @DatabaseField(columnName = FIELD_NAME_PRICE)
    private double price;


//    ForeignCollection<Image> images


    public Estate(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getQuadrature() {
        return quadrature;
    }

    public void setQuadrature(double quadrature) {
        this.quadrature = quadrature;
    }

    public int getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(int roomsNumber) {
        this.roomsNumber = roomsNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return name;
    }
}
