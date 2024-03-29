// KOSTANTSA LAVNTANITI AM 4096
//GEORGIA TSAPIKOUNI AM 4304
//DIMITRIS APOSTOLOS PATSIOU TERZIDIS AM 4153


//package compInteligence;
import java.util.*;
import java.lang.Math;
import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.io.BufferedWriter;
import java.io.IOException;  // Import the IOException class to handle errors


public class dataset {


    public static void main(String args[]) {
        double C1[][] = new double[8000][2]; // arraylist that contains the C1 points
        double C2[][] = new double[8000][2];// arraylist that contains the C2 points
        double C3[][] = new double[8000][2];// arraylist that contains the C3 points
        double arr[][] = new double[8000][2]; //array that contains the 8000 random points

        try {
            File train = new File("train.txt");
            File test = new File("test.txt");
            if (train.createNewFile()) {
                System.out.println("File created: " + test.getName());
            } else {
                System.out.println("File already exists.");
            }

            if (test.createNewFile()) {
                System.out.println("File created: " + test.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        try {
            BufferedWriter wr_train = new BufferedWriter(new FileWriter("train.txt"));
            BufferedWriter wr_test = new BufferedWriter(new FileWriter("test.txt"));

            Random rand = new Random();

            for (int i = 0; i < 8000; i++) {
                double x = 2*rand.nextDouble() + (-1);
                double y = 2*rand.nextDouble() +(-1);

                String str = Double.toString(x)+" "+  Double.toString(y)+  "\n";
                if (i < 4000) {

                    wr_train.write(str);
                }
                else {
                    wr_test.write(str);
                }


            }

            wr_train.close();
            wr_test.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
