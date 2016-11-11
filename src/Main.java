package src;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Users users = new Users();

        ExistTransactionReader existTransactionReader = new ExistTransactionReader("paymo_input/batch_payment.txt",users);

        existTransactionReader.readTransactions();

        NewTransactionReader newTransactionReader = new NewTransactionReader("paymo_input/stream_payment.txt", users);

        newTransactionReader.readTransactions();

    }

}
