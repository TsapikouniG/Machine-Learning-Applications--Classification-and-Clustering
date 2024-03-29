// KOSTANTSA LAVNTANITI AM 4096
//GEORGIA TSAPIKOUNI AM 4304
//DIMITRIS APOSTOLOS PATSIOU TERZIDIS AM 4153



import java.lang.Math;
import java.util.*;

//this class computes the results of the function of the neurons, the yi

public class Neuron {

	int level; //the level of the neuron
	int index; //the inedx of the neuron
	//double input; //the inputs of the neuron inn a lists
	String type_func; //the function of this neuron

	public Neuron( int level,int index, String type_func)
	{
		this.level=level;
		this.index=index;
		this.type_func=type_func;


	}

	public double func(double input) { // this func reurns xi of each neuron


		if(type_func.equals("S")) {
			//we have sigmoid , logistic func
			return (1/(1 + Math.exp(input)) ) ;

		}
		else if (type_func.equals("tanh")) {
			return Math.tanh(input) ;

		}
		else if(type_func.equals("relu")) {
			if(input>0) {
				return input;
			}
			else {
				return 0.0;
			}

		}
		else {
			System.out.println("You gave a wrong function");
			return 0.0;
		}



	}

	public double deriv(String func, double output) {// compute the derivate of the u
		double result=0;
		if(func.equals("S")) {
			//we have sigmoid logistic function
			result=output*(1-output);
		}
		else if (func.equals("relu")){
			if(output>1) {
				result=1.0;
			}
			else{
				result=0.0;
			}

		}
		else if(func.equals("tanh")) {
			result=1-Math.pow(Math.tanh(output), 2.0);
		}

		return result;

	}

	public double computediff(double  output, double right_output) {// computes the difference of the result that forward pass gave and the right result
		double diff= 0;
		diff= output- right_output;

		return diff;

	}

}
