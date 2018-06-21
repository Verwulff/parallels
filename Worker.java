/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iir_thread;

/**
 *
 * @author Verwulf
 */
import java.util.concurrent.Callable;

import static iir_thread.IIR_thread.N;
import static iir_thread.IIR_thread.x;
import static iir_thread.IIR_thread.y;
import static iir_thread.IIR_thread.a;
import static iir_thread.IIR_thread.b;

public class Worker implements Callable<double[]> {

    private double[] in = new double[N];

    public Worker(double[] in) {
        this.in = in;
    }

    @Override
    public double[] call() throws Exception {
        double out[] = new double[N];
        //Фильтрация входных данных
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
        return out;
    }
}
