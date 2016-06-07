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
import br.com.conselho.util.CpfValidator;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBResponsavelCadastro")
@ViewScoped
public class ResponsavelCadastroBean<SituacaoMatrimonial> implements Serializable {
	
	private List<GrauParentesco> listaParentesco = null;
	private List<GrauResponsabilidade> listaResponsabilidade = null;
	private Boolean rendCadastro;
	private String tipoBusca = null;
	private CriancaAdolescente criancaSelecionada = null;
	private String nomeCriancaSelecionado;
	private List<Logradouro> listaLogradouro;
	private Bairro bairroSelecionado = null;	
	
	private List<Estado> listaEstado;
	private Estado estadoSelecionado = null;
	private Cidade cidadeSelecionado;	
	private List<Cidade> listaCidade = new ArrayList<Cidade>();
	private List<Bairro> listaBairro = new ArrayList<Bairro>();
	private String documentoBusca = null;
	
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
	private List<Orgao> listaOrgaoBusca = new ArrayList<Orgao>();
	private Orgao orgaoSelecionado = null;
	private Boolean desativaOrgao;
	private Orgao orgaoBuscaSelecionado = null;
	private Long id = null;

	
	
	@PostConstruct
	public void ini(){
		rendCadastro = Boolean.FALSE;
		tipoBusca = "CPF";
		desativaOrgao = Boolean.TRUE;
		listaParentesco = new ArrayList<GrauParentesco>();
		try {
			listaParentesco = new GrauParentescoDAO().listaGrauParentescos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
				
		listaResponsabilidade = new ArrayList<GrauResponsabilidade>();
		try {
			listaResponsabilidade = new GrauResponsabilidadeDAO().listaGrauResponsabilidade();					
		} catch (Exception e) {
			e.printStackTrace();
		}
							
		listaEstado = new ArrayList<Estado>();
		try {
			listaEstado = new EstadoDAO().listaEstados();
		} catch (Exception e1) {			
			e1.printStackTrace();
			System.out.println("Erro ao carregar lista de estado: "+e1.getMessage());
		}
		
		listaOrgao = new ArrayList<Orgao>();
		listaOrgaoBusca = new ArrayList<Orgao>();
		try {
			listaOrgao = new OrgaoDAO().busca();
			listaOrgaoBusca =  new OrgaoDAO().busca();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
	
	public void alteraOrgaoBusca(){
		
		if("CPF".equals(tipoBusca)){
			desativaOrgao = Boolean.TRUE;
		}else if("RG".equals(tipoBusca)){
			desativaOrgao = Boolean.FALSE;
		}
		
	}
	
	public void buscaPessoa(){
		
		rendCadastro = Boolean.FALSE;
		nome = "";
		cpf = "";
		rg = "";
		orgaoSelecionado = null;
		foneI = "";
		foneII = "";
		localDeTrabalho = "";
		foneTrabalho = "";						
		situacaoMatrimonial = "";
		
		estadoSelecionado = null;
		cidadeSelecionado = null;
		bairroSelecionado = null;
		logradouroSelecionado = null;
		numero = "";
		complemento = "";
		cep = "";
		id = null;
		
		
		if(!"".equals(tipoBusca)){
			if(documentoBusca == null || "".equals(documentoBusca)){
				JSFUtil.addErrorMessage("Informe o numero do documento!");
			}else{
				try {
					if("RG".equals(tipoBusca) && orgaoBuscaSelecionado == null){
						JSFUtil.addErrorMessage("Informe o Orgão para buscar por RG");
						return;
					}else if("CPF".equals(tipoBusca)){
						if(CpfValidator.validate(documentoBusca)){
							JSFUtil.addErrorMessage("CPF informado é inválido!!");
							return;
						}
					}else if("RG".equals(tipoBusca)){
						if(documentoBusca.length() < 7){
							JSFUtil.addErrorMessage("Informe o RG corretamente!");
							return;
						}
					}
				
					Pessoa pessoa = new PessoaDAO().buscaMaior(tipoBusca, documentoBusca, orgaoBuscaSelecionado);
					
					if(!new ResponsavelDAO().buscaResponsavelPorCrianca(pessoa, criancaSelecionada).isEmpty()){
						JSFUtil.addErrorMessage("Pessoa já vinculada como responsavel!");
						return;
					}
					
					if(pessoa != null){
						
						id = pessoa.getId();
						nome = pessoa.getNomeCompleto();
						cpf = pessoa.getCpf();
						rg = pessoa.getRg();
						orgaoSelecionado = pessoa.getOrgao();
						foneI = pessoa.getFoneI();
						foneII = pessoa.getFoneII();
						localDeTrabalho = pessoa.getLocalTrabalho();
						foneTrabalho = pessoa.getFoneTrabalho();						
						situacaoMatrimonial = pessoa.getSituacaoMatrimonial();
						
						if(pessoa.getLogradouro() != null){
							estadoSelecionado = pessoa.getLogradouro().getBairro().getCidade().getEstado();
							cidadeSelecionado = pessoa.getLogradouro().getBairro().getCidade();
							bairroSelecionado = pessoa.getLogradouro().getBairro();
							logradouroSelecionado = pessoa.getLogradouro();
							numero = pessoa.getNumero();
							complemento = pessoa.getComplemento();
							cep = pessoa.getLogradouro().getCep().toString();
						}
												
						rendCadastro = Boolean.TRUE;
						
					}else{
						rendCadastro = Boolean.TRUE;
						JSFUtil.addWarnMessage("Responsavel não encontrado. Para segurança, busque por um documento valido cadastrado = CPF ou RG");
					}
										
				} catch (Exception e) {
					JSFUtil.addErrorMessage(e.getMessage());
					System.out.println("ERROOOOO...: "+e.getMessage());
				}															
			}			
		}else{
			JSFUtil.addErrorMessage("Selecione um tipo de busca!!!");
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
		
		try {
			
			if(grauDeParentesco == null){
				JSFUtil.addErrorMessage("Informe o grau de parentesco!");
				return;
			}
			
			if(grauDeResponsabilidade != null){
				List<Responsavel> listResp = responsavelDAO.buscaResponsavelInstituicaoPorCrianca(criancaSelecionada);
				if(!listResp.isEmpty() && !grauDeResponsabilidade.getNome().equals("Nenhum")){
					JSFUtil.addErrorMessage("Esta criança já possui uma instituição como responsável");
					return;
				}else{
					listResp = responsavelDAO.buscaResponsavelPessoaPorCrianca(criancaSelecionada);
					if(listResp.size() >= 2 && !grauDeResponsabilidade.getNome().equals("Nenhum") ){
						JSFUtil.addErrorMessage("Esta criança já possui o numero máximo de pessoas como responsáveis.");
						return;
					}
				}
			}else{
				JSFUtil.addErrorMessage("Informe o grau de responsabilidade!!!");
				return;
			}					
		} catch (Exception e) {
			JSFUtil.addErrorMessage("ERRO ao verificar cadastro responsável");
		}			
		
	try {
		
		if(id != null){
			//edicao. encontrou pessoa por um documento
			pessoa.setId(id);
			
			if("CPF".equals(tipoBusca)){
				
				cpf = documentoBusca;
				
				if(!"".equals(cpf)){								
					List<Pessoa> listaP = new PessoaDAO().buscaRG(rg, orgaoSelecionado);
					if(!listaP.isEmpty()){
						if(listaP.size() > 1){
							JSFUtil.addErrorMessage("RG e órgão informado está duplicado!!");
							return;
						}else{
							Pessoa p = listaP.get(0);
							if(!cpf.equals(p.getCpf())){
								JSFUtil.addErrorMessage("RG e órgão informado está cadastrado em outra pessoa!");
								return;
							}
						}
					}
				}
				
			}else if("RG".equals(tipoBusca)){
				
				rg = documentoBusca;
				orgaoSelecionado = orgaoBuscaSelecionado;
				if(!"".equals(rg)){
					if(orgaoSelecionado == null){
						JSFUtil.addErrorMessage("Informe o órgao do RG informado!!");
						return;
					}
					List<Pessoa> listaP = new PessoaDAO().buscaCPF(cpf.replaceAll("\\.", "").replaceAll("-", ""));
					if(!listaP.isEmpty()){
						
						if(listaP.size() > 1){
							JSFUtil.addErrorMessage("CPF informado está duplicado!!");
							return;
						}else{
							Pessoa p = listaP.get(0);
							if(p.getRg().equals(rg) && p.getOrgao().equals(p.getOrgao())){
								JSFUtil.addErrorMessage("CPF informado está cadastrado em outra pessoa!!");
								return;
							}
						}					
					}
				}									
			}										
			
		}else{
			// novo cadastro de pessoa não encontrou com o documento informado.					
			if("CPF".equals(tipoBusca)){
				
				cpf = documentoBusca;
				
				if(!"".equals(cpf)){
					if(!"".equals(rg) || orgaoBuscaSelecionado != null){
						List<Pessoa> listaP = new PessoaDAO().buscaRG(rg, orgaoSelecionado);
						if(!listaP.isEmpty()){
							JSFUtil.addErrorMessage("RG e órgão emissor informado já está cadastrado no sistema!!");
							return;
						}
					}					
				}
				
			}else if("RG".equals(tipoBusca)){
				
				rg = documentoBusca;
				orgaoSelecionado = orgaoBuscaSelecionado;
				
				if(!"".equals(rg)){
					if(orgaoSelecionado == null){
						JSFUtil.addErrorMessage("Informe o órgão do RG informado!");
						return;
					}
					if(!"".equals(cpf)){
						List<Pessoa> listaP = new PessoaDAO().buscaCPF(cpf.replaceAll("\\.", "").replaceAll("-", ""));
						if(!listaP.isEmpty()){
							JSFUtil.addErrorMessage("CPF informado já está cadastrado no sistema!");
							return;
						}
					}					
				}							
			}			
		}
		
		
		if("".equals(rg) && "".equals(cpf)){			
			JSFUtil.addErrorMessage("Informe o RG ou CPF!");
			return;
		}else{
			
			if(!"".equals(rg)){
				if(orgaoSelecionado == null){
					JSFUtil.addErrorMessage("Informe o órgao emissor do RG !");
					return;
				}
			}
		}
												
		
		pessoa.setNomeCompleto(nome);
		pessoa.setCpf(cpf.replaceAll("\\.", "").replaceAll("-", ""));
		pessoa.setRg(rg);
		//pessoa.setOrgao(orgaoEmissor);
		pessoa.setFoneI(foneI.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", ""));
		pessoa.setFoneII(foneII.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", ""));
		pessoa.setLocalTrabalho(localDeTrabalho);
		pessoa.setFoneTrabalho(foneTrabalho.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", ""));
		pessoa.setSituacaoMatrimonial(situacaoMatrimonial);
		pessoa.setLogradouro(logradouroSelecionado);
		pessoa.setNumero(numero);
		pessoa.setComplemento(complemento);
		pessoa.setDataCadastro(new Date());
		pessoa.setOrgao(orgaoSelecionado);
		
		
			
			if(CpfValidator.validate(cpf)){
				JSFUtil.addErrorMessage("ERRO! ", "CPF INVÁLIDO");
				return;
			}
			// salvando pessoa
			Pessoa pessoaRetorno = new PessoaDAO().salvarSemVerificacaoExistencia(pessoa);
			
			//salvando como parente
			Parente parente = new Parente();			
			parente.setParente(pessoaRetorno);
			parente.setCriancaAdolescente(criancaSelecionada);
			parente.setGrauParentesco(grauDeParentesco);			
			new ParenteDAO().salvar(parente);					
			
			//salvando como responsavel
			Responsavel responsavel = new Responsavel();			
			responsavel.setPessoa(pessoaRetorno);
			responsavel.setGrauResponsabilidade(grauDeResponsabilidade);
			responsavel.setCriancaAdolescente(criancaSelecionada);			
			new ResponsavelDAO().salvar(responsavel);
			
			
			JSFUtil.addInfoMessage("Responsável salvo com sucesso!!!");
			
			rendCadastro = Boolean.FALSE;
			nome = "";
			cpf = "";
			rg = "";
			orgaoSelecionado = null;
			foneI = "";
			foneII = "";
			localDeTrabalho = "";
			foneTrabalho = "";						
			situacaoMatrimonial = "";
			
			estadoSelecionado = null;
			cidadeSelecionado = null;
			bairroSelecionado = null;
			logradouroSelecionado = null;
			numero = "";
			complemento = "";
			cep = "";
			id = null;
			
			
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


	public Boolean getRendCadastro() {
		return rendCadastro;
	}


	public void setRendCadastro(Boolean rendCadastro) {
		this.rendCadastro = rendCadastro;
	}

	public String getTipoBusca() {
		return tipoBusca;
	}

	public void setTipoBusca(String tipoBusca) {
		this.tipoBusca = tipoBusca;
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


	public String getDocumentoBusca() {
		return documentoBusca;
	}


	public void setDocumentoBusca(String documentoBusca) {
		this.documentoBusca = documentoBusca;
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


	public List<Orgao> getListaOrgaoBusca() {
		return listaOrgaoBusca;
	}


	public void setListaOrgaoBusca(List<Orgao> listaOrgaoBusca) {
		this.listaOrgaoBusca = listaOrgaoBusca;
	}

	public Boolean getDesativaOrgao() {
		return desativaOrgao;
	}

	public void setDesativaOrgao(Boolean desativaOrgao) {
		this.desativaOrgao = desativaOrgao;
	}

	public Orgao getOrgaoBuscaSelecionado() {
		return orgaoBuscaSelecionado;
	}

	public void setOrgaoBuscaSelecionado(Orgao orgaoBuscaSelecionado) {
		this.orgaoBuscaSelecionado = orgaoBuscaSelecionado;
	}				
}
