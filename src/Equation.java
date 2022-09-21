

public class Equation {
	int x, y, z, wt;
	float deg, min, sec;

	public Equation() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.wt = 0;
		this.deg = 0;
		this.min = 0;
		this.sec = 0;
	}

	public Equation(int x, int y, int z, float deg, float min, float sec, int wt) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.wt = wt;
		this.deg = deg;
		this.min = min;
		this.sec = sec;
	}

	@Override
	public String toString() {
		return String.format("%dA + %dB + %dC = %fdeg %fmin %fsec", x, y, z, deg, min, sec);
	}
}