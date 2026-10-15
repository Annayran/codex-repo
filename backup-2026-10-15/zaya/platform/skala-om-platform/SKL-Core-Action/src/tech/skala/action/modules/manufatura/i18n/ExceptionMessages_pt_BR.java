package tech.skala.action.modules.manufatura.i18n;

import java.util.ListResourceBundle;

/**
 * A classe {@code Messages_pt_BR} eh uma implementacao da classe abstrata 
 * {@code java.util.ListResourceBundle}.
 * <p>
 * 
 * Esta classe implementa o metodo @getContents() que retorna uma mensagem 
 * conforme a chave informada.<br>
 * As mensagens desta classe sao utilizadas no contexto do modulo <b>Manufatura</b>.
 * 
 * @author cassiomenezes
 * @author hiagoalexandre
 * @since 2022-07-22
 * @see java.util.ListResourceBundle
 *
 */
public class ExceptionMessages_pt_BR extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { 
			{ "welcome", "Bem vindo ao ERP Grupo Skala" },
			{ "standard-capacity-not-found", "[CENTRO DE TRABALHO %s] Capacidade Padrão não encontrada." },
			{ "multiple-standard-productive-processes-found", "[PRODUTO %s] Existe mais de um processo produtivo padrão para o Produto." },
			{ "standard-productive-process-not-found", "[PRODUTO %s] Processo Produtivo não encontrado para o produto." },
			{ "schedule-not-in-elaboration", "[PROGRAMACAO %s] Só é possível gerar OP para Programação <b>Em Elaboração</b>.%n"},
			{ "batch-less-than-one" , "Quantidade de Lotes não pode ser menor que <b>1</b>!"},
			{ "unable-generate-production-launch", "Não foi possível gerar <b>Lançamento de Produção</b>"},
			{ "production-order-not-included-for-product", "[CODPROD %s] Não foi possível inserir OP para o produto." },
			{ "production-order-not-included", "Não foi possível inserir Ordem(ns) de Produção para o Lançamento." },
			{ "workcenter-not-set", "Não foi possível alocar o <b>Centro de Trabalho</b> %s do Lancamento %s." },
			{ "delete-production-order-launch-not-possible", "[NULOP %s] Não foi possível excluir o Lançamento de OP." },
			{ "uninitialized-sequencing", "Sequenciamento não inicializado. Favor informar os dados."},
			{ "sequencing-not-included", "Não foi possível gerar Sequenciamento"},
			{ "sequencing-not-updated", "[Sequenciamento %s] Não foi possível atualizar o Sequenciamento"},
			{ "schedule-not-found", "[Programação %06d] Não foi possível localizar a programação.%n"},
			{ "schedule-not-included", "Não foi possível incluir as Ordens de Produção na Programação."},
			{ "product-not-found", "Produto Acabado não localizado"},
			{ "schedule-not-update", "[PROGRAMACAO %s] Não foi possível atualizar a Programação.%n"},
			{ "workcenter-not-found", "[WorkCenter %s] Não foi possível localizar Centro de Trabalho."},
			{ "status-not-valid", "[Ordem de Produção %s] [Situação %s] Lote não pode ser alterado."},
			{ "production-order-not-created", "[Ordem de Produção %s] Só é possível cancelar Op's com Status <b>CRIADA</b>"},
			{ "production-order-not-canceled", "[Ordem de Produção %s] Não foi possível cancelar OP."},
			{ "lote-max-for-product-utilized", "[Lote gerado %s] é maior que último lote utilizado %s para o produto %s."},
			{ "lote-max-bigger-then-last-number", "[Número de Lote informado %s] É maior que último número utilizado %s para o produto %s."},
			{ "lote-not-updated", "Não foi possível atualizar numeração do Lote." },
			{ "production-order-creation-not-possible", "Não foi possível criar Ordens de Produção." }
			}; 
	}

}
