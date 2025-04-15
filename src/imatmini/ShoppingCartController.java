package imatmini;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.*;


import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ShoppingCartController extends AnchorPane implements Initializable {
    // Pages
    @FXML private StackPane stack;
    @FXML private AnchorPane anchorPaneShoppingCart;
    @FXML private AnchorPane buyerInfo;
    @FXML private AnchorPane deliverInfo;
    @FXML private AnchorPane verifyingPayment;
    @FXML private AnchorPane paymentDone;
    @FXML private final Model model = Model.getInstance();
    private final IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    Customer customer = dataHandler.getCustomer();
    CreditCard creditCard = dataHandler.getCreditCard();
    ShoppingCart shoppingCart = dataHandler.getShoppingCart();
    private MainController mainController;


    // Delivery info
    @FXML private TextField textFieldFirstName;
    @FXML private TextField textFieldLastName;
    @FXML private TextField textFieldDeliveryAddress;
    @FXML private TextField textFieldPostNumber;
    @FXML private TextField textFieldPostArea;
    @FXML private List<TextField> userInput;

    @FXML private ToggleGroup radioButtonGroupTime;
    @FXML private RadioButton radioButtonMorning;
    @FXML private RadioButton radioButtonLunch;
    @FXML private RadioButton radioButtonEvening;

    @FXML private ComboBox ComboBoxDay;

    @FXML private CheckBox checkBoxSaveUserInput;

    @FXML private Text errorMsgPostNr;



    // Billing
    @FXML private TextField textFieldBillingAddress;
    @FXML private TextField textFieldBillingPostNumber;
    @FXML private TextField textFieldBillingName;
    @FXML private TextField textFieldCardNumber1;
    @FXML private TextField textFieldCardNumber2;
    @FXML private TextField textFieldCardNumber3;
    @FXML private TextField textFieldCardNumber4;
    @FXML private TextField textFieldCVC;
    @FXML private List<TextField> billingInput;

    @FXML private ComboBox comboBoxCardType;
    @FXML private ComboBox comboBoxExpressionDateMonth;
    @FXML private ComboBox comboBoxExpressionDateYear;

    @FXML private CheckBox checkBoxSaveBilling;
    @FXML private CheckBox checkBoxUseUserInformation;


    @FXML private Text errorMsgPostNrBilling;
    @FXML private Text errorMsgCardNr;
    @FXML private Text errorMsgCVC;

    // Conformation
    @FXML private SVGPath SVG_check;


    // Payment Done
    @FXML private Text textReceiptName;
    @FXML private Text textReceiptAddress;
    @FXML private Text textReceiptPostArea;
    @FXML private Text textReceiptPostCode;
    @FXML private Text textReceiptBillingAddress;
    @FXML private Text textReceiptCardNumber;
    @FXML private Text textReceiptTotal;
    @FXML private Text textReceiptOrderNumber;

    @FXML private TextArea cartTextArea;


    // <\VARIBALES>




    public ShoppingCartController(MainController mainController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShoppingCart.fxml"));
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
        //model.getShoppingCart().addShoppingCartListener(this);

        //updateView();
    }

    // <initialize>
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userInput = Arrays.asList(
                textFieldFirstName,
                textFieldLastName,
                textFieldDeliveryAddress,
                textFieldPostNumber,
                textFieldPostArea
        );

        Calendar deliveryDate = Calendar.getInstance();

        // Save start date
        Calendar startDay = Calendar.getInstance();

        for (int i = 0; i < 7; i++){
            ComboBoxDay.getItems().add(deliveryDate.get(Calendar.YEAR) + "-" + deliveryDate.get(Calendar.MONTH) + "-" + (deliveryDate.get(Calendar.DATE)));
            deliveryDate.add(Calendar.DATE, 1);
        }
        ComboBoxDay.getItems().add("+"); // TODO not implmented in backend
        ComboBoxDay.getSelectionModel().select(startDay.get(Calendar.YEAR) + "-" + startDay.get(Calendar.MONTH) + "-" + (startDay.get(Calendar.DATE)));


        // time of delivery
        radioButtonGroupTime = new ToggleGroup();
        radioButtonMorning.setToggleGroup(radioButtonGroupTime);
        radioButtonLunch.setToggleGroup(radioButtonGroupTime);
        radioButtonEvening.setToggleGroup(radioButtonGroupTime);
        radioButtonMorning.setSelected(true);


        billingInput = Arrays.asList(
                textFieldBillingAddress,
                textFieldBillingPostNumber,
                textFieldBillingName,

                textFieldCardNumber1,
                textFieldCardNumber2,
                textFieldCardNumber3,
                textFieldCardNumber4,
                textFieldCVC
        );

        textFieldCardNumber1.setPromptText("xxxx"); //to set the hint text
        textFieldCardNumber2.setPromptText("xxxx"); //to set the hint text
        textFieldCardNumber3.setPromptText("xxxx"); //to set the hint text
        textFieldCardNumber4.setPromptText("xxxx"); //to set the hint text

        textFieldCVC.setPromptText("xxx"); //to set the hint text

        // EXPIRATION

        // YEAR
        Calendar expirationYearValue = Calendar.getInstance();
        for(int i = 0; i < 10; i++){
            comboBoxExpressionDateYear.getItems().add(expirationYearValue.get(Calendar.YEAR));
            expirationYearValue.add(Calendar.YEAR, 1);
        }
        comboBoxExpressionDateYear.getSelectionModel().select(Integer.valueOf(startDay.get(Calendar.YEAR))); // Integer.valueOf (magic)


        // MONTH
        Calendar expirationMonthValue = Calendar.getInstance();
        expirationMonthValue.set(Calendar.MONTH, 0);
        DateFormatSymbols dfs = new DateFormatSymbols();

        for(int i = 0; i < 12; i++){
            comboBoxExpressionDateMonth.getItems().add(dfs.getMonths()[expirationMonthValue.get(Calendar.MONTH)]);
            expirationMonthValue.add(Calendar.MONTH, 1);
        }
        comboBoxExpressionDateMonth.getSelectionModel().select(startDay.get(Calendar.MONTH));

        comboBoxCardType.getItems().add("Mastercard");
        comboBoxCardType.getItems().add("Swedbank");
        comboBoxCardType.getItems().add("SEB");
        comboBoxCardType.getItems().add("Nordea");
        comboBoxCardType.getSelectionModel().select("Mastercard");


        addOpenComboBoxOnEnter(comboBoxCardType);
        addOpenComboBoxOnEnter(comboBoxExpressionDateMonth);
        addOpenComboBoxOnEnter(comboBoxExpressionDateYear);
        addOpenComboBoxOnEnter(ComboBoxDay); // ARGH stort c


        perFillUserInput();
    }

    // <\initialize>


    @FXML
    private void toShoppingCart(ActionEvent event){
        mainController.openShoppingCartFilip();
    }

    @FXML
    private void backToDelivernce(ActionEvent event){
        gotoDeliverInfo(event); // prefill bugg prob
    }

    // Buttons change page
    @FXML
    private void gotoDeliverInfo(ActionEvent event) {
        perFillUserInput();
        deliverInfo.toFront();
    }




    // <gotoBuyerInfo>
    @FXML
    private void perFillUserInput(){
        checkBoxSaveUserInput.setSelected(true);
        textFieldFirstName.setText(customer.getFirstName());
        textFieldLastName.setText(customer.getLastName());
        textFieldDeliveryAddress.setText(customer.getAddress());
        textFieldPostNumber.setText(customer.getPostCode());
        textFieldPostArea.setText(customer.getPostAddress());
    }

    @FXML
    private void gotoBuyerInfo(ActionEvent event) {
        boolean willFail = false;

        if(!allInputDone(userInput)){
            willFail = true;
        }

        if(!checkOnlyNumbers(textFieldPostNumber)){
            errorMsgPostNr.setText("Det är inte nummer");
            addError(textFieldPostNumber);
            willFail = true;
        }
        if(textFieldPostNumber.getText().replaceAll("\\s+","").length() != 5){
            errorMsgPostNr.setText("Den ska vara 5 lång");
            addError(textFieldPostNumber);
            willFail = true;
        }

        if (willFail){
            return;
        }

        if(checkBoxSaveUserInput.isSelected()){
            System.out.println("save consumer info");
            saveUserInput();
        }

        preFillBillingData();
        buyerInfo.toFront();
    }



    @FXML
    private void saveUserInput(){
        dataHandler.getCustomer().setFirstName(textFieldFirstName.getText());
        dataHandler.getCustomer().setLastName(textFieldLastName.getText());
        dataHandler.getCustomer().setAddress(textFieldDeliveryAddress.getText());
        dataHandler.getCustomer().setPostCode(textFieldPostNumber.getText());
        dataHandler.getCustomer().setPostAddress(textFieldPostArea.getText());
    }
    // <\gotoBuyerInfo>


    // <gotoVerifyingPayment>
    @FXML
    private void preFillBillingData(){
        CreditCard creditCard = dataHandler.getCreditCard();
        checkBoxSaveBilling.setSelected(true);
        textFieldBillingName.setText(creditCard.getHoldersName());


        String cardNumber = creditCard.getCardNumber();
        if (cardNumber.length() == 16){
            textFieldCardNumber1.setText(cardNumber.substring(0,4));
            textFieldCardNumber2.setText(cardNumber.substring(4,8));
            textFieldCardNumber3.setText(cardNumber.substring(8,12));
            textFieldCardNumber4.setText(cardNumber.substring(12,16));
        }

        if(creditCard.getVerificationCode() != 0){
            textFieldCVC.setText(String.valueOf(creditCard.getVerificationCode()));
        }

        String userName = textFieldFirstName.getText() + " " + textFieldLastName.getText();
        textFieldBillingName.setText(userName);
        textFieldBillingAddress.setText(textFieldDeliveryAddress.getText());
        textFieldBillingPostNumber.setText(textFieldPostNumber.getText());

        comboBoxExpressionDateMonth.getSelectionModel().select(creditCard.getValidMonth());
    }

    @FXML
    private void doNotUseUserInformation(){
        textFieldBillingName.setText("");
        textFieldBillingAddress.setText("");
        textFieldBillingPostNumber.setText("");
    }

    @FXML
    void limitTextFields(KeyEvent event) {
        removeError(event);
        int maxLength = 4 - 1;
        TextField tf = (TextField) event.getSource();
        if (tf.getText().length() > maxLength) {
            tf.deletePreviousChar();
        }
    }
    @FXML
    void limitCVC(KeyEvent event) {
        removeError(event);
        int maxLength = 3 - 1;
        TextField tf = (TextField) event.getSource();
        if (tf.getText().length() > maxLength) {
            tf.deletePreviousChar();
        }
    }

    @FXML
    private void gotoPaymentDone(ActionEvent event) {
        // check user input
        boolean willFail = false;
        if(!allInputDone(billingInput)){
            willFail = true;
        }


        if(!checkOnlyNumbers(textFieldBillingPostNumber)){
            errorMsgPostNrBilling.setText("Inte nummer");
            addError(textFieldBillingPostNumber);
            willFail = true;
        }

        if(textFieldBillingPostNumber.getText().replaceAll("\\s+","").length() != 5){
            errorMsgPostNrBilling.setText("Inte 5 lång");
            addError(textFieldBillingPostNumber);
            willFail = true;willFail = true;
        }

        List<TextField> creditCardNumberInput = Arrays.asList(
                textFieldCardNumber1,
                textFieldCardNumber2,
                textFieldCardNumber3,
                textFieldCardNumber4);

        for (TextField cardNumber : creditCardNumberInput) {
            if(!checkOnlyNumbers(cardNumber)){
                errorMsgCardNr.setText("Inte nummer");
                addError(cardNumber);
                willFail = true;
            }
            if (cardNumber.getText().trim().length() != 4) {
                errorMsgCardNr.setText("Inte 4 lång");
                addError(cardNumber);
                willFail = true;
            }
        }

        if( !checkOnlyNumbers(textFieldCVC)){
            errorMsgCVC.setText("Inte nummer");
            addError(textFieldCVC);
            willFail = true;
        }
        if(textFieldCVC.getText().trim().length() != 3){
            errorMsgCVC.setText("Inte 3 lång");
            addError(textFieldCVC);
            willFail = true;
        }

        if (willFail){
            return;
        }

        if(checkBoxSaveBilling.isSelected()){
            System.out.println("save billing input");
            saveBillingInput();
        }

        fillOutReceipt();

        dataHandler.placeOrder(true);
        mainController.updateNbrOfItems();
        paymentDone.toFront();
    }



    @FXML
    private void saveBillingInput(){
        creditCard.setHoldersName(textFieldBillingName.getText());
        //textFieldBillingAddress
        //textFieldBillingPostNumber


        String cardNumber = textFieldCardNumber1.getText() +
                            textFieldCardNumber2.getText() +
                            textFieldCardNumber3.getText() +
                            textFieldCardNumber4.getText();

        creditCard.setCardNumber(cardNumber);

        creditCard.setVerificationCode(Integer.parseInt(textFieldCVC.getText()));

        //comboBoxExpressionDateMonth
        //comboBoxExpressionDateYear

        creditCard.setCardType((String) comboBoxCardType.getValue());
    }
    // <\gotoVerifyingPayment>




    // <gotoPaymentDone>
    @FXML
    private void fillOutReceipt(){
        String check_path = "M8 38.2568L18.08 52.4254C18.4201 52.9086 18.856 53.3014 19.3548 53.5742C19.8536 53.8467 20.4024 53.9924 20.96 53.9997C21.5086 54.007 22.0518 53.8798 22.55 53.6287C23.0482 53.3772 23.4889 53.0075 23.84 52.5465L56 10";

        SVG_check.setContent(check_path);


        SVG_check.getStyleClass().add("check");


        textReceiptName.setText(customer.getFirstName() + " " + customer.getLastName());
        textReceiptAddress.setText(customer.getAddress());
        textReceiptPostArea.setText(customer.getPostAddress());
        textReceiptPostCode.setText(customer.getPostCode());
        //textReceiptBillingAddress.setText(creditCard.get); // TODO does not work
        textReceiptCardNumber.setText(creditCard.getCardNumber().substring(12,15));
        textReceiptOrderNumber.setText(String.valueOf(dataHandler.getOrders().getLast().getOrderNumber()));



        textReceiptTotal.setText(String.valueOf(shoppingCart.getTotal()));


        String cartText = "";

        ShoppingCart shoppingCart = Model.getInstance().getShoppingCart();
        for (ShoppingItem item: shoppingCart.getItems()) {
            cartText = cartText + item.getProduct().getName() + " " + item.getAmount() + "\n";
        }
        cartTextArea.setText(cartText);
    }
    // <\gotoPaymentDone>




    @FXML
    private void backToHome(MouseEvent event){
        mainController.backHome();
    }



    // <utility>

    @FXML
    private boolean allInputDone(List<TextField> textFields){
        boolean willFail = false;

        for (TextField tf : textFields) {
            try {
                if(tf.getText().isEmpty()){
                    addError(tf);
                    willFail = true;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
        return !willFail;
    }


    @FXML
    private boolean checkOnlyNumbers(TextField tf){
        Pattern pattern = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(
                tf.getText().replaceAll("\\s+","")); // NO SPACE
        boolean matchFound = matcher.find();

        if(matchFound) {
            addError(tf);
            return false;
        }
        return true;
    }


    @FXML
    private void addError(Node node){
        if(!node.getStyleClass().contains("error")){
            node.getStyleClass().add("error");
        }
    }
    @FXML
    private void removeError(KeyEvent event){
        Node src = (Node) event.getSource();
        try{
            src.getStyleClass().remove("error");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
    }


    private void addOpenComboBoxOnEnter(ComboBox comboBox){
        comboBox.addEventFilter(KeyEvent.KEY_PRESSED, event ->{
            if(event.getCode() == KeyCode.ENTER){
                comboBox.show();
                event.consume();
            }
        });
    }
    // <\Utility>

}
