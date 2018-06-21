/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iir;

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
public class IIR {

    public static final int N = 100000; //Длина фильтра
    //private static final int size = 10_000_000;
    private static final int size = 100000;
    public static double H[] = new double[N]; //Импульсная характеристика фильтра
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        double[] in = generateIn();
        double out[] = new double[size];

        double x[] = new  double[N]; // here x[i] represents input[n - i]
        double y[] = new  double[N]; // here y[i] represents output[n - i]
        int a[] = new  int[N]; // here x[i] represents input[n - i]
        int b[] = new  int[N]; // here y[i] represents output[n - i]
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
        
        long res = 0;// = System.nanoTime();
        long start = System.nanoTime();
        for (int i = 0; i < in.length; i++) {
            out[i] = 0.;
            out[i] += b[0] * in[i];
            
            for (int j = 1; j < N; j++)
            {
              out[i] += b[j] * x[j];
              out[i] -= a[j] * y[j];
            }
            
            for (int j = N-1; j > 1; j--) { x[j] = x[j-1]; }
            for (int j = N-1; j > 1; j--) { y[j] = y[j-1]; }
            x[1] = in[i]; 
            y[1] = out[i]; 
            //System.out.println(out);
        }
        long end = System.nanoTime();
        res = res + end-start;
        
        
        System.out.println("time: " + res/1000000000.0);

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
