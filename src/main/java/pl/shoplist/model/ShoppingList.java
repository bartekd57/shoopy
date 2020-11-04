package pl.shoplist.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="shopping_list")
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String desc;
    @ManyToMany
    @JoinTable(name = "list_item",
    joinColumns = @JoinColumn(name="list_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
    private List<Item> listItems = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Status status;

    public ShoppingList() {
    }



    public ShoppingList(Long id, String name, String desc, List<Item> listItems, Status status) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.listItems = listItems;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Item> getListItems() {
        return listItems;
    }

    public void setListItems(List<Item> listItems) {
        this.listItems = listItems;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingList that = (ShoppingList) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(desc, that.desc) &&
                Objects.equals(listItems, that.listItems) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, listItems, status);
    }
}
