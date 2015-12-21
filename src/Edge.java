
public class Edge extends Struct{
	
	Vertex[] endpts; 
	Face[] adjface;
	
	Face newface;
	
	boolean deleted;
	
	Edge next;
	Edge prev;
	
	public Edge(){
		endpts = new Vertex[2];
		adjface = new Face[2];
	}
			
	
	// TODO: change this method, so the two endpoints are given at the calling
	public static Edge makeEdge(){
		Edge e = new Edge();
		e.endpts = new Vertex[2];
		e.adjface = new Face[2];
	
		e.newface = null;
		e.deleted = true;
		
		add(e);
		
		return e;
	}
	
	public static void add(Edge e){
		ConvexHull.edges.add(e);
	}
}
