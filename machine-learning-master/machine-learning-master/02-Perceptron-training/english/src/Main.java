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
        put(1,"Iris-setosa");
        put(0,"other");
    }};

    //settings
    static  int                     numberOfAttributes  = 0;
    private static final String     trainingSetPath     = "iris_training.txt";
    private static final String     dataSetPath         = "iris_test.txt";
    private static final boolean    manualTrainingConstantSet = false;

    //perceptron settings
    private static double   deviation           = 3;
    private static double   learningFactor      = 0.2;
    private static int      errorMax            = 5;
    private static int      maxIterations       = 10;

    //lists
    private static ArrayList<DataObject> trainingSet   = new ArrayList<>();
    private static ArrayList<DataObject> dataSet       = new ArrayList<>();

    public static void main(String[] args) {
        //get attribute number
        getAttributeNumber();

        //loading data from .csv files
	    loadTrainingSet();
        loadDataSet();

        //set learning factor
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

        System.out.println("\nincorrect: ");
        //analyze data set
        int counter = 0;
        for (DataObject dataObject : dataSet) {
            int calculatedType = perceptron.calculateOutput(dataObject.getAttributes());
            int type = dataObject.getTypeAsInt();

            if(type != calculatedType) {
                System.out.println("correct: " + values.get(type) + "\t" + "perceptron's choice: " + values.get(calculatedType));
                counter++;
            }
        }
        System.out.println();
        System.out.println("poprawnie zaklasyfikowane: " + (dataSet.size() - counter) + '/' + dataSet.size());
        System.out.println("dokładność: " + (100 - counter * 100/dataSet.size()) + '%');

        //manual vector input

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nczy chcesz podać właśny wektor? [y/n]");
            String line = scanner.nextLine();

            if (!line.equals("y")) {
                System.out.println("bye");
                System.exit(0);
            }

            double[] customVector = new double[numberOfAttributes];

            for (int i = 0; i < numberOfAttributes ; i++) {
                System.out.println("podaj atrybut nr " + (i+1) + ": ");
                try{
                    customVector[i] = scanner.nextDouble();
                }
                catch (InputMismatchException e){
                    System.out.println("zła wartość. Ustawiam 0 ...");
                    customVector[i] = 0;
                }
            }

            int result = perceptron.calculateOutput(customVector);
            System.out.println("Wynik to: " + values.get(result));
        }

    }

    private static void getAttributeNumber() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(trainingSetPath));
            String line = reader.readLine();
            line = line.replaceAll("\\s+",";");
            if(line.toCharArray()[0] == ';')
                line = line.substring(1);
            line = line.replaceAll(",",".");
            int count = 0;
            String [] data = line.split(";");
            for (String string : data) {
                try{
                    Double.parseDouble(string);
                    count++;
                }catch (NumberFormatException ignored) {
                }
            }
            numberOfAttributes = count;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadTrainingSet() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(trainingSetPath));
            String line = reader.readLine();
            //iterate over lines
            while (line != null){
                //handle line
                line = line.replaceAll("\\s+",";");
                if(line.toCharArray()[0] == ';')
                    line = line.substring(1);
                line = line.replaceAll(",",".");
                String [] data = line.split(";");

                //create new data object
                double [] attributes = new double[numberOfAttributes];
                for (int i = 0; i < data.length - 1 ; i++) {
                    attributes[i] = Double.parseDouble(data[i]);
                }
                DataObject newObject = new DataObject(attributes,true,data[data.length-1]);
                trainingSet.add(newObject);

                //read next line to proceed with loop
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

                //handle line
                line = line.replaceAll("\\s+",";");
                if(line.toCharArray()[0] == ';')
                    line = line.substring(1);
                line = line.replaceAll(",",".");
                String [] data = line.split(";");

                //create new data object
                double [] attributes = new double[numberOfAttributes];
                for (int i = 0; i < data.length - 1 ; i++) {
                    attributes[i] = Double.parseDouble(data[i]);
                }
                DataObject newObject = new DataObject(attributes,false,data[data.length-1]);
                dataSet.add(newObject);

                //read next line to proceed with loop
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
