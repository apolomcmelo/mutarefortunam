package br.com.apolo.mc.melo.dao;

import javax.persistence.EntityManager;

import br.com.apolo.mc.melo.entidades.Sorteio;

public class SorteioDAO extends GenericDAO<Integer, Sorteio> {
    public SorteioDAO(EntityManager entityManager) {
        super(entityManager);
    }   
}