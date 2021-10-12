import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class userAccount{

    //Username, Usertype, available credits
    private String username;
    private String userType;
    private float availableCredit;

    // public userAccount(String username, String userType, float availableCredit){
    //     this.username = username;
    //     this.userType = userType;
    //     this.availableCredit = availableCredit;
    // }

    public static void login(String username) throws FileNotFoundException{
        
        boolean found = false;
        String userTemp = "";        
        
        try
        {
            Scanner scan = new Scanner(new File("currentUsersAccountsFile.txt"));
            scan.useDelimiter("\\s+");

            while(scan.hasNext() && !found)
            {
                userTemp = scan.next();

                if (userTemp.trim().equals(username.trim()))
                {
                    found = true;
                }
            }
            scan.close();
            if(found==true){
                System.out.println("Login Successful");
            }
            else{
                System.out.println("Invalid Login");
            }
        }
        catch(Exception e)
        {
            System.out.println("Error");
        }
    }
}