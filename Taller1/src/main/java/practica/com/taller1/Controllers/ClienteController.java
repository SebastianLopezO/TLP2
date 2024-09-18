package practica.com.taller1.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import practica.com.taller1.Models.DAO.cliente.IClienteDao;
import practica.com.taller1.Models.Entity.Cliente;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/Clientes")
public class ClienteController {

    @Autowired
    private IClienteDao clienteDao;

    @GetMapping({"","/"})
    public String Listar(Model model, @RequestParam(required=false) boolean confirmDel) {
        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("cliente", clienteDao.findAll());
        model.addAttribute("confirmDel", confirmDel);
        return "/Cliente/List";
    }

    @GetMapping("/Form")
    public String crear(Model model) {

        Cliente cliente = new Cliente();

        model.addAttribute("titulo", "Formulario de Cliente");
        model.addAttribute("cliente", cliente);

        return "/Cliente/Form";
    }

    @PostMapping("/Form")
    public String Guardar(@Valid Cliente cliente, BindingResult result, Model model)
    {

        if(result.hasErrors())
        {
            model.addAttribute("titulo", "Formulario de Cliente ********");
            return "/Cliente/Form";
        }

        clienteDao.Save(cliente);
        //Status.setComplete();
        return "redirect:/Clientes";
    }

    @GetMapping("/Edit/{id}")
    public String Editar(@PathVariable Long id, Model model){
        Cliente cliente=null;

        if(id>0){
            cliente=clienteDao.findOne(id);
        }else{
            return "redirect:/listar";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        return "/Cliente/Form";
    }

    @GetMapping("/Delete/{id}")
    public String Eliminar(@PathVariable Long id){

        if(id>0){
            clienteDao.Delete(id);
        }
        return "redirect:/Clientes?confirmDel=true";
    }

}


