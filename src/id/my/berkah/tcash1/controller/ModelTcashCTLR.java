/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.implement.Implement;
import id.my.berkah.tcash1.model.ArtWorkListParam;
import id.my.berkah.tcash1.model.BdlListDtlParam;
import id.my.berkah.tcash1.model.BdlListHdrParam;
import id.my.berkah.tcash1.model.BundlingGDListHDRParam;
import id.my.berkah.tcash1.model.DtlItmProductionIssue;
import id.my.berkah.tcash1.model.GdListHdrParam;
import id.my.berkah.tcash1.model.GdListPoItemBundlingParam;
import id.my.berkah.tcash1.model.GrListHdrParam;
import id.my.berkah.tcash1.model.IfListDtlParam;
import id.my.berkah.tcash1.model.ListAddDtlPairingParam;
import id.my.berkah.tcash1.model.ListBundlingGRHDR;
import id.my.berkah.tcash1.model.ListBundlingGdEditSNParam;
import id.my.berkah.tcash1.model.ListDTLMappingBDL;
import id.my.berkah.tcash1.model.ListDtlbundlingParam;
import id.my.berkah.tcash1.model.ListExportExcelParam;
import id.my.berkah.tcash1.model.ListGdDtl;
import id.my.berkah.tcash1.model.ListGdDtlBundlingParam;
import id.my.berkah.tcash1.model.ListGdEditSNParam;
import id.my.berkah.tcash1.model.ListGdItemParam;
import id.my.berkah.tcash1.model.ListGenIFParam;
import id.my.berkah.tcash1.model.ListGrListEditSNParam;
import id.my.berkah.tcash1.model.ListGrSelectGDParam;
import id.my.berkah.tcash1.model.ListHDRMappingBDL;
import id.my.berkah.tcash1.model.ListHdrAutoGenParam;
import id.my.berkah.tcash1.model.ListHdrBundlingParam;
import id.my.berkah.tcash1.model.ListIFBndParam;
import id.my.berkah.tcash1.model.ListIFParam;
import id.my.berkah.tcash1.model.ListIFParamTrc;
import id.my.berkah.tcash1.model.ListInvSelectSNParam;
import id.my.berkah.tcash1.model.ListOFDtlParam;
import id.my.berkah.tcash1.model.ListOFParam;
import id.my.berkah.tcash1.model.ListPairingDTLParam;
import id.my.berkah.tcash1.model.ListPairingHdrParam;
import id.my.berkah.tcash1.model.ListPairingParam;
import id.my.berkah.tcash1.model.ListSNUploadErrorParam;
import id.my.berkah.tcash1.model.ListSelectSNPAram;
import id.my.berkah.tcash1.model.ListSelectSNTempParam;
import id.my.berkah.tcash1.model.ListShopFloorDtlParam;
import id.my.berkah.tcash1.model.ListShopFloorParam;
import id.my.berkah.tcash1.model.ListSnMapBundlingParam;
import id.my.berkah.tcash1.model.ListWIPHdrParam;
import id.my.berkah.tcash1.model.ListWipParam;
import id.my.berkah.tcash1.model.ListWoListAddLineDtlParam;
import id.my.berkah.tcash1.model.ListWoListAddLineHdrParam;
import id.my.berkah.tcash1.model.ListWoListAllocateINVParam;
import id.my.berkah.tcash1.model.ListWoListAllocateNBRParam;
import id.my.berkah.tcash1.model.ListWoListAllocateNumberingTempParam;
import id.my.berkah.tcash1.model.ListWoListDtlParam;
import id.my.berkah.tcash1.model.ListWoListHLRDtlParam;
import id.my.berkah.tcash1.model.ListWoListHLRParam;
import id.my.berkah.tcash1.model.ListWoListSNParam;
import id.my.berkah.tcash1.model.ListWoPairing;
import id.my.berkah.tcash1.model.ListWoParam;
import id.my.berkah.tcash1.model.ListWoRcvParam;
import id.my.berkah.tcash1.model.ListdistdataParam;
import id.my.berkah.tcash1.model.ListloadtourpParam;
import id.my.berkah.tcash1.model.ListresultGrListDtlParam;
import id.my.berkah.tcash1.model.ListselectParam;
import id.my.berkah.tcash1.model.LovBUParam;
import id.my.berkah.tcash1.model.LovForwardAgentParam;
import id.my.berkah.tcash1.model.LovItemParam;
import id.my.berkah.tcash1.model.LovRegionalParam;
import id.my.berkah.tcash1.model.LovSiteParam;
import id.my.berkah.tcash1.model.LovWhParam;
import id.my.berkah.tcash1.model.LovsupParam;
import id.my.berkah.tcash1.model.MVItemToVoucherParam;
import id.my.berkah.tcash1.model.OfSumParam;
import id.my.berkah.tcash1.model.PcListDtlParam;
import id.my.berkah.tcash1.model.PcListHdrParam;
import id.my.berkah.tcash1.model.ProductionIssued;
import id.my.berkah.tcash1.model.RpListDtlParam;
import id.my.berkah.tcash1.model.RpListHdrParam;
import id.my.berkah.tcash1.model.SelectRange;
import id.my.berkah.tcash1.model.getRegAvailableQtyParam;
import id.my.berkah.tcash1.model.listDtlRegionalParam;
import id.my.berkah.tcash1.model.wolistHDRPAram;
import id.my.berkah.util.ParameterGlobal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Azec
 */
public class ModelTcashCTLR {
    
     private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];

    public List<ListWIPHdrParam> getListHdr(String InWipRcpId, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InWipRcpId", InWipRcpId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().getListHdr(maper);

    }

    public List<ListWIPHdrParam> getListFindHdr(String InWipRcpId, String InDateFrom, String InDateTo, String InStatus, String InProdIssueNo, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InWipRcpId", InWipRcpId);
        maper.put("InDateFrom", InDateFrom);
        maper.put("InDateTo", InDateTo);
        maper.put("InStatus", InStatus);
        maper.put("InProdIssueNo", InProdIssueNo);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().getListFindHdr(maper);

    }

    public List<ListWIPHdrParam> selectListHdr(String InWipRcpId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InWipRcpId", InWipRcpId);
        return new Implement().selectListHdr(maper);
    }

    public List<Integer> getCountwipListFindparam(String InWipRcpId, String InDateFrom, String InDateTo, String InStatus, String InProdIssueNo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InWipRcpId", InWipRcpId);
        maper.put("InDateFrom", InDateFrom);
        maper.put("InDateTo", InDateTo);
        maper.put("InStatus", InStatus);
        maper.put("InProdIssueNo", InProdIssueNo);
        return new Implement().getCountwipListFindparam(maper);
    }

    public Map doInsertWIPHDR(String InWipRcpId, String InProdIssueId, String InProdIssueNo, String InItemId, String InQty, String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InWipRcpId", InWipRcpId);
        maper.put("InProdIssueId", InProdIssueId);
        maper.put("InProdIssueNo", InProdIssueNo);
        maper.put("InItemId", InItemId);
        maper.put("InQty", InQty);
        maper.put("InUserId", InUserId);
        new Implement().doInsertWIPHDR(maper);
        return maper;
    }

    public Map doValidateInsertHdr(String InWipRcpId, String InProdIssueId, String InItemId, String InQty, String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InWipRcpId", InWipRcpId);
        maper.put("InProdIssueId", InProdIssueId);
        maper.put("InItemId", InItemId);
        maper.put("InQty", InQty);
        maper.put("InUserId", InUserId);
        new Implement().doValidateInsertHdr(maper);
        System.out.println(maper);
        return maper;
    }

    public Map doUpdateStatusWIP(String InWipRcpId, String InStatus, String InUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InWipRcpId", InWipRcpId);
        map.put("InStatus", InStatus);
        map.put("InUserId", InUserId);
        System.out.println(map);
        new Implement().doUpdateStatusWIP(map);
        return map;

    }

    public Map doValidateUpdateHdr(String InWipId, String InProdIssueId, String InItemId, String InQty, String InUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InWipId", InWipId);
        map.put("InProdIssueId", InProdIssueId);
        map.put("InItemId", InItemId);
        map.put("InQty", InQty);
        map.put("InUserId", InUserId);
        new Implement().doValidateUpdateHdr(map);
        return map;
    }

    public Map doUpdateHdr(String InWipRcpId, String InProdIssueId, String InProdIssueNo, String InItemId, String InQty, String InUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InWipRcpId", InWipRcpId);
        map.put("InProdIssueId", InProdIssueId);
        map.put("InProdIssueNo", InProdIssueNo);
        map.put("InItemId", InItemId);
        map.put("InQty", InQty);
        map.put("InUserId", InUserId);
        new Implement().doUpdateHdr(map);
        return map;
    }

    public Map doValidateStatus(String InWipRcpId, String InStatus) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InWipRcpId", InWipRcpId);
        map.put("InStatus", InStatus);
        new Implement().doValidateStatus(map);
        return map;
    }

    public Map doInsertSelectSnTemp(String InWipRcpId, String InProdIssueId, String InItemId, String InUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InWipRcpId", InWipRcpId);
        map.put("InProdIssueId", InProdIssueId);
        map.put("InItemId", InItemId);
        map.put("InUserId", InUserId);

        new Implement().doInsertSelectSnTemp(map);
        return map;
    }

    public List<ListselectParam> getListSelectSnTemp(String InWipRcpId, String pageNumber, String pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InWipRcpId", InWipRcpId);
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);

        return new Implement().getListSelectSnTemp(map);
    }

    public List<Integer> getCountListSelectSnTemp(String InWipRcpId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InWipRcpId", InWipRcpId);
        return new Implement().getCountListSelectSnTemp(map);

    }

    public Map doValidateSelectSn(String InItemDtlId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InItemDtlId", InItemDtlId);
        new Implement().doValidateSelectSn(map);
        return map;

    }

    public Map doInvSelectSN(String InWipRcpId, String InItemLocId, String InBlockFrom, String InBlockTo, String InQty, String InUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InWipRcpId", InWipRcpId);
        map.put("InItemLocId", InItemLocId);
        map.put("InBlockFrom", InBlockFrom);
        map.put("InBlockTo", InBlockTo);
        map.put("InQty", InQty);
        map.put("InUserId", InUserId);
        showLog(map, "doInvSelectSN");
        new Implement().doInvSelectSN(map);
        return map;

    }

    public Map doInvUnselectSN(String InWipRcpId, String InItemLocId, String InBlockFrom, String InBlockTo, String InQty, String InUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InWipRcpId", InWipRcpId);
        map.put("InItemLocId", InItemLocId);
        map.put("InBlockFrom", InBlockFrom);
        map.put("InBlockTo", InBlockTo);
        map.put("InQty", InQty);
        map.put("InUserId", InUserId);
        showLog(map, "doInvUnselectSN");
        new Implement().doInvUnselectSN(map);
        return map;

    }

    public List<ListselectParam> getListInvSelectSN(String InWipRcpId, String pageNumber, String pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InWipRcpId", InWipRcpId);
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);
        return new Implement().getListInvSelectSN(map);
    }

    public List<Integer> getCountListInvSelectSN(String InWipRcpId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InWipRcpId", InWipRcpId);
        return new Implement().getCountListInvSelectSN(map);

    }

    public Map dorunningAuto(String InWoId, String InWipWhId, String InUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InWoId", InWoId);
        map.put("InWipWhId", InWipWhId);
        map.put("InUserId", InUserId);
        new Implement().dorunningAuto(map);
        return map;
    }

    public List<ListShopFloorParam> getListShopFloor() {
        return getListShopFloor(null, null, null, null, null, null, null, null, null);
    }

    public List<ListShopFloorParam> getListShopFloor(String InBuId, String InSfcId, String InSfcDateFrom, String InSfcDateTo, String InWoId, String InWoType, String InItemId, String InWhID, String InStatus) {
        Map map = new HashMap();
        map.put("InBuId", InBuId == null ? "" : InBuId);
        map.put("InSfcId", InSfcId == null ? "" : InSfcId);
        map.put("InSfcDateFrom", InSfcDateFrom == null ? "" : InSfcDateFrom);
        map.put("InSfcDateTo", InSfcDateTo == null ? "" : InSfcDateTo);
        map.put("InWoId", InWoId == null ? "" : InWoId);
        map.put("InWoType", InWoType == null ? "" : InWoType);
        map.put("InItemId", InItemId == null ? "" : InItemId);
        map.put("InWhID", InWhID == null ? "" : InWhID);
        map.put("InStatus", InStatus == null ? "" : InStatus);
        return new Implement().getListShopFloor(map);
    }

    public Map doCreateShopFloor(String sfcBuId, String sfcType, String sfcWoId, String sfcItemId, String sfcWhWIPId, String sfcQty, String sfcStatus, String sfcUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("sfcBuId", sfcBuId);
        map.put("sfcType", sfcType);
        map.put("sfcWoId", sfcWoId);
        map.put("sfcItemId", sfcItemId);
        map.put("sfcWhWIPId", sfcWhWIPId);
        map.put("sfcQty", sfcQty);
        map.put("sfcStatus", sfcStatus);
        map.put("sfcUserId", sfcUserId);
        new Implement().doCreateShopFloor(map);
        return map;
    }

    public Map doUpdateShopFloor(String sfcId, String sfcDate, String sfcType, String sfcWoId, String sfcItemId, String sfcWhWIPId, String sfcQty, String sfcUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("sfcId", sfcId);
        map.put("sfcDate", sfcDate);
        map.put("sfcType", sfcType);
        map.put("sfcWoId", sfcWoId);
        map.put("sfcItemId", sfcItemId);
        map.put("sfcWhWIPId", sfcWhWIPId);
        map.put("sfcQty", sfcQty);
        map.put("sfcUserId", sfcUserId);
        new Implement().doUpdateShopFloor(map);
        return map;
    }

    public Map doApproveShopFloor(String sfcId, String sfcDate, String sfcType, String sfcWoId, String sfcItemId, String sfcWhWIPId, String sfcQty, String sfcUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("sfcId", sfcId);
        map.put("sfcDate", sfcDate);
        map.put("sfcType", sfcType);
        map.put("sfcWoId", sfcWoId);
        map.put("sfcItemId", sfcItemId);
        map.put("sfcWhWIPId", sfcWhWIPId);
        map.put("sfcQty", sfcQty);
        map.put("sfcUserId", sfcUserId);
        new Implement().doApproveShopFloor(map);
        return map;
    }

    public Map doSubmitShopFloor(String sfcId, String sfcUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("sfcId", sfcId);
        map.put("sfcUserId", sfcUserId);
        new Implement().doSubmitShopFloor(map);
        return map;
    }

    public Map doCancelShopFloor(String sfcId, String sfcUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("sfcId", sfcId);
        map.put("sfcUserId", sfcUserId);
        new Implement().doCancelShopFloor(map);
        return map;
    }

    public Map doValidateQuantityComplete(String sfcWoId, String sfcItemId, String sfcQty) {
        Map<String, Object> map = new HashMap<>();
        map.put("sfcWoId", sfcWoId);
        map.put("sfcItemId", sfcItemId);
        map.put("sfcQty", sfcQty);
        new Implement().doValidateQuantityComplete(map);
        return map;
    }

    public List<ListShopFloorDtlParam> getListShopFloorDtl(String InSfcId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InSfcId", InSfcId);
        return new Implement().getListShopFloorDtl(map);
    }

    public List<ListShopFloorParam> selectListHdrSFC(String InSfcId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sfc_id", InSfcId);
        return new Implement().selectListHdrSFC(map);
    }

    public List<ListSelectSNTempParam> getListSelectSNTempSFC(String InWIPRcpDtlId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InWIPRcpDtlId", InWIPRcpDtlId);
        return new Implement().getListSelectSNTempSFC(map);
    }

    public List<ListInvSelectSNParam> getListInvSelectSNSFC(String InProdIssueDtlId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InProdIssueDtlId", InProdIssueDtlId);
        return new Implement().getListInvSelectSNSFC(map);
    }

    public Map doValidateSelectSnSFC(String InItemDtlId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InItemDtlId", InItemDtlId);
        new Implement().doValidateSelectSnSFC(map);
        return map;

    }

    public Map doPairingValidateInsertHdr(String InPoId, String InItemId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InPoId", InPoId);
        map.put("InItemId", InItemId);
        new Implement().doPairingValidateInsertHdr(map);
        return map;

    }

    public Map doPairingInsertHdr(String InPoId, String InItemId, Double InQuantity, String InUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InPoId", InPoId);
        map.put("InItemId", InItemId);
        map.put("InQuantity", InQuantity);
        map.put("InUserId", InUserId);
        new Implement().doPairingInsertHdr(map);
        return map;

    }

    public List<ListPairingHdrParam> getPairingListHdr(String InPairingId) {
        return getPairingListHdr(InPairingId, null);

    }

    public List<ListPairingHdrParam> getPairingListHdr(String InPairingId, Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        map.put("InPairingId", InPairingId == null ? "" : InPairingId);
        if (searchMap != null) {
            map.put("search", searchMap);
        }
        return new Implement().getPairingListHdr(map);

    }

    public Map doPairingValidateUpdateHdr(String InPairingId, String InPoId, String InItemId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InPairingId", InPairingId);
        map.put("InPoId", InPoId);
        map.put("InItemId", InItemId);
        new Implement().doPairingValidateUpdateHdr(map);
        return map;

    }

    public Map doPairingUpdateHdr(String InPairingId, String InPoId, String InItemId, Double InQuantity, String InUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InPairingId", InPairingId);
        map.put("InPoId", InPoId);
        map.put("InItemId", InItemId);
        map.put("InQuantity", InQuantity);
        map.put("InUserId", InUserId);
        new Implement().doPairingUpdateHdr(map);
        return map;

    }

    public Map doPairingSubmitHdr(String InPairingId, String InUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InPairingId", InPairingId);
        map.put("InUserId", InUserId);
        new Implement().doPairingSubmitHdr(map);
        return map;

    }

    public Map doPairingCancelHdr(String InPairingId, String InUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InPairingId", InPairingId);
        map.put("InUserId", InUserId);
        new Implement().doPairingCancelHdr(map);
        return map;

    }

    public Map doPairingApproveHdr(String InPairingId, String InUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InPairingId", InPairingId);
        map.put("InUserId", InUserId);
        new Implement().doPairingApproveHdr(map);
        return map;

    }

    public List<ListPairingDTLParam> getPairingListDtl(String InPairingId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InPairingId", InPairingId);
        return new Implement().getPairingListDtl(map);
    }

    public Map doInsertMappingbdl(String itembdlid, String itemid, String p_uom, String p_compo, String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itembdlid", itembdlid);
        map.put("itemid", itemid);
        map.put("p_uom", p_uom);
        map.put("p_compo", p_compo);
        map.put("userId", userId);
        showLog(map, "doInsertMappingbdl");
        new Implement().doInsertMappingbdl(map);
        return map;
    }

    public Map doModifyMappingbdl(String Inid, String itemid, String p_uom, String p_compo, String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Inid", Inid);
        map.put("itemid", itemid);
        map.put("p_uom", p_uom);
        map.put("p_compo", p_compo);
        map.put("userId", userId);
        new Implement().doModifyMappingbdl(map);
        return map;
    }

    public List<ListHDRMappingBDL> getListHeaderbdl() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("Inbdl", Inbdl);
        return getListHeaderbdl(null);

    }

    public List<ListHDRMappingBDL> getListHeaderbdl(String Inbdl) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Inbdl", Inbdl == null ? "" : Inbdl);
        return new Implement().getListHeaderbdl(map);

    }

    public List<ListDTLMappingBDL> getListDetailBdl(String InId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InId", InId);
        return new Implement().getListDetailBdl(map);

    }

    public Map doExpireMappingbdl(String Inid, String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Inid", Inid);
        map.put("userId", userId);
        showLog(map, "doExpireMappingbdl");
        new Implement().doExpireMappingbdl(map);
        return map;

    }

    public Map doSetDefaultSN(String InId, String InItemBdlId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InId", InId);
        map.put("InItemBdlId", InItemBdlId);
        new Implement().doSetDefaultSN(map);
        return map;
    }

//    -----------------------------   IFM -------------------------------------------
    public void mapLog(Map<String, Object> map, String methode) {
        System.out.println("methode:" + methode);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + ":" + value);
        }
        System.out.println("\n");
    }

    public Map doWoSaveHeader(String inAction, String inPoId, String inBuId, String inSupplierId, String inSupplierCode,
            String inSiteId, String inContractId, String inContractNo, String inUserId, String inOrderType, String inStatus, String inRemarks,
            String inTopId, String inTopDesc, String inOrderDate) {
        Map<String, Object> maper = new HashMap<String, Object>();
        System.out.println("doTrcSaveTransfer");
        System.out.println("inAction=" + inAction);
        System.out.println("inPoId=" + inPoId);
        System.out.println("inBuId=" + inBuId);
        System.out.println("inSupplierId=" + inSupplierId);
        System.out.println("inSupplierCode=" + inSupplierCode);
        System.out.println("inSiteId=" + inSiteId);
        System.out.println("inContractId=" + inContractId);
        System.out.println("inContractNo=" + inContractNo);
        System.out.println("inUserId=" + inUserId);
        System.out.println("inOrderType=" + inOrderType);
        System.out.println("inStatus=" + inStatus);
        System.out.println("inRemarks=" + inRemarks);
        System.out.println("inTopId=" + inTopId);
        System.out.println("inTopDesc=" + inTopDesc);
        System.out.println("inOrderDate=" + inOrderDate);
        maper.put("inAction", inAction);
        maper.put("inPoId", inPoId);
        maper.put("inBuId", inBuId);
        maper.put("inSupplierId", inSupplierId);
        maper.put("inSupplierCode", inSupplierCode);
        maper.put("inSiteId", inSiteId);
        maper.put("inContractId", inContractId);
        maper.put("inContractNo", inContractNo);
        maper.put("inCurrency", "IDR");
        maper.put("inUserId", inUserId);
        maper.put("inOrderType", inOrderType);
        maper.put("inOrderDate", inOrderDate);
        maper.put("inStatus", inStatus);
        maper.put("inRemarks", inRemarks);
        maper.put("inTopId", inTopId);
        maper.put("inTopDesc", inTopDesc);
        Map map = new Implement().doWoSaveHeader(maper);
        mapLog(maper, "doWoSaveHeader");
        return map;
//        mapLog(maper, inBuId);
    }

    public List<ListWoParam> selectallWo(String userId, String respId) {
        return selectallWo(userId, respId, null, null, null, null, null, null);
    }

    public List<ListWoParam> selectallWo(String userId, String respId, String poNo, String buCode,
            String status, String orderType, String dateFrom, String dateTo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("userId", userId);
        maper.put("respId", respId);
        maper.put("poNo", poNo);
        maper.put("buCode", buCode);
        maper.put("status", status);
        maper.put("orderType", orderType);
        maper.put("dateFrom", dateFrom);
        maper.put("dateTo", dateTo);
        return new Implement().selectallWo(maper);
    }

    public List<ListWoParam> selectListWo(String userId, String respId, String poNo, String buCode,
            String status, String orderType, String dateFrom, String dateTo,
            String pageNumber, String pageSize) {

        System.out.println("selectListWo");
        System.out.println("userId=" + userId);
        System.out.println("respId=" + respId);
        System.out.println("poNo=" + poNo);
        System.out.println("buCode=" + buCode);
        System.out.println("buCode=" + buCode);
        System.out.println("status=" + status);
        System.out.println("orderType=" + orderType);
        System.out.println("pageNumber=" + pageNumber);
        System.out.println("pageSize=" + pageSize);
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("userId", userId);
        maper.put("respId", respId);
        maper.put("poNo", poNo);
        maper.put("buCode", buCode);
        maper.put("status", status);
        maper.put("orderType", orderType);
        maper.put("dateFrom", dateFrom);
        maper.put("dateTo", dateTo);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().selectListWo(maper);
    }

    public List<ListWoParam> selectWo(String userId, String respId, String poId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("userId", userId);
        maper.put("respId", respId);
        maper.put("poId", poId);
        return new Implement().selectWo(maper);
    }

    public List<Integer> getCountWo(String userId, String respId, String poNo, String buCode,
            String status, String orderType, String dateFrom, String dateTo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("userId", userId);
        maper.put("respId", respId);
        maper.put("poNo", poNo);
        maper.put("buCode", buCode);
        maper.put("status", status);
        maper.put("orderType", orderType);
        maper.put("dateFrom", dateFrom);
        maper.put("dateTo", dateTo);
        showLog(maper, "getCountWo");
        return new Implement().getCountWo(maper);
    }

    public Map doWoSaveDtl(String inReqId, String inReqLineId, String inContractId, String inPoId, String inPoNo, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReqId", inReqId);
        maper.put("inReqLineId", inReqLineId);
        maper.put("inContractId", inContractId);
        maper.put("inPoId", inPoId);
        maper.put("inPoNo", inPoNo);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doWoSaveDtl(maper);
        mapLog(maper, "doWoSaveDtl");
        return map;
    }

    public Map doWoValidateAddLine(String inWoStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inWoStatus", inWoStatus);
        return new Implement().doWoValidateAddLine(maper);
    }

    public List<ListWoListAddLineHdrParam> selectWoListAddLineHdr(String userId, String respId, String inContractId, String inOrderType) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("userId", userId);
        maper.put("respId", respId);
        maper.put("inContractId", inContractId);
        maper.put("inOrderType", inOrderType);
        return new Implement().selectWoListAddLineHdr(maper);
    }

    public List<ListWoListAddLineDtlParam> selectWoListAddLineDtl(String inReqId, String inContractId, String inOrderType) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReqId", inReqId);
        maper.put("inContractId", inContractId);
        maper.put("inOrderType", inOrderType);
        return new Implement().selectWoListAddLineDtl(maper);
    }

    public List<ListWoListDtlParam> selectWoListDtl(String poId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("poId", poId);
        return new Implement().selectWoListDtl(maper);
    }

    public List<ListWoListHLRParam> selectWoListHLR(String poId, String poLine) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("poId", poId);
        maper.put("poLine", poLine);
        return new Implement().selectWoListHLR(maper);
    }

    public List<ListWoListHLRDtlParam> selectWoListHLRDtl(String poId, String poLine, String orderType, String hlrMapId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("poId", poId);
        maper.put("poLine", poLine);
        maper.put("orderType", orderType);
        maper.put("hlrMapId", hlrMapId);
        return new Implement().selectWoListHLRDtl(maper);
    }

    public Map doWoSaveHlr(String inPoId, String inPoLine, String inPoLineId, String inItemLocId, String inHlrMapId, String inOrderType, String inQty) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inPoLine", inPoLine);
        maper.put("inPoLineId", inPoLineId);
        maper.put("inItemLocId", inItemLocId);
        maper.put("inHlrMapId", inHlrMapId);
        maper.put("inOrderType", inOrderType);
        maper.put("inQty", inQty);
        Map map = new Implement().doWoSaveHlr(maper);
        mapLog(maper, "doWoSaveHlr");
        return map;
    }

    public Map doWoDeleteHlr(String inPoId, String inOrderType, String inDtlHlrPoId, String inPoQty) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inOrderType", inOrderType);
        maper.put("inDtlHlrPoId", inDtlHlrPoId);
        maper.put("inPoQty", inPoQty);
        Map map = new Implement().doWoDeleteHlr(maper);
        mapLog(maper, "doWoDeleteHlr");
        return map;
    }

    public List<ListWoListAllocateNumberingTempParam> selectWoListAllocateNumberingTemp(String inItemLocId, String inHlrMapId, String inUserId,
            String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inItemLocId", inItemLocId);
        maper.put("inHlrMapId", inHlrMapId);
        maper.put("inUserId", inUserId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().selectWoListAllocateNumberingTemp(maper);
    }

    public List<ListWoListAllocateNumberingTempParam> selectWoListAllocateINVTemp(String inItemLocId, String inHlrMapId, String inUserId,
            String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inItemLocId", inItemLocId);
        maper.put("inHlrMapId", inHlrMapId);
        maper.put("inUserId", inUserId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().selectWoListAllocateINVTemp(maper);
    }

    public List<Integer> getCountAllocateNBR(String inItemLocId, String inHlrMapId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inItemLocId", inItemLocId);
        maper.put("inHlrMapId", inHlrMapId);
        maper.put("inUserId", inUserId);
        return new Implement().getCountAllocateNBR(maper);
    }

    public List<Integer> getCountAllocateINV(String inItemLocId, String inHlrMapId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inItemLocId", inItemLocId);
        maper.put("inHlrMapId", inHlrMapId);
        maper.put("inUserId", inUserId);
        return new Implement().getCountAllocateNBR(maper);
    }

    public Map doWoAllocateINVTemp(String inItemLocId, String inHlrMapId, String inUserId) {
        System.out.println("doWoAllocateINVTemp");
        System.out.println("inItemLocId=" + inItemLocId);
        System.out.println("inHlrMapId=" + inHlrMapId);
        System.out.println("inUserId=" + inUserId);
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inItemLocId", inItemLocId);
        maper.put("inHlrMapId", inHlrMapId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doWoAllocateINVTemp(maper);
        mapLog(maper, "doWoAllocateINVTemp");
        return map;
    }

    public Map doWoAllocateNBRTemp(String inItemLocId, String inHlrMapId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inItemLocId", inItemLocId);
        maper.put("inHlrMapId", inHlrMapId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doWoAllocateNBRTemp(maper);
        mapLog(maper, "doWoAllocateNBRTemp");
        return map;
    }

    public Map doWoAllocateNBR(String inPoId, String inPoLine, String inItemLocId, String inDtlHlrPoId, String inRangeQty,
            String inRangeFrom, String inRangeTo, String inStoreStatus, String inTempQty, String inTempFrom, String inTempTo,
            String inOrderQty, String inHlrMapId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inPoLine", inPoLine);
        maper.put("inItemLocId", inItemLocId);
        maper.put("inDtlHlrPoId", inDtlHlrPoId);
        maper.put("inHlrMapId", inHlrMapId);
        maper.put("inRangeQty", inRangeQty);
        maper.put("inRangeFrom", inRangeFrom);
        maper.put("inRangeTo", inRangeTo);
        maper.put("inStoreStatus", inStoreStatus);
        maper.put("inTempQty", inTempQty);
        maper.put("inTempFrom", inTempFrom);
        maper.put("inTempTo", inTempTo);
        maper.put("inOrderQty", inOrderQty);
        Map map = new Implement().doWoAllocateNBR(maper);
        mapLog(maper, "doWoAllocateNBR");
        return map;
    }

    public List<ListWoListAllocateNBRParam> selectWoListAllocateNBR(String poId, String poLine,
            String pageNumber, String pageSize) {
//    public List<ListWoListAllocateNBRParam> selectWoListAllocateNBR(String poId,String poLine) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("poId", poId);
        maper.put("poLine", poLine);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().selectWoListAllocateNBR(maper);
    }

    public List<Integer> getCountAllocateNBRDtl(String poId, String poLine) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("poId", poId);
        maper.put("poLine", poLine);
        return new Implement().getCountAllocateNBRDtl(maper);
    }

    public List<Integer> getCountAllocateINVDtl(String inDtlPoId, String inItemLocId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inDtlPoId", inDtlPoId);
        maper.put("inItemLocId", inItemLocId);
        return new Implement().getCountAllocateINVDtl(maper);
    }

    public List<ListWoListAllocateINVParam> selectWoListAllocateINV(String inDtlPoId, String inItemLocId,
            String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inDtlPoId", inDtlPoId);
        maper.put("inItemLocId", inItemLocId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().selectWoListAllocateINV(maper);
    }

    public Map doWoValidateStatus(String inPoId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        return new Implement().doWoValidateStatus(maper);
    }

    public Map doWoSelectAllocationNBRDtl(String inDtlHlrPoId, String inRangeQty, String inRangeFrom,
            String inRangeTo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inDtlHlrPoId", inDtlHlrPoId);
        maper.put("inRangeFrom", inRangeFrom);
        maper.put("inRangeTo", inRangeTo);
        maper.put("inRangeQty", inRangeQty);
        Map map = new Implement().doWoSelectAllocationNBRDtl(maper);
        mapLog(maper, "doWoSelectAllocationNBRDtl");
        return map;
    }

    public Map doWoUpdateDtl(String inPoId, String inPoLineId, String inPoLine,
            String inItemId, String inItemCode, String inItemPrice, String inContractId, String inContractDtlId, String inReqId,
            String inPctDisc, String inAmtDisc, String inReqLine, String inOrderQty, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inPoLineId", inPoLineId);
        maper.put("inPoLine", inPoLine);
        maper.put("inItemId", inItemId);
        maper.put("inItemCode", inItemCode);
        maper.put("inItemPrice", inItemPrice);
        maper.put("inContractId", inContractId);
        maper.put("inContractDtlId", inContractDtlId);
        maper.put("inReqId", inReqId);
        maper.put("inPctDisc", inPctDisc);
        maper.put("inAmtDisc", inAmtDisc);
        maper.put("inReqLine", inReqLine);
        maper.put("inOrderQty", inOrderQty);
        maper.put("inExpiredDate", "");
        maper.put("inUserId", inUserId);
        mapLog(maper, "doWoUpdateDtl");
        Map map = new Implement().doWoUpdateDtl(maper);
        return map;
    }

    public Map doWoDeleteDtl(String inPoLineId, String poLine, String inContractId, String inContractDtlId, String inReqId,
            String inReqLine, String inOrderQty, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoLineId", inPoLineId);
        maper.put("poLine", poLine);
        maper.put("inContractId", inContractId);
        maper.put("inContractDtlId", inContractDtlId);
        maper.put("inReqId", inReqId);
        maper.put("inReqLine", inReqLine);
        maper.put("inOrderQty", inOrderQty);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doWoDeleteDtl(maper);
        mapLog(maper, "doWoDeleteDtl");
        return map;
    }
    
    public Map dowoUploadSNCheck(String InUploadId,String InPoId,String InPoLine){
        Map<String,Object>map = new HashMap();
        map.put("InUploadId",InUploadId);
        map.put("InPoId",InPoId);
        map.put("InPoLine",InPoLine);
        showLog(map, "dowoUploadSNCheck");
        new Implement().dowoUploadSNCheck(map);
        return map;
    }

    public Map doWoSelectAllocationINV(String inPoId, String inPoLineId, String inPoLine,
            String inItemLocId, String inHlrMapId, String inStoreStatus, String inRangeFrom,
            String inRangeTo, String inOrderQty, String inDtlPoId, String inDtlHlrPoId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inPoLine", inPoLine);
        maper.put("inPoLineId", inPoLineId);
        maper.put("inDtlPoId", inDtlPoId);
        maper.put("inItemLocId", inItemLocId);
        maper.put("inStoreStatus", inStoreStatus);
        maper.put("inRangeFrom", inRangeFrom);
        maper.put("inRangeTo", inRangeTo);
        maper.put("inRangeQty", inOrderQty);
        maper.put("inSessionId", "");
        maper.put("inDtlHlrPoId", inDtlHlrPoId);
        Map map = new Implement().doWoSelectAllocationINV(maper);
        mapLog(maper, "doWoSelectAllocationINV");
        return map;
    }

    public Map doWoUnSelectAllocationINV(String inPoId, String inPoLine, String inPoLineId, String inDtlPoId, String inDtlHlrPoId, String inItemLocId, String inStoreStatus, String inRangeFrom, String inRangeTo, String inOrderQty) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inPoLine", inPoLine);
        maper.put("inPoLineId", inPoLineId);
        maper.put("inDtlPoId", inDtlPoId);
        maper.put("inDtlHlrPoId", inDtlHlrPoId);
        maper.put("inItemLocId", inItemLocId);
        maper.put("inStoreStatus", inStoreStatus);
        maper.put("inRangeFrom", inRangeFrom);
        maper.put("inRangeTo", inRangeTo);
        maper.put("inRangeQty", inOrderQty);
        maper.put("inSessionId", "");
        Map map = new Implement().doWoUnSelectAllocationINV(maper);
        mapLog(maper, "doWoUnSelectAllocationINV");
        return map;
    }

    public Map doWoUnSelectAllocateNBR(String inPoId, String inPoLine, String inPosNum,
            String inDtlHlrPoId, String inItemLocId,
            String inRangeFrom, String inRangeTo, String inRangeQty) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inPoLine", inPoLine);
        maper.put("inPosNum", inPosNum);
        maper.put("inDtlHlrPoId", inDtlHlrPoId);
        maper.put("inRangeFrom", inRangeFrom);
        maper.put("inRangeTo", inRangeTo);
        maper.put("inRangeQty", inRangeQty);
        maper.put("inItemLocId", inItemLocId);
        Map map = new Implement().doWoUnSelectAllocateNBR(maper);
        mapLog(maper, "doWoUnSelectAllocateNBR");
        return map;
    }

    public Map doWoReleased(String inPoId, String inPoLine, String inWoStatus,
            String inBuId, String inUserId, String inResponId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inWoStatus", inWoStatus);
        maper.put("inBuId", inBuId);
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        Map map = new Implement().doWoReleased(maper);
        mapLog(maper, "doWoReleased");
        return map;
    }

    public Map doWoSubmit(String inPoId, String inPoLine, String inWoStatus,
            String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inWoStatus", inWoStatus);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doWoSubmit(maper);
        mapLog(maper, "doWoSubmit");
        return map;
    }

    public Map doWoCancel(String inPoId, String inWoStatus, String inPoLine,
            String InPoLineId, String InOrderQty, String InReqId, String InReqLine, String InItemId, String InContractId,
            String InContractDtlId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inWoStatus", inWoStatus);
        maper.put("inPoLine", inPoLine);
        maper.put("inPoLineId", InPoLineId);
        maper.put("inOrderQty", InOrderQty);
        maper.put("inReqId", InReqId);
        maper.put("inReqLine", InReqLine);
        maper.put("inItemId", InItemId);
        maper.put("inContractId", InContractId);
        maper.put("inContractDtlId", InContractDtlId);
        mapLog(maper, "doWoCancel");
        Map map = new Implement().doWoCancel(maper);
        mapLog(maper, "doWoCancel");
        return map;
    }

    public Map doWoCancelCommit(String inPoId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doWoCancelCommit(maper);
        mapLog(maper, "doWoCancelCommit");
        return map;
    }
    public Map doWoCancelnew(String inPoId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inUserId", inUserId);
        mapLog(maper, "doWoCancelnew");
        Map map = new Implement().doWoCancelnew(maper);
        return map;
    }

    public Map doWoSetArtWork(String inPoId, String inPolineId, String inArtId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        System.out.println("doWoCancelCommit");
        maper.put("inPoId", inPoId);
        maper.put("inPolineId", inPolineId);
        maper.put("inArtId", inArtId);
        Map map = new Implement().doWoSetArtWork(maper);
        mapLog(maper, "doWoSetArtWork");
        return map;
    }

    public Map doGenerateSN(String inPoId, String inWoStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inWoStatus", inWoStatus);
        Map map = new Implement().doGenerateSN(maper);
        mapLog(maper, "doGenerateSN");
        return map;
    }

    public List<ListWoListSNParam> selectWoListSN(String inPoId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        System.out.println("inPoId=" + inPoId);
        maper.put("poId", inPoId);
        return new Implement().selectWoListSN(maper);
    }

    public Map doWoCreateLineDetail(String inPoId, String inPoLineId, String inDtlPoId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inPoLineId", inPoLineId);
        maper.put("inDtlPoId", inDtlPoId);
        Map map = new Implement().doWoCreateLineDetail(maper);
        mapLog(maper, "doWoCreateLineDetail");
        return map;
    }

    //goodDelivery
    List<GdListHdrParam> getGdListHdr(String InSuppDeliveryId, String inPoNo, String inGdNo, String inStartDate, String inEndDate, String inStatus,String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InSuppDeliveryId", InSuppDeliveryId);
        maper.put("inPoNo", inPoNo);
        maper.put("inGdNo", inGdNo);
        maper.put("inStartDate", inStartDate);
        maper.put("inEndDate", inEndDate);
        maper.put("inStatus", inStatus);
        maper.put("InUserId", userId);
        maper.put("InResponId", responsibilityId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        mapLog(maper, "getGdListHdr");
        return new Implement().getGdListHdr(maper);
    }

    List<GdListHdrParam> selectGdListHdr(String InSuppDeliveryId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("suppdeliveryid", InSuppDeliveryId);
        maper.put("InUserId", userId);
        maper.put("InResponId", responsibilityId);
        return new Implement().selectGdListHdr(maper);
    }

    List<BundlingGDListHDRParam> selectGdListHdrBundling(String InSuppDeliveryId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("suppdeliveryid", InSuppDeliveryId);
        maper.put("InUserId", userId);
        maper.put("InResponId", responsibilityId);
        return new Implement().selectGdListHdrBundling(maper);
    }

    List<Integer> getCountGdListHdr(String InSuppDeliveryId, String inPoNo, String inGdNo, String inStartDate, String inEndDate, String inStatus) {
        Map<String, Object> maper = new HashMap<>();
        maper.put("InSuppDeliveryId", InSuppDeliveryId);
        maper.put("inPoNo", inPoNo);
        maper.put("inGdNo", inGdNo);
        maper.put("inStartDate", inStartDate);
        maper.put("inEndDate", inEndDate);
        maper.put("inStatus", inStatus);
        maper.put("InUserId", userId);
        maper.put("InResponId", responsibilityId);
        mapLog(maper, "getCountGdListHdr");
        return new Implement().getCountGdListHdr(maper);
    }

    public Map doGdSaveHdrBundling(String InAction, String InSuppDeveryId, String InSuppDeliverDate, String InBuId,
            String InSupplierId, String InSiteId, String InBundlingId, String InDeliverDate, String InWhId, String InForwardAgent,
            String InArrivedDateFrom, String InArrivedDateTo, String InStatus, String InRemark, String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inAction", InAction);
        maper.put("inSuppDeveryId", InSuppDeveryId);
        maper.put("inSuppDeliverDate", InSuppDeliverDate);
        maper.put("inBuId", InBuId);
        maper.put("inSupplierId", InSupplierId);
        maper.put("inSiteId", InSiteId);
        maper.put("InBundlingId", InBundlingId);
        maper.put("inDeliverDate", InDeliverDate);
        maper.put("inWhId", InWhId);
        maper.put("inForwardAgent", InForwardAgent);
        maper.put("inArrivedDateFrom", InArrivedDateFrom);
        maper.put("inArrivedDateTo", InArrivedDateTo);
        maper.put("inStatus", InStatus);
        maper.put("inRemark", InRemark);
        maper.put("inUserId", InUserId);
        Map map = new Implement().doGdSaveHdrBundling(maper);
        mapLog(maper, "doGdSaveHdrbundling");
        return map;
    }

    public Map doGdSaveHdr(String InAction, String InSuppDeveryId, String InSuppDeliverDate, String InBuId,
            String InSupplierId, String InSiteId, String InPoId, String InDeliverDate, String InWhId, String InForwardAgent,
            String InArrivedDateFrom, String InArrivedDateTo, String InStatus, String InRemark, String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inAction", InAction);
        maper.put("inSuppDeveryId", InSuppDeveryId);
        maper.put("inSuppDeliverDate", InSuppDeliverDate);
        maper.put("inBuId", InBuId);
        maper.put("inSupplierId", InSupplierId);
        maper.put("inSiteId", InSiteId);
        maper.put("inPoId", InPoId);
        maper.put("inDeliverDate", InDeliverDate);
        maper.put("inWhId", InWhId);
        maper.put("inForwardAgent", InForwardAgent);
        maper.put("inArrivedDateFrom", InArrivedDateFrom);
        maper.put("inArrivedDateTo", InArrivedDateTo);
        maper.put("inStatus", InStatus);
        maper.put("inRemark", InRemark);
        maper.put("inUserId", InUserId);
        Map map = new Implement().doGdSaveHdr(maper);
        mapLog(maper, "doGdSaveHdr");
        return map;
    }

    List<ListGdItemParam> selectGdListPoItem(String poId, String whId, String gdId) {
        System.out.println("poId=" + poId);
        System.out.println("whId=" + whId);
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("poId", poId);
        maper.put("whId", whId);
        maper.put("gdId", gdId);
        return new Implement().selectGdListPoItem(maper);
    }

    List<GdListPoItemBundlingParam> selectGdListPoItemBundling(String InBuId, String whId, String gdId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InBuId", InBuId);
        maper.put("whId", whId);
        maper.put("gdId", gdId);
        showLog(maper, "selectGdListPoItemBundling");
        return new Implement().selectGdListPoItemBundling(maper);
    }

    public Map doGdSaveDtl(String inAction, String inGdHdrId, String inPoId, String inPositionNum, String inLineNo,String inItemid, String inQty,
            String inOrderedQty, String inWhId, String inCityid, String inHlrMapId, String inOrderDate,String inLineRemark , String inUserId, String inPoPriceUnit, String inGdDtlId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inAction", inAction);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inPoId", inPoId);
        maper.put("inPositionNum", inPositionNum);
        maper.put("inLineNo", inLineNo);
        maper.put("inItemid", inItemid);
        maper.put("inQty", inQty);
        maper.put("inOrderedQty", inOrderedQty);
        maper.put("inWhId", inWhId);
        maper.put("inCityid", inCityid);
        maper.put("inHlrMapId", inHlrMapId);
        maper.put("inOrderDate", inOrderDate);
        maper.put("inLineRemark", inLineRemark);
        maper.put("inUserId", inUserId);
        maper.put("inPoPriceUnit", inPoPriceUnit);
        maper.put("inGdDtlId", inGdDtlId);
        mapLog(maper, "doGdSaveDtl");
        Map map = new Implement().doGdSaveDtl(maper);
        return map;

    }

    public Map doGdSaveDtlBundling(String inAction, String inGdHdrId, String InPoId, String inPositionNum, String inLineNo, String inItemid, String inQty,
            String inOrderedQty, String inWhId, String inCityid, String inHlrMapId, String inOrderDate, String inLineRemark, String inUserId, String inPoPriceUnit, String inGdDtlId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inAction", inAction);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("InPoId", InPoId);
        maper.put("inPositionNum", inPositionNum);
        maper.put("inLineNo", inLineNo);
        maper.put("inItemid", inItemid);
        maper.put("inQty", inQty);
        maper.put("inOrderedQty", inOrderedQty);
        maper.put("inWhId", inWhId);
        maper.put("inCityid", inCityid);
        maper.put("inHlrMapId", inHlrMapId);
        maper.put("inOrderDate", inOrderDate);
        maper.put("inLineRemark", inLineRemark);
        maper.put("inUserId", inUserId);
        maper.put("inPoPriceUnit", inPoPriceUnit);
        maper.put("inGdDtlId", inGdDtlId);
        mapLog(maper, "doGdSaveDtlbundling");
        Map map = new Implement().doGdSaveDtlBundling(maper);
        return map;

    }

    List<ListGdDtlBundlingParam> selectGdListDtlBundling(String gdId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("gdId", gdId);
        return new Implement().selectGdListDtlBundling(maper);
    }
    
    List<ListGdDtl> selectGdListDtl(String gdId) {
        System.out.println("poId=" + gdId);
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("gdId", gdId);
        return new Implement().selectGdListDtl(maper);
    }

    public Map doGdSubmitDtlValidateLine(String inGdHdrId, String inLineNo, String inItemId, String inItemCode,
            String inDeliverQty) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inLineNo", inLineNo);
        maper.put("inItemId", inItemId);
        maper.put("inItemCode", inItemCode);
        maper.put("inDeliverQty", inDeliverQty);
        Map map = new Implement().doGdSubmitDtlValidateLine(maper);
        mapLog(maper, "doGdSubmitDtlValidateLine");
        return map;
    }

    public Map doGdSubmitDtlValidateLineBundling(String inGdHdrId, String inLineNo, String inItemId, String inItemCode,
            String inDeliverQty) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inLineNo", inLineNo);
        maper.put("inItemId", inItemId);
        maper.put("inItemCode", inItemCode);
        maper.put("inDeliverQty", inDeliverQty);
        mapLog(maper, "doGdSubmitDtlValidateLineBundling");
        Map map = new Implement().doGdSubmitDtlValidateLineBundling(maper);
        return map;
    }

    public Map doGdSubmitValidate(String inGdHdrId, String inUserId, String inResponId, String inBuId,
            String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGdSubmitValidate(maper);
        mapLog(maper, "doGdSubmitValidate");
        return map;
    }

    public Map doGdSubmitValidateBundling(String inGdHdrId, String inUserId, String inResponId, String inBuId,
            String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGdSubmitValidateBundling(maper);
        mapLog(maper, "doGdSubmitValidate");
        return map;
    }

    public Map doGdSubmit(String inGdHdrId,String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("InUserId", InUserId);
        Map map = new Implement().doGdSubmit(maper);
        mapLog(maper, "doGdSubmit");
        return map;
    }

    public Map doGdSubmitBundling(String inGdHdrId,String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("InUserId", InUserId);
        Map map = new Implement().doGdSubmitBundling(maper);
        mapLog(maper, "doGdSubmit");
        return map;
    }

    public Map doGdApprove(String inGdHdrId, String inUserId, String inResponId, String inBuId,
            String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGdApprove(maper);
        mapLog(maper, "doGdApprove");
        return map;
    }

    public Map doGdApproveBundling(String inGdHdrId, String inUserId, String inResponId, String inBuId,
            String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGdApproveBundling(maper);
        mapLog(maper, "doGdApprove");
        return map;
    }

    public Map doGdSelectPOValidate(String inPoId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        System.out.println("doGdSelectPOValidate");
        System.out.println("inPoId=" + inPoId);
        System.out.println("inStatus=" + inStatus);
        maper.put("inPoId", inPoId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGdSelectPOValidate(maper);
        mapLog(maper, "doGdSelectPOValidate");
        return map;
    }

    public Map doGdSelectPOValidateBundling(String inPoId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        System.out.println("doGdSelectPOValidate");
        System.out.println("inPoId=" + inPoId);
        System.out.println("inStatus=" + inStatus);
        maper.put("inPoId", inPoId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGdSelectPOValidateBundling(maper);
        mapLog(maper, "doGdSelectPOValidatebundling");
        return map;
    }

    public Map doGdCancelValidate(String inUserId, String inResponId, String inBuId,
            String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGdCancelValidate(maper);
        mapLog(maper, "doGdCancelValidate");
        return map;
    }

    public Map doGdCancelValidateLine(String inGdHdrId, String inGdLineNo, String inPoId,
            String inPositionNum) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inPoId", inPoId);
        maper.put("inPositionNum", inPositionNum);
        Map map = new Implement().doGdCancelValidateLine(maper);
        mapLog(maper, "doGdCancelValidateLine");
        return map;
    }

    public Map doGdCancel(String inGdHdrId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doGdCancel(maper);
        mapLog(maper, "doGdCancel");
        return map;
    }

    public Map doGdCancelBundling(String inGdHdrId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doGdCancelBundling(maper);
        mapLog(maper, "doGdCancel");
        return map;
    }

    public Map doGdCancelLine(String inGdHdrId, String inGdLineNo, String inPoId, String inItemId, String inPoLineRef) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inPoId", inPoId);
        maper.put("inItemId", inItemId);
        maper.put("inPoLineRef", inPoLineRef);
        Map map = new Implement().doGdCancelLine(maper);
        mapLog(maper, "doGdCancelLine");
        return map;
    }

    public Map doGdCancelLineBundling(String inGdHdrId, String inGdLineNo, String inPoId, String inItemId, String inPoLineRef) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inPoId", inPoId);
        maper.put("inItemId", inItemId);
        maper.put("inPoLineRef", inPoLineRef);
        Map map = new Implement().doGdCancelLineBundling(maper);
        mapLog(maper, "doGdCancelLine");
        return map;
    }

    public Map doGdDeleteDtl(String inGdHdrId, String inGdDtlId, String inLineNo, String inPoId, String inPositionNum,
            String inQty, String inItemId, String inDeliveryQty, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdDtlId", inGdDtlId);
        maper.put("inLineNo", inLineNo);
        maper.put("inPoId", inPoId);
        maper.put("inPositionNum", inPositionNum);
        maper.put("inQty", inQty);
        maper.put("inItemId", inItemId);
        maper.put("inDeliveryQty", inDeliveryQty);
        maper.put("inUserId", inUserId);
        mapLog(maper, "doGdDeleteDtl");
        Map map = new Implement().doGdDeleteDtl(maper);
        mapLog(maper, "doGdDeleteDtl");
        return map;
    }

    public Map doGdDeleteDtlBundling(String inGdHdrId, String inGdDtlId, String inLineNo, String InBundlingId, String inPositionNum,
            String inQty, String inItemId, String inDeliveryQty, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdDtlId", inGdDtlId);
        maper.put("inLineNo", inLineNo);
        maper.put("InBundlingId", InBundlingId);
        maper.put("inPositionNum", inPositionNum);
        maper.put("inQty", inQty);
        maper.put("inItemId", inItemId);
        maper.put("inDeliveryQty", inDeliveryQty);
        maper.put("inUserId", inUserId);
        mapLog(maper, "doGdDeleteDtlBundling");
        Map map = new Implement().doGdDeleteDtlBundling(maper);
        return map;
    }

    public Map doGdDtlQtyValidate(String inGdHdrId, String inPoId, String inPoLine, String inLineNo,
            String inItemId, String inOrderQty, String inQty) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inPoId", inPoId);
        maper.put("inPoLine", inPoLine);
        maper.put("inLineNo", inLineNo);
        maper.put("inItemId", inItemId);
        maper.put("inOrderQty", inOrderQty);
        maper.put("inQty", inQty);
        Map map = new Implement().doGdDtlQtyValidate(maper);
        mapLog(maper, "doGdDtlQtyValidate");
        return map;
    }

    List<ListGdEditSNParam> selectGdListEditSN(String inList, String poId, String poLineRef, String supDelivid,
            String lineNo, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inList", inList);
        maper.put("poId", poId);
        maper.put("poLineRef", poLineRef);
        maper.put("supDelivid", supDelivid);
        maper.put("lineNo", lineNo);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        showLog(maper, "selectGdListEditSN");
        return new Implement().selectGdListEditSN(maper);
    }
    List<ListBundlingGdEditSNParam> selectGdListEditSNBundling(String inList, String poId, String poLineRef, String supDelivid,
            String lineNo, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inList", inList);
        maper.put("poId", poId);
        maper.put("poLineRef", poLineRef);
        maper.put("supDelivid", supDelivid);
        maper.put("lineNo", lineNo);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        showLog(maper, "selectGdListEditSN");
        return new Implement().selectGdListEditSNBundling(maper);
    }

    public List<Integer> getCountEditSNHdr(String inList, String poId, String poLineRef, String supDelivid,
            String lineNo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inList", inList);
        maper.put("poId", poId);
        maper.put("poLineRef", poLineRef);
        maper.put("supDelivid", supDelivid);
        maper.put("lineNo", lineNo);
        return new Implement().getCountEditSNHdr(maper);
    }
    public List<Integer> getCountEditSNHdrBundling(String inList, String poId, String poLineRef, String supDelivid,
            String lineNo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inList", inList);
        maper.put("poId", poId);
        maper.put("poLineRef", poLineRef);
        maper.put("supDelivid", supDelivid);
        maper.put("lineNo", lineNo);
        return new Implement().getCountEditSNHdrBundling(maper);
    }

    public List<Integer> getCountEditSNRst(String inList, String poId, String poLineRef, String supDelivid,
            String lineNo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inList", inList);
        maper.put("poId", poId);
        maper.put("poLineRef", poLineRef);
        maper.put("supDelivid", supDelivid);
        maper.put("lineNo", lineNo);
        return new Implement().getCountEditSNRst(maper);
    }
    public List<Integer> getCountEditSNRstBundling(String inList, String poId, String poLineRef, String supDelivid,
            String lineNo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inList", inList);
        maper.put("poId", poId);
        maper.put("poLineRef", poLineRef);
        maper.put("supDelivid", supDelivid);
        maper.put("lineNo", lineNo);
        return new Implement().getCountEditSNRstBundling(maper);
    }

    public Map doGdSelectRange(String inSelectMode, String inPoId, String inPositionNum, String inGdHdrId,
            String inLineNo, String inRangeFrom, String inRangeTo, String inRangeQty) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inSelectMode", inSelectMode);
        maper.put("inPoId", inPoId);
        maper.put("inPositionNum", inPositionNum);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inLineNo", inLineNo);
        maper.put("inRangeFrom", inRangeFrom);
        maper.put("inRangeTo", inRangeTo);
        maper.put("inRangeQty", inRangeQty);
        mapLog(maper, "doGdSelectRange");
        Map map = new Implement().doGdSelectRange(maper);
        mapLog(maper, "doGdSelectRange");
        return map;
    }
    

    public Map doGdSelectRangeOk(String inPoId, String inPoLineRef, String inQty,
            String inTotalQty) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inPoLineRef", inPoLineRef);
        maper.put("inQty", inQty);
        maper.put("inTotalQty", inTotalQty);
        Map map = new Implement().doGdSelectRangeOk(maper);
        mapLog(maper, "doGdSelectRangeOk");
        return map;

    }
   

    public Map doGdSumSelectQty(String inPoId, String inPoLineRef, String inGdHdrId,
            String inGdLineNo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inPoLineRef", inPoLineRef);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        Map map = new Implement().doGdSumSelectQty(maper);
        mapLog(maper, "doGdSumSelectQty");
        return map;

    }
    

    public Map doGdEditSN(String inItemId, String inPoId, String inPoLineRef, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inItemId", inItemId);
        maper.put("inPoId", inPoId);
        maper.put("inPoLineRef", inPoLineRef);
        maper.put("inStatus", inStatus);
                mapLog(maper, "doGdEditSN");
        Map map = new Implement().doGdEditSN(maper);
        return map;

    }

    public Map doGdEditSNBundling(String inItemId, String inPoId, String inPoLineRef, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inItemId", inItemId);
        maper.put("inPoId", inPoId);
        maper.put("inPoLineRef", inPoLineRef);
        maper.put("inStatus", inStatus);
        mapLog(maper, "doGdEditSNBundling");
        Map map = new Implement().doGdEditSNBundling(maper);
        return map;

    }

    //goodreciept
    List<GrListHdrParam> getGrListHdr(String inReceiveId, String inRcvNo, String inPoNo, String inGdNo, String inStartDate,
            String inEndDate, String inStatus, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReceiveId", inReceiveId);
        maper.put("inRcvNo", inRcvNo);
        maper.put("inPoNo", inPoNo);
        maper.put("inGdNo", inGdNo);
        maper.put("inStartDate", inStartDate);
        maper.put("inEndDate", inEndDate);
        maper.put("inStatus", inStatus);
        maper.put("InUserId", userId);
        maper.put("InResponId", responsibilityId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().getGrListHdr(maper);
    }

    List<GrListHdrParam> selectGrListHdr(String inReceiveId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReceiveId", inReceiveId);
        maper.put("InUserId", userId);
        maper.put("InResponId", responsibilityId);
        return new Implement().selectGrListHdr(maper);
    }

    List<Integer> getCountGrListHdr(String inReceiveId, String inRcvNo, String inPoNo, String inGdNo, String inStartDate,
            String inEndDate, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReceiveId", inReceiveId);
        maper.put("inRcvNo", inRcvNo);
        maper.put("inPoNo", inPoNo);
        maper.put("inGdNo", inGdNo);
        maper.put("inStartDate", inStartDate);
        maper.put("inEndDate", inEndDate);
        maper.put("inStatus", inStatus);
        maper.put("InUserId", userId);
        maper.put("InResponId", responsibilityId);
        return new Implement().getCountGrListHdr(maper);
    }
   

    public Map doGrSaveHdr(String InAction, String inRcvId, String inPoId, String inPoNo, String inWhId,
            String inBuId, String inGdHdrId, String inSupplierId, String inSiteId, String inUserId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inAction", InAction);
        maper.put("inRcvId", inRcvId);
        maper.put("inPoId", inPoId);
        maper.put("inPoNo", inPoNo);
        maper.put("inWhId", inWhId);
        maper.put("inBuId", inBuId);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inSupplierId", inSupplierId);
        maper.put("inSiteId", inSiteId);
        maper.put("inUserId", inUserId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGrSaveHdr(maper);
        mapLog(maper, "doGrSaveHdr");
        return map;
    }

    public Map doGrSelectGdValidate(String inPoId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGrSelectGdValidate(maper);
        mapLog(maper, "doGrSelectGdValidate");
        return map;
    }

    List<ListGrSelectGDParam> selectGrListSelectGD(String gdId, String poId, String grId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("gdId", gdId);
        maper.put("poId", poId);
        maper.put("grId", grId);
        showLog(maper, "selectGrListSelectGD");
        return new Implement().selectGrListSelectGD(maper);
    }

    public Map doGrSaveDtl(String inAction, String inReceiveId, String inReceiveLine, String inReceiveDtlId,
            String inItemId, String inItemCode, String inLotId, String inOrderQty, String inReceiveQty, String inRejectQty,
            String inUserId, String inHlrMapId, String inApprovedQty, String inLotSize, String inPoId, String inPoNo, String inPoLineRef,
            String inPoLineId, String inUOM, String inBalanceQty, String inDeliverQty, String inAcceptQty, String inGdLineNo,
            String inGdHdrId, String inWhId, String inWhLocId, String inRegionalId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inAction", inAction);
        maper.put("inReceiveId", inReceiveId);
        maper.put("inReceiveLine", inReceiveLine);
        maper.put("inReceiveDtlId", inReceiveDtlId);
        maper.put("inItemId", inItemId);
        maper.put("inItemCode", inItemCode);
        maper.put("inLotId", inLotId);
        maper.put("inOrderQty", inOrderQty);
        maper.put("inReceiveQty", inReceiveQty);
        maper.put("inRejectQty", inRejectQty);
        maper.put("inUserId", inUserId);
        maper.put("inHlrMapId", inHlrMapId);
        maper.put("inApprovedQty", inApprovedQty);
        maper.put("inLotSize", inLotSize);
        maper.put("inPoId", inPoId);
        maper.put("inPoNo", inPoNo);
        maper.put("inPoLineRef", inPoLineRef);
        maper.put("inPoLineId", inPoLineId);
        maper.put("inUOM", inUOM);
        maper.put("inBalanceQty", inBalanceQty);
        maper.put("inDeliverQty", inDeliverQty);
        maper.put("inAcceptQty", inAcceptQty);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inWhId", inWhId);
        maper.put("inWhLocId", inWhLocId);
        maper.put("inRegionalId", inRegionalId);
        mapLog(maper, "doGrSaveDtl");
        Map map = new Implement().doGrSaveDtl(maper);
        return map;
    }

    List<ListresultGrListDtlParam> selectGrListDtl(String rcvId) {
        System.out.println("rcvId=" + rcvId);
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("rcvId", rcvId);
        return new Implement().selectGrListDtl(maper);
    }

    List<ListGrListEditSNParam> selectGrListEditSN(String inList, String gdId, String gdLine, String rcvId, String rcvLine) {
        Map<String, Object> maper = new HashMap<String, Object>();
        System.out.println("selectGrListEditSN");
        System.out.println("inList=" + inList);
        System.out.println("gdId=" + gdId);
        System.out.println("gdLine=" + gdLine);
        System.out.println("rcvId=" + rcvId);
        System.out.println("rcvLine=" + rcvLine);
        maper.put("inList", inList);
        maper.put("gdId", gdId);
        maper.put("gdLine", gdLine);
        maper.put("rcvId", rcvId);
        maper.put("rcvNo", rcvLine);
        return new Implement().selectGrListEditSN(maper);
    }

    public Map doGrSelectRangeValidate(String inUserId, String inResponId,
            String inBuId, String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
         mapLog(maper, "doGrSelectRangeValidate");
        Map map = new Implement().doGrSelectRangeValidate(maper);
        return map;
    }

    public Map doGrUnSelectRangeValidate(String inUserId, String inResponId,
            String inBuId, String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
         mapLog(maper, "doGrUnSelectRangeValidate");
        Map map = new Implement().doGrUnSelectRangeValidate(maper);
        return map;
    }

    public Map doGrSelectRange(String inSelectMode, String inGdHdrId, String inGdLineNo,
            String inReceiveId, String inReceiveLine, String inRangeFrom, String inRangeTo, String inRangeQty
    ,String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inSelectMode", inSelectMode);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inReceiveId", inReceiveId);
        maper.put("inReceiveLine", inReceiveLine);
        maper.put("inRangeFrom", inRangeFrom);
        maper.put("inRangeTo", inRangeTo);
        maper.put("inRangeQty", inRangeQty);
        maper.put("inUserId", inUserId);
        mapLog(maper, "doGrSelectRange");
        Map map = new Implement().doGrSelectRange(maper);
        mapLog(maper, "doGrSelectRange");
        return map;
    }

    public Map doGrConfirmRcvValidate(String inReceiveId, String inUserId,
            String inResponId, String inBuId, String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReceiveId", inReceiveId);
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGrConfirmRcvValidate(maper);
        mapLog(maper, "doGrConfirmRcvValidate");
        return map;
    }

    public Map doGrConfirmRcvValidateLine(String inReceiveId, String inReceiveLine, String inRcvQty,
            String inGdHdrId, String inGdLineNo, String inItemId, String inItemCode, String inWhLoc, String inWhLocId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReceiveId", inReceiveId);
        maper.put("inReceiveLine", inReceiveLine);
        maper.put("inRcvQty", inRcvQty);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inItemId", inItemId);
        maper.put("inItemCode", inItemCode);
        maper.put("inWhLoc", inWhLoc);
        maper.put("inWhLocId", inWhLocId);
        showLog(maper, "doGrConfirmRcvValidateLine");
        Map map = new Implement().doGrConfirmRcvValidateLine(maper);
        return map;
    }

    public Map doGrConfirmReceive(String inRcvId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inRcvId", inRcvId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doGrConfirmReceive(maper);
        mapLog(maper, "doGrConfirmReceive");
        return map;

    }

    public Map doGrCancelValidate(String inUserId, String inResponId,
            String inBuId, String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
        mapLog(maper, "doGrCancelValidate");
        Map map = new Implement().doGrCancelValidate(maper);
        return map;

    }

    public Map doGrCancel(String inRcvId, String flag,String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inRcvId", inRcvId);
        maper.put("inFlag", flag);
        maper.put("InUserId", InUserId);
        mapLog(maper, "doGrCancel");
        Map map = new Implement().doGrCancel(maper);
        return map;
    }

    public Map doGrCancelLine(String inRcvId, String inRcvLine,
            String inGdHdrId, String inGdLineNo, String inItemId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inRcvId", inRcvId);
        maper.put("inRcvLine", inRcvLine);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inItemId", inItemId);
        Map map = new Implement().doGrCancelLine(maper);
        mapLog(maper, "doGrCancelLine");
        return map;
    }

    public Map doGrApproveValidate(String inUserId, String inResponId,
            String inBuId, String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGrApproveValidate(maper);
        mapLog(maper, "doGrApproveValidate");
        return map;

    }

    public Map doGrApproveValidateLine(String inReceiveId, String inReceiveLine, String inRcvQty,
            String inGdHdrId, String inGdLineNo, String inItemId, String inItemCode, String inWhLoc, String inWhLocCode,String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        System.out.println("inRcvQty=" + inRcvQty);
        maper.put("inItemId", inItemId);
        maper.put("inItemCode", inItemCode);
        maper.put("inReceiveId", inReceiveId);
        maper.put("inReceiveLine", inReceiveLine);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inWhLocCode", inWhLocCode);
        maper.put("inRcvQty", inRcvQty);
        maper.put("inUserId", inUserId);
        mapLog(maper, "doGrApproveValidateLine");
        Map map = new Implement().doGrApproveValidateLine(maper);
        return map;
    }

    public Map doGrApprove(String inRcvId, String inPoId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inRcvId", inRcvId);
        maper.put("inPoId", inPoId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doGrApprove(maper);
        mapLog(maper, "doGrApprove");
        return map;

    }

    public Map doGrUpdateDtlWhLoc(String inRcvId, String inRcvLine, String inWhLocId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        System.out.println("doGrUpdateDtlWhLoc");
        System.out.println("inRcvId=" + inRcvId);
        System.out.println("inRcvLine=" + inRcvLine);
        System.out.println("inWhLocId=" + inWhLocId);
        maper.put("inRcvId", inRcvId);
        maper.put("inRcvLine", inRcvLine);
        maper.put("inWhLocId", inWhLocId);
        Map map = new Implement().doGrUpdateDtlWhLoc(maper);
        mapLog(maper, "doGrUpdateDtlWhLoc");
        return map;
    }

    public Map doGrDeleteDtl(String inReceiveDtlId, String inReceiveQty, String inGdHdrId, String inGdLineNo, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReceiveDtlId", inReceiveDtlId);
        maper.put("inReceiveQty", inReceiveQty);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doGrDeleteDtl(maper);
        mapLog(maper, "doGrDeleteDtl");
        return map;

    }

    public Map doGrSumSelectQty(String inPoId, String inPoLineRef, String inGrHdrId,
            String inGrLineNo, String inGdId, String inGdLineNo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inPoLineRef", inPoLineRef);
        maper.put("inGrHdrId", inGrHdrId);
        maper.put("inGrLineNo", inGrLineNo);
        maper.put("inGdId", inGdId);
        maper.put("inGdLineNo", inGdLineNo);
                mapLog(maper, "doGrSumSelectQty");
        Map map = new Implement().doGrSumSelectQty(maper);
        return map;

    }

    public Map doGrSetLotSize(String inRcvId, String inRcvDtlId, String inLotSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inRcvId", inRcvId);
        maper.put("inRcvDtlId", inRcvDtlId);
        maper.put("inLotSize", inLotSize);
        Map map = new Implement().doGrSetLotSize(maper);
        mapLog(maper, "doGrSetLotSize");
        return map;

    }

    public Map doMvInsert(String itemId, String itemCode, String itemDesc,
            String voucherCode, String voucherDesc, String userId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inItemId", itemId);
        maper.put("inItemCode", itemCode);
        maper.put("inItemDesc", itemDesc);
        maper.put("inVoucherCode", voucherCode);
        maper.put("inVoucherDesc", voucherDesc);
        maper.put("inUserId", userId);
        Map map = new Implement().doMvInsert(maper);
        mapLog(maper, "doMvInsert");
        return map;

    }

    public Map domvUpdate(String id, String itemId, String itemCode, String itemDesc,
            String voucherCode, String voucherDesc, String userId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inId", id);
        maper.put("inItemId", itemId);
        maper.put("inItemCode", itemCode);
        maper.put("inItemDesc", itemDesc);
        maper.put("inVoucherCode", voucherCode);
        maper.put("inVoucherDesc", voucherDesc);
        maper.put("inUserId", userId);
        Map map = new Implement().domvUpdate(maper);
        mapLog(maper, "domvUpdate");
        return map;

    }

    public Map domvExpire(String id, String userId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inId", id);
        maper.put("inUserId", userId);
        Map map = new Implement().domvExpire(maper);
        mapLog(maper, "domvExpire");
        return map;

    }

    List<MVItemToVoucherParam> getListMvHdr(String inItem, String inVoucher, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inItem", inItem);
        maper.put("inVoucher", inVoucher);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().getListMvHdr(maper);
    }

    List<MVItemToVoucherParam> selectMvListHdr(String inReceiveId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inId", inReceiveId);
        return new Implement().selectMvListHdr(maper);
    }

    List<Integer> getCountMvHdr(String inItem, String inVoucher) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inItem", inItem);
        maper.put("inVoucher", inVoucher);
        return new Implement().getCountMvHdr(maper);
    }

    public Map doArtInsert(String inArtCode, String inArtDesc, String inFileName, String userId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inArtCode", inArtCode);
        maper.put("inArtDesc", inArtDesc);
        maper.put("inFileName", inFileName);
        maper.put("inUserId", userId);
        Map map = new Implement().doArtInsert(maper);
        mapLog(maper, "doArtInsert");
        return map;

    }

    public Map doArtUpdate(String inArtId, String inArtCode, String inArtDesc, String inFileName, String userId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inArtId", inArtId);
        maper.put("inArtCode", inArtCode);
        maper.put("inArtDesc", inArtDesc);
        maper.put("inFileName", inFileName);
        maper.put("inUserId", userId);
        Map map = new Implement().doArtUpdate(maper);
        mapLog(maper, "doArtUpdate");
        return map;

    }

    public Map doArtExpire(String id, String userId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inId", id);
        maper.put("inUserId", userId);
        Map map = new Implement().doArtExpire(maper);
        mapLog(maper, "doArtExpire");
        return map;

    }

    List<ArtWorkListParam> getListArtList(String inArt, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inArt", inArt);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().getListArtList(maper);
    }

    List<ArtWorkListParam> selectArtListHdr(String inId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inId", inId);
        return new Implement().selectArtListHdr(maper);
    }

    List<Integer> getCountArtList(String inArt) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inArt", inArt);
        return new Implement().getCountArtList(maper);
    }

    public List<ListWoRcvParam> selectListWoRcv(String contractNo, String poNo, String rcvNo, String dateFrom, String dateTo,
            String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("contractNo", contractNo);
        maper.put("poNo", poNo);
        maper.put("rcvNo", rcvNo);
        maper.put("dateFrom", dateFrom);
        maper.put("dateTo", dateTo);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        mapLog(maper, "selectListWoRcv");
        return new Implement().selectListWoRcv(maper);

    }

    public List<ListWoRcvParam> selectListWoRcvPrint(String contractNo, String poNo, String rcvNo, String dateFrom, String dateTo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("contractNo", contractNo);
        maper.put("poNo", poNo);
        maper.put("rcvNo", rcvNo);
        maper.put("dateFrom", dateFrom);
        maper.put("dateTo", dateTo);
        mapLog(maper, "selectListWoRcv");
        return new Implement().selectListWoRcvPrint(maper);

    }

    List<Integer> getCountWoRcv(String contractNo, String poNo, String rcvNo, String dateFrom, String dateTo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("contractNo", contractNo);
        maper.put("poNo", poNo);
        maper.put("rcvNo", rcvNo);
        maper.put("dateFrom", dateFrom);
        maper.put("dateTo", dateTo);
        mapLog(maper, "getCountWoRcv");
        return new Implement().getCountWoRcv(maper);

    }

    public List<ListloadtourpParam> selectListloadtourp(String idoNo, String dateFrom, String dateTo,
            String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("idoNo", idoNo);
        maper.put("dateFrom", dateFrom);
        maper.put("dateTo", dateTo);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().selectListloadtourp(maper);
    }

    public List<ListloadtourpParam> selectListloadtourpPrint(String idoNo, String dateFrom, String dateTo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("idoNo", idoNo);
        maper.put("dateFrom", dateFrom);
        maper.put("dateTo", dateTo);
        return new Implement().selectListloadtourpPrint(maper);
    }

    List<Integer> getCountloadtourp(String idoNo, String dateFrom, String dateTo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("idoNo", idoNo);
        maper.put("dateFrom", dateFrom);
        maper.put("dateTo", dateTo);
        return new Implement().getCountloadtourp(maper);
    }

    public List<ListdistdataParam> selectListdistdata(String woNo, String refNo, String dateFrom, String dateTo,
            String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("woNo", woNo);
        maper.put("refNo", refNo);
        maper.put("dateFrom", dateFrom);
        maper.put("dateTo", dateTo);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().selectListdistdata(maper);
    }

    public List<ListdistdataParam> selectListdistdataPrint(String woNo, String refNo, String dateFrom, String dateTo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("woNo", woNo);
        maper.put("refNo", refNo);
        maper.put("dateFrom", dateFrom);
        maper.put("dateTo", dateTo);
        return new Implement().selectListdistdataPrint(maper);
    }

    List<Integer> getCountdistdata(String woNo, String refNo, String dateFrom, String dateTo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("woNo", woNo);
        maper.put("refNo", refNo);
        maper.put("dateFrom", dateFrom);
        maper.put("dateTo", dateTo);
        return new Implement().getCountdistdata(maper);
    }

    public Map dowoUploadPairingSn(String inUploadId, String InUploadDtlId, String inFileName, String inSupplierId, String inSupplierCode, String inPoId, String InPoLine,
            String inItemId, String inSn, String inRows, String inUserId,String InBatchId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inUploadId", inUploadId);
        maper.put("InUploadDtlId", InUploadDtlId);
        maper.put("inFileName", inFileName);
        maper.put("inSupplierId", inSupplierId);
        maper.put("inSupplierCode", inSupplierCode);
        maper.put("inPoId", inPoId);
        maper.put("InPoLine", InPoLine);
        maper.put("inItemId", inItemId);
        maper.put("inSn", inSn);
        maper.put("inRows", inRows);
        maper.put("inUserId", inUserId);
        maper.put("InBatchId", InBatchId);
          mapLog(maper, "dowoUploadPairingSn");
        Map map = new Implement().dowoUploadPairingSn(maper);
      
        return map;
    }

    public Map dowoUpdateQtyUploadSn(String inUploadId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inUploadId", inUploadId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().dowoUpdateQtyUploadSn(maper);
        mapLog(maper, "dowoUpdateQtyUploadSn");
        return map;
    }
    
    public Map doGetUploadBatch(Map map) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put(null, map);
        mapLog(maper, "doGetUploadBatch");
        new Implement().doGetUploadBatch(maper);
        return maper;
    }

    public Map doWOValidateGetSN(String inSupId, String inSupCode, String inItemId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inSupId", inSupId);
        maper.put("inSupCode", inSupCode);
        maper.put("inItemId", inItemId);
        Map map = new Implement().doWOValidateGetSN(maper);
        mapLog(maper, "doWOValidateGetSN");
        return map;
    }

    List<String> getSNCount(String sn) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("sn", sn);
        return new Implement().getSNCount(maper);
    }

    List<ListWoPairing> selectListWOPairing(String woId,String InPoLine) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("woId", woId);
        maper.put("InPoLine", InPoLine);
        showLog(maper, "selectListWOPairing");
        return new Implement().selectListWOPairing(maper);
    }

    public List<ListIFBndParam> selectListIFBnd(String ifId, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("ifId", ifId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().selectListIFBnd(maper);
    }

    public List<ListIFParam> selectListIF(String InIfId) {
        return selectListIF(InIfId, "", "", "", "",userId,responsibilityId);
    }

    public List<ListIFParam> selectListIF(String InIfId, String InPoId, String InDateFrom, String InDateTo, String InStatus,String InUserId,String InResponId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InIfId", InIfId == null ? "" : InIfId);
        maper.put("InPoId", InPoId == null ? "" : InPoId);
        maper.put("InDateFrom", InDateFrom == null ? "" : InDateFrom);
        maper.put("InDateTo", InDateTo == null ? "" : InDateTo);
        maper.put("InStatus", InStatus == null ? "" : InStatus);
        maper.put("InUserId",InUserId );
        maper.put("InResponId",InResponId );
        return new Implement().selectListIF(maper);
    }

    public List<ListIFBndParam> selectListIFTrcBnd(String InIfId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InIfId", InIfId);
        return new Implement().selectListIFTrcBnd(maper);
    }

    public List<ListIFParamTrc> selectListIFTrc() {
        return selectListIFTrc("", "", "", "", "",userId,responsibilityId);
    }

    public List<ListIFParamTrc> selectListIFTrc(String InIfId, String InPoId, String InDateFrom, String InDateTo, String InStatus,String InUserId,String InResponId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InIfId", InIfId == null ? "" : InIfId);
        maper.put("InPoId", InPoId == null ? "" : InPoId);
        maper.put("InDateFrom", InDateFrom == null ? "" : InDateFrom);
        maper.put("InDateTo", InDateTo == null ? "" : InDateTo);
        maper.put("InStatus", InStatus == null ? "" : InStatus);
        maper.put("InUserId",InUserId );
        maper.put("InResponId",InResponId );
        showLog(maper, "selectListIFTrc");
        return new Implement().selectListIFTrc(maper);
    }

    public List<ListGenIFParam> selectGenFile(String ifId,String InPoLine) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("ifId", ifId);
        maper.put("InPoLine", InPoLine);
        return new Implement().selectGenFile(maper);
    }

    List<Integer> getCountIFBnd(String ifId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("ifId", ifId);
        return new Implement().getCountIFBnd(maper);
    }

    List<Integer> getCountIF(String InIfId, String InPoId, String InDateFrom, String InDateTo, String InStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InIfId", InIfId);
        maper.put("InPoId", InPoId);
        maper.put("InDateFrom", InDateFrom);
        maper.put("InDateTo", InDateTo);
        maper.put("InStatus", InStatus);
        return new Implement().getCountIF(maper);
    }

    public Map doIfValidateInsertHdr(String inPoId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        mapLog(maper, "doIfValidateInsertHdr");
        Map map = new Implement().doIfValidateInsertHdr(maper);
        return map;
    }

    public Map doIfValidateUpdateHdr(String inIfId, String inPoId, String inFileName, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inIfId", inIfId);
        maper.put("inPoId", inPoId);
        maper.put("inFileName", inFileName);
        maper.put("inUserId", inUserId);
        mapLog(maper, "doIfValidateUpdateHdr");
        Map map = new Implement().doIfValidateUpdateHdr(maper);
        return map;
    }
    
    
    public List<IfListDtlParam>getIfListDtl(String InPoId,String InUserId,String InResponId){
     Map<String,Object>map = new HashMap<>();
     map.put("InPoId", InPoId);
     map.put("InUserId", InUserId);
     map.put("InResponId", InResponId);
     return new  Implement().getIfListDtl(map);
    }

    public Map doIfInsertHdr(String inPoId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inUserId", inUserId);
        mapLog(maper, "doIfInsertHdr");
        Map map = new Implement().doIfInsertHdr(maper);
        return map;
    }

    public Map doIfInsertBndHdr(String inPoId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inUserId", inUserId);
         mapLog(maper, "doIfInsertBndHdr");
        Map map = new Implement().doIfInsertBndHdr(maper);
        return map;
    }

    public Map doIfUpdateHdr(String inIfId, String inPoId, String inFileName, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inIfId", inIfId);
        maper.put("inPoId", inPoId);
        maper.put("inFileName", inFileName);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doIfUpdateHdr(maper);
        mapLog(maper, "doIfUpdateHdr");
        return map;
    }

    public Map doIfSubmitHdr(String inIfId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inIfId", inIfId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doIfSubmitHdr(maper);
        mapLog(maper, "doIfSubmitHdr");
        return map;
    }

    public Map doIfApproveHdr(String inIfId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inIfId", inIfId);
        maper.put("inUserId", inUserId);
        mapLog(maper, "doIfApproveHdr");
        Map map = new Implement().doIfApproveHdr(maper);
        return map;
    }

    public Map doIfCancelHdr(String inIfId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inIfId", inIfId);
        maper.put("inUserId", inUserId);
        mapLog(maper, "doIfCancelHdr");
        Map map = new Implement().doIfCancelHdr(maper);
        return map;
    }

    public List<ListOFParam> selectListOF(String ofId, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("ofId", ofId);
        maper.put("InUserId", userId);
        maper.put("InResponId", responsibilityId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().selectListOF(maper);
    }
    
    public List<ListOFParam> getselectListOF() {
        return getselectListOF(null, null);
    }
    public List<ListOFParam> getselectListOF(String ofId,Map searchMap) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("ofId", ofId == null ? "": ofId );
        maper.put("InUserId", userId );
        maper.put("InResponId", responsibilityId );
        if (searchMap != null) {
            maper.put("search", searchMap);
        }
        return new Implement().getselectListOF(maper);
    }

    public Map doIfUpdateStatus(String InIfId, String InStatus, String InUserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InIfId", InIfId);
        map.put("InStatus", InStatus);
        map.put("InUserId", InUserId);
        new Implement().doIfUpdateStatus(map);
        return map;
    }

    public List<ListOFParam> selectListOFTrc(String ofId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("ofId", ofId);
        maper.put("InUserId", userId);
        maper.put("InResponId", responsibilityId);
        return new Implement().selectListOFTrc(maper);
    }

    List<Integer> getCountOF(String ofId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("ofId", ofId);
        return new Implement().getCountOF(maper);
    }

    public Map doOfValidateInsertHdr(String inPoId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        new Implement().doOfValidateInsertHdr(maper);
        mapLog(maper, "doOfValidateInsertHdr");
        return maper;
    }
    public Map getOfClearDetail(String InOfId,String InFileName) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InOfId", InOfId);
        maper.put("InFileName", InFileName);
        mapLog(maper, "getOfClearDetail");
        new Implement().getOfClearDetail(maper);
        return maper;
    }

    public Map doOfValidateUpdateHdr(String inOfId, String inPoId, String inFileName, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inOfId", inOfId);
        maper.put("inPoId", inPoId);
        maper.put("inFileName", inFileName);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doOfValidateUpdateHdr(maper);
        mapLog(maper, "doOfValidateUpdateHdr");
        return map;
    }

    public Map doOfInsertHdr(String inPoId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doOfInsertHdr(maper);
        mapLog(maper, "doOfInsertHdr");
        return map;
    }

    public Map doOfUpdateHdr(String inOfId, String inPoId, String inFileName, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inOfId", inOfId);
        maper.put("inPoId", inPoId);
        maper.put("inFileName", inFileName);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doOfUpdateHdr(maper);
        mapLog(maper, "doOfUpdateHdr");
        return map;
    }

    public Map doOfSubmitHdr(String inOfId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inOfId", inOfId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doOfSubmitHdr(maper);
        mapLog(maper, "doOfSubmitHdr");
        return map;
    }

    public Map doOfApproveHdr(String inOfId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inOfId", inOfId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doOfApproveHdr(maper);
        mapLog(maper, "doOfApproveHdr");
        return map;
    }

    public Map doOfCancelHdr(String inOfId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inOfId", inOfId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doOfCancelHdr(maper);
        mapLog(maper, "doOfCancelHdr");
        return map;
    }
    
    public Map doOfInsertLogs(String inOfId, String inOFNumber, String inPoId, String inSNTCash, String inSNInv, String inUID){
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inOfId", inOfId);
        maper.put("inOFNumber", inOFNumber);
        maper.put("inPoId", inPoId);
        maper.put("inSNTCash", inSNTCash);
        maper.put("inSNInv", inSNInv);
        maper.put("inUID", inUID);
        mapLog(maper, "doOfInsertLogs");
        Map map = new Implement().doOfInsertLogs(maper);
        return map;
    }

    public Map doOfInsertDetail(String inOfId, String inOFNumber, String inPoId, String inSNTCash, String inSNInv, String inUID,String InFileName) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inOfId", inOfId);
        maper.put("inOFNumber", inOFNumber);
        maper.put("inPoId", inPoId);
        maper.put("inSNTCash", inSNTCash);
        maper.put("inSNInv", inSNInv);
        maper.put("inUID", inUID);
        maper.put("InFileName", InFileName);
        showLog(maper, "doOfInsertDetail");
        Map map = new Implement().doOfInsertDetail(maper);
        return map;
    }
    
    public Map doOfValidateFileName(String InPoId,String InFileName) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InPoId", InPoId);
        maper.put("InFileName", InFileName);
        showLog(maper, "doOfValidateFileName");
        Map map = new Implement().doOfValidateFileName(maper);
        return map;
    }

    public List<ListOFDtlParam> getselectListOFDtl(String ofId,String InPoLine) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("ofId", ofId);
        maper.put("InPoLine", InPoLine);
        mapLog(maper, "getselectListOFDtls");
        return new Implement().getselectListOFDtl(maper);
    }
    
    public List<OfSumParam> getOfListSumDtl(String ofId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("ofId", ofId);
        mapLog(maper, "getOfListSumDtl");
        return new Implement().getOfListSumDtl(maper);
    }

    public List<ListOFDtlParam> selectListOFDtl(String ofId, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("ofId", ofId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        mapLog(maper, "selectListOFDtl");
        return new Implement().selectListOFDtl(maper);
    }

    List<Integer> getCountOFDtl(String ofId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("ofId", ofId);
        mapLog(maper, "getCountOFDtl");
        return new Implement().getCountOFDtl(maper);
    }

    public List<ListPairingParam> selectListPairing(String pairingId, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("pairingId", pairingId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        mapLog(maper, "selectListPairing");
        return new Implement().selectListPairing(maper);
    }

    public List<ListPairingParam> selectListPairingTrc(String pairingId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("pairingId", pairingId);
        mapLog(maper, "selectListPairingTrc");
        return new Implement().selectListPairingTrc(maper);
    }

    List<Integer> getCountPairing(String pairingId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("pairingId", pairingId);
        mapLog(maper, "getCountPairing");
        return new Implement().getCountPairing(maper);
    }

    public List<ListPairingDTLParam> selectListPairingDtl(String pairingId, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("pairingId", pairingId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        mapLog(maper, "selectListPairingDtl");
        return new Implement().selectListPairingDtl(maper);
    }

    List<Integer> getCountPairingDtl(String pairingId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("pairingId", pairingId);
        mapLog(maper, "getCountPairingDtl");
        return new Implement().getCountPairingDtl(maper);
    }

    List<RpListHdrParam> getRpListHdr(String InBu, String InReqNo, String InReqId, String InUserId,String InResponsibilityId,String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InBu", InBu);
        maper.put("InReqNo", InReqNo);
        maper.put("InReqId", InReqId);
        maper.put("InUserId", InUserId);
        maper.put("InResponsibilityId", InResponsibilityId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().getRpListHdr(maper);

    }

    List<RpListHdrParam> getRpListFind(String InBu, String InReqNo, String InDateFrom, String InDateTo, String InStatus,String InUserId,String InResponsibilityId, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InBu", InBu);
        maper.put("InReqNo", InReqNo);
        maper.put("InDateFrom", InDateFrom);
        maper.put("InDateTo", InDateTo);
        maper.put("InStatus", InStatus);
        maper.put("InUserId", InUserId);
        maper.put("InResponsibilityId", InResponsibilityId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        System.out.println(maper);

        return new Implement().getRpListFind(maper);

    }

    List<Integer> getCountRpListFind(String InBu, String InReqNo, String InDateFrom, String InDateTo, String InStatus,String InUserId,String InResponsibilityId) {
        Map<String, Object> maper = new HashMap<>();
        maper.put("InBu", InBu);
        maper.put("InReqNo", InReqNo);
        maper.put("InDateFrom", InDateFrom);
        maper.put("InDateTo", InDateTo);
        maper.put("InStatus", InStatus);
         maper.put("InUserId", InUserId);
        maper.put("InResponsibilityId", InResponsibilityId);
        return new Implement().getCountRpListFind(maper);
    }

    public List<RpListHdrParam> selectRpListHdr(String InReqId,String InUserId,String InResponsibilityId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("requisitionid", InReqId);
        maper.put("InUserId", InUserId);
        maper.put("InResponsibilityId", InResponsibilityId);
        showLog(maper, "selectRpListHdr");
        return new Implement().selectRpListHdr(maper);
    }

    List<Integer> getCountRpListHdr(String InBu, String InReqNo, String InReqId,String InUserId,String InResponsibilityId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InBu", InBu);
        maper.put("InReqNo", InReqNo);
        maper.put("InReqId", InReqId);
        maper.put("InUserId", InUserId);
        maper.put("InResponsibilityId", InResponsibilityId);
        return new Implement().getCountRpListHdr(maper);
    }

    public Map doRpSaveHdr(String InAction, String InReqId, String InReqStatus, String InBuId, String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InAction", InAction);
        maper.put("InReqId", InReqId);
        maper.put("InReqStatus", InReqStatus);
        maper.put("InBuId", InBuId);
        maper.put("InUserId", InUserId);
        showLog(maper, "doRpSaveHdr");
        new Implement().doRpSaveHdr(maper);
        return maper;
    }

    List<LovBUParam> getLovBU(String InBu, String InResponId, String InUserId) {
        Map<String, Object> maper = new HashMap<>();
        maper.put("InBu", InBu);
        maper.put("InResponId", InResponId);
        maper.put("InUserId", InUserId);
        return new Implement().getLovBU(maper);

    }

    public Map doRpSubmit(String InReqId, String InBuId, String InUserId, String InResponId, String InReqStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InReqId", InReqId);
        maper.put("InBuId", InBuId);
        maper.put("InUserId", InUserId);
        maper.put("InResponId", InResponId);
        maper.put("InReqStatus", InReqStatus);
        new Implement().doRpSubmit(maper);
        return maper;
    }

    public Map doRpApproved(String InReqId, String InBuId, String InReqStatus, String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InReqId", InReqId);
        maper.put("InBuId", InBuId);
        maper.put("InReqStatus", InReqStatus);
        maper.put("InUserId", InUserId);
        new Implement().doRpApproved(maper);
        return maper;
    }

    public Map doRpCancel(String InReqId, String InBuId, String InReqStatus, String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InReqId", InReqId);
        maper.put("InBuId", InBuId);
        maper.put("InReqStatus", InReqStatus);
        maper.put("InUserId", InUserId);
        new Implement().doRpCancel(maper);
        return maper;
    }

    public Map doRpGetUploadAttr(String InItemCode, String InBuCode, String InWhCode) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InItemCode", InItemCode);
        maper.put("InBuCode", InBuCode);
        maper.put("InWhCode", InWhCode);
        new Implement().doRpGetUploadAttr(maper);
        return maper;
    }

    public List<RpListDtlParam> getRpListDtl(String InReqId, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InReqId", InReqId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().getRpListDtl(maper);

    }

    List<Integer> getCountRpListDtl(String InReqId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InReqId", InReqId);
        return new Implement().getCountRpListDtl(maper);
    }

    public List<LovItemParam> getLovItem(String InItem) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InItem", InItem);
        return new Implement().getLovItem(maper);

    }

    public List<LovRegionalParam> getLovRegional(String InRegional, String InUserId, String InResponId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InRegional", InRegional);
        maper.put("InUserId", InUserId);
        maper.put("InResponId", InResponId);
        return new Implement().getLovRegional(maper);

    }

    public List<LovWhParam> getLovWh(String InWh, String InBuId, String InUserId, String InResponId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InWh", InWh);
        maper.put("InBuId", InBuId);
        maper.put("InUserId", InUserId);
        maper.put("InResponId", InResponId);
        return new Implement().getLovWh(maper);

    }

    public Map doRpSaveDtl(String InAction, String InReqId, String InReqNo, String InReqStatus, String InPosition,
            String InItemId, String InItemCode, String InBuId, String InBuCode, String InWhId,
            String InWhCode, String InOrderUnit, String InOrderQty, String InRequiredDate, String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InAction", InAction);
        maper.put("InReqId", InReqId);
        maper.put("InReqNo", InReqNo);
        maper.put("InReqStatus", InReqStatus);
        maper.put("InPosition", InPosition);
        maper.put("InItemId", InItemId);
        maper.put("InItemCode", InItemCode);
        maper.put("InBuId", InBuId);
        maper.put("InBuCode", InBuCode);
        maper.put("InWhId", InWhId);
        maper.put("InWhCode", InWhCode);
        maper.put("InOrderUnit", InOrderUnit);
        maper.put("InOrderQty", InOrderQty);
        maper.put("InRequiredDate", InRequiredDate);
        maper.put("InUserId", InUserId);
        showLog(maper, "doRpSaveDtl");
        new Implement().doRpSaveDtl(maper);
        return maper;
    }

    List<PcListHdrParam> getPcListHdr(String InSupplier, String InContract, String InDateFrom, String InDateTo, String InStatus, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InSupplier", InSupplier);
        maper.put("InContract", InContract);
        maper.put("InDateFrom", InDateFrom);
        maper.put("InDateTo", InDateTo);
        maper.put("InStatus", InStatus);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().getPcListHdr(maper);

    }

    public List<PcListHdrParam> selectPcListHdr(String InContractId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("contractid", InContractId);
        return new Implement().selectPcListHdr(maper);
    }

    List<Integer> getCountPcListHdr(String InSupplier, String InContract, String InDateFrom, String InDateTo, String InStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InSupplier", InSupplier);
        maper.put("InContract", InContract);
        maper.put("InDateFrom", InDateFrom);
        maper.put("InDateTo", InDateTo);
        maper.put("InStatus", InStatus);
        return new Implement().getCountPcListHdr(maper);
    }

    public Map doPcSaveHdr(String InAction, String InContractId, String InDescription, String InSupplierId,
            String InSupplierCode, String InTop, String InCurrency, String InForwardAgent, String InContractStatus,
            String InUserId, String InSiteCode, String InSiteId, String InContractMapNo, String InAgreedQty,
            String InAgreedAmount, String InEffectiveDate, String InExpiredDate) {
        Map<String, Object> maper = new HashMap<String, Object>();

        maper.put("InAction", InAction);
        maper.put("InContractId", InContractId);
        maper.put("InDescription", InDescription);
        maper.put("InSupplierId", InSupplierId);
        maper.put("InSupplierCode", InSupplierCode);
        maper.put("InTop", InTop);
        maper.put("InCurrency", InCurrency);
        maper.put("InForwardAgent", InForwardAgent);
        maper.put("InContractStatus", InContractStatus);
        maper.put("InUserId", InUserId);
        maper.put("InSiteCode", InSiteCode);
        maper.put("InSiteId", InSiteId);
        maper.put("InContractMapNo", InContractMapNo);
        maper.put("InAgreedQty", InAgreedQty);
        maper.put("InAgreedAmount", InAgreedAmount);
        maper.put("InEffectiveDate", InEffectiveDate);
        maper.put("InExpiredDate", InExpiredDate);
        showLog(maper," doPcSaveHdr");
        new Implement().doPcSaveHdr(maper);
        return maper;
    }

    List<PcListDtlParam> getPcListDtl(String InContractId, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InContractId", InContractId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
        return new Implement().getPcListDtl(maper);

    }

    List<Integer> getCountPcListDtl(String InContractId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InContractId", InContractId);
        return new Implement().getCountPcListDtl(maper);
    }

    public Map doPcUpdateStatus(String InContractId, String InContractStatus, String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InContractId", InContractId);
        maper.put("InContractStatus", InContractStatus);
        maper.put("InUserId", InUserId);
        showLog(maper, "doPcUpdateStatus");
        new Implement().doPcUpdateStatus(maper);
        return maper;
    }

    public Map doPcSaveDtl(String InAction, String InContractId, String InContractDtlId, String InItem, String InItemId, String InAgreedQty, String InPurchaseUnit, String InPrice,
            String InPctDisc, String InAmtDisc, String InMinQty, String InMaxQty, String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InAction", InAction);
        maper.put("InContractId", InContractId);
        maper.put("InContractDtlId", InContractDtlId);
        maper.put("InItem", InItem);
        maper.put("InItemId", InItemId);
        maper.put("InAgreedQty", InAgreedQty);
        maper.put("InPurchaseUnit", InPurchaseUnit);
        maper.put("InPrice", InPrice);
        maper.put("InPctDisc", InPctDisc);
        maper.put("InAmtDisc", InAmtDisc);
        maper.put("InMinQty", InMinQty);
        maper.put("InMaxQty", InMaxQty);
        maper.put("InUserId", InUserId);
        showLog(maper, "doPcSaveDtl");
        new Implement().doPcSaveDtl(maper);
        return maper;
    }

    public Map doPcDeleteDtl(String InContractId, String InContractDtlId, String InUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InContractId", InContractId);
        map.put("InContractDtlId", InContractDtlId);
        map.put("InUserId", InUserId);
        new Implement().doPcDeleteDtl(map);
        return map;
    }

    public List<LovsupParam> getLovSupplierPC(String InSupplier) {
        Map<String, Object> map = new HashMap<>();
        map.put("InSupplier", InSupplier);
        return new Implement().getLovSupplierPC(map);
    }

    public List<LovSiteParam> getLovSite(String InSupplierId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InSupplierId", InSupplierId);
        return new Implement().getLovSite(map);
    }

    public List<LovForwardAgentParam> getLovForwardAgent(String InAgent) {
        Map<String, Object> map = new HashMap<>();
        map.put("InAgent", InAgent);
        return new Implement().getLovForwardAgent(map);
    }

    public Map dopcValidasiHdr(String InEvent, String InAction, String InContractId, String InDescription,
            String InSupplierId, String InSupplierCode, String InTop, String InCurrency, String InForwardAgent,
            String InContractStatus, String InUserId, String InSiteCode, String InSiteId, String InContractMapNo, String InAgreedQty,
            String InAgreedAmount, String InEffectiveDate, String InExpiredDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("InEvent", InEvent);
        map.put("InAction", InAction);
        map.put("InContractId", InContractId);
        map.put("InDescription", InDescription);
        map.put("InSupplierId", InSupplierId);
        map.put("InSupplierCode", InSupplierCode);
        map.put("InTop", InTop);
        map.put("InCurrency", InCurrency);
        map.put("InForwardAgent", InForwardAgent);
        map.put("InContractStatus", InContractStatus);
        map.put("InUserId", InUserId);
        map.put("InSiteCode", InSiteCode);
        map.put("InSiteId", InSiteId);
        map.put("InContractMapNo", InContractMapNo);
        map.put("InAgreedQty", InAgreedQty);
        map.put("InAgreedAmount", InAgreedAmount);
        map.put("InEffectiveDate", InEffectiveDate);
        map.put("InExpiredDate", InExpiredDate);
        showLog(map, "dopcValidasiHdr");
        return new Implement().dopcValidasiHdr(map);
    }

    public Map doPcValidasiDtl(String InEvent, String InAction, String InContractId, String InContractDtlId, String InItem, String InItemId,
            String InAgreedQty, String InPurchaseUnit, String InPrice, String InPctDisc, String InAmtDisc, String InMinQty, String InMaxQty) {
        Map<String, Object> map = new HashMap<>();
        map.put("InEvent", InEvent);
        map.put("InAction", InAction);
        map.put("InContractId", InContractId);
        map.put("InContractDtlId", InContractDtlId);
        map.put("InItem", InItem);
        map.put("InItemId", InItemId);
        map.put("InAgreedQty", InAgreedQty);
        map.put("InPurchaseUnit", InPurchaseUnit);
        map.put("InPrice", InPrice);
        map.put("InPctDisc", InPctDisc);
        map.put("InAmtDisc", InAmtDisc);
        map.put("InMinQty", InMinQty);
        map.put("InMaxQty", InMaxQty);
        System.out.println(map);
        return new Implement().doPcValidasiDtl(map);
    }

    public Map doRpDelete(String InReqId, String InReqDtlId, String InStatus, String InUserId) {
        Map<String, Object> mapper = new HashMap<String, Object>();
        mapper.put("InReqId", InReqId);
        mapper.put("InReqDtlId", InReqDtlId);
        mapper.put("InStatus", InStatus);
        mapper.put("InUserId", InUserId);
        new Implement().doRpDelete(mapper);
        return mapper;
    }

    public List<ListHdrAutoGenParam> getListHdrAutoGen() {
        return getListHdrAutoGen("", "", "");
    }

    public List<ListHdrAutoGenParam> getListHdrAutoGen(String InAutoGenNum, String InDateFrom, String InDateTo) {
        Map<String, Object> map = new HashMap<>();
        map.put("InAutoGenNum", InAutoGenNum);
        map.put("InDateFrom", InDateFrom);
        map.put("InDateTo", InDateTo);
        return new Implement().getListHdrAutoGen(map);
    }

    public List<ProductionIssued> listPi(Map map) {
        return new Implement().listPi(map);
    }

    public Map validateInsertHdr(Map<String, Object> map) {
        new Implement().validateInsertHdr(map);
        showLog(map, "validateInsertHdr");
        return map;
    }

    public Map insertHdr(Map<String, Object> map) {
        new Implement().insertHdr(map);
        showLog(map, "insertHdr");
        return map;
    }

    public ProductionIssued listHdr(Map map) {
        ProductionIssued list = new Implement().listHdr(map);
        showLog(map, "listHdr");
        return list;
    }

    public Map validateUpdateHdr(Map<String, Object> map) {
        new Implement().validateUpdateHdr(map);
        showLog(map, "validateUpdateHdr");
        return map;
    }

    public Map updateHdr(Map<String, Object> map) {
        new Implement().updateHdr(map);
        showLog(map, "updateHdr");
        return map;
    }

    public Map validateInsertDtl(Map<String, Object> map) {
        new Implement().validateInsertDtl(map);
        showLog(map, "validateInsertDtl");
        return map;
    }

    public Map insertDtl(Map<String, Object> map) {
        new Implement().insertDtl(map);
        showLog(map, "insertDtl");
        return map;
    }

    public Map validateUpdateDtl(Map<String, Object> map) {
        new Implement().validateUpdateDtl(map);
        showLog(map, "validateUpdateDtl");
        return map;
    }

    public Map updateDtl(Map<String, Object> map) {
        new Implement().updateDtl(map);
        showLog(map, "updateDtl");
        return map;
    }

    public List<DtlItmProductionIssue> listDtl(Map map) {
        return new Implement().listDtl(map);
    }

    public Map validateStatus(Map<String, Object> map) {
        new Implement().validateStatus(map);
        showLog(map, "validateStatus");
        return map;
    }

    public Map updateStatusPi(Map<String, Object> map) {
        new Implement().updateStatusPi(map);
        showLog(map, "updateStatusPi");
        return map;
    }

    public Map insertSelectSnTemp(String InProdIssueDtlId, String InWoId, String InWhId, String InItemId, String InUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InProdIssueDtlId", InProdIssueDtlId);
        map.put("InWoId", InWoId);
        map.put("InWhId", InWhId);
        map.put("InItemId", InItemId);
        map.put("InUserId", InUserId);
        showLog(map, "insertSelectSnTemp");
        new Implement().insertSelectSnTemp(map);
        return map;
    }

    public Map insertSelectSnTemp(Map<String, Object> map) {
        new Implement().insertSelectSnTemp(map);
        showLog(map, "insertSelectSnTemp");
        return map;
    }

    public List<SelectRange> listHeaderRange(Map map) {
        return new Implement().listHeaderRange(map);
    }

    public List<SelectRange> listDetailRange(Map map) {
        return new Implement().listDetailRange(map);
    }

    public Map validateSelectSn(Map<String, Object> map) {
        new Implement().validateSelectSn(map);
        showLog(map, "validateSelectSn");
        return map;
    }

    public Map invSelectSN(Map<String, Object> map) {
        new Implement().invSelectSN(map);
        showLog(map, "invSelectSN");
        return map;
    }

    public Map invUnselectSN(Map<String, Object> map) {
        new Implement().invUnselectSN(map);
        showLog(map, "invUnselectSN");
        return map;
    }

    public Map autoInvSelectSN(Map<String, Object> map) {
        new Implement().autoInvSelectSN(map);
        showLog(map, "autoInvSelectSN");
        return map;
    }

    public void showLog(Map<String, Object> map, String title) {
        System.out.println("title:" + title);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + ":" + value);
        }
    }

    public Map doWoUpdateStatus(String InPoId, String InStatus, String InUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InPoId", InPoId);
        map.put("InStatus", InStatus);
        map.put("InUserId", InUserId);
        new Implement().doWoUpdateStatus(map);
        showLog(map, "doWoUpdateStatus");
        return map;
    }
    public Map doWoUpdateStatusUpload(String InPoId, String InStatus, String InUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InPoId", InPoId);
        map.put("InStatus", InStatus);
        map.put("InUserId", InUserId);
        new Implement().doWoUpdateStatusUpload(map);
        showLog(map, "doWoUpdateStatusUpload");
        return map;
    }

    public Map doDeleteListDtl(String InProdIssueId, String InProdIssueDtlId, String InStatus, String InUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InProdIssueId", InProdIssueId);
        map.put("InProdIssueDtlId", InProdIssueDtlId);
        map.put("InStatus", InStatus);
        map.put("InUserId", InUserId);
        new Implement().doDeleteListDtl(map);
        showLog(map, "doDeleteListDtl");
        return map;
    }

    public Map doPairingInsertDtl(String PI_PairingId, String PI_ItemID, String PI_ProdIssueId, String PI_FromSN, String PI_FromTo, String PI_CreatedBy) {
        Map<String, Object> map = new HashMap<>();
        map.put("PI_PairingId", PI_PairingId);
        map.put("PI_ItemID", PI_ItemID);
        map.put("PI_ProdIssueId", PI_ProdIssueId);
        map.put("PI_FromSN", PI_FromSN);
        map.put("PI_FromTo", PI_FromTo);
        map.put("PI_CreatedBy", PI_CreatedBy);
        new Implement().doPairingInsertDtl(map);
        return map;
    }

    public Map doWoCreateRangeSNInv(String InPoId,String InPoLine) {
        Map<String, Object> map = new HashMap<>();
        map.put("InPoId", InPoId);
        map.put("InPoLine", InPoLine);
        showLog(map, "doWoCreateRangeSNInv");
        new Implement().doWoCreateRangeSNInv(map);
        return map;
    }

    public List<BdlListHdrParam> getBdlListHdr(String InOfId) {
        return getBdlListHdr(InOfId, null);
    }

    public List<BdlListHdrParam> getBdlListHdr(String InOfId, Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        map.put("InOfId", InOfId == null ? "" : InOfId);
        if (searchMap != null) {
            map.put("search", searchMap);
        }
        showLog(map, "getBdlListHdr");

        return new Implement().getBdlListHdr(map);
    }

    public Map doOfValidateInsertHdrbdl(String inPoId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        Map map = new Implement().doOfValidateInsertHdrbdl(maper);
        mapLog(maper, "doOfValidateInsertHdrbdl");
        return map;
    }

    public Map doOfValidateUpdateHdrbdl(String inIfId, String inPoId, String inFileName, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inIfId", inIfId);
        maper.put("inPoId", inPoId);
        maper.put("inFileName", inFileName);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doOfValidateUpdateHdrbdl(maper);
        mapLog(maper, "doOfValidateUpdateHdrbdl");
        return map;
    }

    public Map doOfInsertHdrbdl(String inPoId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doOfInsertHdrbdl(maper);
        mapLog(maper, "doOfInsertHdrbdl");
        return map;
    }

    public Map doOfUpdateHdrbdl(String inOfId, String inPoId, String inFileName, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inOfId", inOfId);
        maper.put("inPoId", inPoId);
        maper.put("inFileName", inFileName);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doOfUpdateHdrbdl(maper);
        mapLog(maper, "doOfUpdateHdrbdl");
        return map;
    }

    public Map doOfSubmitHdrbdl(String inOfId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inOfId", inOfId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doOfSubmitHdrbdl(maper);
        mapLog(maper, "doOfSubmitHdrbdl");
        return map;
    }

    public Map doOfApproveHdrbdl(String inOfId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inOfId", inOfId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doOfApproveHdrbdl(maper);
        mapLog(maper, "doOfApproveHdrbdl");
        return map;
    }

    public Map doOfCancelHdrbdl(String inOfId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inOfId", inOfId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doOfCancelHdrbdl(maper);
        mapLog(maper, "doOfCancelHdrbdl");
        return map;
    }

    public Map doOfInsertDetailbdl(String inOfId, String inOFNumber, String inPoId, String inSNTCash, String inSNInv, String inUID) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inOfId", inOfId);
        maper.put("inOFNumber", inOFNumber);
        maper.put("inPoId", inPoId);
        maper.put("inSNTCash", inSNTCash);
        maper.put("inSNInv", inSNInv);
        maper.put("inUID", inUID);
        Map map = new Implement().doOfInsertDetailbdl(maper);
        mapLog(maper, "doOfInsertDetailbdl");
        return map;
    }

    public List<BdlListDtlParam> BdlListDtl(String InOfId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InOfId", InOfId);
        return new Implement().BdlListDtl(map);
    }

    public Map doWoUpdatePairingSn(String InPoId, String InPoLine, String InUploadId, String InSupplierId, String InSupplierCode, String InItemId,String InUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InPoId", InPoId);
        map.put("InPoLine", InPoLine);
        map.put("InUploadId", InUploadId);
        map.put("InSupplierId", InSupplierId);
        map.put("InSupplierCode", InSupplierCode);
        map.put("InUserId", InUserId);
        map.put("InItemId", InItemId);
        showLog(map, "doWoUpdatePairingSn");
        new Implement().doWoUpdatePairingSn(map);
//        showLog(map, "doWoUpdatePairingSn");
        return map;

    }

    public List<wolistHDRPAram> getWoListPairingHDR(String InPoId,String InPoLine) {
        Map<String, Object> map = new HashMap<>();
        map.put("InPoId", InPoId);
        map.put("InPoLine", InPoLine);
        showLog(map, "getWoListPairingHDR");
        return new Implement().getWoListPairingHDR(map);
    }

    public List<ListSNUploadErrorParam> getListSNUploadError(String InUploadId,String InPoLine) {
        Map<String, Object> map = new HashMap<>();
        map.put("InUploadId", InUploadId);
        map.put("InPoLine", InPoLine);
        showLog(map, "getListSNUploadError");
        return new Implement().getListSNUploadError(map);
    }

    public Map doGetPathFile(String InDir) {
        Map<String, Object> map = new HashMap<>();
        map.put("InDir", InDir);
        showLog(map, "doGetPathFile");
        new Implement().doGetPathFile(map);
        return map;
    }

    public List<ListAddDtlPairingParam> getListAddDtlPairing(String InPoIdTcash, String InPoIdLoop) {
        Map<String, Object> map = new HashMap();
        map.put("InPoIdTcash", InPoIdTcash);
        map.put("InPoIdLoop", InPoIdLoop);
        return new Implement().getListAddDtlPairing(map);
    }

    public Map PairingSnTcashLoop(String InPoIdTcash, String InPoIdLoop) {
        Map<String, Object> map = new HashMap();
        map.put("InPoIdTcash", InPoIdTcash);
        map.put("InPoIdLoop", InPoIdLoop);
        new Implement().PairingSnTcashLoop(map);
        return map;
    }

    public List<ListHdrBundlingParam> getListHdrBundling() {
        return getListHdrBundling(null, userId,responsibilityId, null);
    }

    public List<ListHdrBundlingParam> getListHdrBundling(String InBundlingId,String InUserId,String InResponId, Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        map.put("InBundlingId", InBundlingId == null ? "" : InBundlingId);
        map.put("InUserId", userId);
        map.put("InResponId", responsibilityId);
        if (searchMap != null) {
            map.put("search", searchMap);
        }
        return new Implement().getListHdrBundling(map);
    }

    public List<ListDtlbundlingParam> getListDtlBundling(String InBundlingId) {
        Map map = new HashMap();
        map.put("InBundlingId", InBundlingId);
        return new Implement().getListDtlBundling(map);
    }

    public Map doOnInsertHdr(String bdlHdrId,String InItemId, String InItemCode, String InItemDesc,String BuId,String BuCode,String BuDesc,
            String WipId,String WipCode,String WipDesc,String WhId,String WhCode,String WhDesc,String InQty,String InSupplierId,String InSupplierCode,String InSupplierName ,String InUserId) {
        Map map = new HashMap();
        map.put("InBdlHdrId", bdlHdrId);
        map.put("InItemId", InItemId);
        map.put("InItemCode", InItemCode);
        map.put("InItemDesc", InItemDesc);
        map.put("InBuId", BuId);
        map.put("InBuCode", BuCode);
        map.put("InBuDesc", BuDesc);
        map.put("InWipId", WipId);
        map.put("InWipCode", WipCode);
        map.put("InWipDesc", WipDesc);
        map.put("InWhId", WhId);
        map.put("InWhCode", WhCode);
        map.put("InWhDesc", WhDesc);
        map.put("InSupplierId", InSupplierId);
        map.put("InSupplierCode", InSupplierCode);
        map.put("InSupplierName", InSupplierName);
        map.put("InQty", InQty);
        map.put("InUserId", InUserId);
        showLog(map, "doOnInsertHdr");
        new Implement().doOnInsertHdr(map);
        return map;
    }
    public Map doOnUpdateHdr(String InBundlingId,String InItemId, String InItemCode, String InItemDesc,String InQty,String InUserId,
            String InSupplierId,String InSupplierCode,String InSupplierName,String BuId,String BuCode,String BuDesc,
            String WipId,String WipCode,String WipDesc,String WhId,String WhCode,String WhDesc ) {
        Map map = new HashMap();
        map.put("InBundlingId", InBundlingId);
        map.put("InItemId", InItemId);
        map.put("InItemCode", InItemCode);
        map.put("InItemDesc", InItemDesc);
        map.put("InQty", InQty);
        map.put("InUserId", InUserId);
        map.put("InSupplierId", InSupplierId);
        map.put("InSupplierCode", InSupplierCode);
        map.put("InSupplierName", InSupplierName);
        map.put("InBuId", BuId);
        map.put("InBuCode", BuCode);
        map.put("InBuDesc", BuDesc);
        map.put("InWipId", WipId);
        map.put("InWipCode", WipCode);
        map.put("InWipDesc", WipDesc);
        map.put("InWhId", WhId);
        map.put("InWhCode", WhCode);
        map.put("InWhDesc", WhDesc);
        showLog(map, "doOnUpdateHdr");
        new Implement().doOnUpdateHdr(map);
        return map;
    }

    public Map doOnInsertDtl(String InBundlingId, String InUserId) {
        Map map = new HashMap();
        map.put("InBundlingId", InBundlingId);
        map.put("InUserId", InUserId);
        showLog(map, "doOnInsertDtl");
        new Implement().doOnInsertDtl(map);
        return map;
    }
    
    public Map doInsertQtyByRegional(String InBundlingId,String InBundlingDtlId,String InItemId,String InRegionalId,String InTresholdQty,String InQty,String InUserId){
       Map<String,Object>map = new HashMap<>();
       map.put("InBundlingId", InBundlingId);
       map.put("InBundlingDtlId", InBundlingDtlId);
       map.put("InItemId", InItemId);
       map.put("InRegionalId", InRegionalId);
       map.put("InTresholdQty", InTresholdQty);
       map.put("InQty", InQty);
       map.put("InUserId", InUserId);
       showLog(map, "doInsertQtyByRegional");
       new Implement().doInsertQtyByRegional(map);
       return map;
    }

    public List<ListSelectSNPAram> getListSelectSN(String InMode, String InBundlingId,String InItemId,String InRegionalId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InMode", InMode);
        map.put("InBundlingId", InBundlingId);
        map.put("InItemId", InItemId);
        map.put("InRegionalId", InRegionalId);
        showLog(map, "getListSelectSN");
        return new Implement().getListSelectSN(map);
    }

    public Map dovalidateEditSN(String InBundlingId,String InRegionalId,String InItemId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InBundlingId", InBundlingId);
        map.put("InRegionalId", InRegionalId);
        map.put("InItemId", InItemId);
        showLog(map, "dovalidateEditSN");
        new Implement().dovalidateEditSN(map);
        return map;
    }

    public Map doSetSelectSN(String InMode, String InBundlingId, String InItemLocId, String InRangeFrom, String InRangeTo,String InRegionalId,String InQty, String InUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("InMode", InMode);
        map.put("InBundlingId", InBundlingId);
        map.put("InItemLocId", InItemLocId);
        map.put("InRangeFrom", InRangeFrom);
        map.put("InRangeTo", InRangeTo);
        map.put("InRegionalId", InRegionalId);
        map.put("InQty", InQty);
        map.put("InUserId", InUserId);
        showLog(map, "doSetSelectSN");
        new Implement().doSetSelectSN(map);
        return map;
    }

    public Map doUpdateStatusHdr(String InBundlingId, String InStatus, String InUserId) {
        Map map = new HashMap();
        map.put("InBundlingId", InBundlingId);
        map.put("InStatus", InStatus);
        map.put("InUserId", InUserId);
        new Implement().doUpdateStatusHdr(map);
        return map;
    }
    
    public Map doCanceled(String InBundlingId, String InUserId) {
        Map map = new HashMap();
        map.put("InBundlingId", InBundlingId);
        map.put("InUserId", InUserId);
        showLog(map, "doCanceled");
        new Implement().doCanceled(map);
        return map;
    }
    
   
   public  List<BundlingGDListHDRParam> getGdListHdrBundling() {
       return getGdListHdrBundling("", "", "", "", "", "");
    }
   
   public  List<BundlingGDListHDRParam> getGdListHdrBundling(String InBundlingNo, String InGdId, String InGdNo, String InStartDate, String InEndDate, String InStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InBundlingNo", InBundlingNo==null ? "":InBundlingNo);
        maper.put("InGdId", InGdId==null ? "":InGdId);
        maper.put("InGdNo", InGdNo==null ? "":InGdNo);
        maper.put("InStartDate", InStartDate==null ? "":InStartDate);
        maper.put("InEndDate", InEndDate==null ? "": InEndDate);
        maper.put("InStatus", InStatus== null ? "": InStatus);
        maper.put("InUserId",userId );
        maper.put("InResponId",responsibilityId );
        mapLog(maper, "getGdListHdrbundling");
        return new Implement().getGdListHdrBundling(maper);
    }
   public Map doGdSelectRangeBundling(String inSelectMode, String inPoId, String inPositionNum, String inGdHdrId,
            String inLineNo, String inRangeFrom, String inRangeTo, String inRangeQty) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inSelectMode", inSelectMode);
        maper.put("inPoId", inPoId);
        maper.put("inPositionNum", inPositionNum);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inLineNo", inLineNo);
        maper.put("inRangeFrom", inRangeFrom);
        maper.put("inRangeTo", inRangeTo);
        maper.put("inRangeQty", inRangeQty);
        mapLog(maper, "doGdSelectRangeBundling");
        Map map = new Implement().doGdSelectRangeBundling(maper);
        mapLog(maper, "doGdSelectRange");
        return map;
    }
   
    public Map doGdSelectRangeOkBundling(String inPoId, String inPoLineRef, String inQty,
            String inTotalQty) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inPoLineRef", inPoLineRef);
        maper.put("inQty", inQty);
        maper.put("inTotalQty", inTotalQty);
        mapLog(maper, "doGdSelectRangeOkBundling");
        Map map = new Implement().doGdSelectRangeOkBundling(maper);
        return map;

    }
    
    public Map doGdSumSelectQtyBundling(String inPoId, String inPoLineRef, String inGdHdrId,
            String inGdLineNo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inPoLineRef", inPoLineRef);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        Map map = new Implement().doGdSumSelectQtyBundling(maper);
        mapLog(maper, "doGdSumSelectQtyBundling");
        return map;

    }
    
 
    
    
     public List<ListBundlingGRHDR>getGrListHdrbundling(String inReceiveId, String inRcvNo, String inPoNo, String inGdNo, String inStartDate,
            String inEndDate, String inStatus, String pageNumber, String pageSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReceiveId", inReceiveId);
        maper.put("inRcvNo", inRcvNo);
        maper.put("inPoNo", inPoNo);
        maper.put("inGdNo", inGdNo);
        maper.put("inStartDate", inStartDate);
        maper.put("inEndDate", inEndDate);
        maper.put("inStatus", inStatus);
        maper.put("InUserId", userId);
        maper.put("InResponId", responsibilityId);
        maper.put("pageNumber", pageNumber);
        maper.put("pageSize", pageSize);
      return new Implement().getGrListHdrbundling(maper);
     }
     
      List<Integer> getCountGrListHdrBundling(String inReceiveId, String inRcvNo, String inPoNo, String inGdNo, String inStartDate,
            String inEndDate, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReceiveId", inReceiveId);
        maper.put("inRcvNo", inRcvNo);
        maper.put("inPoNo", inPoNo);
        maper.put("inGdNo", inGdNo);
        maper.put("inStartDate", inStartDate);
        maper.put("inEndDate", inEndDate);
        maper.put("inStatus", inStatus);
        maper.put("InUserId", userId);
        maper.put("InResponId", responsibilityId);
        return new Implement().getCountGrListHdrbundling(maper);
    }
      
        List<ListBundlingGRHDR> selectGrListHdrbundling(String inReceiveId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReceiveId", inReceiveId);
        maper.put("InUserId", userId);
        maper.put("InResponId", responsibilityId);
        return new Implement().selectGrListHdrbundling(maper);
    }
         public Map doGrSaveHdrbundling(String InAction, String inRcvId, String inPoId, String inPoNo, String inWhId,
            String inBuId, String inGdHdrId, String inSupplierId, String inSiteId, String inUserId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inAction", InAction);
        maper.put("inRcvId", inRcvId);
        maper.put("inPoId", inPoId);
        maper.put("inPoNo", inPoNo);
        maper.put("inWhId", inWhId);
        maper.put("inBuId", inBuId);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inSupplierId", inSupplierId);
        maper.put("inSiteId", inSiteId);
        maper.put("inUserId", inUserId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGrSaveHdrbundling(maper);
        mapLog(maper, "doGrSaveHdrbundling");
        return map;
    }

    public Map doGrSelectGdValidatebundling(String inPoId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGrSelectGdValidatebundling(maper);
        mapLog(maper, "doGrSelectGdValidatebundling");
        return map;
    }

    List<ListGrSelectGDParam> selectGrListSelectGDbundling(String gdId, String poId, String grId) {
        System.out.println("gdId=" + gdId);
        System.out.println("poId=" + poId);
        System.out.println("poId=" + grId);
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("gdId", gdId);
        maper.put("poId", poId);
        maper.put("grId", grId);
        return new Implement().selectGrListSelectGDbundling(maper);
    }

    public Map doGrSaveDtlbundling(String inAction, String inReceiveId, String inReceiveLine, String inReceiveDtlId,
            String inItemId, String inItemCode, String inLotId, String inOrderQty, String inReceiveQty, String inRejectQty,
            String inUserId, String inHlrMapId, String inApprovedQty, String inLotSize, String inPoId, String inPoNo, String inPoLineRef,
            String inPoLineId, String inUOM, String inBalanceQty, String inDeliverQty, String inAcceptQty, String inGdLineNo,
            String inGdHdrId, String inWhId, String inWhLocId, String inRegionalId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inAction", inAction);
        maper.put("inReceiveId", inReceiveId);
        maper.put("inReceiveLine", inReceiveLine);
        maper.put("inReceiveDtlId", inReceiveDtlId);
        maper.put("inItemId", inItemId);
        maper.put("inItemCode", inItemCode);
        maper.put("inLotId", inLotId);
        maper.put("inOrderQty", inOrderQty);
        maper.put("inReceiveQty", inReceiveQty);
        maper.put("inRejectQty", inRejectQty);
        maper.put("inUserId", inUserId);
        maper.put("inHlrMapId", inHlrMapId);
        maper.put("inApprovedQty", inApprovedQty);
        maper.put("inLotSize", inLotSize);
        maper.put("inPoId", inPoId);
        maper.put("inPoNo", inPoNo);
        maper.put("inPoLineRef", inPoLineRef);
        maper.put("inPoLineId", inPoLineId);
        maper.put("inUOM", inUOM);
        maper.put("inBalanceQty", inBalanceQty);
        maper.put("inDeliverQty", inDeliverQty);
        maper.put("inAcceptQty", inAcceptQty);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inWhId", inWhId);
        maper.put("inWhLocId", inWhLocId);
        maper.put("inRegionalId", inRegionalId);
        mapLog(maper, "doGrSaveDtlbundling");
        Map map = new Implement().doGrSaveDtlbundling(maper);
        return map;
    }

    List<ListresultGrListDtlParam> selectGrListDtlbundling(String rcvId) {
        System.out.println("rcvId=" + rcvId);
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("rcvId", rcvId);
        return new Implement().selectGrListDtlbundling(maper);
    }

    List<ListGrListEditSNParam> selectGrListEditSNbundling(String inList, String gdId, String gdLine, String rcvId, String rcvLine) {
        Map<String, Object> maper = new HashMap<String, Object>();
        System.out.println("selectGrListEditSN");
        System.out.println("inList=" + inList);
        System.out.println("gdId=" + gdId);
        System.out.println("gdLine=" + gdLine);
        System.out.println("rcvId=" + rcvId);
        System.out.println("rcvLine=" + rcvLine);
        maper.put("inList", inList);
        maper.put("gdId", gdId);
        maper.put("gdLine", gdLine);
        maper.put("rcvId", rcvId);
        maper.put("rcvNo", rcvLine);
        return new Implement().selectGrListEditSNbundling(maper);
    }

    public Map doGrSelectRangeValidatebundling(String inUserId, String inResponId,
            String inBuId, String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGrSelectRangeValidatebundling(maper);
        mapLog(maper, "doGrSelectRangeValidatebundling");
        return map;
    }

    public Map doGrUnSelectRangeValidatebundling(String inUserId, String inResponId,
            String inBuId, String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGrUnSelectRangeValidatebundling(maper);
        mapLog(maper, "doGrUnSelectRangeValidatebundling");
        return map;
    }

    public Map doGrSelectRangebundling(String inSelectMode, String inGdHdrId, String inGdLineNo,
            String inReceiveId, String inReceiveLine, String inRangeFrom, String inRangeTo, String inRangeQty) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inSelectMode", inSelectMode);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inReceiveId", inReceiveId);
        maper.put("inReceiveLine", inReceiveLine);
        maper.put("inRangeFrom", inRangeFrom);
        maper.put("inRangeTo", inRangeTo);
        maper.put("inRangeQty", inRangeQty);
        mapLog(maper, "doGrSelectRange");
        Map map = new Implement().doGrSelectRangebundling(maper);
        mapLog(maper, "doGrSelectRangebundling");
        return map;
    }

    public Map doGrConfirmRcvValidatebundling(String inReceiveId, String inUserId,
            String inResponId, String inBuId, String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReceiveId", inReceiveId);
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGrConfirmRcvValidatebundling(maper);
        mapLog(maper, "doGrConfirmRcvValidatebundling");
        return map;
    }

    public Map doGrConfirmRcvValidateLinebundling(String inReceiveId, String inReceiveLine, String inRcvQty,
            String inGdHdrId, String inGdLineNo, String inItemId, String inItemCode, String inWhLoc, String inWhLocId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReceiveId", inReceiveId);
        maper.put("inReceiveLine", inReceiveLine);
        maper.put("inRcvQty", inRcvQty);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inItemId", inItemId);
        maper.put("inItemCode", inItemCode);
        maper.put("inWhLoc", inWhLoc);
        maper.put("inWhLocId", inWhLocId);
        showLog(maper, "doGrConfirmRcvValidateLinebundling");
        Map map = new Implement().doGrConfirmRcvValidateLinebundling(maper);
        return map;
    }

    public Map doGrConfirmReceivebundling(String inRcvId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inRcvId", inRcvId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doGrConfirmReceivebundling(maper);
        mapLog(maper, "doGrConfirmReceivebundling");
        return map;

    }

    public Map doGrCancelValidatebundling(String inUserId, String inResponId,
            String inBuId, String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGrCancelValidatebundling(maper);
        mapLog(maper, "doGrCancelValidatebundling");
        return map;

    }

    public Map doGrCancelbundling(String inRcvId, String flag) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inRcvId", inRcvId);
        maper.put("inFlag", flag);
        Map map = new Implement().doGrCancel(maper);
        mapLog(maper, "doGrCancelbundling");
        return map;
    }

    public Map doGrCancelLinebundling(String inRcvId, String inRcvLine,
            String inGdHdrId, String inGdLineNo, String inItemId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inRcvId", inRcvId);
        maper.put("inRcvLine", inRcvLine);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inItemId", inItemId);
        Map map = new Implement().doGrCancelLinebundling(maper);
        mapLog(maper, "doGrCancelLinebundling");
        return map;
    }

    public Map doGrApproveValidatebundling(String inUserId, String inResponId,
            String inBuId, String inWhId, String inStatus) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inUserId", inUserId);
        maper.put("inResponId", inResponId);
        maper.put("inBuId", inBuId);
        maper.put("inWhId", inWhId);
        maper.put("inStatus", inStatus);
        Map map = new Implement().doGrApproveValidatebundling(maper);
        mapLog(maper, "doGrApproveValidatebundling");
        return map;

    }

    public Map doGrApproveValidateLinebundling(String inReceiveId, String inReceiveLine, String inRcvQty,
            String inGdHdrId, String inGdLineNo, String inItemId, String inItemCode, String inWhLoc, String inWhLocCode) {
        Map<String, Object> maper = new HashMap<String, Object>();
        System.out.println("inRcvQty=" + inRcvQty);
        maper.put("inItemId", inItemId);
        maper.put("inItemCode", inItemCode);
        maper.put("inReceiveId", inReceiveId);
        maper.put("inReceiveLine", inReceiveLine);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inWhLocCode", inWhLocCode);
        maper.put("inRcvQty", inRcvQty);
        Map map = new Implement().doGrApproveValidateLinebundling(maper);
        mapLog(maper, "doGrApproveValidateLinebundling");
        return map;
    }

    public Map doGrApprovebundling(String inRcvId, String inPoId, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inRcvId", inRcvId);
        maper.put("inPoId", inPoId);
        maper.put("inUserId", inUserId);
        Map map = new Implement().doGrApprovebundling(maper);
        mapLog(maper, "doGrApprove");
        return map;

    }

    public Map doGrUpdateDtlWhLocbundling(String inRcvId, String inRcvLine, String inWhLocId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        System.out.println("doGrUpdateDtlWhLoc");
        System.out.println("inRcvId=" + inRcvId);
        System.out.println("inRcvLine=" + inRcvLine);
        System.out.println("inWhLocId=" + inWhLocId);
        maper.put("inRcvId", inRcvId);
        maper.put("inRcvLine", inRcvLine);
        maper.put("inWhLocId", inWhLocId);
        Map map = new Implement().doGrUpdateDtlWhLocbundling(maper);
        mapLog(maper, "doGrUpdateDtlWhLocbundling");
        return map;
    }

    public Map doGrDeleteDtlbundling(String inReceiveDtlId, String inReceiveQty, String inGdHdrId, String inGdLineNo, String inUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inReceiveDtlId", inReceiveDtlId);
        maper.put("inReceiveQty", inReceiveQty);
        maper.put("inGdHdrId", inGdHdrId);
        maper.put("inGdLineNo", inGdLineNo);
        maper.put("inUserId", inUserId);
        mapLog(maper, "doGrDeleteDtlbundling");
        Map map = new Implement().doGrDeleteDtlbundling(maper);
        return map;

    }

    public Map doGrSumSelectQtybundling(String inPoId, String inPoLineRef, String inGrHdrId,
            String inGrLineNo, String inGdId, String inGdLineNo) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inPoId", inPoId);
        maper.put("inPoLineRef", inPoLineRef);
        maper.put("inGrHdrId", inGrHdrId);
        maper.put("inGrLineNo", inGrLineNo);
        maper.put("inGdId", inGdId);
        maper.put("inGdLineNo", inGdLineNo);
        Map map = new Implement().doGrSumSelectQtybundling(maper);
        mapLog(maper, "doGrSumSelectQtybundling");
        return map;

    }

    public Map doGrSetLotSizebundling(String inRcvId, String inRcvDtlId, String inLotSize) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("inRcvId", inRcvId);
        maper.put("inRcvDtlId", inRcvDtlId);
        maper.put("inLotSize", inLotSize);
        Map map = new Implement().doGrSetLotSizebundling(maper);
        mapLog(maper, "doGrSetLotSizebundling");
        return map;

    }
    
    public Map doGetItemRegional(String InItemId) {
        Map<String, Object> maper = new HashMap<>();
        maper.put("InItemId", InItemId);
        Map map = new Implement().doGetItemRegional(maper);
        mapLog(maper, "doGetItemRegional");
        return maper;

    }
    
      public List<listDtlRegionalParam>getListDtlRegional(String InBundlingDtlId){
         Map<String, Object> maper = new HashMap<>();
        maper.put("InBundlingDtlId", InBundlingDtlId);
       return new Implement().getListDtlRegional(maper);
       
      }
      
    public Map doOnUpdateDtl(String InBundlingDtlId,String InQty) {
        Map<String, Object> maper = new HashMap<>();
        maper.put("InBundlingDtlId", InBundlingDtlId);
        maper.put("InQty", InQty);
        mapLog(maper, "doOnUpdateDtl");
        new Implement().doOnUpdateDtl(maper);
        return maper;

    }
    
      
    public Map doDeleteQtyByRegional(String InId) {
        Map<String, Object> maper = new HashMap<>();
        maper.put("InId", InId);
        mapLog(maper, "doDeleteQtyByRegional");
        new Implement().doDeleteQtyByRegional(maper);
        return maper;

    }
    
     public Map doUpdateQtyByRegional(String InId,String InQty,String InUserId) {
        Map<String, Object> maper = new HashMap<>();
        maper.put("InId", InId);
        maper.put("InQty", InQty);
        maper.put("InUserId", InUserId);
        mapLog(maper, "doUpdateQtyByRegional");
        new Implement().doUpdateQtyByRegional(maper);
        return maper;

    }
    
    
     public Map doOnInsertSubDtl(String InBundlingId,String InBundlingNo,String InRegionalId,String InItemId,String InQty,String InUserId) {
        Map<String, Object> maper = new HashMap<>();
        maper.put("InBundlingId", InBundlingId);
        maper.put("InBundlingNo", InBundlingNo);
        maper.put("InRegionalId", InRegionalId);
        maper.put("InItemId", InItemId);
        maper.put("InQty", InQty);
        maper.put("InUserId", InUserId);
        mapLog(maper, "doOnInsertSubDtl");
        new Implement().doOnInsertSubDtl(maper);
        return maper;

    }
    
    

  public Map doExpireMappingbdlAll(String InItemBundlingId ,String  InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InItemBundlingId", InItemBundlingId);
        maper.put("InUserId", InUserId);
        mapLog(maper, "doExpireMappingbdlAll");
        Map map = new Implement().doExpireMappingbdlAll(maper);
      
        return map;

    }
  public Map doDeleteMappingbdl(String  Inid) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("Inid", Inid);
        mapLog(maper, "doDeleteMappingbdl");
        new Implement().doDeleteMappingbdl(maper);
        return maper;

    }
  public Map getNonRegAvaiQty(String InBundlingId, String InRegionalId,String InItemId,String InWhId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InBundlingId", InBundlingId);
        maper.put("InRegionalId", InRegionalId);
        maper.put("InItemId", InItemId);
        maper.put("InWhId", InWhId);
        mapLog(maper, "getNonRegAvaiQty");
        new Implement().getNonRegAvaiQty(maper);
        return maper;

    }
  
 public List<ListSnMapBundlingParam> getListSnMapBundling(String InBundlingId,String InBundlingNo) {
     Map<String,Object>map = new HashMap();
     map.put("InBundlingId", InBundlingId);
     map.put("InBundlingNo", InBundlingNo);
     showLog(map, "getListSnMapBundling");
     return new Implement().getListSnMapBundling(map);
 } 
  


    public Map doClearUpload(String InPoId,String InPoLine) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InPoId", InPoId);
        maper.put("InPoLine", InPoLine);
         mapLog(maper, "doClearUpload");
        new Implement().doClearUpload(maper);
        return maper;

    }

    public Map doSetLarType(String InId,String InItemBdlId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InId", InId);
        maper.put("InItemBdlId", InItemBdlId);
        mapLog(maper, "doSetLarType");
        new Implement().doSetLarType(maper);
        return maper;

    }
     public Map doEEBdlTcash(String InBundlingId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InBundlingId", InBundlingId);
        mapLog(maper, "doEEBdlTcash");
        new Implement().doEEBdlTcash(maper);
        return maper;

    }
//
   public Map doOfValidateDetail(String InOfId, String InCurrentLine,String InFileName) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InOfId", InOfId);
        maper.put("InCurrentLine", InCurrentLine);
        maper.put("InFileName", InFileName);
        mapLog(maper, "doOfValidateDetail");
        new Implement().doOfValidateDetail(maper);
        return maper;

    }
//
    List<ListExportExcelParam> getListExportExcel(String InBundlingId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InBundlingId", InBundlingId);
        mapLog(maper, "getListExportExcel");
        return new Implement().getListExportExcel(maper);
    }
//
         List<ListWipParam> getListWip(String InWip) {
        Map<String, Object> maper = new HashMap<>();
        maper.put("InWip", InWip);
        showLog(maper, "getListWip");
        return new Implement().getListWip(maper);
    }
//
   public Map doWipInsert(String InWipId,String InWipCode,String InUserId) {
        Map<String, Object> maper = new HashMap<>();
        maper.put("InWipId", InWipId);
        maper.put("InWipCode", InWipCode);
        maper.put("InUserId", InUserId);
        showLog(maper, "doWipInsert");
        return new Implement().doWipInsert(maper);
    }
   
   public Map doWipDelete(String InWipId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InWipId", InWipId);
        return new Implement().doWipDelete(maper);
    }
   public Map doWipExpire(String InId,String InWipId,String InUserId) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InId", InId);
        maper.put("InWipId", InWipId);
        maper.put("InUserId", InUserId);
        showLog(maper, "doWipExpire");
        return new Implement().doWipExpire(maper);
    }

    public Map doGetTotalSN(String InMode, String InGdHdrId, String InGdLine, String InPoId,String InPoLine) {
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InMode", InMode);
        maper.put("InGdHdrId", InGdHdrId);
        maper.put("InGdLine", InGdLine);
        maper.put("InPoId", InPoId);
        maper.put("InPoLine", InPoLine);
        mapLog(maper, "doGetTotalSN");
        Map map = new Implement().doGetTotalSN(maper);
        return map;

    }
//
    public Map doGetOrderQty (String InPoId){
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InPoId", InPoId);
        mapLog(maper, "doGetOrderQty");
        Map map = new Implement().doGetOrderQty(maper);
        return map;

    }
    
      public Map getGetSnInvQty(String InPoId,String InPoLine){
        Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InPoId", InPoId);
        maper.put("InPoLine", InPoLine);
        mapLog(maper, "getGetSnInvQty");
        Map map = new Implement().getGetSnInvQty(maper);
        return map;
      }
//
//    public Map doArtExpire(String id, String userId) {
//        Map<String, Object> maper = new HashMap<String, Object>();
//        maper.put("inId", id);
//        maper.put("inUserId", userId);
//        Map map = new Implement().doArtExpire(maper);
//        mapLog(maper, "doArtExpire");
//        return map;
//
//    }
//
//    List<ArtWorkListParam> getListArtList(String inArt, String pageNumber, String pageSize) {
//        Map<String, Object> maper = new HashMap<String, Object>();
//        maper.put("inArt", inArt);
//        maper.put("pageNumber", pageNumber);
//        maper.put("pageSize", pageSize);
//        return new Implement().getListArtList(maper);
//    }
//
//    List<ArtWorkListParam> selectArtListHdr(String inId) {
//        Map<String, Object> maper = new HashMap<String, Object>();
//        maper.put("inId", inId);
//        return new Implement().selectArtListHdr(maper);
//    }
//
//    List<Integer> getCountArtList(String inArt) {
//        Map<String, Object> maper = new HashMap<String, Object>();
//        maper.put("inArt", inArt);
//        return new Implement().getCountArtList(maper);
//    }
//
//    public List<ListWoRcvParam> selectListWoRcv(String contractNo, String poNo, String rcvNo, String dateFrom, String dateTo,
//            String pageNumber, String pageSize) {
//        Map<String, Object> maper = new HashMap<String, Object>();
//        maper.put("contractNo", contractNo);
//        maper.put("poNo", poNo);
//        maper.put("rcvNo", rcvNo);
//        maper.put("dateFrom", dateFrom);
//        maper.put("dateTo", dateTo);
//        maper.put("pageNumber", pageNumber);
//        maper.put("pageSize", pageSize);
//        mapLog(maper, "selectListWoRcv");
//        return new Implement().selectListWoRcv(maper);
//
//    }
//
//    public List<ListWoRcvParam> selectListWoRcvPrint(String contractNo, String poNo, String rcvNo, String dateFrom, String dateTo) {
//        Map<String, Object> maper = new HashMap<String, Object>();
//        maper.put("contractNo", contractNo);
//        maper.put("poNo", poNo);
//        maper.put("rcvNo", rcvNo);
//        maper.put("dateFrom", dateFrom);
//        maper.put("dateTo", dateTo);
//        mapLog(maper, "selectListWoRcv");
//        return new Implement().selectListWoRcvPrint(maper);
//
//    }
//
//    List<Integer> getCountWoRcv(String contractNo, String poNo, String rcvNo, String dateFrom, String dateTo) {
//        Map<String, Object> maper = new HashMap<String, Object>();
//        maper.put("contractNo", contractNo);
//        maper.put("poNo", poNo);
//        maper.put("rcvNo", rcvNo);
//        maper.put("dateFrom", dateFrom);
//        maper.put("dateTo", dateTo);
//        mapLog(maper, "getCountWoRcv");
//        return new Implement().getCountWoRcv(maper);
//
//    }
//
//    public List<ListloadtourpParam> selectListloadtourp(String idoNo, String dateFrom, String dateTo,
//            String pageNumber, String pageSize) {
//        Map<String, Object> maper = new HashMap<String, Object>();
//        maper.put("idoNo", idoNo);
//        maper.put("dateFrom", dateFrom);
//        maper.put("dateTo", dateTo);
//        maper.put("pageNumber", pageNumber);
//        maper.put("pageSize", pageSize);
//        return new Implement().selectListloadtourp(maper);
//    }
//
//    public List<ListloadtourpParam> selectListloadtourpPrint(String idoNo, String dateFrom, String dateTo) {
//        Map<String, Object> maper = new HashMap<String, Object>();
//        maper.put("idoNo", idoNo);
//        maper.put("dateFrom", dateFrom);
//        maper.put("dateTo", dateTo);
//        return new Implement().selectListloadtourpPrint(maper);
//    }
//
//    List<Integer> getCountloadtourp(String idoNo, String dateFrom, String dateTo) {
//        Map<String, Object> maper = new HashMap<String, Object>();
//        maper.put("idoNo", idoNo);
//        maper.put("dateFrom", dateFrom);
//        maper.put("dateTo", dateTo);
//        return new Implement().getCountloadtourp(maper);
//    }
//
//    public List<ListdistdataParam> selectListdistdata(String woNo, String refNo, String dateFrom, String dateTo,
//            String pageNumber, String pageSize) {
//        Map<String, Object> maper = new HashMap<String, Object>();
//        maper.put("woNo", woNo);
//        maper.put("refNo", refNo);
//        maper.put("dateFrom", dateFrom);
//        maper.put("dateTo", dateTo);
//        maper.put("pageNumber", pageNumber);
//        maper.put("pageSize", pageSize);
//        return new Implement().selectListdistdata(maper);
//    }
//
//    public List<ListdistdataParam> selectListdistdataPrint(String woNo, String refNo, String dateFrom, String dateTo) {
//        Map<String, Object> maper = new HashMap<String, Object>();
//        maper.put("woNo", woNo);
//        maper.put("refNo", refNo);
//        maper.put("dateFrom", dateFrom);
//        maper.put("dateTo", dateTo);
//        return new Implement().selectListdistdataPrint(maper);
//    }
//
//    List<Integer> getCountdistdata(String woNo, String refNo, String dateFrom, String dateTo) {
//        Map<String, Object> maper = new HashMap<String, Object>();
//        maper.put("woNo", woNo);
//        maper.put("refNo", refNo);
//        maper.put("dateFrom", dateFrom);
//        maper.put("dateTo", dateTo);
//        return new Implement().getCountdistdata(maper);
//    }
}
