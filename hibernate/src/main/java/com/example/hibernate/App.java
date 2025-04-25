package com.example.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner src = new Scanner(System.in);
        int ch;

        do {
            displayMenu();
            ch = Integer.parseInt(src.nextLine());

            switch (ch) {
                case 1:
                    insertion();
                    break;
                case 2:
                    delete();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    getAll();
                    break;
                case 5:
                    getById();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid operation");
                    break;
            }
        } while (ch > 0);
    }

    private static void getById() {
        Scanner src = new Scanner(System.in);
        SessionFactory sf = HibernateUtil.getSessionFactory();
        try (Session s = sf.openSession()) {
            System.out.println("Enter id: ");
            int id = src.nextInt();

            Transaction t = s.beginTransaction();
            Employee d = s.get(Employee.class, id);

            if (d != null) {
                System.out.println("id: " + d.getId());
                System.out.println("name: " + d.getName());
                System.out.println("email: " + d.getEmail());
            } else {
                System.out.println("Employee not found.");
            }
        }
    }

    public static void getAll() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        try (Session s = sf.openSession()) {
            Transaction t = s.beginTransaction();

            List<Employee> employees = s.createQuery("from Employee", Employee.class).list();
            t.commit();

            for (Employee d : employees) {
                System.out.println("Id: " + d.getId());
                System.out.println("Name: " + d.getName());
                System.out.println("Email: " + d.getEmail());
            }
        }
    }

    public static void update() {
        Scanner src = new Scanner(System.in);
        SessionFactory sf = HibernateUtil.getSessionFactory();
        try (Session s = sf.openSession()) {
            Transaction t = s.beginTransaction();

            System.out.println("Enter the ID of the employee to update:");
            int id = src.nextInt();
            src.nextLine(); // consume newline

            Employee d = s.get(Employee.class, id);
            if (d != null) {
                System.out.println("Enter new name:");
                String newName = src.nextLine();
                d.setName(newName);

                System.out.println("Enter new email:");
                String newEmail = src.nextLine();
                d.setEmail(newEmail);

                s.update(d);
                t.commit();
                System.out.println("Successfully Updated");
            } else {
                System.out.println("Employee not found!");
            }
        }
    }

    public static void delete() {
        Scanner src = new Scanner(System.in);
        SessionFactory sf = HibernateUtil.getSessionFactory();
        try (Session s = sf.openSession()) {
            System.out.println("Enter id: ");
            int id = src.nextInt();

            Transaction t = s.beginTransaction();

            Employee d = s.get(Employee.class, id);
            if (d != null) {
                s.delete(d);
                t.commit();
                System.out.println("Successfully Deleted");
            } else {
                System.out.println("Employee not found.");
            }
        }
    }

    @SuppressWarnings("deprecation")
	public static void insertion() {
        Scanner src = new Scanner(System.in);
        SessionFactory sf = HibernateUtil.getSessionFactory();
        try (Session s = sf.openSession()) {
            Transaction t = s.beginTransaction();

            Employee d = new Employee();

            System.out.println("Enter name");
            String name = src.nextLine();
            d.setName(name);

            System.out.println("Enter email");
            String email = src.nextLine();
            d.setEmail(email);

            System.out.println("Enter password");
            String pass = src.nextLine();
            d.setPassword(pass);;
            s.save(d);
            t.commit();

            System.out.println("Successfully Inserted");
        }
    }

    private static void displayMenu() {
        System.out.println("_");
        System.out.println("\t1. Insertion");
        System.out.println("\t2. Delete");
        System.out.println("\t3. Update");
        System.out.println("\t4. Get All");
        System.out.println("\t5. Get by ID");
        System.out.println("\t6. Exit");
    }
}
