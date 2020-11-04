package pl.shoplist.model;

import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "shortdesc")
    private String shortDescription;
    private Double price;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    public Item() {
    }

    public Item(Long id, String name, String shortDescription, Double price, Category category) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.price = price;
        this.category = category;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(name, item.name) &&
                Objects.equals(shortDescription, item.shortDescription) &&
                Objects.equals(price, item.price) &&
                Objects.equals(category, item.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortDescription, price, category);
    }
}
