# About
ANESamAds is an Adobe AIR native extension (ANE) for Android to show Samsung Ads.<br />
Supported functionality:<br />
- show ad;<br />
- hide ad;<br />
- show interstitial ad; (doesn't work correctly)<br />

I recommend to use Samsung Ads only if you was pushed (like me) to do so by Samsung and 100% Indie.

# Docs
Please, read docs and try ANE before asking any questions.<br />
http://www.samsungadhub.com/help/sdk.do<br />
http://help.adobe.com/en_US/air/extensions/index.html<br />


# Installation
Extension ID: com.pozirk.ads.SamAds<br />
Add "SamAds.ane" and "air\SamAds\bin\SamAds.swc" to your AIR project.<br />
Add the following lines to your AIR Aplication-app.xml file inside &lt;manifestAdditions&gt; section:<br />
<br />
&lt;uses-permission android:name="android.permission.INTERNET"/&gt;<br />
&lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/&gt;<br />
&lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/&gt;<br />
&lt;uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/&gt;<br />
&lt;application&gt;<br />
	&lt;activity android:name="com.sec.android.ad.AdActivity" android:configChanges="keyboardHidden|orientation|screenSize"/&gt;<br />
&lt;/application&gt;<br />


# Example
```actionscript
import com.pozirk.ads.samads.SamAds;
import com.pozirk.ads.samads.AdParams;
import com.pozirk.ads.samads.AdEvent;

...

protected var _samads:SamAds = null;

...

//> initialization of Samsung Ads
_samads = new SamAds();
_samads.addEventListener(AdEvent.INIT_OK, onEvent);
_samads.addEventListener(AdEvent.INIT_FAIL, onEvent);
_samads.addEventListener(AdEvent.AD_SHOW_OK, onEvent);
_samads.addEventListener(AdEvent.AD_SHOW_FAIL, onEvent);
_samads.addEventListener(AdEvent.INTERSTITIAL_FAIL, onEvent);
_samads.addEventListener(AdEvent.INTERSTITIAL_CLOSED, onEvent);
_samads.init("BANNER_INVENTORY_ID", "INTERSTITIAL_INVENTORY_ID");

...

protected function onEvent(ae:AdEvent):void
{
	trace(ae.type+" "+ae._data);
}
//<


//showing 728x90 ad at the bottom center side of the screen
_samads.show(AdParams.SIZE_728x90, AdParams.HALIGN_CENTER, AdParams.VALIGN_TOP);
```


# Misc
ANE is build with AIR3.9, in order to rebuild for another version (3.8-), do the following:<br />
- edit "air\extension.xml" and change 3.9, in very first line, to any 3.X you need;<br />
- edit "build.bat" and, in the very last line, change path from AIR3.9 SDK to any AIR3.X SDK you need;<br />
- execute "build.bat" to repack the ANE.<br />