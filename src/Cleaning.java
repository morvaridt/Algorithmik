
public class Cleaning {

	public static void cleanUp(){
		cleanEdges();
		cleanFaces();
		cleanVertices();
	}

	private static void cleanEdges() {		
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
		    	if(currentEdge.getAdjface(0).visible){
		    		currentEdge.setAdjface(0, currentEdge.newface);
		    	}
		    	else{
		    		currentEdge.setAdjface(0, currentEdge.newface);
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
	
	private static void cleanFaces() {
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
	
	private static void cleanVertices() {
		// mark vertices on hull
		QueueElement<Edge> we = ConvexHull.edges.getFirst();
		while(we.getNext() != null){
			Edge e = we.getElem();
			e.getEndpts(0).onhull = true;
			e.getEndpts(1).onhull = true;			
			we = we.getNext();
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
			    currentVertex.duplicate = null;
			    currentVertex.onhull = false;
			    
			    ConvexHull.vertices.add(currentVertex);
		    }     
		    currentVertex = ConvexHull.vertices.poll();
		}			
	}
}
