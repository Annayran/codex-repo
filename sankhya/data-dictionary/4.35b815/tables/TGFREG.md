# TGFREG — Regras

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Regras | Regras | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODREGRA | Regra | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRREGRA | Descrição | S |  |  | N |
| DTALTER | Data da Alteração | H |  |  | N |
| INSTPRINC | Instância Principal | S |  |  | N |
| INSTSEC | Instância Secundária | S |  |  | N |
| TIPO | Permissão | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CertificacaoRegra / [TGFCER](TGFCER.md) | Regras / [TGFREG](TGFREG.md) | CODREGRA->CODREGRA | I |
| ItemRegras / [TGFITR](TGFITR.md) | Regras / [TGFREG](TGFREG.md) | CODREGRA->CODREGRA | I |
| Regras / [TGFREG](TGFREG.md) | ItemRegras / [TGFITR](TGFITR.md) | CODREGRA->CODREGRA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
