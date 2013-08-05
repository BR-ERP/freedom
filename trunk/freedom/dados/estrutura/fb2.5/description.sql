/* Comments for database objects. */
COMMENT ON TABLE        ATATENDENTE IS 'Atendentes.';
COMMENT ON    COLUMN    ATATENDIMENTO.DATAATENDOFIN IS 'Data final do atendimento';
COMMENT ON    COLUMN    ATATENDIMENTO.HORAATENDOFIN IS 'Hora final do atendimento.';
COMMENT ON    COLUMN    ATATENDIMENTO.OBSINTERNO IS 'Campo para observa��es de interesse interno.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODEMPCT IS 'C�digo da empresa do contrato.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODFILIALCT IS 'C�digo da filial do contrato.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODCONTR IS 'C�digo do contrato.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODITCONTR IS 'C�digo do �tem de contrato.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODEMPCH IS 'C�digo da empresa do chamado.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODFILIALCH IS 'C�digo da filial do chamado.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODCHAMADO IS 'C�digo do Chamado.';
COMMENT ON    COLUMN    ATATENDIMENTO.CONCLUICHAMADO IS 'Indica se o atendimento deve concluir o chamado';
COMMENT ON    COLUMN    ATATENDIMENTO.CODEMPEA IS 'C�digo da empresa na tabela de especifica��o.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODFILIALEA IS 'C�digo da filial na tabela de especifica��o.';
COMMENT ON    COLUMN    ATATENDIMENTO.CODESPEC IS 'C�digo de especifica��o.';
COMMENT ON TABLE        ATATENDIMENTOITREC IS 'Tabela para vinculo entre item de contas a receber e atendimentos, a fim de registrar contatos e atendimentos,
referentes a cobran�as e negocia��es de t�tulos.';
COMMENT ON TABLE        ATCLASATENDO IS 'Tabela para classificar um atendimento.
ex.: bug, cortesia, garantia, etc.';
COMMENT ON    COLUMN    ATCLASATENDO.GERACOB IS 'Indica se deve gerar cobran�a no relat�rio de atendimentos.';
COMMENT ON    COLUMN    ATCLASATENDO.GERAREL IS 'Indica se deve aparecer no relat�rio de atendimentos.';
COMMENT ON    COLUMN    ATCONVENIADO.CODEMPEC IS 'C�digo da empresa - Encaminhador
';
COMMENT ON    COLUMN    ATCONVENIADO.CODFILIALEC IS 'C�digo da filial na tabela de encaminhador.
';
COMMENT ON    COLUMN    ATCONVENIADO.CODENC IS 'C�digo do encaminhador.
';
COMMENT ON    COLUMN    ATCONVENIADO.CODEMPFC IS 'C�digo da empresa na tabela RHFUNCAO';
COMMENT ON    COLUMN    ATCONVENIADO.CODFILIALFC IS 'C�digo da filial na tabela RHFUNCAO.';
COMMENT ON    COLUMN    ATCONVENIADO.CODFUNC IS 'C�digo da fun��o.';
COMMENT ON    COLUMN    ATCONVENIADO.DDDCONV IS 'C�digo DDD do telefone principal do conveniado.';
COMMENT ON    COLUMN    ATCONVENIADO.DDDFAXCONV IS 'C�DIGO DDD DO FAX DO CONVENIADO.';
COMMENT ON    COLUMN    ATCONVENIADO.DDDCELCONV IS 'C�DIGO DDD DO CELULAR DO CONVENIADO.';
COMMENT ON TABLE        ATENCAMINHADOR IS 'Tabela de empresas encaminhadoras de conveniados';
COMMENT ON    COLUMN    ATENCAMINHADOR.CODEMP IS 'C�digo da empresa
';
COMMENT ON    COLUMN    ATENCAMINHADOR.CODFILIAL IS 'C�digo da filial
';
COMMENT ON    COLUMN    ATENCAMINHADOR.CODENC IS 'C�digo do encaminhador
';
COMMENT ON    COLUMN    ATENCAMINHADOR.NOMEENC IS 'Nome do encaminhador
';
COMMENT ON    COLUMN    ATENCAMINHADOR.ENDENC IS 'Endere�o
';
COMMENT ON    COLUMN    ATENCAMINHADOR.NUMENC IS 'N�mero do logradouro
';
COMMENT ON    COLUMN    ATENCAMINHADOR.COMPLENC IS 'Complemento do endere�o
';
COMMENT ON    COLUMN    ATENCAMINHADOR.CIDENC IS 'Cidade
';
COMMENT ON    COLUMN    ATENCAMINHADOR.BAIRENC IS 'Bairro
';
COMMENT ON    COLUMN    ATENCAMINHADOR.UFENC IS 'Estado UF
';
COMMENT ON    COLUMN    ATENCAMINHADOR.CEPENC IS 'Cep
';
COMMENT ON    COLUMN    ATENCAMINHADOR.FONEENC IS 'Telefone
';
COMMENT ON    COLUMN    ATENCAMINHADOR.FAXENC IS 'N�mero do fax
';
COMMENT ON TABLE        ATESPECATEND IS 'Especifica��o dos atendimentos';
COMMENT ON    COLUMN    ATESPECATEND.CODEMP IS 'C�digo da empresa.';
COMMENT ON    COLUMN    ATESPECATEND.CODFILIAL IS 'C�digo da filial.';
COMMENT ON    COLUMN    ATESPECATEND.CODESPEC IS 'C�digo da especifica��o.';
COMMENT ON    COLUMN    ATESPECATEND.DESCESPEC IS 'Descri��o da especifica��o.';
COMMENT ON    COLUMN    ATESPECATEND.PGCOMIESPEC IS 'Paga comiss�o (S/N).';
COMMENT ON    COLUMN    ATESPECATEND.COBCLIESPEC IS 'Cobrar do cliente (S/N).';
COMMENT ON    COLUMN    ATESPECATEND.CONTMETAESPEC IS 'Contabiliza para meta (S/N).';
COMMENT ON    COLUMN    ATESPECATEND.TEMPOMINCOBESPEC IS 'Tempo m�nimo a contabilizar.';
COMMENT ON    COLUMN    ATESPECATEND.TEMPOMAXCOBESPEC IS 'Tempo m�ximo a contabilizar.';
COMMENT ON    COLUMN    ATESPECATEND.PERCCOMIESPEC IS 'Percentual de comiss�o do atendente.';
COMMENT ON    COLUMN    ATESPECATEND.DTINS IS 'Data de inser��o.';
COMMENT ON    COLUMN    ATESPECATEND.IDUSUINS IS 'Id do usu�rio que inseriu.';
COMMENT ON    COLUMN    ATESPECATEND.DTALT IS 'Data da �ltima altera��o.';
COMMENT ON    COLUMN    ATESPECATEND.IDUSUALT IS 'Id do usu�rio que fez a �ltima altera��o.';
COMMENT ON    COLUMN    ATESPECATEND.HINS IS 'Hora de inser��o.';
COMMENT ON    COLUMN    ATESPECATEND.HALT IS 'Hora da �ltima altera��o.';
COMMENT ON    COLUMN    ATTIPOATENDO.TIPOATENDO IS 'Tipo de atendimento.
C - Contato
A - Atendimento
';
COMMENT ON TABLE        CBCONTAEXT IS 'Tabela de contas cont�beis externas.';
COMMENT ON    COLUMN    CPCOMPRA.DTCOMPCOMPRA IS 'Data de compet�ncia.';
COMMENT ON    COLUMN    CPCOMPRA.VLRBASEICMSSTCOMPRA IS 'Valor da base de c�lculo do ICMS de substitui��o tribut�ria.';
COMMENT ON    COLUMN    CPCOMPRA.VLRICMSSTCOMPRA IS 'Valor do ICMS de substitui��o tribut�ria.';
COMMENT ON    COLUMN    CPCOMPRA.VLRFUNRURALCOMPRA IS 'Valor da reten��o referente ao Funrural.';
COMMENT ON    COLUMN    CPCOMPRA.CODEMPTC IS 'C�digo da empresa na tabela tipo de cobran�a.
';
COMMENT ON    COLUMN    CPCOMPRA.CODFILIALTC IS 'C�digo da filial na tabela tipo de cobran�a.
';
COMMENT ON    COLUMN    CPCOMPRA.CODTIPOCOB IS 'C�digo do tipo de cobran�a.
';
COMMENT ON    COLUMN    CPCOMPRA.IMPNOTACOMPRA IS 'Flag que indica se a nota fiscal foi impressa (S/N).';
COMMENT ON    COLUMN    CPCOMPRA.BLOQCOMPRA IS 'Flag de bloqueio de compra.';
COMMENT ON    COLUMN    CPCOMPRA.EMMANUT IS 'Flag que indica se a tabela est� em manuten��o (S/N).';
COMMENT ON    COLUMN    CPCOMPRA.ADICFRETECOMPRA IS 'Flag que indica se soma o valor do frete ao custo do produto.';
COMMENT ON    COLUMN    CPCOMPRA.TIPOFRETECOMPRA IS 'Tipo do frete na compra.';
COMMENT ON    COLUMN    CPCOMPRA.OBS01 IS 'Campo "coringa" para outras observa��es.';
COMMENT ON    COLUMN    CPCOMPRA.OBS02 IS 'Campo "coringa" para outras observa��es.';
COMMENT ON    COLUMN    CPCOMPRA.OBS03 IS 'Campo "coringa" para outras observa��es.';
COMMENT ON    COLUMN    CPCOMPRA.OBS04 IS 'Campo "coringa" para outras observa��es.';
COMMENT ON    COLUMN    CPCOMPRA.ADICADICCOMPRA IS 'Flag que indica se soma o valor adicional ao custo do produto.';
COMMENT ON    COLUMN    CPCOMPRA.QTDFRETECOMPRA IS 'Quantidade de volumes na nota de entrada.';
COMMENT ON    COLUMN    CPCOMPRA.ADICFRETEBASEICMS IS 'Indica se deve adicionar o valor do frete � base de calculo do icms.';
COMMENT ON    COLUMN    CPCOMPRA.ADICADICBASEICMS IS 'Indica se deve somar os valores adicionais � base de calculo do ICMS.';
COMMENT ON    COLUMN    CPCOMPRA.ADICIPIBASEICMS IS 'Indica se deve adicionar o valor do IPI � base de calculo do ICMS.';
COMMENT ON    COLUMN    CPCOMPRA.CHAVENFECOMPRA IS 'Chave de acesso da nota fiscal eletr�nica de compra.';
COMMENT ON    COLUMN    CPCOMPRA.STATUSCOMPRA IS 'P1 - Pendente;
P2 - Em aberto;
P3 - Em aberto;
C2 - Emitida;
C3 - Emitida;
EP - Pedido de compra emitida parcialmente em outra compra;
ET - Pedido de compra emitido totalmente em outra compra;
X - Cancelada;';
COMMENT ON    COLUMN    CPCOMPRA.NRODI IS 'N�mero do documento de importa��o DI/DSI/DA';
COMMENT ON    COLUMN    CPCOMPRA.DTREGDI IS 'Data de registro da DI';
COMMENT ON    COLUMN    CPCOMPRA.LOCDESEMBDI IS 'Loca de desembara�� da DI';
COMMENT ON    COLUMN    CPCOMPRA.SIGLAUFDESEMBDI IS 'Estado onde ocorreu o desembara�o da DI';
COMMENT ON    COLUMN    CPCOMPRA.CODPAISDESEMBDI IS 'C�digo do pa�s onde ocorreu o desembara�o da DI (ser� sempre o pais da filial, mantido por integridade referencial tabela SGUF)';
COMMENT ON    COLUMN    CPCOMPRA.DTDESEMBDI IS 'Data do desembara�o da DI';
COMMENT ON    COLUMN    CPCOMPRA.IDENTCONTAINER IS 'Identifica��o do container da compra (importa��o)';
COMMENT ON    COLUMN    CPCOMPRA.CALCTRIB IS 'Indica se deve realizar o calculo de tributos';
COMMENT ON    COLUMN    CPCOMPRA.CODEMPRM IS 'C�digo da empresa do recebimento de mercadoria vinculado.';
COMMENT ON    COLUMN    CPCOMPRA.CODFILIALRM IS 'C�digo da filial do recebimento de mercadoria vinculado.';
COMMENT ON    COLUMN    CPCOMPRA.TICKET IS 'Ticket do recebimento de mercadoria vinculado.';
COMMENT ON    COLUMN    CPCOMPRA.CODEMPCT IS 'C�digo da empresa da conta.';
COMMENT ON    COLUMN    CPCOMPRA.CODFILIALCT IS 'C�digo da filial da compra.';
COMMENT ON    COLUMN    CPCOMPRA.NUMCONTA IS 'N�mero da conta.';
COMMENT ON    COLUMN    CPCOMPRA.CODEMPCC IS 'C�digo da empresa do centro de custos';
COMMENT ON    COLUMN    CPCOMPRA.CODFILIALCC IS 'C�digo da filial do centro de custos.';
COMMENT ON    COLUMN    CPCOMPRA.ANOCC IS 'Ano do centro de custo';
COMMENT ON    COLUMN    CPCOMPRA.CODCC IS 'C�digo do centro de custos';
COMMENT ON    COLUMN    CPCOMPRA.CODEMPPN IS 'C�digo da empresa do planejamento financeiro.';
COMMENT ON    COLUMN    CPCOMPRA.CODFILIALPN IS 'C�digo do filial do planejamento financeiro.';
COMMENT ON    COLUMN    CPCOMPRA.CODPLAN IS 'C�digo do planejamento financeiro.';
COMMENT ON    COLUMN    CPCOMPRA.INFCOMPL IS 'Informa��es complementares (interesse do fisco)';
COMMENT ON    COLUMN    CPCOMPRA.NUMACDRAW IS 'N�mero do ato concess�rio do regime drawback (importa��o)';
COMMENT ON    COLUMN    CPCOMPRA.TIPODOCIMP IS 'Tipo de documento de importa��o:
0 - Declara��o de importa��o
1 - Declara��o simplificada de importa��o';
COMMENT ON    COLUMN    CPCOMPRA.SITDOC IS 'Situa��o do documento fiscal:
00-Documento regular;
01-Documento regular expont�neo;
02-Documento cancelado;
03-Documento cancelado expont�neo
04-NFE Denegada;
05-NFE Numera��o inutilizada;
06-Documento fiscal complementar;
07-Documento fiscal complementar expont�neo;
08-Documento emitido com base em Regime Especial ou Norma Espec�fica;';
COMMENT ON    COLUMN    CPCOMPRA.OBSNFE IS 'Observacao gerada na emissao de nfe.';
COMMENT ON    COLUMN    CPCOMPRA.CODEMPIM IS 'C�digo da empresa de importa��o.';
COMMENT ON    COLUMN    CPCOMPRA.CODFILIALIM IS 'C�digo da filial da importa��o.';
COMMENT ON    COLUMN    CPCOMPRA.CODIMP IS 'C�digo da importa��o.
';
COMMENT ON    COLUMN    CPCOMPRA.VLRBASEIICOMPRA IS 'Valor da base de calculo do imposto de importa��o.
';
COMMENT ON    COLUMN    CPCOMPRA.VLRIICOMPRA IS 'Valor do imposto de importa��o da compra.
';
COMMENT ON TABLE        CPCOMPRASOL IS 'Tabela de vinculos para amarra��o entre solicita��es de compra
e pedidos/nf de compra.';
COMMENT ON    COLUMN    CPCOMPRASOL.CODCOMPRA IS 'C�digo da compra.';
COMMENT ON    COLUMN    CPCOMPRASOL.CODSOL IS 'C�digo da solicita��o de compra.';
COMMENT ON    COLUMN    CPCOTACAO.DTVALIDCOT IS 'Data de validade da proposta de pre�o.';
COMMENT ON    COLUMN    CPCOTACAO.SITCOMPITSOL IS '"PE" - Pendente;
"FN" - Finalizada;';
COMMENT ON    COLUMN    CPCOTACAO.SITAPROVITSOL IS '"PE" - Pendente;
"AP" - Aprovada;';
COMMENT ON    COLUMN    CPCOTACAO.SITITSOL IS '"PE" - Pendente;
"FN" - Finalizada;';
COMMENT ON    COLUMN    CPCOTACAO.USARENDACOT IS 'Indica se deve usar o crit�rio da renda na busca do pre�o de cota��es.';
COMMENT ON    COLUMN    CPCOTACAO.RENDACOT IS 'Indica a renda (qualidade) do produto cotado.';
COMMENT ON    COLUMN    CPCOTACAO.CODEMPPP IS 'C�digo da empresa do plano de pagamento.';
COMMENT ON    COLUMN    CPCOTACAO.CODFILIALPP IS 'C�digo da filial do plano de pagamento.';
COMMENT ON    COLUMN    CPCOTACAO.CODPLANOPAG IS 'C�digo do plano de pagamento.';
COMMENT ON TABLE        CPDEVOLUCAO IS 'Tabela para relacionamento entre itens de compra e venda, caracterizando a devolu��o de mercadorias.';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODEMP IS 'C�digo da empresa da compra.';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODFILIAL IS 'C�digo da filial da compra.';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODCOMPRA IS 'C�digo da compra.';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODITCOMPRA IS 'C�digo do �tem de compra.';
COMMENT ON    COLUMN    CPDEVOLUCAO.QTDDEV IS 'Quantidade devolvida.';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODEMPVD IS 'C�digo da empresa da venda (devolu��o)';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODFILIALVD IS 'C�digo da filial da venda (devolu��o)';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODVENDA IS 'C�digo da venda (devolu��o)';
COMMENT ON    COLUMN    CPDEVOLUCAO.TIPOVENDA IS 'Tipo da venda (devolu��o)';
COMMENT ON    COLUMN    CPDEVOLUCAO.CODITVENDA IS 'C�digo do �tem da venda (devolu��o)';
COMMENT ON    COLUMN    CPFORNECED.CELFOR IS 'N�mero do celular
';
COMMENT ON    COLUMN    CPFORNECED.SSPFOR IS 'Org�o de espedi��o do rg.';
COMMENT ON    COLUMN    CPFORNECED.DDDFONEFOR IS 'DDD do telefone principal.';
COMMENT ON    COLUMN    CPFORNECED.DDDFAXFOR IS 'DDD do fax do fornecedor.';
COMMENT ON    COLUMN    CPFORNECED.DDDCELFOR IS 'DDD do celular do fornecedor.';
COMMENT ON    COLUMN    CPFORNECED.SITEFOR IS 'Endere�o eletr�nico do site do fornecedor.';
COMMENT ON    COLUMN    CPFORNECED.CODFORCONTAB IS 'C�digo contabil';
COMMENT ON    COLUMN    CPFORNECED.CODUNIFCOD IS 'C�digo na tabela de unifica��o de c�digos (SGUNIFCOD).';
COMMENT ON    COLUMN    CPFORNECED.CODEMPFF IS 'C�digo da empresa do tipo fiscal do fornecedor.';
COMMENT ON    COLUMN    CPFORNECED.CODFILIALFF IS 'C�digo da filial do tipo fiscal do fornecedor.';
COMMENT ON    COLUMN    CPFORNECED.CODFISCFOR IS 'C�digo do tipo fiscal do fornecedor';
COMMENT ON    COLUMN    CPFORNECED.SUFRAMAFOR IS 'C�digo do SUFRAMA do fornecedor.';
COMMENT ON    COLUMN    CPFORNECED.NRODEPENDFOR IS 'N�mero de dependes do fornecedor (pessoa f�sica) (calculo de irrf)';
COMMENT ON    COLUMN    CPFORNECED.INSCCONREG IS 'Inscri��o no conselho regional (contabilidae, administra��o, medicina, etc..)';
COMMENT ON    COLUMN    CPFRETECP.VLRSEGFRETECP IS 'Valor do seguro no frete.';
COMMENT ON TABLE        CPIMPORTACAO IS 'Tabela de informa��es sobre importa��es.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODEMP IS 'C�digo da empresa.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODFILIAL IS 'C�digo da filial';
COMMENT ON    COLUMN    CPIMPORTACAO.CODIMP IS 'C�digo da importa��o';
COMMENT ON    COLUMN    CPIMPORTACAO.CODEMPMI IS 'C�digo da empresa da moeda de importa��o.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODFILIALMI IS 'C�digo da filial da moeda de importa��o.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODMOEDA IS 'C�digo da moeda de importa��o.';
COMMENT ON    COLUMN    CPIMPORTACAO.COTACAOMOEDA IS 'Fator de convers�o da moeda de importa��o para moeda corrente.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODEMPPG IS 'C�digo da empresa do plano de pagamento.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODFILIALPG IS 'C�digo da filial do plano de pagamento';
COMMENT ON    COLUMN    CPIMPORTACAO.CODPLANOPAG IS 'C�digo do plano de pagamento acordado no invoice.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODEMPFR IS 'C�digo da empresa do fornecedor.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODFILIALFR IS 'C�digo da filial do fornecedor.';
COMMENT ON    COLUMN    CPIMPORTACAO.CODFOR IS 'C�digo do fornecedor na importa��o.';
COMMENT ON    COLUMN    CPIMPORTACAO.TIPOIMP IS 'Tipo de importa��o - O = Original / C = Complementar';
COMMENT ON    COLUMN    CPIMPORTACAO.INVOICE IS 'Identifica��o do invoice (pedido de compra de importa��o)';
COMMENT ON    COLUMN    CPIMPORTACAO.DI IS 'N�mero da declara��o de importa��o .';
COMMENT ON    COLUMN    CPIMPORTACAO.MANIFESTO IS 'Identifica��o do manifesto de carga.';
COMMENT ON    COLUMN    CPIMPORTACAO.CERTORIGEM IS 'Certificado de origem da mercadoria.';
COMMENT ON    COLUMN    CPIMPORTACAO.LACRE IS 'Identifica��o do lacre';
COMMENT ON    COLUMN    CPIMPORTACAO.PRESCARGA IS 'Presen�a de carga';
COMMENT ON    COLUMN    CPIMPORTACAO.IDENTHOUSE IS 'Identifica��o House B/L';
COMMENT ON    COLUMN    CPIMPORTACAO.CONHECCARGA IS 'Identifica��o do conhecimento de carga.';
COMMENT ON    COLUMN    CPIMPORTACAO.IDENTCONTAINER IS 'Identifica��o do container';
COMMENT ON    COLUMN    CPIMPORTACAO.TIPOMANIFESTO IS 'Tipo de manifesto.';
COMMENT ON    COLUMN    CPIMPORTACAO.DTIMP IS 'Data da importa��o';
COMMENT ON    COLUMN    CPIMPORTACAO.DTEMB IS 'Data de embarque.';
COMMENT ON    COLUMN    CPIMPORTACAO.DTCHEGADA IS 'Data de chegada';
COMMENT ON    COLUMN    CPIMPORTACAO.DTDESEMBDI IS 'Data do desembara�o da declara��o de importa��o';
COMMENT ON    COLUMN    CPIMPORTACAO.DTREGDI IS 'Data de registro da declara��o de importa��o.';
COMMENT ON    COLUMN    CPIMPORTACAO.LOCALEMB IS 'Local do desembara�o da declara��o de importa��o.';
COMMENT ON    COLUMN    CPIMPORTACAO.RECINTOADUANEIRO IS 'Recinto Aduaneiro';
COMMENT ON    COLUMN    CPIMPORTACAO.CODPAISDESEMBDI IS 'C�digo do pa�s do desembara�o da DI';
COMMENT ON    COLUMN    CPIMPORTACAO.SIGLAUFDESEMBDI IS 'Sigla da unidade da federa��o do desembara�o da DI';
COMMENT ON    COLUMN    CPIMPORTACAO.LOCDESEMBDI IS ' Local de desembara�o da DI.';
COMMENT ON    COLUMN    CPIMPORTACAO.OBS IS 'Observa��es';
COMMENT ON    COLUMN    CPIMPORTACAO.VEICULO IS 'Identifica��o do ve�culo (ex.: Nome do navio)';
COMMENT ON    COLUMN    CPIMPORTACAO.PESOBRUTO IS 'Peso bruto total.';
COMMENT ON    COLUMN    CPIMPORTACAO.PESOLIQUIDO IS 'Peso l�quido total';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRFRETEMI IS 'Valor do frete na moeda de importa��o';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRFRETE IS 'Valor do frete na moeda corrente.';
COMMENT ON    COLUMN    CPIMPORTACAO.VMLEMI IS 'Valor liquido sem o frete na moeda de importa��o.';
COMMENT ON    COLUMN    CPIMPORTACAO.VMLDMI IS 'Valor liquido mais o frete na moeda de importa��o.';
COMMENT ON    COLUMN    CPIMPORTACAO.VMLE IS 'Valor liquido sem o frete na moeda corrente.';
COMMENT ON    COLUMN    CPIMPORTACAO.VMLD IS 'Valor liquido mais o frete na moeda corrente.';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRSEGUROMI IS 'Valor do seguro na moeda de importa��o.';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRSEGURO IS 'Valor do seguro na moeda corrente.';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRII IS 'Valor do imposto de importa��o.';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRIPI IS 'Valor do  IPI';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRPIS IS 'Valor do PIS';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRCOFINS IS 'Valor do cofins';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRDIREITOSAD IS 'Valor de direitos antidumping.';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRTHC IS 'Valor da taxa de manuten��o no terminal (Terminal Handling Charges)';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRTHCMI IS 'Valor do THC em moeda de importa��o';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRTXSISCOMEX IS 'Valor da taxa siscomex.';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRAD IS 'Valor aduaneiro valor da mercadoria + frete +seguro +thc
';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRADMI IS 'Valor aduaneiro em moeda de importa��o
';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRBASEICMS IS 'Base de calculo do icms
';
COMMENT ON    COLUMN    CPIMPORTACAO.VLRCOMPL IS 'Valor para nota fiscal complementar';
COMMENT ON    COLUMN    CPIMPORTACAO.EMMANUT IS 'Flag que indica se a tabela est� em manuten��o (S/N).';
COMMENT ON    COLUMN    CPIMPORTACAO.DTINS IS 'Data de inser��o do registro.';
COMMENT ON    COLUMN    CPIMPORTACAO.HINS IS 'Hora de inser��o do registro.';
COMMENT ON    COLUMN    CPIMPORTACAO.IDUSUINS IS 'Identifica��o do usu�rio que inseriu o registro';
COMMENT ON    COLUMN    CPIMPORTACAO.DTALT IS 'Data de altera��o do registro';
COMMENT ON    COLUMN    CPIMPORTACAO.HALT IS 'Hora de altera��o do registro.';
COMMENT ON    COLUMN    CPIMPORTACAO.IDUSUALT IS 'Identifica��o do usu�rio que alterou o registro.';
COMMENT ON TABLE        CPIMPORTACAOADIC IS 'Tabela de adi��es da importa��o.';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.CODEMP IS 'C�digo da empresa';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.CODFILIAL IS 'C�digo da filial';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.CODIMP IS 'C�digo da importa��o';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.CODNCM IS 'C�digo da NCM';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.CODADIC IS 'C�digo da adicao';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.VLRTXSISCOMEX IS 'Valor da taxa siscomex para a adic�o';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.DTINS IS 'Data de inser��o do registro';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.HINS IS 'Hora de inser��o do registro';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.IDUSUINS IS 'Identifica��o do usu�rio de inseriu o registro';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.DTALT IS 'Data da altera��o do registro';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.HALT IS 'Hora da altera��o do registro';
COMMENT ON    COLUMN    CPIMPORTACAOADIC.IDUSUALT IS 'Identifica��o do usu�rio que alterou o registro';
COMMENT ON    COLUMN    CPITCOMPRA.CODEMPAX IS 'C�digo da empresa na tabela de almoxarifados.';
COMMENT ON    COLUMN    CPITCOMPRA.CODFILIALAX IS 'C�digo da filial na tabela de almoxarifados.';
COMMENT ON    COLUMN    CPITCOMPRA.CODALMOX IS 'C�digo do almoxarifado.';
COMMENT ON    COLUMN    CPITCOMPRA.QTDITCOMPRACANC IS 'Quantidade cancelada';
COMMENT ON    COLUMN    CPITCOMPRA.VLRBASEFUNRURALITCOMPRA IS 'Valor para base de calculo da reten��o do funrural.';
COMMENT ON    COLUMN    CPITCOMPRA.ALIQFUNRURALITCOMPRA IS 'Percentual de reten��o do funrural no item de compra.';
COMMENT ON    COLUMN    CPITCOMPRA.VLRFUNRURALITCOMPRA IS 'Valor de reten��o do funrural do item.';
COMMENT ON    COLUMN    CPITCOMPRA.CUSTOVDITCOMPRA IS 'Custo para forma��o de pre�os
';
COMMENT ON    COLUMN    CPITCOMPRA.CODEMPIF IS 'Codigo da empresa do item de classifica��o (regra no momento da inser��o)';
COMMENT ON    COLUMN    CPITCOMPRA.CODFILIALIF IS 'Codigo da filial do item de classifica��o (regra no momento da inser��o)';
COMMENT ON    COLUMN    CPITCOMPRA.CODFISC IS 'Codigo da classifica��o (regra no momento da inser��o)';
COMMENT ON    COLUMN    CPITCOMPRA.EMMANUT IS 'Flag que indica se a tabela est� em manuten��o.';
COMMENT ON    COLUMN    CPITCOMPRA.CODEMPNS IS 'C�digo da empresa do n�mero de s�rie';
COMMENT ON    COLUMN    CPITCOMPRA.CODFILIALNS IS 'C�digo da filial do n�mero de s�rie
';
COMMENT ON    COLUMN    CPITCOMPRA.NUMSERIETMP IS 'Campo para abrigar temporariamente o n�mero de serie do produto (para uso do trigger quando produto for unit�rio)
';
COMMENT ON    COLUMN    CPITCOMPRA.NADICAO IS 'N�mero da adicao (entrada de importa��o)';
COMMENT ON    COLUMN    CPITCOMPRA.SEQADIC IS 'N�mero sequencial do �tem dentro da adi��o.';
COMMENT ON    COLUMN    CPITCOMPRA.DESCDI IS 'Valor do desconto do item da DI
';
COMMENT ON    COLUMN    CPITCOMPRA.APROVPRECO IS 'Indica se o pre�o da compra foi aprovado (veio de cota��o anterior);';
COMMENT ON    COLUMN    CPITCOMPRA.EMITITCOMPRA IS 'Indica se o �tem de compra j� foi emitido em compra compra (processo de faturamento de pedidos de compra)';
COMMENT ON TABLE        CPITCOMPRASERIE IS 'Tabela de vinculo entre item de compra e seus respectivos numeros de serie.';
COMMENT ON TABLE        CPITIMPORTACAO IS 'Tabela de item de importa��o.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODEMP IS 'C�digo da empresa.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODFILIAL IS 'C�digo da filial.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODIMP IS 'C�digo da importa��o.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODITIMP IS 'C�digo do item de importa��o.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODEMPPD IS 'C�digo da empresa do produto.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODFILIALPD IS 'C�digo da filial do produto.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODPROD IS 'C�digo do produto.';
COMMENT ON    COLUMN    CPITIMPORTACAO.REFPROD IS 'Refer�ncia do produto.';
COMMENT ON    COLUMN    CPITIMPORTACAO.QTD IS 'Quantidade de itens.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODEMPUN IS 'C�digo da empresa da unidade de medida.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODFILIALUN IS 'C�digo da filial da unidade de medida.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODUNID IS 'C�digo da unidade de medida
';
COMMENT ON    COLUMN    CPITIMPORTACAO.PESOLIQUIDO IS 'Peso liquido';
COMMENT ON    COLUMN    CPITIMPORTACAO.PESOBRUTO IS 'Peso bruto';
COMMENT ON    COLUMN    CPITIMPORTACAO.PRECOMI IS 'Preco na moeda de importa��o';
COMMENT ON    COLUMN    CPITIMPORTACAO.PRECO IS 'Pre�o na moeda corrente.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VMLEMI IS 'Valor bruto da mercadoria na moeda de importa��o.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VMLDMI IS 'Valor bruto da mercadoria + frete e seguro, na moeda de importa��o.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VMLE IS 'Valor bruto da mercadoria na moeda corrente.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VMLD IS 'Valor bruto da mercadoria + frete e seguro, na moeda corrente.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRFRETEMI IS 'Valor do frete na moeda de importa��o.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRFRETE IS 'Valor do frete na moeda corrente.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRSEGUROMI IS 'Valor do seguro na moeda de importa��o.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRSEGURO IS 'Valor do seguro na moeda corrente.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRTHCMI IS 'Valor do THC na moeda de importa��o.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRTHC IS 'Valor do THC';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRADMI IS 'Valor aduaneiro (Valor bruto+frete+seguro+thc) em moeda de importa��o.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRAD IS 'Valor aduaneiro (Valor bruto+frete+seguro+thc) na moeda corrente.';
COMMENT ON    COLUMN    CPITIMPORTACAO.ALIQICMSIMP IS 'Aliquota do ICMS de importa��o.';
COMMENT ON    COLUMN    CPITIMPORTACAO.ALIQICMSUF IS 'Aliquota do ICMS da UF (Opera��o futura)';
COMMENT ON    COLUMN    CPITIMPORTACAO.PERCDIFERICMS IS 'Percentual do diferimento do ICMS.';
COMMENT ON    COLUMN    CPITIMPORTACAO.ALIQIPI IS 'Aliquota de IPI';
COMMENT ON    COLUMN    CPITIMPORTACAO.ALIQPIS IS 'Aliquota do PIS';
COMMENT ON    COLUMN    CPITIMPORTACAO.ALIQCOFINS IS 'Aliquota do cofins';
COMMENT ON    COLUMN    CPITIMPORTACAO.ALIQII IS 'Aliquota do imposto de importa��o.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRII IS 'Valor do imposto de importa��o.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRIPI IS 'Valor do IPI';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRPIS IS 'Valor do PIS';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRCOFINS IS 'Valor do cofins';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRBASEICMS IS 'Valor da base do ICMS.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRICMS IS 'Valor do ICMS bruto';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRICMSDIFERIDO IS 'Valor do ICMS diferido';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRICMSDEVIDO IS 'Valor do ICMS devido';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRICMSCREDPRESUM IS 'Valor do cr�dito do ICMS presumido.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRICMSRECOLHIMENTO IS 'Valor do ICMS a recolher.';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRTXSISCOMEX IS 'Valor da taxa siscomex rateada por �tem.
';
COMMENT ON    COLUMN    CPITIMPORTACAO.VLRVMCV IS 'Valor bruto
';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODEMPCF IS 'C�digo da empresa do da classifica��o fiscal.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODFILIALCF IS 'C�digo da filial da classifica��o fiscal.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODFISC IS 'Codigo da classifica��o fiscal.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODITFISC IS 'C�digo do item de classifica��o fiscal.';
COMMENT ON    COLUMN    CPITIMPORTACAO.CODNCM IS 'C�digo da NCM';
COMMENT ON    COLUMN    CPITIMPORTACAO.SEQADIC IS 'Sequencia do item na adi��o.
';
COMMENT ON    COLUMN    CPITIMPORTACAO.DTINS IS 'Data de inser��o do registro.';
COMMENT ON    COLUMN    CPITIMPORTACAO.HINS IS 'Hora da inser��o do registro.';
COMMENT ON    COLUMN    CPITIMPORTACAO.IDUSUINS IS 'Identifica��o do usu�rio que inseriu o registro.';
COMMENT ON    COLUMN    CPITIMPORTACAO.DTALT IS 'Data da altera��o do registro.';
COMMENT ON    COLUMN    CPITIMPORTACAO.HALT IS 'Hora da altera��o do registro.';
COMMENT ON    COLUMN    CPITIMPORTACAO.IDUSUALT IS 'Identifica��o do usu�rio que alterou o registro.';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODEMP IS 'C�digo da empresa';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODFILIAL IS 'C�digo da filial';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODSOL IS 'C�digo da solicita��o';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODITSOL IS 'N�mero sequencial do item';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODEMPPD IS 'C�digo da empresa para produtos';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODFILIALPD IS 'C�digo da filial para produtos';
COMMENT ON    COLUMN    CPITSOLICITACAO.CODPROD IS 'C�digo do produto ou servi�o';
COMMENT ON    COLUMN    CPITSOLICITACAO.REFPROD IS 'Refer�ncia do produto.';
COMMENT ON    COLUMN    CPITSOLICITACAO.QTDITSOL IS 'Quantidade solicitada';
COMMENT ON    COLUMN    CPITSOLICITACAO.QTDAPROVITSOL IS 'Quantidade aprovada';
COMMENT ON    COLUMN    CPITSOLICITACAO.IDUSUITSOL IS 'Id do usu�rio que solicitou';
COMMENT ON    COLUMN    CPITSOLICITACAO.DTITSOL IS 'Data da solicita��o do item';
COMMENT ON    COLUMN    CPITSOLICITACAO.IDUSUAPROVITSOL IS 'Id do usu�rio que aprovou';
COMMENT ON    COLUMN    CPITSOLICITACAO.DTAPROVITSOL IS 'Data da aprova��o do item';
COMMENT ON    COLUMN    CPITSOLICITACAO.SITAPROVITSOL IS 'Situa��o da aprova��o do item:
PE - Pendente
AP - Aprova��o parcial
AT - Aprova��o total
NA - N�o aprovada';
COMMENT ON    COLUMN    CPITSOLICITACAO.SITCOMPITSOL IS 'Situa��o da compra:
PE - Pendente
CP - Compra parcial
CT - Compra total
';
COMMENT ON    COLUMN    CPITSOLICITACAO.SITITSOL IS 'Situa��o do item da solicita��o:
PE - Pendente
FN - Solicita��o finalizada
CA - Cancelada';
COMMENT ON    COLUMN    CPRATEIO.CODEMP IS 'C�digo da empresa.';
COMMENT ON    COLUMN    CPRATEIO.CODFILIAL IS 'C�digo da filial.';
COMMENT ON    COLUMN    CPRATEIO.CODCOMPRA IS 'C�digo da compra.';
COMMENT ON    COLUMN    CPRATEIO.SEQRATEIO IS 'N�mero sequencial do rateio.';
COMMENT ON    COLUMN    CPRATEIO.CODEMPFN IS 'C�digo da empresa na tabela FNPLANEJAMENTO';
COMMENT ON    COLUMN    CPRATEIO.CODFILIALFN IS 'C�digo da filial na tabela FNPLANEJAMENTO.';
COMMENT ON    COLUMN    CPRATEIO.CODPLAN IS 'C�digo do planejamento.';
COMMENT ON    COLUMN    CPRATEIO.CODEMPCC IS 'C�digo da empresa na tabela FNCC.';
COMMENT ON    COLUMN    CPRATEIO.CODFILIALCC IS 'C�digo da filial na tabela FNCC';
COMMENT ON    COLUMN    CPRATEIO.ANOCC IS 'Ano do centro de custo.';
COMMENT ON    COLUMN    CPRATEIO.CODCC IS 'C�digo do centro de custo.';
COMMENT ON    COLUMN    CPRATEIO.PERCRATEIO IS 'Percentual de rateio.';
COMMENT ON    COLUMN    CPRATEIO.VLRRATEIO IS 'Valor do rateio.';
COMMENT ON    COLUMN    CPRATEIO.SITRATEIO IS 'Situa��o da concilia��o do rateio:
N-Pendente
P-Parcial
T-Total';
COMMENT ON    COLUMN    CPRATEIO.DTINS IS 'Data de inser��o.';
COMMENT ON    COLUMN    CPRATEIO.HINS IS 'Hora de inser��o.';
COMMENT ON    COLUMN    CPRATEIO.IDUSUINS IS 'ID do usu�rio que inseriu.';
COMMENT ON    COLUMN    CPRATEIO.DTALT IS 'Data da �ltima altera��o.';
COMMENT ON    COLUMN    CPRATEIO.HALT IS 'Hora da �ltima altera��o.';
COMMENT ON    COLUMN    CPRATEIO.IDUSUALT IS 'ID do usu�rio que fez a �ltima altera��o.';
COMMENT ON    COLUMN    CPSOLICITACAO.CODEMP IS 'C�digo da empresa';
COMMENT ON    COLUMN    CPSOLICITACAO.CODFILIAL IS 'C�digo da filial';
COMMENT ON    COLUMN    CPSOLICITACAO.CODSOL IS 'C�digo da solicita��o';
COMMENT ON    COLUMN    CPSOLICITACAO.DTEMITSOL IS 'Data de emiss�o da solicita��o';
COMMENT ON    COLUMN    CPSOLICITACAO.SITSOL IS 'PE - Pendente
CT - Em Cota��o
EF - Finalizada
CA - Cancelada';
COMMENT ON    COLUMN    CPSOLICITACAO.ORIGSOL IS 'Origem da solicita��o
RM - RMA
AX - ALMOXARIFE';
COMMENT ON    COLUMN    CPSOLICITACAO.MOTIVOSOL IS 'Motivo de cancelamento.';
COMMENT ON    COLUMN    CPSOLICITACAO.DTINS IS 'Data de inser��o';
COMMENT ON    COLUMN    CPSOLICITACAO.HINS IS 'Hora de inser��o';
COMMENT ON    COLUMN    CPSOLICITACAO.IDUSUINS IS 'ID do usu�rio que inseriu';
COMMENT ON    COLUMN    CPSOLICITACAO.DTALT IS 'Data da �ltima altera��o';
COMMENT ON    COLUMN    CPSOLICITACAO.HALT IS 'Hora da �ltima altera��o';
COMMENT ON    COLUMN    CPSOLICITACAO.IDUSUALT IS 'ID do usu�rio da �ltima altera��o';
COMMENT ON TABLE        CPTIPOFOR IS 'Tabela de tipos de fornecedores.';
COMMENT ON    COLUMN    CPTIPOFOR.RETENCAOINSS IS 'Indica se deve realizar a reten��o do inss nos pagamentos (autonomo).';
COMMENT ON    COLUMN    CPTIPOFOR.PERCBASEINSS IS 'Percentual de base de calculo do INSS.';
COMMENT ON    COLUMN    CPTIPOFOR.RETENCAOOUTROS IS 'Indica se deve calcular outras reten��es agregadas ao INSS ex.: SEST/SENAT';
COMMENT ON    COLUMN    CPTIPOFOR.PERCRETOUTROS IS 'Percentual de reten��o de outros tributos agregados ao inss (SEST/SENAT)';
COMMENT ON    COLUMN    CPTIPOFOR.RETENCAOIRRF IS 'Indica se deve realizar a reten��o do imposto de renda nos pagamentos (autonomo).';
COMMENT ON    COLUMN    CPTIPOFOR.PERCBASEIRRF IS 'Percentual de base de calculo do IRRF.';
COMMENT ON    COLUMN    CRCHAMADO.FATOGERADOR IS 'Identifica��o da a��o que gerou o problema.';
COMMENT ON    COLUMN    CRCHAMADO.AMBIENTE IS 'Informa��es sobre o ambiente onde ocorreu o fato;
';
COMMENT ON    COLUMN    CRCHAMADO.OBSCHAMADO IS 'Outras observa��es sobre o chamado';
COMMENT ON    COLUMN    CRCHAMADO.PRIORIDADE IS 'Ordem de prioridade:
1 - Muito Alta
2 - Alta
3 - Media
4 - Baixa
5 - Muito Baixa';
COMMENT ON    COLUMN    CRCHAMADO.CODEMPTC IS 'C�digo da empresa do tipo de chamado';
COMMENT ON    COLUMN    CRCHAMADO.CODFILIALTC IS 'C�digo da filial do tipo de chamado';
COMMENT ON    COLUMN    CRCHAMADO.CODTPCHAMADO IS 'C�digo do tipo de chamado.';
COMMENT ON    COLUMN    CRCHAMADO.STATUS IS 'Status do chamado:
PE - Pendente
AN - Em analise
EA - Em andamento
CA - Cancelado
CO - Conclu�do';
COMMENT ON    COLUMN    CRCHAMADO.DTCHAMADO IS 'Data de abertura do chamado.';
COMMENT ON    COLUMN    CRCHAMADO.DTPREVISAO IS 'Data de previs�o do encerramento.';
COMMENT ON    COLUMN    CRCHAMADO.QTDHORASPREVISAO IS 'Quantidade de horas prevista pra execu��o do chamado.';
COMMENT ON    COLUMN    CRCHAMADO.DTCONCLUSAO IS 'Data de conclus�o do chamado';
COMMENT ON    COLUMN    CRCHAMADO.CODEMPAE IS 'C�digo da empresa do atendente designado.';
COMMENT ON    COLUMN    CRCHAMADO.CODFILIALAE IS 'Codigo da filial do atendente designado.';
COMMENT ON    COLUMN    CRCHAMADO.CODATEND IS 'C�digo do atendente designado.';
COMMENT ON    COLUMN    CRCHAMADO.CODEMPOS IS 'C�digo da empresa do item de ordem de servi�o.';
COMMENT ON    COLUMN    CRCHAMADO.CODFILIALOS IS 'C�digo da filial da ordem de servi�o.';
COMMENT ON    COLUMN    CRCHAMADO.TICKET IS 'Ticket da ordem de servi�o.';
COMMENT ON    COLUMN    CRCHAMADO.CODITRECMERC IS 'C�digo do �tem de recebimento de mercadorias.';
COMMENT ON    COLUMN    CRCHAMADO.CODITOS IS 'C�digo do �tem da ordem de servi�o.';
COMMENT ON    COLUMN    CRCHAMADO.EMATENDIMENTO IS 'Indica se o chamado est� sendo atendido neste momento.';
COMMENT ON    COLUMN    CRCHAMADO.CODEMPQL IS 'C�digo da empresa da qualifica��o do chamado';
COMMENT ON    COLUMN    CRCHAMADO.CODFILIALQL IS 'C�digo da filial da qualifica��o do chamado.
';
COMMENT ON    COLUMN    CRCHAMADO.CODQUALIFIC IS 'C�digo da qualifica��o do atendimento.
';
COMMENT ON    COLUMN    CRCHAMADOANEXO.CODEMP IS 'C�digo da empresa do anexo';
COMMENT ON    COLUMN    CRCHAMADOANEXO.CODFILIAL IS 'C�digo da filial do anexo;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.CODCHAMADO IS 'C�diugo do chamado;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.CODANEXO IS 'C�digo do anexo;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.DESCANEXO IS 'Descri��o do anexo;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.ANEXO IS 'Arquivo Bin�rio.
';
COMMENT ON    COLUMN    CRCHAMADOANEXO.DTINS IS 'Data de inser��o do registro no banco de dados;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.HINS IS 'Hora de inser��o do registro no banco de dados';
COMMENT ON    COLUMN    CRCHAMADOANEXO.IDUSUINS IS 'Usu�rio que inseriu o registro no banco de dados;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.DTALT IS 'Data de altera��o do registro no banco de dados;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.HALT IS 'Hora de altera��o do registro no banco de dados;';
COMMENT ON    COLUMN    CRCHAMADOANEXO.IDUSUALT IS 'Usu�rio que alterou o registro no banco de dados;';
COMMENT ON TABLE        CRQUALIFICACAO IS 'Tabela para qualifica��o de chamados/atendimentos.';
COMMENT ON    COLUMN    CRQUALIFICACAO.CODEMP IS 'C�digo da empresa.';
COMMENT ON    COLUMN    CRQUALIFICACAO.CODFILIAL IS 'C�digo da filial';
COMMENT ON    COLUMN    CRQUALIFICACAO.CODQUALIFIC IS 'C�digo da qualifica��o';
COMMENT ON    COLUMN    CRQUALIFICACAO.DESCQUALIFIC IS 'Descri��o da qualifica��o';
COMMENT ON    COLUMN    CRQUALIFICACAO.FINALIZA IS 'Indica se a qualifica��o finaliza o processo (chamado/atendimento)
"S" - Sim
"N" - N�o';
COMMENT ON    COLUMN    CRQUALIFICACAO.DTINS IS 'Data de inser��o do registro';
COMMENT ON    COLUMN    CRQUALIFICACAO.HINS IS 'Hora de inser��o do registro';
COMMENT ON    COLUMN    CRQUALIFICACAO.IDUSUINS IS 'Identifica��o do usu�rio que inseriu o registro';
COMMENT ON    COLUMN    CRQUALIFICACAO.DTALT IS 'Data de altere��o do registro';
COMMENT ON    COLUMN    CRQUALIFICACAO.HALT IS 'Hora de altera��o do registro';
COMMENT ON    COLUMN    CRQUALIFICACAO.IDUSUALT IS 'Identifica��o do usu�rio que alterou o registro';
COMMENT ON TABLE        EQALMOX IS 'Tabela de almoxarifados.';
COMMENT ON TABLE        EQALMOXFILIAL IS 'Amarra��o Almoxarifado x Filiais.';
COMMENT ON    COLUMN    EQALMOXFILIAL.CODEMP IS 'C�digo da empresa na tabela EQALMOX.';
COMMENT ON    COLUMN    EQALMOXFILIAL.CODFILIAL IS 'C�digo da filial na tabela EQALMOX.';
COMMENT ON    COLUMN    EQALMOXFILIAL.CODALMOX IS 'C�digo do almoxarifado.';
COMMENT ON    COLUMN    EQALMOXFILIAL.CODEMPAF IS 'C�digo da empresa na tabela SGFILIAL.';
COMMENT ON    COLUMN    EQALMOXFILIAL.CODFILIALAF IS 'C�digo da filial na tabela SGFILIAL.';
COMMENT ON    COLUMN    EQALMOXFILIAL.BAIXAESTOQAF IS 'Flag que indica se � poss�vel baixar o estoque dessa filial.';
COMMENT ON TABLE        EQCLIFOR IS 'Amarra��o Cliente x Fornecedor.';
COMMENT ON TABLE        EQCODALTPROD IS 'Tabela de c�digos alternativos para produtos.';
COMMENT ON    COLUMN    EQFATCONV.TIPOCONV IS 'Indica o tipo de convers�o:
"U" - Apenas converte a unidade no mesmo produto;
"P" - Executa processo de convers�o de produtos atrav�s de estrutura;';
COMMENT ON    COLUMN    EQFATCONV.CPFATCONV IS 'Flag que indica se a unidade � preferencial para compras.';
COMMENT ON    COLUMN    EQFATCONV.CODPRODET IS 'C�digo do produto da estrutura de convers�o.';
COMMENT ON    COLUMN    EQGRUPO.ESTNEGGRUP IS 'Flag para permitir o estoque negativo de produtos.';
COMMENT ON    COLUMN    EQGRUPO.ESTLOTNEGGRUP IS 'Flag que define se permite estoque negativo de lotes.';
COMMENT ON    COLUMN    EQINVPROD.OBSINVP IS 'Observa��es relativas ao invent�rio, como motivo, justificativa, etc.';
COMMENT ON TABLE        EQITRECMERC IS 'M�dia entre as amostragens.';
COMMENT ON    COLUMN    EQITRECMERC.DESCITRECMERC IS 'Descri��o detalhada do item recebido.';
COMMENT ON    COLUMN    EQITRECMERC.NUMSERIE IS 'N�mero de s�rie.
';
COMMENT ON    COLUMN    EQITRECMERC.GARANTIA IS 'Indica se o produto recebido est� na garantia - (S/N)';
COMMENT ON    COLUMN    EQITRECMERC.REFPROD IS 'Refer�ncia do produto
';
COMMENT ON    COLUMN    EQITRECMERC.CODEMPTP IS 'C�digo da empresa do processo de recebimento.';
COMMENT ON    COLUMN    EQITRECMERC.CODFILIALTP IS 'C�digo da filial do processo de recebimento.';
COMMENT ON    COLUMN    EQITRECMERC.CODTIPORECMERC IS 'C�digo do tipo de recebimento de mercadorial.';
COMMENT ON    COLUMN    EQITRECMERC.CODPROCRECMERC IS 'C�digo do processo de recep��o de mercadoria.';
COMMENT ON    COLUMN    EQITRECMERC.MEDIAAMOSTRAGEM IS '0';
COMMENT ON    COLUMN    EQITRECMERC.RENDAAMOSTRAGEM IS 'Renda amostragem *';
COMMENT ON    COLUMN    EQITRECMERC.QTDITRECMERC IS 'Quantidade de itens recebidos (Coleta)
';
COMMENT ON    COLUMN    EQITRECMERC.STATUSITRECMERC IS 'Status do processo de recebimento.
PE - Pendente;
AN - Em analise;
EA - Em andamento;
FN - Finalizado;';
COMMENT ON    COLUMN    EQITRECMERC.DEFEITOITRECMERC IS 'Indica o defeito reclamado do item recebido para concerto.
';
COMMENT ON    COLUMN    EQITRECMERC.CONDICOESITRECMERC IS 'Campo para informar as condicoes do item para concerto no momento da recepcao.
';
COMMENT ON    COLUMN    EQITRECMERC.SERVICOSOLICITRECMERC IS 'Campo para informar o servi�o solicitado pelo cliente no item recebido.
';
COMMENT ON    COLUMN    EQITRECMERC.OBSITRECMERC IS 'Campo para informar os acess�rios que acompanham o item recebido.';
COMMENT ON TABLE        EQITRECMERCITCP IS 'Tabela de vinculo entre �tens de recebimento de mercadoria e itens de compra.';
COMMENT ON TABLE        EQITRECMERCITOS IS 'Tabela para lan�amento de componentes e servi�os utilizando em ordem de produ��o /eqitrecmerc.';
COMMENT ON    COLUMN    EQITRECMERCITOS.CODITOS IS 'C�digo sequencial do item de ordem de servi�o.
';
COMMENT ON    COLUMN    EQITRECMERCITOS.QTDITOS IS 'Quantidade de produtos ou servicos';
COMMENT ON    COLUMN    EQITRECMERCITOS.STATUSITOS IS '"PE" - Item pendente / previsto / Or�ado
"EC" - Encaminhado / Chamado aberto / RMA aberta
"EA" - Em andamento
"CO" - Item Conclu�do;';
COMMENT ON    COLUMN    EQITRECMERCITOS.GERARMA IS 'Indica se deve gerar RMA para o �tem de O.S.';
COMMENT ON    COLUMN    EQITRECMERCITOS.GERACHAMADO IS 'Indica se deve gerar chamado para esse item.';
COMMENT ON    COLUMN    EQITRECMERCITOS.GERANOVO IS 'Indica se o item de OS � um produto novo para substitui��o, 
ao com defeito;';
COMMENT ON    COLUMN    EQITRECMERCITOSITORC.STATUS IS 'Coluna de status para repasse via triggers � tabela de ordem de sevi�o na atualiza��o do status do or�amento.
(Vide status da ordem de sevi�o).';
COMMENT ON TABLE        EQITRECMERCSERIE IS 'Tabela de vinculo entre item de recebimento de mercadoria e seus respectivos numeros de serie."';
COMMENT ON TABLE        EQITRMA IS '�tens de RMA.';
COMMENT ON    COLUMN    EQITRMA.CODEMPAX IS 'C�digo da empresa na tabela de almoxarifados.';
COMMENT ON    COLUMN    EQITRMA.CODFILIALAX IS 'C�digo da filail na tabela de almoxarifados.';
COMMENT ON    COLUMN    EQITRMA.CODALMOX IS 'C�digo do almoxarifado.';
COMMENT ON    COLUMN    EQITRMA.DTAPROVITRMA IS 'Data da aprova��o do item.';
COMMENT ON    COLUMN    EQITRMA.SITITRMA IS 'Situa��o geral do item de rma:
PE - Pendente 
EA - Em andamento
AF - Aprova��o finalizada
EF - Expedi��o finalizada
CA - Cancelado 
';
COMMENT ON    COLUMN    EQITRMA.SITAPROVITRMA IS 'Situa��o da aprova��o do item:
"PE" - Aprova��o pendente
"AP" - Aprova��o parcial
"AT" - Aprova��o total
"NA" - N�o aprovado';
COMMENT ON    COLUMN    EQITRMA.SITEXPITRMA IS 'Situa��o da expedi��o do item:
"PE" - Expedi��o pendente
"EP" - Expedi��o parcial
"ET" - Expedi��o total
"NE" - N�o expedida';
COMMENT ON    COLUMN    EQITRMA.MOTIVOCANCITRMA IS 'Motivo do cancelamento do �tem de rma.';
COMMENT ON    COLUMN    EQITRMA.PRIORITRMA IS 'Prioridade da RMA.
(B-Baixa, M-Media, A-Alta).
';
COMMENT ON    COLUMN    EQITRMA.MOTIVOPRIORITRMA IS 'Motivo de prioridade para item de RMA.
';
COMMENT ON    COLUMN    EQITRMA.CODEMPOS IS 'C�digo da empresa da ordem de servi�o vinculada.';
COMMENT ON    COLUMN    EQITRMA.CODFILIALOS IS 'C�digo da filial da ordem de servi�o vinculada.';
COMMENT ON    COLUMN    EQITRMA.TICKET IS 'Ticket da ordem de servi�o vinculada.';
COMMENT ON    COLUMN    EQITRMA.CODITRECMERC IS 'Item de recebimento da ordem de servi�o vinculada';
COMMENT ON    COLUMN    EQITRMA.CODITOS IS 'Item de suplemento da ordem de servi�o vinculada.';
COMMENT ON    COLUMN    EQLOTE.QTDPRODLOTE IS 'Quantidade de produtos no lote (nfe - medicamentos)';
COMMENT ON    COLUMN    EQLOTE.DIASAVISOLOTE IS 'Indica quantos dias antes do vencimento do lote deve emitir aviso.';
COMMENT ON TABLE        EQMODLOTE IS 'Tabela de modelos de lotes, para gera��o automatica de lotes a partir da Ordem de produ��o.';
COMMENT ON    COLUMN    EQMODLOTE.CODEMP IS 'C�digo da empresa do modelo de lote.';
COMMENT ON    COLUMN    EQMODLOTE.CODFILIAL IS 'Filial do modelo de lote.';
COMMENT ON    COLUMN    EQMODLOTE.CODMODLOTE IS 'C�digo do modelo de lote.';
COMMENT ON    COLUMN    EQMODLOTE.DESCMODLOTE IS 'Descri��o do modelo de lote.';
COMMENT ON    COLUMN    EQMOVPROD.CODEMPAX IS 'C�digo da empresa na tabela de almoxarifados.';
COMMENT ON    COLUMN    EQMOVPROD.CODFILIALAX IS 'C�digo da filial na tabela de almoxarifados.';
COMMENT ON    COLUMN    EQMOVPROD.CODALMOX IS 'C�digo do almoxarifado.';
COMMENT ON    COLUMN    EQMOVPROD.CODEMPOP IS 'C�digo da empresa da ordem de produ��o.';
COMMENT ON    COLUMN    EQMOVPROD.CODFILIALOP IS 'Codigo da filial da Ordem de produ��o.';
COMMENT ON    COLUMN    EQMOVPROD.CODOP IS 'C�digo da Ordem de produ��o.';
COMMENT ON    COLUMN    EQMOVPROD.SEQOP IS 'N�mero sequencial da OP.';
COMMENT ON    COLUMN    EQMOVPROD.SEQENTOP IS 'Sequencial de entrada da ordem de produ��o ';
COMMENT ON    COLUMN    EQMOVPROD.SEQSUBPROD IS 'Sequencial de subproduto.
';
COMMENT ON TABLE        EQMOVSERIE IS 'Tabela de controle de movimenta��es de nemeros de s�rie de produtos.';
COMMENT ON    COLUMN    EQMOVSERIE.CODMOVSERIE IS 'C�digo da movimenta��o da s�rie do produto.';
COMMENT ON    COLUMN    EQMOVSERIE.CODPROD IS 'C�digo do produto.';
COMMENT ON    COLUMN    EQMOVSERIE.NUMSERIE IS 'N�mero de s�rie.';
COMMENT ON    COLUMN    EQMOVSERIE.CODVENDA IS 'C�digo da venda';
COMMENT ON    COLUMN    EQMOVSERIE.CODCOMPRA IS 'C�digo da compra.';
COMMENT ON    COLUMN    EQMOVSERIE.CODINVPROD IS 'C�digo do invent�rio.';
COMMENT ON    COLUMN    EQMOVSERIE.DTMOVSERIE IS 'Data da movimenta��o.';
COMMENT ON    COLUMN    EQMOVSERIE.TIPOMOVSERIE IS 'Quantidade movimentada:
1 - Entrada em estoque;
0 - Sem movimenta��o de estoque;
-1 - Sa�da de estoque;';
COMMENT ON    COLUMN    EQMOVSERIE.CODLOTE IS 'C�digo do lote do produto.
';
COMMENT ON    COLUMN    EQMOVSERIE.CODEMPLE IS 'C�digo da empresa do lote.
';
COMMENT ON    COLUMN    EQMOVSERIE.CODFILIALLE IS 'Codigo da filial do lote.
';
COMMENT ON    COLUMN    EQMOVSERIE.CODALMOX IS 'C�digo do almoxarifado.';
COMMENT ON    COLUMN    EQMOVSERIE.CODEMPAX IS 'C�digo da empresa do almoxarifado.';
COMMENT ON    COLUMN    EQMOVSERIE.CODFILIALAX IS 'C�digo da filial do almoxarifado.';
COMMENT ON    COLUMN    EQMOVSERIE.CODEMPRC IS 'C�digo da empresa do recebimento de mercadoria.';
COMMENT ON    COLUMN    EQMOVSERIE.CODFILIALRC IS 'C�digo da filial do recebimento de mercadoria.
';
COMMENT ON    COLUMN    EQMOVSERIE.TICKET IS 'C�digo do recebimento de mercadoria.';
COMMENT ON    COLUMN    EQMOVSERIE.CODITRECMERC IS 'C�digo do �tem de recebimento de mercadoria.';
COMMENT ON    COLUMN    EQPROCRECMERC.TIPOPROCRECMERC IS 'PI - Pesagem inicial
TR - Descarregamento/tiragem de renda
PF - Pesagem final';
COMMENT ON    COLUMN    EQPROCRECMERC.NROAMOSTPROCRECMERC IS 'Indica o numero de amostras necess�rias para finalizar o processo.';
COMMENT ON TABLE        EQPRODACESSO IS 'Tabela de controle de acesso do produto para RMA, PDV e outras implementa��es futuras.';
COMMENT ON    COLUMN    EQPRODACESSO.CODEMP IS 'C�digo da empresa.';
COMMENT ON    COLUMN    EQPRODACESSO.CODFILIAL IS 'C�digo da filial.';
COMMENT ON    COLUMN    EQPRODACESSO.CODPROD IS 'C�digo do produto.';
COMMENT ON    COLUMN    EQPRODACESSO.TIPOPA IS 'Tipo do acesso (RMA, PDV).
';
COMMENT ON    COLUMN    EQPRODACESSO.CODPA IS 'C�digo do acesso.';
COMMENT ON    COLUMN    EQPRODACESSO.CODEMPCC IS 'C�digo da empresa na tabela de centro de custos.';
COMMENT ON    COLUMN    EQPRODACESSO.CODFILIALCC IS 'C�digo da filial na tabela de centro de custos.';
COMMENT ON    COLUMN    EQPRODACESSO.ANOCC IS 'Ano do centro de custo.';
COMMENT ON    COLUMN    EQPRODACESSO.CODCC IS 'C�digo do centro de custo.';
COMMENT ON    COLUMN    EQPRODACESSO.CODEMPCX IS 'C�digo da empresa na tabela de caixa ECF.';
COMMENT ON    COLUMN    EQPRODACESSO.CODFILIALCX IS 'C�digo da filial na tabela de caixa ECF.';
COMMENT ON    COLUMN    EQPRODACESSO.CODCAIXA IS 'C�digo do caixa ECF.';
COMMENT ON    COLUMN    EQPRODACESSO.DTINS IS 'Data de inser��o.';
COMMENT ON    COLUMN    EQPRODACESSO.HINS IS 'Hor�rio de inser��o.';
COMMENT ON    COLUMN    EQPRODACESSO.IDUSUINS IS 'ID. do usu�rio que inseriu o registro.';
COMMENT ON    COLUMN    EQPRODACESSO.DTALT IS 'Data da �ltima altera��o.';
COMMENT ON    COLUMN    EQPRODACESSO.HALT IS 'Hora da �ltima altera��o.';
COMMENT ON    COLUMN    EQPRODACESSO.IDUSUALT IS 'ID. do usu�rio que fez a �ltima altera��o.';
COMMENT ON TABLE        EQPRODPLAN IS 'Tabela de v�nculo produto x planejamento, para uso nos rateios.';
COMMENT ON    COLUMN    EQPRODPLAN.CODEMP IS 'C�digo da empresa na tabela EQPRODUTO';
COMMENT ON    COLUMN    EQPRODPLAN.CODFILIAL IS 'C�digo da filial na tabela EQPRODUTO';
COMMENT ON    COLUMN    EQPRODPLAN.CODPROD IS 'C�digo do produto.';
COMMENT ON    COLUMN    EQPRODPLAN.SEQPP IS 'N�mero sequencial.';
COMMENT ON    COLUMN    EQPRODPLAN.TIPOPP IS 'Tipo (R-Receita / D-Despesa).';
COMMENT ON    COLUMN    EQPRODPLAN.CODEMPPN IS 'C�digo da empresa na tabela EQPLANEJAMENTO.';
COMMENT ON    COLUMN    EQPRODPLAN.CODFILIALPN IS 'C�digo da filial na tabela EQPLANEJAMENTO.';
COMMENT ON    COLUMN    EQPRODPLAN.CODPLAN IS 'C�digo do planejamento.';
COMMENT ON    COLUMN    EQPRODPLAN.CODEMPCC IS 'C�digo da empresa na tabela FNCC.';
COMMENT ON    COLUMN    EQPRODPLAN.CODFILIALCC IS 'C�digo da filial na tabela FNCC.';
COMMENT ON    COLUMN    EQPRODPLAN.ANOCC IS 'Ano do centro de custo.';
COMMENT ON    COLUMN    EQPRODPLAN.CODCC IS 'C�digo do centro de custo.';
COMMENT ON    COLUMN    EQPRODPLAN.PERCPP IS 'Percentual de rateio.';
COMMENT ON    COLUMN    EQPRODPLAN.DTINS IS 'Data de inser��o.';
COMMENT ON    COLUMN    EQPRODPLAN.HINS IS 'Hora de inser��o.';
COMMENT ON    COLUMN    EQPRODPLAN.IDUSUINS IS 'ID do usu�rio que inseriu.';
COMMENT ON    COLUMN    EQPRODPLAN.DTALT IS 'Data da �ltima altera��o.';
COMMENT ON    COLUMN    EQPRODPLAN.HALT IS 'Hora da �ltima altera��o.';
COMMENT ON    COLUMN    EQPRODPLAN.IDUSUALT IS 'Id do usu�rio da �ltima altera��o.';
COMMENT ON    COLUMN    EQPRODUTO.TIPOPROD IS 'Tipo de produto (campo "fluxo" do cadastro de produtos)
P  - Mercadoria p/revenda
S  - Servi�o
E  - Equipamento
F  - Produto acabado
M  - Mat�ria prima
O  - Ativo imobilizado
C  - Material de Consumo
02 - Embalagem
03 - Em processo
05 - SubProduto
06 - Produto Intermedi�rio
10 - Outros insumos
99 - Outros';
COMMENT ON    COLUMN    EQPRODUTO.CVPROD IS 'Indica se o produto e de compra ou venda:
V - Venda
C - Compra
A - Ambos';
COMMENT ON    COLUMN    EQPRODUTO.CLOTEPROD IS 'Indica se o produto � controlado em lotes.';
COMMENT ON    COLUMN    EQPRODUTO.SERIEPROD IS 'Indica se ser� exigido o n�mero de s�rie para o produto.';
COMMENT ON    COLUMN    EQPRODUTO.CUSTOINFOPROD IS 'Custo informado do produto. Pode incluir os custos de aquisi��o, tributos, e rateio de custos fixo.';
COMMENT ON    COLUMN    EQPRODUTO.PRECOBASEPROD IS 'Pre�o base do produto (primeiro pre�o de venda) base para gera��o de outras tabelas de pre�o.';
COMMENT ON    COLUMN    EQPRODUTO.PRECOCOMISPROD IS 'Pre�o base do produto para c�lculo de comiss�es especiais.';
COMMENT ON    COLUMN    EQPRODUTO.VERIFPROD IS '"S" - Exige senha para venda abaixo de custo deste produto
"N" - Bloqueado (depende de parametro nas preferencias)
"L" - Liberado sem senha para o produto.';
COMMENT ON    COLUMN    EQPRODUTO.LOCALPROD IS 'Local de armazenamento do produto (Prateleira)
';
COMMENT ON    COLUMN    EQPRODUTO.RMAPROD IS 'Flag que indica se podem ser feitas RMA�s do produto.';
COMMENT ON    COLUMN    EQPRODUTO.CODEMPPE IS 'C�digo da empresa do prazo de entrega padr�o.';
COMMENT ON    COLUMN    EQPRODUTO.CODFILIALPE IS 'C�digo da filial do prazo de entrega padr�o.';
COMMENT ON    COLUMN    EQPRODUTO.CODPE IS 'C�digo do prazo de entrega.';
COMMENT ON    COLUMN    EQPRODUTO.USARECEITAPROD IS 'Flag indicando se o produto nescessita de receita.';
COMMENT ON    COLUMN    EQPRODUTO.USATELAADICPDV IS 'Flag indicando se o produto abre uma tela para informa��es adicionais na venda do PDV';
COMMENT ON    COLUMN    EQPRODUTO.VLRDENSIDADE IS 'Valor da densidade';
COMMENT ON    COLUMN    EQPRODUTO.VLRCONCENT IS 'Valor da concentra��o.';
COMMENT ON    COLUMN    EQPRODUTO.COMPRIMENTO IS 'Comprimento do produto em cm.';
COMMENT ON    COLUMN    EQPRODUTO.LARGURA IS 'Largura do produto em cm.';
COMMENT ON    COLUMN    EQPRODUTO.ESPESSURA IS 'Espessura do produto em cm.';
COMMENT ON    COLUMN    EQPRODUTO.QTDEMBALAGEM IS 'Quantidade unitaria contida na embalagem.';
COMMENT ON    COLUMN    EQPRODUTO.CODEANPROD IS 'GTIN-8,GTIN-12,GTIN-13 ou GTIN-14';
COMMENT ON    COLUMN    EQPRODUTO.CODEMPSC IS 'C�digo da empresa da se��o de produ��o/estoque.';
COMMENT ON    COLUMN    EQPRODUTO.CODFILIALSC IS 'C�digo da filial da se��o de produ��o/estoque.';
COMMENT ON    COLUMN    EQPRODUTO.CODSECAO IS 'C�digo da sec�o de produ��o/estoque.';
COMMENT ON    COLUMN    EQPRODUTO.CODEMPTC IS 'C�digo da empresa do tipo de chamado vinculado ao servi�o (Integra��o CRM/GMS)';
COMMENT ON    COLUMN    EQPRODUTO.CODFILIALTC IS 'C�digo da filial do tipo de chamado vinculado ao servi�o (Integra��o CRM/GMS)';
COMMENT ON    COLUMN    EQPRODUTO.CODTPCHAMADO IS 'C�digo do tipo de chamado vinculado ao servi�o (Integra��o CRM/GMS)';
COMMENT ON    COLUMN    EQPRODUTO.QTDHORASSERV IS 'Quantidade de horas padr�o para execu��o do servi�o (Integra�ao CRM/GMS)';
COMMENT ON    COLUMN    EQPRODUTO.NRODIASVALID IS 'N�mero de dias para validade do produto.';
COMMENT ON    COLUMN    EQPRODUTO.DESCCLI IS 'Indica se deve aplicar o desconto padr�o do cliente.';
COMMENT ON    COLUMN    EQPRODUTO.QTDPORPLANO IS 'Indica a quantidade de produto acabado por plano da folha (industria gr�fica/etiquetas)';
COMMENT ON    COLUMN    EQPRODUTO.NROPLANOS IS 'Indica o n�mero de planos por folha (industria gr�fica/etiquetas)';
COMMENT ON    COLUMN    EQPRODUTO.CERTFSC IS 'Indica se o produto participa da Certifica��o FSC.
Forest Stewardship Council (Conselho de Manejo Florestal).
"S" - Sim
"N" - N�o
';
COMMENT ON    COLUMN    EQPRODUTO.FATORFSC IS 'Fator para calculo dos relat�rios FSC em folhas.
';
COMMENT ON    COLUMN    EQPRODUTOLOG.CODEMP IS 'C�digo da empresa';
COMMENT ON    COLUMN    EQPRODUTOLOG.CODFILIAL IS 'C�digo da filial';
COMMENT ON    COLUMN    EQPRODUTOLOG.CODPROD IS 'C�digo do produto';
COMMENT ON    COLUMN    EQPRODUTOLOG.PRECOBASEPRODANT IS 'Preco base anterior.';
COMMENT ON    COLUMN    EQPRODUTOLOG.PRECOBASEPRODNOVO IS 'Novo pre�o base.';
COMMENT ON    COLUMN    EQPRODUTOLOG.SEQLOG IS 'Sequ�ncia do log';
COMMENT ON    COLUMN    EQPRODUTOLOG.PRECOPROC IS 'Indica se o pre�o base j� foi processado (tabelas de pre�o)';
COMMENT ON    COLUMN    EQPRODUTOLOG.DTINS IS 'Data de inser��o do registro';
COMMENT ON    COLUMN    EQPRODUTOLOG.HINS IS 'Hora de inser��o do registro';
COMMENT ON    COLUMN    EQPRODUTOLOG.IDUSUINS IS 'Usu�rio que inseriu o registro';
COMMENT ON    COLUMN    EQPRODUTOLOG.DTALT IS 'Data de altera��o do registro';
COMMENT ON    COLUMN    EQPRODUTOLOG.HALT IS 'Hora de altera��o do registro';
COMMENT ON    COLUMN    EQPRODUTOLOG.IDUSUALT IS 'Usu�rio que alterou o registro.';
COMMENT ON    COLUMN    EQRECAMOSTRAGEM.PESOAMOST IS 'Peso 1 (Pesagem comum)';
COMMENT ON    COLUMN    EQRECAMOSTRAGEM.PESOAMOST2 IS 'Peso 2 (Pesagem em balan�a hidrost�tica)';
COMMENT ON    COLUMN    EQRECAMOSTRAGEM.SEQAMOSTRAGEM IS 'N�mero sequencia para controle das pesagens.';
COMMENT ON TABLE        EQRECMERC IS 'Tabela de registro de informa��es do recebimento de cargas de mercadorias.';
COMMENT ON    COLUMN    EQRECMERC.TICKET IS 'Ticket de entrada do recebimento ou coleta.';
COMMENT ON    COLUMN    EQRECMERC.REFPROD IS 'Refer�ncia do produto';
COMMENT ON    COLUMN    EQRECMERC.CODEMPTR IS 'C�digo da empresa do tipo de recep��o de mercadorias.';
COMMENT ON    COLUMN    EQRECMERC.CODFILIALTR IS 'C�digo da filial to tipo de recep��o de mercadorias.';
COMMENT ON    COLUMN    EQRECMERC.CODTIPORECMERC IS 'C�digo do tipo de recep��o de mercadorias.';
COMMENT ON    COLUMN    EQRECMERC.STATUS IS 'Status do recebimento da mercadoria.
PE - Pendente;
AN - Em an�lise (OS)
EA - Em andamento (OS)
PT - Pronto (OS)
EO - Em or�amento (entrada para concerto);
CA - Cancelada;
OA - Or�amento aprovado (entrada para concerto);
E1 - Em andamento finalizada primeira etapa;
E2 - Em andamento finalizada segunda etapa;
FN - Finalizado;
PC - Pedido de compra emitido;
NE - Nota de entrada emitida;';
COMMENT ON    COLUMN    EQRECMERC.TIPOFRETE IS 'Tipo de frete:
Na compra:
C - CIF frete por conta sua conta.
F - FOB frete por conta do fornecedor.';
COMMENT ON    COLUMN    EQRECMERC.DTENT IS 'Data da entrada da coleta.';
COMMENT ON    COLUMN    EQRECMERC.DTPREVRET IS 'Data prevista para retorno da mercadoria (concerto)';
COMMENT ON    COLUMN    EQRECMERC.DOCRECMERC IS 'N�mero do documento (nota de entrada)';
COMMENT ON    COLUMN    EQRECMERC.CODEMPVD IS 'C�digo da empresa do vendedor (coleta)';
COMMENT ON    COLUMN    EQRECMERC.CODFILIALVD IS 'C�digo da filial do vendedor (coleta)';
COMMENT ON    COLUMN    EQRECMERC.CODVEND IS 'C�digo do vendedor (coleta)';
COMMENT ON    COLUMN    EQRECMERC.CODEMPCL IS 'C�digo da empresa do cliente (coleta)
';
COMMENT ON    COLUMN    EQRECMERC.CODFILIALCL IS 'C�digo da filial do cliente (coleta)
';
COMMENT ON    COLUMN    EQRECMERC.CODCLI IS 'C�digo do cliente (coleta)
';
COMMENT ON    COLUMN    EQRECMERC.SOLICITANTE IS 'Nome do solicitante do servi�o/entrega da mercadoria.';
COMMENT ON    COLUMN    EQRECMERC.MEDIAAMOSTRAGEM IS 'Media geral da amostragem.';
COMMENT ON    COLUMN    EQRECMERC.RENDAAMOSTRAGEM IS 'Renda media geral da amostragem';
COMMENT ON    COLUMN    EQRECMERC.CODEMPAX IS 'C�digo da empresa do almoxarifado de descarregamento da mercadoria.';
COMMENT ON    COLUMN    EQRECMERC.CODFILIALAX IS 'C�digo da filial do almoxarifado de descarregamento da mercadoria.';
COMMENT ON    COLUMN    EQRECMERC.CODALMOX IS 'C�digo do almoxarifado de descarregamento da mercadoria.';
COMMENT ON    COLUMN    EQRECMERC.OBSRECMERC IS 'Observa��es.
';
COMMENT ON    COLUMN    EQRECMERC.DESCONTO IS 'Percentual de desconto ao peso total do recebimento da mercadoria.
';
COMMENT ON    COLUMN    EQRMA.MOTIVORMA IS 'Motivo da solicita��o.';
COMMENT ON    COLUMN    EQRMA.MOTIVOCANCRMA IS 'Motivo do cancelamento.';
COMMENT ON    COLUMN    EQRMA.SITRMA IS 'Situa��o geral da rma:
PE - Pendente (n�o foi realizado nenhum procedimento de aprova��o, ou expedi��o)
EA - Em andamento
AF - Aprova��o finalizada
EF - Expedi��o finalizada
CA - Cancelada (requisi��o foi cancelada)
';
COMMENT ON    COLUMN    EQRMA.DTAREQRMA IS 'Data da requisi��o.';
COMMENT ON    COLUMN    EQRMA.DTAEXPRMA IS 'Data da expedi��o da RMA.';
COMMENT ON    COLUMN    EQRMA.SITAPROVRMA IS 'Status da aprova��o da RMA.
"PE" - Aprova��o pendente.
"AP" - Aprova��o parcial.
"AT" - Aprova��o total.
"NA" - N�o aprovada.';
COMMENT ON    COLUMN    EQRMA.SITEXPRMA IS 'Status da expedi��o da RMA.
"PE" - Expedi��o pendente.
"EP" - Expedida parcial.
"ET" - Expedida total.
"NE" - N�o expedida.';
COMMENT ON    COLUMN    EQRMA.IDUSUAPROV IS 'ID do usu�rio que aprovou/aprovar� a RMA.';
COMMENT ON    COLUMN    EQRMA.CODEMPUA IS 'C�digo da empresa do usu�rio que aprovou/aprovar� a RMA.';
COMMENT ON    COLUMN    EQRMA.CODFILIALUA IS 'C�digo da filial do usu�rio que aprovou/aprovar� a RMA.';
COMMENT ON    COLUMN    EQRMA.IDUSUEXP IS 'ID do usu�rio que expediu/expedir� a RMA.';
COMMENT ON    COLUMN    EQRMA.CODEMPUE IS 'C�digo da empresa do usu�rio que expediu/expedir� a RMA.';
COMMENT ON    COLUMN    EQRMA.CODFILIALUE IS 'C�digo da filial do usu�rio que expediu/expedir� a RMA.';
COMMENT ON    COLUMN    EQRMA.DTAAPROVRMA IS 'Data da aprova��o da RMA.';
COMMENT ON    COLUMN    EQRMA.CODEMPOF IS 'C�digo da empresa a OP/Fase';
COMMENT ON    COLUMN    EQRMA.CODFILIALOF IS 'C�digo da filial da OP X Fase';
COMMENT ON    COLUMN    EQRMA.CODOP IS 'C�digo da ordem de produ��o.';
COMMENT ON    COLUMN    EQRMA.SEQOP IS 'N�mero sequencial da OP.';
COMMENT ON    COLUMN    EQRMA.SEQOF IS 'Sequencial da fase de produ��o.';
COMMENT ON    COLUMN    EQRMA.CODEMPCT IS 'C�digo da empresa do contrato/projeto';
COMMENT ON    COLUMN    EQRMA.CODFILIALCT IS 'C�digo da filial da empresa/contrato';
COMMENT ON    COLUMN    EQRMA.CODCONTR IS 'C�digo do contrato/projeto';
COMMENT ON    COLUMN    EQRMA.CODITCONTR IS 'C�digo do �tem de contrato/projeto';
COMMENT ON    COLUMN    EQRMA.CODEMPOS IS 'Codigo da empresa da ordem de servi�o.';
COMMENT ON    COLUMN    EQRMA.CODFILIALOS IS 'C�digo da filial da ordem de servi�o.';
COMMENT ON    COLUMN    EQRMA.TICKET IS 'N�mero do ticket da ordem de servi�o vinculada.';
COMMENT ON    COLUMN    EQRMA.CODITRECMERC IS 'C�digo do �tem de recebimento de mercadoria (Ordem de servi�o)';
COMMENT ON TABLE        EQSALDOLOTE IS 'Tabelas de saldos por lote e almoxarifado.';
COMMENT ON TABLE        EQSECAO IS 'Tabela de se��es de produ��o/estoque.';
COMMENT ON TABLE        EQSERIE IS 'Tabela para controle de n�meros de s�rie de produtos.';
COMMENT ON    COLUMN    EQSERIE.DTFABRICSERIE IS 'Data de fabrica��o do produto';
COMMENT ON    COLUMN    EQSERIE.DTVALIDSERIE IS 'Data de validade do produto';
COMMENT ON    COLUMN    EQSERIE.OBSSERIE IS 'Observa��es
';
COMMENT ON    COLUMN    EQTIPOMOV.ESTOQTIPOMOV IS 'Indica se o tipo de movimento movimenta estoque.';
COMMENT ON    COLUMN    EQTIPOMOV.IMPPEDTIPOMOV IS 'Flag que indica se imprime pedido.';
COMMENT ON    COLUMN    EQTIPOMOV.IMPNFTIPOMOV IS 'Flag que indica se imprime NF.';
COMMENT ON    COLUMN    EQTIPOMOV.IMPBOLTIPOMOV IS 'Flag que indica se imprime boleto.';
COMMENT ON    COLUMN    EQTIPOMOV.IMPRECTIPOMOV IS 'Flag que indica se imprime recibo no fechamento da venda.';
COMMENT ON    COLUMN    EQTIPOMOV.REIMPNFTIPOMOV IS 'Flag que indica se reimprime NF.';
COMMENT ON    COLUMN    EQTIPOMOV.TUSUTIPOMOV IS 'Flag que indica se todos os usu�rios podem utilizar o tipo de movimento.';
COMMENT ON    COLUMN    EQTIPOMOV.SOMAVDTIPOMOV IS 'Indica se as movimenta��es geradas com esse tipo de movimento devem aparecer nos relat�rios de venda.';
COMMENT ON    COLUMN    EQTIPOMOV.SEQNFTIPOMOV IS 'Flag que indica aloca��o do n�mero da nota fiscal.
';
COMMENT ON    COLUMN    EQTIPOMOV.VLRMFINTIPOMOV IS 'Permite valor financeiro menor que total da nota.';
COMMENT ON    COLUMN    EQTIPOMOV.MCOMISTIPOMOV IS 'Indica se o sistema de comiss�es � com m�ltiplos comissionados.';
COMMENT ON    COLUMN    EQTIPOMOV.OPERTIPOMOV IS 'Opera��o:
"C" - Conjugada;
"P" - Produto;
"S" - Servi�o;
';
COMMENT ON    COLUMN    EQTIPOMOV.CTIPOFRETE IS 'flag de preferencia para o tipo de frete na venda com este tipo de movimento.';
COMMENT ON    COLUMN    EQTIPOMOV.CODEMPTN IS 'C�digo da empresa da transportadora padr�o para o tipo de movimento.';
COMMENT ON    COLUMN    EQTIPOMOV.CODFILIALTN IS 'C�digo da filial da transportadora padr�o para o tipo de movimento.';
COMMENT ON    COLUMN    EQTIPOMOV.CODTRAN IS 'C�digo da transportadora padr�o para o tipo de movimento.';
COMMENT ON    COLUMN    EQTIPOMOV.EMITNFCPMOV IS 'Indica se a nota ser� digitada ou emitida na entrada.';
COMMENT ON    COLUMN    EQTIPOMOV.CODEMPDF IS 'Codigo da empresa do modelo de documento fiscal.';
COMMENT ON    COLUMN    EQTIPOMOV.CODFILIALDF IS 'Codigo da filial do modelo de documento fiscal.';
COMMENT ON    COLUMN    EQTIPOMOV.CODMODDOCFISC IS 'Codigo do modelo do documento fiscal.';
COMMENT ON    COLUMN    EQTIPOMOV.CODEMPPP IS 'C�digo da empresa para o plano de pagamento preferencial
';
COMMENT ON    COLUMN    EQTIPOMOV.CODFILIALPP IS 'C�digo da filial para o plano de pagamento preferencial
';
COMMENT ON    COLUMN    EQTIPOMOV.CODPLANOPAG IS 'C�digo do plano de pagamento preferencial
';
COMMENT ON TABLE        EQTIPOMOVUSU IS 'Tabela de v�nculo de tipos de movimento com usu�rios.';
COMMENT ON    COLUMN    EQTIPOMOVUSU.CODEMP IS 'C�digo da empresa na tabela de tipos de movimento.';
COMMENT ON    COLUMN    EQTIPOMOVUSU.CODFILIAL IS 'C�digo da filial na tabela de tipos de movimento.';
COMMENT ON    COLUMN    EQTIPOMOVUSU.CODTIPOMOV IS 'C�digo do tipo de movimento.';
COMMENT ON    COLUMN    EQTIPOMOVUSU.CODEMPUS IS 'C�digo de empresa na tabela de usu�rios.';
COMMENT ON    COLUMN    EQTIPOMOVUSU.CODFILIALUS IS 'C�digo da filial na tabela de usu�rios.';
COMMENT ON    COLUMN    EQTIPOMOVUSU.IDUSU IS 'ID do usu�rio.';
COMMENT ON TABLE        EQTIPORECMERC IS 'Tabela de tipos de recebimento de mercadorias.';
COMMENT ON    COLUMN    EQTIPORECMERC.TIPORECMERC IS 'Tipo de recep��o de mercadoria:
"RP" - Recebimento com pesagem;
"CM" - Coleta de materiais;
';
COMMENT ON    COLUMN    EQTIPORECMERC.CODEMPTC IS 'Codigo da empresa do tipo de movimento para compra.';
COMMENT ON    COLUMN    EQTIPORECMERC.CODFILIALTC IS 'Codigo da filial para o tipo de movimento para compra.';
COMMENT ON    COLUMN    EQTIPORECMERC.CODTIPOMOVCP IS 'Codigo do tipo de movimento para compra.';
COMMENT ON    COLUMN    EQTIPORECMERC.CODEMPPD IS 'C�digo da empresa do produto padr�o para recebimento de mercadorias.';
COMMENT ON    COLUMN    EQTIPORECMERC.CODFILIALPD IS 'C�digo da filial do produto padr�o para recebimeno de mercadorias.';
COMMENT ON    COLUMN    EQTIPORECMERC.CODPROD IS 'C�digo do produto padr�o para recebimento de mercadorias.';
COMMENT ON    COLUMN    EQUNIDADE.CASASDEC IS 'N�mero de casas decimais utilizadas para a unidade.';
COMMENT ON    COLUMN    EQUNIDADE.CALCVOLEMB IS 'Indica se deve calcular o numero de volumes baseado na quantidade por embalagem.';
COMMENT ON TABLE        FNBANCO IS 'Bancos.';
COMMENT ON    COLUMN    FNBANCO.DVBANCO IS 'D�gito verificador do banco.';
COMMENT ON    COLUMN    FNBANCO.LAYOUTCHEQBANCO IS 'Layout de impress�o de cheques.';
COMMENT ON    COLUMN    FNBORDERO.STATUSBOR IS 'Status do bordero:
BA - Aberto
BC - Completo
BF - Finalizado';
COMMENT ON TABLE        FNCARTCOB IS 'Carteiras de cobran�a.';
COMMENT ON    COLUMN    FNCARTCOB.CODEMPBO IS 'C�digo da empresa na tabela FNBANCO.';
COMMENT ON    COLUMN    FNCARTCOB.CODFILIALBO IS 'C�digo da filial na tabela FNBANCO.';
COMMENT ON    COLUMN    FNCARTCOB.CODBANCO IS 'C�digo do banco.';
COMMENT ON    COLUMN    FNCARTCOB.CODEMP IS 'C�digo da empresa.';
COMMENT ON    COLUMN    FNCARTCOB.CODFILIAL IS 'C�digo da filial.';
COMMENT ON    COLUMN    FNCARTCOB.CODCARTCOB IS 'C�digo da carteira de cobran�a.';
COMMENT ON    COLUMN    FNCARTCOB.VARIACAOCARTCOB IS 'Varia��o da carteira de cobran�a.';
COMMENT ON    COLUMN    FNCARTCOB.DESCCARTCOB IS 'Descri��o da carteira de cobran�a.';
COMMENT ON    COLUMN    FNCARTCOB.CARTCOBCNAB IS 'Carteira de cobran�a para CNAB.';
COMMENT ON TABLE        FNCHEQUE IS 'Tabela de cheques.';
COMMENT ON    COLUMN    FNCHEQUE.CODEMP IS 'C�digo da empresa';
COMMENT ON    COLUMN    FNCHEQUE.CODFILIAL IS 'C�digo da filial';
COMMENT ON    COLUMN    FNCHEQUE.CODEMPBO IS 'C�digo empresa na tabela banco.';
COMMENT ON    COLUMN    FNCHEQUE.SEQCHEQ IS 'Sequencia.';
COMMENT ON    COLUMN    FNCHEQUE.CODFILIALBO IS 'C�digo da filial na tabela banco.';
COMMENT ON    COLUMN    FNCHEQUE.CODBANC IS 'C�digo do banco.';
COMMENT ON    COLUMN    FNCHEQUE.AGENCIACHEQ IS 'Ag�ncia.';
COMMENT ON    COLUMN    FNCHEQUE.CONTACHEQ IS 'N�mero da conta
';
COMMENT ON    COLUMN    FNCHEQUE.NUMCHEQ IS 'N�mero do cheque.';
COMMENT ON    COLUMN    FNCHEQUE.NOMEEMITCHEQ IS 'Nome do emitente.';
COMMENT ON    COLUMN    FNCHEQUE.NOMEFAVCHEQ IS 'Nome do favorecido.';
COMMENT ON    COLUMN    FNCHEQUE.DTEMITCHEQ IS 'Emiss�o';
COMMENT ON    COLUMN    FNCHEQUE.DTVENCTOCHEQ IS 'Data de vencimento.';
COMMENT ON    COLUMN    FNCHEQUE.DTCOMPCHEQ IS 'Data de compensa��o';
COMMENT ON    COLUMN    FNCHEQUE.TIPOCHEQ IS 'Tipo de cheque.
PF - Pagamento de fornecedor;
RC - Recebimento de cliente.';
COMMENT ON    COLUMN    FNCHEQUE.PREDATCHEQ IS 'Cheque pr�-datado (S/N)
';
COMMENT ON    COLUMN    FNCHEQUE.SITCHEQ IS 'Situa��o do cheque.
CA - Cadastrado;
ED - Emitido;
CD - Compensado;
DV - Devolvido.
';
COMMENT ON    COLUMN    FNCHEQUE.VLRCHEQ IS 'Valor do cheque';
COMMENT ON    COLUMN    FNCHEQUE.HISTCHEQ IS 'Hist�rico.';
COMMENT ON    COLUMN    FNCHEQUE.DDDFAVCHEQ IS 'DDD do favorecido do cheque';
COMMENT ON    COLUMN    FNCHEQUE.DDDEMITCHEQ IS 'DDD Tel. emitente.
';
COMMENT ON    COLUMN    FNCHEQUE.FONEEMITCHEQ IS 'Telefone emitente.';
COMMENT ON    COLUMN    FNCHEQUE.FONEFAVCHEQ IS 'Telefone do favorecido';
COMMENT ON    COLUMN    FNCHEQUE.DTINS IS 'Data de inser��o.';
COMMENT ON    COLUMN    FNCHEQUE.HINS IS 'Hora de inser��o.';
COMMENT ON    COLUMN    FNCHEQUE.IDUSUINS IS 'Ident. do usu�rio que inseriu.';
COMMENT ON    COLUMN    FNCHEQUE.DTALT IS 'Data de altera��o.
';
COMMENT ON    COLUMN    FNCHEQUE.HALT IS 'Hora de altera��o.';
COMMENT ON    COLUMN    FNCHEQUE.IDUSUALT IS 'Ident. usu�rio que alterou.';
COMMENT ON    COLUMN    FNCONTA.TIPOCONTA IS 'Indica o tipo de conta:
"C" - Baixa
"B" - Bancos';
COMMENT ON    COLUMN    FNCONTA.CODEMPHP IS 'C�digo da empresa na tabela de hist�rico padr�o.';
COMMENT ON    COLUMN    FNCONTA.CODFILIALHP IS 'C�digo da filial na tabela de hist�rico padr�o.';
COMMENT ON    COLUMN    FNCONTA.CODHIST IS 'C�digo do hist�rico padr�o.';
COMMENT ON    COLUMN    FNCONTA.CODCONTDEB IS 'C�digo da conta d�bito cont�bil.';
COMMENT ON    COLUMN    FNCONTA.CODCONTCRED IS 'C�digo da conta cr�dito cont�bil.';
COMMENT ON    COLUMN    FNCONTA.CONVCOBCONTA IS 'Conv�nio de cobran�a';
COMMENT ON    COLUMN    FNCONTA.ATIVACONTA IS 'Indica se a conta est� ativa.';
COMMENT ON    COLUMN    FNCONTA.TIPOCAIXA IS 'Indica o tipo de caixa
"F" - F�sico
"P" - Previsionamentos';
COMMENT ON TABLE        FNCONTAVINCULADA IS 'Tabela de vinculo entre contas para composis�o de saldo interno/externo';
COMMENT ON    COLUMN    FNCONTAVINCULADA.CODEMPCV IS 'C�digo da empresa da conta vinculada';
COMMENT ON    COLUMN    FNCONTAVINCULADA.CODFILIALCV IS 'C�digo da filial da conta vinculada.';
COMMENT ON    COLUMN    FNCONTAVINCULADA.NUMCONTACV IS 'N�mero da conta vinculada.';
COMMENT ON    COLUMN    FNCONTAVINCULADA.CONTACHEQUE IS 'Indica se a conta � para controle de compensa��o de cheques.';
COMMENT ON    COLUMN    FNFBNCLI.STIPOFEBRABAN IS 'Sub-Tipo Febraban 01=D�bito em folha / 02=D�bito em conta corrente.';
COMMENT ON    COLUMN    FNFBNCODRET.TIPORET IS 'Indica o tipo de ocorr�ncia:
RE - Rejei��o de entrada;
CE - Confirma��o de entrada;
AD - Advert�ncia;
CB - Confirma��o de baixa;
RB - Rejei��o de baixa;
IN - Indefinido;
';
COMMENT ON    COLUMN    FNFBNREC.STIPOFEBRABAN IS 'Sub-Tipo Febraban 01=D�bito em folha / 02=D�bito em conta corrente.';
COMMENT ON    COLUMN    FNFBNREC.SITREMESSA IS 'Situa��o da remessa 00=Selecionado / 01=Exportada';
COMMENT ON    COLUMN    FNFBNREC.SITRETORNO IS 'Situa��o do retorno. 00=Sem retorno.';
COMMENT ON    COLUMN    FNFBNREC.NOMEARQUIVO IS 'Nome do arquivo de remessa em que o registro foi exportado.
';
COMMENT ON TABLE        FNFINALIDADE IS 'Classifica��o de finalidade das contas financeiras.';
COMMENT ON    COLUMN    FNFINALIDADE.CODFIN IS 'C�digo da finalidade.';
COMMENT ON    COLUMN    FNFINALIDADE.DESCFIN IS 'Descri��o da finalidade.';
COMMENT ON    COLUMN    FNFINALIDADE.ESFIN IS 'Define se � entrada ou sa�da (E/S).';
COMMENT ON    COLUMN    FNFINALIDADE.CLASFIN IS 'Classifica��o operacional ou n�o operacional (O/N).';
COMMENT ON TABLE        FNHISTPAD IS 'Tabela de hist�rico padr�o.';
COMMENT ON    COLUMN    FNHISTPAD.TXAHISTPAD IS 'Campo para cria��o de modelo din�mico de hist�rico padr�o.';
COMMENT ON TABLE        FNITMODBOLETO IS 'Tabela de amarra��o modelo de boleto e banco';
COMMENT ON    COLUMN    FNITMODBOLETO.CODEMP IS 'C�digo da empresa';
COMMENT ON    COLUMN    FNITMODBOLETO.CODFILIAL IS 'C�digo filial.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODMODBOL IS 'C�digo do modelo de boleto.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODEMPBO IS 'C�digo da empresa na tabela FNBANCO.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODFILIALBO IS 'C�digo da filial na tabela FNBANCO.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODBANCO IS 'C�digo do banco.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODEMPCB IS 'C�digo da empresa na tabela FNCARTCOB.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODFILIALCB IS 'C�digo da filial na tabela FNCARTCOB.';
COMMENT ON    COLUMN    FNITMODBOLETO.CODCARTCOB IS 'C�digo da carteira de cobran�a.';
COMMENT ON    COLUMN    FNITMODBOLETO.CONVCOB IS 'Conv�nio de cobran�a.';
COMMENT ON    COLUMN    FNITMODBOLETO.DVCONVCOB IS 'D�gito do codigo da empresa.';
COMMENT ON    COLUMN    FNITMODBOLETO.SEQNOSSONUMERO IS 'Sequencia do nosso numero, para gera��o de nosso numero no padr�o "S" SGPREFER1.TPNOSSONUMERO.';
COMMENT ON    COLUMN    FNITPAGAR.VLRDEVITPAG IS 'Valor das devolu��es de compra da parcela.';
COMMENT ON    COLUMN    FNITPAGAR.DTCOMPITPAG IS 'Data de compet�ncia.';
COMMENT ON    COLUMN    FNITPAGAR.STATUSITPAG IS 'P1 - Pendente
PL - Pagamento parcial
PP - Pagamento total
CP - Cancelado';
COMMENT ON    COLUMN    FNITPAGAR.CODEMPSN IS 'C�digo da empresa do sinalizador.
';
COMMENT ON    COLUMN    FNITPAGAR.CODFILIALSN IS 'C�digo da filial do sinalizador.
';
COMMENT ON    COLUMN    FNITPAGAR.CODSINAL IS 'C�digo do sinalizador.
';
COMMENT ON    COLUMN    FNITRECEBER.VLRDESCITREC IS 'Valor do desconto.';
COMMENT ON    COLUMN    FNITRECEBER.VLRMULTAITREC IS 'Valor da multa.';
COMMENT ON    COLUMN    FNITRECEBER.VLRJUROSITREC IS 'Valor dos juros.';
COMMENT ON    COLUMN    FNITRECEBER.VLRDEVITREC IS 'Valor de devolu��o da parcela.';
COMMENT ON    COLUMN    FNITRECEBER.VLRPARCITREC IS 'Valor original da parcela.';
COMMENT ON    COLUMN    FNITRECEBER.VLRPAGOITREC IS 'Valor pago da parcela.';
COMMENT ON    COLUMN    FNITRECEBER.VLRAPAGITREC IS 'Valor a pagar.';
COMMENT ON    COLUMN    FNITRECEBER.VLRCOMIITREC IS 'Valor de comiss�o por parcela.';
COMMENT ON    COLUMN    FNITRECEBER.VLRBASECOMIS IS 'Valor base para comissionamento especial';
COMMENT ON    COLUMN    FNITRECEBER.DESCPONT IS 'Indica se o desconto e do tipo pontualidade.';
COMMENT ON    COLUMN    FNITRECEBER.DTCOMPITREC IS 'Data de compet�ncia.
';
COMMENT ON    COLUMN    FNITRECEBER.DTPREVITREC IS 'Data prevista para o recebimento.';
COMMENT ON    COLUMN    FNITRECEBER.DTPAGOITREC IS 'Data de pagamento ou data de compensa��o.';
COMMENT ON    COLUMN    FNITRECEBER.DTLIQITREC IS 'Data de liquida��o do t�tulo.
';
COMMENT ON    COLUMN    FNITRECEBER.STATUSITREC IS 'R1 (Conta em aberto)
RP (Recebimento total)
RB (Em Bordero)
RL (Recebimento parcial)
RN (Recebimento Renegociado) 
RR (Recebimento em Renegociacao)';
COMMENT ON    COLUMN    FNITRECEBER.CODEMPTC IS 'C�digo da filial em FNTIPOCOB.';
COMMENT ON    COLUMN    FNITRECEBER.CODFILIALTC IS 'C�digo filial em FNTIPOCOB.';
COMMENT ON    COLUMN    FNITRECEBER.CODTIPOCOB IS 'C�digo do tipo de cobran�a.';
COMMENT ON    COLUMN    FNITRECEBER.CODEMPCB IS 'C�digo da empresa na tabela FNCARTCOB.';
COMMENT ON    COLUMN    FNITRECEBER.CODFILIALCB IS 'C�digo filial na tabela FNCARTCOB.';
COMMENT ON    COLUMN    FNITRECEBER.CODCARTCOB IS 'C�digo da carteira de cobran�a.';
COMMENT ON    COLUMN    FNITRECEBER.IMPRECIBOITREC IS 'Flag que indica se ser� impresso recibo.';
COMMENT ON    COLUMN    FNITRECEBER.RECIBOITREC IS 'Armazena o n�mero do recibo.';
COMMENT ON    COLUMN    FNITRECEBER.ALTUSUITREC IS 'Flag que indica se est� sendo feita altera��o de item de contas a receber pelo usu�rio.';
COMMENT ON    COLUMN    FNITRECEBER.PDVITREC IS 'Indica se foi recebido pelo PDV.';
COMMENT ON    COLUMN    FNITRECEBER.RECEMCOB IS 'Definie se recebimento est� em processo de cobran�a';
COMMENT ON    COLUMN    FNITRECEBER.DTINIEMCOB IS 'Data de inicio de cobran�a';
COMMENT ON    COLUMN    FNITRECEBER.DTFIMEMCOB IS 'Fim do processo de cobran�a';
COMMENT ON    COLUMN    FNITRECEBER.SEQNOSSONUMERO IS 'Sequencial do nosso n�mero.';
COMMENT ON    COLUMN    FNITRECEBER.NOSSONUMERO IS 'Nosso n�mero (utilizado para armazenar o nosso n�mero quando gerado pelo banco e retornado atrav�s do CNAB)
';
COMMENT ON    COLUMN    FNITRECEBER.EMMANUT IS 'Coloca registro em estado de manuten��o.
';
COMMENT ON TABLE        FNITTBJUROS IS 'Itens das tabelas de juros.
';
COMMENT ON    COLUMN    FNLANCA.DTCOMPLANCA IS 'Data de compet�ncia.';
COMMENT ON    COLUMN    FNLANCA.HISTBLANCA IS 'Hist�rico banc�rio.';
COMMENT ON    COLUMN    FNLANCA.PDVITREC IS 'Indica se foi recebido pelo PDV';
COMMENT ON    COLUMN    FNLANCA.CODCLI IS 'C�digo do cliente, para v�nculo com contas a receber.';
COMMENT ON    COLUMN    FNLANCA.CODEMPCL IS 'C�digo da empresa do cliente';
COMMENT ON    COLUMN    FNLANCA.CODFILIALCL IS 'C�digo da filial do cliente.';
COMMENT ON    COLUMN    FNLANCA.EMMANUT IS 'Estado de manuten��o (S/N).
';
COMMENT ON    COLUMN    FNLIBCRED.VLRVENDACRED IS 'Valor da venda no momento da libera��o de cr�dito.';
COMMENT ON    COLUMN    FNMODBOLETO.PREIMPMODBOL IS 'Flag para indicar se usa boleto pre-impresso.[ S ]';
COMMENT ON    COLUMN    FNMODBOLETO.CARTCOB IS 'Carteira de cobran�a.';
COMMENT ON    COLUMN    FNMODBOLETO.MDECOB IS 'Modalidade de cobran�a.';
COMMENT ON    COLUMN    FNMODBOLETO.ACEITEMODBOL IS 'Flag indicador para aceita��o do boleto pelo cliente.';
COMMENT ON    COLUMN    FNMODBOLETO.DESCLPMODBOL IS 'Descri��o do local de pagamento do boleto.';
COMMENT ON    COLUMN    FNMODBOLETO.INSTPAGMODBOL IS 'Instru��es para o pagamento do boleto.';
COMMENT ON    COLUMN    FNMODBOLETO.IMPINFOPARC IS 'Indica se deve imprimir informa��es da parcela nas intru��es de cobran�a para boletos gr�ficos.';
COMMENT ON    COLUMN    FNMOEDA.CODFBNMOEDA IS 'C�digo da moeda padr�o Febraban.';
COMMENT ON    COLUMN    FNMOVIMENTO.TIPOMOV IS 'Tipo de movimento:
"R" - Receber
"P" - Pagar';
COMMENT ON    COLUMN    FNMOVIMENTO.SITMOV IS 'Situa��o do movimento:
"E" - Edi��o
"B" - Baixa
"D" - Devolu��o
"C" - Cancelada
"P" - Pagar';
COMMENT ON    COLUMN    FNMOVIMENTO.STATUSOLD IS 'Status antigo.';
COMMENT ON    COLUMN    FNPAGAR.VLRDESCPAG IS 'Valor dos descontos. (Deve ser incluidos as reten��es se houverem)';
COMMENT ON    COLUMN    FNPAGAR.VLRDEVPAG IS 'Valor das devolu��es de compra.';
COMMENT ON    COLUMN    FNPAGAR.DTCOMPPAG IS 'Data de compet�ncia.';
COMMENT ON    COLUMN    FNPAGAR.VLRBASEIRRF IS 'Valor da base de calculo do IRRF (Pagamento de autonomos)';
COMMENT ON    COLUMN    FNPAGAR.VLRBASEINSS IS 'Valor da base de calculo do INSS (Pagamento de autonomos)';
COMMENT ON    COLUMN    FNPAGAR.VLRRETIRRF IS 'Valor de desconto/reten��o do IRRF (Pagamento de autonomos)';
COMMENT ON    COLUMN    FNPAGAR.VLRRETINSS IS 'Valor do desconto/reten��o do INSS (Pagamento de autonomos)';
COMMENT ON    COLUMN    FNPAGAR.CODEMPPN IS 'C�digo da empresa do planejamento financeiro.';
COMMENT ON    COLUMN    FNPAGAR.CODFILIALPN IS 'C�digo da filial do planejamento financeiro.';
COMMENT ON    COLUMN    FNPAGAR.CODPLAN IS 'C�digo do planejamento financeiro.';
COMMENT ON    COLUMN    FNPAGAR.CODEMPCC IS 'C�digo da empresa do centro de custo.';
COMMENT ON    COLUMN    FNPAGAR.CODFILIALCC IS 'C�digo da filial do centro de custo.';
COMMENT ON    COLUMN    FNPAGAR.ANOCC IS 'Ano do centro de custo.';
COMMENT ON    COLUMN    FNPAGAR.CODCC IS 'C�digo do centro de custo.';
COMMENT ON    COLUMN    FNPAGCHEQ.CODEMP IS 'C�digo da empresa na tabela FNPAGAR.';
COMMENT ON    COLUMN    FNPAGCHEQ.CODFILIAL IS 'C�digo da filial na tabela FNPAGAR.';
COMMENT ON    COLUMN    FNPAGCHEQ.CODPAG IS 'C�digo do pagamento.';
COMMENT ON    COLUMN    FNPAGCHEQ.NPARCPAG IS 'N�mero sequencial.';
COMMENT ON    COLUMN    FNPAGCHEQ.CODEMPCH IS 'C�digo da empresa na tabela FNCHEQUE.';
COMMENT ON    COLUMN    FNPAGCHEQ.CODFILIALCH IS 'C�digo filial na tabela FNCHEQUE.';
COMMENT ON    COLUMN    FNPAGCHEQ.SEQCHEQ IS 'Sequencia de cheque.';
COMMENT ON    COLUMN    FNPAGCHEQ.BAIXA IS 'Indica se deve realizar a baixa do titulo vinculado (gatilho cheque emitido)
S - Sim;
N - N�o;
';
COMMENT ON    COLUMN    FNPAGCHEQ.TRANSFERE IS 'Indica se deve transferir o titulo vinculado da conta de cheques para conta real (gatilho cheque compensado)
S - Sim;
N - N�o;';
COMMENT ON    COLUMN    FNPAGCHEQ.DTINS IS 'Data de inser��o.';
COMMENT ON    COLUMN    FNPAGCHEQ.HINS IS 'Hora de inser��o.';
COMMENT ON    COLUMN    FNPAGCHEQ.IDUSUINS IS 'Id. do usu�rio que inseriu.';
COMMENT ON    COLUMN    FNPAGCHEQ.DTALT IS 'Data de altera��o.';
COMMENT ON    COLUMN    FNPAGCHEQ.HALT IS 'Hora de altera��o.';
COMMENT ON    COLUMN    FNPAGCHEQ.IDUSUALT IS 'Id. do usu�rio que alterou.';
COMMENT ON    COLUMN    FNPAGTOCOMI.DTCOMPPCOMI IS 'Data de compet�ncia.';
COMMENT ON    COLUMN    FNPAGTOCOMI.EMMANUT IS 'Estado de manuten��o (S/N).';
COMMENT ON    COLUMN    FNPARCPAG.AUTOBAIXAPARC IS 'Flag para marcar baixa autom�tica da parcela.';
COMMENT ON    COLUMN    FNPLANEJAMENTO.CODREDPLAN IS 'C�digo reduzido';
COMMENT ON    COLUMN    FNPLANEJAMENTO.FINPLAN IS 'Finalidade do planejamento.';
COMMENT ON    COLUMN    FNPLANEJAMENTO.COMPSLDCXPLAN IS 'Flag indicando se comp�e saldo de caixa.';
COMMENT ON    COLUMN    FNPLANEJAMENTO.CODCONTCRED IS 'C�digo da conta cr�dito para contabilidade.';
COMMENT ON    COLUMN    FNPLANEJAMENTO.CODCONTDEB IS 'C�digo da conta d�bido para contabilidade.';
COMMENT ON    COLUMN    FNPLANEJAMENTO.ESFINPLAN IS 'Natureza da categoria E-Entrada / S-Sa�da';
COMMENT ON    COLUMN    FNPLANEJAMENTO.CLASFINPLAN IS 'Classifica��o da categoria O-Operacional / N-N�o Operacional.';
COMMENT ON    COLUMN    FNPLANOPAG.APORCPLANOPAG IS 'Flag que indica se or�amento ser� aprovado por padr�o na tela de fechamento de or�amento.';
COMMENT ON    COLUMN    FNPLANOPAG.ATIVOPLANOPAG IS 'Flag que indica se o plano de pagamento est� ativo "S" ou inativo "N".';
COMMENT ON    COLUMN    FNPLANOPAG.CVPLANOPAG IS 'Indica se o plano de pagamento � para compra "C", venda "V" ou ambos "A", deve ser usado como filtro nas telas correspondentes.';
COMMENT ON    COLUMN    FNPLANOPAG.CODEMPTBJ IS 'C�digo da empresa da tabela de juros.';
COMMENT ON    COLUMN    FNPLANOPAG.CODFILIALTBJ IS 'C�digo da filial da tabela de juros.';
COMMENT ON    COLUMN    FNPLANOPAG.CODTBJ IS 'C�digo da tabela de juros.';
COMMENT ON    COLUMN    FNPLANOPAG.PERCDESC IS 'Percentual de desconto.';
COMMENT ON    COLUMN    FNPLANOPAG.REGRVCTOPLANOPAG IS 'Regra de para vencimento.
N - Nenhuma
A - Antecipar
P - Prorrogar';
COMMENT ON    COLUMN    FNPLANOPAG.RVSABPLANOPAG IS 'Define se a regra de vencimento vale para os s�bados.';
COMMENT ON    COLUMN    FNPLANOPAG.RVDOMPLANOPAG IS 'Define se a regra de vencimento vale para os domingos.';
COMMENT ON    COLUMN    FNPLANOPAG.RVFERPLANOPAG IS 'Define se a regra de vencimento vale para os feriados.';
COMMENT ON    COLUMN    FNPLANOPAG.RVDIAPLANOPAG IS 'Regra do dia de vencimento
A - Autom�tico
F - Dia fixo
U - Dia util
';
COMMENT ON    COLUMN    FNPLANOPAG.DIAVCTOPLANOPAG IS 'Dia fixo ou �til para o vencimento.';
COMMENT ON    COLUMN    FNRECEBER.CODEMPTC IS 'C�digo da empresa em FNTIPOCOB.';
COMMENT ON    COLUMN    FNRECEBER.CODFILIALTC IS 'C�digo da filial em FNTIPOCOB.';
COMMENT ON    COLUMN    FNRECEBER.CODTIPOCOB IS 'C�digo do tipo de cobran�a.';
COMMENT ON    COLUMN    FNRECEBER.CODEMPCB IS 'C�digo da empresa na tabela FNCARTCOB.';
COMMENT ON    COLUMN    FNRECEBER.CODFILIALCB IS 'C�digo da filial na tabela FNCARTCOB.';
COMMENT ON    COLUMN    FNRECEBER.CODCARTCOB IS 'C�digo da carteira de cobran�a.';
COMMENT ON    COLUMN    FNRECEBER.VLRDEVREC IS 'Valor total das devolu��es.';
COMMENT ON    COLUMN    FNRECEBER.VLRBASECOMIS IS 'Valor da base de calculo de comissionamento especial';
COMMENT ON    COLUMN    FNRECEBER.VLRRETENSAOISS IS 'VALOR DO ISS RETIDO PELO CLIENTE.';
COMMENT ON    COLUMN    FNRECEBER.DTCOMPREC IS 'Data de compet�ncia.';
COMMENT ON    COLUMN    FNRECEBER.CODEMPPN IS 'C�digo da empresa do planejamento financeiro.';
COMMENT ON    COLUMN    FNRECEBER.CODFILIALPN IS 'C�digo da filial do planejamento financeiro.';
COMMENT ON    COLUMN    FNRECEBER.CODPLAN IS 'C�digo do planejamento financeiro.';
COMMENT ON    COLUMN    FNRECEBER.CODEMPCC IS 'C�digo da empresa do centro de custo.';
COMMENT ON    COLUMN    FNRECEBER.CODFILIALCC IS 'C�digo da filial do centro de custo.';
COMMENT ON    COLUMN    FNRECEBER.ANOCC IS 'Ano do centro de custo.';
COMMENT ON    COLUMN    FNRECEBER.CODCC IS 'C�digo do centro de custo.';
COMMENT ON    COLUMN    FNRECEBER.CODEMPRR IS 'C�digo da empresa da renegocia��o.';
COMMENT ON    COLUMN    FNRECEBER.CODFILIALRR IS 'C�digo da filial da renegocia��o.';
COMMENT ON    COLUMN    FNRECEBER.CODRENEGREC IS 'C�digo da renegocia��o.';
COMMENT ON    COLUMN    FNRECEBER.EMMANUT IS 'Estado de manuten��o (S/N).';
COMMENT ON TABLE        FNRENEGREC IS 'Tabela para gerenciamento de t�tulos renegociados.';
COMMENT ON TABLE        FNRESTRICAO IS 'Tabela de restri��es dos clientes.';
COMMENT ON    COLUMN    FNRESTRICAO.SITRESTR IS 'Situa�ao da restri��o 
I - Inclus�o
C - Cancelada';
COMMENT ON    COLUMN    FNSALDOLANCA.FECHADO IS 'Indica se o caixa foi fechado para este dia, se "S" n�o dever� permitir lan�amentos para esta data.';
COMMENT ON    COLUMN    FNSALDOLANCA.EMMANUT IS 'Indica se o registro est� em processo de manuten��o.';
COMMENT ON TABLE        FNSINAL IS 'Tabela de sinaliza��o de lan�amentos.';
COMMENT ON    COLUMN    FNSINAL.CODEMP IS 'C�digo da empresa.';
COMMENT ON    COLUMN    FNSINAL.CODFILIAL IS 'C�digo da filial.';
COMMENT ON    COLUMN    FNSINAL.CODSINAL IS 'C�digo de sinaliza��o';
COMMENT ON    COLUMN    FNSINAL.DESCSINAL IS 'Descri��o da sinaliza��o';
COMMENT ON    COLUMN    FNSINAL.CORSINAL IS 'Cor da sinaliza��o';
COMMENT ON    COLUMN    FNSINAL.DTINS IS 'Data de inser��o';
COMMENT ON    COLUMN    FNSINAL.HINS IS 'Hora da inser��o';
COMMENT ON    COLUMN    FNSINAL.IDUSUINS IS 'ID do usu�rio que inseriu';
COMMENT ON    COLUMN    FNSINAL.DTALT IS 'Data da �lt. altera��o';
COMMENT ON    COLUMN    FNSINAL.HALT IS 'Hora da �lt. altera��o';
COMMENT ON    COLUMN    FNSINAL.IDUSUALT IS 'ID do usu�rio que alterou';
COMMENT ON    COLUMN    FNSUBLANCA.DTCOMPSUBLANCA IS 'Data de compet�ncia.';
COMMENT ON    COLUMN    FNSUBLANCA.TIPOSUBLANCA IS 'Tipo de lan�amento (P-Padr�o, D-Desconto, J-Juros, M-Multa)';
COMMENT ON    COLUMN    FNSUBLANCA.CODEMPCT IS 'C�digo da empresa do contrato/projeto';
COMMENT ON    COLUMN    FNSUBLANCA.CODFILIALCT IS 'C�digo da filial da empresa/contrato';
COMMENT ON    COLUMN    FNSUBLANCA.CODCONTR IS 'C�digo do contrato/projeto';
COMMENT ON    COLUMN    FNSUBLANCA.CODITCONTR IS 'C�digo do �tem de contrato/projeto';
COMMENT ON    COLUMN    FNSUBLANCA.EMMANUT IS 'Estado de manuten��o (S/N).';
COMMENT ON TABLE        FNTALAOCHEQ IS 'Tabela de talon�rio de cheques.';
COMMENT ON    COLUMN    FNTALAOCHEQ.CODEMP IS 'C�digo da empresa';
COMMENT ON    COLUMN    FNTALAOCHEQ.CODFILIAL IS 'C�digo da filial.';
COMMENT ON    COLUMN    FNTALAOCHEQ.NUMCONTA IS 'N�mero da conta';
COMMENT ON    COLUMN    FNTALAOCHEQ.SEQTALAO IS 'Sequencia do tal�o.';
COMMENT ON    COLUMN    FNTALAOCHEQ.DTTALAO IS 'Data do tal�o.';
COMMENT ON    COLUMN    FNTALAOCHEQ.CHEQINITALAO IS 'N�mero do cheque inicial.';
COMMENT ON    COLUMN    FNTALAOCHEQ.CHEQFIMTALAO IS 'N�mero final do cheque.';
COMMENT ON    COLUMN    FNTALAOCHEQ.CHEQATUALTALAO IS '�ltimo cheque impresso.';
COMMENT ON    COLUMN    FNTALAOCHEQ.ATIVOTALAO IS 'Talon�rio ativo (S/N).';
COMMENT ON TABLE        FNTBJUROS IS 'Tabela de juros financeiros
';
COMMENT ON    COLUMN    FNTBJUROS.TIPOTBJ IS 'Tipo da tabela de juros: 
"D" = Di�rio
"M" = Mensal
"B" = Bimestral
"T" = Trimestral
"S" = Semestral
"A" = Anual
"F" = Fixo';
COMMENT ON    COLUMN    FNTIPOCOB.TIPOFEBRABAN IS 'Define se � febraban. 00=n�o / 01-SIACC / 02-CNAB';
COMMENT ON    COLUMN    FNTIPOCOB.TIPOSPED IS 'Indicador do tipo de t�tulo de cr�dito para o SPED:
00 - Duplicata
01 - Cheque
02 - Promiss�ria
03 - recibo
';
COMMENT ON    COLUMN    FNTIPOCOB.OBRIGCARTCOB IS 'Define se � obrigat�rio carteira de cobran�a.';
COMMENT ON    COLUMN    FNTIPOCOB.CODEMPCT IS 'C�digo da empresa na tabela de contas.
';
COMMENT ON    COLUMN    FNTIPOCOB.CODFILIALCT IS 'C�digo da filial na tabela de contas.';
COMMENT ON    COLUMN    FNTIPOCOB.NUMCONTA IS 'N�mero da conta para impress�o de cheques.
';
COMMENT ON    COLUMN    FNTIPOCOB.SEQTALAO IS 'Sequencia do talon�rio.';
COMMENT ON    COLUMN    FNTIPOCOB.NRODIASCOMP IS 'Numero de dias para compensa��o do valor.';
COMMENT ON TABLE        FNTIPORESTR IS 'Tipos de restri��es.';
COMMENT ON    COLUMN    FNTIPORESTR.BLOQTPRESTR IS 'Define se a restri��o bloqueia os lan�amentos.';
COMMENT ON TABLE        GTT_RECURSIVO IS 'Tabela utilizada no controle da recursividade em triggers e stored procedures';
COMMENT ON    COLUMN    LFCLFISCAL.TIPOFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.TPREDICMSFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.ALIQFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.ALIQLFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.REDFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.ALIQIPIFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.ORIGFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODEMPTT IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODFILIALTT IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODTRATTRIB IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODEMPME IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODFILIALME IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODMENS IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.SITPISFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.SITCOFINSFISC IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.TIPOST IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.MARGEMVLAGR IS 'Campo inutilizado (mantido por retrocompatibilidade)';
COMMENT ON    COLUMN    LFCLFISCAL.CODSERV IS 'C�digo do servi�o (tabela de servi�os)';
COMMENT ON TABLE        LFCSOSN IS 'Tabela de situa��o da opera��o no simples nacional.';
COMMENT ON    COLUMN    LFCSOSN.CSOSN IS 'C�digo de situa��o da opera��o no simples nacional.';
COMMENT ON    COLUMN    LFCSOSN.DESCCSOSN IS 'Descri��o da situa��o da opera��o no simples nacional.';
COMMENT ON TABLE        LFFRETE IS 'Tabela para lan�amento de conhecimentos de frete.';
COMMENT ON    COLUMN    LFFRETE.CODNAT IS 'Natureza da opera��o.';
COMMENT ON    COLUMN    LFFRETE.TIPOFRETE IS 'Modalidade do frete "1" CIF ou "2" FOB.';
COMMENT ON    COLUMN    LFFRETE.TIPOPGTO IS 'Tipo de pagamento do frete "P" pago ou "A" a pagar.';
COMMENT ON    COLUMN    LFFRETE.CODREMET IS 'C�digo do remetente. Relacionada � tabela de c�digos unificados SGUNIFCOD
Pode ser cliente, fornecedor, empresa, etc...';
COMMENT ON    COLUMN    LFFRETE.CODDESTINAT IS 'C�digo do destinatario. Relacionada � tabela de c�digos unificados SGUNIFCOD
Pode ser cliente, fornecedor, empresa, etc...';
COMMENT ON    COLUMN    LFFRETE.DTEMITFRETE IS 'Data de emiss�o do conhecimento de frete.';
COMMENT ON    COLUMN    LFFRETE.QTDFRETE IS 'Quantidade de volumes.';
COMMENT ON    COLUMN    LFFRETE.VLRMERCADORIA IS 'Valor da mercadoria transportada.';
COMMENT ON    COLUMN    LFFRETE.VLRFRETE IS 'Valor cobrado pelo frete.';
COMMENT ON    COLUMN    LFFRETE.VLRFRETENOTA IS 'Valor do frete destacado na nota fiscal.';
COMMENT ON    COLUMN    LFFRETE.PESOBRUTO IS 'Peso bruto da mercadoria transportada.';
COMMENT ON    COLUMN    LFFRETE.PESOLIQUIDO IS 'Peso liquido da mercadoria transportada.';
COMMENT ON    COLUMN    LFFRETE.VLRBASEICMSFRETE IS 'Base do ICMS.';
COMMENT ON    COLUMN    LFFRETE.CODPAG IS 'Codigo no contas a pagar.';
COMMENT ON    COLUMN    LFFRETE.SERIE IS 'S�rie do documento de conhecimento de frete.';
COMMENT ON    COLUMN    LFFRETE.CODEMPRM IS 'C�digo da empresa do recebimento de mercadorias.';
COMMENT ON    COLUMN    LFFRETE.CODFILIALRM IS 'C�digo da filial do recebimento de mercadorias.';
COMMENT ON    COLUMN    LFFRETE.TICKET IS 'Ticket do recebimento de mercadoria.';
COMMENT ON    COLUMN    LFFRETE.CHAVECTE IS 'Chave do conhecimento de transporte eletronico.';
COMMENT ON    COLUMN    LFFRETE.CODEMPTT IS 'C�digo da empresa do tratamento tributario do icms.';
COMMENT ON    COLUMN    LFFRETE.CODFILIALTT IS 'C�digo da filial do tratamento tribut�rio do ICMS';
COMMENT ON    COLUMN    LFFRETE.CODTRATTRIB IS 'C�digo do tratamento tribut�rio do ICMS';
COMMENT ON    COLUMN    LFITCLFISCAL.ORIGFISC IS 'Origem da mercadoria:
0 - Nacional
1 - Estrangeira Importa��o direta
2 - Estrangeira - Adquirida no mercado interno';
COMMENT ON    COLUMN    LFITCLFISCAL.TPREDICMSFISC IS 'Tipo de redu��o de ICMS
B - BASE DE C�LCULO
V - VALOR DO ICMS';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQFISC IS 'Al�quota interestadual do produto.';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQFISCINTRA IS 'Al�quota intraestadual de ICMS.';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQLFISC IS 'Aliquota de livros fiscais (uso pdv)';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQIPIFISC IS 'Al�quota de IPI';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQPISFISC IS 'Al�quota diferenciada do PIS para a classifica��o fiscal (sobrep�e aliquota da filial)';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQCOFINSFISC IS 'Al�quota diferenciada do Cofins para a classifica��o fiscal (sobrep�e al�quota da filial)';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQCSOCIALFISC IS 'Aliquota da contribui��o social.';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQIRFISC IS 'Aliquota do imposto de renda.';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQFUNRURALFISC IS 'Aliquota de recolhimento do Funrural.';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQIIFISC IS 'Aliquota do imposto de importa��o para a classifica��o fiscal.';
COMMENT ON    COLUMN    LFITCLFISCAL.TIPOST IS 'Tipo da Substitui��o tribut�ria.
SU - Substituto (Respons�vel pela reten��o e recolhimento de todo o imposto (subsequente tamb�m);
SI - Substitu�do (Imposto pago pelo contribuite substituto);';
COMMENT ON    COLUMN    LFITCLFISCAL.MARGEMVLAGR IS 'Margem de valor agregado para calculo da base de calculo do icms de substitui��o tribut�ria.';
COMMENT ON    COLUMN    LFITCLFISCAL.GERALFISC IS 'Indica se � a regra geral da classifica��o fiscal.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODEMPSP IS 'C�digo da empresa da situa��o tribut�ria do PIS.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODFILIALSP IS 'C�digo da filial da situa��o tribut�ria do PIS';
COMMENT ON    COLUMN    LFITCLFISCAL.CODSITTRIBPIS IS 'C�digo da situa��o tribut�rio do PIS.';
COMMENT ON    COLUMN    LFITCLFISCAL.IMPSITTRIBPIS IS 'C�digo do imposto da situa��o tribut�ria do PIS.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODEMPSC IS 'C�digo da empresa da situa��o tribut�ria do COFINS.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODFILIALSC IS 'C�digo da situa��o tribut�ria do COFINS.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODSITTRIBCOF IS 'C�digo da situa��o tribut�ria do COFINS.';
COMMENT ON    COLUMN    LFITCLFISCAL.IMPSITTRIBCOF IS 'C�digo do imposto da situa��o tribut�ria do COFINS.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODEMPSI IS 'C�digo da empresa da situa��o tribut�ria do IPI';
COMMENT ON    COLUMN    LFITCLFISCAL.CODFILIALSI IS 'C�digo da filial da situa��o tribut�ria do IPI';
COMMENT ON    COLUMN    LFITCLFISCAL.CODSITTRIBIPI IS 'C�digo da situa��o tribut�ria do IPI';
COMMENT ON    COLUMN    LFITCLFISCAL.IMPSITTRIBIPI IS 'C�digo do imposto da situa��o tribut�ria do IPI';
COMMENT ON    COLUMN    LFITCLFISCAL.TPCALCIPI IS 'Tipo de c�lculo do IPI 
P - Percentual
V - Valor';
COMMENT ON    COLUMN    LFITCLFISCAL.VLRIPIUNIDTRIB IS 'Valor do IPI por unidade tribut�vel.
Utilizado caso o TPCALCIPI = V';
COMMENT ON    COLUMN    LFITCLFISCAL.MODBCICMS IS 'Modalidade de determina��o da base de c�lculo do ICMS.
0 - Margem Valor Agregado (%)
1 - Pauta (Valor)
2 - Pre�o Tabelado Max. (valor)
3 - Valor da opera��o';
COMMENT ON    COLUMN    LFITCLFISCAL.MODBCICMSST IS 'Modalidade de determina��o da base de c�lculo do ICMS substitui��o tribut�ria.
0 - Pre�o tabelado ou m�ximo sugerido;
1 - Lista Negativa (valor)
2 - Lista Positiva (valor)
3 - Lista Neutra (valor)
4 - Margem Valor Agregado (%);
5 - Pauta (valor)';
COMMENT ON    COLUMN    LFITCLFISCAL.CODPAIS IS 'C�digo do pa�s para amarra��o com estado.';
COMMENT ON    COLUMN    LFITCLFISCAL.SIGLAUF IS 'Sigla da UF';
COMMENT ON    COLUMN    LFITCLFISCAL.VLRPISUNIDTRIB IS 'Valor de tributa��o do PIS por unidade vendida.';
COMMENT ON    COLUMN    LFITCLFISCAL.VLRCOFUNIDTRIB IS 'Valor de tributa��o do COFINS por unidade vendida.';
COMMENT ON    COLUMN    LFITCLFISCAL.TIPOUSOITFISC IS 'Tipo de uso para a regra de classifica��o fiscal:
VD - Venda
CP - Compra';
COMMENT ON    COLUMN    LFITCLFISCAL.REDBASEST IS 'Indica se h� redu��o na base do icms de substitui��o tribut�ria.';
COMMENT ON    COLUMN    LFITCLFISCAL.REDBASEFRETE IS 'Indica se deve reduzir o valor do frete adicionado a base do icms.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODEMPIS IS 'C�digo da empresa da situa��o tribut�rio para o ISS.';
COMMENT ON    COLUMN    LFITCLFISCAL.CODFILIALIS IS 'C�digo da filial para a situa��o tribut�ria do ISS';
COMMENT ON    COLUMN    LFITCLFISCAL.CODSITTRIBISS IS 'C�digo da situa��o tribut�ria para o ISS';
COMMENT ON    COLUMN    LFITCLFISCAL.IMPSITTRIBISS IS 'Sigla do imposto definido para situa��o tribut�ria do ISS';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQISSFISC IS 'Al�quota do ISS';
COMMENT ON    COLUMN    LFITCLFISCAL.RETENSAOISS IS 'Indica se deve ISS deve ser retido pelo cliente na fonte (pago pelo cliente e descontado na nota);';
COMMENT ON    COLUMN    LFITCLFISCAL.INDAPURIPI IS 'Indicativo de apura��o do IPI
0 - Mensal
1 - Decendial';
COMMENT ON    COLUMN    LFITCLFISCAL.CODEMPCN IS 'C�digo da empresa do CSOSN
';
COMMENT ON    COLUMN    LFITCLFISCAL.CODFILIALCN IS 'C�digo da filial do CSOSN
';
COMMENT ON    COLUMN    LFITCLFISCAL.CSOSN IS 'C�digo de situa��o da opera��o no simples nacional.
';
COMMENT ON    COLUMN    LFITCLFISCAL.ALIQICMSIMP IS 'Aliquota do ICMS de importa��o.
';
COMMENT ON TABLE        LFITCOMPRA IS 'Tabela auxiliar de informa��es fiscais do item de compra.';
COMMENT ON    COLUMN    LFITCOMPRA.ALIQREDBCICMS IS 'Al�quota da redu��o da base de calculo do ICMS.';
COMMENT ON    COLUMN    LFITCOMPRA.ALIQREDBCICMSST IS 'Aliquota da redu��o da base de calculo do ICMS Substitui��o tribut�ria.';
COMMENT ON    COLUMN    LFITCOMPRA.ALIQICMSST IS 'Aliquota do icms de substitui��o tribut�ria.';
COMMENT ON    COLUMN    LFITCOMPRA.ALIQPIS IS 'Al�quota do PIS';
COMMENT ON    COLUMN    LFITCOMPRA.VLRPISUNIDTRIB IS 'Valor tribut�vel para o PIS';
COMMENT ON    COLUMN    LFITCOMPRA.VLRBASEPIS IS 'Valor da base de c�lculo do PIS';
COMMENT ON    COLUMN    LFITCOMPRA.VLRBASECOFINS IS 'Valor da base de c�lculo do COFINS';
COMMENT ON    COLUMN    LFITCOMPRA.VLRCOFUNIDTRIB IS 'Valor tribut�vel do pis por unidade comprada.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRIR IS 'Valor do IR sobre o �tem de compra.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRPIS IS 'Valor do PIS sobre o �tem de compra.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRCOFINS IS 'Valor do COFINS sobre o item de compra.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRCSOCIAL IS 'Valor da Contribui��o Social sobre o item de venda.';
COMMENT ON    COLUMN    LFITCOMPRA.ORIGFISC IS 'Origem da mercadoria:
0 - Nacional
1 - Estrangeira Importa��o direta
2 - Estrangeira - Adquirida no mercado interno';
COMMENT ON    COLUMN    LFITCOMPRA.CODTRATTRIB IS 'C�digo do tratamento tribut�rio.';
COMMENT ON    COLUMN    LFITCOMPRA.PERCICMSSIMPLES IS 'Percentual de cr�dito de icms (simples)
';
COMMENT ON    COLUMN    LFITCOMPRA.EMMANUT IS 'Indica se a tabela est� em manuten��o.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRBASEICMSSTITRETCOMPRA IS 'Base de calculo do icms st retido na opera��o anterior.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRICMSSTRETITCOMPRA IS 'Valor do ICMS ST retido na opera��o anterior.
';
COMMENT ON    COLUMN    LFITCOMPRA.ALIQII IS 'Aliquota do imposto de importa��o do item.
';
COMMENT ON    COLUMN    LFITCOMPRA.VLRBASEII IS 'Valor da base de calculo do imposto de importa��o do item.
';
COMMENT ON    COLUMN    LFITCOMPRA.VLRII IS 'Valor do imposto de importa��o do item.
';
COMMENT ON    COLUMN    LFITCOMPRA.VLRICMSDIFERIDO IS 'Valor do ICMS diferido.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRICMSDEVIDO IS 'Valor do ICMS devido.';
COMMENT ON    COLUMN    LFITCOMPRA.VLRICMSCREDPRESUM IS 'Valor do cr�dito presumido do ICMS';
COMMENT ON TABLE        LFITVENDA IS 'Tabela auxiliar de informa��es fiscais do item de venda.';
COMMENT ON    COLUMN    LFITVENDA.ALIQREDBCICMS IS 'Al�quota da redu��o da base de calculo do ICMS.';
COMMENT ON    COLUMN    LFITVENDA.ALIQREDBCICMSST IS 'Aliquota da redu��o da base de calculo do ICMS Substitui��o tribut�ria.';
COMMENT ON    COLUMN    LFITVENDA.ALIQICMSST IS 'Aliquota do icms de substitui��o tribut�ria.';
COMMENT ON    COLUMN    LFITVENDA.ALIQPIS IS 'Al�quota do PIS';
COMMENT ON    COLUMN    LFITVENDA.VLRPISUNIDTRIB IS 'Valor tribut�vel para o PIS';
COMMENT ON    COLUMN    LFITVENDA.VLRBASEPIS IS 'Valor da base de c�lculo do PIS';
COMMENT ON    COLUMN    LFITVENDA.VLRBASECOFINS IS 'Valor da base de c�lculo do COFINS';
COMMENT ON    COLUMN    LFITVENDA.VLRCOFUNIDTRIB IS 'Valor tribut�vel do pis por unidade vendida';
COMMENT ON    COLUMN    LFITVENDA.VLRIR IS 'Valor do IR sobre o �tem de venda.';
COMMENT ON    COLUMN    LFITVENDA.VLRPIS IS 'Valor do PIS sobre o �tem de venda.';
COMMENT ON    COLUMN    LFITVENDA.VLRCOFINS IS 'Valor do COFINS sobre o item de venda.';
COMMENT ON    COLUMN    LFITVENDA.VLRCSOCIAL IS 'Valor da Contribui��o Social sobre o item de venda.';
COMMENT ON    COLUMN    LFITVENDA.VLRBASEICMSITVENDA IS 'Valor da base de calculo do icms sobre o frete do item.';
COMMENT ON    COLUMN    LFITVENDA.VLRBASEICMSFRETEITVENDA IS 'Valor da base de calculo do icms do item de venda.';
COMMENT ON    COLUMN    LFITVENDA.VLRICMSFRETEITVENDA IS 'Valor do icms do frete no item de venda.';
COMMENT ON    COLUMN    LFITVENDA.VLRBASENCM IS 'Valor base de c�lculo para estimativa de impostos, referente a lei de transpar�ncia';
COMMENT ON    COLUMN    LFITVENDA.ALIQNACNCM IS 'Al�quota estimada de tributos nacionais, referente a lei de transpar�ncia';
COMMENT ON    COLUMN    LFITVENDA.ALIQIMPNCM IS 'Al�quota estimada de tributos de importa��o, referente a lei de transpar�ncia';
COMMENT ON    COLUMN    LFITVENDA.VLRNACNCM IS 'Valor estimado de tributos nacionais, referente a lei de transpar�ncia';
COMMENT ON    COLUMN    LFITVENDA.VLRIMPNCM IS 'Valor estimado de tributos de importa��o, referente a lei de transpar�ncia';
COMMENT ON    COLUMN    LFLIVROFISCAL.SITUACAOLF IS 'Situa��o do registro:
"N" - Documento fiscal normal;
"S" - Documento fiscal cancelado;
"E" - Documento fiscal extempor�neo;
"X" - Documento fiscal extempor�neo cancelado;';
COMMENT ON    COLUMN    LFMODNOTA.TIPOMODNOTA IS 'N - Normal
E - Nota fiscal Eletronica
O - Outras';
COMMENT ON    COLUMN    LFNATOPER.IMPDTSAIDANAT IS 'Flag para impress�o de data de saida na nota fiscal.
';
COMMENT ON TABLE        LFNCM IS 'NCM Nomenclatura Comum do Mercosul';
COMMENT ON TABLE        LFSEQSERIE IS 'Tabela de sequencia de s�ries.';
COMMENT ON    COLUMN    LFSEQSERIE.CODEMP IS 'C�digo da empresa conforme a tabela LFSERIE.';
COMMENT ON    COLUMN    LFSEQSERIE.CODFILIAL IS 'C�digo da filial conforme a tabela LFSERIE.';
COMMENT ON    COLUMN    LFSEQSERIE.SERIE IS 'C�digo da s�rie.';
COMMENT ON    COLUMN    LFSEQSERIE.CODEMPSS IS 'C�digo da empresa.';
COMMENT ON    COLUMN    LFSEQSERIE.CODFILIALSS IS 'C�digo da filial.';
COMMENT ON    COLUMN    LFSEQSERIE.SEQSERIE IS 'Sequencia da s�rie.';
COMMENT ON    COLUMN    LFSEQSERIE.DOCSERIE IS 'N�mero sequencia do documento.';
COMMENT ON    COLUMN    LFSEQSERIE.ATIVSERIE IS 'Define se a sequencia est� ativa (S/N).';
COMMENT ON    COLUMN    LFSEQSERIE.DTINS IS 'Data de inser��o.';
COMMENT ON    COLUMN    LFSEQSERIE.HINS IS 'Hora de inser��o.';
COMMENT ON    COLUMN    LFSEQSERIE.IDUSUINS IS 'ID do usu�rio que inseriu.';
COMMENT ON    COLUMN    LFSEQSERIE.DTALT IS 'Data de altera��o.';
COMMENT ON    COLUMN    LFSEQSERIE.HALT IS 'Hora de altera��o.';
COMMENT ON    COLUMN    LFSEQSERIE.IDUSUALT IS 'ID do usu�rio que alterou.';
COMMENT ON TABLE        LFSITTRIB IS 'Tabela de situa��es tribut�rias para os impostos:
PIS e Cofins.';
COMMENT ON    COLUMN    LFSITTRIB.IMPSITTRIB IS 'Imposto, pode ser: CO (Cofins),PI (PIS),IC (ICMS),IR (IMPOSTO DE RENDA),CS (Contribui��o Social), IP (IPI), IS (ISS)';
COMMENT ON    COLUMN    LFSITTRIB.OPERACAO IS 'Operacao de (S) - Saida, (E) - Entrada';
COMMENT ON    COLUMN    LFTABICMS.UFTI IS 'Sigla do estado';
COMMENT ON    COLUMN    LFTABICMS.ALIQTI IS 'Aliquota de icms interestadual';
COMMENT ON    COLUMN    LFTABICMS.ALIQINTRATI IS 'Aliquota de icms intraestadual.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCCOFINSTF IS 'Indica se deve calcular o cofins.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCCSOCIALTF IS 'Indica se calcula a contribui��o social.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCICMSTF IS 'Indica se calcula o ICMS.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCIPITF IS 'Indica se calcula o IPI.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCIRTF IS 'Indica se calcula o IR.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCISSTF IS 'Indica se calcula o iss.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CALCPISTF IS 'Indica se calcula o PIS.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPCOFINSTF IS 'Indica se imprime o cofins.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPCSOCIALTF IS 'indica se imprime a contribui��o social.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPICMSTF IS 'indica se imprime o ICMS.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPISSTF IS 'indica se imprime o ISS.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPIPITF IS 'indica se imprime o IPI.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPIRTF IS 'indica se imprime o IR.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.IMPPISTF IS 'indica se imprime o PIS.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CODEMPOC IS 'C�digo da empresa para o tipo de movimento de or�amento para o tipo fiscal de cliente.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CODFILIALOC IS 'C�digo da filial para o tipo de movimento de or�amento para o tipo fiscal de cliente.';
COMMENT ON    COLUMN    LFTIPOFISCCLI.CODTIPOMOVOC IS 'C�digo do tipo de movimento de or�amento para o tipo fiscal de cliente.';
COMMENT ON TABLE        LFTRATTRIB IS 'Tabela de tratamentos tribut�rios de ICMS.';
COMMENT ON TABLE        PPDISTRIB IS 'Distribui��o da estrutura para mais de um produto final.';
COMMENT ON    COLUMN    PPDISTRIB.CODEMP IS 'C�digo da empresa.';
COMMENT ON    COLUMN    PPDISTRIB.CODFILIAL IS 'C�digo da filial.';
COMMENT ON    COLUMN    PPDISTRIB.CODPROD IS 'C�digo do produto referente a estrutura.';
COMMENT ON    COLUMN    PPDISTRIB.SEQEST IS 'N�mero sequencial da estrutura.';
COMMENT ON    COLUMN    PPDISTRIB.SEQEF IS 'Sequencia da fase.';
COMMENT ON    COLUMN    PPDISTRIB.CODEMPFS IS 'C�digo da empresa na tabela PPFASE.';
COMMENT ON    COLUMN    PPDISTRIB.CODFILIALFS IS 'C�digo da filial na tabela PPFASE.';
COMMENT ON    COLUMN    PPDISTRIB.CODFASE IS 'C�digo da fase.';
COMMENT ON    COLUMN    PPDISTRIB.SEQDE IS 'Sequencia da distribui��o da estrutura.';
COMMENT ON    COLUMN    PPDISTRIB.CODEMPDE IS 'C�digo da empresa para v�nculo com estrutura usada para distribui��o.';
COMMENT ON    COLUMN    PPDISTRIB.CODFILIALDE IS 'C�digo da filial para estrutura de distribui��o.';
COMMENT ON    COLUMN    PPDISTRIB.CODPRODDE IS 'C�digo do produto para estrutura de distribui��o.';
COMMENT ON    COLUMN    PPDISTRIB.SEQESTDE IS 'N�mero sequencial da estrutura de distribui��o.';
COMMENT ON TABLE        PPESTRUANALISE IS 'Cadastro de etapas de analise e controle de qualidade, previstas para a estrutura de um produto.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODEMP IS 'C�digo da empresa.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODFILIAL IS 'C�digo da filial.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODESTANALISE IS 'C�digo da estrutra x analise.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODPROD IS 'C�digo do produto vinculado a estrutura.';
COMMENT ON    COLUMN    PPESTRUANALISE.SEQEST IS 'Sequ�ncia da estrutura.';
COMMENT ON    COLUMN    PPESTRUANALISE.SEQEF IS 'Sequ�ncia da estrutura x fase.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODEMPFS IS 'C�digo da emprea da estrutura x fase.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODFILIALFS IS 'C�digo da filial da estrutura x fase.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODFASE IS 'C�digo da fase.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODEMPTA IS 'C�digo da empresa do tipo de analise.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODFILIALTA IS 'C�digo da filial do tipo de analise.';
COMMENT ON    COLUMN    PPESTRUANALISE.CODTPANALISE IS 'C�digo do tipo de an�lise.';
COMMENT ON    COLUMN    PPESTRUANALISE.VLRMIN IS 'Valor m�nimo de toler�ncia.';
COMMENT ON    COLUMN    PPESTRUANALISE.VLRMAX IS 'Valor m�ximo de toler�ncia.';
COMMENT ON    COLUMN    PPESTRUANALISE.ESPECIFICACAO IS 'Espessifica��o padr�o da an�lise, utilizada quando a an�lise � descritiva.';
COMMENT ON    COLUMN    PPESTRUANALISE.EMITCERT IS 'Indica se a analise deve sair no certificado de analises.';
COMMENT ON    COLUMN    PPESTRUANALISE.DTINS IS 'Data de inser��o do registro.';
COMMENT ON    COLUMN    PPESTRUANALISE.HINS IS 'Hora de inser��o do registro.';
COMMENT ON    COLUMN    PPESTRUANALISE.IDUSUINS IS 'Usu�rio que inseriu o registro.';
COMMENT ON    COLUMN    PPESTRUANALISE.DTALT IS 'Data de altera��o do registro.';
COMMENT ON    COLUMN    PPESTRUANALISE.HALT IS 'Hora da ultima altera��o no registro.';
COMMENT ON    COLUMN    PPESTRUANALISE.IDUSUALT IS 'Usu�rio que alterou o registro.';
COMMENT ON TABLE        PPESTRUFASE IS 'V�nculo estrutura, fase de produ��o e recursos de produ��o.
';
COMMENT ON    COLUMN    PPESTRUFASE.SEQEST IS 'N�mero sequencial da estrutura.';
COMMENT ON    COLUMN    PPESTRUFASE.FINALIZAOP IS 'Indica se a fase finaliza o processo de produ��o (S/N).';
COMMENT ON    COLUMN    PPESTRUFASE.INSTRUCOES IS 'Instru��es da fase de produ��o para a estrutura.';
COMMENT ON TABLE        PPESTRUTURA IS 'Estrutura de produtos.';
COMMENT ON    COLUMN    PPESTRUTURA.SEQEST IS 'N�mero sequencial da estrutura.';
COMMENT ON    COLUMN    PPESTRUTURA.ATIVOEST IS 'Indica se a estrutura est� ativa.(S/N)';
COMMENT ON    COLUMN    PPESTRUTURA.CODEMPML IS 'C�digo da empresa para o modelo de lote.';
COMMENT ON    COLUMN    PPESTRUTURA.CODFILIALML IS 'C�digo da filial do modelo de lote.';
COMMENT ON    COLUMN    PPESTRUTURA.CODMODLOTE IS 'C�digo do modelo de lote.';
COMMENT ON    COLUMN    PPESTRUTURA.NRODIASVALID IS 'N�mero de dias de validade do produto fabricado.';
COMMENT ON    COLUMN    PPESTRUTURA.GLOTEOPP IS 'Flag que indica, se quando distribuido, produto gera codigo do lote a partir do produto da op principal.';
COMMENT ON    COLUMN    PPESTRUTURA.OBSERVACAO IS 'Observacoes sobre a estrutura.';
COMMENT ON    COLUMN    PPESTRUTURA.ESTDINAMICA IS 'Indica se a estrutura � din�mica.';
COMMENT ON TABLE        PPFASE IS 'Fases de produ��o
';
COMMENT ON    COLUMN    PPFASE.TIPOFASE IS '"EX" = Execu��o;
"CQ" = Controle de qualidade;
"EB" = Embalagem.
';
COMMENT ON    COLUMN    PPFASE.EXTERNAFASE IS 'Indica se a fase � realizada externamente (terceiriza��o)
S - Sim
N - Nao
';
COMMENT ON TABLE        PPFOTOMTAN IS 'Fotos das caracter�sticas dos m�todos anal�ticos.';
COMMENT ON    COLUMN    PPFOTOMTAN.CODMTANALISE IS 'C�digo do m�todo anal�tico.';
COMMENT ON    COLUMN    PPFOTOMTAN.CODFOTOMTAN IS 'C�digo da foto.';
COMMENT ON    COLUMN    PPFOTOMTAN.DESCFOTOMTAN IS 'Descri��o da foto.';
COMMENT ON TABLE        PPITESTRUTURA IS '�tens de estrutura de produtos.';
COMMENT ON    COLUMN    PPITESTRUTURA.SEQEST IS 'N�mero sequencial da estrutura.';
COMMENT ON    COLUMN    PPITESTRUTURA.REFPROD IS 'Referencia do produto (estrutura)';
COMMENT ON    COLUMN    PPITESTRUTURA.REFPRODPD IS 'Referencia do produto (item).';
COMMENT ON    COLUMN    PPITESTRUTURA.SEQEF IS 'Sequencia da fase.';
COMMENT ON    COLUMN    PPITESTRUTURA.RMAAUTOITEST IS 'Flag para gera��o de RMA autom�tica (S/N)';
COMMENT ON    COLUMN    PPITESTRUTURA.CPROVA IS 'Indica se o �tem ser� utilizado para reten��o de contra-prova (controle de qualidade).';
COMMENT ON    COLUMN    PPITESTRUTURA.QTDVARIAVEL IS 'Indica se a quantidade varia em virtude da quantidade final produzida.';
COMMENT ON    COLUMN    PPITESTRUTURA.QTDFIXA IS 'Indica se a quantidade n�o deve ser multiplicada pela quantidade de itens de estrutura produzida.';
COMMENT ON    COLUMN    PPITESTRUTURA.PERMITEAJUSTEITEST IS 'Indica se deve permitir o ajuste da quantidade a ser utilizad na produ��o no momento da OP.
';
COMMENT ON    COLUMN    PPITESTRUTURA.TIPOEXTERNO IS 'Tipo de fluxo externo
E - Envio/Remessa
R - Retorno

';
COMMENT ON TABLE        PPITOP IS '�tens de ordens de produ��o.';
COMMENT ON    COLUMN    PPITOP.SEQOP IS 'N�mero sequencial da OP.';
COMMENT ON    COLUMN    PPITOP.REFPROD IS 'Referencia do produto.';
COMMENT ON    COLUMN    PPITOP.CODEMPLE IS 'C�digo da empresa - lote
';
COMMENT ON    COLUMN    PPITOP.CODFILIALLE IS 'C�digo da filial - Lote
';
COMMENT ON    COLUMN    PPITOP.CODLOTE IS 'C�digo do lote
';
COMMENT ON    COLUMN    PPITOP.SEQITOPCP IS 'Sequ�ncia do �tem copiado (utilizado no rateamento autom�tico de lotes)';
COMMENT ON    COLUMN    PPITOP.CODLOTERAT IS 'C�digo do lote rateado';
COMMENT ON    COLUMN    PPITOP.QTDCOPIAITOP IS 'Quantidade da inserida na c�pia do item. Este valor dispara o trigger para cria��o da c�pia.';
COMMENT ON    COLUMN    PPITOP.GERARMA IS 'Indica se deve gerar rma para o item. pode ser que n�o deva ser gerado por padr�o, ou por j� ter sido gerado.';
COMMENT ON    COLUMN    PPITOP.SEQAC IS 'Sequencial da a��o corretiva.';
COMMENT ON    COLUMN    PPITOP.BLOQOP IS 'Marca se item bloqueou op.';
COMMENT ON    COLUMN    PPITOP.PERMITEAJUSTEITOP IS 'Indica se deve permitir o ajuste de quantidades.
S/N
';
COMMENT ON    COLUMN    PPITOP.TIPOEXTERNO IS 'Tipo de fluxo externo
E - Envio/Remessa
R - Retorno';
COMMENT ON    COLUMN    PPITOP.CODEMPVD IS 'C�digo da empresa da venda (remessa ou retorno industrializa��o);
';
COMMENT ON    COLUMN    PPITOP.CODFILIALVD IS 'C�digo da filial da venda (remessa ou retorno industrializa��o);';
COMMENT ON    COLUMN    PPITOP.TIPOVENDA IS 'Tipo da venda (remessa ou retorno industrializa��o);';
COMMENT ON    COLUMN    PPITOP.CODVENDA IS 'C�digo da venda (remessa ou retorno industrializa��o);';
COMMENT ON TABLE        PPMETODOANALISE IS 'Cadastro de m�todos de an�lise.';
COMMENT ON    COLUMN    PPMETODOANALISE.DESCMTANALISE IS 'Descri��o do tipo de an�lise.';
COMMENT ON    COLUMN    PPMETODOANALISE.TITULOANALISE IS 'T�tulo do m�todo anal�tico.';
COMMENT ON    COLUMN    PPMETODOANALISE.FONTEMTANALISE IS 'Fonte do m�todo anal�tico.';
COMMENT ON    COLUMN    PPMETODOANALISE.MATANALISE IS 'Material utilizado para a an�lise.';
COMMENT ON    COLUMN    PPMETODOANALISE.REAGANALISE IS 'Reagente da an�lise.';
COMMENT ON    COLUMN    PPMETODOANALISE.PROCANALISE IS 'Procedimentos';
COMMENT ON    COLUMN    PPMETODOANALISE.DTINS IS 'Data de inser��o do registro.';
COMMENT ON    COLUMN    PPMETODOANALISE.HINS IS 'Hora da inser��o do registro.';
COMMENT ON    COLUMN    PPMETODOANALISE.IDUSUINS IS 'Usu�rio que inseriu o registro';
COMMENT ON    COLUMN    PPMETODOANALISE.DTALT IS 'Data da ultima altera��o do registro.';
COMMENT ON    COLUMN    PPMETODOANALISE.HALT IS 'Hora da ultima altera��o no registro.';
COMMENT ON    COLUMN    PPMETODOANALISE.IDUSUALT IS 'Usu�rio que realizou a ultima altera��o no registro.';
COMMENT ON TABLE        PPOP IS 'Ordens de produ��o.';
COMMENT ON    COLUMN    PPOP.CODOP IS 'C�digo da ordem de produ��o.';
COMMENT ON    COLUMN    PPOP.SEQOP IS 'N�mero sequencial da OP.';
COMMENT ON    COLUMN    PPOP.DTEMITOP IS 'Data de emiss�o
';
COMMENT ON    COLUMN    PPOP.CODEMPPD IS 'C�digo da empresa do produto acabado.';
COMMENT ON    COLUMN    PPOP.CODFILIALPD IS 'C�digo da filial do produto acabado.';
COMMENT ON    COLUMN    PPOP.CODPROD IS 'C�digo do produto acabado.';
COMMENT ON    COLUMN    PPOP.SEQEST IS 'Numero sequencial da estrutura.';
COMMENT ON    COLUMN    PPOP.REFPROD IS 'Referencia do produto acabado.';
COMMENT ON    COLUMN    PPOP.DTFABROP IS 'Data de fabrica��o do produto acabado.';
COMMENT ON    COLUMN    PPOP.QTDSUGPRODOP IS 'Quantidade final sugerida de produ��o.';
COMMENT ON    COLUMN    PPOP.QTDPREVPRODOP IS 'Quantidade prevista a ser produzida.';
COMMENT ON    COLUMN    PPOP.QTDFINALPRODOP IS 'Quantidade realmente produzida.';
COMMENT ON    COLUMN    PPOP.QTDDISTPOP IS 'Quantidade distribu�da referente a OP principal.';
COMMENT ON    COLUMN    PPOP.QTDDISTIOP IS 'Quantidade distribu�da referente a OP de distribui��o.';
COMMENT ON    COLUMN    PPOP.DTVALIDPDOP IS 'Data de validade do lote do produto acabado.';
COMMENT ON    COLUMN    PPOP.CODEMPLE IS 'C�digo da empresa do lote do produto acabado.';
COMMENT ON    COLUMN    PPOP.CODFILIALLE IS 'C�digo da filial do lote do produto acabado.';
COMMENT ON    COLUMN    PPOP.CODLOTE IS 'C�digo do Lote para o produto acabado.';
COMMENT ON    COLUMN    PPOP.CODEMPTM IS 'C�digo da empresa do tipo de movimento.';
COMMENT ON    COLUMN    PPOP.CODFILIALTM IS 'C�digo da filial do tipo de movimento.';
COMMENT ON    COLUMN    PPOP.CODTIPOMOV IS 'C�digo do tipo de movimento.';
COMMENT ON    COLUMN    PPOP.CODEMPAX IS 'C�digo da empresa do almoxarifado.';
COMMENT ON    COLUMN    PPOP.CODFILIALAX IS 'Codigo da filial do almoxarifado.';
COMMENT ON    COLUMN    PPOP.CODALMOX IS 'C�digo do almoxarifado';
COMMENT ON    COLUMN    PPOP.CODEMPOPM IS 'C�digo da empresa da ordem de produ��o mestre (principal).';
COMMENT ON    COLUMN    PPOP.CODFILIALOPM IS 'C�digo da filial da ordem de produ��o mestre (principal).';
COMMENT ON    COLUMN    PPOP.CODOPM IS 'C�digo da ordem de produ��o mestre (principal).';
COMMENT ON    COLUMN    PPOP.SEQOPM IS 'Sequencial da ordem de produ��o mestre (principal).';
COMMENT ON    COLUMN    PPOP.SITOP IS 'Situa��o da OP.
PE - Pendente;
CA - Cancelada;
FN - Finalizada;
BL - Bloqueada;';
COMMENT ON    COLUMN    PPOP.OBSOP IS 'Campo para observa��es a cerca da ordem de produ��o.';
COMMENT ON    COLUMN    PPOP.JUSTFICQTDPROD IS 'Justificativa pela divergencia entre quantidade prevista e quantidade produzida.';
COMMENT ON    COLUMN    PPOP.JUSTIFICCANC IS 'Justificativa relativa ao cancelamento da OP.';
COMMENT ON    COLUMN    PPOP.DTCANC IS 'Data e hora do cancelamento da O.P.';
COMMENT ON    COLUMN    PPOP.IDUSUCANC IS 'Id do usu�rio que cancelou a O.P.';
COMMENT ON    COLUMN    PPOP.HCANC IS 'Hora do cancelamento da O.P.';
COMMENT ON    COLUMN    PPOP.CODEMPCP IS 'C�digo da empresa do item de compra vinculado � OP (convers�o de produtos)';
COMMENT ON    COLUMN    PPOP.CODFILIALCP IS 'C�digo da filial do item de compra vinculado � OP (convers�o de produtos)';
COMMENT ON    COLUMN    PPOP.CODCOMPRA IS 'C�digo da compra do item vinculado � OP (convers�o de produtos)';
COMMENT ON    COLUMN    PPOP.CODITCOMPRA IS 'C�digo do item de compra vinculado � OP (convers�o de produtos)';
COMMENT ON    COLUMN    PPOP.ESTDINAMICA IS 'Indica se a ordem de produ��o deve utilizar o mecanismo de estruturas din�micas.';
COMMENT ON    COLUMN    PPOP.GARANTIA IS 'Indica se a ordem de produ��o e proveniente de garantia.';
COMMENT ON    COLUMN    PPOP.CODEMPOS IS 'C�digo da empresa da Ordem de servi�o vinculada.';
COMMENT ON    COLUMN    PPOP.CODFILIALOS IS 'C�digo da filial da ordem de servi�o vinculada.';
COMMENT ON    COLUMN    PPOP.TICKET IS 'Ticket da ordem de servi�o vinculada.';
COMMENT ON    COLUMN    PPOP.CODITRECMERC IS 'C�digo do item de recebimento da OS vinculada.';
COMMENT ON    COLUMN    PPOP.CODITOS IS 'C�digo do item da OS vinculada.';
COMMENT ON    COLUMN    PPOP.DTINS IS 'Data de inser��o do registro.';
COMMENT ON    COLUMN    PPOP.HINS IS 'Hora de inser��o do registro.';
COMMENT ON    COLUMN    PPOP.IDUSUINS IS 'ID do usu�rio que inseriu o registro.';
COMMENT ON    COLUMN    PPOP.DTALT IS 'Data da �ltima altera��o no registro.';
COMMENT ON    COLUMN    PPOP.HALT IS 'Hora da �ltima altera��o no registro.';
COMMENT ON    COLUMN    PPOP.IDUSUALT IS 'Id do usu�rio que realizou a ultima altera��o no registro.';
COMMENT ON TABLE        PPOPACAOCORRET IS 'Cadastro de a��es corretivas.';
COMMENT ON    COLUMN    PPOPACAOCORRET.TPCAUSA IS 'Tipo de causas fundamentais:
"1M" - Material;
"2M" - M�quina;
"3M" - M�todo;
"4M" - Meio ambiente;
"5M" - M�o-de-obra;
"6M" - Medida;


';
COMMENT ON    COLUMN    PPOPACAOCORRET.TPACAO IS 'Tipo de a��o corretiva pr�-definida:
"II" - Inclus�o de insumos;
"DP" - Descarte da produ��o;';
COMMENT ON TABLE        PPOPCQ IS 'Cadastros de lan�amento de resultados de an�lises de controle de qualidade.';
COMMENT ON    COLUMN    PPOPCQ.CODEMP IS 'C�digo da empresa.';
COMMENT ON    COLUMN    PPOPCQ.CODFILIAL IS 'C�digo da filial.';
COMMENT ON    COLUMN    PPOPCQ.CODOP IS 'C�digo da ordem de produ��o.';
COMMENT ON    COLUMN    PPOPCQ.SEQOP IS 'Sequ�ncia da ordem de produ��o.';
COMMENT ON    COLUMN    PPOPCQ.SEQOPCQ IS 'Sequ�ncia do controle de qualidade.';
COMMENT ON    COLUMN    PPOPCQ.SEQAC IS 'Sequencial da a��o corretiva para o problema detectado.';
COMMENT ON    COLUMN    PPOPCQ.CODEMPEA IS 'C�digo da empresa da estrutura x analise.';
COMMENT ON    COLUMN    PPOPCQ.CODFILIALEA IS 'C�digo da filial da estrutura x analise.';
COMMENT ON    COLUMN    PPOPCQ.CODESTANALISE IS 'C�digo da estrutura x analise.';
COMMENT ON    COLUMN    PPOPCQ.SEQEF IS 'Sequencial da fase na estrutura.
';
COMMENT ON    COLUMN    PPOPCQ.VLRAFER IS 'Valor da aferi��o.';
COMMENT ON    COLUMN    PPOPCQ.DESCAFER IS 'Descritivo da aferi��o.';
COMMENT ON    COLUMN    PPOPCQ.STATUS IS 'Status da an�lise de controle de qualidade.
"PE" - Pendente;
"AP" - Aprovado;
"RC" - Recusado;
"CO" - Corrigido.';
COMMENT ON    COLUMN    PPOPCQ.DTINS IS 'Data de inser��o do registro.';
COMMENT ON    COLUMN    PPOPCQ.HINS IS 'Hora de inser��o do registro.';
COMMENT ON    COLUMN    PPOPCQ.IDUSUINS IS 'Usu�rio que inseriu o registro.';
COMMENT ON    COLUMN    PPOPCQ.DTALT IS 'Data de altera��o no registro.';
COMMENT ON    COLUMN    PPOPCQ.HALT IS 'Hora de altera��o do registro.';
COMMENT ON    COLUMN    PPOPCQ.IDUSUALT IS 'Usu�rio que alterou o registro.';
COMMENT ON    COLUMN    PPOPENTRADA.DTENT IS 'Data da entrada.';
COMMENT ON    COLUMN    PPOPENTRADA.HENT IS 'Hora da entrada.';
COMMENT ON TABLE        PPOPFASE IS 'V�nculo OP x Fase
';
COMMENT ON    COLUMN    PPOPFASE.SEQOP IS 'N�mero sequencial da OP.';
COMMENT ON    COLUMN    PPOPFASE.CODEMPTR IS 'C�digo da empresa para tabela de tipos de recurso.';
COMMENT ON    COLUMN    PPOPFASE.CODFILIALTR IS 'C�digo da filial para tabela de tipos de recurso.';
COMMENT ON    COLUMN    PPOPFASE.CODTPREC IS 'C�digo do tipo de recurso.';
COMMENT ON    COLUMN    PPOPFASE.DATAINIPRODFS IS 'Data de inicio da produ��o da fase.';
COMMENT ON    COLUMN    PPOPFASE.HINIPRODFS IS 'Hora de inicio da produ��o da fase.';
COMMENT ON    COLUMN    PPOPFASE.DATAFIMPRODFS IS 'Data de termino da produ��o da fase.';
COMMENT ON    COLUMN    PPOPFASE.HFIMPRODFS IS 'Hora final da produ��o da fase.';
COMMENT ON    COLUMN    PPOPFASE.OBSFS IS 'Observa��es referentes a fase de produ��o.';
COMMENT ON    COLUMN    PPOPFASE.SITFS IS 'Indica se da fase est� pendente ou finalizada.
"PE" - Pendente
"FN" - Finalizada';
COMMENT ON    COLUMN    PPOPITORC.QTDPROD IS 'Quantidade do item de or�amento que dever� ser produzida nessa OP. (previs�o)';
COMMENT ON    COLUMN    PPOPITORC.QTDFINALPRODITORC IS 'Quantidade final produzida para atendimento a este �tem de or�amento.
';
COMMENT ON TABLE        PPOPSUBPROD IS 'Tabela para lan�amento de sub-produtos em ordens de produ��o.';
COMMENT ON    COLUMN    PPOPSUBPROD.SEQOF IS 'Sequencial da fase
';
COMMENT ON    COLUMN    PPOPSUBPROD.CODEMPTM IS 'C�digo da empresa do tipo de movimento.';
COMMENT ON    COLUMN    PPOPSUBPROD.CODFILIALTM IS 'C�digo da filial do tipo de movimento';
COMMENT ON    COLUMN    PPOPSUBPROD.CODTIPOMOV IS 'C�digo do tipo de movimento.';
COMMENT ON    COLUMN    PPOPSUBPROD.DTSUBPROD IS 'Data para entrada da subproducao (finaliza��o da fase)
';
COMMENT ON TABLE        PPPROCESSAOPTMP IS 'Tabela tempor�ria para gera��o de ordens de produ��o com base em or�amentos.';
COMMENT ON    COLUMN    PPPROCESSAOPTMP.CODEMPET IS 'C�digo da empresa da esta��o de trabalho.';
COMMENT ON    COLUMN    PPPROCESSAOPTMP.CODFILIALET IS 'C�diga da filial da esta��o de trabalho.';
COMMENT ON    COLUMN    PPPROCESSAOPTMP.CODEST IS 'C�digo da esta��o de trabalho.';
COMMENT ON TABLE        PPRECURSO IS 'Recursos de produ��o
';
COMMENT ON TABLE        PPRETCP IS 'Reten��o de contra-provas (controle de qualidade).';
COMMENT ON TABLE        PPTIPOANALISE IS 'Tabela para cadastro dos tipos de an�lise de controle de qualidade.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODEMP IS 'C�digo da empresa.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODFILIAL IS 'C�digo da filial.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODTPANALISE IS 'C�digo do tipo de an�lise.';
COMMENT ON    COLUMN    PPTIPOANALISE.DESCTPANALISE IS 'Descri��o do tipo de an�lise.';
COMMENT ON    COLUMN    PPTIPOANALISE.OBSTPANALISE IS 'Observa��o relativa ao tipo de an�lise, pode descrever o m�todo de an�lise, instru��es de coleta e/ou aferi��o, etc...';
COMMENT ON    COLUMN    PPTIPOANALISE.TIPOEXPEC IS 'Tipo de registro de especifica��es.
"MM" - M�nimo e m�ximo;
"DT" - Descritivo.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODEMPUD IS 'C�digo da empresa da unidade.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODFILIALUD IS 'C�digo da filial da unidade.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODUNID IS 'C�digo da unidade de medida da an�lise.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODEMPMA IS 'C�digo da empresa do m�todo anal�tico.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODFILIALMA IS 'C�digo da filial do m�todo anal�tico.';
COMMENT ON    COLUMN    PPTIPOANALISE.CODMTANALISE IS 'C�digo do m�todo anal�tico.';
COMMENT ON    COLUMN    PPTIPOANALISE.DTINS IS 'Data de inser��o do registro.';
COMMENT ON    COLUMN    PPTIPOANALISE.HINS IS 'Hora de inser��o do registro.';
COMMENT ON    COLUMN    PPTIPOANALISE.IDUSUINS IS 'Usu�rio que inseriu o registro.';
COMMENT ON    COLUMN    PPTIPOANALISE.DTALT IS 'Data da altera��o no registro.';
COMMENT ON    COLUMN    PPTIPOANALISE.HALT IS 'Hora da altera��o no registro.';
COMMENT ON    COLUMN    PPTIPOANALISE.IDUSUALT IS 'Usu�rio que alterou o registro.';
COMMENT ON TABLE        PPTIPOREC IS 'Tipos de recurso de produ��o.
';
COMMENT ON    COLUMN    PVCAIXA.CODEMPET IS 'C�digo da empresa na tabela de esta��o de trabalho.';
COMMENT ON    COLUMN    PVCAIXA.CODFILIALET IS 'C�digo da filial na tabela de esta��es de trabalho.';
COMMENT ON    COLUMN    PVCAIXA.CODEST IS 'C�digo da esta��o de trabalho.';
COMMENT ON    COLUMN    PVCAIXA.SEQINI IS 'FAIXA INICIAL PARA SEQUENCIA DE VENDA DE ECF';
COMMENT ON    COLUMN    PVCAIXA.SEQMAX IS 'FAIXA FINAL PARA SEQUENCIA DE VENDA DE ECF';
COMMENT ON    COLUMN    PVCAIXA.ORCCAIXA IS 'Habilita a venda para pdv somente com or�amento.';
COMMENT ON TABLE        RHAREA IS 'Areas de neg�cio para cursos e vagas m�dulo RH (Recrutamento e sele��o).';
COMMENT ON    COLUMN    RHAREA.CODAREA IS 'C�digo da �rea.';
COMMENT ON TABLE        RHBENEFICIO IS 'Cadastro de benef�cios.';
COMMENT ON TABLE        RHCANDIDATO IS 'Cadastro de candidatos a vaga para uso no m�dulo de recrutamento e sele��o.';
COMMENT ON    COLUMN    RHCANDIDATO.UFCAND IS 'Unidade federativa do candidato.';
COMMENT ON    COLUMN    RHCANDIDATO.OUTROSEMPREGOS IS 'Outros empregos.';
COMMENT ON    COLUMN    RHCANDIDATO.CODEMPES IS 'C�digo da empresa do estado civil.';
COMMENT ON    COLUMN    RHCANDIDATO.CODFILIALES IS 'C�digo da filial do estado civil.';
COMMENT ON    COLUMN    RHCANDIDATO.CODESTCIVIL IS 'C�digo do estado civil.';
COMMENT ON    COLUMN    RHCANDIDATO.PRETENSAOSAL IS 'Pretens�o salarial.';
COMMENT ON    COLUMN    RHCANDIDATO.STCAND IS 'Status do candidato:
"IN" - Inativo;
"DI" - Dispon�vel;
"EN" - Encaminhado;
"EL" - Eliminado de processo seletivo;
"EF" - Efetivado;
"EM" - Empregado;
"EV" - Entrevistado;
"DL" - Desligado;';
COMMENT ON    COLUMN    RHCANDIDATO.OBSCAND IS 'Observa��es do candidato.';
COMMENT ON    COLUMN    RHCANDIDATO.NROFILHOS IS 'N�mero de filhos.';
COMMENT ON    COLUMN    RHCANDIDATO.ISENCTRANSP IS 'Indica se candidato � isento de tarifa de transporte - S/N';
COMMENT ON TABLE        RHCANDIDATOCARAC IS 'Tabela de vinculo entre candidatos e suas caracteristicas.';
COMMENT ON TABLE        RHCANDIDATOCURSO IS 'Tabela de vinculo entre candidatos e cursos.';
COMMENT ON TABLE        RHCANDIDATOFUNC IS 'Tabela de vinculo entre candidatos e funcoes exercidas.';
COMMENT ON TABLE        RHCANDIDATOSTATUS IS 'Tabela de hist�rico dos status do candidato.';
COMMENT ON    COLUMN    RHCANDIDATOSTATUS.DTSTATUS IS 'Data do status.';
COMMENT ON TABLE        RHCARACTERISTICA IS 'Cadastro de caracteristicas pessoais.';
COMMENT ON TABLE        RHCURSO IS 'Cadastro de cursos para utiliza��o no m�dulo de RH (Recrutamento e sele��o).';
COMMENT ON    COLUMN    RHCURSO.DESCCURSO IS 'Descri��o do curso.';
COMMENT ON    COLUMN    RHCURSO.CONTPROGCURSO IS 'Conte�do program�tico do curso.';
COMMENT ON    COLUMN    RHCURSO.INSTITUICAOCURSO IS 'Institui��o de ensino onde foi realizado o curso.';
COMMENT ON    COLUMN    RHCURSO.DURACAOCURSO IS 'Dura��o do curso (em meses).';
COMMENT ON    COLUMN    RHCURSO.CODAREA IS 'C�digo da �rea ';
COMMENT ON    COLUMN    RHCURSO.CODEMPAR IS 'C�digo da empresa da �rea.';
COMMENT ON    COLUMN    RHCURSO.CODFILIALAR IS 'C�digo da filial da �rea.';
COMMENT ON    COLUMN    RHCURSO.CODNIVELCURSO IS 'N�vel do curso';
COMMENT ON    COLUMN    RHCURSO.CODEMPNC IS 'C�digo da empresa do n�vel do curso.';
COMMENT ON    COLUMN    RHCURSO.CODFILIALNC IS 'C�digo da filial do n�vel do curso.';
COMMENT ON TABLE        RHDEPTO IS 'Cadastro de departamentos.';
COMMENT ON TABLE        RHEMPREGADO IS 'Cadastro de empregados.';
COMMENT ON    COLUMN    RHEMPREGADO.APELIDOEMPR IS 'Nome reduzido ou apelido para uso no cracha do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.DTADMISSAO IS 'Data de admiss�o do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.FOTOEMPR IS 'Foto do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.STATUSEMPR IS 'Status do empregado
"AD" - Admitido;
"DE" - Demitido;
"EF" - Em f�rias;
"LM" - Licen�a maternidade;
"AI" - Afastamento INSS;
"AP" - Aposentado;';
COMMENT ON    COLUMN    RHEMPREGADO.SEXOEMPR IS 'Sexo do empregado';
COMMENT ON    COLUMN    RHEMPREGADO.DTNASCEMPR IS 'Data de nascimento do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.CTPSEMPR IS 'Identifica��o da Carteira de Trabalho e Previd�ncia Social.';
COMMENT ON    COLUMN    RHEMPREGADO.SERIECTPSEMPR IS 'S�rie da carteira de trabalho e previd�ncia social.';
COMMENT ON    COLUMN    RHEMPREGADO.UFCTPSEMPR IS 'Estado de emiss�o da carteira de trabalho e previd�ncia social.';
COMMENT ON    COLUMN    RHEMPREGADO.CERTRESERVEMPR IS 'Certificado de reservista (Ex�rcito)';
COMMENT ON    COLUMN    RHEMPREGADO.PISPASEPEMPR IS 'PIS PASEP do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.RGEMPR IS 'RG do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.ORGEXPRHEMPR IS 'Org�o de expedi��o do RG do Empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.CPFEMPR IS 'CPF do Empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.UFRGEMPR IS 'Estado de expedi��o do RG.';
COMMENT ON    COLUMN    RHEMPREGADO.DTEXPRGEMPR IS 'Data de expedi��o do RG.';
COMMENT ON    COLUMN    RHEMPREGADO.TITELEITEMPR IS 'Titulo de eleitor.';
COMMENT ON    COLUMN    RHEMPREGADO.ZONAELEITEMPR IS 'Zona eleitoral do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.SECAOELEITEMPR IS 'Secao eleitoral do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.CNHEMPR IS 'Carteira nacional de habilita��o do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.MAEEMPR IS 'Nome da m�e do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.PAIEMPR IS 'Nome do pai do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.COMPLENDEMPR IS 'Complemento do endere�o do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.UFEMPR IS 'Estado do empregado (Endere�o).';
COMMENT ON    COLUMN    RHEMPREGADO.DTDEMISSAOEMPR IS 'Data de demiss�o do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.DDDEMPR IS 'DDD do telefone do cliente.';
COMMENT ON    COLUMN    RHEMPREGADO.FONE2EMPR IS 'Segundo telefone do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.CELEMPR IS 'Telefone celular do empregado.';
COMMENT ON    COLUMN    RHEMPREGADO.EMAILEMPR IS 'Email do empregado.';
COMMENT ON TABLE        RHEMPREGADOBENEF IS 'Tabela de v�nculos entre empregados e seus benef�cios.';
COMMENT ON TABLE        RHEMPREGADOR IS 'Cadastro de empregadores conveniados para m�dulo de recrutamento e sele��o.';
COMMENT ON    COLUMN    RHEMPREGADOR.NOMEEMPR IS 'Nome do empregador conveniado.';
COMMENT ON    COLUMN    RHEMPREGADOR.ATIVOEMPR IS 'Indica se empregador est� (A) ativo ou inativo (I).';
COMMENT ON    COLUMN    RHEMPREGADOR.CNPJEMPR IS 'CNPJ do empregador conveniado.';
COMMENT ON    COLUMN    RHEMPREGADOR.INSCEMPR IS 'Inscri��o estadual do empregador conveniado.';
COMMENT ON    COLUMN    RHEMPREGADOR.ENDEMPR IS 'Endere�o do empregador conveniado.';
COMMENT ON    COLUMN    RHEMPREGADOR.NUMEMPR IS 'N�mero do endere�o do empregador conveniado.';
COMMENT ON TABLE        RHEMPREGADOSAL IS 'Table de hist�rio salarial do empregado.';
COMMENT ON    COLUMN    RHEMPREGADOSAL.VALORSAL IS 'Valor do sal�rio mensal.';
COMMENT ON    COLUMN    RHEMPREGADOSAL.CUSTOHORATRAB IS 'Custo da hora trabalhada.';
COMMENT ON    COLUMN    RHEMPREGADOSAL.DTVIGOR IS 'Data de vigor do novo sal�rio.';
COMMENT ON    COLUMN    RHEMPREGADOSAL.OBSSAL IS 'Observa��es a respeito do sal�rio.';
COMMENT ON TABLE        RHFUNCAO IS 'Tabela de fun��es profissionais.';
COMMENT ON    COLUMN    RHFUNCAO.CBOFUNC IS 'C�digo brasileiro de ocupa��es (CBO)';
COMMENT ON TABLE        RHNIVELCURSO IS 'Cadastro dos n�veis dos cursos do m�dulo de RH (Recrutamento e sele��o).';
COMMENT ON TABLE        RHPONTO IS 'Tabela para registro do ponto dos funcion�rios.';
COMMENT ON    COLUMN    RHTABELAIRRF.DEDUCAO IS 'Dedu��o';
COMMENT ON TABLE        RHTURNO IS 'Tabela de turnos.';
COMMENT ON    COLUMN    RHTURNO.DESCTURNO IS 'Descri��o do turno.';
COMMENT ON    COLUMN    RHTURNO.NHSTURNO IS 'N�mero de horas semanais.';
COMMENT ON    COLUMN    RHTURNO.TIPOTURNO IS 'Tipo de turno:
N - Normal (Manh� e tarde)
M - Manh�
T - Tarde
O - Noite
E - Especial
';
COMMENT ON    COLUMN    RHTURNO.HINITURNO IS 'Hora inicial do turno.';
COMMENT ON    COLUMN    RHTURNO.HFIMTURNO IS 'Hora final do turno.';
COMMENT ON    COLUMN    RHTURNO.HINIINTTURNO IS 'Hora inicial do intervalo.';
COMMENT ON    COLUMN    RHTURNO.HFIMINTTURNO IS 'Hora final do intervalo.';
COMMENT ON    COLUMN    RHTURNO.TOLENTRADA IS 'Toler�ncia na entrada (minutos).';
COMMENT ON    COLUMN    RHTURNO.TOLSAIDA IS 'Toler�ncia na sa�da (minutos).';
COMMENT ON TABLE        RHVAGA IS 'Cadastro de vagas de emprego.';
COMMENT ON    COLUMN    RHVAGA.CODVAGA IS 'C�digo da vaga.';
COMMENT ON    COLUMN    RHVAGA.CODEMPR IS 'C�digo do empregador.';
COMMENT ON    COLUMN    RHVAGA.CODFUNC IS 'C�digo da fun��o.';
COMMENT ON    COLUMN    RHVAGA.CODEMPTN IS 'C�digo da empresa do turno.';
COMMENT ON    COLUMN    RHVAGA.CODFILIALTN IS 'C�digo da filial do turno.';
COMMENT ON    COLUMN    RHVAGA.CODTURNO IS 'C�digo do turno.';
COMMENT ON    COLUMN    RHVAGA.STVAGA IS 'Status da vaga:
"AB" - Aberta;
"SL" - Em processo de sele��o;
"CA" - Cancelada;
"PR" - Preenchida;';
COMMENT ON    COLUMN    RHVAGA.DTULTST IS 'Data do ultimo status.';
COMMENT ON TABLE        RHVAGACANDIDATO IS 'Tabela de v�nculo entre vaga e candidatos.';
COMMENT ON    COLUMN    RHVAGACANDIDATO.STVAGACAND IS 'Situa��o do candidato na vaga:
"EN" Encaminhado
"EF" Efetivado ';
COMMENT ON TABLE        RHVAGACARACQUALI IS 'Tabela de relacionamento entre vagas e caracteristicas qualificativas.';
COMMENT ON TABLE        RHVAGACARACREST IS 'Tabela de relacionamento entre vagas e atribui��es restritivas.';
COMMENT ON TABLE        RHVAGACURSO IS 'Tabela de v�nculo entre vagas e cursos.';
COMMENT ON TABLE        RHVAGASTATUS IS 'Tabela hist�rica dos status das vagas.';
COMMENT ON    COLUMN    RHVAGASTATUS.CODVAGA IS 'C�digo da vaga.';
COMMENT ON    COLUMN    RHVAGASTATUS.SQSTVAGA IS 'Sequencial do status da vaga.';
COMMENT ON    COLUMN    RHVAGASTATUS.STVAGA IS 'Status da vaga:
""AB"" - Aberta;
""SL"" - Em processo de sele��o;
""CA"" - Cancelada;
""PR"" - Preenchida;';
COMMENT ON    COLUMN    RHVAGASTATUS.DTSTATUS IS 'Data do ustatus.';
COMMENT ON    COLUMN    SGAGENDA.TIPOAGE IS 'Tipo do agente.';
COMMENT ON    COLUMN    SGAGENDA.CODAGE IS 'C�digo do agente.';
COMMENT ON    COLUMN    SGAGENDA.SITAGD IS 'Status do agendamento:
"PE" - Pendente;
"CA" - Cancelado;
"FN" - Finalizado;';
COMMENT ON    COLUMN    SGAGENDA.CODAGEEMIT IS 'C�digo do agente criador do agendamento';
COMMENT ON    COLUMN    SGAGENDA.CAAGD IS 'Controle de acesso ao agendamento
"PU" - publico
"PR" - privado
';
COMMENT ON    COLUMN    SGAGENDA.CODTIPOAGD IS 'C�digo do tipo de agendamento.';
COMMENT ON    COLUMN    SGAGENDA.CODEMPAR IS 'C�digo da empresa do agendamento vinculado (repetitivos).';
COMMENT ON    COLUMN    SGAGENDA.CODFILIALAR IS 'C�digo da filial do agendamento vinculado (repetitivos).';
COMMENT ON    COLUMN    SGAGENDA.TIPOAGEAR IS 'C�digo da tipo do agendamento vinculado (repetitivos).';
COMMENT ON    COLUMN    SGAGENDA.CODAGEAR IS 'C�digo do agente do agendamento vinculado (repetitivos).';
COMMENT ON    COLUMN    SGAGENDA.CODAGDAR IS 'C�digo do agendamento vinculado (repetitivos).';
COMMENT ON    COLUMN    SGAGENDA.DIATODO IS 'Indica se compromisso ocupar� o dia inteiro.';
COMMENT ON TABLE        SGAGENTE IS 'Tabela de pessoas, recursos e outros para os quais podemos agendar tarefas.';
COMMENT ON    COLUMN    SGAGENTE.CODEMP IS 'C�digo da empresa
';
COMMENT ON    COLUMN    SGAGENTE.CODFILIAL IS 'C�digo da filial';
COMMENT ON    COLUMN    SGAGENTE.TIPOAGE IS 'Tipo do agente:
SGUSU - Usu�rio
';
COMMENT ON    COLUMN    SGAGENTE.CODAGE IS 'C�digo do agente.';
COMMENT ON    COLUMN    SGAGENTE.DESCAGE IS 'Descri��o ou nome do agente.';
COMMENT ON    COLUMN    SGATRIBUSU.DTINS IS 'Data de inser��o.';
COMMENT ON    COLUMN    SGATRIBUSU.HINS IS 'Hora de inser��o.';
COMMENT ON    COLUMN    SGATRIBUSU.IDUSUINS IS 'ID do usu�rio que inseriu.';
COMMENT ON    COLUMN    SGATRIBUSU.DTALT IS 'Data da �ltima altera��o.';
COMMENT ON    COLUMN    SGATRIBUSU.HALT IS 'Hora da �ltima altera��o.';
COMMENT ON    COLUMN    SGATRIBUSU.IDUSUALT IS 'ID do �ltimo usu�rio que alterou.';
COMMENT ON TABLE        SGBAIRRO IS 'Tabela de cadastro de bairros.';
COMMENT ON    COLUMN    SGBAIRRO.VLRFRETE IS 'Valor de frete estimado para carga vinda deste bairro.';
COMMENT ON    COLUMN    SGBAIRRO.QTDFRETE IS 'Quantidade referente ao valor de frete.';
COMMENT ON TABLE        SGCNAE IS 'Tabela de atividades padr�o CNAE.';
COMMENT ON    COLUMN    SGEMPRESA.MULTIALMOXEMP IS 'Flag que indica se o estoque ser� controlado por multiplos almoxarifados.';
COMMENT ON    COLUMN    SGEMPRESA.PERCISSEMP IS 'Campo inutilizado (transfer�ncia para a tabela SGFILIAL)';
COMMENT ON    COLUMN    SGEMPRESA.CODPAISEMP IS 'Campo inutilizado (transfer�ncia para a tabela SGPAIS)';
COMMENT ON    COLUMN    SGESTACAO.MODODEMOEST IS 'Indica se a esta��o est� em modo demonstrativo.';
COMMENT ON    COLUMN    SGESTACAO.NFEEST IS 'Habilita NFE para esta��o de trabalho (S/N).';
COMMENT ON    COLUMN    SGESTACAO.TAMFONTETXT IS 'Tamanho da fonte para visualiza��o dos relat�rios texto.';
COMMENT ON    COLUMN    SGESTACAO.FONTETXT IS 'Nome da fonte para visualiza��o dos relat�rios texto.';
COMMENT ON    COLUMN    SGESTACAO.DTINS IS 'Data de inser��o.';
COMMENT ON    COLUMN    SGESTACAO.HINS IS 'Hora de inser��o.';
COMMENT ON    COLUMN    SGESTACAO.IDUSUINS IS 'ID do usu�rio que inseriu.';
COMMENT ON    COLUMN    SGESTACAO.DTALT IS 'Data da �ltima altera��o.';
COMMENT ON    COLUMN    SGESTACAO.HALT IS 'Hora da �ltima altera��o.';
COMMENT ON    COLUMN    SGESTACAO.IDUSUALT IS 'ID do �ltimo usu�rio que alterou.';
COMMENT ON    COLUMN    SGESTACAOBAL.DRIVERBAL IS 'Driver de comunica��o com a balan�a.';
COMMENT ON    COLUMN    SGESTACAOBAL.TIPOPROCRECMERC IS 'Indica o uso principal da balan�a.
"TO" - Todos;
"PI" - Pesagem inicial;
"TR" - Tiragem de renda/descarregamento;
"PF" - Pesagem final;';
COMMENT ON    COLUMN    SGESTACAOIMP.CODFILIAL IS 'C�digo da filial na tabela de esta��es de trabalho.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODEST IS 'C�digo da esta��o de trabalho.';
COMMENT ON    COLUMN    SGESTACAOIMP.NROIMP IS 'N�mero da impressora na esta��o.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODEMPIP IS 'C�digo da empresa na tabela de impressoras.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODFILIALIP IS 'C�digo da filial na tabela de impressoras.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODIMP IS 'C�digo da impressora.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODEMPPP IS 'C�digo da empresa na tabela de papel.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODFILIALPP IS 'C�digo da filial na tabela papel.';
COMMENT ON    COLUMN    SGESTACAOIMP.CODPAPEL IS 'C�digo do papel.';
COMMENT ON    COLUMN    SGESTACAOIMP.PORTAWIN IS 'Porta de impress�o no windows.';
COMMENT ON    COLUMN    SGESTACAOIMP.PORTALIN IS 'Porta de impress�o no linux.';
COMMENT ON    COLUMN    SGESTACAOIMP.IMPPAD IS 'Flag que define se a impressora � padr�o.';
COMMENT ON    COLUMN    SGESTACAOIMP.TIPOUSOIMP IS 'Tipo de uso da impressora.:
NF - Nota fiscal;
NS - Nota fiscal - servi�o;
PD - Pedido;
RS - Relat�rio simples;
RG - Relat�rio gr�fico;
TO - Todos;
';
COMMENT ON    COLUMN    SGESTACAOIMP.IMPGRAFICA IS 'Flag que define se a impress�o � gr�fica.';
COMMENT ON    COLUMN    SGESTACAOIMP.DTINS IS 'Data de inser��o do registro.';
COMMENT ON    COLUMN    SGESTACAOIMP.HINS IS 'Hora da inser��o.';
COMMENT ON    COLUMN    SGESTACAOIMP.IDUSUINS IS 'ID do usu�rio que inseriu.';
COMMENT ON    COLUMN    SGESTACAOIMP.DTALT IS 'Data da �ltima altera��o.';
COMMENT ON    COLUMN    SGESTACAOIMP.HALT IS 'Hora da �ltima altera��o.';
COMMENT ON    COLUMN    SGESTACAOIMP.IDUSUALT IS 'ID do �ltimo usu�rio que alterou.';
COMMENT ON TABLE        SGESTCIVIL IS 'Tabela de estados civis.';
COMMENT ON    COLUMN    SGESTCIVIL.DESCESTCIVIL IS 'Descri��o do estado civil.';
COMMENT ON    COLUMN    SGFERIADO.BANCFER IS 'Indica se � um feriado banc�rio.';
COMMENT ON    COLUMN    SGFERIADO.TRABFER IS 'Indica se � um feriado trabalhista.';
COMMENT ON    COLUMN    SGFILIAL.DDDFILIAL IS 'DDD da filial';
COMMENT ON    COLUMN    SGFILIAL.PERCSIMPLESFILIAL IS 'Percentual de tributa��o da empresa, no caso de enquadramento no simples.';
COMMENT ON    COLUMN    SGFILIAL.CODUNIFCOD IS 'C�digo na tabela de unifica��o de c�digos (SGUNIFCOD).';
COMMENT ON    COLUMN    SGFILIAL.INSCMUNFILIAL IS 'Inscri��o municipal';
COMMENT ON    COLUMN    SGFILIAL.CNAEFILIAL IS 'Classifica��o Nacional de Atividades Econ�micas (CNAE).';
COMMENT ON    COLUMN    SGFILIAL.PERCISSFILIAL IS 'Percentual de ISS pago pela filial.';
COMMENT ON    COLUMN    SGFILIAL.CONTRIBIPIFILIAL IS 'Indica se a filial � contribuinte do IPI.';
COMMENT ON    COLUMN    SGFILIAL.PERFILFILIAL IS 'Perfil de apresentacao do arquivo fiscal (SPED-EFD)
A - Perfil A
B - Perfil B
C - Perfil C';
COMMENT ON    COLUMN    SGFILIAL.INDATIVFILIAL IS 'Indicador de tipo de atividadade  (SPED-EFD)
0 - Industrial ou equiparado a industrial
1 - Outros';
COMMENT ON    COLUMN    SGFILIAL.CODEMPCO IS 'C�digo da empresa do fornecedor (contador)';
COMMENT ON    COLUMN    SGFILIAL.CODFILIALCO IS 'C�digo da filial do fornecedor (contador)';
COMMENT ON    COLUMN    SGFILIAL.CODFOR IS 'C�digo do fornecedor (contador)';
COMMENT ON    COLUMN    SGFILIAL.SUFRAMA IS 'C�digo da filial no suframa';
COMMENT ON TABLE        SGITPREFERE6 IS 'Tabela de prefer�ncias Febraban detalhada por banco e tipo (SIACC E CNAB)';
COMMENT ON    COLUMN    SGITPREFERE6.TIPOFEBRABAN IS 'Tipo de layout SIACC=01 ou CNAB=02';
COMMENT ON    COLUMN    SGITPREFERE6.CODCONV IS 'C�digo do conv�nio.';
COMMENT ON    COLUMN    SGITPREFERE6.CONVCOB IS 'Conv�nio de cobran�a do bloqueto.';
COMMENT ON    COLUMN    SGITPREFERE6.VERLAYOUT IS 'Vers�o do layout.';
COMMENT ON    COLUMN    SGITPREFERE6.IDENTSERV IS 'Identifica��o do servi�o.';
COMMENT ON    COLUMN    SGITPREFERE6.CONTACOMPR IS 'Conta compromisso.';
COMMENT ON    COLUMN    SGITPREFERE6.IDENTAMBCLI IS 'Identifica��o de ambiente do cliente. P=Produ��o - T=Teste.';
COMMENT ON    COLUMN    SGITPREFERE6.IDENTAMBBCO IS 'Identifica��o do ambiente do banco. P=Produ��o - T=Teste.';
COMMENT ON    COLUMN    SGITPREFERE6.NUMCONTA IS 'N�mero da conta corrente.';
COMMENT ON    COLUMN    SGITPREFERE6.FORCADTIT IS 'Forma de cadastramento do titulo.
1 - Com cadastro.
2 - Sem cadastro.';
COMMENT ON    COLUMN    SGITPREFERE6.TIPODOC IS 'Tipo de documento.
1 - Tradicional.
2 - Escrutiral.
';
COMMENT ON    COLUMN    SGITPREFERE6.IDENTEMITBOL IS 'Identifica��o da emiss�o de bloqueto.
1 - Banco emite.
2 - Cliente emite.
3 - Banco pr�-emite e o cliente completa.
4 - Banco reemite.
5 - Banco n�o reemite.
6 - Cobran�a sem papel.
Obs.: Os campos 4 e 5 s� ser�o aceitos para c�digo de movimento para remessa 31.';
COMMENT ON    COLUMN    SGITPREFERE6.IDENTDISTBOL IS 'Identifica��o da distribui��o.
1 - Banco.
2 - Cliente.';
COMMENT ON    COLUMN    SGITPREFERE6.ESPECTIT IS 'Especie do titulo.
01 - CH Cheque.
02 - DM Duplicata mercant�l.
03 - DMI Duplicata mercant�l p/ indica��o.
04 - DS Duplicata de servi�o.
05 - DSI DUplicata de servi�� p/ indica��o.
06 - DR Duplicata rural.
07 - LC Letra de cambio.
08 - NCC Nota de cr�dito comercial.
09 - NCE Nota de cr�dito a exporta��o.
10 - NCI Nota de cr�dito ind�stria.
11 - NCR Nota de cr�dito rural.
12 - NP Nota promiss�ria.
13 - NPR Nota promiss�ria rural.
14 - TM Triplicata mercant�l.
15 - TS Triplicata de servi�o.
16 - NS Nota de seguro.
17 - RC Recibo.
18 - FAT Fatura.
19 - ND Nota de d�bito.
20 - AP Apolice de seguro.
21 - ME Mensalidade escolar.
22 - PC Parcela de cons�rcio.
99 - Outros.

';
COMMENT ON    COLUMN    SGITPREFERE6.CODJUROS IS 'C�digo do juros de mora.
1 - Valor por dia.
2 - Taxa nensal.
3 - Isento.
';
COMMENT ON    COLUMN    SGITPREFERE6.VLRPERCJUROS IS 'Valor ou percentual do juros.
';
COMMENT ON    COLUMN    SGITPREFERE6.CODDESC IS 'C�digo do desconto.
1 - Valor fixo at� a data informada.
2 - Percentual at� a data informada.
3 - Valor por antecipa��o por dia corrido.
4 - Valor por antecipa��o por dia util.
5 - Percentual sobre o valor nominal dia corrido.
6 - Percentual sobre o valor nominal dia util.
Obs.: Para as op��es 1 e 2 ser� obrigat�rio a informa��o da data.';
COMMENT ON    COLUMN    SGITPREFERE6.VLRPERCDESC IS 'Valor ou percentual do desconto.';
COMMENT ON    COLUMN    SGITPREFERE6.CODPROT IS 'C�digo para protesto.
1 - Dias corridos.
2 - Dias ut�is.
3 - N�o protestar.';
COMMENT ON    COLUMN    SGITPREFERE6.DIASPROT IS 'N�mero de dias para protesto.';
COMMENT ON    COLUMN    SGITPREFERE6.CODBAIXADEV IS 'C�digo para baixa/devolu��o.
1 - Baixar/Devolver.
2 - N�o baixar/ N�o devolver.';
COMMENT ON    COLUMN    SGITPREFERE6.DIASBAIXADEV IS 'N�mero de dias para a Baixa / Devolu��o.';
COMMENT ON    COLUMN    SGITPREFERE6.ACEITE IS 'Defini o aceite do arquivo de cnab S - sim e N - n�o';
COMMENT ON    COLUMN    SGITPREFERE6.PADRAOCNAB IS 'Indica o padr�o CNAB, pode ser: 240 ou 400
';
COMMENT ON    COLUMN    SGITPREFERE6.PADRAOSIACC IS 'Indica o padr�o SIACC, pode ser: 150 ou 240
';
COMMENT ON    COLUMN    SGLOG.CLASLOG IS 'Classificacao do log
PR - Procedimento
ER - Erro 
';
COMMENT ON    COLUMN    SGLOG.TIPOLOG IS 'Tipo do log:
LIB - Libera��o de venda abaixo do custo
';
COMMENT ON    COLUMN    SGLOG.DESCLOG IS 'Descri��o resumida da opera��o
';
COMMENT ON    COLUMN    SGLOG.OBSLOG IS 'Descri��o completa e observa��es sobre a opera��o
';
COMMENT ON TABLE        SGLOGCRUD IS 'Log das a��es de insert, update e delete.';
COMMENT ON    COLUMN    SGLOGCRUD.ID IS 'Identifica��o';
COMMENT ON    COLUMN    SGLOGCRUD.TABLENAME IS 'Nome da tabela';
COMMENT ON    COLUMN    SGLOGCRUD.OPERATION IS 'Opera��o:
U - Update
D - Delete
I - Insert
';
COMMENT ON    COLUMN    SGLOGCRUD.EVENTLOG IS 'Evento:
B - Before antes da operac�o
A - After ap�s a opera��o';
COMMENT ON    COLUMN    SGLOGCRUD.DTLOG IS 'Data da opera��o';
COMMENT ON    COLUMN    SGLOGCRUD.HLOG IS 'Hora da opera��o';
COMMENT ON    COLUMN    SGLOGCRUD.IDUSU IS 'ID do usu�rio';
COMMENT ON    COLUMN    SGLOGCRUD.XML IS 'Conte�do do registro antes ou depois da altera��o.';
COMMENT ON    COLUMN    SGMODETIQUETA.EECMODETIQ IS 'N�mero de espa�os entre colunas.';
COMMENT ON    COLUMN    SGMODETIQUETA.COMPRIMIDO IS 'Indica se deve imprimir comprimido.';
COMMENT ON    COLUMN    SGMODETIQUETA.POSSCRIPT IS 'indica se a etiqueta � p�s script.';
COMMENT ON    COLUMN    SGMODETIQUETA.MODETIQ IS 'indica o nome do jasper.';
COMMENT ON TABLE        SGMUNICIPIO IS 'Tabela de municipios.';
COMMENT ON TABLE        SGPAIS IS 'Tabela de Paises';
COMMENT ON    COLUMN    SGPAIS.CODPAIS IS 'C�digo do pa�s segundo o ISO 3166-1';
COMMENT ON    COLUMN    SGPAIS.SIGLA3CPAIS IS 'Sigla do pa�s com 3 caracteres.';
COMMENT ON    COLUMN    SGPAIS.DDIPAIS IS 'C�digo de discagem direta a distancia do pais (ddi)';
COMMENT ON    COLUMN    SGPAIS.SIGLA2CPAIS IS 'Sigla do pais com 2 caracteres';
COMMENT ON    COLUMN    SGPAIS.CODBACENPAIS IS 'C�digo do pa�s na tabela do BACEN (Brasil=1058)';
COMMENT ON    COLUMN    SGPAIS.CODEANPAIS IS 'C�digo do pa�s na tabela EAN.';
COMMENT ON TABLE        SGPREFERE1 IS 'Prefer�ncias do m�dulo STD (Gerais).';
COMMENT ON    COLUMN    SGPREFERE1.USAORCSEQ IS 'FLAG QUE INDICA SE USA PEDIDO SEQUENCIAL NO OR�AMENTO';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOV2 IS 'Tipo de movimento para or�amento';
COMMENT ON    COLUMN    SGPREFERE1.TIPOPREFCRED IS 'Indica quando deve ser feita a verifica��o do cr�dito:
FV - Fechamento de venda;
II - Inser��o de �tem;
AB - Ambos;';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOV4 IS 'Tipo de movimento para servi�os';
COMMENT ON    COLUMN    SGPREFERE1.TABFRETEVD IS 'Indica se a aba frete ser� obrigat�ria na tela de vendas.
';
COMMENT ON    COLUMN    SGPREFERE1.TABADICVD IS 'Indica se a aba adicionais ser� obrigat�ria na tela de vendas.
';
COMMENT ON    COLUMN    SGPREFERE1.TRAVATMNFVD IS 'Trava tela de venda para n�o receber tipo de movimento de NF na inser��o.
';
COMMENT ON    COLUMN    SGPREFERE1.TIPOVALIDORC IS 'Op��o de validade para impress�o nos or�amentos:
"N"=n�mero de dias ; 
"D" data 
';
COMMENT ON    COLUMN    SGPREFERE1.CLIMESMOCNPJ IS 'Permitir clientes com mesmo CNPJ:
S-SIM N-N�O
';
COMMENT ON    COLUMN    SGPREFERE1.CNPJOBRIGCLI IS 'CNPJ Obrigat�rio para o cadastro de clientes S ou N
';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPTN IS 'C�digo da empresa para transportadora';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALTN IS 'C�digo da filial para transportadora.';
COMMENT ON    COLUMN    SGPREFERE1.CODTRAN IS 'C�digo da transportadora.';
COMMENT ON    COLUMN    SGPREFERE1.ESTLOTNEG IS 'Permitir estoque de lote negativo
';
COMMENT ON    COLUMN    SGPREFERE1.NATVENDA IS 'CFOP habilitada na tela de venda
';
COMMENT ON    COLUMN    SGPREFERE1.IPIVENDA IS 'Habilita campo IPI ';
COMMENT ON    COLUMN    SGPREFERE1.CUSTOSICMS IS 'Pre�o de custo na compra sem ICMS.';
COMMENT ON    COLUMN    SGPREFERE1.CASASDECFIN IS 'Define as casas decimas para assuntos financeiros';
COMMENT ON    COLUMN    SGPREFERE1.COMISPDUPL IS 'C�lculo de comiss�es por duplicata
"S" calcula pelas parcelas do contas a receber.
"N" calcula pelo valor da venda.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPT6 IS 'C�digo da empresa para tipo de movimento de invent�rio';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALT6 IS 'C�digo da filial para tipo de movimento de invent�rio';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOV6 IS 'C�digo de tipo de movimento de invent�rio';
COMMENT ON    COLUMN    SGPREFERE1.BLOQCOMPRA IS 'FLAG PARA BLOQUEAR A COMPRA';
COMMENT ON    COLUMN    SGPREFERE1.VENDAMATPRIM IS 'Flag para permitir venda de mat�ria prima (S/N).';
COMMENT ON    COLUMN    SGPREFERE1.VENDAPATRIM IS 'Flag para permitir venda de mat�ria prima (S/N)';
COMMENT ON    COLUMN    SGPREFERE1.PEPSPROD IS 'Flag que indica se deve aparecer o campo custo peps na tela de cadastro de produtos.';
COMMENT ON    COLUMN    SGPREFERE1.CNPJFOROBRIG IS 'Flag que indica se o CNPJ do fornecedor � obrigat�rio.';
COMMENT ON    COLUMN    SGPREFERE1.INSCESTFOROBRIG IS 'Flag que indica se a insc. est. do fornecedor � obrigat�ria.';
COMMENT ON    COLUMN    SGPREFERE1.BUSCAPRODSIMILAR IS 'Flag que indica se ao entrar com c�digo de produto em textfield dever� buscar os produtos similares.';
COMMENT ON    COLUMN    SGPREFERE1.MULTIALMOX IS 'Flag que indica o trabalho com multiplos almoxarifados.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPT8 IS 'Cod. emp. do tipo de movimento padr�o para RMA.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALT8 IS 'Cod. filial do tipo de movimento padr�o para RMA.';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOV8 IS 'Codigo do tipo de movimento padr�o para RMA.';
COMMENT ON    COLUMN    SGPREFERE1.ESTNEGGRUP IS 'Define se controla o estoque negativo por grupo.';
COMMENT ON    COLUMN    SGPREFERE1.USATABPE IS 'Flag para usar tabela de prazos de entrega.';
COMMENT ON    COLUMN    SGPREFERE1.DESCCOMPPED IS 'Indica se ser� utilizada a descri��o completa nos or�amentos e pedidos.';
COMMENT ON    COLUMN    SGPREFERE1.OBSCLIVEND IS 'Flag para mostrar observa��es do cliente na tela de venda.';
COMMENT ON    COLUMN    SGPREFERE1.CONTESTOQ IS 'Flag que define se o sistema vai trabalhar com estoque.';
COMMENT ON    COLUMN    SGPREFERE1.DIASPEDT IS 'S - mostra data de entrega no pedido
N - mostra n�mero de dias para entrega';
COMMENT ON    COLUMN    SGPREFERE1.RECALCPCVENDA IS 'flag que marca se deve recalcular os valores do item quando alterar o cabe�alho';
COMMENT ON    COLUMN    SGPREFERE1.RECALCPCORC IS 'flag que marca se deve recalcular os valores do item quando alterar o cabe�alho';
COMMENT ON    COLUMN    SGPREFERE1.USALAYOUTPED IS 'define se usa layout proprio para pedido';
COMMENT ON    COLUMN    SGPREFERE1.VERIFALTPARCVENDA IS 'FLAG INDICA SE FAZ VERIFICA��O PARA ALTERAR PARCELA DA VENDA';
COMMENT ON    COLUMN    SGPREFERE1.BUSCACODPRODGEN IS 'BUSCA GENERICA DO C�DIGO DO PRODUTO';
COMMENT ON    COLUMN    SGPREFERE1.FILBUSCGENPROD IS 'FILTAR POR PRODUTO NA BUSCA COM O C�DIGO DE BARRAS';
COMMENT ON    COLUMN    SGPREFERE1.FILBUSCGENREF IS 'FILTAR POR REFERENCIA NA BUSCA COM O C�DIGO DE BARRAS';
COMMENT ON    COLUMN    SGPREFERE1.FILBUSCGENCODBAR IS 'FILTAR POR CODIGO DE BARRAS NA BUSCA COM O C�DIGO DE BARRAS';
COMMENT ON    COLUMN    SGPREFERE1.FILBUSCGENCODFAB IS 'FILTAR POR CODIGO DO FABRICANTE NA BUSCA COM O C�DIGO DE BARRAS';
COMMENT ON    COLUMN    SGPREFERE1.FILBUSCGENCODFOR IS 'Busca produro por c�digo de fornecedor.';
COMMENT ON    COLUMN    SGPREFERE1.BUSCAVLRULTCOMPRA IS 'Busca o valor da ultima compra na tela de compras.';
COMMENT ON    COLUMN    SGPREFERE1.USAIMGASSORC IS 'Flag para usar imagem de assinatura no or�amento.';
COMMENT ON    COLUMN    SGPREFERE1.IMGASSORC IS 'Imagem de assinatura pra or�amento.';
COMMENT ON    COLUMN    SGPREFERE1.CONSISTCPFCLI IS 'Indica se deve consistir CPF no cliente';
COMMENT ON    COLUMN    SGPREFERE1.CONSISTEIECLI IS 'Indica se deve consistir IE no cliente';
COMMENT ON    COLUMN    SGPREFERE1.CONSISTEIEFOR IS 'Indica se deve consistir IE no fornecedor.';
COMMENT ON    COLUMN    SGPREFERE1.CONSISTECPFFOR IS 'Indica se deve validar o CPF do fornecedor pessoa f�sica.';
COMMENT ON    COLUMN    SGPREFERE1.USANOMEVENDORC IS 'Flag "S" para indicar o uso de nome do vendedor no or�amento, caso contr�rio utilizar� o nome da empresa.';
COMMENT ON    COLUMN    SGPREFERE1.SISCONTABIL IS 'Seleciona o sistema de contabilidade utilizado.
00 - Nenhum
01 - Freedom Cont�bil
02 - Safe Cont�bil
';
COMMENT ON    COLUMN    SGPREFERE1.ATBANCOIMPBOL IS 'Flag que indica se o banco poder� ser atualizado na tela de impress�o de boleto.';
COMMENT ON    COLUMN    SGPREFERE1.TIPOCODBAR IS 'Tipo de c�digo de barras.
1-EAN
2-39';
COMMENT ON    COLUMN    SGPREFERE1.ADICORCOBSPED IS 'Flag que indica se deve carregar os c�digos dos or�amentos nas observa��es dos pedidos, gerados a partir de or�amentos.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPMENSORC IS 'C�digo da empresa para a mensagem padr�o para or�amento.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALMENSORC IS 'C�digo da filial para mensagem padr�o para or�amento.';
COMMENT ON    COLUMN    SGPREFERE1.CODMENSORC IS 'C�digo da mensagem padr�o para or�amento.';
COMMENT ON    COLUMN    SGPREFERE1.CUSTOCOMPRA IS 'Habilita para digita��o campo de custo na compra.';
COMMENT ON    COLUMN    SGPREFERE1.TABTRANSPCP IS 'Indica se deve mostrar aba de transportadora na tela de compra.';
COMMENT ON    COLUMN    SGPREFERE1.TABTRANSPORC IS 'Indica se deve mostrar aba de transportadora na tela de or�amento.';
COMMENT ON    COLUMN    SGPREFERE1.TABSOLCP IS 'Habilita e desabilita aba de solicita��o na tela de compras.';
COMMENT ON    COLUMN    SGPREFERE1.ADICFRETEBASEICM IS 'Indica se deve permitir adicionar frete � base de calculo do ICMS.
';
COMMENT ON    COLUMN    SGPREFERE1.PRECOCPREL IS 'Indica se deve mostrar o pre�o de compra nos relat�rios de compra.';
COMMENT ON    COLUMN    SGPREFERE1.MULTICOMIS IS 'Habilita e desabilita multi-comissionados.';
COMMENT ON    COLUMN    SGPREFERE1.USUATIVCLI IS 'S - Define controle de acesso para ativa��o de cliente por usu�rio.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPHISTREC IS 'C�digo da empresa do hist�rico padr�o para lan�amentos financeiros provenientes do contas a receber.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALHISTREC IS 'C�digo da filial do hist�rico padr�o para lan�amentos financeiros provenientes do contas a receber.';
COMMENT ON    COLUMN    SGPREFERE1.CODHISTREC IS 'C�digo do hist�rico padr�o para lan�amentos financeiros provenientes do contas a receber.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPHISTPAG IS 'C�digo da empresa do hist�rico padr�o para lan�amentos financeiros provenientes do contas a pagar.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALHISTPAG IS 'C�digo da filial do hist�rico padr�o para lan�amentos financeiros provenientes do contas a pagar.';
COMMENT ON    COLUMN    SGPREFERE1.CODHISTPAG IS 'C�digo do hist�rico padr�o para lan�amentos financeiros provenientes do contas a pagar.';
COMMENT ON    COLUMN    SGPREFERE1.ESTITRECALTDTVENC IS 'Flag que indica se estorna recebimento ao alterar data de vencimento da parcela com baixa automatica.';
COMMENT ON    COLUMN    SGPREFERE1.LCREDGLOBAL IS 'Indica se a libera��o de cr�dito deve ser agrupada, para clientes com sub-clientes.';
COMMENT ON    COLUMN    SGPREFERE1.VDMANUTCOMOBRIG IS 'Indica se o filtro de vendedor � obrigat�ria na tela de manuten��o de comiss�es.';
COMMENT ON    COLUMN    SGPREFERE1.CLASSPED IS 'Classe padr�o para pedidos gr�ficos.';
COMMENT ON    COLUMN    SGPREFERE1.TIPOCLASSPED IS 'Indica o tipo de classe padr�o para or�amentos.
QA - Resultset como par�metro (Query na Aplica��o);
QJ - Parametros de filtro (Query no Jasper).
';
COMMENT ON    COLUMN    SGPREFERE1.USAIBGECLI IS 'Indica se deve usar a tabela de IBGE de cidades para o cadastro de clientes.';
COMMENT ON    COLUMN    SGPREFERE1.USAIBGEFOR IS 'Indica se deve usar a tabela de IBGE de cidades para o cadastro de fornecedores.';
COMMENT ON    COLUMN    SGPREFERE1.USAIBGETRANSP IS 'Indica se deve usar a tabela de IBGE de cidades para o cadastro de transportadoras.';
COMMENT ON    COLUMN    SGPREFERE1.SOMAVOLUMES IS 'Indica se deve somar as quantidades dos itens de venda e lan�ar no campo "volumes".';
COMMENT ON    COLUMN    SGPREFERE1.BUSCACEP IS 'Indica se deve habilitar o bot�o para busca de ceps nos cadastros.';
COMMENT ON    COLUMN    SGPREFERE1.URLWSCEP IS 'URL do web service de busca de endere�o pelo cep.';
COMMENT ON    COLUMN    SGPREFERE1.CLASSCP IS 'Classe padr�o para pedido de compra.';
COMMENT ON    COLUMN    SGPREFERE1.LABELOBS01CP IS 'Descri��o para o campo coringa obs01 da tabela CPCOMPRA.';
COMMENT ON    COLUMN    SGPREFERE1.LABELOBS02CP IS 'Descri��o para o campo coringa obs02 da tabela CPCOMPRA.';
COMMENT ON    COLUMN    SGPREFERE1.LABELOBS03CP IS 'Descri��o para o campo coringa obs03 da tabela CPCOMPRA.';
COMMENT ON    COLUMN    SGPREFERE1.LABELOBS04CP IS 'Descri��o para o campo coringa obs04 da tabela CPCOMPRA.';
COMMENT ON    COLUMN    SGPREFERE1.CONSISTEIEPF IS 'Indica se deve consistir a inscri��o estadual de clientes do tipo pessoa f�sica.';
COMMENT ON    COLUMN    SGPREFERE1.CREDICMSSIMPLES IS 'Indica se deve destacar cr�dito de ICMS (empesa simples.)';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPMS IS 'C�digo da empresa da mensagem de destaque de ICMS de empresa enquadrada no simples.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALMS IS 'C�digo da filial da mensagem de destaque de ICMS para empresa enquadrada no simples.';
COMMENT ON    COLUMN    SGPREFERE1.CODMENSICMSSIMPLES IS 'C�digo da mensagem a ser destacada na nota fiscal, quando empresa destacar cr�dito de icms, estando enquadrada no simples.';
COMMENT ON    COLUMN    SGPREFERE1.GERACOMISVENDAORC IS 'Indica se deve gerar gerar comiss�o padr�o nas vendas 
geradas atrav�s de busca de or�amentos.';
COMMENT ON    COLUMN    SGPREFERE1.GERACODUNIF IS 'Indica se deve gerar c�digo unificado na tabela SGUNIFCOD 
para uso como destinatario ou remetente no conhecimento de frete.';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOV9 IS 'C�digo do tipo de movimento padr�o para conhecimento de frete.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALT9 IS 'C�digo da filial do tipo de movimento para conheciemento de frete.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPT9 IS 'C�digo da empresa do tipo de moviento padr�o para conhecimento de frete.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPJP IS 'C�digo da empresa da conta de planejamento de Juros Pagos.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALJP IS 'C�digo da filial da conta de planejamento de Juros Pagos.';
COMMENT ON    COLUMN    SGPREFERE1.CODPLANJP IS 'C�digo da conta de planejamento de Juros Pagos.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPJR IS 'C�digo da empresa da conta de planejamento de Juros Recebidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALJR IS 'C�digo da filial da conta de planejamento de Juros Recebidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODPLANJR IS 'C�digo da conta de planejamento de Juros Recebidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPDR IS 'C�digo da empresa da conta de planejamento de Descontos recebidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALDR IS 'C�digo da filial da conta de planejamento de Descontos recebidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODPLANDR IS 'C�digo da conta de planejamento de Descontos recebidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPDC IS 'C�digo da empresa da conta de planejamento de Descontos concedidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALDC IS 'C�digo da filial da conta de planejamento de Descontos concedidos.';
COMMENT ON    COLUMN    SGPREFERE1.CODPLANDC IS 'C�digo da conta de planejamento de Descontos concedidos.';
COMMENT ON    COLUMN    SGPREFERE1.GERAPAGEMIS IS 'Gera��o de contas a pagar por data de emiss�o.';
COMMENT ON    COLUMN    SGPREFERE1.LANCAFINCONTR IS 'Indica se deve habilitar o vinculo entre lan�amento financeiro (fnsublanca) e contrato/projeto. Para
apura��o de custos.';
COMMENT ON    COLUMN    SGPREFERE1.LANCARMACONTR IS 'Indica se eve habilitar o vinculo entre rma e contrato/projeto. Para apura��o de custos.';
COMMENT ON    COLUMN    SGPREFERE1.VISUALIZALUCR IS 'Indica se deve habilitar a aba "Lucratividade" na tela de venda.';
COMMENT ON    COLUMN    SGPREFERE1.CLASSNFE IS 'Classe do plugin de integra��o Nfe';
COMMENT ON    COLUMN    SGPREFERE1.DIRNFE IS 'Diret�rio padr�o para arquivos NFE (windows).';
COMMENT ON    COLUMN    SGPREFERE1.DIRNFELIN IS 'Diret�rio padr�o para arquivos NFE (linux).';
COMMENT ON    COLUMN    SGPREFERE1.FORMATODANFE IS '1 - Retrato, 2 - Paisagem';
COMMENT ON    COLUMN    SGPREFERE1.AMBIENTENFE IS '1 - Produ��o, 2 - Homologa��o';
COMMENT ON    COLUMN    SGPREFERE1.PROCEMINFE IS 'Identificador do processo de emiss�o da NFe:
0 - emiss�o de NF-e com aplicativo do contribuinte;
3 - emiss�o NF-e pelo contribuinte com aplicativo fornecido pelo Fisco.';
COMMENT ON    COLUMN    SGPREFERE1.VERPROCNFE IS 'Identificador da vers�o do processo de emiss�o (informar a ers�o do aplicativo emissor de NFe)';
COMMENT ON    COLUMN    SGPREFERE1.KEYLICNFE IS 'Chave de licenciamento NFE.
';
COMMENT ON    COLUMN    SGPREFERE1.DTVENCTONFE IS 'Data de vencimento da licen�a NFE.
';
COMMENT ON    COLUMN    SGPREFERE1.INFADPRODNFE IS 'Indica se deve incluir as informa��es adicionais do produto na nota fiscal eletronica (Campo HinfAdProd)';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPNF IS 'C�digo da empresa do email padr�o para envio de nfe.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALNF IS 'C�digo da filial do email padr�o para envio nfe';
COMMENT ON    COLUMN    SGPREFERE1.CODEMAILNF IS 'C�digo do email padr�o para envio de nfe.';
COMMENT ON    COLUMN    SGPREFERE1.EXIBEPARCOBSDANFE IS 'Indica se deve adicionar o desdobramento das parcelas nas observa��es da DANFE.';
COMMENT ON    COLUMN    SGPREFERE1.REGIMETRIBNFE IS 'Regime tribut�rio para NFE.
1 - Simples Nacional
2 - Simples Nacional (excesso de sub-limite)
3 - Normal';
COMMENT ON    COLUMN    SGPREFERE1.INFCPDEVOLUCAO IS 'Indica se deve informar a compra na devolu��o.';
COMMENT ON    COLUMN    SGPREFERE1.INFVDREMESSA IS 'Indica de informa nota de remessa.';
COMMENT ON    COLUMN    SGPREFERE1.GERARECEMIS IS 'Indica se deve gerar contas a receber a partir da data de emiss�o do pedido.';
COMMENT ON    COLUMN    SGPREFERE1.RETENSAOIMP IS 'Indica se deve realizar a retens�o de impostos na emiss�o da nota, reduzindo o valor liquido da nota fiscal.';
COMMENT ON    COLUMN    SGPREFERE1.TIPOCUSTOLUC IS 'Informa qual o tipo de custo deve ser usado no c�lculo de lucratividade de or�amentos/pedidos.
U - Ultima compra;
M - MPM
P - PEPS';
COMMENT ON    COLUMN    SGPREFERE1.TABIMPORTCP IS 'Indica se deve habilitar a aba importa��o na tela de compras.';
COMMENT ON    COLUMN    SGPREFERE1.HABVLRTOTITORC IS 'Indica se deve habilitar o valor total do item para digita��o, na tela de or�amentos.';
COMMENT ON    COLUMN    SGPREFERE1.USABUSCAGENPRODCP IS 'Indica se deve realizar a busca gen�rica de produtos na compra.';
COMMENT ON    COLUMN    SGPREFERE1.ADICOBSORCPED IS 'Indica se deve transferir as observa��es do or�amento para o pedido.';
COMMENT ON    COLUMN    SGPREFERE1.USAPRECOCOT IS 'Indica se deve utilizar o pre�o de cota��es para pedidos de compras.';
COMMENT ON    COLUMN    SGPREFERE1.BLOQPRECOAPROV IS 'Indica se deve bloquear o faturamento de pedido de compra com pre�o n�o aprovado.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPFT IS 'C�digo da empresa do tipo de fornecedor para transportadoras.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALFT IS 'C�digo da filial do tipo de fornecedor para transportadoras.';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOFORFT IS 'C�digo do tipo de fornecedor para transportadoras.';
COMMENT ON    COLUMN    SGPREFERE1.USAPRECOCOMIS IS 'Indica se deve utilizar o pre�o para comissionamento do cadastro de produtos, para calculo de comissioament especial por se��o de produ��o.';
COMMENT ON    COLUMN    SGPREFERE1.ESPECIALCOMIS IS 'Indica se deve utilizar o mecanismo de comissionamento especial (por setor de produ��o)';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALTS IS 'C�digo da filial do tipo de movimento para or�amentos de servi�o.';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOVS IS 'C�digo do tipo de movimento para or�amentos de servi�o.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPTS IS 'C�digo da empresa do tipo de movimento para or�amentos de servi�o.';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPSV IS 'C�digo da empresa de plano de pagamento sem valor financeiro para uso em devolu��es e afins.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALSV IS 'C�digo da filial de plano de pagamento sem valor financeiro para uso em devolu��es e afins.';
COMMENT ON    COLUMN    SGPREFERE1.CODPLANOPAGSV IS 'C�digo de plano de pagamento sem valor financeiro para uso em devolu��es e afins.';
COMMENT ON    COLUMN    SGPREFERE1.ARREDPRECO IS 'Indice de arredondamento de precos:
null ou 0 = sem arredondamento;
> 0 fator de arredondamento decimal';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPPC IS 'C�digo da empresa padr�o planejamento para pagamento com cheques.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALPC IS 'C�digo da empresa para o planejamento padr�o para pagamento com cheques.';
COMMENT ON    COLUMN    SGPREFERE1.CODPLANPC IS 'C�digo do planejamento padr�o para pagamento com cheques.';
COMMENT ON    COLUMN    SGPREFERE1.TPNOSSONUMERO IS 'Indica o padr�o para gera��o do nosso n�mero (boletos e arquivos de remessa)
D - N�mero do documento (doc)
R - N�mero do contas a receber (codrec)
S - Sequencial �nico (recomendado)';
COMMENT ON    COLUMN    SGPREFERE1.IMPDOCBOL IS 'Define se o n�mero  NF ser� impresso no campo documento do boleto (S/N).
';
COMMENT ON    COLUMN    SGPREFERE1.FECHACAIXA IS 'Indica se deve bloquear caixas para lan�amentos retroativos.';
COMMENT ON    COLUMN    SGPREFERE1.FECHACAIXAAUTO IS 'Indica se deve realizar o fechamento de caixas automaticamente, 
ou atrav�s de procedimento manual.';
COMMENT ON    COLUMN    SGPREFERE1.NUMDIGIDENTTIT IS 'Numero de digitos para campo de identificacao do titulo em arquivos de remessa padrao cnab (parametro corretivo para periodo intermediario entre a versao 1.2.4.1 e 1.2.4.2);';
COMMENT ON    COLUMN    SGPREFERE1.KEYLICEFD IS 'Chave de licenciamento do m�dulo Sped EFD.';
COMMENT ON    COLUMN    SGPREFERE1.DTVENCTOEFD IS 'Data de vencimento da licencao do SPED Efd.';
COMMENT ON    COLUMN    SGPREFERE1.ENCORCPROD IS 'Indica se deve encaminhar or�amentos contendo produtos acabados para a produ��o (Sistema Pull)
';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPIM IS 'C�digo do tipo de movimento para NF de importa��o.';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALIM IS 'C�digo da filial do tipo de movimento para NF de importa��o';
COMMENT ON    COLUMN    SGPREFERE1.CODTIPOMOVIM IS 'C�digo do tipo de movimento para nota fiscal de importa��o.';
COMMENT ON    COLUMN    SGPREFERE1.COMISSAODESCONTO IS 'Indica se deve habilitar o mecanismo de comissionamento progressivo, de acordo com o desconto concedido.
"S" - Sim
"N" - N�o
';
COMMENT ON    COLUMN    SGPREFERE1.CODEMPHC IS 'C�digo da empresa para hist�rico padr�o para baixa CNAB.
';
COMMENT ON    COLUMN    SGPREFERE1.CODFILIALHC IS 'C�digo da filial para hist�rico padr�o para baixa CNAB.
';
COMMENT ON    COLUMN    SGPREFERE1.CODHISTCNAB IS 'C�digo do hist�rico padr�o para baixa CNAB.
';
COMMENT ON    COLUMN    SGPREFERE1.ALINHATELALANCA IS 'Indica se deve alinhar a tela de lan�amentos financeiros a direta.
S - Sim
N - N�o
';
COMMENT ON    COLUMN    SGPREFERE1.IDENTCLIBCO IS 'Identifica��o cliente (SIACC)- D - Documento - CPF/CNPJ / C - C�digo do cliente ';
COMMENT ON    COLUMN    SGPREFERE1.VDPRODQQCLAS IS 'Permite venda de produto independentemente da classifica��o.';
COMMENT ON    COLUMN    SGPREFERE1.UTILIZATBCALCCA IS 'Utiliza tabela para c�lculo do custo de aquisi��o.';
COMMENT ON    COLUMN    SGPREFERE1.BLOQPEDVD IS 'Bloqueia pedido de venda ap�s emiss�o.';
COMMENT ON TABLE        SGPREFERE2 IS 'Prefer�ncias do m�dulo ATD.';
COMMENT ON    COLUMN    SGPREFERE2.CABTERMR01 IS 'Cabe�alho 1 do termo de recebimento.';
COMMENT ON    COLUMN    SGPREFERE2.CABTERMR02 IS 'Cabe�alho 2 do termo de recebimento.';
COMMENT ON    COLUMN    SGPREFERE2.RODTERMR IS 'Rodap� do termo de recebimento.';
COMMENT ON TABLE        SGPREFERE3 IS 'Preferencias do m�dulo TMK.';
COMMENT ON    COLUMN    SGPREFERE3.AUTOHORATEND IS 'Define se utiliza contagem automatica do tempo de atendimento.';
COMMENT ON    COLUMN    SGPREFERE3.CODATIVCE IS 'C�digo da atividade padr�o para envio de campanha.';
COMMENT ON    COLUMN    SGPREFERE3.CODATIVTE IS 'C�digo da atividade padr�o para envio de campanha frustrado.';
COMMENT ON    COLUMN    SGPREFERE3.BLOQATENDCLIATRASO IS 'Indica se deve bloquear atendimentos para clientes em atraso. S - Sim
N - N�o
';
COMMENT ON    COLUMN    SGPREFERE3.MOSTRACLIATRASO IS 'Indica se deve exibir informa��o de cliente em atraso, na tela de gerenciamento de atendimentos.
S - Sim
N - N�o';
COMMENT ON    COLUMN    SGPREFERE3.CODEMPNC IS 'C�digo da empresa do email de notifica��o de chamados a t�cnicos designados.';
COMMENT ON    COLUMN    SGPREFERE3.CODFILIALNC IS 'C�digo da filial do email de notifica��o de chamados a t�cnicos designados.';
COMMENT ON    COLUMN    SGPREFERE3.CODEMAILNC IS 'C�digo do email de notifica��o de chamados a t�cnicos designados.';
COMMENT ON    COLUMN    SGPREFERE3.CODEMPEC IS 'C�digo da empresa do email de notifica��o de chamados ao cliente.
';
COMMENT ON    COLUMN    SGPREFERE3.CODFILIALEC IS 'C�digo da filial do email de notifica��o de chamados ao cliente.
';
COMMENT ON    COLUMN    SGPREFERE3.CODEMAILEC IS 'C�digo do email de notifica��o de chamados ao cliente.
';
COMMENT ON TABLE        SGPREFERE4 IS 'Prefer�ncias do m�dulo de PDV
';
COMMENT ON    COLUMN    SGPREFERE4.CODEMPCL IS 'C�digo da empresa para cliente.';
COMMENT ON    COLUMN    SGPREFERE4.CODFILIALCL IS 'C�digo filial para cliente.';
COMMENT ON    COLUMN    SGPREFERE4.CODCLI IS 'C�digo do cliente padr�o para venda.';
COMMENT ON    COLUMN    SGPREFERE4.CODEMPPD IS 'CODIGO DA EMPRESA DO PRODUTO PARA FRETE';
COMMENT ON    COLUMN    SGPREFERE4.CODFILIALPD IS 'CODIGO DA FILIAL DO PRODUTO PARA FRETE';
COMMENT ON    COLUMN    SGPREFERE4.CODPROD IS 'CODIGO DO PRODUTO PRA FRETE';
COMMENT ON    COLUMN    SGPREFERE4.ADICPDV IS 'flag que indica se mostra frete no fechamento da venda no PDV';
COMMENT ON    COLUMN    SGPREFERE4.APROVORC IS 'FLAG QUE PERMITE APROVA��O DO OR�AMENTO DA TELA DE CADASTRO DO OR�AMENTO';
COMMENT ON    COLUMN    SGPREFERE4.DIASVENCORC IS 'DIAS PARA O VENCIMENTO DO OR�AMENTO.';
COMMENT ON    COLUMN    SGPREFERE4.USABUSCAGENPROD IS 'indica se abilita o campo para fazer a busca generica de produtos';
COMMENT ON    COLUMN    SGPREFERE4.AUTOFECHAVENDA IS 'Flag indica se chama a tela de fecha venda automatico quando buscar or�amento.';
COMMENT ON    COLUMN    SGPREFERE4.USALOTEORC IS 'Flag que indica se usa lote na tela de or�amento.';
COMMENT ON    COLUMN    SGPREFERE4.HABRECEBER IS 'Habilita aba de receber no fechamento da venda de ECF.';
COMMENT ON TABLE        SGPREFERE5 IS 'Prefer�ncias do m�dulo de PCP
';
COMMENT ON    COLUMN    SGPREFERE5.MESESDESCCP IS 'N�mero de meses para descarte de an�lises de contra-prova.';
COMMENT ON    COLUMN    SGPREFERE5.CODTIPOMOV IS 'C�digo do tipo de movimento padr�o para Ordem de produ��o.';
COMMENT ON    COLUMN    SGPREFERE5.CODEMPTM IS 'C�digo da empresa do tipo de movimento padr�o para Ordem de Produ��o.';
COMMENT ON    COLUMN    SGPREFERE5.CODFILIALTM IS 'C�digo da filial do tipo de movimento padr�o para Ordem de Produ��o.';
COMMENT ON    COLUMN    SGPREFERE5.SITRMAOP IS 'Situa��o da RMA gerada pela OP.
Pode ser:
"PE" - Pendente
"AF" - Aprovada 
"EF" - Expedida ';
COMMENT ON    COLUMN    SGPREFERE5.IMGASSRESP IS 'Imagem da assinatura do respons�vel t�cnico.';
COMMENT ON    COLUMN    SGPREFERE5.BAIXARMAAPROV IS 'Indica se deve baixar o estoque de insumos em RMA"s aprovadas n�o expedidas.';
COMMENT ON    COLUMN    SGPREFERE5.RATAUTO IS 'Defini rateio automatico de itens de OP.';
COMMENT ON    COLUMN    SGPREFERE5.APAGARMAOP IS 'Indica se deve permitir a exclus�o de RMAs geradas por outro usu�rio.
Regra se aplica apenas em RMA vinculadas a Ordens de produ��o.';
COMMENT ON    COLUMN    SGPREFERE5.NOMERELANAL IS 'Indica qual o nome a ser impresso no relat�rio de an�lise.
"U" - Usu�rio de cadastrou a an�se;
"R" - Respons�vel pela produ��o;';
COMMENT ON    COLUMN    SGPREFERE5.SITPADOP IS 'Status padr�o da OP ap�s inser��o.
"PE" Pendente
"FN" Finalizada';
COMMENT ON    COLUMN    SGPREFERE5.SITPADOPCONV IS 'Status padr�o da OP de convers�o de produtos ap�s inser��o.
"PE" Pendente
"FN" Finalizada';
COMMENT ON    COLUMN    SGPREFERE5.HABCONVCP IS 'Indica se deve habilitar convers�o de produtos na compra.';
COMMENT ON    COLUMN    SGPREFERE5.PRODETAPAS IS 'Indica se deve permitir a finaliza��o de Ops em etapas.';
COMMENT ON    COLUMN    SGPREFERE5.CODEMPTS IS 'C�digo da empresa para tipo de movimento para entrada de subprodutos.
';
COMMENT ON    COLUMN    SGPREFERE5.CODFILIALTS IS 'C�digo da filial para tipo de movimento para entrada de subprodutos.
';
COMMENT ON    COLUMN    SGPREFERE5.CODTIPOMOVSP IS 'C�digo do tipo de movimento para entrada de subprodutos.
';
COMMENT ON    COLUMN    SGPREFERE5.CODEMPEN IS 'C�digo da empresa do tipo de movimento padr�o para envio de remessa para produ��o externa.';
COMMENT ON    COLUMN    SGPREFERE5.CODFILIALEN IS 'C�digo da filial do tipo de movimento padr�o para envio de remessa para produ��o externa.';
COMMENT ON    COLUMN    SGPREFERE5.CODTIPOMOVEN IS 'C�digo do tipo de movimento padr�o para envio de remessa para produ��o externa.';
COMMENT ON    COLUMN    SGPREFERE5.CODEMPRE IS 'C�digo da empresa do tipo de movimento padr�o para retorno de produ��o externa.';
COMMENT ON    COLUMN    SGPREFERE5.CODFILIALRE IS 'C�digo da filial do tipo de movimento padr�o para retorno de produ��o externa.';
COMMENT ON    COLUMN    SGPREFERE5.CODTIPOMOVRE IS 'C�digo do tipo de movimento padr�o para retorno de produ��o externa.';
COMMENT ON TABLE        SGPREFERE6 IS 'Tabela de prefer�ncias Febraban (SIACC E CNAB)';
COMMENT ON    COLUMN    SGPREFERE6.NOMEEMP IS 'Nome da empresa para remessa.';
COMMENT ON    COLUMN    SGPREFERE6.NOMEEMPCNAB IS 'Nome da empresa para cnab.';
COMMENT ON TABLE        SGPREFERE7 IS 'Tabela de prefer�ncias para mecanismo de venda consignada.';
COMMENT ON    COLUMN    SGPREFERE7.CODTIPOMOVCO IS 'Tipo de movimento de consigna��o.';
COMMENT ON    COLUMN    SGPREFERE7.CODTIPOMOVTV IS 'Tipo de movimento de pedido de venda.';
COMMENT ON    COLUMN    SGPREFERE7.CODTIPOMOVTP IS 'Tipo de movimento de venda.';
COMMENT ON    COLUMN    SGPREFERE7.CODEMPPV IS 'C�digo da empresa do planejamento padr�o para venda consignada.';
COMMENT ON    COLUMN    SGPREFERE7.CODEMPPC IS 'C�digo da empresa do planejamento padr�o para consigna��o.';
COMMENT ON    COLUMN    SGPREFERE7.CODFILIALPC IS 'C�digo da filial do planejamento padr�o para consigna��o.';
COMMENT ON    COLUMN    SGPREFERE7.CODPLANCONSIG IS 'C�digo do planejamento padr�o para consigna��o.';
COMMENT ON    COLUMN    SGPREFERE7.CODFILIALPV IS 'C�digo da filial do planejamento padr�o para venda consignada.';
COMMENT ON    COLUMN    SGPREFERE7.CODPLANVDCONSIG IS 'C�digo do planejamento padr�o para venda consignada.';
COMMENT ON TABLE        SGPREFERE8 IS 'Tabela de preferencias do m�dulo GMS.';
COMMENT ON    COLUMN    SGPREFERE8.CODEMPTR IS 'C�digo da empresa para o tipo de recep��o padr�o para recebimento com pesagem.';
COMMENT ON    COLUMN    SGPREFERE8.CODFILIALTR IS 'C�digo da filial para o tipo de recep��o padr�o para recebimento com pesagem.';
COMMENT ON    COLUMN    SGPREFERE8.CODTIPORECMERC IS 'C�digo do tipo de recep��o padr�o para recebimento com pesagem.';
COMMENT ON    COLUMN    SGPREFERE8.CODEMPCM IS 'C�digo da empresa to tipo de recebimento para coletas';
COMMENT ON    COLUMN    SGPREFERE8.CODFILIALCM IS 'C�digo da filial do tipo de recebimento para coleta.';
COMMENT ON    COLUMN    SGPREFERE8.CODTIPORECMERCCM IS 'C�digo do tipo de recebimento para coleta.';
COMMENT ON    COLUMN    SGPREFERE8.CODEMPTC IS 'C�digo da empresa para o tipo de movimento de compra gera a partir de pedidos de compra.';
COMMENT ON    COLUMN    SGPREFERE8.CODFILIALTC IS 'C�digo da filial para o tipo de movimento de compra gerada a partir de pedidos de compra.';
COMMENT ON    COLUMN    SGPREFERE8.CODTIPOMOVTC IS 'C�digo do tipo de movimento padr�o para compra, gerada a partir de pedidos de compra. ';
COMMENT ON    COLUMN    SGPREFERE8.GERACHAMADOOS IS 'Indica se deve gerar chamado (CRM) a partir de itens de ordem de servi�o.';
COMMENT ON    COLUMN    SGPREFERE8.USAPRECOPECASERV IS 'Indica se deve utilizar o pre�o do pe�a consertada no or�amento de servi�os.';
COMMENT ON    COLUMN    SGPREFERE8.CODEMPDS IS 'C�digo da empresa para o tipo de movimento padr�o para devolu��o de pe�as consertadas.';
COMMENT ON    COLUMN    SGPREFERE8.CODFILIALDS IS 'C�digo da filial para o tipo de movimento padr�o devolu��o de pe�as consertadas.';
COMMENT ON    COLUMN    SGPREFERE8.CODTIPOMOVDS IS 'C�digo do tipo de movimento padr�o para devolu��o de pe�as consertadas.';
COMMENT ON    COLUMN    SGPREFERE8.CODEMPSE IS 'C�digo da empresa do produto padr�o para servi�os.';
COMMENT ON    COLUMN    SGPREFERE8.CODFILIALSE IS 'C�digo da filial para produto padr�o para servi�o.';
COMMENT ON    COLUMN    SGPREFERE8.CODPRODSE IS 'C�digo do produto padr�o para servi�o.';
COMMENT ON    COLUMN    SGTABELA.SIGLATB IS 'Sigla da tabela.';
COMMENT ON    COLUMN    SGTIPOAGENDA.CODTIPOAGD IS 'C�digo do tipo de agendamento';
COMMENT ON TABLE        SGUF IS 'Tabela de Unidades Federativas.';
COMMENT ON    COLUMN    SGUF.REGIAOUF IS 'Regi�o geogr�fica
N - Norte
NE - Nordeste
S - Sul
SE - Sudeste
CO - Centro Oeste';
COMMENT ON TABLE        SGUNIFCOD IS 'Tabela de unifica��o de c�digos para realiza��o de v�nculos em tabelas heterog�neas;';
COMMENT ON    COLUMN    SGUNIFCOD.TIPOUNIFCOD IS 'Tipo de c�digo:
"C" - Cliente;
"F" - Fornecedor;
"T" - Transportadora;
"E" - Empresa/Filial;';
COMMENT ON    COLUMN    SGUNIFCOD.DESCUNIFCOD IS 'Descri��o do c�digo unificado (no caso de cliente/fornecedor/transportadora utilizar a raz�o social).';
COMMENT ON    COLUMN    SGUSUARIO.BAIXOCUSTOUSU IS 'Indica se o usu�rio pode liberar venda abaixo do custo.';
COMMENT ON    COLUMN    SGUSUARIO.APROVCPSOLICITACAOUSU IS 'Indica o n�vel de aprova��o do usuario para solicita��o de compras.
ND : Nenhuma
CC : Mesmo Centro de Custo
TD : Todas';
COMMENT ON    COLUMN    SGUSUARIO.APROVRMAUSU IS 'Indica o n�vel de aprova��o do usuario para RMA.
ND : Nenhuma
CC : Mesmo Centro de Custo
TD : Todas';
COMMENT ON    COLUMN    SGUSUARIO.ALTPARCVENDA IS 'FLAG INDICA DE PODE ALTERAR PARCELA DA VENDA';
COMMENT ON    COLUMN    SGUSUARIO.APROVRECEITA IS 'Permite que o usuario aprove venda de produto com receita.';
COMMENT ON    COLUMN    SGUSUARIO.ATIVCLI IS 'Define se o usu�rio tem acesso para ativar clientes.';
COMMENT ON    COLUMN    SGUSUARIO.CORAGENDA IS 'Cor representativa para visualiza��o na agenda corporativa.';
COMMENT ON    COLUMN    SGUSUARIO.CODEMPCE IS 'C�digo da empresa para configura��o de email.';
COMMENT ON    COLUMN    SGUSUARIO.CODFILIALCE IS 'C�digo da filial para configura��o de email.';
COMMENT ON    COLUMN    SGUSUARIO.CODCONFEMAIL IS 'C�digo da configura��o de email.';
COMMENT ON    COLUMN    SGUSUARIO.CANCELAOP IS 'Permite que o usu�rio cancele Ordens de Produ��o geradas
por outros usu�rios.';
COMMENT ON    COLUMN    SGUSUARIO.VENDAPATRIMUSU IS 'Define se o usu�rio pode liberar venda de produtos do patrim�nio (imobilizado).';
COMMENT ON    COLUMN    SGUSUARIO.RMAOUTCC IS 'Indica se permite criar RMA em outros Centros de custo.';
COMMENT ON    COLUMN    SGUSUARIO.ATIVOUSU IS 'Indica se o usu�rio est� ativo.';
COMMENT ON    COLUMN    SGUSUARIO.VISUALIZALUCR IS 'Indica se o usu�rio pode visualizar a aba lucratividade na tela de venda.';
COMMENT ON    COLUMN    SGUSUARIO.LIBERACAMPOPESAGEM IS 'Indica se o usu�rio possui permiss�o para digita��o
do peso nas telas de pesagem (recebimento de mercadoria)';
COMMENT ON TABLE        SVOS IS 'Cabe�alho para ordem de servi�o.
';
COMMENT ON TABLE        TKATIVIDADE IS 'Tabela de atividades.';
COMMENT ON TABLE        TKCAMPANHA IS 'Tabela de cadastro de campanhas de marketing.';
COMMENT ON    COLUMN    TKCAMPANHA.CODCAMP IS 'C�digo da campanha.';
COMMENT ON    COLUMN    TKCAMPANHA.DESCCAMP IS 'Descri��o da campanha.';
COMMENT ON    COLUMN    TKCAMPANHA.OBSCAMP IS 'Observa��es relativas � campanha.';
COMMENT ON TABLE        TKCAMPANHACLI IS 'Tabela de relacionamento entre clientes e campanhas.';
COMMENT ON    COLUMN    TKCAMPANHACLI.CODCAMP IS 'C�digo da campanha.';
COMMENT ON    COLUMN    TKCAMPANHACLI.CODCLI IS 'C�digo do cliente.';
COMMENT ON TABLE        TKCAMPANHACTO IS 'Tabela de relacionamento entre contatos e campanhas.';
COMMENT ON    COLUMN    TKCAMPANHACTO.CODCAMP IS 'C�digo da campanha.';
COMMENT ON    COLUMN    TKCAMPANHACTO.CODCTO IS 'C�digo do contato.';
COMMENT ON TABLE        TKCAMPANHAEMAIL IS 'Tabela de relacionamento entre campanhas e seus respectivos emails associados.';
COMMENT ON TABLE        TKCONFEMAIL IS 'Tabela de configura��es para utiliza��o nos emails de campanhas de marketing.';
COMMENT ON    COLUMN    TKCONFEMAIL.ASSINATREMET IS 'Assinatura do email, do remetente.';
COMMENT ON TABLE        TKCONTATO IS 'Tabela de contatos.';
COMMENT ON    COLUMN    TKCONTATO.CODORIGCONT IS 'C�digo da origem do contato.';
COMMENT ON    COLUMN    TKCONTATO.CODEMPTO IS 'C�digo da empresa do tipo de contato.';
COMMENT ON    COLUMN    TKCONTATO.CODFILIALTO IS 'C�digo da filial do tipo de contato.';
COMMENT ON    COLUMN    TKCONTATO.CODTIPOCONT IS 'C�digo do tipo de contato.';
COMMENT ON    COLUMN    TKCONTATO.CELCTO IS 'Nro de telefone celular do contato';
COMMENT ON TABLE        TKCTOATIV IS 'Tabela de relacionamento entre contatos e atividades.';
COMMENT ON TABLE        TKCTOGRPINT IS 'Tabela de relacionamento entre contatos e os grupos de interesse.';
COMMENT ON TABLE        TKEMAIL IS 'Tabela de emails para campanhas de marketing.';
COMMENT ON    COLUMN    TKEMAIL.FORMATO IS 'text/plain ou text/html';
COMMENT ON    COLUMN    TKEMAIL.CHARSET IS 'Formato da pagina de c�digos.';
COMMENT ON    COLUMN    TKHISTORICO.TIPOHISTTK IS 'Tipo de hist�rico: 
H - Hist�rico 
V - Visita ao cliente
N - Visita a novos clientes(n�o cadastrados)
C - Campanha
O - Cobran�a
L - Liga��o Pr�-venda
P - Liga��o P�s-venda
I - Indefinida';
COMMENT ON    COLUMN    TKHISTORICO.CODEMPCA IS 'C�digo da empresa da campanha.';
COMMENT ON    COLUMN    TKHISTORICO.CODFILIALCA IS 'C�digo da filial da campanha.';
COMMENT ON    COLUMN    TKHISTORICO.CODCAMP IS 'C�digo da campanha.';
COMMENT ON    COLUMN    TKHISTORICO.SEQSITCAMP IS 'Sequencia da situa��o da campanha (para fk com tksitcamp)';
COMMENT ON TABLE        TKORIGCONT IS 'Tabela de origens de contatos ex: lista telefonica, pesquisa na internet, panfletagem, etc...';
COMMENT ON TABLE        TKSITCAMP IS 'Tabela da situa��o do cantato com rela��o � campanha, de acordo com a atividade desenvolvida.';
COMMENT ON    COLUMN    VDCLASCLI.SIGLACLASCLI IS 'Sigla ou abrevia��o da descri��o da classifica��o do cliente (utilizado em alguns relat�rios)';
COMMENT ON TABLE        VDCLCOMIS IS 'Classifica��o de comiss�es
';
COMMENT ON TABLE        VDCLIAUTP IS 'Pessoas autorizadas a comprar em nome do cliente';
COMMENT ON    COLUMN    VDCLIAUTP.NOMEAUTP IS 'Nome da pessoa autorizada a comprar em nome do cliente.';
COMMENT ON    COLUMN    VDCLIAUTP.ENDAUTP IS 'Endereco da pessoa autorizada a comprar em nome do cliente.';
COMMENT ON    COLUMN    VDCLIAUTP.NUMAUTP IS 'N�mero do endere�o da pessoa autorizada a comprar em nome do cliente.';
COMMENT ON    COLUMN    VDCLIAUTP.COMPLAUTP IS 'Complemento do endere�o da pessoa autorizada a comprar em nome do cliente.';
COMMENT ON TABLE        VDCLICOMPL IS 'Ficha de informa��es complementares do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.CARGOTRABCLI IS 'Cargo ocupado pelo cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.DTADMTRABCLI IS 'Data de admiss�o do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.DDDTRABCLI IS 'DDD do local de trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.FONETRABCLI IS 'Telefone do local de trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.ENDTRABCLI IS 'Endere�o do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.NUMTRABCLI IS 'N�mero do logradouro do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.BAIRTRABCLI IS 'Bairro do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.CIDTRABCLI IS 'Cidade do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.UFTRABCLI IS 'Estado (UF) do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.OUTRENDACLI IS 'Outras rendas do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.FONTRENDACLI IS 'Fonte das outras rendas do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.NOMECONJCLI IS 'Nome do conjuge do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.DTNASCCONJCLI IS 'Data de nascimento do conjuge do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.RENDACONJCLI IS 'Renda do conjuge do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.COMPLTRABCLI IS 'Complemento de endereco do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.CEPTRABCLI IS 'Cep do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.RAMALTRABCLI IS 'Ramal do trabalho do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.RGCONJCLI IS 'RG do conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.SSPCONJCLI IS 'Orgao de espedicao do RG do conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.CPFCONJCLI IS 'CPF do conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.NATCONJCLI IS 'Naturalidade do conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.UFNATCONJCLI IS 'Estado de onde e natural o conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.CARGOCONJCLI IS 'Cargo do conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.EMPTRABCONJCLI IS 'Empresa onde trabalha o conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.DTADMTRABCONJCLI IS 'Data de admissao do conjuge.';
COMMENT ON    COLUMN    VDCLICOMPL.NOMEAVALCLI IS 'Nome do avalista.';
COMMENT ON    COLUMN    VDCLICOMPL.DTNASCAVALCLI IS 'Data de nascimento do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.RGAVALCLI IS 'Rg do avalista.';
COMMENT ON    COLUMN    VDCLICOMPL.SSPAVALCLI IS 'Orgao de espedicao do Rg do avalista.';
COMMENT ON    COLUMN    VDCLICOMPL.CPFAVALCLI IS 'CPF do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.ENDAVALCLI IS 'Endereco do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.NUMAVALCLI IS 'Numero no endereco do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.BAIRAVALCLI IS 'Bairro do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.CIDVALCLI IS 'Cidade do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.CEPAVALCLI IS 'Cep do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.UFAVALCLI IS 'Estado do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.DDDAVALCLI IS 'DDD do telefone do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.FONEAVALCLI IS 'Fone do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.EMPTRABAVALCLI IS 'Empresa onde trabalha o avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.DTADMTRABAVALCLI IS 'Data de admissao do avalista do cliente.';
COMMENT ON    COLUMN    VDCLICOMPL.CARGOAVALCLI IS 'Cargo do avalista do cliente.';
COMMENT ON TABLE        VDCLICONTAS IS 'Contas bancarias dos clientes.';
COMMENT ON TABLE        VDCLIENTE IS 'Clientes.';
COMMENT ON    COLUMN    VDCLIENTE.CODEMPEC IS 'C�digo da empresa para tabela de estado civil.';
COMMENT ON    COLUMN    VDCLIENTE.CODFILIALEC IS 'C�digo da filial para tabela de estado civil.';
COMMENT ON    COLUMN    VDCLIENTE.CODTBEC IS 'C�digo da tabela de estado civil.';
COMMENT ON    COLUMN    VDCLIENTE.CODITTBEC IS 'C�digo do estado civil.';
COMMENT ON    COLUMN    VDCLIENTE.CODEMPCB IS 'C�digo da empresa na tabela FNCARTCOB.';
COMMENT ON    COLUMN    VDCLIENTE.CODFILIALCB IS 'C�digo da filial na tabela FNCARTCOB.';
COMMENT ON    COLUMN    VDCLIENTE.CODCARTCOB IS 'C�digo da carteira de cobran�a.';
COMMENT ON    COLUMN    VDCLIENTE.INSCCLI IS ' Inscri��o estadual do cliente.';
COMMENT ON    COLUMN    VDCLIENTE.SSPCLI IS 'Org�o de espedi��o do RG.';
COMMENT ON    COLUMN    VDCLIENTE.EMAILCOB IS 'Email de conbran�a.';
COMMENT ON    COLUMN    VDCLIENTE.EMAILENT IS 'Email de entrega';
COMMENT ON    COLUMN    VDCLIENTE.EMAILNFECLI IS 'Email para envio de nota fiscal eletronica';
COMMENT ON    COLUMN    VDCLIENTE.DTINITR IS 'Data de abertura do cr�dito';
COMMENT ON    COLUMN    VDCLIENTE.CELCLI IS 'N�mero do Celular
';
COMMENT ON    COLUMN    VDCLIENTE.NATCLI IS 'Naturalidade do cliente';
COMMENT ON    COLUMN    VDCLIENTE.UFNATCLI IS 'UF ref. a naturalizade.';
COMMENT ON    COLUMN    VDCLIENTE.TEMPORESCLI IS 'Tempo de resid�ncia do cliente';
COMMENT ON    COLUMN    VDCLIENTE.APELIDOCLI IS 'Apelido do cliente.';
COMMENT ON    COLUMN    VDCLIENTE.SITECLI IS 'Endere�o eletr�nico do site do cliente.';
COMMENT ON    COLUMN    VDCLIENTE.CODCONTDEB IS 'C�digo contabil de debito.';
COMMENT ON    COLUMN    VDCLIENTE.CODCONTCRED IS 'C�digo contabil de credito';
COMMENT ON    COLUMN    VDCLIENTE.CODCLICONTAB IS 'C�digo contabil';
COMMENT ON    COLUMN    VDCLIENTE.FOTOCLI IS 'Foto do cliente.';
COMMENT ON    COLUMN    VDCLIENTE.IMGASSCLI IS 'Imagem da assinatura do cliente';
COMMENT ON    COLUMN    VDCLIENTE.CODMUNIC IS 'C�digo do municipio (IBGE)';
COMMENT ON    COLUMN    VDCLIENTE.SIGLAUF IS 'Sigla da Unidade da Federe��o (Estado)';
COMMENT ON    COLUMN    VDCLIENTE.CODPAIS IS 'C�digo do pais.';
COMMENT ON    COLUMN    VDCLIENTE.CODMUNICENT IS 'C�digo do municipio (IBGE) do endere�o de entrega.';
COMMENT ON    COLUMN    VDCLIENTE.SIGLAUFENT IS 'Sigla da Unidade da Federa��o do endere�o de entrega.';
COMMENT ON    COLUMN    VDCLIENTE.CODPAISENT IS 'C�digo do pa�s do endere�o de entrega.';
COMMENT ON    COLUMN    VDCLIENTE.CODMUNICCOB IS 'C�digo do municipio (IBGE) do endere�o de cobran�a.';
COMMENT ON    COLUMN    VDCLIENTE.SIGLAUFCOB IS 'Sigla da Unidade da Federa��o do endere�o de cobran�a.';
COMMENT ON    COLUMN    VDCLIENTE.CODPAISCOB IS 'C�digo do pa�s do endere�o de cobran�a.';
COMMENT ON    COLUMN    VDCLIENTE.CODUNIFCOD IS 'C�digo na tabela de unifica��o de c�digos (SGUNIFCOD).';
COMMENT ON    COLUMN    VDCLIENTE.SUFRAMACLI IS 'C�digo do SUFRAMA do cliente.';
COMMENT ON    COLUMN    VDCLIENTE.CTOCLI IS 'C - Cliente
O - Contato';
COMMENT ON    COLUMN    VDCLIENTE.CODCNAE IS 'C�digo da atividade principal, padr�o CNAE.
';
COMMENT ON    COLUMN    VDCLIENTE.INSCMUNCLI IS 'Inscri��o municipal do cliente.';
COMMENT ON    COLUMN    VDCLIENTE.PERCDESCCLI IS 'Percentual padr�o de desconto para o cliente.';
COMMENT ON    COLUMN    VDCLIENTE.CONTCLICOB IS 'Contato no cliente, para cobran�a.';
COMMENT ON    COLUMN    VDCLIENTE.CONTCLIENT IS 'Contato no cliente, para entrega.';
COMMENT ON    COLUMN    VDCLIENTE.DESCIPI IS 'Indica se deve haver o desconto do IPI ao pre�o da mercadoria.
';
COMMENT ON    COLUMN    VDCLIENTE.IDENTCLIBCO IS 'Identifica��o cliente (SIACC)- D - Documento - CPF/CNPJ / C - C�digo do cliente ';
COMMENT ON    COLUMN    VDCLIENTEFOR.CODCPCLIFOR IS 'C�digo complementar ou da campanha que o cliente participa  junto ao fornecedor.';
COMMENT ON TABLE        VDCLIIMOV IS 'Cadastro de imoveis do cliente.';
COMMENT ON    COLUMN    VDCLIIMOV.TIPOIMOV IS 'Tipo de imovel.';
COMMENT ON    COLUMN    VDCLIIMOV.CONSTRIMOV IS 'Tipo de construcao no imovel';
COMMENT ON    COLUMN    VDCLIIMOV.AREATERIMOV IS 'Area do terrano do imovel.';
COMMENT ON    COLUMN    VDCLIIMOV.VALORIMOV IS 'Valor do imovel.';
COMMENT ON    COLUMN    VDCLIMETAVEND.ANOMETAVEND IS 'Ano para a meta de vendas';
COMMENT ON    COLUMN    VDCLIMETAVEND.VLRMETAVEND IS 'Valor de meta para vendas.';
COMMENT ON    COLUMN    VDCLIMETAVEND.OBSMETAVEND IS 'Observa��es referentes a meta de vendas para um determinado ano.';
COMMENT ON TABLE        VDCLIREFC IS 'Refer�ncias comerciais do cliente.';
COMMENT ON    COLUMN    VDCLIREFC.NOMEEMPREFC IS 'Nome da empresa.';
COMMENT ON    COLUMN    VDCLIREFC.DDDREFC IS 'DDD da referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.FONEREFC IS 'Fone da referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.DTMAIORCP IS 'Data da maior compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.VLRMAIORCP IS 'Valor da maior compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.NROPARCMAIORCP IS 'Numero de parcelas da maior compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.DTULTCP IS 'Data da ultima compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.VLRULTCP IS 'Valor da ultima compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.NROPARCULTCP IS 'Nro de parcelas da ultima compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.DTPRIMCP IS 'Data da primeira compra do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.PONTUAL IS 'Flag que indica se o cliente � pontual na referencia comercial. (S ou N)';
COMMENT ON    COLUMN    VDCLIREFC.MEDIAATRASO IS 'M�dia de atraso do cliente na referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.CONCEITO IS 'Conceito do cliente junto � referencia comercial.';
COMMENT ON    COLUMN    VDCLIREFC.AVALISTA IS 'Flag que indica se a empresa � avalista do cliente.';
COMMENT ON    COLUMN    VDCLIREFC.INFORMANTE IS 'Nome do informante na empresa.';
COMMENT ON    COLUMN    VDCLIREFC.OBSREFC IS 'Observa��es.';
COMMENT ON    COLUMN    VDCLIREFP.CODEMP IS 'Codigo da empresa.';
COMMENT ON    COLUMN    VDCLIREFP.CODFILIAL IS 'Codigo da filial.';
COMMENT ON    COLUMN    VDCLIREFP.CODCLI IS 'Codigo do cliente.';
COMMENT ON    COLUMN    VDCLIREFP.NOMEREFP IS 'Nome da refer�ncia pessoal.';
COMMENT ON    COLUMN    VDCLIREFP.ENDREFP IS 'Endereco.';
COMMENT ON    COLUMN    VDCLIREFP.NUMREFP IS 'Numero.';
COMMENT ON    COLUMN    VDCLIREFP.COMPLREFP IS 'Complemento do endere�o.';
COMMENT ON    COLUMN    VDCLIREFP.BAIRREFP IS 'Bairro.';
COMMENT ON    COLUMN    VDCLIREFP.CIDREFP IS 'Cidade.';
COMMENT ON    COLUMN    VDCLIREFP.UFREFP IS 'Estado.';
COMMENT ON    COLUMN    VDCLIREFP.CEPREFP IS 'Cep.';
COMMENT ON    COLUMN    VDCLIREFP.DDDREFP IS 'DDD.';
COMMENT ON    COLUMN    VDCLIREFP.FONEREFP IS 'Fone.';
COMMENT ON    COLUMN    VDCLIREFP.CODREFP IS 'Codigo da referencia.';
COMMENT ON TABLE        VDCLISOCIOS IS 'Socios da empresa cliente.';
COMMENT ON TABLE        VDCLITERRA IS 'Terras do cliente.';
COMMENT ON    COLUMN    VDCLITERRA.ENDTERRA IS 'Endere�o da terra.';
COMMENT ON TABLE        VDCLIVEIC IS 'Ve�culos do cliente.';
COMMENT ON    COLUMN    VDCLIVEIC.PLACAVEIC IS 'Placa do ve�culo.';
COMMENT ON    COLUMN    VDCLIVEIC.MODELOVEIC IS 'Modelo do ve�culo.';
COMMENT ON    COLUMN    VDCLIVEIC.ALIENADOVEIC IS 'Indica se o ve�culo est� alienado.';
COMMENT ON    COLUMN    VDCLIVEIC.ANOVEIC IS 'Ano de fabrica��o.';
COMMENT ON    COLUMN    VDCLIVEIC.VALORVEIC IS 'Valor do ve�culo.';
COMMENT ON    COLUMN    VDCOMISSAO.TIPOCOMI IS 'Tipo de comiss�o:
F-Valor gerado no faturamento
R-Valor gerado no recebimento
E-Valor gerado no estorno de contas a receber
';
COMMENT ON    COLUMN    VDCOMISSAO.DTCOMPCOMI IS 'Data de compet�ncia.
';
COMMENT ON    COLUMN    VDCOMISSAO.STATUSCOMI IS 'Situa��o da comiss�o:
C1 - Em aberto
C2 - Liberada
CP - Paga';
COMMENT ON    COLUMN    VDCOMISSAO.CODEMPVD IS 'C�digo da empresa do vendedor.';
COMMENT ON    COLUMN    VDCOMISSAO.CODFILIALVD IS 'C�digo da filial do vendedor.';
COMMENT ON    COLUMN    VDCOMISSAO.CODVEND IS 'C�digo do vendedor.';
COMMENT ON    COLUMN    VDCOMISSAO.CODEMPVE IS 'C�digo da empresa da venda vinculada (comissionamento especial)';
COMMENT ON    COLUMN    VDCOMISSAO.CODFILIALVE IS 'C�digo da filial da venda vinculada (comissionamento especial)';
COMMENT ON    COLUMN    VDCOMISSAO.CODVENDA IS 'C�digo da venda (comissionamento especial)';
COMMENT ON    COLUMN    VDCOMISSAO.TIPOVENDA IS 'Tipo da venda';
COMMENT ON    COLUMN    VDCOMISSAO.EMMANUT IS 'Estado de manuten��o (S/N).';
COMMENT ON TABLE        VDCONSIGNACAO IS 'Table de vendas consignadas';
COMMENT ON    COLUMN    VDCONSIGNACAO.DOCCONSIG IS 'Documento da consigna��o';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODEMPSL IS 'C�digo da empresa do sub-lan�amento financeiro.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODFILIALSL IS 'C�digo da filial do sub-lan�amento financeiro.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODLANCA IS 'C�digo do lan�amento financeiro.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODSUBLANCA IS 'C�digo do sub-lan�amento financeiro.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODEMPSD IS 'C�digo da empresa do sub-lan�amento financeiro de devolu��o.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODFILIALSD IS 'C�digo da filial do sub-lan�amento financeiro de devolu��o.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODLANCASD IS 'C�digo do lan�amento financeiro de devolu��o.';
COMMENT ON    COLUMN    VDCONSIGNACAO.CODSUBLANCASD IS 'C�digo do sub-lan�amento financeiro de devolu��o.';
COMMENT ON TABLE        VDCONTRATO IS 'Tabela de contratos.';
COMMENT ON    COLUMN    VDCONTRATO.CODCONTR IS 'C�digo do contrato.';
COMMENT ON    COLUMN    VDCONTRATO.DESCCONTR IS 'Descri��o do contrato.';
COMMENT ON    COLUMN    VDCONTRATO.MINUTACONTR IS 'Minuta do contrato.';
COMMENT ON    COLUMN    VDCONTRATO.CODEMPCL IS 'C�digo da empresa do cliente';
COMMENT ON    COLUMN    VDCONTRATO.CODFILIALCL IS 'C�digo da filial do cliente.';
COMMENT ON    COLUMN    VDCONTRATO.CODCLI IS 'C�digo do cliente.';
COMMENT ON    COLUMN    VDCONTRATO.DTINICIO IS 'Data de inicio de vigor do contrato.';
COMMENT ON    COLUMN    VDCONTRATO.DTFIM IS 'Data do fim do contrato.';
COMMENT ON    COLUMN    VDCONTRATO.TPCOBCONTR IS 'Tipo de cobran�a do contrato:
"ME" - Contrato Mensal
"BI" - Contrato Bimestral
"AN" - Contrato Anual
"ES" - Contrato Espor�dico';
COMMENT ON    COLUMN    VDCONTRATO.DIAVENCCONTR IS 'Dia de vencimento da cobran�a.';
COMMENT ON    COLUMN    VDCONTRATO.DIAFECHCONTR IS 'Dia do mes para fechamento das cobran�as.';
COMMENT ON    COLUMN    VDCONTRATO.TPCONTR IS 'Indica se tem caracter�sticas de contrato ou de projeto.
"C" - Contrato;
"P" - Projeto;
"S" - Sub-projeto';
COMMENT ON    COLUMN    VDCONTRATO.SITCONTR IS 'Situa��o do contrato:
"PE" - Pendente
"PA" - Em planejamento
"PF" - Planejado
"EE" - Em execu��o
"EX" - Executado
"PO" - Paralizado
"CC" - Canc. cliente
"CP" - Canc. prestador
"FN" - Finalizado';
COMMENT ON TABLE        VDETIQCLI IS '  tabela tempor�ria para impress�o de etiquetas de cliente.';
COMMENT ON    COLUMN    VDFRETEVD.ADICFRETEVD IS 'FLAG QUE INDICA SE ADICIONA O VALOR DO FRETE NA NOTA';
COMMENT ON    COLUMN    VDFRETEVD.ADICFRETEBASEICM IS 'Indica se deve adicionar o valor do frete na base de calculo do icms da venda.';
COMMENT ON    COLUMN    VDFRETEVD.VLRBASEICMSFRETEVD IS 'Base de calculo do icms do frete.';
COMMENT ON    COLUMN    VDFRETEVD.ALIQICMSFRETEVD IS 'Aliquota de icms incidente no frete.';
COMMENT ON    COLUMN    VDFRETEVD.VLRSEGFRETEVD IS 'Valor do seguro do frete.';
COMMENT ON TABLE        VDITCONTRATO IS 'Tabela de �tens de contrato.';
COMMENT ON    COLUMN    VDITCONTRATO.DESCITCONTR IS 'Descri��o do item de contrato';
COMMENT ON    COLUMN    VDITCONTRATO.CODEMPPD IS 'C�digo da empresa do produto.';
COMMENT ON    COLUMN    VDITCONTRATO.CODFILIALPD IS 'C�digo da filial do produto.';
COMMENT ON    COLUMN    VDITCONTRATO.CODPROD IS 'C�digo do produto.';
COMMENT ON    COLUMN    VDITCONTRATO.QTDITCONTR IS 'Quantidade do produto no �tem de contrato.';
COMMENT ON    COLUMN    VDITCONTRATO.VLRITCONTR IS 'Valor contratado para o produto.';
COMMENT ON    COLUMN    VDITCONTRATO.CODEMPPE IS 'C�digo da empresa do produto excedente.';
COMMENT ON    COLUMN    VDITCONTRATO.CODFILIALPE IS 'C�digo da filial do produto excedente.';
COMMENT ON    COLUMN    VDITCONTRATO.CODPRODPE IS 'C�digo do produto excedente.';
COMMENT ON    COLUMN    VDITCONTRATO.VLRITCONTREXCED IS 'Valor cobrado por excedente � quantidade contratada.';
COMMENT ON    COLUMN    VDITCONTRATO.KEYLIC IS 'Chave de licenciamento do produto/contrato.';
COMMENT ON    COLUMN    VDITCONTRATO.FRANQUIAITCONTR IS 'Agregar quantidade ao valor da franquia (S/N).';
COMMENT ON TABLE        VDITCONTRATOAND IS 'Tabela para lan�amento do andamento de execu��o de um projeto.';
COMMENT ON    COLUMN    VDITCONTRATOAND.CODCONTR IS 'C�digo do contrato.';
COMMENT ON    COLUMN    VDITCONTRATOAND.CODITCONTR IS 'C�digo do �tem de contrato.';
COMMENT ON    COLUMN    VDITCONTRATOAND.OBSAND IS 'Observa��o sobre o andamento do projeto.';
COMMENT ON    COLUMN    VDITCONTRATOAND.PERCAND IS 'Percentual de conclus�o do projeto.';
COMMENT ON    COLUMN    VDITCONTRATOAND.DATAAND IS 'Data do andamento.';
COMMENT ON    COLUMN    VDITCONTRATOAND.HORAAND IS 'Hora do andamento.';
COMMENT ON    COLUMN    VDITORCAMENTO.CODEMPAX IS 'C�digo da empresa na tabela de almoxarifados.';
COMMENT ON    COLUMN    VDITORCAMENTO.CODFILIALAX IS 'C�digo da filial na tabela de almoxarifados.';
COMMENT ON    COLUMN    VDITORCAMENTO.CODALMOX IS 'C�digo do almoxarifado.';
COMMENT ON    COLUMN    VDITORCAMENTO.QTDAPROVITORC IS 'Quantidade aprovada.';
COMMENT ON    COLUMN    VDITORCAMENTO.STATUSITORC IS '"*"  - Or�amento em aberto;
"OA" - Or�amento em aberto;
"OC" - Or�amento completo/impresso;
"OL" - Or�amento liberado/aprovado;
"OV" - Or�amento faturado.
"OP" - Or�amento produzido.
"CA" - Or�amento Cancelado/N�o Aprovado.';
COMMENT ON    COLUMN    VDITORCAMENTO.CODEMPPE IS 'C�digo da empresa do prazo de entrega.';
COMMENT ON    COLUMN    VDITORCAMENTO.CODFILIALPE IS 'C�digo da filial do prazo de entrega.';
COMMENT ON    COLUMN    VDITORCAMENTO.CODPE IS 'C�digo do prazo de entrega.';
COMMENT ON    COLUMN    VDITORCAMENTO.DIASPE IS 'Prazo de entrega (em dias) no �tem.';
COMMENT ON    COLUMN    VDITORCAMENTO.EMMANUT IS 'Flag para por a tabela em manuten�ao (S/N).';
COMMENT ON    COLUMN    VDITORCAMENTO.SITENTITORC IS 'Situa��o da entrega 
N - N�o entregue
E - Entregue
';
COMMENT ON    COLUMN    VDITORCAMENTO.SITTERMRITORC IS 'Situa��o do termo de recebimento.
E - Emitir
N - N�o emitir
O - Emitido';
COMMENT ON    COLUMN    VDITORCAMENTO.CANCITORC IS 'Flag de cancelamento S/N.';
COMMENT ON    COLUMN    VDITORCAMENTO.FATITORC IS 'Flag de faturamento S/N/P
(Sim, n�o, parcial)';
COMMENT ON    COLUMN    VDITORCAMENTO.VLRCOMISITORC IS 'Valor previsto de comiss�o no item de or�amento.';
COMMENT ON    COLUMN    VDITORCAMENTO.PERCCOMISITORC IS 'Percentual previsto de comiss�o no item de or�amento.';
COMMENT ON    COLUMN    VDITORCAMENTO.VLRFRETEITORC IS 'Valor previsto de frete por item do or�amento.';
COMMENT ON    COLUMN    VDITORCAMENTO.DTAPROVITORC IS 'Data de aprova��o do �tem de or�amento.';
COMMENT ON    COLUMN    VDITORCAMENTO.SITPRODITORC IS 'Situa��o da produ��o do �tem de or�amento.
PE - Pendente
EP - Em produ��o
NP - N�o produzir
PD - Produzido';
COMMENT ON    COLUMN    VDITREGRACOMIS.CODEMPVD IS 'C�digo da empresa do vendedor.';
COMMENT ON    COLUMN    VDITREGRACOMIS.CODFILIALVD IS 'C�digo da filial do vendedor ';
COMMENT ON    COLUMN    VDITREGRACOMIS.CODVEND IS 'C�digo do vendedor.';
COMMENT ON    COLUMN    VDITVENDA.CODEMPAX IS 'C�digo da empresa na tabela de almoxarifados.';
COMMENT ON    COLUMN    VDITVENDA.CODFILIALAX IS 'C�digo da filial na tabela de almoxarifados.';
COMMENT ON    COLUMN    VDITVENDA.CODALMOX IS 'C�digo do almoxarifado.';
COMMENT ON    COLUMN    VDITVENDA.QTDITVENDACANC IS 'Quantidade cancelada';
COMMENT ON    COLUMN    VDITVENDA.VLRBASEICMSBRUTITVENDA IS 'Base de c�lculo do ICMS sem redu��o e outras altera��es.';
COMMENT ON    COLUMN    VDITVENDA.VLRBASEICMSSTITVENDA IS 'Valor da base de calculo do ICMS da substitui��o tribut�ria.';
COMMENT ON    COLUMN    VDITVENDA.VLRICMSSTITVENDA IS 'Valor do ICMS da substitui��o tribut�ria.';
COMMENT ON    COLUMN    VDITVENDA.VLRBASECOMISITVENDA IS 'Valor base para comissionamento
Qtd x Preco comissao (cad.prod.)';
COMMENT ON    COLUMN    VDITVENDA.MARGEMVLAGRITVENDA IS 'Margem de valor agregado para calculo da base de calculo do icms de substitui��o tribut�ria.';
COMMENT ON    COLUMN    VDITVENDA.TIPOST IS 'Tipo de substitui��o tribut�ria.';
COMMENT ON    COLUMN    VDITVENDA.CANCITVENDA IS 'Flag para marcar se o item foi cancelado.';
COMMENT ON    COLUMN    VDITVENDA.CODEMPPE IS 'C�digo da empresa do prazo de entrega.';
COMMENT ON    COLUMN    VDITVENDA.CODFILIALPE IS 'C�digo da filial do prazo de entrega.';
COMMENT ON    COLUMN    VDITVENDA.CODPE IS 'C�digo do prazo de entrega.';
COMMENT ON    COLUMN    VDITVENDA.DIASPE IS 'Prazo de entrega (em dias) no �tem.';
COMMENT ON    COLUMN    VDITVENDA.CODCONV IS 'C�digo do conveniado';
COMMENT ON    COLUMN    VDITVENDA.CODEMPIF IS 'C�digo da empresa do item de classifica��o fiscal (fk transit�ria, utilizada no trigger que carrega a tabela lfitvenda)';
COMMENT ON    COLUMN    VDITVENDA.CODFILIALIF IS 'C�digo da filial do item de classifica��o fiscal (fk transit�ria, utilizada no trigger que carrega a tabela lfitvenda)';
COMMENT ON    COLUMN    VDITVENDA.CODFISC IS 'C�digo da classifica��o fiscal (fk transit�ria, utilizada no trigger que carrega a tabela lfitvenda)';
COMMENT ON    COLUMN    VDITVENDA.CODITFISC IS 'C�digo do tem de classifica��o fiscal (fk transit�ria, utilizada no trigger que carrega a tabela lfitvenda)';
COMMENT ON    COLUMN    VDITVENDA.CODEMPCP IS 'C�digo da empresa da compra (devolu��o)';
COMMENT ON    COLUMN    VDITVENDA.CODFILIALCP IS 'C�digo da filial da compra (devolu��o)';
COMMENT ON    COLUMN    VDITVENDA.CODCOMPRA IS 'C�digo da compra (devolu��o)';
COMMENT ON    COLUMN    VDITVENDA.CODITCOMPRA IS 'C�digo do �tem da compra (devolu��o)';
COMMENT ON    COLUMN    VDITVENDA.CODEMPVR IS 'C�digo da empresa do item da nota de remessa';
COMMENT ON    COLUMN    VDITVENDA.CODFILIALVR IS 'C�digo da filial do item de nota de remessa';
COMMENT ON    COLUMN    VDITVENDA.TIPOVENDAVR IS 'Tipo da venda do item de nota de remessa';
COMMENT ON    COLUMN    VDITVENDA.CODVENDAVR IS 'C�digo da venda do item de nota de remessa';
COMMENT ON    COLUMN    VDITVENDA.CODITVENDAVR IS 'C�digo do item de nota de remessa';
COMMENT ON    COLUMN    VDITVENDA.CODEMPNS IS 'C�digo da empresa do numero de serie';
COMMENT ON    COLUMN    VDITVENDA.CODFILIALNS IS 'C�digo da filial do n�mero de s�rie.';
COMMENT ON    COLUMN    VDITVENDA.NUMSERIETMP IS 'Campo para abrigar temporariamente o n�mero de s�rie do produto (para uso do trigger quando produto for unit�rio)';
COMMENT ON    COLUMN    VDITVENDA.VLRBASEICMSSTRETITVENDA IS 'Valor da base de calculo do icms st retido na opeara��o anterior.';
COMMENT ON    COLUMN    VDITVENDA.VLRICMSSTRETITVENDA IS 'Valor do icms st retido na opera��o anterior.
';
COMMENT ON    COLUMN    VDITVENDA.EMMANUT IS 'Flag para manuten��o da tabela (S/N).';
COMMENT ON TABLE        VDITVENDASERIE IS 'Tabela de vinculo entre item de venda e seus respectivos numeros de serie.';
COMMENT ON    COLUMN    VDORCAMENTO.STATUSORC IS '"*"  - Or�amento em aberto;
"OA" - Or�amento em aberto;
"OC" - Or�amento completo/impresso;
"OL" - Or�amento liberado/aprovado;
"OV" - Or�amento faturado.
"OP" - Or�amento produzido.
"CA" - Or�amento Cancelado/N�o Aprovado.';
COMMENT ON    COLUMN    VDORCAMENTO.EMMANUT IS 'Flag para por a tabela em manuten�ao (S/N).';
COMMENT ON    COLUMN    VDORCAMENTO.CODCLCOMIS IS 'CODIGO DA CLASSIFICA��O DA COMISS�O';
COMMENT ON    COLUMN    VDORCAMENTO.CODEMPTN IS 'C�digo da empresa da transportadora.';
COMMENT ON    COLUMN    VDORCAMENTO.CODFILIALTN IS 'C�digo da filial da transportadora.';
COMMENT ON    COLUMN    VDORCAMENTO.CODTRAN IS 'C�digo da transportadora.';
COMMENT ON    COLUMN    VDORCAMENTO.TIPOFRETE IS 'C - CIF ; F - FOB';
COMMENT ON    COLUMN    VDORCAMENTO.ADICFRETE IS 'Indica se deve adicionar o valor do frete ao total dos produtos.';
COMMENT ON    COLUMN    VDORCAMENTO.VLRFRETEORC IS 'Valor previsto em despesa com frete.';
COMMENT ON    COLUMN    VDORCAMENTO.VLRCOMISORC IS 'Valor previsto de comiss�o no or�amento.';
COMMENT ON    COLUMN    VDORCAMENTO.CODEMPTM IS 'C�digo da empresa do tipo de movimento previsto para o faturamento do or�amento.';
COMMENT ON    COLUMN    VDORCAMENTO.CODFILIALTM IS 'C�digo da filial do tipo de movimento previsto para o faturamento do or�amento.';
COMMENT ON    COLUMN    VDORCAMENTO.CODTIPOMOV IS 'C�digo do tipo de movimento previsto para o faturamento do or�amento.';
COMMENT ON    COLUMN    VDORCAMENTO.DTAPROVORC IS 'Data da aprova��o do or�amento.';
COMMENT ON    COLUMN    VDORCAMENTO.JUSTIFICCANCORC IS 'Justificativa pelo cancelamento do or�amento.';
COMMENT ON    COLUMN    VDORCAMENTO.ACORC IS 'Indica o recebedor do or�amento (Aos cuidados de)';
COMMENT ON TABLE        VDPRAZOENT IS 'Prazos de entrega.';
COMMENT ON    COLUMN    VDPRAZOENT.CODEMP IS 'C�digo da empresa.';
COMMENT ON    COLUMN    VDPRAZOENT.CODFILIAL IS 'C�digo da filial';
COMMENT ON    COLUMN    VDPRAZOENT.CODPE IS 'C�d. prazo de entrega.';
COMMENT ON    COLUMN    VDPRAZOENT.DESCPE IS 'Descri��o do prazo de entrega';
COMMENT ON    COLUMN    VDPRAZOENT.DIASPE IS 'N�mero de dias para entrega.';
COMMENT ON    COLUMN    VDPRECOPROD.TIPOPRECOPROD IS 'Indica qual o tipo de pre�o que origin�rio.
B - Pre�o Base
I - Custo informado;
O - Outros;
';
COMMENT ON    COLUMN    VDPRECOPROD.DTALTPRECO IS 'Data da altera��o do pre�o.
';
COMMENT ON    COLUMN    VDPRECOPROD.HALTPRECO IS 'Hora de altera��o do pre�o.
';
COMMENT ON    COLUMN    VDPRECOPROD.PRECOANT IS 'Pre�o anterior � �ltima altera��o.
';
COMMENT ON    COLUMN    VDPRECOPROD.IDUSUALTPRECO IS 'Usu�rio que realizou a altera��o no pre�o.
';
COMMENT ON TABLE        VDREGRACOMIS IS 'Regras de comiss�es';
COMMENT ON    COLUMN    VDREGRACOMIS.PERCCOMISGERAL IS 'Percentual de comiss�o para o grupo de comissionados (comissionamento especial por producao)';
COMMENT ON    COLUMN    VDREMCONSIG.CODREMCO IS 'C�digo da remessa de consigna��o.';
COMMENT ON    COLUMN    VDREMCONSIG.CODPROD IS 'C�digo do produto remetido.';
COMMENT ON    COLUMN    VDREMCONSIG.QTDSAIDA IS 'Quantidade remetida';
COMMENT ON    COLUMN    VDREMCONSIG.QTDDEVOL IS 'Quantidade devolvida.';
COMMENT ON    COLUMN    VDREMCONSIG.QTDTROCA IS 'Quantidade remetida para troca.';
COMMENT ON    COLUMN    VDREMCONSIG.QTDBONIF IS 'Quantidade remetida em bonifica��o.';
COMMENT ON    COLUMN    VDREMCONSIG.PRECO IS 'Pre�o do produto remetido.';
COMMENT ON TABLE        VDSETORROTA IS 'Tela de rotas baseada no setor dos clientes.';
COMMENT ON TABLE        VDTEF IS 'Tabela para vendas com TEF vinculado';
COMMENT ON TABLE        VDTIPOCLI IS 'Tipos de clientes.';
COMMENT ON    COLUMN    VDTIPOCLI.CHEQTIPOCLI IS 'Flag que indica se haver� aba adicional no cadastro de clientes (cheque).';
COMMENT ON    COLUMN    VDTIPOCLI.FISTIPOCLI IS 'Flag que indica se haver� aba adicional no cadastro de clientes (pessoa f�sica).';
COMMENT ON    COLUMN    VDTIPOCLI.JURTIPOCLI IS 'Flag que indica se haver� aba adicional no cadastro de clientes (pessoa jur�dica).';
COMMENT ON    COLUMN    VDTIPOCLI.FILTIPOCLI IS 'Flag que indica se haver� aba adicional no cadastro de clientes (Filia��o).';
COMMENT ON    COLUMN    VDTIPOCLI.LOCTRABTIPOCLI IS 'Flag que indica se haver� aba adicional no cadastro de clientes (local de trabalho).';
COMMENT ON    COLUMN    VDTIPOCLI.REFCOMLTIPOCLI IS 'Flag que indica se haver� aba adicional no cadastro de clientes (Refer�ncias comerciais).';
COMMENT ON    COLUMN    VDTIPOCLI.BANCTIPOCLI IS 'Flag que indica se haver� aba adicional no cadastro de clientes (Dados banc�rios).';
COMMENT ON    COLUMN    VDTIPOCLI.REFPESTIPOCLI IS 'Flag que indica se haver� aba adicional no cadastro de clientes (refer�ncias pessoais).';
COMMENT ON    COLUMN    VDTIPOCLI.CONJTIPOCLI IS 'Flag que indica se haver� aba adicional no cadastro de clientes (dados do c�njuge jur�dica).';
COMMENT ON    COLUMN    VDTIPOCLI.VEICTIPOCLI IS 'Flag que indica se haver� aba adicional no cadastro de clientes (ve�culos).';
COMMENT ON    COLUMN    VDTIPOCLI.IMOVTIPOCLI IS 'Flag que indica se haver� aba adicional no cadastro de clientes (im�veis).';
COMMENT ON    COLUMN    VDTIPOCLI.TERRATIPOCLI IS 'Flag que indica se haver� aba adicional no cadastro de clientes (terras).';
COMMENT ON    COLUMN    VDTIPOCLI.PESAUTCPTIPOCLI IS 'Flag para dados complementares (pessoa autorizada a comprar).';
COMMENT ON    COLUMN    VDTIPOCLI.AVALTIPOCLI IS 'Flag para dados complementares (avalista).';
COMMENT ON    COLUMN    VDTIPOCLI.SOCIOTIPOCLI IS 'Flag para dados complementares (s�cios).';
COMMENT ON    COLUMN    VDTIPOCLI.PRODRURALTIPOCLI IS 'Flag que indica se cliente � produtor rural.';
COMMENT ON    COLUMN    VDTIPOCLI.SIGLATIPOCLI IS 'Sigla ou abrevia��o da descri��o do tipo de cliente (utilizado em alguns relat�rios)';
COMMENT ON TABLE        VDTIPOVEND IS 'Tipos de comissionados.';
COMMENT ON TABLE        VDTRANSP IS 'Transportadoras.';
COMMENT ON    COLUMN    VDTRANSP.CPFTRAN IS 'CPF do transportador';
COMMENT ON    COLUMN    VDTRANSP.DDDFONETRAN IS 'DDD do telefone principal.';
COMMENT ON    COLUMN    VDTRANSP.DDDFAXTRAN IS 'DDD do Fax.';
COMMENT ON    COLUMN    VDTRANSP.DDDCELTRAN IS 'DDD do celular da transportadora.';
COMMENT ON    COLUMN    VDTRANSP.CODFILIALUC IS 'C�digo na tabela de unifica��o de c�digos (SGUNIFCOD).';
COMMENT ON    COLUMN    VDTRANSP.CODFOR IS 'Correspondente na tabela de fornecedores.';
COMMENT ON    COLUMN    VDTRANSP.CONJUGETRAN IS 'Nome do conjuge do transportado (pessoa f�sica)';
COMMENT ON    COLUMN    VDTRANSP.PLACATRAN IS 'Placa do ve�culo do transportador (pessoa f�sica)';
COMMENT ON    COLUMN    VDTRANSP.NRODEPENDTRAN IS 'N�mero de dependentes do transportador (pessoa f�sica)';
COMMENT ON    COLUMN    VDTRANSP.RGTRAN IS 'N�mero da Identidade.';
COMMENT ON    COLUMN    VDTRANSP.CODGPS IS 'C�digo de pagamento do gps/inss.';
COMMENT ON    COLUMN    VDTRANSP.NROPISTRAN IS 'N�mero do PIS.';
COMMENT ON    COLUMN    VDTRANSP.OBSTRAN IS 'Observa��es.';
COMMENT ON    COLUMN    VDTRANSP.EMAILNFETRAN IS 'Email para envio do XML da Nfe.';
COMMENT ON    COLUMN    VDVENDA.SUBTIPOVENDA IS 'Subtipo da venda - NF = Nota fiscal / NC = Nota complementar.';
COMMENT ON    COLUMN    VDVENDA.DTCOMPVENDA IS 'Data de compet�ncia.';
COMMENT ON    COLUMN    VDVENDA.CALCICMSVENDA IS 'Indica se deve calcular o valor do ICMS na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPICMSVENDA IS 'Indica se deve imprimir o valor do ICMS na nota.';
COMMENT ON    COLUMN    VDVENDA.CALCIPIVENDA IS 'Indica se deve calcular o valor do IPI na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPIPIVENDA IS 'Indica se deve imprimir o valor do IPI na nota.';
COMMENT ON    COLUMN    VDVENDA.CALCISSVENDA IS 'Indica se deve calcular o valor do ISS na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPIISSVENDA IS 'Indica se deve imprimir o valor do ISS na nota.';
COMMENT ON    COLUMN    VDVENDA.CALCPISVENDA IS 'Indica se deve calcular o valor do PIS na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPPISVENDA IS 'Indica se deve imprimir o valor do PIS na nota.';
COMMENT ON    COLUMN    VDVENDA.CALCCOFINSVENDA IS 'Indica se deve calcular o valor do COFINS na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPCOFINSVENDA IS 'Indica se deve imprimir o valor do COFINS na nota.';
COMMENT ON    COLUMN    VDVENDA.CALCIRVENDA IS 'Indica se deve calcular o valor do IR na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPIRVENDA IS 'Indica se deve imprimir o valor do IR na nota.';
COMMENT ON    COLUMN    VDVENDA.CALCCSOCIALVENDA IS 'Indica se deve calcular o valor da CONTRIBUICAO SOCIAL na nota.';
COMMENT ON    COLUMN    VDVENDA.IMPCSOCIALVENDA IS 'Indica se deve imprimir o valor da CONTRIBUICAO SOCIAL na nota.';
COMMENT ON    COLUMN    VDVENDA.CODEMPCB IS 'C�digo da empresa na tabela FNCARTCOB.';
COMMENT ON    COLUMN    VDVENDA.CODFILIALCB IS 'C�digo da filial na tabela FNCARTCOB.';
COMMENT ON    COLUMN    VDVENDA.CODCARTCOB IS 'C�digo da carteira de cobran�a.';
COMMENT ON    COLUMN    VDVENDA.PEDCLIVENDA IS 'N�mero do pedido do cliente.';
COMMENT ON    COLUMN    VDVENDA.VLRICMSSTVENDA IS 'Valor do icms da substitui��o tribut�ria.';
COMMENT ON    COLUMN    VDVENDA.VLRBASEICMSSTVENDA IS 'Valor da base de calculo do icms de substitui��o tribut�ria.';
COMMENT ON    COLUMN    VDVENDA.EMMANUT IS 'Flag que indica se a tabela est� em manuten��o (S/N).';
COMMENT ON    COLUMN    VDVENDA.VLRICMSSIMPLES IS 'Valor do cr�dito de ICMS destacado na venda quando do enquadramento no simples.';
COMMENT ON    COLUMN    VDVENDA.PERCICMSSIMPLES IS 'Al�quota do cr�dito de ICMS destacado na venda quando do enquadramento no simples.';
COMMENT ON    COLUMN    VDVENDA.VLRBASECOMIS IS 'Valor base para calculo das comiss�es especiais.';
COMMENT ON    COLUMN    VDVENDA.CHAVENFEVENDA IS 'Chave de acesso da nota fiscal eletr�nica.';
COMMENT ON    COLUMN    VDVENDA.OBSREC IS 'Observa��o a ser repassada para a tabela fnreceber no trigger/procedure de inser��o.';
COMMENT ON    COLUMN    VDVENDA.INFCOMPL IS 'Informa��es complementares da nota fiscal ( de interesse do fisco );';
COMMENT ON    COLUMN    VDVENDA.SITDOC IS 'Situa��o do documento fiscal:
00-Documento regular;
01-Documento regular expont�neo;
02-Documento cancelado;
03-Documento cancelado expont�neo
04-NFE Denegada;
05-NFE Numera��o inutilizada;
06-Documento fiscal complementar;
07-Documento fiscal complementar expont�neo;
08-Documento emitido com base em Regime Especial ou Norma Espec�fica;';
COMMENT ON    COLUMN    VDVENDA.OBSNFE IS 'Observacoes geradas na emissao da nfe.';
COMMENT ON    COLUMN    VDVENDA.DESCIPIVENDA IS 'Indica se o valor do IPI foi descontado no pre�o dos �tens.
';
COMMENT ON    COLUMN    VDVENDA.CODEMPOP IS 'C�digo da OP vinculada (remessa industrializa��o)
';
COMMENT ON    COLUMN    VDVENDA.CODFILIALOP IS 'C�digo da filial da OP vinculada (remessa industrializa��o)
';
COMMENT ON    COLUMN    VDVENDA.SEQOP IS 'C�digo da sequencia da OP vinculada (remessa industrializa��o)
';
COMMENT ON    COLUMN    VDVENDA.CODOP IS 'CC�digo da OP vinculada (remessa industrializa��o)
';
COMMENT ON    COLUMN    VDVENDA.MOTIVOCANCVENDA IS 'Motivo do cancelamento da venda.';
COMMENT ON    COLUMN    VDVENDACOMIS.TIPOVENDA IS 'Tipo de venda
O = Or�amento
V = Venda
E = Vencda Ecf
';
COMMENT ON TABLE        VDVENDACONSIG IS 'Tabela de vendas em consigna��o.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODCONSIG IS 'C�digo da consigna��o.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODVENDACO IS 'C�digo da venda em consigna��o.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODCLI IS 'C�digo do cliente.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODPROD IS 'C�digo do produto.';
COMMENT ON    COLUMN    VDVENDACONSIG.PRECO IS 'Pre�o praticado.';
COMMENT ON    COLUMN    VDVENDACONSIG.PRECOVENDA IS 'Pre�o aplicado ao cliente.';
COMMENT ON    COLUMN    VDVENDACONSIG.QTDVENDACO IS 'Quantidade vendida.';
COMMENT ON    COLUMN    VDVENDACONSIG.QTDTROCA IS 'Quantidade trocada.';
COMMENT ON    COLUMN    VDVENDACONSIG.QTDBONIF IS 'Quantidade de bonifica��es.';
COMMENT ON    COLUMN    VDVENDACONSIG.DESCONTO IS 'Desconto praticado.';
COMMENT ON    COLUMN    VDVENDACONSIG.RECEBIDO IS 'Flag indicador de recebido.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODEMPVD IS 'C�digo da empresa do item de venda';
COMMENT ON    COLUMN    VDVENDACONSIG.CODFILIALVD IS 'C�digo da filial do item de venda.';
COMMENT ON    COLUMN    VDVENDACONSIG.TIPOVENDA IS 'Tipo da venda do item de venda.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODVENDA IS 'C�digo da venda do item de venda.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODITVENDA IS 'C�digo do item de venda.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODEMPSL IS 'C�digo da empresa do sub-lan�amento financeiro.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODFILIALSL IS 'C�digo da filial do sub-lan�amento financeiro.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODLANCA IS 'C�digo do lan�amento financeiro.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODSUBLANCA IS 'C�digo do sub-lan�amento financeiro.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODEMPSC IS 'C�digo da empresa do sub-lan�amento financeiro de contra-partida.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODFILIALSC IS 'C�digo da filial do sub-lan�amento financeiro de contra-partida.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODLANCASC IS 'C�digo do lan�amento financeiro de contra-partida.';
COMMENT ON    COLUMN    VDVENDACONSIG.CODSUBLANCASC IS 'C�digo do sub-lan�amento financeiro de contra-partida.';
COMMENT ON    COLUMN    VDVENDEDOR.CODFORNVEND IS 'CODIGO DO VENDEDOR NO FORNECEDOR
';
COMMENT ON    COLUMN    VDVENDEDOR.CODFUNC IS 'Funcao/Cargo do comissionado.';
COMMENT ON    COLUMN    VDVENDEDOR.CODEMPFU IS 'C�digo da empresa da funcao/cargo do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.CODFILIALFU IS 'C�digo da filial da funcao do comissionado.';
COMMENT ON    COLUMN    VDVENDEDOR.DDDFONEVEND IS 'DDD do telefone do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.DDDFAXVEND IS 'DDD do fax do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.DDDCELVEND IS 'DDD do celular do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.SSPVEND IS 'Org�o de emiss�o do RG do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.OBSVEND IS 'Observa��es do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.ATIVOCOMIS IS 'Indica se o comissionado esta ativo.';
COMMENT ON    COLUMN    VDVENDEDOR.CODEMPCA IS 'C�digo da empresa da conta do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.CODFILIALCA IS 'C�digo da filial da conta do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.NUMCONTA IS 'Numero da conta do vendedor.';
COMMENT ON    COLUMN    VDVENDEDOR.VLRABONO IS 'Valor do abono (utilizado nos relat�rios de comissionamento pela produ��o.';
COMMENT ON    COLUMN    VDVENDEDOR.VLRDESCONTO IS 'Valor do desconto (utilizado nos relat�rios de comissionamento pela produ��o.';
COMMENT ON    PARAMETER EQMOVPRODISP.SEQSUBPROD IS 'Sequencial do subproduto';
COMMENT ON    PARAMETER EQMOVPRODIUDSP.SEQSUBPROD IS 'Sequencial de subproduto';
COMMENT ON    PARAMETER FNADICRECEBERSP01.VLRRETENSAOISS IS 'Indica se deve ser realizada a retens�o do tributo ISS (descontando do valor final do t�tulo)';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODEMPCF IS 'C�digo da empresa do cliente ou fornecedor';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODFILIALCF IS 'C�digo da filial do cliente ou fornecedor';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODCF IS 'C�digo do cliente ou fornecedor';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.TIPOBUSCA IS 'Indica se a busca � para VD venda ou CP compra';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODEMPIFP IS 'C�digo da empresa do item de classifica��o fiscal ';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODFILIALIFP IS 'C�digo da filial do �tem de classifica��o fiscal';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODFISCP IS 'C�digo da classifica��o fiscal';
COMMENT ON    PARAMETER LFBUSCAFISCALSP.CODITFISCP IS 'C�digo do �tem de classficia��o fiscal';
COMMENT ON PROCEDURE    LFBUSCAFISCALSP02 IS 'Procedure para busca de informa��es fiscais de um �tem de venda, utilizada para preencher dados da tabela lfitvenda.';
COMMENT ON PROCEDURE    LFBUSCAPREVTRIBORC IS 'Procedure para busca de previs�o de tributos incidentes em �tem de or�amento para calculo da previs�o de lucratividade.';
COMMENT ON PROCEDURE    PPCUSTOPRODSP IS 'Retorna o custo unit�rio do produto';
COMMENT ON    PARAMETER PPGERAOP.TIPOPROCESS IS 'Tipo de processamento (D=Detalhado, A=Agrupado, C=Comum)';
COMMENT ON    PARAMETER PPGERAOP.CODEMPOP IS 'C�digo da empresa da ordem de produ��o';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALOP IS 'C�digo da filial da ordem de produ��o';
COMMENT ON    PARAMETER PPGERAOP.CODOP IS 'C�digo da ordem de produ��o';
COMMENT ON    PARAMETER PPGERAOP.SEQOP IS 'Sequencia da ordem de produ��o';
COMMENT ON    PARAMETER PPGERAOP.CODEMPPD IS 'C�digo da empresa do produto';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALPD IS 'C�digo da filial do produto';
COMMENT ON    PARAMETER PPGERAOP.CODPROD IS 'C�digo do produto';
COMMENT ON    PARAMETER PPGERAOP.CODEMPOC IS 'C�digo da emrpesa do or�amento';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALOC IS 'C�digo da filial do or�amento';
COMMENT ON    PARAMETER PPGERAOP.CODORC IS 'C�digo do or�amento';
COMMENT ON    PARAMETER PPGERAOP.TIPOORC IS 'Tipo de or�amento';
COMMENT ON    PARAMETER PPGERAOP.CODITORC IS 'C�digo do �tem de or�amento';
COMMENT ON    PARAMETER PPGERAOP.QTDSUGPRODOP IS 'Quantidade sugerida para fabrica��o';
COMMENT ON    PARAMETER PPGERAOP.DTFABROP IS 'Data de fabrica��o';
COMMENT ON    PARAMETER PPGERAOP.SEQEST IS 'Sequ�ncia da estrutura';
COMMENT ON    PARAMETER PPGERAOP.CODEMPET IS 'C�digo da empresa da esta��o de trabalho';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALET IS 'C�digo da filial da esta��o de trabalho';
COMMENT ON    PARAMETER PPGERAOP.CODEST IS 'C�digo da esta��o de trabalho';
COMMENT ON    PARAMETER PPGERAOP.AGRUPDATAAPROV IS 'Indica se o agrupamento � por data de aprova��o';
COMMENT ON    PARAMETER PPGERAOP.AGRUPDTFABROP IS 'Indica se o agrupamento � por data de fabrica��o';
COMMENT ON    PARAMETER PPGERAOP.AGRUPCODCLI IS 'Indica se o agrupamento � por c�digo de cliente';
COMMENT ON    PARAMETER PPGERAOP.CODEMPCL IS 'C�digo da empresa do cliente do lote processado';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALCL IS 'C�digo da filial do cliente do lote processado';
COMMENT ON    PARAMETER PPGERAOP.CODCLI IS 'C�digo do cliente do lote processado';
COMMENT ON    PARAMETER PPGERAOP.DATAAPROV IS 'Data de aprova��o do lote processado';
COMMENT ON    PARAMETER PPGERAOP.CODEMPCP IS 'C�digo da empresa do item de compra (convers�o de produtos)';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALCP IS 'C�digo da filial do item de compra (convers�o de produtos)';
COMMENT ON    PARAMETER PPGERAOP.CODCOMPRA IS 'C�digo da compra (convers�o de produtos)';
COMMENT ON    PARAMETER PPGERAOP.CODITCOMPRA IS 'C�digo do item de compra (convers�o de produtos)';
COMMENT ON    PARAMETER PPGERAOP.JUSTFICQTDPROD IS 'Justificativa por divergencias na quantidade final (convers�o de produtos)';
COMMENT ON    PARAMETER PPGERAOP.CODEMPPDENTRADA IS 'C�digo da empresa do produto de entrada (convers�o de produtos)';
COMMENT ON    PARAMETER PPGERAOP.CODFILIALPDENTRADA IS 'C�digo da filial do produto de entrada (convers�o de produtos)';
COMMENT ON    PARAMETER PPGERAOP.CODPRODENTRADA IS 'C�digo do produto de entrada (convers�o de produtos)';
COMMENT ON    PARAMETER PPGERAOP.QTDENTRADA IS 'Quantidade do produto de entrada (convers�o de produtos)';
COMMENT ON PROCEDURE    PPGERAOPCQ IS 'Procedure disparada ap�s a inser��o na tabela PPOP, realiza varredura na estrutura do produto, verificando as an�lises necess�rias
no controle de qualidade e gerando registros referentes ao controle de qualidade na tabela PPOPCQ.';
COMMENT ON PROCEDURE    PRO_RECURSIVO IS 'Stored Procedure utilizada no controle da recursividade em triggers e stored procedures';
COMMENT ON    PARAMETER RHLISTACANDVAGASP.ICODVAGA IS 'C�digo da vaga';
COMMENT ON    PARAMETER RHLISTACANDVAGASP.ICODFUNC IS 'C�digo da fun��o';
COMMENT ON PROCEDURE    TKGERACAMPANHACTO IS 'Procedure para gera��o de registros nas tabelas TKCAMPANHACTO e TKSITCAMP.';
COMMENT ON PROCEDURE    VDRETULTVDCLIPROD IS 'Procedure para relat�rio de ultimas vendas por cliente/produto.';
COMMIT WORK;