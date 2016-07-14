package br.com.conselho.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.Atendimento;
import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.CaracterizacaoViolacaoDireito;
import br.com.conselho.domain.CaracterizarDireitoViolado;
import br.com.conselho.domain.DireitoViolado;
import br.com.conselho.domain.RegistroDireitoViolado;
import br.com.conselho.domain.Vitima;
import br.com.conselho.util.HibernateUtil;

public class RegistroDireitoVioladoDAO {
	
	public void salvar(RegistroDireitoViolado registroDireitoViolado) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		
		try {
			transaction = sessao.beginTransaction();
			sessao.save(registroDireitoViolado);
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
	
	public List<RegistroDireitoViolado> busca(Atendimento atendimento) throws Exception {		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<RegistroDireitoViolado> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(RegistroDireitoViolado.class);
//			criteria.createAlias("atendimento", "a");
			criteria.add(Restrictions.eq("atendimento", atendimento));			
			criteria.addOrder(Order.desc("dataInc"));
			lista =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception();
		}finally{
			sessao.close();
		}
		
		return lista;		
	}
	
	public List<RegistroDireitoViolado> buscaDireitoViolado(Atendimento atendimento) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<RegistroDireitoViolado> lista = new ArrayList<RegistroDireitoViolado>();
		
		try{
			Criteria criteria = sessao.createCriteria(RegistroDireitoViolado.class);						
			criteria.add(Restrictions.eq("atendimento", atendimento));			
			//criteria.addOrder(Order.desc("dataInc"));
			
			ProjectionList projList = Projections.projectionList();
	        projList.add(Projections.property("direitoViolado"));
	        projList.add(Projections.groupProperty("direitoViolado"));
			
	        criteria.setProjection(projList);
	        
	        			
			criteria.setProjection(Projections.distinct(Projections.property("direitoViolado")));			
			lista = (List<RegistroDireitoViolado>) criteria.list();				
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}
	
	public List<RegistroDireitoViolado> buscaRegistroDireitoViolado(Atendimento atendimento, CaracterizarDireitoViolado caracterizarDireitoViolado) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<RegistroDireitoViolado> lista = new ArrayList<RegistroDireitoViolado>();
		
		try{
			Criteria criteria = sessao.createCriteria(RegistroDireitoViolado.class);
			criteria.add(Restrictions.eq("atendimento", atendimento));			
			criteria.add(Restrictions.eq("caracterizarDireitoViolado", caracterizarDireitoViolado));
			lista =  criteria.list();				
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}
	
	public RegistroDireitoViolado buscaRegistroDireitoViolado(Atendimento atendimento, CaracterizarDireitoViolado caracterizarDireitoViolado, Vitima  vitima) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		RegistroDireitoViolado registroDireitoViolado = new RegistroDireitoViolado();
		
		try{
			Criteria criteria = sessao.createCriteria(RegistroDireitoViolado.class);
			criteria.add(Restrictions.eq("atendimento", atendimento));			
			criteria.add(Restrictions.eq("caracterizarDireitoViolado", caracterizarDireitoViolado));
			criteria.add(Restrictions.eq("vitima", vitima));
			registroDireitoViolado = (RegistroDireitoViolado) criteria.uniqueResult();				
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return registroDireitoViolado;
		
	}
	
	public List<DireitoViolado> buscaDireitoVioladoUtilizados(Date inicio, Date fim) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<DireitoViolado> lista;
		
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
						
			
			Criteria criteria = sessao.createCriteria(RegistroDireitoViolado.class);
			criteria.add(Restrictions.between("dataInc", inicio, fim));
			criteria.setProjection(Projections.distinct(Projections.property("direitoViolado")));
			lista =  criteria.list();				
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}
	
	public List<RegistroDireitoViolado> buscaTodosRegistroDireitoViolado(Date inicio, Date fim) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<RegistroDireitoViolado> lista;
		
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
						
			
			Criteria criteria = sessao.createCriteria(RegistroDireitoViolado.class);
			criteria.add(Restrictions.between("dataInc", inicio, fim));			
			lista =  criteria.list();				
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}
	
	public List<DireitoViolado> buscaDireitoVioladoUtilizadosPorBairro(Date inicio, Date fim, Bairro bairro) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<DireitoViolado> lista;
		
		try {
			
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
			
			Criteria criteria = sessao.createCriteria(RegistroDireitoViolado.class);
			criteria.add(Restrictions.between("dataInc", inicio, fim));
			criteria.createAlias("atendimento", "a");
			criteria.createAlias("a.logradouroFato", "l");
			criteria.createAlias("l.bairro", "b");
			criteria.add(Restrictions.eq("b.id", bairro.getId()));
			criteria.setProjection(Projections.distinct(Projections.property("direitoViolado")));
			lista =  criteria.list();
					
		} catch (Exception e) {
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}
		
		return lista;
	}
	
	public List<RegistroDireitoViolado> buscaTodosRegistroDireitoVioladoPorBairro(Date inicio, Date fim, Bairro bairro) throws Exception {		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<RegistroDireitoViolado> lista;
		
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
						
			
			Criteria criteria = sessao.createCriteria(RegistroDireitoViolado.class);
			criteria.add(Restrictions.between("dataInc", inicio, fim));
			criteria.createAlias("atendimento", "a");
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
	
	public static void main(String[] args) {				
		
		try {
			
//			RegistroDireitoVioladoDAO registroVioladoDAO = new RegistroDireitoVioladoDAO();			
//			Bairro bairro = new Bairro();
//			bairro.setId(2L);
//			List<RegistroDireitoViolado> listaRegistroDireitoViolados = registroVioladoDAO.busca(bairro);
//			
//			for (RegistroDireitoViolado registroDireitoViolado : listaRegistroDireitoViolados) {
//				System.out.println("direito violao acessado: "+registroDireitoViolado.getDireitoViolado().getNome());
//			}
			
		} catch (Exception e) {
			System.out.println("[ERRO] => "+e.getMessage());
			e.printStackTrace();
		}		
	}

}
