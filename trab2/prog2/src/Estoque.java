import java.util.LinkedList;  
import java.util.List;

public class Estoque {
	
	@SuppressWarnings("unchecked")
	private List conteudo = new LinkedList();

	public Estoque() {}

	@SuppressWarnings("unchecked")
	public List getConteudo() {
		return conteudo;
	} 
}
