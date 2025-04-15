package imatmini;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
//import imatmini.rea;
import java.io.IOException;
import java.util.ArrayList;

public class AdvertismentController extends AnchorPane {
    @FXML
    AnchorPane RabbatAnchor1;
    @FXML
    Button RabbatNasta;
    @FXML
    Button RabbatFaregaende;
    @FXML
    Button RabbatFortsattHandla;
    @FXML
    AnchorPane AnchorSide1;
    @FXML
    AnchorPane AnchorSide2;
    @FXML
    ImageView RabbatView1;
    @FXML
    ImageView RabbatView2;
    @FXML
    ImageView RabbatView3;
    @FXML
    ImageView RabbatView4;
    @FXML
    ImageView RabbatView5;
    @FXML
    ImageView RabbatView6;
    @FXML
    ImageView RabbatView7;
    @FXML
    ImageView RabbatView8;
    @FXML
    ImageView RabbatView9;
    @FXML
    ImageView RabbatView10;
    @FXML
    ImageView RabbatView11;
    @FXML
    ImageView RabbatView12;
    @FXML
    ImageView RabbatView13;
    @FXML
    ImageView RabbatView14;
    @FXML
    ImageView RabbatView15;
    @FXML
    ImageView RabbatView16;
    @FXML
    ImageView RabbatView17;
    @FXML
    ImageView RabbatView18;
    @FXML
    ImageView RabbatView19;
    @FXML
    ImageView RabbatView20;
    @FXML
    ImageView RabbatView21;
    @FXML
    Text Text2 = new Text();
    @FXML
    Text Text3 = new Text();
    @FXML
    Text Text4 = new Text();
    @FXML
    Text Text5 = new Text();
    @FXML
    Text Text6 = new Text();
    @FXML
    Text Text7 = new Text();
    @FXML
    Text Text8 = new Text();
    @FXML
    Text Text9 = new Text();
    @FXML
    Text Text10 = new Text();
    @FXML
    Text Text11 = new Text();
    @FXML
    Text Text12 = new Text();
    @FXML
    Text Text13 = new Text();
    @FXML
    Text Text14 = new Text();
    @FXML
    Text Text15 = new Text();
    @FXML
    Text Text16 = new Text();
    @FXML
    Text Text17 = new Text();
    @FXML
    Text Text18 = new Text();
    @FXML
    Text Text19 = new Text();
    @FXML
    Text Text20 = new Text();
    @FXML
    Text Text21 = new Text();
    //@FXML
    //rea rea1;

    @FXML
    Text test = new Text();

    private MainController mainController;
    private Model model = Model.getInstance();

    private int currentPage = 1;

    public AdvertismentController(MainController mainController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Advertisment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainController = mainController;//testar denna med, man vet aldrig.
        AnchorSide1.toFront();
        PicturesOut();// lägger ut bilder
        TextOut();  //lägge rut text
        //updateView();
        RabbatNasta.toFront();
        RabbatFaregaende.toFront();
        RabbatFortsattHandla.toFront();

    }

    private void PicturesOut() {
        //Sida 1 (rabbatsida1)
        RabbatView2.setImage(model.getImage(model.getProduct(2)));
        RabbatView3.setImage(model.getImage(model.getProduct(3)));
        RabbatView4.setImage(model.getImage(model.getProduct(4)));
        RabbatView5.setImage(model.getImage(model.getProduct(5)));
        RabbatView6.setImage(model.getImage(model.getProduct(6)));
        RabbatView7.setImage(model.getImage(model.getProduct(7)));
        RabbatView8.setImage(model.getImage(model.getProduct(8)));
        RabbatView9.setImage(model.getImage(model.getProduct(9)));
        RabbatView10.setImage(model.getImage(model.getProduct(10)));
        RabbatView11.setImage(model.getImage(model.getProduct(11)));
        //sida 2:(rabbatsida2)
        //12 ska vara imat
        RabbatView13.setImage(model.getImage(model.getProduct(13)));
        RabbatView14.setImage(model.getImage(model.getProduct(14)));
        RabbatView15.setImage(model.getImage(model.getProduct(15)));
        RabbatView16.setImage(model.getImage(model.getProduct(16)));
        RabbatView17.setImage(model.getImage(model.getProduct(17)));
        RabbatView18.setImage(model.getImage(model.getProduct(18)));
        RabbatView19.setImage(model.getImage(model.getProduct(19)));
        RabbatView20.setImage(model.getImage(model.getProduct(20)));
        RabbatView21.setImage(model.getImage(model.getProduct(21)));
    }

    private void TextOut(){

        Text2.setText("24kr/kg");
        Text3.setText("18kr/förp");
        Text4.setText("12kr/förp");
        Text5.setText("15kr/förp");
        Text6.setText("20kr/förp");
        Text7.setText("24kr/förp");
        Text8.setText("29kr/st");
        Text9.setText("28kr/st");
        Text10.setText("20kr/förp");
        Text11.setText("24kr/förp");

//sida2
        //1 och 12 imat
        Text13.setText("20kr/förp");
        Text14.setText("23kr/st");
        Text15.setText("49kr/kg");
        Text16.setText("52kr/kg");
        Text17.setText("75kr/kg");
        Text18.setText("64kr/kg");
        Text19.setText("40kr/kg");
        Text20.setText("69kr/kg");
        Text21.setText("25kr/kg");
    }

    @FXML
    void clickNasta(MouseEvent event){
        if(this.currentPage == 1){
            this.AnchorSide2.toFront();
            currentPage = 2;}
        else if(currentPage == 2){
            this.AnchorSide1.toFront();
            currentPage = 1;
        }
        RabbatNasta.toFront();
        RabbatFaregaende.toFront();
        RabbatFortsattHandla.toFront();
    }
    @FXML
    void clickFaregaende(MouseEvent event){
        if(currentPage == 1){
            this.AnchorSide2.toFront();
            currentPage = 2;}
        else if(currentPage == 2){
            this.AnchorSide1.toFront();
            currentPage = 1;
        }
        RabbatNasta.toFront();
        RabbatFaregaende.toFront();
        RabbatFortsattHandla.toFront();
        //RabbatNasta.getStyleClass().add("gray"); // .gray
    }
    @FXML
    void clickFortsatthandla(MouseEvent event){
        mainController.backHome();
    }


    /*
    @FXML public void onClick2(MouseEvent event){mainController.initPopUp(2);}
    @FXML public void onClick3(MouseEvent event){mainController.initPopUp(3);}
    @FXML public void onClick4(MouseEvent event){mainController.initPopUp(4);}
    @FXML public void onClick5(MouseEvent event){mainController.initPopUp(5);}
    @FXML public void onClick6(MouseEvent event){mainController.initPopUp(6);}
    @FXML public void onClick7(MouseEvent event){mainController.initPopUp(7);}
    @FXML public void onClick8(MouseEvent event){mainController.initPopUp(8);}
    @FXML public void onClick9(MouseEvent event){mainController.initPopUp(9);}
    @FXML public void onClick10(MouseEvent event){mainController.initPopUp(10);}
    @FXML public void onClick11(MouseEvent event){mainController.initPopUp(11);}
    //12=imat logga
    @FXML public void onClick13(MouseEvent event){mainController.initPopUp(13);}
    @FXML public void onClick14(MouseEvent event){mainController.initPopUp(14);}
    @FXML public void onClick15(MouseEvent event){mainController.initPopUp(15);}
    @FXML public void onClick16(MouseEvent event){mainController.initPopUp(16);}
    @FXML public void onClick17(MouseEvent event){mainController.initPopUp(17);}
    @FXML public void onClick18(MouseEvent event){mainController.initPopUp(18);}
    @FXML public void onClick19(MouseEvent event){mainController.initPopUp(19);}
    @FXML public void onClick20(MouseEvent event){mainController.initPopUp(20);}
    @FXML public void onClick21(MouseEvent event){mainController.initPopUp(21);}
    */


    @FXML public void onClick2(MouseEvent event){mainController.initPopUp(2, new Label());}
    @FXML public void onClick3(MouseEvent event){mainController.initPopUp(3, new Label());}
    @FXML public void onClick4(MouseEvent event){mainController.initPopUp(4, new Label());}
    @FXML public void onClick5(MouseEvent event){mainController.initPopUp(5, new Label());}
    @FXML public void onClick6(MouseEvent event){mainController.initPopUp(6, new Label());}
    @FXML public void onClick7(MouseEvent event){mainController.initPopUp(7, new Label());}
    @FXML public void onClick8(MouseEvent event){mainController.initPopUp(8, new Label());}
    @FXML public void onClick9(MouseEvent event){mainController.initPopUp(9, new Label());}
    @FXML public void onClick10(MouseEvent event){mainController.initPopUp(10, new Label());}
    @FXML public void onClick11(MouseEvent event){mainController.initPopUp(11, new Label());}
    //12=imat logga
    @FXML public void onClick13(MouseEvent event){mainController.initPopUp(13, new Label());}
    @FXML public void onClick14(MouseEvent event){mainController.initPopUp(14, new Label());}
    @FXML public void onClick15(MouseEvent event){mainController.initPopUp(15, new Label());}
    @FXML public void onClick16(MouseEvent event){mainController.initPopUp(16, new Label());}
    @FXML public void onClick17(MouseEvent event){mainController.initPopUp(17, new Label());}
    @FXML public void onClick18(MouseEvent event){mainController.initPopUp(18, new Label());}
    @FXML public void onClick19(MouseEvent event){mainController.initPopUp(19, new Label());}
    @FXML public void onClick20(MouseEvent event){mainController.initPopUp(20, new Label());}
    @FXML public void onClick21(MouseEvent event){mainController.initPopUp(21, new Label());}

}