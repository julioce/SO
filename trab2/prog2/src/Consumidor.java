
public class Consumidor extends Thread {
	String Nome;
	private Estoque estoque;
	
	public Consumidor() { }

	public Consumidor(Estoque estoque) {
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

	public void consumir() {
		synchronized (estoque) {

			/* Verifica se existem itens no estoque */
			if (estoque.getConteudo().size() > 0) {
				Recurso recurso = (Recurso)estoque.getConteudo().remove(0);
				System.out.println("- " + this.getNome() + "\t -> Recurso consumido: " + recurso);
			}
			else {
				/* Não existe recursos no estoque */
				try {
					System.out.println("! " + this.getNome() +  "\t -> Consumidor esperando estoque ser reposto...");
					/* Espera o produtor notificar que houve uma reposição no estoque */
					estoque.wait();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void run() {
		
		while (Main.getRecursosProduzidos() < Configuracoes.TOTAL_PRODUTOS_A_SER_PRODUZIDO) {

			this.consumir();
			
			try {
				Thread.sleep((int)(Math.random() * Configuracoes.MAX_TIME_TO_SLEEP));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
   
}
