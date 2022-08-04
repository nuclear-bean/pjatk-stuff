import java.io.*;

@SuppressWarnings("WeakerAccess")
public class TestText {
    File file;
    String correctLanguage;
    String discoveredLanguage;
    double [] occurrences = new double[Language.latinAlphabet.length];

    TestText(File file, String correctLanguage) {
        this.file = file;
        this.correctLanguage = correctLanguage;
    }

    TestText(String content){
        //get rid of non-english characters
        char [] chars = content.toCharArray();

        for (char aChar : chars) {
            String s = String.valueOf(aChar).toLowerCase();
            //check if character is in latin alphabet
            if (s.matches("[a-z]")) {
                for (int i = 0; i < Language.latinAlphabet.length; i++) {
                    if(Language.latinAlphabet[i] == aChar){
                        occurrences[i]++;
                    }
                }
            }
        }
    }

    void countOccurrences() throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();

        while (line != null){
            content.append(line);
            line = reader.readLine();
        }

        //get rid of non-english characters
        char [] chars = content.toString().toCharArray();

        for (char aChar : chars) {
            String s = String.valueOf(aChar).toLowerCase();
            //check if character is in latin alphabet
            if (s.matches("[a-z]")) {
                for (int i = 0; i < Language.latinAlphabet.length; i++) {
                    if(Language.latinAlphabet[i] == aChar){
                        occurrences[i]++;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return file.getAbsolutePath();
    }
}
