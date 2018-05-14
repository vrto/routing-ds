package net.helpscout.routingds;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerCommands {

    private final CustomerRepository customerRepository;

    @Transactional
    public void saveCustomer(CustomerEntity customer) {
        customerRepository.save(customer);
    }

    @Transactional
    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }
}
