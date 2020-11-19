package pl.shoplist.service;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.shoplist.repository.ItemRepository;
import pl.shoplist.repository.ShoppingListRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
class ItemServiceTest2 {

    @Mock
    ItemRepository itemRepository;
    @Mock
    ShoppingListRepository shoppingListRepository;
    @Mock
    ShoppingListService shoppingListService;

    @InjectMocks
    ItemService itemService;



    @Test
    void getListItems() {
    }
}