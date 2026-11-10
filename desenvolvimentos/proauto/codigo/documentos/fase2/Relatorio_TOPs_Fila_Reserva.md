# Relatório de classificação das TOPs — Fila de Reserva

Data do levantamento: 17/08/2026  
Ambiente: `TESTE`  
Campo analisado: `TGFTOP.AD_FILARESERVA`

## Evidência

- Arquivo: `2026-08-17_TESTE_B3_03_01_TOPS_FILA_RESERVA.xlsx.xlsx`.
- SHA-256: `031B84EBA37660C8A6D63ADEAC7C129AD6BAE81C29325A3250FC8ADE88DDC3A9`.
- Últimas versões marcadas com `AD_FILARESERVA = 'S'`: `72`.

## Diferença comprovada entre trigger e view

- A trigger atual considera somente `AD_FILARESERVA = 'S'` e pode atuar nas 72 TOPs.
- A `AD_VGFRESEST` exige o mesmo campo e também `TIPMOV IN ('P','J')`.
- Assim, 57 TOPs podem atuar sobre `AD_TGFSEQENT` sem aparecer na fila ativa.

O documento `ZY-PROA-2026-VALFIL-001` confirmou que a posição na fila é criada para pedidos de venda participantes do processo com `AD_FILARESERVA = 'S'`. As demais operações não devem ganhar posição apenas por possuírem a marcação; as 57 ocorrências continuam exigindo teste de regressão antes de qualquer alteração cadastral.

## Grupo A — candidatas a criar reserva

### TIPMOV `P` — 14 TOPs

`900`, `901`, `902`, `906`, `908`, `909`, `911`, `920`, `925`, `926`, `927`, `932`, `955`, `956`.

Todas precisam de aceite individual. Entrega futura, exportação, doação/bonificação e operação triangular podem exigir tratamentos próprios.

### TIPMOV `J` — 1 TOP

- `401` — Fila de Reserva para Ordem de Produção.

## Grupo B — não participantes da criação de posição ativa

Essas TOPs não aparecem na view ativa e não devem criar posição ativa fora do critério confirmado. Não remover a marcação cadastral em massa sem verificar outros usos legados.

| TIPMOV | Quantidade | Códigos |
|---|---:|---|
| `E` | 13 | `300`, `301`, `302`, `305`, `306`, `308`, `309`, `310`, `313`, `314`, `317`, `337`, `339` |
| `Q` | 3 | `500`, `501`, `502` |
| `T` | 8 | `700`, `701`, `704`, `1706`, `1707`, `1708`, `1709`, `1710` |
| `F` | 1 | `801` |
| `V` | 30 | `1106`, `1107`, `1109`, `1112`, `1113`, `1116`, `1117`, `1119`, `1120`, `1121`, `1122`, `1124`, `1129`, `1130`, `1131`, `1132`, `1137`, `1139`, `1141`, `1142`, `1144`, `1151`, `1153`, `1158`, `1164`, `1165`, `1166`, `1170`, `1173`, `1177` |
| `C` | 2 | `1175`, `1176` |

## Grupo C — revisão prioritária por descrição inativa

- `302` — Retorno de remessa consignada de compra.
- `306` — Evolução de compra/bonificação.
- `309` — Retorno industrial.
- `314` — Devolução de garantia WMS.
- `337` — Retorno de conserto.
- `1113` — Simples remessa.

Não remover a marcação sem verificar uso, histórico e responsável funcional.

## Pontos técnicos remanescentes

- Compras futuras foram comprovadas em oito TOPs com pedidos confirmados e
  pendentes `TIPMOV = 'C'` na coleta de 18/08/2026.
- Foram identificadas 23 TOPs atuais de OE, códigos `1000` a `1034` conforme
  os cadastros existentes, todas com `AD_FILARESERVA = 'N'`. O bloqueio deve ser
  comprovado pelo vínculo com o pedido de origem durante o faturamento.
- A proteção mantém o comportamento atual, sem nova separação por empresa, local, lote, série, controle ou alçada.

## Recomendação candidata

1. Criar posição somente para pedidos participantes do processo, com flag `S`, preservando o filtro atual da fila ativa.
2. Não introduzir nova regra de proteção por empresa, local, lote, série ou controle.
3. Não usar a gravação em `AD_TGFSEQENT` para movimentos que não participam da fila ativa.
4. Testar as 72 TOPs antes de alterar trigger ou marcações cadastrais.
