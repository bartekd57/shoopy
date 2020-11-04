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
    private ShoppingListRepository shoppingListRepository;
    private ItemRepository itemRepository;
    private ItemService itemService;

    @Autowired
    public ItemController(ShoppingListService shoppingListService, ShoppingListRepository shoppingListRepository, ItemRepository itemRepository, ItemService itemService) {
        this.shoppingListService = shoppingListService;
        this.shoppingListRepository = shoppingListRepository;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }


    @PostMapping("/dodaj")
    public String addItemsToList(@RequestParam Long listId, @RequestParam String itemName,
                                 @RequestParam String itemDesc, @RequestParam Double itemPrice, Model model) {

        List<Item> items = itemService.getListItems(listId);
        Item item = itemService.createSetSaveItem(itemName, itemDesc, itemPrice);
        items.add(item);

        shoppingListRepository.findById(listId)
                .ifPresent(list -> {
                    list.setListItems(items);
                model.addAttribute("list", list);});
        shoppingListRepository.findById(listId)
                .ifPresent(list -> {shoppingListRepository.save(list);});

        Double sum = itemService.getSumPrices(listId);

        model.addAttribute("message", new Message("Dodano produkt", "Dodano nowy produkt do listy zakupów"));
        model.addAttribute("listItems", items);
        model.addAttribute("sum", sum);

        return "message";

    }

    @GetMapping("/edytuj/lista{listId}/produkt{itemId}")
    public String getToItemEdit(@PathVariable Long listId, @PathVariable Long itemId, Model model){
        itemRepository.findById(itemId)
                .ifPresent(item -> {
                    model.addAttribute("item", item);
                });
        shoppingListRepository.findById(listId)
                .ifPresent(list ->{
                    model.addAttribute("list",list);
                });
        return "itemEdit";
    }


    @PostMapping("/edytuj/lista{listId}/produkt{itemId}")
    public String editItem(@PathVariable Long listId,@PathVariable Long itemId,
                                @RequestParam String itemName, @RequestParam String itemDesc,
                           @RequestParam Double itemPrice, Model model){

        itemService.editItem(itemId, itemName, itemDesc, itemPrice);
        List<Item> items = itemService.getListItems(listId);
        Double sum = itemService.getSumPrices(listId);
        shoppingListRepository.findById(listId)
                .ifPresent(list ->
                        model.addAttribute("list", list));

        model.addAttribute("message", new Message("Edycja produktu zakończona", "Poprawnie edytowano produkt"));
        model.addAttribute("listItems", items);
        model.addAttribute("sum", sum);
        return "message";
    }

    @GetMapping("/usunProdukt/{id}")
    public String deleteProductFromList( @PathVariable Long id, Model model){
        itemRepository.findById(id)
                .ifPresent(item -> {
                    item.setCategory(null);
                    itemRepository.delete(item);
                });




        // shoppingListRepository.findById(listId)
          //      .ifPresent(list ->{
             //       model.addAttribute("list", list);
             //   });
        model.addAttribute("message", new Message("Usunięto produkt", "Usnięto produkt z listy"));
        return "messageDeletedItem";
    }



}
