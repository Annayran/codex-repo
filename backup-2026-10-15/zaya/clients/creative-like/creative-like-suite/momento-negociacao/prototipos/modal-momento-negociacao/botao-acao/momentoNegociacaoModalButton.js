var titulo = '[ZAYA.IT] - Momento Negociacao - BETA V1.0';
var entryPoint = 'modalMomentoNegociacao.jsp';
var nuGdg = 'PREENCHER_ID_COMPONENTE_HTML5';
var codAcaoJavaMomento = '299';
var codAcaoJavaCancelarDocumento = 'CADASTRAR_ID_ACAO_CANCELAR_DOCUMENTO';

var nunegociacao = '';

if (linhas && linhas.length > 0) {
    nunegociacao = linhas[0].getCampo('NUNEGOCIACAO');
}

if (!nunegociacao) {
    contexto.mostraErro('Selecione uma negociacao antes de abrir o momento da negociacao.');
} else {
    var largura = 1160;
    var altura = 760;

    var url = '/mge/html5component.mge'
        + '?entryPoint=' + encodeURIComponent(entryPoint)
        + '&nuGdg=' + encodeURIComponent(nuGdg)
        + '&nunegociacao=' + encodeURIComponent(nunegociacao)
        + '&codAcaoJavaMomento=' + encodeURIComponent(codAcaoJavaMomento)
        + '&codAcaoJavaCancelarDocumento=' + encodeURIComponent(codAcaoJavaCancelarDocumento)
        + '&modo=' + encodeURIComponent('OPERACIONAL');

    var mensagem = ''
        + '<style>'
        + 'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in > div {'
        + 'width:' + largura + 'px !important;'
        + 'max-width:calc(100vw - 80px) !important;'
        + 'margin:18px auto 0 auto !important;'
        + '}'
        + '</style>'
        + '<style>'
        + 'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in > div > div {'
        + 'width:100% !important;'
        + 'height:' + altura + 'px !important;'
        + 'max-height:calc(100vh - 48px) !important;'
        + 'display:flex;'
        + 'flex-direction:column;'
        + 'overflow:hidden;'
        + '}'
        + '</style>'
        + '<style>'
        + 'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in > div > div > div.modal-footer {display:none}'
        + '</style>'
        + '<style>'
        + 'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in > div > div > div.modal-header.ng-scope {background-color:#fff}'
        + '</style>'
        + '<style>'
        + 'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in > div > div > div.modal-body {'
        + 'padding:0;'
        + 'flex:1 1 auto;'
        + 'min-height:0;'
        + 'overflow:hidden;'
        + '}'
        + '</style>'
        + '<style>'
        + 'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in > div > div > div.modal-body > div {'
        + 'height:100%;'
        + 'margin:0;'
        + 'padding:0;'
        + '}'
        + '</style>'
        + '<style>'
        + 'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in > div > div > div.modal-body > div > iframe {'
        + 'position:relative;'
        + 'height:100%;'
        + 'width:100%;'
        + 'display:block;'
        + 'border:none;'
        + 'margin:0;'
        + 'padding:0;'
        + '}'
        + '</style>'
        + '<script>'
        + 'setTimeout(function(){'
        + 'var h=document.querySelector("body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in > div > div > div.modal-header.ng-scope > sk-hbox > h5");'
        + 'if(h) h.innerText="' + titulo.replace(/"/g, '\\"') + '";'
        + '},700);'
        + '</script>'
        + '<iframe src="' + url + '"></iframe>';

    contexto.setMensagemRetorno(mensagem);
}
