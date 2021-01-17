import java.util.Objects;

/**
 * This class represents the coordinate of a Brick. Every brick has coordinate.
 * They can be compared, since the equals and hashcode methods are overridden to work with x and y.
 */
public class Coordinate {
    private Integer x;
    private Integer y;

    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * this method is used to compare the coordinate objects by x and y
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    /**
     * this method is overridden to be able to compare properly the objects.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
