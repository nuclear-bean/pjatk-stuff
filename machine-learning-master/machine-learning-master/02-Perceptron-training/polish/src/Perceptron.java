import java.util.Arrays;
import java.util.List;
import java.util.Random;

class Perceptron {
    private double [] wageVector = new double[4];
    private double deviation;   //odchylenie

    Perceptron(double deviation) {
        this.deviation = deviation;
        generateWageVector();
    }

    private void generateWageVector() {
        //a method that generates new random wage vector
        Random random = new Random();
        for (int i = 0; i < wageVector.length ; i++) {
            wageVector[i] = 1000;
        }
    }

    void train(List<DataObject> trainingSet, double learningFactor, int errorMax, int maxIterations) {
        int iteration = 0;
        int errorCount = errorMax + 1;

        while (errorCount >= errorMax && iteration++ < maxIterations){

            for (DataObject dataObject : trainingSet) {
                //calculate perceptron's output value
                int y = calculateOutput(dataObject.dataAsVector());
                //set discovered type
                dataObject.setDiscoveredType(Main.values.get(y).toString());

                //DELTA RULE
                applyDeltaRule(dataObject, learningFactor, y);
            }

            //check error count
            errorCount = 0;
            for (DataObject dataObject : trainingSet) {
                if(!dataObject.type.equals(dataObject.getDiscoveredType()))
                    errorCount++;
            }

        }
    }

    private void applyDeltaRule(DataObject dataObject, double learningFactor, int calculatedType) {
        //this method modifies deviation and wages vector according to delta rule and data object argument.
        if(!dataObject.isTraining){
            //check if passed object is a training object
            System.out.println("ten obiekt nie jest obiektem treningowym!");
            return;
        }

        //quit if type was discovered correctly - no changes will be applied anyway
        int expectedType = dataObject.getTypeAsInt();
        if (expectedType == calculatedType)
            return;

        //if types mismatch apply delta rule
        double [] newWageVector = Arrays.copyOf(wageVector,wageVector.length);
        double [] objectVector  = dataObject.dataAsVector();
        double newDeviation = deviation;

        for (int i = 0; i<wageVector.length; i++) {
            newWageVector[i] = wageVector[i] + (learningFactor * (expectedType - calculatedType) * objectVector[i]);
            newDeviation = deviation - (learningFactor * (expectedType - calculatedType));
        }

        //prepare output message
        StringBuilder message = new StringBuilder("zmieniono własności perceptronu: wektor: ");
        for (double aWageVector : wageVector) {
            message.append(round(aWageVector, 2)).append(", ");
        }
        message.append("/ ");
        for (double aNewWageVector : newWageVector) {
            message.append(round(aNewWageVector, 2)).append(", ");
        }
        message.append("\t\t\tdeviation: ").append(round(deviation,3)).append("/").append(round(newDeviation,3)).append(" (stare/nowe)");
        System.out.println(message.toString());


        //update Perceptron's vector and deviation
        wageVector = newWageVector;
        deviation = newDeviation;
    }

    int calculateOutput(double[] inputVector){
        //check if vectors match each other
        if(inputVector.length != wageVector.length){
            System.out.println("Zły wektor");
            return -1;
        }

        int net = 0;

        //calculate vectors' scalar product
        for (int i = 0; i<wageVector.length; i++) {
            net += wageVector[i] * inputVector[i];
        }
        net -= deviation;

        if(net >= 0)
            return 1;
        return 0;           //czy perceptron sie aktywowal
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
