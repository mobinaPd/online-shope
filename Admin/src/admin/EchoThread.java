package admin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoThread extends Thread {

    private Socket socket;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public static void sendUpdatedList() throws IOException {
        
        DataOutputStream outPut;
        for (int j = 0; j < Admin.socketList.size(); j++) 
        {
            outPut = new DataOutputStream(Admin.socketList.get(j).getOutputStream());
            outPut.writeUTF("CLEAR");
            for (int i = 0; i < ManageProducts.productsList.size(); i++) 
            {
                try {
                    outPut.writeUTF(ManageProducts.productsList.get(i).getName() + "//" + ManageProducts.productsList.get(i).getCategory() + "//" + ManageProducts.productsList.get(i).getPrice() + "//" + ManageProducts.productsList.get(i).getCount() + "//" + ManageProducts.productsList.get(i).getGuarantee());
                } catch (IOException ex) {
                    //Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public void run() {
        DataInputStream inputServer;
        DataOutputStream outPutServer;
        try {
            inputServer = new DataInputStream(socket.getInputStream());
            outPutServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        for (int i = 0; i < ManageProducts.productsList.size(); i++) {
            try {
                outPutServer.writeUTF(ManageProducts.productsList.get(i).getName() + "//" + ManageProducts.productsList.get(i).getCategory() + "//" + ManageProducts.productsList.get(i).getPrice() + "//" + ManageProducts.productsList.get(i).getCount() + "//" + ManageProducts.productsList.get(i).getGuarantee());
            } catch (IOException ex) {
                Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String[] splitedinput;
        Product product;
        while (true) {
            try {
                splitedinput = inputServer.readUTF().split("//");
                product = ManageProducts.findProductByName(splitedinput[0]);
                product.setCount(product.getCount() - Integer.parseInt(splitedinput[1]));
                sendUpdatedList();
            } catch (IOException ex) {
                Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
