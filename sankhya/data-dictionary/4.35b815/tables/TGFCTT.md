# TGFCTT — Contatos dos Parceiros

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 52
- Instâncias DWF: 3
- Relações DWF: 42
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Contato | Contato | S | S |
| ContatoEntrega | Contato de Entrega | S | N |
| Implantador | Implantador | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APELIDO | Apelido | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CARGO | Cargo | S |  |  | N |
| CELULAR | Celular | S |  |  | N |
| CEP | CEP | S |  |  | N |
| CNPJ | CNPJ | S |  |  | N |
| CODBAI | Bairro | I |  |  | N |
| CODCID | Cód. Cidade | I |  |  | N |
| CODCONTATO | Contato | I |  |  | N |
| CODEND | Endereço | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODREG | Cód. Região | I |  |  | N |
| CODUSU | Cód. Usuário B2B | I |  |  | N |
| CODUSUALT | Cód. Usuário Alteração | I |  |  | N |
| COMPLEMENTO | Complemento | S |  |  | N |
| CPF | CPF | S |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| DTCAD | Data Cadastro | H |  |  | N |
| DTNASC | Data Nascimento | D |  |  | N |
| EMAIL | Email | S |  |  | N |
| ENVIANOTIFCOTA | Enviar notificações de cotação? | S |  |  | N |
| FAX | Fax | S |  |  | N |
| HABPLANENTCESTAS | Participa plan. entrega cesta | S |  |  | N |
| INSCESTAD | Inscrição Estadual | S |  |  | N |
| LATITUDE | Latitude | S |  |  | N |
| LONGITUDE | Longitude | S |  |  | N |
| NIVELCOB | Nível p/cobrança | I |  |  | N |
| NOMECONTATO | Nome Contato | S |  |  | N |
| NUMEND | Número | S |  |  | N |
| POSSUIACESSOBT | Possui Acesso Banco Talentos | S |  |  | N |
| PRIORIDADE | Prioridade | I |  |  | N |
| QTDENTREGACESTAS | Quantidade Cestas | I |  |  | N |
| RAMAL | Ramal | I |  |  | N |
| RECEBEBOLETOEMAIL | Recebe boleto/Pix p/ email | S |  |  | N |
| RECEBENOTAEMAIL | Recebe Nota por email | S |  |  | N |
| RECEMAILIMPPED | Recebe e-mails de importação de pedidos | S |  |  | N |
| RESPCOBRANCA | Responsável pela cobrança | S |  |  | N |
| SENHAACESSO | Senha de acesso | S |  |  | N |
| SOCIO | Sócio | S |  |  | N |
| TELEFONE | Telefone | S |  |  | N |
| TELRESID | Telefone Residencial | S |  |  | N |
| TIMAGENCIA | Agência | S |  |  | N |
| TIMBANCO | Banco | I |  |  | N |
| TIMBENEFICIARIO | Benef. Repasse | S |  |  | N |
| TIMCONTA | Conta | S |  |  | N |
| TIMNACIONALIDAD | Nacionalidade | I |  |  | N |
| TIMPROCURADOR | Procurador | I |  |  | N |
| TIMPROFISSAO | Profissão | I |  |  | N |
| TIMREPRESENTANTE | Representante | S |  |  | N |
| TIMRG | RG | S |  |  | N |
| TIMTIPO | Tipo de Conta | I |  |  | N |
| TIPPESSOA | Tipo de pessoa | S |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ContatoEntrega / [TGFCTT](TGFCTT.md) | CODCONTATOENTREGA->CODCONTATO; CODPARC->CODPARC | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARCFAT->CODPARC | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Contato / [TGFCTT](TGFCTT.md) | CODPARC->CODPARC; CODCONTATO->CODCONTATO | I |
| ColetaItemCotacao / [TGFITC](TGFITC.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| Contato / [TGFCTT](TGFCTT.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| Contato / [TGFCTT](TGFCTT.md) | Banco / [TSIBCO](TSIBCO.md) | TIMBANCO->CODBCO | I |
| Contato / [TGFCTT](TGFCTT.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Contato / [TGFCTT](TGFCTT.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| Contato / [TGFCTT](TGFCTT.md) | Pais / [TSIPAI](TSIPAI.md) | TIMNACIONALIDAD->CODPAIS | I |
| Contato / [TGFCTT](TGFCTT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Contato / [TGFCTT](TGFCTT.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | TIMPROCURADOR->CODPARC | I |
| Contato / [TGFCTT](TGFCTT.md) | PerfilContato / [TGFPPA](TGFPPA.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | L |
| Contato / [TGFCTT](TGFCTT.md) | ProdutoContato / [TGFCTTPRO](TGFCTTPRO.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| Contato / [TGFCTT](TGFCTT.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| Contato / [TGFCTT](TGFCTT.md) | TimProfissaoRamoAtividade / [TIMPRF](TIMPRF.md) | TIMPROFISSAO->PRFCODIGO | I |
| Contato / [TGFCTT](TGFCTT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Contrato / [TCSCON](TCSCON.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| Contrato / [TCSCON](TCSCON.md) | Implantador / [TGFCTT](TGFCTT.md) | CODIMPLANT->CODCONTATO; CODPARC->CODPARC | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| MSDDestinatario / [TMDCON](TMDCON.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONT->CODCONTATO; CODPARC->CODPARC | I |
| OcorrenciaContrato / [TCSOCC](TCSOCC.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | L |
| Parceiro / [TGFPAR](TGFPAR.md) | Contato / [TGFCTT](TGFCTT.md) | CODPARC->CODPARC | L |
| Parceiro / [TGFPAR](TGFPAR.md) | ContatoEntrega / [TGFCTT](TGFCTT.md) | CODPARC->CODPARC; CODCONTATOPADCOT->CODCONTATO | I |
| PerfilContato / [TGFPPA](TGFPPA.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| Relacionamento / [TGFTEL](TGFTEL.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| TimAntigoComprador / [TIMTVP](TIMTVP.md) | Contato / [TGFCTT](TGFCTT.md) | TVPENDERECO->CODCONTATO; TVPPESSOA->CODPARC | I |
| TimBeneficiario / [TIMIRB](TIMIRB.md) | Contato / [TGFCTT](TGFCTT.md) | IRBBENEFICIARIO->CODCONTATO; IRBPROPIMOVEL->CODPARC | I |
| TimCompradorLote / [TIMLVC](TIMLVC.md) | Contato / [TGFCTT](TGFCTT.md) | LVCCLIENTE->CODPARC; LVCENDERECO->CODCONTATO | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | Contato / [TGFCTT](TGFCTT.md) | DTLPARCEIRO->CODPARC; DTLCONTATO->CODCONTATO | I |
| TimNovoComprador / [TIMTNP](TIMTNP.md) | Contato / [TGFCTT](TGFCTT.md) | TNPENDERECO->CODCONTATO; TNPPESSOA->CODPARC | I |
| Usuario / [TSIUSU](TSIUSU.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATOPERFIL->CODCONTATO; CODPARCPERFIL->CODPARC | I |
| Verbas / [TGFGEV](TGFGEV.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONT->CODCONTATO; CODPARC->CODPARC | L |
| ViewCabPlanejamentoEntrega / [VGFPLAC](VGFPLAC.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| ViewPerfilContatoParceiro / [VGFPCP](VGFPCP.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
