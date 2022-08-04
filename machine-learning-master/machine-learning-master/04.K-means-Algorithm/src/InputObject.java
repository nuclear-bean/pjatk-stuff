class InputObject {
    double a,b,c,d;
    String type;
    int group;

    InputObject(String a, String b, String c, String d, String type) {
        this.a = Double.parseDouble(a);
        this.b = Double.parseDouble(b);
        this.c = Double.parseDouble(c);
        this.d = Double.parseDouble(d);
        this.type = type;
    }
}
