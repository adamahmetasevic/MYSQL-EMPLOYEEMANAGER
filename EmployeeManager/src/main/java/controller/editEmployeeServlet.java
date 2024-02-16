package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@WebServlet("/editEmployeeServlet")
public class editEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("id");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeManager");
        EntityManager em = emf.createEntityManager();

        try {
            Employee employee = em.find(Employee.class, Integer.parseInt(employeeId));

            request.setAttribute("employee", employee);

            request.getRequestDispatcher("editEmployee.jsp").forward(request, response);
        } finally {
            em.close();
            emf.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String employeeId = request.getParameter("id");
        String name = request.getParameter("name");
        String position = request.getParameter("position");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeManager");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Employee employee = em.find(Employee.class, Integer.parseInt(employeeId));

            employee.setName(name);
            employee.setPosition(position);

            em.getTransaction().commit();

            response.sendRedirect("viewEmployeeServlet");
        } finally {
            em.close();
            emf.close();
        }
    }
}
