package br.com.conselho.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Membro;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.domain.ResponsavelNovo;
import br.com.conselho.util.HibernateUtil;

public class ResponsavelNovoDAO {

	public void salvar(ResponsavelNovo ResponsavelNovo) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(ResponsavelNovo);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERRO ao salvar ResponsavelNovo..." + e.getMessage());
			throw new Exception(e);
		} finally {
			sessao.close();
		}

	}
	
	public void excluir(ResponsavelNovo ResponsavelNovo) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(ResponsavelNovo);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERROO ao excluir ResponsavelNovo..." + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			sessao.close();
		}
		
	}

	public List<ResponsavelNovo> buscaResponsavelNovo(String nome) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<ResponsavelNovo> lista = null;

		try {
			Criteria criteria = sessao.createCriteria(ResponsavelNovo.class);			
			criteria.createAlias("pessoa", "p");
			if (!"".equals(nome)) {
				
				criteria.add(Restrictions.ilike("p.nomeCompleto", "%" + nome + "%"));
			}
			//criteria.addOrder(Order.asc("p.nomeCompleto"));
			lista = criteria.list();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
		return lista;		
	}
	
	public ResponsavelNovo buscaResponsavelNovo(Long id) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<ResponsavelNovo> lista = null;

		try {
			Criteria criteria = sessao.createCriteria(ResponsavelNovo.class);
			criteria.add(Restrictions.idEq(id));
			
			lista = criteria.list();
			if(!lista.isEmpty()){
				return lista.get(0);
			}else{
				return null;
			}
			
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
	}
	
	public ResponsavelNovo buscaResponsavelNovoPorMembros(Membro membroPrincipal, Membro membroSec) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();

		try {
			Criteria criteria = sessao.createCriteria(ResponsavelNovo.class);										
			criteria.add(Restrictions.eq("membroPrincipal", membroPrincipal));
			criteria.add(Restrictions.eq("membro", membroSec));
					
			return (ResponsavelNovo) criteria.uniqueResult();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
	}
	
	public List<ResponsavelNovo> buscaResponsavelNovoPorInstituicao(Instituicao instituicao, CriancaAdolescente crianca) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<ResponsavelNovo> lista = new ArrayList<ResponsavelNovo>();

		try {
			Criteria criteria = sessao.createCriteria(ResponsavelNovo.class);			
			//criteria.createAlias("pessoa", "p");							
			criteria.add(Restrictions.eq("instituicao", instituicao));
			criteria.add(Restrictions.eq("criancaAdolescente", crianca));
					
			lista = criteria.list();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
		return lista;		
	}
	
	public List<ResponsavelNovo> buscaResponsavelNovoInstituicaoPorCrianca(CriancaAdolescente crianca) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<ResponsavelNovo> lista = new ArrayList<ResponsavelNovo>();

		try {
			Criteria criteria = sessao.createCriteria(ResponsavelNovo.class);			
			criteria.add(Restrictions.isNotNull("instituicao"));										
			criteria.add(Restrictions.eq("criancaAdolescente", crianca));
					
			lista = criteria.list();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
		return lista;		
	}
	
	public List<ResponsavelNovo> buscaResponsavelNovoPessoaPorCrianca(CriancaAdolescente crianca) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<ResponsavelNovo> lista = new ArrayList<ResponsavelNovo>();

		try {
			Criteria criteria = sessao.createCriteria(ResponsavelNovo.class);			
			criteria.add(Restrictions.isNotNull("pessoa"));										
			criteria.add(Restrictions.eq("criancaAdolescente", crianca));
			criteria.createAlias("grauResponsabilidade", "gr");
			criteria.add(Restrictions.not(Restrictions.like("gr.nome", "Nenhum")));
			
			lista = criteria.list();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
		return lista;		
	}
	
	public List<ResponsavelNovo> buscaResponsavelNovo(CriancaAdolescente crianca) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<ResponsavelNovo> lista = new ArrayList<ResponsavelNovo>();

		try {
			Criteria criteria = sessao.createCriteria(ResponsavelNovo.class);											
			criteria.add(Restrictions.eq("criancaAdolescente", crianca));
			
			lista = criteria.list();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
		return lista;		
	}

}
