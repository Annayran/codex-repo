# TGFNFCOMNT — Nota Técnica NFe

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NotaTecnicaNFCom | Nota Técnica NFCom | S | N |

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
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NotaTecnicaNFCom / [TGFNFCOMNT](TGFNFCOMNT.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
