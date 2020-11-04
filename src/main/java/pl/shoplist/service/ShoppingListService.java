package pl.shoplist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.shoplist.model.Item;
import pl.shoplist.model.ShoppingList;
import pl.shoplist.model.Status;
import pl.shoplist.repository.ShoppingListRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {


    private ShoppingListRepository shoppingListRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    public Optional<ShoppingList> findListById(Long id) {
        return shoppingListRepository.findById(id);

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
