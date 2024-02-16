package controller;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addEmployeeServlet")
public class addEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String position = request.getParameter("position");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeManager");
        EntityManager em = emf.createEntityManager();

        try {
            // Begin transaction
            em.getTransaction().begin();

            Employee newEmployee = new Employee(name, position);

            em.persist(newEmployee);

            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }

        // Redirect the user back to the index page
        response.sendRedirect("index.jsp");
    }
}
