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

import br.com.conselho.dao.MembroDao;
import br.com.conselho.dao.OrgaoDAO;
import br.com.conselho.dao.PessoaDAO;
import br.com.conselho.domain.Conselheiro;
import br.com.conselho.domain.Membro;
import br.com.conselho.domain.Orgao;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.util.AutenticaUsuario;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBBuscaCidadao")
@ViewScoped
public class CidadaoBuscaBean implements Serializable {
	
	private String template;
	private String nomeBusca;
	private List<Pessoa> listaPessoa;
	private Pessoa pessoaExcluir = new Pessoa();
	private String idPessoaSelecionada;	
	private MembroDao membroDAO = new MembroDao();
	private Conselheiro conselheiroLogado;
	private String tipoBusca;
	private String numeroDocumento;
	private List<Orgao> listaOrgaoBusca = new ArrayList<Orgao>();
	private Orgao orgaoSelecionado;
	private Boolean desativaOrgao;
	
	public CidadaoBuscaBean() {
		template = AutenticaUsuario.verificaTemplate();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();        
        conselheiroLogado = (Conselheiro) session.getAttribute("conselheiroLogado");
	}
	
	@PostConstruct
	public void ini(){
		try {
			listaPessoa = new ArrayList<Pessoa>();	
			listaOrgaoBusca =  new OrgaoDAO().busca();
			desativaOrgao = Boolean.TRUE;					
		} catch (Exception e) {
			System.out.println("Erro ao iniciar Busca Cidadão: "+e.getMessage());
		}		
	}		
	
	public void alteraOrgaoBusca(){
			
		if("RG".equals(tipoBusca)){
			desativaOrgao = Boolean.FALSE;
		}else{
			desativaOrgao = Boolean.TRUE;
		}		
	}
	
	public void buscarCidadao(){

		try {						
			
			if("".equals(nomeBusca.trim())){
				JSFUtil.addErrorMessage("Nenhum valor informado. Verifique os dados informados.");
				return;
			}
			
			switch (tipoBusca) {
			case "NOME":
				listaPessoa = new PessoaDAO().buscaPorNome(nomeBusca);
				break;
			
			case "CPF":
				nomeBusca = nomeBusca.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\_", "");
				listaPessoa = new PessoaDAO().buscaCPF(nomeBusca);
				break;
			
			case "RG":
				if(orgaoSelecionado != null){
					listaPessoa = new PessoaDAO().buscaRG(nomeBusca, orgaoSelecionado);
				}else{
					JSFUtil.addErrorMessage("Informe o orgão");
				}				
				break;
			
			case "CrtNasc":
				listaPessoa = new PessoaDAO().buscaCertidao(nomeBusca);
				break;

			default:
				JSFUtil.addErrorMessage("Informe o tipo da busca");
				break;
			}																	
			
			
			Membro aux;
			for (Pessoa pessoa : listaPessoa) {
				aux = new Membro();
				aux = membroDAO.buscaMembro(pessoa);				
				pessoa.setMembro(aux);
			}
									
			
		} catch (Exception e) {
			System.out.println("Erro ao buscar cidadão: "+e.getMessage());
			JSFUtil.addErrorMessage("ERRO ao Buscar: "+e.getMessage());
		}				
	}
	
	public void excluir(ActionEvent event){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! ", "Procedimento Inválido! Nível de acesso não permitido excluir.");
			return;
		}
				
		pessoaExcluir = (Pessoa) event.getComponent().getAttributes().get("pessoaSelecionada");
		System.out.println("Cidadão a ser excluido: "+pessoaExcluir.getNomeCompleto());
		
		try {
			new PessoaDAO().excluir(pessoaExcluir);
			listaPessoa = new ArrayList<Pessoa>();
			JSFUtil.addInfoMessage("Excluido com sucesso!");			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Cidadão cadastrado em procedimento, por este motivo não pode ser excluido. ");
			System.out.println("Erro ao excluir cidadão: "+e.getMessage());
			e.printStackTrace();
		}
						
	}	
	
	public String preparaMostraDados(){
		
		try {
			
			System.out.println("ID SELECIONADO PARA EDITAR TELA DE BUSCA: "+idPessoaSelecionada);
			
		} catch (Exception e) {
			System.out.println("Erro ao salvar: "+e.getMessage());
			JSFUtil.addErrorMessage("ERRO ao Buscar: "+e.getMessage());
		}		
		return "cidadao_cadastro?faces-redirect=true&c="+idPessoaSelecionada;		
	}
	
	
	public String preparaMostraNucleoFamiliar(){
		
		try {
			
			System.out.println("ID SELECIONADO PARA EDITAR TELA DE BUSCA: "+idPessoaSelecionada);
			
		} catch (Exception e) {
			System.out.println("Erro ao salvar: "+e.getMessage());
			JSFUtil.addErrorMessage("ERRO ao Buscar: "+e.getMessage());
		}		
		return "familia_cadastro?faces-redirect=true&c="+idPessoaSelecionada;		
	}
	
	public String getNomeBusca() {
		return nomeBusca;
	}
	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}
	public List<Pessoa> getListaPessoa() {
		return listaPessoa;
	}
	public void setListaPessoa(List<Pessoa> listaPessoa) {
		this.listaPessoa = listaPessoa;
	}
	public String getIdPessoaSelecionada() {
		return idPessoaSelecionada;
	}
	public void setIdPessoaSelecionada(String idPessoaSelecionada) {
		this.idPessoaSelecionada = idPessoaSelecionada;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}	

	public String getTipoBusca() {
		return tipoBusca;
	}

	public void setTipoBusca(String tipoBusca) {
		this.tipoBusca = tipoBusca;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public List<Orgao> getListaOrgaoBusca() {
		return listaOrgaoBusca;
	}

	public void setListaOrgaoBusca(List<Orgao> listaOrgaoBusca) {
		this.listaOrgaoBusca = listaOrgaoBusca;
	}

	public Orgao getOrgaoSelecionado() {
		return orgaoSelecionado;
	}

	public void setOrgaoSelecionado(Orgao orgaoSelecionado) {
		this.orgaoSelecionado = orgaoSelecionado;
	}

	public Boolean getDesativaOrgao() {
		return desativaOrgao;
	}

	public void setDesativaOrgao(Boolean desativaOrgao) {
		this.desativaOrgao = desativaOrgao;
	}	
}
