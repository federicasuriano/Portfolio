import java.util.Random;

class Randomizzatore {

	public static double range (double a, double b)
	{
		double diff; 
		double temp;
		if (b>a) {
			diff=b-a;
			temp=a+randInt(a,b)%(diff+1);
		}
		else {
			diff=a-b;
			temp=b+randInt(b,a)%(diff+1);
		}

		return temp;
	}


	public static long randInt(double min, double max) {
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