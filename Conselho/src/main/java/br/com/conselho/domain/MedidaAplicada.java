package br.com.conselho.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
@SequenceGenerator(name = "medidaAplicada_seq", sequenceName = "medidaAplicada_cod_seq", allocationSize =1)
public class MedidaAplicada {
	
	@Id
	@GeneratedValue(generator = "medidaAplicada_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(nullable = false, length = 450)
	private String nome;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(nullable = false)
//	private DireitoViolado direitoViolado;
	
	@ManyToOne(fetch = FetchType.EAGER)
	//@JoinColumn(nullable = false)
	private Aplicacao aplicacao;
	
	@Transient
	private String resumoDescricao;		
	
	@Override
	public String toString() {
		return getResumoDescricao();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
	
	public String getResumoDescricao() {
		
		if(nome.length() <= 138){
			resumoDescricao = nome.substring(0, nome.length());
		}else{
			resumoDescricao = nome.substring(0, 138);
		}		
		
		return resumoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((aplicacao == null) ? 0 : aplicacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((resumoDescricao == null) ? 0 : resumoDescricao.hashCode());
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
		MedidaAplicada other = (MedidaAplicada) obj;
		if (aplicacao == null) {
			if (other.aplicacao != null)
				return false;
		} else if (!aplicacao.equals(other.aplicacao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (resumoDescricao == null) {
			if (other.resumoDescricao != null)
				return false;
		} else if (!resumoDescricao.equals(other.resumoDescricao))
			return false;
		return true;
	}
		
}
