import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class main{

    //This method runs the ticket service program
    public static void main(String[] args) throws FileNotFoundException{

        boolean done = false;
        boolean loginFlag = false;
        //empty userAccount object to be filled after a successful login
        userAccount user = new userAccount(null, null, 0);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tix Event Ticketing Service\nPlease Login:");
        
        while(!(done))
        {
            Scanner commandLine = new Scanner(scanner.nextLine());
            String input = commandLine.next();
            
            //Ending the session
            if (input.equals("Q"))
            {
                done = true;
                commandLine.close();
                scanner.close();
                return;
            }
            //Checking if user is already logged in
            if (loginFlag == false)
            {
                //Checking if username is a valid login
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
                        //If the username is valid then we fill the userAccount object with the right credentials
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
                //No transaction is accepted before a login
                else
                {
                    System.out.println("You must be logged in before using this application");
                }
            }
            else
            {
                //No double logins
                if (input.equals("login"))
                {
                    System.out.println("You are already logged in ");
                }
                //Logging out
                else if (input.equals("logout"))
                {
                    System.out.println("You have successfully logged out");
                    loginFlag = false;
                }
                //Creating a new user
                else if (input.equals("create"))
                {
                    if(!(commandLine.hasNextLine()))
					{
					    continue;
					}
                    String newUser = commandLine.next();
                    if(!(commandLine.hasNextLine()))
					{
					    continue;
					}
                    String newUserType = commandLine.next();
                    if(!(commandLine.hasNextDouble()))
					{
					    continue;
					}
                    double newCredits = commandLine.nextDouble();
                    //Checking if the user logged in is an admin
                    if (user.userType.equals("AA"))
                    {
                        user.createUser(newUser, newUserType, newCredits);
                        System.out.println("You have successfully created an new user");
                    }
                    else
                    {
                        System.out.println("You are unauthorized to use this transaction");
                    }
                }
                //Put tickets up for sale
                else if (input.equals("sell"))
                {
                    if(!(commandLine.hasNextLine()))
					{
					    continue;
					}
                    String eventTitle = commandLine.next();
                    if(!(commandLine.hasNextDouble()))
					{
					    continue;
					}
                    double ticketPrice = commandLine.nextDouble();
                    if(!(commandLine.hasNextInt()))
					{
					    continue;
					}
                    int numberOfTickets = commandLine.nextInt();
                    //Checking if user is logged in as standard-buy
                    if (user.userType.equals("BS"))
                    {
                        System.out.println("You are unauthorized to use this transaction");
                    }
                    else
                    {
                        user.sellTicket(user.username, eventTitle, ticketPrice, numberOfTickets);
                    }
                }
            }

        }

    }
}