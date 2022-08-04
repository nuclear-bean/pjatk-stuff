public class Node {
    double x;
    double y;
    int a;

    Node(double x, double y, int a) {
        this.x = x;
        this.y = y;
        this.a = a;
    }

    void rotateRight(){
        a += 25;
    }

    void rotateLeft(){
        a -= 25;
    }

    void moveForward(){
        //
        x += Math.cos(Math.toRadians(a));
        y += Math.sin(Math.toRadians(a));
    }

    @Override
    public String toString() {
        if(x==-1 && y ==-1 && a==-1)
            return "";
        return x + "\t" + y + "\t" + a;
    }
}
