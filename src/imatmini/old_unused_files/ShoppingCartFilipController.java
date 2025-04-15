/*
package imatmini;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingCartFilipController implements Initializable {
    @FXML
    private AnchorPane MenuBarAnchorPlane;

    @FXML
    private FlowPane ProductScollPaneFlowPane;

    @FXML
    private Button keepShopping;

    @FXML
    private ScrollPane productScollPane;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label shoppingCartTotal;

    @FXML
    private Button toPaying;

    private Model model = Model.getInstance();

    private ShoppingCart shoppingCart = model.getShoppingCart();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Lägg till navbar
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("MenuBar.fxml"));
            MenuBarAnchorPlane.getChildren().addAll(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int i = 0;
        // Temp product show
        for(Product spit : model.getProducts()) {
            System.out.println(spit);
            shoppingCart.addItem(new ShoppingItem(spit));
            i++;
            if(i == 5){
                break;
            }
        }

        // Populate shopping list
        updateShoppingCart();
    }

    public void updateShoppingCart() {
        ProductScollPaneFlowPane.getChildren().clear();

        // Lägg till varor i lista
        for (ShoppingItem sitem : shoppingCart.getItems()) {
            System.out.println(sitem.getAmount());
            InlineProductRowRemoveProduct iprrp = new InlineProductRowRemoveProduct(sitem, this);
            ProductScollPaneFlowPane.getChildren().add(iprrp);
        }
    }

    public void gotoPayment(ActionEvent event){

    }
}
*/
