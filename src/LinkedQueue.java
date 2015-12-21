

public class LinkedQueue<T> {
	private QueueElement<T> first;
	private QueueElement<T> last;
	private int count;
	
	public LinkedQueue(){
		first = last = null;
		count = 0;
	}
	
	public void add(T el){
		QueueElement<T> element = new QueueElement<T>(el);
		if(count == 0){
			first = element;
			last = first;
		}
		else{
			last.setNext(element);
			last = last.getNext();
		}
		count++;
		last.setNext(null);
	}
	
	public T peek(){
		return first.getElem();
	}

	public T poll(){
		QueueElement<T> tmp = first;
		first = first.getNext();
		return tmp.getElem();
	}
	
	public QueueElement<T> find(T elem){
		QueueElement<T> curr = first;
		while(curr != null){
			if(curr.getElem() != elem){
				curr = curr.getNext();
			}
			else{
				break;
			}
		}
		return curr;
	}

	public QueueElement<T> getFirst() {
		return first;
	}
}
