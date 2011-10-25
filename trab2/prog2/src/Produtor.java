
public class Produtor extends Thread {
	String Nome;
	private Estoque estoque;
	
	public Produtor() { }
	
	public Produtor(Estoque estoque) {
		this.estoque = estoque;
	}
	
	public void setNome(String name) {
		this.Nome = name;
	}
	
	public String getNome() {
		return this.Nome;
	}
	
	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	
	@SuppressWarnings("unchecked")
	public void produzir() {
		synchronized (estoque) {
			
			/* Insere um recurso no estoque */
			Recurso recurso = new Recurso((int)(Main.getRecursosProduzidos()+1));
			this.estoque.getConteudo().add(recurso);
			
			System.out.println("+ " + this.getNome() + "\t -> Recurso produzido: " + recurso + "\tTotal já produzido: " + (Main.getRecursosProduzidos()+1) + "\tRecursos disponíveis: " + estoque.getConteudo().size());
			
			/* Notifica os consumidores que o estoque já foi reposto */
			estoque.notifyAll();
			Main.setRecursosProduzidos(Main.getRecursosProduzidos()+1);
		}
	}

	public void run() {

		while (Main.getRecursosProduzidos() < Configuracoes.TOTAL_RECURSOS_A_SER_PRODUZIDO) {

			this.produzir();

			try {
				Thread.sleep((int)(Math.random() * Configuracoes.MAX_TIME_TO_SLEEP));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
