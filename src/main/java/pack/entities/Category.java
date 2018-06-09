package pack.entities;

import javax.persistence.*;

@Entity
@Table(name = "Category")
public class Category {

    @GeneratedValue
    @Id
    @Column(name = "ID_category")
    private Long id;

    @Column(name = "Namecategory")
    private String name;

    @Column(name="Image")
    private String image;

    public Category() {
    }

    public Category(String name, String image) {

        this.name = name;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Category " + getId() + " = " + name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
