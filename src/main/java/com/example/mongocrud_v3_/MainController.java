package com.example.mongocrud_v3_;


import com.mongodb.client.model.Filters;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import java.util.Collections;

public class MainController implements Initializable {


    @FXML
    private TableView<Coches> tableCoches;


    @FXML
    private TableColumn<Coches, String> colMarca;
    @FXML
    private TableColumn<Coches, String> colModelo;
    @FXML
    private TableColumn<Coches, String> colCarroceria;

    @FXML
    private TableColumn<Coches, Integer> colPrecio;
    @FXML
    private TableColumn<Coches, Integer> colAnio;

    @FXML
    private TableColumn colEdit;

    @FXML
    private Button btnTabla;
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnModificar;


    private String marca;
    private String modelo;
    private String carroceria;
    private int precio;
    private int anio;


    // Declaramos host y port para la conexión
    private final static String Host = "localhost";
    private final static int Port = 27017;

    // Creamos un primaryStage
    Stage primaryStage = new Stage();

    // Creamos una lista observable para guardar los objetos empleados de mongo en una clase.

    public ObservableList<Coches> lista;
    public List coches = new ArrayList();


    MongoClient mongoCliente = new MongoClient(Host, Port);

    // Buscamos la colección dentro de la base de datos y creamos el cursor
    MongoDatabase mongoDatabase = mongoCliente.getDatabase("miBBDD");

    // Creamos un cursor de la coleccion
    MongoCollection coleccion = mongoDatabase.getCollection("Coches");
    MongoCursor<Document> cursor = coleccion.find().iterator();
    private StringConverter<java.lang.Integer> Integer; // No recuerdo que era esto, esta en el otro crud


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        tableCoches.setEditable(true);

        try {

            for (int i = 0; i < coleccion.countDocuments(); i++) {
                // Aqui solo recogemos los valores de la bbdd
                int posicion = i + 1;

                // Creamos el cursor
                Document doc = cursor.next();
                marca = doc.getString("marca");
                modelo = doc.getString("modelo");
                carroceria = doc.getString("carroceria");
                precio = doc.getInteger("precio");
                anio = doc.getInteger("anio");

                coches.add(new Coches(marca, modelo, carroceria, precio, anio));

            }
            lista = FXCollections.observableArrayList(coches); // Tampoco recuerdo que hacía exactamente esto.

        } finally {
            cursor.close();
        }
        // Este fragmento realmente es para recoger los datos de la bbdd según se abra la aplicación

        setTable(); // Este método es el que rellena la tabla cada vez que abrimos la aplicación.
        loadData();



    }




    private void setTable() {
        // Para ser capaces de hacer doble click encima de un campo y modificarlo hay que hacer esto.
        // Si no nos interesa modificar un campo, por ejemplo un id no lo haremos. En este caso lo haremos porque podemos modificar todo.

        colMarca.setCellFactory(TextFieldTableCell.forTableColumn());
        colMarca.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Coches, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Coches, String> event) {
                ((Coches) event.getTableView().getItems().get(event.getTablePosition().getRow())).setMarca(event.getNewValue());
            }
        });

        colModelo.setCellFactory(TextFieldTableCell.forTableColumn());
        colModelo.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Coches, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Coches, String> event) {
                ((Coches) event.getTableView().getItems().get(event.getTablePosition().getRow())).setModelo(event.getNewValue());
            }
        });

        colCarroceria.setCellFactory(TextFieldTableCell.forTableColumn());
        colCarroceria.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Coches, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Coches, String> event) {
                ((Coches) event.getTableView().getItems().get(event.getTablePosition().getRow())).setCarroceria(event.getNewValue());
            }
        });

        colPrecio.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); // Para convertir los Integer en String
        colPrecio.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Coches, java.lang.Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Coches, java.lang.Integer> event) {
                ((Coches) event.getTableView().getItems().get(event.getTablePosition().getRow())).setPrecio(event.getNewValue());
            }
        });

        colAnio.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); // Para convertir los Integer en String
        colAnio.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Coches, java.lang.Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Coches, java.lang.Integer> event) {
                ((Coches) event.getTableView().getItems().get(event.getTablePosition().getRow())).setAnio(event.getNewValue());
            }
        });


        // Ponemos los valores dentro de la tabla.
        colMarca.setCellValueFactory(new PropertyValueFactory<Coches, String>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<Coches, String>("modelo"));
        colCarroceria.setCellValueFactory(new PropertyValueFactory<Coches, String>("carroceria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Coches, Integer>("precio"));
        colAnio.setCellValueFactory(new PropertyValueFactory<Coches, Integer>("anio"));

        tableCoches.setItems(lista);
    }


    @FXML
    private void refreshTable() {

            coches.clear();  // Limpiamos la tabla completamente.
            // TODO: Hay que crear un cursor nuevo para que recorra la lista de nuevo porque el cursor que se crea primero se cierra.
            // Creamos un nuevo cursor
            MongoCollection coleccion = mongoDatabase.getCollection("Coches");
            MongoCursor<Document> cursor = coleccion.find().iterator();


        try {

            for (int i = 0; i < coleccion.countDocuments(); i++) {
                // Aqui solo recogemos los valores de la bbdd
                int posicion = i + 1;

                // Creamos el cursor
                Document doc = cursor.next();
                marca = doc.getString("marca");
                modelo = doc.getString("modelo");
                carroceria = doc.getString("carroceria");
                precio = doc.getInteger("precio");
                anio = doc.getInteger("anio");

                coches.add(new Coches(marca, modelo, carroceria, precio, anio));

            }
            lista = FXCollections.observableArrayList(coches); // Tampoco recuerdo que hacía exactamente esto.

        } finally {
            cursor.close();
        }

        setTable(); // Llamamos a setTable para que rellene la tabla con los nuevos datos recogidos.

    }

    @FXML
    private void getAddView() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("addElement.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void print() {

    }

    private void loadData() {

        // Conexión con mongo
        try {
            // Creamos la conexión con mongo
            MongoClient coches = new MongoClient(Host, Port);
            MongoDatabase mongodatabase = coches.getDatabase("miBBDD");
            // Coger la coleccion
            MongoCollection coleccion = mongodatabase.getCollection("Coches");

            // Llamamos al método refreshData() para que salgan todos los resultados, incluso los que acabemos de añadir.
            // Ponemos los valores dentro de la tabla.
            colMarca.setCellValueFactory(new PropertyValueFactory<Coches, String>("marca"));
            colModelo.setCellValueFactory(new PropertyValueFactory<Coches, String>("modelo"));
            colCarroceria.setCellValueFactory(new PropertyValueFactory<Coches, String>("carroceria"));
            colPrecio.setCellValueFactory(new PropertyValueFactory<Coches, Integer>("precio"));
            colAnio.setCellValueFactory(new PropertyValueFactory<Coches, Integer>("anio"));

            // Añadimos el botón de editar en la columna EDIT
            Callback<TableColumn<Coches, String>, TableCell<Coches, String>> cellFoctory = (TableColumn<Coches, String> param) -> {
                // make cell containing buttons
                final TableCell<Coches, String> cell = new TableCell<Coches, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        //that cell created only on non-empty rows
                        if (empty) {
                            setGraphic(null);
                            setText(null);

                        } else {

                            Button deleteIcon = new Button();
                            Button editIcon = new Button();

                            deleteIcon.setText("BORRAR");
                            deleteIcon.setStyle("-fx-fill:#ff1744;");

                            editIcon.setText("EDITAR");

                            editIcon.setStyle("-fx-fill:#00E676;");

                            deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                                try {
                                    Coches cocheSeleccionado = tableCoches.getSelectionModel().getSelectedItem();

                                    if(cocheSeleccionado == null){
                                        System.out.println("ERROR: NO HAY NINGÚN COCHE SELECCIONADO!!");
                                    }else{
                                        // Recogemos los datos de la fila seleccionada. Deberiamos recoger por id, o cualquier campo que no se repita.
                                        modelo = cocheSeleccionado.getModelo(); // Vamos a coger por modelo aunque es un poco peligroso, deberíamos poner un id.

                                        // Hacemos el filtro  y borramos el elemento de la coleccion
                                        Bson filter = Filters.eq("modelo", modelo );
                                        coleccion.deleteOne(filter);
                                        System.out.println("ELIMINADO EL COCHE (MODELO: "+modelo+")");

                                        refreshTable(); // Para actualizar la tabla con el valor borrado
                                    }


                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }


                            });
                            // Hacemos lo mismo con el botón de modificar.
                            editIcon.setOnMouseClicked((MouseEvent event) -> {

                                Coches cocheSeleccionado = tableCoches.getSelectionModel().getSelectedItem(); // Recogemos los datos de la fila seleccionada
                                FXMLLoader loader = new FXMLLoader();
                                // Abrimos la interfaz de añadir Coche
                                loader.setLocation(getClass().getResource("addElement.fxml")); // Cargamos y abrimos la interfaz de añadir elementos
                                try {
                                    loader.load();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                                AddElementController addElementController = loader.getController();
                                addElementController.setUpdate(true);
                                addElementController.setTextField(cocheSeleccionado.getMarca(), cocheSeleccionado.getModelo(), cocheSeleccionado.getCarroceria(), cocheSeleccionado.getPrecio(), cocheSeleccionado.getAnio());
                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();


                            });

                            HBox managebtn = new HBox(editIcon, deleteIcon);
                            managebtn.setStyle("-fx-alignment:center");
                            HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                            HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                            setGraphic(managebtn);

                            setText(null);

                        }
                    }

                };

                return cell;
            };
            colEdit.setCellFactory(cellFoctory);
            tableCoches.setItems(lista);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}