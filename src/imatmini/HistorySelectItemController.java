package imatmini;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HistorySelectItemController extends AnchorPane {
    @FXML
    private ScrollPane historyItemizedList;

    @FXML
    private FlowPane historyItemizedListFlowPane;

    private Model model = Model.getInstance();
    private IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    private MainController mainController;

    public HistorySelectItemController(MainController mainController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HistorySelectItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.mainController = mainController;
        boolean dark = true;
        for(Order orderIte : iMatDataHandler.getOrders().reversed()) {
            HistoryListItemController historyListItemController = new HistoryListItemController(orderIte, mainController);
            historyItemizedListFlowPane.getChildren().add(historyListItemController);
            if (dark){
                historyListItemController.getStyleClass().add("dark_row");
                dark = false;
            }else{
                dark = true;
            }
        }
    }


    @FXML
    private void backToHome(MouseEvent event){
        mainController.backHome();
    }
}
