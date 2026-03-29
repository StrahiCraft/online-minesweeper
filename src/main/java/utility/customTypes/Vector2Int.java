package utility.customTypes;

import java.io.Serializable;

/**
 * 2D vector data structure that uses integers as coordinates
 */
public class Vector2Int implements Serializable {
    /**
     * The x coordinate of the vector
     */
    private int x;
    /**
     * The y coordinate of the vector
     */
    private int y;

    public Vector2Int() {
        x = 0;
        y = 0;
    }

    public Vector2Int(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The up vector(0, 1)
     * @return The up vector
     */
    public static Vector2Int up() {
        return new Vector2Int(0, 1);
    }
    /**
     * The down vector(0, -1)
     * @return The down vector
     */
    public static Vector2Int down() {
        return new Vector2Int(0, -1);
    }
    /**
     * The left vector(-1, 0)
     * @return The left vector
     */
    public static Vector2Int left() {
        return new Vector2Int(-1, 0);
    }

    /**
     * The right vector(1, 0)
     * @return The right vector
     */
    public static Vector2Int right() {
        return new Vector2Int(1, 0);
    }

    /**
     * The zero vector(0, 0)
     * @return The zero vector
     */
    public static Vector2Int zero(){
        return new Vector2Int(0, 0);
    }

    /**
     * The distance of the vector from zero
     * @return The up vector
     */
    public static int distanceFromZero(Vector2Int point){
        return Math.abs(point.getX()) + Math.abs(point.getY());
    }

    /**
     * Adds two vectors together
     * @param other The other vector that is being added to this one
     * @return The added value of the two vectors
     */
    public Vector2Int add(Vector2Int other) {
        return new Vector2Int(this.x + other.x, this.y + other.y);
    }

    /**
     * Checks if this vector is the same as the other given vector
     * @param other The other vector
     * @return True if they are the same, false otherwise
     */
    public boolean equalValue(Vector2Int other) {
        return  other.x == this.x && other.y == this.y;
    }

    /**
     * Returns the reversed vector of this one
     * @return (-x, -y)
     */
    public Vector2Int reversed(){
        return new Vector2Int(-x, -y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x=" + x +
                ", y=" + y ;
    }
}