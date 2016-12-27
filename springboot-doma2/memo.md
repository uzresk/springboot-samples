
# 環境構築

## Eclipse編

### pluginの設定
* Eclipse メニューバーから Help > Install New Software... と進み、Work With’のテキストボックスに次のURLを入力してください。
* Thymeleafのタグの保管をしてくれます
    * http://www.thymeleaf.org/eclipse-plugin-update-site/
* DomaのDaoからsqlファイルに飛べます
    * http://dl.bintray.com/domaframework/eclipse/

### プロジェクトの設定方法

* git clone https://github.com/uzresk/springboot-samples.git
* develop ブランチに変更
* インポート - フォルダーまたはアーカイブからプロジェクトを選択しspringboot-doma2を取り込む
* Javaのビルドパス - src/main/resourcesの除外が*.*になっているので、これを除去する
* プロジェクトのプロパティ - Javaコンパイラ - 注釈処理
    * プロジェクト固有の設定を可能にする。
* 注釈処理 - ファクトリーパス - 外部jarの追加からdoma.jarを追加する
- Eclipseの設定
    - html インライン要素から削除（input,span,div

### アプリケーションの実行方法

* DemoApplication.javaを右クリックして実行
* http://localhost:8080/app/でログイン画面が表示される
* http://localhost:8080/app/h2-console/でh2のConsole画面が表示される

---

## IntelliJ

* git clone
* Openする。pomを選択して、add as maven・・・
* lombok pluginをインストールする
    * http://siosio.hatenablog.com/entry/2013/12/23/000054
* devtoolsのreloadを有効にする
    * http://rabitarochan.hatenablog.com/entry/2016/01/12/010626
* thymeleaf templateをreload対象にする
    * maven経由で起動しているため、spring-boot-maven-plugin <addResource>true</addResource>が必要
* Run Configurationの設定
    * Command Line に spring-boot:runを設定
    

---

## Memo

* 画面を構成するのは、html, Controller, Service, Dao
* トランザクション境界はService
* Lombokを利用する
    * formのgetter,setter,toStringなどは@Data
    * slf4jも@slf4jを利用する
* Entityは自動生成する
* Component
     * componentは余計なオブジェクトを生成しないため、Singleton(default)で取り扱う
     * このため、componentとなるオブジェクトにはメンバ変数を記載しないこと。
* validator
    * 必須チェック、属性チェック、相関チェックのタイミングは分割する（必須チェックが通らないと属性チェックは行わない）
    * 日付、電話番号、Emailなどのチェックの場合、チェック内で属性のチェックを行う（日付フィールドに数値チェックと日付チェックをわざわざつけない）
    * その画面でしか利用しない複数項目のチェックは画面固有のValidatorで実装する。複数画面で利用するものは独自のValidatorを作る
    * 参考：http://qiita.com/eiryu/items/95a206d617bd2b956953
    * Validationの仕様はここから
        * http://beanvalidation.org/specification/
        * http://hibernate.org/validator/documentation/
* パッケージ/ディレクトリ構成
    * dao
    * entity
    * web
        * config
        * controller
            * error
            * login
    * service
* テーブル名とEntityのマッピング
    * テーブル名はsnake、Entityはcamelで記載する。
    * マッピングに関してはEntityのアノテーションで解決する（@Entity(naming=NamingType.SNAKE_UPPER_CASE)）
    * EntityクラスBaseEntityクラスを継承する。BaseEntityクラスは共通のフィールドを保持し、BaseEntityListenerでInsert,Update時に共通のカラムがupdateされる
* Entityの自動生成
    * Doma2Genを利用する予定
* 排他制御
    * 各テーブルにはバージョンIDカラムを持ち、@Versionを付与しておく。
* 例外
    * どんな例外も基本的にはGlobalErrorControllerが動く。
    * GlobalErrorControllerは例外をリクエストスコープに入れて画面側のErrorControllerにforwardする。
    * 一つControllerを間に挟んでいるのはRESTを考慮してのこと。
    * 404エラーは通常ではtomcatのエラーページが表示されてしまう。
* 認証/認可
    * 認証はSecurityConfigで設定
    * 認可はSpringSecurityの機能を使って実現
* コード値
    * プルダウンなどで使うコード値はCodeテーブルに格納しておく。
    * 起動時にCodeManagerが全件キャッシュしている。
    * キャッシュのリロード機能は未実装
    * テンプレート側からキャッシュされたコード値を利用する場合には、CodeUtilityを利用することで実現している。(edit.html参照）
* テスト
    * 未実装(dbsetupを使う？）
* はまったところ
    * SubmitボタンのnameでControllerのメソッドを振り分けるのは振り分け先のメソッドが両方ともPostMappingである必要がある。
