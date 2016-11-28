# SpringBoot1.4.2 + Doma2の実装サンプル

作る前に最低限の部分を盛り込んどく

## タスク

* 画面

- [x] 会員登録
- [x] 会員情報変更
- [x] ログイン
- [x] TOP
- [ ] 検索
- [ ] 会員情報削除

* Controller

- [x] Contoller前後でのログ出力（traceでパラメータ出力）
- [x] 入力チェックの順序性（必須チェック→属性チェック→相関チェック）
- [ ] FlashScope使うサンプルを入れておきたい
- [ ] コード管理
- [ ] 例外処理共通化
- [ ] SessionAttributesを使うとちょっと見通し悪い？再度確認
- [ ] チェックボックスやらいろいろなフォームのサンプル

* DB周り(Doma)
- [x] 登録者・登録日、更新者・更新日を自動で設定
- [ ] Select for updateの実装サンプル
- [ ] 検索SQLのサンプル、ページングのサンプル
- [ ] DomaのEntity自動生成を試しとく
- [ ] Stream,Collectを使うサンプルも作っとく
- [x] EntityでDateTimeAPIを使う
- [x] Optionalを使う
- [ ] システム日付の外部化
- [ ] テストの実装方法

* その他
- [x] messages.propertiesにまとめる
- [ ] RBACの実装
