package imatmini;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ReaController {
    public ReaController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rea.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
