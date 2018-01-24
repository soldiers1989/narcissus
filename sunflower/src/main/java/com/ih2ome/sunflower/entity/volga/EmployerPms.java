package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class EmployerPms implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private long employer;
  private long landlordContractCreate;
  private long landlordContractEdit;
  private long landlordContractRenew;
  private long landlordContractEvict;
  private long landlordOrderView;
  private long landlordOrderEdit;
  private long landlordOrderPaid;
  private long customerContractCreate;
  private long customerContractEdit;
  private long customerContractRenew;
  private long customerContractEvict;
  private long customerOrderView;
  private long customerOrderEdit;
  private long customerOrderSend;
  private long customerOrderPaid;
  private long financeFlowView;
  private long financeFlowAdd;
  private long openchannel;
  private long financeFlowDel;
  private long crmManageDel;
  private long crmManageEdit;
  private long customerOrderAdd;
  private long customerOrderDel;
  private long customerOrderMod;
  private long financeloanFlowView;
  private long landlordOrderAdd;
  private long landlordOrderDel;
  private long smartPowerBind;
  private long smartPowerPrepay;
  private long smartPowerSwitch;
  private long baseView;
  private long crmManageView;
  private long customerContractCancel;
  private long customerContractDel;
  private long customerContractReserve;
  private long customerContractView;
  private long financeFlowExp;
  private long financeFlowPreExp;
  private long financeFlowPreView;
  private long financeManageView;
  private long h2Ometrades;
  private long landlordContractDel;
  private long landlordContractView;
  private long landlordHouseAdd;
  private long landlordHouseAddroom;
  private long landlordHouseDel;
  private long landlordHouseDelroom;
  private long landlordHouseEdit;
  private long landlordHouseEditroom;
  private long openchannelView;
  private long reportsBusiExp;
  private long reportsBusiView;
  private long reportsDetailExp;
  private long reportsDetailView;
  private long reportsLcfExp;
  private long reportsLcfView;
  private long reportsView;
  private long smartDeviceView;
  private long smartPowerSet;
  private long smartPowerUnbind;
  private long statisticsBusiView;
  private long statisticsFinaView;
  private long statisticsView;
  private long financeFlowPreInExp;
  private long financeFlowPreInView;
  private long financeFlowPreOutExp;
  private long financeFlowPreOutView;
  private long financePaymentExp;
  private long financePaymentView;

}