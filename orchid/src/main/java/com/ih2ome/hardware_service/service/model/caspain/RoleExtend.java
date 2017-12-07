package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class RoleExtend implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private long baseView;
  private long landlordHouseAdd;
  private long landlordHouseEdit;
  private long landlordHouseDel;
  private long landlordHouseAddroom;
  private long landlordHouseEditroom;
  private long landlordHouseDelroom;
  private long landlordContractView;
  private long landlordContractCreate;
  private long landlordContractEdit;
  private long landlordContractRenew;
  private long landlordContractEvict;
  private long landlordContractDel;
  private long landlordOrderView;
  private long landlordOrderEdit;
  private long landlordOrderPaid;
  private long landlordOrderAdd;
  private long landlordOrderDel;
  private long customerContractView;
  private long customerContractCreate;
  private long customerContractEdit;
  private long customerContractRenew;
  private long customerContractEvict;
  private long customerContractDel;
  private long customerContractReserve;
  private long customerContractCancel;
  private long customerOrderView;
  private long customerOrderEdit;
  private long customerOrderSend;
  private long customerOrderPaid;
  private long customerOrderAdd;
  private long customerOrderMod;
  private long customerOrderDel;
  private long crmManageView;
  private long crmManageEdit;
  private long crmManageDel;
  private long financeManageView;
  private long financeFlowView;
  private long financeFlowAdd;
  private long financeFlowDel;
  private long financeFlowExp;
  private long financeFlowPreView;
  private long financeFlowPreExp;
  private long reportsView;
  private long reportsBusiView;
  private long reportsBusiExp;
  private long reportsDetailView;
  private long reportsDetailExp;
  private long reportsLcfView;
  private long reportsLcfExp;
  private long statisticsView;
  private long statisticsBusiView;
  private long statisticsFinaView;
  private long openchannelView;
  private long openchannel;
  private long financeloanFlowView;
  private long smartDeviceView;
  private long smartPowerBind;
  private long smartPowerPrepay;
  private long smartPowerSwitch;
  private long smartPowerUnbind;
  private long smartPowerSet;
  private long h2Ometrades;
  private String name;
  private long roleId;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private long financeFlowPreInExp;
  private long financeFlowPreInView;
  private long financeFlowPreOutExp;
  private long financeFlowPreOutView;
  private long financePaymentExp;
  private long financePaymentView;

}
