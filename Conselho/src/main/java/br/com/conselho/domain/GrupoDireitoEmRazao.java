package br.com.conselho.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "grupoDireitoEmRazao_seq", sequenceName = "grupoDireitoEmRazao_cod_seq", allocationSize =1)
public class GrupoDireitoEmRazao {
	
	@Id
	@GeneratedValue(generator = "grupoDireitoEmRazao_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(nullable = false, length = 80)
	private String nome;
		
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
	
	

}
