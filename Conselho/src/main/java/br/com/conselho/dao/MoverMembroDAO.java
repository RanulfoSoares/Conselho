package br.com.conselho.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.Membro;
import br.com.conselho.domain.MoverMembro;
import br.com.conselho.domain.Status;
import br.com.conselho.util.HibernateUtil;

public class MoverMembroDAO {
	
	public MoverMembro salvar(MoverMembro moverMembro) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;			
		Membro membro;
		MembroDao membroDao = new MembroDao();
		
		try {												
			
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(moverMembro);
				
				/**
				 * faz desativação do membro do nucleo antigo e o insere no nucleo de destino
				 */
			
				Status s = new Status();
				
				// Desativação do membro 
				membro = moverMembro.getMembro();								
				s.setId(2L);
				membro.setStatus(s);
				membro.setDataDesvinculo(new Date());								
				membroDao.salvar(membro);
				
				
				// inserindo no novo nucleo
				membro = new Membro();
				s.setId(1L);											
				membro.setPessoa(moverMembro.getMembro().getPessoa());
				
				if(moverMembro.getFamiliaDestino() != null){
					membro.setFamilia(moverMembro.getFamiliaDestino());
					
				}else if(moverMembro.getInstituicaoDestino() != null){
					membro.setInstituicao(moverMembro.getInstituicaoDestino());
				}
				
				
				membro.setStatus(s);
				membro.setDataCadastro(new Date());				
				membroDao.salvar(membro);
			
			// commita a transicao de nucleo...	
			transaction.commit();						
			
			System.out.println("Sucesso!!!!...");
			return moverMembro;							
			
		} catch (Exception e) {		
			if(transaction != null){
				transaction.rollback();
			}
			System.out.println("ERROO Não consegui movimentar membro..."+e.getMessage());
			throw new Exception("ERROO Não consegui movimentar membro... "+e.getMessage());
		}finally{
			sessao.close();
		}			
	}
	
	public void excluir(MoverMembro moverMembro) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(moverMembro);
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
	
	public List<MoverMembro> buscaMembro(Membro membro) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<MoverMembro> listaMembro = null;

		try {
			Criteria criteria = sessao.createCriteria(MoverMembro.class);						
			criteria.add(Restrictions.eq("membro", membro));
			listaMembro = criteria.list();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
		return listaMembro;
		
	}			

}
