// KOSTANTSA LAVNTANITI AM 4096
//GEORGIA TSAPIKOUNI AM 4304
//DIMITRIS APOSTOLOS PATSIOU TERZIDIS AM 4153




import java.util.*;

public class Level {

	int neuronNum; //the number of neurons in this level
	double[] input; //array of xi for this level
	double[]bias; //aray of bias of the neurons of this level
	List<double[]> weights=new ArrayList<double[]>(); //the weights of each input (wi)
	double[] u;// this contain the input that each neuron recieves
	int level;
	String func; //the func for this level
	Neuron[] NeuronList;
	double[] output;//contains the yi of the neurons of this level
	List<double[]> weighted_derivs=new ArrayList<double[]>();//this contains the weighted derivatives of all nodes of this level
	double[] delta;

	double learningrate;

	public Level(int level, int neuronNum, double[] bias, String func, double[] input)
	{
		this.level=level;
		this.neuronNum=neuronNum;
		this.bias=bias;
		this.func=func;
		this.input=input;
		this.u= new double[neuronNum];
		this.NeuronList=new Neuron[neuronNum];
		this.output=new double[neuronNum];
		delta=new double[neuronNum];
	}

	public double FirstNeuronInput(int PreviousLevelnum, double [] input) { //computes input xi*wi of each neuron only for the first time

		double in=0;
		double[] weight=new double[input.length];
		Random rand = new Random();
		 for( int i =0; i<PreviousLevelnum; i++) { //PreviousLevelnum is the number of neurons in the previous level, they may not be the same with this level and we must add all inputs
			if(level!=0) {
				weight[i]=2*rand.nextDouble() + (-1); //compute random weigh w for each neuron
			}
			else {
				weight[i]=1.0;
			}

			 in=in+input[i] *weight[i];  //sum of wji*xi
		 }
		 weights.add(weight);
		return in;// sum of wi*xi , the bias is added when the object is called
	}

	public double NeuronInput(int PreviousLevelnum, int neuronid, double[] input ) {
		//computes the input of the neuron
		double in=0;
		for(int i=0; i<PreviousLevelnum; i++) {
			in=in+(input[i] * weights.get(neuronid)[i]);
		}
		return in;
	}

	public double[] createNeurons(int PreviousLevelnum, int flag, double[] input) { //this method creates the neurons of the level

		if(flag ==0) { //flag =0 shows that the neurons are not yet created so we need to createthem

			for (int i=0; i<neuronNum; i++) {
				u[i]=FirstNeuronInput(PreviousLevelnum, input)+bias[i];
				Neuron n=new Neuron(level, i,func); //create the neuron
				NeuronList[i]=n; //add the neuron to the list of this level
				output[i]=n.func(u[i]); //func() returns the result of the y of each node
			}
		}

		else {
			for(int i=0; i<neuronNum; i++ ) {
				u[i]=NeuronInput(PreviousLevelnum,i, input)+bias[i];
				output[i]=NeuronList[i].func(u[i]); //func() returns the result of the y of each node
			}
		}

		return output; //array of outputs
	}

	public double weighted_error_sum(int neuron_num,double[] error,int sender_num){//this function computes the sum(wji*dj), sender_num is the number of neurons that the sender level has
		double sum=0;
		for(int i=0; i<sender_num; i++) {
			for(int j=0; j<weights.get(neuron_num).length; j++){
				sum= sum+ error[i]*weights.get(neuron_num)[j];
			}
		}
		return sum;
	}

	//this functio computes the derivatives of the weights and the derivative of the error
	public double[] computeError(double[] prev_error, int sender_num, double[] prev_output, List<double[]> prev_weighted_derivs){
		//double[] Leveldelta = new double[NeuronList.length]; //delta of each level
		double[] olddelta=delta.clone();
		if(level==4) { //final level
			for(int i=0; i<NeuronList.length; i++) {//compute for each neuron of this level derivate(paragogos)*the difference between the actual and the wanted result

				delta[i]=NeuronList[i].deriv(func,output[i])*NeuronList[i].computediff(output[i], prev_error[i]);//only for final level prev_error contains the right output
				double[] arr=new double[prev_output.length];//this will contain the weighted derivs for eaxh node
				for (int j=0; j<prev_output.length; j++) {//multiply the ooutput(the deltas, the erros from each neron) with the outputs of the nodes of the previous level to compute
					arr[j]=delta[i]*prev_output[j];//the derivatives of the weighted connections
				}
				if (weighted_derivs.size()< NeuronList.length) {
					weighted_derivs.add(arr);
				}
				else {
					weighted_derivs.set(i, arr);
				}

			}
		}
		else {
			double sum=0;
			for(int i =0; i< prev_weighted_derivs.size(); i++) {

				for(int j=0; j< prev_weighted_derivs.get(i).length; j++) {

					sum=sum+(prev_weighted_derivs.get(i)[j]*prev_error[i]);
				}
			}

			for(int i=0; i<NeuronList.length; i++) {//compute for each neuron of this level derivate(paragogos)*error passed from the previous level
				//Leveldelta[i]=NeuronList[i].deriv(func,output[i])*NeuronList[i].computediff(output[i], prev_error[i]);
				delta[i]=NeuronList[i].deriv(func,output[i])*sum;
				double[] arr=new double[prev_output.length];//this will contain the weighted derivs for each node
				for (int j=0; j<prev_output.length; j++) {//multiply the ooutput(the deltas, the erros from each neron) with the outputs of the nodes of the previous level to compute
					arr[j]=delta[i]*prev_output[j];//the derivatives of the weighted connections
				}
				if (weighted_derivs.size()< NeuronList.length) {
					weighted_derivs.add(arr);
				}
				else {
					weighted_derivs.set(i, arr);
				}

			}

		}
		return olddelta;
	}

	public double[] computeGradient(double[] olddelta) { //this computes the meriko athroisma
		for(int i=0; i<delta.length; i++) {
			delta[i]=delta[i]+olddelta[i];
		}
		return delta;
	}













}
