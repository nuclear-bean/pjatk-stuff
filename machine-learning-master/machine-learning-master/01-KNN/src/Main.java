import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("unchecked")
public class Main {
    private static final String TRAINING_SET_FILENAME = "iris.test.data";
    private static final String DATA_SET_FILENAME = "iris.data";
    private static int k = 24;
    private static List<TrainingObject> trainingSet = new ArrayList<>();
    private static List<AnalyzedObject> dataSet = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Program start");
        System.out.println("K = " + k);

        //load training set from file
        loadTrainingSet();

        //load actual data set
        loadSetToAnalyze();

        //analyze data
        analyzeData();
    }

    private static void analyzeData() {
        System.out.println("\n\nresults:\n\n");
        for (AnalyzedObject dataObject : dataSet) {
            LinkedHashMap<TrainingObject,Double> distances = new LinkedHashMap<>();
            LinkedHashMap<TrainingObject,Double> decisiveObjects = new LinkedHashMap<>();

            //calculate distances
            for (TrainingObject trainingObject : trainingSet) {
                distances.put(trainingObject,calculateDistance(dataObject,trainingObject));
            }

            //a method to sort distances descending
            distances = sort(distances);

            //get k-number of closes points
            for (int i = 0; i < k; i++) {
                TrainingObject key = (TrainingObject) distances.keySet().toArray()[i];
                decisiveObjects.put(key,distances.get(key));
            }

            //determine object class basing on training set type occurrences
            LinkedHashMap<String, Integer> occurrences = new LinkedHashMap<>();
            decisiveObjects.forEach((trainingObject, aDouble) -> {
                if(!occurrences.containsKey(trainingObject.type)){
                    occurrences.put(trainingObject.type,1);
                }
                else {
                    occurrences.put(trainingObject.type,occurrences.get(trainingObject.type) + 1);
                }
            });

            //set object discovered type to most common decisive class
            dataObject.discoveredType = occurrences.keySet().toArray()[0].toString();

            System.out.println(dataObject.type + " " + dataObject.discoveredType);
        }
    }

    private static LinkedHashMap sort(LinkedHashMap<TrainingObject, Double> distances) {
        LinkedHashMap<TrainingObject,Double> result = new LinkedHashMap<>();

        //sort
        while (!distances.isEmpty()) {
            //get first value from distances
            Double minimalValue = (Double) distances.values().toArray()[0];
            TrainingObject minimalKey = (TrainingObject) distances.keySet().toArray()[0];

            for (TrainingObject key : distances.keySet()) {
                if(distances.get(key) < minimalValue) {
                    minimalValue = distances.get(key);
                    minimalKey = key;
                }
            }

            result.put(minimalKey,minimalValue);
            distances.remove(minimalKey);
        }

        return result;
    }

    private static Double calculateDistance(AnalyzedObject dataObject, TrainingObject trainingObject) {
        Double distance;
        double A = dataObject.a, B = dataObject.b, C = dataObject.c, D = dataObject.d;
        double a = trainingObject.a, b = trainingObject.b, c = trainingObject.c, d = trainingObject.d;

        distance = Math.sqrt(Math.pow(a-A,2) + Math.pow(b-B,2) + Math.pow(c-C,2) + Math.pow(d-D,2));

        return distance;
    }

    private static void loadTrainingSet() {
        System.out.println("loading training set from file: " + TRAINING_SET_FILENAME);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(TRAINING_SET_FILENAME));
            String line = reader.readLine();

            while (line != null){
                String [] data = line.split(",");

                TrainingObject object = new TrainingObject(data[0],data[1],data[2],data[3],data[4]);
                trainingSet.add(object);
                line = reader.readLine();
            }
            System.out.println("training set was read successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadSetToAnalyze() {
        System.out.println("loading data set from file: " + DATA_SET_FILENAME);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(DATA_SET_FILENAME));
            String line = reader.readLine();

            while (line != null){
                String [] data = line.split(",");

                AnalyzedObject object = new AnalyzedObject(data[0],data[1],data[2],data[3],data[4]);
                dataSet.add(object);

                line = reader.readLine();
            }
            System.out.println("data set was read successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
