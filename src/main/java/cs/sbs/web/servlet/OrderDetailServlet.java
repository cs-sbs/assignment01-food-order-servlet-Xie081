package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class OrderDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String path = request.getPathInfo();
        if (path == null || path.length() <= 1) {
            out.println("Error: order ID required");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(path.substring(1));
        } catch (NumberFormatException e) {
            out.println("Error: invalid order ID");
            return;
        }

        Order order = OrderCreateServlet.findOrderById(orderId);
        if (order == null) {
            out.println("Error: order not found");
            return;
        }

        out.println("Order Detail");
        out.println();
        out.println("Order ID: " + order.getOrderId());
        out.println("Customer: " + order.getCustomer());
        out.println("Food: " + order.getFood());
        out.println("Quantity: " + order.getQuantity());
    }
}
