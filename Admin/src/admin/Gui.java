package admin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
public class Gui
{
    ManageProducts manager = new ManageProducts();
    
    //Login elements 
    TextField userField = new TextField();
    PasswordField passField = new PasswordField();
    Button logInbutton = new Button("Log in");
    GridPane loginFieldsLayout = new GridPane();
    AnchorPane loginLayout = new AnchorPane();
    Label warningLabel = new Label();
    ImageView userView = new ImageView(new Image(getClass().getResourceAsStream("UserIcon.png")));
    ImageView movingView = new ImageView(new Image(getClass().getResourceAsStream("m.gif")));

    public Gui() 
    {
        
    }
    
    
    //Main scene elements
    AnchorPane mainLayout = new AnchorPane();
    static ListView<String> productList = new ListView<>();
    Image addIcon = new  Image(getClass().getResourceAsStream("addIcon.png"));
    ImageView addView = new ImageView(addIcon);
    Button saveButton = new Button("Save");
    GridPane fieldsLayout = new GridPane();
    Scene tempScene = new Scene(loginFieldsLayout);
    
    
    public Gui(Stage window) throws IOException
    {
            
        //Login Scene
        PathTransition gotransition = new PathTransition();
        gotransition.setPath(new Line(10 , 350 , 650 , 350));
        gotransition.setNode(movingView);
        gotransition.setDuration(new Duration(5000));
        gotransition.play();
        
        userField.setPromptText("Username:");
        passField.setPromptText("Password:");
        warningLabel.setVisible(false);
        GridPane.setConstraints(userField, 0, 0);
        GridPane.setConstraints(passField, 0, 1);
        loginFieldsLayout.setVgap(15);
        loginFieldsLayout.getChildren().addAll(userField, passField);
        logInbutton.setPrefWidth(100);
        AnchorPane.setRightAnchor(logInbutton, 250d);
        AnchorPane.setBottomAnchor(logInbutton, 100d);
        AnchorPane.setRightAnchor(loginFieldsLayout, 210d);
        AnchorPane.setTopAnchor(loginFieldsLayout, 200d);
        AnchorPane.setBottomAnchor(warningLabel, 110d);
        AnchorPane.setTopAnchor(userView, 20d);
        AnchorPane.setRightAnchor(userView, 215d);
        
        userView.setPreserveRatio(true);
        userView.setFitHeight(175);
        movingView.setPreserveRatio(true);
        movingView.setFitWidth(100);
        
        loginLayout.getChildren().addAll(loginFieldsLayout, logInbutton, warningLabel, userView , movingView);
        
        logInbutton.setOnAction( e -> signIn(window));
        passField.setOnKeyPressed(e -> 
        {
            if(e.getCode() == KeyCode.ENTER)
                signIn(window);
        });
        
        //Add Scene
        RotateTransition rotate = new RotateTransition();
        AnchorPane.setRightAnchor(addView , 326d);
        AnchorPane.setBottomAnchor(addView, 57d);
        addView.setPreserveRatio(true);
        addView.setFitHeight(50);
        addView.setOnMouseEntered(e-> 
        {
            rotate.setNode(addView);
            rotate.setFromAngle(0);
            rotate.setToAngle(90);
            rotate.setDuration(new Duration(200));
            rotate.play();
        });
        addView.setOnMouseExited(e-> 
        {
            rotate.setNode(addView);
            rotate.setFromAngle(90);
            rotate.setToAngle(0);
            rotate.setDuration(new Duration(200));
            rotate.play();
        });
        
        addView.setOnMouseClicked(e-> 
        {
            Product product = new Product();
            Label warningLabel = new Label();
            warningLabel.setVisible(false);
            
            FadeTransition fade = new FadeTransition();
            fade.setNode(warningLabel);
            fade.setDuration(new Duration(700));
            fade.setAutoReverse(true);
            fade.setCycleCount(4);
            fade.setFromValue(0);
            fade.setToValue(1);
            
            TextField nameField = new TextField();
            TextField categoryField = new TextField();
            TextField priceField = new TextField();
            TextField countField = new TextField();
            TextField guaranteeField = new TextField();
            
            GridPane addFieldsLayout = new GridPane();
            Button submitButton = new Button("Submit");
            AnchorPane addLayout = new AnchorPane();
            
            addFieldsLayout.setVgap(15);
            GridPane.setConstraints(nameField, 0, 0);
            GridPane.setConstraints(categoryField, 0, 1);
            GridPane.setConstraints(priceField, 0,2);
            GridPane.setConstraints(countField, 0, 3);
            GridPane.setConstraints(guaranteeField, 0, 4);
            addFieldsLayout.getChildren().addAll(nameField ,categoryField, priceField, countField, guaranteeField);
            Scene addScene = new Scene(addLayout,400 , 350);
            addScene.getStylesheets().add("CSS.css");
            window.setScene(addScene);
            
            nameField.setText("");
            categoryField.setText("");
            priceField.setText("");
            countField.setText("");
            
            nameField.setPromptText("Name:");
            categoryField.setPromptText("Category:");
            priceField.setPromptText("Price:");
            countField.setPromptText("Count:");
            guaranteeField.setPromptText("Guarantee:");
            countField.setEditable(true);
            
            AnchorPane.setTopAnchor(addFieldsLayout, 20d);
            AnchorPane.setRightAnchor(addFieldsLayout, 110d);
            AnchorPane.setLeftAnchor(addFieldsLayout, 110d);
            
            Button backButton = new Button("Back");
            backButton.setOnAction(e2 -> window.setScene(tempScene));
            
            HBox buttons = new HBox(10);
            buttons.getChildren().addAll(backButton , submitButton);
            addLayout.getChildren().addAll(buttons, addFieldsLayout);
            AnchorPane.setBottomAnchor(buttons, 25d);
            AnchorPane.setLeftAnchor(buttons,130d);
            GridPane.setConstraints(warningLabel, 0, 5);
            warningLabel.setPadding(new Insets(0, 0, 0, 10));
            
            addFieldsLayout.getChildren().addAll(warningLabel);
           
            submitButton.setOnAction(e2 ->
            {
                 
                //Checking whether inputs are valid or not
                if("".equals(nameField.getText()) || "".equals(categoryField.getText()) || "".equals(priceField.getText()) || "".equals(countField.getText()) || "".equals(guaranteeField.getText()))
                {  
                    warningLabel.setVisible(true);
                    warningLabel.setText("Please fill all the blanks!!!");
                    fade.play();
                }
                else
                {
                    try 
                    {
                        manager.addNewProduct(nameField.getText(), Double.parseDouble(priceField.getText()), Integer.parseInt(countField.getText()), categoryField.getText(), guaranteeField.getText());
                        productList.getItems().addAll(nameField.getText());
                        mainLayout.getChildren().clear();
                        mainLayout.getChildren().addAll(addFieldsLayout, productList, addView, saveButton);
                        addFieldsLayout.setVisible(false);
                        saveButton.setVisible(false);
                        window.setScene(tempScene);
                    } catch (NumberFormatException e3) 
                    {
                        warningLabel.setVisible(true);
                        warningLabel.setText("Oops! invalid inputs...");
                        warningLabel.setPadding(new Insets(0, 0, 0, 20));
                        fade.play();
                    }                 
                     try {
                        EchoThread.sendUpdatedList();
                    } catch (IOException ex) {
                        Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        });
     
        //Main scene
        FadeTransition fade = new FadeTransition();
        productList.getSelectionModel().selectedItemProperty().addListener((v,oldValue ,newValue) -> 
        {
            TextField nameField = new TextField();
            TextField categoryField = new TextField();
            TextField priceField = new TextField();
            TextField countField = new TextField();
            TextField guaranteefField = new TextField();
            fieldsLayout.setVisible(false);
            saveButton.setVisible(false);
            
            GridPane.setConstraints(nameField, 0, 0);
            GridPane.setConstraints(categoryField, 0, 1);
            GridPane.setConstraints(priceField, 0, 2);
            GridPane.setConstraints(countField, 0, 3);
            GridPane.setConstraints(guaranteefField, 0, 4);
            fieldsLayout.getChildren().addAll(nameField, categoryField, priceField, countField, guaranteefField);
            fieldsLayout.setVgap(10);
            if(newValue != null) //when user clicks on an item for the first time
            { 
                fieldsLayout.setVisible(true);
                saveButton.setVisible(true);
                if(oldValue == null)
                {
                    fade.setNode(fieldsLayout);
                    fade.setFromValue(0);
                    fade.setToValue(1);
                    fade.setDuration(new Duration(200));
                    fade.play();

                    fade.setNode(saveButton);
                    fade.play();
                }
                
                nameField.setText(ManageProducts.findProductByName(newValue).getName());
                categoryField.setText(ManageProducts.findProductByName(newValue).getCategory());
                priceField.setText(Double.toString(ManageProducts.findProductByName(newValue).getPrice()));
                countField.setText(Integer.toString(ManageProducts.findProductByName(newValue).getCount()));
                guaranteefField.setText(ManageProducts.findProductByName(newValue).getGuarantee());
            }
            AnchorPane.setRightAnchor(fieldsLayout, 80d);
            AnchorPane.setTopAnchor(fieldsLayout, 130d);
            AnchorPane.setRightAnchor(saveButton, 150d);
            AnchorPane.setTopAnchor(saveButton, 340d);
            
            mainLayout.getChildren().clear();
            mainLayout.getChildren().addAll(fieldsLayout, productList, addView, saveButton);
            countField.setEditable(false);
            saveButton.setOnAction(e-> 
            {
                Product product = ManageProducts.findProductByName(newValue);
                try{
                manager.editCategory(product, categoryField.getText());
                manager.editPrice(product, Double.parseDouble(priceField.getText()));
                manager.editGuarantee(product, guaranteefField.getText());
                manager.editName(product, nameField.getText());}
                catch (NumberFormatException e3) {
                    System.err.println("err");
                }
                if("".equals(nameField.getText()))
                    manager.minusProduct(product);
                try
                {
                    EchoThread.sendUpdatedList();
                }
                catch(IOException e2) 
                {
                    System.out.println("ERROR");
                }
                
                
                //Refreshing the listView items
                productList.getItems().clear();
                for(int i =0 ; i< ManageProducts.productsList.size() ; i++)
                    productList.getItems().add(ManageProducts.productsList.get(i).getName());
            });
        });   
    
    
    }
    
    public void signIn(Stage window)
    {
        AnchorPane.setBottomAnchor(warningLabel, 150d);

        ScaleTransition scale = new ScaleTransition();
        scale.setNode(logInbutton);
        scale.setDuration(new Duration(500));
        scale.setFromX(1);
        scale.setToX(.9);
        scale.setFromY(1);
        scale.setToY(.9);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);

        FadeTransition fade = new FadeTransition();
        fade.setNode(warningLabel);
        fade.setDuration(new Duration(1200));
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setAutoReverse(true);
        fade.setCycleCount(2);

        if("".equals(userField.getText())||"".equals(passField.getText()))
        {
            warningLabel.setText("Please fill out both blanks!!!");
            AnchorPane.setRightAnchor(warningLabel, 210d);
            scale.play();
            fade.play();
        }
        else
        {
            warningLabel.setText("Ooops! Something went wrong :/");
            AnchorPane.setRightAnchor(warningLabel, 190d);
            scale.play();
            fade.play();
        }
        warningLabel.setVisible(true);

        boolean temp = manager.logIn(userField.getText(), passField.getText());
        if(temp != false) //if username and password are true
        {
            AnchorPane.setTopAnchor(productList, 20d);
            AnchorPane.setRightAnchor(productList, 350d);
            AnchorPane.setLeftAnchor(productList, 50d);
            try {
                manager.inIt();
            } catch (Exception ex) {
                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
            }
            //initializing the listView
            for(int i = 0; i < ManageProducts.productsList.size(); i++)
                productList.getItems().addAll(ManageProducts.productsList.get(i).getName());
            mainLayout.getChildren().addAll(productList ,addView);
            Scene mainScene = new Scene(mainLayout, 800, 500);
            mainScene.getStylesheets().add("CSS.css");
            tempScene = mainScene;

            window.setTitle("MoBeKala - StoreRoom");
            window.setScene(mainScene);
        }
    }
}
