package src;

import com.opencsv.CSVReader;
import java.io.*;

/**
 * Created by Tempaycai on 11/6/16.
 */
public class NewTransactionReader {

    private final String path;
    private final Users users;
    private BufferedWriter writer;
    private static final String OUTPUT1 = "paymo_output/output1.txt";
    private static final String OUTPUT2 = "paymo_output/output2.txt";
    private static final String OUTPUT3 = "paymo_output/output3.txt";

    public NewTransactionReader(String path, Users users) {
        this.path = path;
        this.users = users;

    }

    // this method read the transaction stream line by line
    public void readTransactions() throws IOException {

        CSVReader reader = new CSVReader(new FileReader(path));

        reader.readNext();

        for(String[] transaction = reader.readNext(); transaction != null; transaction = reader.readNext()) {
            try {
                // parse the user id
                Integer id1 = Integer.parseInt(transaction[1].trim());
                Integer id2 = Integer.parseInt(transaction[2].trim());

                // verify this transaction
                friendVerification(id1, id2);
                secondGenerationVerification(id1, id2);
                fourthGenerationVerification(id1, id2);

                // add the transaction to the users database
                users.addTransaction(id1, id2);
            }
            catch (Exception e) {
                continue;
            }
        }
    }

    // verify if user2 is user1's firend, implements feature 1;
    private void friendVerification(Integer id1, Integer id2) throws IOException {
        if (users.areFriends(id1, id2)) {
            printVerified(OUTPUT1);
        }
        else {
            printUnverified(OUTPUT1);
        }
    }

    // verify if user2 is the second generation friend of user1, implements feature 2;
    private void secondGenerationVerification(Integer id1, Integer id2) throws IOException {
        if (!users.areSecondGenerationFriends(id1, id2)) {
            printUnverified(OUTPUT2);
        }
        else{
            printVerified(OUTPUT2);
        }
    }

    // verify if user2 is the forth generation friend of user1, implements feature 3;
    private void fourthGenerationVerification(Integer id1, Integer id2) throws IOException {
        // if users are not 4th generation friends, print 'Unverified' warning
        if(!users.areFourthGenerationFriends(id1, id2)) {
            printUnverified(OUTPUT3);
        }
        else{
            printVerified(OUTPUT3);
        }
    }

    // print 'Unverified' warning
    private void printUnverified(String path) throws IOException {
        writer = new BufferedWriter(new FileWriter(path, true));
        writer.write("unverified\n");
        writer.flush();
        writer.close();
    }

    // print trusted message
    private void printVerified(String path) throws IOException {
        writer = new BufferedWriter(new FileWriter(path, true));
        writer.write("trusted\n");
        writer.flush();
        writer.close();
    }
}
