
public class Element<T> {
	
	private T elem;
	private Element next;
	
	public Element(T el){
		this.elem = el;
		setNext(null);
	}

	public Element getNext() {
		return next;
	}
	
	public void setNext(Element elem){
		next = elem;
	}
	
	public T getElem(){
		return elem;
	}
}
