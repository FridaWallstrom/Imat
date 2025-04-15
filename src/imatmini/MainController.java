package imatmini;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML; // what did you dooo, all den historian som fanns nerskriven, OOOFF. Sounds like a skill issue
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;



public class MainController extends AnchorPane implements Initializable {
    @FXML private AnchorPane MenuBarAnchorPlane;
    @FXML public FlowPane productsFlowPane;
    @FXML private AnchorPane dynamicPane = new AnchorPane();
    @FXML private ComboBox sortComboBox;
    @FXML private AnchorPane footerAnchorPane;

    // Detailed view
    @FXML AnchorPane detailView = new AnchorPane();
    @FXML ImageView imageView = new ImageView();
    @FXML Label nameLabel = new Label();
    @FXML Label prizeLabel = new Label();
    @FXML Label ecoLabel = new Label();
    @FXML Label descriptionLabel = new Label();
    @FXML Label originLabel = new Label();
    @FXML Label brandLabel = new Label();
    @FXML Label contentsLabel = new Label();
    @FXML ScrollPane scrollPaneForProducts;
    private final static double kImageWidth = 100.0;
    private final static double kImageRatio = 0.75;
    @FXML Group IncDecGroup;
    @FXML  Label InlineItemAmount1;
    @FXML Label holdLabel;


    // Don't change, muy importante
    public Product product; // was static
    private final IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private final Model model = Model.getInstance();
    private final ShoppingCart shoppingCart = model.getShoppingCart();


    @FXML private Button breadButt2;
    @FXML private Button breadButt3;
    List<Button> categoryButtons;
    List<Button> subCategoryButtons;

    @FXML private Button allaVaror;
    @FXML private Button fruktGront;
    @FXML private Button dryck;
    @FXML private Button kottFisk;
    @FXML private Button notterFron;
    @FXML private Button kolhydrater;
    @FXML private Button bak;
    @FXML private Button kryddor;
    @FXML private Button mejeri;
    @FXML private Button favoriter;

    @FXML private Button btnSub0;
    @FXML private Button btnSub1;
    @FXML private Button btnSub2;
    @FXML private Button btnSub3;
    @FXML private Button btnSub4;

    @FXML private Button HandlaButton;
    @FXML private Button HistorikButton;
    @FXML private Button HomeLogoButton;
    @FXML private Button RabatterButton;
    @FXML private TextField SearchBarNavBar;
    @FXML private Button ShoppingCartButton;

    @FXML private SVGPath circle;
    @FXML private SVGPath star;

    @FXML private Text arrow1;
    @FXML private Text arrow2;


    private String currentCategory = "allt";

    private Boolean inCategory = Boolean.FALSE;
    private HashMap<String, Pane> screen = new HashMap<>();

    private List<Product> productList;

    @FXML public Text nbrOfItems;


    ///////
    
    // <initialize>
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //model.getShoppingCart().addShoppingCartListener(this);
        updateProductList(model.getProducts());
        nbrOfItems.setText("0");

        ///////// CATEGORIES
        sortComboBox.getItems().addAll(
                "Sortera",
                "Pris (Högt till lågt)",
                "Pris (Lågt till Högt)",
                "Alfabet (A - Ö)"
        );

        sortComboBox.getSelectionModel().select("Sortera");

        sortComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (newValue == "Sortera") {
                    if(inCategory) {
                        updateProductList(productList);
                    } else {
                        List<Product> products = getCurrentCategory(currentCategory);
                        updateProductList(products);
                    }
                }
                if (newValue == "Pris (Högt till lågt)") {
                    List<Product> products;
                    if(inCategory) {
                        products = productList;
                    } else {
                        products = getCurrentCategory(currentCategory);
                    }
                    System.out.println(productList);
                    HashMap<Product, Double> hash = new HashMap<>();
                    for (Product product : products) {
                        hash.put(product, product.getPrice());
                    }
                    products = bubbleSort(hash);
                    products = products.reversed();

                    updateProductList(products);

                } else if (newValue == "Pris (Lågt till Högt)") {
                    List<Product> products;
                    if(inCategory) {
                        products = productList;
                    } else {
                        products = getCurrentCategory(currentCategory);
                    }
                    HashMap<Product, Double> hash = new HashMap<>();
                    for (Product product : products) {
                        hash.put(product, product.getPrice());
                    }
                    products = bubbleSort(hash);
                    updateProductList(products);

                } else if (newValue == "Alfabet (A - Ö)") {
                    List<Product> products;
                    if(inCategory) {
                        products = productList;
                    } else {
                        products = getCurrentCategory(currentCategory);
                    }
                    products.sort(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            char c1 = o1.getName().charAt(0);
                            char c2 = o2.getName().charAt(0);
                            return c1 - c2;
                        }
                    });
                    updateProductList(products);
                }
            }
            private List<Product> bubbleSort(HashMap<Product, Double> hash) {
                List<Product> keys = new ArrayList<>();
                List<Double> values = new ArrayList<>();
                for (Map.Entry<Product, Double> entry : hash.entrySet()) {
                    keys.add(entry.getKey());
                    values.add(entry.getValue());
                }

                int n = values.size();
                Double temp = 0.0;
                Product temp2;

                for (int i = 0; i < n; i++) {
                    for (int j = 1; j < (n - i); j++) {
                        if (values.get(j - 1) > values.get(j)) {
                            temp = values.get(j - 1);
                            values.set(j - 1, values.get(j));
                            values.set(j, temp);

                            temp2 = keys.get(j - 1);
                            keys.set(j - 1, keys.get(j));
                            keys.set(j, temp2);
                        }

                    }
                }
                return keys;
            }
        });

        btnSub0.setVisible(false);
        btnSub1.setVisible(false);
        btnSub2.setVisible(false);
        btnSub3.setVisible(false);
        btnSub4.setVisible(false);

        dataHandler.placeOrder();

        String circle_path = "M0 25C0 11.1929 11.1929 0 25 0C38.8071 0 50 11.1929 50 25C50 38.8071 38.8071 50 25 50C11.1929 50 0 38.8071 0 25Z";
        String star_path = "M24.0672 8.41411C24.3967 7.56134 25.6033 7.56134 25.9328 8.41411L29.6922 18.1436C29.8411 18.529 30.2118 18.7832 30.625 18.7832H42.3684C43.3784 18.7832 43.7516 20.11 42.8897 20.6365L33.7042 26.2485C33.2918 26.5004 33.1186 27.0114 33.2928 27.4622L36.868 36.715C37.2125 37.6067 36.2296 38.4271 35.4139 37.9288L25.5214 31.8849C25.2013 31.6894 24.7987 31.6894 24.4786 31.8849L14.5861 37.9288C13.7704 38.4271 12.7875 37.6067 13.132 36.715L16.7072 27.4622C16.8814 27.0114 16.7082 26.5004 16.2958 26.2485L7.11026 20.6365C6.24838 20.11 6.62161 18.7832 7.63162 18.7832H19.375C19.7882 18.7832 20.1589 18.529 20.3078 18.1436L24.0672 8.41411Z";

        circle.setContent(circle_path);
        star.setContent(star_path);

        if(dataHandler.isFavorite(product)){
            circle.getStyleClass().add("circle_favorite");
            star.getStyleClass().add("star_favorite");
        }

        categoryButtons = new ArrayList<>();
        categoryButtons.add(allaVaror);
        categoryButtons.add(fruktGront);
        categoryButtons.add(dryck);
        categoryButtons.add(kottFisk);
        categoryButtons.add(notterFron);
        categoryButtons.add(kolhydrater);
        categoryButtons.add(bak);
        categoryButtons.add(kryddor);
        categoryButtons.add(mejeri);
        categoryButtons.add(favoriter);

        subCategoryButtons = new ArrayList<>();
        subCategoryButtons.add(btnSub0);
        subCategoryButtons.add(btnSub1);
        subCategoryButtons.add(btnSub2);
        subCategoryButtons.add(btnSub3);
        subCategoryButtons.add(btnSub4);

        clearSelectedStyle();
        clearSelectedStyleSub();
        allaVaror.getStyleClass().add("selected_category");
        btnSub0.getStyleClass().add("selected_category");

        //openNamePanel();
        openFooter();
        removeEmptyOrders();

        breadButt2.setVisible(false);
        breadButt3.setVisible(false);

        arrow1.setVisible(false);
        arrow2.setVisible(false);

    }
    // <\initialize>


    // <detailedView>
    public void initPopUp(int prod_id, Label ProductPanelLabel) {
        this.product = model.getProduct(prod_id);

        nameLabel.setText(product.getName());

        prizeLabel.setText(String.format("%.2f", product.getPrice()) + " " + product.getUnit());
        imageView.setImage(model.getImage(product));
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

        detailView.toFront();
        holdLabel = ProductPanelLabel;
        initIncDecFunc();
    }


    private void initIncDecFunc() {
        List<ShoppingItem> items = dataHandler.getShoppingCart().getItems();
        if (items != null) {
            for (ShoppingItem item : items) {
                if (item.getProduct() == product) {
                    InlineItemAmount1.setText(String.valueOf((int)item.getAmount()));
                    IncDecGroup.toFront();
                }else{
                    IncDecGroup.toBack();
                }
            }
        }
    }

    private void initIncDecFunc(Label ProductPanelLabel) {
        List<ShoppingItem> items = dataHandler.getShoppingCart().getItems();
        if (items != null) {
            for (ShoppingItem item : items) {
                if (item.getProduct() == product) {
                    ProductPanelLabel.setText(String.valueOf((int)item.getAmount())); // DEN ÄNDRAR I POP UP OCH INTE KORTET
                    IncDecGroup.toFront();
                }else{
                    IncDecGroup.toBack();
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
        updateNbrOfItems();
    }
    // <\detailedView>


    // <navigation>
    @FXML
    public void openShoppingCartFilip() {

        AnchorPane shoppingCartFilipPane = new ShoppingCartFilipController(this);
        dynamicPane.getChildren().clear();
        dynamicPane.getChildren().add(shoppingCartFilipPane);
        dynamicPane.toFront();
    }

    @FXML
    public void openPayment() {
        AnchorPane paymentPain = new ShoppingCartController(this);
        dynamicPane.getChildren().clear();
        dynamicPane.getChildren().add(paymentPain);
        dynamicPane.toFront();
    }

    @FXML
    public void openAdvertisment() {
        AnchorPane advertismentPane = new AdvertismentController(this);
        dynamicPane.getChildren().clear();
        dynamicPane.getChildren().add(advertismentPane);
        dynamicPane.toFront();

        clearSelectedNavClasses();
        RabatterButton.getStyleClass().add("big_select_wowo_UwU");
    }

    @FXML
    public void openNamePanel() {
        AnchorPane advertismentPane = new NamePanel(this);
        dynamicPane.getChildren().clear();
        dynamicPane.getChildren().add(advertismentPane);
        dynamicPane.toFront();
    }

    @FXML
    public void openHistory() {
        AnchorPane historyPane = new HistorySelectItemController(this);

        dynamicPane.getChildren().clear();
        dynamicPane.getChildren().add(historyPane);
        dynamicPane.toFront();

        clearSelectedNavClasses();
        HistorikButton.getStyleClass().add("big_select_wowo_UwU");
    }

    @FXML
    public void closeDetailView() {
        initIncDecFunc(holdLabel);
        detailView.toBack();
    }

    private void clearSelectedNavClasses() {
        HistorikButton.getStyleClass().remove("big_select_wowo_UwU");
        HandlaButton.getStyleClass().remove("big_select_wowo_UwU");
        RabatterButton.getStyleClass().remove("big_select_wowo_UwU");
    }

    public void backHome() {
        categoryAllaVaror();
        dynamicPane.toBack();

        clearSelectedNavClasses();
        HandlaButton.getStyleClass().add("big_select_wowo_UwU");
    }

    public void openHistorySelectItem(int ordernr) {
        AnchorPane historyPane = new HistoryController(this, ordernr);
        dynamicPane.getChildren().add(historyPane);
        dynamicPane.toFront();
    }
    // <\navigation>





    // <product>
    @FXML
    public void addProductToCart() {
        // Adds latest opened product
        model.addToShoppingCart(product);
        initIncDecFunc();
        updateNbrOfItems();
    }

    @FXML
    public void updateNbrOfItems() {
        int tmp = 0;
        List<ShoppingItem> items = dataHandler.getShoppingCart().getItems();
        if (items != null) {
            for (ShoppingItem item : items) {
                tmp += item.getAmount();
            }
        }
        nbrOfItems.setText(String.valueOf(tmp));
    }

    @FXML
    private void addFavorite(ActionEvent event) {
        if (dataHandler.isFavorite(product)) {
            dataHandler.removeFavorite(product);
        } else {
            dataHandler.addFavorite(product);
        }
    }
    // <\product>


    private void openFooter(){
        AnchorPane footerPane = new FooterController(this);
        footerAnchorPane.getChildren().add(footerPane);
        footerAnchorPane.toFront();

    };

    private void updateProductList(List<Product> products) {
        if (inCategory == Boolean.TRUE) {
            if (productsFlowPane.getLayoutY() == 270)
                productsFlowPane.setLayoutY(productsFlowPane.getLayoutY() + 70);
        } else if (inCategory == Boolean.FALSE && productsFlowPane.getLayoutY() != 270) {
            productsFlowPane.setLayoutY(productsFlowPane.getLayoutY() - 70);
        }

        productsFlowPane.getChildren().clear();
        int tempI = 0;
        for (Product product : products) {
            if (tempI == 4 && currentCategory == "allt") {
                productsFlowPane.getChildren().add(new ProductPanel(product, this, Boolean.TRUE));
                tempI++;
                continue;
            }
            if (tempI % 13 == 0 && tempI != 0) {
                productsFlowPane.getChildren().add(new ProductPanel(product, this, Boolean.TRUE, Boolean.TRUE));
                tempI++;
                continue;
            }
            productsFlowPane.getChildren().add(new ProductPanel(product, this));
            tempI++;
        }
        productsFlowPane.getChildren().add(new ProductPanel(model.getProduct(3), this, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE));

    }



    // <categories>

    @FXML
    public void breadButton1() {
        categoryAllaVaror();
    }
    @FXML
    public void breadButton2() {
        arrow2.setVisible(false);
        switch (currentCategory) {
            case "kolhydrater":
                getCurrentCategory("kolhydrater");
                break;
            case "fruktgront":
                getCurrentCategory("fruktgront");
                break;
            case "dryck":
                getCurrentCategory("dryck");
                break;
            case "protein":
                getCurrentCategory("protein");
                break;
            case "notterfron":
                getCurrentCategory("notterfron");
                break;
            case "bak":
                getCurrentCategory("bak");
                break;
            case "snacks":
                getCurrentCategory("snacks");
                break;
            case "mejeri":
                getCurrentCategory("mejeri");
                break;
            case "favoriter":
                getCurrentCategory("favoriter");
                break;
        }
    }
    @FXML
    public void breadButton3() {

    }

    public List<Product> categoryAllaVaror() {
        inCategory = Boolean.FALSE;
        currentCategory = "allt";
        updateProductList(model.getProducts());
        btnSub0.setVisible(false);
        btnSub1.setVisible(false);
        btnSub2.setVisible(false);
        btnSub3.setVisible(false);
        btnSub4.setVisible(false);

        clearSelectedStyle();
        clearSelectedStyleSub();
        allaVaror.getStyleClass().add("selected_category");

        breadButt2.setVisible(false);
        breadButt3.setVisible(false);
        arrow1.setVisible(false);
        arrow2.setVisible(false);

        return model.getProducts();
    }
    public List<Product> categoryFruktGront() {
        inCategory = Boolean.TRUE;
        //visa knappar
        currentCategory = "fruktgront";
        List<Product> p1 = dataHandler.getProducts(ProductCategory.FRUIT);
        List<Product> p2 = dataHandler.getProducts(ProductCategory.BERRY);
        List<Product> p3 = dataHandler.getProducts(ProductCategory.CABBAGE);
        List<Product> p4 = dataHandler.getProducts(ProductCategory.CITRUS_FRUIT);
        List<Product> p5 = dataHandler.getProducts(ProductCategory.EXOTIC_FRUIT);
        List<Product> p6 = dataHandler.getProducts(ProductCategory.MELONS);
        List<Product> p7 = dataHandler.getProducts(ProductCategory.ROOT_VEGETABLE);
        List<Product> p8 = dataHandler.getProducts(ProductCategory.VEGETABLE_FRUIT);
        List<Product> p9 = dataHandler.getProducts(ProductCategory.HERB);

        List<Product> out = new ArrayList<Product>();
        Stream.of(p1, p2, p3, p4, p5, p6, p7, p8, p9).forEach(out::addAll);
        updateProductList(out);
        productList = out;

        btnSub0.setVisible(true);
        btnSub1.setVisible(true);
        btnSub2.setVisible(true);
        btnSub3.setVisible(true);
        btnSub4.setVisible(true);

        btnSub0.setText("Alla");
        btnSub1.setText("Frukt");
        btnSub2.setText("Bär");
        btnSub3.setText("Grönsaker");
        btnSub4.setText("Kryddor");

        clearSelectedStyle();
        clearSelectedStyleSub();
        fruktGront.getStyleClass().add("selected_category");
        btnSub0.getStyleClass().add("selected_category");

        breadButt3.setVisible(false);
        breadButt2.setVisible(true);
        breadButt2.setText("Frukt & grönt");
        arrow1.setVisible(true);


        return out;
    }
    public List<Product> categoryKolhydrater() {
        inCategory = Boolean.TRUE;

        currentCategory = "kolhydrater";

        List<Product> p1 = dataHandler.getProducts(ProductCategory.PASTA);
        List<Product> p2 = dataHandler.getProducts(ProductCategory.BREAD);
        List<Product> p3 = dataHandler.getProducts(ProductCategory.POTATO_RICE);

        List<Product> out = new ArrayList<Product>();
        Stream.of(p1, p2, p3).forEach(out::addAll);
        updateProductList(out);
        productList = out;

        btnSub0.setVisible(true);
        btnSub1.setVisible(true);
        btnSub2.setVisible(true);
        btnSub3.setVisible(true);
        btnSub4.setVisible(false);


        btnSub0.setText("Alla");
        btnSub1.setText("Pasta");
        btnSub2.setText("Ris & potatis");
        btnSub3.setText("Bröd");

        clearSelectedStyle();
        clearSelectedStyleSub();

        kolhydrater.getStyleClass().add("selected_category");
        btnSub0.getStyleClass().add("selected_category");

        breadButt3.setVisible(false);
        breadButt2.setVisible(true);
        breadButt2.setText("Kolhydrater");
        arrow1.setVisible(true);



        return out;
    }
    public List<Product> categoryBak() {
        inCategory = Boolean.FALSE;

        currentCategory = "bak";

        List<Product> p1 = dataHandler.getProducts(ProductCategory.FLOUR_SUGAR_SALT);
        updateProductList(p1);
        btnSub0.setVisible(false);
        btnSub1.setVisible(false);
        btnSub2.setVisible(false);
        btnSub3.setVisible(false);
        btnSub4.setVisible(false);

        clearSelectedStyle();
        bak.getStyleClass().add("selected_category");

        breadButt3.setVisible(false);
        breadButt2.setVisible(true);
        breadButt2.setText("Bak");
        arrow1.setVisible(true);

        return p1;
    }
    public List<Product> categoryDryck() {
        inCategory = Boolean.TRUE;

        currentCategory = "dryck";

        List<Product> p1 = dataHandler.getProducts(ProductCategory.COLD_DRINKS);
        List<Product> p2 = dataHandler.getProducts(ProductCategory.HOT_DRINKS);

        List<Product> out = new ArrayList<Product>();
        Stream.of(p1, p2).forEach(out::addAll);
        updateProductList(out);
        productList = out;

        btnSub0.setVisible(true);
        btnSub1.setVisible(true);
        btnSub2.setVisible(true);
        btnSub3.setVisible(false);
        btnSub4.setVisible(false);

        btnSub0.setText("Alla");
        btnSub1.setText("Varma drycker");
        btnSub2.setText("Kalla drycker");

        clearSelectedStyle();
        clearSelectedStyleSub();

        dryck.getStyleClass().add("selected_category");
        btnSub0.getStyleClass().add("selected_category");

        breadButt3.setVisible(false);
        breadButt2.setVisible(true);
        breadButt2.setText("Dryck");
        arrow1.setVisible(true);


        return out;
    }
    public List<Product> categorySnacks() {
        inCategory = Boolean.FALSE;

        currentCategory = "snacks";
        List<Product> p1 = dataHandler.getProducts(ProductCategory.SWEET);

        updateProductList(p1);

        btnSub0.setVisible(false);
        btnSub1.setVisible(false);
        btnSub2.setVisible(false);
        btnSub3.setVisible(false);
        btnSub4.setVisible(false);

        clearSelectedStyle();
        kryddor.getStyleClass().add("selected_category"); // TODO WHAT????????

        breadButt3.setVisible(false);
        breadButt2.setVisible(true);
        breadButt2.setText("Snacks");
        arrow1.setVisible(true);


        return p1;
    }
    public List<Product> categoryKottFisk() {
        inCategory = Boolean.TRUE;

        currentCategory = "protein";

        List<Product> p1 = dataHandler.getProducts(ProductCategory.FISH);
        List<Product> p2 = dataHandler.getProducts(ProductCategory.MEAT);
        List<Product> p3 = dataHandler.getProducts(ProductCategory.POD);

        List<Product> out = new ArrayList<Product>();
        Stream.of(p1, p2, p3).forEach(out::addAll);
        updateProductList(out);
        productList = out;

        btnSub0.setVisible(true);
        btnSub1.setVisible(true);
        btnSub2.setVisible(true);
        btnSub3.setVisible(true);

        btnSub0.setText("Alla");
        btnSub1.setText("Fisk");
        btnSub2.setText("Kött");
        btnSub3.setText("Bönor");
        btnSub4.setVisible(false);

        clearSelectedStyle();
        clearSelectedStyleSub();
        kottFisk.getStyleClass().add("selected_category");
        btnSub0.getStyleClass().add("selected_category");

        breadButt3.setVisible(false);
        breadButt2.setVisible(true);
        breadButt2.setText("Protein");
        arrow1.setVisible(true);


        return out;
    }
    public List<Product> categoryMejeri() {
        inCategory = Boolean.FALSE;

        currentCategory = "mejeri";

        List<Product> p1 = dataHandler.getProducts(ProductCategory.DAIRIES);
        updateProductList(p1);

        btnSub0.setVisible(false);
        btnSub1.setVisible(false);
        btnSub2.setVisible(false);
        btnSub3.setVisible(false);
        btnSub4.setVisible(false);

        clearSelectedStyle();
        mejeri.getStyleClass().add("selected_category");

        breadButt3.setVisible(false);
        breadButt2.setVisible(true);
        breadButt2.setText("Mejeri");
        arrow1.setVisible(true);


        return p1;
    }
    public List<Product> categoryNotterFron() {
        inCategory = Boolean.FALSE;

        currentCategory = "notterfron";

        List<Product> p1 = dataHandler.getProducts(ProductCategory.NUTS_AND_SEEDS);
        updateProductList(p1);

        btnSub0.setVisible(false);
        btnSub1.setVisible(false);
        btnSub2.setVisible(false);
        btnSub3.setVisible(false);
        btnSub4.setVisible(false);

        clearSelectedStyle();
        notterFron.getStyleClass().add("selected_category");

        breadButt3.setVisible(false);
        breadButt2.setVisible(true);
        breadButt2.setText("Nötter & frön");
        arrow1.setVisible(true);


        return p1;
    }
    public List<Product> categoryFavoriter() {
        inCategory = Boolean.FALSE;

        currentCategory = "favoriter";

        List<Product> p1 = dataHandler.favorites();
        updateProductList(p1);

        btnSub0.setVisible(false);
        btnSub1.setVisible(false);
        btnSub2.setVisible(false);
        btnSub3.setVisible(false);
        btnSub4.setVisible(false);

        clearSelectedStyle();
        favoriter.getStyleClass().add("selected_category");

        breadButt3.setVisible(false);
        breadButt2.setVisible(true);
        breadButt2.setText("Favoriter");
        arrow1.setVisible(true);


        return p1;
    }

    private List<Product> getCurrentCategory(String category) {
        List<Product> products = null;
        switch(category) {
            case "kolhydrater":
                products = categoryKolhydrater();
                break;
            case "allt":
                products = model.getProducts();
                break;
            case "fruktgront":
                products = categoryFruktGront();
                break;
            case "dryck":
                products = categoryDryck();
                break;
            case "protein":
                products = categoryKottFisk();
                break;
            case "notterfron":
                products = categoryNotterFron();
                break;
            case "bak":
                products = categoryBak();
                break;
            case "snacks":
                products = categorySnacks();
                break;
            case "mejeri":
                products = categoryMejeri();
                break;
            case "favoriter":
                products = categoryFavoriter();
                break;
            default:
                products = model.getProducts();
        }
        return products;
    }


    public void button0(ActionEvent event) {
        clearSelectedStyleSub();
        btnSub0.getStyleClass().add("selected_category");

        switch(currentCategory) {
            case "kolhydrater":
                productList = categoryKolhydrater();
                break;
            case "fruktgront":
                productList = categoryFruktGront();
                break;
            case "dryck":
                productList = categoryDryck();
                break;
            case "protein":
                productList = categoryKottFisk();
                break;
        }
    }

    public void button1(ActionEvent event) {
        clearSelectedStyleSub();
        btnSub1.getStyleClass().add("selected_category");
        switch(currentCategory) {
            case "kolhydrater":
                updateProductList(dataHandler.getProducts(ProductCategory.PASTA));
                productList = dataHandler.getProducts(ProductCategory.PASTA);
                breadButt3.setVisible(true);
                breadButt3.setText("Pasta");
                arrow2.setVisible(true);

                break;
            case "fruktgront":
                List<Product> p1 = dataHandler.getProducts(ProductCategory.FRUIT);
                List<Product> p2 = dataHandler.getProducts(ProductCategory.CITRUS_FRUIT);
                List<Product> p3 = dataHandler.getProducts(ProductCategory.EXOTIC_FRUIT);
                List<Product> p4 = dataHandler.getProducts(ProductCategory.MELONS);

                List<Product> out = new ArrayList<Product>();
                Stream.of(p1, p2, p3, p4).forEach(out::addAll);
                updateProductList(out);
                productList = out;

                breadButt3.setVisible(true);
                breadButt3.setText("Frukt");
                arrow2.setVisible(true);

                break;
            case "dryck":
                updateProductList(dataHandler.getProducts(ProductCategory.HOT_DRINKS));
                productList = dataHandler.getProducts(ProductCategory.HOT_DRINKS);

                breadButt3.setVisible(true);
                breadButt3.setText("Varma drycker");
                arrow2.setVisible(true);

                break;
            case "protein":
                updateProductList(dataHandler.getProducts(ProductCategory.FISH));
                productList = dataHandler.getProducts(ProductCategory.FISH);
                breadButt3.setVisible(true);
                breadButt3.setText("Fisk");
                arrow2.setVisible(true);

                break;
        }
    }

    public void button2(ActionEvent event) {
        clearSelectedStyleSub();
        btnSub2.getStyleClass().add("selected_category");

        switch(currentCategory) {
            case "kolhydrater":
                updateProductList(dataHandler.getProducts(ProductCategory.POTATO_RICE));
                productList = dataHandler.getProducts(ProductCategory.POTATO_RICE);

                breadButt3.setVisible(true);
                breadButt3.setText("Ris & potatis");
                arrow2.setVisible(true);

                break;
            case "fruktgront":
                updateProductList(dataHandler.getProducts(ProductCategory.BERRY));
                productList = dataHandler.getProducts(ProductCategory.BERRY);
                breadButt3.setVisible(true);
                breadButt3.setText("Bär");
                arrow2.setVisible(true);

                break;
            case "dryck":
                updateProductList(dataHandler.getProducts(ProductCategory.COLD_DRINKS));
                productList = dataHandler.getProducts(ProductCategory.COLD_DRINKS);
                breadButt3.setVisible(true);
                breadButt3.setText("Kalla drycker");
                arrow2.setVisible(true);

                break;
            case "protein":
                updateProductList(dataHandler.getProducts(ProductCategory.MEAT));
                productList = dataHandler.getProducts(ProductCategory.MEAT);
                breadButt3.setVisible(true);
                breadButt3.setText("Kött");
                arrow2.setVisible(true);

                break;
        }
    }

    public void button3(ActionEvent event) {
        clearSelectedStyleSub();
        btnSub3.getStyleClass().add("selected_category");

        switch(currentCategory) {
            case "kolhydrater":
                updateProductList(dataHandler.getProducts(ProductCategory.BREAD));
                productList = dataHandler.getProducts(ProductCategory.BREAD);

                breadButt3.setVisible(true);
                breadButt3.setText("Bröd");
                arrow2.setVisible(true);

                break;
            case "fruktgront":
                List<Product> p1 = dataHandler.getProducts(ProductCategory.CABBAGE);
                List<Product> p2 = dataHandler.getProducts(ProductCategory.ROOT_VEGETABLE);
                List<Product> p3 = dataHandler.getProducts(ProductCategory.VEGETABLE_FRUIT);

                List<Product> out = new ArrayList<Product>();
                Stream.of(p1, p2, p3).forEach(out::addAll);
                updateProductList(out);
                productList = out;

                breadButt3.setVisible(true);
                breadButt3.setText("Grönsaker");
                arrow2.setVisible(true);

                break;
            case "protein":
                updateProductList(dataHandler.getProducts(ProductCategory.POD));
                productList = dataHandler.getProducts(ProductCategory.POD);

                breadButt3.setVisible(true);
                breadButt3.setText("Bönor");
                arrow2.setVisible(true);

                break;
        }
    }

    public void button4(ActionEvent event) {
        clearSelectedStyleSub();
        btnSub4.getStyleClass().add("selected_category");

        switch(currentCategory) {
            case "fruktgront":
                updateProductList(dataHandler.getProducts(ProductCategory.HERB));
                productList = dataHandler.getProducts(ProductCategory.HERB);

                breadButt3.setVisible(true);
                breadButt3.setText("Kryddor");
                arrow2.setVisible(true);

                break;
        }
    }

    // <\categories>


    // <utility>
    @FXML
    void performSearchAction(ActionEvent event) {
        List<Product> matches = model.findProducts(SearchBarNavBar.getText());
        updateProductList(matches);
    }

    @FXML
    void toTopOfScroll(MouseEvent event){
        scrollPaneForProducts.setVvalue(0);
    }

    void clearSelectedStyle(){
        for (Button btn : categoryButtons) {
            try{
                btn.getStyleClass().remove("selected_category");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    void clearSelectedStyleSub(){
        for (Button btn : subCategoryButtons) {
            try {
                btn.getStyleClass().remove("selected_category");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
    }


    void testMainFunctionCall(){
        System.out.println("testMainFunctionCall");
    }
    void removeEmptyOrders(){
        List<Order> removeOrders = new ArrayList<>();
        for (Order order : dataHandler.getOrders()) {
            if (order.getItems().isEmpty()){
                System.out.println("Remove Order");
                removeOrders.add((order));
            }
        }
        for (Order order : removeOrders) {
            dataHandler.getOrders().remove(order);
        }
    }
    // <\ utility>
}
