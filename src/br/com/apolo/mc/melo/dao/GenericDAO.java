package br.com.apolo.mc.melo.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;

@SuppressWarnings("unchecked")
public class GenericDAO<PK, T> {
	private EntityManager entityManager;

	public GenericDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public T getById(PK pk) {
		return (T) entityManager.find(getTypeClass(), pk);
	}

	public void salvar(T entity) {
		entityManager.persist(entity);
	}

	public void atualizar(T entity) {
		entityManager.merge(entity);
	}

	public void excluir(T entity) {
		entityManager.remove(entity);
	}

	public List<T> listar() {
		return entityManager.createQuery(("FROM " + getTypeClass().getName()))
				.getResultList();
	}

	public Class<?> getTypeClass() {
		Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1];
		return clazz;
	}
}