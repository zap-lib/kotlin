<h1><img src="https://user-images.githubusercontent.com/6410412/282291416-f46dbcda-e298-4dee-bd26-70b23515b6cc.png" width="22px" height="22px" /> Zap</h1>

Zap implementation for Kotlin.

Zap is a development library for building multi-device application that enable communication with other devices. While mobile devices offer a wide range of data sources, such as motion sensors, biometrics devices, microphones, touchscreens and more, traditional PCs like laptops and desktops are typically lack these resources.

The data sources available on mobile devices are valuable, but are often device-dependent, limiting their widespread use. To get over this limitation, Zap provides programming interface to access data sources on other devices. Imagine if PCs could use the series of data from the accelerometer sensor on a mobile device. A simple example is using smartphone as motion controller for PC.

<video src="https://user-images.githubusercontent.com/6410412/281803373-bd6b55e0-65cd-421a-9504-5df169d31c03.mp4" muted controls></video>

The main goal of Zap is to support mobile-PC communication, but it also extends its capabilities to enable mobile-mobile and PC-PC communication. Furthermore, it's not limited to PCs; any devices capable of running Zap implementations(e.g., Kiosk device, Smart TV, etc.) can also participate in this communication.

If you want to learn more about Zap, please read the documentation: https://zap-lib.github.io/

## Setup

The latest release is available on [JitPack](https://jitpack.io/#zap-lib/kotlin).

### Gradle

```kotlin
dependencyResolutionManagement {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```

```kotlin
dependencies {
  implementation 'com.github.zap-lib:kotlin:0.2.0'
}
```

### Maven

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

```xml
<dependency>
  <groupId>com.github.zap-lib</groupId>
  <artifactId>kotlin</artifactId>
  <version>0.2.0</version>
</dependency>
```

## Usage

A client can transmit data obtained from their local data sources to remote device. The following code is an example of sending values obtained from an accelerometer sensor on an Android mobile device to the server.

```kotlin
class MainActivity: AppCompatActivity(), SensorEventListener {
  private lateinit var zap: ZapClient

  override fun onCreate(state: Bundle?) {
    // Create a new zap client with the server's IP address.
    zap = ZapClient(InetAddress.getByName(...))
  }

  // Define the method that is invoked whenever
  // any sensors on the local device have changed.
  override fun onSensorChanged(event: SensorEvent) {
    if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
      val (x, y, z) = event.values
      // Send the data acquired from the accelerometer to the server.
      zap.send(ZapAccelerometer(x, y, z))
    }
  }
}
```

On the server side, the values sent by the client can be easily received and used.

```kotlin
fun main() {
  // Create and start a new zap server to listen for data from clients.
  object : ZapServer() {
    // Define the method that is called whenever accelerometer sensor data is
    // received from client devices.
    override fun onAccelerometerChanged(info: MetaInfo, data: ZapAccelerometer) {
      println("Data received from ${info.dgram.address}, (${data.x}, ${data.y}, ${data.z})")
    }
  }.listen()
}
```

For more use cases, please check the [Examples](https://github.com/zap-lib/examples).

## License

Zap is released under the [Apache License, Version 2.0](LICENSE).
