public class GenericArrayStack<E> implements Stack<E> {
   
   // ADD YOUR INSTANCE VARIABLES HERE
   private E[] elems;
   private int top;
   // Constructor
   
   // ADD YOU CODE HERE
    public GenericArrayStack( int capacity ) {
      top = -1;
      elems = (E[]) new Object[ capacity ];
    }

    // Returns true if this ArrayStack is empty
    public boolean isEmpty() {

      return top == 0;
    }

    public void push( E elem ) {
    // ADD YOU CODE HERE
      elems[ top++ ] = elem;
	  }
		
    public E pop() { 
	// ADD YOU CODE HERE
      E placeholder = elems[ --top ];

      elems[ top ] = null;

      return placeholder;
    }

    public E peek() {
        
      return elems[ top-1 ];
	
    }
}
