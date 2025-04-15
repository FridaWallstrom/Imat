/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatmini.old_unused_files.old_unused_files;

import imatmini.MainController;
import imatmini.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductDetail;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author oloft
 */
public class PopUpController extends AnchorPane {
    @FXML ImageView imageView;
    @FXML Label nameLabel;
    @FXML Label prizeLabel;
    @FXML Label ecoLabel;

    @FXML Label descriptionLabel;

    @FXML Label originLabel;

    @FXML Label brandLabel;

    @FXML Label contentsLabel;

    public static MainController main;


    private Model model = Model.getInstance();

    private Product product;

    private final static double kImageWidth = 100.0;
    private final static double kImageRatio = 0.75;

    private final IMatDataHandler dataHandler = IMatDataHandler.getInstance();


    public PopUpController(Product product, MainController main) {
        this.main = main;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ProductPanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        // Setting basic product info
        this.product = product;

        nameLabel.setText(product.getName());

        prizeLabel.setText(String.format("%.2f", product.getPrice()) + " " + product.getUnit());
        imageView.setImage(model.getImage(product, kImageWidth, kImageWidth*kImageRatio));
        if (!product.isEcological()) {
            ecoLabel.setText("");
        }
        // Setting additional product info
        ProductDetail detail = model.getDetail(product);
        if (detail != null) {
            descriptionLabel.setText(detail.getDescription());
            originLabel.setText(detail.getOrigin());
            brandLabel.setText(detail.getBrand());
            contentsLabel.setText(detail.getContents());
        }
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        model.addToShoppingCart(product);

        System.out.println("Add " + product.getName());

        int tmp = 0;
        List<ShoppingItem> items = dataHandler.getShoppingCart().getItems();
        if (items != null) {
            for (ShoppingItem item : items) {
                tmp += item.getAmount();
            }
        }
        main.nbrOfItems.setText(String.valueOf(tmp));
    }

    @FXML
    private void addFavorite(ActionEvent event) {
        if (dataHandler.isFavorite(product)) {
            dataHandler.removeFavorite(product);
        } else {
            dataHandler.addFavorite(product);
        }
    }
}
