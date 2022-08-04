import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class Centroid {
    static int groupCounter = 1;

    double a,b,c,d;
    List<InputObject> objectsInGroup = new ArrayList<>();
    int group;

    Centroid() {
        double bound = 3;
        a = 7;
        b = 5;
        c = 6.5 + (1 * Math.random());
        d = 4;
        group = groupCounter++;
    }

    static Centroid getClosestCentroid(InputObject object){
        double shortestDistance = Double.MAX_VALUE;
        Centroid dominant = null;

        for (Centroid centroid : Main.centroids) {
            double distance;
            //calculate distance
            distance = Math.sqrt(
                    Math.pow((object.a - centroid.a),2) +
                    Math.pow((object.b - centroid.b),2) +
                    Math.pow((object.c - centroid.c),2) +
                    Math.pow((object.d - centroid.d),2));

            if(distance < shortestDistance){
                shortestDistance = distance;
                dominant = centroid;
            }
        }

        return dominant;
    }


    public static void repositionCentroids() {
        for (Centroid centroid : Main.centroids) {
            if(centroid.objectsInGroup.size() < 1){
                continue;
            }

            int count = centroid.objectsInGroup.size();

            double sumA = 0, sumB = 0, sumC = 0, sumD = 0;

            for (InputObject object : centroid.objectsInGroup) {
                sumA += object.a;
                sumB += object.b;
                sumC += object.c;
                sumD += object.d;
            }

            centroid.a = sumA / count;
            centroid.b = sumB / count;
            centroid.c = sumC / count;
            centroid.d = sumD / count;
        }
    }
}
