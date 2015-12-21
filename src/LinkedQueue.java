import java.util.Iterator;


public class LinkedQueue implements Iterable<Element>{
	private Element first;
	private Element last;
	private int count;
	
	public <T> LinkedQueue(){
		first = last = null;
		count = 0;
	}
	
	<T> void add(T el){
		Element<T> element = new Element<T>(el);
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
	
	<T> T peek(){
		return (T) first.getElem();
	}

	<T> T poll(){
		T tmp = (T) first.getElem();
		first = first.getNext();
		return tmp;
	}

	@Override
	public Iterator<Element> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
