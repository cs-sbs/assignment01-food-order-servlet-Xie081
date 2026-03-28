package cs.sbs.web.servlet;

import cs.sbs.web.model.MenuItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MenuListServlet extends HttpServlet {

    private static final List<MenuItem> MENU = new ArrayList<>();

    static {
        MENU.add(new MenuItem("Fried Rice", 8.0));
        MENU.add(new MenuItem("Fried Noodles", 9.0));
        MENU.add(new MenuItem("Burger", 10.0));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");

        if (name == null) {
            printMenu(MENU, out);
            return;
        }

        if (name.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Error: search name cannot be empty");
            return;
        }

        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : MENU) {
            if (item.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(item);
            }
        }
        printMenu(result, out);
    }

    private void printMenu(List<MenuItem> menu, PrintWriter out) {
        out.println("Menu List:");
        out.println();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.get(i);
            out.println((i + 1) + ". " + item.getName() + " - $" + item.getPrice());
        }
    }
}