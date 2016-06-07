package br.com.conselho.domain;

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

@Entity
@SequenceGenerator(name = "adendo_seq", sequenceName = "adendo_cod_seq", allocationSize =1)
public class Adendo {
	
	@Id
	@GeneratedValue(generator = "adendo_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Atendimento atendimento;
	
	@Column(length = 150)
	private String conselheiro;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	@Column(length = 4000, nullable = false)
	private String descricao;
	
	@Transient
	private String resumoDescricao;
	
	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(nullable = false) habilitar quando for possivel gerar o banco novamente.
	private Conselheiro conselheiroRegistro;
	
	public String getDataFatoFormatada(){
		return Helper.formatDate().format(dataCadastro);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public String getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(String conselheiro) {
		this.conselheiro = conselheiro;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}				
	
	public Conselheiro getConselheiroRegistro() {
		return conselheiroRegistro;
	}

	public void setConselheiroRegistro(Conselheiro conselheiroRegistro) {
		this.conselheiroRegistro = conselheiroRegistro;
	}

	public String getResumoDescricao() {
		
		if(descricao.length() <= 170){
			resumoDescricao = descricao.substring(0, descricao.length());
		}else{
			resumoDescricao = descricao.substring(0, 170);
		}		
		
		return resumoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((atendimento == null) ? 0 : atendimento.hashCode());
		result = prime * result
				+ ((conselheiro == null) ? 0 : conselheiro.hashCode());
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Adendo other = (Adendo) obj;
		if (atendimento == null) {
			if (other.atendimento != null)
				return false;
		} else if (!atendimento.equals(other.atendimento))
			return false;
		if (conselheiro == null) {
			if (other.conselheiro != null)
				return false;
		} else if (!conselheiro.equals(other.conselheiro))
			return false;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}		
	
}
