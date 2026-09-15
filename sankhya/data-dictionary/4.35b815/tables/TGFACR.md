# TGFACR — Acerto Carga

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AcertoCarga | Acertos de Cargas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BAIXADO | Baixado | F |  |  | S |
| CODEMP | Empresa | I |  |  | N |
| CODTIPTIT | Tipo Título | I |  |  | N |
| DESCRTIPTIT | Descrição do Título | S |  |  | S |
| ESPERADO | Esperado | F |  |  | S |
| ORDEMCARGA | Ordem de Carga | I |  |  | N |
| TRANSF | Transferência | F |  |  | S |
| TRANSFDIF | Transferir diferença entre Retornado e Baixado? | S |  |  | S |
| TROCO | Troco | F |  |  | S |
| VALOR | Valor retornado | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcertoCarga / [TGFACR](TGFACR.md) | OrdemCarga / [TGFORD](TGFORD.md) | CODEMP->CODEMP; ORDEMCARGA->ORDEMCARGA | I |
| AcertoCarga / [TGFACR](TGFACR.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
