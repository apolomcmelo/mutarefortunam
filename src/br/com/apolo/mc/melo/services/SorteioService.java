package br.com.apolo.mc.melo.services;

import java.util.List;

import br.com.apolo.mc.melo.dao.SorteioDAO;
import br.com.apolo.mc.melo.entidades.Sorteio;
import br.com.apolo.mc.melo.managers.SimpleEntityManager;

public class SorteioService {
    private SorteioDAO sorteioDAO;
    private SimpleEntityManager simpleEntityManager;
     
    public SorteioService() {
    	this.simpleEntityManager = new SimpleEntityManager();
    	sorteioDAO = new SorteioDAO(simpleEntityManager.getEntityManager());
    }
    
    public SorteioService(SimpleEntityManager simpleEntityManager){
        this.simpleEntityManager = simpleEntityManager;
        sorteioDAO = new SorteioDAO(simpleEntityManager.getEntityManager());
    }
     
    public void salvar(Sorteio sorteio){
        try{
            simpleEntityManager.beginTransaction();
            sorteioDAO.salvar(sorteio);
            simpleEntityManager.commit();
        }catch(Exception e){
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }finally {
        	simpleEntityManager.close();
        }
    }
    
    public void salvarTodos(List<Sorteio> sorteios){
    	try{
    		simpleEntityManager.beginTransaction();
    		for(int i=0; i<sorteios.size(); i++) {
    			sorteioDAO.salvar(sorteios.get(i));
    		}
    		simpleEntityManager.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    		simpleEntityManager.rollBack();
    	}finally {
    		simpleEntityManager.close();
    	}
    }
    
    public List<Sorteio> listar(){
        return sorteioDAO.listar();
    }
    
    public Sorteio buscarSorteio(int concurso) {
		return (Sorteio) simpleEntityManager.getEntityManager()
				.createQuery(
						(" FROM Sorteio WHERE concurso = " + concurso));
    }
    
	public Sorteio buscarUltimoSorteio() {
		return (Sorteio) simpleEntityManager.getEntityManager()
				.createQuery(
						(" FROM Sorteio ORDER BY concurso DESC")).setMaxResults(1).getSingleResult();
    }
}