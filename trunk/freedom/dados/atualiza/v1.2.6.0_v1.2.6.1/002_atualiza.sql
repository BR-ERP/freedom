/* Server Version: LI-V6.3.4.4910 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.6.0.fdb' USER 'SYSDBA' PASSWORD '123654' ROLE 'ADM';

SET AUTODDL ON;

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencial do subproduto'
where Rdb$Procedure_Name='EQMOVPRODISP' and Rdb$Parameter_Name='SEQSUBPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencial do subproduto'
where Rdb$Procedure_Name='EQMOVPRODISP' and Rdb$Parameter_Name='SEQSUBPROD';


COMMIT WORK;
