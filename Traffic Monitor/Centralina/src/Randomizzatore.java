
import java.util.Random;

class Randomizzatore {

	public static long randInt(double a, double b)
	{
		if (b>a) {
			return range(a, b);
		}
		else {
			return range(b, a);
		}

	}


	public static long range(double min, double max) {
		Random rand = new Random();
		int temp= (int) ((max - min) + 1);
		int randomNum = (rand.nextInt(temp) + (int) min);

		return (long) randomNum;
	}

	public static void main (String[] args)
	{
		int t=0;
		while(t<100) {
			float a=(float) 5;
			float b=(float) 300;
			System.out.println(randInt(a,b));
			t++;
		}
	}
}