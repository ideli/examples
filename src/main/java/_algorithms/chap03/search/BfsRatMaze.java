package _algorithms.chap03.search;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 老鼠走迷宫 BFS算法
 * Created by autfish on 2016/9/5.
 */
public class BfsRatMaze {

    int min = Integer.MAX_VALUE;
    int endX = 3;  //目标点横坐标
    int endY = 3;  //目标点纵坐标
    int width = 5;  //迷宫宽度
    int height = 4;  //迷宫高度
    int[][] maze;
    int[][] mark;

    public void bfs() {
        int[][] next = new int[][] { //按右->下->左->上的顺序尝试
                {1, 0},
                {0, 1},
                {-1, 0},
                {0, -1}
        };
        int head = 0, tail = 1;
        int startX = 0, startY = 0;
        int nextX = 0, nextY = 0;
        List<Trace> traces = new ArrayList<>();
        traces.add(head, new Trace(startX, startY, -1, 0));
        //Trace[] que = new Trace[2501];
        //que[tail] = new Trace(startX, startY, 0, 0);
        //tail++;
        mark[startX][startY] = 1;
        int flag = 0;
        while(head < tail) {
            for(int k = 0; k <=3; k++) {
                nextX = traces.get(head).getX() + next[k][0];
                nextY = traces.get(head).getY() + next[k][1];
                //System.out.println("cur: " + nextX + "," + nextY);
                if(nextX < 0 || nextX >= width || nextY < 0 || nextY >= height) {  //超出边界
                    continue;
                }
                if(maze[nextX][nextY] == 0 && mark[nextX][nextY] == 0) {
                    this.mark[nextX][nextY] = 1;
                    traces.add(tail, new Trace(nextX, nextY, head, traces.get(head).getStep() + 1));
                    tail++;
                }
                if(nextX == endX && nextY == endY) {
                    flag = 1;
                    break;
                }
            }
            if(flag == 1)
                break;
            head++;
            //System.out.println("head,tail=" + head + "," + tail);
        }
        Trace end = traces.get(tail - 1);
        int father = end.getFather();
        System.out.println("共" + end.getStep() + "步");

        StringBuilder path = new StringBuilder();
        path.insert(0, "->[" + end.getX() + "," + end.getY() + "]");
        while(father >= 0) {
            Trace prev = traces.get(father);
            father = prev.getFather();
            if(father > -1)
                path.insert(0, "->[" + prev.getX() + "," + prev.getY() + "]");
            else
                path.insert(0, "[" + prev.getX() + "," + prev.getY() + "]");
        }
        System.out.println(path.toString());
    }

    public void initMaze() {
        this.maze = new int[width][height];
        this.mark = new int[width][height];

        this.maze[2][0] = 1;
        this.maze[1][2] = 1;
        this.maze[2][2] = 1;
        this.maze[3][2] = 1;
        this.mark[0][0] = 1;

        //打印迷宫 _表示可通行 *表示障碍 !表示目标
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(x == endX && y == endY) {
                    System.out.print("!  ");
                }  else if(this.maze[x][y] == 1) {
                    System.out.print("*  ");
                } else {
                    System.out.print("_  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        BfsRatMaze b = new BfsRatMaze();
        b.initMaze();
        b.bfs();
    }
}

class Trace {

    public Trace(int x, int y, int father, int step) {
        this.x = x;
        this.y = y;
        this.father = father;
        this.step = step;
    }

    private int x;
    private int y;
    private int father;
    private int step;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFather() {
        return father;
    }

    public void setFather(int father) {
        this.father = father;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
