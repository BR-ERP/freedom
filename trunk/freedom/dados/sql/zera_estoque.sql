-- Query de limpeza de estoque
-- �ltima revis�o em 05/09/2011 - Robson Sanchez

insert into eqinvprod (
codemp, codfilial,
codinvprod,
codemppd, codfilialpd, codprod,
codemptm, codfilialtm, codtipomov, datainvp,
qtdinvp, precoinvp,
codempax, codfilialax, codalmox, refprod,
sldatualinvp,
slddiginvp,
flag, obsinvp)
select
pd.codemp, pd.codfilial,
( select max(iv.codinvprod)+1 from eqinvprod iv
) as codinv,
pd.codemp, pd.codfilial , pd.codprod,
pd.codemp, pd.codfilial, pf.codtipomov6 as codtipomov, cast('now' as date) as datainvp,
coalesce( sp.sldliqprod * -1,0) as saldoatual,
pd.custompmprod,
ax.codemp, ax.codfilial, ax.codalmox, PD.refprod,
coalesce(sp.sldliqprod,0) as saldodigitado,
0,
'S','Limpeza de saldos para invent�rio 05 de setembro de 2011'
from eqproduto pd, eqalmox ax, sgprefere1 pf, eqsaldoprod sp
where sp.codemp=pd.codemp and sp.codfilial=pd.codfilial and sp.codprod=pd.codprod and 
sp.codempax=ax.codemp and sp.codfilialax=ax.codfilial and 
sp.codalmox=ax.codalmox and pf.codemp=pd.codemp and pf.codfilial=pd.codfilial and 
sp.sldliqprod<>0 and  
ax.codalmox in (select spd.codalmox from eqsaldoprod spd where spd.codemp=pd.codemp and 
spd.codfilial=pd.codfilial and spd.codprod=pd.codprod);

-- Ajusta a sequencia de invent�rios para evitar problemas nos cadastros

update sgsequencia sq set sq.nroseq=(select coalesce(max(iv.codinvprod),0)+1
from eqinvprod iv, sgfilial f
where f.codemp=iv.codemp and f.codfilial=iv.codfilial and 
f.mzfilial='S')
where sq.sgtab='IV' and 
exists(select f.codemp from sgfilial f
where f.codemp=sq.codemp and f.codfilial=sq.codfilial and f.mzfilial='S' );

-- Mostra resultado da tabela EQPRODUTO ap�s inser��o 
select p.codprod, p.descprod, p.sldliqprod
from eqproduto p where p.sldliqprod<>0;

-- Mostra resultado da tabela EQSALDOPROD ap�s inser��o
select p.codprod, p.descprod, sp.sldliqprod
from eqproduto p, eqsaldoprod sp
where sp.codemp=p.codemp and sp.codfilial=p.codfilial and 
sp.codprod=p.codprod and sp.sldliqprod<>0;

-- Faz update na tabela de produtos em casos de diverg�ncias com a tabela de saldos
update eqproduto p set p.sldprod=coalesce(
  (select sum(sp.sldprod) from eqsaldoprod sp
  where sp.codemp=p.codemp and sp.codfilial=p.codfilial and 
  sp.codprod=p.codprod ),0)
where p.sldprod<>coalesce((select sum(sp.sldprod) from eqsaldoprod sp
  where sp.codemp=p.codemp and sp.codfilial=p.codfilial and 
  sp.codprod=p.codprod ),0); 

-- Mostra resultado da tabela EQPRODUTO ap�s atualiza��o
select p.codprod, p.descprod, p.sldliqprod
from eqproduto p where p.sldliqprod<>0;

-- Mostra resultado da tabela EQSALDOPROD ap�s atualiza��o
select p.codprod, p.descprod, sp.sldliqprod
from eqproduto p, eqsaldoprod sp
where sp.codemp=p.codemp and sp.codfilial=p.codfilial and 
sp.codprod=p.codprod and sp.sldliqprod<>0;

--insert into eqinvprod (
--codemp, codfilial, codinvprod, 
--codemppd, codfilialpd, codprod,
--codemptm, codfilialtm, codtipomov, datainvp,
--qtdinvp, precoinvp, 
--codempax, codfilialax, codalmox, 
--refprod, sldatualinvp, slddiginvp, flag, obsinvp)
--select iv.codemp, iv.codfilial, 
--  ( select max(ivs.codinvprod)+1 from eqinvprod ivs 
--  where ivs.codemp=iv.codemp and ivs.codfilial=iv.codfilial ) as codinvprod,
--iv.codemppd, iv.codfilialpd, iv.codprod
--iv.codemptm, iv.codfilialtm, iv.codtipomov, iv.datainvp,
--sum(iv.qtdinvp) qtdinvp, overage(iv.precoinvp) precoinvp, 
--iv.codempax, iv.codfilialax, ? codalmox,
--iv.refprod, 0 sldatualinvp, sum(iv.sldatualinvp) slddiginvp, iv.flag,
--'Invent�rio automatizado para envio de saldos anteriores para o almoxarifado ?'
--from eqinvprod iv
--where iv.datainvp=cast('now' as date) and iv.obsinvp like 'Limpeza de saldos%';

-- Ajusta a sequencia de invent�rios novamente para evitar problemas nos cadastros
--update sgsequencia sq set sq.nroseq=(select coalesce(max(iv.codinvprod),0)+1
--from eqinvprod iv, sgfilial f
--where f.codemp=iv.codemp and f.codfilial=iv.codfilial and 
--f.mzfilial='S')
--where sq.sgtab='IV' and 
--exists(select f.codemp from sgfilial f
--where f.codemp=sq.codemp and f.codfilial=sq.codfilial and f.mzfilial='S' );

commit work;

