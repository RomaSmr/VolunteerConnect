package com.example.demo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.demo.db.DbFunctions;
import com.example.demo.db.Variables;
import com.example.demo.models.Request;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableColumn<Request, String> descriptionTableColumn;

    @FXML
    private TableColumn<Request, String> endDateTableColumn;

    @FXML
    private TableColumn<Request, String> idTableColumn;

    @FXML
    private ImageView imageAddRequest;

    @FXML
    private ImageView imageDeleteRequest;

    @FXML
    private ImageView imageUpdateRequest;

    @FXML
    private TableColumn<Request, String> nameTableColumn;

    @FXML
    private TableColumn<Request, String> startDateTableColumn;

    @FXML
    private TableColumn<Request, String> statusTableColumn;

    @FXML
    private TableView<Request> tableView;

    private final DbFunctions dbFunctions = new DbFunctions();

    Request request;

    @FXML
    void addRequest(MouseEvent event) {
        if (Variables.ACTIVE_USER_ROLE.equals("admin")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/requestdesktop/add-request-view.fxml"));
                Parent parent = loader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(parent));
                stage.setTitle("Добавление заявки");
                stage.showAndWait();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @FXML
    void deleteRequest(MouseEvent event) {

    }

    @FXML
    void updateRequest(MouseEvent event) {
        tableView.setItems(dbFunctions.getAllRequests());
    }

    @FXML
    void initialize() {
        if (Variables.ACTIVE_USER_ROLE.equals("user")) {
            imageAddRequest.setVisible(false);
            imageDeleteRequest.setVisible(false);
            imageUpdateRequest.setVisible(false);
        }
        installTableView();
    }

    private void installTableView() {
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        endDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        statusTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.setItems(dbFunctions.getAllRequests());

        if (Variables.ACTIVE_USER_ROLE.equals("admin")) {
            tableView.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                    request = tableView.getSelectionModel().getSelectedItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/requestdesktop/update-request.fxml"));
                        Parent parent = loader.load();
                        UpdateRequestController updateRequestController = loader.getController();
                        updateRequestController.setDataRequest(request.getId(), request.getName(), request.getDescription(), request.getStart_date(), request.getEnd_date(), request.getStatus());
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(new Scene(parent));
                        stage.setTitle("Редактирование заявки");
                        stage.showAndWait();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (e.getClickCount() == 1) {
                    request = tableView.getSelectionModel().getSelectedItem();
                    imageDeleteRequest.setOnMouseClicked(event -> {
                        if (request.getId().isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Ошибка");
                            alert.setContentText("Не найден Id заявки!");

                            ButtonType buttonTypeOk = new ButtonType("Ок");

                            alert.getButtonTypes().setAll(buttonTypeOk);

                            alert.showAndWait().ifPresent(response -> {
                                if (response == buttonTypeOk) {
                                    alert.close();
                                }
                            });
                        } else {
                            dbFunctions.deleteRequest(request.getId());
                        }
                    });
                }
            });
        }
    }

}