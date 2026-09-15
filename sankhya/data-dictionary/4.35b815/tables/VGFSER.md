# VGFSER — View VGFSER

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewSerie | View Serie | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUALESTOQUE | Atual Estoque | I |  |  | N |
| CODEMP | Cód. Empresa | I |  |  | N |
| CODLOCAL | Cód. Local | I |  |  | N |
| CODPROD | Cód. Produto | I |  |  | N |
| SERIE | Série do Produto | S |  |  | N |
| SMARTCARD | SmartCard | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewSerie / [VGFSER](VGFSER.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
