http://192.0.4.112:8180/mgeprod/service.sbr?serviceName=LancamentoOrdemProducaoSP.getNovoLancamentoOP&counter=67&application=OrdensProducao&outputType=json&preventTransform=false&mgeSession=THMtHyOcFZZqLG7Kf6MG-YxTnXeQdwMAlLvRHw5_&resourceID=br.com.sankhya.prod.OrdensProducaoHTML&globalID=D7E4C2BDC08E580E1000FC5555CD274F

{
    "serviceName": "LancamentoOrdemProducaoSP.getNovoLancamentoOP",
    "requestBody": {
        "params": {
            "descricao": "DEFAULT",
            "reutilizar": "N"
        },
        "clientEventList": {
            "clientEvent": [
                {
                    "$": "br.com.sankhya.mgeProd.libera.wc"
                },
                {
                    "$": "br.com.mgeprod.clientEvent.cancelaOP.deleteNotaConfirmada"
                },
                {
                    "$": "br.com.sankhya.actionbutton.clientconfirm"
                }
            ]
        }
    }
}


{
    "serviceName": "LancamentoOrdemProducaoSP.getNovoLancamentoOP",
    "status": "1",
    "pendingPrinting": "false",
    "transactionId": "E7B3988D6DFA6C93C8113BCE4462C141",
    "responseBody": {
        "lancamento": {
            "nulop": "67292",
            "descricao": "DEFAULT",
            "reutilizar": "N",
            "processos": {}
        }
    }
}

LancamentoOP lancamentoOPBean = new LancamentoOP(jdbc);
lancamentoOPBean.setDescricao(descricao);
lancamentoOPBean.setReutilizar(reutilizar);
lancamentoOPBean.persistir();

===
http://192.0.4.112:8180/mgeprod/service.sbr?serviceName=LancamentoOrdemProducaoSP.getProcessoProdutivoParaProduto&counter=83&application=OrdensProducao&outputType=json&preventTransform=false&mgeSession=THMtHyOcFZZqLG7Kf6MG-YxTnXeQdwMAlLvRHw5_&resourceID=br.com.sankhya.prod.OrdensProducaoHTML&globalID=D7E4C2BDC08E580E1000FC5555CD274F

===
{
    "serviceName": "LancamentoOrdemProducaoSP.getProcessoProdutivoParaProduto",
    "requestBody": {
        "params": {
            "codprod": 3108,
            "nulop": "67294",
            "tipop": "P",
            "producaoParaTerc": "N",
            "controle": " ",
            "codplp": 1
        },
        "clientEventList": {
            "clientEvent": [
                {
                    "$": "br.com.sankhya.mgeProd.libera.wc"
                },
                {
                    "$": "br.com.mgeprod.clientEvent.cancelaOP.deleteNotaConfirmada"
                },
                {
                    "$": "br.com.sankhya.actionbutton.clientconfirm"
                }
            ]
        }
    }
}

{
    "serviceName": "LancamentoOrdemProducaoSP.getProcessoProdutivoParaProduto",
    "status": "1",
    "pendingPrinting": "false",
    "transactionId": "F1BECF55F72E1CABB3B0D580F0FC77F0",
    "responseBody": {
        "idProcesso": "388",
        "usaTerceiro": "N",
        "defTerceiro": "O",
        "versao": "2",
        "codProcesso": "400",
        "tipNroLote": "AX",
        "producaoParaTerceiro": "N",
        "exigePedido": "O"
    }
}
===
http://192.0.4.112:8180/mgeprod/service.sbr?serviceName=LancamentoOrdemProducaoSP.getProdutoParaProcesso&counter=84&application=OrdensProducao&outputType=json&preventTransform=false&mgeSession=THMtHyOcFZZqLG7Kf6MG-YxTnXeQdwMAlLvRHw5_&resourceID=br.com.sankhya.prod.OrdensProducaoHTML&globalID=D7E4C2BDC08E580E1000FC5555CD274F

===
{
    "serviceName": "LancamentoOrdemProducaoSP.getProdutoParaProcesso",
    "requestBody": {
        "params": {
            "codprod": 3108,
            "idproc": "388",
            "nulop": "67294",
            "tipop": "P",
            "codPlp": 1,
            "codParc": null,
            "controle": " "
        },
        "clientEventList": {
            "clientEvent": [
                {
                    "$": "br.com.sankhya.mgeProd.libera.wc"
                },
                {
                    "$": "br.com.mgeprod.clientEvent.cancelaOP.deleteNotaConfirmada"
                },
                {
                    "$": "br.com.sankhya.actionbutton.clientconfirm"
                }
            ]
        }
    }
}

{
    "serviceName": "LancamentoOrdemProducaoSP.getProdutoParaProcesso",
    "status": "1",
    "pendingPrinting": "false",
    "transactionId": "D774AC6151D5C3D8435BFCD6A0658FFA",
    "responseBody": {
        "tamLote": "2000.0",
        "multIdeal": "0.0",
        "decQtd": "2.0",
        "minLote": "0.0",
        "tipNroLote": "AX"
    }
}

===
http://192.0.4.112:8180/mgeprod/service.sbr?serviceName=LancamentoOrdemProducaoSP.getProdutoParaProcesso&counter=86&application=OrdensProducao&outputType=json&preventTransform=false&mgeSession=THMtHyOcFZZqLG7Kf6MG-YxTnXeQdwMAlLvRHw5_&resourceID=br.com.sankhya.prod.OrdensProducaoHTML&globalID=D7E4C2BDC08E580E1000FC5555CD274F

{
    "serviceName": "LancamentoOrdemProducaoSP.getProdutoParaProcesso",
    "requestBody": {
        "params": {
            "codprod": 3108,
            "idproc": 388,
            "nulop": "67294",
            "tipop": "P",
            "codPlp": 1,
            "codParc": null,
            "controle": " "
        },
        "clientEventList": {
            "clientEvent": [
                {
                    "$": "br.com.sankhya.mgeProd.libera.wc"
                },
                {
                    "$": "br.com.mgeprod.clientEvent.cancelaOP.deleteNotaConfirmada"
                },
                {
                    "$": "br.com.sankhya.actionbutton.clientconfirm"
                }
            ]
        }
    }
}

{
    "serviceName": "LancamentoOrdemProducaoSP.getProdutoParaProcesso",
    "status": "1",
    "pendingPrinting": "false",
    "transactionId": "48C4F3F48C2783DEDFAD91B7D59FDF15",
    "responseBody": {
        "tamLote": "2000.0",
        "multIdeal": "0.0",
        "decQtd": "2.0",
        "minLote": "0.0",
        "tipNroLote": "AX"
    }
}

LancamentoOPBuilder lancOPBuilder = LancamentoOPBuilder.build(jdbc, nuLop);
DynamicVO produtoAcabado = lancOPBuilder.getProdutoAcabado(codProd, controle, idProc);
DynamicVO produto = lancOPBuilder.getProduto(codProd);
JapeWrapper processoProdutivoDAO = JapeFactory.dao("ProcessoProdutivo");
DynamicVO processoVO = processoProdutivoDAO.findByPK(new Object[] { idProc });
ctx.getBodyElement().setAttribute("tamLote", (new Double(produtoAcabado.asDouble("TAMLOTEPAD"))).toString());
ctx.getBodyElement().setAttribute("multIdeal", (new Double(produtoAcabado.asDouble("MULTIDEAL"))).toString());
ctx.getBodyElement().setAttribute("decQtd", (new Double(produto.asDouble("DECQTD"))).toString());
ctx.getBodyElement().setAttribute("minLote", (new Double(produtoAcabado.asDouble("QTDPRODMIN"))).toString());
ctx.getBodyElement().setAttribute("controlePA", produtoAcabado.asString("CONTROLEPA"));
ctx.getBodyElement().setAttribute("codParcTerc", (codParc != null) ? codParc.toString() : "");
ctx.getBodyElement().setAttribute("loteCuringa", (processoVO.asString("LOTECURINGA") != null) ? processoVO.asString("LOTECURINGA") : "");
ctx.getBodyElement().setAttribute("tipNroLote", processoVO.asString("TIPONROLOTE"));


===
http://192.0.4.112:8180/mgeprod/service.sbr?serviceName=LancamentoOrdemProducaoSP.inserirProdutoHTML5&counter=120&application=OrdensProducao&outputType=json&preventTransform=false&mgeSession=THMtHyOcFZZqLG7Kf6MG-YxTnXeQdwMAlLvRHw5_&resourceID=br.com.sankhya.prod.OrdensProducaoHTML&globalID=D7E4C2BDC08E580E1000FC5555CD274F

{
    "serviceName": "LancamentoOrdemProducaoSP.inserirProdutoHTML5",
    "requestBody": {
        "params": {
            "nulop": "67295",
            "codplp": 1,
            "codprod": 3108,
            "idproc": 388,
            "tamlote": "2000.0",
            "oldTamLote": "2000.0",
            "agruparEmUnicaOP": false,
            "opDesmonte": "N",
            "opReparo": "N",
            "multIdeal": "0.0",
            "minLote": "0.0",
            "controle": {}
        },
        "clientEventList": {
            "clientEvent": [
                {
                    "$": "br.com.sankhya.mgeProd.libera.wc"
                },
                {
                    "$": "br.com.mgeprod.clientEvent.cancelaOP.deleteNotaConfirmada"
                },
                {
                    "$": "br.com.sankhya.actionbutton.clientconfirm"
                }
            ]
        }
    }
}

{
    "serviceName": "LancamentoOrdemProducaoSP.inserirProdutoHTML5",
    "status": "1",
    "pendingPrinting": "false",
    "transactionId": "5A6C2BA1450CE9AC6DB4258338F448E4",
    "responseBody": {}
}

          JapeSession.putProperty("mge.prod.lacamentoHtml.idProc", idProc);

            ProdutoGeradorLancamento produtoLancamento = lancOPBuilder.buildProdutoLancamento(idProc, codplp, codprod, codParc, nuLote, controle, seqop, agruparUnicaOP);

                seqop = lancOPBuilder.inserirOPProducao(codprod, controle, codplp, nuPedido, null, sequencia, codParc, produtoLancamento);

            lancOPBuilder.inserirPAemOP(tipoProcesso, seqop, new ProdutoControle(codprod, controle), codplp, produtoLancamento);

        lancOPBuilder.persistir(); 
===
http://192.0.4.112:8180/mgeprod/service.sbr?serviceName=LancamentoOrdemProducaoSP.getListaExtratoMateriais&counter=128&application=OrdensProducao&outputType=json&preventTransform=false&mgeSession=THMtHyOcFZZqLG7Kf6MG-YxTnXeQdwMAlLvRHw5_&resourceID=br.com.sankhya.prod.OrdensProducaoHTML&globalID=D7E4C2BDC08E580E1000FC5555CD274F

{
    "serviceName": "LancamentoOrdemProducaoSP.getListaExtratoMateriais",
    "requestBody": {
        "params": {
            "nulop": "67295",
            "apresentarPIs": false
        },
        "clientEventList": {
            "clientEvent": [
                {
                    "$": "br.com.sankhya.mgeProd.libera.wc"
                },
                {
                    "$": "br.com.mgeprod.clientEvent.cancelaOP.deleteNotaConfirmada"
                },
                {
                    "$": "br.com.sankhya.actionbutton.clientconfirm"
                }
            ]
        }
    }
}

{"serviceName":"LancamentoOrdemProducaoSP.getListaExtratoMateriais","status":"1","pendingPrinting":"false","transactionId":"F1D5C60D1B1D7195F73EF1DA3F5AEB04","responseBody":{"materiais":{"materiaPrima":[{"CODPRODMP":"3961","DESCRPROD":"ROT. FILME CR  COND. ABACATE  SK  1000G CTRA","NECESSIDADE":"2000","ESTOQUE":"637825","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"6","EXISTEMPSUB":"true","SALDO":"635825","UNIDADE":"UN","QTDPEDCOMPRA":"0"},{"CODPRODMP":"3959","DESCRPROD":"ROT. FILME CR  COND. ABACATE  SK  1000G","NECESSIDADE":"2000","ESTOQUE":"617779","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"6","EXISTEMPSUB":"true","SALDO":"615779","UNIDADE":"UN","QTDPEDCOMPRA":"0"},{"CODPRODMP":"4023","DESCRPROD":"POTE SK CR. LEITE CABRA 1000GRS","NECESSIDADE":"2000","ESTOQUE":"137634","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"135634","UNIDADE":"UN","QTDPEDCOMPRA":"360220"},{"CODPRODMP":"6428","DESCRPROD":"LIPEX SHEA W / AGREEN W","NECESSIDADE":"4.124","ESTOQUE":"13066.411017211","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"13062.287017211","UNIDADE":"KG","QTDPEDCOMPRA":"880"},{"CODPRODMP":"19610","DESCRPROD":"CX POTE 1000 G - MALETA","NECESSIDADE":"333.334","ESTOQUE":"478789.0101748037","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"10","EXISTEMPSUB":"true","SALDO":"478455.6761748037","UNIDADE":"UN","QTDPEDCOMPRA":"658527"},{"CODPRODMP":"1617","DESCRPROD":"CORANTE AZUL CROMAX AT CI42090/Q-B615","NECESSIDADE":"0.00025775","ESTOQUE":"32.1317527673","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"10","EXISTEMPSUB":"true","SALDO":"32.1314950173","UNIDADE":"KG","QTDPEDCOMPRA":"0"},{"CODPRODMP":"3016","DESCRPROD":"FILME PEBD STRETCH BLOW AUTOMATICO","REFERENCIA":"0","NECESSIDADE":"1.000000000","ESTOQUE":"30573.84432","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"6","EXISTEMPSUB":"true","SALDO":"30572.844320000","UNIDADE":"KG","QTDPEDCOMPRA":"9800"},{"CODPRODMP":"1978","DESCRPROD":"CORANTE VERDE CROMAX S13 57A11/Q-ML7108","REFERENCIA":"0","NECESSIDADE":"0.0077325","ESTOQUE":"6.254679517","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"6.246947017","UNIDADE":"KG","QTDPEDCOMPRA":"0"},{"CODPRODMP":"1367","DESCRPROD":"EXTRATO DE ALOE VERA","REFERENCIA":"0","NECESSIDADE":"0.1031","ESTOQUE":"156.682318687","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"156.579218687","UNIDADE":"KG","QTDPEDCOMPRA":"5"},{"CODPRODMP":"3263","DESCRPROD":"ES 021 SK ABACATE EURO","REFERENCIA":"0","NECESSIDADE":"2.61874","ESTOQUE":"1905.526662691","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"1902.907922691","UNIDADE":"KG","QTDPEDCOMPRA":"0"},{"CODPRODMP":"1896","DESCRPROD":"ETIQ. COD. BARRAS BOPP 50MMX100MM","REFERENCIA":"0","NECESSIDADE":"333.333334","ESTOQUE":"3300427.599189379","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"3300094.265855379","UNIDADE":"UN","QTDPEDCOMPRA":"5600000"},{"CODPRODMP":"1414","DESCRPROD":"ZONEM MI/PROTEG GC/CONSERV C","NECESSIDADE":"10.31","ESTOQUE":"9698.633817183","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"9688.323817183","UNIDADE":"KG","QTDPEDCOMPRA":"0"},{"CODPRODMP":"1602","DESCRPROD":"FITA  COD. 5811 TRANSP. 45MM X 1800M ","REFERENCIA":"0","NECESSIDADE":"0.2","ESTOQUE":"1219.948993949","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"1219.748993949","UNIDADE":"UN","QTDPEDCOMPRA":"621"},{"CODPRODMP":"1376","DESCRPROD":"EXTRATO OLEOSO DE ABACATE","REFERENCIA":"0","NECESSIDADE":"0.1031","ESTOQUE":"127.605942716","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"127.502842716","UNIDADE":"KG","QTDPEDCOMPRA":"4"},{"CODPRODMP":"5728","DESCRPROD":"VITAMINA E 100%","NECESSIDADE":"0.02062","ESTOQUE":"663.202053786","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"663.181433786","UNIDADE":"KG","QTDPEDCOMPRA":"0"},{"CODPRODMP":"1599","DESCRPROD":"D'PANTENOL","NECESSIDADE":"0.02062","ESTOQUE":"560.349702094","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"560.329082094","UNIDADE":"KG","QTDPEDCOMPRA":"0"},{"CODPRODMP":"1354","DESCRPROD":"ALCOOL CETO ESTEARILICO 30/70 C 1618/30F","REFERENCIA":"1354","NECESSIDADE":"92.79","ESTOQUE":"62813.734074124","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"62720.944074124","UNIDADE":"KG","QTDPEDCOMPRA":"504000"},{"CODPRODMP":"16277","DESCRPROD":"RIBBON 2000 PTO E 0 (MISTO) 50X450M","REFERENCIA":"0","NECESSIDADE":"0.078","ESTOQUE":"1010.59017192","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"1010.51217192","UNIDADE":"UN","QTDPEDCOMPRA":"600"},{"CODPRODMP":"1353","DESCRPROD":"ACIDO CITRICO","NECESSIDADE":"0.247440000","ESTOQUE":"4092.375976961","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"4092.128536961","UNIDADE":"KG","QTDPEDCOMPRA":"0"},{"CODPRODMP":"2590","DESCRPROD":"QUATERNARIO QUATERCAP CTTN 29%","NECESSIDADE":"71.139","ESTOQUE":"109383.85202289","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"109312.71302289","UNIDADE":"KG","QTDPEDCOMPRA":"253000"},{"CODPRODMP":"1390","DESCRPROD":"MONOESTEARATO DE GLICERILA 40 VEGETAL","REFERENCIA":"0","NECESSIDADE":"30.93","ESTOQUE":"47239.896415143","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"47208.966415143","UNIDADE":"KG","QTDPEDCOMPRA":"23100"},{"CODPRODMP":"15924","DESCRPROD":"TAMPA 1000GRS SK LINHA AMAZONICO","REFERENCIA":"0","NECESSIDADE":"2000","ESTOQUE":"191262","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"189262","UNIDADE":"UN","QTDPEDCOMPRA":"0"},{"CODPRODMP":"14445","DESCRPROD":"OLEO DE RICINO/MAMONA","NECESSIDADE":"0.1031","ESTOQUE":"2438.671272299","QTDSUBS":"0","ESTSUFICIENTE":"Sim","DECQTD":"9","EXISTEMPSUB":"true","SALDO":"2438.568172299","UNIDADE":"KG","QTDPEDCOMPRA":"0"}]}}}

===
http://192.0.4.112:8180/mgeprod/service.sbr?serviceName=LancamentoOrdemProducaoSP.alterarAlocacaoWorkCenter&counter=166&application=OrdensProducao&outputType=json&preventTransform=false&mgeSession=THMtHyOcFZZqLG7Kf6MG-YxTnXeQdwMAlLvRHw5_&resourceID=br.com.sankhya.prod.OrdensProducaoHTML&globalID=D7E4C2BDC08E580E1000FC5555CD274F

{
    "serviceName": "LancamentoOrdemProducaoSP.alterarAlocacaoWorkCenter",
    "requestBody": {
        "params": {
            "nulop": 67295,
            "seqop": 1,
            "idawc": 1,
            "codwcp": 1
        },
        "clientEventList": {
            "clientEvent": [
                {
                    "$": "br.com.sankhya.mgeProd.libera.wc"
                },
                {
                    "$": "br.com.mgeprod.clientEvent.cancelaOP.deleteNotaConfirmada"
                },
                {
                    "$": "br.com.sankhya.actionbutton.clientconfirm"
                }
            ]
        }
    }
}

{
    "serviceName": "LancamentoOrdemProducaoSP.alterarAlocacaoWorkCenter",
    "status": "1",
    "pendingPrinting": "false",
    "transactionId": "532F31BC56FE1612BC42B915E6A18319",
    "responseBody": {
        "lancamento": {
            "nulop": "67295",
            "descricao": "DEFAULT",
            "reutilizar": "N",
            "processos": {
                "processo": {
                    "seqop": "1",
                    "ordem": "1",
                    "idproc": "388",
                    "codprc": "400",
                    "codplp": "1",
                    "descrabrev": "COSMETICOS - PRODUTO SKU",
                    "multicontrole": "N",
                    "tiponrolote": "AX",
                    "defterceiro": "O",
                    "usaterceiro": "N",
                    "exigePedido": "O",
                    "procdesmonte": "N",
                    "versao": "2",
                    "procreparo": "N",
                    "produtos": {
                        "produto": {
                            "codprod": "3108",
                            "tamlote": "2000.0",
                            "editado": "true",
                            "calctamlote": "0",
                            "codformula": "0",
                            "descricao": "CR COND SK BOMBA ABACATE 1000G CX6X1",
                            "referencia": "7897042005048",
                            "tamlotepad": "2000",
                            "multideal": "0",
                            "qtdprodmin": "0",
                            "codvol": "UN",
                            "decQtd": "2",
                            "estoque": "6097",
                            "dependencias": {}
                        }
                    },
                    "alocacoes": {
                        "alocacao": {
                            "idawc": "1",
                            "codwcp": "1",
                            "nome": "LINHA 01",
                            "codcwc": "1",
                            "descricao_categoria": "CATEGORIA C.T. COSM\u00c9TICOS",
                            "tipalocacao": "O"
                        }
                    },
                    "terceiros": {}
                }
            }
        }
    }
}

LancamentoOP lancamento = LancamentoOP.load(nulop, jdbc);

ItemLancamentoOPBean processo = lancamento.getProcesso(seqop);

WorkCenterHelper.getInstance().validCapacityWorkCenter(novoCodwcp, codProdPA, controlePA, tamLote, null, false);
processo.alterarAlocacaoWorkCenter(idawc, novoCodwcp);
lancamento.persistir();

===
http://192.0.4.112:8180/mgeprod/service.sbr?serviceName=LancamentoOrdemProducaoSP.lancarOrdensDeProducao&counter=237&application=OrdensProducao&outputType=json&preventTransform=false&mgeSession=THMtHyOcFZZqLG7Kf6MG-YxTnXeQdwMAlLvRHw5_&resourceID=br.com.sankhya.prod.OrdensProducaoHTML&globalID=D7E4C2BDC08E580E1000FC5555CD274F

{
    "serviceName": "LancamentoOrdemProducaoSP.lancarOrdensDeProducao",
    "requestBody": {
        "params": {
            "nulop": "67295",
            "ignorarWarnings": "N"
        },
        "clientEventList": {
            "clientEvent": [
                {
                    "$": "br.com.sankhya.mgeProd.libera.wc"
                },
                {
                    "$": "br.com.mgeprod.clientEvent.cancelaOP.deleteNotaConfirmada"
                },
                {
                    "$": "br.com.sankhya.actionbutton.clientconfirm"
                }
            ]
        }
    }
}

{
    "serviceName": "LancamentoOrdemProducaoSP.lancarOrdensDeProducao",
    "status": "1",
    "pendingPrinting": "false",
    "transactionId": "58D2B13769D52C2977AEBB119D8A18BB",
    "responseBody": {
        "ordensIniciadas": {
            "quantidade": {
                "$": "1"
            }
        },
        "ordens": {
            "ordem": {
                "$": "375489"
            }
        }
    }
}


