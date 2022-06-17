package clientapp;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Gui 
{
    public Gui()
    {  
    }
    
    Stage window;
    AnchorPane logInLayout = new AnchorPane();
    GridPane logInFieldsLayout = new GridPane();
    TextField userField = new TextField();
    PasswordField passField = new PasswordField();
    Button logInButton = new Button("Login");
    
    ManageUser user = new ManageUser();
    Product product = new Product();
    Scene mainScene;
    
    ImageView refreshIcon = new ImageView(new Image(getClass().getResourceAsStream("refreshIcon.png")));
    ListView<String> productsList = new ListView<>();
    Label charge = new Label("");
    TextField searchField = new TextField();
    ImageView cartView = new ImageView(new Image(getClass().getResourceAsStream("cartIcon.png")));
    AnchorPane mainLayout = new AnchorPane();
    VBox listSearch = new VBox(15);
    Tcp clientServer = new Tcp();
    ListView<HBox> selectedProducts = new ListView<>();
    Socket socket = null;
    ImageView logoView = new ImageView(new Image(getClass().getResourceAsStream("Logo.png")));
    RotateTransition rotate = new RotateTransition();
    MediaPlayer mediaPlayer ;
    Media media ;
    ImageView productView;
    public Gui(Stage stage)
    {
        Scene logInScene = new Scene(logInLayout,600 , 450);
        window = stage;
        window.setScene(logInScene);
        productView = new ImageView();
        TextField nameField = new TextField();
        TextField categoryField = new TextField();
        TextField priceField = new TextField();
        TextField guaranteeField = new TextField();
        AnchorPane cartLayout = new AnchorPane();
        Scene cartScene = mainAndCartSceneMaker(nameField, categoryField, priceField, guaranteeField, cartLayout);
        
        //Add checkBoxes to "productsList"
        productsList.setCellFactory(CheckBoxListCell.forListView((String item) -> 
        {
            BooleanProperty observable = new SimpleBooleanProperty();
            observable.addListener((obs, wasSelected, isNowSelected) -> 
            {   
                HBox tempHBox;
                Label tempLabel;
                int count;
                if(isNowSelected)
                {
                    //Check whether the selected item has been selected before or not
                    for(int i = 0; i < selectedProducts.getItems().size(); i++)
                    {
                        tempHBox = (HBox) selectedProducts.getItems().get(i).getChildren().get(1);
                        tempLabel = (Label) tempHBox.getChildren().get(1);
                        count = Integer.parseInt(tempLabel.getText());

                        tempHBox = (HBox) selectedProducts.getItems().get(i).getChildren().get(0);
                        tempLabel = (Label)tempHBox.getChildren().get(0);
                        product = Product.findProductByName(tempLabel.getText());
                        if(item.equals(tempLabel.getText()))
                        {
                           selectedProducts.getItems().remove(i);
                           break;
                        } 
                    }
                    ImageView addIcon = new ImageView(new Image(getClass().getResourceAsStream("addIcon.png")));
                    ImageView minusIcon = new ImageView(new Image(getClass().getResourceAsStream("minusIcon.png")));
                    HBox editSection = new HBox(5);
                    HBox nameHolder = new HBox(new Label(item));
                    HBox mainRow = new HBox(320);
                    Label countLabel = new Label();
                    
                    addIcon.setOnMouseEntered(e -> addIcon.setImage(new Image(getClass().getResourceAsStream("addIconHovered.png"))));
                    addIcon.setOnMouseExited(e -> addIcon.setImage(new Image(getClass().getResourceAsStream("addIcon.png"))));
                    minusIcon.setOnMouseEntered(e -> minusIcon.setImage(new Image(getClass().getResourceAsStream("minusIconHovered.png"))));
                    minusIcon.setOnMouseExited(e -> minusIcon.setImage(new Image(getClass().getResourceAsStream("minusIcon.png"))));

                    addIcon.setPreserveRatio(true);
                    minusIcon.setPreserveRatio(true);
                    addIcon.setFitWidth(25);
                    minusIcon.setFitWidth(25);
                    countLabel.setText("1");
                    nameHolder.setPrefWidth(200);
                    editSection.getChildren().addAll(addIcon,countLabel, minusIcon);
                    
                    mainRow.getChildren().addAll(nameHolder,editSection);
                    selectedProducts.getItems().add(mainRow);
                   
                    addIcon.setOnMouseClicked(e -> 
                    {
                       int counter = Integer.parseInt(countLabel.getText());
                       if(counter < 9)
                           counter++;
                       countLabel.setText(Integer.toString(counter));
                    });
                    minusIcon.setOnMouseClicked(e -> 
                    {
                       int counter = Integer.parseInt(countLabel.getText());
                       if(counter > 0)
                            counter--;
                       countLabel.setText(Integer.toString(counter));
                    });
                    
                    rotate.setNode(cartView);
                    rotate.setDuration(new Duration(150));
                    rotate.setFromAngle(0);
                    rotate.setToAngle(15);
                    rotate.setAutoReverse(true);
                    rotate.setCycleCount(2);
                    rotate.play();
                }
                if(!isNowSelected)
                {
                    for(int i = 0; i < selectedProducts.getItems().size(); i++)
                    {
                        tempHBox = (HBox) selectedProducts.getItems().get(i).getChildren().get(1);
                        tempLabel = (Label) tempHBox.getChildren().get(1);
                        count = Integer.parseInt(tempLabel.getText());

                        tempHBox = (HBox) selectedProducts.getItems().get(i).getChildren().get(0);
                        tempLabel = (Label)tempHBox.getChildren().get(0);
                        product = Product.findProductByName(tempLabel.getText());
                        if(item.equals(tempLabel.getText()))
                        {
                           selectedProducts.getItems().remove(i);
                           break;
                        }
                    }
                    rotate.setNode(cartView);
                    rotate.setDuration(new Duration(150));
                    rotate.setFromAngle(0);
                    rotate.setToAngle(-15);
                    rotate.setAutoReverse(true);
                    rotate.setCycleCount(2);
                    rotate.play();
                }
            });
            return observable ;
        }));
        selectedProducts.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> 
        {
            selectedProducts.setOnKeyPressed(e -> 
            {
                if(e.getCode() == KeyCode.DELETE)
                {
                    HBox temp = (HBox)newValue.getChildren().get(0);
                    Label tempLabel = (Label)temp.getChildren().get(0);
                    productsList.getItems().remove(tempLabel.getText());
                    productsList.getItems().add(tempLabel.getText());

                    if(selectedProducts.getItems().size() > 1)
                        selectedProducts.getItems().remove(newValue);
                    else
                        selectedProducts.getItems().clear();
                    
                    media = new Media(new File("delete.wav").toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                }
            });
        });
        
        GridPane.setConstraints(userField, 0, 0);
        GridPane.setConstraints(passField, 0, 1);
        logInFieldsLayout.getChildren().addAll(userField,passField);
        logInButton.setPrefWidth(100d);
        logInFieldsLayout.setVgap(15);
        logInButton.prefWidth(200);
        logInLayout.getChildren().addAll(logInButton, logInFieldsLayout, logoView);
        AnchorPane.setRightAnchor(logInFieldsLayout, logInScene.getWidth()/2 - 85);
        AnchorPane.setTopAnchor(logInFieldsLayout, 200d);
        AnchorPane.setRightAnchor(logInButton, logInScene.getWidth()/2 - 45);
        AnchorPane.setTopAnchor(logInButton, AnchorPane.getTopAnchor(logInFieldsLayout) + 100);

        logInScene.getStylesheets().add("LoginCss.css");
        userField.setPromptText("Username:");
        passField.setPromptText("Password:");
        logoView.setX(logInScene.getWidth()/2);
        logoView.setY(logInScene.getHeight()/2);
        ScaleTransition scale = new ScaleTransition();
        scale.setDuration(new Duration(1000));
        scale.setNode(logoView);
        scale.setToX(.13);
        scale.setToY(.13);
        scale.play();

        
        rotate.setNode(logoView);
        rotate.setDuration(new Duration(1500));
        rotate.setFromAngle(0);
        rotate.setToAngle(720);
        rotate.play();
        
        PathTransition transition = new PathTransition();
        transition.setDuration(new Duration(1600));
        transition.setNode(logoView);
        transition.setPath(new Line(logoView.getX(), logoView.getY(), logoView.getX(), logoView.getY() - 100));
        transition.play();
        passField.setOnKeyPressed(e->
        {
            if(e.getCode()== KeyCode.ENTER)
                signIn(logInScene, cartScene);
        });
        logInButton.setOnAction(e ->  signIn(logInScene, cartScene));

        productsList.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> 
        {
            if(newValue != null)
            {  
                product = Product.findProductByName(newValue);
                nameField.setText(product.getName());
                categoryField.setText(product.getCategory());
                priceField.setText(Double.toString(product.getPrice()));
                guaranteeField.setText(product.getGuarantee());
             
            }
        });

        

        searchField.setOnKeyPressed(e2 -> 
        {
            List<Product> list = new LinkedList<>();
            if(e2.getCode()== KeyCode.ENTER)
            {
                boolean exist = false;
                for(int i =0 ; i < Tcp.productsList.size(); i++)
                {
                    if(searchField.getText().equalsIgnoreCase(Tcp.productsList.get(i).getCategory()))
                    {
                        Product.findProductByCategory(searchField.getText(),list);
                        exist = true;
                        break;
                    }
                }
                if(!exist)
                    searchField.setText("\"" + searchField.getText() + "\" category" + " dose not exist!");
                 else
                {
                    productsList.getItems().clear();
                    for(int i =0 ; i< list.size() ; i++)
                       productsList.getItems().add(list.get(i).getName());
                    searchField.setText("");
                }
            }
        });
    }
    
    public Scene mainAndCartSceneMaker(TextField nameField, TextField categoryField, TextField priceField, TextField guaranteeField, AnchorPane cartLayout)
    {
        VBox nameLayout = new VBox(5);
        VBox categoryLayout = new VBox(5);
        VBox priceLayout = new VBox(5);
        VBox guaranteeLayout = new VBox(5);
        GridPane detailsLayout = new GridPane();
        HBox listDetail = new HBox(15);
        
        Label nameLabel = new Label("Name");
        Label categoryLabel = new Label("Category");
        Label priceLabel = new Label("Price");
        Label guaranteeLabel = new Label("Guarantee");
        
        nameField.setEditable(false);
        categoryField.setEditable(false);
        priceField.setEditable(false);
        guaranteeField.setEditable(false);
        
        nameLayout.getChildren().addAll(nameLabel, nameField);
        categoryLayout.getChildren().addAll(categoryLabel,categoryField);
        priceLayout.getChildren().addAll(priceLabel, priceField);
        guaranteeLayout.getChildren().addAll(guaranteeLabel,guaranteeField);
        
        GridPane.setConstraints(nameLayout, 0, 0);
        GridPane.setConstraints(categoryLayout, 1, 0);
        GridPane.setConstraints(priceLayout, 0, 1);
        GridPane.setConstraints(guaranteeLayout, 1, 1);
        detailsLayout.setVgap(30);
        detailsLayout.setHgap(10);
        detailsLayout.getChildren().addAll(nameLayout, categoryLayout, priceLayout,guaranteeLayout);
        
        listDetail.getChildren().addAll(productsList, detailsLayout);
        listSearch.getChildren().addAll(listDetail ,searchField);
        
        mainLayout.getChildren().addAll(listSearch, cartView, refreshIcon, productView);

        AnchorPane.setTopAnchor(listSearch, 60d);
        AnchorPane.setRightAnchor(listSearch, 120d);
        AnchorPane.setLeftAnchor(listSearch, 100d);
        AnchorPane.setBottomAnchor(listSearch, 100d);
        AnchorPane.setLeftAnchor(cartView,500d);
        AnchorPane.setBottomAnchor(cartView,160d);
        
        refreshIcon.setPreserveRatio(true);
        refreshIcon.setFitWidth(28);
        AnchorPane.setRightAnchor(refreshIcon, 121d);
        AnchorPane.setBottomAnchor(refreshIcon, 102d);
        
        RotateTransition rotate = new RotateTransition();
        
        refreshIcon.setOnMouseEntered(e -> 
        {
            rotate.setNode(refreshIcon);
            rotate.setDuration(new Duration(100));
            rotate.setFromAngle(0);
            rotate.setToAngle(45);
            rotate.play();
        });
        refreshIcon.setOnMouseExited(e -> 
        {
            rotate.setNode(refreshIcon);
            rotate.setFromAngle(45);
            rotate.setToAngle(0);
            rotate.play();
        });
        refreshIcon.setOnMouseClicked(e ->
        {
            rotate.setNode(refreshIcon);
            rotate.setDuration(new Duration(500));
            rotate.setFromAngle(0);
            rotate.setToAngle(360);
            rotate.play();
            productsList.refresh();
            productsList.getItems().clear();
            for(int i = 0 ; i < Tcp.productsList.size() ; i++)
                productsList.getItems().add(Tcp.productsList.get(i).getName());
            searchField.setText("");
        });
        ScaleTransition scale = new ScaleTransition();
        cartView.setPreserveRatio(true);
        cartView.setFitWidth(100);
        cartView.setOnMouseEntered(e->
        {
            scale.setNode(cartView);
            scale.setFromX(1);
            scale.setFromY(1);
            scale.setToX(1.2);
            scale.setToY(1.2);
            scale.setDuration(new Duration(100));
            scale.play();
        });
        cartView.setOnMouseExited(e->
        {
            scale.setNode(cartView);
            scale.setFromX(1.2);
            scale.setFromY(1.2);
            scale.setToX(1);
            scale.setToY(1);
            scale.setDuration(new Duration(100));
            scale.play();
        });
        
/////     Scene buyScene

        HBox buyBack = new HBox(10);
        Button buyButton = new Button("Buy");
        Button backButton = new Button("Back");
        Button addChargeButton = new Button("Add Charge");
        buyBack.getChildren().addAll(backButton, buyButton , addChargeButton);
        VBox listCharge = new VBox(5);
        listCharge.getChildren().addAll(selectedProducts , charge);
        cartLayout.getChildren().addAll(buyBack , listCharge);
        
        AnchorPane.setTopAnchor(listCharge,30d);
        AnchorPane.setBottomAnchor(listCharge,100d);
        AnchorPane.setRightAnchor(listCharge, 40d);
        AnchorPane.setLeftAnchor(listCharge, 40d);
        AnchorPane.setBottomAnchor(buyBack, 30d);

        Scene cartScene = new Scene(cartLayout ,700 , 500);
        AnchorPane.setLeftAnchor(buyBack, cartScene.getWidth()/3);
        
        
        backButton.setPrefWidth(60);
        buyButton.setPrefWidth(60);
        Scene mainScene = new Scene(mainLayout,900, 500);
        
        backButton.setOnAction(e-> window.setScene(mainScene));
        buyButton.setOnAction(e -> 
        {
            double totalExpense = 0;
            boolean canBuy = true;
            Label warningLabel = new Label();
            cartLayout.getChildren().add(warningLabel);
            AnchorPane.setRightAnchor(warningLabel, cartScene.getHeight()/2);
            AnchorPane.setBottomAnchor(warningLabel, 100d);
            warningLabel.setVisible(false);
            Label tempLabel;
            HBox tempHBox;
            int count;
            FadeTransition fade = new FadeTransition();
            fade.setNode(warningLabel);
            fade.setDuration(new Duration(2000));
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setDelay(new Duration(500));
            for(int i = 0 ; i < selectedProducts.getItems().size() ; i ++)
            {
               tempHBox = (HBox) selectedProducts.getItems().get(i).getChildren().get(1);
               tempLabel = (Label) tempHBox.getChildren().get(1);
               count = Integer.parseInt(tempLabel.getText());
               
               tempHBox = (HBox) selectedProducts.getItems().get(i).getChildren().get(0);
               tempLabel = (Label)tempHBox.getChildren().get(0);
               product = Product.findProductByName(tempLabel.getText());
               
               totalExpense += product.getPrice() * count;
               if(totalExpense > ManageUser.c.getCharge())
               {
                   fade.play();
                   warningLabel.setText("Sorry, no enough money!");
                   warningLabel.setVisible(true);
                   canBuy = false ;
                   break ;
               }
               if(product.getCount() < count)
               {
                   fade.play();
                   warningLabel.setText("Sorry " + product.getName() + " is available " +  product.getCount() + "!");
                   warningLabel.setVisible(true);
                   canBuy = false;
                   break;
               }
            }
            if(selectedProducts.getItems().isEmpty())
            {
                fade.play();
                warningLabel.setText("You haven't chosen anything yet :/");
                warningLabel.setVisible(true);
                canBuy = false;
            }
            if (canBuy)
            {
                ManageUser.c.setCharge(ManageUser.c.getCharge() - totalExpense) ;
                charge.setText("Charge: " + Double.toString(ManageUser.c.getCharge()));
                for(int i = 0 ; i < selectedProducts.getItems().size() ; i ++)
                {
                    tempHBox = (HBox) selectedProducts.getItems().get(i).getChildren().get(1);
                    tempLabel = (Label) tempHBox.getChildren().get(1);
                    count = Integer.parseInt(tempLabel.getText());

                    tempHBox = (HBox) selectedProducts.getItems().get(i).getChildren().get(0);
                    tempLabel = (Label)tempHBox.getChildren().get(0);
                    product = Product.findProductByName(tempLabel.getText());
                    try 
                    {
                        clientServer.clientSendsMassage(socket, product.getName(), count);
                    } catch (IOException ex) 
                    {
                        Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                selectedProducts.getItems().clear();
                productsList.getItems().clear();
                for(int i = 0 ; i < Tcp.productsList.size() ; i++)
                    productsList.getItems().add(Tcp.productsList.get(i).getName());
                
                mainLayout.getChildren().add(warningLabel);
                warningLabel.setVisible(true);
                
                warningLabel.setText("Thanks for your purchase!");
                AnchorPane.setBottomAnchor(warningLabel, AnchorPane.getBottomAnchor(listSearch) - 25d);
                AnchorPane.setRightAnchor(warningLabel, mainScene.getWidth()/2 - warningLabel.getText().length()*4);
                
                fade.play();
                window.setScene(mainScene);
            }
        });
        
        ///////////////////transfer scene
        ImageView cardView = new ImageView(new Image(getClass().getResourceAsStream("card.png")));
        TextField bankId1 = new TextField();
        TextField bankId2 = new TextField();
        TextField bankId3 = new TextField();
        TextField bankId4 = new TextField();
        TextField cartPass2 = new TextField();
        TextField cvv = new TextField();
        TextField amountMoney = new TextField();
        
        final int MAX_CHARACHTERS = 4;
        bankId1.setPromptText("XXXX");
        bankId1.setPrefWidth(150);
        bankId1.setOnKeyTyped(e ->
        {
            if(bankId1.getText().length() >= MAX_CHARACHTERS)
                e.consume();
        });
        bankId2.setPromptText("XXXX");
        bankId2.setPrefWidth(150);
        bankId2.setOnKeyTyped(e ->
        {
            if(bankId2.getText().length() >= MAX_CHARACHTERS)
                e.consume();
        });
        bankId3.setPromptText("XXXX");
        bankId3.setPrefWidth(150);
        bankId3.setOnKeyTyped(e ->
        {
            if(bankId3.getText().length() >= MAX_CHARACHTERS)
                e.consume();
        });
        bankId4.setPromptText("XXXX");
        bankId4.setPrefWidth(150);
        bankId4.setOnKeyTyped(e ->
        {
                if(bankId4.getText().length() >= MAX_CHARACHTERS)
                    e.consume();
        });
        cartPass2.setPromptText("Password :");
        amountMoney.setPromptText("Money:");
        cvv.setPromptText("Cvv2 :");
        
        Label warningLabel= new Label();
        //VBox dataLayout = new VBox(10);
        HBox bankIds = new HBox(15);
        
        bankIds.getChildren().addAll(bankId1, bankId2, bankId3, bankId4);
        
        AnchorPane addChargeLayout = new AnchorPane();
        VBox warningButtons = new  VBox(10);
        Button transferMoneyButton = new Button("Transfer money");
        Button backButton2 = new Button("Back");
        HBox buttonsLayout = new HBox(10);
        buttonsLayout.getChildren().addAll(backButton2 , transferMoneyButton);
        //dataLayout.getChildren().addAll(bankIds , cartPass2 , cvv , amountMoney);
        warningButtons.getChildren().addAll(buttonsLayout , warningLabel);
//        warningLabel.setPadding(new Insets(0, 0, 0, 20));
        addChargeLayout.getChildren().addAll(cardView ,bankIds ,warningButtons, cvv, cartPass2, amountMoney);
        AnchorPane.setLeftAnchor(cardView, 10d);
        AnchorPane.setTopAnchor(cardView, 20d);
        AnchorPane.setTopAnchor(bankIds, 145d);
        AnchorPane.setRightAnchor(bankIds, 20d);
        AnchorPane.setLeftAnchor(bankIds, 140d);
        AnchorPane.setTopAnchor(cartPass2, 200d);
        AnchorPane.setRightAnchor(cartPass2, 40d);
        AnchorPane.setLeftAnchor(cartPass2, 330d);
        AnchorPane.setTopAnchor(cvv, 200d);
        AnchorPane.setLeftAnchor(cvv, 140d);
        AnchorPane.setRightAnchor(cvv, 240d);
        AnchorPane.setBottomAnchor(warningButtons, 5d);
        AnchorPane.setRightAnchor(warningButtons, 155d);
        AnchorPane.setBottomAnchor(amountMoney, 110d);
        AnchorPane.setLeftAnchor(amountMoney, 60d);
        Scene addChargeScene = new Scene(addChargeLayout , 520 , 420);
        addChargeScene.getStylesheets().add("addChargeCss.css");
        addChargeButton.setOnAction(e->
        {
            
            window.setScene(addChargeScene);
            
        });
       
        ManageUser manager = new ManageUser();
        transferMoneyButton.setOnAction(e->{
            FadeTransition fade = new FadeTransition();
            fade.setNode(warningLabel);
            fade.setDuration(new Duration(1000));
            fade.setCycleCount(2);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.setAutoReverse(true);
            manager.inIt();
            String bankId = bankId1.getText() + bankId2.getText() + bankId3.getText() + bankId4.getText();
         if (null!= manager.bankData(bankId ,cvv.getText(), cartPass2.getText()))
         {
             warningLabel.setPadding(new Insets(0, 0, 0, 20));
             charge.setText("Charge: " +(ManageUser.c.getCharge()+ Integer.parseInt(amountMoney.getText())));
             warningLabel.setText("Transfered successfully");
             warningLabel.setVisible(true);
             fade.play();
         }
         else
         {
             warningLabel.setPadding(new Insets(0, 0, 0, 50));
             warningLabel.setText("Try again!");
             warningLabel.setVisible(true);
             fade.play();
         }
       
        });
        backButton2.setOnAction(e->{
            window.setScene(cartScene);
        });
        cartView.setOnMouseClicked(e -> window.setScene(cartScene));
                
        mainScene.getStylesheets().add("buyScene.css");
        cartScene.getStylesheets().add("buyScene.css");
        return mainScene;
    }
    
    public void signIn(Scene logInScene, Scene cartScene)
    {
            media = new Media(new File("wrong.wav").toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            user.inIt();
            Label warningLabel = new Label();

            logInLayout.getChildren().add(warningLabel);
            
            PathTransition labelTransition = new PathTransition();
            labelTransition.setDuration(new Duration(500));
            labelTransition.setNode(warningLabel);
            labelTransition.setPath(new Line(logInScene.getWidth()/2, logoView.getY()+250, logInScene.getWidth()/2, logoView.getY()+ 200));

            FadeTransition fade = new FadeTransition();
            fade.setNode(warningLabel);
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setDuration(new Duration(3000));
            
            PathTransition wrongInputTransition = new PathTransition();
            wrongInputTransition.setNode(logoView);
            wrongInputTransition.setDuration(new Duration(100));
            wrongInputTransition.setPath(new Line(logoView.getX() - 5, logoView.getY() - 100, logoView.getX() + 5, logoView.getY() - 100));
            wrongInputTransition.setAutoReverse(true);
            wrongInputTransition.setCycleCount(5);


            if("".equals(userField.getText()) || "".equals(passField.getText()))
            {
                warningLabel.setText("Please fill out both blanks!");
                warningLabel.setVisible(true);
                
                wrongInputTransition.play();
                labelTransition.play();
                mediaPlayer.play();
                fade.play();
            }
            ManageUser temp = user.logIn(userField.getText(), passField.getText());
            if(temp != null)
            {
                media = new Media(new File("logIn.wav").toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                user = temp;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() 
                    {
                        try 
                        {
                            socket = clientServer.clientHandler();
                            clientServer.clientListening(socket);
                        } catch (UnknownHostException ex) 
                        {
                            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                thread.start();
                window.setScene(cartScene);
                
                //initializing the listView
                for(int i = 0 ; i < Tcp.productsList.size(); i++)
                    productsList.getItems().add(Tcp.productsList.get(i).getName());
                window.setTitle("MoBeKala");
            }
            else if (!"".equals(userField.getText()) && !"".equals(passField.getText()))
            {
                warningLabel.setText("Wrong username or password:/");
                wrongInputTransition.play();
                labelTransition.play();
                mediaPlayer.play();
                fade.play();
            }
            charge.setText("Charge: " + user.getCharge() + "$");
    }
}