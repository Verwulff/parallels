#include <math.h>
#include <omp.h>
#include <ctime>
#include <iostream>

using namespace std;

const double M_PI = 3.141592653589793238462643383279;

void main()
{
	const int sizeIn = 100000;

	long double *in;
	long double *out;

	in = new long double[sizeIn];
	out = new long double[sizeIn];

	for (int i = 0; i < sizeIn; i++) {
		in[i] = rand() % 10;
	}


	const int N = 100000;
	long int *x;
	long int *y;
	long int *a;
	long int *b;
    x = new long int[N]; // here x[i] represents input[n - i]
	y = new long int[N]; // here y[i] represents output[n - i]
    a = new long int[N]; // here x[i] represents input[n - i]
    b = new long int[N]; // here y[i] represents output[n - i]
    for (int i = 0; i < N; i++)
        { 
            x[i] = 0; 
            y[i] = 0;
        }
        
        

        int q = 40;
        
        for (int i = 0; i < N; i++)
        {
            a[i] = rand() * q;
            b[i] = rand() * q;
        }

        

	omp_set_num_threads(4);
	int step = sizeIn / 4;

	
	int i,j;

        int res = 0;// = System.nanoTime();
		unsigned int start = clock();
#pragma omp parallel for schedule(dynamic, step) private(j) 
		for (i = 0; i < sizeIn; i++) {
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
		unsigned int end = clock();
		res = res + end-start;

		cout << "time " << ((float)res) / CLOCKS_PER_SEC << "\n"; // искомое время
	system("pause");

}