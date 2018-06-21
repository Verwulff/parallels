/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iir_thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static java.lang.Math.PI;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

/**
 *
 * @author Verwulf
 */
public class IIR_thread {

    public static final int N = 100000; //Длина фильтра
    //private static final int size = 10_000_000;
    private static final int size = 1000; 
    static double x[] = new  double[N]; // here x[i] represents input[n - i]
    static    double y[] = new  double[N]; // here y[i] represents output[n - i]
    static    int a[] = new  int[N]; // here x[i] represents input[n - i]
    static    int b[] = new  int[N]; // here y[i] represents output[n - i]
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        double[] in = generateIn();
        double out[] = new double[size];

       
        for (int i = 0; i < N; i++)
        { 
            x[i] = 0; 
            y[i] = 0;
        }
        
        final Random random = new Random();

        int q = 40;
        
        for (int i = 0; i < N; i++)
        {
            a[i] = random.nextInt() * q;
            b[i] = random.nextInt() * q;
        }

        //Фильтрация входных данных
        
         List<Future<double[]>> workerFutures = new ArrayList<>();
            ExecutorService executor = Executors.newFixedThreadPool(4);

            long start = System.nanoTime();
            int nn = size/4;
            for (int j = 0; j < size; j = j + size / 4) {
                double[] workerIn = new double[nn];
                System.arraycopy(in, j, workerIn, 0, nn);
                Worker worker = new Worker(workerIn);
                Future<double[]> future = executor.submit(worker);
                workerFutures.add(future);
            }

            for (Future<double[]> workerFuture : workerFutures) {
                workerFuture.get();
            }
            System.out.println("time: " + ((System.nanoTime() - start))/1000000000.0);



    }

      private static double[] generateIn() {


        final Random random = new Random();

        List<Double> inList = DoubleStream.generate(random::nextDouble).limit(size).boxed().collect(Collectors.toList());
        double[] in = new double[size];

        for (int i = 0; i < size; i++) {
            in[i] = inList.get(i);
        }
        return in;
    }
}
