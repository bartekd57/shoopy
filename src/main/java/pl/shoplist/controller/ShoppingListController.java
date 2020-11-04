package pl.shoplist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pl.shoplist.common.Message;
import pl.shoplist.model.Item;
import pl.shoplist.model.ShoppingList;
import pl.shoplist.model.Status;
import pl.shoplist.repository.ItemRepository;
import pl.shoplist.repository.ShoppingListRepository;
import pl.shoplist.service.ItemService;
import pl.shoplist.service.ShoppingListService;

import java.util.List;
import java.util.Optional;

@Controller
public class ShoppingListController {

    private ShoppingListService shoppingListService;
    private ShoppingListRepository shoppingListRepository;
    private ItemRepository itemRepository;
    private ItemService itemService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService, ShoppingListRepository shoppingListRepository,  ItemRepository itemRepository, ItemService itemService) {
        this.shoppingListService = shoppingListService;
        this.shoppingListRepository = shoppingListRepository;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }


    @GetMapping("/lista/{id}")
    public String getItem(@PathVariable Long id, Model model) {
        Optional<ShoppingList> listById = shoppingListRepository.findById(id);
        List<Item> listItems = itemService.getListItems(id);
        listById.ifPresent(list -> model.addAttribute("list", list));

        Double sum = itemService.getSumPrices(id);

        model.addAttribute("listItems", listItems);
        model.addAttribute("sum", sum);

        return "shopList";
    }

    @GetMapping("/nowaLista")
    public String addNewList() {
        return "newList";
    }

    @GetMapping("/dodaj/{id}")
    public String addItemsToList(@PathVariable Long id, Model model) {
        Optional<ShoppingList> listById = shoppingListRepository.findById(id);
        listById.ifPresent(list -> model.addAttribute("listFromId", list));

        return "addItems";
    }

    @GetMapping("usun/{id}")
    public String removeList(@PathVariable Long id, Model model) {
        shoppingListRepository.findById(id)
                .ifPresent(list -> {
                    shoppingListRepository.delete(list);
                });

        model.addAttribute("message", new Message("Usunięto listę", "Lista została usunięta z bazy"));
        return "messageDeleted";
    }


    @PostMapping("/nowaLista")
    public String getNewList(@RequestParam String listName, @RequestParam String listDesc, Model model) {
        ShoppingList shoppingList = shoppingListService.createSetSaveList(listName, listDesc);
        model.addAttribute("message", new Message("Dodano nową listę", "Dodano nową listę o nazwie " + shoppingList.getName()));
        return "messageDeleted";
    }

    @GetMapping("/status")
    public String changeStatus(@RequestParam Long listId, Model model) {
        shoppingListRepository.findById(listId)
                .ifPresent(list -> {
                    shoppingListService.changeListStatus(list);
                    shoppingListRepository.save(list);
                });

        model.addAttribute("message", new Message("Zmieniono status listy", "Status listy pomyślnie zmieniony"));
        return "messageDeleted";

    }

    @GetMapping("/listy")
    public String home(@RequestParam(required = false) Status status, Model model) {
        List<ShoppingList> shoppingLists;
        if (status == null)
            shoppingLists = shoppingListRepository.findAll();
        else
            shoppingLists = shoppingListRepository.findAllByStatus(status);

        model.addAttribute("shopLists", shoppingLists);
        return "index";
    }


}
