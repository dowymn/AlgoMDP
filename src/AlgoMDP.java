import java.util.Arrays;
import java.util.Scanner;

/**
 * Little class that asks the user to enter a password, and then checks all the possibilities to find it.
 * When the password is found, the program prints it, but also the number of tries and the time needed.
 *
 * @author dowymn
 */
public class AlgoMDP {


    //-----[ ATTRIBUTES

    /**
     * The tab that contains the possible characters.
     * They are not all in, but there are the most used ones.
     */
    private final char[] alpha = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            '0','1','2','3','4','5','6','7','8','9','!','?','.',',',';',':','/','\\'};
    /**
     * The lenght of the alpha list.
     * Is only used to avoid writing "alpha.length" each time.
     */
    private final int max = alpha.length;

    /**
     * The list that will be used to know which character has to be tried.
     */
    private int[] list;

    /**
     * The password chosen by the user.
     */
    private final String mdp;
    /**
     * The password found by the program.
     */
    private String foundMdp;

    /**
     * The number of tries the program needed to find the password.
     */
    private int nbTries;


    //-----[ MAIN METHOD

    /**
     * Asks the user for a password and then searches it until it is found.
     * Then, calls the method to prints the password, the number of tries and the duration the program needed to find it.
     */
    private AlgoMDP() {

        this.nbTries = 1;

        Scanner scan = new Scanner(System.in);
        System.out.print("Entrez un mot de passe : ");
        mdp = scan.nextLine();

        double t1 = System.currentTimeMillis();

        String line = "";
        list = new int[]{0};

        while ( !check(line) ) {
            reset();
            line = buildLine();
        }

        double t2 = System.currentTimeMillis();
        finish((t2-t1)/1000);
    }


    //-----[ OTHER METHODS

    /**
     * Builds the new line to compare with the password.
     * @return the line
     */
    private String buildLine() {
        StringBuilder line = new StringBuilder();
        for (int j : list) {
            line.append(alpha[j]);
        }
        return line.toString();
    }

    /**
     * Checks if the list has to be reseted, and does it if necessary.
     */
    private void reset() {

        // checks if the list has to be reseted
        boolean reset = true;
        int n = 0;
        while ( n < list.length && reset ) {
            if ( list[n] < max-1 ) {
                reset = false;
            }
            n++;
        }

        // if it does, we reset the list
        if ( reset ) {
            list = new int[list.length+1];
            Arrays.fill(list, 0);

        } else {
            boolean stop = false;
            int i = list.length-1;

            while ( i >= 0 && !stop ) {
                if ( list[i] >= max-1 ) {
                    list[i] = 0;
                } else {
                    list[i] ++;
                    stop = true;
                }
                i--;
            }
        }
    }

    /**
     * Checks if the built line matches with the password.
     * @param line the build line
     * @return true if the line is the good password, else false
     */
    private boolean check(String line) {
        boolean ok = false;
        if ( line.equals(mdp) ) {
            ok = true;
            foundMdp = line;
        } else {
            nbTries ++;
        }
        return ok;
    }

    /**
     * Prints the end of the program.
     * @param time the time the program needed to find the password
     */
    private void finish(double time) {
        System.out.println("\n-------------------------------------------------");
        System.out.println(" Mot de passe entré : " + foundMdp);
        System.out.println(" Nombre d'essais nécessaires : " + nbTries);
        System.out.println(" Temps : " + time + " s");
        System.out.println("-------------------------------------------------\n");
    }


    //-----[ LAUNCHER

    public static void main(String[] args) {
        new AlgoMDP();
    }

}