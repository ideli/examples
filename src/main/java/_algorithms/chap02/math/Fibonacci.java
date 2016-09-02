package _algorithms.chap02.math;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * 斐波那契数列
 * Created by Administrator on 2016/8/30.
 */
public class Fibonacci extends RecursiveTask<Long> {

    public static void main(String[] args) {
        /*
        long n = 50;
        long begin = System.nanoTime();
        long f = Fibonacci.fib(n);
        long end = System.nanoTime();
        System.out.println("第" + n + "个斐波那契数是" + f + ", 耗时" + TimeUnit.NANOSECONDS.toMillis(end - begin) + "毫秒");
        */
        long n = 50;
        long begin = System.nanoTime();
        Fibonacci fibonacci = new Fibonacci(n);
        ForkJoinPool pool = new ForkJoinPool();
        long f = pool.invoke(fibonacci);
        long end = System.nanoTime();
        System.out.println("第" + n + "个斐波那契数是" + f + ", 耗时" + TimeUnit.NANOSECONDS.toMillis(end - begin) + "毫秒");

        begin = System.nanoTime();
        f = Fibonacci.calcWithoutRecursion(n);
        end = System.nanoTime();
        System.out.println("第" + n + "个斐波那契数是" + f + ", 耗时" + TimeUnit.NANOSECONDS.toMillis(end - begin) + "毫秒");
    }

    long n;
    public Fibonacci(long n) {
        this.n = n;
    }

    public Long compute() {
        if(n <= 10) {  //小于10不再分解
            return Fibonacci.calc(n);
        }
        Fibonacci f1 = new Fibonacci(n - 1);  //分解出计算n-1斐波那契数的子任务
        f1.fork();  //由ForkJoinPool分配线程执行子任务
        Fibonacci f2 = new Fibonacci(n - 2);  //分解出计算n-2斐波那契数的子任务
        return f2.compute() + f1.join();
    }

    public static long calc(long n) {
        if(n < 0)
            return 0;
        if(n == 0 || n == 1) {
            return n;
        } else {
            return calc(n - 1) + calc(n - 2);
        }
    }

    public static long calcWithoutRecursion(long n) {
        if(n < 0)
            return 0;
        if(n == 0 || n == 1) {
            return n;
        }
        long fib = 0;
        long fibOne = 1;
        long fibTwo = 1;
        for(long i = 2; i < n; i++) {
            fib = fibOne + fibTwo;
            fibTwo = fibOne;
            fibOne = fib;
        }
        return fib;
    }
}
