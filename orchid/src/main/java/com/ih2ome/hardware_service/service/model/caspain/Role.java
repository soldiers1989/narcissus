package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Role implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private long landlordHouseAdd;
  private long landlordHouseDel;
  private long landlordHouseEdit;
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
  private String name;
  private long type;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private long financeFlowDel;
  private long crmManageDel;
  private long crmManageEdit;
  private long customerContractCancel;
  private long customerContractReserve;
  private long customerOrderAdd;
  private long customerOrderMod;
  private long financeloanFlowView;
  private long landlordHouseAddroom;
  private long landlordHouseDelroom;
  private long landlordHouseEditroom;
  private long landlordOrderAdd;
  private long smartPowerBind;
  private long smartPowerPrepay;
  private long smartPowerSwitch;
  private long customerOrderDel;
  private long landlordOrderDel;
  private long baseView;
  private long crmManageView;
  private long customerContractDel;
  private long customerContractView;
  private long financeFlowExp;
  private long financeFlowPreExp;
  private long financeFlowPreView;
  private long financeManageView;
  private long landlordContractDel;
  private long landlordContractView;
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
  private long h2Ometrades;
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
