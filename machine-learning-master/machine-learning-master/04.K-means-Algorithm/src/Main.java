import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public class Main {
    private static int k;
    private static File input = new File("iris.data");


    static List<InputObject> objects = new ArrayList<>();
    static List<Centroid> centroids = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        readInputFile();
        System.out.println("podaj K: ");
        k = new Scanner(System.in).nextInt();

        for (int i = 0; i < k; i++) {
            centroids.add(new Centroid());
        }

        int iterationsWithoutChange = 0;
        int iterationCounter = 1;
        while (iterationsWithoutChange < 2) {
            System.out.println("starting iteration no. " + iterationCounter++);
            //find closes centroid
            for (InputObject object : objects) {
                Centroid closest = Centroid.getClosestCentroid(object);
                object.group = closest.group;
                closest.objectsInGroup.add(object);
            }

            Centroid.repositionCentroids();

            //clear centroids group elements
            for (Centroid centroid : centroids) {
                centroid.objectsInGroup.clear();
            }

            boolean withoutChange = true;
            for (InputObject object : objects) {
                Centroid newClosest = Centroid.getClosestCentroid(object);
                newClosest.objectsInGroup.add(object);
                if(object.group != newClosest.group) {
                    withoutChange = false;
                    object.group = newClosest.group;
                }
            }

            if(withoutChange)
                iterationsWithoutChange++;
            else
                iterationsWithoutChange = 0;

            for (Centroid centroid : centroids) {
                double sum = 0;
                for (InputObject object : centroid.objectsInGroup) {
                    sum += Math.sqrt(
                            Math.pow((object.a - centroid.a),2) +
                                    Math.pow((object.b - centroid.b),2) +
                                    Math.pow((object.c - centroid.c),2) +
                                    Math.pow((object.d - centroid.d),2));
                }
                System.out.println("śr. odległość grupy " + centroid.group + " to: " + sum);
            }
        }

        System.out.println("==== SKŁAD GRUP ====");
        for (Centroid centroid : centroids) {
            System.out.println(centroid.group);
            centroid.objectsInGroup.forEach(object -> System.out.println(object.type));
            System.out.println("\n");
        }
        System.out.println("finished");




    }

    private static void readInputFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String line = reader.readLine();
        while (line != null){
            String [] data = line.split(",");
            InputObject object = new InputObject(data[0],data[1],data[2],data[3],data[4]);
            objects.add(object);
            line = reader.readLine();
        }
    }
}
