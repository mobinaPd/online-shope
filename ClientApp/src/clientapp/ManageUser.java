package clientapp;

public class ManageUser 
{
    ManageUser[] array = new ManageUser[100];
    final int userCount = 3 ;
    private String user, pass;
    private double charge;
    private String bankId , cvv , cartPass2 ;
    

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }
    public static ManageUser c ;
    public  ManageUser logIn(String user , String pass)
    {
        int i;
        for (i =0 ;i< userCount ; i++)
        {
            if(user.equals(array[i].user) && pass.equals(array[i].pass))
            {
                c = array[i];
                return c;
            }
        }
        return null;
    }
    
    public ManageUser bankData(String bankId , String cvv , String cartPass2)
    {
        for(int i = 0 ; i< userCount ; i++)
        {
            if(bankId.equals(array[i].bankId) && cvv.equals(array[i].cvv) && cartPass2.equals(array[i].cartPass2))
            {
                 c = array[i];
                return c;
            }
        }
        return null;
    }
    
     public void inIt()
    {
        array[0]=new ManageUser();
        array[0].user="d";
        array[0].pass="1234";
        array[0].charge = 10000;
        array[0].bankId = "1234123412341235";
        array[0].cvv = "1231";
        array[0].cartPass2 = "1231";
        
        array[1]=new ManageUser();
        array[1].user="e";
        array[1].pass="1234";
        array[1].charge = 2000;
        array[1].bankId = "1234567899";
        array[1].cvv = "1232";
        array[1].cartPass2 = "1232";

        array[2]=new ManageUser();
        array[2].user="f";
        array[2].pass="1234";
        array[2].charge = 5000;
        array[2].bankId = "1234567898";
        array[2].cvv = "1233";
        array[2].cartPass2 = "1233";
    }
}
