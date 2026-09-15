# TFXPDV — FOX-TFXPDV

- Módulo técnico: [TFX](../modules/TFX.md)
- Campos: 19
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CadastroDePdv | Cadastro de PDV | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APRESENTAIMGPRODUTO | Apresenta Imagem Produto | S |  |  | N |
| ASSINATURA | Assinatura | S |  |  | N |
| CODEMPRESA | Cód.Empresa | I |  |  | N |
| CODPDV | Cód.Pdv | I |  |  | N |
| CONFIGURADO | Configurado | S |  |  | N |
| CONTINGENCIASAT | ContigenciaSAT | S |  |  | N |
| CSCH | CSC Homologação | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| HARDWAREINFO | Informações de Hardware | C |  |  | N |
| HOMOLOGACAO | Homologação | S |  |  | N |
| IDTOKENH | Id.Token Homologação | S |  |  | N |
| IMPRESSORADEFAULT | Impressora Default | S |  |  | N |
| INTEGRAPARCEIRO | Integra Parceiro | S |  |  | N |
| IP | IP | S |  |  | N |
| NUVERSAO | NUVERSAO | I |  |  | N |
| SATDEFAULT | Sat.Default | S |  |  | N |
| SERIE | Serie | S |  |  | N |
| TIPOEQPFISCAL | Tp.Equipamento Fiscal | I |  |  | N |
| TIPOTEF | Tipo TEF | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CadastroBalancasPdv / [TFXBLC](TFXBLC.md) | CadastroDePdv / [TFXPDV](TFXPDV.md) | CODPDV->CODPDV | I |
| CadastroContingenciaPdv / [TFXCNF](TFXCNF.md) | CadastroDePdv / [TFXPDV](TFXPDV.md) | CODPDV->CODPDV | I |
| CadastroDePdv / [TFXPDV](TFXPDV.md) | CadastroSatPdv / [TFXSAT](TFXSAT.md) | SATDEFAULT->CODSAT | I |
| CadastroDePdv / [TFXPDV](TFXPDV.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPRESA->CODEMP | I |
| CadastroDePdv / [TFXPDV](TFXPDV.md) | ImpressorasPdv / [TFXIMP](TFXIMP.md) | IMPRESSORADEFAULT->CODIMP | I |
| CadastroSatPdv / [TFXSAT](TFXSAT.md) | CadastroDePdv / [TFXPDV](TFXPDV.md) | CODPDV->CODPDV | I |
| ImpressorasPdv / [TFXIMP](TFXIMP.md) | CadastroDePdv / [TFXPDV](TFXPDV.md) | CODPDV->CODPDV | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
