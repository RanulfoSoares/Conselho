package br.com.conselho.domain;

import java.io.Serializable;
import java.util.ArrayList;
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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "instituicao_seq", sequenceName = "instituicao_cod_seq", allocationSize =1)
public class Instituicao implements Serializable {
	
	@Id
	@GeneratedValue(generator = "instituicao_seq", strategy = GenerationType.SEQUENCE)
	private Long id;	
	
	@Column(length = 100, nullable = false)
	private String nomeRazao;
	
	@Column(length = 100, nullable = false)
	private String fantasia;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private TipoInstituicao tipoInstituicao;
	
	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(nullable = false) habilitar quando for possivel gerar o banco novamente.
	private Conselheiro conselheiroRegistro;
	
	@Column(length = 15)
	private String cnpj;
	
	@Column(length = 14)
	private String foneI;
	
	@Column(length = 14)
	private String foneII;
	
	@Column(length = 100)
	private String contato;
	
	@Column(length = 14)
	private String foneContatoI;
	
	@Column(length = 14)
	private String foneContatoII;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Logradouro logradouro;
	
	@Column(length = 6, nullable = false)
	private String numero;
	
	@Column(length = 100)
	private String complemento;
	
	@Column(length = 300)
	private String obs;
	
	@Column(length = 100)
	private String email;
	
	@Column(length = 6)
	private String cie;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "instituicao")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Membro> listaMembros;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeRazao() {
		return nomeRazao;
	}
	public void setNomeRazao(String nomeRazao) {
		this.nomeRazao = nomeRazao;
	}	
	public Logradouro getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
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
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getFoneI() {
		return foneI;
	}
	public void setFoneI(String foneI) {
		this.foneI = foneI;
	}
	public String getFoneII() {
		return foneII;
	}
	public void setFoneII(String foneII) {
		this.foneII = foneII;
	}
	public String getFoneContatoI() {
		return foneContatoI;
	}
	public void setFoneContatoI(String foneContatoI) {
		this.foneContatoI = foneContatoI;
	}
	public String getFoneContatoII() {
		return foneContatoII;
	}
	public void setFoneContatoII(String foneContatoII) {
		this.foneContatoII = foneContatoII;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getFantasia() {
		return fantasia;
	}
	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}
	public TipoInstituicao getTipoInstituicao() {
		return tipoInstituicao;
	}
	public void setTipoInstituicao(TipoInstituicao tipoInstituicao) {
		this.tipoInstituicao = tipoInstituicao;
	}
	public String getCie() {
		return cie;
	}
	public void setCie(String cie) {
		this.cie = cie;
	}			
	public Conselheiro getConselheiroRegistro() {
		return conselheiroRegistro;
	}
	public void setConselheiroRegistro(Conselheiro conselheiroRegistro) {
		this.conselheiroRegistro = conselheiroRegistro;
	}		
	public List<Membro> getListaMembros() {
		return listaMembros;
	}
	public void setListaMembros(List<Membro> listaMembros) {
		this.listaMembros = listaMembros;
	}
	public boolean getTemMembros(){
		
		List<Membro> auxEx = new ArrayList<Membro>();
		if(listaMembros.isEmpty()){					
			return true;
		}else{
			for (Membro membro : listaMembros) {
				if(membro.getDataDesvinculo() != null){
					auxEx.add(membro);
				}
			}
			
			listaMembros.removeAll(auxEx);
			
			if(listaMembros.isEmpty()){
				return true;
			}else{
				return false;
			}			
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cie == null) ? 0 : cie.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result
				+ ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + ((contato == null) ? 0 : contato.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((fantasia == null) ? 0 : fantasia.hashCode());
		result = prime * result
				+ ((foneContatoI == null) ? 0 : foneContatoI.hashCode());
		result = prime * result
				+ ((foneContatoII == null) ? 0 : foneContatoII.hashCode());
		result = prime * result + ((foneI == null) ? 0 : foneI.hashCode());
		result = prime * result + ((foneII == null) ? 0 : foneII.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result
				+ ((nomeRazao == null) ? 0 : nomeRazao.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((obs == null) ? 0 : obs.hashCode());
		result = prime * result
				+ ((tipoInstituicao == null) ? 0 : tipoInstituicao.hashCode());
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
		Instituicao other = (Instituicao) obj;
		if (cie == null) {
			if (other.cie != null)
				return false;
		} else if (!cie.equals(other.cie))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (contato == null) {
			if (other.contato != null)
				return false;
		} else if (!contato.equals(other.contato))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fantasia == null) {
			if (other.fantasia != null)
				return false;
		} else if (!fantasia.equals(other.fantasia))
			return false;
		if (foneContatoI == null) {
			if (other.foneContatoI != null)
				return false;
		} else if (!foneContatoI.equals(other.foneContatoI))
			return false;
		if (foneContatoII == null) {
			if (other.foneContatoII != null)
				return false;
		} else if (!foneContatoII.equals(other.foneContatoII))
			return false;
		if (foneI == null) {
			if (other.foneI != null)
				return false;
		} else if (!foneI.equals(other.foneI))
			return false;
		if (foneII == null) {
			if (other.foneII != null)
				return false;
		} else if (!foneII.equals(other.foneII))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (nomeRazao == null) {
			if (other.nomeRazao != null)
				return false;
		} else if (!nomeRazao.equals(other.nomeRazao))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (obs == null) {
			if (other.obs != null)
				return false;
		} else if (!obs.equals(other.obs))
			return false;
		if (tipoInstituicao == null) {
			if (other.tipoInstituicao != null)
				return false;
		} else if (!tipoInstituicao.equals(other.tipoInstituicao))
			return false;
		return true;
	}	
}
