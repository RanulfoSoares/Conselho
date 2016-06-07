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

import br.com.conselho.dao.CriancaAdolescenteDAO;
import br.com.conselho.dao.InstituicaoDAO;
import br.com.conselho.dao.MembroDao;
import br.com.conselho.dao.MoverMembroDAO;
import br.com.conselho.domain.Conselheiro;
import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Membro;
import br.com.conselho.domain.MoverMembro;
import br.com.conselho.domain.auxiliotela.MembroUltimaFamilia;
import br.com.conselho.util.AutenticaUsuario;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBInstituicaoBusca")
@ViewScoped
public class InstituicaoBuscaBean implements Serializable {
	
	private String template;
	private Instituicao instituicaoSelecionada = null;
	private InstituicaoDAO instituicaoDAO = new InstituicaoDAO();
	private List<Instituicao> listaInstituicao;
	private String nomeBusca = "";
	private Instituicao instituicaoExcluir;
	private String idInstituicaoSelecionado;
	private List<MembroUltimaFamilia> listaAcolhidos;
	private String idMembro;
	private Conselheiro conselheiroLogado;
	
	public InstituicaoBuscaBean() {
		template = AutenticaUsuario.verificaTemplate();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();        
        conselheiroLogado = (Conselheiro) session.getAttribute("conselheiroLogado");
	}
	
	@PostConstruct
	public void ini(){		
//		String idCrianca = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("i");
//		System.out.println("id CriancaAdo Selecionado: "+idCrianca);
//		
//		if(idCrianca != null){
//			try {
//				criancaSelecionada = criancaAdolescenteDAO.buscaCrianca(Long.parseLong(idCrianca));
//				nomeCriancaSelecionado = criancaSelecionada.getPessoa().getNomeCompleto();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}			
	}
	
	public String navegaCadastro(){		
		
		if(idInstituicaoSelecionado != null){
			return "instituicao_cadastro?faces-redirect=true&i="+idInstituicaoSelecionado;
		}else{
			return "ERRO";
		}			
	}
	
	public void buscaInstituicao(){
		
		try {
			listaInstituicao = new InstituicaoDAO().listaInstituicao(nomeBusca);			
			
			if(listaInstituicao.isEmpty()){
				JSFUtil.addWarnMessage("Nenhum registro encontrado!");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void excluir(ActionEvent event){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! ", "Procedimento Inválido! Nível de acesso não permitido excluir.");
			return;
		}
		
		instituicaoExcluir = (Instituicao) event.getComponent().getAttributes().get("insSelecionada");
		//System.out.println("Crianca Adolescente a ser excluido: "+criancaAdolescenteExcluir.getPessoa().getNomeCompleto());
		
		try {
			new InstituicaoDAO().excluir(instituicaoExcluir);
			listaInstituicao = new ArrayList<Instituicao>();
			JSFUtil.addInfoMessage("Excluido com sucesso!");			
		} catch (ConstraintViolationException e) {
			JSFUtil.addErrorMessage("Erro ao excluir instituição pode estar em uso");
			e.printStackTrace();
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao excluir");
			e.printStackTrace();
		}							
	}
	
	public void buscaAcolhidos(ActionEvent event){
		
		try{
			
			instituicaoSelecionada = (Instituicao) event.getComponent().getAttributes().get("insSelecionada");
			listaAcolhidos = new ArrayList<MembroUltimaFamilia>();
			//List<Membro> listaMembrosInstituicao = new MembroDao().buscaMembro(instituicaoSelecionada);
			
			List<Membro> listaMembrosInstituicao = instituicaoSelecionada.getListaMembros();
			
			for (Membro membro : listaMembrosInstituicao) {
				
				if(membro.getDataDesvinculo() == null){
					System.out.println("Membro: "+membro.getPessoa().getNomeCompleto());
					List<Membro> historicoMembro = new  MembroDao().buscaMembroEmUmtimaFamilia(membro.getPessoa());
					MembroUltimaFamilia membroUltimaFamilia = new MembroUltimaFamilia();
					membroUltimaFamilia.setMembro(membro);
					if(!historicoMembro.isEmpty()){
						membroUltimaFamilia.setUltimaFamilia(historicoMembro.get(0).getFamilia());
					}
					listaAcolhidos.add(membroUltimaFamilia);
				}							
			}
			
			
		}catch(Exception e){
			
		}		
	}
	
	public String preparaMoverMembro(){
		System.out.println("id selecionado: "+idMembro);
		return "mover_membro?faces-redirect=true&c="+idMembro;		
	}

	public Instituicao getInstituicaoSelecionada() {
		return instituicaoSelecionada;
	}

	public void setInstituicaoSelecionada(Instituicao instituicaoSelecionada) {
		this.instituicaoSelecionada = instituicaoSelecionada;
	}

	public List<Instituicao> getListaInstituicao() {
		return listaInstituicao;
	}

	public void setListaInstituicao(List<Instituicao> listaInstituicao) {
		this.listaInstituicao = listaInstituicao;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

	public String getIdInstituicaoSelecionado() {
		return idInstituicaoSelecionado;
	}

	public void setIdInstituicaoSelecionado(String idInstituicaoSelecionado) {
		this.idInstituicaoSelecionado = idInstituicaoSelecionado;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<MembroUltimaFamilia> getListaAcolhidos() {
		return listaAcolhidos;
	}

	public void setListaAcolhidos(List<MembroUltimaFamilia> listaAcolhidos) {
		this.listaAcolhidos = listaAcolhidos;
	}

	public String getIdMembro() {
		return idMembro;
	}

	public void setIdMembro(String idMembro) {
		this.idMembro = idMembro;
	}	
	
	
	
}
