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

public class LoginController {

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
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    private final DbFunctions dbFunctions = new DbFunctions();

    public static String login;
    public static String password;

    @FXML
    void initialize() {
        btnRegister.setOnAction(e -> {
            new HelloApplication().openNewScene(anchorPane, "/com/example/requestdesktop/register-view.fxml", "Регистрация");
            anchorPane.getScene().getWindow().hide();
            Stage stage = new Stage();
            StageModel.setMyStage(stage);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 346, 459);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            SceneModel.setMyScene(scene);
            stage.setTitle("Регистрация");
            stage.setScene(scene);
            stage.show();
        });

        btnLogin.setOnAction(e -> {
            try {
                validation();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void validation() throws IOException {
        login = loginTextField.getText();
        password = passwordTextField.getText();
        int codeError = dbFunctions.loginUser(login, password);






        if (login.isEmpty()) {
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
        } else if (password.isEmpty()) {
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
        } else if (login.equals(password)) {
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
        } else if (codeError == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Не найден аккаунт!");

            ButtonType buttonTypeOk = new ButtonType("Ок");

            alert.getButtonTypes().setAll(buttonTypeOk);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeOk) {
                    alert.close();
                }
            });
        } else if (codeError == 404) {
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
        } else {
            new HelloApplication().openNewScene(anchorPane, "/com/example/requestdesktop/main-view.fxml", "Заявки");
            anchorPane.getScene().getWindow().hide();
            Stage stage = new Stage();
            StageModel.setMyStage(stage);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 723, 400);
            SceneModel.setMyScene(scene);
            stage.setTitle("Заявки");
            stage.setScene(scene);
            stage.show();
        }
    }

}
