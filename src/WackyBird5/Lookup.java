package WackyBird5;


public class Lookup
{
	public final static double[] sin = calculateSin();
	public final static double[] cos = calculateCos();
	
	
	public static double[] calculateSin()
	{
		double[] sin = new double[360];
		
		for(int i = 0; i < 360; i++)
		
			sin[i] = Math.sin(i * Math.PI/180);
		
		return sin;
	}

	public static double[] calculateCos()
	{
		double[] cos = new double[360];
		
		for(int i = 0; i < 360; i++)
		
			cos[i] = Math.cos(i * Math.PI/180);
		
		return cos;
	}
}