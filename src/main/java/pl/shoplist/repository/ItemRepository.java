package pl.shoplist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shoplist.model.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {

}
