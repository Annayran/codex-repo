package br.com.crmbonus.common.integration;

import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;

public class JapeDaoWrapper {
    public JapeWrapper dao(String entityName) {
        return JapeFactory.dao(entityName);
    }
}

