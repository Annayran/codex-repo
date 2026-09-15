# TIMIRB — Beneficiarios dos proprietários dos imóveis

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimBeneficiario | Beneficiários | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IRBBENEFICIARIO | Beneficiário | I |  |  | N |
| IRBCODTIPTIT | Tipo título p/ repasse | I |  |  | N |
| IRBCONTRATO | Contrato | I |  |  | N |
| IRBIMOVEL | Imóvel | I |  |  | N |
| IRBPERCENTUAL | Percentual que tem direito | F |  |  | N |
| IRBPROPIMOVEL | Proprietário do imóvel | I |  |  | N |
| IRBTIPOREPASSE | Tipo de repasse | S |  |  | N |
| IRBVLRREPASSE | Valor fixo para repasse | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimBeneficiario / [TIMIRB](TIMIRB.md) | Contato / [TGFCTT](TGFCTT.md) | IRBBENEFICIARIO->CODCONTATO; IRBPROPIMOVEL->CODPARC | I |
| TimBeneficiario / [TIMIRB](TIMIRB.md) | Parceiro / [TGFPAR](TGFPAR.md) | IRBPROPIMOVEL->CODPARC | I |
| TimBeneficiario / [TIMIRB](TIMIRB.md) | TimImovel / [TIMIMV](TIMIMV.md) | IRBIMOVEL->IMVCODIGO | I |
| TimBeneficiario / [TIMIRB](TIMIRB.md) | TimProprietario / [TIMIMP](TIMIMP.md) | IRBCONTRATO->IMPCONTRATO; IRBPROPIMOVEL->IMPPROPRIETARIO | I |
| TimBeneficiario / [TIMIRB](TIMIRB.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | IRBCODTIPTIT->CODTIPTIT | I |
| TimProprietario / [TIMIMP](TIMIMP.md) | TimBeneficiario / [TIMIRB](TIMIRB.md) | IMPCONTRATO->IRBCONTRATO; IMPIMOVEL->IRBIMOVEL; IMPPROPRIETARIO->IRBPROPIMOVEL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
