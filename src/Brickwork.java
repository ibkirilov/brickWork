import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is used to manage the application. Here we read the input, which we call first layer. The name of the object in the program is Wall,
 * which is a Graph as data structure. Lets imagine that the wall is horizontal, and we split it to black and white fields. Every "brick"
 * has two Bricks. We search the valid edges, after which by finding the maximum bipartite matching of the graph(wall) and collecting
 * the edges to a collection. Now we know the coordinates for valid placements of second layer or second wall.
 */

public class Brickwork {
    private ConsoleInput consoleInput;

    public Brickwork(ConsoleInput consoleInput) {
        this.consoleInput = consoleInput;
    }

    /**
     * This method is used to start the Brickwork and delegate processes to other object's methods.
     */

    public void start() {

        /**
         * Reading the input from ConsoleInput class
         */
        int rows = this.consoleInput.getRows();
        int cols = this.consoleInput.getCols();

        /**
         * Checking if rows and cols are even
         */
        if (rows % 2 != 0 || cols % 2 != 0) {
            System.out.println("Invalid wall dimensions! Start again the application and try again!");
            return;
        }

        List<List<Brick>> firstLayerInput = new ArrayList<>();
        Wall wall = new Wall();

        /**
         * Reading from ConsoleInput the bricks table, creating bricks and add them to the Wall
         */
        for (int i = 0; i < rows; i++) {
            List<Integer> row = Arrays.stream(this.consoleInput
                    .getInputsForWallRows()
                    .get(i)
                    .split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            firstLayerInput.add(new ArrayList<>());

            for (int j = 0; j < cols; j++) {
                Brick brick = new Brick(row.get(j), new Coordinate(i, j));
                firstLayerInput.get(i).add(brick);
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        wall.addWhiteBrick(brick);
                    } else {
                        wall.addBlackBrick(brick);
                    }
                } else {
                    if (j % 2 == 0) {
                        wall.addBlackBrick(brick);
                    } else {
                        wall.addWhiteBrick(brick);
                    }
                }
            }
        }

        /**
         * Creating valid edges
         */
        wall.createValidEdges(firstLayerInput);

        /**
         * Here we calculate and collect the maximum connections possible at given first layer
         */
        List<List<Brick>> adjacency = wall.getMaximumConnections();

        /**
         * If maximum connections possible are less than whole bricks count, it means it is impossible to create second layer
         */
        if (adjacency.size() < wall.getWhiteBricks().size()) {
            System.out.println("-1");
        }

        /**
         * creating new wall for the second layer and print it to the console.
         */
        Wall secondLayer = new Wall(adjacency);

        Brick[][] layerOutputArray = new Brick[rows][cols];
        for (int i = 0; i < secondLayer.getWhiteBricks().size(); i++) {
            int whiteRow = secondLayer.getWhiteBricks().get(i).getCoordinate().getX();
            int whiteCol = secondLayer.getWhiteBricks().get(i).getCoordinate().getY();

            layerOutputArray[whiteRow][whiteCol] = secondLayer.getWhiteBricks().get(i);

            int blackRow = secondLayer.getBlackBricks().get(i).getCoordinate().getX();
            int blackCol = secondLayer.getBlackBricks().get(i).getCoordinate().getY();

            layerOutputArray[blackRow][blackCol] = secondLayer.getBlackBricks().get(i);
        }

        List<List<Brick>> layerOutput = new ArrayList<>();
        for (int i = 0; i < layerOutputArray.length; i++) {
            List<Brick> record = Arrays.asList(layerOutputArray[i]);
            layerOutput.add(record);
        }

        secondLayer.createValidEdges(layerOutput);

        for (int i = 0; i < layerOutput.size(); i++) {
            for (int j = 0; j < layerOutput.get(i).size(); j++) {
                System.out.printf(layerOutput.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
}
