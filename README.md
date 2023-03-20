# 「水」時隨地

<img width="64" src="https://user-images.githubusercontent.com/86880683/226264916-11af814a-45a4-459a-ab37-96eeef542947.png" align="left" />

這是一個搭配物聯網裝置和水杯，以雙重提醒的方式提醒使用者記得補充水分的APP。
使用Firebase同步設備資料連接D1Mini做到硬體閃爍提醒。
希望透過這個裝置，能夠提醒自己補充水分，避免忘記喝水的情況發生。即便沒有手機在身旁，只要水壺在旁也能達到喝水提醒的效果。
## 功能
- 體重設定：完成每日喝水目標設定
- 喝水提醒：根據天氣情況和體重調節喝水頻率
- 水壺提醒：結合D1mini Wifi模組，水壺會在設定的時間點發出提醒
- 喝水紀錄：會記錄每次喝水時間，提供統計數據
- 植物養成：勤奮喝水養育自己的盆景植物
![功能](https://user-images.githubusercontent.com/86880683/226267812-a81b9467-9a5a-4690-9ad0-f690498e041f.jpg)


- 燈泡閃爍代表該喝水了
![image](https://user-images.githubusercontent.com/86880683/226261466-f0a66dd4-5d26-45ba-b602-c157ea901391.png)
- 除了APP通知外，Line notify 也會接收來自D1Mini的訊號發送通知
<img src="https://user-images.githubusercontent.com/86880683/226262587-00f1fe50-0525-4d00-aa23-94a90d53fdf1.jpg" width="200" >


## 安裝
Android Versioin: > 4.0
- 使用 Android Studio 開啟專案，等待 Gradle 同步完成後即可建置和執行 APP。
- 下載 APK 安裝執行
