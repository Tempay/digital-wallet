package src;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Tempaycai on 11/6/16.
 */
public class ExistTransactionReader {

    private final String path;

    private final Users users;

    public ExistTransactionReader(String path, Users users) {
        this.path = path;
        this.users = users;
    }

    public void readTransactions() throws IOException {

        CSVReader reader = new CSVReader(new FileReader(path));

        reader.readNext();

        for(String[] transaction = reader.readNext(); transaction != null; transaction = reader.readNext()) {
            try {
                Integer id1 = Integer.parseInt(transaction[1].trim());
                Integer id2 = Integer.parseInt(transaction[2].trim());
                users.addTransaction(id1, id2);
            }
            catch (Exception e) {
                continue;
            }
        }
    }

}
