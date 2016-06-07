package br.com.conselho.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.exception.ConstraintViolationException;

import br.com.conselho.dao.ConselheiroDAO;
import br.com.conselho.dao.InstituicaoDAO;
import br.com.conselho.domain.Conselheiro;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.util.AutenticaUsuario;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name="MBConselheiroBusca")
@ViewScoped
public class ConselheiroBuscaBean implements Serializable {
	
	private String template;
	private List<Conselheiro> listaConselho;
	private String nomeBusca;
	private String idConselheiroSelecionado;
	private Conselheiro conselheiroExcluir;
	private String conselheiroDesativado = "false";
	private Conselheiro conselheiroLogado;
	private ConselheiroDAO conselheiroDAO = new ConselheiroDAO();
	
	public ConselheiroBuscaBean() {
		template = AutenticaUsuario.verificaTemplate();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();        
        conselheiroLogado = (Conselheiro) session.getAttribute("conselheiroLogado");
	}
	
	@PostConstruct
	public void ini(){
		try {
			
			listaConselho = new ArrayList<Conselheiro>();
			listaConselho = conselheiroDAO.busca("", false);
			
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
	
	public void buscar(){		
		
		try {
			listaConselho = conselheiroDAO.busca(nomeBusca, (conselheiroDesativado.equals("true") ? true : false));
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Ocorreu um erro ao consultar conselheiro: "+e.getMessage());
			System.out.println("Erro ao efetuar a busca:"+e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	public String navegaCadastro(){		
		
		if(idConselheiroSelecionado != null){
			return "conselheiro_cadastro?faces-redirect=true&c="+idConselheiroSelecionado;
		}else{
			return "ERRO";
		}			
	}
	
	public void excluir(ActionEvent event){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! ", "Procedimento Inválido! Nível de acesso não permitido desativar.");
			return;
		}
		
		conselheiroExcluir = new Conselheiro();
		conselheiroExcluir = (Conselheiro) event.getComponent().getAttributes().get("conSelecionada");
		conselheiroExcluir.setFlgAtivo("N");
		try {
			conselheiroDAO.salvar(conselheiroExcluir);
			listaConselho = new ArrayList<Conselheiro>();
			JSFUtil.addInfoMessage("Conselheiro desativado com sucesso!");
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao excluir conselheiro");
			e.printStackTrace();
		}
		
		//System.out.println("Crianca Adolescente a ser excluido: "+criancaAdolescenteExcluir.getPessoa().getNomeCompleto());
		
		/*
		 * add metodo que inativa conselheiro							
		 */
	}
	
	public List<Conselheiro> getListaConselho() {
		return listaConselho;
	}
	public void setListaConselho(List<Conselheiro> listaConselho) {
		this.listaConselho = listaConselho;
	}
	public String getNomeBusca() {
		return nomeBusca;
	}
	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}
	public String getIdConselheiroSelecionado() {
		return idConselheiroSelecionado;
	}
	public void setIdConselheiroSelecionado(String idConselheiroSelecionado) {
		this.idConselheiroSelecionado = idConselheiroSelecionado;
	}	
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}

	public String getConselheiroDesativado() {
		return conselheiroDesativado;
	}

	public void setConselheiroDesativado(String conselheiroDesativado) {
		this.conselheiroDesativado = conselheiroDesativado;
	}
		
	
}
