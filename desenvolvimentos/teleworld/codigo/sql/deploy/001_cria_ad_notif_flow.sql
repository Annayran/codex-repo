CREATE TABLE AD_NOTIFFLOW (
    IDNOTIF          NUMBER(15)       NOT NULL,
    NUNOTA           NUMBER(10)       NOT NULL,
    CODTIPOPER       NUMBER(10),
    ORIGEM           VARCHAR2(30)     NOT NULL,
    CODUSU           NUMBER(10)       NOT NULL,
    ASSUNTO          VARCHAR2(200)    NOT NULL,
    MSG              VARCHAR2(4000)   NOT NULL,
    STATUS           VARCHAR2(2)      DEFAULT 'PE' NOT NULL,
    IDTAREFAFLOW     VARCHAR2(200),
    DHCRIACAO        DATE             DEFAULT SYSDATE NOT NULL,
    DHENVIOFLOW      DATE,
    DHULTTENTATIVA   DATE,
    QTDTENTATIVAS    NUMBER(5)        DEFAULT 0 NOT NULL,
    ULTIMOERRO       VARCHAR2(4000),
    CONSTRAINT PK_AD_NOTIFFLOW PRIMARY KEY (IDNOTIF),
    CONSTRAINT UK_AD_NOTIFFLOW UNIQUE (NUNOTA, ORIGEM),
    CONSTRAINT CK_AD_NOTIFFLOW_STATUS CHECK (
        STATUS IN ('PE', 'PR', 'EN', 'ER')
    )
);

CREATE SEQUENCE AD_SEQ_NOTIF_FLOW
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

COMMENT ON TABLE AD_NOTIFFLOW IS
    'Fila de notificacoes que serao enviadas para o Flow pela integracao Java.';

COMMENT ON COLUMN AD_NOTIFFLOW.ORIGEM IS
    'Identifica a regra que gerou a notificacao e compoe o controle de duplicidade.';

COMMENT ON COLUMN AD_NOTIFFLOW.IDTAREFAFLOW IS
    'Identificador da tarefa criada no Flow.';

COMMENT ON COLUMN AD_NOTIFFLOW.ULTIMOERRO IS
    'Mensagem da ultima falha retornada pela integracao Java.';
