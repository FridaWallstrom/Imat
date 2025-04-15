package imatmini;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoryListItemController extends AnchorPane {
    @FXML private Label orderCost;
    @FXML private Label orderDate;
    @FXML private Label orderNumber;

    private Order order;

    public static MainController mainController;
    private final IMatDataHandler dataHandler = IMatDataHandler.getInstance();



    public HistoryListItemController(Order order, MainController mainController) {
        this.mainController = mainController;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HistoryListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.order = order;
        float total = 0;
        for (ShoppingItem shoppingItem : order.getItems()) {
            total += (float) ((float) shoppingItem.getProduct().getPrice() * shoppingItem.getAmount());
        }
        orderCost.setText(String.valueOf(total));
        String year =  String.valueOf(order.getDate().getYear() - 100) ;
        String month = String.valueOf(order.getDate().getMonth() + 1);
        String day = String.valueOf(order.getDate().getDate());

        orderDate.setText(year + "/" + month + "/" + day);
        orderNumber.setText(String.valueOf(order.getOrderNumber()));
    }

    @FXML
    void removeOrder(MouseEvent event){
        dataHandler.getOrders().remove(order); // TODO DOES NOT UPDATE VIEW
    }

    @FXML
    void enterOrderDetails(MouseEvent event) {
        mainController.openHistorySelectItem(order.getOrderNumber());
    }

}
