# TGFIDI — Declaração de Importação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 22
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DeclaracaoImportacao | Declaração de Importação - DI | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CNPJADQUIRENTE | CNPJ/CPF do Adquirente | S |  |  | N |
| CODEXPORTADOR | Código do Exportador | S |  |  | N |
| CODUFDESEMB | UF Desembaraço | I |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| DOCIMP | Tipo | I |  |  | N |
| DTDESEMBARACO | Dt. Desembaraço | H |  |  | N |
| DTPAGCOFINS | Dt. Pagto. COFINS | H |  |  | N |
| DTPAGPIS | Dt. Pagto. PIS | H |  |  | N |
| DTREGISTRO | Dt. Registro | H |  |  | N |
| LOCDESEMBARACO | Local Desembaraço | S |  |  | N |
| NRODOCUMENTO | Nro. Doc. Importação | S |  |  | N |
| NUMACDRAW | Nro. Ato Conc. regime DrawBack | S |  |  | N |
| NUNOTA | NUNOTA | I |  |  | N |
| SEQDI | Seq. DI | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| TIPPROCIMP | Tipo Processo de Importação | S |  |  | N |
| UFADQUIRENTE | UF do Adquirente | S |  |  | N |
| VIATRANSP | Tipo de Transporte | S |  |  | N |
| VLRAFRMM | Vlr AFRMM | F |  |  | N |
| VLRCOFINSIMP | Vlr. COFINS Importação | F |  |  | N |
| VLRPISIMP | Vlr. PIS Importação | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DeclaracaoImportacao / [TGFIDI](TGFIDI.md) | AdicoesImportacao / [TGFIAD](TGFIAD.md) | NUNOTA->NUNOTA; SEQDI->SEQDI; SEQUENCIA->SEQUENCIA | I |
| DeclaracaoImportacao / [TGFIDI](TGFIDI.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUFDESEMB->CODUF | I |
| ImpostosImportacao / [TGFIII](TGFIII.md) | DeclaracaoImportacao / [TGFIDI](TGFIDI.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
