package pack.entities;

import javax.persistence.*;

@Entity
public class Meal {

    @Id
    @GeneratedValue
    @Column(name="ID_meal")
    private Long ID_meal;

    @Column(name="Title")
    private String Title;

    @Column(name="Pricemeal")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_category")
    private Category category;

    @Column(name="Discountmeal")
    private Double discountMeal;

    @Column(name="Image")
    private String image;

    public Meal(){}

    public Meal(String title, Double price, Category category, Double discountMeal, String image){

        this.Title = title;
        this.price = price;
        this.category = category;
        this.discountMeal = discountMeal;
        this.image = image;
    }

    public Long getID_meal() {
        return ID_meal;
    }

    public void setID_meal(Long ID_meal) {
        this.ID_meal = ID_meal;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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

    public Double getDiscountMeal() {
        return discountMeal;
    }

    public void setDiscountMeal(Double discountMeal) {
        this.discountMeal = discountMeal;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
