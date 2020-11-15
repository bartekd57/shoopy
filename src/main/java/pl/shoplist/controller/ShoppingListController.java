package pl.shoplist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
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
    private ItemService itemService;
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService, ItemService itemService, ShoppingListRepository shoppingListRepository) {
        this.shoppingListService = shoppingListService;
        this.itemService = itemService;
        this.shoppingListRepository = shoppingListRepository;
    }


    @GetMapping("/lista/{id}")
    public String getItem(@PathVariable Long id, Model model) {
        Optional<ShoppingList> listById = shoppingListService.findListById(id);
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
        Optional<ShoppingList> listById = shoppingListService.findListById(id);
        listById.ifPresent(list -> model.addAttribute("listFromId", list));

        return "addItems";
    }

    @GetMapping("usun/{id}")
    public String removeList(@PathVariable Long id, Model model) {
        shoppingListService.findAndDeleteList(id);
        model.addAttribute("message", new Message("Usunięto listę", "Lista została usunięta z bazy"));
        return "messageDeleted";
    }


    @PostMapping("/nowaLista")
    public String getNewList(@RequestParam String listName, @RequestParam String listDesc, Model model) {
        if (listName.isEmpty()) {
            model.addAttribute("message", new Message("Nie podano nazwy", "Lista musi mieć nadaną nazwę"));
        } else {
            ShoppingList shoppingList = shoppingListService.createSetSaveList(listName, listDesc);
            model.addAttribute("message", new Message("Dodano nową listę", "Dodano nową listę o nazwie " + shoppingList.getName()));
        }
        return "messageDeleted";
    }

    @GetMapping("/status")
    public String changeStatus(@RequestParam Long listId, Model model) {
        shoppingListService.findListAndChangeStatus(listId);
        model.addAttribute("message", new Message("Zmieniono status listy", "Status listy pomyślnie zmieniony"));
        return "messageDeleted";

    }

    @GetMapping("/listy")
    public String home(@RequestParam(required = false) Status status, Model model) {
        List<ShoppingList> shoppingLists = shoppingListService.findAllListsOrByStatus(status);
        model.addAttribute("shopLists", shoppingLists);
        return "index";
    }


}
