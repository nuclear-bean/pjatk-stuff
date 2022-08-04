import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int languageCount = 0;
    static List<Language> languages = new ArrayList<>();
    static List<Perceptron> perceptrons = new ArrayList<>();            //list to keep all perceptrons in one place
    static List<Text> texts = new ArrayList<>();
    static String text = null;

    static List<TestText> testTexts = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        FileHandler.readTrainingData();

        //create Perceptrons
        for (Language language : languages) {
            Perceptron perceptron = new Perceptron(language.name);
            perceptrons.add(perceptron);
        }

        //train Perceptrons
        for (Perceptron perceptron : perceptrons) {
            System.out.println("before training: " + Arrays.toString(perceptron.wageVector));
            perceptron.train();
            System.out.println("after training: " + Arrays.toString(perceptron.wageVector));
        }

        //classify new language
        FileHandler.readTestData();

        System.out.println("\noccurrences of chars in test files");
        for (TestText testText : testTexts) {
            System.out.println(Arrays.toString(testText.occurrences));
        }

        //analyze test texts to determine their language
        for (TestText testText : testTexts) {
            Perceptron strongestPerceptron = null;
            double maxFunctionValue = -1;

            for (Perceptron perceptron : perceptrons) {
                double functionValue = perceptron.calculatePerceptronOutput(testText.occurrences);

                if(functionValue > maxFunctionValue){
                    maxFunctionValue = functionValue;
                    strongestPerceptron = perceptron;
                }
            }
            if(strongestPerceptron == null)
                continue;

            testText.discoveredLanguage = strongestPerceptron.language;
        }

        int errors = 0;
        for (TestText testText : testTexts) {
            System.out.println();
            System.out.println("correct: " + testText.correctLanguage);
            System.out.println("discovered: " + testText.discoveredLanguage);
            if(!testText.discoveredLanguage.equals(testText.correctLanguage)){
             errors++;
            }
        }

        System.out.println("success rate: " + (100-errors*100/testTexts.size()) + "%");

            System.out.println("\n\ndo you want to enter your own text? [y/n]");
            Scanner scanner = new Scanner(System.in);

            if (!scanner.nextLine().equals("y")) {
                return;
            }

            enterText enterText = new enterText();
            SwingUtilities.invokeLater(enterText);

            while (text == null){
                Thread.sleep(100);
            }

            TestText testText = new TestText(text);

        //analyze user text
        Perceptron strongestPerceptron = null;
        double maxFunctionValue = -1;

        for (Perceptron perceptron : perceptrons) {
            double functionValue = perceptron.calculatePerceptronOutput(testText.occurrences);

            if(functionValue > maxFunctionValue){
                maxFunctionValue = functionValue;
                strongestPerceptron = perceptron;
            }
        }

        if(strongestPerceptron == null)
            return;

        testText.discoveredLanguage = strongestPerceptron.language;
        System.out.println(testText.discoveredLanguage);
    }
}
