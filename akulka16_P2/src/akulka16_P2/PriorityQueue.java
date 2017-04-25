package akulka16_P2;

public class PriorityQueue {
	
	private Node[] queue;
	private int capacity,size;
	
	public PriorityQueue(int capacity){
		this.capacity = capacity + 1;
		queue = new Node[this.capacity];
		size = 0;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	public int size(){
		return size;
	}
	
	public void add(Node u){
		Node v = new Node(u.getPath(),u.getLevel(),u.getBound());
		queue[++size] = v;
		int tempSize = size;
		while(tempSize!=1 && v.getBound() < queue[tempSize/2].getBound()){
			queue[tempSize] = queue[tempSize/2];
			tempSize /= 2;
		}
		queue[tempSize] = v;
	}
	
	public Node remove(){
		int parent=1,child=2;
		Node item,tempNode;
		if(isEmpty()){
			return null;
		}
		item = queue[1];
		tempNode = queue[size--];
		while(child <= size){
			if(child < size && queue[child].getBound() > queue[child+1].getBound())
				child++;
			if(tempNode.getBound() <= queue[child].getBound())
				break;
			queue[parent] = queue[child];
			parent = child;
			child *= 2;
		}
		queue[parent] = tempNode;
		return item;
	}
	
	public void clear(){
		queue = new Node[this.capacity];
	}
}
