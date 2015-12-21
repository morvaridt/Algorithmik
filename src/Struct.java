
public class Struct {
	
	public Face makeStructs(Edge e, Vertex p){
		Face new_face = new Face();
		Edge[] new_edge = new Edge[2];	
		
		for(int i = 0; i < 2; i++){
			if(! (e.endpts[i].duplicte == null)){
				new_edge[i] = Edge.makeEdge();
				new_edge[i].endpts[0] = e.endpts[i];
				new_edge[i].endpts[1] = p;
				
				e.endpts[i].duplicte = new_edge[i];
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
	
	public Face makeCcw(Face f, Edge e, Vertex p){
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
	
	public boolean addOne(Vertex p){
		boolean visible = false;
		
		// mark from p visible faces
		for(Face f : ConvexHull.faces){
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
		
		for(Edge e: ConvexHull.edges){
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

	private int volume(Face f, Vertex p) {
		// TODO Auto-generated method stub
		return 0;
	}


}
