
public class Vertex extends Struct{
		
	// coordinates
	int[] v;
	
	Edge duplicate;
	
	boolean onhull;
	boolean mark;
	
	// TODO: really used?
	int vnum;

	public Vertex(){
		v = new int[3];
	}
	
	public Vertex makeVertex(){
		Vertex v = new Vertex();

		v.duplicate = null;
		v.onhull = false;
		v.mark = false;
		
		add(v);
		
		return v;
	}
	
	public void add(Vertex v){
		ConvexHull.vertices.add(v);
	}
}
