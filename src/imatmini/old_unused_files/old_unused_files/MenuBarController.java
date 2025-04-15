package imatmini.old_unused_files.old_unused_files;

import imatmini.MainController;
import imatmini.Model;
import imatmini.ProductPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.util.List;

public class MenuBarController {

    public static FlowPane productsFlowPane;

    @FXML
    private Button HandlaButton;

    @FXML
    private Button HistorikButton;

    @FXML
    private Button HomeLogoButton;

    @FXML
    private Button RabatterButton;

    @FXML
    private TextField SearchBarNavBar;

    @FXML
    private Button ShoppingCartButton;

    private Model model = Model.getInstance();
    public MainController mainController = new MainController();

    @FXML
    void goToHandlaPage(MouseEvent event) {

    }

    @FXML
    void goToHistorikPage(MouseEvent event) {

    }

    @FXML
    void goToRabatterPage(MouseEvent event) {

    }

    @FXML
    void goToShoppingCart(ActionEvent event) {
        System.out.println("hello");
        mainController.openShoppingCartFilip();
    }

    @FXML
    void performSearchAction(ActionEvent event) {
        List<Product> matches = model.findProducts(SearchBarNavBar.getText());
        updateProductList(matches);
        System.out.println("# matching products: " + matches.size());
    }



    // Denna denna metoden körs när searchbaren "söker". Resultatet av sökningen blir "products" listan.

    private void updateProductList(List<Product> products) {

        System.out.println("updateProductList " + products.size());

        productsFlowPane.getChildren().clear();

        for (Product product : products) {

            productsFlowPane.getChildren().add(new ProductPanel(product));
        }

    }

}