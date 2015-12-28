
public class ConvexHull {
		
	static LinkedQueue<Edge> edges;
	static LinkedQueue<Face> faces;
	static LinkedQueue<Vertex> vertices;
	
	public static void main(String[] args){
		setUp();
		Struct.readVertices(args[0]);
		Struct.buildTetrahedron();
		Struct.constructHull();
		print();
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
