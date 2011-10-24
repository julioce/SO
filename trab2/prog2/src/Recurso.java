
public class Recurso {
	private int id;

	public Recurso() { }

	public Recurso(int id) {
		this.id = id;
	}

	public String toString() {
		return "" + id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	} 
}
