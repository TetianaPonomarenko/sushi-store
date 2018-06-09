package pack.service;

import java.util.Arrays;
import java.util.HashSet;

import pack.entities.Client;
import pack.entities.Role;
import pack.repository.ClientRepository;
import pack.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("clientService")
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Client findClientByFIO(String fio) {
        return clientRepository.findByClientFIO(fio);
    }

    @Override
    public void saveClient(Client client) {
       client.setPassword(client.getPassword());
        client.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        client.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        clientRepository.save(client);
    }
}