package br.com.conselho.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.conselho.dao.BairroDAO;
import br.com.conselho.dao.CidadeDAO;
import br.com.conselho.dao.ConselheiroDAO;
import br.com.conselho.dao.EstadoDAO;
import br.com.conselho.dao.GrauEscolaridadeDAO;
import br.com.conselho.dao.InstituicaoDAO;
import br.com.conselho.dao.LogradouroDAO;
import br.com.conselho.dao.MembroDao;
import br.com.conselho.dao.OrgaoDAO;
import br.com.conselho.dao.PessoaDAO;
import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.Conselheiro;
import br.com.conselho.domain.Estado;
import br.com.conselho.domain.GrauEscolaridade;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Logradouro;
import br.com.conselho.domain.Membro;
import br.com.conselho.domain.Orgao;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.util.AutenticaUsuario;
import br.com.conselho.util.CpfValidator;
import br.com.conselho.util.Helper;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBCidadoaCadastro")
@ViewScoped
public class CadastroCidadaoBean implements Serializable {
	
	private String template;
	
	/** campos que serão validados */
	private String rg;
	private Orgao orgaoSelecionado;	
	private String cpf;
	private String certNasc;
	private Date dataNascimento;
	private String idade;
	private String trabalha;
	private Boolean desabilitaLocalTrabalho;
	private String localTrabalho;
	
	private String dataCadatsro;
	private String nomeLogado;			
	
	/** Fim */
	
	private Pessoa pessoaEditar = null;
	private Pessoa pessoa;
	private List<Orgao> listaOrgao = new ArrayList<Orgao>();
	private List<GrauEscolaridade> listaGrauEscolaridade = new ArrayList<GrauEscolaridade>();
	private List<Instituicao> listaInstituicao = new ArrayList<Instituicao>();	
	private Membro membro;
	private Boolean familia;
	private Conselheiro conselheiroLogado;
	private Boolean enderecoPessoa;
	
	private Conselheiro pessoaConselheiro;
	private List<Estado> listaEstado;
	private List<Cidade> listaCidade;
	private List<Bairro> listaBairro = new ArrayList<Bairro>();
	private List<Logradouro> listaLogradouro = new ArrayList<Logradouro>();
	
	private Estado estadoSelecionado;	
	private Cidade cidadeSelecionado;
	private Bairro bairroSelecionado;
	private Logradouro logradouroSelecionado;
	
	private String numero;
	private String complemento;
	
	public CadastroCidadaoBean() {
		template = AutenticaUsuario.verificaTemplate();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();        
        conselheiroLogado = (Conselheiro) session.getAttribute("conselheiroLogado");
	}
	
	@PostConstruct
	public void ini(){
		
		try {
			listaEstado = new EstadoDAO().listaEstados();
			pessoa = new Pessoa();
			listaOrgao = new OrgaoDAO().busca();
			listaGrauEscolaridade = new GrauEscolaridadeDAO().listaGrauEscolaridade();
			listaInstituicao = new InstituicaoDAO().listaEscola();
			desabilitaLocalTrabalho = Boolean.TRUE;
			trabalha = "N";
			dataCadatsro = Helper.formatDate().format(new Date());
			nomeLogado = conselheiroLogado.getNomeUsual();
			familia = Boolean.FALSE;
			enderecoPessoa = Boolean.FALSE;
			verificaEditar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void verificaEditar(){
		
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("c");
		System.out.println("ID Selecionado: "+id);
		
		if(id != null){
			try {
				pessoaEditar = new Pessoa();
				pessoaEditar = new PessoaDAO().buscaCodigo(Long.parseLong(id));
				pessoa = pessoaEditar;				
				rg = pessoa.getRg();
				orgaoSelecionado = pessoa.getOrgao();	
				cpf = pessoa.getCpf();
				certNasc = pessoa.getNumeroCertidaoNascimento();
				dataNascimento = pessoa.getDataNascimento();
				idade = Helper.executaCalculoIdade(pessoa.getDataNascimento()).toString();				
				
				pessoaConselheiro = new ConselheiroDAO().conselheiroPorPessoa(pessoaEditar);
				if(pessoaConselheiro != null){
					enderecoPessoa = Boolean.TRUE;
					estadoSelecionado = pessoaConselheiro.getLogradouro().getBairro().getCidade().getEstado();
					cidadeSelecionado = pessoaConselheiro.getLogradouro().getBairro().getCidade();
					bairroSelecionado = pessoaConselheiro.getLogradouro().getBairro();
					logradouroSelecionado = pessoaConselheiro.getLogradouro();
					numero = pessoaConselheiro.getNumero();
					complemento = pessoaConselheiro.getComplemento();
					buscaCidade();
					buscaBairro();
					buscaLogradouro();
					
				}else if(pessoa.getLogradouro() != null){
					enderecoPessoa = Boolean.TRUE;
					estadoSelecionado = pessoa.getLogradouro().getBairro().getCidade().getEstado();
					cidadeSelecionado = pessoa.getLogradouro().getBairro().getCidade();
					bairroSelecionado = pessoa.getLogradouro().getBairro();
					logradouroSelecionado = pessoa.getLogradouro();
					numero = pessoa.getNumero();
					complemento = pessoa.getComplemento();
					buscaCidade();
					buscaBairro();
					buscaLogradouro();
				}				
				
				membro = new MembroDao().buscaMembro(pessoaEditar);
				if(membro != null){
					familia = Boolean.TRUE;					
				}
				
				
				if(pessoa.getTrabalha()){
					trabalha = "S";
					desabilitaLocalTrabalho = Boolean.FALSE;
				}else{
					trabalha = "N";
					desabilitaLocalTrabalho = Boolean.TRUE;
				}							
				localTrabalho = pessoa.getLocalTrabalho();
				
			} catch (Exception e) {
				System.out.println("Erro ao buscar cidadão: "+e.getMessage());
			}						
		}					
	}
	
	public void alteraCampoTrabalho(){		
		
		if("S".equals(trabalha)){
			desabilitaLocalTrabalho = false;
		}else if("N".equals(trabalha)){
			desabilitaLocalTrabalho = true;
			localTrabalho = "";
		}
	}
	
	public void calculaIdade(){
		
		if(dataNascimento != null){
		  idade	= Helper.executaCalculoIdade(dataNascimento).toString();			
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
	
	public void salvar(){
		
		try {
			
			if(conselheiroLogado.getNivelAcesso().equals("col")){
				JSFUtil.addErrorMessage("ERRO! ", "Procedimento Inválido! Nível de acesso não permitido salvar.");
				return;
			}
			
			/*  */
			if(CpfValidator.validate(cpf)){
				JSFUtil.addErrorMessage("ERRO! ", "CPF INVÁLIDO");
				return;
			}
			
			if("".equals(rg) && "".equals(cpf) && "".equals(certNasc)){				
				JSFUtil.addErrorMessage("Informe o RG ou CPF ou Certidão de Nascimento!");
			}else{
				
				/** verifica se a data de nascimento está preenchida */
				if(dataNascimento == null){					
					JSFUtil.addErrorMessage("Informe a data de nascimento da cidadã(o)!");
					return;
				}
				
				/** verifica se o sexo está preenchido */
				if(pessoa.getSexo() == null){					
					JSFUtil.addErrorMessage("Informe o sexo da cidadã(o)!");
					return;
				}
				
				if(!"".equals(rg)){
					if(orgaoSelecionado == null){
						JSFUtil.addErrorMessage("Informe o órgao emissor do RG !");
						return;
					}
				}
				
				
				if(pessoaEditar != null){
					pessoa.setDataCadastro(pessoaEditar.getDataCadastro());
				}else{
					pessoa.setDataCadastro(new Date());
				}								
				
				if(pessoaConselheiro != null){
					pessoaConselheiro.setLogradouro(logradouroSelecionado);
					pessoaConselheiro.setNumero(numero);
					pessoaConselheiro.setComplemento(complemento);
				}
												
				pessoa.setCpf(cpf.replaceAll("\\.", "").replaceAll("-", ""));
				pessoa.setRg(rg.replaceAll("\\.", "").replaceAll("-", ""));
				pessoa.setNumeroCertidaoNascimento(certNasc);
				pessoa.setDataNascimento(dataNascimento);
				pessoa.setIdade( Integer.parseInt(idade));										
				pessoa.setTrabalha("S".equals(trabalha) ? true : false);
				pessoa.setLocalTrabalho(localTrabalho);
				pessoa.setOrgao(orgaoSelecionado);	
				pessoa.setConselheiroRegistro(conselheiroLogado);
				
				pessoa.setLogradouro(logradouroSelecionado);
				pessoa.setNumero(numero);
				pessoa.setComplemento(complemento);
				
				Pessoa pessoaRetorno = new PessoaDAO().salvar(pessoa);
				
				if(pessoaConselheiro != null){
					new ConselheiroDAO().salvar(pessoaConselheiro);
				}
				
				System.out.println("CPF: "+pessoa.getCpf());
				
				JSFUtil.addInfoMessage("Cidadã(o) salvo com sucesso!!!");
				
				pessoa = new Pessoa();
				desabilitaLocalTrabalho = Boolean.TRUE;
				trabalha = "N";
				rg = "";
				orgaoSelecionado = null; 	
				cpf = "";
				certNasc = "";
				dataNascimento = null;
				idade = "";
				localTrabalho = "";
				estadoSelecionado = null;
				cidadeSelecionado = null;
				bairroSelecionado = null;
				logradouroSelecionado = null;
				numero = "";
				enderecoPessoa = Boolean.FALSE;
			}
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao salvar cidadã(o): "+e.getMessage());
		}		
	}
	
	public String navegaFamilia(){
					
		return "familia_cadastro?faces-redirect=true&c="+membro.getFamilia().getId();		
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

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}		

	public Orgao getOrgaoSelecionado() {
		return orgaoSelecionado;
	}

	public void setOrgaoSelecionado(Orgao orgaoSelecionado) {
		this.orgaoSelecionado = orgaoSelecionado;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public List<Orgao> getListaOrgao() {
		return listaOrgao;
	}


	public void setListaOrgao(List<Orgao> listaOrgao) {
		this.listaOrgao = listaOrgao;
	}


	public String getCertNasc() {
		return certNasc;
	}


	public void setCertNasc(String certNasc) {
		this.certNasc = certNasc;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public List<GrauEscolaridade> getListaGrauEscolaridade() {
		return listaGrauEscolaridade;
	}

	public void setListaGrauEscolaridade(
			List<GrauEscolaridade> listaGrauEscolaridade) {
		this.listaGrauEscolaridade = listaGrauEscolaridade;
	}

	public String getTrabalha() {
		return trabalha;
	}

	public void setTrabalha(String trabalha) {
		this.trabalha = trabalha;
	}

	public List<Instituicao> getListaInstituicao() {
		return listaInstituicao;
	}

	public void setListaInstituicao(List<Instituicao> listaInstituicao) {
		this.listaInstituicao = listaInstituicao;
	}

	public Boolean getDesabilitaLocalTrabalho() {
		return desabilitaLocalTrabalho;
	}

	public void setDesabilitaLocalTrabalho(Boolean desabilitaLocalTrabalho) {
		this.desabilitaLocalTrabalho = desabilitaLocalTrabalho;
	}

	public String getLocalTrabalho() {
		return localTrabalho;
	}

	public void setLocalTrabalho(String localTrabalho) {
		this.localTrabalho = localTrabalho;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getDataCadatsro() {
		return dataCadatsro;
	}

	public void setDataCadatsro(String dataCadatsro) {
		this.dataCadatsro = dataCadatsro;
	}

	public String getNomeLogado() {
		return nomeLogado;
	}

	public void setNomeLogado(String nomeLogado) {
		this.nomeLogado = nomeLogado;
	}

	public Boolean getFamilia() {
		return familia;
	}

	public void setFamilia(Boolean familia) {
		this.familia = familia;
	}

	public Boolean getEnderecoPessoa() {
		return enderecoPessoa;
	}

	public void setEnderecoPessoa(Boolean enderecoPessoa) {
		this.enderecoPessoa = enderecoPessoa;
	}

	public List<Estado> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<Estado> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public List<Bairro> getListaBairro() {
		return listaBairro;
	}

	public void setListaBairro(List<Bairro> listaBairro) {
		this.listaBairro = listaBairro;
	}

	public List<Logradouro> getListaLogradouro() {
		return listaLogradouro;
	}

	public void setListaLogradouro(List<Logradouro> listaLogradouro) {
		this.listaLogradouro = listaLogradouro;
	}

	public Estado getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Estado estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public Cidade getCidadeSelecionado() {
		return cidadeSelecionado;
	}

	public void setCidadeSelecionado(Cidade cidadeSelecionado) {
		this.cidadeSelecionado = cidadeSelecionado;
	}

	public Bairro getBairroSelecionado() {
		return bairroSelecionado;
	}

	public void setBairroSelecionado(Bairro bairroSelecionado) {
		this.bairroSelecionado = bairroSelecionado;
	}

	public Logradouro getLogradouroSelecionado() {
		return logradouroSelecionado;
	}

	public void setLogradouroSelecionado(Logradouro logradouroSelecionado) {
		this.logradouroSelecionado = logradouroSelecionado;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
}
