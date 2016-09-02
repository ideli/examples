package _algorithms.chap02.math;

/**
 * 百钱买百鸡
 * 公鸡5文钱1只,母鸡3文钱1只,小鸡3只1文钱,要求用100文钱买100只鸡,求公鸡、母鸡和小鸡各应该买多少只?
 * Created by autfish on 2016/9/2.
 */
public class HundredsChicken {

    public static void main(String[] args) {
        HundredsChicken hc = new HundredsChicken();
        hc.calc();
        hc.quickCalc();
    }

    public void calc() {
        int cook, hen, chick;
        for(cook = 0; cook <=20; cook++ ) {
            for(hen = 0; hen <= 33; hen++) {
                chick = 100 - cook - hen;
                if(chick % 3 == 0 && cook * 5 + hen * 3 + chick / 3 == 100) {
                    System.out.println("公鸡*" + cook + " 母鸡*" + hen + " 小鸡*" + chick);
                }
            }
        }
    }

    public void quickCalc() {
        int cook, hen, chick;
        for(int i = 0; i <= 3; i++) {
            cook = i * 4;
            hen = 25 - 7 * i;
            chick = 100 - cook - hen;
            System.out.println("公鸡*" + cook + " 母鸡*" + hen + " 小鸡*" + chick);
        }
    }
}
