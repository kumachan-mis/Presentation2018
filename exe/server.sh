if [ $# -ne 1 ]; then
  echo "[ERROR]通信するポート番号を指定してください" 1>&2
  exit 1
fi
java \
-cp \
\/Applications/Processing.app/Contents/Java/core/library/core.jar:. \
goita.server.GoitaServer \
$1