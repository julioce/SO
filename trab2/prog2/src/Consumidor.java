
public class Consumidor extends Thread {
	String Nome;
	private Estoque estoque;
	private Desenho canvas;
	
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
		return this.estoque;  
	}
	
	public void setEstoque(Estoque estoque) {  
		this.estoque = estoque;  
	}

	@SuppressWarnings("deprecation")
	public void consumir() {
		synchronized (estoque) {

			/* Verifica se existem recursos no estoque */
			if (estoque.getConteudo().size() > 0) {
				Recurso recurso = (Recurso)estoque.getConteudo().remove(0);
				System.out.println("- " + this.getNome() + "\t -> Recurso consumido: " + recurso);
			}
			else {
				/* Não existe recursos no estoque */
				try {
					if(Main.getRecursosProduzidos() != Configuracoes.TOTAL_RECURSOS_A_SER_PRODUZIDO){
						/* Espera o produtor notificar que houve uma reposição no estoque */
						System.out.println("! " + this.getNome() +  "\t -> Esperando recurso ser reposto...");
						estoque.wait();
					}
					else{
						/* Não serão mais produzidos recursos, então a thread morre */
						System.out.println("! " + this.getNome() +  "\t -> Sai do sistema porque não serão mais produzidos recursos...");
						this.stop();
					}
					
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void run() {
		
		while (true) {

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
