# 開発・リリースの進め方

- 作業は`main`で進める。作業前に`git status --short`を確認し、既存の変更を上書き・削除しない。
- 変更したら`mvn -B package`と`python3 scripts/test-hpview-paper.py`を通す。

## リリース

- バージョンはリリースを頼まれたときだけ上げる。ふだんの実装ではバージョンに触れない。
- 公開済みのリリースのJARは差し替えない（`--clobber`は使わない）。変更を配るときは、必ず新しいバージョンで新しいリリースを作る。
- バージョンはSemVerで決める。不具合修正だけなら`1.0.1`のようにパッチ、機能追加や設定項目の追加はマイナー（`1.1.0`）、既存の設定やコマンドが使えなくなる変更はメジャー（`2.0.0`）を上げる。迷ったらユーザーに聞く。
- バージョンを上げるときは、次の箇所をすべて新しい値にそろえる（`git grep -n '<旧バージョン>'`で漏れを確かめる）。
  - `pom.xml`の`<version>`
  - `README.md`・`README.ja.md`・`README.zh-CN.md`・`README.ko.md`のJAR名
  - `scripts/test-hpview-paper.py`のJARのパス
  - `.github/ISSUE_TEMPLATE/bug_report.yml`の`placeholder`
- リリースの手順:
  1. 上の箇所を更新し、`mvn -B package`とPaperテストを通してコミットする。
  2. `target/hpview-<版>.jar`の`SHA256SUMS`を作る。
  3. `gh release create v<版> target/hpview-<版>.jar SHA256SUMS`で、変更点を書いたリリースノートを付けて公開する。
  4. 公開URLからJARを落とし直し、SHA-256が`SHA256SUMS`と一致することを確かめる。
  5. `../spsmc-infra/Dockerfile`の`HPVIEW_URL`を新しいリリースのURLに、`HPVIEW_SHA256`を新しい値に変える。
