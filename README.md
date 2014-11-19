9/7 linwei
虚拟按键界面分屏、布局移动到中央

8/7 linwei
+SuperBarReceiver：添加launcher启动后的屏蔽控制；
+MyContext$startDaemonSevice(),clearService(): 启动服务的方法写入MyContext中；

7/31 linwei
+RingTrajectory$getPathAfterAdapte(),pointToPath(): 对戒指产生的轨迹进行缩放处理，平滑处理；
+TrajectoryView： 用于绘制手势轨迹的View；
部分细节优化；

7/30 linwei
-GestureOperateReceiver
-VirtualKeyReceiver
-GestureOperateService
-SensorService
+BaseInputService: 针对特定输入设备的输入事件监听服务基类；
+RingGestureService: 戒指设备的监听服务主类，实例化类,负责对戒指手势输入事件的收集和处理；
+Device: 设备结点的描述信息；
+DeviceManager: 获取所有的设备结点,并能根据名称获取相应的设别结点名；
+RawEvent: 从设备结点获取的原始数据类型；
+RingTrajectory: 戒指监听轨迹类,轨迹识别（修改自王彥学长，初始版本由手机陀螺仪控制）；
duxin
+BaseSensorService: 传感器抬头监听服务基类；


Before 7/28 duxin
+ ...

Change Log：

####################################################
##
##  SuperBar
##	功能：全局启动虚拟按键或者手势操作面板服务
##	Author：杜鑫/林伟
##
###############################################