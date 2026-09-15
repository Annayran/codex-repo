# TSIRIM — Roteamento de impressão

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 4
- Instâncias DWF: 3
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RoteamentoImpressaoEmpresa | Roteamento de impressão | S | N |
| RoteamentoImpressaoGrupo | Roteamento de impressão | S | N |
| RoteamentoImpressaoUsuario | Roteamento de impressão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIGO | CODIGO | I |  |  | N |
| NOMEENTRADA | Nome de entrada | S |  |  | N |
| NOMESAIDA | Nome de saída | S |  |  | N |
| TIPOCODIGO | TIPOCODIGO | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | RoteamentoImpressaoEmpresa / [TSIRIM](TSIRIM.md) | CODEMP->CODIGO | I |
| GrupoUsuario / [TSIGRU](TSIGRU.md) | RoteamentoImpressaoGrupo / [TSIRIM](TSIRIM.md) | CODGRUPO->CODIGO | I |
| Usuario / [TSIUSU](TSIUSU.md) | RoteamentoImpressaoUsuario / [TSIRIM](TSIRIM.md) | CODUSU->CODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
