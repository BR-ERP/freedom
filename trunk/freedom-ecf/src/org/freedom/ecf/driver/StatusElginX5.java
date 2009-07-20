
package org.freedom.ecf.driver;

import java.util.ArrayList;
import java.util.List;

public class StatusElginX5 implements Status {

	public static StatusElginX5 ErroGeralFalRAM = new StatusElginX5( 1, RELEVANC_ERRO, "N�o foi poss�vel alocar mais mem�ria" );
	
	public static StatusElginX5 ErroGeralPerdaRAM = new StatusElginX5( 2, RELEVANC_ERRO, "Mem�ria RAM foi corrompida" );
	
	public static StatusElginX5 ErroMFDesconectada = new StatusElginX5( 1000, RELEVANC_ERRO, "Mem�ria Fiscal foi desconectada" );
	
	public static StatusElginX5 ErroMFLeitura = new StatusElginX5( 1001, RELEVANC_ERRO, "Erro na leitura na Mem�ria Fiscal" );
	
	public static StatusElginX5 ErroMFApenasLeitura = new StatusElginX5( 1002, RELEVANC_ERRO, "Mem�ria est� setada apenas para leitura" );
	
	public static StatusElginX5 ErroMFTamRegistro = new StatusElginX5( 1003, RELEVANC_ERRO, "Registro fora dos padr�es (erro interno)" );
	
	public static StatusElginX5 ErroMFCheia = new StatusElginX5( 1004, RELEVANC_ERRO, "Mem�ria Fiscal est� lotada" );
	
	public static StatusElginX5 ErroMFCartuchosExcedidos = new StatusElginX5( 1005, RELEVANC_ERRO, "N�mero m�ximo de cartuchos excedidos" );
	
	public static StatusElginX5 ErroMFJaInicializada = new StatusElginX5( 1006, RELEVANC_ERRO, "Tentativa de gravar novo modelo de ECF" );
	
	public static StatusElginX5 ErroMFNaoInicializada = new StatusElginX5( 1007, RELEVANC_ERRO, "Tentativa de grava��o de qualquer dado antes da inicializa��o da Mem�ria Fiscal" );
	
	public static StatusElginX5 ErroMFUsuariosExcedidos = new StatusElginX5( 1008, RELEVANC_ERRO, "N�mero m�ximo de usu�rios foi atingido" );
	
	public static StatusElginX5 ErroMFIntervencoesExedidas = new StatusElginX5( 1009, RELEVANC_ERRO, "N�mero m�xiom de interven��es foi atingido" );
	
	public static StatusElginX5 ErroMFVersoesExcedidas = new StatusElginX5( 1010, RELEVANC_ERRO, "N�mero m�ximo de vers�es foi atingido" );
	
	public static StatusElginX5 ErroMFReducoesExcedidas = new StatusElginX5( 1011, RELEVANC_ERRO, "N�mero m�xiom de redu��es foi atingido" );
	
	public static StatusElginX5 ErroMFGravacao = new StatusElginX5( 1012, RELEVANC_ERRO, "Erro na grava��o de registro na mem�ria fiscal" );
	
	public static StatusElginX5 ErroTransactDrvrLeitura = new StatusElginX5( 2000, RELEVANC_ERRO, "Erro na leitura no dispositivo f�sico" );
	
	public static StatusElginX5 ErroTransactDrvrEscrita = new StatusElginX5( 2001, RELEVANC_ERRO, "Erro de leitura no dispositivo" );
	
	public static StatusElginX5 ErroTransactDrvrDesconectado = new StatusElginX5( 2002, RELEVANC_ERRO, "Dispositivo de transa��es foi desconectado" );
	
	public static StatusElginX5 ErroTransactRegInvalido = new StatusElginX5( 3000, RELEVANC_ERRO, "Tipo de registro a ser gravado inv�lido" );
	
	public static StatusElginX5 ErroTransactCheio = new StatusElginX5( 3001, RELEVANC_ERRO, "registro de transa��es est� esgotado" );
	
	public static StatusElginX5 ErroTransactTransAberta = new StatusElginX5( 3002, RELEVANC_ERRO, "Tentativa de abrir nova transa��o com trasa��o j� aberta" );
	
	public static StatusElginX5 ErroTransactTransNaoAberta = new StatusElginX5( 3003, RELEVANC_ERRO, "Tentativa de fechar uma transa��o que n�o se encontra" );
	
	public static StatusElginX5 ErroContextDrvrLeitura = new StatusElginX5( 4000, RELEVANC_ERRO, "Erro de leitura de dispositivo f�sico" );
	
	public static StatusElginX5 ErroContextDrvrEscrita = new StatusElginX5( 4001, RELEVANC_ERRO, "Erro de escrita no dispositivo" );
	
	public static StatusElginX5 ErroContextDrvrDesconectado = new StatusElginX5( 4002, RELEVANC_ERRO, "Dispositivo de contexto foi desconectado" );
	
	public static StatusElginX5 ErroContextDrvrLeituraAposFim = new StatusElginX5( 4003, RELEVANC_ERRO, "Leitura ap�s final do arquivo" );
	
	public static StatusElginX5 ErroContextDrvrEscritaAposFim = new StatusElginX5( 4004, RELEVANC_ERRO, "Escrita ap�s final do arquivo" );
	
	public static StatusElginX5 ErroContextVersaoInvalida = new StatusElginX5( 5000, RELEVANC_ERRO, "vers�o de contexto fiscal no dispositivo n�o foi reconhecida" );
	
	public static StatusElginX5 ErroContextCRC = new StatusElginX5( 5001, RELEVANC_ERRO, "CRC do dispositivo est� incorreto" );
	
	public static StatusElginX5 ErroContextLimitesExcedidos = new StatusElginX5( 5002, RELEVANC_ERRO, "Tentativa de escrita fora da �rea de contexto" );
	
	public static StatusElginX5 ErroRelogioInconsistente = new StatusElginX5( 6000, RELEVANC_ERRO, "Rel�rio do ECF inconsistente" );
	
	public static StatusElginX5 ErroRelogioDataHoraInvalida = new StatusElginX5( 6001, RELEVANC_ERRO, "Data/hora informadas n�o est�o consistentes" );
	
	public static StatusElginX5 ErroPrintSemMecanismo = new StatusElginX5( 7000, RELEVANC_ERRO, "Nenhum mecanismo de impress�o presente" );
	
	public static StatusElginX5 ErroPrintDesconectado = new StatusElginX5( 7001, RELEVANC_ERRO, "Atual mecanismo de impress�o est� desconectado" );
	
	public static StatusElginX5 ErroPrintCapacidadeInesistente = new StatusElginX5( 7002, RELEVANC_ERRO, "Mecanismo n�o possui capacidade suficiente para realizar esta opera��o" );
	
	public static StatusElginX5 ErroPrintSemPapel = new StatusElginX5( 7003, RELEVANC_ERRO, "Impressora est� sem papel para imprimir" );
	
	public static StatusElginX5 ErroPrintFaltouPapel = new StatusElginX5( 7004, RELEVANC_ERRO, "Faltou papel durante a impress�o do comando" );
	
	public static StatusElginX5 ErroCMDForaDeSequencia = new StatusElginX5( 8000, RELEVANC_ERRO, "Comando fora de sequ�ncia" );
	
	public static StatusElginX5 ErroCMDCodigoInvalido = new StatusElginX5( 8001, RELEVANC_ERRO, "C�digo mercadoria n�o v�lido" );
	
	public static StatusElginX5 ErroCMDDescricaoInvalida = new StatusElginX5( 8002, RELEVANC_ERRO, "Descri��o inv�lida" );
	
	public static StatusElginX5 ErroCMDQuantidadeInvalida = new StatusElginX5( 8003, RELEVANC_ERRO, "Quantidade n�o inv�lida" );
	
	public static StatusElginX5 ErroCMDAliquotaInvalida = new StatusElginX5( 8004, RELEVANC_ERRO, "�ndice de al�quota n�o v�lido" );
	
	public static StatusElginX5 ErroCMDAliquotaNaoCarregada = new StatusElginX5( 8005, RELEVANC_ERRO, "Al�quota n�o caregada" );
	
	public static StatusElginX5 ErroCMDValorInvalido = new StatusElginX5( 8006, RELEVANC_ERRO, "Valor cont�m caracter inv�lido" );
	
	public static StatusElginX5 ErroCMDMontanteOperacao = new StatusElginX5( 8007, RELEVANC_ERRO, "Total da opera��o igual a 0(zero)" );
	
	public static StatusElginX5 ErroCMDAliquotaIndisponivel = new StatusElginX5( 8008, RELEVANC_ERRO, "Al�quota n�o dispon�vel para carga" );
	
	public static StatusElginX5 ErroCMDValorAliquotaInvalido = new StatusElginX5( 8009, RELEVANC_ERRO, "Valor da al�quota n�o v�lido" );
	
	public static StatusElginX5 ErroCMDTrocaSTAposFechamento = new StatusElginX5( 8010, RELEVANC_ERRO, "Troca de situra��o tribut�ria somente ap�s Redu��o Z" );
	
	public static StatusElginX5 ErroCMDFormaPagamentoInvalida = new StatusElginX5( 8011, RELEVANC_ERRO, "�ndice de Meio de Pagamento n�o v�lido" );
	
	public static StatusElginX5 ErroCMDPayIndisponivel = new StatusElginX5( 8012, RELEVANC_ERRO, "Meio de Pagamento indispon�vel para carga" );
	
	public static StatusElginX5 ErroCMDCupomTotalizadoEmZero = new StatusElginX5( 8013, RELEVANC_ERRO, "Cupom totalizado em 0(zero)" );
	
	public static StatusElginX5 ErroCMDFomraPagamentoIndefinida = new StatusElginX5( 8014, RELEVANC_ERRO, "Meio de Pagamento n�o definido" );
	
	public static StatusElginX5 ErroCMDtracaUsuarioAposFechamento = new StatusElginX5( 8015, RELEVANC_ERRO, "Carga de usu�rio permitido somente ap�s Redu��o Z" );
	
	public static StatusElginX5 ErroCMDSemMovimento = new StatusElginX5( 8016, RELEVANC_ERRO, "Dia sem movimento" );
	
	public static StatusElginX5 ErroCMDPagamentoIncompleto = new StatusElginX5( 8017, RELEVANC_ERRO, "Total pago inferior ao total do cupom" );
	
	public static StatusElginX5 ErroCMDGerencialNaoDefinido = new StatusElginX5( 8018, RELEVANC_ERRO, "Gerencial n�o definido" );
	
	public static StatusElginX5 ErroGerencialInvalido = new StatusElginX5( 8019, RELEVANC_ERRO, "�ndice de Gerencial fora da faixa" );
	
	public static StatusElginX5 ErroCMDGerencialIndisponivel = new StatusElginX5( 8020, RELEVANC_ERRO, "Gerencial n�o dispon�vel para carga" );
	
	public static StatusElginX5 ErroCMDNomeGerencialInvalido = new StatusElginX5( 8021, RELEVANC_ERRO, "Nome do Gerencial inv�lido" );
	
	public static StatusElginX5 ErroCMDNaoHaMaisRelatoriosLivres = new StatusElginX5( 8022, RELEVANC_ERRO, "Esgotado n�mero de Gerenciais" );
	
	public static StatusElginX5 ErroCMDAcertoHVPermitidoAposZ = new StatusElginX5( 8023, RELEVANC_ERRO, "Acerto do hor�rio de ver�o somente ap�s a Redu��o Z" );
	
	public static StatusElginX5 ErroCMDHorarioVeraoJaRealizado = new StatusElginX5( 8024, RELEVANC_ERRO, "J� acertou hor�rio de ver�o" );
	
	public static StatusElginX5 ErroCMDAliquotasIndisponiveis = new StatusElginX5( 8025, RELEVANC_ERRO, "Sem Al�quotas dispon�veis para carga" );
	
	public static StatusElginX5 ErroCMDItemInexistente = new StatusElginX5( 8026, RELEVANC_ERRO, "Item n�o vendido no cupom" );
	
	public static StatusElginX5 ErroCMDQtdCancInvalida = new StatusElginX5( 8027, RELEVANC_ERRO, "Quantidade a ser cancelada maior do que a quantidade vendida" );
	
	public static StatusElginX5 ErroCMDCampoCabecalhoInvalido = new StatusElginX5( 8028, RELEVANC_ERRO, "Cabe�alho possui campos(s) inv�lido(s)" );
	
	public static StatusElginX5 ErroCMDNomeDepartamentoInvalido = new StatusElginX5( 8029, RELEVANC_ERRO, "NomeDepartamento n�o v�lido" );
	
	public static StatusElginX5 ErroCMDDepartamentoNaoEncontrado = new StatusElginX5( 8030, RELEVANC_ERRO, "Departamento n�o encontrado" );
	
	public static StatusElginX5 ErroCMDDepartamentoIndefinido = new StatusElginX5( 8031, RELEVANC_ERRO, "Departamento n�o definido" );
	
	public static StatusElginX5 ErroCMDFormasPagamentosIndisponiveis = new StatusElginX5( 8032, RELEVANC_ERRO, "N�o h� Meio de Pagamento dispon�vel" );
	
	public static StatusElginX5 ErroCMDAltPagamentosSoAposZ = new StatusElginX5( 8033, RELEVANC_ERRO, "Altera��o de Meio de Pagamento somente ap�s a Redu��o Z" );
	
	public static StatusElginX5 ErroCMDNomeNalFiscalInvalido = new StatusElginX5( 8034, RELEVANC_ERRO, "Nome do Documento N�o Fiscal n�o pode ser vazio" );
	
	public static StatusElginX5 ErroCMDDocsFiscaisIndisponiveis = new StatusElginX5( 8035, RELEVANC_ERRO, "N�o h� mais Documentos N�o Fiscais dispon�veis" );
	
	public static StatusElginX5 ErroCMDnaoFislcaIndisponivel = new StatusElginX5( 8036, RELEVANC_ERRO, "Documento N�o Fiscal indispon�vel para carga" );
	
	public static StatusElginX5 ErroCMDReducaoInvalida = new StatusElginX5( 8037, RELEVANC_ERRO, "N�mero de redu��o inicial inv�lida" );
	
	public static StatusElginX5 ErroCMDCabecalhoJaImpresso = new StatusElginX5( 8038, RELEVANC_ERRO, "Cabe�alho do documento � foi impresso" );
	
	public static StatusElginX5 ErroCMDLinhasSuplementaresExcedidas = new StatusElginX5( 8039, RELEVANC_ERRO, "N�mero m�ximo de linhas de propaganda excedidas" );
	
	public static StatusElginX5 ErroHorarioVeraoAtualizado = new StatusElginX5( 8040, RELEVANC_ERRO, "Rel�gio j� est� no estado desejado" );
	
	public static StatusElginX5 ErroCMDValorAcrescimoInvalido = new StatusElginX5( 8041, RELEVANC_ERRO, "Valor do acr�scimo inconsistente" );
	
	public static StatusElginX5 ErroCMDNaohaMeiodePagamento = new StatusElginX5( 8042, RELEVANC_ERRO, "N�o h� meio de pagamento definido" );
	
	public static StatusElginX5 ErroCMDCOOVinculadoInvalido = new StatusElginX5( 8043, RELEVANC_ERRO, "COO do documento vinculado inv�lido" );
	
	public static StatusElginX5 ErroCMDIndiceItemInvalido = new StatusElginX5( 8044, RELEVANC_ERRO, "�ndice do item inexistente no contexto" );
	
	public static StatusElginX5 ErroCMDCodigoNaoEncontrado = new StatusElginX5( 8045, RELEVANC_ERRO, "C�digo de item n�o encontrado no cupom atual" );
	
	public static StatusElginX5 ErroCMDPercentualDescontoInvalido = new StatusElginX5( 8046, RELEVANC_ERRO, "Percentual do desconto ultrapassou 100%" );
	
	public static StatusElginX5 ErroCMDDescontoItemInvalido = new StatusElginX5( 8047, RELEVANC_ERRO, "Desconto do item inv�lido" );
	
	public static StatusElginX5 ErroCMDFaltaDefinirValor = new StatusElginX5( 8048, RELEVANC_ERRO, "Falta definir valor percentual ou absoluto em opera��o de desconto/acr�scimo" );
	
	public static StatusElginX5 ErroCMDItemCancelado = new StatusElginX5( 8049, RELEVANC_ERRO, "Tentativa de opera��o sobre item cancelado" );
	
	public static StatusElginX5 ErroCMDCancelaAcrDescInvalido = new StatusElginX5( 8050, RELEVANC_ERRO, "Cancelamento de acr�scimo/desconto inv�lidos" );
	
	public static StatusElginX5 ErroCMDAcrDescInvalido = new StatusElginX5( 8051, RELEVANC_ERRO, "Opera��o de acr�scimo/desconto inv�lida" );
	
	public static StatusElginX5 ErroCMDNaoHaMaisDepartamentosLivres = new StatusElginX5( 8052, RELEVANC_ERRO, "N�mero de Departamentos esgotados" );
	
	public static StatusElginX5 ErroCMDIndiceNaoFiscalInvalido = new StatusElginX5( 8053, RELEVANC_ERRO, "�ndice de Documento N�o Fiscal fora da faixa" );
	
	public static StatusElginX5 ErroCMDTrocaN�oFiscalAposZ = new StatusElginX5( 8054, RELEVANC_ERRO, "Troca de Documento N�o Fiscal somente ap�s a Redu��o Z" );
	
	public static StatusElginX5 ErroCMDInscricaoInvalida = new StatusElginX5( 8055, RELEVANC_ERRO, "CNPJ e/ou Inscri��o Estadual inv�lida(s)" );
	
	public static StatusElginX5 ErroCMDVinculadoParametrosInsuficientes = new StatusElginX5( 8056, RELEVANC_ERRO, "Falta(m) par�mentro(s) no comando de abertura de Comprovante Cr�dito ou D�bito" );
	
	public static StatusElginX5 ErroCMDNaoFiscalIndefinido = new StatusElginX5( 8057, RELEVANC_ERRO, "C�digo e Nome do Documento N�o Fiscal indefinidos" );
	
	public static StatusElginX5 ErroCMDFaltaAliquotaVenda = new StatusElginX5( 8058, RELEVANC_ERRO, "Al�quota n�o definida no comando de venda" );
	
	public static StatusElginX5 ErroCMDFaltaMeioPagamento = new StatusElginX5( 8059, RELEVANC_ERRO, "C�digo e Nome do Meio de Pagamento n�o definidos" );
	
	public static StatusElginX5 ErroCMDFaltaParametro = new StatusElginX5( 8060, RELEVANC_ERRO, "Par�metro de comando n�o informado" );
	
	public static StatusElginX5 ErroCMDNaoHaDocNaoFiscaisDefinidos = new StatusElginX5( 8061, RELEVANC_ERRO, "N�o h� Documentos N�o Fiscais definidos" );
	
	public static StatusElginX5 ErroCMDOperacaoJaCancelada = new StatusElginX5( 8062, RELEVANC_ERRO, "Acr�scimo/Desconto de item j� cancelado" );
	
	public static StatusElginX5 ErroCMDNaohaAcresDescItem = new StatusElginX5( 8063, RELEVANC_ERRO, "N�o h� acr�scimo/desconto em item" );
	
	public static StatusElginX5 ErroCMDItemAcrescido = new StatusElginX5( 8064, RELEVANC_ERRO, "Item j� possui acr�scimo" );
	
	public static StatusElginX5 ErroCMDOperSoEmICMS = new StatusElginX5( 8065, RELEVANC_ERRO, "Opera��o de acr�scimo em item ou subtotal s� � v�lido para ICMS" );
	
	public static StatusElginX5 ErroCMDFaltaInformaValor = new StatusElginX5( 8066, RELEVANC_ERRO, "Valor do Comprovante Cr�dito ou D�bito n�o informado" );
	
	public static StatusElginX5 ErroCMDCOOInvalido = new StatusElginX5( 8067, RELEVANC_ERRO, "COO inv�lido" );
	
	public static StatusElginX5 ErroCMDIndiceInvalido = new StatusElginX5( 8068, RELEVANC_ERRO, "�ndice do Meio de Pagamento no cupom inv�lido" );
	
	public static StatusElginX5 ErroCMDCupomNaoEncontrado = new StatusElginX5( 8069, RELEVANC_ERRO, "Documento N�o Fiscal n�o encontrado" );
	
	public static StatusElginX5 ErroCMDSequenciaPagamentoNaoEncontrada = new StatusElginX5( 8070, RELEVANC_ERRO, "Sequ�ncia de pagamento n�o encontrada no cupom" );
	
	public static StatusElginX5 ErroCMDPagamentoNaoPermiteCDC = new StatusElginX5( 8071, RELEVANC_ERRO, "Meio de Pagamento n�o permite CDC" );
	
	public static StatusElginX5 ErroCMDUltimaFormaPagamentoInv = new StatusElginX5( 8072, RELEVANC_ERRO, "Valor insuficiente para pagar o cupom" );
	
	public static StatusElginX5 ErroCMDMeioPagamentoNEncontrado = new StatusElginX5( 8073, RELEVANC_ERRO, "Meio de Pagamento origem ou destino n�o encontrado no �ltimo cupom emitido" );
	
	public static StatusElginX5 ErroCMDValorEstornoInvalido = new StatusElginX5( 8074, RELEVANC_ERRO, "Valor do estorno n�o pode exceder o valor do pagamento no meio origem" );
	
	public static StatusElginX5 ErroCMDMeiosPagamentoOrigemDestinoIguais = new StatusElginX5( 8075, RELEVANC_ERRO, "Meios de Pagamento origem e destino devem ser diferentes no estorno" );
	
	public static StatusElginX5 ErroCMDPercentualInvalido = new StatusElginX5( 8076, RELEVANC_ERRO, "Percentual da al�quota inv�lido" );
	
	public static StatusElginX5 ErroCMDNaoHouveOpSubtotal = new StatusElginX5( 8077, RELEVANC_ERRO, "N�o houve opera��o em subtotal para ser cancelada" );
	
	public static StatusElginX5 ErroCMDOpSubtotalInvalida = new StatusElginX5( 8078, RELEVANC_ERRO, "S� � permitida uma opera��o de acr�scimo em suvtotal por cupom" );
	
	public static StatusElginX5 ErroCMDTextoAdicional = new StatusElginX5( 8079, RELEVANC_ERRO, "Texto adicional do meio de pagamento dever ter no m�ximo 2 linhas" );
	
	public static StatusElginX5 ErroCMDPrecoUnitarioInvalido = new StatusElginX5( 8080, RELEVANC_ERRO, "Pre�o unit�rio ultrapassou o n�mero m�ximo de d�gitos permitido" );
	
	public static StatusElginX5 ErroCMDDepartamentoInvalido = new StatusElginX5( 8081, RELEVANC_ERRO, "C�digo do departamento fora da faixa" );
	
	public static StatusElginX5 ErroCDMDescontoInvalido = new StatusElginX5( 8082, RELEVANC_ERRO, "O valor do desconto n�o pode zerar o valor do cupom ou ser maior que o item" );
	
	public static StatusElginX5 ErroCMDPercentualAcrescimoInvalido = new StatusElginX5( 8083, RELEVANC_ERRO, "Percentual de acr�scimo n�o pode ser superior a 999,99%" );
	
	public static StatusElginX5 ErroCMDAcrescimoInvalido = new StatusElginX5( 8084, RELEVANC_ERRO, "Valor do acr�scimo ultrapassa o n�mero m�ximo de digitos permitido (13 d�gitos)" );
	
	public static StatusElginX5 ErroCMDNaoHouveVendaEmICMS = new StatusElginX5( 8085, RELEVANC_ERRO, "Cupom sem venda em al�quota de ICMS" );
	
	public static StatusElginX5 ErroCMDCancelamentoInvalido = new StatusElginX5( 8086, RELEVANC_ERRO, "Cancelamento inv�lido" );
	
	public static StatusElginX5 ErroCMDCliche = new StatusElginX5( 8087, RELEVANC_ERRO, "Texto de cliche do usu�rio dever ter no m�ximo tr�s linhas" );
	
	public static StatusElginX5 ErroCMDNaoHouveVendaNaoFiscal = new StatusElginX5( 8088, RELEVANC_ERRO, "N�o houve venda de item n�o fiscal" );
	
	public static StatusElginX5 ErroCMDDataInvalida = new StatusElginX5( 8089, RELEVANC_ERRO, "A data n�o pode ser inferior a data do �ltimo documento emitido" );
	
	public static StatusElginX5 ErroCMDHoraInvalida = new StatusElginX5( 8090, RELEVANC_ERRO, "A hora informada no comando n�o pode ser inferior ao hor�rio do �ltimo documento" );
	
	public static StatusElginX5 ErroCMDEstorno = new StatusElginX5( 8091, RELEVANC_ERRO, "Sem fun��o" );
	
	public static StatusElginX5 ErroCMDAcertoRelogio = new StatusElginX5( 8092, RELEVANC_ERRO, "Estado inv�lido para ajuste de relogio ou hor�rio de ver�o" );
	
	public static StatusElginX5 ErroCMDCDCInvalido = new StatusElginX5( 8093, RELEVANC_ERRO, "A opera��o de CDC deve preceder as opera��es de estorno de meio de pagamento" );
	
	public static StatusElginX5 ErroSenhaInvalida = new StatusElginX5( 8094, RELEVANC_ERRO, "Senha inv�lida para inicializa��o do propriet�rio" );
	
	public static StatusElginX5 ErroCMDMecanismoCheque = new StatusElginX5( 8095, RELEVANC_ERRO, "Erro gerado pelo mecanismo de cheques" );
	
	public static StatusElginX5 ErroFaltaIniciarDia = new StatusElginX5( 8096, RELEVANC_ERRO, "Comando v�lido somente ap�s a abertura do dia" );
	
	public static StatusElginX5 ErroMFDNenhumCartuchoVazio = new StatusElginX5( 9000, RELEVANC_ERRO, "N�o foi encontrado nenhum cartucho de dados vazion para ser inicializado" );
	
	public static StatusElginX5 ErroMFDCartuchoInexistente = new StatusElginX5( 9001, RELEVANC_ERRO, "Cartucho com o n�mero de s�rie informado n�o foi encontrado" );
	
	public static StatusElginX5 ErroMFDNumSerie = new StatusElginX5( 9002, RELEVANC_ERRO, "N�mero de s�rie do ECF � inv�lido na inicializa��o" );
	
	public static StatusElginX5 ErroMFDCartuchoDesconhecido = new StatusElginX5( 9003, RELEVANC_ERRO, "Cartucho de MFD desconctado ou com problemas" );
	
	public static StatusElginX5 ErroMFDEscrita = new StatusElginX5( 9004, RELEVANC_ERRO, "Erro de escrita no dispositivo de MDF" );
	
	public static StatusElginX5 ErroMFDSeek = new StatusElginX5( 9005, RELEVANC_ERRO, "Erro na tentativa de posicionar ponteiro de leitura" );
	
	public static StatusElginX5 ErroMFDBadBadSector = new StatusElginX5( 9006, RELEVANC_ERRO, "Endere�� do bad Sector informado � inv�lido" );
	
	public static StatusElginX5 ErroMFDLeitura = new StatusElginX5( 9007, RELEVANC_ERRO, "Erro de leitura na MFD" );
	
	public static StatusElginX5 ErroMFDLeituraAlemEOF = new StatusElginX5( 9008, RELEVANC_ERRO, "Tentativa de leitura al�m dos limites da MFD" );
	
	public static StatusElginX5 ErroMFDEsgotada = new StatusElginX5( 9009, RELEVANC_ERRO, "MFD n�o possui mais espa�o para escrita" );
	
	public static StatusElginX5 ErroMFDLeituraInterrompida = new StatusElginX5( 9010, RELEVANC_ERRO, "Leitura da MFD serial � interrompida por comando diferente de LeImpressao" );
	
	public static StatusElginX5 ErroBNFEstadoInvalido = new StatusElginX5( 10000, RELEVANC_ERRO, "Estado inv�lido para registro sendo codificado" );
	
	public static StatusElginX5 ErroBNDParametroInvalido = new StatusElginX5( 10001, RELEVANC_ERRO, "Inconsist�ncia nos par�mentros lidos no Logger" );
	
	public static StatusElginX5 ErroBNFRegistroInvalido = new StatusElginX5( 10002, RELEVANC_ERRO, "Registro inv�lido detectado no Logger" );
	
	public static StatusElginX5 ErroBNFErroMFD = new StatusElginX5( 10003, RELEVANC_ERRO, "Erro interno" );
	
	public static StatusElginX5 ErroProtParamInvalido = new StatusElginX5( 11000, RELEVANC_ERRO, "Par�metro repassado ao comando � inv�lido" );
	
	public static StatusElginX5 ErroProtParamSintaxe = new StatusElginX5( 11001, RELEVANC_ERRO, "Erro de sintaxe na lista de par�metros" );
	
	public static StatusElginX5 ErroProtParamValorInvalido = new StatusElginX5( 11002, RELEVANC_ERRO, "Valor inv�lido para par�metro do comando" );
	
	public static StatusElginX5 ErroProtParamStringInvalido = new StatusElginX5( 11003, RELEVANC_ERRO, "String cont�m sequ�ncia de caracteres inv�lidos" );
	
	public static StatusElginX5 ErroProtParamRedefinido = new StatusElginX5( 11004, RELEVANC_ERRO, "Par�metro foi declarado 2 ou mais vezes na lista" );
	
	public static StatusElginX5 ErroProtParamIndefinido = new StatusElginX5( 11005, RELEVANC_ERRO, "Par�metro obrigat�rio ausente na lista" );
	
	public static StatusElginX5 ErroProtComandoIncexistente = new StatusElginX5( 11006, RELEVANC_ERRO, "N�o existe o comando no protocolo" );
	
	public static StatusElginX5 ErroProtSequenciaComando = new StatusElginX5( 11007, RELEVANC_ERRO, "Estado atual n�o permite a execu��o deste comando" );
	
	public static StatusElginX5 ErroProtAborta2aVia = new StatusElginX5( 11008, RELEVANC_ERRO, "Sinaliza��o indicando que comando aborta a impress�o da segunda via" );
	
	public static StatusElginX5 ErroProtSemRetorno = new StatusElginX5( 11009, RELEVANC_ERRO, "Sinaliza��o indicando que comando n�o possui retorno" );
	
	public static StatusElginX5 ErroProtTimeout = new StatusElginX5( 11010, RELEVANC_ERRO, "Tempo de execu��o esgotado" );
	
	public static StatusElginX5 ErroProtNomeRegistrador = new StatusElginX5( 11011, RELEVANC_ERRO, "Nome de registrador inv�lido" );
	
	public static StatusElginX5 ErroProttipoRegistrador = new StatusElginX5( 11012, RELEVANC_ERRO, "Tipo de registrador inv�lido" );
	
	public static StatusElginX5 ErroProtSomenteLeitura = new StatusElginX5( 11013, RELEVANC_ERRO, "Tentativa de escrita em registrador de apenas leitura" );
	
	public static StatusElginX5 ErroProtSomenteEscrita = new StatusElginX5( 11014, RELEVANC_ERRO, "Tentativa de leitura em registrador de apenas escrita" );
	
	public static StatusElginX5 ErroProtComandoDiferenteAnterior = new StatusElginX5( 11015, RELEVANC_ERRO, "Comando recebido diferente do anterior no buffer de recep��o" );
	
	public static StatusElginX5 ErroProtFilaCheia = new StatusElginX5( 11016, RELEVANC_ERRO, "Fila de comando cheia" );
	
	public static StatusElginX5 ErroProtIndiceRegistrador = new StatusElginX5( 11017, RELEVANC_ERRO, "�ndice de registrador indexado fora dos limites" );
	
	public static StatusElginX5 ErroProtNumEmissoesExcedido = new StatusElginX5( 11018, RELEVANC_ERRO, "N�mero de emiss�es do Logger foi excedido na Interven��o T�cnica" );
	
	public static StatusElginX5 ErroMathDivisaoPorZero = new StatusElginX5( 11019, RELEVANC_ERRO, "Divis�o por 0(zero) nas rotinas de BDC" );
	
	public static StatusElginX5 ErroApenasIntTecnica = new StatusElginX5( 15001, RELEVANC_ERRO, "Comando aceito apenas em modo de Interven��o T�cnica" );
	
	public static StatusElginX5 ErroECFIntTecnica = new StatusElginX5( 15002, RELEVANC_ERRO, "Comando n�o pode ser executado em modo de Interven��o T�cnica" );
	
	public static StatusElginX5 ErroMFDPresente = new StatusElginX5( 15003, RELEVANC_ERRO, "J� existe MFD presente neste ECF" );
	
	public static StatusElginX5 ErroSemMFD = new StatusElginX5( 15004, RELEVANC_ERRO, "N�o existe MFD neste ECF" );
	
	public static StatusElginX5 ErroRAMInconsistente = new StatusElginX5( 15005, RELEVANC_ERRO, "Mem�ria RAM do ECF n�o esta consistente" );
	
	public static StatusElginX5 ErroMemoriaFiscalDesconectada = new StatusElginX5( 15006, RELEVANC_ERRO, "Mem�ria fiscal n�o encontrada" );
	
	public static StatusElginX5 ErroDiaFechado = new StatusElginX5( 15007, RELEVANC_ERRO, "Dia j� fechado" );
	
	public static StatusElginX5 ErroDiaAberto = new StatusElginX5( 15008, RELEVANC_ERRO, "Dia aberto" );
	
	public static StatusElginX5 ErroZPendente = new StatusElginX5( 15009, RELEVANC_ERRO, "Falta Redu��o Z" );
	
	public static StatusElginX5 ErroMecanismoNaoConfigurado = new StatusElginX5( 15010, RELEVANC_ERRO, "Mecanismo impressor n�o selecionado" );
	
	public static StatusElginX5 ErroSemPapel = new StatusElginX5( 15011, RELEVANC_ERRO, "Sem bobina de papel na esta��o de documento fiscal" );
	
	public static StatusElginX5 ErroDocumentoEncerrado = new StatusElginX5( 15012, RELEVANC_ERRO, "Tentativa de finalizar documento j� encerrado" );
	
	public static StatusElginX5 ErroSemSinalDTR = new StatusElginX5( 15013, RELEVANC_ERRO, "N�o h� sinal de DTR" );
	
	public static StatusElginX5 ErroSemInscricoes = new StatusElginX5( 15014, RELEVANC_ERRO, "Sem inscri��es do usu�rio no ECF" );
	
	public static StatusElginX5 ErroSemCliche = new StatusElginX5( 15015, RELEVANC_ERRO, "Sem dados do propriet�rio no ECF" );
	
	public static StatusElginX5 ErroEmLinha = new StatusElginX5( 15016, RELEVANC_ERRO, "ECF encontra-se indevidamente em linha" );
	
	public static StatusElginX5 ErroForaDeLinha = new StatusElginX5( 15017, RELEVANC_ERRO, "ECF n�o encontra-se em linha para executar o comando" );
	
	public static StatusElginX5 ErroMecanismoBloqueado = new StatusElginX5( 15018, RELEVANC_ERRO, "Mecanismo est� indispon�vel para impress�o" );

	private String message;
	
	private String messageComplementar;

	private int code;

	private int relevanc;

	private static final List<StatusElginX5> statusList = new ArrayList<StatusElginX5>();
	
	
	static {
		statusList.add( ErroGeralFalRAM );
		statusList.add( ErroGeralPerdaRAM );
		statusList.add( ErroMFDesconectada );
		statusList.add( ErroMFLeitura );
		statusList.add( ErroMFApenasLeitura );
		statusList.add( ErroMFTamRegistro );
		statusList.add( ErroMFCheia );
		statusList.add( ErroMFCartuchosExcedidos );
		statusList.add( ErroMFJaInicializada );
		statusList.add( ErroMFNaoInicializada );
		statusList.add( ErroMFUsuariosExcedidos );
		statusList.add( ErroMFIntervencoesExedidas );
		statusList.add( ErroMFVersoesExcedidas );
		statusList.add( ErroMFReducoesExcedidas );
		statusList.add( ErroMFGravacao );
		statusList.add( ErroTransactDrvrLeitura );
		statusList.add( ErroTransactDrvrEscrita );
		statusList.add( ErroTransactDrvrDesconectado );
		statusList.add( ErroTransactRegInvalido );
		statusList.add( ErroTransactCheio );
		statusList.add( ErroTransactTransAberta );
		statusList.add( ErroTransactTransNaoAberta );
		statusList.add( ErroContextDrvrLeitura );
		statusList.add( ErroContextDrvrEscrita );
		statusList.add( ErroContextDrvrDesconectado );
		statusList.add( ErroContextDrvrLeituraAposFim );
		statusList.add( ErroContextDrvrEscritaAposFim );
		statusList.add( ErroContextVersaoInvalida );
		statusList.add( ErroContextCRC );
		statusList.add( ErroContextLimitesExcedidos );
		statusList.add( ErroRelogioInconsistente );
		statusList.add( ErroRelogioDataHoraInvalida );
		statusList.add( ErroPrintSemMecanismo );
		statusList.add( ErroPrintDesconectado );
		statusList.add( ErroPrintCapacidadeInesistente );
		statusList.add( ErroPrintSemPapel );
		statusList.add( ErroPrintFaltouPapel );
		statusList.add( ErroCMDForaDeSequencia );
		statusList.add( ErroCMDCodigoInvalido );
		statusList.add( ErroCMDDescricaoInvalida );
		statusList.add( ErroCMDQuantidadeInvalida );
		statusList.add( ErroCMDAliquotaInvalida );
		statusList.add( ErroCMDAliquotaNaoCarregada );
		statusList.add( ErroCMDValorInvalido );
		statusList.add( ErroCMDMontanteOperacao );
		statusList.add( ErroCMDAliquotaIndisponivel );
		statusList.add( ErroCMDValorAliquotaInvalido );
		statusList.add( ErroCMDTrocaSTAposFechamento );
		statusList.add( ErroCMDFormaPagamentoInvalida );
		statusList.add( ErroCMDPayIndisponivel );
		statusList.add( ErroCMDCupomTotalizadoEmZero );
		statusList.add( ErroCMDFomraPagamentoIndefinida );
		statusList.add( ErroCMDtracaUsuarioAposFechamento );
		statusList.add( ErroCMDSemMovimento );
		statusList.add( ErroCMDPagamentoIncompleto );
		statusList.add( ErroCMDGerencialNaoDefinido );
		statusList.add( ErroGerencialInvalido );
		statusList.add( ErroCMDGerencialIndisponivel );
		statusList.add( ErroCMDNomeGerencialInvalido );
		statusList.add( ErroCMDNaoHaMaisRelatoriosLivres );
		statusList.add( ErroCMDAcertoHVPermitidoAposZ );
		statusList.add( ErroCMDHorarioVeraoJaRealizado );
		statusList.add( ErroCMDAliquotasIndisponiveis );
		statusList.add( ErroCMDItemInexistente );
		statusList.add( ErroCMDQtdCancInvalida );
		statusList.add( ErroCMDCampoCabecalhoInvalido );
		statusList.add( ErroCMDNomeDepartamentoInvalido );
		statusList.add( ErroCMDDepartamentoNaoEncontrado );
		statusList.add( ErroCMDDepartamentoIndefinido );
		statusList.add( ErroCMDFormasPagamentosIndisponiveis );
		statusList.add( ErroCMDAltPagamentosSoAposZ );
		statusList.add( ErroCMDNomeNalFiscalInvalido );
		statusList.add( ErroCMDDocsFiscaisIndisponiveis );
		statusList.add( ErroCMDnaoFislcaIndisponivel );
		statusList.add( ErroCMDReducaoInvalida );
		statusList.add( ErroCMDCabecalhoJaImpresso );
		statusList.add( ErroCMDLinhasSuplementaresExcedidas );
		statusList.add( ErroHorarioVeraoAtualizado );
		statusList.add( ErroCMDValorAcrescimoInvalido );
		statusList.add( ErroCMDNaohaMeiodePagamento );
		statusList.add( ErroCMDCOOVinculadoInvalido );
		statusList.add( ErroCMDIndiceItemInvalido );
		statusList.add( ErroCMDCodigoNaoEncontrado );
		statusList.add( ErroCMDPercentualDescontoInvalido );
		statusList.add( ErroCMDDescontoItemInvalido );
		statusList.add( ErroCMDFaltaDefinirValor );
		statusList.add( ErroCMDItemCancelado );
		statusList.add( ErroCMDCancelaAcrDescInvalido );
		statusList.add( ErroCMDAcrDescInvalido );
		statusList.add( ErroCMDNaoHaMaisDepartamentosLivres );
		statusList.add( ErroCMDIndiceNaoFiscalInvalido );
		statusList.add( ErroCMDTrocaN�oFiscalAposZ );
		statusList.add( ErroCMDInscricaoInvalida );
		statusList.add( ErroCMDVinculadoParametrosInsuficientes );
		statusList.add( ErroCMDNaoFiscalIndefinido );
		statusList.add( ErroCMDFaltaAliquotaVenda );
		statusList.add( ErroCMDFaltaMeioPagamento );
		statusList.add( ErroCMDFaltaParametro );
		statusList.add( ErroCMDNaoHaDocNaoFiscaisDefinidos );
		statusList.add( ErroCMDOperacaoJaCancelada );
		statusList.add( ErroCMDNaohaAcresDescItem );
		statusList.add( ErroCMDItemAcrescido );
		statusList.add( ErroCMDOperSoEmICMS );
		statusList.add( ErroCMDFaltaInformaValor );
		statusList.add( ErroCMDCOOInvalido );
		statusList.add( ErroCMDIndiceInvalido );
		statusList.add( ErroCMDCupomNaoEncontrado );
		statusList.add( ErroCMDSequenciaPagamentoNaoEncontrada );
		statusList.add( ErroCMDPagamentoNaoPermiteCDC );
		statusList.add( ErroCMDUltimaFormaPagamentoInv );
		statusList.add( ErroCMDMeioPagamentoNEncontrado );
		statusList.add( ErroCMDValorEstornoInvalido );
		statusList.add( ErroCMDMeiosPagamentoOrigemDestinoIguais );
		statusList.add( ErroCMDPercentualInvalido );
		statusList.add( ErroCMDNaoHouveOpSubtotal );
		statusList.add( ErroCMDOpSubtotalInvalida );
		statusList.add( ErroCMDTextoAdicional );
		statusList.add( ErroCMDPrecoUnitarioInvalido );
		statusList.add( ErroCMDDepartamentoInvalido );
		statusList.add( ErroCDMDescontoInvalido );
		statusList.add( ErroCMDPercentualAcrescimoInvalido );
		statusList.add( ErroCMDAcrescimoInvalido );
		statusList.add( ErroCMDNaoHouveVendaEmICMS );
		statusList.add( ErroCMDCancelamentoInvalido );
		statusList.add( ErroCMDCliche );
		statusList.add( ErroCMDNaoHouveVendaNaoFiscal );
		statusList.add( ErroCMDDataInvalida );
		statusList.add( ErroCMDHoraInvalida );
		statusList.add( ErroCMDEstorno );
		statusList.add( ErroCMDAcertoRelogio );
		statusList.add( ErroCMDCDCInvalido );
		statusList.add( ErroSenhaInvalida );
		statusList.add( ErroCMDMecanismoCheque );
		statusList.add( ErroFaltaIniciarDia );
		statusList.add( ErroMFDNenhumCartuchoVazio );
		statusList.add( ErroMFDCartuchoInexistente );
		statusList.add( ErroMFDNumSerie );
		statusList.add( ErroMFDCartuchoDesconhecido );
		statusList.add( ErroMFDEscrita );
		statusList.add( ErroMFDSeek );
		statusList.add( ErroMFDBadBadSector );
		statusList.add( ErroMFDLeitura );
		statusList.add( ErroMFDLeituraAlemEOF );
		statusList.add( ErroMFDEsgotada );
		statusList.add( ErroMFDLeituraInterrompida );
		statusList.add( ErroBNFEstadoInvalido );
		statusList.add( ErroBNDParametroInvalido );
		statusList.add( ErroBNFRegistroInvalido );
		statusList.add( ErroBNFErroMFD );
		statusList.add( ErroProtParamInvalido );
		statusList.add( ErroProtParamSintaxe );
		statusList.add( ErroProtParamValorInvalido );
		statusList.add( ErroProtParamStringInvalido );
		statusList.add( ErroProtParamRedefinido );
		statusList.add( ErroProtParamIndefinido );
		statusList.add( ErroProtComandoIncexistente );
		statusList.add( ErroProtSequenciaComando );
		statusList.add( ErroProtAborta2aVia );
		statusList.add( ErroProtSemRetorno );
		statusList.add( ErroProtTimeout );
		statusList.add( ErroProtNomeRegistrador );
		statusList.add( ErroProttipoRegistrador );
		statusList.add( ErroProtSomenteLeitura );
		statusList.add( ErroProtSomenteEscrita );
		statusList.add( ErroProtComandoDiferenteAnterior );
		statusList.add( ErroProtFilaCheia );
		statusList.add( ErroProtIndiceRegistrador );
		statusList.add( ErroProtNumEmissoesExcedido );
		statusList.add( ErroMathDivisaoPorZero );
		statusList.add( ErroApenasIntTecnica );
		statusList.add( ErroECFIntTecnica );
		statusList.add( ErroMFDPresente );
		statusList.add( ErroSemMFD );
		statusList.add( ErroRAMInconsistente );
		statusList.add( ErroMemoriaFiscalDesconectada );
		statusList.add( ErroDiaFechado );
		statusList.add( ErroDiaAberto );
		statusList.add( ErroZPendente );
		statusList.add( ErroMecanismoNaoConfigurado );
		statusList.add( ErroSemPapel );
		statusList.add( ErroDocumentoEncerrado );
		statusList.add( ErroSemSinalDTR );
		statusList.add( ErroSemInscricoes );
		statusList.add( ErroSemCliche );
		statusList.add( ErroEmLinha );
		statusList.add( ErroForaDeLinha );
		statusList.add( ErroMecanismoBloqueado );
	}
	
	public static StatusElginX5 getStatusElginX5( int code, String messageComplementar ) {
		
		StatusElginX5 status = null;
		
		for ( StatusElginX5 sd : statusList ) {
			if ( sd.getCode() == code ) {
				status = sd;
				status.setMessageComplementar( messageComplementar );
				break;
			}
		}
		
		return status;
	}

	public StatusElginX5( int code, int relevanc, String message ) {

		this.code = code;
		this.relevanc = relevanc;
		this.message = message;
	}

	public void setCode( int code ) {	
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}

	public void setMessage( String message ) {	
		this.message = message;
	}

	public String getMessage() {
		return message + (getMessageComplementar()==null ? "" : (": " + getMessageComplementar()) ) ;
	}
	
	public String getMessageComplementar() {	
		return messageComplementar;
	}
	
	public void setMessageComplementar( String messageComplementar ) {	
		this.messageComplementar = messageComplementar;
	}

	public void setRelevanc( int relevanc ) {	
		this.relevanc = relevanc;
	}

	public int getRelevanc() {
		return relevanc;
	}
}
