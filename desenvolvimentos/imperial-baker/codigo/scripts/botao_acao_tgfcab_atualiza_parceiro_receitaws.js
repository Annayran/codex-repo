/*
 * Botao de Acao Sankhya - TGFCAB
 *
 * Como usar:
 * 1. Cole primeiro o conteudo de scripts/receitaws_parceiro_utils.js.
 * 2. Na sequencia, cole este bloco.
 *
 * Origem:
 * - Notas selecionadas na TGFCAB.
 *
 * Regra:
 * - Para cada TGFCAB selecionada, pega TGFCAB.CODPARC.
 * - Consulta o parceiro em TGFPAR.
 * - Consulta ReceitaWS pelo TGFPAR.CGC_CPF.
 * - Se TGFPAR.DTALTER for maior ou igual a ultima_atualizacao da ReceitaWS/Simples, nao atualiza.
 * - Se TGFPAR.DTALTER for menor, atualiza apenas campos do parceiro.
 */

function getLinhasTgfcabSelecionadas() {
  try {
    if (typeof linhas !== "undefined" && linhas && linhas.length) {
      return linhas;
    }
  } catch (e0) {}

  try {
    var selecionadas = getLinhasSelecionadas();
    if (selecionadas && selecionadas.length) {
      return selecionadas;
    }
  } catch (e1) {}

  return [];
}

function getCodParcLinhaCab(linhaCab) {
  var codParc = "";
  try {
    codParc = linhaCab.getCampo("CODPARC");
  } catch (e1) {}

  if (!codParc) {
    try {
      codParc = getCampo("CODPARC");
    } catch (e2) {}
  }

  return txt(codParc);
}

configurarProxyReceitaWs();

var linhasCab = getLinhasTgfcabSelecionadas();
if (!linhasCab || !linhasCab.length) {
  throw "Nenhuma nota selecionada na TGFCAB.";
}

var codParcProcessados = {};
var atualizados = 0;
var ignorados = 0;
var falhas = [];

for (var i = 0; i < linhasCab.length; i++) {
  try {
    var codParc = getCodParcLinhaCab(linhasCab[i]);
    if (!codParc) {
      falhas.push("Linha " + (i + 1) + ": CODPARC nao encontrado.");
      continue;
    }

    if (codParcProcessados[codParc]) {
      continue;
    }
    codParcProcessados[codParc] = true;

    var resultado = atualizarParceiroReceitaPorCodParc(codParc);
    if (resultado.atualizado) {
      atualizados++;
    } else {
      ignorados++;
    }
  } catch (e) {
    falhas.push(String(e));
  }
}

var msg = "Atualizacao ReceitaWS concluida. Parceiros atualizados: " + atualizados + ". Parceiros sem atualizacao: " + ignorados + ".";
if (falhas.length) {
  msg += " Falhas: " + falhas.join(" | ");
}

try {
  mensagem = msg;
} catch (eMsg) {
  print(msg);
}
