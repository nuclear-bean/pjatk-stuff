import java.io.File;

class Language {
    static final char [] latinAlphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','w','v','x','y','z'};
    String name;
    int    [] occurrencesArray = new int[26];
    double [] normalizedVector = new double[26];

    int occurrencesCount = 0;
    File DataDirectory;

    Language(String name) {
        this.name = name;
    }

    void add(char aChar) {
        for (int i = 0; i < latinAlphabet.length; i++) {
            if(aChar == latinAlphabet[i]){
                occurrencesArray[i] ++;
            }
        }
        occurrencesCount++;
    }


    void printOccurrences(){
        for (int i = 0; i < occurrencesArray.length; i++) {
            System.out.println(latinAlphabet[i] + ": " + occurrencesArray[i]);
        }
    }

    void normalizeData() {
        int sum = 0;
        double length;
        for (int i : occurrencesArray) {
            sum += i;
        }
        length = Math.sqrt(sum);
        for (int i = 0; i < occurrencesArray.length; i++) {
            normalizedVector[i] = occurrencesArray[i] / length;
        }
    }
}
