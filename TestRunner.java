package minesweeper;

// import java.io.FileInputStream;
import java.io.IOException;


public class TestRunner {
    public static void main(String[] args){
        int width = 10;
        int height = 10;
        int bees = 10;
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