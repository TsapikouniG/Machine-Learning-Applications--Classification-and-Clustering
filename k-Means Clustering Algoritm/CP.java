//Lavntaniti Kostantsa 4096
//Patsiou Terzidis Dimitrios Apostolos 4153
//Tsapikouni Georgia 4304

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.lang.Math;

public class CP {

    private ArrayList<double[]> data = new ArrayList<double[]>();
    private ArrayList<double[]> centroids = new ArrayList<double[]>(); //holds current k centroids
    private ArrayList<ArrayList<double[]>> clusters = new ArrayList<ArrayList<double[]>>();
    dataSet theData = new dataSet();

    public CP(){
        data = theData.generateData();
        theData.saveData();
    }

    public void pickCentroids(int M){
        int z;
        centroids.clear();
        if (clusters.size() == 0){
            newCluster(M);
        }
        ArrayList<Integer> index = new ArrayList<Integer>();
        for (int i = 0; i < M; i++){
            z = (int) Math.round((Math.random()* (data.size() - 1.0)) + 0.0);
            index.add(z);
            centroids.add(data.get(z));

        }
        System.out.println("\n=== " + M + " centroids picked from dataSet with values: \n");
        for (int j = 0; j < centroids.size(); j++){
            System.out.println("index: " + index.get(j) + " -> " + centroids.get(j)[0] + ", " + centroids.get(j)[1]);
        }
    }

    private void newCluster(int n){
        for (int i = 0; i < n; i++){
            clusters.add(new ArrayList<double[]>());
        }
        System.out.println("\n=== Total number of Clusters: " + clusters.size());
    }

    private void emptyClusters(){
        for (int i = 0; i < clusters.size(); i++){
            clusters.get(i).clear();
        }
        System.out.println("\n=== Total number of Clusters: " + clusters.size());
    }

    public void generateCentroids(int M){
        double x, y;

        for (int i = 0; i < M; i ++){
            double[] temp = new double[2];
            x = (Math.random() * (2.0 - 0.0)) + 0.0; //x coordinate
            y = (Math.random() * (2.0 - 0.0)) + 0.0; //y coordinate
            temp[0] = x;
            temp[1] = y;
            centroids.add(temp);
        }
        System.out.println("\n" + M + " centroids initialized with values: \n");
        for (int j = 0; j < centroids.size(); j++){
            System.out.println(centroids.get(j)[0] + ", " + centroids.get(j)[1]);
        }
    }

    public void sortClusters(){
        emptyClusters();
        for (int i = 0; i < data.size(); i++){
            double[] distances = new double[centroids.size()];
            int cluster = -1;
            double minDis = Integer.MAX_VALUE;
            for (int j = 0; j < centroids.size(); j ++){
                double distance = euclideanDistance(centroids.get(j), data.get(i));
                if (distance < minDis){
                    minDis = distance;
                    cluster = j;
                }
            }
            //System.out.println(cluster);
            clusters.get(cluster).add(data.get(i));
        }

        for (int k = 0; k < clusters.size(); k++){
            System.out.println("\n=== Cluster #" + (k+1) + " was sorted with " + clusters.get(k).size() +
                    " elements and center -> " + centroids.get(k)[0] + ", " + centroids.get(k)[1]);
        }
    }

    //calculates the new center of the cluster
    private void kMeans(){


        for (int j =0; j < clusters.size(); j++) {
            double xmean = 0, ymean = 0;
            for (int i = 0; i < clusters.get(j).size(); i++) {
                xmean += clusters.get(j).get(i)[0];
                ymean += clusters.get(j).get(i)[1];
            }
            double[] newCentroid = new double[2];
            newCentroid[0] = xmean / clusters.get(j).size();
            newCentroid[1] = ymean / clusters.get(j).size();
            centroids.set(j, newCentroid);
            System.out.println("\n=== Centroid of cluster #" + (j+1) +" was updated with new value of: \n"
                    + centroids.get(j)[0] + ", " + centroids.get(j)[1]);
        }
    }

    private double euclideanDistance(double[] p, double[] q){
        return Math.sqrt((q[1] - p[1]) * (q[1] - p[1]) + (q[0] - p[0]) * (q[0] - p[0]));
        //return Math.sqrt(Math.pow((p[0] - q[0]),2) + Math.pow((p[1] - q[1]),2));
    }



    //organizes clusters until convergance
    public void clustering(int times, int centroids){
        double minOffset = Integer.MAX_VALUE;
        if (times <1){ times = 1;}
        for(int i = 0; i < times; i++) {
            this.pickCentroids(centroids);

            ArrayList<double[]> temp = new ArrayList<double[]>();//temporarilly holds values of old centroids
            int counter = 0;

            do {
                set(temp);
                this.sortClusters();
                this.kMeans();
                counter++;
            } while (compareCentroids(temp) == false);
           double cO =clusteringOffset();
            if (cO <minOffset){minOffset = cO;}

            System.out.println("\n=== Program finished after " + counter + " iterations.");
        }
        System.out.println("Minimum offset of " + times + " runs was: " + minOffset);
    }

    //calculating the total distance between each point and their associated centroid
    private double clusteringOffset(){
        double totDis = 0;
        for (int i = 0; i < clusters.size(); i++){
            for (int j = 0; j <clusters.get(i).size(); j++){
               totDis += euclideanDistance(clusters.get(i).get(j), centroids.get(i));
            }
        }
        System.out.println("\n=== Total clustering Offset: " + totDis);
        return totDis;
    }


    private ArrayList<double[]> set(ArrayList<double[]> temp){
        temp.clear();
        for (int i = 0; i < centroids.size(); i++){
            temp.add(centroids.get(i));
        }
        return temp;
    }

    private boolean compareCentroids (ArrayList<double[]> temp){
        boolean equals = true;
        for (int i = 0; i < temp.size(); i++){
            double a =Math.abs(temp.get(i)[0] - centroids.get(i)[0]);
            double b =Math.abs(temp.get(i)[1] - centroids.get(i)[1]);

            //System.out.println("\nTEMP: " + temp.get(i)[0] + ", " + temp.get(i)[1]
              //      + "\nCENTROIDS: " + centroids.get(i)[0] + "; " + centroids.get(i)[1]);
            if (a > 0.0001 && b > 0.0001){ //if their offset is less than this they are practically the same number
                equals = false;
            }
        }
        return equals;
    }

    public void loadData(String path){

    }

    public static void main(String[] args) {
        CP test = new CP();
        test.clustering(15, 9);
        /*
        test.pickCentroids(4);
        test.sortClusters();
        test.kMeans();
        */
    }


}
