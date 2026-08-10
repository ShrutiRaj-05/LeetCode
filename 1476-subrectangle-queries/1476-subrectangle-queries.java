import java.util.ArrayList;
import java.util.List;

class SubrectangleQueries {

    // Helper class to store update parameters
    private static class Update {
        int row1, col1, row2, col2, val;

        Update(int row1, int col1, int row2, int col2, int val) {
            this.row1 = row1;
            this.col1 = col1;
            this.row2 = row2;
            this.col2 = col2;
            this.val = val;
        }
    }

    private final int[][] rectangle;
    private final List<Update> updates;

    public SubrectangleQueries(int[][] rectangle) {
        this.rectangle = rectangle;
        this.updates = new ArrayList<>();
    }

    public void updateSubrectangle(int row1, int col1, int row2, int col2, int newValue) {
        // O(1) operation: just log the update
        updates.add(new Update(row1, col1, row2, col2, newValue));
    }

    public int getValue(int row, int col) {
        // Traverse updates in reverse (from newest to oldest)
        for (int i = updates.size() - 1; i >= 0; i--) {
            Update update = updates.get(i);
            if (row >= update.row1 && row <= update.row2 && col >= update.col1 && col <= update.col2) {
                return update.val;
            }
        }

        // Return original value if no update covers this coordinate
        return rectangle[row][col];
    }
}

/**
 * Your SubrectangleQueries object will be instantiated and called as such:
 * SubrectangleQueries obj = new SubrectangleQueries(rectangle);
 * obj.updateSubrectangle(row1,col1,row2,col2,newValue);
 * int param_2 = obj.getValue(row,col);
 */