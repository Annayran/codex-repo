# TGFPMA — Marca por parceiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MarcaParceiro | Marca por Parceiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data alteração | H |  |  | N |
| MARCA | Marca | S |  |  | N |
| PERCDESC | % Desconto | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MarcaParceiro / [TGFPMA](TGFPMA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Parceiro / [TGFPAR](TGFPAR.md) | MarcaParceiro / [TGFPMA](TGFPMA.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
