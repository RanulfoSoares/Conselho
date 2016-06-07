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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private DireitoViolado direitoViolado;
	
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
	public DireitoViolado getDireitoViolado() {
		return direitoViolado;
	}
	public void setDireitoViolado(DireitoViolado direitoViolado) {
		this.direitoViolado = direitoViolado;
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
				+ ((direitoViolado == null) ? 0 : direitoViolado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		if (direitoViolado == null) {
			if (other.direitoViolado != null)
				return false;
		} else if (!direitoViolado.equals(other.direitoViolado))
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
		return true;
	}		
}
