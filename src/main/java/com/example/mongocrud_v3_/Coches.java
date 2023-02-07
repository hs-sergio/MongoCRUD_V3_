package com.example.mongocrud_v3_;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Coches {


    private final SimpleStringProperty marca;
    private final SimpleStringProperty modelo;
    private final SimpleStringProperty carroceria;
    private final SimpleIntegerProperty precio;
    private final SimpleIntegerProperty anio;


    public Coches(String marca, String modelo, String carroceria, Integer precio, Integer anio){

        this.marca = new SimpleStringProperty(marca);
        this.modelo = new SimpleStringProperty(modelo);
        this.carroceria = new SimpleStringProperty(carroceria);
        this.precio = new SimpleIntegerProperty(precio);
        this.anio = new SimpleIntegerProperty(anio);

    }

    public String getMarca() {
        return marca.get();
    }

    public SimpleStringProperty marcaProperty() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca.set(marca);
    }

    public String getModelo() {
        return modelo.get();
    }

    public SimpleStringProperty modeloProperty() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo.set(modelo);
    }

    public String getCarroceria() {
        return carroceria.get();
    }

    public SimpleStringProperty carroceriaProperty() {
        return carroceria;
    }

    public void setCarroceria(String carroceria) {
        this.carroceria.set(carroceria);
    }

    public int getPrecio() {
        return precio.get();
    }

    public SimpleIntegerProperty precioProperty() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio.set(precio);
    }

    public int getAnio() {
        return anio.get();
    }

    public SimpleIntegerProperty anioProperty() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio.set(anio);
    }
}
