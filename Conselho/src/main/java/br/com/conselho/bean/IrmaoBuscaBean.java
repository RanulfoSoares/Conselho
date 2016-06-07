package br.com.conselho.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.conselho.dao.CriancaAdolescenteDAO;
import br.com.conselho.domain.CriancaAdolescente;

@SuppressWarnings("serial")
@ManagedBean(name = "MBParenteMenorBusca")
@ViewScoped
public class IrmaoBuscaBean implements Serializable {
	
	private String nomeBuscaIrmao;

	private String nomeCriancaSelecionado;
	private CriancaAdolescente criancaSelecionada = null;
	
	@PostConstruct
	public void ini(){
		
		String idCrianca = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("c");		
		
		if(idCrianca != null){
			try {
				criancaSelecionada = new CriancaAdolescenteDAO().buscaCrianca(Long.parseLong(idCrianca));
				nomeCriancaSelecionado = criancaSelecionada.getPessoa().getNomeCompleto();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String preparaMostraDados(){		
		return "";
	}
	
	public String navegaCadastroParenteMenor(){
		
		System.out.println("Chamou o metodooo..");
		
		if(criancaSelecionada != null){
			return "parente_cadastro?faces-redirect=true&c="+criancaSelecionada.getId();
		}else{
			return "ERRO";
		}			
	}

	public String getNomeBuscaIrmao() {
		return nomeBuscaIrmao;
	}

	public void setNomeBuscaIrmao(String nomeBuscaIrmao) {
		this.nomeBuscaIrmao = nomeBuscaIrmao;
	}

	public String getNomeCriancaSelecionado() {
		return nomeCriancaSelecionado;
	}

	public void setNomeCriancaSelecionado(String nomeCriancaSelecionado) {
		this.nomeCriancaSelecionado = nomeCriancaSelecionado;
	}
	
	
	
}
	
	

	