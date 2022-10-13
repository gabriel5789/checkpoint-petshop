package br.com.fiap.petshot.controller;

import br.com.fiap.petshot.dao.ProdutoDAO;
import br.com.fiap.petshot.model.Produto;
import br.com.fiap.petshot.validation.FormPedido;
import br.com.fiap.petshot.validation.FormProduto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@Controller
@RequestMapping("produto")
public class ProdutoController {

	@GetMapping("cadastro")
	public String cadastro(Produto produto, Model model) {
		model.addAttribute("unidadesDeMedida", new ProdutoDAO().getUnidadesDeMedida());
		return "produto/cadastro";
	}

	@PostMapping("cadastro")
	public String cadastroPost(@Validated(FormProduto.class) Produto produto, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		System.out.println(produto);
		if (result.hasErrors()) {
			model.addAttribute("unidadesDeMedida", new ProdutoDAO().getUnidadesDeMedida());
			return "produto/cadastro";
		}
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.cadastrar(produto);

		} catch (SQLException e) {
			if (e.getMessage().substring(0, 9).equals("ORA-00001")) {
				result.addError(new ObjectError("produto", "Já existe um produto com essa descrição"));
			}
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("msg", "Produto cadastrado com sucesso");
		return "redirect:atualizar";
	}

	@GetMapping("atualizar")
	public String listar(Model model) {
		try {
			model.addAttribute("produtos", new ProdutoDAO().listar());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "produto/listar";
	}

	@GetMapping("editar/{id}")
	public String editar(@PathVariable Integer id, Model model) {
		ProdutoDAO dao = new ProdutoDAO();
		try {
			model.addAttribute("produto", dao.pesquisar(id));
			model.addAttribute("unidadesDeMedida", new ProdutoDAO().getUnidadesDeMedida());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "produto/editar";
	}

	@PostMapping("editar")
	public String editar(@Validated({FormProduto.class}) Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
		if(result.hasErrors())
			return "produto/editar";

		ProdutoDAO dao = new ProdutoDAO();
		try {
			dao.atualizar(produto);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		redirectAttributes.addFlashAttribute("msg", "Produto atualizado com sucesso");
		return "redirect:atualizar";
	}

	@PostMapping("deletar/{id}")
	public String deletar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		ProdutoDAO dao = new ProdutoDAO();
		try {
			dao.deletar(id);
			redirectAttributes.addFlashAttribute("msg", "Pedido deletado com sucesso");
		} catch (SQLException e) {
			if (e.getMessage().startsWith("ORA-02292")) {
				redirectAttributes.addFlashAttribute("error", "Erro ao excluir produto." +
						"\nExistem pedidos no sistema com este produto, exclua esses pedidos para remover o produto");
			}
			e.printStackTrace();
		}
		return "redirect:/produto/atualizar";
	}
}
