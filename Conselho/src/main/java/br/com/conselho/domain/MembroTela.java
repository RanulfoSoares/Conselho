package br.com.conselho.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MembroTela implements Serializable, Comparable<MembroTela> {
	
	private Long idMembro;
	private String nome;
	private String idade;
	private Pessoa pessoa;
	
	// itens usados na tela de familia membro
	private GrauParentesco grauParentesco;
	private GrauResponsabilidade grauResponsabilidade;
	
			
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIdade() {
		return idade;
	}
	public void setIdade(String idade) {
		this.idade = idade;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Long getIdMembro() {
		return idMembro;
	}
	public void setIdMembro(Long idMembro) {
		this.idMembro = idMembro;
	}
	public GrauParentesco getGrauParentesco() {
		return grauParentesco;
	}
	public void setGrauParentesco(GrauParentesco grauParentesco) {
		this.grauParentesco = grauParentesco;
	}
	public GrauResponsabilidade getGrauResponsabilidade() {
		return grauResponsabilidade;
	}
	public void setGrauResponsabilidade(GrauResponsabilidade grauResponsabilidade) {
		this.grauResponsabilidade = grauResponsabilidade;
	}
	
	@Override
	public int compareTo(MembroTela outroMembro) {
			int idadelocal = Integer.parseInt(this.idade);
			int outraIdade = Integer.parseInt(outroMembro.getIdade());
			if(idadelocal < outraIdade){
				return -1;
			}else if(idadelocal > outraIdade){
				return 1;
			}
		return 0;
	}
		
}
