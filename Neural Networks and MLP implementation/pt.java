// KOSTANTSA LAVNTANITI AM 4096
//GEORGIA TSAPIKOUNI AM 4304
//DIMITRIS APOSTOLOS PATSIOU TERZIDIS AM 4153


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
//this is the main class

public class pt {

    private static final int d= 2; // define the number of inputs
    private static final int k = 3;// define the number of outputs
    private static final int H1num = 10; //number of neurals h1
    private static final int H2num = 30;//number of neurals h1
    private static final int H3num = 7;//number of neurals h1
    private static final String func = "tanh"; // logistiki,tanh,relu
    private static final String finalfunc = "S"; //last level func
    private static final double learningrate = 0.0001;// rate of learning
    private static final double treshold = 0.6 ; // the treshold help us to sort the data
    private static int B =400;// mini-batches size


	public static void main(String[] args) throws IOException {


		int flag=0;
		//create objects to read the train and the test data
		loadData loadTrain= new loadData("train.txt");
		loadData loadTest= new loadData("test.txt");

		List<Double> C1_train=loadTrain.C1;
		List<Double> C2_train=loadTrain.C2;
		List<Double> C3_train=loadTrain.C3;

		List<Double> C1_test=loadTest.C1;
		List<Double> C2_test=loadTest.C2;
		List<Double> C3_test=loadTest.C3;

		File train = new File("train.txt");
		BufferedReader br= new BufferedReader(new FileReader(train));


		MLP mlp=new MLP(H1num, H2num, H3num, func,finalfunc,learningrate,treshold);
		int a =0;
		double e_old = 0;

		//compute how many seasons are needed
		int seasons=((C1_train.size()/2)+(C2_train.size()/2)+(C3_train.size()/2))/B;

		String st;
		List<double[]> newdelta= new ArrayList<double[]>();

		//loop through the seasons
		while(a<seasons) {

			double e = 0;

	 		System.out.println("Starting season "+a);
	 		int t = 0;
	 		double season_sum=0.0;

	 		//loop through the values of each seasons
			while(t<B) {
				double[] in_arr=new double[2]; //this will contain the two inputs
				double[] right_arr=new double[3];//this will contain the output as a set of 3 number
				st = br.readLine();
				if(st==null) {
					a=seasons;
					break;
				}

				String[] arr=st.split(" ");
				in_arr[0]=Double.valueOf(arr[0]);
				in_arr[1]=Double.valueOf(arr[1]);

				//C1== 001    C2=010 	C3=100
				if (C1_train.indexOf(in_arr[0])==C1_train.indexOf(in_arr[1])-1) {

					right_arr[0]=0.0;
					right_arr[1]=0.0;
					right_arr[2]=1.0;
				}

				else if (C2_train.indexOf(in_arr[0])==C2_train.indexOf(in_arr[1])-1) {

					right_arr[0]=0.0;
					right_arr[1]=1.0;
					right_arr[2]=0.0;
				}

				else if (C3_train.indexOf(in_arr[0])==C3_train.indexOf(in_arr[1])-1) {

					right_arr[0]=1.0;
					right_arr[1]=0.0;
					right_arr[2]=0.0;
				}


				//call mlp.gradient to start the mlp
				//mlp.gradient will return newdelta
				//newdelta is an array of errors for each output  of each level
				newdelta=mlp.gradient(in_arr,d, right_arr, k, flag);
				double sum =0;
				//iterate though the errors of Final level to compute square error  for this input
				for(int i =0; i < newdelta.get(0).length; i++) {
					sum = sum + (newdelta.get(0)[i])*(newdelta.get(0)[i]);
				}
				sum = sum/2;

				//Season_sum will contain the square errors of each input
				season_sum=season_sum+sum;

				t=t+1;
				flag=1; //this flag becomes 1 onse the first itteration of the first season has finished

				}

			e = e+season_sum;

			double diff =Math.abs(e - e_old);// compute the difference between this season error and the pervious one
			if((a > 700) &&( diff < treshold )) {

				break;
			}

			//call mlp.weights_update to update the weights of mlp
			mlp.weights_update(newdelta);

			System.out.println("Final training error for this season is  "+ season_sum );


			e_old =e;
			a=a+1;
		}
		br.close();
		// Start testing
		System.out.println("Starting testing...");
		File test = new File("test.txt");
		BufferedReader r= new BufferedReader(new FileReader(test));
		double[] in_arr=new double[2];
		double[] right_arr=new double[3];
		String sttest;

		double test_error=0;
		double rights=0;
		//read the file
		while((sttest = r.readLine()) != null) {
			//Start by reading the test file , choosinf each two numbers
			//ande deciding o which category it belongs to.
			String[] arr=sttest.split(" ");

			in_arr[0]=Double.parseDouble(arr[0]);
			in_arr[1]=Double.parseDouble(arr[1]);

			if (C1_test.indexOf(in_arr[0])==C1_test.indexOf(in_arr[1])-1) {

				right_arr[0]=0.0;
				right_arr[1]=0.0;
				right_arr[2]=1.0;
			}

			else if (C2_test.indexOf(in_arr[0])==C2_test.indexOf(in_arr[1])-1) {

				right_arr[0]=0.0;
				right_arr[1]=1.0;
				right_arr[2]=0.0;
			}

			else if (C3_test.indexOf(in_arr[0])==C3_test.indexOf(in_arr[1])-1) {

				right_arr[0]=1.0;
				right_arr[1]=0.0;
				right_arr[2]=0.0;
			}

			double diff=0.0;

			// call mlp.gradient in order to pass all the  data through the mlp
			//that was just trained
			newdelta=mlp.gradient(in_arr,d, right_arr, k, flag);

			//find the number of rights predictions made from mlp
			//using the output of the Final level (mlp.Final.output[i])
			// and the the array with the right numbers (right_arr)
			for(int i=0; i<k; i++) {
				diff=diff+ (mlp.Final.output[i] - right_arr[i]);

			}
			//if the diff is not 0, it means that there were added differences
			//that wore not 0 in the first place

			if (diff==0.0) {

				rights+=1;
			}

			//sum all the deltas of the last (Final) level
			//get that in the power of 2
			//and sum all of that in order
			double testsum=0;
			for(int i =0; i < newdelta.get(0).length; i++) {
				testsum = testsum + (newdelta.get(0)[i])*(newdelta.get(0)[i]);
			}
			testsum = testsum/2;

			test_error=test_error+testsum;


		}
		r.close();



		//Here we compute the average number of the right descisions
		double right_percentage= (rights/4000);
		//double genralization_error= (test_error/4000)*100;
		//System.out.println("The percentage of genralization_error is "+genralization_error+"%" );
		System.out.println("The percentage of right decisions is "+right_percentage +"%" );







	}

}
