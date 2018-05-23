if [ $# -ne 2 ]; then
  echo "[ERROR]通信するポート番号とサーバのIPアドレスを指定してください" 1>&2
  exit 1
fi
java \
-cp \
\/Applications/Processing.app/Contents/Java/core/library/core.jar:. \
goita.client.GoitaClient \
$1 $2