package pl.shoplist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.shoplist.common.ItemsNotFoundException;
import pl.shoplist.model.Item;
import pl.shoplist.model.ShoppingList;
import pl.shoplist.repository.ItemRepository;
import pl.shoplist.repository.ShoppingListRepository;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private ItemRepository itemRepository;
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, ShoppingListRepository shoppingListRepository) {
        this.itemRepository = itemRepository;
        this.shoppingListRepository = shoppingListRepository;
    }


    public Item createSetSaveItem(String name, String desc, Double price){
        Item item = new Item();
        item.setName(name);
        item.setShortDescription(desc);
        item.setPrice(price);
        itemRepository.save(item);
        return item;

    }

    public List<Item> getListItems(Long id){
        return shoppingListRepository.findById(id)
                .map(ShoppingList::getListItems)
                .orElseThrow(ItemsNotFoundException::new)
                .stream()
                .collect(Collectors.toList());

    }

    public Double getSumPrices(Long id){
        return shoppingListRepository.findById(id)
                .map(ShoppingList::getListItems)
                .orElseThrow(ItemsNotFoundException::new)
                .stream()
                .mapToDouble(Item::getPrice)
                .sum();
    }

    public void editItem(Long itemId, String itemName, String itemDesc, Double itemPrice){
        itemRepository.findById(itemId)
                .ifPresent(item -> {
                    item.setName(itemName);
                    item.setShortDescription(itemDesc);
                    item.setPrice(itemPrice);
                    itemRepository.save(item);
                });
    }

    public void deleteItemFromList(Long id, List list){
        for (Iterator<Item> it = list.iterator(); it.hasNext();) {
            Item next = it.next();
            if((next.getId()).equals(id))
                list.remove(next);
        }
    }

}
