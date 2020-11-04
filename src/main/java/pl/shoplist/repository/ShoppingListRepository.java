package pl.shoplist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shoplist.model.ShoppingList;
import pl.shoplist.model.Status;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingList,Long> {


    List<ShoppingList> findAllByStatus(Status status);
}
