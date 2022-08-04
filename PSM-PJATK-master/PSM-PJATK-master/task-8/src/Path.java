public class Path extends Heap {

    @Override
    Node pop() {
        System.out.println("invalid operation on path");
        return null;
    }

    void addEmptyNode(){
        put(new Node(-1,-1,-1));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Node node : this.heap) {
            result.append(node.toString()).append("\n");
        }
        return result.toString();
    }

    String printX(){
        StringBuilder result = new StringBuilder();
        for (Node node : this.heap) {
            if(node.x == -1 && node.y == -1 && node.a == -1) {
                result.append("\n");
                continue;
            }
            result.append(node.x).append("\n");
        }
        return result.toString();
    }

    String printY(){
        StringBuilder result = new StringBuilder();
        for (Node node : this.heap) {
            if(node.x == -1 && node.y == -1 && node.a == -1) {
                result.append("\n");
                continue;
            }
            result.append(node.y).append("\n");
        }
        return result.toString();
    }

    String printA(){
        StringBuilder result = new StringBuilder();
        for (Node node : this.heap) {
            if(node.x == -1 && node.y == -1 && node.a == -1) {
                result.append("\n");
                continue;
            }
            result.append(node.a).append("\n");
        }
        return result.toString();
    }
}
