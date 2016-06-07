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
import br.com.conselho.dao.OrgaoDAO;
import br.com.conselho.dao.PessoaDAO;
import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.Conselheiro;
import br.com.conselho.domain.Estado;
import br.com.conselho.domain.GrauEscolaridade;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Logradouro;
import br.com.conselho.domain.Orgao;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.util.AutenticaUsuario;
import br.com.conselho.util.CpfValidator;
import br.com.conselho.util.Helper;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name="MBConselheiroCadastro")
@ViewScoped
public class ConselheiroCadastroBean implements Serializable{		

	
	private Conselheiro conselheiroLogado;
	private String template;
	private String nomeCompleto;
	private String rg;
	private Orgao orgao;
	private String cpf;
	private String foneI;
	private String foneII;
	private String senha;
	private Logradouro logradouro;
	private String numero;
	
	private String complemento;
	private String sexo;
	private String cor;
	private GrauEscolaridade escolaridade;
	private String situacaoMatrimonial;
	private String email;
	private Date primeiroMandatoInicio;
	private Date primeiroMandatoFim;
	private Date segundoMandatoInicio;
	private Date segundoMandatoFim;
	private String obs;
	private String dataCadastro;
	private String funcao;
	private String nivelAcesso;
	private String nomeUsual;
	private String confirmeSenha;
	private Bairro bairroSelecionado;	
	private List<Orgao> listaOrgao;
	private List<GrauEscolaridade> listaGrauEscolaridade;
	private List<Bairro> listaBairro;
	private List<Logradouro> listaLogradouro;
	private Date dataNascimento;
	private List<Estado> listaEstado;
	private Estado estadoSelecionado;
	private List<Cidade> listaCidade;
	private Cidade cidadeSelecionado;
	
	private Conselheiro conselhoEdita;
	private boolean conselheiroDesativado;
	
	public ConselheiroCadastroBean() {
		template = AutenticaUsuario.verificaTemplate();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();        
        conselheiroLogado = (Conselheiro) session.getAttribute("conselheiroLogado");
	}
	
	@PostConstruct
	public void ini(){
		try {
			listaOrgao = new OrgaoDAO().busca();
			listaGrauEscolaridade = new GrauEscolaridadeDAO().listaGrauEscolaridade();			
			listaEstado = new EstadoDAO().listaEstados();
			dataCadastro = Helper.formatDate().format(new Date());
			conselheiroDesativado = false;
			verificaEditar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void verificaEditar(){
		
		String idConselheiro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("c");
		
		try {
			
			if(idConselheiro != null){				
				conselhoEdita = new ConselheiroDAO().busca(Long.parseLong(idConselheiro));
				nomeCompleto = conselhoEdita.getPessoa().getNomeCompleto();
				dataCadastro = Helper.formatDate().format(conselhoEdita.getDataCadastro());
				nomeUsual = conselhoEdita.getNomeUsual();
				
				cpf = conselhoEdita.getPessoa().getCpf();
				rg =  conselhoEdita.getPessoa().getRg();
				orgao = conselhoEdita.getPessoa().getOrgao();
				foneI = conselhoEdita.getPessoa().getFoneI();
				foneII =  conselhoEdita.getPessoa().getFoneII();
				sexo = conselhoEdita.getPessoa().getSexo();
				cor = conselhoEdita.getPessoa().getCor();
				escolaridade = conselhoEdita.getPessoa().getEscolaridade();
				situacaoMatrimonial =conselhoEdita.getPessoa().getSituacaoMatrimonial();	
				
				estadoSelecionado = conselhoEdita.getLogradouro().getBairro().getCidade().getEstado();
				cidadeSelecionado = conselhoEdita.getLogradouro().getBairro().getCidade();
				bairroSelecionado = conselhoEdita.getLogradouro().getBairro();
				logradouro = conselhoEdita.getLogradouro();
				buscaCidade();
				buscaBairro();
				buscaLogradouro();
				
				numero = conselhoEdita.getNumero();
				complemento = conselhoEdita.getComplemento();
				email = conselhoEdita.getEmail();
				nivelAcesso = conselhoEdita.getNivelAcesso();
				funcao = conselhoEdita.getFuncao();
				
				primeiroMandatoInicio = conselhoEdita.getPrimeiroMandatoInicio();
				primeiroMandatoFim = conselhoEdita.getPrimeiroMandatoTermino();
				segundoMandatoInicio = conselhoEdita.getSegundoMandatoInicio();
				segundoMandatoFim = conselhoEdita.getSegundoMandatoTermino();
				dataNascimento = conselhoEdita.getPessoa().getDataNascimento();
				
				if(conselhoEdita.getFlgAtivo().equals("N")){
					conselheiroDesativado = true;
				}else{
					conselheiroDesativado = false;
				}
				
				obs = conselhoEdita.getObs();										
				
			}
		} catch (Exception e) {
			JSFUtil.addErrorMessage("erro ao carregar conselheiro selecionado: "+e.getMessage());
			System.out.println("erro ao carregar conselheiro selecionado: "+e.getMessage());
		}
		
	}	
	
	public void buscaLogradouro(){
		try {
			if(bairroSelecionado != null){
				listaLogradouro = new LogradouroDAO().buscaLogradouro(bairroSelecionado);
			}else{
				listaLogradouro = new ArrayList<Logradouro>();
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao buscar logradouro [CADASTRO CONSELHEIRO]");
		}
	}
	
	public void salvar(){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! ", "Procedimento Inválido! Nível de acesso não permitido salvar.");
			return;
		}
		
		try {
			
			if(!nomeCompleto.equals("")  &&			     
			   !cpf.equals("")			 &&
			   !rg.equals("")			 &&
			   orgao			!= null	 &&
			   dataNascimento	!= null	 &&
			   !foneI.equals("")		 &&
			   logradouro		!= null  &&
			   !numero.equals("")		 &&
			   !nivelAcesso.equals("")){																			
				
			    Pessoa pessoa = new Pessoa();
				Conselheiro conselheiro = new Conselheiro();
								
				
				if(conselhoEdita == null){
					if(!senha.equals("") && !confirmeSenha.equals("")){
						if(!validaSenha()){
							JSFUtil.addErrorMessage("Verifique a senha ", "estão incorretas ou não contém 8 digitos.");
							return;
						}
					}else{
						JSFUtil.addErrorMessage("Informe a senha e confirme.");
						return;
					}
				}else if(!senha.equals("")){
					if(!confirmeSenha.equals("")){
						if(!validaSenha()){
							JSFUtil.addErrorMessage("Verifique a senha: ", "estão incorretas ou não contém 8 digitos");
							return;
						}
					}else{
						JSFUtil.addErrorMessage("Informe a senha e confirme");
						return;
					}
				}
				
				if(CpfValidator.validate(cpf)){
					JSFUtil.addErrorMessage("ERRO! ", "CPF INVÁLIDO");
					return;
				}
				
				if(conselhoEdita != null){
					pessoa = conselhoEdita.getPessoa();
					conselheiro = conselhoEdita;
				}
						
				pessoa.setNomeCompleto(nomeCompleto);
				pessoa.setCpf(cpf.replaceAll("\\.", "").replaceAll("-", ""));
				pessoa.setRg(rg);
				pessoa.setOrgao(orgao);
				pessoa.setFoneI(foneI);
				pessoa.setFoneII(foneII);
				pessoa.setSexo(sexo);
				pessoa.setCor(cor);
				pessoa.setEscolaridade(escolaridade);
				pessoa.setSituacaoMatrimonial(situacaoMatrimonial);								
				pessoa.setNumeroCertidaoNascimento("");
				pessoa.setDataNascimento(dataNascimento);
				
				pessoa.setLogradouro(logradouro);
				pessoa.setNumero(numero);
				pessoa.setComplemento(complemento);
				
				if(conselheiroLogado.getId() != null){
					pessoa.setConselheiroRegistro(conselheiroLogado);
				}
				
				if(conselhoEdita == null){
					pessoa.setDataCadastro(new Date());
				}
				
				pessoa = new PessoaDAO().salvar(pessoa);
				
				if(conselhoEdita == null){
					conselheiro.setDataCadastro(new Date());
				}
						
				conselheiro.setPessoa(pessoa);								
				conselheiro.setLogradouro(logradouro);
				conselheiro.setNumero(numero);
				conselheiro.setComplemento(complemento);
				conselheiro.setEmail(email);
				conselheiro.setPrimeiroMandatoInicio(primeiroMandatoInicio);
				conselheiro.setPrimeiroMandatoTermino(primeiroMandatoFim);
				conselheiro.setSegundoMandatoInicio(segundoMandatoInicio);
				conselheiro.setSegundoMandatoTermino(segundoMandatoFim);
				conselheiro.setFuncao(funcao);
				conselheiro.setNivelAcesso(nivelAcesso);
				conselheiro.setObs(obs);
				conselheiro.setFlgAtivo("S");
				conselheiro.setNomeUsual(nomeUsual);
				
				if(conselheiroLogado.getId() != null){
					conselheiro.setConselheiroRegistro(conselheiroLogado);
				}
				
				if(conselhoEdita != null && senha.equals("")){
					conselheiro.setSenha(conselhoEdita.getSenha());
				}else{
					conselheiro.setSenha(senha);
				}								
						
				new ConselheiroDAO().salvar(conselheiro);
				
				limpaCampos();
				JSFUtil.addInfoMessage("Salvo com sucesso !!!");
						
						
			}else{
				 JSFUtil.addErrorMessage("Faltam informações obrigatórias");				 						
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao salvar: "+e.getMessage());
			JSFUtil.addErrorMessage("Erro ao salvar: "+e.getMessage());
		}
						
	}
	
	public void reativarConselheiro(){
		
		try {
			
			if(!nomeCompleto.equals("")  &&			     
			   !cpf.equals("")			 &&
			   !rg.equals("")			 &&
			   orgao			!= null	 &&
			   dataNascimento	!= null	 &&
			   !foneI.equals("")		 &&
			   logradouro		!= null  &&
			   !numero.equals("")		 &&
			   !nivelAcesso.equals("")	 &&
			   !senha.equals("")         &&
			   !confirmeSenha.equals("")){																			
				
			    Pessoa pessoa = new Pessoa();
				Conselheiro conselheiro = new Conselheiro();
													
				if(!validaSenha()){
					JSFUtil.addErrorMessage("Verifique a senha ", "estão incorretas ou não contém 8 digitos.");
					return;
				}
				
				if(CpfValidator.validate(cpf)){
					JSFUtil.addErrorMessage("ERRO! ", "CPF INVÁLIDO");
					return;
				}
				
				if(conselhoEdita != null){
					pessoa = conselhoEdita.getPessoa();
					conselheiro = conselhoEdita;
				}
						
				pessoa.setNomeCompleto(nomeCompleto);
				pessoa.setCpf(cpf.replaceAll("\\.", "").replaceAll("-", ""));
				pessoa.setRg(rg);
				pessoa.setOrgao(orgao);
				pessoa.setFoneI(foneI);
				pessoa.setFoneII(foneII);
				pessoa.setSexo(sexo);
				pessoa.setCor(cor);
				pessoa.setEscolaridade(escolaridade);
				pessoa.setSituacaoMatrimonial(situacaoMatrimonial);								
				pessoa.setNumeroCertidaoNascimento("");
				pessoa.setConselheiroRegistro(conselheiroLogado);
				pessoa.setDataNascimento(dataNascimento);
				
				if(conselhoEdita == null){
					pessoa.setDataCadastro(new Date());
				}
				
				new PessoaDAO().salvar(pessoa);
				
				if(conselhoEdita == null){
					conselheiro.setDataCadastro(new Date());
				}
						
				conselheiro.setPessoa(pessoa);								
				conselheiro.setLogradouro(logradouro);
				conselheiro.setNumero(numero);
				conselheiro.setComplemento(complemento);
				conselheiro.setEmail(email);
				conselheiro.setPrimeiroMandatoInicio(primeiroMandatoInicio);
				conselheiro.setPrimeiroMandatoTermino(primeiroMandatoFim);
				conselheiro.setSegundoMandatoInicio(segundoMandatoInicio);
				conselheiro.setSegundoMandatoTermino(segundoMandatoFim);
				conselheiro.setFuncao(funcao);
				conselheiro.setNivelAcesso(nivelAcesso);
				conselheiro.setObs(obs);
				conselheiro.setFlgAtivo("S");
				
				if(conselhoEdita != null && senha.equals("")){
					conselheiro.setSenha(conselhoEdita.getSenha());
				}else{
					conselheiro.setSenha(senha);
				}								
						
				new ConselheiroDAO().salvar(conselheiro);
				
				limpaCampos();
				conselheiroDesativado = false;
				JSFUtil.addInfoMessage("Salvo com sucesso !!!");
						
						
			}else{
				 JSFUtil.addErrorMessage("Faltam informações obrigatórias");				 						
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao salvar: "+e.getMessage());
			JSFUtil.addErrorMessage("Erro ao salvar: "+e.getMessage());
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
	
	public boolean validaSenha() {			
		
		try {
			
			if(!senha.equals("")  && senha.length() < 8){
				JSFUtil.addErrorMessage("Senha no minimo 8 digitos");
				return false;
			}
			
			if(!senha.equals("") && !confirmeSenha.equals("")){
				if(!senha.equals(confirmeSenha)){
					JSFUtil.addErrorMessage("Senhas incorretas");
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
			
		} catch (Exception e) {
			return true;
		}
		
	}
	
	public void limpaCampos(){
		
		  nomeCompleto = "";
		  rg = "";
		  orgao = null;
		  cpf = "";
		  foneI = "";
		  foneII = "";
		  senha = "";
		  logradouro = null;
		  numero = "";
		  nomeUsual = "";
		  complemento = "";
		  sexo = "";
		  cor = "";
		  escolaridade = null;
		  situacaoMatrimonial = "";
		  email = "";
		  primeiroMandatoInicio = null;
		  primeiroMandatoFim = null;
		  segundoMandatoInicio = null;
		  segundoMandatoFim = null;
		  dataNascimento = null;
		  obs = "";
		  dataCadastro = "";
		  funcao = "";
		  nivelAcesso = "";
		  estadoSelecionado = null;
		  cidadeSelecionado = null;
		  bairroSelecionado = null;
		  logradouro = null;
		
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
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}		
	
	public Orgao getOrgao() {
		return orgao;
	}

	public void setOrgao(Orgao orgao) {
		this.orgao = orgao;
	}

	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Logradouro getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
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
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public GrauEscolaridade getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(GrauEscolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	public String getSituacaoMatrimonia() {
		return situacaoMatrimonial;
	}
	public void setSituacaoMatrimonia(String situacaoMatrimonial) {
		this.situacaoMatrimonial = situacaoMatrimonial;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getPrimeiroMandatoInicio() {
		return primeiroMandatoInicio;
	}
	public void setPrimeiroMandatoInicio(Date primeiroMandatoInicio) {
		this.primeiroMandatoInicio = primeiroMandatoInicio;
	}
	public Date getPrimeiroMandatoFim() {
		return primeiroMandatoFim;
	}
	public void setPrimeiroMandatoFim(Date primeiroMandatoFim) {
		this.primeiroMandatoFim = primeiroMandatoFim;
	}
	public Date getSegundoMandatoInicio() {
		return segundoMandatoInicio;
	}
	public void setSegundoMandatoInicio(Date segundoMandatoInicio) {
		this.segundoMandatoInicio = segundoMandatoInicio;
	}
	public Date getSegundoMandatoFim() {
		return segundoMandatoFim;
	}
	public void setSegundoMandatoFim(Date segundoMandatoFim) {
		this.segundoMandatoFim = segundoMandatoFim;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getConfirmSenha() {
		return confirmeSenha;
	}
	public void setConfirmSenha(String confirmeSenha) {
		this.confirmeSenha = confirmeSenha;
	}
	public Bairro getBairroSelecionado() {
		return bairroSelecionado;
	}
	public void setBairroSelecionado(Bairro bairroSelecionado) {
		this.bairroSelecionado = bairroSelecionado;
	}
	public String getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public List<Orgao> getListaOrgao() {
		return listaOrgao;
	}
	public void setListaOrgao(List<Orgao> listaOrgao) {
		this.listaOrgao = listaOrgao;
	}
	public List<GrauEscolaridade> getListaGrauEscolaridade() {
		return listaGrauEscolaridade;
	}
	public void setListaGrauEscolaridade(
			List<GrauEscolaridade> listaGrauEscolaridade) {
		this.listaGrauEscolaridade = listaGrauEscolaridade;
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

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public String getNivelAcesso() {
		return nivelAcesso;
	}

	public void setNivelAcesso(String nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public boolean isConselheiroDesativado() {
		return conselheiroDesativado;
	}

	public void setConselheiroDesativado(boolean conselheiroDesativado) {
		this.conselheiroDesativado = conselheiroDesativado;
	}

	public String getNomeUsual() {
		return nomeUsual;
	}

	public void setNomeUsual(String nomeUsual) {
		this.nomeUsual = nomeUsual;
	}

	public Conselheiro getConselheiroLogado() {
		return conselheiroLogado;
	}

	public void setConselheiroLogado(Conselheiro conselheiroLogado) {
		this.conselheiroLogado = conselheiroLogado;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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

	public Cidade getCidadeSelecionado() {
		return cidadeSelecionado;
	}

	public void setCidadeSelecionado(Cidade cidadeSelecionado) {
		this.cidadeSelecionado = cidadeSelecionado;
	}		
		
}
