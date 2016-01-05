
public class ConvexHull {
		
	static LinkedQueue<Edge> edges;
	static LinkedQueue<Face> faces;
	static LinkedQueue<Vertex> vertices;
	
	final static boolean generateNewCoordinates = true;
	
	public static void main(String[] args){
		System.out.println("I started");
		setUp();
		if(generateNewCoordinates){
			Struct.readVertices(CoordinateGeneration.generateCoordinates(4, 0, 2));
		}
		else{
			if(args[0].equals("")){
				Struct.readVertices(CoordinateGeneration.generateCoordinates());
			}
			else{
				Struct.readVertices(args[0]);
			}
		}
		Struct.buildTetrahedron();
		Struct.constructHull();
		print();
		System.out.println("I'm finished");
	}
	
	private static void print() {
		// TODO Auto-generated method stub
		
	}

	public static void setUp(){
		edges = new LinkedQueue<Edge>();
		faces = new LinkedQueue<Face>();		
		vertices = new LinkedQueue<Vertex>();
	}
}
