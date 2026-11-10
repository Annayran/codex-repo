# Changelog

## Nao publicado
- SinalInclusaoProduto envia sinal somente para itens cujo cabecalho tenha
  TIPMOV = C ou V, consultado pela NUNOTA da TGFITE.
- Bloqueios de confirmacao restritos a notas com TIPMOV = C ou V.
- Comparacao de NCM e IPI entre XML da TGFIXN e cadastro TGFPRO/TGFIPI na regra
  existente de confirmacao; vinculo por SEQUENCIAFISCAL e nItem.
- 33 cenarios de teste fiscal e parser seguro integrados ao check Gradle.
- Criterios de IPI zerado e nota sem XML documentados como provisorios.

## 2.0
- Sinal de minuta br.zaya.solMinComercial na transicao de AD_SOLMIN para S.
- Sinal independente br.zaya.libProcComercial na transicao de AD_LIBPROC para N.
- Instancia obtida de AD_IDNEGOCIO e NUNOTA do VO, sem SQL.
- Testes de regras integrados ao check Gradle.

## 1.0
- Evento inicial de liberacao CRM com AD_IDINSTPRN e flowAdLibproc.
- Build Gradle corporativo e separacao da regra e integracao Flow.
