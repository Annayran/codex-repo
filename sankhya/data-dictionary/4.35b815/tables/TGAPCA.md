# TGAPCA — Pesagem Inicial e Final do Caminhão no Armazém

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 18
- Instâncias DWF: 2
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Pesagem | Pesagem | S | N |
| PesagemArmazem | Pesagem armazenagem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BALANCAPB | Balança Peso Bruto | I |  |  | N |
| BALANCATARA | Balança Tara | I |  |  | N |
| CODPARCTRANSP | Cód.Parceiro Transportadora | I |  |  | N |
| CODUSUPB | Cód. Usuário Peso Bruto | I |  |  | N |
| CODUSUTARA | Cód. Usuário Tara | I |  |  | N |
| CODVEICULO | Cód.Veiculo | I |  |  | N |
| DHPESOBRUTO | Data Hora Peso Bruto | H |  |  | N |
| DHTARA | Data Hora Tara | H |  |  | N |
| NFTRANSP | Nota Fiscal Transportadora | S |  |  | N |
| NUNOTA | Número único Nota | I |  |  | N |
| NUNOTADEST | Número Único da Nota de Destino | I |  |  | N |
| NUPESAGEM | Nùmero de pesagem | I |  |  | N |
| PESAGEMMANUAL | Pesagem Manual | S |  |  | N |
| PESOBRUTO | Peso Bruto | F |  |  | N |
| PESOLIQ | Peso Líquido | F |  |  | N |
| SEQUENCIA | Sequencia | I |  |  | N |
| TARA | Tara | F |  |  | N |
| VARIASNOTAS | Várias Notas? | S |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
