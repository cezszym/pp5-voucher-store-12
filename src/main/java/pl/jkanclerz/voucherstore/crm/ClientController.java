package pl.jkanclerz.voucherstore.crm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @PostMapping("/api/clients")
    public void addClient(@Valid @RequestBody Client client){
        clientRepository.save(client);
    }

    @GetMapping("/api/clients")
    public Iterable<Client> clients(){
        return clientRepository.findAll();
    }

    @DeleteMapping("/api/clients/{id}")
    public void delete(@PathVariable Integer id) {
        clientRepository.deleteById(id);
    }
}
