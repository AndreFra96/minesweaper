package minesweaper;

// import java.io.FileInputStream;
import java.io.IOException;


public class TestRunner {
    public static void main(String[] args){
        int width = 20;
        int height = 20;
        int bees = 20;
        PratoFiorito partita = new PratoFiorito(height, width, bees);
        try{
            // partita.play(new FileInputStream("playgroundInput.txt"));
            partita.play(System.in);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}