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

import br.com.conselho.dao.MembroDao;


@Entity
@SequenceGenerator(name = "violador_seq", sequenceName = "violador_cod_seq", allocationSize =1)
public class Violador {
	
	@Id
	@GeneratedValue(generator = "violador_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()
	private Pessoa pessoa;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()
	private Instituicao instituicao;	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Atendimento atendimento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()
	private AgenteVioladorSubClasse agenteVioladorSubClasse;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()
	private Adendo adendoIncluir;
	
	@Column(length = 3)
	private Integer idade;
	
	private String escolaridade;
	
	private String estadoCivil;
	

	public Familia getFamilia() {
		try {
			if(pessoa != null){
				Membro membro = new MembroDao().buscaMembro(pessoa);
				return membro.getFamilia();
			}else{
				return null;
			}
		} catch (Exception e) {
			System.out.println("erro ao buscar familia de violador: "+e.getMessage());
			return null;
		}
			
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Atendimento getAtendimento() {
		return atendimento;
	}
	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}
	public AgenteVioladorSubClasse getAgenteVioladorSubClasse() {
		return agenteVioladorSubClasse;
	}
	public void setAgenteVioladorSubClasse(
			AgenteVioladorSubClasse agenteVioladorSubClasse) {
		this.agenteVioladorSubClasse = agenteVioladorSubClasse;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public String getNomeViolador() {
		if(instituicao != null){
			return instituicao.getNomeRazao();
		}else{
			return pessoa.getNomeCompleto();
		}		
	}
	public Adendo getAdendoIncluir() {
		return adendoIncluir;
	}
	public void setAdendoIncluir(Adendo adendoIncluir) {
		this.adendoIncluir = adendoIncluir;
	}		
	
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	public String getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	@Override
	public String toString() {
		
		if(pessoa != null){
			return pessoa.getNomeCompleto();
		}else{			
			return instituicao.getNomeRazao();
		}				
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((adendoIncluir == null) ? 0 : adendoIncluir.hashCode());
		result = prime
				* result
				+ ((agenteVioladorSubClasse == null) ? 0
						: agenteVioladorSubClasse.hashCode());
		result = prime * result
				+ ((atendimento == null) ? 0 : atendimento.hashCode());
		result = prime * result
				+ ((escolaridade == null) ? 0 : escolaridade.hashCode());
		result = prime * result
				+ ((estadoCivil == null) ? 0 : estadoCivil.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idade == null) ? 0 : idade.hashCode());
		result = prime * result
				+ ((instituicao == null) ? 0 : instituicao.hashCode());
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
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
		Violador other = (Violador) obj;
		if (adendoIncluir == null) {
			if (other.adendoIncluir != null)
				return false;
		} else if (!adendoIncluir.equals(other.adendoIncluir))
			return false;
		if (agenteVioladorSubClasse == null) {
			if (other.agenteVioladorSubClasse != null)
				return false;
		} else if (!agenteVioladorSubClasse
				.equals(other.agenteVioladorSubClasse))
			return false;
		if (atendimento == null) {
			if (other.atendimento != null)
				return false;
		} else if (!atendimento.equals(other.atendimento))
			return false;
		if (escolaridade == null) {
			if (other.escolaridade != null)
				return false;
		} else if (!escolaridade.equals(other.escolaridade))
			return false;
		if (estadoCivil == null) {
			if (other.estadoCivil != null)
				return false;
		} else if (!estadoCivil.equals(other.estadoCivil))
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
		if (instituicao == null) {
			if (other.instituicao != null)
				return false;
		} else if (!instituicao.equals(other.instituicao))
			return false;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		return true;
	}
	
}
