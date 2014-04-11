/**
 * 
 */
package com.davew.website.templates.train;

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
public class TrainDAO {
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
	public List<Train> getAllTrains() {
		Session session = sessionFactory.getCurrentSession();
		Query trainQuery = session.createQuery("FROM Train");
		trainQuery.setFirstResult(getFirstResult());
		trainQuery.setMaxResults(getMaxResults());

		List trains = trainQuery.list();

		return trains;
	}

	@Transactional
	public Train getTrain(long id) {
		Session session = sessionFactory.getCurrentSession();
		Query trainQuery = session.createQuery("FROM Train WHERE id=:id")
				.setLong("id", id);

		List trains = trainQuery.list();

		Train train;
		try {
			train = (Train) trains.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			train = new Train();
		}

		return train;
	}

	@Transactional
	public Train updateTrain(Train train) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		
		try {

			Transaction tx = null;
	
			try {
				tx = session.beginTransaction();
				session.update(train);
				tx.commit();
	
				// force the SQL INSERT
				// session.flush();
				// session.refresh(train);
			
			} catch (Exception e) {
				tx.rollback();
				throw e;
			}
		} finally {
			
			sessionFactory.close();
			sessionFactory.openSession();
		}
		return train;
	}

	@Transactional
	public Train saveTrain(Train train) {
		Session session = sessionFactory.getCurrentSession();

		try {
			session.save(train);
			// force the SQL INSERT
			// session.flush();
			// session.refresh(train);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return train;
	}

	@Transactional
	public void deleteTrain(Train train) {
		Session session = sessionFactory.getCurrentSession();
		try {
			// Pass train to database
			session.delete(train);

			// force the SQL INSERT
			session.flush();
			session.refresh(train);

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
