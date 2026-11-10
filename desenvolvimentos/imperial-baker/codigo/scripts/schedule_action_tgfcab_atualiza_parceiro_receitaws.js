/*
 * Schedule Action Sankhya - Atualizacao de parceiros por periodo de TGFCAB.DTNEG
 *
 * Como usar:
 * 1. Cole primeiro o conteudo de scripts/receitaws_parceiro_utils.js.
 * 2. Na sequencia, cole este bloco.
 *
 * Origem:
 * - TGFCAB por periodo de negociacao.
 *
 * Parametros esperados, flexiveis:
 * - DTINI / P_DTINI / DTNEG_INI / DATA_INICIAL
 * - DTFIN / P_DTFIN / DTNEG_FIN / DATA_FINAL
 *
 * Criterio inicial:
 * - SELECT DISTINCT CODPARC FROM TGFCAB
 *   WHERE DTNEG BETWEEN data inicial e data final
 *   AND CODPARC IS NOT NULL
 *
 * O criterio pode ser refinado depois com TIPMOV, CODEMP, CODTIPOPER, STATUSNOTA etc.
 */

function getParametroSchedule(nomes) {
  for (var i = 0; i < nomes.length; i++) {
    try {
      var v = getParam(nomes[i]);
      if (v != null && txt(v) !== "") {
        return v;
      }
    } catch (e1) {}
  }
  return null;
}

function asTimestampSchedule(v, fimDoDia) {
  if (v == null) return null;

  try {
    if (v instanceof java.util.Date) {
      return new java.sql.Timestamp(v.getTime());
    }
  } catch (e0) {}

  var s = txt(v);
  if (!s) return null;

  try {
    return java.sql.Timestamp.valueOf(s);
  } catch (e1) {}

  var partes = s.split(/[\/\-]/);
  if (partes.length === 3) {
    var dia, mes, ano;
    if (partes[0].length === 4) {
      ano = partes[0];
      mes = partes[1];
      dia = partes[2];
    } else {
      dia = partes[0];
      mes = partes[1];
      ano = partes[2];
    }

    if (dia.length === 1) dia = "0" + dia;
    if (mes.length === 1) mes = "0" + mes;

    var hora = fimDoDia ? "23:59:59" : "00:00:00";
    return java.sql.Timestamp.valueOf(ano + "-" + mes + "-" + dia + " " + hora);
  }

  return null;
}

function listarCodParcPorDtneg(dtIni, dtFin) {
  var jdbc = null;
  var rs = null;
  var lista = [];

  try {
    jdbc = Packages.br.com.sankhya.jape.EntityFacadeFactory.getEntityFacade().getJdbcWrapper();
    jdbc.openSession();

    var sql = new Packages.br.com.sankhya.jape.sql.NativeSql(jdbc);
    sql.setNamedParameter("DTINI", dtIni);
    sql.setNamedParameter("DTFIN", dtFin);

    rs = sql.executeQuery(
      "SELECT DISTINCT CODPARC " +
      "FROM TGFCAB " +
      "WHERE DTNEG >= :DTINI " +
      "AND DTNEG <= :DTFIN " +
      "AND CODPARC IS NOT NULL"
    );

    while (rs.next()) {
      lista.push(String(rs.getBigDecimal("CODPARC")));
    }
  } finally {
    try {
      if (rs != null) rs.close();
    } catch (eRs) {}
    try {
      if (jdbc != null) jdbc.closeSession();
    } catch (eJdbc) {}
  }

  return lista;
}

configurarProxyReceitaWs();

var paramIni = getParametroSchedule(["DTINI", "P_DTINI", "DTNEG_INI", "DATA_INICIAL"]);
var paramFin = getParametroSchedule(["DTFIN", "P_DTFIN", "DTNEG_FIN", "DATA_FINAL"]);

var dtIni = asTimestampSchedule(paramIni, false);
var dtFin = asTimestampSchedule(paramFin, true);

if (dtIni == null || dtFin == null) {
  throw "Parametros de periodo nao informados ou invalidos. Configure DTINI/DTFIN para filtrar TGFCAB.DTNEG.";
}

var parceiros = listarCodParcPorDtneg(dtIni, dtFin);

var atualizados = 0;
var ignorados = 0;
var falhas = [];

for (var i = 0; i < parceiros.length; i++) {
  try {
    var resultado = atualizarParceiroReceitaPorCodParc(parceiros[i]);
    if (resultado.atualizado) {
      atualizados++;
    } else {
      ignorados++;
    }
  } catch (e) {
    falhas.push("CODPARC " + parceiros[i] + ": " + e);
  }
}

var msg = "Schedule ReceitaWS concluido. Parceiros encontrados: " + parceiros.length + ". Atualizados: " + atualizados + ". Sem atualizacao: " + ignorados + ".";
if (falhas.length) {
  msg += " Falhas: " + falhas.join(" | ");
}

try {
  mensagem = msg;
} catch (eMsg) {
  print(msg);
}
