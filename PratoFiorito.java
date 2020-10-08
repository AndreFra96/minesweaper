package minesweaper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PratoFiorito {
    private Playground playground;

    public PratoFiorito(int height, int width, int bees) {
        this.playground = new Playground(height, width, bees);
    }

    public void play(InputStream stream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line = "";
            System.out.println(
                    "\n###################################################################################################################################################\n");
            System.out.println("BENVENUTO A PRATOFIORITO!\n");
            System.out.println("Il gioco e' organizzato in righe e colonne con valori compresi fra 0 e n.");
            System.out.println(
                    "Per effettuare il click su una casella inserire il valore della riga e della colonna separati da uno spazio.");
            System.out.println(
                    "Per posizionare/rimuovere un flag su una casella inserire la stringa flag/unflag seguita dal valore della riga e della colonna separati da uno spazio.");
            System.out.println(" '^' : ape");
            System.out.println(" '@' : flag");
            System.out.println(" '.' : contenuto nascosto");
            System.out.println(
                    "\nTODO: rivelare spazi intorno ad uno spazio rivelato, fermare il gioco al game over, defleggare, vincita se tutte le api sono fleggate ");
            System.out.println(
                    "\n###################################################################################################################################################\n");
            System.out.println(playground);
            System.out.println("Inserisci riga e colonna separati da uno spazio:");
            while ((line = reader.readLine()) != null) {
                String[] splitted = line.split(" ");
                if (splitted.length == 2) {
                    try {
                        System.out.println("\nriga: " + Integer.parseInt(splitted[0]));
                        System.out.println("colonna: " + Integer.parseInt(splitted[1]));

                        if (!(playground.click(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])))) {
                            System.out.println(playground);
                            System.out.println("GAME OVER!");
                            break;
                        }
                        System.out.println(playground);

                    } catch (NumberFormatException e) {
                        System.out.println("Input errato, riprovare");
                    }

                } else if (splitted.length == 3) {
                    if (splitted[0].equals("flag")) {
                        try {
                            if (playground.flag(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]))) {
                                playground.revealAll();
                                System.out.println(playground);
                                System.out.println("COMPLIMENTI, HAI VINTO!");
                                break;
                            }
                            System.out.println("flag");
                            System.out.println("riga: " + Integer.parseInt(splitted[1]));
                            System.out.println("colonna: " + Integer.parseInt(splitted[2]));
                            System.out.println(playground);

                        } catch (NumberFormatException e) {
                            System.out.println("Input errato, riprovare");
                        }
                    } else if (splitted[0].equals("unflag")) {
                        try {
                            playground.unflag(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));
                            System.out.println("unflag");
                            System.out.println("riga: " + Integer.parseInt(splitted[1]));
                            System.out.println("colonna: " + Integer.parseInt(splitted[2]));
                            System.out.println(playground);
                        } catch (NumberFormatException e) {
                            System.out.println("Input errato, riprovare");
                        }
                    } else {
                        System.out.println("Input errato, riprovare");
                    }
                } else {
                    System.out.println("Input errato, riprovare");
                }
                System.out.println("Inserisci riga e colonna separati da uno spazio:");
            }
        } catch (IOException e) {
            throw e;
        }
    }

    public void setup(int height, int width, int bees) {
        this.playground = new Playground(height, width, bees);
    }

}
