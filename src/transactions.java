import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class transactions {
    
    // An empty constructor
    public transactions()
    {
        
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
            System.out.println("You have successfully put your tickets up for sale");
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

    //This method processes tickets bought by the user and is done so by updating the buy information on the daily transactions file, deducting the amount sold from the seller’s inventory, and deducting the amount of user credits after a purchase has been made.
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
                        String newLine = (eventTitle + whiteSpace.repeat(numberOfWhiteSpacesEvent) + " " +  sellerUsername + whiteSpace.repeat(numberOfWhiteSpacesUser) + " " + zero.repeat(numberOfZerosTicketAmount2-1) + (Integer.parseInt(totalTicketsTemp) - numberOfTickets));
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
                userAvailableCredit = userScan.nextDouble();

                if (userTemp.trim().equals(username.trim()))
                {
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

        // Adding the amount of user credits to the seller after a purchase has been made.
        try
        {
            int sellerUsernameLength = sellerUsername.length();

            int numberOfWhiteSpacesSeller = 15 - sellerUsernameLength;

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
                userAvailableCredit = userScan.nextDouble();

                if (userTemp.trim().equals(sellerUsername.trim()))
                {
                    double newSellersCreditValue = userAvailableCredit + totalCost;
                    int newSellersCreditValueDigits = String.valueOf(newSellersCreditValue).length();
                    int numberOfZerosForSellersValue = 9 - newSellersCreditValueDigits;

                    int numDigitsOldSeller = String.valueOf(userAvailableCredit).length();
                    int numberOfZerosOldSeller = 9 - numDigitsOldSeller;

                    String oldLine = (sellerUsername + whiteSpaces.repeat(numberOfWhiteSpacesSeller) + " " + userType + " " + zeros.repeat(numberOfZerosOldSeller-1) + userAvailableCredit + "0");
                    String newLine = (sellerUsername + whiteSpaces.repeat(numberOfWhiteSpacesSeller) + " " + userType + " " + zeros.repeat(numberOfZerosForSellersValue-1) + newSellersCreditValue + "0");
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
    }

    //This method issues a refund to the buyer's account from the seller's account.
    public static void refund(String username, String sellerUsername, double creditAmount)
    {
        // Adding the specified amount of user credits to the buyer from the seller for the refund.

        int usernameLength = username.length();
        int sellerUsernameLength = sellerUsername.length();

        int numberOfWhiteSpaces = 15 - usernameLength;
        int numberOfWhiteSpacesSeller = 15 - sellerUsernameLength;

        String whiteSpaces = " ";
        String zeros = "0";

        String userTemp = "";
        String userType = "";
        double userAvailableCredit;
        
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
                userAvailableCredit = userScan.nextDouble();

                if (userTemp.trim().equals(username.trim()))
                {
                    int numDigits = String.valueOf(userAvailableCredit).length();
                    int numOfZeros = 9 - numDigits;

                    double addedAmount = userAvailableCredit + creditAmount;

                    int numDigitsForNewAmount = String.valueOf(addedAmount).length();
                    int numOfZerosForNewAmount = 9 - numDigitsForNewAmount;

                    String oldLine = (username + whiteSpaces.repeat(numberOfWhiteSpaces) + " " + userType + " " + zeros.repeat(numOfZeros-1) + userAvailableCredit + "0");
                    // System.out.println(oldLine);
                    String newLine = (username + whiteSpaces.repeat(numberOfWhiteSpaces) + " " + userType + " " + zeros.repeat(numOfZerosForNewAmount-1) + addedAmount + "0");
                    // System.out.println(newLine);
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

        // Deducting the specified amount of user credits from the seller for the refund.
        
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
                userAvailableCredit = userScan.nextDouble();

                if (userTemp.trim().equals(sellerUsername.trim()))
                {
                    int numDigits = String.valueOf(userAvailableCredit).length();
                    int numOfZeros = 9 - numDigits;

                    double subtractedAmount = userAvailableCredit - creditAmount;

                    int numDigitsForNewAmount = String.valueOf(subtractedAmount).length();
                    int numOfZerosForNewAmount = 9 - numDigitsForNewAmount;

                    String oldLine = (sellerUsername + whiteSpaces.repeat(numberOfWhiteSpacesSeller) + " " + userType + " " + zeros.repeat(numOfZeros-1) + userAvailableCredit + "0");
                    // System.out.println(oldLine);
                    String newLine = (sellerUsername + whiteSpaces.repeat(numberOfWhiteSpacesSeller) + " " + userType + " " + zeros.repeat(numOfZerosForNewAmount-1) + subtractedAmount + "0");
                    // System.out.println(newLine);
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

        // Saving this information to the daily transaction file.

        String refundTransactionCode = "05";
        int numOfDigitsCreditAmount = String.valueOf(creditAmount).length();
        int numOfZerosForCreditAmount = 9 - numOfDigitsCreditAmount;

        try(FileWriter myWriter = new FileWriter("txtfiles/dailyTransactionFile.txt", true);
            BufferedWriter bw = new BufferedWriter(myWriter);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print(refundTransactionCode + " " + username + whiteSpaces.repeat(numberOfWhiteSpaces) + " " + sellerUsername + whiteSpaces.repeat(numberOfWhiteSpacesSeller) + " " + zeros.repeat(numOfZerosForCreditAmount-1) + creditAmount + "0" + "\n");
        }
        catch(IOException e)
        {
            System.out.println("Error");
        }
    }

    //This addCredit method allows a user to add credits into their account and also allows an admin to add credits to an existing account of their choice.
    public static void addCredit(double creditAmount, String username)
    {
        int usernameLength = username.length();

        int numberOfWhiteSpaces = 15 - usernameLength;

        String whiteSpaces = " ";
        String zeros = "0";

        String userTemp = "";
        String userType = "";
        double userAvailableCredit;

        try
        {
            Scanner scan1 = new Scanner(new File("txtfiles/currentUsersAccountsFile.txt"));
            StringBuffer buffer = new StringBuffer();
            while (scan1.hasNextLine()) {
                buffer.append(scan1.nextLine()+System.lineSeparator());
            }
            String fileText = buffer.toString();

            boolean found = false;

            Scanner userScan = new Scanner(new File("txtfiles/currentUsersAccountsFile.txt"));

            userScan.useDelimiter("\\s+");
            while(userScan.hasNext() && !found)
            {
                userTemp = userScan.next();
                userType = userScan.next();
                userAvailableCredit = userScan.nextDouble();

                if (userTemp.trim().equals(username.trim()))
                {
                    int numDigits = String.valueOf(userAvailableCredit).length();
                    int numOfZeros = 9 - numDigits;

                    double addedAmount = userAvailableCredit + creditAmount;

                    int numDigitsForNewAmount = String.valueOf(addedAmount).length();
                    int numOfZerosForNewAmount = 9 - numDigitsForNewAmount;

                    String oldLine = (username + whiteSpaces.repeat(numberOfWhiteSpaces) + " " + userType + " " + zeros.repeat(numOfZeros-1) + userAvailableCredit + "0");
                    // System.out.println(oldLine);
                    String newLine = (username + whiteSpaces.repeat(numberOfWhiteSpaces) + " " + userType + " " + zeros.repeat(numOfZerosForNewAmount-1) + addedAmount + "0");
                    // System.out.println(newLine);
                    fileText = fileText.replaceAll(oldLine, newLine);
                    FileWriter writer = new FileWriter("txtfiles/currentUsersAccountsFile.txt");
                    writer.append(fileText);
                    writer.flush();
                    found = true;
                    
                }
            }
            userScan.close();
        }
        catch(Exception e)
        {
            System.out.println("Error");
        }

        // Saving this information to the daily transaction file.

        boolean found1 = false;

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

            while(userScan.hasNext() && !found1)
            {
                userTemp = userScan.next();
                userType = userScan.next();
                userAvailableCredit = userScan.nextDouble();

                if (userTemp.trim().equals(username.trim()))
                {
                    String addCreditTransactionCode = "06";
                    int numOfDigitsAvailableCredit = String.valueOf(userAvailableCredit).length();
                    int numOfZerosForAvailableCredit= 9 - numOfDigitsAvailableCredit;

                    try(FileWriter myWriter = new FileWriter("txtfiles/dailyTransactionFile.txt", true);
                        BufferedWriter bw = new BufferedWriter(myWriter);
                        PrintWriter out = new PrintWriter(bw))
                    {
                        out.print(addCreditTransactionCode + " " + username + whiteSpaces.repeat(numberOfWhiteSpaces) + " " + userType + " " + zeros.repeat(numOfZerosForAvailableCredit-1) + userAvailableCredit + "0" + "\n");
                    }
                    catch(IOException e)
                    {
                        System.out.println("Error");
                    }
                    found1 = true;
                }
            }
            userScan.close();
        }
        catch(Exception e)
        {
            System.out.println("addCredit Error");
        }
    }
}
