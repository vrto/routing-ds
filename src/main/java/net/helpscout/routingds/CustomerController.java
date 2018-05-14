package net.helpscout.routingds;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

    private final CustomerQueries customerQueries;
    private final CustomerCommands customerCommands;

    @GetMapping("/slave/customers")
    public List<CustomerEntity> getCustomersFromSlave() {
        return customerQueries.findAll();
    }

    @PostMapping("/master/customers")
    public void postCustomer() {
        customerCommands.saveCustomer(new CustomerEntity("Wayne", "Gretzky"));
    }

    @GetMapping("/master/customers")
    public List<CustomerEntity> getCustomersFromMaster() {
        return customerCommands.findAll();
    }

}
