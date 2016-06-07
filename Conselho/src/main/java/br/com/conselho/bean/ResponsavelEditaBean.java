package br.com.conselho.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javassist.runtime.Cflow;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.conselho.dao.BairroDAO;
import br.com.conselho.dao.CidadeDAO;
import br.com.conselho.dao.CriancaAdolescenteDAO;
import br.com.conselho.dao.EstadoDAO;
import br.com.conselho.dao.GrauParentescoDAO;
import br.com.conselho.dao.GrauResponsabilidadeDAO;
import br.com.conselho.dao.LogradouroDAO;
import br.com.conselho.dao.OrgaoDAO;
import br.com.conselho.dao.ParenteDAO;
import br.com.conselho.dao.PessoaDAO;
import br.com.conselho.dao.ResponsavelDAO;
import br.com.conselho.dao.SituacaoMatrimonialDAO;
import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.Estado;
import br.com.conselho.domain.GrauParentesco;
import br.com.conselho.domain.GrauResponsabilidade;
import br.com.conselho.domain.Logradouro;
import br.com.conselho.domain.Orgao;
import br.com.conselho.domain.Parente;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.domain.Responsavel;
import br.com.conselho.domain.auxiliotela.ResponsavelTela;
import br.com.conselho.util.CpfValidator;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBResponsavelEdita")
@ViewScoped
public class ResponsavelEditaBean<SituacaoMatrimonial> implements Serializable {
	
	private List<GrauParentesco> listaParentesco = null;
	private List<GrauResponsabilidade> listaResponsabilidade = null;
	private CriancaAdolescente criancaSelecionada = null;
	private String nomeCriancaSelecionado;
	private List<Logradouro> listaLogradouro;
	private Bairro bairroSelecionado = null;	
	
	private List<Estado> listaEstado;
	private Estado estadoSelecionado = null;
	private Cidade cidadeSelecionado;	
	private List<Cidade> listaCidade = new ArrayList<Cidade>();
	private List<Bairro> listaBairro = new ArrayList<Bairro>();
	
	private String nome;
	private String cpf;
	private String rg;
	private String orgaoEmissor;
	private String foneI;
	private String foneII;
	private String localDeTrabalho;
	private String foneTrabalho;
	private String numero;
	private String cep;
	private String complemento;
	private String situacaoMatrimonial;
	private GrauParentesco grauDeParentesco;
	private GrauResponsabilidade grauDeResponsabilidade = null;
	private Logradouro logradouroSelecionado;
	private List<Orgao> listaOrgao = new ArrayList<Orgao>();
	private Orgao orgaoSelecionado = null;
	private Long id = null;	
	private ResponsavelTela responsavelTela = null;
	private Responsavel responsavel = null;
	
	private ResponsavelDAO responsavelDAO = new ResponsavelDAO();
	
	
	@PostConstruct
	public void ini(){

		listaParentesco = new ArrayList<GrauParentesco>();
		listaResponsabilidade = new ArrayList<GrauResponsabilidade>();
		listaEstado = new ArrayList<Estado>();		
		listaOrgao = new ArrayList<Orgao>();
		
		try {
			listaParentesco = new GrauParentescoDAO().listaGrauParentescos();
			listaResponsabilidade = new GrauResponsabilidadeDAO().listaGrauResponsabilidade();
			listaEstado = new EstadoDAO().listaEstados();
			listaOrgao = new OrgaoDAO().busca();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		
		String idCrianca = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("c");
		String idResponsavel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("r");
				
		
		if(idCrianca != null && idResponsavel != null){
			try {
				
				criancaSelecionada = new CriancaAdolescenteDAO().buscaCrianca(Long.parseLong(idCrianca));
				nomeCriancaSelecionado = criancaSelecionada.getPessoa().getNomeCompleto();							
				responsavel = responsavelDAO.buscaResponsavel(Long.parseLong(idResponsavel));				
				responsavelTela = new ResponsavelTela();				
				responsavelTela.setId(responsavel.getId());
				responsavelTela.setCrianca(responsavel.getCriancaAdolescente());
				responsavelTela.setGrauResponsabilidade(responsavel.getGrauResponsabilidade());
				
				if(responsavel.getPessoa() != null){
					responsavelTela.setEditavel(Boolean.TRUE);
					responsavelTela.setPessoa(responsavel.getPessoa());
					Parente parente = new ParenteDAO().buscaParente(criancaSelecionada, responsavel.getPessoa());
					responsavelTela.setParente(parente);
				}			
				
				
				id = responsavelTela.getId();
				nome = responsavelTela.getPessoa().getNomeCompleto();
				cpf = responsavelTela.getPessoa().getCpf();
				rg = responsavelTela.getPessoa().getRg();
				orgaoSelecionado = responsavelTela.getPessoa().getOrgao();
				foneI = responsavelTela.getPessoa().getFoneI();
				foneII = responsavelTela.getPessoa().getFoneII();
				localDeTrabalho = responsavelTela.getPessoa().getLocalTrabalho();
				foneTrabalho = responsavelTela.getPessoa().getFoneTrabalho();						
				situacaoMatrimonial = responsavelTela.getPessoa().getSituacaoMatrimonial();
				grauDeParentesco = responsavelTela.getParente().getGrauParentesco();
				grauDeResponsabilidade = responsavelTela.getGrauResponsabilidade();						
				
				if(responsavelTela.getPessoa().getLogradouro() != null){
					estadoSelecionado = responsavelTela.getPessoa().getLogradouro().getBairro().getCidade().getEstado();
					cidadeSelecionado = responsavelTela.getPessoa().getLogradouro().getBairro().getCidade();
					bairroSelecionado = responsavelTela.getPessoa().getLogradouro().getBairro();
					logradouroSelecionado = responsavelTela.getPessoa().getLogradouro();
					numero = responsavelTela.getPessoa().getNumero();
					complemento = responsavelTela.getPessoa().getComplemento();
					cep = responsavelTela.getPessoa().getLogradouro().getCep().toString();
					
					buscaCidade();
					buscaBairro();
					buscaLogradouro();
				}
											
				
			} catch (Exception e) {
				System.out.println("ERRO....: "+e.getMessage());
				e.printStackTrace();
			}
		
		}else{
			
		
		}
		
	}
	
	
	public void buscaCidade(){
		
		try {
			listaCidade = new CidadeDAO().buscaCidade(estadoSelecionado);
			
			if(!listaBairro.isEmpty()){
				listaBairro = new ArrayList<Bairro>();
			}
			
			if(!listaLogradouro.isEmpty()){
				listaLogradouro = new ArrayList<Logradouro>();
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void buscaBairro(){									
					
		try {
			listaBairro = new BairroDAO().buscaBairro(cidadeSelecionado);
			
			if(!listaLogradouro.isEmpty()){
				listaLogradouro = new ArrayList<Logradouro>();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
	}
	
	public void buscaLogradouro(){
		
		System.out.println("Busca bairro!!!!... bairro selecionado "+bairroSelecionado.getNome());
		try {
									
			listaLogradouro = new LogradouroDAO().buscaLogradouro(bairroSelecionado);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}				
	}
	
	public String navegaResponsavel(){
		
		System.out.println("entrou no metodo redireciona responsavel... selecionado id: "+criancaSelecionada.getId());
		
		return "responsavel_busca?faces-redirect=true&c="+criancaSelecionada.getId();
		
	}
	
	public void salvar(){						
		
		Pessoa pessoa = new Pessoa();
		ResponsavelDAO responsavelDAO = new ResponsavelDAO();					
														
		try{
			
			if("".equals(nome)){
				JSFUtil.addErrorMessage("Informe o nome do responsavel!");
				return;
			}
			
			if("".equals(rg) && "".equals(cpf)){			
				JSFUtil.addErrorMessage("Informe o RG ou CPF!");
				return;
			}else{
				
				if(!"".equals(rg)){
					if(orgaoSelecionado == null){
						JSFUtil.addErrorMessage("Informe o orgao emissor do RG !");
						return;
					}
				}else if(!"".equals(cpf)){
					if(CpfValidator.validate(cpf)){
						JSFUtil.addErrorMessage("ERRO! ", "CPF INVÁLIDO");
						return;
					}
				}
			}
			
			if(!"".equals(cpf)){								
				List<Pessoa> listaP = new PessoaDAO().buscaCPF(cpf.replaceAll("\\.", "").replaceAll("-", ""));
				if(!listaP.isEmpty()){
					if(listaP.size() > 1){
						JSFUtil.addErrorMessage("CPF informado esta duplicado!!");
						return;
					}else{
						Pessoa p = listaP.get(0);
						if(!responsavelTela.getPessoa().getId().equals(p.getId())){
							JSFUtil.addErrorMessage("CPF informado esta cadastrado em outra pessoa!");
							return;
						}
					}
				}
			}
			
			if(grauDeParentesco == null){
				JSFUtil.addErrorMessage("Informe o grau de parentesco!");
				return;
			}
			
			if(grauDeResponsabilidade != null){
				//não precisa tratar o id do responsavel pq ele sempre vai editar uma pessoa
				List<Responsavel> listResp = responsavelDAO.buscaResponsavelInstituicaoPorCrianca(criancaSelecionada);
				if(!listResp.isEmpty() && !grauDeResponsabilidade.getNome().equals("Nenhum")){
					JSFUtil.addErrorMessage("Esta criança já possui uma instituição como responsável");
					return;
				}else{
					listResp = responsavelDAO.buscaResponsavelPessoaPorCrianca(criancaSelecionada);
					if(listResp.size() >= 2 && !grauDeResponsabilidade.getNome().equals("Nenhum")){
						Boolean bloqueia = Boolean.TRUE;
						for (Responsavel responsavel : listResp) {
							if(responsavel.getId().equals(responsavelTela.getId())){
								bloqueia = Boolean.FALSE;
							}													
						}
						
						if(bloqueia){
							JSFUtil.addErrorMessage("Esta criança já possui o numero máximo de pessoas como responsáveis.");
							return;
						}											
					}
				}
			}else{
				JSFUtil.addErrorMessage("Informe o grau de responsabilidade!!!");
				return;
			}														
						
				
			pessoa.setId(responsavelTela.getPessoa().getId());
			
			pessoa.setNomeCompleto(nome);
			pessoa.setCpf(cpf.replaceAll("\\.", "").replaceAll("-", ""));
			pessoa.setRg(rg);
			pessoa.setOrgao(orgaoSelecionado);
			pessoa.setFoneI(foneI.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", ""));
			pessoa.setFoneII(foneII.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", ""));
			pessoa.setLocalTrabalho(localDeTrabalho);
			pessoa.setFoneTrabalho(foneTrabalho.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", ""));
			pessoa.setSituacaoMatrimonial(situacaoMatrimonial);
			pessoa.setLogradouro(logradouroSelecionado);
			pessoa.setNumero(numero);
			pessoa.setComplemento(complemento);		
			pessoa.setOrgao(orgaoSelecionado);
			pessoa.setDataCadastro(responsavelTela.getPessoa().getDataCadastro());
								
			// salvando pessoa
			Pessoa pessoaRetorno = new PessoaDAO().salvarSemVerificacaoExistencia(pessoa);
				
			//salvando como parente
			Parente parente = new Parente();
			parente.setId(responsavelTela.getParente().getId());
				
			parente.setParente(pessoaRetorno);
			parente.setCriancaAdolescente(criancaSelecionada);
			parente.setGrauParentesco(grauDeParentesco);			
			new ParenteDAO().salvar(parente);					
				
			//salvando como responsavel
			Responsavel responsavel = new Responsavel();
			responsavel.setId(responsavelTela.getId());
				
			responsavel.setPessoa(pessoaRetorno);
			responsavel.setGrauResponsabilidade(grauDeResponsabilidade);
			responsavel.setCriancaAdolescente(criancaSelecionada);			
			new ResponsavelDAO().salvar(responsavel);			
			
			JSFUtil.addInfoMessage("Responsavel editado com sucesso!!!");
					
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Pessoa não vinculado como responsável: "+e.getMessage());
			e.printStackTrace();
		}					
	}
	
	public void validaCPF(){
		
		try {
			if(CpfValidator.validate(cpf)){
				JSFUtil.addErrorMessage("ERRO! ", "CPF INVÁLIDO");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<GrauParentesco> getListaParentesco() {
		return listaParentesco;
	}

	public void setListaParentesco(List<GrauParentesco> listaParentesco) {
		this.listaParentesco = listaParentesco;
	}


	public List<GrauResponsabilidade> getListaResponsabilidade() {
		return listaResponsabilidade;
	}


	public void setListaResponsabilidade(
			List<GrauResponsabilidade> listaResponsabilidade) {
		this.listaResponsabilidade = listaResponsabilidade;
	}

	public String getNomeCriancaSelecionado() {
		return nomeCriancaSelecionado;
	}

	public void setNomeCriancaSelecionado(String nomeCriancaSelecionado) {
		this.nomeCriancaSelecionado = nomeCriancaSelecionado;
	}	


	public List<Bairro> getListaBairro() {
		return listaBairro;
	}

	public void setListaBairro(List<Bairro> listaBairro) {
		this.listaBairro = listaBairro;
	}


	public List<Estado> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<Estado> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public Estado getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Estado estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public Bairro getBairroSelecionado() {
		return bairroSelecionado;
	}

	public void setBairroSelecionado(Bairro bairroSelecionado) {
		this.bairroSelecionado = bairroSelecionado;
	}


	public Cidade getCidadeSelecionado() {
		return cidadeSelecionado;
	}


	public void setCidadeSelecionado(Cidade cidadeSelecionado) {
		this.cidadeSelecionado = cidadeSelecionado;
	}


	public List<Logradouro> getListaLogradouro() {
		return listaLogradouro;
	}


	public void setListaLogradouro(List<Logradouro> listaLogradouro) {
		this.listaLogradouro = listaLogradouro;
	}
	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public String getRg() {
		return rg;
	}


	public void setRg(String rg) {
		this.rg = rg;
	}


	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}


	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}


	public String getFoneI() {
		return foneI;
	}


	public void setFoneI(String foneI) {
		this.foneI = foneI;
	}


	public String getFoneII() {
		return foneII;
	}


	public void setFoneII(String foneII) {
		this.foneII = foneII;
	}


	public String getLocalDeTrabalho() {
		return localDeTrabalho;
	}


	public void setLocalDeTrabalho(String localDeTrabalho) {
		this.localDeTrabalho = localDeTrabalho;
	}


	public String getFoneTrabalho() {
		return foneTrabalho;
	}


	public void setFoneTrabalho(String foneTrabalho) {
		this.foneTrabalho = foneTrabalho;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getComplemento() {
		return complemento;
	}


	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public String getSituacaoMatrimonial() {
		return situacaoMatrimonial;
	}


	public void setSituacaoMatrimonial(String situacaoMatrimonial) {
		this.situacaoMatrimonial = situacaoMatrimonial;
	}
		

	public GrauParentesco getGrauDeParentesco() {
		return grauDeParentesco;
	}


	public void setGrauDeParentesco(GrauParentesco grauDeParentesco) {
		this.grauDeParentesco = grauDeParentesco;
	}


	public GrauResponsabilidade getGrauDeResponsabilidade() {
		return grauDeResponsabilidade;
	}


	public void setGrauDeResponsabilidade(
			GrauResponsabilidade grauDeResponsabilidade) {
		this.grauDeResponsabilidade = grauDeResponsabilidade;
	}


	public Logradouro getLogradouroSelecionado() {
		return logradouroSelecionado;
	}


	public void setLogradouroSelecionado(Logradouro logradouroSelecionado) {
		this.logradouroSelecionado = logradouroSelecionado;
	}


	public List<Orgao> getListaOrgao() {
		return listaOrgao;
	}


	public void setListaOrgao(List<Orgao> listaOrgao) {
		this.listaOrgao = listaOrgao;
	}


	public Orgao getOrgaoSelecionado() {
		return orgaoSelecionado;
	}


	public void setOrgaoSelecionado(Orgao orgaoSelecionado) {
		this.orgaoSelecionado = orgaoSelecionado;
	}
				
}
