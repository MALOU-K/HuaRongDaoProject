import AI.BFS;
import AI.Node;
import model.MapModel;

public class AItest {
    public static void main(String[] args) {
        MapModel mapModel = new MapModel(new int[][]{
                {4, 3, 3, 5},
                {4, 3, 3, 5},
                {6, 2, 2, 7},
                {6, 1, 1, 7},
                {1, 0, 0, 1}
        });
        BFS bfs = new BFS();
        Node node = bfs.node(mapModel);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(node.getModel().getId(i, j) + " ");

            }
            System.out.println();
        }
        System.out.println(node.getStep());
        for (int i = 0; i < node.getPath().size(); i++) {
            MapModel m = bfs.hashToModel(node.getPath().get(i));
            printModel(m);
            System.out.println();
        }
    }

    public static void printModel(MapModel mapModel){
        int[][] map = mapModel.getMatrix();
        for (int i = 0; i <map.length ; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

}
