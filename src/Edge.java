
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
			
	public static Edge makeEdge(){
		Edge e = new Edge();
		e.endpts = new Vertex[2];
		e.adjface = new Face[2];
	
		e.newface = null;
		e.deleted = ! ConvexHull.REMOVED;
		
		add(e);
		
		return e;
	}
	
	public static void add(Edge e){
		ConvexHull.edges.add(e);
	}
}
