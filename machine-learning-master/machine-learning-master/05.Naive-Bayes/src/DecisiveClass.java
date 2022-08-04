import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

class DecisiveClass {
    static HashMap<String,Double> classProbability = new HashMap<>();
    private HashMap<String,Double> occurrencesCount = new HashMap<>();
    private int rowCount;
    private final static File training = new File("car.data");

    void countOccurrences() throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(training));
        String line = reader.readLine();
        while (line != null){
            String [] data = line.split(",");
            String key = data[data.length-1];

            //add to occurrences and increment sum
            if (!occurrencesCount.containsKey(key)) occurrencesCount.put(key,1.0);
            else occurrencesCount.put(key, occurrencesCount.get(key) + 1.0);
            rowCount += 1;

            //read next line
            line = reader.readLine();

            for (String cls : occurrencesCount.keySet())
                classProbability.put(cls,(occurrencesCount.get(cls)/ rowCount));
        }
        double check = 0;
        System.out.println("CLASS PROBABILITY: ");
        for (String s : occurrencesCount.keySet()) {
            System.out.println(s + "\t" + classProbability.get(s));
            check += classProbability.get(s);
        }
        if(check == 1.0) System.out.println("class probability read OK");
        else System.out.println("class probability read FAIL");
    }

    public HashMap<String, Double> getOccurrencesCount() {
        return occurrencesCount;
    }
}
