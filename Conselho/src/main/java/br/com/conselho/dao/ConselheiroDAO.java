package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.Conselheiro;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.util.HibernateUtil;

public class ConselheiroDAO {
	
	public void salvar(Conselheiro conselheiro) throws Exception{
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;	
		
		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(conselheiro);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			System.out.println("ERROO..."+e.getMessage());
			throw new Exception();
		}finally{
			sessao.close();
		}		
	}
	
	public List<Conselheiro> busca(String nome, boolean checkDesativado) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Conselheiro> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Conselheiro.class);
			criteria.createAlias("pessoa", "p");
			
			if(checkDesativado){
				criteria.add(Restrictions.ilike("flgAtivo", "N"));
			}else{
				criteria.add(Restrictions.ilike("flgAtivo", "S"));
			}
			
			
			if(!"".equals(nome)){				
				criteria.add(Restrictions.ilike("p.nomeCompleto", "%"+nome+"%"));
			}
			
			criteria.addOrder(Order.asc("p.nomeCompleto"));
			lista =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception();
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}
	
	public Conselheiro busca(Long id)throws Exception{
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria criteria = sessao.createCriteria(Conselheiro.class);			
			criteria.add(Restrictions.idEq(id));
			return (Conselheiro) criteria.uniqueResult();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			sessao.close();
		}					
	}
	
	public Conselheiro buscaLogin(String cpf, String senha) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Conselheiro usuario = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Conselheiro.class);
			criteria.createAlias("pessoa", "p");
			criteria.add(Restrictions.ilike("p.cpf", cpf.replaceAll("\\.", "").replaceAll("-", "")));
			criteria.add(Restrictions.ilike("senha", senha));
						
			usuario =  (Conselheiro) criteria.uniqueResult();				
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return usuario;
		
	}
	
	public Conselheiro conselheiroPorPessoa(Pessoa pessoa) throws Exception {
				
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Conselheiro conselheiro = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Conselheiro.class);
			criteria.add(Restrictions.eq("pessoa", pessoa));					
			criteria.add(Restrictions.ilike("flgAtivo", "S"));			
			conselheiro =  (Conselheiro) criteria.uniqueResult();				
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return conselheiro;
		
	}
	
}
