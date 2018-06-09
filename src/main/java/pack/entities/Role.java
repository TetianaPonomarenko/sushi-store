package pack.entities;

import javax.persistence.*;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue
    @Column(name="ID_role")
    private int id;

    @Column(name="role")
    private String role;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}