package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        // Configure and build session factory
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = factory.openSession();

        try {
            // Begin transaction
            Transaction transaction = session.beginTransaction();

            // Insert records
            Customer customer1 = new Customer();
            customer1.setName("John Doe");
            customer1.setEmail("john@example.com");
            customer1.setAge(25);
            customer1.setLocation("New York");

            Customer customer2 = new Customer();
            customer2.setName("Jane Smith");
            customer2.setEmail("jane@example.com");
            customer2.setAge(30);
            customer2.setLocation("California");

            session.save(customer1);
            session.save(customer2);

            transaction.commit();

            // Apply criteria queries
            Criteria criteria = session.createCriteria(Customer.class);

            System.out.println("Customers with age > 25:");
            criteria.add(Restrictions.gt("age", 25));
            List<Customer> customers = criteria.list();
            customers.forEach(c -> System.out.println(c.getName() + " - " + c.getAge()));

            // Example: LIKE operator
            System.out.println("Customers with location like 'New%':");
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.like("location", "New%"));
            customers = criteria.list();
            customers.forEach(c -> System.out.println(c.getName() + " - " + c.getLocation()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
}
