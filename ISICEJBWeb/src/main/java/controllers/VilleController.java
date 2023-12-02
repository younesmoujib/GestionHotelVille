package controllers;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.IDaoLocale;
import entities.Ville;

@WebServlet("/VilleController")
public class VilleController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private IDaoLocale<Ville> ejb;

    public VilleController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("ajouter".equals(action)) {
            afficherFormulaireAjout(request, response);
        } else if ("modifier".equals(action)) {
            afficherFormulaireModification(request, response);
        } else if ("supprimer".equals(action)) {
            supprimerVille(request, response);
        } else {
            afficherListeVilles(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("ajouter".equals(action)) {
            ajouterVille(request, response);
        } else if ("modifier".equals(action)) {
            modifierVille(request, response);
        } else {
            // Handle other actions or redirect appropriately
            response.sendRedirect(request.getContextPath() + "/VilleController");
        }
    }

    private void afficherFormulaireAjout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("ville.jsp");
        dispatcher.forward(request, response);
    }

    private void afficherFormulaireModification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            Ville ville = ejb.findById(id);

            if (ville != null) {
                // Fetch the updated list of cities
                List<Ville> villes = ejb.findAll();
                request.setAttribute("villes", villes);

                // Set the selected city for modification
                request.setAttribute("ville", ville);

                RequestDispatcher dispatcher = request.getRequestDispatcher("ville.jsp");
                dispatcher.forward(request, response);
            }
        }
    }


    private void afficherListeVilles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Ville> villes = ejb.findAll();
        request.setAttribute("villes", villes);

        RequestDispatcher dispatcher = request.getRequestDispatcher("ville.jsp");
        dispatcher.forward(request, response);
    }

    private void ajouterVille(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("ville");
        ejb.create(new Ville(nom));

        // Redirect to display the updated list of cities
        response.sendRedirect(request.getContextPath() + "/VilleController");
    }

    private void modifierVille(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String nom = request.getParameter("ville");

        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            Ville existingVille = ejb.findById(id);

            if (existingVille != null) {
                // Update the properties of the existingVille with the new values
                existingVille.setNom(nom);

                // Call the update method with the attached entity
                ejb.update(existingVille);
            }
        }

        response.sendRedirect(request.getContextPath() + "/VilleController");
    }


    private void supprimerVille(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            Ville ville = ejb.findById(id);

            if (ville != null) {
                ejb.delete(ville);
            }
        }

        // Redirect to display the updated list of cities
        response.sendRedirect(request.getContextPath() + "/VilleController");
    }
}