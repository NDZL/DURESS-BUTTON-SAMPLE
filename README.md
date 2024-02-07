# EMERGENCY BUTTON SAMPLE

### This app is a sample of how to use the emergency button. 

Please be aware that this application / sample is provided as-is for demonstration purposes without any guarantee of support

### Configuration
- install the apk from the Releases section
    ```adb install -g C:\Users\CXNT48\AndroidStudioProjects\DURESS-BUTTON\app\build\outputs\apk\debug\duress-button-sample-v1.0d.apk```, adjust the apk version if needed

- grant access to the Accessibility Service
    ```adb shell settings put secure enabled_accessibility_services com.zebra.duress_button_sample/com.zebra.duress_button_sample.EmergencyAccessibilityService```

- when installing and configuring manually, grant all the permissions from the app's Info activty, including 'Display over other apps'
- and set it as an Accessibility Service
  
    ![image](https://github.com/NDZL/DURESS-BUTTON-SAMPLE/assets/11386676/57857212-8bd5-4ca4-a473-955a3a18e3c1)


### Run
- reboot the device, so the duress button service is automatically started
- long-press the left-side hardare button of any Zebra device to trigger the alarm
- a modal window is displayed on top of all apps, flashing the screnn
- long-press again to silent it

### Screenshots

The Duress button service

Ongoing Alert

![image](https://github.com/NDZL/DURESS-BUTTON-SAMPLE/assets/11386676/8e1d3da8-bdbf-4c74-8bee-040e70288d2e)
![image](https://github.com/NDZL/DURESS-BUTTON-SAMPLE/assets/11386676/e1f164ae-d0a0-49fc-8987-ba29e206d9e0)



