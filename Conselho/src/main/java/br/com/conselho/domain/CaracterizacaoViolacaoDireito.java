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
@SequenceGenerator(name = "caracterizacao_violacao_direito_seq", sequenceName = "caracterizacao_violacao_direito_cod_seq", allocationSize =1)
public class CaracterizacaoViolacaoDireito {
	
	@Id
	@GeneratedValue(generator = "caracterizacao_violacao_direito_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(nullable = false, length = 400)
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private DireitoFundamental direitoFundamental;
	
	@Transient
	private String resumoDescricao;	
	
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
	
	public DireitoFundamental getDireitoFundamental() {
		return direitoFundamental;
	}
	public void setDireitoFundamental(DireitoFundamental direitoFundamental) {
		this.direitoFundamental = direitoFundamental;
	}
	
	public String getResumoDescricao() {
		
		if(nome.length() <= 180){
			resumoDescricao = nome.substring(0, nome.length());
		}else{
			resumoDescricao = nome.substring(0, 180);
		}		
		
		return resumoDescricao;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((direitoFundamental == null) ? 0 : direitoFundamental
						.hashCode());
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
		CaracterizacaoViolacaoDireito other = (CaracterizacaoViolacaoDireito) obj;
		if (direitoFundamental == null) {
			if (other.direitoFundamental != null)
				return false;
		} else if (!direitoFundamental.equals(other.direitoFundamental))
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
