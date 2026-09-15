# TGFNFENT — Nota Técnica NFe

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NotaTecnicaNFe | Nota Técnica NFe | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVONT | Ativo | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| DTENTHOMOLOG | Data de Homologação | H |  |  | N |
| DTENTPROD | Data de Produção | H |  |  | N |
| VERSAONT | Versão Nota Técnica | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NotaTecnicaNFe / [TGFNFENT](TGFNFENT.md) | CODEMP->CODEMP | I |
| NotaTecnicaNFe / [TGFNFENT](TGFNFENT.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
