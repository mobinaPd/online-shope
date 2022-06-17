package admin;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Admin extends Application
{
    static List<Socket> socketList = new LinkedList<>();
    
    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception 
    {
//        ManageProducts object = new ManageProducts();        
        Thread thread = new Thread(() -> 
        {
            ServerSocket serverSocket;
            try
            {
                serverSocket = new ServerSocket(2000);
                window.setOnCloseRequest(e -> {
                    try {
                        serverSocket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                while (true)
                {
                    socketList.add(serverSocket.accept());
                    new EchoThread(socketList.get(socketList.size()-1)).start();
                    
                }
            }catch (IOException e)
            {
                System.exit(-1);
            }
        });
        thread.start();
        window.setTitle("Login");
        window.setResizable(false);
        Gui userInterface = new Gui(window);
        Scene mainScene = new Scene(userInterface.loginLayout, 600, 450);
        
        window.setScene(mainScene);
        window.getIcons().add(new Image(getClass().getResourceAsStream("Logo.png")));
        mainScene.getStylesheets().add("CSS.css");
        window.show();
        
    }
}







/*
Today is 12.2.1398 the project is almost done. Mobina and Behazd has spent much time sice Nowrooz and today are honored to present thier common project.
Behzad: I need to say my teammate was and even is very "zedeh Hal" but the project were in process. I "eltemas" her to do some CSS but she was just ressiting. 
I learn 2 very important points from my dear teammate and which are first never quit and another one is don't think so musch and instead say "Be Jahanam!"
Mobina: just my pleasure. very kind boy.
*/
