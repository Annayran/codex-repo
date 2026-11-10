package br.com.zaya.apiboletos.application;

import static br.com.zaya.apiboletos.domain.Models.require;

import br.com.zaya.apiboletos.domain.Models.*;
import java.math.BigDecimal;
import java.util.*;

public final class InstructionValidator {
  public static void validate(Command command, Map<String, String> v) {
    Set<String> allowed = new HashSet<String>();
    switch (command) {
      case VENCIMENTO:
        allowed.add("data_vencimento");
        break;
      case VALOR:
        allowed.add("valor_nominal");
        break;
      case ABATIMENTO:
        allowed.add("valor_abatimento");
        break;
      case JUROS:
        allowed.addAll(
            Arrays.asList("codigo_tipo_juros", "percentual_juros", "valor_juros", "data_juros"));
        break;
      case MULTA:
        allowed.addAll(
            Arrays.asList("codigo_tipo_multa", "percentual_multa", "valor_multa", "data_multa"));
        break;
      case DESCONTO:
        allowed.addAll(Arrays.asList("codigo_tipo_desconto", "valor_desconto", "data_desconto"));
        break;
      case CANCELAR:
      case ENTREGAR:
        require(v.isEmpty(), "COMANDO_SEM_PARAMETROS");
        return;
      default:
        throw new IllegalArgumentException("COMANDO_INVALIDO");
    }
    require(!v.isEmpty() && allowed.containsAll(v.keySet()), "CAMPOS_INSTRUCAO_INVALIDOS");
    for (Map.Entry<String, String> e : v.entrySet()) {
      require(e.getValue() != null && !e.getValue().trim().isEmpty(), "CAMPO_INSTRUCAO_AUSENTE");
      if (e.getKey().startsWith("data_")) java.time.LocalDate.parse(e.getValue());
      else if (e.getKey().startsWith("codigo_"))
        require(e.getValue().matches("[0-9]{2}"), "CODIGO_INVALIDO");
      else {
        require(e.getValue().matches("[0-9]+(\\.[0-9]{1,5})?"), "VALOR_INSTRUCAO_INVALIDO");
        require(new BigDecimal(e.getValue()).signum() >= 0, "VALOR_NEGATIVO");
      }
    }
    if (command == Command.VENCIMENTO) require(v.containsKey("data_vencimento"), "DATA_AUSENTE");
    if (command == Command.VALOR)
      require(
          v.containsKey("valor_nominal") && new BigDecimal(v.get("valor_nominal")).signum() > 0,
          "VALOR_INVALIDO");
    if (command == Command.ABATIMENTO) require(v.containsKey("valor_abatimento"), "VALOR_AUSENTE");
    if (command == Command.JUROS)
      require(
          v.containsKey("codigo_tipo_juros")
              && (v.containsKey("percentual_juros") || v.containsKey("valor_juros")),
          "JUROS_INCOMPLETOS");
    if (command == Command.MULTA)
      require(
          v.containsKey("codigo_tipo_multa")
              && v.containsKey("data_multa")
              && (v.containsKey("percentual_multa") || v.containsKey("valor_multa")),
          "MULTA_INCOMPLETA");
    if (command == Command.DESCONTO)
      require(
          v.containsKey("valor_desconto") && v.containsKey("data_desconto"), "DESCONTO_INCOMPLETO");
  }

  private InstructionValidator() {}
}
