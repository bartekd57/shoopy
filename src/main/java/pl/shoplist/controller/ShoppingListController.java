package pl.shoplist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pl.shoplist.common.Message;
import pl.shoplist.model.Item;
import pl.shoplist.model.ShoppingList;
import pl.shoplist.model.Status;
import pl.shoplist.repository.ShoppingListRepository;
import pl.shoplist.service.ItemService;
import pl.shoplist.service.ShoppingListService;

import java.util.List;

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
        List<Item> listItems = itemService.getListItems(id);
        Double sum = itemService.getSumPrices(id);
        ShoppingList list = shoppingListService.findListByIdIfPresent(id);

        model.addAttribute("list", list);
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
        ShoppingList list = shoppingListService.findListByIdIfPresent(id);
        model.addAttribute("listFromId", list);
        return "addItems";
    }

    @GetMapping("usun/{id}")
    public String removeList(@PathVariable Long id, Model model) {
        shoppingListService.findAndDeleteList(id);
        model.addAttribute("message", new Message("Usunięto listę", "Lista została usunięta z bazy"));
        return "messageInfo";
    }


    @PostMapping("/nowaLista")
    public String getNewList(@RequestParam String listName, @RequestParam String listDesc, Model model) {
        Message message = shoppingListService.createNewListWithMessage(listName, listDesc);
        model.addAttribute("message", message);
        return "messageInfo";
    }

    @GetMapping("/status")
    public String changeStatus(@RequestParam Long listId, Model model) {
        shoppingListService.findListAndChangeStatus(listId);
        model.addAttribute("message", new Message("Zmieniono status listy", "Status listy pomyślnie zmieniony"));
        return "messageInfo";
    }

    @GetMapping("/listy")
    public String home(@RequestParam(required = false) Status status, Model model) {
        List<ShoppingList> shoppingLists = shoppingListService.findAllListsOrByStatus(status);
        model.addAttribute("shopLists", shoppingLists);
        return "index";
    }


}
