// KOSTANTSA LAVNTANITI AM 4096
//GEORGIA TSAPIKOUNI AM 4304
//DIMITRIS APOSTOLOS PATSIOU TERZIDIS AM 4153



import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
// Import the Scanner class to read text files
import java.util.*;


//this class creates the data

public class loadData {

	ArrayList<Double> C1 = new ArrayList<Double>();
	ArrayList<Double> C2 = new ArrayList<Double>();
	ArrayList<Double> C3 = new ArrayList<Double>();



    public loadData(String filename) {
    	//ArrayList C1 = new ArrayList<>();
        //C2= new ArrayList<>();
        //C3= new ArrayList<>();

        analiseFile(filename);

    }



    public void analiseFile(String filename) {// this method will create three lists C1,C2,C3 and they will contain the points coresponding to C1, C2 or C3


    	try {
    		//read the iven file
            File file= new File(filename);

            Scanner read_file = new Scanner(file);

            double[] dparts= new double[2];
            while (read_file.hasNextLine()) {
                String data = read_file.nextLine();
                String parts[] = (data.split(" "));
                //double[] dparts = Arrays.stream(parts) //dparts a double array that contais the readen x and y axis dparts[0]=xaxis of point dparts[1]=yaxis of point
                 //       .mapToDouble(Double::parseDouble)
                  //      .toArray();
                dparts[0]=Double.valueOf(parts[0]);
                dparts[1]=Double.valueOf(parts[1]);
                //System.out.println("dparts[0] :"+dparts[0].getClass());
               //based on the categorize() results add the readen parts to the right list (C1,C2,C3)
                if (categorize(dparts[0],dparts[1]).equals("C1")){
                	C1.add(dparts[0]);
                	C1.add(dparts[1]);
                }
                else if (categorize(dparts[0],dparts[1]).equals("C2")) {
                	C2.add(dparts[0]);
                	C2.add(dparts[1]);
                }
                else if (categorize(dparts[0],dparts[1]).equals("C3")) {
                	C3.add(dparts[0]);
                	C3.add(dparts[1]);
                }



                }

            /*traindict.put("C1",C1_value);
            traindict.put("C2",C2_value);
            traindict.put("C3",C3_value);
            thisi is only needed for printing the dictionary
            for(int i=0;i<traindict.get("C3").size();i++)
    		{
    			double [] arr = C3_value.get(i);
    			for(int j=0;j<arr.length;j++)
    			{
    				System.out.print(arr[j]+ " ");
    			}
    			System.out.println();
    		}*/


              }catch (FileNotFoundException e) {
               System.out.println("An error occurred.");
               e.printStackTrace();
             }



    }


    public static String categorize(double dparts0, double dparts1) { //this method returns C1, C2 or C3 after calculations
    	//dparts0=xaxis and dparts1=yaxis
    	double a=dparts0-0.5;
        double b=dparts1-0.5;
        double c=dparts0 + 0.5;
        double d=dparts1 + 0.5;

        if ( (Math.pow(a,2) + Math.pow(b, 2))<0.2 && Double.valueOf(dparts1)>0.5 ) {
        	return "C1";


        } else if ( (Math.pow(a,2) + Math.pow(b, 2))<0.2 && Double.valueOf(dparts1)<0.5 ) {
        	return "C2";

        } else if ( (Math.pow(c,2) + Math.pow(d, 2))<0.2 && Double.valueOf(dparts1)>(-0.5) ) {
        	return "C1";


        } else if ( (Math.pow(c,2) + Math.pow(d, 2))<0.2 && Double.valueOf(dparts1)<(-0.5) ) {
        	return "C2";


        } else if ( (Math.pow(a,2) + Math.pow(d, 2))<0.2 && Double.valueOf(dparts1)>(-0.5) ) {
        	return "C1";


        } else if ( (Math.pow(a,2) + Math.pow(d, 2))<0.2 && Double.valueOf(dparts1)<(-0.5) ) {
        	return "C2";


        } else if ( (Math.pow(c,2) + Math.pow(b, 2))<0.2 && Double.valueOf(dparts1)>0.5 ) {
        	return "C1";


        } else if ( (Math.pow(c,2) + Math.pow(b, 2))<0.2 && Double.valueOf(dparts1)>0.5 ) {
        	return "C2";

        } else {
        	return "C3";


        }


    }



}
