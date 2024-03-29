//Lavntaniti Kostantsa 4096
//Patsiou Terzidis Dimitrios Apostolos 4153
//Tsapikouni Georgia 4304

import java.util.ArrayList;
import java.lang.Math;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class dataSet {

    private ArrayList<double[]> data = new ArrayList<double[]>();

    public ArrayList<double[]> generateData(){ // generates random vec2 points in specific ranges
        double rx, ry;
        double x,y;

        for (int i = 0; i < 1200; i++){
            rx = Math.random();
            ry = Math.random();
            double[] temp = new double[2];

            if (i < 150){
                x = (rx * (1.2 - 0.8)) + 0.8;
                y = (ry * (1.2 - 0.8)) + 0.8;
                //System.out.println("1");
            }else if (i >= 150 && i < 300){
                x = (rx * (0.5 - 0.0)) + 0.0;
                y = (ry * (0.5 - 0.0)) + 0.0;
                //System.out.println("2");
            }else if (i >= 300 && i < 450){
                x = (rx * (0.5 - 0.0)) + 0.0;
                y = (ry * (2.0 - 1.5)) + 1.5;
                //System.out.println("3");
            }else if (i >= 450 && i < 600){
                x = (rx * (2.0 - 1.5)) + 1.5;
                y = (ry * (0.5 - 0.0)) + 0.0;
                //System.out.println("4");
            }else if (i >= 600 && i < 750){
                x = (rx * (2.0 - 1.5)) + 1.5;
                y = (ry * (2.0 - 1.5)) + 1.5;
                //System.out.println("5");
            }else if (i >= 750 && i < 825){
                x = (rx * (1.2 - 0.8)) + 0.8;
                y = (ry * (0.4 - 0.0)) + 0.0;
                //System.out.println("6");
            }else if (i >= 825 && i < 900){
                x = (rx * (1.2 - 0.8)) + 0.8;
                y = (ry * (2.0 - 1.6)) + 1.6;
                //System.out.println("7");
            }else if (i >= 900 && i < 975){
                x = (rx * (0.7 - 0.3)) + 0.3;
                y = (ry * (1.2 - 0.8)) + 0.8;
               // System.out.println("8");
            }else if (i >= 975 && i < 1050){
                x = (rx * (1.7 - 1.3)) + 1.3;
                y = (ry * (1.2 - 0.8)) + 0.8;
                //System.out.println("9");
            }else if (i >= 1050 && i < 1200){
                x = (rx * (2.0 - 0.0)) + 0.0;
                y = (ry * (2.0 - 0.0)) + 0.0;
                //System.out.println("10");
            }else{
                x = -1;
                y = -1;
            }
            temp[0] = x;
            temp[1] = y;
            data.add(temp);


        }
        for (int j = 0; j < data.size(); j++){
            System.out.println(data.get(j)[0] + ", " + data.get(j)[1]);
        }

        return data;
    }

    public void saveData(){
        newFile();
        writeData();
    }

    private void newFile(){
        try {
            File myObj = new File("data.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void writeData(){

        try {
            FileWriter myWriter = new FileWriter("data.txt");
            for (int j = 0; j < data.size(); j++){
                //System.out.println(data.get(j)[0] + ", " + data.get(j)[1]);
                myWriter.write(data.get(j)[0] + " " + data.get(j)[1] + " \n");
            }
            myWriter.close();
            System.out.println("Data were Successfully saved.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /*
     public static void main(String[] args) {
        dataSet ds = new dataSet();
        ds.generateData();
        ds.saveData();
    }
*/

}
