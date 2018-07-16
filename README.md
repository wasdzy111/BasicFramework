# BasicFramework

Step 1.在项目的 build.gradle 中添加:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
Step 2. 引入

	dependencies {
	        implementation 'com.github.wasdzy111:BasicFramework:1.0.0'
	}
	
Step 3. 打开dataBinding
	
	dataBinding {
        	enabled = true
    	}
Step 4. 依赖引入

    api 'com.trello:rxlifecycle:1.0'
    api 'com.trello:rxlifecycle-android:1.0'
    api 'com.trello:rxlifecycle-components:1.0'
    api 'io.reactivex.rxjava2:rxjava:+'
    api 'com.google.code.gson:gson:2.2.4'
    api 'io.reactivex.rxjava2:rxandroid:+'
    api 'com.orhanobut:logger:1.15'
    api 'org.greenrobot:eventbus:3.0.0'
    api 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'
    api 'com.tbruyelle.rxpermissions2:rxpermissions:0.9.4@aar'
    api 'com.tencent.bugly:crashreport:latest.release'
    api 'com.tencent.bugly:nativecrashreport:latest.release'
    //api 'com.android.support:multidex:1.0.0'
    api 'org.xutils:xutils:3.5.0'
