#!/bin/sh


ISC_USER="SYSDBA"
ISC_PASSWORD="masterkey"
CMD_GBAK="/opt/firebird/bin/gbak"
RESULT=0

fn_senha_firebird()
{
   SENHA_FIREBIRD_TMP=""
   while [ -z $SENHA_FIREBIRD_TMP ]; do 
      echo "Senha do firebird? (Deixe em branco para padr�o)"
      echo "Padr�o: $ISC_PASSWORD" 
      read SENHA_FIREBIRD_TMP
      if [ -z $SENHA_FIREBIRD_TMP ]; then 
        SENHA_FIREBIRD_TMP=$ISC_PASSWORD
      fi
      echo "Senha: $SENHA_FIREBIRD_TMP"
      echo "Senha est� correta? (S/N)"
      read OPCAO
      if [ $OPCAO = "N" -o $OPCAO = "n" ]; then 
         SENHA_FIREBIRD_TMP=""
      fi
   done
   ISC_PASSWORD="$SENHA_FIREBIRD_TMP"
   
   #export ISC_USER
   #export ISC_PASSWORD
}

fn_fim_script() 
{
  echo $LINHA_COMENTARIO
  if [ $RESULT -gt 0 ]; then
     echo "Script foi finalizado com erro(s)!"
  else
     echo "Script conclu�do com sucesso!"
  fi
  echo $LINHA_COMENTARIO
  exit 0
}

fn_executa() 
{
  fn_senha_firebird
  if [ "$ISC_PASSWORD" = "0" ]; then
    fn_fim_script
  fi

  #export $ISC_USER
  #export $ISC_PASSWORD   
  $CMD_GBAK -B localhost:/opt/firebird/dados/desenv/freedom.fdb /tmp/freedom.fbk -user "$ISC_USER" -pass "$ISC_PASSWORD"
  rm ./freedom.zip
  zip ./freedom.zip /tmp/freedom.fbk
   
}

fn_executa

fn_fim_script

