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

@Entity
@SequenceGenerator(name = "local_do_fato_seq", sequenceName = "local_do_fato_cod_seq", allocationSize =1)
public class LocalDoFato {
	
	@Id
	@GeneratedValue(generator = "local_do_fato_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(length = 100, nullable = false)
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Logradouro Logradouro;
	
	@Column(length = 200)
	private String PontoDeRefrencia;
	
	@Column(length = 6)
	private String numero;
	
	@Column(length = 100)
	private String complemento;
	
	
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
	public Logradouro getLogradouro() {
		return Logradouro;
	}
	public void setLogradouro(Logradouro logradouro) {
		Logradouro = logradouro;
	}
	public String getPontoDeRefrencia() {
		return PontoDeRefrencia;
	}
	public void setPontoDeRefrencia(String pontoDeRefrencia) {
		PontoDeRefrencia = pontoDeRefrencia;
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
	
	
	

}
