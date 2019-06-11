import java.util.EmptyStackException;

public class LinkedStack<T> implements StackInterface<T> {

	// Variables
	private Node topNode;
	private int size = 0;

	// Constructor

	// empty constructor
	public LinkedStack() {
		
	}

	// Methods

	/** Adds a new entry to the top of this stack.
    @param newEntry  An object to be added to the stack. */
	@Override
	public void push(T newEntry) {
		Node newNode = new Node(newEntry);
		// if stack empty
		if (isEmpty()) {
			topNode = newNode;
			size++;  // increase size by 1
		} else {
		// if stack not empty
		newNode.next = topNode;
		topNode = newNode;
		size++;
		}
	}

	/** Removes and returns this stack's top entry.
    @return  The object at the top of the stack. 
    @throws  EmptyStackException if the stack is empty before the operation. */
	@Override
	public T pop() {
		// if stack empty
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		Node top = topNode;
		topNode = topNode.next;
		size--;
		return top.data;
	}

	/** Retrieves this stack's top entry.
    @return  The object at the top of the stack.
    @throws  EmptyStackException if the stack is empty. */
	@Override
	public T peek() {
		// if stack empty
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return topNode.data;
	}

	/** Detects whether this stack is empty.
    @return  True if the stack is empty. */
	@Override
	public boolean isEmpty() {
		// if topNode is null, stack empty
		if(topNode == null) {
			return true;
		}
		return false;
	}

	/** Removes all entries from this stack. */
	@Override
	public void clear() {
		// set topNode to null and reset size
		topNode = null;
		size = 0;
	}

	// Node class
	private class Node {

		// Variables
		private T data;
		private Node next;

		// Constructors

		// parameter constructor
		public Node(T data) {
			this.data = data;
		}

	}
}
