package br.com.conselho.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.com.conselho.util.Helper;

@Entity
@SequenceGenerator(name = "atribuicao_seq", sequenceName = "atribuicao_cod_seq", allocationSize =1)
public class Atribuicao {
	
	@Id
	@GeneratedValue(generator = "atribuicao_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date data;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Conselheiro conselheiro;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private AgenteVioladorSubClasse descumpridor;
	
	@Column(length = 500)
	private String descricao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Atendimento atendimento;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "atribuicao")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DeterminacaoAplicada> listaDeterminacoesAplicadas;
	
	@Transient
	private String resumoDescricao;
	
	public String getDataFatoFormatada(){
		return Helper.formatDate().format(data);
	}
	
	public String getResumoDescricao() {
		
		if(descricao.length() <= 210){
			resumoDescricao = descricao.substring(0, descricao.length());
		}else{
			resumoDescricao = descricao.substring(0, 210);
		}		
		
		return resumoDescricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Conselheiro getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(Conselheiro conselheiro) {
		this.conselheiro = conselheiro;
	}

	public AgenteVioladorSubClasse getDescumpridor() {
		return descumpridor;
	}

	public void setDescumpridor(AgenteVioladorSubClasse descumpridor) {
		this.descumpridor = descumpridor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}		

	public List<DeterminacaoAplicada> getListaDeterminacoesAplicadas() {
		return listaDeterminacoesAplicadas;
	}

	public void setListaDeterminacoesAplicadas(
			List<DeterminacaoAplicada> listaDeterminacoesAplicadas) {
		this.listaDeterminacoesAplicadas = listaDeterminacoesAplicadas;
	}

	public void setResumoDescricao(String resumoDescricao) {
		this.resumoDescricao = resumoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((atendimento == null) ? 0 : atendimento.hashCode());
		result = prime * result
				+ ((conselheiro == null) ? 0 : conselheiro.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((descumpridor == null) ? 0 : descumpridor.hashCode());
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
		Atribuicao other = (Atribuicao) obj;
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
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (descumpridor == null) {
			if (other.descumpridor != null)
				return false;
		} else if (!descumpridor.equals(other.descumpridor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}
