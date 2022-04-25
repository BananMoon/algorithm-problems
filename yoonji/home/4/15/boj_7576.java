import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 토마토
public class boj_7576 {
    static int[][] box;
    static int h,w;
    static final int UNRIPE_TOMATO = 0;
    static final int RIPE_TOMATO = 1;
    static Queue<TomatoPosition> que = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        // 1. 초기화
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        w = sToI(st.nextToken());
        h = sToI(st.nextToken());
        box = new int[h][w];
        for (int i=0; i<h; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j=0; j<w; j++) {
                box[i][j] = sToI(st.nextToken());
                // 2. 익은 토마토 찾기
                if (box[i][j] == RIPE_TOMATO)
                    que.offer(new TomatoPosition(i, j));
            }
        }
        // 3. 토마토 익히기 (bfs)
        ripeTomato_byBFS();
        // 4. 안 익은 토마토 체크
        System.out.println(checkZeroAndGetDate()-1);    // 처음에 1일부터 시작했으므로
    }

    private static int checkZeroAndGetDate() {
        int takenDate = Integer.MIN_VALUE;
        Loop1:
        for (int i=0; i<h; i++) {
            for (int j = 0; j < w; j++){
                if (box[i][j] == 0) {
                    takenDate = 0;
                    break Loop1;
                }
                takenDate = Math.max(takenDate, box[i][j]);
            }
        }
        return takenDate;
    }
    private static void ripeTomato_byBFS() {
        int[][] dir = {{-1, 1, 0, 0}, {0, 0, -1, 1}};
        while (!que.isEmpty()) {
            TomatoPosition t = que.poll();
            for (int i=0; i<dir[0].length; i++) {
                int nextX = t.x + dir[0][i];
                int nextY = t.y + dir[1][i];
                if (isInBounds(nextX, nextY) && box[nextX][nextY] == UNRIPE_TOMATO) {
                    que.offer(new TomatoPosition(nextX, nextY));
                    box[nextX][nextY] = box[t.x][t.y] + 1;
                }
            }
        }
    }
    private static boolean isInBounds(int x, int y) {
        return x>=0 && x<h && y>=0 && y<w;
    }
    private static int sToI(String str) {
        return Integer.parseInt(str);
    }
    static class TomatoPosition{
        int x;
        int y;
        public TomatoPosition(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}