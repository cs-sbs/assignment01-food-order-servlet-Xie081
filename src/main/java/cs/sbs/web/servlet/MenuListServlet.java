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
import java.util.stream.Collectors;

public class MenuListServlet extends HttpServlet {
    // 模拟菜单数据
    private static final List<MenuItem> MENU = new ArrayList<>();
    static {
        MENU.add(new MenuItem("Fried Rice", 8));
        MENU.add(new MenuItem("Fried Noodles", 9));
        MENU.add(new MenuItem("Burger", 10));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        List<MenuItem> result;

        if (name == null || name.isBlank()) {
            result = MENU;
        } else {
            // 按菜名模糊搜索
            result = MENU.stream()
                    .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        out.println("Menu List:");
        out.println();
        for (int i = 0; i < result.size(); i++) {
            MenuItem item = result.get(i);
            out.println((i + 1) + ". " + item.getName() + " - $" + item.getPrice());
        }
    }
}
