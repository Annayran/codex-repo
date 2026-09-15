# TGFTIE — Tributação de cupom fiscal

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
| TributacaoCupomFiscal | Tributação de cupom fiscal | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQUOTA | ALIQUOTA | F |  |  | N |
| ALIQUOTARED | ALIQUOTARED | F |  |  | N |
| CODMAQ | CODMAQ | I |  |  | N |
| INDTRIB | INDTRIB | I |  |  | N |
| TIPOTRIB | TIPOTRIB | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TributacaoCupomFiscal / [TGFTIE](TGFTIE.md) | CadastroMaquina / [TGFMAQ](TGFMAQ.md) | CODMAQ->CODMAQ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
