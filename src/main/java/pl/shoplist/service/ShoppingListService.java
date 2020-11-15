package pl.shoplist.service;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.shoplist.common.ItemsNotFoundException;
import pl.shoplist.common.Message;
import pl.shoplist.model.Item;
import pl.shoplist.model.ShoppingList;
import pl.shoplist.model.Status;
import pl.shoplist.repository.ShoppingListRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ShoppingListService {


    private ShoppingListRepository shoppingListRepository;


    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    public Optional<ShoppingList> findListById(Long id) {
        return shoppingListRepository.findById(id);
    }

    public ShoppingList findListByIdIfPresent(Long id) {
        return shoppingListRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void deleteList(ShoppingList shoppingList) {
        List<Item> items = shoppingList.getListItems();
        items.clear();
        shoppingListRepository.delete(shoppingList);
    }

    public void findAndDeleteList(Long id) {
        findListById(id)
                .ifPresent(list -> {
                    deleteList(list);
                });
    }


    public void saveList(ShoppingList shoppingList) {
        shoppingListRepository.save(shoppingList);
    }

    public List<ShoppingList> getAllLists() {
        return shoppingListRepository.findAll();
    }

    public List<ShoppingList> findListByStatus(Status status) {
        return shoppingListRepository.findAllByStatus(status);
    }


    private ShoppingList createSetSaveList(String name, String desc) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(name);
        shoppingList.setDesc(desc);
        shoppingList.setStatus(Status.NEW);
        shoppingListRepository.save(shoppingList);
        return shoppingList;
    }

    public Message createNewListWithMessage(String listName, String listDesc) {
        if (listName.isEmpty()) {
            return new Message("Nie podano nazwy", "Lista musi mieć nadaną nazwę");
        } else {
            ShoppingList shoppingList = createSetSaveList(listName, listDesc);
            return new Message("Dodano nową listę", "Dodano nową listę o nazwie " + shoppingList.getName());
        }
    }

    public void changeListStatus(ShoppingList list) {
        if (list.getStatus().equals(Status.NEW)) {
            list.setStatus(Status.FINISHED);
        } else
            list.setStatus(Status.NEW);
    }

    public void findListAndChangeStatus(Long id) {
        findListById(id)
                .ifPresent(list -> {
                    changeListStatus(list);
                    shoppingListRepository.save(list);
                });
    }

    public List<ShoppingList> findAllListsOrByStatus(Status status) {
        List<ShoppingList> shoppingLists;
        if (status == null)
            shoppingLists = getAllLists();
        else
            shoppingLists = findListByStatus(status);
        return shoppingLists;
    }


}
