import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

@SuppressWarnings({"SameParameterValue", "UnnecessaryContinue"})
public class Main {
    private static final String input = "X";
    private static String current = input;
    private static Node inputNode = new Node(0,0,25);
    private static Node currentNode = inputNode;
    private static Heap heap = new Heap();
    private static Path path = new Path();


    //REPLACEMENT FUNCTIONS
    //X -> F+[[X]-X]-F[-FX]+X
    //F -> FF
    //ALPHABET
    //+ and – rotate (25 degrees)
    //[ - put on heap
    //] – pop from heap
    //F – forward (leave trace)
    //X – assistance symbol

    public static void main(String[] args) {
        generate(5);

        for (char c : current.toCharArray()) {
            switch (c){
                case '+' : currentNode.rotateRight();   break;
                case '-' : currentNode.rotateLeft();    break;
                case '[' : heap.put(currentNode);       break;
                case ']' : currentNode = heap.pop();    break;
                case 'F' : currentNode.moveForward();   break;
                case 'X' : break;
            }
            if(c == ']') {
                path.addEmptyNode();
                path.put(currentNode);
            }
            else if (c == 'X')
                continue;
            else
                path.put(currentNode);
        }

        System.out.println(path.printA());
    }

    private static void generate(int n){
        for (int i = 0; i < n; i++) {
            current = current.replaceAll("X","F+[[X]-X]-F[-FX]+X");
            current = current.replaceAll("F","FF");
        }
    }
}
