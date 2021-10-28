import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.FileReader;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class test {
    // Method used to run all the tests, files in the txtfiles directory should be reset before running the tests
    public static void main(String[] args) throws Exception
    {
        loginInvalid();
        loginValid();
        logoutNoTransaction();
        logoutValidLogout();
        createStoreUserCredentials();
        createAdminPrivilege();
        createUniqueUser();
        deleteTransactionFile();
        deleteAdmin();
        deleteCurrentUser();
        sellTransactionFile();
        sellStandardBuy();
        buyTransactionFile();
        buyStandardSell();
        buyMaxTickets();
        buyAdminNoLimit();
        refundTransactionFile();
        refundCurrentUsers();
        refundPrivilege();
        addCreditTransaction();
        addCreditAdmin();
        generalBadInput();
    }

    // A helper function that creates the actual output files and compares them with the expected output files
    public static void testHelper(String inputFilePath, String actualTOPath, String actualCUAFPath, String actualATFPath, String actualDTFPath, 
    String expectedTOPath, String expectedCUAFPath, String expectedATFPath, String expectedDTFPath) throws IOException, Exception
    {
        // Change System.out to our actualTO.txt file
        PrintStream originalOut = System.out;
        PrintStream fileOut = new PrintStream(actualTOPath);
        System.setOut(fileOut);
        
        // Run main and change System.in to our input file
        String[] args = null;
        InputStream originalIn = System.in;
        FileInputStream inputFile = new FileInputStream(new File(inputFilePath));
        System.setIn(inputFile);
        main.main(args);
        
        //Set System.in and System.out back to normal
        System.setIn(originalIn);
        System.setOut(originalOut);
        
        // Copy the files from the txtfiles directory into the actualOutputFiles directory
        Path originalCUAF = Paths.get("txtfiles/currentUsersAccountsFile.txt");
        Path actualCUAF = Paths.get(actualCUAFPath);
        Files.copy(originalCUAF, actualCUAF, StandardCopyOption.REPLACE_EXISTING);

        Path originalATF = Paths.get("txtfiles/availableTicketsFile.txt");
        Path actualATF = Paths.get(actualATFPath);
        Files.copy(originalATF, actualATF, StandardCopyOption.REPLACE_EXISTING);

        Path originalDTF = Paths.get("txtfiles/dailyTransactionFile.txt");
        Path actualDTF = Paths.get(actualDTFPath);
        Files.copy(originalDTF, actualDTF, StandardCopyOption.REPLACE_EXISTING);

        // Making Readers to compare the expected output files with the actual output files
        Path actualTO = Paths.get(actualTOPath);
        Reader actualTOReader = new BufferedReader(new FileReader(actualTO.toFile()));
        Reader actualCUAFReader = new BufferedReader(new FileReader(actualCUAF.toFile()));
        Reader actualATFReader = new BufferedReader(new FileReader(actualATF.toFile()));
        Reader actualDTFReader = new BufferedReader(new FileReader(actualDTF.toFile()));

        Path expectedTO = Paths.get(expectedTOPath);
        Reader expectedTOReader = new BufferedReader(new FileReader(expectedTO.toFile()));
        Path expectedCUAF = Paths.get(expectedCUAFPath);
        Reader expectedCUAFReader = new BufferedReader(new FileReader(expectedCUAF.toFile()));
        Path expectedATF = Paths.get(expectedATFPath);
        Reader expectedATFReader = new BufferedReader(new FileReader(expectedATF.toFile()));
        Path expectedDTF = Paths.get(expectedDTFPath);
        Reader expectedDTFReader = new BufferedReader(new FileReader(expectedDTF.toFile()));

        // Compare the expected output files with the actual output files
        try
        {
            Assert.assertTrue(IOUtils.contentEqualsIgnoreEOL(expectedTOReader, actualTOReader));
            System.out.println("Success: TO");
        }
        catch(AssertionError e)
        {
            System.out.println("Failed: TO");
        }
        try
        {
            Assert.assertTrue(IOUtils.contentEqualsIgnoreEOL(expectedCUAFReader, actualCUAFReader));
            System.out.println("Success: CUAF");
        }
        catch(AssertionError e)
        {
            System.out.println("Failed: CUAF");
        }
        try
        {
            Assert.assertTrue(IOUtils.contentEqualsIgnoreEOL(expectedATFReader, actualATFReader));
            System.out.println("Success: ATF");
        }
        catch(AssertionError e)
        {
            System.out.println("Failed: ATF");
        }
        try
        {
            Assert.assertTrue(IOUtils.contentEqualsIgnoreEOL(expectedDTFReader, actualDTFReader));
            System.out.println("Success: DTF");
        }
        catch(AssertionError e)
        {
            System.out.println("Failed: DTF");
        }
    }

    @Test
    public static void loginInvalid() throws Exception
    {
        System.out.println("Test #1: loginInvalid");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/login/loginInvalid.txt", 
        "testCases/actualOutputFiles/login/loginInvalid/actualTO.txt", 
        "testCases/actualOutputFiles/login/loginInvalid/actualCUAF.txt", 
        "testCases/actualOutputFiles/login/loginInvalid/actualATF.txt", 
        "testCases/actualOutputFiles/login/loginInvalid/actualDTF.txt", 
        "testCases/expectedOutputFiles/login/loginInvalid/expectedTO.txt", 
        "testCases/expectedOutputFiles/login/loginInvalid/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/login/loginInvalid/expectedATF.txt", 
        "testCases/expectedOutputFiles/login/loginInvalid/expectedDTF.txt");
    }

    @Test
    public static void loginValid() throws Exception
    {
        System.out.println("\nTest #2: loginValid");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/login/loginValid.txt", 
        "testCases/actualOutputFiles/login/loginValid/actualTO.txt", 
        "testCases/actualOutputFiles/login/loginValid/actualCUAF.txt", 
        "testCases/actualOutputFiles/login/loginValid/actualATF.txt", 
        "testCases/actualOutputFiles/login/loginValid/actualDTF.txt", 
        "testCases/expectedOutputFiles/login/loginValid/expectedTO.txt", 
        "testCases/expectedOutputFiles/login/loginValid/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/login/loginValid/expectedATF.txt", 
        "testCases/expectedOutputFiles/login/loginValid/expectedDTF.txt");
    }

    @Test
    public static void logoutNoTransaction() throws Exception
    {
        System.out.println("\nTest #3: logoutNoTransaction");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/logout/logoutNoTransaction.txt", 
        "testCases/actualOutputFiles/logout/logoutNoTransaction/actualTO.txt", 
        "testCases/actualOutputFiles/logout/logoutNoTransaction/actualCUAF.txt", 
        "testCases/actualOutputFiles/logout/logoutNoTransaction/actualATF.txt", 
        "testCases/actualOutputFiles/logout/logoutNoTransaction/actualDTF.txt", 
        "testCases/expectedOutputFiles/logout/logoutNoTransaction/expectedTO.txt", 
        "testCases/expectedOutputFiles/logout/logoutNoTransaction/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/logout/logoutNoTransaction/expectedATF.txt", 
        "testCases/expectedOutputFiles/logout/logoutNoTransaction/expectedDTF.txt");
    }

    @Test
    public static void logoutValidLogout() throws Exception
    {
        System.out.println("\nTest #4: logoutValidLogout");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/logout/logoutValidLogout.txt", 
        "testCases/actualOutputFiles/logout/logoutValidLogout/actualTO.txt", 
        "testCases/actualOutputFiles/logout/logoutValidLogout/actualCUAF.txt", 
        "testCases/actualOutputFiles/logout/logoutValidLogout/actualATF.txt", 
        "testCases/actualOutputFiles/logout/logoutValidLogout/actualDTF.txt", 
        "testCases/expectedOutputFiles/logout/logoutValidLogout/expectedTO.txt", 
        "testCases/expectedOutputFiles/logout/logoutValidLogout/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/logout/logoutValidLogout/expectedATF.txt", 
        "testCases/expectedOutputFiles/logout/logoutValidLogout/expectedDTF.txt");
    }

    @Test
    public static void createStoreUserCredentials() throws Exception
    {
        System.out.println("\nTest #5: createStoreUserCredentials");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/create/createStoreUserCredentials.txt", 
        "testCases/actualOutputFiles/create/createStoreUserCredentials/actualTO.txt", 
        "testCases/actualOutputFiles/create/createStoreUserCredentials/actualCUAF.txt", 
        "testCases/actualOutputFiles/create/createStoreUserCredentials/actualATF.txt", 
        "testCases/actualOutputFiles/create/createStoreUserCredentials/actualDTF.txt", 
        "testCases/expectedOutputFiles/create/createStoreUserCredentials/expectedTO.txt", 
        "testCases/expectedOutputFiles/create/createStoreUserCredentials/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/create/createStoreUserCredentials/expectedATF.txt", 
        "testCases/expectedOutputFiles/create/createStoreUserCredentials/expectedDTF.txt");
    }

    @Test
    public static void createAdminPrivilege() throws Exception
    {
        System.out.println("\nTest #6: createAdminPrivilege");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/create/createAdminPrivilege.txt", 
        "testCases/actualOutputFiles/create/createAdminPrivilege/actualTO.txt", 
        "testCases/actualOutputFiles/create/createAdminPrivilege/actualCUAF.txt", 
        "testCases/actualOutputFiles/create/createAdminPrivilege/actualATF.txt", 
        "testCases/actualOutputFiles/create/createAdminPrivilege/actualDTF.txt", 
        "testCases/expectedOutputFiles/create/createAdminPrivilege/expectedTO.txt", 
        "testCases/expectedOutputFiles/create/createAdminPrivilege/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/create/createAdminPrivilege/expectedATF.txt", 
        "testCases/expectedOutputFiles/create/createAdminPrivilege/expectedDTF.txt");
    }

    @Test
    public static void createUniqueUser() throws Exception
    {
        System.out.println("\nTest #7: createUniqueUser");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/create/createUniqueUser.txt", 
        "testCases/actualOutputFiles/create/createUniqueUser/actualTO.txt", 
        "testCases/actualOutputFiles/create/createUniqueUser/actualCUAF.txt", 
        "testCases/actualOutputFiles/create/createUniqueUser/actualATF.txt", 
        "testCases/actualOutputFiles/create/createUniqueUser/actualDTF.txt", 
        "testCases/expectedOutputFiles/create/createUniqueUser/expectedTO.txt", 
        "testCases/expectedOutputFiles/create/createUniqueUser/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/create/createUniqueUser/expectedATF.txt", 
        "testCases/expectedOutputFiles/create/createUniqueUser/expectedDTF.txt");
    }

    @Test
    public static void deleteTransactionFile() throws Exception
    {
        System.out.println("\nTest #8: deleteTransactionFile");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/delete/deleteTransactionFile.txt", 
        "testCases/actualOutputFiles/delete/deleteTransactionFile/actualTO.txt", 
        "testCases/actualOutputFiles/delete/deleteTransactionFile/actualCUAF.txt", 
        "testCases/actualOutputFiles/delete/deleteTransactionFile/actualATF.txt", 
        "testCases/actualOutputFiles/delete/deleteTransactionFile/actualDTF.txt", 
        "testCases/expectedOutputFiles/delete/deleteTransactionFile/expectedTO.txt", 
        "testCases/expectedOutputFiles/delete/deleteTransactionFile/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/delete/deleteTransactionFile/expectedATF.txt", 
        "testCases/expectedOutputFiles/delete/deleteTransactionFile/expectedDTF.txt");
    }

    @Test
    public static void deleteAdmin() throws Exception
    {
        System.out.println("\nTest #9: deleteAdmin");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/delete/deleteAdmin.txt",
        "testCases/actualOutputFiles/delete/deleteAdmin/actualTO.txt", 
        "testCases/actualOutputFiles/delete/deleteAdmin/actualCUAF.txt", 
        "testCases/actualOutputFiles/delete/deleteAdmin/actualATF.txt", 
        "testCases/actualOutputFiles/delete/deleteAdmin/actualDTF.txt", 
        "testCases/expectedOutputFiles/delete/deleteAdmin/expectedTO.txt", 
        "testCases/expectedOutputFiles/delete/deleteAdmin/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/delete/deleteAdmin/expectedATF.txt", 
        "testCases/expectedOutputFiles/delete/deleteAdmin/expectedDTF.txt");
    }

    @Test
    public static void deleteCurrentUser() throws Exception
    {
        System.out.println("\nTest #10: deleteCurrentUser");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/delete/deleteCurrentUser.txt",
        "testCases/actualOutputFiles/delete/deleteCurrentUser/actualTO.txt", 
        "testCases/actualOutputFiles/delete/deleteCurrentUser/actualCUAF.txt", 
        "testCases/actualOutputFiles/delete/deleteCurrentUser/actualATF.txt", 
        "testCases/actualOutputFiles/delete/deleteCurrentUser/actualDTF.txt", 
        "testCases/expectedOutputFiles/delete/deleteCurrentUser/expectedTO.txt", 
        "testCases/expectedOutputFiles/delete/deleteCurrentUser/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/delete/deleteCurrentUser/expectedATF.txt", 
        "testCases/expectedOutputFiles/delete/deleteCurrentUser/expectedDTF.txt");
    }

    @Test
    public static void sellTransactionFile() throws Exception
    {
        System.out.println("\nTest #11: sellTransactionFile");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/sell/sellTransactionFile.txt",
        "testCases/actualOutputFiles/sell/sellTransactionFile/actualTO.txt", 
        "testCases/actualOutputFiles/sell/sellTransactionFile/actualCUAF.txt", 
        "testCases/actualOutputFiles/sell/sellTransactionFile/actualATF.txt", 
        "testCases/actualOutputFiles/sell/sellTransactionFile/actualDTF.txt", 
        "testCases/expectedOutputFiles/sell/sellTransactionFile/expectedTO.txt", 
        "testCases/expectedOutputFiles/sell/sellTransactionFile/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/sell/sellTransactionFile/expectedATF.txt", 
        "testCases/expectedOutputFiles/sell/sellTransactionFile/expectedDTF.txt");
    }

    @Test
    public static void sellStandardBuy() throws Exception
    {
        System.out.println("\nTest #12: sellStandardBuy");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/sell/sellStandardBuy.txt",
        "testCases/actualOutputFiles/sell/sellStandardBuy/actualTO.txt", 
        "testCases/actualOutputFiles/sell/sellStandardBuy/actualCUAF.txt", 
        "testCases/actualOutputFiles/sell/sellStandardBuy/actualATF.txt", 
        "testCases/actualOutputFiles/sell/sellStandardBuy/actualDTF.txt", 
        "testCases/expectedOutputFiles/sell/sellStandardBuy/expectedTO.txt", 
        "testCases/expectedOutputFiles/sell/sellStandardBuy/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/sell/sellStandardBuy/expectedATF.txt", 
        "testCases/expectedOutputFiles/sell/sellStandardBuy/expectedDTF.txt");
    }

    @Test
    public static void buyTransactionFile() throws Exception
    {
        System.out.println("\nTest #13: buyTransactionFile");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/buy/buyTransactionFile.txt",
        "testCases/actualOutputFiles/buy/buyTransactionFile/actualTO.txt", 
        "testCases/actualOutputFiles/buy/buyTransactionFile/actualCUAF.txt", 
        "testCases/actualOutputFiles/buy/buyTransactionFile/actualATF.txt", 
        "testCases/actualOutputFiles/buy/buyTransactionFile/actualDTF.txt", 
        "testCases/expectedOutputFiles/buy/buyTransactionFile/expectedTO.txt", 
        "testCases/expectedOutputFiles/buy/buyTransactionFile/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/buy/buyTransactionFile/expectedATF.txt", 
        "testCases/expectedOutputFiles/buy/buyTransactionFile/expectedDTF.txt");
    }

    @Test
    public static void buyStandardSell() throws Exception
    {
        System.out.println("\nTest #14: buyStandardSell");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/buy/buyStandardSell.txt",
        "testCases/actualOutputFiles/buy/buyStandardSell/actualTO.txt", 
        "testCases/actualOutputFiles/buy/buyStandardSell/actualCUAF.txt", 
        "testCases/actualOutputFiles/buy/buyStandardSell/actualATF.txt", 
        "testCases/actualOutputFiles/buy/buyStandardSell/actualDTF.txt", 
        "testCases/expectedOutputFiles/buy/buyStandardSell/expectedTO.txt", 
        "testCases/expectedOutputFiles/buy/buyStandardSell/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/buy/buyStandardSell/expectedATF.txt", 
        "testCases/expectedOutputFiles/buy/buyStandardSell/expectedDTF.txt");
    }

    @Test
    public static void buyMaxTickets() throws Exception
    {
        System.out.println("\nTest #15: buyMaxTickets");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/buy/buyMaxTickets.txt",
        "testCases/actualOutputFiles/buy/buyMaxTickets/actualTO.txt", 
        "testCases/actualOutputFiles/buy/buyMaxTickets/actualCUAF.txt", 
        "testCases/actualOutputFiles/buy/buyMaxTickets/actualATF.txt", 
        "testCases/actualOutputFiles/buy/buyMaxTickets/actualDTF.txt", 
        "testCases/expectedOutputFiles/buy/buyMaxTickets/expectedTO.txt", 
        "testCases/expectedOutputFiles/buy/buyMaxTickets/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/buy/buyMaxTickets/expectedATF.txt", 
        "testCases/expectedOutputFiles/buy/buyMaxTickets/expectedDTF.txt");
    }

    @Test
    public static void buyAdminNoLimit() throws Exception
    {
        System.out.println("\nTest #16: buyAdminNoLimit");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/buy/buyAdminNoLimit.txt",
        "testCases/actualOutputFiles/buy/buyAdminNoLimit/actualTO.txt", 
        "testCases/actualOutputFiles/buy/buyAdminNoLimit/actualCUAF.txt", 
        "testCases/actualOutputFiles/buy/buyAdminNoLimit/actualATF.txt", 
        "testCases/actualOutputFiles/buy/buyAdminNoLimit/actualDTF.txt", 
        "testCases/expectedOutputFiles/buy/buyAdminNoLimit/expectedTO.txt", 
        "testCases/expectedOutputFiles/buy/buyAdminNoLimit/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/buy/buyAdminNoLimit/expectedATF.txt", 
        "testCases/expectedOutputFiles/buy/buyAdminNoLimit/expectedDTF.txt");
    }

    @Test
    public static void refundTransactionFile() throws Exception
    {
        System.out.println("\nTest #17: refundTransactionFile");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/refund/refundTransactionFile.txt",
        "testCases/actualOutputFiles/refund/refundTransactionFile/actualTO.txt", 
        "testCases/actualOutputFiles/refund/refundTransactionFile/actualCUAF.txt", 
        "testCases/actualOutputFiles/refund/refundTransactionFile/actualATF.txt", 
        "testCases/actualOutputFiles/refund/refundTransactionFile/actualDTF.txt", 
        "testCases/expectedOutputFiles/refund/refundTransactionFile/expectedTO.txt", 
        "testCases/expectedOutputFiles/refund/refundTransactionFile/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/refund/refundTransactionFile/expectedATF.txt", 
        "testCases/expectedOutputFiles/refund/refundTransactionFile/expectedDTF.txt");
    }

    @Test
    public static void refundCurrentUsers() throws Exception
    {
        System.out.println("\nTest #18: refundCurrentUsers");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/refund/refundCurrentUsers.txt",
        "testCases/actualOutputFiles/refund/refundCurrentUsers/actualTO.txt", 
        "testCases/actualOutputFiles/refund/refundCurrentUsers/actualCUAF.txt", 
        "testCases/actualOutputFiles/refund/refundCurrentUsers/actualATF.txt", 
        "testCases/actualOutputFiles/refund/refundCurrentUsers/actualDTF.txt", 
        "testCases/expectedOutputFiles/refund/refundCurrentUsers/expectedTO.txt", 
        "testCases/expectedOutputFiles/refund/refundCurrentUsers/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/refund/refundCurrentUsers/expectedATF.txt", 
        "testCases/expectedOutputFiles/refund/refundCurrentUsers/expectedDTF.txt");
    }

    @Test
    public static void refundPrivilege() throws Exception
    {
        System.out.println("\nTest #19: refundPrivilege");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/refund/refundPrivilege.txt",
        "testCases/actualOutputFiles/refund/refundPrivilege/actualTO.txt", 
        "testCases/actualOutputFiles/refund/refundPrivilege/actualCUAF.txt", 
        "testCases/actualOutputFiles/refund/refundPrivilege/actualATF.txt", 
        "testCases/actualOutputFiles/refund/refundPrivilege/actualDTF.txt", 
        "testCases/expectedOutputFiles/refund/refundPrivilege/expectedTO.txt", 
        "testCases/expectedOutputFiles/refund/refundPrivilege/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/refund/refundPrivilege/expectedATF.txt", 
        "testCases/expectedOutputFiles/refund/refundPrivilege/expectedDTF.txt");
    }

    @Test
    public static void addCreditTransaction() throws Exception
    {
        System.out.println("\nTest #20: addCreditTransaction");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/addCredit/addCreditTransaction.txt",
        "testCases/actualOutputFiles/addCredit/addCreditTransaction/actualTO.txt", 
        "testCases/actualOutputFiles/addCredit/addCreditTransaction/actualCUAF.txt", 
        "testCases/actualOutputFiles/addCredit/addCreditTransaction/actualATF.txt", 
        "testCases/actualOutputFiles/addCredit/addCreditTransaction/actualDTF.txt", 
        "testCases/expectedOutputFiles/addCredit/addCreditTransaction/expectedTO.txt", 
        "testCases/expectedOutputFiles/addCredit/addCreditTransaction/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/addCredit/addCreditTransaction/expectedATF.txt", 
        "testCases/expectedOutputFiles/addCredit/addCreditTransaction/expectedDTF.txt");
    }

    @Test
    public static void addCreditAdmin() throws Exception
    {
        System.out.println("\nTest #21: addCreditAdmin");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/addCredit/addCreditAdmin.txt",
        "testCases/actualOutputFiles/addCredit/addCreditAdmin/actualTO.txt", 
        "testCases/actualOutputFiles/addCredit/addCreditAdmin/actualCUAF.txt", 
        "testCases/actualOutputFiles/addCredit/addCreditAdmin/actualATF.txt", 
        "testCases/actualOutputFiles/addCredit/addCreditAdmin/actualDTF.txt", 
        "testCases/expectedOutputFiles/addCredit/addCreditAdmin/expectedTO.txt", 
        "testCases/expectedOutputFiles/addCredit/addCreditAdmin/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/addCredit/addCreditAdmin/expectedATF.txt", 
        "testCases/expectedOutputFiles/addCredit/addCreditAdmin/expectedDTF.txt");
    }

    @Test
    public static void generalBadInput() throws Exception
    {
        System.out.println("\nTest #22: generalBadInput");
        
        // Calling testHelper with the right files
        testHelper("testCases/inputFiles/general/generalBadInput.txt",
        "testCases/actualOutputFiles/general/generalBadInput/actualTO.txt", 
        "testCases/actualOutputFiles/general/generalBadInput/actualCUAF.txt", 
        "testCases/actualOutputFiles/general/generalBadInput/actualATF.txt", 
        "testCases/actualOutputFiles/general/generalBadInput/actualDTF.txt", 
        "testCases/expectedOutputFiles/general/generalBadInput/expectedTO.txt", 
        "testCases/expectedOutputFiles/general/generalBadInput/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/general/generalBadInput/expectedATF.txt", 
        "testCases/expectedOutputFiles/general/generalBadInput/expectedDTF.txt");
    }
}
