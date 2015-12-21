
public class Face {

	private Vertex[] vertices;
	private Edge[] edges;
	
	boolean visible;
	
	public Face(){
		vertices = new Vertex[3];
		edges = new Edge[3];
	}
	
	public static Face makeFace(Edge e0, Edge e1, Edge e2){
		Face f = new Face();
		f.edges[0] = e0;
		f.edges[1] = e1;
		f.edges[2] = e2;
		f.visible = false;
		
		add(f);
		
		return f;
	}
	
	public static void add(Face f){
		ConvexHull.faces.add(f);
	}

	public void setVertices(Vertex v0, Vertex v1, Vertex v2) {
		vertices[0] = v0;		
		vertices[1] = v1;
		vertices[2] = v2;
	}

	public Edge getEdge(int i) {
		return edges[i];
	}

	public void setVertex(int i, Vertex v) {
		vertices[i] = v; 
	}
	
	public Vertex getVertex(int i) {
		return vertices[i]; 
	}
}
