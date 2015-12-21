
public class QueueElement<T> {
	
	private T elem;
	private QueueElement<T> next;
	
	public QueueElement(T el){
		this.elem = el;
		setNext(null);
	}

	public QueueElement<T> getNext() {
		return next;
	}
	
	public void setNext(QueueElement<T> elem){
		next = elem;
	}
	
	public T getElem(){
		return elem;
	}
}
