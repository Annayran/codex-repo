# TCICTE — Inventário de Bens

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InventarioBem | Inventário de Bens | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AJUSTADO | Ajustado | S |  |  | N |
| CODBEM | Código do Bem | S |  |  | N |
| CODDEPCP | Departamento da Cópia | I |  |  | N |
| CODDEPCT | Departamento da Contagem | I |  |  | N |
| CODDEPSIS | Departamento no Sistema | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODEMPSIS | Empresa no Sistema | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| DTINVENT | Data Inventário | D |  |  | N |
| EXISTENACP | Existe na Cópia | S |  |  | N |
| EXISTENACT | Existe na Contagem | S |  |  | N |
| NUNOTA | Nota da Baixa | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| InventarioBem / [TCICTE](TCICTE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| InventarioBem / [TCICTE](TCICTE.md) | DepartamentoContagem /  | CODDEPCT->CODDEP | I |
| InventarioBem / [TCICTE](TCICTE.md) | DepartamentoCopia /  | CODDEPCP->CODDEP | I |
| InventarioBem / [TCICTE](TCICTE.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| InventarioBem / [TCICTE](TCICTE.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM | I |
| InventarioBem / [TCICTE](TCICTE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
