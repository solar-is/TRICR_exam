package com.prosolovich.tricr.exam;

import com.prosolovich.tricr.exam.domain.Point;
import com.prosolovich.tricr.exam.domain.Role;
import com.prosolovich.tricr.exam.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.sqrt;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "login";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        User userInDB = userRepository.findByUsername(user.getUsername());
        if (userInDB != null) {
            model.addAttribute("message", "User already exist!");
            return "redirect:/login";
        } else {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
            model.addAttribute("message", "User " + user.getUsername() + " successfully signed up!");
            return "redirect:/login";
        }
    }

    @RequestMapping("/")
    public String mainPage(HttpServletRequest req, Model model) {
        if (req.getParameter("clearSession") != null) {
            req.getSession().setAttribute("entries", null);
        }
        model.addAttribute("entries", req.getSession().getAttribute("entries"));
        return "index";
    }

    @RequestMapping("/areaCheck")
    protected String check(HttpServletRequest req, Model model) {
        String x = req.getParameter("x");
        String yVal = req.getParameter("yVal");
        String r = req.getParameter("r");

        double xReal = 0d, yReal = 0d, rReal = 0d;

        boolean parsedSuccessfully = true;

        try {
            xReal = Double.parseDouble(x);
            yReal = Double.parseDouble(yVal);
            rReal = Double.parseDouble(r);
        } catch (Exception e) {
            parsedSuccessfully = false;
        }

        String onlyDefaultValidation = req.getParameter("onlyDefaultValidation");

        boolean isValid = isValid(xReal, yReal, rReal);

        if ((onlyDefaultValidation != null && !parsedSuccessfully) ||
                (onlyDefaultValidation == null && (!parsedSuccessfully || !isValid))){
            return "areaCheck";
        }
        boolean isEntry = isEntry(xReal, yReal, rReal);

        Point point = new Point(xReal, yReal, (int) rReal, isEntry);

        HttpSession session = req.getSession();
        Object entriesObject = session.getAttribute("entries");
        List<Point> entries;
        if (entriesObject != null) {
            entries = (List<Point>) entriesObject;
        } else {
            entries = new ArrayList<>();
        }
        entries.add(point);
        session.setAttribute("entries", entries);

        model.addAttribute("point", point);
        return "areaCheck";
    }

    private static boolean isValid(double x, double y, double r) {
        return (x >= -2 && x <= 2 && y >= -5 && y <= 5 && r >= 1 && r <= 5);
    }

    private static boolean isEntry(double x, double y, double r) {
        return (x <= 0 && y >= 0 && y <= r / 2 && x >= -r)
                || (x >= 0 && y <= 0 && x <= r && y >= 0.5 * (x - r))
                || (x <= 0 && y <= 0 && x >= -r && y >= -(sqrt(r - x) * sqrt(r + x)));
    }

}
