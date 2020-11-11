package pl.shoplist.service;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.shoplist.model.Item;
import pl.shoplist.model.ShoppingList;
import pl.shoplist.model.Status;
import pl.shoplist.repository.ShoppingListRepository;
import java.util.List;
import java.util.Optional;


@Service
public class ShoppingListService {


    private ShoppingListRepository shoppingListRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    public Optional<ShoppingList> findListById(Long id) {
        return  shoppingListRepository.findById(id);

    }

    public void deleteList(ShoppingList shoppingList){
        List<Item> items = shoppingList.getListItems();
        items.clear();
        shoppingListRepository.delete(shoppingList);
    }
    public void saveList(ShoppingList shoppingList){
        shoppingListRepository.save(shoppingList);
    }

    public List<ShoppingList> getAllLists(){
        return shoppingListRepository.findAll();
    }

    public List<ShoppingList> findListByStatus(Status status){
        return shoppingListRepository.findAllByStatus(status);
    }


   public ShoppingList createSetSaveList(String name, String desc){

       ShoppingList shoppingList = new ShoppingList();
       shoppingList.setName(name);
       shoppingList.setDesc(desc);
       shoppingList.setStatus(Status.NEW);
       shoppingListRepository.save(shoppingList);

       return  shoppingList;
   }

   public void changeListStatus(ShoppingList list){
        list.setStatus(Status.FINISHED);

   }


}
