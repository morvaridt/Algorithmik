
public class Face extends Struct{

	Vertex[] vertex;
	Edge[] edge;
	Face next;
	Face prev;
	
	boolean visible;
	
	public Face(){
		vertex = new Vertex[3];
		edge = new Edge[3];
	}
	
	// TODO: same as makeEdge just with edges instead of vertices
	public static Face makeFace(){
		Face f = new Face();
		f.visible = false;
		
		add(f);
		
		return f;
	}
	
	public static void add(Face f){
		ConvexHull.faces.add(f);
	}
}
