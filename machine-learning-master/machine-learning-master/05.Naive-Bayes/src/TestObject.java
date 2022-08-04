import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class TestObject {
    private final static File test = new File("car.test.data");

    private String [] dataVector;
    String trueClass;
    String discoveredClass;

    TestObject(String[] dataVector, String trueClass) {
        this.dataVector = dataVector;
        this.trueClass = trueClass;
    }

    static List<TestObject> readFile() throws IOException {
        List<TestObject> result = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(test));
        String line = reader.readLine();
        while (line!=null){
            String [] data = line.split(",");
            String decClass = data[data.length - 1];

            TestObject to = new TestObject(Arrays.copyOf(data,data.length-1),decClass);
            result.add(to);

            line = reader.readLine();
        }

        return result;
    }

    void classify(){
        HashMap<String,Double> probabilities = new HashMap<>();
        for (String decClass : DecisiveClass.classProbability.keySet()) {
            probabilities.put(decClass,countProb(decClass,dataVector));
        }

        double max = 0;
        String maxKey = "";
        for (String key : probabilities.keySet()) {
            Double prob = probabilities.get(key);
            if(prob > max) {
                max = prob;
                maxKey = key;
            }
        }
        discoveredClass = maxKey;
    }

    private double countProb(String cls,String [] dataVector){
        double probability = DecisiveClass.classProbability.get(cls);
        for (int i = 0; i < dataVector.length; i++) {
            probability *= Values.columns.get(i).getProb(dataVector[i],cls);
        }
        return probability;
    }
}
