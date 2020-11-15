package pl.shoplist.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.shoplist.common.ItemsNotFoundException;
import pl.shoplist.model.Item;
import pl.shoplist.model.ShoppingList;
import pl.shoplist.repository.ItemRepository;
import pl.shoplist.repository.ShoppingListRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ItemService {

    private ItemRepository itemRepository;
    private ShoppingListRepository shoppingListRepository;
    private ShoppingListService shoppingListService;

    @Autowired
    public ItemService(ItemRepository itemRepository, ShoppingListRepository shoppingListRepository, ShoppingListService shoppingListService) {
        this.itemRepository = itemRepository;
        this.shoppingListRepository = shoppingListRepository;
        this.shoppingListService = shoppingListService;
    }


    public Optional<Item> findItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Item findItemByIdIfPresent(Long id) {
        return itemRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    private Item createSetSaveItem(String name, String desc, Double price) {
        Item item = new Item();
        item.setName(name);
        item.setShortDescription(desc);
        item.setPrice(price);
        itemRepository.save(item);
        return item;
    }

    public List<Item> saveItemOnShoppingList(String itemName, String itemDesc, Double itemPrice, Long listId) {
        List<Item> items = getListItems(listId);
        Item item = createSetSaveItem(itemName, itemDesc, itemPrice);
        items.add(item);

        shoppingListService.findListById(listId)
                .ifPresent(list -> {
                    list.setListItems(items);
                    shoppingListService.saveList(list);
                });
        return items;
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

    private void deleteItem(Long id, List<Item> list) {
        list.stream()
                .filter(u -> u.getId().equals(id))
                .findAny()
                .ifPresent(list::remove);
    }

    public ShoppingList deleteItemFromList(Long itemId, Long listId) {
        List<Item> items = getListItems(listId);
        deleteItem(itemId, items);
        return shoppingListService.findListByIdIfPresent(listId);
    }


}
