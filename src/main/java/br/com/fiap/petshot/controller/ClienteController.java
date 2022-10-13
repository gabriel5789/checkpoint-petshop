package br.com.fiap.petshot.controller;

import br.com.fiap.petshot.dao.ClienteDAO;
import br.com.fiap.petshot.dao.EnderecoDAO;
import br.com.fiap.petshot.model.Cliente;
import br.com.fiap.petshot.model.ClientePF;
import br.com.fiap.petshot.model.ClientePJ;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@RequestMapping("cliente")
public class ClienteController {

    @GetMapping("cadastro")
    public String cadastro() {
        return "cliente/cadastro";
    }

    @GetMapping("cadastro-pf")
    public String cadastroPF(ClientePF clientePF, Model model) {
        try {
            model.addAttribute("estados", new EnderecoDAO().getEstados());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "cliente/cadastro-pf";
    }

    @GetMapping("cadastro-pj")
    public String cadastroPJ(ClientePJ clientePJ, Model model) {
        try {
            model.addAttribute("estados", new EnderecoDAO().getEstados());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "cliente/cadastro-pj";
    }

    @PostMapping("cadastro-pf")
    public String cadastroPF(@Valid ClientePF cliente, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        ClienteDAO dao = new ClienteDAO();
        if(result.hasErrors()) {
            try {
                model.addAttribute("estados", new EnderecoDAO().getEstados());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return "cliente/cadastro-pf";
        }
        try {
            dao.cadastrarCliente(cliente);
        } catch(SQLException e) {
            try {
                model.addAttribute("estados", new EnderecoDAO().getEstados());
            } catch (SQLException ex) {
                e.printStackTrace();
            }
            e.printStackTrace();
            if(e.getMessage().substring(0, 9).equals("ORA-00001")) {
                try {
                    if(dao.getClienteByCnpj(cliente.getCpf()) != null)
                        result.addError(new ObjectError("ClientePF", "Este CPF já está cadastrado"));
                    else
                        result.addError(new ObjectError("ClientePF", "Este email já está em uso"));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return "cliente/cadastro-pf";
                }
            } else {
                result.addError(new ObjectError("Erro ao cadastrar o cliente", "Erro ao cadastrar o cliente"));
                return "cliente/cadastro-pf";
            }
        }
        redirectAttributes.addFlashAttribute("msg", "Cliente cadastrado com sucesso");
        return "redirect:listar-pf";
    }

    @PostMapping("cadastro-pj")
    public String cadastroPJ(@Valid ClientePJ cliente, BindingResult result, RedirectAttributes redirectAttributes) {
        ClienteDAO dao = new ClienteDAO();
        if(result.hasErrors())
            return "cliente/cadastro-pj";
        try {
            dao.cadastrarCliente(cliente);
        } catch(SQLException e) {
            if(e.getMessage().substring(0, 9).equals("ORA-00001")) {
                try {
                    if(dao.getClienteByCnpj(cliente.getCnpj()) != null)
                        result.addError(new ObjectError("ClientePJ", "Este CNPJ já está cadastrado"));
                    else
                        result.addError(new ObjectError("ClientePJ", "Este email já está em uso"));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return "cliente/cadastro-pj";
                }
            } else {
                e.printStackTrace();
                result.addError(new ObjectError("Erro ao cadastrar o cliente", "Erro ao cadastrar o cliente"));
                return "cliente/cadastro-pj";
            }
        }
        redirectAttributes.addFlashAttribute("msg", "Cliente cadastrado com sucesso");
        return "redirect:listar-pj";
    }

    @GetMapping("listar-pf")
    public String listarPF(Model model) {
        try {
            model.addAttribute("clientesPF", new ClienteDAO().getClientesPF());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "cliente/listar-pf";
    }

    @GetMapping("listar-pj")
    public String listarPJ(Model model) {
        try {
            model.addAttribute("clientesPJ", new ClienteDAO().getClientesPJ());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "cliente/listar-pj";
    }

    @GetMapping("editar/pf/{clPF}")
    public String atualizarPF(@PathVariable("clPF") Integer id, Model model) {
        try {
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = clienteDAO.getClientePF(id);
            model.addAttribute("clientePF", cliente);
            model.addAttribute("estados", enderecoDAO.getEstados());
            model.addAttribute("cidades", enderecoDAO.getCidades(cliente.getEndereco().getEstado()));
            System.out.println(new ClienteDAO().getClientePF(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "cliente/editar-pf";
    }

    @GetMapping("editar/pj/{clPJ}")
    public String atualizarPJ(@PathVariable("clPJ") Integer id, Model model) {
        try {
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = clienteDAO.getClientePJ(id);
            model.addAttribute("clientePJ", cliente);
            model.addAttribute("estados", enderecoDAO.getEstados());
            model.addAttribute("cidades", enderecoDAO.getCidades(cliente.getEndereco().getEstado()));
            System.out.println(new ClienteDAO().getClientePJ(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "cliente/editar-pj";
    }

    @PostMapping("editar-pf")
    public String atualizarPF(ClientePF clientePF, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            new ClienteDAO().atualizar(clientePF);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("msg", "Cliente atualizado com sucesso!");
        return "redirect:listar-pf";
    }

    @PostMapping("editar-pj")
    public String atualizarPJ(ClientePJ clientePJ, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            new ClienteDAO().atualizar(clientePJ);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("msg", "Cliente atualizado com sucesso!");
        return "redirect:listar-pj";
    }

    @PostMapping("deletar/{id}")
    public String deletar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        ClienteDAO dao = new ClienteDAO();
        try {
            dao.remover(id);
            redirectAttributes.addFlashAttribute("msg", "Cliente removido com sucesso");
        } catch (SQLException e) {
            e.printStackTrace();
            if(e.getMessage().startsWith("ORA-02292")) {
                redirectAttributes.addFlashAttribute("error", "Erro ao excluir. Existem registros no sistema associados ao cliente.");
            }
        }
        return "redirect:/cliente/cadastro";
    }
}
