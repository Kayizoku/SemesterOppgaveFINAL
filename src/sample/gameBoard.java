public class gameBoard {

    private int[][] map;

    public gameBoard(int width, int height) {
        map = new int[width][height];

        for (int i = 0; i < map[0].length; i++) {
            map[i][9] = 1;
        }
        map[2][2] = 1;
        for (int x = 0; x < map.length; x++) {
            System.out.println();
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y] == 1) System.out.print("1");
                else System.out.print("0 ");
            }
        }
    }

}
