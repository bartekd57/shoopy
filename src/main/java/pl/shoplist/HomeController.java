package pl.shoplist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import pl.shoplist.model.ShoppingList;
import pl.shoplist.model.Status;
import pl.shoplist.repository.ShoppingListRepository;
import pl.shoplist.service.ShoppingListService;

import java.util.List;

@Controller
public class HomeController {

    private ShoppingListService shoppingListService;

    @Autowired
    public HomeController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping("/")
    public String home( Model model) {
        List<ShoppingList> shoppingLists =  shoppingListService.getAllLists();

        model.addAttribute("shopLists", shoppingLists);
        return "index";
    }


}
