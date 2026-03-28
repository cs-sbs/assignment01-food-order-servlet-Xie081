package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderCreateServlet extends HttpServlet {

    private static final List<Order> ORDERS = new ArrayList<>();
    private static final AtomicInteger NEXT_ID = new AtomicInteger(1001);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html" +"; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String customer = request.getParameter("customer");
        String food = request.getParameter("food");
        String quantityStr = request.getParameter("quantity");

        if (customer == null || customer.isBlank() ||
                food == null || food.isBlank() ||
                quantityStr == null || quantityStr.isBlank()) {
            out.println("Error: all parameters (customer, food, quantity) are required");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                out.println("Error: quantity must be positive");
                return;
            }
        } catch (NumberFormatException e) {
            out.println("Error: invalid quantity");
            return;
        }

        int orderId = NEXT_ID.getAndIncrement();
        Order order = new Order(orderId, customer, food, quantity);
        ORDERS.add(order);

        out.println("Order Created: " + orderId);
        out.println("<a href='/order/" + orderId + "'>Order #" + orderId + " (Click to view details)</a>");
    }

    public static Order findOrderById(int orderId) {
        return ORDERS.stream()
                .filter(o -> o.getOrderId() == orderId)
                .findFirst()
                .orElse(null);
    }
}
