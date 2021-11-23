# Kotlin coroutine サンプル実装

IO系処理を非同期で処理できるように、kotlinxパッケージを利用したコルーチンの実装。

## 題材
https://raw.githubusercontent.com/simonNozaki/jsons/main/simple-kv.json にホストされているJSONファイルを非同期で5回、並行にリクエストを実行します。

## ビルド、実行
main.ktで実行できます。アプリケーションの処理手順はAppクラスにかかれています。
