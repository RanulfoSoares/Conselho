package br.com.conselho.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.conselho.dao.CriancaAdolescenteDAO;
import br.com.conselho.dao.GrauEscolaridadeDAO;
import br.com.conselho.dao.InstituicaoDAO;
import br.com.conselho.dao.PessoaDAO;
import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.GrauEscolaridade;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBCrianca")
@ViewScoped
public class CriancaAdo implements Serializable {
	
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
	
	
	private List<Instituicao> listaInstituicao;
	
	private List<GrauEscolaridade> listaGrauEscolaridade;
	
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
	public void iniCadastro(){	
		try {
			listaInstituicao = new InstituicaoDAO().listaEscola();
			listaGrauEscolaridade = new GrauEscolaridadeDAO().listaGrauEscolaridade();		
			dataCadastro = formato.format(new Date());
			conselheiro = "Ranulfo";
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao carregar parametros da pagina");
		}
		
	}
	
	public void salvar(){
		
		try {
			
			Pessoa pessoa = new Pessoa();
			
			pessoa.setDataCadastro(new java.sql.Date(formato.parse(dataCadastro).getTime()));
			pessoa.setConselheiro(conselheiro);
			pessoa.setNomeCompleto(nomeCrianAdolesc);
			pessoa.setCpf(cpf.replaceAll("\\.", "").replaceAll("\\-", ""));
			pessoa.setRg(rg);
			//pessoa.setOrgao(orgaoEmissor);
			pessoa.setNumeroCertidaoNascimento(certNasc);
			pessoa.setDataNascimento(dataNascimento);
			pessoa.setIdade( Integer.parseInt(idade));
			pessoa.setFoneI(foneI.replaceAll("(", "").replaceAll(")", "").replaceAll("-", "").replace(" ", ""));
			pessoa.setFoneII(foneII.replaceAll("(", "").replaceAll(")", "").replaceAll("-", "").replace(" ", ""));
			pessoa.setSexo(sexo);
			pessoa.setCor(cor);	
			pessoa.setEscolaridade(escolaridade);		
			pessoa.setTrabalha(trabalha == "S" ? true : false);
			pessoa.setLocalTrabalho(localTrabalho);
			
			CriancaAdolescente criancaAdolescente = new CriancaAdolescente();
			
			criancaAdolescente.setPessoa(pessoa);
			criancaAdolescente.setEscola(escola);
			
			//Long idPessoa = new PessoaDAO().salvar(pessoa);
			new CriancaAdolescenteDAO().salvar(criancaAdolescente);
			
			
			System.out.println("CPF: "+pessoa.getCpf());
			
			JSFUtil.addInfoMessage("Criança Adolescente salvo com sucesso");
			
		} catch (Exception e) {
			System.out.println("Erro ao salvar: "+e.getMessage());
			JSFUtil.addErrorMessage("ERRO ao inserir: "+e.getMessage());
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
	
	
}
