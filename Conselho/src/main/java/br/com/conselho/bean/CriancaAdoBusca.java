package br.com.conselho.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.conselho.dao.CriancaAdolescenteDAO;
import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.util.Helper;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBCriancaBusca")
@ViewScoped
public class CriancaAdoBusca implements Serializable {
	
	private String nomeBuscaCriancaAdolescente;
	private List<CriancaAdolescente> listaCriancaAdolescente;
	private Long idCriancaSelecionada;
	private CriancaAdolescenteDAO criancaAdolescenteDAO = new CriancaAdolescenteDAO();
	private CriancaAdolescente criancaAdolescenteExcluir = new CriancaAdolescente();
	
	
	public void buscarCriancaAdolescente(){

		try {
			listaCriancaAdolescente = criancaAdolescenteDAO.buscaCrianca(nomeBuscaCriancaAdolescente);	
			for (CriancaAdolescente criancaAdolescente : listaCriancaAdolescente) {
				if(Helper.executaCalculoIdade(criancaAdolescente.getPessoa().getDataNascimento()) >= 18){
					listaCriancaAdolescente.remove(criancaAdolescente);
				}
			}
		} catch (Exception e) {
			System.out.println("Erro ao salvar: "+e.getMessage());
			JSFUtil.addErrorMessage("ERRO ao Buscar: "+e.getMessage());
		}				
	}
	
	public String preparaMostraDados(){
		
		try {

			
		} catch (Exception e) {
			System.out.println("Erro ao salvar: "+e.getMessage());
			JSFUtil.addErrorMessage("ERRO ao Buscar: "+e.getMessage());
		}
		
	return "crianca_ado_cadastro?faces-redirect=true&c="+idCriancaSelecionada;
		
	}
	
	public void excluir(ActionEvent event){
		
		criancaAdolescenteExcluir = (CriancaAdolescente) event.getComponent().getAttributes().get("criancaSelecionada");
		System.out.println("Crianca Adolescente a ser excluido: "+criancaAdolescenteExcluir.getPessoa().getNomeCompleto());
		
		try {
			criancaAdolescenteDAO.excluir(criancaAdolescenteExcluir);
			listaCriancaAdolescente = new ArrayList<CriancaAdolescente>();
			JSFUtil.addInfoMessage("Excluido com sucesso!");			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao excluir");
			e.printStackTrace();
		}
						
	}
	
	public String getNomeBuscaCriancaAdolescente() {
		return nomeBuscaCriancaAdolescente;
	}

	public void setNomeBuscaCriancaAdolescente(String nomeBuscaCriancaAdolescente) {
		this.nomeBuscaCriancaAdolescente = nomeBuscaCriancaAdolescente;
	}

	public List<CriancaAdolescente> getListaCriancaAdolescente() {
		return listaCriancaAdolescente;
	}

	public void setListaCriancaAdolescente(
			List<CriancaAdolescente> listaCriancaAdolescente) {
		this.listaCriancaAdolescente = listaCriancaAdolescente;
	}

	public Long getIdCriancaSelecionada() {
		return idCriancaSelecionada;
	}

	public void setIdCriancaSelecionada(Long idCriancaSelecionada) {
		this.idCriancaSelecionada = idCriancaSelecionada;
	}

	public CriancaAdolescente getCriancaAdolescenteExcluir() {
		return criancaAdolescenteExcluir;
	}

	public void setCriancaAdolescenteExcluir(
			CriancaAdolescente criancaAdolescenteExcluir) {
		this.criancaAdolescenteExcluir = criancaAdolescenteExcluir;
	}	
}
