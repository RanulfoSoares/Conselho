package br.com.conselho.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.Conselheiro;
import br.com.conselho.domain.Familia;
import br.com.conselho.domain.Membro;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.util.Helper;
import br.com.conselho.util.HibernateUtil;

@SuppressWarnings("serial")
public class FamiliaDAO implements Serializable {
	
	public Familia salvar(Familia familia) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;			
		
		try {												
			
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(familia);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
			return familia;							
			
		} catch (Exception e) {		
			if(transaction != null){
				transaction.rollback();
			}
			System.out.println("ERROO Não consegui salvar Nucleo Familiar..."+e.getMessage());
			throw new Exception("ERROO Não consegui salvar Nucleo Familiar... "+e.getMessage());
		}finally{
			sessao.close();
		}			
	}
	
	public Familia buscaRegistro(String registro) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Familia familia = null;
		
		registro = Helper.formataNumeroPasta(registro);	
		
		try {
			Criteria criteria = sessao.createCriteria(Familia.class);						
			criteria.add(Restrictions.eq("numeroPasta", Helper.formataNumeroPasta(registro) ));
			familia = (Familia) criteria.uniqueResult();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
		return familia;
		
	}
	
	public Familia buscaUltimoRegistro() throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Familia familia = null;				
		
		try {
			Criteria criteria = sessao.createCriteria(Familia.class);
			criteria.addOrder(Order.asc("id"));
			if(criteria.list().isEmpty()){
				familia = null;
				
			}else{
				
				List<Familia> listFamilia = criteria.list();
				
				for (Familia familia2 : listFamilia) {
					System.out.println("Familia: "+familia2.getId());
				}
				
				familia = (Familia) criteria.list().get(criteria.list().size() -1);
				System.out.println("Ultima Familia: "+familia.getId());
			}
			
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
		return familia;
		
	}
	
	public List<Familia> busca(Conselheiro conselheiro, String numeroRegistro) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Familia> lista = null;				

		try {
			Criteria criteria = sessao.createCriteria(Familia.class);
			
			if(conselheiro != null){
				criteria.add(Restrictions.eq("conselheiroRegistro", conselheiro));								
			}
			
			if(numeroRegistro != null){				
				numeroRegistro = Helper.formataNumeroPasta(numeroRegistro);
				criteria.add(Restrictions.like("numeroPasta", numeroRegistro));				
			}
			
			
			
			criteria.addOrder(Order.desc("dataCadastro"));
			lista = criteria.list();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
		return lista;
		
	}		
	
	public Familia buscaCodigo(Long id)throws Exception{
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria criteria = sessao.createCriteria(Familia.class);			
			criteria.add(Restrictions.idEq(id));
			return (Familia) criteria.uniqueResult();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			sessao.close();
		}					
	}
	
	public void excluir(Familia familia) throws Exception{
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(familia);
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
