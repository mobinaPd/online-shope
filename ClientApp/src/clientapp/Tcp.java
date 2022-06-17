package clientapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tcp 
{
   public static List<Product> productsList = new LinkedList<>();
   
    private String [] total = new String[5];
    public Socket clientHandler () throws UnknownHostException
    {
        InetAddress ip = InetAddress.getLocalHost();
        final int port = 2000 ;
        try
        {
            Socket mySocket = new Socket(ip , port);
            System.out.println("Connected!");
            return mySocket;
        }
        catch(IOException e)
        {
            System.out.println("Didnt connect!");
            return null ;
        }
        
    }
    public void clientListening(Socket mySocket) throws IOException
    {
       DataInputStream inPut;
       try 
       {
            inPut = new DataInputStream(mySocket.getInputStream());
            while(true)
            {
                String test = inPut.readUTF(); 
                if(test.equals("CLEAR"))
                    productsList.clear();
                else 
                {
                    Product product = new Product();

                    total = test.split("//");
                    product.setName(total[0]);
                    product.setCategory(total[1]);
                    product.setPrice(Double.parseDouble(total[2]));
                    product.setCount(Integer.parseInt(total[3]));
                    product.setGuarantee(total[4]);
                    if(product.getCount() != 0)
                        productsList.add(product);
                }
            }
       }
       catch (IOException ex)
       {
           Logger.getLogger(Tcp.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    public void clientSendsMassage(Socket mySocket, String productName, int count) throws IOException
    {
        DataOutputStream outPut = new DataOutputStream(mySocket.getOutputStream());
        
        outPut.writeUTF(productName + "//" + count);
        
    }
}