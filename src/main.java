import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class main{

    public static void main(String[] args) throws FileNotFoundException{

        boolean done = false;
        boolean loginFlag = false;
        userAccount user = new userAccount(null, null, 0);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tix Event Ticketing Service\nPlease Login:");
        String currentUser = "";
        
        while(!(done))
        {
            Scanner commandLine = new Scanner(scanner.nextLine());
            String input = commandLine.next();
            
            if (loginFlag == false)
            {
                if (input.equals("login"))
                {
                    if(!(commandLine.hasNextLine()))
					{
					    continue;
					}
                    String username = commandLine.next();
                    if (user.login(username) == true)
                    {
                        System.out.println("Login Successful");
                        String userCredentials = user.getUserCredentials(username);
                        String[] credentialsParts = userCredentials.split("-");
                        String userType = credentialsParts[0];
                        double availableCredit = Double.parseDouble(credentialsParts[1]);
                        user.username = username;
                        user.userType = userType;
                        user.availableCredit = availableCredit;                     
                        loginFlag = true;
                    }
                    else
                    {
                        System.out.println("Invalid Login");
                    }
                }
                else
                {
                    System.out.println("You must be logged in before using this application");
                }
            }
            else
            {

                if (input.equals("login"))
                {
                    System.out.println("You are already logged in ");
                }
                else if (input.equals("logout"))
                {
                    done = true;
                    commandLine.close();
                    scanner.close();
                    return;
                } 
            }

        }

    }
}