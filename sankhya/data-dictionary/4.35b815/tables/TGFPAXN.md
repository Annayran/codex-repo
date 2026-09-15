# TGFPAXN — Acesso ao XML da NF-e/CT-e

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParceiroAutorizacaoXml | Acesso ao XML da NF-e/CTe | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiros | I |  |  | N |
| CODPARXN | Parceiros | I |  |  | N |
| CPFCNPJ | CPF/CNPJ autorizado | S |  |  | N |
| NOMEPARC | Nome do parceiro autorizado | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPOENVIO | Tipo de envio | S |  |  | N |
| TIPOPARCEIRO | Enviar autorizador quando | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Parceiro / [TGFPAR](TGFPAR.md) | ParceiroAutorizacaoXml / [TGFPAXN](TGFPAXN.md) | CODPARC->CODPARC | I |
| ParceiroAutorizacaoXml / [TGFPAXN](TGFPAXN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARXN->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
