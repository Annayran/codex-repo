# TGFACT — Acompanhamento de Notas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AcompanhamentoNota | Acompanhamento de Notas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DHOCOR | Data | D |  |  | N |
| DIGITADO | Digitado | S |  |  | N |
| HRACT | Hr. acompanhamento | T |  |  | N |
| NOTAPONTUAL | Nota para pontualidade | I |  |  | N |
| NOTAQUALIDADE | Nota para qualidade | I |  |  | N |
| NUMNOTA | Número Nota | I |  |  | S |
| NUNOTA | Número único Nota | I |  |  | N |
| OCORRENCIAS | Ocorrências | S |  |  | N |
| REFERENCIA | Referência | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcompanhamentoNota / [TGFACT](TGFACT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| AcompanhamentoNota / [TGFACT](TGFACT.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| AcompanhamentoNota / [TGFACT](TGFACT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | AcompanhamentoNota / [TGFACT](TGFACT.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
