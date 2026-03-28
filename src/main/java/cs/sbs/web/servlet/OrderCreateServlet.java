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

public class OrderCreateServlet extends HttpServlet {

    private static final List<Order> ORDER_LIST = new ArrayList<>();
    private static int nextId = 1001;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String customer = request.getParameter("customer");
        String food = request.getParameter("food");
        String quantityStr = request.getParameter("quantity");

        // 空值判断
        if (customer == null || customer.isBlank()
                || food == null || food.isBlank()
                || quantityStr == null || quantityStr.isBlank()) {
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
            out.println("Error: quantity must be a valid number");
            return;
        }

        // 创建订单
        int orderId;
        synchronized (OrderCreateServlet.class) {
            orderId = nextId++;
            Order order = new Order(orderId, customer, food, quantity);
            ORDER_LIST.add(order);
        }

        response.sendRedirect("order.html?orderId=" + orderId);
    }

    public static synchronized Order getOrderById(int id) {
        for (Order o : ORDER_LIST) {
            if (o.getOrderId() == id) {
                return o;
            }
        }
        return null;
    }
}