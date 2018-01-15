import java.io.Serializable;

public class Pair<T1, T2> implements Serializable {

	private T1 first;
	private T2 second;

	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	public T1 getFirst() {
		return first;
	}

	public T2 getSecond() {
		return second;
	}

	public String toString(){
		return "[" + (first == null?null:first.toString()) + ", " + (second == null?null:second.toString()) + "]";
	}
	
	public void setFirst(T1 i) {
		first = i;
	}

	public void setSecond(T2 i) {
		second = i;
	}
}
