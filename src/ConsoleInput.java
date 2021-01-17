import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * This class is used to read all the console input and store it in fields, which
 * later can be called from the other classes.
 *
 */
public class ConsoleInput {
    private final int rows;
    private final int cols;
    private final List<String> inputsForWallRows = new ArrayList<>();
    private final BufferedReader bufferedReader;

    /**
     *
     * All the logic in this class is in the constructor, because i want to have the input before everything.
     *
     * @param inputStreamReader
     * @throws IOException
     */
    public ConsoleInput(InputStreamReader inputStreamReader) throws IOException {
        this.bufferedReader = new BufferedReader(inputStreamReader);

        int[] wallDimensions = getWallDimensionsFromConsoleAndSplitBySpace(this.bufferedReader.readLine());
        this.rows = wallDimensions[0];
        this.cols = wallDimensions[1];

        for (int i = 0; i < rows; i++) {
            inputsForWallRows.add(this.bufferedReader.readLine());
        }
    }

    /**
     * Splitting the string by interval, map members to Integer and returning the int array
     *
     * @param s
     * @return
     */
    private int[] getWallDimensionsFromConsoleAndSplitBySpace(String s) {
        return Arrays
                .stream(s.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public List<String> getInputsForWallRows() {
        return inputsForWallRows;
    }
}
