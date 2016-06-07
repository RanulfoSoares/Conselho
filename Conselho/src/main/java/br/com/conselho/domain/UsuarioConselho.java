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

@Entity
@SequenceGenerator(name = "usuario_conselho_seq", sequenceName = "usuario_conselho_cod_seq", allocationSize =1)
public class UsuarioConselho {
	
	@Id
	@GeneratedValue(generator = "usuario_conselho_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(length = 60, nullable = false)
	private String usuario;
	
	@Column(length = 60, nullable = false)
	private String pass;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Funcao funcao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Pessoa pessoa;
	
	@Column(nullable = false)
	private Boolean ativado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date ativacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date desativacao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Funcao getFuncao() {
		return funcao;
	}
	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Boolean getAtivado() {
		return ativado;
	}
	public void setAtivado(Boolean ativado) {
		this.ativado = ativado;
	}
	public Date getAtivacao() {
		return ativacao;
	}
	public void setAtivacao(Date ativacao) {
		this.ativacao = ativacao;
	}
	public Date getDesativacao() {
		return desativacao;
	}
	public void setDesativacao(Date desativacao) {
		this.desativacao = desativacao;
	}
}
