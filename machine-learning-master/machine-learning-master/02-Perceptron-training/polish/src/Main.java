import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("FieldCanBeLocal")
public class Main {
    //TYPE BINARY VALUES
    static HashMap values = new HashMap<Integer,String>(){{
        put(0,"Iris-setosa");
        put(1,"Iris-versicolor");
    }};

    //settings
    private static final String trainingSetPath = "/Users/admin/Desktop/git/machine-learning/02-Perceptron-training/polish/perceptron.data.csv";
    private static final String dataSetPath = "/Users/admin/Desktop/git/machine-learning/02-Perceptron-training/polish/perceptron.test.data.csv";
    private static final boolean manualTrainingConstantSet = false;

    //perceptron settings
    private static double   deviation           = 10;
    private static double   learningFactor      = 0.5;
    private static int      errorMax            = 5;
    private static int      maxIterations       = 100;

    //lists
    private static ArrayList<DataObject> trainingSet   = new ArrayList<>();
    private static ArrayList<DataObject> dataSet       = new ArrayList<>();

    public static void main(String[] args) {
        //loading data from .csv files
	    loadTrainingSet();
        loadDataSet();

        //set training constant
        if(manualTrainingConstantSet){
            System.out.println("input training constant value: ");
            try {
                learningFactor = new Scanner(System.in).nextDouble();
            } catch (InputMismatchException e){
                System.out.println("wrong double entered. Use \',\'");
            }
        }
        System.out.println("learning factor is: " + learningFactor);

        //create Perceptron
        Perceptron perceptron = new Perceptron(deviation);
        
        //train perceptron
        perceptron.train(trainingSet, learningFactor, errorMax, maxIterations);

        System.out.println("\nresults:");
        //analyze data set
        int counter = 0;
        for (DataObject dataObject : dataSet) {
            int calculatedType = perceptron.calculateOutput(dataObject.dataAsVector());
            int type = dataObject.getTypeAsInt();

            if(type != calculatedType) {
                System.out.println(values.get(type) + "\t\t" + values.get(calculatedType));
                counter++;
            }
        }
        System.out.println("dokładność: " + (100 - counter * 100/dataSet.size()) + '%');

        //manual vector input

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nczy chcesz podać właśny wektor? [y/n]");
            String line = scanner.nextLine();

            if (!line.equals("y")) {
                System.out.println("siema");
                System.exit(0);
            }

            double[] customVector = new double[4];
            System.out.println("a: ");
            customVector[0] = scanner.nextDouble();

            System.out.println("b: ");
            customVector[1] = scanner.nextDouble();

            System.out.println("c: ");
            customVector[2] = scanner.nextDouble();

            System.out.println("d: ");
            customVector[3] = scanner.nextDouble();

            int result = perceptron.calculateOutput(customVector);
            System.out.println("Wynik to: " + values.get(result));
        }

    }

    private static void loadTrainingSet() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(trainingSetPath));
            String line = reader.readLine();
            while (line != null){
                String [] data = line.split(",");
                double a = Double.parseDouble(data[0]),b = Double.parseDouble(data[1]),c = Double.parseDouble(data[2]),d = Double.parseDouble(data[3]);
                trainingSet.add(new DataObject(a,b,c,d,true,data[4]));
                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found"+ trainingSetPath);
        } catch (IOException e) {
            System.out.println("an exception was thrown: " + e.getMessage());
        }

        System.out.println("training set read OK");
    }

    private static void loadDataSet() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataSetPath));
            String line = reader.readLine();
            while (line != null){
                String [] data = line.split(",");
                double a = Double.parseDouble(data[0]),b = Double.parseDouble(data[1]),c = Double.parseDouble(data[2]),d = Double.parseDouble(data[3]);
                dataSet.add(new DataObject(a,b,c,d,false,data[4]));
                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found"+ dataSetPath);
        } catch (IOException e) {
            System.out.println("an exception was thrown: " + e.getMessage());
        }
        System.out.println("data set read OK");
    }
}
