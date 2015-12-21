
public class Struct {
	
	public static Face makeStructs(Edge e, Vertex p){
		Face new_face = new Face();
		Edge[] new_edge = new Edge[2];	
		
		for(int i = 0; i < 2; i++){
			if(! (e.endpts[i].duplicate == null)){
				new_edge[i] = Edge.makeEdge();
				new_edge[i].endpts[0] = e.endpts[i];
				new_edge[i].endpts[1] = p;
				
				e.endpts[i].duplicate = new_edge[i];
			}
		}
		
		new_face = Face.makeFace();
		new_face.edge[0] = e;
		new_face.edge[1] = new_edge[0];
		new_face.edge[2] = new_edge[1];

		new_face = makeCcw(new_face, e, p);
		
		for(int i = 0; i < 2; ++i){
			for(int j = 0; j < 3; ++j){
				if(! (new_edge[i].adjface[j] == null)){
					new_edge[i].adjface[j] = new_face;
					break;
				}
			}
		}
		
		return new_face;
	}
	
	public static Face makeCcw(Face f, Edge e, Vertex p){
		int i;
		Face invisibleFace;
		Edge tmp;
		
		if( e.adjface[1] == null ){
			invisibleFace = e.adjface[0];
		}
		else{
			if(! e.adjface[0].visible){
				invisibleFace = e.adjface[0];
			}
			else{
				invisibleFace = e.adjface[1];
			}
		}
		
		for(i = 0; invisibleFace.vertex[i] != e.endpts[1]; i++){}
		
		if(invisibleFace.vertex[(i + 1) % 3] != e.endpts[0]){
			f.vertex[0] = e.endpts[1];
			f.vertex[1] = e.endpts[0];
		}
		else{
			f.vertex[0] = e.endpts[0];
			f.vertex[1] = e.endpts[1];
			
			// swap
			tmp = f.edge[1];
			f.edge[1] = f.edge[2];
			f.edge[2]= tmp;
		}
		
		f.vertex[2] = p;
		return f;
	}
	
	// TODO: needs to return boolean?
	public static boolean addOne(Vertex p){
		boolean visible = false;
		
		// mark from p visible faces
		for(Element<Face> wf : ConvexHull.faces){
			Face f = wf.getElem();
			if(volume(f, p) < 0){
				f.visible = true;
				visible = true;
			}
		}
		
		// no faces visible, p is inside of hull
		if(! visible){
			p.onhull = false;
			return false;
		}
		
		for(Element<Edge> we: ConvexHull.edges){
			Edge e = we.getElem();
			if(e.adjface[0].visible && e.adjface[1].visible){
				e.deleted = true;
			}
			else{
				if(e.adjface[0].visible || e.adjface[1].visible){
					e.newface = makeStructs(e, p);
				}
			}
		}
		
		return true;
	}

	private static int volume(Face f, Vertex p) {
		double ax = f.vertex[0].v[0] - p.v[0];
		double ay = f.vertex[0].v[1] - p.v[1];
		double az = f.vertex[0].v[2] - p.v[2];
		double bx = f.vertex[1].v[0] - p.v[0];
		double by = f.vertex[1].v[1] - p.v[1];
		double bz = f.vertex[1].v[2] - p.v[2];		
		double cx = f.vertex[2].v[0] - p.v[0];
		double cy = f.vertex[2].v[1] - p.v[1];
		double cz = f.vertex[2].v[2] - p.v[2];
		
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
		Vertex v0;
		Vertex v3;
		Vertex temp;
		Face f = Face.makeFace();
		Edge e0 = Edge.makeEdge();
		Edge e1 = Edge.makeEdge();
		Edge e2 = Edge.makeEdge();
		Edge s;
		int volume;
		
		// find 3 noncollinear points
		v0 = ConvexHull.vertices.peek();
		while(collinear(v0, v0.next, v0.next.next)){
			v0 = v0.next;
			if(v0.next == ConvexHull.vertices.peek()){
				// TODO: write error in red
				System.out.println("All points are collinear");
				System.exit(0);
			}
		}
		
		// mark vertices as processed
		v0.mark = true;
		v0.next.mark = true;
		v0.next.next.mark = true;
		
		e0.endpts[0] = v0;
		e0.endpts[1] = v0.next;
		e1.endpts[0] = v0.next;
		e1.endpts[1] = v0.next.next;		
		e2.endpts[0] = v0.next.next;
		e2.endpts[1] = v0;	
		
		f.edge[0] = e0;
		f.edge[1] = e1;
		f.edge[2] = e2;
		// TODO: setEdges? setVertices?
		f.vertex[0] = v0;
		f.vertex[1] = v0.next;
		f.vertex[2] = v0.next.next;
		
		e0.adjface[0] = e1.adjface[0] = e2.adjface[0] = f;
	
		// try to find another noncollinear vertex
		v3 = v0.next.next.next;
		volume = volume(f, v3);
		
		while(volume != 1){
			v3 = v3.next;
			if(v3 == ConvexHull.vertices.peek()){
				// TODO: write error in red
				System.out.println("All points are coplanar");
				System.exit(0);
			}
			volume = volume(f, v3);
		}
		// TODO: any occurence, where we assign v3.mark = false? --> method?
		v3.mark = true;
		
		// store vertices in ccw order
		if(volume < 0){
			// TODO: swap into generic methode
			temp = f.vertex[1];
			f.vertex[1] = f.vertex[2];
			f.vertex[2] = temp;
			
			s = f.edge[1];
			f.edge[1] = f.edge[2];
			f.edge[2] = s;
		}
		
		e0.adjface[1] = makeStructs(e0, v3);
		e1.adjface[1] = makeStructs(e1, v3);
		e2.adjface[1] = makeStructs(e2, v3);
		
		Cleaning.cleanUp();
	}

	private static boolean collinear(Vertex v0, Vertex v1, Vertex v2) {
		return 	(v2.v[2] - v0.v[3]) * (v1.v[1] - v0.v[1]) -
				(v1.v[2] - v0.v[3]) * (v2.v[1] - v0.v[1]) == 0 &&
				(v1.v[2] - v0.v[3]) * (v2.v[0] - v0.v[0]) -
				(v1.v[0] - v0.v[0]) * (v2.v[2] - v0.v[2]) == 0 &&
				(v1.v[0] - v0.v[0]) * (v2.v[1] - v0.v[1]) -
				(v1.v[1] - v0.v[1]) * (v2.v[0] - v0.v[0]) == 0;
	}

	public static void readVertices() {
		Vertex v;
		
	}

	public static void constructHull() {
		Vertex v = ConvexHull.vertices.peek();
			
		{
			if(! v.mark){
				v.mark = true;
				addOne(v);
				Cleaning.cleanUp();
			}
			v = v.next;
		} while (v != ConvexHull.vertices.peek());
	}
	
}
