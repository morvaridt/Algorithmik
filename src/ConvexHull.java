

public class ConvexHull {
		
	
	static LinkedQueue edges;
	static LinkedQueue faces;
	static LinkedQueue vertices;

	
	public static void main(String[] args){
		setUp();
		Struct.readVertices();
		Struct.buildTetrahedron();
		Struct.constructHull();
		print();
	}
	
	private static void print() {
		// TODO Auto-generated method stub
		
	}

	public static void setUp(){
		edges = new LinkedQueue();
		faces = new LinkedQueue();		
		vertices = new LinkedQueue();
	}

}
