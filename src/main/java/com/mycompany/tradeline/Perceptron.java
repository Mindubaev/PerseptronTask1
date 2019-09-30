package com.mycompany.tradeline;

public class Perceptron {
	double[] x;
	double y;
	double[] w;
	double[][] pat;
        TradeLine tradeLine;
	public Perceptron(TradeLine tradeLine) {
                this.tradeLine=tradeLine;
		x = new double[2];
		w = new double[x.length];
                pat=tradeLine.getStaticticForNeuroStuding();
		for (int i = 0; i < x.length; i++) {
			w[i] =0;
                        System.out.println("w["+i+"]="+w[i]);
		}
//            w[0]=-tradeLine.b;
//            w[1]=1;
	}

	public void cy() {
		y = 0;
		for (int i = 0; i < x.length; i++) {
			y += x[i] * w[i];
		}
		if ( tradeLine.a<=y)
                {
			y = 1;
                }
		else
			y = 0;
		
	}

	public void study() {
		double gEr = 0;
		int m=0;
		do {
			gEr = 0;
			for (int p = 0; p < Math.round(pat.length*0.5); p++) {
				x = java.util.Arrays.copyOf(pat[p], pat[p].length - 1);
				cy();
				double er = pat[p][2] - y;
                                System.out.println(er);
				gEr += Math.abs(er);
				for (int i = 0; i < x.length; i++) {
					w[i] += 0.01 * er * x[i];
                                        System.out.println("w["+i+"]="+w[i]);
				}
			}
                    System.out.println("GeneralError="+gEr);
		m++;
				} while (m<100000);
		System.out.println("m="+m);
	}

	public void test() {
            int failCounter=0;
		study();
		for (int p = Integer.parseInt(String.valueOf(Math.round(pat.length*0.5))); p < pat.length; p++) {
			x = java.util.Arrays.copyOf(pat[p], pat[p].length - 1);
			cy();
			if (pat[p][2]!=y)
                            failCounter++;
		}
                System.out.println("fails="+failCounter);
                double persent=(double)((Math.round(pat.length*0.5)-failCounter)*100)/Math.round(pat.length*0.5);
                System.out.println("Процент успеха:"+persent+"%");
	}

}
