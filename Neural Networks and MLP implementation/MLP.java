// KOSTANTSA LAVNTANITI AM 4096
//GEORGIA TSAPIKOUNI AM 4304
//DIMITRIS APOSTOLOS PATSIOU TERZIDIS AM 4153



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.lang.Math;


public class MLP {


	//this class creates the levels of mlp and computes forward pass , backpropagation and
	//update the weights with gradient()


	int H1num; //the number of the neurones of H1
	int H2num; //the number of the neurones of H2
	int H3num;//the number of the neurones of H3
	String func;//the levels function
	String finalfunc;//the function of last level
	double[] H1bias; //the array of the biases (poloseis) of H1 neurons
	double[] H2bias; //the array of the biases(poloseis) of H2 neurons
	double[] H3bias; //the array of the biases(poloseis) of H3 neuros
	double[] finalbias; //the array of the biases(poloseis) of final level
	Level H1;
	Level H2;
	Level H3;
	Level Final;
	double learningrate;
	double treshold;
	List<double[]> olddelta= new ArrayList<double[]>();

	public MLP( int H1num, int H2num, int H3num, String func, String finalfunc, double learningrate, double treshold  ) {
		this.H1num=H1num;
		this.H2num=H2num;
		this.H3num=H3num;
		this.func=func;
		this.finalfunc=finalfunc;
		this.learningrate = learningrate;
		this.treshold= treshold;

	}

	public double[] computeBias(int Hnum) { //this will compute the random biases of the given level the first time
		double[] bias=new double [Hnum];
		Random rand = new Random();
		for (int i=0; i< Hnum; i++){
			bias[i]=2*rand.nextDouble() + (-1);
		}
		return bias;

	}

	//this does the forward pass
	public double[] forward_pass(int inputnum, double[] input, int k, int flag) {

		if (flag==0) {
			H1bias=computeBias(H1num); //compute the bias of the H1level
			H1= new Level(1, H1num,H1bias, func, input); //create the H1Lvl

			H2bias=computeBias(H2num);//compute the bias of the H2level
			H2= new Level(2, H2num,H2bias,func,input);//create the H2Lvl

			H3bias=computeBias(H3num);//compute the bias of the H3level
			H3= new Level(3, H3num,H3bias,func,input);//create the H3Lvl

			finalbias=computeBias(k);//compute the bias of the Finallevel
			Final= new Level(4,k,finalbias,finalfunc, input); //create the FinalLvl


		}

		//the output of each level is passed as input to the next one
		input=H1.createNeurons(inputnum, flag, input);
		input=H2.createNeurons(H1num, flag, input);
		input=H3.createNeurons(H2num,flag, input);
		double[] output=Final.createNeurons(H3num, flag, input);



		return output; //puput is an array that containsthe output of forward_pass

	}


	public List<double[]>  backprop(double[]  input, int d, double[] right_output, int k, int flag) {
		//first do the forward pass
		double[] output=forward_pass(d, input, k, flag); //first do the forward pass and return the the output of the mlp , each is the u in bachprop for next level
		//then compute the errors and each output of each level is passed as input to the next one
		if (olddelta.size()==4) {
			olddelta.set(0,Final.computeError(right_output,k, output, olddelta) );//here oldelta plays no role it is just passed so that the code can work
			olddelta.set(1,H3.computeError(Final.delta, Final.neuronNum,Final.output, Final.weighted_derivs));
			olddelta.set(2,H2.computeError(H3.delta, H3.neuronNum,H3.output, H3.weighted_derivs));
			olddelta.set(3,H1.computeError(H2.delta, H2.neuronNum,H2.output, H2.weighted_derivs));


		}
		else {
			olddelta.add(Final.computeError(right_output,k, output, olddelta));//here output plays no role it is just passed so that the code can work
			olddelta.add(H3.computeError(Final.delta, Final.neuronNum,Final.output, Final.weighted_derivs));
			olddelta.add(H2.computeError(H3.delta, H3.neuronNum,H3.output, H3.weighted_derivs));
			olddelta.add(H1.computeError(H2.delta, H2.neuronNum,H2.output, H2.weighted_derivs));
		}


		return olddelta; //olddelta is an array tha contains the erros before backprop()

	}


	public List<double[]> gradient( double[]  input, int d, double[] right_output, int k , int flag) {

		List<double[]> newdelta= new ArrayList<double[]>();


		List<double[]> olddelta = backprop(input,d,right_output,k, flag);
		//new delta is an array that contains the new derivatives of th
		newdelta.add(Final.computeGradient( olddelta.get(0)));
	    newdelta.add(H3.computeGradient( olddelta.get(1)));
	    newdelta.add(H2.computeGradient( olddelta.get(2)));
	    newdelta.add(H1.computeGradient( olddelta.get(3)));

		return newdelta; //newdelta is an array that contans the new  variances of the errors

	}

	public void weights_update(List<double[]> newdelta) {
		//here we update al the weights of each level based on
		//wi(t+1)=wi(t)+(learningrate* new error that is computed fro backprop())
		for(int i=0; i<Final.weights.size(); i++) {

			for (int j =0; j<Final.weights.get(i).length; j++) {
				Final.weights.get(i)[j]= Final.weights.get(i)[j] - (learningrate*newdelta.get(0)[i]);

			}
			Final.bias[i]=Final.bias[i]- (learningrate*newdelta.get(0)[i]);
		}



		for(int i=0; i<H3.weights.size(); i++) {
			for (int j =0; j<H3.weights.get(i).length; j++) {
				H3.weights.get(i)[j]= H3.weights.get(i)[j] - (learningrate*newdelta.get(1)[i]);

			}
			H3.bias[i]=H3.bias[i]- (learningrate*newdelta.get(1)[i]);
		}



		for(int i=0; i<H2.weights.size(); i++) {
			for (int j =0; j<H2.weights.get(i).length; j++) {
				H2.weights.get(i)[j]= H2.weights.get(i)[j] - (learningrate*newdelta.get(2)[i]);

			}
			H2.bias[i]=H2.bias[i]- (learningrate*newdelta.get(2)[i]);
		}


		for(int i=0; i<H1.weights.size(); i++) {
			for (int j =0; j<H1.weights.get(i).length; j++) {
				H1.weights.get(i)[j]= H1.weights.get(i)[j] - (learningrate*newdelta.get(3)[i]);

			}
			H1.bias[i]=H1.bias[i]- (learningrate*newdelta.get(3)[i]);
		}


	}






}
