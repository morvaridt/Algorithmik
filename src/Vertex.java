
public class Vertex extends Struct{
		
	// coordinates
	int[] v;
	
	Edge duplicte;
	
	boolean onhull;
	boolean mark;
	
	// TODO: really exists?
	int vnum;
	
	Vertex next;
	Vertex prev;

	public Vertex(){
		v = new int[3];
	}
	
	public Vertex makeVertex(){
		Vertex v = new Vertex();

		v.duplicte = null;
		v.onhull = ! ConvexHull.ONHULL;
		v.mark = ! ConvexHull.PROCESSED;
		
		add(v);
		
		return v;
	}
	
	public void add(Vertex v){
		ConvexHull.vertices.add(v);
	}
}
