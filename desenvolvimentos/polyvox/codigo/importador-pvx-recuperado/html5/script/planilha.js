
//    document.getElementById('fileInput').addEventListener('change', function (event) {
//        document.getElementById('resultado').innerHTML = 'Dados Carregados e Prontos Para Serem Processados!!!';
//
//    });
//
//    function uploadPlanilha() {
//        const fileInput = document.getElementById('fileInput');
//        if (!fileInput.files.length) {
//            alert('Por favor, selecione um arquivo primeiro!');
//            return;
//        }
//
//        const file = fileInput.files[0];
//        const reader = new FileReader();
//
//        reader.onload = function (event) {
//            const data = new Uint8Array(event.target.result);
//            const workbook = XLSX.read(data, { type: 'array' });
//
//            const primeiraPlanilha = workbook.Sheets[workbook.SheetNames[0]];
//            let dados = XLSX.utils.sheet_to_json(primeiraPlanilha);
//
//            // Convertendo o objeto JSON para string
//            const jsonData = JSON.stringify(dados);
//
//            // Simulação de envio para o Sankhya usando a função JX.salvar
//            salvarNoSankhya(jsonData);
//
//        };
//
//        reader.readAsArrayBuffer(file);
//    }
//
//    function salvarNoSankhya(dadosJSON) {
//        JX.salvar({ JSON: dadosJSON }, 'AD_PRCSJSON', {})
//            .then(response => {
//                processarPlanilha();
//                console.log('Sucesso ao salvar no Sankhya:', response);
//                document.getElementById('resultado').innerHTML = 'Planilha salva';
//                alert('Dados salvos com sucesso no Sankhya!');
//            })
//            .catch(error => {
//                console.error('Erro ao salvar no Sankhya:', error);
//                document.getElementById('resultado').innerHTML = 'Erro ao salvar planilha!';
//                alert('Erro ao salvar os dados.');
//            });
//    }
//    function processarPlanilha() {
//
//
//        JX.acionarBotao(
//            {
//                PARAMETRO: 1
//            },
//            {
//                tipo: 'JAVA',
//                idBotao: 110
//            }
//        ).then(respostaAcionamentoBotao => {
//            console.log('Sucesso ao processar a planilha:', respostaAcionamentoBotao);
//            alert('Sucesso!!!');
//        }).catch(error => {
//            console.error('Erro ao chamar a ação: ', error);
//            alert('Erro ao chamar a ação.');
//        });
//    }
//    function geraNota() {
//
//
//        JX.acionarBotao(
//            {
//                PARAMETRO: 1
//            },
//            {
//                tipo: 'JAVA',
//                idBotao: 67
//            }
//        ).then(respostaAcionamentoBotao => {
//            console.log('Sucesso ao gerar nota:', respostaAcionamentoBotao);
//            alert('Sucesso!!!');
//        }).catch(error => {
//            console.error('Erro ao chamar a ação: ', error);
//            alert('Erro ao chamar a ação.');
//        });
//    }
//