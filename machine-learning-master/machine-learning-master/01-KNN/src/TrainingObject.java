public class TrainingObject {
    static int idCounter = 0;
    int id;
    double a,b,c,d;
    String type;

    TrainingObject(String a, String b, String c, String d, String type) {
        id = idCounter++;
        this.a = Double.valueOf(a);
        this.b = Double.valueOf(b);
        this.c = Double.valueOf(c);
        this.d = Double.valueOf(d);
        this.type = type;
    }

    @Override
    public String toString() {
        return "TrainingObject{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", type='" + type + '\'' +
                ", id=" + id +
                '}';
    }
}
