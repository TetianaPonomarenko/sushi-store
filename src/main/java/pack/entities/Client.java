package pack.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Client{

    @Id
    @GeneratedValue
    @Column(name="ID_client")
  private Long ID_client;

    @Column(name="ClientFIO")
    private String clientFIO;
    @Column(name="Phone")
    private String phone;

    @Column(name="password")
    private String password;

    @Column(name = "active")
    private int active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ClientRole", joinColumns = @JoinColumn(name = "ID_client"), inverseJoinColumns = @JoinColumn(name = "ID_role"))
    private Set<Role> roles;

    public Client(){}

    public Client(String clientFIO, String phone, String password){
        this.clientFIO = clientFIO;
        this.phone = phone;
        this.password = password;
    }

    public Long getId() {
        return ID_client;
    }

    public String getClientFIO() {
        return clientFIO;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id){  this.ID_client = id;}

    public void setClientFIO(String clientFIO){this.clientFIO = clientFIO;}

    public void setPhone(String phone){    this.phone = phone;}

    public void setPassword(String password){ this.password = password;}


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}