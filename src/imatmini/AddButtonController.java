package imatmini;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;


public class AddButtonController extends AnchorPane {
    @FXML private StackPane stack;
    @FXML private Button frontButton;
    @FXML private Group backAddGroup;

    private final Model model = Model.getInstance();
    private final ShoppingCart shoppingCart = model.getShoppingCart();

    private ShoppingItem shoppingItem;
    private ShoppingCartFilipController parentController;

    public void AddButtonController(ShoppingItem item){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addButton.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.shoppingItem = item;
    }

    @FXML
    private void openAdd(MouseEvent event){
        backAddGroup.toFront();
    }
    @FXML
    void removeProductFromCart(MouseEvent event) {
        shoppingCart.removeItem(shoppingItem);
        parentController.updateShoppingCart();
    }

    @FXML
    void DecreaseAmountOfProduct(MouseEvent event) {
        shoppingCart.removeItem(shoppingItem);
        shoppingItem.setAmount(shoppingItem.getAmount() - 1);

        if (shoppingItem.getAmount() < 1.0) {
            removeProductFromCart(event);
            parentController.updateShoppingCart();
            return;
        }
        shoppingCart.addItem(shoppingItem);
        System.out.println(shoppingItem.getAmount());
        System.out.println("Deleted: " + shoppingItem);
        parentController.updateShoppingCart();
    }

    @FXML
    void IncreaseAmountOfProduct(MouseEvent event) {
        shoppingCart.removeItem(shoppingItem);
        shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        shoppingCart.addItem(shoppingItem);
        System.out.println("Added: " + shoppingItem);
        parentController.updateShoppingCart();
    }
}

