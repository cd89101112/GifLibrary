# GifLibrary
基于DaVinci改造后的GIF加载库，主要是加载的时候对ImageView使用软引用解决内存泄漏问题


# How to use
in gradle 
<pre>
allprojects {
    repositories {
        jcenter()
        maven {
            url 'https://dl.bintray.com/kevinchen/maven'
        }
    }
}

dependencies {
    compile 'com.kevin.library:gif-library:1.0.3'
}

DaVinci.with(this)
              .getImageLoader()
              .load("http://qq.yh31.com/tp/zjbq/201706231712500189.gif")
              .into(new WeakReference<>(imageView));
              </pre>
              
Thanks for DaVinci which powered by CPPAlien.

https://github.com/CPPAlien/DaVinci
