import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class CoordinateGeneration {

	private static int number;
	private static int lowerBound;
	private static int upperBound;
	
	private static Random rnd;
	
	static File f;
	private static BufferedWriter bw;
	
	public static String generateCoordinates(int n, int lb, int ub){
		number = n;
		lowerBound = lb;
		upperBound = ub;
		
		return generate();
	} 
	
	public static String generateCoordinates(){
		number = 10;
		lowerBound = 0;
		upperBound = 101;
		
		return generate();
	} 
	
	private static String generate() {
		rnd = new Random();
		
		f = new File("coordinates_" + System.currentTimeMillis() + ".txt");
		try {
			bw = new BufferedWriter(new FileWriter(f));
			bw.write("# " + number + " " + upperBound + " " + lowerBound);
			
			//TODO: warum??
			upperBound++;
			
			for(int i=0; i < number; i++){
				bw.newLine();
				bw.write("c " + calcCoord() + " " + calcCoord() + " " + calcCoord());
			}
			
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f.getAbsolutePath();		
	}

	private static int calcCoord(){
		int z = (int)(upperBound* rnd.nextDouble());
		if ( z<lowerBound || z > upperBound ) z=1;
		return z;
	}
	
	public static void main(String[] args){
		generateCoordinates(100, 0, 3);
	}
}
