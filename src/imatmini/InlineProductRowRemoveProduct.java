package imatmini;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InlineProductRowRemoveProduct extends AnchorPane {
    @FXML
    private Label InlineItemName;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button InlineItemAddAmount;

    @FXML
    private Button InlineItemRemoveProduct;

    @FXML
    private Label InlineItemAmount;

    @FXML
    private Button InlineItemDecreaseAmount;

    @FXML
    private ImageView InlineItemImage;

    @FXML
    private Label InlineItemPrice;

    @FXML
    void removeProductFromCart(MouseEvent event) {
        shoppingCart.removeItem(shoppingItem);
        parentController.updateShoppingCart();
    }

    @FXML
    void DecreaseAmountOfProduct(MouseEvent event) {
        List<ShoppingItem> oldShoppingCart = new ArrayList<>(); // = shoppingCart
        for(ShoppingItem sitem : shoppingCart.getItems()) {
            oldShoppingCart.add(sitem);
        }
        shoppingCart.clear(); // remove

        for(ShoppingItem sitem : oldShoppingCart) {
            if(sitem.equals(shoppingItem)) {
                shoppingItem.setAmount(shoppingItem.getAmount() - 1);

                if (shoppingItem.getAmount() < 1.0) {
                    removeProductFromCart(event);
                    continue;
                }
                shoppingCart.addItem(shoppingItem);
                continue;
            }
            shoppingCart.addItem(sitem);
        }
        parentController.updateShoppingCart();
    }

    @FXML
    void IncreaseAmountOfProduct(MouseEvent event) {
        List<ShoppingItem> oldShoppingCart = new ArrayList<>(); // = shoppingCart
        for(ShoppingItem sitem : shoppingCart.getItems()) {
            oldShoppingCart.add(sitem);
        }
        shoppingCart.clear(); // remove

        for(ShoppingItem sitem : oldShoppingCart) {
            if(sitem.equals(shoppingItem)) {
                shoppingItem.setAmount(shoppingItem.getAmount() + 1);

                shoppingCart.addItem(shoppingItem);
                continue;
            }
            shoppingCart.addItem(sitem);
        }
        parentController.updateShoppingCart();
    }

    private final Model model = Model.getInstance();
    private final ShoppingCart shoppingCart = model.getShoppingCart();

    private ShoppingItem shoppingItem;
    private ShoppingCartFilipController parentController;

    public InlineProductRowRemoveProduct(ShoppingItem item, ShoppingCartFilipController parentController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InlineProductRowRemoveProduct.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = parentController;
        this.shoppingItem = item;

        InlineItemImage.setImage(model.getImage(shoppingItem.getProduct()));
        InlineItemPrice.setText(String.valueOf(shoppingItem.getProduct().getPrice()));
        InlineItemName.setText(shoppingItem.getProduct().getName());
        InlineItemAmount.setText(String.valueOf((int)shoppingItem.getAmount()));
    }
}
