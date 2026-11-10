# Regras de Negócio

- A nota representa o evento; produto+série representa o equipamento.
- Garantia e status pertencem à `TGFSER`.
- TOPs elegíveis vêm de `AD_CENTPARAMTOP`, `NUPAR=1`.
- `TGFTOP.ATUALEST`: `E` entrada, `B` saída, `R` reserva.
- Quando `ATUALEST` não movimenta, `ATUALESTTERC` define soma/subtração.
- Combinações `BP` e `ET` definem poder próprio e de terceiro.
- A classificação analítica preserva `TIPO` conforme `TGFESE` e `PODER`
  conforme `TGFEST.CODPARC`.
- Somente `TIPO_ESTOQUE` compara o CNPJ do parceiro com a `TSIEMP`: CNPJ de uma
  empresa gera `PROPRIO`, CNPJ externo gera `TERCEIRO` e documento ausente gera
  `NAO_CLASSIFICADO`.
- Configuração incompleta vira divergência e não é enviada.
- `matQuantidade=1` por equipamento serializado. Confirmar em homologação.
