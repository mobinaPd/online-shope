package clientapp;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class ClientApp extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception 
    {
        Gui userInterface = new Gui(window);
        window.getIcons().add(new Image(getClass().getResourceAsStream("Logo2.png")));
        window.setTitle("Login");
        window.show();
        window.setResizable(false);
    }
}
