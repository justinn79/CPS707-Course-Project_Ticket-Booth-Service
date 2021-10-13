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
    public static void sellTicket(String sellerUsername, String eventTitle, double ticketPrice, int numberOfTickets) throws FileNotFoundException{
        
        
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

        int userLength = sellerUsername.length();
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
            out.print(sellTransactionCode + " " + eventTitle + whiteSpace.repeat(numberOfWhiteSpacesEvent) + " " +  sellerUsername + whiteSpace.repeat(numberOfWhiteSpacesUser) + " " + zero.repeat(numberOfZerosTicketAmount) + numberOfTickets + " " + zero.repeat(numberOfZerosTicketPrice-1) + ticketPrice + "0" + "\n");
        }
        catch(IOException e)
        {
            System.out.println("Error");
        }

        try(FileWriter myWriter = new FileWriter("txtfiles/availableTicketsFile.txt", true);
            BufferedWriter bw = new BufferedWriter(myWriter);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print(eventTitle + whiteSpace.repeat(numberOfWhiteSpacesEvent) + " " +  sellerUsername + whiteSpace.repeat(numberOfWhiteSpacesUser) + " " + zero.repeat(numberOfZerosTicketAmount) + numberOfTickets + " " + zero.repeat(numberOfZerosTicketPrice-1) + ticketPrice + "0" + "\n");
        }
        catch(IOException e)
        {
            System.out.println("Error");
        }
    }

    public static void buyTicket(String eventTitle, int numberOfTickets, String sellerUsername, double availableCredit, String username) throws FileNotFoundException
    {
        boolean found = false;
        double userAvailableCredit;
        String userType = "";
        String userTemp = "";
        String eventTemp = "";
        String totalTicketsTemp = "";
        String ticketCostTemp = "";

        Scanner scanner = new Scanner(System.in);

        try
        {
            Scanner scan = new Scanner(new File("txtfiles/availableTicketsFile.txt"));
            scan.useDelimiter("\\s+");

            while(scan.hasNext() && !found)
            {
                eventTemp = scan.next();

                if (eventTemp.trim().equals(eventTitle.trim()))
                {
                    userTemp = scan.next();
                    totalTicketsTemp = scan.next();
                    ticketCostTemp = scan.next();

                    if(userTemp.equals(sellerUsername))
                    {
                        if(Integer.parseInt(totalTicketsTemp) < numberOfTickets)
                        {
                            System.out.println("There are an insufficient number of tickets for sale.");
                            found = true;
                            break;
                        }
                        System.out.println("Cost per ticket: " + ticketCostTemp);
                        System.out.println("Number of tickets your purchasing: " + numberOfTickets);
                        double totalCost = Double.parseDouble(ticketCostTemp) * numberOfTickets;
                        System.out.println("Your total is: " + totalCost);

                        System.out.println("Would you like to make this purchase? (Y/N)");
                        Scanner commandLine = new Scanner(scanner.nextLine());
                        String input = commandLine.next();

                        if(input.equals("Y"))
                        {
                            if(availableCredit < totalCost)
                            {
                                System.out.println("You do not have enough credits to make this purchase. You have: $" + availableCredit);
                                found = true;
                                break;
                            }

                            System.out.println("Purchase successful");
                        }

                        if(input.equals("N"))
                        {
                            System.out.println("Purchase cancelled.");
                        }
                        
                        
                        found = true;
                    }
                }
            }
            if(found == false)
            {
                System.out.println("The information provided does not match with any available tickets.");
            }
            scan.close();
        }
        catch(Exception e)
        {
            System.out.println("Error");
        }

        // //Updating the buy information on the daily transaction file.

        int userLength = sellerUsername.length();
        int eventLength = eventTitle.length();
        int ticketLength = String.valueOf(numberOfTickets).length();
        int priceLength = String.valueOf(ticketCostTemp).length();

        int numberOfWhiteSpacesUser = 15 - userLength;
        int numberOfWhiteSpacesEvent = 25 - eventLength;
        int numberOfZerosTicketAmount = 3 - ticketLength;
        int numberOfZerosTicketPrice = 6 - priceLength;

        String whiteSpace = " ";
        String zero = "0";

        String buyTransactionCode = "04";

        try(FileWriter myWriter = new FileWriter("txtfiles/dailyTransactionFile.txt", true);
            BufferedWriter bw = new BufferedWriter(myWriter);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print(buyTransactionCode + " " + eventTitle + whiteSpace.repeat(numberOfWhiteSpacesEvent) + " " +  sellerUsername + whiteSpace.repeat(numberOfWhiteSpacesUser) + " " + zero.repeat(numberOfZerosTicketAmount) + numberOfTickets + " " + zero.repeat(numberOfZerosTicketPrice) + ticketCostTemp + "\n");
        }
        catch(IOException e)
        {
            System.out.println("Error");
        }

        // Deducting the amount sold from the seller's inventory
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

                if (eventTemp.trim().equals(eventTitle.trim()))
                {
                    userTemp = ticketScan.next();

                    if(userTemp.equals(sellerUsername))
                    {
                        int numberOfZerosTicketAmount2 = totalTicketsTemp.length() - ticketLength;
                        totalTicketsTemp = ticketScan.next();
                        String oldLine = (eventTitle + whiteSpace.repeat(numberOfWhiteSpacesEvent) + " " +  sellerUsername + whiteSpace.repeat(numberOfWhiteSpacesUser) + " " + totalTicketsTemp);
                        String newLine = (eventTitle + whiteSpace.repeat(numberOfWhiteSpacesEvent) + " " +  sellerUsername + whiteSpace.repeat(numberOfWhiteSpacesUser) + " " + zero.repeat(numberOfZerosTicketAmount2) + (Integer.parseInt(totalTicketsTemp) - numberOfTickets));
                        fileText = fileText.replaceAll(oldLine, newLine);
                        FileWriter writer = new FileWriter("txtfiles/availableTicketsFile.txt");
                        writer.append(fileText);
                        writer.flush();
                        found2 = true;
                    }
                }
            }
            ticketScan.close();
        }
        catch(Exception e)
        {
            System.out.println("Error");
        }

        // Deducting the amount of user credits after a purchase has been made.

        int usernameLength = username.length();
        int numDigits = String.valueOf(availableCredit).length();

        double totalCost = Double.parseDouble(ticketCostTemp) * numberOfTickets;
        double newCreditValue = availableCredit - totalCost;
        int newCreditValueDigits = String.valueOf(newCreditValue).length();

        int numberOfWhiteSpaces = 15 - usernameLength;
        int numberOfZeros = 9 - numDigits;

        int numberOfZerosForNewValue = 9 - newCreditValueDigits;

        String whiteSpaces = " ";
        String zeros = "0";
        
        try
        {
            Scanner scan1 = new Scanner(new File("txtfiles/currentUsersAccountsFile.txt"));
            StringBuffer buffer = new StringBuffer();
            while (scan1.hasNextLine()) {
                buffer.append(scan1.nextLine()+System.lineSeparator());
            }
            String fileText = buffer.toString();

            boolean found3 = false;

            Scanner userScan = new Scanner(new File("txtfiles/currentUsersAccountsFile.txt"));

            userScan.useDelimiter("\\s+");
            while(userScan.hasNext() && !found3)
            {
                userTemp = userScan.next();
                userType = userScan.next();

                if (userTemp.trim().equals(username.trim()))
                {
                    userAvailableCredit = userScan.nextDouble();
                    String oldLine = (username + whiteSpaces.repeat(numberOfWhiteSpaces) + " " + userType + " " + zeros.repeat(numberOfZeros-1) + availableCredit + "0");
                    String newLine = (username + whiteSpaces.repeat(numberOfWhiteSpaces) + " " + userType + " " + zeros.repeat(numberOfZerosForNewValue-1) + newCreditValue + "0");
                    fileText = fileText.replaceAll(oldLine, newLine);
                    FileWriter writer = new FileWriter("txtfiles/currentUsersAccountsFile.txt");
                    writer.append(fileText);
                    writer.flush();
                    found3 = true;
                    
                }
            }
            userScan.close();
        }
        catch(Exception e)
        {
            System.out.println("Error");
        }

        //username + whiteSpace.repeat(numberOfWhiteSpaces) + " " + userType + " " + zero.repeat(numberOfZeros-1) + availableCredit + "0"

    }

    // String oldLine = (eventTitle + whiteSpace.repeat(numberOfWhiteSpacesEvent) + " " +  sellerUsername + whiteSpace.repeat(numberOfWhiteSpacesUser) + " " + zero.repeat(numberOfZerosTicketAmount) + numberOfTickets + " " + zero.repeat(numberOfZerosTicketPrice) + ticketCostTemp);
    // String newLine = (eventTitle + whiteSpace.repeat(numberOfWhiteSpacesEvent) + " " +  sellerUsername + whiteSpace.repeat(numberOfWhiteSpacesUser) + " " + zero.repeat(numberOfZerosTicketAmount2) + (Integer.parseInt(totalTicketsTemp) - numberOfTickets) + " " + zero.repeat(numberOfZerosTicketPrice) + ticketCostTemp);
    

    public static void main(String[] args) throws FileNotFoundException{
        //createUser("Josh", "SS", 250.00);
        //sellTicket("Ryan", "myEvent", 600, 75);
        buyTicket("myEvent", 10, "Ryan", 10000.00, "Justin");
    }
}