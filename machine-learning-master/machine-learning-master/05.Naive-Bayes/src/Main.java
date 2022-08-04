
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<TestObject> testObjects = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        testObjects = TestObject.readFile();
        DecisiveClass dc = new DecisiveClass();
        dc.countOccurrences();
        Values v = new Values(dc.getOccurrencesCount());
        v.countOccurrences();

        for (TestObject testObject : testObjects) {
            testObject.classify();
        }

        int success = 0;
        int failure;
        int total = 0;
        for (TestObject testObject : testObjects) {
            total++;
            System.out.println(testObject.trueClass + "\t" + testObject.discoveredClass);
            if(testObject.trueClass.equals(testObject.discoveredClass))
                success++;
        }
        failure = total - success;
        double P,R,F;
        P = (double)((success*100)/(success+failure))/100;
        R = (double)(((success-failure)*100)/(success+failure))/100;
        F = (2*P*R)/(P+R);
        System.out.println("P: " + P);
        System.out.println("R: " + R);
        System.out.println("F: " + (double)((int)(F*100))/100);


        System.out.println("\nsuccess rate:" + (success*100)/(total)+"%");


    }
}
