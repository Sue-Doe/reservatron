package comp2150.reservatron.server.reserve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import comp2150.reservatron.server.session.Customer;

/**
 * COMP 2150 Summer 23 Assignment 1: Hotel reservations
 */

/**
 * Store a list of customers.
 * Customers don't need an entire class, since they're only a name.
 * We can reconstruct other info from other data structures
 * (this may not be good enough, at some point).
 * 
 * @author john
 */

public class CustomerList implements Iterable<Customer> {
  private ArrayList<Customer> customers;
  
  public CustomerList() {
    customers = new ArrayList<Customer>();
  }
  
  public boolean contains(Customer c) {
    return customers.contains(c);
  }
  
  public Customer add(String ID) {
    Customer nCust = getCustomer(ID);
    if (nCust == null) {
      nCust = new Customer(ID);
      customers.add(nCust);
      Collections.sort(customers);
    }
    return nCust;
  }

  public Customer getCustomer(String name) {
    
    for(Customer c : customers) {
      if (c.getName().equals(name)) {
        return c;
      }
    }
    return null;
  }

  public void remove(Customer c) {
    customers.remove(c);
  }

  public Iterator<Customer> iterator() {
    return customers.iterator();
  }
  
  public String toString() {
    return customers.toString();
  }
}