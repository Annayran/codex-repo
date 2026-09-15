# TGFCCO — Configuracao de Conferência

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 55
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConfiguracaoConferencia | Configuração da Conferência | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABRANGRECONTAGEM | Abrangência da recontagem | S |  |  | N |
| ABRIRCONFERENCIA | Abrir Fila de Conferência ao faturar | S |  |  | N |
| ABRIRNOTAFAT | Abrir nota faturada na finalização da conferência | S |  |  | N |
| AOLIBERAR | Ao liberar | S |  |  | N |
| APRESFILASEMPRE | Sempre apresentar na fila de conferência | S |  |  | N |
| BUSCARCODBARRAPOR | Buscar código de barras por | S |  |  | N |
| CONFPORSERIE | Conferência por série | S |  |  | N |
| CONSIDERAESTCONF | Ao faturar, considerar estoque conferido | S |  |  | N |
| CORTECONF | Corte/divergência | S |  |  | N |
| CORTEPARCIAL | Corte parcial de itens | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| EXIBIRALERTASONORO | Emitir alerta sonoro em mensagens de divergência | S |  |  | N |
| EXIBIRCODBARRAS | Exibe código de barras dos produtos do pedido | S |  |  | N |
| EXIBIRIMGPROD | Exibir imagem do produto | S |  |  | N |
| EXIBIRPROD | Exibir produtos do pedido | S |  |  | N |
| EXIBIRPRODCONF | Exibir produtos conferidos | S |  |  | N |
| EXIBIRPRODDIVER | Exibir produtos divergentes na recontagem | S |  |  | N |
| EXIBIRQTD | Exibir quantidade do pedido | S |  |  | N |
| EXIBIRQTDCONF | Exibir quantidade conferida | S |  |  | N |
| EXIGEIDENTIFPROD | Exige identificador do produto | S |  |  | N |
| EXPLODIRLOTE | Detalhar lote pela conferência | S |  |  | N |
| FATAOCONCLUIR | Faturar ao concluir conferência | S |  |  | N |
| FEEDBACKAUTOMATICO | Exibir divergências ao conferir | S |  |  | N |
| FORMACAOVOLUMES | Formação de volumes | S |  |  | N |
| GERARPEDCOMPL | Procedimento de divergência a maior | S |  |  | N |
| IGNORACOMPONENTKIT | Ignorar componentes de Kit | S |  |  | N |
| IGNORACONTROLELOTE | Ignorar controle do tipo lote | S |  |  | N |
| IGNORAMSGCONF | Inibir mensagem de confirmação ao finalizar conferência | S |  |  | N |
| IMPDANFEVOL | Imprimir um DANFE por volume | S |  |  | N |
| IMPETIQVOL | Imprimir uma etiqueta por volume | S |  |  | N |
| IMPREETIQUETAS | Imprimir etiquetas ao finalizar conferência | S |  |  | N |
| LIBCORTE | Liberação p/ corte/divergência | S |  |  | N |
| MODNOTADEVOLUCAO | Modelo nota p/ devolução | I |  |  | N |
| MODNOTAPEDCOMPL | Modelo nota p/ pedido complementar | I |  |  | N |
| MOMENTOCONFERENCIA | Momento da conferência | S |  |  | N |
| MULTENTREGAS | Aceita múltiplas entregas | S |  |  | N |
| NOTADEVCONFIRMADA | Nota de devolução confirmada | S |  |  | N |
| NUCCO | Nro único | I |  |  | N |
| NUETIQPESO | Modelo p/ etiqueta | I |  |  | N |
| NURFEETIQ | Modelo p/ etiqueta | I |  |  | N |
| OBTERQTDBALANCA | Obter quantidade pela balança | S |  |  | N |
| PEDCOMPLCONFIRMADO | Ped. complementar confirmado | S |  |  | N |
| PROCEDCORTE | Procedimento de corte | S |  |  | N |
| PRODUTOSFORAPED | Produtos não constantes no pedido | S |  |  | N |
| PROIBEALTERARDOCUMENTO | Proibe alt. documento em conferência | S |  |  | N |
| QTDAMAIOR | Quantidade a maior | S |  |  | N |
| QTDMINRECONT | Recontagens p/ permitir corte/divergência | I |  |  | N |
| REGPESOTOTAL | Registrar peso | S |  |  | N |
| SEPSEQCODBAR | Separador | S |  |  | N |
| SOLRECNOCORTE | Ao efetuar corte pela Central/Portal | S |  |  | N |
| TIPOCONTAGEM | Tipo de contagem | S |  |  | N |
| TIPOLIBERACAO | Tipo de liberação | S |  |  | N |
| TIPORECONTAGEM | Recontagem | S |  |  | N |
| USASEQCODBAR | Usar seq. lote no cód. barras | S |  |  | N |
| VALLOTCONEST | Valida lote conforme Estoque | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoConferencia / [TGFCCO](TGFCCO.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | MODNOTADEVOLUCAO->NUNOTA | I |
| ConfiguracaoConferencia / [TGFCCO](TGFCCO.md) | CabeçalhoNotaModelo2 / [TGFCAB](TGFCAB.md) | MODNOTAPEDCOMPL->NUNOTA | I |
| ConfiguracaoConferencia / [TGFCCO](TGFCCO.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFEETIQ->NURFE | I |
| ConfiguracaoConferencia / [TGFCCO](TGFCCO.md) | RelatorioEtiquetaPeso / [TSIRFE](TSIRFE.md) | NUETIQPESO->NURFE | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | ConfiguracaoConferencia / [TGFCCO](TGFCCO.md) | NUCCO->NUCCO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
