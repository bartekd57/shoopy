package pl.shoplist.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import pl.shoplist.model.Item;
import pl.shoplist.model.ShoppingList;
import pl.shoplist.model.Status;
import pl.shoplist.repository.ItemRepository;
import pl.shoplist.repository.ShoppingListRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
class ShoppingListServiceTest2 {


    @Mock
    ShoppingListRepository shoppingListRepository;

    @InjectMocks
    ShoppingListService shoppingListService;

    @Before
    public void init(){
        given(shoppingListRepository.findAll()).willReturn(preparedShoppingLists());
    }

    @BeforeEach
    public void setUp() throws Exception {
        initMocks(this);
        given(shoppingListRepository.findAll()).willReturn(preparedShoppingLists());
        given(shoppingListRepository.findAllByStatus(Mockito.any(Status.class))).willReturn(finishedShoppingLists());
        given(shoppingListRepository.findById(Mockito.anyLong())).willReturn(Optional.of(preparedShoppingList()));  // czy z tego optionala trzeba gdzies obsłużyć wyjątek?
    }

    @Test
    public void shouldGetAllLists() {
        List<ShoppingList> allLists = shoppingListService.getAllLists();
        //then
        Assert.assertEquals(allLists.size(),3);
    }

    @Test
    void shouldFindListByStatus() {
        List<ShoppingList> listsByStatus = shoppingListService.findListByStatus(Status.FINISHED);
        //then
        Assert.assertEquals(listsByStatus.size(), 2);
    }

    @Test
    void shouldFindListById() {
        Optional<ShoppingList> listById = shoppingListService.findListById(1L);
        Assert.assertEquals("codzienna", listById.get().getName());
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
    private ShoppingList preparedShoppingList(){
        return new ShoppingList(1L,"codzienna", "lista na co dzien", preparedItemList1(), Status.NEW);
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