# BasicFramework

Step 1.在项目的 build.gradle 中添加:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}Copy
Step 2. 引入

	dependencies {
	        implementation 'com.github.wasdzy111:BasicFramework:1.0.0'
	}
Step 3. 打开dataBinding
	
	dataBinding {
        	enabled = true
    	}
