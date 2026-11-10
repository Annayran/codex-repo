# Provisão manual e agendada - candidato local

> NÃO EXECUTAR DIRETAMENTE EM PRODUÇÃO.

## Conclusão da revisão

O botão manual e a ação agendada devem delegar à mesma procedure central,
`STP_ATUALIZA_PROV_FILA_PRA`. A procedure não deve executar `COMMIT`,
`ROLLBACK` nem usar transação autônoma.

O botão atual funciona como delegador, porém possui quatro variáveis não usadas
e um bloco `BEGIN/END` redundante. O arquivo `deploy/01_...` limpa esses pontos
sem mudar a assinatura cadastrada no Sankhya.

## Problemas da procedure central atual

1. `TRUNC(DTPREV - SYSDATE) <= 120` usa dias corridos, contrariando os 120 dias
   úteis confirmados.
2. A condição inclui qualquer data vencida antiga, pois valores negativos também
   são menores ou iguais a 120.
3. `DTPREV IS NULL` é descartado implicitamente, sem regra documentada.
4. `MAX(SEQENT) + 10` é consultado dentro do loop e pode colidir em execuções
   concorrentes do botão e do agendamento.
5. O processamento não devolve quantidade, dificultando auditoria operacional.

## Gate para corrigir a procedure central

Executar `validation/01_VALIDAR_CALENDARIO_EMPRESARIAL.sql` no ambiente TESTE e
confirmar:

- domínio real de `TSIFER.NACIONAL`;
- uso de feriado obrigatório e recorrente;
- relacionamento da empresa do pedido com país, UF e cidade;
- regra para `DTPREV IS NULL`;
- se o dia corrente participa da contagem dos 120 dias úteis.

Sem essas respostas, alterar a procedure criaria uma regra funcional não
comprovada. O JAR da ação agendada pode ser compilado e instalado em homologação,
mas o agendamento não deve ser ativado em produção antes desse gate.
