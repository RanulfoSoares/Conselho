package br.com.conselho.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.conselho.dao.CriancaAdolescenteDAO;
import br.com.conselho.dao.EstadoDAO;
import br.com.conselho.dao.GrauEscolaridadeDAO;
import br.com.conselho.dao.GrauParentescoDAO;
import br.com.conselho.dao.GrauResponsabilidadeDAO;
import br.com.conselho.dao.InstituicaoDAO;
import br.com.conselho.dao.OrgaoDAO;
import br.com.conselho.dao.ParenteDAO;
import br.com.conselho.dao.PessoaDAO;
import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.Estado;
import br.com.conselho.domain.GrauEscolaridade;
import br.com.conselho.domain.GrauParentesco;
import br.com.conselho.domain.GrauResponsabilidade;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Orgao;
import br.com.conselho.domain.Parente;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.util.CpfValidator;
import br.com.conselho.util.Helper;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBParenteCadastro")
@ViewScoped
public class ParenteCadastroBean implements Serializable {
					
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");				
	private CriancaAdolescente criancaSelecionada = null;	
	private String tipoBusca = null;
	private Boolean renderizaGridPessoa;
	private Boolean renderizaOrgao;
	private String nomeCriancaSelecionado;
	private Orgao orgaoSelecionado = new Orgao();
	private CriancaAdolescente criancaParente = new CriancaAdolescente();
	private String documentoBusca;
	private List<Orgao> listaOrgaoBusca;
	private List<GrauParentesco> listaParentesco = null;
	private GrauParentesco grauDeParentescoSelecionado = null;
	private String dataCadastroFormatada;
	private String cor;
	private String dataNascFormatada;
	
	
	@PostConstruct
	public void iniCadastro(){
				
		tipoBusca = "CrtNasc";
		renderizaGridPessoa = Boolean.FALSE;					
		renderizaOrgao = Boolean.FALSE;
		listaOrgaoBusca = new ArrayList<Orgao>();
		listaParentesco = new ArrayList<GrauParentesco>();
		
		try {
			
			listaOrgaoBusca = new OrgaoDAO().busca();
			listaParentesco = new GrauParentescoDAO().listaGrauParentescos();
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
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao carregar parametros da pagina");
		}		
	}	
	
	public void buscaPessoa(){
		
		if(!"".equals(documentoBusca)){														
			if(tipoBusca.equals("RG") && orgaoSelecionado == null){
				JSFUtil.addErrorMessage("Informe o orgão do RG");
			}else{
				try {
					criancaParente = new CriancaAdolescenteDAO().buscaCrianca(tipoBusca, documentoBusca, orgaoSelecionado);
					
					if(criancaParente != null){
						
						if(Helper.executaCalculoIdade(criancaParente.getPessoa().getDataNascimento()) < 18){													
						
							Parente parente = new ParenteDAO().buscaParente(criancaSelecionada, criancaParente.getPessoa());
						
							if(parente == null){
								dataCadastroFormatada = formato.format(criancaParente.getPessoa().getDataCadastro());
								dataNascFormatada = formato.format(criancaParente.getPessoa().getDataNascimento());
								
								switch (criancaParente.getPessoa().getCor()) {
								case "AM":
									cor = "Amarela"; 
									break;
								case "BR":
									cor = "Branca";	
									break;
								case "IN":
									cor = "Indígena";	
									break;
								case "PA":
									cor = "Parda";	
									break;
								case "PR":
									cor = "Preta";	
									break;
	
								default:
									break;
								}
								
								renderizaGridPessoa = Boolean.TRUE;
							}else{
								JSFUtil.addErrorMessage("ATENÇÃO...: Crianças e adolescentes já vinculadas");
							}
						}else{
							JSFUtil.addErrorMessage("Este documento pertence a uma pessoa adulta");
						}
					}else{
						JSFUtil.addErrorMessage("Criança não vinculada a este documento ou pertence a adulto. Verifique com outro documento.");
					}
				} catch (Exception e) {
					JSFUtil.addErrorMessage("ERROO.... "+e.getMessage());
					e.printStackTrace();
				}
			}
		}else{
			JSFUtil.addErrorMessage("Informe o numero do documento");
		}				
	}	
	
	public String navegaParenteMenor(){
		
		System.out.println("entrou no metodo redireciona responsavel... selecionado id: "+criancaSelecionada.getId());
		
		return "irmao_busca?faces-redirect=true&c="+criancaSelecionada.getId();
		
	}
	
	public void criaVinculo(){
		
		if(grauDeParentescoSelecionado != null){
						
			try {
				Parente parente = new Parente();
				parente.setCriancaAdolescente(criancaSelecionada);
				parente.setParente(criancaParente.getPessoa());
				parente.setGrauParentesco(grauDeParentescoSelecionado);				
				new ParenteDAO().salvar(parente);
				JSFUtil.addInfoMessage("Vinculo criado com sucesso!!!");
				renderizaGridPessoa = Boolean.FALSE;
			} catch (Exception e) {
				JSFUtil.addErrorMessage("Erro ao salvar parente :"+e.getMessage());
				e.printStackTrace();
			}
			
		}else{
			JSFUtil.addErrorMessage("Informe o grau de parentesco");
		}
					
		System.out.println("Parente selecionado: "+criancaParente.getPessoa().getNomeCompleto());		
		
	}
	
	public void alteraComboBusca(){
		
		if("CPF".equals(tipoBusca)){
			renderizaOrgao = Boolean.FALSE;			
		}else if("RG".equals(tipoBusca)){
			renderizaOrgao = Boolean.TRUE;			
		}else if("CrtNasc".equals(tipoBusca)){
			renderizaOrgao = Boolean.FALSE;			
		}
		
	}

	public String getFormato() {
		return formato.format(new Date());
	}


	public String getTipoBusca() {
		return tipoBusca;
	}

	public void setTipoBusca(String tipoBusca) {
		this.tipoBusca = tipoBusca;
	}

	public Boolean getRenderizaGridPessoa() {
		return renderizaGridPessoa;
	}

	public void setRenderizaGridPessoa(Boolean renderizaGridPessoa) {
		this.renderizaGridPessoa = renderizaGridPessoa;
	}

	public Boolean getRenderizaOrgao() {
		return renderizaOrgao;
	}

	public void setRenderizaOrgao(Boolean renderizaOrgao) {
		this.renderizaOrgao = renderizaOrgao;
	}

	public String getNomeCriancaSelecionado() {
		return nomeCriancaSelecionado;
	}

	public void setNomeCriancaSelecionado(String nomeCriancaSelecionado) {
		this.nomeCriancaSelecionado = nomeCriancaSelecionado;
	}

	public Orgao getOrgaoSelecionado() {
		return orgaoSelecionado;
	}

	public void setOrgaoSelecionado(Orgao orgaoSelecionado) {
		this.orgaoSelecionado = orgaoSelecionado;
	}	

	public String getDocumentoBusca() {
		return documentoBusca;
	}

	public void setDocumentoBusca(String documentoBusca) {
		this.documentoBusca = documentoBusca;
	}

	public CriancaAdolescente getCriancaParente() {
		return criancaParente;
	}

	public void setCriancaParente(CriancaAdolescente criancaParente) {
		this.criancaParente = criancaParente;
	}

	public List<Orgao> getListaOrgaoBusca() {
		return listaOrgaoBusca;
	}

	public void setListaOrgaoBusca(List<Orgao> listaOrgaoBusca) {
		this.listaOrgaoBusca = listaOrgaoBusca;
	}

	public String getDataCadastroFormatada() {
		return dataCadastroFormatada;
	}

	public String getCor() {
		return cor;
	}	

	public String getDataNascFormatada() {
		return dataNascFormatada;
	}

	public List<GrauParentesco> getListaParentesco() {
		return listaParentesco;
	}

	public void setListaParentesco(List<GrauParentesco> listaParentesco) {
		this.listaParentesco = listaParentesco;
	}

	public GrauParentesco getGrauDeParentescoSelecionado() {
		return grauDeParentescoSelecionado;
	}

	public void setGrauDeParentescoSelecionado(
			GrauParentesco grauDeParentescoSelecionado) {
		this.grauDeParentescoSelecionado = grauDeParentescoSelecionado;
	}	
}
