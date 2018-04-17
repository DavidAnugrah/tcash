/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.dao;

import id.my.berkah.tcash1.model.BdlListHdrParam;
import id.my.berkah.tcash1.model.ListDTLMappingBDL;
import id.my.berkah.tcash1.model.ListGenIFParam;
import id.my.berkah.tcash1.model.ListHDRMappingBDL;
import id.my.berkah.tcash1.model.ListHdrAutoGenParam;
import id.my.berkah.tcash1.model.ListInvSelectSNParam;
import id.my.berkah.tcash1.model.ListPairingDTLParam;
import id.my.berkah.tcash1.model.ListPairingHdrParam;
import id.my.berkah.tcash1.model.ListSNUploadErrorParam;
import id.my.berkah.tcash1.model.ListSelectSNTempParam;
import id.my.berkah.tcash1.model.ListShopFloorDtlParam;
import id.my.berkah.tcash1.model.ListShopFloorParam;
import id.my.berkah.tcash1.model.ListWIPHdrParam;
import id.my.berkah.tcash1.model.ListselectParam;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Azec
 */
public interface RequestInterface {

    //function
    public List<ListWIPHdrParam> getListHdr(Map map);

    public List<ListWIPHdrParam> selectListHdr(Map map);

    public List<ListWIPHdrParam> getListFindHdr(Map map);

    public List<Integer> getCountwipListFindparam(Map map);

    public List<ListselectParam> getListSelectSnTemp(Map map);

    public List<ListselectParam> getCountListSelectSnTemp(Map map);

    public Map doInvUnselectSN(Map map);

    public List<ListShopFloorParam> getListShopFloor(Map map);

    public List<ListPairingHdrParam> getPairingListHdr(Map map);
    
     public List<ListPairingDTLParam>getPairingListDtl(Map map);
     
     public List<ListHDRMappingBDL> getListHeaderbdl(Map map) ;
     
       public List<ListDTLMappingBDL> getListDetailBdl(Map map);

    //procedure
    public Map doInsertWIPHDR(Map map);

    public Map doValidateInsertHdr(Map map);

    public Map doUpdateStatusWIP(Map map);

    public Map doValidateUpdateHdr(Map map);

    public Map doUpdateHdr(Map map);

    public Map doValidateStatus(Map map);

    public Map doValidateSelectSn(Map map);

    public Map doInvSelectSN(Map map);

    public List<ListselectParam> getListInvSelectSN(Map map);

    public List<Integer> getCountListInvSelectSN(Map map);

    public Map doPcDeleteDtl(Map map);

    public Map dopcValidasiHdr(Map map);

    public Map doPcValidasiDtl(Map map);

    public Map doInsertSelectSnTemp(Map map);

    public Map dorunningAuto(Map map);

    public Map doCreateShopFloor(Map map);

    public Map doUpdateShopFloor(Map map);

    public Map doApproveShopFloor(Map map);

    public Map doSubmitShopFloor(Map map);

    public List<ListShopFloorDtlParam> getListShopFloorDtl(Map map);

    public Map doValidateQuantityComplete(Map map);

    public List<ListShopFloorParam> selectListHdrSFC(Map map);

    public List<ListSelectSNTempParam> getListSelectSNTempSFC(Map map);

    public List<ListInvSelectSNParam> getListInvSelectSNSFC(Map map);

    public Map doValidateSelectSnSFC(Map map);

    public Map doInvSelectSNSFC(Map map);

    public Map doInsertSelectSnTempSFC(Map map);

    public Map doInvUnselectSNSFC(Map map);

    public Map doAutoInvSelectSNSFC(Map map);

    public Map doPairingInsertHdr(Map map);

    public Map doPairingValidateInsertHdr(Map map);

    public Map doPairingCancelHdr(Map map);

    public Map doPairingSubmitHdr(Map map);

    public Map doPairingUpdateHdr(Map map);

    public Map doPairingValidateUpdateHdr(Map map);
    
    public Map doInsertMappingbdl(Map map);
    
    public Map doModifyMappingbdl(Map map);
    
    public Map doExpireMappingbdl(Map map);
     
    public List<ListHdrAutoGenParam> getListHdrAutoGen(Map map);
    
    public List<RequestInterface> listPi(Map map);
    
    public Map insertHdr(Map map);
       
    public RequestInterface listHdr(Map map);
       
    public Map validateUpdateHdr(Map map);
           
    public Map updateHdr(Map map);
           
    public Map validateInsertDtl(Map map);
            
    public Map insertDtl(Map map);
             
    public Map validateUpdateDtl(Map map);
              
    public Map updateDtl(Map map);
             
    public Map doWoUpdateStatus (Map map);
    
    public Map doDeleteListDtl(Map map);
    
    public Map doPairingApproveHdr(Map map);
    
     public List<ListGenIFParam> selectGenFile(Map map);
     
     public Map doPairingInsertDtl(Map map);
     
      public Map doWoCreateRangeSNInv(Map map);
      
      public Map doIfInsertBndHdr(Map map);
      
      public List<BdlListHdrParam> getBdlListHdr(Map map);
      
      public Map doOfInsertDetailbdl(Map map);
      
      public Map doWoUpdatePairingSn(Map map);
      
      public List<ListSNUploadErrorParam> getListSNUploadError(Map map);
      
      public Map doGetPathFile(Map map);
      
}
