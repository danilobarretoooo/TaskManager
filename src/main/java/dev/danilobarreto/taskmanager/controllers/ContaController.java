package dev.danilobarreto.taskmanager.controllers;

import javax.validation.Valid;

import dev.danilobarreto.taskmanager.modelos.Usuario;
import dev.danilobarreto.taskmanager.servicos.ServicoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContaController {
	
	@Autowired
	private ServicoUsuario servicoUsuario;
	
	@GetMapping("/login")
	public String login() {
		return "conta/login.html";
	}
	
	@GetMapping("/registration")
	public ModelAndView registrar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("conta/registrar");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@PostMapping("/registration")
	public ModelAndView registrar(@Valid Usuario usuario, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		Usuario usr = servicoUsuario.encontrarPorEmail(usuario.getEmail());
		if (usr != null) {
			result.rejectValue("email", "", "Usuario já cadastrado.");
		}
		if (result.hasErrors()) {
			mv.setViewName("conta/registrar");
			mv.addObject("usuario", usuario);
		} else {
			servicoUsuario.salvar(usuario);
			mv.setViewName("redirect:/login");
		}
		return mv;
	}

}
