package com.microservice.customer.service;

import com.microservice.customer.model.Customer;
import com.microservice.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private  final CustomerRepository customerRepository;

    public Flux<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }
    public Mono<Customer> getCustomerById(String id){
        return  customerRepository.findById(id);
    }
    public Mono<Customer> createCustomer(Customer customer){return customerRepository.save(customer);
    }
    public Mono<Customer> updateCustomer(String id, Customer customer){
        return customerRepository.findById(id)
                .flatMap(bean -> {
                    bean.setName(customer.getName());
                    bean.setLastname(customer.getLastname());
                    bean.setDni(customer.getDni());
                    bean.setTypcustomer(customer.getTypcustomer());
                    return customerRepository.save(bean);
                });
    }
    public Mono<Customer> deleteCustomer(String id){
        return customerRepository.findById(id)
                .flatMap(existsCustomerRepository -> customerRepository.delete(existsCustomerRepository)
                        .then(Mono.just(existsCustomerRepository)));
    }

}
