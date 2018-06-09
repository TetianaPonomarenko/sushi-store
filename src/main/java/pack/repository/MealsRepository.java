package pack.repository;

import pack.entities.Meal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealsRepository extends CrudRepository<Meal, Long>{
}