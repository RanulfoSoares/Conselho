package br.com.conselho.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.DireitoViolado;
import br.com.conselho.domain.MedidaAplicada;
import br.com.conselho.domain.RegistroDireitoViolado;
import br.com.conselho.domain.RegistroMedidaAplicada;
import br.com.conselho.util.HibernateUtil;

public class RegistroMedidaAplicadaDAO {
	
	public void salvar(RegistroMedidaAplicada registroMedidaAplicada) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		
		try {
			transaction = sessao.beginTransaction();
			sessao.save(registroMedidaAplicada);
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
	
	public List<MedidaAplicada> buscaMedidaAplicadaUtilizados(Date inicio, Date fim) throws Exception {		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<MedidaAplicada> lista;
		
		try{			
			// subtrai um dia da data de inicio
			Calendar c = Calendar.getInstance();
			c.setTime(inicio);			
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);			
			System.out.println("DATA INICIO: "+new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));			
			inicio = c.getTime();
			
			// adiciona um dia da data fim		
			c.setTime(fim);			
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);			
			System.out.println("DATA FIM: "+new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));			
			fim = c.getTime();
						
			
			Criteria criteria = sessao.createCriteria(RegistroMedidaAplicada.class);
			criteria.add(Restrictions.between("data", inicio, fim));
			criteria.setProjection(Projections.distinct(Projections.property("medidaAplicada")));
			lista =  criteria.list();				
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;		
	}
	
	public List<RegistroMedidaAplicada> buscaTodosRegistroMedidaAplicadaUtilizados(Date inicio, Date fim) throws Exception {		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<RegistroMedidaAplicada> lista;
		
		try{			
			// subtrai um dia da data de inicio
			Calendar c = Calendar.getInstance();
			c.setTime(inicio);			
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);			
			System.out.println("DATA INICIO: "+new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));			
			inicio = c.getTime();
			
			// adiciona um dia da data fim		
			c.setTime(fim);			
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);			
			System.out.println("DATA FIM: "+new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));			
			fim = c.getTime();
						
			
			Criteria criteria = sessao.createCriteria(RegistroMedidaAplicada.class);
			criteria.add(Restrictions.between("data", inicio, fim));			
			lista =  criteria.list();				
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}
	
	public List<RegistroMedidaAplicada> buscaTodosRegistroMedidaAplicadaPorBairro(Date inicio, Date fim, Bairro bairro) throws Exception {		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<RegistroMedidaAplicada> lista;
		
		try{			
			// subtrai um dia da data de inicio
			Calendar c = Calendar.getInstance();
			c.setTime(inicio);			
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);			
			System.out.println("DATA INICIO: "+new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));			
			inicio = c.getTime();
			
			// adiciona um dia da data fim		
			c.setTime(fim);			
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);			
			System.out.println("DATA FIM: "+new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));			
			fim = c.getTime();
						
			
			Criteria criteria = sessao.createCriteria(RegistroMedidaAplicada.class);
			criteria.add(Restrictions.between("data", inicio, fim));
			criteria.createAlias("registroDireitoViolado", "r");
			criteria.createAlias("r.atendimento", "a");
			criteria.createAlias("a.logradouroFato", "l");
			criteria.createAlias("l.bairro", "b");
			criteria.add(Restrictions.eq("b.id", bairro.getId()));
			lista =  criteria.list();				
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}

}
