/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.implement;

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
import id.my.berkah.util.dao.MyBatisConnectionFactory;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 *
 * @author Azec
 */
public class Implement {

    private SqlSessionFactory sqlSessionFactory;

    public Implement() {
        sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
    }

    public Map doInsertWIPHDR(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doInsertWIPHDR", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doValidateInsertHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doValidateInsertHdr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doUpdateHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doUpdateHdr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doValidateUpdateHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doValidateUpdateHdr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doValidateStatus(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doValidateStatus", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doUpdateStatusWIP(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doUpdateStatusWIP", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doInsertSelectSnTemp(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doInsertSelectSnTemp", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ListselectParam> getListSelectSnTemp(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListselectParam> list = session.selectList("RequestInterface.getListSelectSnTemp", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountListSelectSnTemp(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountListSelectSnTemp", map);
            return list;
        } finally {
            session.close();
        }
    }

    public Map doValidateSelectSn(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doValidateSelectSn", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doInvSelectSN(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doInvSelectSN", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doInvUnselectSN(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doInvUnselectSN", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ListselectParam> getListInvSelectSN(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListselectParam> list = session.selectList("RequestInterface.getListInvSelectSN", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountListInvSelectSN(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountListInvSelectSN", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountwipListFindparam(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountwipListFindparam", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<ListWIPHdrParam> getListFindHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListWIPHdrParam> list = session.selectList("RequestInterface.getListFindHdr", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<ListWIPHdrParam> getListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListWIPHdrParam> list = session.selectList("RequestInterface.getListHdr", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<ListWIPHdrParam> selectListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListWIPHdrParam> list = session.selectList("RequestInterface.selectListHdr", map);
            return list;
        } finally {
            session.close();
        }

    }
    
    public Map dorunningAuto(Map map){
        SqlSession session=sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.dorunningAuto", map);
            return map;
        } finally {
            session.close();
        }
    }


    public List<ListShopFloorParam> getListShopFloor(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListShopFloorParam> list = session.selectList("RequestInterface.getListShopFloor", map);

            return list;
        } finally {
            session.close();
        }
    }

    public List<ListShopFloorParam> selectListHdrSFC(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListShopFloorParam> list = session.selectList("RequestInterface.selectListHdrSFC", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListShopFloorDtlParam> getListShopFloorDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListShopFloorDtlParam> list = session.selectList("RequestInterface.getListShopFloorDtl", map);
            return list;
        } finally {
            session.close();
        }
    }

    public Map doCreateShopFloor(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doCreateShopFloor", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doUpdateShopFloor(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doUpdateShopFloor", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doApproveShopFloor(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doApproveShopFloor", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doSubmitShopFloor(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doSubmitShopFloor", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doCancelShopFloor(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doCancelShopFloor", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doValidateQuantityComplete(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doValidateQuantityComplete", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ListSelectSNTempParam> getListSelectSNTempSFC(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListSelectSNTempParam> list = session.selectList("RequestInterface.getListSelectSNTempSFC", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListInvSelectSNParam> getListInvSelectSNSFC(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListInvSelectSNParam> list = session.selectList("RequestInterface.getListInvSelectSNSFC", map);
            return list;
        } finally {
            session.close();
        }
    }

    public Map doValidateSelectSnSFC(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doValidateSelectSnSFC", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doInvSelectSNSFC(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doInvSelectSNSFC", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doInsertSelectSnTempSFC(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doInsertSelectSnTempSFC", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doInvUnselectSNSFC(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doInvUnselectSNSFC", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doAutoInvSelectSNSFC(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doAutoInvSelectSNSFC", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doPairingValidateInsertHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doPairingValidateInsertHdr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doPairingInsertHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doPairingInsertHdr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ListPairingHdrParam> getPairingListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListPairingHdrParam> list = session.selectList("RequestInterface.getPairingListHdr", map);
            return list;
        } finally {
            session.close();
        }
    }

    public Map doPairingValidateUpdateHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doPairingValidateUpdateHdr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doPairingUpdateHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doPairingUpdateHdr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doPairingSubmitHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doPairingSubmitHdr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doPairingCancelHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doPairingCancelHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doPairingApproveHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doPairingApproveHdr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ListPairingDTLParam> getPairingListDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListPairingDTLParam> list = session.selectList("RequestInterface.getPairingListDtl", map);
            return list;
        } finally {
            session.close();
        }
    }

    public Map doInsertMappingbdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doInsertMappingbdl", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doModifyMappingbdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doModifyMappingbdl", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ListHDRMappingBDL> getListHeaderbdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListHDRMappingBDL> list = session.selectList("RequestInterface.getListHeaderbdl", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListDTLMappingBDL> getListDetailBdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListDTLMappingBDL> list = session.selectList("RequestInterface.getListDetailBdl", map);
            return list;
        } finally {
            session.close();
        }
    }

    public Map doExpireMappingbdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doExpireMappingbdl", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doSetDefaultSN(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doSetDefaultSN", map);
            return map;
        } finally {
            session.close();
        }
    }
    
//  ==================================  ifm ======================================== 

     public Map doWoSaveHeader(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoSaveHeader", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ListWoParam> selectListWo(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoParam> list = session.selectList("RequestInterface.selectListWo", map);
//                                Messagebox.show("sip");
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListWoParam> selectWo(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoParam> list = session.selectList("RequestInterface.selectWo", map);
//                                Messagebox.show("sip");
            return list;
        } finally {
            session.close();
        }
    }
    public List<ListWoParam> getListHDRwo(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoParam> list = session.selectList("RequestInterface.getListHDRwo", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountWo(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountWo", map);
            return list;
        } finally {
            session.close();
        }
    }

    public Map doWoSaveDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoSaveDtl", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoValidateAddLine(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoValidateAddLine", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoSaveHlr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoSaveHlr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoDeleteHlr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoDeleteHlr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoAllocateNBRTemp(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoAllocateNBRTemp", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoAllocateNBR(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoAllocateNBR", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoAllocateINVTemp(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoAllocateINVTemp", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoValidateStatus(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoValidateStatus", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoSelectAllocationNBRDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoSelectAllocationNBRDtl", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoUpdateDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoUpdateDtl", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoDeleteDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoDeleteDtl", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoSelectAllocationINV(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoSelectAllocationINV", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public Map dowoUploadSNCheck(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.dowoUploadSNCheck",map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public Map doGetUploadBatch(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGetUploadBatch",map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoUnSelectAllocationINV(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoUnSelectAllocationINV", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGenerateSN(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGenerateSN", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoUnSelectAllocateNBR(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoUnSelectAllocateNBR", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoReleased(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoReleased", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoSubmit(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoSubmit", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoCancel(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoCancel", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoCreateLineDetail(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoCreateLineDetail", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoCancelCommit(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoCancelCommit", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public Map doWoCancelnew(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoCancelnew", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWoSetArtWork(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoSetArtWork", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ListWoListAddLineHdrParam> selectWoListAddLineHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoListAddLineHdrParam> list = session.selectList("RequestInterface.selectWoListAddLineHdr", map);
//                                Messagebox.show("sip");
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListWoListAddLineDtlParam> selectWoListAddLineDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoListAddLineDtlParam> list = session.selectList("RequestInterface.selectWoListAddLineDtl", map);
//                                Messagebox.show("sip");
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListWoListDtlParam> selectWoListDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoListDtlParam> list = session.selectList("RequestInterface.selectWoListDtl", map);
//                                Messagebox.show("sip");
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListWoListHLRDtlParam> selectWoListHLRDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoListHLRDtlParam> list = session.selectList("RequestInterface.selectWoListHLRDtl", map);
//                            
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListWoListHLRParam> selectWoListHLR(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoListHLRParam> list = session.selectList("RequestInterface.selectWoListHLR", map);
//                                Messagebox.show("sip");
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListWoListAllocateNumberingTempParam> selectWoListAllocateNumberingTemp(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoListAllocateNumberingTempParam> list = session.selectList("RequestInterface.selectWoListAllocateNumberingTemp", map);
//                                Messagebox.show("sip");
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListWoListAllocateNumberingTempParam> selectWoListAllocateINVTemp(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoListAllocateNumberingTempParam> list = session.selectList("RequestInterface.selectWoListAllocateINVTemp", map);
//                                Messagebox.show("sip");
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountAllocateNBR(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountAllocateNBR", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountAllocateNBRDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountAllocateNBRDtl", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountAllocateINVDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountAllocateINVDtl", map);
            return list;
        } finally {
            session.close();
        }
    }
    
    public Map getGetSnInvQty(Map map){
          SqlSession session = sqlSessionFactory.openSession();
        try {
          session.selectOne("RequestInterface.getGetSnInvQty", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountAllocateINV(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountAllocateINV", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListWoListAllocateNBRParam> selectWoListAllocateNBR(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoListAllocateNBRParam> list = session.selectList("RequestInterface.selectWoListAllocateNBR", map);
//                                Messagebox.show("sip");
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListWoListAllocateINVParam> selectWoListAllocateINV(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoListAllocateINVParam> list = session.selectList("RequestInterface.selectWoListAllocateINV", map);
//                                Messagebox.show("sip");
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListWoListSNParam> selectWoListSN(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoListSNParam> list = session.selectList("RequestInterface.selectWoListSN", map);
//                                Messagebox.show("sip");
            return list;
        } finally {
            session.close();
        }
    }

    //Good Delivery
    public List<GdListHdrParam> getGdListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<GdListHdrParam> list = session.selectList("RequestInterface.getGdListHdr", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<Integer> getCountGdListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountGdListHdr", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<GdListHdrParam> selectGdListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<GdListHdrParam> list = session.selectList("RequestInterface.selectGdListHdr", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<ListGdItemParam> selectGdListPoItem(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListGdItemParam> list = session.selectList("RequestInterface.selectGdListPoItem", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<ListGdDtl> selectGdListDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListGdDtl> list = session.selectList("RequestInterface.selectGdListDtl", map);
            return list;
        } finally {
            session.close();
        }

    }

    public Map doGdSaveHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSaveHdr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSaveDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSaveDtl", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSubmitDtlValidateLine(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSubmitDtlValidateLine", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSubmitValidate(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSubmitValidate", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSubmit(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSubmit", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdApprove(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdApprove", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSelectPOValidate(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSelectPOValidate", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdCancelValidate(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdCancelValidate", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdCancelValidateLine(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdCancelValidateLine", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdCancel(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdCancel", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdCancelLine(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdCancelLine", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdDeleteDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdDeleteDtl", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdDtlQtyValidate(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdDtlQtyValidate", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSelectRange(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSelectRange", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSelectRangeOk(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSelectRangeOk", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSumSelectQty(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSumSelectQty", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdEditSN(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdEditSN", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ListGdEditSNParam> selectGdListEditSN(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListGdEditSNParam> list = session.selectList("RequestInterface.selectGdListEditSN", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<Integer> getCountEditSNHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountEditSNHdr", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountEditSNRst(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountEditSNRst", map);
            return list;
        } finally {
            session.close();
        }
    }

    //good reciept
    public List<GrListHdrParam> getGrListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<GrListHdrParam> list = session.selectList("RequestInterface.getGrListHdr", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountGrListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountGrListHdr", map);
            return list;
        } finally {
            session.close();
        }
    }
   

    public List<GrListHdrParam> selectGrListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<GrListHdrParam> list = session.selectList("RequestInterface.selectGrListHdr", map);
            return list;
        } finally {
            session.close();
        }
    }

    public Map doGrSaveHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSaveHdr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrSelectGdValidate(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSelectGdValidate", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrSaveDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSaveDtl", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ListGrSelectGDParam> selectGrListSelectGD(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListGrSelectGDParam> list = session.selectList("RequestInterface.selectGrListSelectGD", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListresultGrListDtlParam> selectGrListDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListresultGrListDtlParam> list = session.selectList("RequestInterface.selectGrListDtl", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListGrListEditSNParam> selectGrListEditSN(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListGrListEditSNParam> list = session.selectList("RequestInterface.selectGrListEditSN", map);
            return list;
        } finally {
            session.close();
        }
    }

    public Map doGrSelectRangeValidate(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSelectRangeValidate", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrUnSelectRangeValidate(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrUnSelectRangeValidate", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrSelectRange(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSelectRange", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrConfirmRcvValidate(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrConfirmRcvValidate", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrConfirmRcvValidateLine(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrConfirmRcvValidateLine", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrConfirmReceive(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrConfirmReceive", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrCancelValidate(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrCancelValidate", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrCancel(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrCancel", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrCancelLine(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrCancelLine", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrApproveValidate(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrApproveValidate", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrApproveValidateLine(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrApproveValidateLine", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrApprove(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrApprove", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrUpdateDtlWhLoc(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrUpdateDtlWhLoc", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrDeleteDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrDeleteDtl", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrSumSelectQty(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSumSelectQty", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrSetLotSize(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSetLotSize", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doMvInsert(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doMvInsert", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map domvUpdate(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.domvUpdate", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map domvExpire(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.domvExpire", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<MVItemToVoucherParam> getListMvHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<MVItemToVoucherParam> list = session.selectList("RequestInterface.getListMvHdr", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountMvHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountMvHdr", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<MVItemToVoucherParam> selectMvListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<MVItemToVoucherParam> list = session.selectList("RequestInterface.selectMvListHdr", map);
            return list;
        } finally {
            session.close();
        }
    }

    public Map doArtInsert(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doArtInsert", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doArtUpdate(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doArtUpdate", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doArtExpire(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doArtExpire", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ArtWorkListParam> getListArtList(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ArtWorkListParam> list = session.selectList("RequestInterface.getListArtList", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountArtList(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountArtList", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ArtWorkListParam> selectArtListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ArtWorkListParam> list = session.selectList("RequestInterface.selectArtListHdr", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListWoRcvParam> selectListWoRcv(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoRcvParam> list = session.selectList("RequestInterface.selectListWoRcv", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListWoRcvParam> selectListWoRcvPrint(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoRcvParam> list = session.selectList("RequestInterface.selectListWoRcvPrint", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountWoRcv(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountWoRcv", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListloadtourpParam> selectListloadtourp(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListloadtourpParam> list = session.selectList("RequestInterface.selectListloadtourp", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListloadtourpParam> selectListloadtourpPrint(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListloadtourpParam> list = session.selectList("RequestInterface.selectListloadtourpPrint", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountloadtourp(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountloadtourp", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListdistdataParam> selectListdistdata(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListdistdataParam> list = session.selectList("RequestInterface.selectListdistdata", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListdistdataParam> selectListdistdataPrint(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListdistdataParam> list = session.selectList("RequestInterface.selectListdistdataPrint", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountdistdata(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountdistdata", map);
            return list;
        } finally {
            session.close();
        }
    }

    public Map dowoUploadPairingSn(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.dowoUploadPairingSn", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map dowoUpdateQtyUploadSn(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.dowoUpdateQtyUploadSn", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doWOValidateGetSN(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWOValidateGetSN", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<String> getSNCount(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<String> list = session.selectList("RequestInterface.getSNCount", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListWoPairing> selectListWOPairing(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListWoPairing> list = session.selectList("RequestInterface.selectListWOPairing", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListIFBndParam> selectListIFBnd(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListIFBndParam> list = session.selectList("RequestInterface.selectListIFBnd", map);
            return list;
        } finally {
            session.close();
        }
    }
        
    public List<ListIFParam> selectListIF(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListIFParam> list = session.selectList("RequestInterface.selectListIF", map);
            return list;
        } finally {
            session.close();
        }
    }
    
    public List<ListIFBndParam> selectListIFTrcBnd(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListIFBndParam> list = session.selectList("RequestInterface.selectListIFTrcBnd", map);
            return list;
        } finally {
            session.close();
        }
    }
    public List<ListIFParamTrc> selectListIFTrc(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListIFParamTrc> list = session.selectList("RequestInterface.selectListIFTrc", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountIFBnd(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountIFBnd", map);
            return list;
        } finally {
            session.close();
        }
    }
    public List<Integer> getCountIF(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountIF", map);
            return list;
        } finally {
            session.close();
        }
    }
    
    public Map doIfValidateInsertHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doIfValidateInsertHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doIfValidateUpdateHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doIfValidateUpdateHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public List<IfListDtlParam>getIfListDtl(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
             List<IfListDtlParam>list = session.selectList("RequestInterface.getIfListDtl", map);
             return list;
        } finally{
            session.close();
        }
    }
    public Map doIfInsertHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doIfInsertHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doIfInsertBndHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doIfInsertBndHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doIfUpdateHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doIfUpdateHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doIfApproveHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doIfApproveHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doIfSubmitHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doIfSubmitHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doIfCancelHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doIfCancelHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public Map doIfUpdateStatus(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doIfUpdateStatus", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public List<ListGenIFParam> selectGenFile(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListGenIFParam> list = session.selectList("RequestInterface.selectGenFile", map);
            return list;
        } finally {
            session.close();
        }
    }
    
    public List<ListOFParam> getselectListOF(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListOFParam> list = session.selectList("RequestInterface.getselectListOF", map);
            return list;
        } finally {
            session.close();
        }
    }
    public List<ListOFParam> selectListOF(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListOFParam> list = session.selectList("RequestInterface.selectListOF", map);
            return list;
        } finally {
            session.close();
        }
    }
    public List<ListOFParam> selectListOFTrc(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListOFParam> list = session.selectList("RequestInterface.selectListOFTrc", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountOF(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountOF", map);
            return list;
        } finally {
            session.close();
        }
    }
    
    public Map doOfValidateInsertHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfValidateInsertHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public Map getOfClearDetail(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.getOfClearDetail", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfValidateUpdateHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfValidateUpdateHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfInsertHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfInsertHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfUpdateHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfUpdateHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfApproveHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfApproveHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfSubmitHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfSubmitHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfCancelHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfCancelHdr", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public Map doOfInsertDetail(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfInsertDetail", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfInsertLogs(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfInsertLogs", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public List<ListOFDtlParam> selectListOFDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListOFDtlParam> list = session.selectList("RequestInterface.selectListOFDtl", map);
            return list;
        } finally {
            session.close();
        }
    }
    public List<ListOFDtlParam> getselectListOFDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListOFDtlParam> list = session.selectList("RequestInterface.getselectListOFDtl", map);
            return list;
        } finally {
            session.close();
        }
    }
    public List<OfSumParam> getOfListSumDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<OfSumParam> list = session.selectList("RequestInterface.getOfListSumDtl", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountOFDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountOFDtl", map);
            return list;
        } finally {
            session.close();
        }
    }
    
    public List<ListPairingParam> selectListPairing(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListPairingParam> list = session.selectList("RequestInterface.selectListPairing", map);
            return list;
        } finally {
            session.close();
        }
    }
    public List<ListPairingParam> selectListPairingTrc(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListPairingParam> list = session.selectList("RequestInterface.selectListPairingTrc", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountPairing(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountPairing", map);
            return list;
        } finally {
            session.close();
        }
    }
    
    public List<ListPairingDTLParam> selectListPairingDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            List<ListPairingDTLParam> list = session.selectList("RequestInterface.selectListPairingDtl", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountPairingDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountPairingDtl", map);
            return list;
        } finally {
            session.close();
        }
    }
    
     public List<RpListHdrParam> getRpListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<RpListHdrParam> list = session.selectList("RequestInterface.getRpListHdr", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<RpListHdrParam> getRpListFind(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<RpListHdrParam> list = session.selectList("RequestInterface.getRpListFind", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<RpListHdrParam> selectRpListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<RpListHdrParam> list = session.selectList("RequestInterface.selectRpListHdr", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<Integer> getCountRpListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountRpListHdr", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountRpListFind(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountRpListFind", map);
            return list;
        } finally {
            session.close();
        }

    }

    public Map doRpSaveHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doRpSaveHdr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<LovBUParam> getLovBU(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<LovBUParam> list = session.selectList("RequestInterface.getLovBU", map);
            return list;
        } finally {
            session.close();
        }

    }

    public Map doRpSubmit(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doRpSubmit", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doRpApproved(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doRpApproved", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doRpCancel(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doRpCancel", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<RpListDtlParam> getRpListDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<RpListDtlParam> list = session.selectList("RequestInterface.getRpListDtl", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<Integer> getCountRpListDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountRpListDtl", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<LovItemParam> getLovItem(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<LovItemParam> list = session.selectList("RequestInterface.getLovItem", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<LovRegionalParam> getLovRegional(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<LovRegionalParam> list = session.selectList("RequestInterface.getLovRegional", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<LovWhParam> getLovWh(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<LovWhParam> list = session.selectList("RequestInterface.getLovWh", map);
            return list;
        } finally {
            session.close();
        }

    }

    public Map doRpSaveDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doRpSaveDtl", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doRpGetUploadAttr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doRpGetUploadAttr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<PcListHdrParam> getPcListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<PcListHdrParam> list = session.selectList("RequestInterface.getPcListHdr", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<PcListHdrParam> selectPcListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<PcListHdrParam> list = session.selectList("RequestInterface.selectPcListHdr", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<Integer> getCountPcListHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountPcListHdr", map);
            return list;
        } finally {
            session.close();
        }

    }

    public Map doPcSaveHdr(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doPcSaveHdr", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<PcListDtlParam> getPcListDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<PcListDtlParam> list = session.selectList("RequestInterface.getPcListDtl", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<Integer> getCountPcListDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountPcListDtl", map);
            return list;
        } finally {
            session.close();
        }

    }

    public Map doPcUpdateStatus(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doPcUpdateStatus", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doPcSaveDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doPcSaveDtl", map);
            return map;
        } finally {
            session.close();
        }
    }
    
     public Map doRpDelete(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doRpDelete", map);
            return map;
        } finally {
            session.close();
        }
    }

     public Map doPcDeleteDtl(Map map){
             SqlSession session = sqlSessionFactory.openSession();
             try {
                 session.selectOne("RequestInterface.doPcDeleteDtl", map);
                 return map;
             } finally {
                 session.close();
             }
         }
         
         public List<LovsupParam> getLovSupplierPC(Map map){
             SqlSession session = sqlSessionFactory.openSession();
             try {
                 List<LovsupParam> list= session.selectList("RequestInterface.getLovSupplierPC", map);
            return list;
             } finally {
                 session.close();
             }
         }
         public List<LovSiteParam> getLovSite(Map map){
             SqlSession session = sqlSessionFactory.openSession();
             try {
                 List<LovSiteParam> list= session.selectList("RequestInterface.getLovSite", map);
            return list;
             } finally {
                 session.close();
             }
         }
         
         public List<LovForwardAgentParam> getLovForwardAgent(Map map){
             SqlSession session = sqlSessionFactory.openSession();
             try {
                 List<LovForwardAgentParam> list= session.selectList("RequestInterface.getLovForwardAgent", map);
            return list;
             } finally {
                 session.close();
             }
         }
         
            public Map dopcValidasiHdr(Map map){
             SqlSession sesion = sqlSessionFactory.openSession();
             try {
                 sesion.selectOne("RequestInterface.dopcValidasiHdr",map);
                 return map;
             } finally {
                 sesion.close();
             }
         }
            public Map doPcValidasiDtl(Map map){
             SqlSession sesion = sqlSessionFactory.openSession();
             try {
                 sesion.selectOne("RequestInterface.doPcValidasiDtl",map);
                 return map;
             } finally {
                 sesion.close();
             }
         }
            
            public List<ListHdrAutoGenParam> getListHdrAutoGen(Map map){
                 SqlSession sesion = sqlSessionFactory.openSession();
                 try {
                List<ListHdrAutoGenParam>list= sesion.selectList("RequestInterface.getListHdrAutoGen",map);
                 return list;
             } finally {
                 sesion.close();
             }
            }
            
      public List<ProductionIssued> listPi(Map map)
        {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            List<ProductionIssued> list = session.selectList("RequestInterface.ListFindHdr", map);
          return list;
        } finally
        {
            session.close();
        }
        
    }
      public List<ListWoParam> selectallWo(Map map)
        {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            List<ListWoParam> list = session.selectList("RequestInterface.selectallWo", map);
          return list;
        } finally
        {
            session.close();
        }
        
    }

    public Map validateInsertHdr(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.ValidateInsertHdr", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public Map insertHdr(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.InsertHdr", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public ProductionIssued listHdr(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            ProductionIssued list = session.selectOne("RequestInterface.ListHdr", map);
            return list;
        } finally
        {
            session.close();
        }
    }

    public Map validateUpdateHdr(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.ValidateUpdateHdr", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public Map updateHdr(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.UpdateHdr", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public Map validateInsertDtl(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.ValidateInsertDtl", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public Map insertDtl(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.InsertDtl", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public Map validateUpdateDtl(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.ValidateUpdateDtl", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public Map updateDtl(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.UpdateDtl", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public List<DtlItmProductionIssue> listDtl(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            List<DtlItmProductionIssue> list = session.selectList("RequestInterface.ListDtl", map);
            return list;
        } finally
        {
            session.close();
        }
    }

    public Map validateStatus(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.ValidateStatus", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public Map updateStatusPi(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.UpdateStatusPi", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public Map insertSelectSnTemp(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.InsertSelectSnTemp", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public List<SelectRange> listHeaderRange(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            List<SelectRange> list = session.selectList("RequestInterface.ListSelectSNTemp", map);
            return list;
        } finally
        {
            session.close();
        }
    }

    public List<SelectRange> listDetailRange(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            List<SelectRange> list = session.selectList("RequestInterface.ListInvSelectSN", map);
            return list;
        } finally
        {
            session.close();
        }
    }

    public Map validateSelectSn(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.ValidateSelectSn", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public Map invSelectSN(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.update("RequestInterface.InvSelectSN", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public Map invUnselectSN(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.InvUnselectSN", map);
            return map;
        } finally
        {
            session.close();
        }
    }

    public Map autoInvSelectSN(Map map)
    {
        SqlSession session = sqlSessionFactory.openSession();
        try
        {
            session.selectOne("RequestInterface.AutoInvSelectSN", map);
            return map;
        } finally
        {
            session.close();
        }
    }
    
    public Map doWoUpdateStatus (Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoUpdateStatus", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doWoUpdateStatusUpload (Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoUpdateStatusUpload", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public Map doDeleteListDtl(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doDeleteListDtl", map);
            return  map;
        } finally {
            session.close();
        }
    }
    
    public Map doPairingInsertDtl(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doPairingInsertDtl", map);
            return map;
        } finally  {
            session.close();
        }
    }
    
    
    public Map doWoCreateRangeSNInv(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoCreateRangeSNInv", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public List<BdlListHdrParam> getBdlListHdr(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<BdlListHdrParam> list = session.selectList("RequestInterface.getBdlListHdr", map);
            return list;
        } finally {
            session.close();
        }
    }
    
     public Map doOfValidateInsertHdrbdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfValidateInsertHdrbdl", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfValidateUpdateHdrbdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfValidateUpdateHdrbdl", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfInsertHdrbdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfInsertHdrbdl", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfUpdateHdrbdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfUpdateHdrbdl", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfApproveHdrbdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfApproveHdrbdl", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfSubmitHdrbdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfSubmitHdrbdl", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfCancelHdrbdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfCancelHdrbdl", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public Map doOfInsertDetailbdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfInsertDetailbdl", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public Map doOfValidateFileName(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfValidateFileName", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public List<BdlListDtlParam>BdlListDtl(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<BdlListDtlParam>list = session.selectList("RequestInterface.BdlListDtl", map);
          return list;
        } finally {
            session.close();
        }
    }
    
   
    public Map doWoUpdatePairingSn(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doWoUpdatePairingSn",map);
            return map;
        } finally  {
            session.close();
        }
    }
    
    public List<wolistHDRPAram> getWoListPairingHDR(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
          List<wolistHDRPAram>list =  session.selectList("RequestInterface.getWoListPairingHDR",map);
            return list;
        } finally  {
            session.close();
        }
    }
    public List<ListSNUploadErrorParam> getListSNUploadError(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
          List<ListSNUploadErrorParam>list =  session.selectList("RequestInterface.getListSNUploadError",map);
            return list;
        } finally  {
            session.close();
        }
    }
    
    public Map doGetPathFile(Map map){
        SqlSession  session=sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGetPathFile",map);
            return map;
        } finally {
            session.close();
        }
    }
   
    
    public List<ListAddDtlPairingParam>getListAddDtlPairing(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListAddDtlPairingParam>list= session.selectList("RequestInterface.getListAddDtlPairing",map);
        return list;
        } finally {
            session.close();
        }
    }
    
    
    
    public List<ListHdrBundlingParam>getListHdrBundling(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListHdrBundlingParam>list= session.selectList("RequestInterface.getListHdrBundling",map);
            return list;
        } finally {
            session.close();
        }
    }
    public List<ListDtlbundlingParam>getListDtlBundling(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListDtlbundlingParam>list= session.selectList("RequestInterface.getListDtlBundling",map);
        return list;
        } finally {
            session.close();
        }
    }
    
    
    public Map PairingSnTcashLoop(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
        session.selectOne("RequestInterface.PairingSnTcashLoop",map);
        return map ;
        } finally {
            session.close();
        }
    }
    
    
    public Map doOnInsertHdr(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOnInsertHdr",map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public Map doOnUpdateHdr(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOnUpdateHdr",map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public Map doInsertQtyByRegional(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doInsertQtyByRegional",map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public List<listDtlRegionalParam>getListDtlRegional(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List <listDtlRegionalParam>list = session.selectList("RequestInterface.getListDtlRegional",map);
           return list;
        } finally {
                session.close();
        }
    }
    public Map doOnInsertDtl(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOnInsertDtl",map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public List <ListSelectSNPAram>getListSelectSN(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List <ListSelectSNPAram>list = session.selectList("RequestInterface.getListSelectSN",map);
           return list;
        } finally {
                session.close();
        }
    }
    
    public Map dovalidateEditSN(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.dovalidateEditSN",map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doSetSelectSN(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doSetSelectSN",map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public Map doUpdateStatusHdr(Map map){
        SqlSession session= sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doUpdateStatusHdr",map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doCanceled(Map map){
        SqlSession session= sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doCanceled",map);
            return map;
        } finally {
            session.close();
        }
    }
    
     public List<BundlingGDListHDRParam> getGdListHdrBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<BundlingGDListHDRParam> list = session.selectList("RequestInterface.getGdListHdrBundling", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<Integer> getCountGdListHdrBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountGdListHdrBundling", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<BundlingGDListHDRParam> selectGdListHdrBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<BundlingGDListHDRParam> list = session.selectList("RequestInterface.selectGdListHdrBundling", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<GdListPoItemBundlingParam> selectGdListPoItemBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<GdListPoItemBundlingParam> list = session.selectList("RequestInterface.selectGdListPoItemBundling", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<ListGdDtlBundlingParam> selectGdListDtlBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListGdDtlBundlingParam> list = session.selectList("RequestInterface.selectGdListDtlBundling", map);
            return list;
        } finally {
            session.close();
        }

    }

    public Map doGdSaveHdrBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSaveHdrBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSaveDtlBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSaveDtlBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSubmitDtlValidateLineBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSubmitDtlValidateLineBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSubmitValidateBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSubmitValidateBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSubmitBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSubmitBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdApproveBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdApproveBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSelectPOValidateBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSelectPOValidateBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdCancelValidateBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdCancelValidateBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdCancelValidateLineBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdCancelValidateLineBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdCancelBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdCancelBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdCancelLineBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdCancelLineBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdDeleteDtlBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdDeleteDtlBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdDtlQtyValidateBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdDtlQtyValidateBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSelectRangeBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSelectRangeBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSelectRangeOkBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSelectRangeOkBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdSumSelectQtyBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdSumSelectQtyBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGdEditSNBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGdEditSNBundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ListBundlingGdEditSNParam> selectGdListEditSNBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListBundlingGdEditSNParam> list = session.selectList("RequestInterface.selectGdListEditSNBundling", map);
            return list;
        } finally {
            session.close();
        }

    }

    public List<Integer> getCountEditSNHdrBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountEditSNHdrBundling", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<Integer> getCountEditSNRstBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountEditSNRstBundling", map);
            return list;
        } finally {
            session.close();
        }
    }
    
    
    public List<ListBundlingGRHDR>getGrListHdrbundling(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
             List<ListBundlingGRHDR>list = session.selectList("RequestInterface.getGrListHdrbundling", map);
             return list;
        }finally {
            session.close();
        }
    }
    public List<ListBundlingGRHDR>selectGrListHdrbundling(Map map){
        SqlSession session = sqlSessionFactory.openSession();
        try {
             List<ListBundlingGRHDR>list = session.selectList("RequestInterface.selectGrListHdrbundling", map);
             return list;
        }finally {
            session.close();
        }
    }
    
     public List<Integer> getCountGrListHdrbundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> list = session.selectList("RequestInterface.getCountGrListHdrbundling", map);
            return list;
        } finally {
            session.close();
        }
    }
     
     
      public Map doGrSaveHdrbundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSaveHdrbundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrSelectGdValidatebundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSelectGdValidatebundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrSaveDtlbundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSaveDtlbundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ListGrSelectGDParam> selectGrListSelectGDbundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListGrSelectGDParam> list = session.selectList("RequestInterface.selectGrListSelectGDbundling", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListresultGrListDtlParam> selectGrListDtlbundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListresultGrListDtlParam> list = session.selectList("RequestInterface.selectGrListDtlbundling", map);
            return list;
        } finally {
            session.close();
        }
    }

    public List<ListGrListEditSNParam> selectGrListEditSNbundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListGrListEditSNParam> list = session.selectList("RequestInterface.selectGrListEditSNbundling", map);
            return list;
        } finally {
            session.close();
        }
    }

    public Map doGrSelectRangeValidatebundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSelectRangeValidatebundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrUnSelectRangeValidatebundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrUnSelectRangeValidatebundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrSelectRangebundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSelectRangebundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrConfirmRcvValidatebundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrConfirmRcvValidatebundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrConfirmRcvValidateLinebundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrConfirmRcvValidateLinebundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrConfirmReceivebundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrConfirmReceivebundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrCancelValidatebundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrCancelValidatebundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrCancelbundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrCancelbundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrCancelLinebundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrCancelLinebundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrApproveValidatebundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrApproveValidatebundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrApproveValidateLinebundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrApproveValidateLinebundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrApprovebundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrApprovebundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrUpdateDtlWhLocbundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrUpdateDtlWhLocbundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrDeleteDtlbundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrDeleteDtlbundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrSumSelectQtybundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSumSelectQtybundling", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGrSetLotSizebundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGrSetLotSizebundling", map);
            return map;
        } finally {
            session.close();
        }
    }
    
    public Map doGetItemRegional(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doGetItemRegional", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOnUpdateDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOnUpdateDtl", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doDeleteQtyByRegional(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doDeleteQtyByRegional", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doUpdateQtyByRegional(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doUpdateQtyByRegional", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOnInsertSubDtl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOnInsertSubDtl", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doExpireMappingbdlAll(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doExpireMappingbdlAll", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doDeleteMappingbdl(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doDeleteMappingbdl", map);
            return map;
        } finally {
            session.close();
        }
    }
//
    public Map getNonRegAvaiQty(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.getNonRegAvaiQty", map);
            return map;
        } finally {
            session.close();
        }
    }
//
    public List<ListSnMapBundlingParam> getListSnMapBundling(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListSnMapBundlingParam> list = session.selectList("RequestInterface.getListSnMapBundling", map);
            return list;
        } finally {
            session.close();
        }
    }
//
   

//    public List<MVItemToVoucherParam> selectMvListHdrbundling(Map map) {
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            List<MVItemToVoucherParam> list = session.selectList("RequestInterface.selectMvListHdrbundling", map);
//            return list;
//        } finally {
//            session.close();
//        }
//    }
//
    public Map doClearUpload(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doClearUpload", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doSetLarType(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doSetLarType", map);
            return map;
        } finally {
            session.close();
        }
    }
//
    public Map doEEBdlTcash(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doEEBdlTcash", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doOfValidateDetail(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.selectOne("RequestInterface.doOfValidateDetail", map);
            return map;
        } finally {
            session.close();
        }
    }

    public List<ListExportExcelParam> getListExportExcel(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListExportExcelParam> list = session.selectList("RequestInterface.getListExportExcel", map);
            return list;
        } finally {
            session.close();
        }
    }
//
    public Map doWipInsert(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
           session.selectOne("RequestInterface.doWipInsert", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doWipDelete(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
           session.selectOne("RequestInterface.doWipDelete", map);
            return map;
        } finally {
            session.close();
        }
    }
    public Map doWipExpire(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
           session.selectOne("RequestInterface.doWipExpire", map);
            return map;
        } finally {
            session.close();
        }
    }
//
    public List<ListWipParam> getListWip(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<ListWipParam> list = session.selectList("RequestInterface.getListWip", map);
            return list;
        } finally {
            session.close();
        }
    }

    public Map doGetTotalSN(Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
           session.selectList("RequestInterface.doGetTotalSN", map);
            return map;
        } finally {
            session.close();
        }
    }

    public Map doGetOrderQty (Map map) {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            session.selectList("RequestInterface.doGetOrderQty", map);
            return map;
        } finally {
            session.close();
        }
    }
//
//    public List<Integer> getCountWoRcvbundling(Map map) {
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            List<Integer> list = session.selectList("RequestInterface.getCountWoRcvbundling", map);
//            return list;
//        } finally {
//            session.close();
//        }
//    }
//
//    public List<ListloadtourpParam> selectListloadtourpbundling(Map map) {
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//
//            List<ListloadtourpParam> list = session.selectList("RequestInterface.selectListloadtourpbundling", map);
//            return list;
//        } finally {
//            session.close();
//        }
//    }
//
//    public List<ListloadtourpParam> selectListloadtourpPrintbundling(Map map) {
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//
//            List<ListloadtourpParam> list = session.selectList("RequestInterface.selectListloadtourpPrintbundling", map);
//            return list;
//        } finally {
//            session.close();
//        }
//    }
//
//    public List<Integer> getCountloadtourpbundling(Map map) {
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            List<Integer> list = session.selectList("RequestInterface.getCountloadtourpbundling", map);
//            return list;
//        } finally {
//            session.close();
//        }
//    }
//
//    public List<ListdistdataParam> selectListdistdatabundling(Map map) {
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//
//            List<ListdistdataParam> list = session.selectList("RequestInterface.selectListdistdatabundling", map);
//            return list;
//        } finally {
//            session.close();
//        }
//    }
//
//    public List<ListdistdataParam> selectListdistdataPrintbundling(Map map) {
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//
//            List<ListdistdataParam> list = session.selectList("RequestInterface.selectListdistdataPrintbundling", map);
//            return list;
//        } finally {
//            session.close();
//        }
//    }
//
//    public List<Integer> getCountdistdatabundling(Map map) {
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            List<Integer> list = session.selectList("RequestInterface.getCountdistdatabundling", map);
//            return list;
//        } finally {
//            session.close();
//        }
//    }
}
