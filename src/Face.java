
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
	
	public static Face makeFace(){
		Face f = new Face();
		f.visible = ! ConvexHull.VISIBLE;
		
		add(f);
		
		return f;
	}
	
	public static void add(Face f){
		ConvexHull.faces.add(f);
	}
}
