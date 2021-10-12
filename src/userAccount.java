import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class userAccount{

    //Username, Usertype, available credits
    public String username, userType;
    public double availableCredit;

    //Constructor for the userAccount class
    public userAccount(String username, String userType, double availableCredit){
        this.username = username;
        this.userType = userType;
        this.availableCredit = availableCredit;
    }

    //Login method where the inputted username by the user is scanned in the user accounts file to verify their log in.
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

    //When the user is logged in, this method will use their username to search through the user accounts file to determine their additional info which is their user type and available credits.
    public static String getUserCredentials(String username) throws FileNotFoundException
    {
        
        boolean found = false;
        String userTemp = "";
        String userType = "";
        String availableCredit = "";      
        
        try
        {
            Scanner scan = new Scanner(new File("txtfiles/currentUsersAccountsFile.txt"));
            scan.useDelimiter("\\s+");

            while(scan.hasNext() && !found)
            {
                userTemp = scan.next();

                if (userTemp.trim().equals(username.trim()))
                {
                    userType = scan.next();
                    availableCredit = scan.next();
                }
            }
            scan.close();
        }
        catch(Exception e)
        {
            System.out.println("Error");
        }
        System.out.println(userType + " " + availableCredit);
        return userType + "-" + availableCredit;
    }


    // This method creates a new account for a user and updates the users account file and daily transaction file. This method also checks and catches bad inputs when the user is trying to make an account.
    public static void createUser(String username, String userType, double availableCredit) throws FileNotFoundException
    {
        boolean found = false;
        String userTemp = "";

        if(username.length() > 15)
        {
            System.out.println("Your username must have 15 characters at most.");
            return;
        }

        if(!userType.equals("AA") && !userType.equals("FS") && !userType.equals("BS") && !userType.equals("SS"))
        {
            System.out.println("The user type does not exist.");
            return;
        }

        if(availableCredit > 999999)
        {
            System.out.println("The maximum number of credits an account can hold is 999,999");
            return;
        }
        
        try
        {
            Scanner scan = new Scanner(new File("txtfiles/currentUsersAccountsFile.txt"));
            scan.useDelimiter("\\s+");

            while(scan.hasNext() && !found)
            {
                userTemp = scan.next();

                if (userTemp.trim().equals(username.trim()))
                {
                    System.out.println("This user already exists.");
                    found = true;
                    scan.close();
                    return;
                }
            }
            scan.close();
        }
        catch(Exception e)
        {
            System.out.println("Error");
        }

        // create a user in the usersaccountfile.

        int userLength = username.length();
        int numDigits = String.valueOf(availableCredit).length();

        int numberOfWhiteSpaces = 15 - userLength;
        int numberOfZeros = 9 - numDigits;

        String whiteSpace = " ";
        String zero = "0";

        String createTransactionCode = "01";

        try(FileWriter myWriter = new FileWriter("txtfiles/currentUsersAccountsFile.txt", true);
            BufferedWriter bw = new BufferedWriter(myWriter);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print("\n" + username + whiteSpace.repeat(numberOfWhiteSpaces) + " " + userType + " " + zero.repeat(numberOfZeros-1) + availableCredit + "0");
        }
        catch(IOException e)
        {
            System.out.println("Error");
        }

        try(FileWriter myWriter = new FileWriter("txtfiles/dailyTransactionFile.txt", true);
            BufferedWriter bw = new BufferedWriter(myWriter);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print(createTransactionCode + " " + username + whiteSpace.repeat(numberOfWhiteSpaces) + " " + userType + " " + zero.repeat(numberOfZeros-1) + availableCredit + "0" + "\n");
        }
        catch(IOException e)
        {
            System.out.println("Error");
        }

    }

    // The sell ticket method where it takes the username, event title, ticket price, and the number of tickets. This information is stored in the daily transaction file and will be listed in the available tickets file.
    public static void sellTicket(String username, String eventTitle, double ticketPrice, int numberOfTickets) throws FileNotFoundException{
        
        
        if(ticketPrice > 999.99)
        {
            System.out.println("The maximum price for a ticket sale is $999.99");
            return;
        }

        if(eventTitle.length() > 25)
        {
            System.out.println("The title of the event must have 25 characters at most.");
            return;
        }

        if(numberOfTickets > 100)
        {
            System.out.println("The maximum number of tickets you could sell is 100.");
            return;
        }

        int userLength = username.length();
        int eventLength = eventTitle.length();
        int ticketLength = String.valueOf(numberOfTickets).length();
        int priceLength = String.valueOf(ticketPrice).length();

        int numberOfWhiteSpacesUser = 15 - userLength;
        int numberOfWhiteSpacesEvent = 25 - eventLength;
        int numberOfZerosTicketAmount = 3 - ticketLength;
        int numberOfZerosTicketPrice = 6 - priceLength;

        String whiteSpace = " ";
        String zero = "0";

        String sellTransactionCode = "03";
        String userTypeCode = "";

        try(FileWriter myWriter = new FileWriter("txtfiles/dailyTransactionFile.txt", true);
            BufferedWriter bw = new BufferedWriter(myWriter);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print(sellTransactionCode + " " + eventTitle + whiteSpace.repeat(numberOfWhiteSpacesEvent) + " " +  username + whiteSpace.repeat(numberOfWhiteSpacesUser) + " " + zero.repeat(numberOfZerosTicketAmount) + numberOfTickets + " " + zero.repeat(numberOfZerosTicketPrice-1) + ticketPrice + "0" + "\n");
        }
        catch(IOException e)
        {
            System.out.println("Error");
        }

        try(FileWriter myWriter = new FileWriter("txtfiles/availableTicketsFile.txt", true);
            BufferedWriter bw = new BufferedWriter(myWriter);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print(eventTitle + whiteSpace.repeat(numberOfWhiteSpacesEvent) + " " +  username + whiteSpace.repeat(numberOfWhiteSpacesUser) + " " + zero.repeat(numberOfZerosTicketAmount) + numberOfTickets + " " + zero.repeat(numberOfZerosTicketPrice-1) + ticketPrice + "0" + "\n");
        }
        catch(IOException e)
        {
            System.out.println("Error");
        }
    }

    // public static void main(String[] args) throws FileNotFoundException{
    //     //createUser("Josh", "SS", 250.00);
    //     sellTicket("Ryan", "myEvent", 600, 75);
    // }
}