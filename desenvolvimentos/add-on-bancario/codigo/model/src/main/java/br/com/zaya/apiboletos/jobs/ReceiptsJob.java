package br.com.zaya.apiboletos.jobs;

import br.com.sankhya.studio.annotations.Job;
import br.com.sankhya.studio.annotations.enums.EJBTransactionType;
import br.com.sankhya.studio.stereotypes.IJob;
import br.com.zaya.apiboletos.application.RuntimeModule;

@Job(
    serviceName = "ZycbReceiptsJobSP",
    frequency = "&60000",
    transactionType = EJBTransactionType.NotSupported)
public final class ReceiptsJob extends IJob {
  public void onSchedule() {
    if (!RuntimeModule.enabled()) return;
    try {
      RuntimeModule.ENGINE.collect(50);
    } catch (Exception e) {
      throw new IllegalStateException("ZYCB_JOB_RECEBIMENTOS_FALHOU");
    }
  }
}
