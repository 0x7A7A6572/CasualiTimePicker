# CasualiTimePicker v1.0.0
完全使用NumberPicker实现的时间选择器。<p>
 -- CasualiTimePicker是对 iTimePicker 的封装，CasualiTimePicker增加了模式的切换。<p>
 -- iTimePickers使用了多个NumberPicker组成 ，并管理数据的联动，显示和设置时间。<p>
 
 
 

## 界面
![]()![]()
<img style="display:inline" width="200" src="https://blog-static.cnblogs.com/files/zzerx/itimepicker1.gif"/>
<img style="display:inline" width="200" src="https://blog-static.cnblogs.com/files/zzerx/itimepicker2.gif"/>
<img style="display:inline" width="200" src="https://blog-static.cnblogs.com/files/zzerx/itimepicker3.gif"/>

## CasualiTimePicker使用
添加依赖<p>
 

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```

	dependencies {
	        implementation 'com.github.0x7A7A6572:CustomImageView:Tag'
	}
```
布局文件中添加：
```xml
        <cn.zzerx.itimepicker.CasualiTimePicker
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
```


部分api
CasualiTimePicker --|


#### setTimeSelectMode 设置显示模式
#### getPickerParent 获取iTimePicker
#### setOnValueChangesListener 监听模式改变 


iTimePicker --|
   #### getTimePicker 获取当前显示的时间
   #### setTimePicker 设置时间
   
   
## ![下载Demo](https://github.com/0x7A7A6572/CasualiTimePicker/blob/master/app-debug.apk)

