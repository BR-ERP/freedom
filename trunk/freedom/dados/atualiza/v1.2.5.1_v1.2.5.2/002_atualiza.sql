/* Server Version: LI-V6.3.4.4910 Firebird 1.5.  ODS Version: 10.1. */
SET NAMES NONE;

SET SQL DIALECT 3;

--CONNECT 'localhost:/opt/firebird/dados/desenv/freedom1.2.5.1.fdb' USER 'SYSDBA' PASSWORD '123654';

SET AUTODDL ON;

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencial do subproduto'
where Rdb$Procedure_Name='EQMOVPRODISP' and Rdb$Parameter_Name='SEQSUBPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencial de subproduto'
where Rdb$Procedure_Name='EQMOVPRODIUDSP' and Rdb$Parameter_Name='SEQSUBPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se deve ser realizada a retens�o do tributo ISS (descontando do valor final do t�tulo)'
where Rdb$Procedure_Name='FNADICRECEBERSP01' and Rdb$Parameter_Name='VLRRETENSAOISS';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Tipo de processamento (D=Detalhado, A=Agrupado, C=Comum)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='TIPOPROCESS';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da empresa da ordem de produ��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial da ordem de produ��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da ordem de produ��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencia da ordem de produ��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='SEQOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da empresa do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPPD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALPD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da emrpesa do or�amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPOC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial do or�amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALOC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo do or�amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Tipo de or�amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='TIPOORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo do �tem de or�amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODITORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Quantidade sugerida para fabrica��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='QTDSUGPRODOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Data de fabrica��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='DTFABROP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequ�ncia da estrutura'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='SEQEST';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da empresa da esta��o de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPET';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial da esta��o de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALET';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da esta��o de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEST';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento � por data de aprova��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPDATAAPROV';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento � por data de fabrica��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPDTFABROP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento � por c�digo de cliente'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPCODCLI';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da empresa do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPCL';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALCL';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODCLI';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Data de aprova��o do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='DATAAPROV';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da empresa do item de compra (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial do item de compra (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da compra (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODCOMPRA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo do item de compra (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODITCOMPRA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Justificativa por divergencias na quantidade final (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='JUSTFICQTDPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da empresa do produto de entrada (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPPDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial do produto de entrada (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALPDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo do produto de entrada (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODPRODENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Quantidade do produto de entrada (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='QTDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencial do subproduto'
where Rdb$Procedure_Name='EQMOVPRODISP' and Rdb$Parameter_Name='SEQSUBPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencial de subproduto'
where Rdb$Procedure_Name='EQMOVPRODIUDSP' and Rdb$Parameter_Name='SEQSUBPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se deve ser realizada a retens�o do tributo ISS (descontando do valor final do t�tulo)'
where Rdb$Procedure_Name='FNADICRECEBERSP01' and Rdb$Parameter_Name='VLRRETENSAOISS';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Tipo de processamento (D=Detalhado, A=Agrupado, C=Comum)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='TIPOPROCESS';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da empresa da ordem de produ��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial da ordem de produ��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da ordem de produ��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequencia da ordem de produ��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='SEQOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da empresa do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPPD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALPD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo do produto'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da emrpesa do or�amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPOC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial do or�amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALOC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo do or�amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Tipo de or�amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='TIPOORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo do �tem de or�amento'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODITORC';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Quantidade sugerida para fabrica��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='QTDSUGPRODOP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Data de fabrica��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='DTFABROP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Sequ�ncia da estrutura'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='SEQEST';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da empresa da esta��o de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPET';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial da esta��o de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALET';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da esta��o de trabalho'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEST';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento � por data de aprova��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPDATAAPROV';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento � por data de fabrica��o'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPDTFABROP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Indica se o agrupamento � por c�digo de cliente'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='AGRUPCODCLI';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da empresa do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPCL';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALCL';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo do cliente do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODCLI';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Data de aprova��o do lote processado'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='DATAAPROV';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da empresa do item de compra (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial do item de compra (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALCP';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da compra (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODCOMPRA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo do item de compra (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODITCOMPRA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Justificativa por divergencias na quantidade final (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='JUSTFICQTDPROD';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da empresa do produto de entrada (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODEMPPDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo da filial do produto de entrada (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODFILIALPDENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'C�digo do produto de entrada (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='CODPRODENTRADA';

Update Rdb$Procedure_Parameters set Rdb$Description =
'Quantidade do produto de entrada (convers�o de produtos)'
where Rdb$Procedure_Name='PPGERAOP' and Rdb$Parameter_Name='QTDENTRADA';


COMMIT WORK;
