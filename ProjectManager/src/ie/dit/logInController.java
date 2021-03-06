package ie.dit;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class logInController {

    @FXML
    private Button sign_up;

    @FXML
    private TextField username_check;

    @FXML
    private Button log_in;

    @FXML
    private PasswordField password_check;

    @FXML
    //goes to sign up page
    void sign_up_event(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        root = FXMLLoader.load(getClass().getResource("ie/dit/sign_up.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("ie/dit/daytheme.css");
        Image app = new Image(getClass().getResourceAsStream("kingthomas.png"));
        stage = (Stage) sign_up.getScene().getWindow();
        stage.setScene(scene);
        stage.getIcons().add(app);
        stage.setTitle("Project Manager");
        stage.show();
    }

    @FXML
    //will check database to see if the username and password exist together
    void log_in_event(ActionEvent event) throws IOException{

        String username = username_check.getText();
        String password = password_check.getText();

        Client client = new Client();
        try
        {
            String success = client.getUser(username,password);
            if(success.equals("0")==true) {

                Stage stage;
                Parent root;
                Image app = new Image(getClass().getResourceAsStream("kingthomas.png"));
                stage = (Stage) log_in.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("manager.fxml"));
                stage.setScene(new Scene(root));
                stage.getIcons().add(app);
                stage.setTitle("Project Manager");
                stage.show();
            }
            if(success.equals("1")==true){
              System.out.println("no");
            }
        }
        catch(Exception e)
        {

        }
    }


}