
public class Vertex {
		
	// coordinates
	private int[] coordinates;
	
	Edge duplicate;
	
	boolean onhull;
	private static boolean mark;
	
	// TODO: really used?
	int vnum;

	public Vertex(){
		coordinates = new int[3];
	}
	
	public static Vertex makeVertex(int c0, int c1, int c2){
		Vertex v = new Vertex();

		v.setCoordinate(0, c0);
		v.setCoordinate(1, c1);
		v.setCoordinate(2, c2);
		
		v.duplicate = null;
		v.onhull = false;
		v.setMark(false);
		
		add(v);
		
		return v;
	}
	
	public static void add(Vertex v){
		ConvexHull.vertices.add(v);
	}
	
	public Vertex next(){
		ConvexHull.vertices.find(this);
		return null;
	}

	public int getCoordinate(int i) {
		return coordinates[i];
	}

	public void setCoordinate(int i, int j) {
		coordinates[i] = j;
	}

	public boolean getMark() {
		return mark;
	}

	public void setMark(boolean mark) {
		Vertex.mark = mark;
	}
}
