
public class Cleaning {

	public void cleanUp(){
		cleanEdges();
		cleanFaces();
		cleanVertices();
	}

	private void cleanEdges() {		
		Edge firstEdge = ConvexHull.edges.peek();
		Edge currentEdge = ConvexHull.edges.poll();
		
		while (true) {
		    Edge tmpEdge = ConvexHull.edges.peek();
		    if (tmpEdge == null || tmpEdge.equals(firstEdge)) {
			    ConvexHull.edges.add(currentEdge);
		        break;
		    }
		    
		    // integrate new face
		    if(currentEdge.newface != null){
		    	if(currentEdge.adjface[0].visible){
		    		currentEdge.adjface[1] = currentEdge.newface;
		    	}
		    	else{
		    		currentEdge.adjface[0] = currentEdge.newface;
		    	}
		    	currentEdge.newface = null;
		    }
		    
		    // delete marked edges
		    if(! currentEdge.deleted){
			    ConvexHull.edges.add(currentEdge);
		    }    
		    currentEdge = ConvexHull.edges.poll();
		}
	}
	
	private void cleanFaces() {
		Face firstFace = ConvexHull.faces.peek();
		Face currentFace = ConvexHull.faces.poll();
		
		while (true) {
		    Face tmpFace = ConvexHull.faces.peek();
		    if (tmpFace == null || tmpFace.equals(firstFace)) {
			    ConvexHull.faces.add(currentFace);
		        break;
		    }
		    
		    // delete visible faces
		    if(! currentFace.visible){
			    ConvexHull.faces.add(currentFace);
		    }    
		    currentFace = ConvexHull.faces.poll();
		}		
	}
	
	private void cleanVertices() {
		// mark vertices on hull
		for(Edge e : ConvexHull.edges){
			e.endpts[0].onhull = true;
			e.endpts[1].onhull = true;			
		}
		
		Vertex firstVertex = ConvexHull.vertices.peek();
		Vertex currentVertex = ConvexHull.vertices.poll();
		
		while (true) {
		    Vertex tmpVertex = ConvexHull.vertices.peek();
		    if (tmpVertex == null || tmpVertex.equals(firstVertex)) {
			    ConvexHull.vertices.add(currentVertex);
		        break;
		    }
		    
		    // delete marked vertices, which are not on hull
		    if(! currentVertex.mark || currentVertex.onhull){
			    // reset flags
			    currentVertex.duplicte = null;
			    currentVertex.onhull = false;
			    
			    ConvexHull.vertices.add(currentVertex);
		    }     
		    currentVertex = ConvexHull.vertices.poll();
		}			
	}
}
