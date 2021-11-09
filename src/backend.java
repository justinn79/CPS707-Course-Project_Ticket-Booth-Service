import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

//This class produces the mergedDailyTransactionFile after every session (after every logout)
public class backend {

    //This method merges all files from the dailyTransactionFiles folder into the mergedDailyTransactionFile.txt
    public static void mergeDTF() throws IOException{
        File folder = new File("CPS707-Course-Project-main/dailyTransactionFiles");
 
        PrintWriter pw = new PrintWriter("CPS707-Course-Project-main/txtfiles/mergedDailyTransactionFile.txt");
 
        // Get list of all the files in form of String Array
        String[] fileNames = folder.list();
 
        // loop for reading the contents of all the files in the directory dailyTransactionFiles
        for (String fileName : fileNames) {
            File f = new File(folder, fileName);
 
            // create object of BufferedReader
            BufferedReader br = new BufferedReader(new FileReader(f));
 
            // Read from current file
            String line = br.readLine();
            while (line != null) {
 
                // write to the output file in the txtfiles folder
                pw.println(line);
                line = br.readLine();
            }
            pw.flush();
    }
    }
}
