import java.util.LinkedList;
import java.util.Queue;


public class ConvexHull {
	
	//TODO: entfernen
	// final variables used for implementation of incremental algorithm
	final static boolean ONHULL = true;
	final static boolean REMOVED = true;
	final static boolean VISIBLE = true;
	// TODO:  processed = false?? 
	final static boolean PROCESSED = true;	
	
	static Queue<Edge> edges;
	static Queue<Face> faces;
	static Queue<Vertex> vertices;

	
	public static void main(String[] args){
		setUp();
	}
	
	public static void setUp(){
		edges = new LinkedList<>();
		faces = new LinkedList<>();		
		vertices = new LinkedList<>();
	}

}
