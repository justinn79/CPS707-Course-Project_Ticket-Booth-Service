# Ticket Booth Service
### CPS707 Course Project

A ticket booth service program written completely in java. This is a program that runs on the command line.

Commands:\
<pre>
login *username*                                        (Username must exist in the currentUsersAccountsFile.txt in order to successfully log in)\
create *username* *userType* *creditAmount*             (Existing User Types: SS,FS,BS,AA) (AA is admin)\
sell *eventTitle* *ticketPrice* *numberOfTickets*       (Adds a listing to the text file availableTicketsFile.txt and allows users to purchase it)\
buy *eventTitle* *numberOfTickets* *sellerUsername*     (Buys the specified ticket and deducts the amount from the current user that is logged in)\
delete *username*                                       (Admin Command: deletes a user from the currentUsersAccountsFile.txt file)\
refund *buyerUsername* *sellerUsername* *creditAmount*  (Admin Command: Adds specified creditAmount back to the buyer and deducts it from the seller)\
addcredit *creditAmount*                                (Adds specified creditAmount to the current user that is logged in)\
addcredit *creditAmount* *username*                     (Admin Command: Adds specified creditAmount to the specified username)
</pre>

<pre>
txtfiles Info:
availableTicketsFile.txt:       Lists all of the available tickets that users can buy. Users are also able to add more to this list by using the command "sell"
currentUsersAccountsFile.txt:   Lists all of the existing users of the program.
dailyTransactionFile.txt:       Displays all of the actions the user does within one session of using the ticket booth program.
mergedDailyTransactionFile.txt: Stores everything from dailyTransactionFile.txt to this file and keeps it after the program is terminated.
</pre>
