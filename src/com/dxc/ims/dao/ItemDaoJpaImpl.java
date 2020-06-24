package com.dxc.ims.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.dxc.ims.exception.InventoryException;
import com.dxc.ims.model.Item;

public class ItemDaoJpaImpl implements ItemDAO {

	static {
		em = Persistence.createEntityManagerFactory("mysqlPU").createEntityManager();
	}

	private static EntityManager em;

	@Override
	public void addItem(Item item) throws InventoryException {
		if (item != null) {
			EntityTransaction txn = em.getTransaction();
			txn.begin();
			em.persist(item);
			txn.commit();
			em.flush();
		}

	}

	@Override
	public void deleteItem(int icode) throws InventoryException {

		EntityTransaction txn = em.getTransaction();

		Item item = em.find(Item.class, icode);
		if (item != null) {
			txn.begin();
			em.remove(item);
			txn.commit();
			em.flush();
		} else {
			throw new InventoryException("No such Item Found!!");
		}

	}

	@Override
	public List<Item> getAllItems() throws InventoryException {
		TypedQuery<Item> qry = em.createNamedQuery("allItemsQry", Item.class);
		return qry.getResultList();
	}

	@Override
	public Item getItemByIcode(int icode) throws InventoryException {
		TypedQuery<Item> qry = em.createNamedQuery("itemQry", Item.class);
		qry.setParameter("icode", icode);
		return qry.getFirstResult() > 0 ? qry.getSingleResult() : null;
	}
}
