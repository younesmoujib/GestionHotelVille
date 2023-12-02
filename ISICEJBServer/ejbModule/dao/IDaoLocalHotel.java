package dao;

import java.util.List;

import entities.Hotel;
import jakarta.ejb.Local;
import jakarta.persistence.Query;


@Local
public interface IDaoLocalHotel extends IDaoLocale <Hotel>{

	

	public List<Hotel> findHotelsbyville(int villeId);

}
