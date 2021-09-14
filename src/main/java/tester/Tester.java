package tester;

import entity.Employee;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Tester {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Employee("xa12tt", "Kurt", "Wonnegut", new BigDecimal(335567)));
            em.persist(new Employee("hyu654", "Hanne", "Olsen", new BigDecimal(435867)));
            em.persist(new Employee("uio876", "Jan", "Olsen", new BigDecimal(411567)));
            em.persist(new Employee("klo999", "Irene", "Petersen", new BigDecimal(33567)));
            em.persist(new Employee("jik666", "Tian", "Wonnegut", new BigDecimal(56567)));
            em.getTransaction().commit();
            
            //Complete all these small tasks. Your will find the solution to all, but the last,
            //In this document: https://en.wikibooks.org/wiki/Java_Persistence/JPQL#JPQL_supported_functions
            
            //1) Create a query to fetch all employees with a salary > 100000 and print out all the salaries
            
            TypedQuery<Employee> q1 = em.createQuery("SELECT e FROM Employee e WHERE e.salary > 100000", Employee.class);
            List<Employee> salarys = q1.getResultList();
            System.out.println("Salaries higher then 100000:");
            for (Employee e: salarys) {
                System.out.println(e.getSalary());
            }
            
            //2) Create a query to fetch the employee with the id "klo999" and print out the firstname
            
            String id = "klo999";
            TypedQuery<Employee> q2 = em.createQuery("SELECT e FROM Employee e WHERE e.id =:id", Employee.class);
            q2.setParameter("id", id);
            Employee e2 = q2.getSingleResult();
            
            System.out.println("The person with id \"klo999\" has the first name: " + e2.getFirstName());
            
            //3) Create a query to fetch the highest salary and print the value
            
            TypedQuery<Employee> q3 = em.createQuery("SELECT MAX(e.salary) FROM Employee e", Employee.class);
            List<Employee> high = q3.getResultList();
            
            System.out.println("The highest salary is: " + high.get(0));

            //4) Create a query to fetch the firstName of all Employees and print the names
            
            TypedQuery<Employee> q4 = em.createQuery("SELECT e FROM Employee e", Employee.class);
            List<Employee> fNames = q4.getResultList();
            
            System.out.println("First Name of all employees: ");
            for (Employee e : fNames) {
                System.out.println(e.getFirstName());
            }
           
            //5 Create a query to calculate the number of employees and print the number
            
            TypedQuery<Employee> q5 = em.createQuery("SELECT e FROM Employee e", Employee.class);
            List<Employee> e5 = q4.getResultList();
            
            System.out.println("The total number of employees:");
            int totalNumber = 0;
            for (Employee e : e5) {
                totalNumber++;
            }
            System.out.println(totalNumber);
            
            //6 Create a query to fetch the Employee with the higest salary and print all his details
            
            TypedQuery<Employee> q6 = em.createQuery("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e.salary) FROM Employee e)", Employee.class);
            List<Employee> allDetails = q6.getResultList();
            
            System.out.println("The person getting the highest salary has the following details:");
            System.out.println("ID: " + allDetails.get(0).getId());
            System.out.println("First Name: " + allDetails.get(0).getFirstName());
            System.out.println("Last Name: " + allDetails.get(0).getLastName());
            System.out.println("Salary: " + allDetails.get(0).getSalary());
            
        } finally {
            em.close();
            emf.close();
        }
    }
}
