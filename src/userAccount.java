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

    //Login method where the username inputted by the user is scanned in the user accounts file to verify their log in.
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

    public static void deleteUser(String username)
    {
        boolean found = false;
        String userTemp = "";  
        String userType = "";
        double userCredits = 0;      

        int userLength = username.length();
        int numberOfWhiteSpaces = 15 - userLength;

        String whiteSpace = " ";
        String zero = "0";
        
        // Removing the username from the currentUsersAccountFile.
        try
        {
            Scanner scan1 = new Scanner(new File("txtfiles/currentUsersAccountsFile.txt"));
            StringBuffer buffer = new StringBuffer();
            while (scan1.hasNextLine()) {
                buffer.append(scan1.nextLine()+System.lineSeparator());
            }
            String fileText = buffer.toString();

            Scanner userScan = new Scanner(new File("txtfiles/currentUsersAccountsFile.txt"));

            userScan.useDelimiter("\\s+");

            while(userScan.hasNext() && !found)
            {
                userTemp = userScan.next();
                userType = userScan.next();
                userCredits = userScan.nextDouble();

                if (userTemp.trim().equals(username.trim()))
                {
                    int numDigits = String.valueOf(userCredits).length();
                    int numberOfZeros = 9 - numDigits;
                    String oldLine = (username + whiteSpace.repeat(numberOfWhiteSpaces) + " " + userType + " " + zero.repeat(numberOfZeros-1) + userCredits + "0");
                    String newLine = ("");
                    fileText = fileText.replaceAll(oldLine, newLine);
                    FileWriter writer = new FileWriter("txtfiles/currentUsersAccountsFile.txt");
                    writer.append(fileText);
                    writer.flush();
                    found = true;
                }
            }
            userScan.close();

            if(found == false)
            {
                System.out.println("This username does not exist.");
                return;
            }
        }
        catch(Exception e)
        {
            System.out.println("deleteUser Error");
        }

        // Removing any ticket in the availableTicketsFile that is associated with the deleted user.

        String eventTemp = "";
        String numOfTickets = "";
        String ticketPrice = "";

        String whiteSpaces = " ";
        String zeros = "0";

        try
        {
            Scanner scan = new Scanner(new File("txtfiles/availableTicketsFile.txt"));
            StringBuffer buffer = new StringBuffer();
            while (scan.hasNextLine()) {
                buffer.append(scan.nextLine()+System.lineSeparator());
            }
            String fileText = buffer.toString();

            boolean found2 = false;

            Scanner ticketScan = new Scanner(new File("txtfiles/availableTicketsFile.txt"));

            ticketScan.useDelimiter("\\s+");
            while(ticketScan.hasNext() && !found2)
            {
                eventTemp = ticketScan.next();
                userTemp = ticketScan.next();
                numOfTickets = ticketScan.next();
                ticketPrice = ticketScan.next();

                if(userTemp.equals(username))
                {
                    // int userLength = username.length();
                    int eventLength = eventTemp.length();
                    int ticketLength = String.valueOf(numOfTickets).length();
                    int priceLength = String.valueOf(ticketPrice).length();
                    int numberOfWhiteSpacesUser = 15 - userLength;
                    int numberOfWhiteSpacesEvent = 25 - eventLength;
                    // int numberOfZerosTicketAmount = 3 - ticketLength;
                    // int numberOfZerosTicketPrice = 6 - priceLength;
                    String oldLine = (eventTemp + whiteSpaces.repeat(numberOfWhiteSpacesEvent) + " " +  userTemp + whiteSpaces.repeat(numberOfWhiteSpacesUser) + " " + numOfTickets + " " + ticketPrice);
                    String newLine = ("");
                    fileText = fileText.replaceAll(oldLine, newLine);
                    FileWriter writer = new FileWriter("txtfiles/availableTicketsFile.txt");
                    writer.append(fileText);
                    writer.flush();
                    found2 = true;
                }
                //eventTitle + whiteSpace.repeat(numberOfWhiteSpacesEvent) + " " +  sellerUsername + whiteSpace.repeat(numberOfWhiteSpacesUser) + " " + zero.repeat(numberOfZerosTicketAmount) + numberOfTickets + " " + zero.repeat(numberOfZerosTicketPrice-1) + ticketPrice + "0" + "\n"
            }
            ticketScan.close();
        }
        catch(Exception e)
        {
            System.out.println("deleteUser Error");
            
        }
        //Updating the delete information on the daily transaction file.

        String deleteTransactionCode = "02";
        int numDigits = String.valueOf(userCredits).length();
        int numberOfZeros = 9 - numDigits;

        try(FileWriter myWriter = new FileWriter("txtfiles/dailyTransactionFile.txt", true);
        BufferedWriter bw = new BufferedWriter(myWriter);
        PrintWriter out = new PrintWriter(bw))
        {
            out.print(deleteTransactionCode + " " + username + whiteSpace.repeat(numberOfWhiteSpaces) + " " + userType + " " + zero.repeat(numberOfZeros-1) + userCredits + "0" + "\n");
        }
        catch(IOException e)
        {
            System.out.println("Error");
        }
    }
}