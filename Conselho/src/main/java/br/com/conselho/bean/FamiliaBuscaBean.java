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

import br.com.conselho.dao.ConselheiroDAO;
import br.com.conselho.dao.FamiliaDAO;
import br.com.conselho.dao.PessoaDAO;
import br.com.conselho.domain.Conselheiro;
import br.com.conselho.domain.Familia;
import br.com.conselho.domain.Membro;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.util.AutenticaUsuario;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBFamiliaBusca")
@ViewScoped
public class FamiliaBuscaBean implements Serializable  {
	
	private String template;
	private List<Familia> listaFamilia = new ArrayList<Familia>();
	private FamiliaDAO familiaDao = new FamiliaDAO();
	private String idFamiliaSelecionado;
	private Familia familiaSelecionada = new Familia();
	private List<Conselheiro> listaConselhero;
	private Conselheiro conselheiroSelecionado;
	private String numeroRegistro;
	private Conselheiro conselheiroLogado;
	
	public FamiliaBuscaBean() {
		template = AutenticaUsuario.verificaTemplate();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();        
        conselheiroLogado = (Conselheiro) session.getAttribute("conselheiroLogado");
	}
	
	@PostConstruct
	public void ini(){
		try {
			listaConselhero = new ConselheiroDAO().busca("", false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void busca(){
		
		try {
			if(numeroRegistro.equals("") || numeroRegistro == null){
				listaFamilia = familiaDao.busca(conselheiroSelecionado, null);
			}else{
				listaFamilia = familiaDao.busca(conselheiroSelecionado, numeroRegistro);
			}
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao efetuar a busca: "+e.getMessage());
			System.out.println("Erro ao buscar conselheiros: "+e.getMessage());
		}
	}
	
	public void excluir(ActionEvent event){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! ", "Procedimento Inválido! Nível de acesso não permitido excluir.");
			return;
		}
		
		familiaSelecionada = (Familia) event.getComponent().getAttributes().get("familiaSelecionada");
		System.out.println("Cidadão a ser excluido: "+familiaSelecionada.getNumero());
		
		for (Membro membro : familiaSelecionada.getListaMembros()) {
			System.out.println("Membro Familia: "+membro.getPessoa().getNomeCompleto());
		}
		
		try {
			familiaDao.excluir(familiaSelecionada);
			listaFamilia = new ArrayList<Familia>();
			JSFUtil.addInfoMessage("Excluido com sucesso!");			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao excluir");
			e.printStackTrace();
		}			
	}
	
	public String preparaMostraDados(){		
		try {
			
			System.out.println("ID SELECIONADO PARA EDITAR TELA DE BUSCA: "+idFamiliaSelecionado);
			
		} catch (Exception e) {
			System.out.println("Erro ao salvar: "+e.getMessage());
			JSFUtil.addErrorMessage("ERRO ao Buscar: "+e.getMessage());
		}		
		return "familia_cadastro?faces-redirect=true&c="+idFamiliaSelecionado;		
	}

	
	public List<Familia> getListaFamilia() {
		return listaFamilia;
	}

	public void setListaFamilia(List<Familia> listaFamilia) {
		this.listaFamilia = listaFamilia;
	}

	public String getIdFamiliaSelecionado() {
		return idFamiliaSelecionado;
	}

	public void setIdFamiliaSelecionado(String idFamiliaSelecionado) {
		this.idFamiliaSelecionado = idFamiliaSelecionado;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<Conselheiro> getListaConselhero() {
		return listaConselhero;
	}

	public void setListaConselhero(List<Conselheiro> listaConselhero) {
		this.listaConselhero = listaConselhero;
	}

	public Conselheiro getConselheiroSelecionado() {
		return conselheiroSelecionado;
	}

	public void setConselheiroSelecionado(Conselheiro conselheiroSelecionado) {
		this.conselheiroSelecionado = conselheiroSelecionado;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	
	
}
