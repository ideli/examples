package _algorithms.chap03.search;

/**
 * 二分查找法
 * Created by autfish on 2016/9/14.
 */
public class BinarySearch {
    private int[] datas;

    public BinarySearch() {
        datas = new int[100];
        for(int i = 1; i <= 100; i++) {
            datas[i - 1] = i;
        }
    }

    public void search(int key) {
        if(key < this.datas[0] || key > this.datas[this.datas.length - 1]) {
            System.out.println("未找到关键字" + key + ", 查找0次");
            return;
        }
        int from = 0;
        int to = this.datas.length - 1;
        int mid;
        int times = 0;
        while(from <= to) { //查找范围内至少包含一个元素
            times++;
            mid = (from + to) / 2; //计算中间位置
            if(this.datas[mid] == key) {
                System.out.println("在位置" + mid + "查找到关键字" + key + ", 查找" + times + "次");
                return;
            } else if(this.datas[mid] > key) { //中间元素大于关键字
                to = mid - 1;
            } else {
                from = mid + 1;
            }
        }
        System.out.println("未找到关键字" + key + ", 查找" + times + "次");
    }

    public static void main(String[] args) {
        BinarySearch bs = new BinarySearch();
        bs.search(1);
        bs.search(5);
        bs.search(33);
        bs.search(50);
        bs.search(75);
        bs.search(101);
    }
}
