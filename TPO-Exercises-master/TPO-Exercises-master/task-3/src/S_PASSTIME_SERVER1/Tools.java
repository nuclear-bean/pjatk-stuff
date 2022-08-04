/**
 *
 *  @author Partyka Jakub S18830
 *
 */

package S_PASSTIME_SERVER1;


import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class Tools {
    static Options createOptionsFromYaml(String fileName) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        FileInputStream in = new FileInputStream(fileName);
        Map<String, Object> clientsMap = yaml.load(in);

        return new Options((String)clientsMap.get("host"),
                (int)clientsMap.get("port"),
                (boolean)clientsMap.get("concurMode"),
                (boolean)clientsMap.get("showSendRes"),
                (Map)clientsMap.get("clientsMap"));
    }
}
