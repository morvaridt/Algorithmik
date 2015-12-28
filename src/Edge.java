
public class Edge {
	
	private Vertex[] endpts; 
	private Face[] adjface;
	
	Face newface;
	
	boolean deleted;
	
	public Edge(){
		endpts = new Vertex[2];
		adjface = new Face[2];
	}
				
	public static Edge makeEdge(Vertex v0, Vertex v1){
		Edge e = new Edge();
		e.endpts = new Vertex[2];
		e.endpts[0] = v0;
		e.endpts[1] = v1;
		
		e.adjface = new Face[2];
	
		e.newface = null;
		e.deleted = true;
		
		add(e);
		
		return e;
	}
	
	public static void add(Edge e){
		ConvexHull.edges.add(e);
	}

	public Vertex getEndpt(int i) {
		return endpts[i];
	}

	public Face getAdjface(int i) {
		return adjface[i];
	}
	
	public void setAdjface(int i, Face f){
		adjface[i] = f;
	}
}
