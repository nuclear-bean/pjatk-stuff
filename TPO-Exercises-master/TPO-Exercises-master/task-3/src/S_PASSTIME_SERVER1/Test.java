package S_PASSTIME_SERVER1;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String[] args) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        System.out.println( " logged in at " + (new Timestamp(System.currentTimeMillis()).toString().split(" ")[1]));

    }
}
