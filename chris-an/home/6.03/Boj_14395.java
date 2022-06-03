import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Boj_14395 {
    static boolean[] visited = new boolean[1_000_000_001]; // 10의 9승
    static final char[] operators = {'*', '+', '-', '/'};
    static boolean flag;
    static class Node {
        int s;
        String operator;

        public Node(int s, String operator) {
            this.s = s;
            this.operator = operator;
        }
    }
    static String bfs (int s, int t) {
        String result = "-1";
        if (s == t) return "0";

        Queue<Node> qu = new LinkedList<>();
        visited[s] = true;
        qu.offer(new Node(s, ""));

        while (!qu.isEmpty()) {
            Node curNode = qu.poll();

            if (curNode.s == t) return curNode.operator;
            for (int i = 0; i < operators.length; i++) {
                if (flag && operators[i] == '/') continue;
                long operateS = operateNumber(i, curNode.s);
                if (operateS > t) continue;

                int castingS = (int)operateS;
                if (curNode.s == 0) continue;
                if (visited[castingS]) continue;

                visited[castingS] = true;
                qu.offer(new Node(castingS, operatorAdd(curNode.operator, operators[i])));
            }
        }

        return result;
    }

    static String operatorAdd(String operator, char addOperator) {
        StringBuilder sb = new StringBuilder();
        sb.append(operator).append(addOperator);
        return sb.toString();
    }

    static long operateNumber(int i, int s) {
        long castingS = s;
        switch (operators[i]) {
            case '+' :
                castingS = castingS + castingS;
                break;
            case '-' :
                castingS = castingS - castingS;
                break;
            case '*' :
                castingS = castingS * castingS;
                break;
            case '/' :
                castingS = castingS / castingS;
                flag = true;
                break;
        }
        return castingS;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int s = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        System.out.println(bfs(s, t));
    }
}
