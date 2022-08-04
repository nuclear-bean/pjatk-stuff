import java.util.ArrayList;
import java.util.List;

class Heap {
    List<Node> heap = new ArrayList<>();

    void put(Node node){
        heap.add(new Node(node.x,node.y,node.a));
    }

    Node pop(){
        return heap.remove(heap.size()-1);
    }
}
