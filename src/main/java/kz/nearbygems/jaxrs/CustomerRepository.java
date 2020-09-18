package kz.nearbygems.jaxrs;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;


@ApplicationScoped
public class CustomerRepository {

  @Inject
  EntityManager entityManager;

  public List<Customer> findAll() {
    return entityManager.createNamedQuery("Customers.findAll", Customer.class).getResultList();
  }

  public Customer findCustomerById(Long id) {

    var customer = entityManager.find(Customer.class, id);

    if (customer == null) {
      throw new WebApplicationException("Customer with id of " + id + " does not exist.", 404);
    }

    return customer;
  }

  @Transactional
  public void updateCustomer(Long id, String name, String surname) {
    var customerToUpdate = findCustomerById(id);
    customerToUpdate.setName(name);
    customerToUpdate.setSurname(surname);
  }

  @Transactional
  public void createCustomer(Customer customer) { entityManager.persist(customer); }

  @Transactional
  public void deleteCustomer(Long customerId) {
    var customer = findCustomerById(customerId);
    entityManager.remove(customer);
  }


}
