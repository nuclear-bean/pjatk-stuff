class AnalyzedObject extends TrainingObject {
    static int idCounter = 0;
    int id;
    String discoveredType = null;

    AnalyzedObject(String a, String b, String c, String d, String type) {
        super(a, b, c, d, type);
        id = idCounter++;
    }

    @Override
    public String toString() {
        return "AnalyzedObject{" +
                "id=" + id +
                ", discoveredType='" + discoveredType + '\'' +
                ", id=" + id +
                ", a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", type='" + type + '\'' +
                '}';
    }
}
