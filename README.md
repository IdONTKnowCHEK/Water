
<img width="64" src="https://user-images.githubusercontent.com/86880683/226264916-11af814a-45a4-459a-ab37-96eeef542947.png" />

# 「水」時隨地

這是一個搭配物聯網裝置和水杯，以雙重提醒的方式提醒使用者記得補充水分的APP。  
使用Firebase同步設備資料連接D1Mini做到硬體閃爍提醒。  
希望透過這個裝置，能夠提醒自己補充水分，避免忘記喝水的情況發生。  
即便沒有手機在身旁，只要水壺在旁也能達到喝水提醒的效果。
## 功能
| 功能列表      | 功能概述                             |
| ------------ | ----------------------------------- |
| **體重設定**  |設定體重，完成每日喝水目標：體重*30。   |
| **喝水提醒**  |根據天氣狀況和體重調節喝水提醒頻率。        |
| **水壺提醒**  |結合D1mini Wifi模組，水壺在設定的時間發出提醒。     |
| **喝水紀錄**  |記錄每次喝水紀錄，提供時間歷史紀錄。         |
| **植物養成**  |鼓勵多喝水，會隨著一天喝越多水長越大的小寵物。 |
| **天氣預報**  |獲得中央氣象局API，顯示目前溫度濕度。 |

<table>
  <tr>
    <th colspan="3"> 
        使用範例
    </th>
  </tr>
  <tr>
    <td colspan="3">
      <img src="https://user-images.githubusercontent.com/86880683/229344837-c06642f6-6aa2-4388-9857-ea3f3939d429.gif" width="800">
    </td>
  </tr>
  <tr>
    <th> 
        設定體重
    </th>
    <th> 
        記錄喝水
    </th>
    <th> 
        喝水紀錄
    </th>
  </tr>
</table>


- 燈泡閃爍代表該喝水了

![image](https://user-images.githubusercontent.com/86880683/226261466-f0a66dd4-5d26-45ba-b602-c157ea901391.png)
- 除了APP通知外，Line notify 也會接收來自D1Mini的訊號發送通知
<img src="https://user-images.githubusercontent.com/86880683/226262587-00f1fe50-0525-4d00-aa23-94a90d53fdf1.jpg" width="200" >

### 影片展示
[![DEMO](https://img.youtube.com/vi/3IvEtQEqFzo/0.jpg)](https://www.youtube.com/watch?v=3IvEtQEqFzo) 

[影片連結](https://youtu.be/3IvEtQEqFzo)

## 安裝
> Android Versioin: > 4.0
- 使用 Android Studio 開啟專案，等待 Gradle 同步完成後即可建置和執行 APP。
- 下載 APK 安裝執行
