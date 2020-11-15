package pl.shoplist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.shoplist.common.Message;
import pl.shoplist.model.Item;
import pl.shoplist.repository.ItemRepository;
import pl.shoplist.repository.ShoppingListRepository;
import pl.shoplist.service.ItemService;
import pl.shoplist.service.ShoppingListService;

import java.util.List;

@Controller
public class ItemController {


    private ShoppingListService shoppingListService;
    private ItemService itemService;
    private ShoppingListRepository shoppingListRepository;
    private ItemRepository itemRepository;

    @Autowired
    public ItemController(ShoppingListService shoppingListService, ItemService itemService, ShoppingListRepository shoppingListRepository, ItemRepository itemRepository) {
        this.shoppingListService = shoppingListService;
        this.itemService = itemService;
        this.shoppingListRepository = shoppingListRepository;
        this.itemRepository = itemRepository;
    }





    @PostMapping("/dodaj")
    public String addItemsToList(@RequestParam Long listId, @RequestParam String itemName,
                                 @RequestParam String itemDesc, @RequestParam Double itemPrice, Model model) {
        if(itemService.checkItem(itemName, itemPrice)){
            model.addAttribute("message", new Message( "Podano niepoprawne dane o produkcie","Ograniczenia: produkt musi mieć nazwę i cenę większą od 0"));
            return "messageInfo";
        }

        List<Item> items = itemService.getListItems(listId);
        Item item = itemService.createSetSaveItem(itemName, itemDesc, itemPrice);
        items.add(item);

        shoppingListService.findListById(listId)
                .ifPresent(list -> {
                    list.setListItems(items);
                    model.addAttribute("list", list);
                    shoppingListService.saveList(list);
                });

        Double sum = itemService.getSumPrices(listId);

        model.addAttribute("message", new Message("Dodano produkt", "Dodano nowy produkt do listy zakupów"));
        model.addAttribute("listItems", items);
        model.addAttribute("sum", sum);

        return "message";
    }

    @GetMapping("/edytuj/lista{listId}/produkt{itemId}")
    public String getToItemEdit(@PathVariable Long listId, @PathVariable Long itemId, Model model) {
        itemService.findItemById(itemId)
                .ifPresent(item -> {
                    model.addAttribute("item", item);
                });
        shoppingListService.findListById(listId)
                .ifPresent(list -> {
                    model.addAttribute("list", list);
                });
        return "itemEdit";
    }


    @PostMapping("/edytuj/lista{listId}/produkt{itemId}")
    public String editItem(@PathVariable Long listId, @PathVariable Long itemId,
                           @RequestParam String itemName, @RequestParam String itemDesc,
                           @RequestParam Double itemPrice, Model model) {
        if(itemService.checkItem(itemName, itemPrice)){
            model.addAttribute("message", new Message( "Podano niepoprawne dane o produkcie","Ograniczenia: produkt musi mieć nazwę i cenę większą od 0"));
            return "messageInfo";
        }
        itemService.editItem(itemId, itemName, itemDesc, itemPrice);
        List<Item> items = itemService.getListItems(listId);
        Double sum = itemService.getSumPrices(listId);
        shoppingListService.findListById(listId)
                .ifPresent(list ->
                        model.addAttribute("list", list));

        model.addAttribute("message", new Message("Edycja produktu zakończona", "Poprawnie edytowano produkt"));
        model.addAttribute("listItems", items);
        model.addAttribute("sum", sum);
        return "message";
    }

    @GetMapping("/usunProdukt/{id}/{listId}")
    public String deleteProductFromList(@PathVariable Long id, @PathVariable Long listId, Model model) {

        List<Item> items = itemService.getListItems(listId);
        itemService.deleteItemFromList(id, items);

        shoppingListService.findListById(listId)
                .ifPresent(list -> {
                    list.setListItems(items);
                    shoppingListService.saveList(list);
                    model.addAttribute("list", list);
                });

        model.addAttribute("message", new Message("Usunięto produkt", "Usnięto produkt z listy"));
        return "messageDeletedItem";
    }


}
