package _algorithms.chap03.search;

/**
 * Created by Administrator on 2016/9/6.
 */
public class DfsEightQuees {

    int[] Queens = new int[8];
    int count = 0;

    public void dfs(int column) {
        if(column == 8) { //8个皇后已放置完成
            count++;
            System.out.println("第" + count + "种方法:");
            print();

            return;
        }
        for(int i = 0; i < 8; i++) {
            Queens[column] = i; //在该列的第i行上放置皇后
            if(isValid(column))
                dfs(column + 1);
        }
    }

    private boolean isValid(int column) {
        for(int i = 0; i < column; i++) { //第column列上的皇后与前面column-1个皇后比较
            if(Queens[i] == Queens[column]) //两个皇后在同一行上
                return false;
            if(Math.abs(Queens[i] - Queens[column]) == (column - i)) //两个皇后在同一对角线上
                return false;
        }
        return true;
    }

    private void print() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(Queens[i] == j)
                    System.out.print("* ");
                else
                    System.out.print("_ ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        DfsEightQuees q = new DfsEightQuees();
        q.dfs(0);
        System.out.println("共" + q.count + "摆放方法");
    }
}
