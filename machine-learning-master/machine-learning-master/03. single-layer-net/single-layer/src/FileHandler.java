import java.io.*;

class FileHandler {
    private static final String TRAINING_FOLDER_PATH = "data/lang";
    private static final String TEST_FOLDER_PATH = "data/lang.test";


    private static void countOccurrences(Language language) throws IOException {
        File mainDir = language.DataDirectory;

        for (String filename : mainDir.list()) {
            File file = new File(mainDir + "/" + filename);
            StringBuilder content = new StringBuilder();

            if(file.isFile()){
                //read content from file
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                while (line!=null){
                    content.append(line);
                    line = reader.readLine();
                }

                //get rid of non-english characters
                char [] chars = content.toString().toCharArray();

                for (char aChar : chars) {
                    String s = String.valueOf(aChar).toLowerCase();
                    //check if character is in latin alphabet
                    if(s.matches("[a-z]")){
                        language.add(s.toCharArray()[0]);
                    }
                }

                //normalize input vector
                language.normalizeData();


                Text text = new Text(language);
                Main.texts.add(text);
            }
        }

        //calculate frequencies and print results
        System.out.println("\n\n\n");
        language.printOccurrences();
        System.out.println("total: " + language.occurrencesCount + "\n");
    }

    static void readTrainingData(){
        //count number of languages
        File trainingDirectory = new File(TRAINING_FOLDER_PATH);
        for (String filename : trainingDirectory.list()) {
            File file = new File(trainingDirectory + "/" + filename);
            if(file.isDirectory()){
                Language language = new Language(filename);
                language.DataDirectory = file;
                Main.languages.add(language);
            }
        }

        //print detected languages
        Main.languageCount = Main.languages.size();
        System.out.println("detected languages: " + Main.languageCount);

        for (Language language : Main.languages) {
            System.out.println(language.name);
        }

        //count letters occurrences
        for (Language language : Main.languages) {
            try {
                countOccurrences(language);
            } catch (IOException e) {
                System.out.println("an error occurred");
                e.printStackTrace();
            }
        }
    }

    static void readTestData() throws IOException {
        //count number of languages
        File trainingDirectory = new File(TEST_FOLDER_PATH);
        for (String pathname : trainingDirectory.list()) {
            File file = new File(trainingDirectory + "/" + pathname);
            if(!file.isDirectory())
                continue;
            String languageName = file.getName();

            for (String s : file.list()) {
                File file1 = new File(file.getAbsolutePath() + "/" + s);
                TestText testText = new TestText(file1,languageName);
                testText.countOccurrences();
                Main.testTexts.add(testText);
            }
        }
    }
}
