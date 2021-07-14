import java.util.Arrays;
import java.util.Scanner;

public class AlgoMDP {

    private final char[] alpha = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            '0','1','2','3','4','5','6','7','8','9','!','?','.',',',';',':','/','\\',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private int[] list;
    private final int max = alpha.length;

    private final String mdp;
    private String foundMdp;

    private int nbTries;

    /**
     * Asks the user for a password and then searches it until it is found.
     * Then, prints the passwordn the number of tries needed to find it and the duration.
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
     * Modifies the list as necessary.
     */
    private void modifyList() {

        if ( list[list.length-1] < max ) {
            list[list.length-1] = list[list.length-1] + 1;

        } else { // we check
            list[list.length-1] = 0;

            boolean found = false;
            int k = 0;

            while ( !found && k >= 0 ) {
                if ( list[k] < max ) {
                    list[k] = list[k]+1;
                    found = true;
                } else {
                    list[k] = 0;
                }
                k--;
            }
        }

    }

    /**
     * Checks if the list has to be reseted, and does it if necessary.
     */
    private void reset() {

        // checks if the list has to be reseted
        boolean reset = true;
        for (int n = 0; n < list.length && reset ; n++ ) {
            if ( list[n] < max-1 ) {
                reset = false;
            }
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
     * Allows to print the content of an array.
     */
    private void printList() {
        for (int j : list) {
            System.out.print(j + " - ");
        }
        System.out.println();
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

    // The main method
    public static void main(String[] args) {
        new AlgoMDP();
    }

}