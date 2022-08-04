import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Values {
    private final static File training = new File("car.data");
    private final HashMap<String, Double> classCount;
    static List<Column> columns = new ArrayList<>();

    Values(HashMap<String, Double> classCount) {
        this.classCount = classCount;
    }

    void countOccurrences() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(training));

        //read first line
        String line = reader.readLine();

        //createColumns
        for (int i = 0; i < line.split(",").length - 1; i++) {
            columns.add(new Column(i,classCount));
        }

        while (line!=null){

            String [] rowData = line.split(",");
            String decClass = rowData[rowData.length-1];

            for (int i = 0; i < rowData.length - 1; i++) {
                columns.get(i).addValue(rowData[i],decClass);
            }

            line = reader.readLine();
        }

        for (Column column : columns) {
            column.countProb();
        }

    }
}
