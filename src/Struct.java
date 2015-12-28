import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Struct {
	
	public static Face makeStructs(Edge e, Vertex p){
		Face new_face = new Face();
		Edge[] new_edge = new Edge[2];	
		
		for(int i = 0; i < 2; i++){
			if(! (e.getEndpt(i).duplicate == null)){
				new_edge[i] = Edge.makeEdge(e.getEndpt(i), p);
				e.getEndpt(i).duplicate = new_edge[i];
			}
		}
		
		new_face = Face.makeFace(e, new_edge[0], new_edge[1]);
		new_face = makeCcw(new_face, e, p);
		
		for(int i = 0; i < 2; ++i){
			for(int j = 0; j < 3; ++j){
				if(! (new_edge[i].getAdjface(j) == null)){
					new_edge[i].setAdjface(j, new_face);
					break;
				}
			}
		}
		
		return new_face;
	}
	
	public static Face makeCcw(Face f, Edge e, Vertex p){
		int i;
		Face invisibleFace;
		
		if( e.getAdjface(1) == null ){
			invisibleFace = e.getAdjface(0);
		}
		else{
			if(! e.getAdjface(0).visible){
				invisibleFace = e.getAdjface(0);
			}
			else{
				invisibleFace = e.getAdjface(1);
			}
		}
		
		for(i = 0; invisibleFace.getVertex(i) != e.getEndpt(1); i++){}
		
		if(invisibleFace.getVertex((i + 1) % 3) != e.getEndpt(0)){
			f.setVertex(0, e.getEndpt(1));
			f.setVertex(1, e.getEndpt(0));
		}
		else{
			f.setVertex(0, e.getEndpt(0));
			f.setVertex(1, e.getEndpt(1));
			
			swap(f.getEdge(1), f.getEdge(2));
		}
		
		f.setVertex(2, p);
		return f;
	}
	
	public static boolean addOne(Vertex p){
		boolean visible = false;
		
		// mark from p visible faces
		QueueElement<Face> wf = ConvexHull.faces.getFirst();
		while(wf.getNext() != null){			
			Face f = wf.getElem();
			if(volume(f, p) < 0){
				f.visible = true;
				visible = true;
			}
			wf = wf.getNext();
		}
		
		// no faces visible, p is inside of hull
		if(! visible){
			p.onhull = false;
			return false;
		}
		
		QueueElement<Edge> we = ConvexHull.edges.getFirst();
		while(we.getNext() != null){
			Edge e = we.getElem();
			if(e.getAdjface(0).visible && e.getAdjface(1).visible){
				e.deleted = true;
			}
			else{
				if(e.getAdjface(0).visible || e.getAdjface(1).visible){
					e.newface = makeStructs(e, p);
				}
			}
			we = we.getNext();
		}
		
		return true;
	}

	private static int volume(Face f, Vertex p) {
		double ax = f.getVertex(0).getCoordinate(0) - p.getCoordinate(0);
		double ay = f.getVertex(0).getCoordinate(1) - p.getCoordinate(1);
		double az = f.getVertex(0).getCoordinate(2) - p.getCoordinate(2);
		double bx = f.getVertex(1).getCoordinate(0) - p.getCoordinate(0);
		double by = f.getVertex(1).getCoordinate(1) - p.getCoordinate(1);
		double bz = f.getVertex(1).getCoordinate(2) - p.getCoordinate(2);		
		double cx = f.getVertex(2).getCoordinate(0) - p.getCoordinate(0);
		double cy = f.getVertex(2).getCoordinate(1) - p.getCoordinate(1);
		double cz = f.getVertex(2).getCoordinate(2) - p.getCoordinate(2);
		
		double volume = ax * (by * cz - bz * cy) + ay * (bz * cx - bx * cz) + az * (bx * cy - by * cx);
		
		if(volume > 0.5){
			return 1;
		}
		else if(volume < -0.5){
			return -1;
		}
		else{
			return 0;
		}
	}

	public static void buildTetrahedron(){
		QueueElement<Vertex> v0;
		QueueElement<Vertex> v3;
		
		int volume;
		
		// find 3 noncollinear points
		v0 = ConvexHull.vertices.getFirst();
		while(collinear(v0.getElem(), v0.getNext().getElem(), v0.getNext().getNext().getElem())){
			v0 = v0.getNext();
			if(v0.getNext() == ConvexHull.vertices.getFirst()){
				System.err.println("All points are collinear");
				System.exit(0);
			}
		}
		
		// mark vertices as processed
		v0.getElem().setMark(true);
		v0.getNext().getElem().setMark(true);
		v0.getNext().getNext().getElem().setMark(true);
		
		Edge e0 = Edge.makeEdge(v0.getElem(), v0.getNext().getElem());
		Edge e1 = Edge.makeEdge(v0.getNext().getElem(), v0.getNext().getNext().getElem());
		Edge e2 = Edge.makeEdge(v0.getNext().getNext().getElem(), v0.getElem());	
		
		Face f = Face.makeFace(e0, e1, e2);
		f.setVertices(v0.getElem(), v0.getNext().getElem(), v0.getNext().getNext().getElem());
		
		e0.setAdjface(0, f);
		e1.setAdjface(0, f);
		e1.setAdjface(0, f);
	
		// try to find another noncollinear vertex
		v3 = v0.getNext().getNext().getNext();
		volume = volume(f, v3.getElem());
		
		while(volume != 1){
			v3 = v3.getNext();
			if(v3 == ConvexHull.vertices.getFirst()){
				System.err.println("All points are coplanar");
				System.exit(0);
			}
			volume = volume(f, v3.getElem());
		}
		v3.getElem().setMark(true);
		
		// store vertices in ccw order
		if(volume < 0){
			swap(f.getVertex(1), f.getVertex(2));
			swap(f.getEdge(1), f.getEdge(2));
		}
		
		e0.setAdjface(1, makeStructs(e0, v3.getElem()));
		e1.setAdjface(1, makeStructs(e1, v3.getElem()));
		e2.setAdjface(1, makeStructs(e2, v3.getElem()));
		
		Cleaning.cleanUp();
	}
	
	public static <T> void swap(T a, T b){
		T tmp = a;
		a = b;
		b = tmp;
	}

	private static boolean collinear(Vertex v0, Vertex v1, Vertex v2) {
		
		return 	(v2.getCoordinate(2) - v0.getCoordinate(3)) * (v1.getCoordinate(1) - v0.getCoordinate(1)) -
				(v1.getCoordinate(2) - v0.getCoordinate(3)) * (v2.getCoordinate(1) - v0.getCoordinate(1)) == 0 &&
				(v1.getCoordinate(2) - v0.getCoordinate(3)) * (v2.getCoordinate(0) - v0.getCoordinate(0)) -
				(v1.getCoordinate(0) - v0.getCoordinate(0)) * (v2.getCoordinate(2) - v0.getCoordinate(2)) == 0 &&
				(v1.getCoordinate(0) - v0.getCoordinate(0)) * (v2.getCoordinate(1) - v0.getCoordinate(1)) -
				(v1.getCoordinate(1) - v0.getCoordinate(1)) * (v2.getCoordinate(0) - v0.getCoordinate(0)) == 0;
	}
	
	public static void readVertices(String file) {
		File f = new File(file);
		if(! f.canRead() || ! f.isFile()){
			System.err.println("File " + file + " could not be read.");
			System.exit(0);
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String sb = br.readLine();
			while(sb != null){
				String[] coords = sb.split(" ");

				Vertex.makeVertex(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
				
				sb = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public static void constructHull() {
		QueueElement<Vertex> v = ConvexHull.vertices.getFirst();
			
		{
			if(! v.getElem().getMark()){
				v.getElem().setMark(true);
				addOne(v.getElem());
				Cleaning.cleanUp();
			}
			v = v.getNext();
		} while (v != ConvexHull.vertices.getFirst());
	}
	
}
