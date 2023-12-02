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

import dao.IDaoLocalHotel;
import dao.IDaoLocale;
import entities.Hotel;
import entities.Ville;

@WebServlet("/HotelController")
public class HotelController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private IDaoLocalHotel ejb;

    @EJB
    private IDaoLocale<Ville> villeEjb;

    public HotelController() {
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
            supprimerHotel(request, response);
        } else {
            afficherListeHotels(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("ajouter".equals(action)) {
            ajouterHotel(request, response);
        } else if ("modifier".equals(action)) {
            modifierHotel(request, response);
        } else {
            // Handle other actions or redirect appropriately
            response.sendRedirect(request.getContextPath() + "/HotelController");
        }
    }

    private void afficherFormulaireAjout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	List<Ville> villes = villeEjb.findAll();
    	request.setAttribute("villes", villes);
    System.out.println(villes);
    
        RequestDispatcher dispatcher = request.getRequestDispatcher("hotel.jsp");
        dispatcher.forward(request, response);
    }

    private void afficherFormulaireModification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            Hotel hotel = ejb.findById(id);
          
            if (hotel != null) {
                List<Ville> villes = villeEjb.findAll();
                request.setAttribute("villes", villes);
                List<Hotel> hotels = ejb.findAll();
                request.setAttribute("hotels", hotels);
                

                request.setAttribute("hotel", hotel);

                RequestDispatcher dispatcher = request.getRequestDispatcher("hotel.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    private void afficherListeHotels(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Hotel> hotels = ejb.findAll();
        request.setAttribute("hotels", hotels);
        System.out.println("hotels"+hotels);
        List<Ville> villes = villeEjb.findAll();
        request.setAttribute("villes", villes);

        RequestDispatcher dispatcher = request.getRequestDispatcher("hotel.jsp");
        dispatcher.forward(request, response);
    }

    private void ajouterHotel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String adresse = request.getParameter("adresse");
        String telephone = request.getParameter("telephone");
        int villeId = Integer.parseInt(request.getParameter("ville"));

        // Récupérer la liste des villes pour afficher dans le formulaire
        List<Ville> villes = villeEjb.findAll();
        request.setAttribute("villes", villes);

        Ville ville = villeEjb.findById(villeId);
        Hotel newHotel = new Hotel(nom, adresse, telephone, ville);

        ejb.create(newHotel);

        response.sendRedirect(request.getContextPath() + "/HotelController");
    }


    private void modifierHotel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String nom = request.getParameter("nom");
        String adresse = request.getParameter("adresse");
        String telephone = request.getParameter("telephone");
        int villeId = Integer.parseInt(request.getParameter("ville"));

        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            Hotel existingHotel = ejb.findById(id);

            if (existingHotel != null) {
                Ville ville = villeEjb.findById(villeId);

                existingHotel.setNom(nom);
                existingHotel.setAdresse(adresse);
                existingHotel.setTelephone(telephone);
                existingHotel.setVille(ville);

                ejb.update(existingHotel);
            }
        }

        response.sendRedirect(request.getContextPath() + "/HotelController");
    }

    private void supprimerHotel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            Hotel hotel = ejb.findById(id);

            if (hotel != null) {
                ejb.delete(hotel);
            }
        }

        response.sendRedirect(request.getContextPath() + "/HotelController");
    }
}
