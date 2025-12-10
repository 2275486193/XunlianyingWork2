# W2 天气演示应用

一个基于高德开放平台 WebService 天气接口的 Android 演示项目。支持下拉选择北京/上海/广州/深圳四个城市，展示实时天气与未来四天预报。项目仅使用标准库 `HttpURLConnection` 与 `org.json`，不引入额外网络依赖，并强制使用国内镜像源进行依赖与工具下载。

## 功能
- 城市下拉选择：北京、上海、广州、深圳
- 实时天气展示：天气、温度、风向/风力/湿度（缺失项自动隐藏）
- 未来四天预报：日期、天气、温度范围、风向
- 仅使用镜像源：Aliyun/Huawei/Tencent Gradle 与 Maven 镜像

## 环境要求
- JDK：`11`（项目已固定 Gradle 运行时为 `D:/JDK11`）
- Android SDK：`sdk.dir` 指向本机 Android SDK 目录（`local.properties`）
- Gradle Wrapper：`7.6`（镜像 `gradle-7.6-all.zip`）
- Android Gradle Plugin：`7.4.2`
- Kotlin：`1.8.20`
- compileSdk / targetSdk：`34`

## 快速开始
1. 配置密钥与 SDK 路径（文件：`local.properties`）
   - `AMAP_KEY=你的高德Key`
   - `sdk.dir=D:/Android/Sdk`
2. 构建调试包
   - `./gradlew.bat :app:assembleDebug`
3. 安装运行
   - 使用 Android Studio 运行或将 `app/build/outputs/apk/debug/app-debug.apk` 安装到设备

## 镜像配置
- Gradle Wrapper：`gradle/wrapper/gradle-wrapper.properties`
  - `distributionUrl=https://mirrors.cloud.tencent.com/gradle/gradle-7.6-all.zip`
- 仓库镜像：`settings.gradle.kts`
  - Aliyun：`google`、`central`、`gradle-plugin`、`public`、`androidx`
  - Huawei：`https://repo.huaweicloud.com/repository/maven/*`
  - 已启用 `metadataSources { mavenPom(); artifact() }` 提升兼容性
- 固定 Gradle JVM：`gradle.properties`
  - `org.gradle.java.home=D:/JDK11`

## 高德天气接入
- 接口文档：`https://lbs.amap.com/api/webservice/guide/api/weatherinfo`
- 实时天气（base）：`/v3/weather/weatherInfo?city=<adcode>&key=<AMAP_KEY>&extensions=base`
- 未来预报（all）：`/v3/weather/weatherInfo?city=<adcode>&key=<AMAP_KEY>&extensions=all`
- 城市编码（adcode）：北京 `110000`、上海 `310000`、广州 `440100`、深圳 `440300`

## 目录结构（关键）
- `app/src/main/java/com/example/w2/MainActivity.kt`
  - 处理城市选择与两次接口调用，解析并渲染 UI
- `app/src/main/res/layout/activity_main.xml`
  - 布局包含城市下拉（Spinner）、实时卡片与预报列表
- `app/src/main/java/com/example/w2/ForecastAdapter.kt`
  - 预报列表适配器与数据结构 `ForecastItem`

## 常见问题
- Gradle/IDE 使用了错误的 JVM（如 JBR 21）
  - 已在 `gradle.properties` 固定为 `D:/JDK11`；如 IDE 仍报错，请在 IDE 设置中将 Gradle JDK 设为 11
- 仅使用镜像源下载
  - 仓库镜像与 Gradle Wrapper 已指向国内镜像；如仍访问官方，请清理 Gradle/IDE 缓存后重试
- 依赖版本不兼容
  - 关键版本：`AGP 7.4.2 / Gradle 7.6 / Kotlin 1.8.20 / activity 1.6.1 / material 1.9.0`；升级将可能需要切换到 JDK 17/AGP 8+


