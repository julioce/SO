import java.util.LinkedList;  
import java.util.List;

public class Estoque {
	
	@SuppressWarnings("rawtypes")
	public List conteudo = new LinkedList();

	public Estoque() {}

	@SuppressWarnings("rawtypes")
	public List getConteudo() {
		return conteudo;
	} 
}
