/**
 * Construtor da controladora da pagina inicial
 * 
 * Aqui, foi definido como uma classe, mas pode ser instanciada como uma funcao.
 * Nesse caso da classe, necessario ser usado os elementos inerentes a instancia atual (this)
 */
class ControladoraCentral {

    /**
     * Importacao dos elementos nativos injetados pelo SNK.JS ou por modulos devidamente definidos
     */
    constructor (ObjectUtils, Criteria, DateUtils, SanPopup, MessageUtils, $scope) {

        /** **/
        /** REMOCAO DO FRAME (frame de entorno padrao dos componentes de BI)                             **/
        /** **/
        /** Ele recarregara o _iframe_ do componente de BI, por isso, informe o                          **/
        /** local onde ele encontrara novamente a pagina inicial JSP na propriedade                      **/
        /** `paginaInicial`. E faremos a busca do componente a ser recarregado baseado                   **/
        /** no nome exato do componente que devemos informar a propriedade `instancia`                   **/
        /** **/
            JX.removerFrame ({
                paginaInicial: 'paginas/entidade/index.jsp', /** Local da pagina inicial(nao use BASE_FOLDER) **/
                instancia: 'TELA_HTML5'                      /** Nome EXATO (case sensitive) do Componente BI **/
            });
        /** **/

        /* Interceptadores da entidade de tela */
            ObjectUtils.implements (this, IDynaformInterceptor);
            ObjectUtils.implements (this, IDatagridInterceptor);
        /* */

        /* Modulos de componentes necessarios ao sistema */
            this.Criteria       = Criteria;
            this.DateUtils      = DateUtils;
            this.SanPopup       = SanPopup;
            this.MessageUtils   = MessageUtils;
        /* */

        /* Variaveis com os dados da entidade principal da tela */
            this.dataset        = undefined;
            this.formulario     = undefined;
            this.nomeEntidade   = `AD_TGLCAB`;
        /* */

    }


    /**
     * Evento disparado ao se finalizar a criacao e montagem do componente de formulario dinamico
     * 
     * @param { any } dynaform Instancia e informacoes sobre o formulario dinamico
     * @param  { any } dataset Instancia do conjunto de informacoes dos registros para preencher a grade de registros
     */

    aoCarregarDynaform (dynaform, dataset) {

        /* Checa se a tabela carregada eh a entidade */
        if (dataset.getEntityName () === this.nomeEntidade) {

            /* Salva as informacoes da tabela */
            this.dataset    = dataset;
            this.formulario = dynaform;

            var queryString = window.location.search;
            var urlParams = new URLSearchParams(queryString);

            // Verifica se o parâmetro existe e se é um número
            //if (nufin && !isNaN(nufin)) {
                // Aplica o critério de pesquisa ao dataset
                this.dataset.query = `SELECT F.*
                                         FROM AD_TGLCAB F
                                         WHERE 1 = 0`;
            //}

            /* Inicializa o dataset com os valores iniciais */
            this.dataset.initAndRefresh ();
            console.log("Retorno do dataset:", this.dataset);

            /* Alterna o modo de visualizacao do formulario para o modo grade */
            this.formulario.goToGridView ();

        }

    }

    /**
     * Abre o modal para aplicar o bônus (usa SanPopup quando disponível)
     * @param {string|number} codparc
     * @param {string|number} nunota
     */
    openApplyBonusModal(codparc, nunota) {
        var url = 'paginas/entidade/modalApplyBonus.jsp?codparc=' + encodeURIComponent(codparc) + '&nunota=' + encodeURIComponent(nunota);

        // se SanPopup estiver disponível (componentes Sankhya), usar popup integrado
        if (this.SanPopup && typeof this.SanPopup.open === 'function') {
            try {
                this.SanPopup.open({
                    title: 'Aplicar Bônus',
                    url: url,
                    width: 520,
                    height: 280
                });
                return;
            } catch (e) {
                console.log('SanPopup falhou, fallback para window.open', e);
            }
        }

        // fallback: window.open
        window.open(url, '_blank', 'width=520,height=280');
    }

}

// Exemplo de uso (por ex. do botão de ação do servidor):
// var controller = new ControladoraCentral(ObjectUtils, Criteria, DateUtils, SanPopup, MessageUtils, $scope);
// controller.openApplyBonusModal(1234, 5678);

// Global helper that the modal will call if available.
// New: executeApplyBonusPayload(payload, callback) sends arbitrary payload (must include action) to configured endpoint.
window.executeApplyBonusPayload = function(payload, callback) {
    var endpoint = window.APPLY_BONUS_ENDPOINT;
    if(!endpoint){
        callback('APPLY_BONUS_ENDPOINT not configured; set window.APPLY_BONUS_ENDPOINT to your endpoint URL');
        return;
    }

    fetch(endpoint, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    }).then(function(response){
        if(!response.ok){
            return response.text().then(function(txt){ callback(txt || ('HTTP ' + response.status)); });
        }
        return response.json().then(function(json){ callback(null, json); });
    }).catch(function(err){ callback(err); });
};

// Backwards-compatible helper: executeApplyBonusAction(codparc, nunota, callback)
window.executeApplyBonusAction = function(codparc, nunota, callback) {
    var payload = { action: 'apply-bonus', codparc: codparc, nunota: nunota };
    window.executeApplyBonusPayload(payload, callback);
};
