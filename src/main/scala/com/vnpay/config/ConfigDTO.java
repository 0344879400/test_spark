package com.vnpay.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ConfigDTO {
    @Expose
    @SerializedName("ceph.uri")
    public String cephUri;
    @Expose
    @SerializedName("ceph.etl.access")
    public String cephEtlAccess;
    @Expose
    @SerializedName("ceph.etl.secret")
    public String cephEtlSecret;
    @Expose
    @SerializedName("ceph.cdp.access")
    public String cephCdpAccess;
    @Expose
    @SerializedName("ceph.cdp.secret")
    public String cephCdpSecret;
    @Expose
    @SerializedName("mongodb.uri")
    public String mongodbUri;
    @Expose
    @SerializedName("mongodb.authSource")
    public String mongodbAuthSource;
    @Expose
    @SerializedName("mongodb.database")
    public String mongodbDatabase;
    @Expose
    @SerializedName("mssql.datamart.ipaddress")
    public String mssqlDatamartIpaddress;
    @Expose
    @SerializedName("mssql.datamart.user")
    public String mssqlDatamartUser;
    @Expose
    @SerializedName("mssql.datamart.password")
    public String mssqlDatamartPassword;
    @Expose
    @SerializedName("oracle.ipaddress")
    public String oracleIpaddress;
    @Expose
    @SerializedName("oracle.aicndl.user")
    public String oracleAicndlUser;
    @Expose
    @SerializedName("oracle.aicndl.password")
    public String oracleAicndlPassword;
    @Expose
    @SerializedName("oracle.datacdp.user")
    public String oracleDatacdpUser;
    @Expose
    @SerializedName("oracle.datacdp.password")
    public String oracleDatacdpPassword;
    @Expose
    @SerializedName("oracle.metadata.user")
    public String oracleMetadataUser;
    @Expose
    @SerializedName("oracle.metadata.password")
    public String oracleMEtadataPassword;

    @Expose
    @SerializedName("oracle.aicndl.servicename")
    public String oracleAicndlService;

    @Expose
    @SerializedName("oracle.datamart.user")
    public String oracleDatamartUser;

    @Expose
    @SerializedName("oracle.datamart.password")
    public String oracleDatamartPassword;

    @Expose
    @SerializedName("oracle.thanhbt1.password")
    public String oracleThanhbt1Password;

    @Expose
    @SerializedName("oracle.thanhbt1.user")
    public String oracleThanhbt1User;

    @Expose
    @SerializedName("oracle.ces.thanhbt1.user")
    public String oracleCesThanhbt1User;

    @Expose
    @SerializedName("oracle.ces.thanhbt1.password")
    public String oracleCesThanhbt1Password;

    @Expose
    @SerializedName("oracle.report.user")
    public String oracleReportUser;

    @Expose
    @SerializedName("oracle.report.password")
    public String oracleReportPassword;

    @Expose
    @SerializedName("oracle.detail.user")
    public String oracleDetailUser;

    @Expose
    @SerializedName("oracle.detail.password")
    public String oracleDetailPassword;

    @Expose
    @SerializedName("oracle.bidv.user")
    public String oracleBidvUser;

    @Expose
    @SerializedName("oracle.bidv.password")
    public String oracleBidvPassword;

    public String oracleMetadataPassword;

    public String getCephUri() {
        return cephUri;
    }

    public String getOracleBidvUser() {
        return oracleBidvUser;
    }
    public String getOracleBidvPassword() {
        return oracleBidvPassword;
    }

    public String getDetailUser() {
        return oracleDetailUser;
    }

    public String getDetailPassword() { return oracleDetailPassword; }

    public String getCephEtlAccess() {
        return cephEtlAccess;
    }

    public String getCephEtlSecret() {
        return cephEtlSecret;
    }

    public String getCephCdpAccess() {
        return cephCdpAccess;
    }

    public String getCephCdpSecret() {
        return cephCdpSecret;
    }

    public String getMongodbUri() {
        return mongodbUri;
    }

    public String getMongodbAuthSource() {
        return mongodbAuthSource;
    }

    public String getMongodbDatabase() {
        return mongodbDatabase;
    }

    public String getMssqlDatamartIpaddress() {
        return mssqlDatamartIpaddress;
    }
    public String getMssqlDatamartUser() {
        return mssqlDatamartUser;
    }
    public String getMssqlDatamartPassword() {
        return mssqlDatamartPassword;
    }

    public String getOracleIpaddress() {
        return oracleIpaddress;
    }

    public String getOracleAicndlUser() {
        return oracleAicndlUser;
    }

    public String getOracleAicndlPassword() {
        return oracleAicndlPassword;
    }

    public String getOracleThanhbt1User() { return oracleThanhbt1User; }

    public String getOracleCesThanhbt1User() { return oracleCesThanhbt1User; }

    public String getOracleThanhbt1Password() { return oracleThanhbt1Password; }

    public String getOracleCesThanhbt1Password() { return oracleCesThanhbt1Password; }

    public String getOracleAicndlService() { return oracleAicndlService;}

    public String getOracleDatacdpUser() {
        return oracleDatacdpUser;
    }

    public String getOracleDatacdpPassword() {
        return oracleDatacdpPassword;
    }

    public String getOracleMetadataUser() {
        return oracleMetadataUser;
    }

    public String getOracleMetadataPassword() {
        return oracleMetadataPassword;
    }

    public String getOracleReportUser() { return oracleReportUser; }

    public String getOracleReportPassword() { return oracleReportPassword; }
}
