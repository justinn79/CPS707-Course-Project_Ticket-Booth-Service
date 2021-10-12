import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;



public class main{

    public static void main(String[] args) throws FileNotFoundException{

        boolean done = false;
        userAccount user = new userAccount();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Username:");
        String currentUser = "";
        
        while(!(done))
        {
            Scanner commandLine = new Scanner(scanner.nextLine());
            String input = commandLine.next();
            
            user.login(input);

            if (input.equals("logout"))
            {
                done = true;
                commandLine.close();
                scanner.close();
                return;
            }
        }

    }

}