package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.UsuarioConselho;
import br.com.conselho.util.HibernateUtil;

public class UsuarioConselheiroDAO {
	
	public void salvar(UsuarioConselho usuario) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		
		try{
			transaction = sessao.beginTransaction();
			sessao.save(usuario);
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
	
	public UsuarioConselho buscaUsuario(String user, String senha) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		UsuarioConselho usuario = null;
		
		try{
			Criteria criteria = sessao.createCriteria(UsuarioConselho.class);						
			criteria.add(Restrictions.ilike("usuario", user));
			criteria.add(Restrictions.ilike("pass", senha));
						
			usuario =  (UsuarioConselho) criteria.uniqueResult();				
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return usuario;
		
	}

}
