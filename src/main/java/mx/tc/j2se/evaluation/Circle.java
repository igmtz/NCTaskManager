package mx.tc.j2se.evaluation;

public class Circle {
    private int radius;

    public Circle() {
        this.radius = 1;
    }

    public Circle(int radius) throws IllegalArgumentException{
        if (radius <= 0){
            throw new IllegalArgumentException("The radius must be positive and greater than zero");
        }
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) throws IllegalArgumentException{
        if (radius <= 0){
            throw new IllegalArgumentException("The radius must be positive and greater than zero");
        }
        this.radius = radius;
    }

    public double getArea(Circle circle){
        double area = Math.PI * Math.pow(circle.radius, 2);
        return area;
    }
}
