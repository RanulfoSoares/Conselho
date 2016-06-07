package br.com.conselho.bean;

import javax.faces.bean.ManagedBean;


@ManagedBean(name = "MBTesteBean")
public class TesteBean {
	
	private String nome;
	
	public String navegaPagina3(){
		
		System.out.println("Navegando para pagina 3");
		
		return "pagina3";
	}
	
	public void acaoMostraConteudo(){
		System.out.println(nome);
	}

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
}
