# TIMIMV — Imóveis

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 112
- Instâncias DWF: 2
- Relações DWF: 40
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimImovel | Imóvel | S | N |
| TimImovelPesquisa | Imóveis | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IMVANDAR | Andar | I |  |  | N |
| IMVANUNCIOJORNAL | Anúncios Jornal | S |  |  | N |
| IMVAPTO | Apartamento | I |  |  | N |
| IMVAREACONSTRUIDA | Área construída (m²) | F |  |  | N |
| IMVAREAGARAGEM | Área garagem (m²) | F |  |  | N |
| IMVAREAINTERNA | Área Interna (m²) | F |  |  | N |
| IMVAREAPRIVTOTAL | Área privativa interna (m²) | F |  |  | N |
| IMVASSINATURA | Assinatura Anúncio | S |  |  | N |
| IMVATIVO | Ativo | S |  |  | N |
| IMVAVLCONSTRUCAO | Vlr. avaliação construção | I |  |  | N |
| IMVBAIRRO | Bairro | I |  |  | N |
| IMVCAPTADORLOC | Captador de Locação | I |  |  | S |
| IMVCAPTADORVENDA | Captador para Venda | I |  |  | S |
| IMVCARTDTREGISTRO | Data de Registro no Cartório | D |  |  | N |
| IMVCARTFOLHA | Folha do Livro de Registro | S |  |  | N |
| IMVCARTLIVRO | Livro de Registro no Cartório | S |  |  | N |
| IMVCARTMATRICULA | Matrícula do Imóvel | S |  |  | N |
| IMVCARTORIO | Cartório de Registro | I |  |  | N |
| IMVCARTREGISTRO | Registro do Imóvel | S |  |  | N |
| IMVCEP | CEP | S |  |  | N |
| IMVCHAVES | Chaves | S |  |  | N |
| IMVCIDADE | Cidade | I |  |  | N |
| IMVCODEND | Endereço | I |  |  | N |
| IMVCODIGO | Código | I |  |  | N |
| IMVCODUSUINC | Usuário Inclusão | I |  |  | N |
| IMVCOMPLEMENTO | Complemento | S |  |  | N |
| IMVCONDFECHADO | Condomínio Fechado | S |  |  | N |
| IMVCOZINHA | Cozinha | S |  |  | N |
| IMVDATAALTERACAO | Dh. Alteração | H |  |  | N |
| IMVDATAENVREDE | Dt. Envio Rede Imobiliária | D |  |  | N |
| IMVDESCRICAO | Descrição | S |  |  | N |
| IMVDESCRICAOATUAL | Descrição Completa | S |  |  | N |
| IMVDFTABELADEPRECOS | Tabela de preços | I |  |  | N |
| IMVDFVLCONDOMINIO | Vlr. Condomínio | F |  |  | N |
| IMVDHINC | Dh. Inclusão | H |  |  | N |
| IMVDOCUMENTACAO | Documentação | I |  |  | N |
| IMVDTFIMRESERVA | Fim Reserva | D |  |  | N |
| IMVDTPROXCONTATO | Próximo Contato | D |  |  | N |
| IMVDTVISITA | Dt. Visita | D |  |  | N |
| IMVEDICULA | Edícula | S |  |  | N |
| IMVEDIFICIO | Edifício | I |  |  | N |
| IMVEMPREENDIMENTO | Empreendimento | I |  |  | N |
| IMVENDERECO | Endereço | S |  |  | N |
| IMVENDFINAL | Endereço | S |  |  | S |
| IMVESTADO | Estado | S |  |  | N |
| IMVESTAGIO | Estágio | S |  |  | N |
| IMVESTATISTICA | Influência em Indicadores | S |  |  | N |
| IMVEXIBECOMDOMINIOSITE | Exibe Preço Condomínio no Site | S |  |  | N |
| IMVEXIBEPRECOSITE | Exibe Preço no Site | S |  |  | N |
| IMVFACCHAVE | Fac Solicitação Chave | I |  |  | N |
| IMVFRACAOIDEAL | Fração Ideal | S |  |  | N |
| IMVGARAGEMCOBERTA | Garagens Cobertas | I |  |  | N |
| IMVGARAGEMDESCOBERTA | Garagens Descobertas | I |  |  | N |
| IMVGARPARQTD | Qtd. Garagens | I |  |  | N |
| IMVIFEANO | Ano Construção | I |  |  | N |
| IMVIFEAREACONSTRUIDA | Área Construída (m²) | F |  |  | N |
| IMVIFEAREAPRIVATIVA | Área Privativa (m²) | F |  |  | N |
| IMVIFEAREATERRENO | Área Terreno (m²) | F |  |  | N |
| IMVIFEASFALTO | Asfalto | S |  |  | N |
| IMVIFEDIVARMCOZINHA | Armários Cozinha | S |  |  | N |
| IMVIFEDIVBANHEIROS | Banheiros | I |  |  | N |
| IMVIFEDIVBOXGARAGEM | Box Garagens | S |  |  | N |
| IMVIFEDIVCHURRASQEIRA | Churrasqueira | S |  |  | N |
| IMVIFEDIVEMPREGADA | Dep. Empregada | S |  |  | N |
| IMVIFEDIVESTADO | Estado Conservação | S |  |  | N |
| IMVIFEDIVMASTER | Suíte Master | I |  |  | N |
| IMVIFEDIVPISCINA | Piscina | S |  |  | N |
| IMVIFEDIVQTCOMARMARIO | Quartos com armário | I |  |  | N |
| IMVIFEDIVQTCOMARMARIOS | Quartos com armários | I |  |  | N |
| IMVIFEDIVQUARTOS | Quartos | I |  |  | N |
| IMVIFEDIVREVERSIVEL | Quartos Reversíveis | I |  |  | N |
| IMVIFEDIVSALAS | Salas | I |  |  | N |
| IMVIFEDIVSALATV | Sala de TV / Home Teather | S |  |  | N |
| IMVIFEDIVSUITES | Suítes | I |  |  | N |
| IMVIFEDIVVAGASGARAGEM | Vagas Garagem | I |  |  | N |
| IMVIFEESGOTO | Esgoto | S |  |  | N |
| IMVIFEESQUINA | Esquina | S |  |  | N |
| IMVIFEFORRO | Forro | S |  |  | N |
| IMVIFEFRENTE | Frente | S |  |  | N |
| IMVIFEGABARITO | Gabarito | S |  |  | N |
| IMVIFEGRADE | Grade | S |  |  | N |
| IMVIFELADO | Lado | S |  |  | N |
| IMVIFEMURO | Muro | S |  |  | N |
| IMVIFEPISOS | Pisos | S |  |  | N |
| IMVIFEPORTAO | Portão | S |  |  | N |
| IMVIFESOL | Sol | S |  |  | N |
| IMVINTENDERECO | Divulgar Endereço | S |  |  | N |
| IMVINTMAPA | Mostrar Mapa | S |  |  | N |
| IMVLATITUDEGPS | Latitude | F |  |  | N |
| IMVLEMBRETE | Lembrete de Problema | S |  |  | N |
| IMVLOCALCHAVES | Local Chaves | S |  |  | N |
| IMVLONGITUDEGPS | Longitude | F |  |  | N |
| IMVMOBILIADO | Mobiliado | S |  |  | N |
| IMVNUMERO | Número | S |  |  | N |
| IMVOBSERVACAO | Ponto Referência | S |  |  | N |
| IMVOCUPACAO | Ocupação | I |  |  | N |
| IMVPASTA | Pasta Física | I |  |  | N |
| IMVPORTARIA | Portaria | S |  |  | N |
| IMVQUINTAL | Quintal | S |  |  | N |
| IMVREGISTROIPTU | Registro IPTU | S |  |  | N |
| IMVTIPODOIMOVEL | Tipo Imóvel | I |  |  | N |
| IMVULTADM | Contrato Adm. Locação | I |  |  | N |
| IMVULTADMVENDA | Contrato Adm. Venda | I |  |  | N |
| IMVULTANU | Último Anun. | I |  |  | N |
| IMVUNIDADEMEDIDA | Unidade de medida | I |  |  | N |
| IMVURLGOOGLEMAP | URL Google Map | S |  |  | N |
| IMVUSUARIO | Usuário Alteração | I |  |  | N |
| IMVVISIVELSITE | Publicado no site | S |  |  | N |
| IMVVLRALUGUEL | Vlr. Aluguel | F |  |  | S |
| IMVVLRIPTU | Vlr. IPTU | F |  |  | N |
| IMVVLRVENDA | Vlr. Venda | F |  |  | S |
| NUPEV | Caminho FTP | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | TimImovel / [TIMIMV](TIMIMV.md) | TIMIMOVEL->IMVCODIGO | I |
| TimAnuncio / [TIMANU](TIMANU.md) | TimImovel / [TIMIMV](TIMIMV.md) | ANUIMOVEL->IMVCODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimImovel / [TIMIMV](TIMIMV.md) | SACIMOVEL->IMVCODIGO | I |
| TimBeneficiario / [TIMIRB](TIMIRB.md) | TimImovel / [TIMIMV](TIMIMV.md) | IRBIMOVEL->IMVCODIGO | I |
| TimClassificadoAnuncio / [TIMCFI](TIMCFI.md) | TimImovel / [TIMIMV](TIMIMV.md) | CFIIMOVEL->IMVCODIGO | I |
| TimContaControlada / [TIMCIL](TIMCIL.md) | TimImovel / [TIMIMV](TIMIMV.md) | CILIMOVEL->IMVCODIGO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimImovel / [TIMIMV](TIMIMV.md) | ADMIMOVEL->IMVCODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimImovel / [TIMIMV](TIMIMV.md) | CLAIMOVEL->IMVCODIGO | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimImovel / [TIMIMV](TIMIMV.md) | DTLIMOVEL->IMVCODIGO | I |
| TimEdificioGaleria / [TIMEDG](TIMEDG.md) | TimImovel / [TIMIMV](TIMIMV.md) | EDGCODIGO->IMVEDIFICIO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | Bairro / [TSIBAI](TSIBAI.md) | IMVBAIRRO->CODBAI | I |
| TimImovel / [TIMIMV](TIMIMV.md) | Cidade / [TSICID](TSICID.md) | IMVCIDADE->CODCID | I |
| TimImovel / [TIMIMV](TIMIMV.md) | Endereco / [TSIEND](TSIEND.md) | IMVCODEND->CODEND | I |
| TimImovel / [TIMIMV](TIMIMV.md) | PedidoPorEvento / [TGFPEV](TGFPEV.md) | NUPEV->NUPEV | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimAnuncio / [TIMANU](TIMANU.md) | IMVCODIGO->ANUIMOVEL | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimCaracterizacaoObjeto / [TIMIEO](TIMIEO.md) | IMVCODIGO->IEOIMOVEL | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimCartorio / [TIMCAR](TIMCAR.md) | IMVCARTORIO->CARCODIGO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimCorretor / [TIMCOR](TIMCOR.md) | IMVCAPTADORVENDA->CORCODIGO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimCorretor2 / [TIMCOR](TIMCOR.md) | IMVCAPTADORLOC->CORCODIGO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimEdificioGaleria / [TIMEDG](TIMEDG.md) | IMVEDIFICIO->EDGCODIGO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | IMVEMPREENDIMENTO->EPRCODIGO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimImovelConta / [TIMCIM](TIMCIM.md) | IMVCODIGO->CIMIMOVEL | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimImovelLugarCerto / [TIMILC](TIMILC.md) | IMVCODIGO->ILCIMOVEL | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimImovelTipo / [TIMTPI](TIMTPI.md) | IMVTIPODOIMOVEL->TPICODIGO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimProprietario / [TIMIMP](TIMIMP.md) | IMVCODIGO->IMPIMOVEL | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | IMVUSUARIO->CODUSU | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | IMVCODUSUINC->CODUSU | I |
| TimImovel / [TIMIMV](TIMIMV.md) | Usuario / [TSIUSU](TSIUSU.md) | IMVUSUARIO->CODUSU | I |
| TimImovelChaves / [TIMCHV](TIMCHV.md) | TimImovel / [TIMIMV](TIMIMV.md) | IMVCODIGO->IMVCODIGO | I |
| TimImovelConta / [TIMCIM](TIMCIM.md) | TimImovel / [TIMIMV](TIMIMV.md) | CIMIMOVEL->IMVCODIGO | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | TimImovel / [TIMIMV](TIMIMV.md) | IPRIMOVEL->IMVCODIGO | I |
| TimImovelFilaChaves / [TIMFILCHV](TIMFILCHV.md) | TimImovel / [TIMIMV](TIMIMV.md) | FILIMOVEL->IMVCODIGO | I |
| TimImovelPlano / [TIMTIP](TIMTIP.md) | TimImovel / [TIMIMV](TIMIMV.md) | TIPIMOVEL->IMVCODIGO | I |
| TimIptu / [TIMIPT](TIMIPT.md) | TimImovel / [TIMIMV](TIMIMV.md) | IPTIMOVEL->IMVCODIGO | I |
| TimIptuImovel / [TIMIVI](TIMIVI.md) | TimImovel / [TIMIMV](TIMIMV.md) | IVIIMOVEL->IMVCODIGO | I |
| TimIptuImovelParcela / [TIMPIP](TIMPIP.md) | TimImovel / [TIMIMV](TIMIMV.md) | PIPIMOVEL->IMVCODIGO | I |
| TimIptuReajusteImovel / [TIMRIC](TIMRIC.md) | TimImovel / [TIMIMV](TIMIMV.md) | RICIMOVEL->IMVCODIGO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimImovel / [TIMIMV](TIMIMV.md) | LOCIMOVEL->IMVCODIGO | I |
| TimLocacaoImovelCaucao / [TIMLIC](TIMLIC.md) | TimImovel / [TIMIMV](TIMIMV.md) | LICIMOVEL->IMVCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimImovel / [TIMIMV](TIMIMV.md) | VDOIMOVEL->IMVCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
