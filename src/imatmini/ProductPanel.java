/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatmini;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import se.chalmers.cse.dat216.project.*;


/**
 *
 * @author oloft
 */
public class ProductPanel extends AnchorPane {
    @FXML ImageView imageView;
    @FXML ImageView backgroundImageView;
    @FXML Label nameLabel;
    @FXML Label prizeLabel;
    @FXML SVGPath star;
    @FXML SVGPath circle;
    @FXML Group IncDecGroup;
    @FXML  Label InlineItemAmount1;


    private final IMatDataHandler dataHandler = IMatDataHandler.getInstance();


    private Model model = Model.getInstance();

    private Product product;

    private ShoppingItem shoppingItem;
    private final ShoppingCart shoppingCart = model.getShoppingCart();
    private ShoppingCartFilipController parentController;

    public static MainController main;
    public static AnchorPane dynamicPane;


    private final static double kImageWidth = 100.0;
    private final static double kImageRatio = 0.75;

    // Basic stuff
    public ProductPanel(Product product) {

    }

    public ProductPanel(Product product, MainController main) {
        this.main = main;
        //MainController.product = product;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductPanel.fxml"));
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
        imageView.setImage(model.getImage(product));
        /*
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
        */

        String circle_path = "M0 25C0 11.1929 11.1929 0 25 0C38.8071 0 50 11.1929 50 25C50 38.8071 38.8071 50 25 50C11.1929 50 0 38.8071 0 25Z";
        String star_path = "M24.0672 8.41411C24.3967 7.56134 25.6033 7.56134 25.9328 8.41411L29.6922 18.1436C29.8411 18.529 30.2118 18.7832 30.625 18.7832H42.3684C43.3784 18.7832 43.7516 20.11 42.8897 20.6365L33.7042 26.2485C33.2918 26.5004 33.1186 27.0114 33.2928 27.4622L36.868 36.715C37.2125 37.6067 36.2296 38.4271 35.4139 37.9288L25.5214 31.8849C25.2013 31.6894 24.7987 31.6894 24.4786 31.8849L14.5861 37.9288C13.7704 38.4271 12.7875 37.6067 13.132 36.715L16.7072 27.4622C16.8814 27.0114 16.7082 26.5004 16.2958 26.2485L7.11026 20.6365C6.24838 20.11 6.62161 18.7832 7.63162 18.7832H19.375C19.7882 18.7832 20.1589 18.529 20.3078 18.1436L24.0672 8.41411Z";

        circle.setContent(circle_path);
        star.setContent(star_path);

        if(dataHandler.isFavorite(product)){
            circle.getStyleClass().add("circle_favorite");
            star.getStyleClass().add("star_favorite");
        }

        initIncDecFunc();
    }

    public ProductPanel(Product product, MainController main, Boolean wide) {
        this.main = main;
        //MainController.product = product;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductPanelWideFeature.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        Rectangle clip = new Rectangle(
                backgroundImageView.getFitWidth(), backgroundImageView.getFitHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        backgroundImageView.setClip(clip);

        // snapshot the rounded image.
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = backgroundImageView.snapshot(parameters, null);

        // remove the rounding clip so that our effect can show through.
        backgroundImageView.setClip(null);


        // store the rounded image in the imageView.
        backgroundImageView.setImage(image);

        // Setting basic product info
        this.product = product;
        prizeLabel.setText(String.format("%.2f", product.getPrice()) + " " + product.getUnit());
        /*
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
        */

        String circle_path = "M0 25C0 11.1929 11.1929 0 25 0C38.8071 0 50 11.1929 50 25C50 38.8071 38.8071 50 25 50C11.1929 50 0 38.8071 0 25Z";
        String star_path = "M24.0672 8.41411C24.3967 7.56134 25.6033 7.56134 25.9328 8.41411L29.6922 18.1436C29.8411 18.529 30.2118 18.7832 30.625 18.7832H42.3684C43.3784 18.7832 43.7516 20.11 42.8897 20.6365L33.7042 26.2485C33.2918 26.5004 33.1186 27.0114 33.2928 27.4622L36.868 36.715C37.2125 37.6067 36.2296 38.4271 35.4139 37.9288L25.5214 31.8849C25.2013 31.6894 24.7987 31.6894 24.4786 31.8849L14.5861 37.9288C13.7704 38.4271 12.7875 37.6067 13.132 36.715L16.7072 27.4622C16.8814 27.0114 16.7082 26.5004 16.2958 26.2485L7.11026 20.6365C6.24838 20.11 6.62161 18.7832 7.63162 18.7832H19.375C19.7882 18.7832 20.1589 18.529 20.3078 18.1436L24.0672 8.41411Z";

        circle.setContent(circle_path);
        star.setContent(star_path);

        if(dataHandler.isFavorite(product)){
            circle.getStyleClass().add("circle_favorite");
            star.getStyleClass().add("star_favorite");
        }

        initIncDecFunc();
    }

    public ProductPanel(Product product, MainController main, Boolean wide, Boolean miniWide) {
        this.main = main;
        //MainController.product = product;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductPanelKindaWideFeature.fxml"));
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
        imageView.setImage(model.getImage(product));
        /*
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
        */

        String circle_path = "M0 25C0 11.1929 11.1929 0 25 0C38.8071 0 50 11.1929 50 25C50 38.8071 38.8071 50 25 50C11.1929 50 0 38.8071 0 25Z";
        String star_path = "M24.0672 8.41411C24.3967 7.56134 25.6033 7.56134 25.9328 8.41411L29.6922 18.1436C29.8411 18.529 30.2118 18.7832 30.625 18.7832H42.3684C43.3784 18.7832 43.7516 20.11 42.8897 20.6365L33.7042 26.2485C33.2918 26.5004 33.1186 27.0114 33.2928 27.4622L36.868 36.715C37.2125 37.6067 36.2296 38.4271 35.4139 37.9288L25.5214 31.8849C25.2013 31.6894 24.7987 31.6894 24.4786 31.8849L14.5861 37.9288C13.7704 38.4271 12.7875 37.6067 13.132 36.715L16.7072 27.4622C16.8814 27.0114 16.7082 26.5004 16.2958 26.2485L7.11026 20.6365C6.24838 20.11 6.62161 18.7832 7.63162 18.7832H19.375C19.7882 18.7832 20.1589 18.529 20.3078 18.1436L24.0672 8.41411Z";

        circle.setContent(circle_path);
        star.setContent(star_path);

        if(dataHandler.isFavorite(product)){
            circle.getStyleClass().add("circle_favorite");
            star.getStyleClass().add("star_favorite");
        }

        initIncDecFunc();
    }

    public ProductPanel(Product product, MainController main, Boolean wide, Boolean miniWide, Boolean invi) {
        this.main = main;
        //MainController.product = product;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductPanelWideFeatureInvi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void initIncDecFunc() {
        List<ShoppingItem> items = dataHandler.getShoppingCart().getItems();
        if (items != null) {
            for (ShoppingItem item : items) {
                if (item.getProduct() == product) {
                    InlineItemAmount1.setText(String.valueOf((int)item.getAmount()));
                    IncDecGroup.toFront();
                }
            }
        }
    }

    @FXML
    void DecreaseAmountOfProduct(ActionEvent event) {

        List<ShoppingItem> items = dataHandler.getShoppingCart().getItems();
        ShoppingItem temp = new ShoppingItem(product);
        if (items != null) {
            for (ShoppingItem item : items) {
                if (item.getProduct() == product) {
                    item.setAmount(item.getAmount() - 1);
                }
                temp = item;
            }
        }
        initIncDecFunc();

        if (temp.getAmount() < 1.0) {
            shoppingCart.removeItem(temp);
            IncDecGroup.toBack();
        }

        int tmp = 0;
        List<ShoppingItem> products = dataHandler.getShoppingCart().getItems();
        if (products != null) {
            for (ShoppingItem item : products) {
                tmp += item.getAmount();
            }
        }
        main.nbrOfItems.setText(String.valueOf((int)tmp));
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        model.addToShoppingCart(product);
        int tmp = 0;
        List<ShoppingItem> items = dataHandler.getShoppingCart().getItems();
        if (items != null) {
            for (ShoppingItem item : items) {
                tmp += item.getAmount();
            }
        }
        main.nbrOfItems.setText(String.valueOf((int)tmp));

        initIncDecFunc();
    }


    @FXML
    private void handleInfoAction(ActionEvent event) {
        System.out.println("info");
        System.out.println(product.getProductId());
        main.initPopUp(product.getProductId(), InlineItemAmount1);
    }
    @FXML
    private void addFavorite(ActionEvent event) {
        if (dataHandler.isFavorite(product)) {
            dataHandler.removeFavorite(product);
            circle.getStyleClass().remove("circle_favorite");
            star.getStyleClass().remove("star_favorite");
        } else {
            dataHandler.addFavorite(product);
            circle.getStyleClass().add("circle_favorite");
            star.getStyleClass().add("star_favorite");
        }
    }
}
