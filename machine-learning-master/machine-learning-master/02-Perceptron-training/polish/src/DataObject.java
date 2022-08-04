class DataObject {
    private double a,b,c,d;
    boolean isTraining;
    String type;
    private String discoveredType = null;

    DataObject(double a, double b, double c, double d, boolean isTraining, String type) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.isTraining = isTraining;
        this.type = type;
    }

    //a method that returns data in a vector form
    double[] dataAsVector(){
        return new double[]{a,b,c,d};
    }

    String getDiscoveredType() {
        if(isTraining){
            return "this is a training object - no discovered type";
        }
        return discoveredType;
    }

    void setDiscoveredType(String discoveredType) {
        if(!isTraining)
            this.discoveredType = discoveredType;
    }

    int getTypeAsInt(){
        for (Object o : Main.values.keySet()) {
            if(Main.values.get(o).equals(type))
                return (int) o;
        }
        return -1;      //in case type not found in values HashMap
    }
}
