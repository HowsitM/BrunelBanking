import com.sun.deploy.util.SessionState;

/**
 * Created by Administrator on 12/12/2016.
 */

public class BankingActionState {

    private BankingActionState mySharedObj;
    private String myThreadName;
    private double mySharedVariable, ClientOneAccount, ClientTwoAccount, ClientThreeAccount, ClientFourAccount;
    private boolean accessing = false; // true a thread has a lock, false otherwise
    private int threadsWaiting = 0; // number of waiting writers
    private String[] outputs = new String[4];

// Constructor

    BankingActionState(double transaction) {
        ClientOneAccount = transaction;
        ClientTwoAccount = transaction;
        ClientThreeAccount = transaction;
        ClientFourAccount = transaction;
    }

//Attempt to aquire a lock

    public synchronized void acquireLock() throws InterruptedException {
        Thread me = Thread.currentThread(); // get a ref to the current thread
        System.out.println(me.getName() + " is attempting to acquire a lock!");
        ++threadsWaiting;
        while (accessing) {  // while someone else is accessing or threadsWaiting > 0
            System.out.println(me.getName() + " waiting to get a lock as someone else is accessing...");
            //wait for the lock to be released - see releaseLock() below
            wait();
        }
        // nobody has got a lock so get one
        --threadsWaiting;
        accessing = true;
        System.out.println(me.getName() + " got a lock!");
    }

    // Releases a lock to when a thread is finished

    public synchronized void releaseLock() {
        //release the lock and tell everyone
        accessing = false;
        notifyAll();
        Thread me = Thread.currentThread(); // get a ref to the current thread
        System.out.println(me.getName() + " released a lock!");
    }


    /* The processInput method */

    public synchronized String processInput(String myThreadName, String theInput) {
        System.out.println("Thread " + myThreadName + " wants to do action " + theInput);
        //String theOutput = null;
        String theOutput = "Something went wrong";
        int transfer = 20;

        //Deposit Funds into any account
        // Check what the client said
        if (theInput.equals("1")) {
            //Correct request
            if (myThreadName.equals("BankingThread1")) {

                ClientOneAccount = ClientOneAccount + 50;
                System.out.println("Client" + myThreadName + " has added £50 to their account " + ClientOneAccount);
                outputs[0] = "Client " + myThreadName + "'s account has been updated = " + ClientOneAccount;

            } else if (myThreadName.equals("BankingThread2")) {

                ClientTwoAccount = ClientTwoAccount + 50;
                System.out.println("Client" + myThreadName + " has added £50 to their account " + ClientTwoAccount);
                outputs[0] = "Client " + myThreadName + "'s account has been updated = " + ClientTwoAccount;

            } else if (myThreadName.equals("BankingThread3")) {

                ClientThreeAccount = ClientThreeAccount + 50;
                System.out.println(myThreadName + " made the SharedVariable " + ClientThreeAccount);
                outputs[0] = "Do action completed.  Shared Variable now = " + ClientThreeAccount;

            } else if (myThreadName.equals("BankingThread4")) {

                ClientFourAccount = ClientFourAccount + 50;
                System.out.println(myThreadName + " made the SharedVariable " + ClientFourAccount);
                outputs[0] = "Do action completed.  Shared Variable now = " + ClientFourAccount;
            } else {
                System.out.println("Error - thread call not recognised.");
            }
        } else { //incorrect request
            outputs[0] = myThreadName + " received incorrect request - only understand \"Do my action!\"";

        }
//Withdraw funds from any account
        if (theInput.equals("2")) {
            if (myThreadName.equals("BankingThread1")) {

                ClientOneAccount = ClientOneAccount - 50;
                System.out.println("Client" + myThreadName + " has withdrew £50 to their account " + ClientOneAccount);
                outputs[1] = "Client " + myThreadName + "'s account has been updated = " + ClientOneAccount;

            } else if (myThreadName.equals("BankingThread2")) {

                ClientTwoAccount = ClientTwoAccount - 50;
                System.out.println("Client" + myThreadName + " has withdrew £50 to their account " + ClientTwoAccount);
                outputs[1] = "Client " + myThreadName + "'s account has been updated = " + ClientTwoAccount;

            } else if (myThreadName.equals("BankingThread3")) {

                ClientThreeAccount = ClientThreeAccount - 50;
                System.out.println("Client" + myThreadName + " has withdrew £50 to their account " + ClientThreeAccount);
                outputs[1] = "Client " + myThreadName + "'s account has been updated = " + ClientThreeAccount;

            } else if (myThreadName.equals("BankingThread4")) {

                ClientFourAccount = ClientFourAccount - 50;
                System.out.println("Client" + myThreadName + " has withdrew £50 to their account " + ClientFourAccount);
                outputs[1] = "Client " + myThreadName + "'s account has been updated = " + ClientFourAccount;

            } else {
                System.out.println("Error - thread call not recognised.");
            }
        }
//Transfer Funds to any account
        if (theInput.equals("3")) {
            if (myThreadName.equals("BankingThread1")) {

                System.out.println("Who do you want to transfer money to? 1,2,3 or 4");
                if (theInput.equals("1")) {
                    System.out.println("Unable to transfer money to yourself");

                } else if (theInput.equals("2")) {
                    ClientOneAccount = ClientOneAccount - transfer;
                    ClientTwoAccount = ClientTwoAccount + transfer;
                    outputs[0] = "Client " + myThreadName + "'s account has been updated = " + ClientOneAccount;
                    outputs[1] = "Client 2 now has " + ClientTwoAccount;

        //You need to update both array positions to make the change stick otherwise you are only updating the one value.
                } else if (theInput.equals("3")) {
                    ClientOneAccount = ClientOneAccount - transfer;
                    ClientThreeAccount = ClientThreeAccount + transfer;
                    outputs[0] = "Client " + myThreadName + "'s account has been updated = " + ClientOneAccount;
                    outputs[2] = "Client 3 now has " + ClientThreeAccount;

                } else if (theInput.equals("4")) {
                    ClientOneAccount = ClientOneAccount - transfer;
                    ClientFourAccount = ClientFourAccount + transfer;
                    outputs[0] = "Client " + myThreadName + "'s account has been updated = " + ClientOneAccount;}
                    outputs[3] = "Client 4 now has " + ClientFourAccount;
            }

            if (myThreadName.equals("BankingThread2")) {

                System.out.println("Who do you want to transfer money to? 1,2,3 or 4");
                if (theInput.equals("2")) {
                    System.out.println("Unable to transfer money to yourself");

                } else if (theInput.equals("1")) {
                    ClientTwoAccount = ClientTwoAccount - transfer;
                    ClientOneAccount = ClientOneAccount + transfer;
                    outputs[1] = "Client " + myThreadName + "'s account has been updated = " + ClientTwoAccount;
                    outputs[0] = "Client 1 now has " + ClientOneAccount;

                } else if (theInput.equals("3")) {
                    ClientTwoAccount = ClientTwoAccount - transfer;
                    ClientThreeAccount = ClientThreeAccount + transfer;
                    outputs[1] = "Client " + myThreadName + "'s account has been updated = " + ClientTwoAccount;
                    outputs[2] = "Client 3 now has " + ClientThreeAccount;

                } else if (theInput.equals("4")) {
                    ClientTwoAccount = ClientTwoAccount - transfer;
                    ClientFourAccount = ClientFourAccount + transfer;
                    outputs[1] = "Client " + myThreadName + "'s account has been updated = " + ClientTwoAccount;}
                    outputs[3] = "Client 4 now has " + ClientFourAccount;
            }

            if (myThreadName.equals("BankingThread3")) {

                System.out.println("Who do you want to transfer money to? 1,2,3 or 4");
                if (theInput.equals("3")) {
                    System.out.println("Unable to transfer money to yourself");

                } else if (theInput.equals("1")) {
                    ClientThreeAccount = ClientThreeAccount - transfer;
                    ClientOneAccount = ClientOneAccount + transfer;
                    outputs[2] = "Client " + myThreadName + "'s account has been updated = " + ClientTwoAccount;
                    outputs[0] = "Client 1 now has " + ClientOneAccount;

                } else if (theInput.equals("2")) {
                    ClientThreeAccount = ClientThreeAccount - transfer;
                    ClientTwoAccount = ClientTwoAccount + transfer;
                    outputs[2] = "Client " + myThreadName + "'s account has been updated = " + ClientTwoAccount;
                    outputs[1] = "Client Two now has " + ClientTwoAccount;

                } else if (theInput.equals("4")) {
                    ClientOneAccount = ClientOneAccount - transfer;
                    ClientFourAccount = ClientFourAccount + transfer;
                    outputs[2] = "Client " + myThreadName + "'s account has been updated = " + ClientTwoAccount;}
                    outputs[3] = "Client 4 now has " + ClientFourAccount;
            }

            if (myThreadName.equals("BankingThread4")) {

                System.out.println("Who do you want to transfer money to? 1,2,3 or 4");
                if (theInput.equals("4")) {
                    System.out.println("Unable to transfer money to yourself");

                } else if (theInput.equals("1")) {
                    ClientFourAccount = ClientFourAccount - transfer;
                    ClientOneAccount = ClientOneAccount + transfer;
                    outputs[2] = "Client " + myThreadName + "'s account has been updated = " + ClientFourAccount;
                    outputs[0] = "Client 1 now has " + ClientOneAccount;

                } else if (theInput.equals("2")) {
                    ClientFourAccount = ClientFourAccount - transfer;
                    ClientTwoAccount = ClientTwoAccount + transfer;
                    outputs[2] = "Client " + myThreadName + "'s account has been updated = " + ClientFourAccount;
                    outputs[1] = "Client 2 now has " + ClientTwoAccount;

                } else if (theInput.equals("3")) {
                    ClientFourAccount = ClientFourAccount - transfer;
                    ClientThreeAccount = ClientThreeAccount + transfer;
                    outputs[2] = "Client " + myThreadName + "'s account has been updated = " + ClientFourAccount;}
                    outputs[2] = "Client 3 now has " + ClientThreeAccount;
            }

        }
//Check your balance here
        if(theInput.equals("4")){
            if(myThreadName.equals("BankingThread1")){
                System.out.println("Client " + myThreadName + "'s account has been updated = " + ClientOneAccount);
                outputs[3] = myThreadName + ": Your balance is" + ClientOneAccount;
            }
            else if(myThreadName.equals("BankingThread2")){
                outputs[3] = myThreadName + ": Your balance is" + ClientTwoAccount;
            }
            else if(myThreadName.equals("BankingThread3")){
                outputs[3] = myThreadName + ": Your balance is" + ClientThreeAccount;
            }
            else if(myThreadName.equals("BankingThread4")){
                outputs[3] = " " + myThreadName + ": Your balance is" + ClientFourAccount;
            }
        }

        if(theInput.equals("5")){
            System.exit(0);
        }
        //Return the output message to the BankingActionServer
        System.out.println(theOutput);

        if(theInput.equals("1")){ return outputs[0];}
        if(theInput.equals("2")){ return outputs[1];}
        if(theInput.equals("3")){ return outputs[2];}
        if(theInput.equals("4")){ return outputs[3];}

        return theOutput;
    }
}