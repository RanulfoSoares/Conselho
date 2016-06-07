package br.com.conselho.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.conselho.dao.CriancaAdolescenteDAO;
import br.com.conselho.dao.GrauEscolaridadeDAO;
import br.com.conselho.dao.InstituicaoDAO;
import br.com.conselho.dao.OrgaoDAO;
import br.com.conselho.dao.PessoaDAO;
import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.GrauEscolaridade;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Orgao;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.util.CpfValidator;
import br.com.conselho.util.Helper;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBCriancaCadastro")
@ViewScoped
public class CriancaAdoCadastro implements Serializable {
	
	private String codigo;
	private String dataCadastro;
	private String conselheiro;
	private String nomeCrianAdolesc;
	private String cpf;
	private String rg;
	private String orgaoEmissor;
	private String certNasc;
	private Date dataNascimento;
	private String idade;
	private String foneI;
	private String foneII;
	private String sexo;
	private String cor;
	private GrauEscolaridade escolaridade = new GrauEscolaridade();
	private Instituicao escola = new Instituicao();
	private String trabalha;
	private String localTrabalho;	
	private CriancaAdolescente criancaSelecionada = null;
	private List<Instituicao> listaInstituicao;	
	private List<GrauEscolaridade> listaGrauEscolaridade;
	private boolean desabilitaLocalTrabalho;
	private Long idCriancaSelecionado = null;
	private Long idPessoaSelecionadoCrianca = null;
	private boolean botoesDeVinculacao;
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	private List<Orgao> listaOrgao = new ArrayList<Orgao>();
	private Orgao orgaoSelecionado;
	private CriancaAdolescenteDAO criancaAdolescenteDAO = new CriancaAdolescenteDAO();
	
	
	@PostConstruct
	public void iniCadastro(){	
		try {
			listaInstituicao = new InstituicaoDAO().listaEscola();
			listaGrauEscolaridade = new GrauEscolaridadeDAO().listaGrauEscolaridade();		
			dataCadastro = formato.format(new Date());
			conselheiro = "Ranulfo";
			listaOrgao = new OrgaoDAO().busca();
			
			verificaEditar();
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao carregar parâmetros da página");
		}		
	}	
	
	private void verificaEditar(){
		
		String idCrianca = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("c");
		
		if(idCrianca != null){
			
			try {
				criancaSelecionada = criancaAdolescenteDAO.buscaCrianca(Long.parseLong(idCrianca));
				
				
				dataCadastro = formato.format(criancaSelecionada.getPessoa().getDataCadastro());
				conselheiro = criancaSelecionada.getPessoa().getConselheiro();
				nomeCrianAdolesc = criancaSelecionada.getPessoa().getNomeCompleto();
				cpf = criancaSelecionada.getPessoa().getCpf();
				rg = criancaSelecionada.getPessoa().getRg();
				//orgaoEmissor = criancaSelecionada.getPessoa().getOrgao();
				certNasc = criancaSelecionada.getPessoa().getNumeroCertidaoNascimento();
				dataNascimento = criancaSelecionada.getPessoa().getDataNascimento();
				idade = criancaSelecionada.getPessoa().getIdade().toString();
				foneI = criancaSelecionada.getPessoa().getFoneI();
				foneII = criancaSelecionada.getPessoa().getFoneII();
				sexo = criancaSelecionada.getPessoa().getSexo();
				cor = criancaSelecionada.getPessoa().getCor();
				escolaridade = criancaSelecionada.getPessoa().getEscolaridade();
				escola = criancaSelecionada.getEscola();
				trabalha = (criancaSelecionada.getPessoa().getTrabalha() ? "S" : "N");
				botoesDeVinculacao = true;
				if(criancaSelecionada.getPessoa().getTrabalha()){
					desabilitaLocalTrabalho = false;
				}else{
					desabilitaLocalTrabalho = true;
				}
				
				localTrabalho = criancaSelecionada.getPessoa().getLocalTrabalho();
				orgaoSelecionado = criancaSelecionada.getPessoa().getOrgao();
				idCriancaSelecionado = criancaSelecionada.getId();
				idPessoaSelecionadoCrianca = criancaSelecionada.getPessoa().getId();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}								
		}else{

			
			try {
				nomeCrianAdolesc = "";
				cpf = "";
				rg = "";
				orgaoEmissor = "";
				certNasc = "";
				dataNascimento = null;
				idade = "";
				foneI = "";
				foneII = "";
				sexo = null;
				escola = null;
				escolaridade = null;						
				dataCadastro = formato.format(new Date());
				conselheiro = "Ranulfo";
				trabalha = "N";
				desabilitaLocalTrabalho = true;
				cor = null;
				localTrabalho = "";
				idCriancaSelecionado = null;
				idPessoaSelecionadoCrianca = null;
				botoesDeVinculacao = false;
				orgaoSelecionado = null;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	
	public void salvar(){
		
		try {
			
			try {
				if(CpfValidator.validate(cpf)){
					JSFUtil.addErrorMessage("ERRO! ", "CPF INVÁLIDO");
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if("".equals(rg) && "".equals(cpf) && "".equals(certNasc)){
									
				JSFUtil.addErrorMessage("Informe o RG ou CPF ou Certidão de Nascimento!");
			}else{
				
				if(!"".equals(rg)){
					if(orgaoSelecionado == null){
						JSFUtil.addErrorMessage("Informe o órgao emissor do RG !");
						return;
					}
				}
				
				Pessoa pessoa = new Pessoa();
				
				pessoa.setDataCadastro(new java.sql.Date(formato.parse(dataCadastro).getTime()));
				pessoa.setConselheiro(conselheiro);
				pessoa.setNomeCompleto(nomeCrianAdolesc);
				pessoa.setCpf(cpf.replaceAll("\\.", "").replaceAll("-", ""));
				pessoa.setRg(rg.replaceAll("\\.", "").replaceAll("-", ""));
				pessoa.setNumeroCertidaoNascimento(certNasc);
				pessoa.setDataNascimento(dataNascimento);
				pessoa.setIdade( Integer.parseInt(idade));
				pessoa.setFoneI(foneI.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", ""));
				pessoa.setFoneII(foneII.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", ""));
				pessoa.setSexo(sexo);
				pessoa.setCor(cor);	
				pessoa.setEscolaridade(escolaridade);		
				pessoa.setTrabalha("S".equals(trabalha) ? true : false);
				pessoa.setLocalTrabalho(localTrabalho);
				pessoa.setOrgao(orgaoSelecionado);
				
				CriancaAdolescente criancaAdolescente = new CriancaAdolescente();
				
				if(idCriancaSelecionado != null && idPessoaSelecionadoCrianca != null){					
					pessoa.setId(idPessoaSelecionadoCrianca);
					criancaAdolescente.setId(idCriancaSelecionado);
				}
				
				Pessoa pessoaRetorno = new PessoaDAO().salvar(pessoa);
				
				criancaAdolescente.setPessoa(pessoaRetorno);
				criancaAdolescente.setEscola(escola);			
				
				criancaAdolescenteDAO.salvar(criancaAdolescente);
				
				
				System.out.println("CPF: "+pessoa.getCpf());
				
				JSFUtil.addInfoMessage("Criança Adolescente salvo com sucesso");
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao salvar: "+e.getMessage());
			JSFUtil.addErrorMessage("ERRO ao inserir: "+e.getMessage());
		}						
	}
	
	public String navegaResponsavel(){
		
		System.out.println("entrou no metodo redireciona responsavel... selecionado id: "+criancaSelecionada.getId());
		
		return "responsavel_busca?faces-redirect=true&c="+criancaSelecionada.getId();
		
	}
	
	
	public String navegaParenteMenor(){
		
		System.out.println("entrou no metodo redireciona responsável... selecionado id: "+criancaSelecionada.getId());
		
		return "irmao_busca?faces-redirect=true&c="+criancaSelecionada.getId();
		
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
	
	public void calculaIdade(){
		
		if(dataNascimento != null){
		  idade	= Helper.executaCalculoIdade(dataNascimento).toString();			
		}		 		
	}
		
	public List<Instituicao> getListaInstituicao() {
		return listaInstituicao;
	}


	public void setListaInstituicao(List<Instituicao> listaInstituicao) {
		this.listaInstituicao = listaInstituicao;
	}


	public List<GrauEscolaridade> getListaGrauEscolaridade() {
		return listaGrauEscolaridade;
	}


	public void setListaGrauEscolaridade(
			List<GrauEscolaridade> listaGrauEscolaridade) {
		this.listaGrauEscolaridade = listaGrauEscolaridade;
	}
	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(String conselheiro) {
		this.conselheiro = conselheiro;
	}

	public String getNomeCrianAdolesc() {
		return nomeCrianAdolesc;
	}

	public void setNomeCrianAdolesc(String nomeCrianAdolesc) {
		this.nomeCrianAdolesc = nomeCrianAdolesc;
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

	public Instituicao getEscola() {
		return escola;
	}

	public void setEscola(Instituicao escola) {
		this.escola = escola;
	}

	public String getTrabalha() {
		return trabalha;
	}

	public void setTrabalha(String trabalha) {
		this.trabalha = trabalha;
	}

	public String getLocalTrabalho() {
		return localTrabalho;
	}

	public void setLocalTrabalho(String localTrabalho) {
		this.localTrabalho = localTrabalho;
	}

	public boolean isDesabilitaLocalTrabalho() {
		return desabilitaLocalTrabalho;
	}

	public void setDesabilitaLocalTrabalho(boolean desabilitaLocalTrabalho) {
		this.desabilitaLocalTrabalho = desabilitaLocalTrabalho;
	}

	public boolean isBotoesDeVinculacao() {
		return botoesDeVinculacao;
	}

	public void setBotoesDeVinculacao(boolean botoesDeVinculacao) {
		this.botoesDeVinculacao = botoesDeVinculacao;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getIdCriancaSelecionado() {
		return idCriancaSelecionado;
	}

	public void setIdCriancaSelecionado(Long idCriancaSelecionado) {
		this.idCriancaSelecionado = idCriancaSelecionado;
	}	
	
	}
