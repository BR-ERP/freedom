-- CLIENTES

-- Inserindo c�digos unificados
insert into sgunifcod (codemp, codfilial, codunifcod, tipounifcod, descunifcod, codtmp)
select codemp, codfilial,(select (coalesce(max(codunifcod),0)+1) from sgunifcod ), 'C', razcli, codcli
from vdcliente cl where cl.ativocli='S' and cl.codunifcod is null;

-- Gerando v�nculo na tabela de clientes
update vdcliente cl set codempuc=codemp, codfilialuc=codfilial,
codunifcod=(select codunifcod from sgunifcod where codemp=cl.codemp and codfilial=cl.codfilial and codtmp=cl.codcli);

-- limpando c�digos tempor�rios
update sgunifcod set codtmp=null;

-- FORNECEDORES

-- Inserindo c�digos unificados
insert into sgunifcod (codemp, codfilial, codunifcod, tipounifcod, descunifcod, codtmp)
select codemp, codfilial,(select (coalesce(max(codunifcod),0)+1) from sgunifcod ), 'F', razfor, codfor
from cpforneced fo where fo.ativofor='S' and fo.codunifcod is null;

-- Gerando v�nculo na tabela de fornecedores
update cpforneced fo set codempuc=codemp, codfilialuc=codfilial,
codunifcod=(select codunifcod from sgunifcod where codemp=fo.codemp and codfilial=fo.codfilial and codtmp=fo.codfor);

-- limpando c�digos tempor�rios
update sgunifcod set codtmp=null;

-- TRANSPORTADORAS

-- Inserindo c�digos unificados
insert into sgunifcod (codemp, codfilial, codunifcod, tipounifcod, descunifcod, codtmp)
select codemp, codfilial,(select (coalesce(max(codunifcod),0)+1) from sgunifcod ), 'T', tr.raztran, tr.codtran
from vdtransp tr where tr.codunifcod is null;

-- Gerando v�nculo na tabela de transportadoras
update vdtransp tr set codempuc=codemp, codfilialuc=codfilial,
codunifcod=(select codunifcod from sgunifcod where codemp=tr.codemp and codfilial=tr.codfilial and codtmp=tr.codtran);

-- limpando c�digos tempor�rios
update sgunifcod set codtmp=null;

-- FILIAL

-- Inserindo c�digos unificados
insert into sgunifcod (codemp, codfilial, codunifcod, tipounifcod, descunifcod, codtmp)
select codemp, codfilial,(select (coalesce(max(codunifcod),0)+1) from sgunifcod ), 'E', fl.razfilial, fl.codfilial
from sgfilial fl where fl.codunifcod is null;

-- Gerando v�nculo na tabela de transportadoras
update sgfilial fl set codempuc=codemp, codfilialuc=codfilial,
codunifcod=(select codunifcod from sgunifcod where codemp=fl.codemp and codfilial=fl.codfilial and codtmp=fl.codfilial);

-- limpando c�digos tempor�rios
update sgunifcod set codtmp=null;

