package br.com.conselho.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.Violador;
import br.com.conselho.util.HibernateUtil;

public class VioladorDAO {
	
	public void salvar(Violador violador) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(violador);
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
	
	public void excluir(Violador violador) throws Exception{
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(violador);
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
	
	public Long totalFeminino(Date inicio, Date fim) throws Exception{
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = sessao.createCriteria(Violador.class);
		criteria.createAlias("atendimento", "a");
		criteria.add(Restrictions.between("a.dataFato", inicio, fim));		
		criteria.createAlias("pessoa", "p");
		criteria.add(Restrictions.eq("p.sexo", "F"));
		criteria.setProjection(Projections.rowCount());
		Long result = (Long) criteria.uniqueResult();
		return result;
				
	}
	
	public Long totalMasculino(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.createAlias("pessoa", "p");
			criteria.add(Restrictions.eq("p.sexo", "M"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}				
	}
	
	public Long totalMaior(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.add(Restrictions.ge("idade", 18));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}				
	}
	
	public Long totalMenor(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.add(Restrictions.lt("idade", 18));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			System.out.println("ERRO...: "+e.getMessage());
			throw new Exception(e.getMessage());
		}				
	}
	
//	public Long totalNaoIdentificado(Date inicio, Date fim) throws Exception{
//		try {
//			
//			Session sessao = HibernateUtil.getSessionFactory().openSession();
//			Criteria criteria = sessao.createCriteria(Violador.class);
//			criteria.createAlias("atendimento", "a");
//			criteria.add(Restrictions.eq("a.violadorNaoIdentificado", true));
//			criteria.add(Restrictions.between("a.dataFato", inicio, fim));			
//			criteria.setProjection(Projections.rowCount());
//			Long result = (Long) criteria.uniqueResult();
//			return result;
//			
//		} catch (Exception e) {
//			System.out.println("ERRO...: "+e.getMessage());
//			throw new Exception(e.getMessage());
//		}				
//	}
	
	public Long totalInstituicao(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.add(Restrictions.isNotNull("instituicao"));
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));			
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			System.out.println("ERRO...: "+e.getMessage());
			throw new Exception(e.getMessage());
		}				
	}

	public Long totalAmarela(Date inicio, Date fim) throws Exception {
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.createAlias("pessoa", "p");
			criteria.add(Restrictions.eq("p.cor", "AM"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			System.out.println("ERRO...: "+e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	public Long totalBranca(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.createAlias("pessoa", "p");
			criteria.add(Restrictions.eq("p.cor", "BR"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			System.out.println("ERRO...: "+e.getMessage());
			throw new Exception(e.getMessage());
		}		
	}
	
	public Long totalIndigena(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.createAlias("pessoa", "p");
			criteria.add(Restrictions.eq("p.cor", "IN"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			System.out.println("ERRO...: "+e.getMessage());
			throw new Exception(e.getMessage());
		}		
	}
	
	public Long totalParda(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));			
			criteria.createAlias("pessoa", "p");
			criteria.add(Restrictions.eq("p.cor", "PA"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			System.out.println("ERRO...: "+e.getMessage());
			throw new Exception(e.getMessage());
		}		
	}
	
	public Long totalPreta(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.createAlias("pessoa", "p");
			criteria.add(Restrictions.eq("p.cor", "PR"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			System.out.println("ERRO...: "+e.getMessage());
			throw new Exception(e.getMessage());
		}		
	}
	
	public Long totalAmasiada(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.add(Restrictions.eq("estadoCivil", "AM"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			System.out.println("ERRO...: "+e.getMessage());
			throw new Exception(e.getMessage());
		}		
	}
	
	public Long totalCasada(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.add(Restrictions.eq("estadoCivil", "CA"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			System.out.println("ERRO...: "+e.getMessage());
			throw new Exception(e.getMessage());
		}		
	}
	
	public Long totalSeparada(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.add(Restrictions.eq("estadoCivil", "SE"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			System.out.println("ERRO...: "+e.getMessage());
			throw new Exception(e.getMessage());
		}		
	}
	
	public Long totalDesconhecido(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));			
			criteria.add(Restrictions.eq("estadoCivil", "DE"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			System.out.println("ERRO...: "+e.getMessage());
			throw new Exception(e.getMessage());
		}		
	}
	
	public Long totalSolteira(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));			
			criteria.add(Restrictions.eq("estadoCivil", "SO"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			System.out.println("ERRO...: "+e.getMessage());
			throw new Exception(e.getMessage());
		}		
	}
	
	public Long totalViuva(Date inicio, Date fim) throws Exception{
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));			
			criteria.add(Restrictions.eq("estadoCivil", "VI"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			System.out.println("ERRO...: "+e.getMessage());
			throw new Exception(e.getMessage());
		}		
	}
		
	public Long totalSuperior(Date inicio, Date fim) throws Exception{
		
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.createAlias("pessoa", "p");
			criteria.createAlias("p.escolaridade", "e");
			criteria.add(Restrictions.in("e.nome", new String[]{"Superior"}));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	public Long totalEnsinoMedio(Date inicio, Date fim) throws Exception{
		
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.createAlias("pessoa", "p");
			criteria.createAlias("p.escolaridade", "e");
			criteria.add(Restrictions.in("e.nome", new String[]{"1º Ensino médio","2º Ensino médio","3º Ensino médio"}));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	public Long totalFundamentalPrimeira(Date inicio, Date fim) throws Exception{
		
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.createAlias("pessoa", "p");
			criteria.createAlias("p.escolaridade", "e");
			criteria.add(Restrictions.in("e.nome", new String[]{"1ª Ensino fundamental",
																"2ª Ensino fundamental",
																"3ª Ensino fundamental",
																"4ª Ensino fundamental",
																"5ª Ensino fundamental"}));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	public Long totalFundamentalSexta(Date inicio, Date fim) throws Exception{
		
		try {
			
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Violador.class);
			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.between("a.dataFato", inicio, fim));
			criteria.createAlias("pessoa", "p");
			criteria.createAlias("p.escolaridade", "e");
			criteria.add(Restrictions.in("e.nome", new String[]{"6ª Ensino fundamental",
																"7ª Ensino fundamental",
																"8ª Ensino fundamental",
																"9ª Ensino fundamental"}));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

}
