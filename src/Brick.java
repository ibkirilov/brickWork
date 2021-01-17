import java.util.Objects;

/**
 * This class is used to represent a half brick. It does not have any logic. It is only for collecting data.
 */
public class Brick {
    private Integer number;
    private Coordinate coordinate;

    public Brick(Integer number, Coordinate coordinate) {
        this.number = number;
        this.coordinate = coordinate;
    }

    public Brick(Brick brick) {
        this.number = brick.number;
        this.coordinate = brick.coordinate;
    }


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    private void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * this method is used to compare the Bricks objects by coordinate.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brick brick = (Brick) o;
        return coordinate.equals(brick.coordinate);
    }

    /**
     * this method is overridden to be able to compare properly the objects.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(coordinate);
    }

    /**
     * toString method for easy printing only the number of a brick
     *
     * @return
     */
    @Override
    public String toString() {
        return this.getNumber().toString();
    }
}
