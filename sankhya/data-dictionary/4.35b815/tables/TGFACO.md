# TGFACO — Acordos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 21
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Acordo | Acordos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALISARGIRO | Analisar giro | I |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODCONTATO | Contato | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODLOCALORIG | Local Origem | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUCOT | CODUSUCOT | I |  |  | N |
| CODUSURESP | Cód. Usuário Responsável | I |  |  | N |
| DHVAL | Data de Validade | H |  |  | N |
| DIASAVISOVALIDADE | Dias de aviso para validade | I |  |  | N |
| DTACORDO | Data do Acordo | H |  |  | N |
| EXIGEAPROVPED | Exige Aprovação do Pedido | S |  |  | N |
| GERARPED | Gerar pedido | S |  |  | N |
| NUACORDO | Número do Acordo | I |  |  | N |
| TIPEST | Tipo de Estoque | S |  |  | N |
| VIAPEDIDO | Via para o Pedido | S |  |  | N |
| VLRMINPED | Valor mínimo do pedido | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Acordo / [TGFACO](TGFACO.md) | ItemAcordo / [TGFIAC](TGFIAC.md) | NUACORDO->NUACORDO | I |
| Acordo / [TGFACO](TGFACO.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Acordo / [TGFACO](TGFACO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSURESP->CODUSU | I |
| ItemAcordo / [TGFIAC](TGFIAC.md) | Acordo / [TGFACO](TGFACO.md) | NUACORDO->NUACORDO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
