package com.example.demo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.demo.db.DbFunctions;
import com.example.demo.models.SceneModel;
import com.example.demo.models.StageModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField middleNameTextField;

    @FXML
    private PasswordField passwordTextField;

    private final DbFunctions dbFunctions = new DbFunctions();

    @FXML
    void initialize() {
        btnRegister.setOnAction(e -> {
            try {
                validation();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnLogin.setOnAction(e -> {
            new HelloApplication().openNewScene(anchorPane, "/com/example/requestdesktop/login-view.fxml", "Авторизация");
            anchorPane.getScene().getWindow().hide();
            Stage stage = new Stage();
            StageModel.setMyStage(stage);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 346, 339);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            SceneModel.setMyScene(scene);
            stage.setTitle("Авторизация");
            stage.setScene(scene);
            stage.show();
        });
    }

    private void validation() throws IOException {
        String last_name = lastNameTextField.getText();
        String first_name = firstNameTextField.getText();
        String middle_name = middleNameTextField.getText();
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        int codeError = dbFunctions.checkLogin(login);

        if (last_name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Поле ввода Фамилия пустое!");

            ButtonType buttonTypeOk = new ButtonType("Ок");

            alert.getButtonTypes().setAll(buttonTypeOk);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeOk) {
                    alert.close();
                }
            });
        }
        else if (first_name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Поле ввода Имя пустое!");

            ButtonType buttonTypeOk = new ButtonType("Ок");

            alert.getButtonTypes().setAll(buttonTypeOk);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeOk) {
                    alert.close();
                }
            });
        }
        else if (middle_name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Поле ввода Отчество пустое!");

            ButtonType buttonTypeOk = new ButtonType("Ок");

            alert.getButtonTypes().setAll(buttonTypeOk);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeOk) {
                    alert.close();
                }
            });
        }
        else if (login.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Поле ввода Логин пустое!");

            ButtonType buttonTypeOk = new ButtonType("Ок");

            alert.getButtonTypes().setAll(buttonTypeOk);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeOk) {
                    alert.close();
                }
            });
        }
        else if (password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Поле ввода Пароль пустое!");

            ButtonType buttonTypeOk = new ButtonType("Ок");

            alert.getButtonTypes().setAll(buttonTypeOk);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeOk) {
                    alert.close();
                }
            });
        }
        else if (login.equals(password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Логин и пароль не должны совпадать!");

            ButtonType buttonTypeOk = new ButtonType("Ок");

            alert.getButtonTypes().setAll(buttonTypeOk);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeOk) {
                    alert.close();
                }
            });
        }
        else if (codeError == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Такой логин уже существует!");

            ButtonType buttonTypeOk = new ButtonType("Ок");

            alert.getButtonTypes().setAll(buttonTypeOk);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeOk) {
                    alert.close();
                }
            });
        }
        else if (codeError == 404){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Какая-та ошибка!");

            ButtonType buttonTypeOk = new ButtonType("Ок");

            alert.getButtonTypes().setAll(buttonTypeOk);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeOk) {
                    alert.close();
                }
            });
        }
        else {
            dbFunctions.registerUser(last_name, first_name, middle_name, login, password, "user");
            new HelloApplication().openNewScene(anchorPane, "/com/example/requestdesktop/login-view.fxml", "Авторизация");
            anchorPane.getScene().getWindow().hide();
            Stage stage = new Stage();
            StageModel.setMyStage(stage);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 346, 339);
            SceneModel.setMyScene(scene);
            stage.setTitle("Авторизация");
            stage.setScene(scene);
            stage.show();
        }
    }
}
