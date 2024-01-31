# EMERGENCY BUTTON SAMPLE

### This app is a sample of how to use the emergency button. 

Please be aware that this application / sample is provided as-is for demonstration purposes without any guarantee of support

### Configuration
- install the apk from the Releases section
    ```adb install -g C:\Users\CXNT48\AndroidStudioProjects\DURESS-BUTTON\app\build\outputs\apk\debug\duress-button-sample-v1.0d.apk```, adjust the apk version if needed

- grant access to the Accessibility Service
    ```adb shell settings put secure enabled_accessibility_services com.zebra.duress_button_sample/com.zebra.duress_button_sample.EmergencyAccessibilityService```

