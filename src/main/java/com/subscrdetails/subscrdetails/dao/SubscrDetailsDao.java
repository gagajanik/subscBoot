package com.subscrdetails.subscrdetails.dao;


import com.subscrdetails.subscrdetails.entity.SubscrDetailsDialogEntity;
import com.subscrdetails.subscrdetails.entity.SubscrDetailsDialogOk1Entity;
import com.subscrdetails.subscrdetails.entity.SubscrDetailsDialogOkEntity;
import com.subscrdetails.subscrdetails.entity.accountment.AccountmentChargeEntity;
import com.subscrdetails.subscrdetails.entity.accountment.AccountmentDataEntity;
import com.subscrdetails.subscrdetails.entity.accountment.AccountmentGridsEntity;
import com.subscrdetails.subscrdetails.entity.incomingCalls.IncomingCallsEntity;
import com.subscrdetails.subscrdetails.entity.outsideCallsTab.OutsideCallsTabEntity;
import com.subscrdetails.subscrdetails.entity.reports.BothListEntity;
import com.subscrdetails.subscrdetails.entity.reports.ListOneEntity;
import com.subscrdetails.subscrdetails.entity.reports.ListTwoEntity;
import com.subscrdetails.subscrdetails.entity.reports.ValebiEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubscrDetailsDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SubscrDetailsDialogEntity> getSubscrDetails(String phone){

        String sql="select abonenti, subscrnum, install_date, end_date  from table (cgcbs.SUBSCRIBER_DETAILS.execute_1_tab_func(?))";
        List<SubscrDetailsDialogEntity> result=null;
        try {
           result=jdbcTemplate.query(sql, new Object[]{phone}, (rs, i)->{
            SubscrDetailsDialogEntity sdde=new SubscrDetailsDialogEntity();
            sdde.setAbonenti(rs.getString("abonenti"));
            sdde.setSubscrNum(rs.getString("subscrnum"));
            sdde.setInstallDate(rs.getString("install_date"));
            sdde.setEndDate(rs.getString("end_date"));
            return sdde;
           });
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public List<SubscrDetailsDialogOkEntity> getSubscrDetailsTab1Ok1(String phone, String startDate, String endDate){

        String sql=" SELECT cdno,dateofcall,timeofcall, meter, amount,location, country_descr " +
                " FROM TABLE (cgcbs.SUBSCRIBER_DETAILS.ok2_1_tab_func(?,?,?))";
        List<SubscrDetailsDialogOkEntity> result=null;
        try {
            result=jdbcTemplate.query(sql,new Object[]{phone, startDate, endDate},(rs,i)->{
               SubscrDetailsDialogOkEntity sdde=new SubscrDetailsDialogOkEntity();
               sdde.setCdno(rs.getString("cdno"));
               sdde.setDateOfCall(rs.getString("dateofcall"));
               sdde.setTimeofcall(rs.getString("timeofcall"));
               sdde.setMeter(rs.getDouble("meter"));
               sdde.setAmount(rs.getDouble("amount"));
               sdde.setLocation(rs.getString("location"));
               sdde.setCountryDescr(rs.getString("country_descr"));
               return sdde;
            });
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public SubscrDetailsDialogOk1Entity getSubscrDetailsTab1Ok2(String phone, String subscr, String startDate, String endDate){

        String sql=" select current_balance, estimate_balance, credit_balance, dow_payment_balance " +
                " from table (cgcbs.SUBSCRIBER_DETAILS.ok1_1_tab_func(?,?,?,?))";
        SubscrDetailsDialogOk1Entity result=null;
        try {
            result=jdbcTemplate.queryForObject(sql,new Object[]{phone, subscr, startDate, endDate},(rs,i)->{
                SubscrDetailsDialogOk1Entity sddo1e=new SubscrDetailsDialogOk1Entity();
                sddo1e.setCurrentBalance(rs.getDouble("current_balance"));
                sddo1e.setEstimateBalance(rs.getDouble("estimate_balance"));
                sddo1e.setCreditBalance(rs.getDouble("credit_balance"));
                sddo1e.setDowPaymentBalance(rs.getDouble("dow_payment_balance"));
                return sddo1e;
            });
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public List<BothListEntity> getTwoList(String phone, String subscr, String startDate, String endDate){
        String sqlOne="select phoneno, description,amount,operation_date  from " +
                " table (cgcbs.SUBSCRIBER_DETAILS.bechdva_1_tab_func(?, ?, ?))";
        String sqlTwo="select cgno, cdno, dateofcall, timeofcall, meter, amount from " +
                " table (cgcbs.SUBSCRIBER_DETAILS.bechdva2_1_tab_func(?,?,?))";
        String sqlAbonenti="select person_info from table (cgcbs.SUBSCRIBER_DETAILS.get_person_info_func(?))";
        String sqlValebi="select balance, balance_time from table (cgcbs.SUBSCRIBER_DETAILS.get_balance_func " +
                "(?,?,?, 'bechdva_1_tab_func'))";


        List<ListOneEntity> one;
        List<ListTwoEntity> two;
        String abonenti;
        List<ValebiEntity> valebi;
        List<BothListEntity> all=new ArrayList<>();
        try {
            one=jdbcTemplate.query(sqlOne,new Object[]{subscr, startDate, endDate},(rs,i)->{
                ListOneEntity loe=new ListOneEntity();
                loe.setPhoneNo(rs.getString("phoneno"));
                loe.setDescription(rs.getString("description"));
                loe.setAmount(rs.getDouble("amount"));
                loe.setOperationDate(rs.getString("operation_date"));
                return loe;
            });
            two=jdbcTemplate.query(sqlTwo, new Object[]{subscr, startDate, endDate}, (rs,i)->{
                ListTwoEntity lte=new ListTwoEntity();
                lte.setCgno(rs.getString("cgno"));
                lte.setCdno(rs.getString("cdno"));
                lte.setDateOfCall(rs.getString("dateofCall"));
                lte.setMeter(rs.getDouble("meter"));
                lte.setAmount(rs.getDouble("amount"));
                return lte;
            });

            valebi=jdbcTemplate.query(sqlValebi, new Object[]{subscr, startDate, endDate}, (rs,i)->{
                ValebiEntity ve=new ValebiEntity();
                ve.setBalance(rs.getDouble("balance"));
                ve.setBalanceTime(rs.getString("balance_time"));
                return ve;
            });

            abonenti=jdbcTemplate.queryForObject(sqlAbonenti,new Object[]{subscr}, String.class);

            BothListEntity result=new BothListEntity();

            result.setListOne(one);
            result.setListTwo(two);
            result.setAbonenti(abonenti);
            result.setValebi(valebi);
            all.add(result);

        }catch (Exception ex){
            System.err.println(ex.getMessage()+"aaaaaaa");
        }
        return all;
    }

    public List<BothListEntity> getTwoListCurrent(String phone, String subscr, String startDate, String endDate){
        String sqlOne="select phoneno, description,amount,operation_date  from " +
                " table (cgcbs.SUBSCRIBER_DETAILS.bechdva_mimd_1_tab_func(?, ?, ?))";
        String sqlTwo="select cgno, cdno, dateofcall, timeofcall, meter, amount from " +
                " table (cgcbs.SUBSCRIBER_DETAILS.bechdva2_mimd_1_tab_func(?,?,?))";
        String sqlAbonenti="select person_info from table (cgcbs.SUBSCRIBER_DETAILS.get_person_info_func(?))";
        String sqlValebi="select balance, balance_time from table (cgcbs.SUBSCRIBER_DETAILS.get_balance_func " +
                "(?,?,?, 'bechdva_mimd_1_tab_func'))";


        List<ListOneEntity> one;
        List<ListTwoEntity> two;
        String abonenti;
        List<ValebiEntity> valebi;
        List<BothListEntity> all=new ArrayList<>();
        try {
            one=jdbcTemplate.query(sqlOne,new Object[]{subscr, startDate, endDate},(rs,i)->{
                ListOneEntity loe=new ListOneEntity();
                loe.setPhoneNo(rs.getString("phoneno"));
                loe.setDescription(rs.getString("description"));
                loe.setAmount(rs.getDouble("amount"));
                loe.setOperationDate(rs.getString("operation_date"));
                return loe;
            });
            two=jdbcTemplate.query(sqlTwo, new Object[]{subscr, startDate, endDate}, (rs,i)-> {
                ListTwoEntity lte=new ListTwoEntity();
                lte.setCgno(rs.getString("cgno"));
                lte.setCdno(rs.getString("cdno"));
                lte.setDateOfCall(rs.getString("dateofCall"));
                lte.setMeter(rs.getDouble("meter"));
                lte.setAmount(rs.getDouble("amount"));
                return lte;
            });

            valebi=jdbcTemplate.query(sqlValebi, new Object[]{subscr, startDate, endDate}, (rs,i)->{
                ValebiEntity ve=new ValebiEntity();
                ve.setBalance(rs.getDouble("balance"));
                ve.setBalanceTime(rs.getString("balance_time"));
                return ve;
            });

            abonenti=jdbcTemplate.queryForObject(sqlAbonenti,new Object[]{subscr}, String.class);

            BothListEntity result=new BothListEntity();

            result.setListOne(one);
            result.setListTwo(two);
            result.setAbonenti(abonenti);
            result.setValebi(valebi);
            all.add(result);

        }catch (Exception ex){
            System.err.println(ex.getMessage()+"aaaaaaa");
        }
        return all;
    }

    public List<BothListEntity> getOneNumberPrint(String phone, String subscr, String startDate, String endDate){
        String sqlOne="select phoneno, description,amount,operation_date  from " +
                " table (cgcbs.SUBSCRIBER_DETAILS.erti_nomris_bechdva_1_tab_func(?,?, ?, ?))";
        String sqlTwo="select cgno, cdno, dateofcall, timeofcall, meter, amount from " +
                " table (cgcbs.SUBSCRIBER_DETAILS.erti_nom_bechdva2_1_tab_func(?,?,?))";
        String sqlAbonenti="select person_info from table (cgcbs.SUBSCRIBER_DETAILS.get_person_info_func(?))";
        String sqlValebi="select balance, balance_time from table (cgcbs.SUBSCRIBER_DETAILS.get_balance_func " +
                "(?,?,?, 'erti_nomris_bechdva_1_tab_func'))";


        List<ListOneEntity> one;
        List<ListTwoEntity> two;
        String abonenti;
        List<ValebiEntity> valebi;
        List<BothListEntity> all=new ArrayList<>();
        try {
            one=jdbcTemplate.query(sqlOne,new Object[]{phone,subscr, startDate, endDate},(rs,i)->{
                ListOneEntity loe=new ListOneEntity();
                loe.setPhoneNo(rs.getString("phoneno"));
                loe.setDescription(rs.getString("description"));
                loe.setAmount(rs.getDouble("amount"));
                loe.setOperationDate(rs.getString("operation_date"));
                return loe;
            });
            two=jdbcTemplate.query(sqlTwo, new Object[]{subscr, startDate, endDate}, (rs,i)->{
                ListTwoEntity lte=new ListTwoEntity();
                lte.setCgno(rs.getString("cgno"));
                lte.setCdno(rs.getString("cdno"));
                lte.setDateOfCall(rs.getString("dateofCall"));
                lte.setMeter(rs.getDouble("meter"));
                lte.setAmount(rs.getDouble("amount"));
                return lte;
            });

            valebi=jdbcTemplate.query(sqlValebi, new Object[]{subscr, startDate, endDate}, (rs,i)->{
                ValebiEntity ve=new ValebiEntity();
                ve.setBalance(rs.getDouble("balance"));
                ve.setBalanceTime(rs.getString("balance_time"));
                return ve;
            });

            abonenti=jdbcTemplate.queryForObject(sqlAbonenti,new Object[]{subscr}, String.class);

            BothListEntity result=new BothListEntity();

            result.setListOne(one);
            result.setListTwo(two);
            result.setAbonenti(abonenti);
            result.setValebi(valebi);
            all.add(result);

        }catch (Exception ex){
            System.err.println(ex.getMessage()+"aaaaaaa");
        }
        return all;
    }

    public List<BothListEntity> getOneNumberPrint1(String phone, String subscr, String startDate, String endDate){
        String sqlOne="select phoneno, description,amount,operation_date  from " +
                " table (cgcbs.SUBSCRIBER_DETAILS.erti_nom_bechd_mimd_1_tab_func(?,?,?,?))";
        String sqlTwo="select cgno, cdno, dateofcall, timeofcall, meter, amount from " +
                " table (cgcbs.SUBSCRIBER_DETAILS.erti_nom_bech2_mimd_1_tab_func(?,?,?,?))";
        String sqlAbonenti="select person_info from table (cgcbs.SUBSCRIBER_DETAILS.get_person_info_func(?))";
        String sqlValebi="select balance, balance_time from table (cgcbs.SUBSCRIBER_DETAILS.get_balance_func " +
                "(?,?,?, 'erti_nom_bechd_mimd_1_tab_func'))";


        List<ListOneEntity> one;
        List<ListTwoEntity> two;
        String abonenti;
        List<ValebiEntity> valebi;
        List<BothListEntity> all=new ArrayList<>();
        try {
            one=jdbcTemplate.query(sqlOne,new Object[]{phone,subscr, startDate, endDate},(rs,i)->{
                ListOneEntity loe=new ListOneEntity();
                loe.setPhoneNo(rs.getString("phoneno"));
                loe.setDescription(rs.getString("description"));
                loe.setAmount(rs.getDouble("amount"));
                loe.setOperationDate(rs.getString("operation_date"));
                return loe;
            });
            two=jdbcTemplate.query(sqlTwo, new Object[]{phone,subscr, startDate, endDate}, (rs,i)->{
                ListTwoEntity lte=new ListTwoEntity();
                lte.setCgno(rs.getString("cgno"));
                lte.setCdno(rs.getString("cdno"));
                lte.setDateOfCall(rs.getString("dateofCall"));
                lte.setMeter(rs.getDouble("meter"));
                lte.setAmount(rs.getDouble("amount"));
                return lte;
            });

            valebi=jdbcTemplate.query(sqlValebi, new Object[]{subscr, startDate, endDate}, (rs,i)->{
                ValebiEntity ve=new ValebiEntity();
                ve.setBalance(rs.getDouble("balance"));
                ve.setBalanceTime(rs.getString("balance_time"));
                return ve;
            });

            abonenti=jdbcTemplate.queryForObject(sqlAbonenti,new Object[]{subscr}, String.class);

            BothListEntity result=new BothListEntity();

            result.setListOne(one);
            result.setListTwo(two);
            result.setAbonenti(abonenti);
            result.setValebi(valebi);
            all.add(result);

        }catch (Exception ex){
            System.err.println(ex.getMessage()+"aaaaaaa");
        }
        return all;
    }

    public List<ListTwoEntity> getAnatherTwoceReport(String phone, String startDate, String endDate){
        String sql="SELECT cdno, dateofcall, timeofcall, meter, amount, country_descr   " +
                " FROM TABLE (cgcbs.SUBSCRIBER_DETAILS.sxvadasxva_1_tab_func(?,?,?))";
        List<ListTwoEntity> result=null;
        try {
            result=jdbcTemplate.query(sql,new Object[]{phone, startDate, endDate},(rs,i)->{
               ListTwoEntity lte=new ListTwoEntity();
                lte.setCdno(rs.getString("cdno"));
                lte.setDateOfCall(rs.getString("dateofCall"));
                lte.setTimeOfCall(rs.getString("timeofcall"));
                lte.setMeter(rs.getDouble("meter"));
                lte.setAmount(rs.getDouble("amount"));
                lte.setCountryDescr(rs.getString("country_descr"));
               return lte;
            });
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public List<OutsideCallsTabEntity> getOutsideTabData(String phone, String startDate, String endDate){
        String sql="SELECT cgno, cdno,dateofcall,timeofcall,meter,amount,location,country_descr, ROWNUM AS r " +
                " FROM TABLE(cgcbs.SUBSCRIBER_DETAILS.execute_2_tab_func(?,?,?))";
        List<OutsideCallsTabEntity> result=null;
        try {
           result= jdbcTemplate.query(sql, new Object[]{phone, startDate, endDate},(rs,i)->{
                OutsideCallsTabEntity oscte=new OutsideCallsTabEntity();
                oscte.setCgno(rs.getString("cgno"));
                oscte.setCdno(rs.getString("cdno"));
                oscte.setDateOfCall(rs.getString("dateofcall"));
                oscte.setTimeOfCall(rs.getString("timeofcall"));
                oscte.setMeter(rs.getDouble("meter"));
                oscte.setAmount(rs.getDouble("amount"));
                oscte.setLocation(rs.getString("location"));
                oscte.setCountryDescr(rs.getString("country_descr"));
                return oscte;
            });
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public List<IncomingCallsEntity> getIncomingCallsData(String phone, String startDate, String endDate){
        String sql="SELECT cgno,dateofcall, timeofcall,durationofcall from table " +
                "  (cgcbs.SUBSCRIBER_DETAILS.execute_3_tab_func(?,?,?)) ";
        List<IncomingCallsEntity> result=null;
        try {
           result=jdbcTemplate.query(sql,new Object[]{phone, startDate, endDate},(rs, i)->{
               IncomingCallsEntity ice=new IncomingCallsEntity();
               ice.setCgno(rs.getString("cgno"));
               ice.setDateOfCall(rs.getString("dateofcall"));
               ice.setTimeOfCall(rs.getString("timeofcall"));
               ice.setDurationOfCall(rs.getString("durationofcall"));
               return ice;
           });
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public List<IncomingCallsEntity> getlocalCallsData(String phone, String startDate, String endDate){
        String sql="SELECT cgno,dateofcall, timeofcall,durationofcall from table " +
                "  (cgcbs.SUBSCRIBER_DETAILS.execute_4_tab_func(?,?,?)) ";
        List<IncomingCallsEntity> result=null;
        try {
            result=jdbcTemplate.query(sql,new Object[]{phone, startDate, endDate},(rs, i)->{
                IncomingCallsEntity ice=new IncomingCallsEntity();
                ice.setCgno(rs.getString("cgno"));
                ice.setDateOfCall(rs.getString("dateofcall"));
                ice.setTimeOfCall(rs.getString("timeofcall"));
                ice.setDurationOfCall(rs.getString("durationofcall"));
                return ice;
            });
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public List<IncomingCallsEntity> getIncomingCallsReport(String phone, String startDate, String endDate){
        String sql="select cgno,dateofcall,timeofcall, durationofcall from table " +
                " (cgcbs.SUBSCRIBER_DETAILS.bechdva_3_tab_func(?,?,?))";
        List<IncomingCallsEntity> result=null;
        try {
            result=jdbcTemplate.query(sql, new Object[]{phone, startDate, endDate},(rs, i)->{
                IncomingCallsEntity ice= new IncomingCallsEntity();
                ice.setCgno(rs.getString("cgno"));
                ice.setDateOfCall(rs.getString("dateOfCall"));
                ice.setTimeOfCall(rs.getString("timeofcall"));
                ice.setDurationOfCall(rs.getString("durationOfCall"));
                return ice;
            });
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public List<AccountmentDataEntity> getAccountmentData(String phone, String subscrNum, String startDate, String endDate){
      String sql="select phoneno, operation_date,amount, description  from table " +
              " (cgcbs.SUBSCRIBER_DETAILS.execute_5_tab_func(?,?,?,?))";
      List<AccountmentDataEntity> result=null;
      try {
          result=jdbcTemplate.query(sql,new Object[]{phone, subscrNum, startDate, endDate},(rs, i)->{
             AccountmentDataEntity ade=new AccountmentDataEntity();
             ade.setPhoneNo(rs.getString("phoneno"));
             ade.setOperationDate(rs.getString("operation_date"));
             ade.setAmount(rs.getDouble("amount"));
             ade.setDescription(rs.getString("description"));
             return ade;
          });
      }catch (Exception ex){
          System.err.println(ex.getMessage());
      }
      return result;
    }

    public int getTotal(String phone, String startDate, String endDate){
        String sql="SELECT COUNT(*) " +
                " FROM TABLE(cgcbs.SUBSCRIBER_DETAILS.execute_2_tab_func(?,?,?)) ";
        int total=0;
        try {
            total=jdbcTemplate.queryForObject(sql, new Object[]{phone, startDate, endDate}, Integer.class);
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return total;
    }

    public AccountmentChargeEntity getAccountmentCharge(String subscriber) {
        AccountmentChargeEntity result = null;
        try {
            result = jdbcTemplate.execute(new CallableStatementCreator() {
                @Override
                public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                    CallableStatement cs = connection.prepareCall("{ call payment.GET_LAST_PAYMENT (?,?,?,?,?) }");
                    cs.setString(1, subscriber);
                    cs.registerOutParameter(2, Types.NUMERIC);
                    cs.registerOutParameter(3, Types.NUMERIC);
                    cs.registerOutParameter(4, Types.NUMERIC);
                    cs.registerOutParameter(5, Types.VARCHAR);
                    return cs;
                }
            }, new CallableStatementCallback<AccountmentChargeEntity>() {
                @Override
                public AccountmentChargeEntity doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                    AccountmentChargeEntity ade=new AccountmentChargeEntity();
                    callableStatement.execute();
                    ade.setAmount(callableStatement.getDouble(2));
                    ade.setCurrentBalance(callableStatement.getDouble(3));
                    ade.setPrevBalance(callableStatement.getDouble(4));
                    ade.setOperationDate(callableStatement.getString(5));
                    return ade;
                }
            });


        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }

        return result;
    }

    public List<AccountmentGridsEntity> getAmountmentGrids(String subscriber){
        String sql="SELECT amount, operation_date FROM TABLE (cgcbs.SUBSCRIBER_DETAILS.daricxvebi_5_tab_func(?))";
        List<AccountmentGridsEntity> result=null;
        try {
            result= jdbcTemplate.query(sql,new Object[]{subscriber},(rs, i)->{
               AccountmentGridsEntity age=new AccountmentGridsEntity();
               age.setAmount(rs.getDouble("amount"));
               age.setOperationDate(rs.getString("operation_date"));
               return age;
            });
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public List<AccountmentGridsEntity> getAmountmentGridsYear(String subscriber){
        String sql="SELECT amount, operation_date FROM TABLE (cgcbs.SUBSCRIBER_DETAILS.daricxvebi_wlis_mix_5_tab_func(?))";
        List<AccountmentGridsEntity> result=null;
        try {
            result= jdbcTemplate.query(sql,new Object[]{subscriber},(rs, i)->{
                AccountmentGridsEntity age=new AccountmentGridsEntity();
                age.setAmount(rs.getDouble("amount"));
                age.setOperationDate(rs.getString("operation_date"));
                return age;
            });
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }


}
