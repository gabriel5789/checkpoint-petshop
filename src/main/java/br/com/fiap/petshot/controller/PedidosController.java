package br.com.fiap.petshot.controller;

import br.com.fiap.petshot.dao.*;
import br.com.fiap.petshot.model.*;
import br.com.fiap.petshot.service.CriarPedidoService;
import br.com.fiap.petshot.validation.FormPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("pedidos")
public class PedidosController {

	@Autowired
	CriarPedidoService service;

	@GetMapping("criar")
	public String criar(Cliente cliente, Model model) {
		if (service == null)
			service = new CriarPedidoService();
		if (service.getCliente() == null)
			return "pedido/cliente";
		if (service.getLoja() == null
				|| service.getVendedor() == null)
			return "redirect:dados-loja";
		return "redirect:itens";
	}

	@GetMapping("pesquisar")
	public String pesquisar(@RequestParam("tipoCliente") String tipoCliente, @RequestParam("id") String id, Model model,
							RedirectAttributes redirectAttributes) {
		Cliente cliente = null;
		try {
			if (tipoCliente.equals("PF")) {
				cliente = new ClienteDAO().getClienteByCpf(id);
			} else
				cliente = new ClienteDAO().getClienteByCnpj(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (cliente == null) {
			model.addAttribute("error", "Cliente não encontrado");
			model.addAttribute("cliente", new Cliente(null, "", new Endereco(), "", ""));
			return "pedido/cliente";
		}

		model.addAttribute("cliente", cliente);
		return "pedido/cliente";
	}

	@PostMapping("criar")
	public String criar(@Valid Cliente cliente, BindingResult result, RedirectAttributes redirectAttributes) throws SQLException {
		System.out.println("inserindo cliente");
		System.out.println(cliente);
		cliente = new ClienteDAO().getClienteByEmail(cliente.getEmail());
		System.out.println(cliente);
		service.setCliente(cliente);

		return "redirect:criar";
	}

	@GetMapping("dados-loja")
	public String inserirDadosLoja(Model model) {
		try {
			model.addAttribute("lojas", new LojaDAO().listarLojas());
			model.addAttribute("vendedores", new VendedorDAO().listarVendedores());
			model.addAttribute("cliente", service.getCliente());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "pedido/dados-loja";
	}

	@PostMapping("dados-loja")
	public String inserirDadosLoja(@RequestParam("idLoja") Integer idLoja, @RequestParam("idVendedor") Integer idVendedor,
								   RedirectAttributes redirectAttributes) {
		if (idLoja == null)
			redirectAttributes.addAttribute("error", "Loja inválida");
		else if (idVendedor == null)
			redirectAttributes.addAttribute("error", "Vendedor inválido");
		try {
			service.setVendedor(new VendedorDAO().pesquisar(idVendedor));
			service.setLoja(new LojaDAO().pesquisar(idLoja));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:itens";
	}

	@GetMapping("itens")
	public String inserirItens(ItemVenda itemVenda, Model model) {
		model.addAttribute("itens", service.getItensVenda());
		return "pedido/itens";
	}

	@PostMapping("add-item")
	@Validated(FormPedido.class)
	public String addItem(@Valid ItemVenda itemVenda, BindingResult result, RedirectAttributes redirectAttributes) {
		ProdutoDAO dao = new ProdutoDAO();
		try {
			if (result.hasErrors() || itemVenda.getProduto().getId() == null || dao.pesquisar(itemVenda.getProduto().getId()) == null) {
				List<ObjectError> errors = result.getAllErrors();
				List<String> messages = new LinkedList<>();
				StringBuilder msg = new StringBuilder();
				for (ObjectError error : errors) {
					messages.add(error.getDefaultMessage());
				}
				if (itemVenda.getProduto().getId() == null || dao.pesquisar(itemVenda.getProduto().getId()) == null) {
					messages.add("Código de produto inválido");
				}
				redirectAttributes.addFlashAttribute("errors", messages);
				return "redirect:/pedidos/itens";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			List<String> errors = new LinkedList<>();
			errors.add("Um erro ocorreu ao processar sua solicitação");
			redirectAttributes.addFlashAttribute("errors", errors);
			return "redirect:/pedidos/itens";
		}

		try {
			Produto produto = dao.pesquisar(itemVenda.getProduto().getId());
			if (itemVenda.getPreco() == null) {
				itemVenda.setPreco(produto.getValor());
			}
			itemVenda.setProduto(produto);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		List<ItemVenda> itens = service.getItensVenda();
		System.out.println(itemVenda);
		redirectAttributes.addFlashAttribute("msg", "Item adicionado com sucesso");
		for (ItemVenda item :
				itens) {
			if (item.getProduto().getId().equals(itemVenda.getProduto().getId())) {
				item.setQuantidade(item.getQuantidade() + itemVenda.getQuantidade());
				item.setPreco(itemVenda.getPreco());
				return "redirect:/pedidos/itens";
			}
		}
		service.getItensVenda().add(itemVenda);
		return "redirect:/pedidos/itens";
	}

	@PostMapping("itens")
	public String fecharPedido(Model model, RedirectAttributes redirectAttributes) {
		PedidoDAO dao = new PedidoDAO();
		List<ItemVenda> itemVendas = service.getItensVenda();
		for (ItemVenda item :
				itemVendas) {
			item.setVendedorAprovador(service.getVendedor());

		}
		if (service.getId() != null) {
			try {
				dao.atualizar(new Pedido(service.getId(), null, service.getCliente(), service.getVendedor(), service.getLoja(), service.getItensVenda()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			service = null;
			redirectAttributes.addFlashAttribute("msg", "Pedido atualizado com sucesso");
			return "redirect:/pedidos/editar";
		}
		Pedido pedido = new Pedido(null, null, service.getCliente(), service.getVendedor(), service.getLoja(), service.getItensVenda());
		Integer id;
		try {
			pedido = dao.cadastrar(pedido);
			System.out.println("Inserido pedido");
			System.out.println(pedido);
		} catch (SQLException e) {
			e.printStackTrace();
			return "pedido/fail";
		}
		id = pedido.getId();
		service = null;
		model.addAttribute("codigoProduto", id);
		return "pedido/success";
	}

	@GetMapping("itens/editar/{id}")
	public String editar(@PathVariable Integer id, Model model) {
		List<ItemVenda> itens = service.getItensVenda();
		for (ItemVenda i :
				itens) {
			if (i.getProduto().getId().equals(id))
				model.addAttribute("itemVenda", i);
		}
		if (!model.containsAttribute("itemVenda"))
			return "redirect:/pedidos/itens";
		return "pedido/editar-item";
	}

	@PostMapping("itens/editar")
	public String editar(@Valid ItemVenda itemVenda, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("itemVenda", itemVenda);
			return "pedido/editar-item";
		}
		List<ItemVenda> itens = service.getItensVenda();
		for (int i = 0; i < itens.size(); i++) {
			if (itens.get(i).getProduto().getId().equals(itemVenda.getProduto().getId())) {
				itens.set(i, new ItemVenda(itens.get(i).getProduto(), itemVenda.getQuantidade(),
						(itemVenda.getPreco() == null ? itens.get(i).getProduto().getValor() : itemVenda.getPreco()),
						itemVenda.getVendedorAprovador()));
				if (itens.get(i).getQuantidade().equals(0)) {
					itens.remove(i);
					redirectAttributes.addFlashAttribute("msg", "Item editado com sucesso");
					return "redirect:/pedidos/itens";
				}
			}
		}
		redirectAttributes.addFlashAttribute("msg", "Item editado com sucesso");
		return "redirect:/pedidos/itens";
	}

	@PostMapping("itens/deletar/{id}")
	public String deletar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		List<ItemVenda> itens = service.getItensVenda();
		for (int i = 0; i < itens.size(); i++) {
			if (itens.get(i).getProduto().getId().equals(id)) {
				itens.remove(i);
				redirectAttributes.addFlashAttribute("msg", "Item removido com sucesso");
				return "redirect:/pedidos/itens";
			}
		}
		redirectAttributes.addFlashAttribute("error", "Item não encontrado");
		return "redirect:/pedidos/itens";
	}

	@GetMapping("editar")
	public String editar(Model model) {
		try {
			model.addAttribute("pedidos", new PedidoDAO().listarPedidos());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "pedido/listar";
	}

	@GetMapping("editar/{id}")
	public String editarPedido(@PathVariable("id") Integer id) {
		service = new CriarPedidoService();
		try {
			Pedido pedido = new PedidoDAO().pesquisarPedido(id);
			service.setId(pedido.getId());
			service.setLoja(pedido.getLoja());
			service.setVendedor(pedido.getVendedor());
			service.setItensVenda(pedido.getItensVenda());
			service.setCliente(pedido.getCliente());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/pedidos/itens";
	}

	@PostMapping("deletar")
	public String deletarPedido(RedirectAttributes redirectAttributes) {
		if (service.getId() != null) {
			try {
				new PedidoDAO().deletar(service.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		service = null;
		redirectAttributes.addFlashAttribute("msg", "Pedido deletado com sucesso");
		return "redirect:/pedidos/editar";
	}

}
