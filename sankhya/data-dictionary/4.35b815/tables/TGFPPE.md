# TGFPPE — Desenvolvimento da Produção Controle das Entradas Existentes

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DesenvolvProdContEntExistentes | Desenvolvimento da Produção Controle das Entradas Existentes | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODLOCAL | Local do Produto | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CONTROLE | Controle do Produto | S |  |  | N |
| DTMOV | Data do Movimento | H |  |  | N |
| ENTRADA | Quantidade de Entradas | F |  |  | N |
| SOMADO | Controle do Planejamento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DesenvolvProdContEntExistentes / [TGFPPE](TGFPPE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
