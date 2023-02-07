package com.example.mongocrud_v3_;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.BufferedWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class AddElementController implements Initializable {

    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtCarroceria;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtAnio;

    @FXML
    private Button btnClear;
    @FXML
    private Button btnAnadir;

    private final static String Host = "localhost";
    private final static int Port = 27017;


    private boolean update;
    Coches coche = null;
    private int CocheID;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void Anadir(ActionEvent event) {
        try {
            // Creamos la conexión con mongo
            MongoClient coches = new MongoClient(Host, Port);
            MongoDatabase mongodatabase = coches.getDatabase("miBBDD");
            // Coger la coleccion
            MongoCollection coleccion = mongodatabase.getCollection("Coches");

            String marca = txtMarca.getText();
            String modelo = txtModelo.getText();
            String carroceria = txtCarroceria.getText();
            int precio = Integer.parseInt(txtPrecio.getText());
            int anio = Integer.parseInt(txtAnio.getText());

            // Recoger los datos del formulario y meterlos en un objeto tipo Document
            Document doc = new Document("marca", marca).append("modelo", modelo).append("carroceria", carroceria)
                    .append("precio", precio).append("anio", anio);

            // Guardamos el documento
            // Lo que hemos hecho ha sido un documento nuevo como lo de emp1 = {empNo: 5432, apellido: "sala"}...
            // Y posteriormente lo guardamos dentro de nuestra colección (Empresa)

            updateSelectedElement(); // Si es un registro nuevo añade uno nuevo, si hemos dado al botón de editar, rellenará los campos y los podremos editar.
            setTextField(marca, modelo, carroceria, precio, anio);
            Clear();


            coleccion.insertOne(doc);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("HA OCURRIDO UN ERROR GUARDANDO EL COCHE");
        }


    }

    @FXML
    private void Clear() {

        txtMarca.setText("");
        txtModelo.setText("");
        txtCarroceria.setText("");
        txtPrecio.setText("");
        txtAnio.setText("");
    }


    private void updateSelectedElement(){
        MongoClient coches = new MongoClient(Host, Port);
        MongoDatabase mongodatabase = coches.getDatabase("miBBDD");
        // Coger la coleccion
        MongoCollection coleccion = mongodatabase.getCollection("Coches");
        if(update == false){
            // Si update es falso, es decir que es un elemento nuevo. Pues lo insertamos
            try {
                // Creamos la conexión con mongo


                String marca = txtMarca.getText();
                String modelo = txtModelo.getText();
                String carroceria = txtCarroceria.getText();
                int precio = Integer.parseInt(txtPrecio.getText());
                int anio = Integer.parseInt(txtAnio.getText());

                // Recoger los datos del formulario y meterlos en un objeto tipo Document
                Document doc = new Document("marca", marca).append("modelo", modelo).append("carroceria", carroceria)
                        .append("precio", precio).append("anio", anio);

                // Guardamos el documento
                // Lo que hemos hecho ha sido un documento nuevo como lo de emp1 = {empNo: 5432, apellido: "sala"}...
                // Y posteriormente lo guardamos dentro de nuestra colección (Empresa)
                coleccion.insertOne(doc);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.printf("HA OCURRIDO UN ERROR GUARDANDO EL COCHE");
            }


        }else{
            // Pero si update es true entonces el registro ya existe y solamente tenemos que cambiar los valores pero manteniendo el id, que nisiquiera se puede cambiar
            // Hacemos el filtro  y borramos el elemento de la coleccion
            Bson filter = Filters.eq("modelo", txtModelo.getText());
            Document doc = new Document("marca", txtMarca).append("modelo", txtModelo).append("carroceria", txtCarroceria)
                    .append("precio", txtPrecio).append("anio", txtAnio);

            coleccion.updateOne(filter, doc);

        }
    }

    void setUpdate(boolean b){
        this.update = b;
    }

    // Metodo para rellenar los campos con lo que nos pasen de la tabla
    void setTextField(String marca, String modelo, String carroceria, int precio, int anio){
        txtMarca.setText(marca);
        txtModelo.setText(modelo);
        txtCarroceria.setText(carroceria);
        txtPrecio.setText(String.valueOf(precio));
        txtAnio.setText(String.valueOf(anio));
    }

}
