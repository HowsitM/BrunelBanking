/**
 * Created by Administrator on 12/12/2016.
 */

import java.net.*;
import java.io.*;


public class BankingServerThread extends Thread {


    private Socket BankingActionSocket = null;
    private BankingActionState myBankingActionStateObject;
    private String myBankingServerThreadName;
    private double mySharedVariable;

    //Setup the thread
    public BankingServerThread(Socket BankingActionSocket, String BankingServerThreadName, BankingActionState SharedObject) {

	    //super(BankingServerThreadName);
        this.BankingActionSocket = BankingActionSocket;
        myBankingActionStateObject = SharedObject;
        myBankingServerThreadName = BankingServerThreadName;
    }

    public void run() {
        try {
            System.out.println(myBankingServerThreadName + "initialising.");
            PrintWriter out = new PrintWriter(BankingActionSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(BankingActionSocket.getInputStream()));
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                // Get a lock first
                try {
                    myBankingActionStateObject.acquireLock();
                    outputLine = myBankingActionStateObject.processInput(myBankingServerThreadName, inputLine);
                    out.println(outputLine);
                    myBankingActionStateObject.releaseLock();
                }
                catch(InterruptedException e) {
                    System.err.println("Failed to get lock when reading:"+e);
                }
            }

            out.close();
            in.close();
            BankingActionSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
