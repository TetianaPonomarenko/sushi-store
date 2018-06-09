package pack.service;

import pack.entities.Client;

public interface ClientService {
     Client findClientByFIO(String fio);
     void saveClient(Client client);
}