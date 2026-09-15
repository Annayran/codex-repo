# TGFFAD — Ficha de Acompanhamento Diário

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FichaAcompanhamentoDiario | Ficha de Acompanhamento Diário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODFUNC | Funcionario | I |  |  | N |
| CODGRUPOPROD | Grupo de produtos | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODSERV | Servico | I |  |  | N |
| DTEXEC | Data | H |  |  | N |
| HRFIM | Hora final | I |  |  | N |
| HRINICIO | Hora Inicial | I |  |  | N |
| MINUTOS | Minutos | I |  |  | N |
| NUNOTA | Número da nota | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FichaAcompanhamentoDiario / [TGFFAD](TGFFAD.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| FichaAcompanhamentoDiario / [TGFFAD](TGFFAD.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| FichaAcompanhamentoDiario / [TGFFAD](TGFFAD.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
