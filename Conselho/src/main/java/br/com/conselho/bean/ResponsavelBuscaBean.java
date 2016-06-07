package br.com.conselho.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.conselho.dao.CriancaAdolescenteDAO;
import br.com.conselho.dao.ParenteDAO;
import br.com.conselho.dao.ResponsavelDAO;
import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.Parente;
import br.com.conselho.domain.Responsavel;
import br.com.conselho.domain.auxiliotela.ResponsavelTela;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBResponsavelBusca")
@ViewScoped
public class ResponsavelBuscaBean implements Serializable {
	
	private String nomeCriancaSelecionado;
	private CriancaAdolescente criancaSelecionada = null;
	private CriancaAdolescenteDAO criancaAdolescenteDAO = new CriancaAdolescenteDAO();
	private List<ResponsavelTela> listaResponsavel = new ArrayList<ResponsavelTela>();
	private ResponsavelTela respTelaSelecionado = new ResponsavelTela();
	private String idResponsavelSelecionado;

	
	@PostConstruct
	public void ini(){
		
		String idCrianca = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("c");
		System.out.println("id CriancaAdo Selecionado: "+idCrianca);
		
		if(idCrianca != null){
			try {
				criancaSelecionada = criancaAdolescenteDAO.buscaCrianca(Long.parseLong(idCrianca));
				nomeCriancaSelecionado = criancaSelecionada.getPessoa().getNomeCompleto();
				buscaResponsavel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
	}
	
	public String navegaCadastroResponsavel(){
		
		if(criancaSelecionada != null){
			return "responsavel_cadastro?faces-redirect=true&c="+criancaSelecionada.getId();
		}else{
			return "ERRO";
		}			
	}
	
	public String navegaEditarResponsavel(){
		
		//System.out.println("TENTANDO REDIRICONAR REEDITAR RESPONSAVEL.............. id selecionado: "+idResponsavelSelecionado);
		
		
		if(criancaSelecionada != null){
			return "responsavel_editar?faces-redirect=true&c="+criancaSelecionada.getId()+"&r="+idResponsavelSelecionado;
		}else{
			return "ERRO";
		}			
	}
	
	public String navegaCadastroResponsavelInstituicao(){
		
		if(criancaSelecionada != null){
			return "responsavel_cadastro_instituicao?faces-redirect=true&c="+criancaSelecionada.getId();
		}else{
			return "ERRO";
		}			
	}
	
	public void buscaResponsavel(){	
		
		try {
			List<Responsavel> listaResp = new ResponsavelDAO().buscaResponsavel(criancaSelecionada);
			ResponsavelTela respTela;
			ParenteDAO parenteDao = new ParenteDAO();
			Parente parente;
			
			for (Responsavel responsavel : listaResp) {
				respTela = new ResponsavelTela();
				
				respTela.setId(responsavel.getId());
				respTela.setCrianca(responsavel.getCriancaAdolescente());
				respTela.setGrauResponsabilidade(responsavel.getGrauResponsabilidade());
				
				if(responsavel.getPessoa() != null){
					respTela.setEditavel(Boolean.TRUE);
					respTela.setPessoa(responsavel.getPessoa());
					parente = parenteDao.buscaParente(criancaSelecionada, responsavel.getPessoa());
					respTela.setParente(parente);
				}else if(responsavel.getInstituicao() != null){
					respTela.setEditavel(Boolean.FALSE);					
					respTela.setInstituicao(responsavel.getInstituicao());
				}
				
				listaResponsavel.add(respTela);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void excluir(ActionEvent event){
		
		respTelaSelecionado = (ResponsavelTela) event.getComponent().getAttributes().get("responsavelSelecionada");
		ParenteDAO parenteDAO = new ParenteDAO();
		ResponsavelDAO responsavelDAO = new ResponsavelDAO();
		
		try {		
			
			Responsavel responsavel = new Responsavel();
			responsavel.setId(respTelaSelecionado.getId());
			responsavel.setCriancaAdolescente(respTelaSelecionado.getCrianca());
			responsavel.setGrauResponsabilidade(respTelaSelecionado.getGrauResponsabilidade());
			
			responsavelDAO.excluir(responsavel);
			
			if(respTelaSelecionado.getEditavel()){
				parenteDAO.excluir(respTelaSelecionado.getParente());
			}								
			
			JSFUtil.addInfoMessage("Responsável excluido com sucesso!");
			listaResponsavel = new ArrayList<ResponsavelTela>();
			buscaResponsavel();
		} catch (Exception e) {
			System.out.println("ERROOOO......."+e.getMessage());
			e.printStackTrace();
			JSFUtil.addErrorMessage("ERRO ao Excluir: "+e.getMessage());
		}
		
		
	}

	public String getNomeCriancaSelecionado() {
		return nomeCriancaSelecionado;
	}


	public void setNomeCriancaSelecionado(String nomeCriancaSelecionado) {
		this.nomeCriancaSelecionado = nomeCriancaSelecionado;
	}

	public List<ResponsavelTela> getListaResponsavel() {
		return listaResponsavel;
	}

	public void setListaResponsavel(List<ResponsavelTela> listaResponsavel) {
		this.listaResponsavel = listaResponsavel;
	}

	public String getIdResponsavelSelecionado() {
		return idResponsavelSelecionado;
	}

	public void setIdResponsavelSelecionado(String idResponsavelSelecionado) {
		this.idResponsavelSelecionado = idResponsavelSelecionado;
	}	
}
