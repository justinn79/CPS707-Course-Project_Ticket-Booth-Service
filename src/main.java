import java.util.Scanner;
import java.io.FileNotFoundException;

public class main{

    //This method runs the ticket service program
    public static void main(String[] args) throws FileNotFoundException{

        boolean done = false;
        boolean loginFlag = false;
        //Empty userAccount object to be filled after a successful login
        userAccount user = new userAccount(null, null, 0);
        //transactions object
        transactions transaction = new transactions();
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
                //Putting tickets up for sale
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
                        transaction.sellTicket(user.username, eventTitle, ticketPrice, numberOfTickets);
                    }
                }
                //Buying a ticket
                else if (input.equals("buy"))
                {
                    if(!(commandLine.hasNextLine()))
					{
					    continue;
					}
                    String eventTitle = commandLine.next();
                    if(!(commandLine.hasNextInt()))
					{
					    continue;
					}
                    int numberOfTickets = commandLine.nextInt();
                    if(!(commandLine.hasNextLine()))
					{
					    continue;
					}
                    String sellerUsername = commandLine.next();
                    //Checking if user is logged in as standard-sell
                    if (user.userType.equals("SS"))
                    {
                        System.out.println("You are unauthorized to use this transaction");
                    }
                    //Checking if user is logged in as admin
                    else if (user.userType.equals("AA"))
                    {
                        transaction.buyTicket(eventTitle, numberOfTickets, sellerUsername, user.availableCredit, user.username);
                        //Updating the user's credentials
                        String userCredentials = user.getUserCredentials(user.username);
                        String[] credentialsParts = userCredentials.split("-");
                        String userType = credentialsParts[0];
                        double availableCredit = Double.parseDouble(credentialsParts[1]);
                        user.username = user.username;
                        user.userType = userType;
                        user.availableCredit = availableCredit;
                    }
                    //For BS and FS users
                    else
                    {
                        //making sure the user only buys a maximum of 4 tickets
                        if (numberOfTickets > 4)
                        {
                            System.out.println("You can only buy a maximum of 4 tickets");
                        }
                        else
                        {
                            transaction.buyTicket(eventTitle, numberOfTickets, sellerUsername, user.availableCredit, user.username);
                            //Updating the user's credentials
                            String userCredentials = user.getUserCredentials(user.username);
                            String[] credentialsParts = userCredentials.split("-");
                            String userType = credentialsParts[0];
                            double availableCredit = Double.parseDouble(credentialsParts[1]);
                            user.username = user.username;
                            user.userType = userType;
                            user.availableCredit = availableCredit;
                        }
                    }
                }
                //Delete a user
                else if (input.equals("delete"))
                {
                    if(!(commandLine.hasNextLine()))
					{
					    continue;
					}
                    String deleteUser = commandLine.next();
                    //Checking if current user is logged in as admin
                    if (user.userType.equals("AA"))
                    {
                        //Can't delete current user
                        if (user.username.equals(deleteUser))
                        {
                            System.out.println("Can not delete the current user logged in");
                        }
                        else
                        {
                            user.deleteUser(deleteUser);
                            System.out.println("User: " + deleteUser + " successfully deleted");
                        }
                    }
                    else
                    {
                        System.out.println("You are unauthorized to use this transaction"); 
                    }
                }
                //Refunds a buyer
                else if (input.equals("refund"))
                {
                    if(!(commandLine.hasNextLine()))
					{
					    continue;
					}
                    String buyer = commandLine.next();
                    if(!(commandLine.hasNextLine()))
					{
					    continue;
					}
                    String seller = commandLine.next();
                    if(!(commandLine.hasNextDouble()))
					{
					    continue;
					}
                    double creditAmount = commandLine.nextDouble();
                    //Checking if current user is logged in as admin
                    if (user.userType.equals("AA"))
                    {
                        //Checking that buyer and seller are actual users
                        if (user.login(buyer) == false)
                        {
                            System.out.println("The buyer's account does not exist");
                        }
                        else if (user.login(seller) == false)
                        {
                            System.out.println("The seller's account does not exist");
                        }
                        else
                        {
                            transaction.refund(buyer, seller, creditAmount);
                            System.out.println("Refund was successful");
                        }
                    }
                    else
                    {
                        System.out.println("You are unauthorized to use this transaction");
                    }
                }
                //Adds credit to a user
                else if (input.equals("addcredit"))
                {
                    if(!(commandLine.hasNextDouble()))
					{
					    continue;
					}
                    double creditAmount = commandLine.nextDouble();
                    if (creditAmount > 1000)
                    {
                        System.out.println("You can only add a maximum of $1000.00 in credit.");
                        continue;
                    }
                    //For regular add credit
                    if(!(commandLine.hasNext()))
                    {
                        transaction.addCredit(creditAmount, user.username);
                        //Updating the user's credentials
                        String userCredentials = user.getUserCredentials(user.username);
                        String[] credentialsParts = userCredentials.split("-");
                        String userType = credentialsParts[0];
                        double availableCredit = Double.parseDouble(credentialsParts[1]);
                        user.username = user.username;
                        user.userType = userType;
                        user.availableCredit = availableCredit;
                        System.out.println("Credit successfully added to your account");
                    }
                    else if(commandLine.hasNextLine())
                    {
                        String addCreditUser = commandLine.next();
                        //Checking if current user is logged in as admin for admin add credit
                        if (user.userType.equals("AA"))
                        {
                            //Checking if the user exists
                            if (user.login(addCreditUser) == false)
                            {
                                System.out.println("The user: " + addCreditUser + " does not exist");
                            }
                            else if (user.username.equals(addCreditUser))
                            {
                                System.out.println("To add credit to your account, please use the other addcredit command");
                            }
                            else
                            {
                            transaction.addCredit(creditAmount, addCreditUser);
                            System.out.println("Credit successfully added to " + addCreditUser + "'s account");
                            }
                        }
                        else
                        {
                            System.out.println("You are unauthorized to give another user credit");
                        }
                    }
                }
                //handling for non valid commands
				else
				{
					System.out.println("Please enter a valid command (Q to quit): ");
				}
            }

        }

    }
}