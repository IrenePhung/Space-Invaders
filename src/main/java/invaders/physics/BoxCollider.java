package invaders.physics;

public class BoxCollider implements Collider {

    private double width;
    private double height;
    private Vector2D position;

    public BoxCollider(double width, double height, Vector2D position){
        this.height = height;
        this.width = width;
        this.position = position;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public Vector2D getPosition(){
        return this.position;
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
    }
}

