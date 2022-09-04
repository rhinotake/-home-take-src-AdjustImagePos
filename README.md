いくつかのイメージファイルの位置合わせをするためのデータを作成する。

1. Mainクラスを実行する。
2. イメージを開く
3. 位置合わせの基準となる位置をクリックする
4. クリックした位置に + が表示される
5. それを延々と繰り返す
6. 結果は、points.jsonに出力される
7. 一通り位置を定義したら、MkConvertCommandを実行する

```json
[ {
  "path" : "jjj/IMG_3501.JPG",
  "x" : 2061,
  "y" : 623
}, {
  "path" : "jjj/IMG_3502.JPG",
  "x" : 2089,
  "y" : 619
}, {
```

8. MkConvertCommandを実行すると、イメージファイルを変換するconvertコマンドを吐き出す
9. convertコマンドを実行する

```shell
convert jjj/IMG_3501.JPG -distort Affine '0,0 45,-30' jjj2/IMG_3501.JPG
convert jjj/IMG_3502.JPG -distort Affine '0,0 17,-26' jjj2/IMG_3502.JPG
```

10. convertコマンドで animation gif ファイルを作成する

```shell
convert -delay 50 -loop 1 jjj2/* animation.gif
```

11. ffmpegコマンドで animation gif ファイルを mp4 ファイルに変換する

```shell
ffmpeg -i animation.gif  \
  -movflags faststart \
  -pix_fmt yuv420p \
  -vf "scale=trunc(iw/2)*2:trunc(ih/2)*2" \
  out.mp4 ```