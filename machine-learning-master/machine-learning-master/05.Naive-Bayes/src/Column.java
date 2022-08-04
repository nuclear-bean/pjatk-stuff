import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Column {
    int n;
    int d;
    private List<String> values = new ArrayList<>();        //list to count d
    HashMap<String,HashMap<String,Double>> probability = new HashMap<>();

    Column(int n, HashMap<String, Double> classCount){
        this.n = n;
        //init probability inner HashMap
        for (String decClass : classCount.keySet()) {
            probability.put(decClass,new HashMap<>());
        }
    }

    void addValue(String value, String decisiveClass){
        HashMap<String,Double> hm = probability.get(decisiveClass);
        if(!hm.containsKey(value))
            hm.put(value,1.0);
        else
            hm.put(value,hm.get(value) + 1);

        //add this value to values list if absent
        if(!values.contains(value))
            values.add(value);
    }

    void countProb(){
        this.d = values.size();

        for (String outerKey : probability.keySet()) {
            int occurrenceSum = 0;
            HashMap<String,Double> hm = probability.get(outerKey);

            //wygładzanie
            for (String value : values) {
                if(!hm.containsKey(value)){
                    for (String innerKey : hm.keySet()) {
                        hm.replace(innerKey,hm.get(innerKey)+1/(occurrenceSum+d));      //todo ???
                    }
                    hm.put(value,1.0);
                }
            }

            //sum occurrences
            for (Double value : hm.values()) {
                occurrenceSum += value;
            }

            //replace occurrences with probability
            for (String innerKey : hm.keySet()) {
               hm.replace(innerKey,hm.get(innerKey)/occurrenceSum);
            }
        }
    }

    double getProb(String atr, String cls){
        return probability.get(cls).get(atr);
    }

    @Override
    public String toString() {
        String result = "FOR COLUMN NR. " + (n+1) + "\n";
        for (String outerKey : probability.keySet()) {
            result += outerKey + "\n";
            HashMap<String,Double> hm = probability.get(outerKey);
            for (String innerKey : hm.keySet()) {
                result += "\t" + innerKey + "->" + hm.get(innerKey) + "\n";
            }
        }


        return result;
    }
}
