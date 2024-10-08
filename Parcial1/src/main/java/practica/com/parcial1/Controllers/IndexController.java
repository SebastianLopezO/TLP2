package practica.com.parcial1.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping({"", "/", "/Home"})
    public String index(Model model) {
        model.addAttribute("Title", "Home Parcial#1");
        return "Index";
    }
}
