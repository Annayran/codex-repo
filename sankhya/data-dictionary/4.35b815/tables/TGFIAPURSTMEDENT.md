# TGFIAPURSTMEDENT — Item Apuracao STAnterior PorMedia

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemApuracaoSTAnteriorPorMedia | Item Apuracao STAnterior PorMedia | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Cód.Empresa | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| NUAPUR | Código | I |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemApuracaoSTAnteriorPorMedia / [TGFIAPURSTMEDENT](TGFIAPURSTMEDENT.md) | CabecalhoApurSTAnt / [TGFAPURSTMEDENT](TGFAPURSTMEDENT.md) | NUAPUR->NUAPUR | I |
| ItemApuracaoSTAnteriorPorMedia / [TGFIAPURSTMEDENT](TGFIAPURSTMEDENT.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ItemApuracaoSTAnteriorPorMedia / [TGFIAPURSTMEDENT](TGFIAPURSTMEDENT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
