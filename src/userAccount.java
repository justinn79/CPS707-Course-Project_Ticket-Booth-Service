import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class userAccount{

    //Username, Usertype, available credits
    public String username, userType;
    public double availableCredit;

    public userAccount(String username, String userType, double availableCredit){
        this.username = username;
        this.userType = userType;
        this.availableCredit = availableCredit;
    }

    public static boolean login(String username) throws FileNotFoundException
    {
        
        boolean found = false;
        String userTemp = "";        
        
        try
        {
            Scanner scan = new Scanner(new File("txtfiles/currentUsersAccountsFile.txt"));
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
        }
        catch(Exception e)
        {
            System.out.println("Error");
        }
        return found;
    }

    public static String getUserCredentials(String username) throws FileNotFoundException
    {
        
        boolean found = false;
        String userTemp = "";
        String userType = "";
        String availableCredit = "";      
        
        try
        {
            Scanner scanner = new Scanner(new File("txtfiles/currentUsersAccountsFile.txt"));
            scanner.useDelimiter("\\s+");

            while(scanner.hasNext() && !found)
            {
                userTemp = scanner.next();

                if (userTemp.trim().equals(username.trim()))
                {
                    userType = scanner.next();
                    availableCredit = scanner.next();
                }
            }
            scanner.close();
        }
        catch(Exception e)
        {
            System.out.println("Error");
        }
        return userType + "-" + availableCredit;
    }
}