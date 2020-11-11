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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ItemService {

    private ItemRepository itemRepository;
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, ShoppingListRepository shoppingListRepository) {
        this.itemRepository = itemRepository;
        this.shoppingListRepository = shoppingListRepository;
    }

    public Optional<Item> findItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Item createSetSaveItem(String name, String desc, Double price) {
        Item item = new Item();
        item.setName(name);
        item.setShortDescription(desc);
        item.setPrice(price);
        itemRepository.save(item);
        return item;
    }

    public boolean checkItem(String name, Double price) {
        return (name.isEmpty() || price <= 0);
    }

    public List<Item> getListItems(Long id) {
        return shoppingListRepository.findById(id)
                .map(ShoppingList::getListItems)
                .orElseThrow(ItemsNotFoundException::new)
                .stream()
                .collect(Collectors.toList());

    }

    public Double getSumPrices(Long id) {
        return shoppingListRepository.findById(id)
                .map(ShoppingList::getListItems)
                .orElseThrow(ItemsNotFoundException::new)
                .stream()
                .mapToDouble(Item::getPrice)
                .sum();
    }

    public void editItem(Long itemId, String itemName, String itemDesc, Double itemPrice) {
        itemRepository.findById(itemId)
                .ifPresent(item -> {
                    item.setName(itemName);
                    item.setShortDescription(itemDesc);
                    item.setPrice(itemPrice);
                    itemRepository.save(item);
                });
    }

    public void deleteItemFromList(Long id, List<Item> list) {
        list.stream()
                .filter(u -> u.getId().equals(id))
                .findAny()
                .ifPresent(item -> list.remove(item));
    }


}
