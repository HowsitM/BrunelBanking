/**
 * Created by Administrator on 12/12/2016.
 */
import java.net.*;
import java.io.*;

public class BankingServer {
    public static void main(String[] args) throws IOException {

        ServerSocket BankingServerSocket = null;
        boolean listening = true;
        String BankingServerName = "BankingServer";
        int BankingServerNumber = 4545;

        double SharedVariable = 100;

        double ClientOneAccount =   100;
        double ClientTwoAccount =   200;
        double ClientThreeAccount = 300;
        double ClientFourAccount =  400;

        //Create the shared object in the global scope...

        //BankingActionState ourBankingActionStateObject = new BankingActionState(SharedVariable);
        BankingActionState ourBankingActionStateObject1 = new BankingActionState(ClientOneAccount);
        BankingActionState ourBankingActionStateObject2= new BankingActionState(ClientTwoAccount);
        BankingActionState ourBankingActionStateObject3 = new BankingActionState(ClientThreeAccount);
        BankingActionState ourBankingActionStateObject4 = new BankingActionState(ClientFourAccount);

        // Make the server socket

        try {
            BankingServerSocket = new ServerSocket(BankingServerNumber);
        } catch (IOException e) {
            System.err.println("Could not start " + BankingServerName + " specified port.");
            System.exit(-1);
        }
        System.out.println(BankingServerName + " started");

        //Got to do this in the correct order with only four clients!  Can automate this...

        while (listening){
            new BankingServerThread(BankingServerSocket.accept(), "BankingThread1", ourBankingActionStateObject1).start();
            new BankingServerThread(BankingServerSocket.accept(), "BankingThread2", ourBankingActionStateObject2).start();
            new BankingServerThread(BankingServerSocket.accept(), "BankingThread3", ourBankingActionStateObject3).start();
            new BankingServerThread(BankingServerSocket.accept(), "BankingThread4", ourBankingActionStateObject4).start();
            System.out.println("New " + BankingServerName + " thread started.");
        }

        BankingServerSocket.close();
    }
}