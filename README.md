# Android_PhoneNumber_Input
[![](https://jitpack.io/v/Willena/Android_PhoneNumber_Input.svg)](https://jitpack.io/#Willena/Android_PhoneNumber_Input)

A small library that bring a more powerful widget for telephone number inputs

## What can this lib do

The main purpose of this library is to pre-validate, phone numbers when asked to the user. The widget automaticaly genrate a number in the international format.
It also bring a more intuitive interface for users. Everytime I personnaly have to enter my phone number, I always ask myself : "Which format should I use ? The international one with the dialing code ? The nationnal one ? Should I remove the fist digit of the number ?"

These kind of question disappear with this widget.

Thanks to the more low level google's phonenumber library (https://github.com/googlei18n/libphonenumber) it is possible to format phonenumber as needed and also to validate them and now this library act as an interface for this library ! Cool isn't it ?

## How to install

This library is made for android from API 15 to API 29 (and probably more in the future ).
Installation within android Studio is pretty easy, just add these lines to your gradle dependencies.

1. Add this in your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
2. Add the dependency
```gradle
	dependencies {
	        compile 'com.github.Willena:Android_PhoneNumber_Input:1.3.1'
	}
```
## How to use

The widget can be used in your .xml layout, here is an example :
```xml
 <com.github.willena.phoneinputview.PhoneInputView
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:id="@+id/phoneId"/>
```
  
  and then in your activity :
  
```java
phoneView = (PhoneInputView) findViewById(R.id.phoneId);
```
 
There are some useful method such as :
- **setConfig** : this method take a CountryConfigurator as parameter
- **getConfig** : this method give you back the current configuration applied to the widget
- **getFormatedNumber** : this method should be called when the OnValidEntryListener is triggered. The method also accept as the output type as parameter. The default one is INTERNATIONAL but here are the format supported by google's number formating lib:
  * E164
  * INTERNATIONAL
  * NATIONAL
  * RFC3966
- **setCountry** : this method sets the country on the spinner
- **setDisplayMobileHint** : this method sets the phone number type that will be displayed as hint
- **setPhoneNumber** : this method sets the number and the country.
- **getCountryList** : this method returns the list of availale country

There are also some Listener to listen to allowing you to get information from the widget in real time such as:
- **OnCountryChangedListener** : triggered when the user decide to change the country in the spinner, you can get the associated country code.
- **OnValidEntryListener** : triggered when the user enter a valid number ( may be false )

### Three words about the CountryConfigurator

For now it only permit you to display or not some element in the Spiner, such as the flag _(1)_, the country code _(2)_, and the dial code _(3)_.
By default all values are set to true, so every thing is displayed

![this image shows what is (1) (2) (3) ](https://github.com/Willena/Android_PhoneNumber_Input/raw/master/screenshoot/spinner_display_explain.png)


```java
CountryConfigurator config = new CountryConfigurator();
config.setDisplayFlag(true);
config.setDisplayCountryCode(true);
config.setDisplayDialingCode(true);
config.setPhoneNumberHintType(MOBILE); //Set the phone number type that will be displayed as hint (MOBILE, FIXED, NONE)
config.setDefaultCountry("FR"); //Set the default country that will be selected when loading
``` 

Settings can be aplied by using : ``` phoneView.setConfig(config); ```

## Screenshots

![this annimated image shows the widget in action](https://github.com/Willena/Android_PhoneNumber_Input/raw/master/screenshoot/demo_phone_number.gif)


## Contributions
Any suggestions are welcome.
This library may not be complete (some flags or country may be missing from the list). Feel free to fork this depos and add new features to this lib.
If you have any questions, feel free to ask, I will be happy to answer them.

