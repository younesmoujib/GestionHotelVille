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

@WebServlet("/HotelByVilleController")
public class HotelByVilleController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private IDaoLocale<Ville> villeEjb;

    @EJB
    private IDaoLocalHotel ejb;

    public HotelByVilleController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Ville> villes = villeEjb.findAll();
        request.setAttribute("villes", villes);

        RequestDispatcher dispatcher = request.getRequestDispatcher("HotelByVille.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Ville> villes = villeEjb.findAll();
        request.setAttribute("villes", villes);

        String villeIdStr = request.getParameter("villeId");

        if (villeIdStr != null && !villeIdStr.isEmpty()) {
            int villeId = Integer.parseInt(villeIdStr);
            List<Hotel> hotels = ejb.findHotelsbyville(villeId);

            request.setAttribute("hotels", hotels);
            RequestDispatcher dispatcher = request.getRequestDispatcher("HotelByVille.jsp");
            dispatcher.forward(request, response);
        } else {
            // Handle the case where no Ville is selected
            request.setAttribute("errorMessage", "Please select a Ville");
            RequestDispatcher dispatcher = request.getRequestDispatcher("HotelByVille.jsp");
            dispatcher.forward(request, response);
        }
    }
}
