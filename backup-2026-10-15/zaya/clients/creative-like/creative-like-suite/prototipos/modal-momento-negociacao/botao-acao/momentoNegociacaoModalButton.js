var titulo = '[ZAYA.IT] - Momento Negociacao - BETA V1.0';
var entryPoint = 'modalMomentoNegociacao.jsp';
var nuGdg = 'PREENCHER_ID_COMPONENTE_HTML5';
var codAcaoJavaMomento = '299';

var nunegociacao = '';

if (linhas && linhas.length > 0) {
    nunegociacao = linhas[0].getCampo('NUNEGOCIACAO');
}

if (!nunegociacao) {
    contexto.mostraErro('Selecione uma negociacao antes de abrir o momento da negociacao.');
} else {
    var largura = 1100;
    var altura = 680;
    var iframeAltura = 580;

    var url = '/mge/html5component.mge'
        + '?entryPoint=' + encodeURIComponent(entryPoint)
        + '&nuGdg=' + encodeURIComponent(nuGdg)
        + '&nunegociacao=' + encodeURIComponent(nunegociacao)
        + '&codAcaoJavaMomento=' + encodeURIComponent(codAcaoJavaMomento)
        + '&modo=' + encodeURIComponent('SIMULACAO');

    var mensagem = ''
        + '<style>'
        + 'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in > div > div {'
        + 'width:' + largura + 'px;'
        + 'height:' + altura + 'px;'
        + 'float:left;'
        + 'margin-left:calc(50% - ' + Math.floor(largura / 2) + 'px);'
        + '}'
        + '</style>'
        + '<style>'
        + 'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in > div > div > div.modal-footer {display:none}'
        + '</style>'
        + '<style>'
        + 'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in > div > div > div.modal-header.ng-scope {background-color:#fff}'
        + '</style>'
        + '<style>'
        + 'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in > div > div > div.modal-body {padding:0}'
        + '</style>'
        + '<style>'
        + 'body > div.modal-type-warning.modal.modal-message.sk-popup.ng-isolate-scope.in > div > div > div.modal-body > div > iframe {'
        + 'position:relative;'
        + 'height:' + iframeAltura + 'px;'
        + 'width:100%;'
        + 'border:none;'
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
