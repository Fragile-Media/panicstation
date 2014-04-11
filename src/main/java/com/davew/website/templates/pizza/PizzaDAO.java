/**
 * 
 */
package com.davew.website.templates.pizza;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dave
 * 
 */

@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PizzaDAO {
	@Autowired
	private SessionFactory sessionFactory;

	private int firstResult = 0;
	private int maxResults = 100;

	/**
	 * @Transactional annotation below will trigger Spring Hibernate transaction
	 *                manager to automatically create a hibernate session. See
	 *                src/main/webapp/WEB-INF/servlet-context.xml
	 * 
	 *                http://docs.jboss.org/hibernate/core/3.6/reference/en-US/
	 *                html/objectstate.html
	 */
	@Transactional
	public List<Pizza> getAllPizzas() {
		Session session = sessionFactory.getCurrentSession();
		Query pizzaQuery = session.createQuery("FROM Pizza");
		pizzaQuery.setFirstResult(getFirstResult());
		pizzaQuery.setMaxResults(getMaxResults());

		List pizzas = pizzaQuery.list();

		return pizzas;
	}

	@Transactional
	public Pizza getPizza(long id) {
		Session session = sessionFactory.getCurrentSession();
		Query pizzaQuery = session.createQuery("FROM Pizza WHERE id=:id")
				.setLong("id", id);

		List pizzas = pizzaQuery.list();

		Pizza pizza;
		try {
			pizza = (Pizza) pizzas.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			pizza = new Pizza();
		}

		return pizza;
	}

	@Transactional
	public Pizza updatePizza(Pizza pizza) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		
		try {

			Transaction tx = null;
	
			try {
				tx = session.beginTransaction();
				session.update(pizza);
				tx.commit();
	
				// force the SQL INSERT
				// session.flush();
				// session.refresh(pizza);
			
			} catch (Exception e) {
				tx.rollback();
				throw e;
			}
		} finally {
			
			sessionFactory.close();
			sessionFactory.openSession();
		}
		return pizza;
	}

	@Transactional
	public Pizza savePizza(Pizza pizza) {
		Session session = sessionFactory.getCurrentSession();

		try {
			session.save(pizza);
			// force the SQL INSERT
			// session.flush();
			// session.refresh(pizza);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pizza;
	}

	@Transactional
	public void deletePizza(Pizza pizza) {
		Session session = sessionFactory.getCurrentSession();
		try {
			// Pass pizza to database
			session.delete(pizza);

			// force the SQL INSERT
			session.flush();
			session.refresh(pizza);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the firstResult
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * @param firstResult
	 *            the firstResult to set
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	/**
	 * @return the maxResults
	 */
	public int getMaxResults() {
		return maxResults;
	}

	/**
	 * @param maxResults
	 *            the maxResults to set
	 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
}
