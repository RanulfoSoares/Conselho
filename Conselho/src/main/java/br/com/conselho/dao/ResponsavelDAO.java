package br.com.conselho.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.domain.Responsavel;
import br.com.conselho.util.HibernateUtil;

public class ResponsavelDAO {

	public void salvar(Responsavel responsavel) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(responsavel);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERRO ao salvar responsavel..." + e.getMessage());
			throw new Exception(e);
		} finally {
			sessao.close();
		}

	}
	
	public void excluir(Responsavel responsavel) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(responsavel);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERROO ao excluir responsavel..." + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			sessao.close();
		}
		
	}

	public List<Responsavel> buscaResponsavel(String nome) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Responsavel> lista = null;

		try {
			Criteria criteria = sessao.createCriteria(Responsavel.class);			
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
	
	public Responsavel buscaResponsavel(Long id) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Responsavel> lista = null;

		try {
			Criteria criteria = sessao.createCriteria(Responsavel.class);
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
	
	public List<Responsavel> buscaResponsavelPorCrianca(Pessoa pessoa, CriancaAdolescente crianca) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Responsavel> lista = new ArrayList<Responsavel>();

		try {
			Criteria criteria = sessao.createCriteria(Responsavel.class);										
			criteria.add(Restrictions.eq("pessoa", pessoa));
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
	
	public List<Responsavel> buscaResponsavelPorInstituicao(Instituicao instituicao, CriancaAdolescente crianca) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Responsavel> lista = new ArrayList<Responsavel>();

		try {
			Criteria criteria = sessao.createCriteria(Responsavel.class);			
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
	
	public List<Responsavel> buscaResponsavelInstituicaoPorCrianca(CriancaAdolescente crianca) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Responsavel> lista = new ArrayList<Responsavel>();

		try {
			Criteria criteria = sessao.createCriteria(Responsavel.class);			
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
	
	public List<Responsavel> buscaResponsavelPessoaPorCrianca(CriancaAdolescente crianca) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Responsavel> lista = new ArrayList<Responsavel>();

		try {
			Criteria criteria = sessao.createCriteria(Responsavel.class);			
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
	
	public List<Responsavel> buscaResponsavel(CriancaAdolescente crianca) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Responsavel> lista = new ArrayList<Responsavel>();

		try {
			Criteria criteria = sessao.createCriteria(Responsavel.class);											
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
