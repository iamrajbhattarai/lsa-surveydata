

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LSA {

	ArrayList<Equation> eqList; // seven equation list
	Equation[] nEL;  //normal equations A, B and C
	String[] output;

	public LSA() {
		this.eqList = new ArrayList<Equation>();
		this.nEL = new Equation[3];
		nEL[0] = new Equation();
		nEL[1] = new Equation();
		nEL[2] = new Equation();
		output = new String[3];
	}
/*//for future use
	public LSA(ArrayList<Equation> eqList) {
		this.eqList = eqList;
		this.nEL = new Equation[3];
		nEL[0] = new Equation();
		nEL[1] = new Equation();
		nEL[2] = new Equation();
		output = new String[3];
	}*/

	public void calculate() {
		for (Equation eq : eqList) {

//			Normal equation for A
			if (eq.x > 0) {
				int factor = eq.wt * eq.x;
				nEL[0].x += (factor * eq.x);
				nEL[0].y += (factor * eq.y);
				nEL[0].z += (factor * eq.z);

				nEL[0].deg += (factor * eq.deg);
				nEL[0].min += (factor * eq.min);
				nEL[0].sec += (factor * eq.sec);

				nEL[0].min += ((int) nEL[0].sec / 60);
				nEL[0].sec = nEL[0].sec % 60;

				nEL[0].deg += ((int) nEL[0].min / 60);
				nEL[0].min = nEL[0].min % 60;
			}

//			Normal equation for B
			if (eq.y > 0) {
				int factor = eq.wt * eq.y;
				nEL[1].x += (factor * eq.x);
				nEL[1].y += (factor * eq.y);
				nEL[1].z += (factor * eq.z);

				nEL[1].deg += (factor * eq.deg);
				nEL[1].min += (factor * eq.min);
				nEL[1].sec += (factor * eq.sec);

				nEL[1].min += ((int) nEL[1].sec / 60);
				nEL[1].sec = nEL[1].sec % 60;

				nEL[1].deg += ((int) nEL[1].min / 60);
				nEL[1].min = nEL[1].min % 60;
			}

//			Normal equation for C
			if (eq.z > 0) {
				int factor = eq.wt * eq.z;
				nEL[2].x += (factor * eq.x);
				nEL[2].y += (factor * eq.y);
				nEL[2].z += (factor * eq.z);

				nEL[2].deg += (factor * eq.deg);
				nEL[2].min += (factor * eq.min);
				nEL[2].sec += (factor * eq.sec);

				nEL[2].min += ((int) nEL[2].sec / 60);
				nEL[2].sec = nEL[2].sec % 60;

				nEL[2].deg += ((int) nEL[2].min / 60);
				nEL[2].min = nEL[2].min % 60;
			}
		}
		double[] outputDegree = new double[] { 0, 0, 0 };
		double[][] reqMatrix = { 	{ nEL[0].x, nEL[0].y, nEL[0].z, nEL[0].deg },
									{ nEL[1].x, nEL[1].y, nEL[1].z, nEL[1].deg },
									{ nEL[2].x, nEL[2].y, nEL[2].z, nEL[2].deg } };
		try {
			double[] tmp = Matrix.findSolution(reqMatrix);
			outputDegree[0] += tmp[0];
			outputDegree[1] += tmp[1];
			outputDegree[2] += tmp[2];
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		reqMatrix = new double[][] { { nEL[0].x, nEL[0].y, nEL[0].z, nEL[0].min },
				{ nEL[1].x, nEL[1].y, nEL[1].z, nEL[1].min }, { nEL[2].x, nEL[2].y, nEL[2].z, nEL[2].min } };
		try {
			double[] tmp = Matrix.findSolution(reqMatrix);
			outputDegree[0] += tmp[0] / 60;
			outputDegree[1] += tmp[1] / 60;
			outputDegree[2] += tmp[2] / 60;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		reqMatrix = new double[][] { { nEL[0].x, nEL[0].y, nEL[0].z, nEL[0].sec },
				{ nEL[1].x, nEL[1].y, nEL[1].z, nEL[1].sec }, { nEL[2].x, nEL[2].y, nEL[2].z, nEL[2].sec } };
		try {
			double[] tmp = Matrix.findSolution(reqMatrix);
			outputDegree[0] += tmp[0] / 60 / 60;
			outputDegree[1] += tmp[1] / 60 / 60;
			outputDegree[2] += tmp[2] / 60 / 60;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("x: " + outputDegree[0]);
		System.out.println("y: " + outputDegree[1]);
		System.out.println("z: " + outputDegree[2]);

		System.out.println("Value of x y z in Degree Minute Seconds format");
		DecimalFormat df = new DecimalFormat("##.##");
		for (int i = 0; i < outputDegree.length; i++) {
			int deg = (int) outputDegree[i];
			double mindouble = (outputDegree[i] - (int) outputDegree[i]) * 60;
			int min = (int) mindouble;
			double sec = (mindouble - min) * 60;
			if (i == 0)
				output[i] = ("A: " + df.format(deg) + " deg " + df.format(min) + " min " + df.format(sec) + " sec");
			if (i == 1)
				output[i] = ("B: " + df.format(deg) + " deg " + df.format(min) + " min " + df.format(sec) + " sec");
			if (i == 2)
				output[i] = ("C: " + df.format(deg) + " deg " + df.format(min) + " min " + df.format(sec) + " sec");
		}
	}

}
