/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatmini;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;

/**
 *
 * @author oloft
 */
public class NamePanel extends AnchorPane implements ShoppingCartListener {
    @FXML TextField username;
    @FXML TextField password;

    @FXML TextField setUsername;
    @FXML TextField setPassword;
    @FXML TextField setPasswordAgain;

    @FXML Button createAccountButton;
    @FXML Button loginButton;
    @FXML Button toCreateAccountButton;

    @FXML AnchorPane createAccountAnchorPane;
    @FXML AnchorPane logginAnchorPane;
    @FXML AnchorPane confermationAchorPane;


    private Model model = Model.getInstance();
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    private Customer customer;
    private User user;
    private imatmini.MainController mainController;

    public NamePanel(imatmini.MainController mainController) {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NamePanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainController = mainController;
        this.customer = model.getCustomer();
        this.user = IMatDataHandler.getInstance().getUser();
        model.getShoppingCart().addShoppingCartListener(this);

        updateView();
    }

    @FXML
    public void closeNameView(){

        // Save values
        //customer.setFirstName(fnameTextField.getText());
        //customer.setLastName(lnameTextField.getText());
        //mainController.closeNameView();

    }

    @FXML
    private void loginFunc(){
        if (user == null){
            createAccountAnchorPane.toFront();
            return;
        }

        if(!username.getText().equals(user.getUserName())){
            addError(username);
        }

        if(!password.getText().equals(user.getPassword())){
            addError(password);
        }
        toMain();
    }

    @FXML
    private void createUser(){
        boolean willFail = false;

        if(setUsername.getText().trim().isEmpty()){
            addError(setUsername);
            willFail = true;
        }

        if(setPassword.getText().trim().isEmpty()){
            willFail = true;
            addError(setPassword);
        }

        try {
            if(!setPasswordAgain.getText().equals(setPassword.getText())){
                addError(setPasswordAgain);
                willFail = true;
            }
        }catch (NullPointerException exception){

        }
        if (willFail == true){
            return;
        }

        user.setUserName(setUsername.getText());
        user.setPassword(setPassword.getText());

        confermationAchorPane.toFront();
    }

    @FXML
    private void toMain(){
        mainController.backHome();
    }


    @Override
    public void shoppingCartChanged(CartEvent evt) {
        updateView();
    }



    @FXML
    private void toCreateAccount(MouseEvent event){
        createAccountAnchorPane.toFront();
    }
    @FXML
    private void toLogin(MouseEvent event){
        logginAnchorPane.toFront();
    }



    private void updateView() {

        //fnameTextField.setText(customer.getFirstName());
        //lnameTextField.setText(customer.getLastName());

        String cartText = "";

        ShoppingCart shoppingCart = Model.getInstance().getShoppingCart();

        for (ShoppingItem item: shoppingCart.getItems()) {

            cartText = cartText + item.getProduct().getName() + " " + item.getAmount() + "\n";
        }
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
}
