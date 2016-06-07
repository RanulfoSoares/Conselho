package br.com.conselho.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.conselho.util.Helper;

@SuppressWarnings("serial")
@Entity
//@Table( name = "pessoa", uniqueConstraints = { @UniqueConstraint(columnNames = {"rg", "orgao_id"}), @UniqueConstraint(columnNames = {"cpf" }), @UniqueConstraint(columnNames = {"numerocertidaonascimento" }) })
@SequenceGenerator(name = "pessoa_seq", sequenceName = "pessoa_cod_seq", allocationSize =1)
public class Pessoa implements Serializable {
	
	@Id
	@GeneratedValue(generator = "pessoa_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataCadastro;
	
	@Column(length = 45)
	private String conselheiro;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Conselheiro conselheiroRegistro;
	
	@Column(length = 150, nullable = false)
	private String nomeCompleto;
	
	@Column(length = 60)
	private String primeiroNome;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataNascimento;
	
	@Column(length = 3)
	private Integer idade;
		
	@Column(length = 10)
	private String sexo;
	
	@Column(length = 30)
	private String cor;
	
	@Column(length = 11)
	private String rg;
	
	@ManyToOne
	private Orgao orgao;
	
	@Column(length = 11)
	private String cpf;
	
	@Column(length = 15)
	private String situacaoMatrimonial;
	
	@Column(length = 20)
	private String numeroCertidaoNascimento;
	
	@ManyToOne(fetch = FetchType.EAGER)			
	private GrauEscolaridade escolaridade;
	
	@ManyToOne(fetch = FetchType.EAGER)			
	private Instituicao escola;
	
	
	/** Retirar depois das modificações incluindo nucleo familiar */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()	
	private Logradouro logradouro;
	
	@Column(length = 6)
	private String numero;
	
	@Column(length = 100)
	private String complemento;
	/** até aqui!..  */
	
	
	@Column(length = 70)
	private String email;
	
	@Column(length = 15)
	private String foneI;
	
	@Column(length = 15)
	private String foneII;
	
	private Boolean trabalha;
	
	@Column(length = 100)
	private String localTrabalho;
	
	@Column(length = 15)
	private String foneTrabalho;
	
	@Column(length = 300)
	private String obs;
	
	@Column(length = 3)
	private String situacaoTrabalho;
	
	@Column(length = 3)
	private String situacaoMoradia;
	
	
	/* usado pra saber se esta pessoa esta vinculada a um nucleo familiar */
	@Transient
	private Membro membro;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getPrimeiroNome() {
		return primeiroNome;
	}
	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getNumeroCertidaoNascimento() {
		return numeroCertidaoNascimento;
	}
	public void setNumeroCertidaoNascimento(String numeroCertidaoNascimento) {
		this.numeroCertidaoNascimento = numeroCertidaoNascimento;
	}
	public GrauEscolaridade getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(GrauEscolaridade escolaridade) {
		this.escolaridade = escolaridade;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public Boolean getTrabalha() {
		return trabalha;
	}
	public void setTrabalha(Boolean trabalha) {
		this.trabalha = trabalha;
	}
	public String getLocalTrabalho() {
		return localTrabalho;
	}
	public void setLocalTrabalho(String localTrabalho) {
		this.localTrabalho = localTrabalho;
	}
	public String getFoneTrabalho() {
		return foneTrabalho;
	}
	public void setFoneTrabalho(String foneTrabalho) {
		this.foneTrabalho = foneTrabalho;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}	
	public String getConselheiro() {
		return conselheiro;
	}
	public void setConselheiro(String conselheiro) {
		this.conselheiro = conselheiro;
	}
	
	public Orgao getOrgao() {
		return orgao;
	}
	public void setOrgao(Orgao orgao) {
		this.orgao = orgao;
	}
	public String getSituacaoMatrimonial() {
		return situacaoMatrimonial;
	}
	public void setSituacaoMatrimonial(String situacaoMatrimonial) {
		this.situacaoMatrimonial = situacaoMatrimonial;
	}
	
	public Instituicao getEscola() {
		return escola;
	}
	public void setEscola(Instituicao escola) {
		this.escola = escola;
	}		
	
	public Membro getMembro() {
		return membro;
	}	
	public void setMembro(Membro membro) {
		this.membro = membro;
	}		
	public Conselheiro getConselheiroRegistro() {
		return conselheiroRegistro;
	}
	public void setConselheiroRegistro(Conselheiro conselheiroRegistro) {
		this.conselheiroRegistro = conselheiroRegistro;
	}
	public String getSituacaoTrabalho() {
		return situacaoTrabalho;
	}
	public void setSituacaoTrabalho(String situacaoTrabalho) {
		this.situacaoTrabalho = situacaoTrabalho;
	}
	public String getSituacaoMoradia() {
		return situacaoMoradia;
	}
	public void setSituacaoMoradia(String situacaoMoradia) {
		this.situacaoMoradia = situacaoMoradia;
	}
	
	public Integer getIdadeAtual(){
		if(dataNascimento != null){
			return Helper.executaCalculoIdade(dataNascimento);
		}else{
			return 0;
		}
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result
				+ ((conselheiro == null) ? 0 : conselheiro.hashCode());
		result = prime
				* result
				+ ((conselheiroRegistro == null) ? 0 : conselheiroRegistro
						.hashCode());
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result
				+ ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((escola == null) ? 0 : escola.hashCode());
		result = prime * result
				+ ((escolaridade == null) ? 0 : escolaridade.hashCode());
		result = prime * result + ((foneI == null) ? 0 : foneI.hashCode());
		result = prime * result + ((foneII == null) ? 0 : foneII.hashCode());
		result = prime * result
				+ ((foneTrabalho == null) ? 0 : foneTrabalho.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idade == null) ? 0 : idade.hashCode());
		result = prime * result
				+ ((localTrabalho == null) ? 0 : localTrabalho.hashCode());
		result = prime * result
				+ ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((membro == null) ? 0 : membro.hashCode());
		result = prime * result
				+ ((nomeCompleto == null) ? 0 : nomeCompleto.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime
				* result
				+ ((numeroCertidaoNascimento == null) ? 0
						: numeroCertidaoNascimento.hashCode());
		result = prime * result + ((obs == null) ? 0 : obs.hashCode());
		result = prime * result + ((orgao == null) ? 0 : orgao.hashCode());
		result = prime * result
				+ ((primeiroNome == null) ? 0 : primeiroNome.hashCode());
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime
				* result
				+ ((situacaoMatrimonial == null) ? 0 : situacaoMatrimonial
						.hashCode());
		result = prime * result
				+ ((situacaoMoradia == null) ? 0 : situacaoMoradia.hashCode());
		result = prime
				* result
				+ ((situacaoTrabalho == null) ? 0 : situacaoTrabalho.hashCode());
		result = prime * result
				+ ((trabalha == null) ? 0 : trabalha.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (conselheiro == null) {
			if (other.conselheiro != null)
				return false;
		} else if (!conselheiro.equals(other.conselheiro))
			return false;
		if (conselheiroRegistro == null) {
			if (other.conselheiroRegistro != null)
				return false;
		} else if (!conselheiroRegistro.equals(other.conselheiroRegistro))
			return false;
		if (cor == null) {
			if (other.cor != null)
				return false;
		} else if (!cor.equals(other.cor))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (escola == null) {
			if (other.escola != null)
				return false;
		} else if (!escola.equals(other.escola))
			return false;
		if (escolaridade == null) {
			if (other.escolaridade != null)
				return false;
		} else if (!escolaridade.equals(other.escolaridade))
			return false;
		if (foneI == null) {
			if (other.foneI != null)
				return false;
		} else if (!foneI.equals(other.foneI))
			return false;
		if (foneII == null) {
			if (other.foneII != null)
				return false;
		} else if (!foneII.equals(other.foneII))
			return false;
		if (foneTrabalho == null) {
			if (other.foneTrabalho != null)
				return false;
		} else if (!foneTrabalho.equals(other.foneTrabalho))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idade == null) {
			if (other.idade != null)
				return false;
		} else if (!idade.equals(other.idade))
			return false;
		if (localTrabalho == null) {
			if (other.localTrabalho != null)
				return false;
		} else if (!localTrabalho.equals(other.localTrabalho))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (membro == null) {
			if (other.membro != null)
				return false;
		} else if (!membro.equals(other.membro))
			return false;
		if (nomeCompleto == null) {
			if (other.nomeCompleto != null)
				return false;
		} else if (!nomeCompleto.equals(other.nomeCompleto))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (numeroCertidaoNascimento == null) {
			if (other.numeroCertidaoNascimento != null)
				return false;
		} else if (!numeroCertidaoNascimento
				.equals(other.numeroCertidaoNascimento))
			return false;
		if (obs == null) {
			if (other.obs != null)
				return false;
		} else if (!obs.equals(other.obs))
			return false;
		if (orgao == null) {
			if (other.orgao != null)
				return false;
		} else if (!orgao.equals(other.orgao))
			return false;
		if (primeiroNome == null) {
			if (other.primeiroNome != null)
				return false;
		} else if (!primeiroNome.equals(other.primeiroNome))
			return false;
		if (rg == null) {
			if (other.rg != null)
				return false;
		} else if (!rg.equals(other.rg))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		if (situacaoMatrimonial == null) {
			if (other.situacaoMatrimonial != null)
				return false;
		} else if (!situacaoMatrimonial.equals(other.situacaoMatrimonial))
			return false;
		if (situacaoMoradia == null) {
			if (other.situacaoMoradia != null)
				return false;
		} else if (!situacaoMoradia.equals(other.situacaoMoradia))
			return false;
		if (situacaoTrabalho == null) {
			if (other.situacaoTrabalho != null)
				return false;
		} else if (!situacaoTrabalho.equals(other.situacaoTrabalho))
			return false;
		if (trabalha == null) {
			if (other.trabalha != null)
				return false;
		} else if (!trabalha.equals(other.trabalha))
			return false;
		return true;
	}		
}	
	
	

