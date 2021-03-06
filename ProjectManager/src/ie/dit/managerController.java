package ie.dit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.json.JSONException;

import javax.annotation.Resources;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.prefs.Preferences;

import static ie.dit.Main.csvReader;
import static ie.dit.Main.csvWriter;

public class managerController {


    @FXML
    private ListView<String> listDate;

    @FXML
    private DatePicker event_date_pick;

    @FXML
    private TextField event_name;

    @FXML
    private TextArea event_info;

    @FXML
    private Button save;

    @FXML
    private Button clear;

    @FXML
    private MenuBar toolbar;

    @FXML
    private Menu help;

    @FXML
    private Menu settings_menu;

    @FXML
    private Label usernamevalue;

    @FXML
    private Label teamvalue;

    @FXML
    private CheckMenuItem daytheme;

    @FXML
    private CheckMenuItem nighttheme;

    @FXML
    private Button checkEvent;

    private String daythemecss = getClass().getResource("daytheme.css").toExternalForm();
    private String nightthemecss = getClass().getResource("nighttheme.css").toExternalForm();

    @FXML
    void save_event(ActionEvent event) throws IOException, JSONException {

        //converts Date to String
        LocalDate eventdate = event_date_pick.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String formattedString = eventdate.format(formatter);
        String eventinfo = event_info.getText();
        String eventname = event_name.getText();

        System.out.println(formattedString);

        csvWriter(eventinfo, eventname, formattedString);
        csvReader();

        Client client = new Client();

        try {
            Main.gid = client.getGID(Main.teamname);
            System.out.println(Main.gid);
            client.postEvent(Main.gid,eventinfo,eventname,formattedString);

            event_info.clear();
            event_name.clear();
            event_date_pick.setValue(null);
            event_date_pick.getEditor().clear();
        }
        catch(Exception e) {
        }
    }

    @FXML
    void clear_event(ActionEvent event) {
        event_info.clear();
        event_name.clear();
        event_date_pick.setValue(null);
        event_date_pick.getEditor().clear();
    }

    @FXML
    void show_help(ActionEvent event) {
        final Stage dialog = new Stage();
        Stage stage = (Stage) toolbar.getScene().getWindow();
        dialog.initModality(Modality.NONE);
        dialog.initOwner(stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("I am helpful :)"));
        Scene dialogScene = new Scene(dialogVbox, 300, 100);
        dialog.setScene(dialogScene);
        dialogScene.getStylesheets().add("ie/dit/daytheme.css");
        dialog.show();
    }

    @FXML
    void uncheck1(ActionEvent event) throws IOException {
        if(daytheme.isSelected() == true){
            nighttheme.setSelected(false);

            Stage stage;
            Parent root = FXMLLoader.load(getClass().getResource("manager.fxml"));
            Image app = new Image(getClass().getResourceAsStream("kingthomas.png"));
            stage = (Stage) toolbar.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().remove(nightthemecss);
            scene.getStylesheets().add("ie/dit/daytheme.css");
            stage.setScene(scene);
            stage.getIcons().add(app);
            stage.setTitle("Project Manager");
            stage.show();

        }
    }

    @FXML
    void uncheck2(ActionEvent event) throws IOException {
        if(nighttheme.isSelected() == true) {
            daytheme.setSelected(false);

            Stage stage;
            Parent root = FXMLLoader.load(getClass().getResource("manager.fxml"));
            Image app = new Image(getClass().getResourceAsStream("kingthomas.png"));
            stage = (Stage) toolbar.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("ie/dit/nighttheme.css");
            stage.setScene(scene);
            stage.getIcons().add(app);
            stage.setTitle("Project Manager");
            stage.show();
        }

    }

    @FXML
    void jointeam(ActionEvent event) throws IOException {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("jointeam.fxml"));
        Image app = new Image(getClass().getResourceAsStream("kingthomas.png"));
        stage = (Stage) toolbar.getScene().getWindow();
        //stage.initModality(Modality.NONE);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("ie/dit/daytheme.css");
        stage.setScene(scene);
        stage.getIcons().add(app);
        stage.setTitle("Project Manager");
        stage.show();
    }

    @FXML
    void createteam(ActionEvent event) throws IOException {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("createteam.fxml"));
        Image app = new Image(getClass().getResourceAsStream("kingthomas.png"));
        stage = (Stage) toolbar.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("ie/dit/daytheme.css");
        //stage.initModality(Modality.NONE);
        stage.setScene(scene);
        stage.getIcons().add(app);
        stage.setTitle("Project Manager");
        stage.show();
    }

    @FXML
    void leaveteam(ActionEvent event) throws IOException {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("leaveteam.fxml"));
        Image app = new Image(getClass().getResourceAsStream("kingthomas.png"));
        stage = (Stage) toolbar.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("ie/dit/daytheme.css");
        stage.initModality(Modality.NONE);
        stage.setScene(scene);
        stage.getIcons().add(app);
        stage.setTitle("Project Manager");
        stage.show();
    }

   @FXML
    void eventCheck(ActionEvent event) {
       String date = csvReader();

       String[] dates = {date};
       ObservableList<String> data = FXCollections.observableArrayList();

       data.clear();
       data.addAll(dates);
       listDate.setItems(data);
    }
}
