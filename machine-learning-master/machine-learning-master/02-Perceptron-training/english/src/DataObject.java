class DataObject {
    private double [] attributes;
    boolean isTraining;
    String type;
    private String discoveredType = null;

    DataObject(double[] attributes, boolean isTraining, String type){
        this.attributes = attributes;
        this.isTraining = isTraining;
        if(type.equals("Iris-setosa"))
            this.type = type;
        else
            this.type = "other";
    }

    //a method that returns data in a vector form
    double[] getAttributes(){
        return attributes;
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
