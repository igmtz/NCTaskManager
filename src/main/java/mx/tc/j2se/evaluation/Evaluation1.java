package mx.tc.j2se.evaluation;

public class Evaluation1 {
    static int biggestCircle(Circle[] circles){
        int biggest = 0;
        for (int i = 0; i<=circles.length-1; i++){
            if(circles[i].getRadius() > circles[biggest].getRadius()){
                biggest = i;
            }
        }
        return biggest;
    }

    public static void main(String[] args){

        Circle invalidCircle = new Circle(0);

        Circle circle_1 = new Circle(2);
        Circle circle_2 = new Circle(3);
        Circle circle_3 = new Circle(4);

        Circle circles[] = {circle_1, circle_2, circle_3};


        System.out.println(biggestCircle(circles));

        System.out.println("The index of the biggest circle is: "+biggestCircle(circles));
        System.out.println("The biggest circle radius is: "+ circles[biggestCircle(circles)].getRadius());
    }
}
