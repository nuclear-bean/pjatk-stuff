@SuppressWarnings("WeakerAccess")
class Perceptron {
    int maxIterations               = 100000;
    static double learningFactor    = 0.1;
    double deviation                = 10;
    int errorMax                    = 1;

    double [] wageVector    = new double[26];

    String language;

    Perceptron(String language){
        this.language = language;
        fillWageVector();
    }

    private void fillWageVector(){
        for (int i = 0; i < wageVector.length; i++) {
            wageVector[i] = Math.random() * 100;
        }
    }

    private int calculateNet(double [] valuesVector){
        int net = 0;
        for (int i = 0; i < wageVector.length; i++) {
            net += wageVector[i] * valuesVector[i];
        }
        net -= deviation;

        if(net > 0)
            return 1;

        else return 0;
    }

    double calculatePerceptronOutput(double [] occurrences){
        //f(net) is calculated here (for bipolar perceptron function)
        return (2/(1+Math.exp(-calculateNet(occurrences)))) - 1;

    }

    void train() {
        int errorCount = errorMax + 1;
        int iteration = 0;

        while (errorCount >= errorMax && iteration++ < maxIterations) {
            for (Text text : Main.texts) {
                //iterate over texts from training set
                double y = calculatePerceptronOutput(text.occurrences);

                //train perceptron using delta rule
                applyDeltaRule(text,y);
            }

            //update error count
            errorCount = 0;
            for (Text text : Main.texts) {
                int expected;
                if(text.language.equals(this.language))
                    expected = 1;
                else
                    expected = 0;

                if(!(calculateNet(text.occurrences) == expected))
                    errorCount++;
            }
        }
    }

    private void applyDeltaRule(Text text, double y) {
        int d;                                      //declaring expected value

        if(text.language.equals(this.language))
            d = 1;
        else
            d = 0;

        //update wage vector and deviation according to delta rule for bipolar discrete functions
        for (int i = 0; i < wageVector.length; i++) {
            wageVector[i] = wageVector[i] + (0.5 * learningFactor * (d - y) * (1 - Math.pow(y,2)) * text.occurrences[i]);
        }
        deviation = deviation - (0.5 * learningFactor * (d-y) * (1 - Math.pow(y,2)));
    }
}
