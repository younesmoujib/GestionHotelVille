package services;

import java.util.List;

import dao.IDaoLocalHotel;
import dao.IDaoLocale;
import dao.IDaoRemote;
import entities.Hotel;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless(name = "hotel")
public class HotelService implements  IDaoLocalHotel {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Hotel create(Hotel o) {
		em.persist(o);
		return o;
	}

	@Override
	public boolean delete(Hotel o) {
		em.remove(em.contains(o) ? o : em.merge(o));
		return true;
	}
	@Override
	public Hotel update(Hotel o) {
	
		  return em.merge(o);
	}

	@Override
	public Hotel findById(int id) {
		// TODO Auto-generated method stub
		return em.find(Hotel.class, id);
	}

	@Override
	public List<Hotel> findAll() {
		Query query = em.createQuery("select v from Hotel v");
		return query.getResultList();
	}
	
	public List<Hotel> findHotelsbyville(int id){
		Query query=em.createQuery("SELECT h FROM Hotel h JOIN h.ville v WHERE v.id = :id");
		query.setParameter("id",id);
		return query.getResultList();
	}


}

