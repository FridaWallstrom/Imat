package imatmini;

import com.sun.webkit.Timer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController extends AnchorPane {

    @FXML
    private Button AddAllProducts;

    @FXML
    private Button BackToHome;

    @FXML
    private AnchorPane MenuBarAnchorPlane;

    @FXML
    private Label OrderDate;

    @FXML
    private Label OrderNumber;

    @FXML
    private ScrollPane ProductScollPane;

    @FXML
    private FlowPane ProductScollPaneFlowPane;

    @FXML
    private AnchorPane rootPane;

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private imatmini.MainController mainController;


    private final Model model = Model.getInstance();

    private void loadInlineProductRow() {

    }

    @FXML
    private void addAllProductsButtonClicked() {
        for(ShoppingItem sitem : order.getItems()) {
            model.getShoppingCart().addItem(sitem);
        }
        try {
            mainController.updateNbrOfItems();
        }catch (Exception e){

        }

    }

    private Order order = new Order();

    public HistoryController(imatmini.MainController mainController, int orderNr) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("History.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainController = mainController;

        // Finds the right order that matches the inputed order number
        List<Order> allOrders = iMatDataHandler.getOrders();
        //System.out.println(allOrders);
        for (Order thisOrder : allOrders) {
            if (thisOrder.getOrderNumber() == orderNr) {
                order = thisOrder;
                break;
            }
            //updateView();
        }
        getInfo();

        //Populate the items
        List<ShoppingItem> itemsInShoppingCart = order.getItems();
        boolean dark = false;
        for(ShoppingItem sitem : itemsInShoppingCart) {
            InlineProductRowAddProduct iprap = new InlineProductRowAddProduct(sitem, mainController);
            ProductScollPaneFlowPane.getChildren().add(iprap);
            if (dark){
                iprap.getStyleClass().add("dark_row");
                dark = false;
            }else{
                dark = true;
            }
        }
    }
    public void getInfo() {
        //hämtar id & datum.
        OrderNumber.setText(String.valueOf(order.getOrderNumber()));
        String year =  String.valueOf(order.getDate().getYear() - 100) ;
        String month = String.valueOf(order.getDate().getMonth() + 1);
        String day = String.valueOf(order.getDate().getDate());

        OrderDate.setText(year + "/" + month + "/" + day);
    }

    public void getCards() {
        if (order.getItems() != null) {
            boolean dark = false;
            for (ShoppingItem sitem : order.getItems()) {
                InlineProductRowAddProduct iprap = new InlineProductRowAddProduct(sitem, mainController);
                ProductScollPaneFlowPane.getChildren().add(iprap);
                if (dark){
                    iprap.getStyleClass().add("dark_row");
                    dark = false;
                }else{
                    dark = true;
                }
            }
        }

        // Populera resten av fälten
        System.out.println(order.getOrderNumber());
        OrderNumber.setText(String.valueOf(order.getOrderNumber()));
        OrderDate.setText(String.valueOf(order.getDate()));

        /* Lägg till ett test item
        ShoppingItem item = new ShoppingItem(model.getProducts().get(1));
        InlineProductRowAddProduct iprap = new InlineProductRowAddProduct(item);
        ProductScollPaneFlowPane.getChildren().add(iprap);

        System.out.println(ProductScollPaneFlowPane.getChildren());
         */
    }

    @FXML
    private void backToHome(MouseEvent event){
        mainController.openHistory();
    }
}
