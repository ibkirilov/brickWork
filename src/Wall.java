import java.util.ArrayList;
import java.util.List;

/**
 * Here is the class where almost everything happens. It represents one layer. Every object contains white bricks, black bricks and valid edges between them.
 */
public class Wall {
    private List<Brick> whiteBricks;
    private List<Brick> blackBricks;
    private List<List<Brick>> adjBricks;

    /**
     * We need empty constructor to create wall in the beginning of the application.
     */
    public Wall() {
        this.whiteBricks = new ArrayList<>();
        this.blackBricks = new ArrayList<>();
        this.adjBricks = new ArrayList<>();
    }

    /**
     * We need constructor which creates wall from valid edges. It fills the white and black bricks lists by coordinates in edges.
     *
     * @param adjBricks
     */
    public Wall(List<List<Brick>> adjBricks) {
        this.adjBricks = adjBricks;
        whiteBricks = new ArrayList<>();
        blackBricks = new ArrayList<>();
        fillBricksLists();
    }

    /**
     * Filling the white and black bricks lists by coordinates in edges.
     */
    private void fillBricksLists() {
        for (int i = 0; i < adjBricks.size(); i++) {
            for (int j = 0; j < adjBricks.get(i).size()  ; j++) {
                if (j == 0) {
                    Brick whiteBrick = new Brick(adjBricks.get(i).get(j));
                    whiteBrick.setNumber(i + 1);
                    whiteBricks.add(whiteBrick);
                } else {
                    Brick blackBrick = new Brick(adjBricks.get(i).get(j));
                    blackBrick.setNumber(i + 1);
                    blackBricks.add(blackBrick);
                }
            }
        }
    }

    public List<Brick> getWhiteBricks() {
        return whiteBricks;
    }

    public List<Brick> getBlackBricks() {
        return blackBricks;
    }

    public List<List<Brick>> getAdjBricks() {
        return adjBricks;
    }

    public void addWhiteBrick(Brick brick) {
        if (!this.whiteBricks.contains(brick)) {
            this.whiteBricks.add(brick);
        }
    }

    public void addBlackBrick(Brick brick) {
        if (!this.blackBricks.contains(brick)) {
            this.blackBricks.add(brick);
        }
    }

    public void addEdge(List<Brick> edge) {
        if (!adjBricks.contains(edge)) {
            this.adjBricks.add(edge);
        }
    }

    /**
     * this method searches for maximum connections possible.
     *
     * @return
     */
    public List<List<Brick>> getMaximumConnections() {

        int whiteBricksSize = this.getWhiteBricks().size();

        List<List<Brick>> usedConnections = new ArrayList<>();  //an array, where we will collect proper connections

        for (int i = 0; i < whiteBricksSize; i++) {
            List<Brick> currentConnection = new ArrayList<>();
            List<Brick> visited = new ArrayList<>();

            //checks if two bricks can connect
            if (checkMatch(this, this.getWhiteBricks().get(i), visited, currentConnection, usedConnections)) {
                currentConnection = new ArrayList<>(List.of(currentConnection.get(0), currentConnection.get(1)));
                usedConnections.add(currentConnection);
            }
        }
        return usedConnections;
    }

    /**
     * Here we find if two bricks are ok to connect. We are searching the maximum bipartite matching of the graph,
     * which is the goal of out application.
     *
     * @param wall
     * @param whiteBrick
     * @param visited
     * @param currentConnection
     * @param usedConnections
     * @return
     */
    private boolean checkMatch(Wall wall, Brick whiteBrick, List<Brick> visited, List<Brick> currentConnection, List<List<Brick>> usedConnections) {
        int blackBricksSize = wall.getBlackBricks().size();

        //checking all black bricks capable to connect to explored white brick
        for (int j = 0; j < blackBricksSize; j++) {
            Brick blackBrick = wall.getBlackBricks().get(j);
            List<Brick> checkBricks = List.of(whiteBrick, blackBrick);

            //checks if bricks in the edge connections list and if white brick already tried connection with black brick
            if (wall.getAdjBricks().contains(checkBricks) && !visited.contains(blackBrick)) {
                visited.add(blackBrick);

                //checks if some of the bricks are already connected with some other brick.
                // it makes a recursive call of the method for the white brick to check if other connection is possible.
                if (!usedConnectionsContainsCheckedBricks(usedConnections, checkBricks) || checkMatch(wall, whiteBrick, visited, currentConnection, usedConnections)) {
                    currentConnection.addAll(checkBricks); //adding the proper connection
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checking if bricks from checked bricks list are already connected with another brick in the used connections list.
     *
     * @param usedConnections
     * @param checkBricks
     * @return
     */
    private boolean usedConnectionsContainsCheckedBricks(List<List<Brick>> usedConnections, List<Brick> checkBricks) {
        for (int i = 0; i < usedConnections.size(); i++) {
            if (usedConnections.get(i).get(0).equals(checkBricks.get(0)) || usedConnections.get(i).get(0).equals(checkBricks.get(1))) {
                return true;
            }
            if (usedConnections.get(i).get(1).equals(checkBricks.get(0)) || usedConnections.get(i).get(1).equals(checkBricks.get(1))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creating valid edges right after the wall is ready.
     *
     * @param firstLayerInput
     */
    public void createValidEdges(List<List<Brick>> firstLayerInput) {
        for (int i = 0; i < firstLayerInput.size(); i++) {
            for (int j = 0; j < firstLayerInput.get(i).size(); j++) {
                Brick exploredBrick = firstLayerInput.get(i).get(j);

                if (this.getWhiteBricks().contains(exploredBrick)) {
                    if (i != firstLayerInput.size() - 1 && !exploredBrick.getNumber().equals(firstLayerInput.get(i + 1).get(j).getNumber())) {
                        this.addEdge(List.of(exploredBrick, firstLayerInput.get(i + 1).get(j)));
                    }

                    if (j != firstLayerInput.get(i).size() - 1 && !exploredBrick.getNumber().equals(firstLayerInput.get(i).get(j + 1).getNumber())) {
                        this.addEdge(List.of(exploredBrick, firstLayerInput.get(i).get(j + 1)));
                    }
                } else {
                    if (i != firstLayerInput.size() - 1 && !exploredBrick.getNumber().equals(firstLayerInput.get(i + 1).get(j).getNumber())) {
                        this.addEdge(List.of(firstLayerInput.get(i + 1).get(j), exploredBrick));
                    }

                    if (j != firstLayerInput.get(i).size() - 1 && !exploredBrick.getNumber().equals(firstLayerInput.get(i).get(j + 1).getNumber())) {
                        this.addEdge(List.of(firstLayerInput.get(i).get(j + 1), exploredBrick));
                    }
                }
            }
        }
    }
}
