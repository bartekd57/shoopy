package pl.shoplist.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.shoplist.model.Item;
import pl.shoplist.model.ShoppingList;
import pl.shoplist.model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShoppingListServiceTest {

    @Test
    void should_find_List_By_Id_IfPresent() {
        //given
        ShoppingListService shoppingListService = mock(ShoppingListService.class);
        when(shoppingListService.findListByIdIfPresent(Mockito.anyLong())).thenReturn(preparedShoppingLists().get(0));
        //when
        ShoppingList shoppingList = shoppingListService.findListByIdIfPresent(Mockito.anyLong());
        //then
        Assert.assertEquals(shoppingList.getName(), ("codzienna"));

    }

    @Test
    void should_get_All_Lists() {
        //given
        ShoppingListService shoppingListService = mock(ShoppingListService.class);
        when(shoppingListService.getAllLists()).thenReturn(preparedShoppingLists());
        //when
        List<ShoppingList> allLists = shoppingListService.getAllLists();
        //then
        Assert.assertEquals(allLists.size(), 3);
    }

    @Test
    void should_find_List_By_Status() {
        //given
        ShoppingListService shoppingListService = mock(ShoppingListService.class);
        when(shoppingListService.findListByStatus(Mockito.any(Status.class))).thenReturn(finishedShoppingLists());
        //when
        List<ShoppingList> listsByStatus = shoppingListService.findListByStatus(Status.FINISHED);
        //then
        Assert.assertEquals(listsByStatus.get(0).getName(), "przemyslowa");
    }

    @Test
    void should_find_All_Lists_Or_By_Status() {
        //given
        ShoppingListService shoppingListService = mock(ShoppingListService.class);
        when(shoppingListService.findAllListsOrByStatus(Mockito.any(Status.class))).thenReturn(newShoppingLists());
        //when
        List<ShoppingList> listsByStatus = shoppingListService.findAllListsOrByStatus(Status.NEW);
        //then
        Assert.assertEquals(listsByStatus.get(0).getStatus(), Status.NEW);
    }


    private List<Item> preparedItemList1() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("maka", "do pieczenia", 5.0));
        items.add(new Item("sprite", "dobre do picia", 7.0));
        items.add(new Item("ryż", "do obiadu", 4.0));
        return items;
    }

    private List<Item> preparedItemList2() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("woda", "gazowana", 1.8));
        items.add(new Item("banany", "dobre na mase", 4.5));
        items.add(new Item("chipsy", "paprykowe", 6.0));
        return items;
    }

    private List<ShoppingList> preparedShoppingLists() {
        List<ShoppingList> lists = new ArrayList<>();
        lists.add(new ShoppingList(1L, "codzienna", "lista na co dzien", preparedItemList1(), Status.NEW));
        lists.add(new ShoppingList(2L, "przemyslowa", "chemia gospodarcza", preparedItemList2(), Status.FINISHED));
        lists.add(new ShoppingList(3L, "chemiczna", "chemia gospodarcza", preparedItemList2(), Status.FINISHED));

        return lists;
    }
    private List<ShoppingList> finishedShoppingLists() {
        List<ShoppingList> lists = new ArrayList<>();
        lists.add(new ShoppingList(1L, "przemyslowa", "chemia gospodarcza", preparedItemList2(), Status.FINISHED));
        lists.add(new ShoppingList(2L, "chemiczna", "chemia gospodarcza", preparedItemList2(), Status.FINISHED));
        return lists;
    }


    private List<ShoppingList> newShoppingLists() {
        List<ShoppingList> lists = new ArrayList<>();
        lists.add(new ShoppingList(1L, "codzienna", "lista na co dzien", preparedItemList1(), Status.NEW));
        return lists;
    }


}