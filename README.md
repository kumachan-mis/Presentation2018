# Presentation2018
MIS.Wプロ研発表会2018で発表したゲームです。情報理工学実験Bの成績が確定し次第、ソースコードも公開します。  
石川県の伝統ゲーム「ごいた」を実装しました。  
フリー素材を一部お借りしています。引用元サイトは フリー素材使用報告.txt をご覧ください。

Copyright 2018 Kumachan(Yuya SUWA), MIS.W, Waseda Univ.


## 遊ぶには
このプログラムの実行にはJava仮想マシン(JVM)が必要です。Java8のダウンロードは下記より行ってください。
[Oracle Javaをインストールするには](https://www.java.com/ja/download/help/download_options.xml)

以下に実行の手順を記載しておきます。
1. exe ディレクトリに移動する。  
<コマンド>  
`cd Presentation2018/exe`

2. 1人がサーバを立ち上げる。  
<コマンド>  
`java goita.server.GoitaServer XXXX`  
> XXXX には通信したいポート番号を入れてください。1024以上、65535以下であればなんでも良いです。
3. 4人がクライアントを立ち上げる。  
<コマンド>  
`java -cp core.jar:. goita.client.GoitaClient XXXX IP_ADDR`  
> XXXX にはサーバ側が入力したポート番号と同じ数字を入れてください。  
> IP_ADDR にはサーバ役のコンピュータのIPアドレスを入れてください。ローカルホスト名で接続できる場合もあります。  
4. あとは画面の指示に従って楽しく遊んでください！