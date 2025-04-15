package imatmini;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class InlineProductRowAddProduct extends AnchorPane {
    @FXML
    private Label InlineItemName;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button InlineItemAddProduct;

    @FXML
    private Label InlineItemAmount;

    @FXML
    private ImageView InlineItemImage;

    @FXML
    private Label InlineItemPrice;

    private MainController mainController;


    @FXML
    void AddProductToCart(MouseEvent event) {
        for(int i = 0; i < shoppingItem.getAmount(); i++) {
            Model.getInstance().addToShoppingCart(shoppingItem.getProduct());
        }
        try {
            mainController.updateNbrOfItems();
        }catch (Exception e){

        }
    }

    private final Model model = Model.getInstance();

    private ShoppingItem shoppingItem;

    public InlineProductRowAddProduct(ShoppingItem item, MainController main) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InlineProductRowAddProduct.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.mainController = main;
        this.shoppingItem = item;

        InlineItemImage.setImage(model.getImage(shoppingItem.getProduct()));
        InlineItemPrice.setText(String.valueOf(shoppingItem.getProduct().getPrice()));
        InlineItemName.setText(shoppingItem.getProduct().getName());
        InlineItemAmount.setText(String.valueOf((int)shoppingItem.getAmount()));
    }
}
