package imatmini;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingCartFilipController extends AnchorPane implements Initializable, ShoppingCartListener {
    @FXML private FlowPane ProductScollPaneFlowPane;
    @FXML private Button keepShopping;
    @FXML private ScrollPane productScollPane;
    @FXML private AnchorPane rootPane;
    @FXML private Label shoppingCartTotal;
    @FXML private Button toPaying;

    private Model model = Model.getInstance();

    private ShoppingCart shoppingCart = model.getShoppingCart();
    private MainController mainController;
    private IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public ShoppingCartFilipController(MainController mainController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShoppingCartFilip.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        /*
        Button backToHomeButton = new Button();
        backToHomeButton.setPrefWidth(159);
        backToHomeButton.setPrefHeight(44);
        backToHomeButton.setLayoutX(42);
        backToHomeButton.setLayoutY(83);
        backToHomeButton.setOnMouseClicked(event -> mainController.backHome());

        this.getChildren().add(backToHomeButton);
         */

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.mainController = mainController;
        model.getShoppingCart().addShoppingCartListener(this);

        //updateView();
    }


    @Override
    public void shoppingCartChanged(CartEvent evt) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*
        int i = 0;
        for(Product spit : model.getProducts()) {
            System.out.println(spit);
            shoppingCart.addItem(new ShoppingItem(spit));
            i++;
            if(i == 5){
                break;
            }
        }
        */

        // Populate shopping list
        updateShoppingCart();
    }

    public void updateShoppingCart() {
        ProductScollPaneFlowPane.getChildren().clear();
        int tmp = 0;


        boolean dark = false;

        // Lägg till varor i lista

        for (ShoppingItem sitem : shoppingCart.getItems()) {
            InlineProductRowRemoveProduct iprrp = new InlineProductRowRemoveProduct(sitem, this);
            ProductScollPaneFlowPane.getChildren().add(iprrp);
            if (dark){
                iprrp.getStyleClass().add("dark_row");
                dark = false;
            }else{
                dark = true;
            }
            tmp += sitem.getAmount();
        }


        try {
            mainController.updateNbrOfItems();
        }catch (Exception e){

        }
        //main.nbrOfItems.setText(String.valueOf(tmp)); //behöver fixas
        if(shoppingCart.getTotal() > 0){
            toPaying.getStyleClass().remove("inactive");
        }else{
            toPaying.getStyleClass().add("inactive");
            System.out.println("hello");
        }

        updateScreenLabels();
    }

    public void updateScreenLabels() {
        shoppingCartTotal.setText(String.valueOf(model.getShoppingCart().getTotal()) + " kr");
    }


    @FXML
    private void backToHome(MouseEvent event){
        mainController.backHome();
    }
    @FXML
    private void toPayment(MouseEvent event){
        if(shoppingCart.getTotal() > 0){
            mainController.openPayment();
            toPaying.getStyleClass().remove("inactive");
        }else{
            toPaying.getStyleClass().add("inactive");
        }
    }
}
