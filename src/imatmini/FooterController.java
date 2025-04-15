package imatmini;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class FooterController extends AnchorPane {

    private imatmini.MainController mainController;
    private Model model = Model.getInstance();

    public FooterController(imatmini.MainController mainController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Footer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainController = mainController;
        mainController.testMainFunctionCall();


        //updateView();
    }
    @FXML
    private void onViewClick(){
        mainController.backHome();
    }
}
