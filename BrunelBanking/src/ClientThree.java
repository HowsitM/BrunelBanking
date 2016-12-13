/**
 * Created by Administrator on 12/12/2016.
 */
import java.io.*;
import java.net.*;

public class ClientThree {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket BankingClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int BankingSocketNumber = 4545;
        String BankingServerName = "localhost";
        String BankingClientID = "ClientThree";

        try {
            BankingClientSocket = new Socket(BankingServerName, BankingSocketNumber);
            out = new PrintWriter(BankingClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(BankingClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ BankingSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + BankingClientID + " client and IO connections");

        System.out.println("--Menu--");
        System.out.println("1. Add");
        System.out.println("2. Subtract");
        System.out.println("3. Transfer");
        System.out.println("4. Balance");
        System.out.println("5. Exit");

        // This is modified as it's the client that speaks first

        while (true) {

            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println(BankingClientID + " sending " + fromUser + " to BankingServer");
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println(BankingClientID + " received " + fromServer + " from BankingServer");
        }


        // Tidy up - not really needed due to true condition in while loop
        //  out.close();
        // in.close();
        // stdIn.close();
        // BankingClientSocket.close();
    }
}

