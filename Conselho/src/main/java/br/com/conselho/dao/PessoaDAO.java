package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.Orgao;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.util.Helper;
import br.com.conselho.util.HibernateUtil;

public class PessoaDAO {
	
	
	public Pessoa salvar(Pessoa pessoa) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;			
		
		try {
			
			if(pessoa.getId() == null){
				verificaCadastro(pessoa);
					
			}else{				
				Pessoa pessoaBanco = buscaCodigo(pessoa.getId());
				
				if(!"".equals(pessoa.getCpf())){
					
					if(pessoaBanco.getCpf().equals("")){
						
						List<Pessoa> listaPessoa = null;
						listaPessoa = buscaCPF(pessoa.getCpf());
						if(!listaPessoa.isEmpty()){
							throw new Exception("O CPF editado já está cadastrado em outro cidadão");
						}
						
					}else if(!pessoaBanco.getCpf().equals(pessoa.getCpf())){
						
						List<Pessoa> listaPessoa = null;
						listaPessoa = buscaCPF(pessoa.getCpf());
						if(!listaPessoa.isEmpty()){
							throw new Exception("O CPF editado já está cadastrado em outro cidadão");
						}						
					}
				}
				
				if(!"".equals(pessoa.getNumeroCertidaoNascimento())){
					
					if(pessoaBanco.getNumeroCertidaoNascimento().equals("")){
						
						List<Pessoa> listaPessoa = null;
						listaPessoa = buscaCertidao(pessoa.getNumeroCertidaoNascimento());
						if(!listaPessoa.isEmpty()){
							throw new Exception("A Certidão editada já está cadastrada em outro cidadão");
						}
						
					}else if(!pessoaBanco.getNumeroCertidaoNascimento().equals(pessoa.getNumeroCertidaoNascimento())){
						
						List<Pessoa> listaPessoa = null;
						listaPessoa = buscaCertidao(pessoa.getNumeroCertidaoNascimento());
						if(!listaPessoa.isEmpty()){
							throw new Exception("A Certidão editada já está cadastrada em outro cidadão");
						}
						
					}
				}
				
				if(!"".equals(pessoa.getRg()) && !"".equals(pessoa.getOrgao())){
					
					if(pessoaBanco.getRg().equals("") && pessoaBanco.getOrgao() == null ){
						
						List<Pessoa> listaPessoa = null;
						listaPessoa = buscaRG(pessoa.getRg(), pessoa.getOrgao());
						if(!listaPessoa.isEmpty()){
							throw new Exception("O RG editado já está cadastrado em outro cidadão");
						}
						
					}else if(!pessoaBanco.getRg().equals(pessoa.getRg()) && !pessoaBanco.getOrgao().equals(pessoa.getOrgao())){
						
						List<Pessoa> listaPessoa = null;
						listaPessoa = buscaRG(pessoa.getRg(), pessoa.getOrgao());
						if(!listaPessoa.isEmpty()){
							throw new Exception("O RG editado já está cadastrado em outro cidadão");
						}
						
					}
				}						
			}
																

			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(pessoa);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
			return pessoa;							
			
			
		} catch (Exception e) {		
			if(transaction != null){
				transaction.rollback();
			}
			System.out.println("ERROO Não consegui salvar pessoa..."+e.getMessage());
			throw new Exception("ERROO Não consegui salvar pessoa... "+e.getMessage());
		}finally{
			sessao.close();
		}			
	}
	
	
	public Pessoa salvarSemVerificacaoExistencia(Pessoa pessoa) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;			
		
		try {												
			
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(pessoa);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
			return pessoa;							
			
		} catch (Exception e) {		
			if(transaction != null){
				transaction.rollback();
			}
			System.out.println("ERROO Não consegui salvar pessoa..."+e.getMessage());
			throw new Exception("ERROO Não consegui salvar pessoa... "+e.getMessage());
		}finally{
			sessao.close();
		}			
	}
	
	
	
	
	public List<Pessoa> buscaCPF(String cpf) throws Exception {		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Pessoa> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Pessoa.class);			
			criteria.add(Restrictions.ilike("cpf", cpf));
			lista =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO busca CPF...."+e.getMessage());
			throw new Exception("ERRO busca CPF...."+e.getMessage());
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}
	
	public List<Pessoa> buscaCertidao(String certidao) throws Exception {		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Pessoa> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Pessoa.class);			
			criteria.add(Restrictions.ilike("numeroCertidaoNascimento", certidao));
			lista =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO busca certidao...."+e.getMessage());
			throw new Exception("ERRO busca certidao...."+e.getMessage());
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}
	
	public List<Pessoa> buscaRG(String rg, Orgao orgao) throws Exception {		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Pessoa> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Pessoa.class);			
			criteria.add(Restrictions.ilike("rg", rg));
			criteria.add(Restrictions.eq("orgao", orgao));
			lista =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception("ERRO buscar RG...."+e.getMessage());
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}
	
	public Boolean verificaCadastro(Pessoa pessoa) throws Exception {
		
		Boolean jaCadastrado = Boolean.FALSE;
		
		try {
//			verifica se o cpf já está cadastrado no banco de dados
			if(!"".equals(pessoa.getCpf())){
				List<Pessoa> listaPessoa = null;
				listaPessoa = buscaCPF(pessoa.getCpf());
				if(!listaPessoa.isEmpty()){
					jaCadastrado = Boolean.TRUE;
					throw new Exception("CPF já cadastrado");
				}
			}
			
//			verifica se a certidao de nascimento já está cadastrado no banco de dados			
			if(!"".equals(pessoa.getNumeroCertidaoNascimento())){				
				List<Pessoa> listaPessoa = null;
				listaPessoa = buscaCertidao(pessoa.getNumeroCertidaoNascimento());
				if(!listaPessoa.isEmpty()){
					jaCadastrado = Boolean.TRUE;
					throw new Exception("certidao de nascimento já cadastrada");
				}				
			}
			
//			verifica se o RG já está cadastrado no banco de dados			
			if(!"".equals(pessoa.getRg()) && pessoa.getOrgao() != null){
				List<Pessoa> listaPessoa = null;
				listaPessoa = buscaRG(pessoa.getRg(), pessoa.getOrgao());
				if(!listaPessoa.isEmpty()){
					jaCadastrado = Boolean.TRUE;
					throw new Exception("RG já cadastrado");
				}
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return jaCadastrado;
		
	}
	
	public Pessoa buscaCodigo(Long id)throws Exception{
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria criteria = sessao.createCriteria(Pessoa.class);			
			criteria.add(Restrictions.idEq(id));
			return (Pessoa) criteria.uniqueResult();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			sessao.close();
		}					
	}
	
	public List<Pessoa> buscaPorNome(String nome) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Pessoa> lista = null;

		try {
			Criteria criteria = sessao.createCriteria(Pessoa.class);			
			if (!"".equals(nome)) {
				
				criteria.add(Restrictions.ilike("nomeCompleto", "%" + nome + "%"));
			}
			
			criteria.addOrder(Order.asc("nomeCompleto"));
			lista = criteria.list();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
		return lista;
		
	}
	
	public Pessoa buscaPorDocumento(String tipoBusca, String documento, Orgao orgao) throws Exception {			
		
		List<Pessoa> lista;
		Pessoa pessoa = null;
		
		switch (tipoBusca) {
		case "RG":						
			lista = buscaRG(documento, orgao);														
			break;
			
		case "CPF":
			lista = buscaCPF(documento);
			break;
		
		case "CrtNasc":
			lista = buscaCertidao(documento);			
			break;

		default:
			throw new Exception("Nenhuma opção foi encontrada");
		}
		
		if(lista.size() > 1){
			throw new Exception("Este documento está duplicado!!");
		}else if(!lista.isEmpty()){			
			pessoa = lista.get(0);
//			if(pessoa.getDataNascimento() != null){
//				if(Helper.executaCalculoIdade(pessoa.getDataNascimento()) > 18){
//					throw new Exception("Este documento pertence ao cadastro de um adulto!");
//				}
//			}								
		}						
		return pessoa;		
	}
	
	public Pessoa buscaMenor(String tipoBusca, String documento, Orgao orgao) throws Exception {			
		
		List<Pessoa> lista;
		Pessoa pessoa = null;
		
		switch (tipoBusca) {
		case "RG":						
			lista = buscaRG(documento, orgao);														
			break;
			
		case "CPF":
			lista = buscaCPF(documento);
			break;
		
		case "CrtNasc":
			lista = buscaCertidao(documento);			
			break;

		default:
			throw new Exception("Nenhuma opção foi encontrada");
		}
		
		if(lista.size() > 1){
			throw new Exception("Este documento está duplicado!!");
		}else if(!lista.isEmpty()){			
			pessoa = lista.get(0);
			if(pessoa.getDataNascimento() != null){
				if(Helper.executaCalculoIdade(pessoa.getDataNascimento()) > 18){
					throw new Exception("Este documento pertence ao cadastro de um adulto!");
				}
			}								
		}						
		return pessoa;		
	}
	
	public Pessoa buscaMaior(String tipoBusca, String documento, Orgao orgao) throws Exception {			
		
		List<Pessoa> lista;
		Pessoa pessoa = null;
		
		switch (tipoBusca) {
		case "RG":						
			lista = buscaRG(documento, orgao);														
			break;
			
		case "CPF":
			lista = buscaCPF(documento);
			break;

		default:
			throw new Exception("Nenhuma opção foi encontrada");
		}
		
		if(lista.size() > 1){
			throw new Exception("Este documento está duplicado!!");
		}else if(!lista.isEmpty()){			
			pessoa = lista.get(0);
			if(pessoa.getDataNascimento() != null){
				if(Helper.executaCalculoIdade(pessoa.getDataNascimento()) < 18){
					throw new Exception("Este documento pertence ao cadastro de uma criança!");
				}
			}								
		}						
		return pessoa;		
	}
	
	public void excluir(Pessoa pessoa) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(pessoa);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERROO..." + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			sessao.close();
		}
		
	}
	
}
