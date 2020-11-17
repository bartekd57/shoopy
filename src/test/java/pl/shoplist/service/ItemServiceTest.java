package pl.shoplist.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.shoplist.model.Item;
import pl.shoplist.model.ShoppingList;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemServiceTest {


    @Test
    void should_save_Item_On_ShoppingList() {
        //given
        ItemService itemService = mock(ItemService.class);
        when(itemService.saveItemOnShoppingList(Mockito.anyString(), Mockito.anyString(), Mockito.anyDouble(), Mockito.anyLong())).thenReturn(preparedItemList());
        //when
        List<Item> items = itemService.saveItemOnShoppingList(Mockito.anyString(), Mockito.anyString(), Mockito.anyDouble(), Mockito.anyLong());
        //then
        Assert.assertEquals(3, preparedItemList().size());

    }

    @Test
    void should_get_ListItems() {
        //given
        ItemService itemService = mock(ItemService.class);
        when(itemService.getListItems(Mockito.anyLong())).thenReturn(preparedItemList());
        //when
        List<Item> listOfItems = itemService.getListItems(Mockito.anyLong());
        //then
        Assert.assertTrue(listOfItems.get(0).getPrice().equals(5.0));

    }

    @Test
    void should_get_SumPrices() {
        //given
        ItemService itemService = mock(ItemService.class);
        when(itemService.getSumPrices(Mockito.anyLong())).thenReturn(preparedItemList().stream().mapToDouble(Item::getPrice).sum());
        //when
        Double sum = itemService.getSumPrices(Mockito.anyLong());
        //then
        Assert.assertEquals(java.util.Optional.ofNullable(sum), Optional.of(16.0));
    }

    @Test
    void should_not_checkItem() {
        //given
        ItemService itemService = mock(ItemService.class);
        when(itemService.checkItem(Mockito.anyString(), Mockito.anyDouble())).thenReturn(preparedItemList().get(1).getName().isEmpty());
        //when
        boolean checkResult = itemService.checkItem(Mockito.anyString(), Mockito.anyDouble());
        //then
        Assert.assertFalse(checkResult);
    }

    @Test
    void should_delete_Item_From_List() {
        //given
        ItemService itemService = mock(ItemService.class);
        when(itemService.deleteItemFromList(Mockito.anyLong(), Mockito.anyLong())).thenReturn(preparedShoppingList());
        //when
        ShoppingList shoppingList = itemService.deleteItemFromList(Mockito.anyLong(), Mockito.anyLong());
        //then
        Assert.assertEquals(shoppingList.getListItems().get(2).getName(), "ryż");
    }

    @Test
    void should_find_Item_By_Id_IfPresent() {
        //given
        ItemService itemService = mock(ItemService.class);
        when(itemService.findItemByIdIfPresent(Mockito.anyLong())).thenReturn(preparedItemList().get(2));
        //when
        Item item = itemService.findItemByIdIfPresent(Mockito.anyLong());
        //then
        Assert.assertTrue(item.getPrice().equals(4.0));
    }

    private List<Item> preparedItemList() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("maka", "do pieczenia", 5.0));
        items.add(new Item("sprite", "dobre do picia", 7.0));
        items.add(new Item("ryż", "do obiadu", 4.0));
        return items;
    }
    private ShoppingList preparedShoppingList(){
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setListItems(preparedItemList());
        return  shoppingList;
    }
}