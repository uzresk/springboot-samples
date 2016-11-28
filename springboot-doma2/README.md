
- http://www.thymeleaf.org/eclipse-plugin-update-site/

- formとcontrollerは1:1
- formのgetter,setter,toStringはすべてlombokを利用する

- Eclipseの設定
    - html インライン要素から削除（input,span,div



- 層について
    - Entityとテーブルは同一のモデルとしてみなし、複雑なSQL以外はすべて自動で生成するS2JDBCのようなイメージにする
    - Entityはentityパッケージ。Entityに紐付くロジックはserviceパッケージに定義する。
    - 画面固有のビジネスロジックについてはcontroller側で記載する。

- Component
     - componentは余計なオブジェクトを生成しないため、Singleton(default)で取り扱う
     - componentとなるオブジェクトにはメンバ変数を記載しないこと。

- validator
    - 必須チェック、属性チェック、相関チェックのタイミングは分割する（必須チェックが通らないと属性チェックは行わない）
    - 日付、電話番号、Emailなどのチェックの場合、チェック内で属性のチェックを行う（日付フィールドに数値チェックと日付チェックをわざわざつけない）
    - その画面でしか利用しない複数項目のチェックは画面固有のValidatorで実装する。複数画面で利用するものは独自のValidatorを作る
    - 参考：http://qiita.com/eiryu/items/95a206d617bd2b956953
    - Validationの仕様はここから
        - http://beanvalidation.org/specification/
        - http://hibernate.org/validator/documentation/

- テーブル名とEntityのマッピング
    - テーブル名はsnake、Entityはcamelで記載する。
    - マッピングに関してはEntityのアノテーションで解決する（@Entity(naming=NamingType.SNAKE_UPPER_CASE)）
    - EntityクラスBaseEntityクラスを継承する。BaseEntityクラスは共通のフィールドを保持し、BaseEntityListenerでInsert,Update時に共通のカラムがupdateされる
- 楽観排他
     - 各テーブルにはバージョンIDカラムを持ち、@Versionを付与しておく。

- TODO
    - 辞書作る
    - validatorの一覧
    - Controller層からDaoの直接利用は可とするか。

- はまったところ
    - SubmitボタンのnameでControllerのメソッドを振り分けるのは振り分け先のメソッドが両方ともPostMappingである必要がある。